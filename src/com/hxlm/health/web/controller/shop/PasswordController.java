/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.controller.shop;

import com.hxlm.health.web.*;
import com.hxlm.health.web.Setting.CaptchaType;
import com.hxlm.health.web.entity.BaseEntity.Save;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.SafeKey;
import com.hxlm.health.web.service.CaptchaService;
import com.hxlm.health.web.service.ConfigService;
import com.hxlm.health.web.service.MailService;
import com.hxlm.health.web.service.MemberService;
import com.hxlm.health.web.util.SettingUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controller - 密码
 * 
 * 
 * 
 */
@Controller("shopPasswordController")
@RequestMapping("/password")
public class PasswordController extends BaseController {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "configServiceImpl")
	private ConfigService configService;

	/**
	 * 找回密码
	 */
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(Model model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/shop/password/find";
	}

	/**
	 * 找回密码提交
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public @ResponseBody
	Message find(String captchaId, String captcha, String username, String email) {
		if (!captchaService.isValid(CaptchaType.findPassword, captchaId, captcha)) {//验证码输入错误
			return Message.error("shop.captcha.invalid");
		}
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(email)) {//username 或email为空返回参数错误
			return Message.error("shop.common.invalid");
		}
		Member member = memberService.findByUsername(username);//根据username查询会员
		if (member == null) {//member==null 用户不存在
			return Message.error("shop.password.memberNotExist");
		}
		if (!member.getEmail().equalsIgnoreCase(email)) {//邮箱输入错误
			return Message.error("shop.password.invalidEmail");
		}
		Setting setting = SettingUtils.get();
		SafeKey safeKey = new SafeKey();
		safeKey.setValue(UUID.randomUUID().toString() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
		safeKey.setExpire(setting.getSafeKeyExpiryTime() != 0 ? DateUtils.addMinutes(new Date(), setting.getSafeKeyExpiryTime()) : null);
		member.setSafeKey(safeKey);
		memberService.update(member);
		mailService.sendFindPasswordMail(member.getEmail(), member.getUsername(), safeKey);
		return Message.success("shop.password.mailSuccess");
	}

	/**
	 * 重置密码
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String reset(String username, String key, Model model) {
		Member member = memberService.findByUsername(username);
		if (member == null) {//如果会员为空的时候，返回一个错误试图
			return ERROR_VIEW;
		}
		SafeKey safeKey = member.getSafeKey();//安全密钥获得会员秘钥。；会员安全秘钥是否为空，秘钥是否为空
		if (safeKey == null || safeKey.getValue() == null || !safeKey.getValue().equals(key)) {
			return ERROR_VIEW;
		}
		if (safeKey.hasExpired()) {//找回密码连接已失效
			model.addAttribute("erroInfo", Message.warn("shop.password.hasExpired"));
			return ERROR_VIEW;
		}
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		model.addAttribute("member", member);
		model.addAttribute("key", key);
		return "/shop/password/reset";
	}

	/**
	 * 重置密码提交
	 */
	@RequestMapping(value = "reset", method = RequestMethod.POST)
	public @ResponseBody
	Message reset(String captchaId, String captcha, String username, String newPassword, String key) {
		if (!captchaService.isValid(CaptchaType.resetPassword, captchaId, captcha)) {
			return Message.error("shop.captcha.invalid");//验证码输入错误
		}
		Member member = memberService.findByUsername(username);//根据用户名查找会员
		if (member == null) {/** 错误消息 */
			return ERROR_MESSAGE;
		}
		if (!isValid(Member.class, "password", newPassword, Save.class)) {//数据验证-类型-属性-值-验证组
			return Message.warn("shop.password.invalidPassword");//密码格式错误
		}
		Setting setting = SettingUtils.get();
		if (newPassword.length() < setting.getPasswordMinLength() || newPassword.length() > setting.getPasswordMaxLength()) {
			return Message.warn("shop.password.invalidPassword");
		}
		SafeKey safeKey = member.getSafeKey();
		if (safeKey == null || safeKey.getValue() == null || !safeKey.getValue().equals(key)) {
			return ERROR_MESSAGE;
		}
		if (safeKey.hasExpired()) {
			return Message.error("shop.password.hasExpired");
		}
		member.setPassword(DigestUtils.md5Hex(newPassword));
		safeKey.setExpire(new Date());
		safeKey.setValue(null);
		memberService.update(member);
		return Message.success("shop.password.resetSuccess");
	}

