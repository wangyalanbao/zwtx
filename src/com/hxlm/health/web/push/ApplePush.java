package com.hxlm.health.web.push;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dbay.apns4j.IApnsService;
import com.dbay.apns4j.impl.ApnsServiceImpl;
import com.dbay.apns4j.model.ApnsConfig;
import com.dbay.apns4j.model.Feedback;
import com.dbay.apns4j.model.Payload;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dengyang on 15/11/27.
 */

public class ApplePush {

    private static Logger logger = Logger.getLogger(ApplePush.class);

    private static IApnsService apnsService;

    private static IApnsService getApnsService() {
        if (apnsService == null) {
            ApnsConfig config = new ApnsConfig();
            InputStream is = ApplePush.class.getClassLoader().getResourceAsStream("aps_SiHangHui_production.p12");
            config.setKeyStore(is);
            // true 是开发环境
            config.setDevEnv(false);
            config.setPassword("sihanghui");
            config.setPoolSize(3);
            // 假如需要在同个java进程里给不同APP发送通知，那就需要设置为不同的name
            // config.setName("welove1");
            apnsService = ApnsServiceImpl.createInstance(config);
        }
        return apnsService;
    }

    /**
     * 向iphone群组推送消息.
     * @param deviceTokens iphone手机获取的token
     * @param customDictionarys CustomDictionary字典
     * @param content 推送内容
     */
    public List<Feedback> sendPush(List<String> deviceTokens, String content, Map<String, String> customDictionarys){
        IApnsService service = getApnsService();

        Payload payload = new Payload();
        payload.setAlert(content);
        payload.setBadge(1);
        payload.setSound("default");

        Iterator<String> it = customDictionarys.keySet().iterator();
        while (it.hasNext()) {
            String k = it.next();
            payload.addParam(k, customDictionarys.get(k));
        }
        logger.error(" push payLoad string =============== " + payload.toString());
        logger.error(" push payLoad length =============== " + payload.toString().length());

        for (int i = 0; i < deviceTokens.size(); i++) {
            logger.error(" push  token =============== " + deviceTokens.get(i));
            service.sendNotification(deviceTokens.get(i), payload);
        }

        List<Feedback> list = service.getFeedbacks();
        //if (list != null && list.size() > 0) {
        //    return list;
        //}

        return list;
    }

    public static void main(String[] args) {
        IApnsService service = getApnsService();

        // send notification
        String token = "0682fb20b9a70b02c3681f4c95a6d8bfb378a2bd2db0d9d97ae75aec4290901b";

        //String token2 = "6a4ab185bf494516fb93f852830304ae34ee1fb32ad12dbc9c8b95e0ff58d39a";
        List<String> tokens = new ArrayList<String>();
        tokens.add(token);

        Payload payload = new Payload();
        payload.setAlert("本地推送测试 14:50");
        // If this property is absent, the badge is not changed. To remove the badge, set the value of this property to 0
        payload.setBadge(1);
        // set sound null, the music won't be played
        //payload.setSound(null);
        payload.setSound("default");
        payload.addParam("push_type", "specialPrice");
        payload.addParam("url", "http://app.ky3h.com:8001/healthlm/");

        System.out.println(" payload ====== "+payload.toString());
        for (int i = 0; i < tokens.size(); i++) {
            service.sendNotification(tokens.get(i), payload);
        }
        //service.sendNotification(token, payload);

        /***
        // payload, use loc string
        Payload payload2 = new Payload();
        payload2.setBadge(1);
        payload2.setAlertLocKey("GAME_PLAY_REQUEST_FORMAT");
        payload2.setAlertLocArgs(new String[]{"Jenna", "Frank"});
        service.sendNotification(token, payload2);
        **/
        // get feedback
        List<Feedback> list = service.getFeedbacks();
        System.out.println("feedback === "+list.size());
        if (list != null && list.size() > 0) {
            for (Feedback feedback : list) {
                System.out.println(feedback.getDate() + " " + feedback.getToken());
            }
        }


    }

}