package com.hxlm.health.web.plugin.weixinPay.api;

import com.hxlm.health.web.plugin.weixinPay.token.Token;
import com.hxlm.health.web.plugin.weixinPay.util.LocalHttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

/**
 * Created by dengyang on 15/7/30.
 */

public class TokenAPI extends BaseAPI{

    /**
     * 获取access_token
     * @param appid
     * @param secret
     * @return
     */
    public static Token token(String appid,String secret){
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/cgi-bin/token")
                .addParameter("grant_type","client_credential")
                .addParameter("appid", appid)
                .addParameter("secret", secret)
                .build();
        return LocalHttpClient.executeJsonResult(httpUriRequest, Token.class);
    }

}

