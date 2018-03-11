package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.WxAccessTokenDao;
import com.hxlm.health.web.entity.WxAccessToken;
import com.hxlm.health.web.job.TokenManagerJob;
import com.hxlm.health.web.service.WxAccessTokenService;
import com.hxlm.health.web.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dengyang on 15/9/1.
 */
@Service("wxAccessTokenServiceImpl")
public class WxAccessTokenServiceImpl extends BaseServiceImpl<WxAccessToken,Long> implements WxAccessTokenService {

    @Resource(name = "wxAccessTokenDaoImpl")
    private WxAccessTokenDao wxAccessTokenDao;

    @Resource(name = "wxAccessTokenDaoImpl")
    public void setBaseDao(WxAccessTokenDao wxAccessTokenDao){super.setBaseDao(wxAccessTokenDao);}

    @Transactional
    public void truncateToken() {
        wxAccessTokenDao.truncateToken();
    }

    public String getToken() {
        String token = TokenManagerJob.getToken();
        if (token == null || token.isEmpty()) {
            (new TokenManagerJob()).updateAccessToken();
            token = TokenManagerJob.getToken();
        }
        return token;
    }

}
