package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.WxAccessToken;

/**
 * Created by dengyang on 15/9/1.
 */
public interface WxAccessTokenDao extends BaseDao<WxAccessToken, Long> {

    void truncateToken();
}
