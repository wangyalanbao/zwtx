package com.hxlm.health.web.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by dengyang on 15/9/1.
 * 存储从微信获取的access token
 */

@Entity
@Table(name = "lm_wx_acess_token")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_wx_acess_token_sequence")
public class WxAccessToken extends BaseEntity {

    private static final long serialVersionUID = -2546589102334305952L;

    public static final long setExpiresTime = 6600000L;

    private String token;

    private Date expires_time;

    @NotEmpty
    @Length(max = 200)
    @Column(nullable = false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(nullable = false, updatable = false)
    public Date getExpires_time() {
        return expires_time;
    }

    public void setExpires_time(Date expires_time) {
        this.expires_time = expires_time;
    }
}
