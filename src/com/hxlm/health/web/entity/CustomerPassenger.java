package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by guofeng on 2015/12/25.
 * entity--客户的乘客信息
 */
@Entity
@Table(name = "lm_customer_passenger")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_customer_passenger_sequence")
public class CustomerPassenger extends BaseEntity {
    private static final long serialVersionUID = -8874197592184828780L;


    /**
     * 性别
     */
    public enum Sex {
        //未知
        unknown,
        /**
         * 男
         */
        male,

        /**
         * 女
         */
        female
    }

    //证件类型
    public enum IdentityType {
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
    //乘客姓名.真实姓名
    private String name;
    //乘客拼音
    private String pinyin;
    //客户电话/手机号
    private String telephone;
    //证件类型
    private IdentityType identityType;
    //证件号码
    private String identityNo;
    //证件有效期（截止）有效日期
    private Date identityExpiryEnd;
    //国籍
    private String nationality;
    //职务
    private String business;
    //客户性别
    private Sex sex;

    //客户生日
    private Date birthDate;

    //客户id
    private Customer customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }
    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    @JsonProperty
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    @JsonProperty
    public IdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType) {
        this.identityType = identityType;
    }
    @JsonProperty
    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }
    @JsonProperty
    public Date getIdentityExpiryEnd() {
        return identityExpiryEnd;
    }

    public void setIdentityExpiryEnd(Date identityExpiryEnd) {
        this.identityExpiryEnd = identityExpiryEnd;
    }
    @JsonProperty
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    @JsonProperty
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
    @JsonProperty
    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    @JsonProperty
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


}
