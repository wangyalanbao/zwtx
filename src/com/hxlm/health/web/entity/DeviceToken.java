package com.hxlm.health.web.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by dengyang on 15/11/25.
 * 用来记录ios推送需要用到的手机token
 */

@Entity
@Table(name = "lm_device_token")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_device_token_sequence")
public class DeviceToken extends BaseEntity {

    private static final long serialVersionUID = 8847938942621411360L;

    /* 设备 */
    public enum DeviceType{
        /* ios设备 */
        ios,
        /* android设备 */
        android,
        /* 未知 */
        other
    }

    /** 私行会用户id */
    private Long userId;

    /** token */
    private String deviceToken;

    /** 设备类型 */
    private DeviceType deviceType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}