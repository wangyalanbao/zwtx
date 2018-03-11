package com.hxlm.health.web.job;

import com.hxlm.health.web.entity.WxAccessToken;
import com.hxlm.health.web.plugin.WeixinServicePay.WeixinServicePayPlugin;
import com.hxlm.health.web.plugin.weixinPay.api.TokenAPI;
import com.hxlm.health.web.plugin.weixinPay.token.Token;
import com.hxlm.health.web.service.WxAccessTokenService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by dengyang on 15/8/31.
 * job 微信中定时获取acess_token
 */

@Component("tokenManagerJob")
@Lazy(false)
public class TokenManagerJob {

    private static Map<String,String> tokenMap = new LinkedHashMap<String,String>();

    @Resource(name = "wxAccessTokenServiceImpl")
    private WxAccessTokenService wxAccessTokenService;

    //@Scheduled(cron = "${job.get_acess_token.cron}")
    public void updateAccessToken() {
        System.out.println(" -------------------------------------------------------------------- ");
        List<WxAccessToken> list = wxAccessTokenService.findAll();

        // 应该更新token了
        if (list.size() <= 0 || list.isEmpty() || list.get(0).getExpires_time().getTime() - System.currentTimeMillis() <= 600L) {
            Token token = TokenAPI.token(WeixinServicePayPlugin.APPID, WeixinServicePayPlugin.APP_SECRECT);
            wxAccessTokenService.truncateToken();
            WxAccessToken wxAccessToken = new WxAccessToken();
            wxAccessToken.setToken(token.getAccess_token());
            wxAccessToken.setExpires_time(new Date(System.currentTimeMillis() + WxAccessToken.setExpiresTime));
            wxAccessTokenService.save(wxAccessToken);
            tokenMap.put("token", token.getAccess_token());
        } else {
            tokenMap.put("token", list.get(0).getToken());
        }
    }

    public static String getToken(){
        return tokenMap.get("token");
    }
}
