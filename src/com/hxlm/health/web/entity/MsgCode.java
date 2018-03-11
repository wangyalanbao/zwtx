package com.hxlm.health.web.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by guofeng on 2017/3/10.
 * 收集短信验证码
 */
@Entity
@Table(name = "lm_msg_code")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_msg_code_sequence")
public class MsgCode extends BaseEntity {

    //手机号
    private String mobile;

    //最新验证码
    private String randomCode;

    /**
     * 手机号
     * @return
     */
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 最新验证码
     * @return
     */
    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }
}
