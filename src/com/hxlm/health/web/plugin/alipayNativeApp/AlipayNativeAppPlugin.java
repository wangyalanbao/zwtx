package com.hxlm.health.web.plugin.alipayNativeApp;

import com.hxlm.health.web.Setting;
import com.hxlm.health.web.entity.Payment;
import com.hxlm.health.web.entity.PluginConfig;
import com.hxlm.health.web.plugin.PaymentPlugin;
import com.hxlm.health.web.service.PaymentService;
import com.hxlm.health.web.util.SettingUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dengyang on 15/8/12.
 *
 * 支付宝应用内支付
 */
@Component("alipayNativeAppPlugin")
public class AlipayNativeAppPlugin extends PaymentPlugin {

    @Resource(name = "paymentServiceImpl")
    private PaymentService paymentService;

    private Logger logger = LoggerFactory.getLogger(AlipayNativeAppPlugin.class);

    public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    @Override
    public String getName() {
        return "支付宝(应用内支付)";
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
        return "alipay_native_app/install.jhtml";
    }

    @Override
    public String getUninstallUrl() {
        return "alipay_native_app/uninstall.jhtml";
    }

    @Override
    public String getSettingUrl() {
        return "alipay_native_app/setting.jhtml";
    }

    @Override
    public String getRequestUrl() {
        return "https://mapi.alipay.com/gateway.do";
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.get;
    }

    @Override
    public String getRequestCharset() {
        return "UTF-8";
    }

    @Override
    public Map<String, Object> getParameterMap(String sn, String description, HttpServletRequest request) {
        return new TreeMap<String, Object>(request.getParameterMap());
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean verifyNotify(String sn, NotifyMethod notifyMethod, HttpServletRequest request) {
        PluginConfig pluginConfig = getPluginConfig();
        Payment payment = getPayment(sn);

        String content = joinKeyValue(new TreeMap<String, Object>(request.getParameterMap()), null, null, "&", true, "sign_type", "sign");

        logger.error(" --------------------------------------- alipay partner----------------------------------------- "+pluginConfig.getAttribute("partner").equals(request.getParameter("seller_id")));
        logger.error(" --------------------------------------- alipay vdata----------------------------------------- "+("TRADE_SUCCESS".equals(request.getParameter("trade_status")) || "TRADE_FINISHED".equals(request.getParameter("trade_status"))));
        logger.error(" --------------------------------------- alipay payment.getAmount()----------------------------------------- "+payment.getAmount());
        logger.error(" --------------------------------------- alipay payment.getAmount()----------------------------------------- "+request.getParameter("total_fee"));

        paymentService.saveAliPayer(request.getParameter("buyer_email"), request.getParameter("buyer_id"),
                request.getParameter("trade_no"), sn);

        if (RSASignature.doCheck(content, request.getParameter("sign"), ali_public_key)
                && pluginConfig.getAttribute("partner").equals(request.getParameter("seller_id")) && ("TRADE_SUCCESS".equals(request.getParameter("trade_status")) || "TRADE_FINISHED".equals(request.getParameter("trade_status")))
                && payment.getAmount().compareTo(new BigDecimal(request.getParameter("total_fee"))) == 0) {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("service", "notify_verify");
            parameterMap.put("partner", pluginConfig.getAttribute("partner"));
            parameterMap.put("notify_id", request.getParameter("notify_id"));

            String vdata = post("https://mapi.alipay.com/gateway.do", parameterMap);
            logger.error(" --------------------------------------- alipay vdata----------------------------------------- "+vdata);
            if ("true".equals(vdata)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNotifyMessage(String sn, NotifyMethod notifyMethod, HttpServletRequest request) {
        if (notifyMethod == NotifyMethod.async) {
            return "success";
        }
        return null;
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
    private String generateSign(Map<String, ?> parameterMap) {
        PluginConfig pluginConfig = getPluginConfig();
        return DigestUtils.md5Hex(joinKeyValue(new TreeMap<String, Object>(parameterMap), null, pluginConfig.getAttribute("key"), "&", true, "sign_type", "sign"));
    }

}
