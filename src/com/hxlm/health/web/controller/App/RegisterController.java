package com.hxlm.health.web.controller.app;

import com.hxlm.health.web.Result;
import com.hxlm.health.web.entity.MsgCode;
import com.hxlm.health.web.service.MemberService;
import com.hxlm.health.web.service.MsgCodeService;
import com.hxlm.health.web.util.SendSms;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/11.
 *
 * app
 */
@Controller
@RequestMapping("/register")
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
    @RequestMapping(value = "/verification")
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
                status = new SendSms().send(mobile, content);
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
                    status = new SendSms().send(mobile, content);
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

}
