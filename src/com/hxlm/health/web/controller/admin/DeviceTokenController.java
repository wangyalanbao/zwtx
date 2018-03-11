package com.hxlm.health.web.controller.admin;

import com.dbay.apns4j.model.Feedback;
import com.hxlm.health.web.CommonAttributes;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.DeviceToken;
import com.hxlm.health.web.entity.PushContent;
import com.hxlm.health.web.push.ApplePush;
import com.hxlm.health.web.service.DeviceTokenService;
import com.hxlm.health.web.service.PushContentService;
import javapns.data.PayLoad;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dengyang on 15/11/25.
 *
 * 私行会发ios推送用
 */


@Controller("adminDeviceTokenController")
@RequestMapping("/admin/push")
public class DeviceTokenController extends BaseController  {

    private static Logger logger = Logger.getLogger(DeviceTokenController.class);

    @Resource(name = "deviceTokenServiceImpl")
    private DeviceTokenService deviceTokenService;

    @Resource(name = "pushContentServiceImpl")
    private PushContentService pushContentService;


    /**
     * 增加一个push消息
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(RedirectAttributes redirectAttributes) {

        return "/admin/push/push";
    }

    /**
     * 增加一个push消息
     */
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public String push(String push_type, String content, RedirectAttributes redirectAttributes) throws Exception {

        List<DeviceToken> deviceTokenList = deviceTokenService.findAll();

        List<String> deviceTokens = new ArrayList<String>();
        for (DeviceToken deviceToken : deviceTokenList) {
            deviceTokens.add(deviceToken.getDeviceToken());
        }

        Map map = new HashMap();
        map.put("push_type", push_type);
        map.put("url", "http://app.ky3h.com:8001/healthlm/");

        //deviceTokenService.push2More((new ClassPathResource(CommonAttributes.APS_DEVELOPMENT_PATH).getFile()).toString(), PUSHPASSWORD, deviceTokens, content, map);

        ApplePush applePush = new ApplePush();
        List<Feedback> list = applePush.sendPush(deviceTokens, content, map);

        if (list != null && list.size() > 0) {
            List<String> tokens = new ArrayList<String>();
            for (Feedback feedback : list) {
                logger.error(" push feed token ===== "+feedback.getToken());
                tokens.add(feedback.getToken());
            }
            deviceTokenService.deleteDeviceTokens(tokens);
        }

        // 增加记录
        PushContent pushContent = new PushContent();
        pushContent.setType(push_type);
        pushContent.setContent(content);
        pushContentService.save(pushContent);

        addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
        return "redirect:list.jhtml";
    }

    /**
     * 推送列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Pageable pageable, ModelMap model) {
        model.addAttribute("page",pushContentService.findPage(pageable));
        return "/admin/push/list";
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    Message delete(Long[] ids) {
        pushContentService.delete(ids);
        return SUCCESS_MESSAGE;
    }

}
