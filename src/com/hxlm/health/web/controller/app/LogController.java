package com.hxlm.health.web.controller.app;

import com.hxlm.health.web.ErrorMsg;
import com.hxlm.health.web.Principal;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.service.MemberService;
import com.hxlm.health.web.util.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2018/3/12.
 */
@Controller("logController")
@RequestMapping("/app/login")
public class LogController extends BaseController{

    @Resource(name = "memberServiceImpl")
    private MemberService memberService;


    /**
     * 登录接口
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    @ResponseBody
    public ErrorMsg submit(String phone, String password, HttpServletRequest request, HttpServletResponse response, HttpSession session){

        ErrorMsg errorMsg = new ErrorMsg();

        Member member = memberService.findByUsername(phone);
        if (member == null){
            errorMsg.setCode(Status.INVALID_PARAMS);
            errorMsg.setMessage("该用户名不存在");
            return errorMsg;
        }

        if (!DigestUtils.md5Hex(password).equals(member.getPassword())){
            errorMsg.setMessage("用户名或者密码错误");
            errorMsg.setCode(Status.ERROR_NAME_PASSWORD);
            return errorMsg;
        }
        //调用登录方法
        Result result = login(member, request, response, session);
        return result;
    }

    //注销
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Result execute(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Result result = new Result();

        session.removeAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
        WebUtils.removeCookie(request, response, Member.USERNAME_COOKIE_NAME);

        result.setData(new ArrayList());
        result.setMessage("success");
        result.setCode(Status.SUCCESS);

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

        result.setData(new ArrayList().add(map));
        result.setMessage("success");
        result.setCode(Status.SUCCESS);

        return result;
    }


}
