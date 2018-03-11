package com.hxlm.health.web.service.impl;

import com.dbay.apns4j.IApnsService;
import com.hxlm.health.web.dao.DeviceTokenDao;
import com.hxlm.health.web.entity.DeviceToken;
import com.hxlm.health.web.push.ApplePush;
import com.hxlm.health.web.service.DeviceTokenService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by dengyang on 15/11/25.
 */

@Service("deviceTokenServiceImpl")
public class DeviceTokenServiceImpl extends BaseServiceImpl<DeviceToken, Long>  implements DeviceTokenService {

    private static String host = "gateway.sandbox.push.apple.com";
    private static int port = 2195;

    private static Logger logger = Logger.getLogger(DeviceTokenServiceImpl.class);

    @Resource(name="deviceTokenDaoImpl")
    private DeviceTokenDao deviceTokenDao;

    @Resource(name="deviceTokenDaoImpl")
    public void setBaseDao(DeviceTokenDao deviceTokenDao){
        super.setBaseDao(deviceTokenDao);
    }

    /**
     * 向单个iphone手机推送消息.
     * @param deviceToken iphone手机获取的token
     * @param p12File .p12格式的文件路径
     * @param p12Pass .p12格式的文件密码
     * @param customDictionarys CustomDictionary字典组
     * @param content 推送内容
     */
    @Transactional(readOnly = true)
    public void push2One(String p12File, String p12Pass, String deviceToken, String content, PayLoad payLoad) {
        try {
            PushNotificationManager pushManager = PushNotificationManager.getInstance();
            pushManager.addDevice("iphone", deviceToken);

            //链接到APNs
            pushManager.initializeConnection(host, port, p12File, p12Pass, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);

            //开始推送
            Device client = pushManager.getDevice("iphone");
            pushManager.sendNotification(client, payLoad);
            //断开链接
            pushManager.stopConnection();
            pushManager.removeDevice("iphone");
            logger.info("iphone push success!! content:" + content);
        } catch (Exception e) {
            //   System.out.println("iphone 推送消息异常：" + e.getMessage());
            logger.error("Failed to push iphone message：" + e.getMessage(), e);
        }


    }

    /**
     * 向iphone群组推送消息.
     * @param deviceTokens iphone手机获取的token
     * @param p12File .p12格式的文件路径
     * @param p12Pass .p12格式的文件密码
     * @param customDictionarys CustomDictionary字典
     * @param content 推送内容
     */
    @Transactional(readOnly = true)
    public void push2More(String p12File, String p12Pass, List<String> deviceTokens, String content, Map<String, String> customDictionarys) {
        try {
            PushNotificationManager pushManager = PushNotificationManager.getInstance();

            //链接到APNs
            pushManager.initializeConnection(host, port, p12File, p12Pass, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);

            logger.error(" push  host =============== "+host);
            logger.error(" push  port =============== "+port);
            logger.error(" push  p12File =============== "+p12File);
            PayLoad payLoad = new PayLoad();
            payLoad.addAlert(content);//push的内容
            payLoad.addBadge(1);//应用图标上小红圈上的数值
            payLoad.addSound("default");//铃音

            Iterator<String> it = customDictionarys.keySet().iterator();
            while (it.hasNext()) {
                String k = it.next();
                payLoad.addCustomDictionary(k, customDictionarys.get(k));
            }
            logger.error(" push  payLoad =============== "+payLoad.toString());

            //开始循环推送
            for (int i = 0; i < deviceTokens.size(); i++) {
                pushManager.addDevice("iphone" + i, deviceTokens.get(i));
                Device client = pushManager.getDevice("iphone" + i);
                pushManager.sendNotification(client, payLoad);
            }
            //断开链接
            pushManager.stopConnection();
            for (int i = 0; i < deviceTokens.size(); i++) {
                pushManager.removeDevice("iphone" + i);
            }
            logger.error("push ==== ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public DeviceToken findByToken(String token) {
        return deviceTokenDao.findByToken(token);
    }

    @Transactional
    public void deleteDeviceTokens(List<String> tokens) {
        deviceTokenDao.deleteDeviceTokens(tokens);
    }


}
