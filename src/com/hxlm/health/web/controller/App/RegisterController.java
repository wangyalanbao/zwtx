package com.hxlm.health.web.controller.app;

import com.hxlm.health.web.*;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.MsgCode;
import com.hxlm.health.web.service.MemberService;
import com.hxlm.health.web.service.MsgCodeService;
import com.hxlm.health.web.util.SendSms;
import com.hxlm.health.web.util.SettingUtils;
import com.hxlm.health.web.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2018/3/11.
 *
 * app
 */
@Controller("registerController")
@RequestMapping("/app/register")
public class RegisterController extends BaseController{

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;
    @Resource(name = "msgCodeServiceImpl")
    private MsgCodeService msgCodeService;

    /**
     *
     * @param mobile
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verification", method = RequestMethod.POST)
    public Object dosendmsgcode(String mobile, HttpSession session) {
        Map map = new HashMap();

        String randomCode;
        MsgCode msgCode = msgCodeService.findOne(mobile);
        if (msgCode == null) {
            // 获取四位随机数
            randomCode = (int) (Math.random() * 9000 + 1000) + "";
            map.put("time", System.currentTimeMillis());
            map.put("code", randomCode);

            System.out.println(" ====== "+randomCode);
            //String content = "您的校验码是：【" + randomCode + "】，有效期是5分钟，请不要把校验码泄露给其他人！";
            String content = "您的校验码是：【" + randomCode + "】，有效期是5分钟，请不要把校验码泄露给其他人。如非本人操作，可不用理会！";
            String status = "0";
            //给客户手机发送短信
            try {
                status = new SendSms().getVerification(mobile, content, randomCode);
            } catch (Exception e) {
                map.put("errcode", 258);
                map.put("message", "验证码发送失败,请稍后重试!");
                map.put("needauth", 1);
                return map;
            }
            if (!status.equals("2")) {
                map.put("errcode", 258);
                map.put("message", "验证码发送失败,请稍后重试!");
                map.put("needauth", 1);
                return map;
            }

            msgCode = new MsgCode();
            msgCode.setMobile(mobile);
            msgCode.setRandomCode(randomCode);
            msgCodeService.save(msgCode);

            map.remove("code");
            map.put("errcode", 0);
            map.put("message", "");
            map.put("needauth", 0);
        } else {
            Long time = msgCode.getCreateDate().getTime();
            Long nowtime = System.currentTimeMillis();
            if (nowtime - time > 60000L) {
                map = new HashMap();
                // 获取四位随机数
                randomCode = (int) (Math.random() * 9000 + 1000) + "";
                map.put("time", System.currentTimeMillis());
                map.put("code", randomCode);

                System.out.println(" ====== "+randomCode);
                //String content = "您的校验码是：【" + randomCode + "】，有效期是5分钟，请不要把校验码泄露给其他人！";
                String content = "您的校验码是：【" + randomCode + "】，有效期是5分钟，请不要把校验码泄露给其他人。如非本人操作，可不用理会！";
                String status = "0";
                //给客户手机发送短信
                try {
                    status = new SendSms().getVerification(mobile, content, randomCode);
                } catch (Exception e) {
                    map.put("errcode", 258);
                    map.put("message", "验证码发送失败,请稍后重试!");
                    map.put("needauth", 1);
                    return map;
                }
                if (!status.equals("2")) {
                    map.put("errcode", 258);
                    map.put("message", "验证码发送失败,请稍后重试!");
                    map.put("needauth", 1);
                    return map;
                }

                msgCode = new MsgCode();
                msgCode.setMobile(mobile);
                msgCode.setRandomCode(randomCode);
                msgCodeService.save(msgCode);

                map.remove("code");
                map.put("errcode", 0);
                map.put("message", "");
                map.put("needauth", 0);
            } else {
                map.put("errcode", 258);
                map.put("message", "请求次数太频繁");
                map.put("needauth", 1);
            }
        }
        return map;
    }


    /**
     * 注册/修改密码接口
     * @param phone
     * 手机号
     * @param code
     * 验证码
     * @param memberImage
     * 头像地址
     * @param password
     * 密码
     * @param imei
     * 设备号
     * @param gender
     * 性别
     * @return
     */
    @RequestMapping(value = "/register_or_update", method = RequestMethod.POST)
    @ResponseBody
    public ErrorMsg register(String phone, String code, String memberImage, String password, String imei, Member.Gender gender, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Result result = new Result();
        ErrorMsg errorMsg = new ErrorMsg();

        Setting setting = SettingUtils.get();

        if (phone == null || code == null || memberImage == null || password == null || gender == null || imei == null){
            errorMsg.setMessage("必填参数不能为空");
            errorMsg.setCode(Status.INVALID_PARAMS);
            return errorMsg;
        }

        //获取最新短信验证码
        MsgCode msgCode = msgCodeService.findOne(phone);

        if (!msgCode.getRandomCode().equals(code)){
            errorMsg.setMessage("手机号和验证码不匹配");
            errorMsg.setCode(Status.NOT_PHONE_CODE);
            return errorMsg;
        }

        //根据手机号查询一个用户
        Member member = memberService.findByUsername(phone);

        if (member == null){
            //注册
            member = new Member();
            member.setUsername(phone);
            member.setPassword(DigestUtils.md5Hex(password));
            member.setPoint(setting.getRegisterPoint());
            member.setAmount(new BigDecimal(0));
            member.setBalance(new BigDecimal(0));
            member.setIsEnabled(true);
            member.setIsLocked(false);
            member.setLoginFailureCount(0);
            member.setLockedDate(null);
            member.setImei(imei);
            member.setRegisterIp(request.getRemoteAddr());
            member.setLoginIp(request.getRemoteAddr());
            member.setLoginDate(new Date());
            member.setSafeKey(null);
            member = memberService.saveAndReturn(member);
        } else {
            //修改密码
            member.setPassword(DigestUtils.md5Hex(password));
            member = memberService.update(member);
        }
        //登录
        result = login(member, request, response, session);
        return result;
    }


    /**
     * 登录方法
     */
    public Result login(Member member, HttpServletRequest request, HttpServletResponse response, HttpSession session){

        Result result = new Result();

        Map map = new HashMap();
        String token = UUID.randomUUID().toString();
        map.put("token", token);
        //增加到一个cookie
        WebUtils.addCookie(request, response, "token", token);

        Map<String, Object> attributes = new HashMap<String, Object>();
        Enumeration<?> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            attributes.put(key, session.getAttribute(key));
        }

        session.invalidate();
        session = request.getSession();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
        session.setAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), member.getUsername()));

        map.put("JSESSIONID", session.getId());
        map.put("member", member);

        result.setData(map);
        result.setMessage("success");
        result.setCode(Status.SUCCESS);

        return result;
    }

}
