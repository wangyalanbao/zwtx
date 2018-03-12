package com.hxlm.health.web.controller.shop;

import com.hxlm.health.web.ErrorMsg;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.entity.DeviceToken;
import com.hxlm.health.web.service.DeviceTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by dengyang on 15/11/26.
 * 保存iphone手机的device token
 */

@Controller("shopDeviceTokenController")
@RequestMapping("/uptoken")
public class DeviceTokenController extends BaseController {

    @Resource(name = "deviceTokenServiceImpl")
    private DeviceTokenService deviceTokenService;

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public @ResponseBody
    ErrorMsg set(String token, Long id, DeviceToken.DeviceType deviceType) {
        Result result = new Result();
        ErrorMsg errorMsg = new ErrorMsg();
        if (token == null || deviceType == null) {
            errorMsg.setCode(Status.INVALID_PARAMS);
            errorMsg.setMessage("参数错误");
            return result;
        }
        String tmpToken = token.replace(" ", "");
        DeviceToken deviceToken;
        if (id == null) {
            deviceToken = deviceTokenService.findByToken(tmpToken);
        } else {
            deviceToken = deviceTokenService.find(id);
        }

        if (deviceToken == null) {
            deviceToken = new DeviceToken();
            deviceToken.setDeviceToken(tmpToken);
            deviceToken.setDeviceType(deviceType);
            deviceToken.setUserId(id);
            deviceTokenService.save(deviceToken);
        } else {
            deviceToken.setDeviceToken(tmpToken);
            deviceToken.setUserId(id);
            deviceTokenService.update(deviceToken);
        }

        result.setCode(Status.SUCCESS);
        result.setMessage("ok");
        return result;
    }

}
