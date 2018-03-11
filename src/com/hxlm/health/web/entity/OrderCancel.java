package com.hxlm.health.web.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * Entity--取消订单申请
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_order_cancel")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_cancel_sequence")
public class OrderCancel extends OrderEntity {

    private static final long serialVersionUID = -8742195760718900784L;
    /** 订单ID */
    private Long orderId;
    /** 客户姓名 */
    private String name;
    /** 客户手机 */
    private String telephone;
    /** 申请理由 */
    private String comment;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Length(max = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(max = 20)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Length(max = 200)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
