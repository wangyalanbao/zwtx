/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.controller.shop;

import com.hxlm.health.web.*;
import com.hxlm.health.web.Setting.AccountLockType;
import com.hxlm.health.web.Setting.CaptchaType;
import com.hxlm.health.web.entity.Cart;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.service.CaptchaService;
import com.hxlm.health.web.service.MemberService;
import com.hxlm.health.web.service.RSAService;
import com.hxlm.health.web.util.SettingUtils;
import com.hxlm.health.web.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map.Entry;

/**
 * Controller - 会员登录
 * 
 * 
 * 
 */
@Controller("shopLoginController")
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	/**
	 * 登录检测
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public @ResponseBody
	Boolean check() {
		return memberService.isAuthenticated();
	}

	/**
	 * 登录检测，app使用
	 */
	@RequestMapping(value = "/logincheck", method = RequestMethod.GET)
	public @ResponseBody
	ErrorMsg loginCheck() {
		Result result = new Result();
		result.setData(memberService.isAuthenticated());
		result.setCode(Status.SUCCESS);
		return result;
	}

	/**
	 * 登录页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(String redirectUrl, HttpServletRequest request, ModelMap model) {
		Setting setting = SettingUtils.get();
		if (redirectUrl != null && !redirectUrl.equalsIgnoreCase(setting.getSiteUrl()) && !redirectUrl.startsWith(request.getContextPath() + "/") && !redirectUrl.startsWith(setting.getSiteUrl() + "/")) {
			redirectUrl = null;
		}
		model.addAttribute("redirectUrl", redirectUrl);
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/shop/login/index";
	}

	/**
	 * 登录提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody
	Message submit(String captchaId, String captcha, String username, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String password = rsaService.decryptParameter("enPassword", request);
		rsaService.removePrivateKey(request);

		if (!captchaService.isValid(CaptchaType.memberLogin, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");//验证码输入错误
		}
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return Message.error("shop.common.invalid");//参数错误
		}
		Member member;
		Setting setting = SettingUtils.get();
		if (setting.getIsEmailLogin() && username.contains("@")) {
			List<Member> members = memberService.findListByEmail(username);
			if (members.isEmpty()) {
				member = null;
			} else if (members.size() == 1) {
				member = members.get(0);
			} else {//该账号不支持EMAIL
				return Message.error("shop.login.unsupportedAccount");
			}
		} else {
			member = memberService.findByUsername(username);
		}
		if (member == null) {//此账号不存在
			return Message.error("shop.login.unknownAccount");
		}
		if (!member.getIsEnabled()) {//此账号被禁用
			return Message.error("shop.login.disabledAccount");
		}
		if (member.getIsLocked()) {
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.member)) {
				int loginFailureLockTime = setting.getAccountLockTime();
				if (loginFailureLockTime == 0) {//此账号被锁定
					return Message.error("shop.login.lockedAccount");
				}
				Date lockedDate = member.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				if (new Date().after(unlockDate)) {
					member.setLoginFailureCount(0);
					member.setIsLocked(false);
					member.setLockedDate(null);
					memberService.update(member);
				} else {
					return Message.error("shop.login.lockedAccount");
				}
			} else {
				member.setLoginFailureCount(0);
				member.setIsLocked(false);
				member.setLockedDate(null);
				memberService.update(member);
			}
		}
		//String md5 = DigestUtils.md5Hex(str)
		if (!DigestUtils.md5Hex(password).equals(member.getPassword())) {
			int loginFailureCount = member.getLoginFailureCount() + 1;
			if (loginFailureCount >= setting.getAccountLockCount()) {
				member.setIsLocked(true);
				member.setLockedDate(new Date());
			}
			member.setLoginFailureCount(loginFailureCount);
			memberService.update(member);
			if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.member)) {//密码错误，若连续{0}次密码错误账号将被锁定
				return Message.error("shop.login.accountLockCount", setting.getAccountLockCount());
			} else {
				return Message.error("shop.login.incorrectCredentials");
			}
		}
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setLoginFailureCount(0);
		memberService.update(member);

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

		session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), username));
		WebUtils.addCookie(request, response, Member.USERNAME_COOKIE_NAME, member.getUsername());

		return SUCCESS_MESSAGE;
	}


	/**
	 * 登录接口
	 */
	@RequestMapping(value = "/commit",method = RequestMethod.POST)
	public @ResponseBody
	ErrorMsg commit(String username, String password, HttpServletRequest request, HttpServletResponse response, HttpSession session,String deviceToken){
		Result result=new Result();
		ErrorMsg errorMsg = new ErrorMsg();
		Member member = memberService.findByUsername(username);
		if (member == null) { //此账号不存在
			errorMsg.setMessage("该账号不存在");
			errorMsg.setCode(Status.INVALID_PARAMS);
		} else {
			if(DigestUtils.md5Hex(password).equals(member.getPassword())){

				Map map = new HashMap();
				String token = UUID.randomUUID().toString();
				map.put("token", token);
				WebUtils.addCookie(request, response, "token", token);

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
				session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), username));

				map.put("JSESSIONID", session.getId());
				map.put("member", member);
				memberService.updateUA(member.getId(), request.getHeader("User-Agent"));
				if(StringUtils.isEmpty(deviceToken)){
					memberService.updateToken(member.getId(),null);
				}else {
					memberService.updateToken(member.getId(),deviceToken);
				}
				result.setData(map);
				result.setCode(Status.SUCCESS);
			}else {
				errorMsg.setMessage("登录失败");
				errorMsg.setCode(Status.INVALID_PARAMS);
			}
		}
	return result;
	}

	@RequestMapping(value = "index" , method = RequestMethod.GET)
	public String index(){
			return "/shop/indexback";
	}

}