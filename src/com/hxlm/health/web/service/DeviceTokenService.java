package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.DeviceToken;
import javapns.data.PayLoad;

import java.util.List;
import java.util.Map;

/**
 * Created by dengyang on 15/11/25.
 */
public interface DeviceTokenService extends BaseService<DeviceToken, Long>  {

    void push2One(String p12File, String p12Pass, String deviceToken, String content, PayLoad payLoad);

    void push2More(String p12File, String p12Pass, List<String> deviceTokens, String content, Map<String, String> customDictionarys);

    DeviceToken findByToken(String token);

    void deleteDeviceTokens(List<String> tokens);
}
