package com.hxlm.health.web.plugin.weixinPay.api;

import com.hxlm.health.web.plugin.weixinPay.bean.SecapiPayRefund;
import com.hxlm.health.web.plugin.weixinPay.bean.SecapiPayRefundResult;
import com.hxlm.health.web.plugin.weixinPay.bean.Unifiedorder;
import com.hxlm.health.web.plugin.weixinPay.bean.UnifiedorderResult;
import com.hxlm.health.web.plugin.weixinPay.util.LocalHttpClient;
import com.hxlm.health.web.plugin.weixinPay.util.XMLConverUtil;
import com.hxlm.health.web.util.MapUtil;
import com.hxlm.health.web.util.SignatureUtil;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;


import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by dengyang on 15/7/30.
 */
public class PayMchAPI extends BaseAPI {

    /**
     * 统一下单
     * 请使用  payUnifiedorder(Unifiedorder unifiedorder,String key),
     * 自动生成sign
     * @param unifiedorder
     * @return
     */
    @Deprecated
    public static UnifiedorderResult payUnifiedorder(Unifiedorder unifiedorder){
        return payUnifiedorder(unifiedorder, null);
    }

    /**
     * 统一下单
     * @param unifiedorder
     * @param key
     * @return
     */
    public static UnifiedorderResult payUnifiedorder(Unifiedorder unifiedorder,String key){
        Map<String,String> map = MapUtil.objectToMap(unifiedorder);
        if(key != null){
            String sign = SignatureUtil.generateSign(map, key);
            unifiedorder.setSign(sign);
        }
        String unifiedorderXML = XMLConverUtil.convertToXML(unifiedorder);

        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(xmlHeader)
                .setUri(MCH_URI + "/pay/unifiedorder")
                .setEntity(new StringEntity(unifiedorderXML, Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.executeXmlResult(httpUriRequest, UnifiedorderResult.class);
    }

    /**
     * 申请退款
     *
     * 注意：
     *	1.交易时间超过半年的订单无法提交退款；
     *	2.微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额。
     * @param secapiPayRefund
     * @param key 商户支付密钥
     * @return
     */
    public static SecapiPayRefundResult secapiPayRefund(SecapiPayRefund secapiPayRefund,String xml){
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setHeader(xmlHeader)
                .setUri(MCH_URI + "/secapi/pay/refund")
                .setEntity(new StringEntity(xml,Charset.forName("utf-8")))
                .build();
        return LocalHttpClient.keyStoreExecuteXmlResult(secapiPayRefund.getMch_id(),httpUriRequest,SecapiPayRefundResult.class);
    }

}