/****************************************************分割线*********************************************************************/
	/**
	 *更新-----修改密码-- 获取验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/captchaPassword",method = RequestMethod.POST)
	public ErrorMsg captcha(String username,HttpSession session){
		Result result=new Result();
		ErrorMsg errorMsg = new ErrorMsg();
		Map map = new HashMap();
		String randomCode="";
		Member member=memberService.findByUsername(username);
		//先判断用户名是否存在
		if(member==null){
			errorMsg.setMessage("用户不存在或者手机号码错误，请重新输入");
			errorMsg.setCode(Status.INVALID_PARAMS);
		}else {
			// map2为空，生成验证码并存入session下发客户端
			Map map2= (Map) session.getAttribute(username);
			if (map2 == null || map2.isEmpty()) {
				randomCode= (int)(Math.random()*9000+1000)+"";
				//给客户手机发送短信
				configService.sendSmsYH(username, randomCode);
				map.put("time", System.currentTimeMillis());
				map.put("code",randomCode);
				session.setAttribute(username,map);
				session.setMaxInactiveInterval(9000);
				result.setCode(Status.SUCCESS);
				result.setData(randomCode);
			}else {
					//如果map中有值获得手机号所对的时间
					Long time = (Long) map2.get("time");
					//获取当前时间
					Long nowtime = System.currentTimeMillis();
					// 超时了，可以重新生成验证码，大于60秒
					if (nowtime-time>60000L) {
						//比较这两个时间，超过一分钟，再次正常下发验证码，否则返回客户端错误吗，错误信息为两次请求的的时间间隔太短
						randomCode=(int) (Math.random() * 9000 + 1000) + "";
						//给客户手机发送短信
						configService.sendSmsYH(username, randomCode);
						map.put("time", System.currentTimeMillis());
						map.put("code", randomCode);
						session.setAttribute(username, map);
						session.setMaxInactiveInterval(9000);
						result.setCode(Status.SUCCESS);
						result.setData(randomCode);
					} else { // 没有超时，不需要重新生成。直接输出result结果。
						result.setCode(Status.INVALID_EXCHANGE_TIME);
						result.setData("两次请求时间间隔太短");
					}
			}
		}
		return result;

	}


	/**
	 * 更新-----修改密码
	 */
	@ResponseBody
	@RequestMapping(value = "/resetPassword",method = RequestMethod.POST)
	public Result resetPassword(String code,String username,String newPassword,HttpSession session){
		Member member=memberService.findByUsername(username);
		Result result=new Result();
		Map map = (Map) session.getAttribute(username);
		if (map == null || map.isEmpty()) { //测试用注册验证session过期
			// session过期或者没有下发过验证码，返回客户端错误信息
			result.setMessage("服务超时，请重新获取验证码");
			result.setCode(Status.SERVER_TIME_OUT);
		} else { //做验证，保存数据库或者返回客户端相关错误信息等
//			注册获取验证码使用代码
			String randomCode = (String) map.get("code");
//			String randomCode = "1234";
			//取得username、和验证码、密码，如果验证码与username匹配则注册成功，否则返回错误信息
			//判断验证码是否正确
			if (randomCode.equals(code)) {
				//验证新密码类型是否正确，更改成功
				if (newPassword.length() < 4) {
					result.setData("密码长度不允许小于4");
					result.setCode(Status.INVALID_PARAMS);
				} else {
					member.setPassword(DigestUtils.md5Hex(newPassword));
					memberService.update(member);
					result.setData("恭喜您修改成功");
					result.setCode(Status.SUCCESS);
				}
			} else {
				result.setData("验证码错误，请重新输入");
				result.setCode(Status.INVALID_PARAMS);
			}
		}
		return result;
	}


}