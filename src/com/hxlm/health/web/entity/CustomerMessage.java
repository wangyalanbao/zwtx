package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by guofeng on 2015/12/24.
 * entity--发送消息
 */
@Entity
@Table(name = "lm_customer_message")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_customer_message_sequence")
public class CustomerMessage extends OrderEntity {

    private static final long serialVersionUID = 4291715577129940297L;

    private String title;
    private String content;
    private Boolean viewed;
    private Customer customerId ;
    private Long fromId;

    @JsonProperty
    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }
    @JsonProperty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @JsonProperty
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @JsonProperty
    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }
}
