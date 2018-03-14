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
     * 发送验证码
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/smsSend", method = RequestMethod.GET)
    public Object smsSend(String phone) {
        Map map = new HashMap();

        String randomCode;
        MsgCode msgCode = msgCodeService.findOne(phone);
        if (msgCode == null) {
            // 获取6位随机数
            randomCode = String.valueOf((int) (Math.random() * 90000 + 100000));
            String content = "【中网天下】 恭喜您成为雷锋来了一员，整装待发，迈开步伐，新时代的雷锋精神，由我们传承！您的短信验证码是：" + randomCode;
            map.put("time", System.currentTimeMillis());
            map.put("code", randomCode);

            System.out.println(" ====== "+randomCode);
            String status = "0";
            //给客户手机发送短信
            try {
                status = new SendSms().getVerification(phone, randomCode);

                System.out.print("======================= status =======================" + status);
            } catch (Exception e) {
                map.put("errcode", 258);
                map.put("message", "验证码发送失败,请稍后重试!");
                map.put("needauth", 1);
                return map;
            }
            if (!"0".equals(status)) {
                map.put("errcode", 258);
                map.put("message", "验证码发送失败,请稍后重试!");
                map.put("needauth", 1);
                return map;
            }

            msgCode = new MsgCode();
            msgCode.setMobile(phone);
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
                // 获取6位随机数
                randomCode = String.valueOf((int) (Math.random() * 90000 + 100000));
                String content = "【中网天下】 恭喜您成为雷锋来了一员，整装待发，迈开步伐，新时代的雷锋精神，由我们传承！您的短信验证码是：" + randomCode;
                map.put("time", System.currentTimeMillis());
                map.put("code", randomCode);

                System.out.println(" ====== "+randomCode);
                String status = "0";
                //给客户手机发送短信
                try {
                    status = new SendSms().getVerification(phone, content);
                } catch (Exception e) {
                    map.put("errcode", 258);
                    map.put("message", "验证码发送失败,请稍后重试!");
                    map.put("needauth", 1);
                    return map;
                }
                if (!status.equals("0")) {
                    map.put("errcode", 258);
                    map.put("message", "验证码发送失败,请稍后重试!");
                    map.put("needauth", 1);
                    return map;
                }

                msgCode = new MsgCode();
                msgCode.setMobile(phone);
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
     * 生成雷锋号
     * @return
     */
    @RequestMapping(value = "/createLfNumber", method = RequestMethod.GET)
    @ResponseBody
    public synchronized Result createLfNumber() {
        Result result = new Result();

        result.setData(new ArrayList().add(System.currentTimeMillis()));
        result.setCode(Status.SUCCESS);
        result.setMessage("雷锋号生成成功");

        return result;
    }


    /**
     * 注册接口
     * @param phone
     * 手机号
     * @param code
     * 验证
     * @param password
     * 密码
     * @return
     *ring
     * 昵称   性别  头像  雷锋号
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ErrorMsg register(String phone, String code, String password, String lf_number, String nickName, String memberImage, Member.Gender gender, HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Result result = new Result();
        ErrorMsg errorMsg = new ErrorMsg();

        Setting setting = SettingUtils.get();

        if (phone == null || code == null || password == null || lf_number == null || nickName == null || memberImage == null || gender == null){
            errorMsg.setMessage("必填参数不能为空");
            errorMsg.setCode(Status.INVALID_PARAMS);
            return errorMsg;
        }

        if (!memberService.nicknameExists(nickName)){
            errorMsg.setMessage("昵称已存在");
            errorMsg.setCode(Status.EXISTS_EMAIL);
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
            member.setPhone(phone);
            member.setPassword(DigestUtils.md5Hex(password));
            member.setPoint(setting.getRegisterPoint());
            member.setAmount(new BigDecimal(0));
            member.setBalance(new BigDecimal(0));
            member.setIsEnabled(true);
            member.setIsLocked(false);
            member.setLoginFailureCount(0);
            member.setLockedDate(null);
            member.setImei(null);
            member.setRegisterIp(request.getRemoteAddr());
            member.setLoginIp(request.getRemoteAddr());
            member.setLoginDate(new Date());
            member.setSafeKey(null);
            member.setLf_number(lf_number);
            member.setNickName(nickName);
            member.setMemberImage(memberImage);
            member.setGender(gender);
            memberService.saveAndReturn(member);
        }
        result.setCode(Status.SUCCESS);
        result.setData(new ArrayList());
        result.setMessage("注册成功");
        return result;
    }

    /**
     * 更新密码
     * @param phone
     * @param code
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/update_password", method = RequestMethod.POST)
    @ResponseBody
    public ErrorMsg updatePassword(String phone, String code, String newPassword){
        ErrorMsg errorMsg = new ErrorMsg();
        Result result = new Result();

        Member member = memberService.findByUsername(phone);
        if (member == null || code == null || newPassword == null){
            errorMsg.setCode(Status.INVALID_PARAMS);
            errorMsg.setMessage("必填参数为空");
            return errorMsg;
        }
        MsgCode msgCode = msgCodeService.findOne(phone);

        if (!msgCode.getRandomCode().equals(code)){
            errorMsg.setMessage("手机号和验证码不匹配");
            errorMsg.setCode(Status.NOT_PHONE_CODE);
            return errorMsg;
        }

        member.setPassword(newPassword);
        memberService.saveMember(member);

        result.setCode(Status.SUCCESS);
        result.setData(new ArrayList());
        result.setMessage("更新密码成功");
        return result;
    }

}
