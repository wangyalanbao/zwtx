package com.hxlm.health.web.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangwei on 16/7/14.
 *
 */
public class SendSms {

//    private static final String Url="http://10658.cc/webservice/api?method=SendSms";
//    private static final String USERNAME = "vs_jztz";
//    private static final String PASSWORD = "jztz88888888";

    private Logger logger = LoggerFactory.getLogger(SendSms.class);

    private static final String Url="http://smssh1.253.com/msg/send/json";
    private static final String USERNAME = "N1733610";
    private static final String PASSWORD = "SjzZvCP8lo2e16";

    /**
     * 相同内容群发示例
     * 返回内容中包含以下内容就代表发送成功了
     * <code>2</code>
     */
    public String send(String mobile, String content) throws Exception {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(Url);

        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

        //多个手机号码请用英文,号隔开
        NameValuePair[] data = {//提交短信
                new NameValuePair("account", USERNAME),
                new NameValuePair("password", PASSWORD),
                new NameValuePair("mobile", mobile),
//                new NameValuePair("pid", "9"),
//                new NameValuePair("time", ""),
                new NameValuePair("content", content),
        };
        
        method.setRequestBody(data);

        String result = null;
        try {
            client.executeMethod(method);
            result = method.getResponseBodyAsString();

            logger.error(" ============ sendsms result ================ "+result);

            Document doc = DocumentHelper.parseText(result);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            logger.error(" ============ sendsms result code ================ "+code);
            String msg = root.elementText("msg");
            logger.error(" ============ sendsms result msg ================ "+msg);
            String smsid = root.elementText("smsid");
            logger.error(" ============ sendsms result smsid ================ "+smsid);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

//        if (result != null || StringUtils.isNotEmpty(result)){
//            if (result.contains("<code>2</code>")) {
//                return "2";
//            }
//        }
//
//        return "0";
    }

    /**
     * 调用发送接口ring
     * @param phone
     * @return
     */
    public String getVerification(String phone, String content) {

        SmsSendRequest smsSingleRequest = new SmsSendRequest(USERNAME, PASSWORD, content, phone);
        String requestJson = JSON.toJSONString(smsSingleRequest);
        String response = ChuangLanSmsUtil.sendSmsByPost(Url, requestJson);
        logger.error("=================== response =================" + response);
        SmsSendResponse smsSingleResponse = (SmsSendResponse)JSON.parseObject(response, SmsSendResponse.class);
        logger.error("======================= sendms result code ====================" + smsSingleResponse.getCode());
        return smsSingleResponse.getCode();
    }

    public static void main(String[] args) throws Exception {
        new SendSms().getVerification("17611349025", "");
    }
}
