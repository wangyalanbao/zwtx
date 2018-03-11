/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.controller.shop;

import com.hxlm.health.web.*;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.service.*;
import com.hxlm.health.web.util.SettingUtils;
import com.hxlm.health.web.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * Controller - 会员注册
 */
@Controller("shopRegisterController")
@RequestMapping("/register")
public class RegisterController extends BaseController {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "memberChildServiceImpl")
	private MemberChildService memberChildService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "configServiceImpl")
	private ConfigService configService;


	@RequestMapping(value = "/sendsms", method = RequestMethod.GET)
	public
	@ResponseBody
	Object sendsms(String phone) {
		String randomCode=(int) (Math.random() * 9000 + 1000) + "";
		randomCode = "您的验证码是" + randomCode + "，15分钟内有效";
		int status = configService.sendSmsYH(phone,randomCode);
		return status;
	}

	/**
	 * 检查用户名是否被禁用或已存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public
	@ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (memberService.usernameDisabled(username) || memberService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查E-mail是否存在
	 */
	@RequestMapping(value = "/check_email", method = RequestMethod.GET)
	public
	@ResponseBody
	boolean checkEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		if (memberService.emailExists(email)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 注册页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		model.addAttribute("genders", Member.Gender.values());
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/shop/register/index";
	}

	/**
	 * 注册提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public
	@ResponseBody
	Message submit(String captchaId, String captcha, String username, String email, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String password = rsaService.decryptParameter("enPassword", request);
		rsaService.removePrivateKey(request);

		if (!captchaService.isValid(Setting.CaptchaType.memberRegister, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");
		}
		Setting setting = SettingUtils.get();
		if (!setting.getIsRegisterEnabled()) {
			return Message.error("shop.register.disabled");
		}
		if (!isValid(Member.class, "username", username, BaseEntity.Save.class) || !isValid(Member.class, "password", password, BaseEntity.Save.class) || !isValid(Member.class, "email", email, BaseEntity.Save.class)) {
			return Message.error("shop.common.invalid");
		}
		if (username.length() < setting.getUsernameMinLength() || username.length() > setting.getUsernameMaxLength()) {
			return Message.error("shop.common.invalid");
		}
		if (password.length() < setting.getPasswordMinLength() || password.length() > setting.getPasswordMaxLength()) {
			return Message.error("shop.common.invalid");
		}
		if (memberService.usernameDisabled(username) || memberService.usernameExists(username)) {
			return Message.error("shop.register.disabledExist");
		}
		if (!setting.getIsDuplicateEmail() && memberService.emailExists(email)) {
			return Message.error("shop.register.emailExist");
		}

		Member member = new Member();
		List<MemberAttribute> memberAttributes = memberAttributeService.findList();
		for (MemberAttribute memberAttribute : memberAttributes) {
			String parameter = request.getParameter("memberAttribute_" + memberAttribute.getId());
			if (memberAttribute.getType() == MemberAttribute.Type.name || memberAttribute.getType() == MemberAttribute.Type.address || memberAttribute.getType() == MemberAttribute.Type.zipCode || memberAttribute.getType() == MemberAttribute.Type.phone || memberAttribute.getType() == MemberAttribute.Type.mobile || memberAttribute.getType() == MemberAttribute.Type.text || memberAttribute.getType() == MemberAttribute.Type.select) {
				if (memberAttribute.getIsRequired() && StringUtils.isEmpty(parameter)) {
					return Message.error("shop.common.invalid");
				}
				member.setAttributeValue(memberAttribute, parameter);
			} else if (memberAttribute.getType() == MemberAttribute.Type.gender) {
				Member.Gender gender = StringUtils.isNotEmpty(parameter) ? Member.Gender.valueOf(parameter) : null;
				if (memberAttribute.getIsRequired() && gender == null) {
					return Message.error("shop.common.invalid");
				}
				member.setGender(gender);
			} else if (memberAttribute.getType() == MemberAttribute.Type.birth) {
				try {
					Date birth = StringUtils.isNotEmpty(parameter) ? DateUtils.parseDate(parameter, CommonAttributes.DATE_PATTERNS) : null;
					if (memberAttribute.getIsRequired() && birth == null) {
						return Message.error("shop.common.invalid");
					}
					member.setBirth(birth);
				} catch (ParseException e) {
					return Message.error("shop.common.invalid");
				}
			} else if (memberAttribute.getType() == MemberAttribute.Type.area) {
				Area area = StringUtils.isNotEmpty(parameter) ? areaService.find(Long.valueOf(parameter)) : null;
				if (area != null) {
					member.setArea(area);
				} else if (memberAttribute.getIsRequired()) {
					return Message.error("shop.common.invalid");
				}
			} else if (memberAttribute.getType() == MemberAttribute.Type.checkbox) {
				String[] parameterValues = request.getParameterValues("memberAttribute_" + memberAttribute.getId());
				List<String> options = parameterValues != null ? Arrays.asList(parameterValues) : null;
				if (memberAttribute.getIsRequired() && (options == null || options.isEmpty())) {
					return Message.error("shop.common.invalid");
				}
				member.setAttributeValue(memberAttribute, options);
			}
		}
		member.setUsername(username.toLowerCase());
		member.setPassword(DigestUtils.md5Hex(password));
		member.setEmail(email);
		member.setPoint(setting.getRegisterPoint());
		member.setAmount(new BigDecimal(0));
		member.setBalance(new BigDecimal(0));
		member.setIsEnabled(true);
		member.setIsLocked(false);
		member.setLoginFailureCount(0);
		member.setLockedDate(null);
		member.setRegisterIp(request.getRemoteAddr());
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setSafeKey(null);
		member.setMemberRank(memberRankService.findDefault());
		member.setFavoriteProducts(null);
		memberService.save(member);

		Map<String, Object> attributes = new HashMap<String, Object>();
		Enumeration<?> keys = session.getAttributeNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			attributes.put(key, session.getAttribute(key));
		}
		session.invalidate();
		session = request.getSession();
		for (Entry<String, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}

		session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), member.getUsername()));
		WebUtils.addCookie(request, response, Member.USERNAME_COOKIE_NAME, member.getUsername());

		return Message.success("shop.register.success");
	}


	/*******************************分割线*********************************************************/
	/**
	 *
	 *
	 * 获得验证码接口
	 */
	/**
	private Integer sendSmsYH(String mobile, String content) {
		SMSServiceInput input = new SMSServiceInput();

		input.setServiceID("炎黄手机闻音短信业务");
		input.setSendtarget(mobile);
		String smsSuffix = this.configService.getSmsSuffix();
		if (!StringUtils.isEmpty(smsSuffix)) {
			content = content + smsSuffix;
		}

		input.setSmcontent(content);
		input.setRcompleteTime(DateEditor.format(new Date(), "HH:mm:ss"));
		input.setPAD4("code");

		SMSWebServicePortType service = this.smsServiceYanhuang.getSMSWebServiceHttpPort();
		service.soaSMSServiceForCUNLE(input);
		return Status.SUCCESS;
	}
	 **/

	@RequestMapping(value = "/captcha", method = RequestMethod.POST)
	public @ResponseBody
	Result captcha(String username, HttpSession session) {
		//定义验证码
		String randomCode = "";
		Result result = new Result();
		Map map = new HashMap();
		int status;
		//设定username为手机号格式
		Pattern p = Pattern.compile("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
		//判断是否为手机号是发送验证码，执行后续程序
		if(p.matcher(username).matches()){
			//	判断手机号是否可以注册
			if (memberService.usernameDisabled(username) || memberService.usernameExists(username)) {
				status= Status.INVALID_MOBILE;
				randomCode = "手机号不正确或已被注册，请重新输入";
			}else {
				// map2为空，生成验证吗并存session下发客户端
				Map map2 = (Map) session.getAttribute(username);
				if (map2 == null || map2.isEmpty()) {
					// 获取四位随机数
					randomCode=(int) (Math.random() * 9000 + 1000) + "";
					map.put("time", System.currentTimeMillis());
					map.put("code", randomCode);

					//给客户手机发送短信
					status = configService.sendSmsYH(username, randomCode);
					session.setAttribute(username, map);
					session.setMaxInactiveInterval(900);

				} else {
						// 如果map中有值获得手机号所对的时间
						Long time = (Long) map2.get("time");
						// 获取当前时间
						Long nowtime = System.currentTimeMillis();
						// 超时了，可以重新生成验证码，大于60秒
						if (nowtime-time>60000L) {
							//比较这两个时间，超过一分钟，再次正常下发验证码，否则返回客户端错误吗，错误信息为两次请求的的时间间隔太短
							randomCode=(int) (Math.random() * 9000 + 1000) + "";
							map.put("time", System.currentTimeMillis());
							map.put("code", randomCode);

							//给客户手机发送短信
							status = configService.sendSmsYH(username, randomCode);
							session.setAttribute(username, map);
							session.setMaxInactiveInterval(900);
						} else { // 没有超时，不需要重新生成。直接输出result结果。
							status = Status.INVALID_EXCHANGE_TIME;
							randomCode = "两次请求时间间隔太短";
						}
					}
				}
		} else { //不是手机号返回一个错误信息
			status = Status.INVALID_USER_MOBILE;
			randomCode = "只能输入手机号";
		}
		result.setData(randomCode);
		result.setStatus(status);
		return result;
	}

	/**
	 * 修改后 验证注册信息 验证接口
	 * 从前台获取信息，手机号，密码，验证码
	 * 注册第二步
	 * com.hxlm.health.web.util.StringUtils.string2Unicode()
	 */
	@RequestMapping(value = "/commit", method = RequestMethod.POST)
	public @ResponseBody
	Result commit(String username, String password, String password2, String code, HttpServletRequest request, HttpSession session) {
		Result result = new Result();

		if (memberService.usernameDisabled(username) || memberService.usernameExists(username)){
			result.setStatus(Status.INVALID_MOBILE);
			result.setData("手机号不正确或已被注册，请重新输入");
			return result;
		}


		// 取得username、和验证码、密码，如果验证码与username匹配则注册成功，否则返回错误信息
		Map map = (Map) session.getAttribute(username);

		if (map == null || map.isEmpty()) { //测试用注册验证session过期
			// session过期或者没有下发过验证码，返回客户端错误信息
			result.setData("服务超时，请重新获取验证码");
			result.setStatus(Status.SERVER_TIME_OUT);
		} else { //做验证，保存数据库或者返回客户端相关错误信息等
			//注册获取验证码使用代码
			String randomCode = (String) map.get("code");

			//测试用注册获取验证码测试用
//			String randomCode="1234";

			//验证手机号对应的验证码是否匹配
			if (randomCode.equals(code)) {
				//判断密码长度是否符合规定
				if(password.length()<4){
					result.setData("密码长度不允许小于4");
					result.setStatus(Status.INVALID_PARAMS);
				} else {
					if(password.equals(password2)){
						Member member = new Member();
						member.setUsername(username);
						//给密码加密
						member.setPassword(DigestUtils.md5Hex(password));
						member.setUa(request.getHeader("User-Agent"));
						member.setIsEnabled(true);
						member.setIsLocked(false);
						member.setIsMark(false);
						member.setLoginFailureCount(0);
						member.setPoint(0L);
						member.setAmount(new BigDecimal(0));
						member.setBalance(new BigDecimal(0));
						member = memberService.saveAndReturn(member);
						MemberChild memberChild=new MemberChild();

						//注册会员并添加子账户
						memberChild.setName(member.getUsername());
						memberChild.setIsMedicare(MemberChild.Medicare.no);
						memberChild.setMobile(member.getUsername());
						memberChild.setMember(member);
						memberChildService.save(memberChild);

						Map mmap = new HashMap();
						mmap.put("token", UUID.randomUUID().toString());
						session.invalidate();
						session = request.getSession();
						session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), username));
						mmap.put("JSESSIONID", session.getId());
						mmap.put("member", member);
						result.setData(mmap);
						result.setStatus(Status.SUCCESS);
					}else {
						result.setData("两次密码不同,请重新输入");
						result.setStatus(Status.INVALID_PASSWORD);
					}
				}
			} else {
				result.setData("验证码错误，请重新输入");
				result.setStatus(Status.INVALID_REGCODE);
			}
		}


		/**
		 *

		String randomCode="1234";

		//验证手机号对应的验证码是否匹配
		if (randomCode.equals(code)) {
			//判断密码长度是否符合规定
			if(password.length()<4){
				result.setData("密码长度不允许小于4");
				result.setStatus(Status.INVALID_PARAMS);
			} else {
				if(password.equals(password2)){
					Member member = new Member();
					member.setUsername(username);
					//给密码加密
					member.setPassword(DigestUtils.md5Hex(password));
					member.setUa(request.getHeader("User-Agent"));
					member.setIsEnabled(true);
					member.setIsLocked(false);
					member = memberService.saveAndReturn(member);
					MemberChild memberChild=new MemberChild();

					//注册会员并添加子账户
					memberChild.setName(member.getUsername());
					memberChild.setIsMedicare(MemberChild.Medicare.no);
					memberChild.setMobile(member.getUsername());
					memberChild.setMember(member);
					memberChildService.save(memberChild);

					Map mmap = new HashMap();
					mmap.put("token", UUID.randomUUID().toString());
					session.invalidate();
					session = request.getSession();
					session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), username));
					mmap.put("JSESSIONID", session.getId());
					mmap.put("member", member);
					result.setData(mmap);
					result.setStatus(Status.SUCCESS);
				}else {
					result.setData("两次密码不同,请重新输入");
					result.setStatus(Status.INVALID_PASSWORD);
				}
			}
		} else {
			result.setData("验证码错误，请重新输入");
			result.setStatus(Status.INVALID_REGCODE);
		}
		 */
		return result;
	}

	/**
	 * 登录接口
	 *
	 *
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public @ResponseBody
	Result commit(String username,String password,HttpServletRequest request){
		Result result=new Result();
		Member member;
		int status=Status.SUCCESS;
		String message="";
		member = memberService.findByUsername(username);
		if (member == null) {//此账号不存在
			status=Status.INVALID_PARAMS;
			message = "账号不存在或手机号码错误,请重新输入";
		}else {
		String Username=member.getUsername();
		String Password=member.getPassword();
		if(username.equals(Username)&&DigestUtils.md5Hex(password).equals(Password)){//校对加密密码
			status=Status.SUCCESS;
			message = "登录成功";
		}else {
			status=Status.INVALID_PARAMS;
			message = "用户名或者密码错误";
		}
		}
		result.setData(message);
		result.setStatus(status);
		return result;
	}



	}
