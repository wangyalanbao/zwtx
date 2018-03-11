package com.hxlm.health.web.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity--订单历史
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_order_history")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_history_sequence")
public class OrderHistory extends OrderEntity {

    private static final long serialVersionUID = -6108170440131210060L;
    /** 订单ID */
    private Long orderId;
    /** 订单状态 */
    private Integer orderStatus;
    /** 备注 */
    private String comment;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer order_status) {
        this.orderStatus = order_status;
    }

    @Length(max = 200)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
