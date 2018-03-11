package com.hxlm.health.web.plugin.weixinPayHcyPhone;

import com.hxlm.health.web.Setting;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.Payment;
import com.hxlm.health.web.entity.PluginConfig;
import com.hxlm.health.web.plugin.PaymentPlugin;
import com.hxlm.health.web.plugin.weixinPay.bean.MchPayNotify;
import com.hxlm.health.web.plugin.weixinPay.util.XMLConverUtil;
import com.hxlm.health.web.service.PaymentService;
import com.hxlm.health.web.util.MapUtil;
import com.hxlm.health.web.util.SettingUtils;
import com.hxlm.health.web.util.SignatureUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengyang on 15/10/19.
 * app内支付使用微信支付
 * 安卓和畅依手机版版客户端使用
 */

@Component("weixinPayHcyPhonePlugin")
public class WeixinPayHcyPhonePlugin extends PaymentPlugin {

    //服务号的应用号
    public static final String APPID = "wx2101d2fcba50b476";
    //服务号的应用密码
    public final static String APP_SECRECT = "18fc112061353466c4d1be4b1d6b53f5";
    public static final String MCH_ID = "1274470401";
    public static final String API_KEY = "KY3HYANHUANGDONGFANGCCCCCCCCCCCC";


    @Resource(name = "paymentServiceImpl")
    private PaymentService paymentService;

    @Override
    public String getName() {
        return "微信支付(和畅依手机版)";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return SettingUtils.get().getSiteName();
    }

    @Override
    public String getSiteUrl() {
        return SettingUtils.get().getSiteUrl();
    }

    @Override
    public String getInstallUrl() {
        return "weixin_pay_hcy_phone/install.jhtml";
    }

    @Override
    public String getUninstallUrl() {
        return "weixin_pay_hcy_phone/uninstall.jhtml";
    }

    @Override
    public String getSettingUrl() {
        return "weixin_pay_hcy_phone/setting.jhtml";
    }

    @Override
    public String getRequestUrl() {
        return "https://api.mch.weixin.qq.com/pay/unifiedorder";
    }

    @Override
    public PaymentPlugin.RequestMethod getRequestMethod() {
        return PaymentPlugin.RequestMethod.post;
    }

    @Override
    public String getRequestCharset() {
        return "UTF-8";
    }

    @Override
    public Map<String, Object> getParameterMap(String sn, String description, HttpServletRequest request) {
        Setting setting = SettingUtils.get();
        PluginConfig pluginConfig = getPluginConfig();
        Payment payment = getPayment(sn);
        Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("service", "create_direct_pay_by_user");
        parameterMap.put("partner", pluginConfig.getAttribute("partner"));
        parameterMap.put("_input_charset", "utf-8");
        parameterMap.put("sign_type", "MD5");
        parameterMap.put("return_url", getNotifyUrl(sn, PaymentPlugin.NotifyMethod.sync));
        parameterMap.put("notify_url", getNotifyUrl(sn, PaymentPlugin.NotifyMethod.async));
        parameterMap.put("out_trade_no", sn);
        parameterMap.put("subject", StringUtils.abbreviate(description.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", ""), 60));
        parameterMap.put("body", StringUtils.abbreviate(description.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", ""), 600));
        parameterMap.put("payment_type", "1");
        parameterMap.put("seller_id", pluginConfig.getAttribute("partner"));
        parameterMap.put("total_fee", payment.getAmount().setScale(2).toString());
        parameterMap.put("show_url", setting.getSiteUrl());
        parameterMap.put("paymethod", "directPay");
        parameterMap.put("exter_invoke_ip", request.getLocalAddr());
        parameterMap.put("extra_common_param", "healthlm");
        parameterMap.put("sign", generateSign(parameterMap));

        return new HashMap<String, Object>(parameterMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean verifyNotify(String sn, PaymentPlugin.NotifyMethod notifyMethod, HttpServletRequest request) {
        PluginConfig pluginConfig = getPluginConfig();
        Payment payment = getPayment(sn);
        if (payment.getStatus() == Payment.Status.success && payment.getOrder().getOrderStatus() == Order.OrderStatus.confirmed
                && payment.getOrder().getPaymentStatus() == Order.PaymentStatus.paid) {
            return true;
        }
        boolean flag = false;
        try {
            MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, request.getInputStream());
            Map<String, String> map = MapUtil.objectToMap(payNotify);
            paymentService.savePayTradeNo(map.get("transaction_id"), sn);
            System.out.println(" ------------------------------- weixin req para map ---------------------------------- "+map);
            System.out.println(" ------------------------------- weixin pay amount ---------------------------------- "+payment.getAmount().multiply(new BigDecimal("100")).intValue());
            System.out.println(" ------------------------------- weixin pay appid ---------------------------------- "+pluginConfig.getAttribute("appid"));
            // 先查看支付结果
            if (map.get("result_code").equals("SUCCESS") && map.get("return_code").equals("SUCCESS")) {
                //签名验证，付款账号，收款单号，付款金额
                if(SignatureUtil.validateAppSignature(payNotify, pluginConfig.getAttribute("apikey"))
                        && map.get("out_trade_no").equals(sn) && map.get("appid").equals(pluginConfig.getAttribute("appid"))
                        && Integer.valueOf(map.get("total_fee")) == payment.getAmount().multiply(new BigDecimal("100")).intValue()){
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(" ------------------------------- weixin flag ---------------------------------- "+flag);
        return flag;
    }

    @Override
    public String getNotifyMessage(String sn, PaymentPlugin.NotifyMethod notifyMethod, HttpServletRequest request) {
        if (notifyMethod == PaymentPlugin.NotifyMethod.async) {
            return "<xml>" +
                    "<return_code><![CDATA[SUCCESS]]></return_code>" +
                    "<return_msg><![CDATA[OK]]></return_msg>" +
                    "</xml>";
        }
        return "<xml>" +
                "<return_code><![CDATA[FAIL]]></return_code>" +
                "<return_msg><![CDATA[ERROR]]></return_msg>" +
                "</xml>";
    }

    @Override
    public Integer getTimeout() {
        return 21600;
    }

    /**
     * 生成签名
     *
     * @param parameterMap
     *            参数
     * @return 签名
     */
    private String generateSign(Map<String, String> parameterMap) {
        return SignatureUtil.generateSign(parameterMap, API_KEY);
    }

}
