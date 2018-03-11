package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity--订单乘客
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_order_passenger")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_passenger_sequence")
public class OrderPassenger extends OrderEntity {


    private static final long serialVersionUID = 1664534242289798043L;

    /**
     * 性别
     */
    public enum Sex{
        /** 男 */
        male,
        /** 女 */
        female
    }
    //证件类型
    public enum IdentityTypes {
        //身份证
        idCard,
        //护照
        passport,
        //港澳通行证
        officerCard,
        //回乡证
        homeCard,
        //其他
        elseCard
    }
    /** 订单 */
    private Order tripOrder;
    /** 订单航段 */
    private OrderAirline orderAirline;
    /** 乘客姓名 */
    private String name;
    /** 姓名拼音 */
    private String pinyin;
    /** 乘客电话 */
    private String telephone;
    /** 证件类型 */
    private IdentityTypes identityType;
    /** 证件号码 */
    private String idCardNo;
    /** 证件有效期 */
    private Date identityExpiryEnd;
    /** 乘客性别 */
    private Sex sex;
    /** 乘客生日 */
    private Date birth;
    /** 乘客国籍 */
    private String nationality;
    /** 乘客职务 */
    private String business;

    //客户id
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( updatable = false)
    public Order getTripOrder() {
        return tripOrder;
    }

    public void setTripOrder(Order order) {
        this.tripOrder = order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( updatable = false)
    public OrderAirline getOrderAirline() {
        return orderAirline;
    }

    public void setOrderAirline(OrderAirline orderAirline) {
        this.orderAirline = orderAirline;
    }

    @JsonProperty
    @Length(max = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    @Length(max = 32)
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @JsonProperty
    @Length(max = 30)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    @JsonProperty
    public IdentityTypes getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IdentityTypes identityType) {
        this.identityType = identityType;
    }

    @JsonProperty
    @Length(max = 50)
    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    @JsonProperty
    public Date getIdentityExpiryEnd() {
        return identityExpiryEnd;
    }

    public void setIdentityExpiryEnd(Date identityExpiryEnd) {
        this.identityExpiryEnd = identityExpiryEnd;
    }

    @JsonProperty
    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @JsonProperty
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @JsonProperty
    @Length(max = 50)
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @JsonProperty
    @Length(max = 50)
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
}
