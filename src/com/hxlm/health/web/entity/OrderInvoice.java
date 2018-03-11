package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Entity--订单邮寄发票
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_order_invoice")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_invoice_sequence")
public class OrderInvoice extends OrderEntity {

    private static final long serialVersionUID = -8370174228853349911L;
    /** 订单 */
    private Order tripOrder;
    /** 收件人姓名 */
    private String name;
    /** 邮箱 */
    private String email;
    /** 收件人地址 */
    private String address;
    /** 收件人手机号 */
    private String mobile;
    /** 发票抬头 */
    private String invoiceTitle;
    /** 是否为默认地址 */
    private Boolean isDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    public Order getTripOrder() {
        return tripOrder;
    }

    public void setTripOrder(Order order) {
        this.tripOrder = order;
    }

    @JsonProperty
    @Length(max = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    @Length(max = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty
    @Length(max = 200)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty
    @Length(max = 20)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @JsonProperty
    @Length(max = 200)
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    @JsonProperty
    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
