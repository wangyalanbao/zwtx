package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.DeviceToken;

import java.util.List;

/**
 * Created by dengyang on 15/11/25.
 */
public interface DeviceTokenDao extends BaseDao<DeviceToken, Long> {

    DeviceToken findByToken(String token);

    void deleteDeviceTokens(List<String> tokens);

}
