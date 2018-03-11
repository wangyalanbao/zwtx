package com.hxlm.health.web.push;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import com.common.util.StringUtils;
import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;
import javapns.exceptions.DuplicateDeviceException;
import javapns.exceptions.NullDeviceTokenException;
import javapns.exceptions.NullIdException;
import javapns.exceptions.UnknownDeviceException;

import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 * Created by dengyang on 15/11/25.
 */
public class IosPusher {

    private final Logger logger = Logger.getLogger(this.getClass());

    /************************************************
     测试推送服务器地址：gateway.sandbox.push.apple.com /2195
     产品推送服务器地址：gateway.push.apple.com / 2195
     ***************************************************/

    public static final int IOS_MESSAGES_SERVER_PORT = 2195;
    private String host= "gateway.sandbox.push.apple.com";
    private int port = IOS_MESSAGES_SERVER_PORT;
    private String p12File;
    private String p12Password;
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getP12File() {
        return p12File;
    }
    public void setP12File(String p12File) {
        this.p12File = p12File;
    }
    public String getP12Password() {
        return p12Password;
    }
    public void setP12Password(String p12Password) {
        this.p12Password = p12Password;
    }

    /**
     * 判断IOS的基本配置参数是否正确
     * @return 有效参数则返回true,否则返回false
     */
    private boolean validIosConfig() {
        if(StringUtils.isEmpty(this.host)) {
            logger.warn("Invalid ios message service address, can't be null or empty, please check config!!!");
            return false;
        }
        if(this.port != IOS_MESSAGES_SERVER_PORT) {
            logger.warn("Invalid ios message service port, should be:" + IOS_MESSAGES_SERVER_PORT + ", please check config!!!");
            return false;
        }

        if(StringUtils.isEmpty(this.p12File)) {
            logger.warn("Invalid ios p12File name, can't be null or empty, please check config!!!");
            return false;
        }

        if(StringUtils.isEmpty(this.p12Password)) {
            logger.warn("Invalid ios p12File password, can't be null or empty, please check config!!!");
            return false;
        }
        return true;
    }

    /**
     * 向单个手机用户推送消息
     * @param deviceToken
     * @param content
     */
    public void pushOne(String deviceToken, String content) {
        //验证配置参数是否正确
        if(!this.validIosConfig()) {
            logger.warn("Invalid config infos, can't push message to ios center, return!");
            return;
        }
        //验证输入参数是否正确
        if(StringUtils.isEmpty(deviceToken)) {
            logger.warn("Invalid input parameter deviceToken, can't be null or empty!!!");
            return;
        }

        if(StringUtils.isEmpty(content)) {
            logger.warn("Invalid input parameter content, can't be null or empty!!!");
            return;
        }
        //取得ios消息通知管理对象
        PushNotificationManager pushManager = PushNotificationManager.getInstance();

        try {
            pushManager.addDevice(deviceToken, deviceToken);
            //链接到APNs
            pushManager.initializeConnection(this.host, this.port, this.p12File, this.p12Password, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);

            PayLoad payLoad = new PayLoad();
            payLoad.addAlert(content);//push的内容
            payLoad.addBadge(1);//应用图标上小红圈上的数值
            payLoad.addSound("default");//铃音

            //添加字典
            payLoad.addCustomDictionary("url", "www.baidu.com");

            //开始推送
            Device client = pushManager.getDevice(deviceToken);
            pushManager.sendNotification(client, payLoad);


        } catch (DuplicateDeviceException e) {
            logger.error("Failed to add device to ios message center by deviceToken:" + deviceToken + "; errorInfo:" + e.getMessage(), e);
        } catch (NullIdException e) {
            logger.error("Failed to add device to ios message center by deviceToken:" + deviceToken + "; errorInfo:" + e.getMessage(), e);
        } catch (NullDeviceTokenException e) {
            logger.error("Failed to add device to ios message center by deviceToken:" + deviceToken + "; errorInfo:" + e.getMessage(), e);
        } catch (UnrecoverableKeyException e) {
            logger.error("Failed to connect to ios message center 'UnrecoverableKeyException' by host:" + this.host + "; port:" + this.port + "; fileName:" + this.p12File + "; password:" + this.p12Password + ", errorInfo:" + e.getMessage(), e);
        } catch (KeyManagementException e) {
            logger.error("Failed to connect to ios message center 'KeyManagementException' by host:" + this.host + "; port:" + this.port + "; fileName:" + this.p12File + "; password:" + this.p12Password + ", errorInfo:" + e.getMessage(), e);
        } catch (KeyStoreException e) {
            logger.error("Failed to connect to ios message center 'KeyStoreException' by host:" + this.host + "; port:" + this.port + "; fileName:" + this.p12File + "; password:" + this.p12Password + ", errorInfo:" + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Failed to connect to ios message center 'NoSuchAlgorithmException' by host:" + this.host + "; port:" + this.port + "; fileName:" + this.p12File + "; password:" + this.p12Password + ", errorInfo:" + e.getMessage(), e);
        } catch (CertificateException e) {
            logger.error("Failed to connect to ios message center 'CertificateException' by host:" + this.host + "; port:" + this.port + "; fileName:" + this.p12File + "; password:" + this.p12Password + ", errorInfo:" + e.getMessage(), e);
        } catch (FileNotFoundException e) {
            logger.error("Failed to connect to ios message center 'FileNotFoundException' by host:" + this.host + "; port:" + this.port + "; fileName:" + this.p12File + "; password:" + this.p12Password + ", errorInfo:" + e.getMessage(), e);
        } catch (NoSuchProviderException e) {
            logger.error("Failed to connect to ios message center 'NoSuchProviderException' by host:" + this.host + "; port:" + this.port + "; fileName:" + this.p12File + "; password:" + this.p12Password + ", errorInfo:" + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("Failed to connect to ios message center 'IOException' by host:" + this.host + "; port:" + this.port + "; fileName:" + this.p12File + "; password:" + this.p12Password + ", errorInfo:" + e.getMessage(), e);
        } catch (JSONException e) {
            logger.error("Failed to set PayLoad, JSONException", e );
        } catch (UnknownDeviceException e) {
            logger.error("Faied to get client by device UnknownDeviceException", e );
        } catch (Exception e) {
            logger.error("Failed to send notification, errorInfo:" + e.getMessage(), e);
        }

        //断开链接
        try {
            pushManager.stopConnection();
        } catch (IOException e) {
            logger.error("Failed to stopConnection from ios center, error:" + e.getMessage(), e);
        }

        try {
            pushManager.removeDevice(deviceToken);
        } catch (UnknownDeviceException e) {
            logger.error("Failed to remove device from pushManager, error:" + e.getMessage(), e);
        }catch (NullIdException e) {
            logger.error("Failed to remove device from pushManager, error:" + e.getMessage(), e);
        }
    }

}
