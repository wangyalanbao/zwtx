package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.WxAccessToken;

/**
 * Created by dengyang on 15/9/1.
 */
public interface WxAccessTokenService extends BaseService<WxAccessToken,Long> {

    void truncateToken();

    String getToken();
}
