package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.*;

/**
 * Created by guofeng on 2015/12/23.
 * entity--客户
 */
@Entity
@Table(name = "lm_customer")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_customer_sequence")
public class Customer extends OrderEntity {

    private static final long serialVersionUID = -1073878368032487960L;


    //性别
    public enum Sex {
        //未知
        unknown,

        //男
        male,
        //女
        female
    }

    //最后一次登录时软件平台类别
    public enum Type {

        //未登录
        nothing,
        //android版
        android,
        //IOS版
        IOS
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

    //客户电话/手机号
    private String telephone;
    //密码干扰串
    private String salt;
    //客户密码
    private String password;
    //客户邮箱
    private String email;
    //客户头像
    private String avatar;
    //客户生日
    private Date birthDate;
    //客户姓名.真实姓名
    private String realName;
    //客户性别
    private Sex sex;
    //证件类型
    private IdentityType identityType;
    //证件号码
    private String identityNo;
    //证件有效期（起始）
    private Date identityExpiryStart;
    //证件有效期（截止）有效日期
    private Date identityExpiryEnd;
    //公司
    private String companys;
    //职务
    private String business;
    //国籍
    private String nationality;
    //使用设备标识
    private String deviceToken;
    //最后一次登录时软件平台类别
    private Type type;
    //状态
    private Boolean status;
    //包机状态
    private Boolean charted;
    //发送消息
    private Set<CustomerMessage> customerMessages = new HashSet<CustomerMessage>();
    //客户反馈
    private Set<CustomerFeedback> customerFeedbacks = new HashSet<CustomerFeedback>();

    private Set<CustomerPassenger> customerPassengers = new HashSet<CustomerPassenger>();
    //订单乘客
    private List<OrderPassenger> orderPassengers = new ArrayList<OrderPassenger>();
    //订单
    private List<Order> orders = new ArrayList<Order>();

    @OneToMany(mappedBy = "customerId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @JsonProperty
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<OrderPassenger> getOrderPassengers() {
        return orderPassengers;
    }

    public void setOrderPassengers(List<OrderPassenger> orderPassengers) {
        this.orderPassengers = orderPassengers;
    }

    @JsonProperty
    @OneToMany(mappedBy = "customerId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<CustomerPassenger> getCustomerPassengers() {
        return customerPassengers;
    }

    public void setCustomerPassengers(Set<CustomerPassenger> customerPassengers) {
        this.customerPassengers = customerPassengers;
    }

    @JsonProperty
    @OneToMany(mappedBy = "customerId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<CustomerFeedback> getCustomerFeedbacks() {
        return customerFeedbacks;
    }

    public void setCustomerFeedbacks(Set<CustomerFeedback> customerFeedbacks) {
        this.customerFeedbacks = customerFeedbacks;
    }

    @JsonProperty
    @OneToMany(mappedBy = "customerId", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<CustomerMessage> getCustomerMessages() {
        return customerMessages;
    }

    public void setCustomerMessages(Set<CustomerMessage> customerMessages) {
        this.customerMessages = customerMessages;
    }

    @JsonProperty
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @JsonProperty
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonProperty
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @JsonProperty
    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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
    public Date getIdentityExpiryStart() {
        return identityExpiryStart;
    }

    public void setIdentityExpiryStart(Date identityExpiryStart) {
        this.identityExpiryStart = identityExpiryStart;
    }

    @JsonProperty
    public Date getIdentityExpiryEnd() {
        return identityExpiryEnd;
    }

    public void setIdentityExpiryEnd(Date identityExpiryEnd) {
        this.identityExpiryEnd = identityExpiryEnd;
    }

    @JsonProperty
    public String getCompanys() {
        return companys;
    }

    public void setCompanys(String companys) {
        this.companys = companys;
    }

    @JsonProperty
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    @JsonProperty
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @JsonProperty
    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @JsonProperty
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @JsonProperty
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @JsonProperty
    public Boolean getCharted() {
        return charted;
    }

    public void setCharted(Boolean charted) {
        this.charted = charted;
    }
}
