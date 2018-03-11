package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by guofeng on 2015/12/25.
 * entity--客户反馈
 */
@Entity
@Table(name = "lm_customer_feedback")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "customer_feedback_sequence")
public class CustomerFeedback extends BaseEntity {
    private static final long serialVersionUID = 2330906543092378690L;

    //客户ID
    private Customer customerId;
    //反馈内容
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }
    @JsonProperty
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
