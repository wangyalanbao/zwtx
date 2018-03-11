/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hxlm.health.web.interceptor.MemberInterceptor;
import com.hxlm.health.web.util.JsonUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Entity - 会员
 */
@Entity
@Table(name = "lm_member")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_member_sequence")
public class Member extends BaseEntity {

    private static final long serialVersionUID = 1533130686714725835L;

    /**
     * 性别
     */
    public enum Gender {

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
        //军官证
        officerCard,
        //护照
        passport,
        //其他
        elseCard
    }

    /**
     * "身份信息"参数名称
     */
    public static final String PRINCIPAL_ATTRIBUTE_NAME = MemberInterceptor.class.getName() + ".PRINCIPAL";

    /**
     * "用户名"Cookie名称
     */
    public static final String USERNAME_COOKIE_NAME = "username";

    /**
     * 会员注册项值属性个数
     */
    public static final int ATTRIBUTE_VALUE_PROPERTY_COUNT = 10;

    /**
     * 会员注册项值属性名称前缀
     */
    public static final String ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "attributeValue";

    /**
     * 最大收藏商品数
     */
    public static final Integer MAX_FAVORITE_COUNT = 10;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * E-mail
     */
    private String email;

    /**
     * 积分
     */
    private Long point;

    /**
     * 消费金额
     */
    private BigDecimal amount;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 是否锁定
     */
    private Boolean isLocked;

    /**
     * 连续登录失败次数
     */
    private Integer loginFailureCount;

    /**
     * 锁定日期
     */
    private Date lockedDate;

    /**
     * 注册IP
     */
    private String registerIp;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录日期
     */
    private Date loginDate;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Gender gender;

    /**
     * 出生日期
     */
    private Date birth;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 会员注册项值0
     */
    private String attributeValue0;

    /**
     * 会员注册项值1
     */
    private String attributeValue1;

    /**
     * 会员注册项值2
     */
    private String attributeValue2;

    /**
     * 会员注册项值3
     */
    private String attributeValue3;

    /**
     * 会员注册项值4
     */
    private String attributeValue4;

    /**
     * 会员注册项值5
     */
    private String attributeValue5;

    /**
     * 会员注册项值6
     */
    private String attributeValue6;

    /**
     * 会员注册项值7
     */
    private String attributeValue7;

    /**
     * 会员注册项值8
     */
    private String attributeValue8;

    /**
     * 会员注册项值9
     */
    private String attributeValue9;

    /**
     * 地区
     */
    private Area area;

    /**
     * 会员等级
     */
    private MemberRank memberRank;

    /**
     * 购物车
     */
    private Cart cart;

    /**
     * 订单
     */
    private Set<Order> orders = new HashSet<Order>();

    /**
     * 收款单
     */
    private Set<Payment> payments = new HashSet<Payment>();



    /**
     * 收货地址
     */
    private Set<Receiver> receivers = new HashSet<Receiver>();

    /**
     * 评论
     */
    private Set<Review> reviews = new HashSet<Review>();

    /**
     * 安全密匙
     */
    private SafeKey safeKey;

    /**
     * 收藏商品
     */
    private Set<Product> favoriteProducts = new HashSet<Product>();

    /**
     * 到货通知
     */
    private Set<ProductNotify> productNotifies = new HashSet<ProductNotify>();

    /**
     * 接收的消息
     */
    private Set<Message> inMessages = new HashSet<Message>();

    /**
     * 发送的消息
     */
    private Set<Message> outMessages = new HashSet<Message>();

    /*会员子账户*/
    private Set<MemberChild> mengberchild = new HashSet<MemberChild>();

    /*头像图片*/
    private String memberImage;





    /*Request Headers请求头里的User-Agent*/
    private String ua;

    /*客户反馈信息*/
    private Set<MemberSuggest> memberSuggest = new HashSet<MemberSuggest>();

    //是否结婚
    private Boolean isMarried;

    //是否绑定
    private Boolean isBind;

    //民族
    private String nation;

    //证件类型
    private IdentityType identityType;

    //证件号码
    private String idNumber;

    //设备token
    private String deviceToken;

    //是否有医保
    private Boolean isMedicare;

    //客户生日
    private String birthday;

    // 获取微信用户信息需要使用的refresh_token
    private String wxRefreshToken;

    //  微信用户的openid
    private String openid;



    //是否更改私人医生标示位
    private  Boolean   isMark;
    //是否更改私人医生标示位
    @JsonProperty
    public Boolean getIsMark() {
        return isMark;
    }

    public void setIsMark(Boolean isMark) {
        this.isMark = isMark;
    }




    @Length(max = 255)
    public String getWxRefreshToken() {
        return wxRefreshToken;
    }

    public void setWxRefreshToken(String wxRefreshToken) {
        this.wxRefreshToken = wxRefreshToken;
    }

    @Length(max = 100)
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    //获取客户生日
    @JsonProperty
    @Length(max = 25)
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    //获取是否有医保
    @JsonProperty
    public Boolean getIsMedicare() {
        return isMedicare;
    }

    public void setIsMedicare(Boolean isMedicare) {
        this.isMedicare = isMedicare;
    }

    //获取设备token
    @Length(max = 200)
    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    //证件号
    @JsonProperty
    @Length(max = 18)
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    //民族
    @JsonProperty
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    //证件类型
    @JsonProperty
    public IdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(IdentityType identityType) {
        this.identityType = identityType;
    }

    //是否结婚
    @JsonProperty
    public Boolean getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(Boolean isMarried) {
        this.isMarried = isMarried;
    }

    //是否结婚
    @JsonProperty
    public Boolean getIsBind() {
        return isBind;
    }

    public void setIsBind(Boolean isBind) {
        this.isBind = isBind;
    }

    @JsonProperty
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<MemberSuggest> getMemberSuggest() {
        return memberSuggest;
    }

    public void setMemberSuggest(Set<MemberSuggest> memberSuggest) {
        this.memberSuggest = memberSuggest;
    }

    /*User-Agent*/
    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }


    //头像图片
    @JsonProperty
    public String getMemberImage() {
        return memberImage;
    }

    public void setMemberImage(String memberImage) {
        this.memberImage = memberImage;
    }


    @JsonProperty
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<MemberChild> getMengberchild() {
        return mengberchild;
    }

    public void setMengberchild(Set<MemberChild> mengberchild) {
        this.mengberchild = mengberchild;
    }


    /**
     * 获取用户名
     *
     * @return 用户名
     */
    @JsonProperty
    @NotEmpty(groups = Save.class)
    @Pattern(regexp = "^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$")
    @Column(nullable = false, updatable = false, unique = true, length = 100)
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return 密码
     */
    @NotEmpty(groups = Save.class)
    @Pattern(regexp = "^[^\\s&\"<>]+$")
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取E-mail
     *
     * @return E-mail
     */
//	@NotEmpty
    @Email
    @Length(max = 200)
    public String getEmail() {
        return email;
    }

    /**
     * 设置E-mail
     *
     * @param email E-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取积分
     *
     * @return 积分
     */
    @Min(0)
    public Long getPoint() {
        return point;
    }

    /**
     * 设置积分
     *
     * @param point 积分
     */
    public void setPoint(Long point) {
        this.point = point;
    }

    /**
     * 获取消费金额
     *
     * @return 消费金额
     */
    @Column(precision = 27, scale = 12)
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置消费金额
     *
     * @param amount 消费金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取余额
     *
     * @return 余额
     */
//	@NotNull(groups = Save.class)
    @Min(0)
    @Digits(integer = 12, fraction = 3)
    @Column(precision = 27, scale = 12)
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置余额
     *
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取是否启用
     *
     * @return 是否启用
     */
//	@NotNull
    @Column(nullable = false)
    public Boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * 设置是否启用
     *
     * @param isEnabled 是否启用
     */
    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * 获取是否锁定
     *
     * @return 是否锁定
     */
    @JsonProperty
    @Column(nullable = false)
    public Boolean getIsLocked() {
        return isLocked;
    }

    /**
     * 设置是否锁定
     *
     * @param isLocked 是否锁定
     */
    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    /**
     * 获取连续登录失败次数
     *
     * @return 连续登录失败次数
     */
    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    /**
     * 设置连续登录失败次数
     *
     * @param loginFailureCount 连续登录失败次数
     */
    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    /**
     * 获取锁定日期
     *
     * @return 锁定日期
     */
    @JsonProperty
    public Date getLockedDate() {
        return lockedDate;
    }

    /**
     * 设置锁定日期
     *
     * @param lockedDate 锁定日期
     */
    public void setLockedDate(Date lockedDate) {
        this.lockedDate = lockedDate;
    }

    /**
     * 获取注册IP
     *
     * @return 注册IP
     */
    @Column(updatable = false)
    public String getRegisterIp() {
        return registerIp;
    }

    /**
     * 设置注册IP
     *
     * @param registerIp 注册IP
     */
    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    /**
     * 获取最后登录IP
     *
     * @return 最后登录IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置最后登录IP
     *
     * @param loginIp 最后登录IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 获取最后登录日期
     *
     * @return 最后登录日期
     */
    @JsonProperty
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * 设置最后登录日期
     *
     * @param loginDate 最后登录日期
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    @JsonProperty
    @Length(max = 200)
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取性别
     *
     * @return 性别
     */
    @JsonProperty
    public Gender getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * 获取出生日期
     *
     * @return 出生日期
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * 设置出生日期
     *
     * @param birth 出生日期
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 获取地址
     *
     * @return 地址
     */
    @JsonProperty
    @Length(max = 200)
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取邮编
     *
     * @return 邮编
     */
    @Length(max = 200)
    public String getZipCode() {
        return zipCode;
    }

    /**
     * 设置邮编
     *
     * @param zipCode 邮编
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 获取电话
     *
     * @return 电话
     */
    @JsonProperty
    @Length(max = 200)
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取手机
     *
     * @return 手机
     */
    @Length(max = 200)
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机
     *
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    /**
     * 获取会员注册项值0
     *
     * @return 会员注册项值0
     */
    @Length(max = 200)
    public String getAttributeValue0() {
        return attributeValue0;
    }

    /**
     * 设置会员注册项值0
     *
     * @param attributeValue0 会员注册项值0
     */
    public void setAttributeValue0(String attributeValue0) {
        this.attributeValue0 = attributeValue0;
    }

    /**
     * 获取会员注册项值1
     *
     * @return 会员注册项值1
     */
    @Length(max = 200)
    public String getAttributeValue1() {
        return attributeValue1;
    }

    /**
     * 设置会员注册项值1
     *
     * @param attributeValue1 会员注册项值1
     */
    public void setAttributeValue1(String attributeValue1) {
        this.attributeValue1 = attributeValue1;
    }

    /**
     * 获取会员注册项值2
     *
     * @return 会员注册项值2
     */
    @Length(max = 200)
    public String getAttributeValue2() {
        return attributeValue2;
    }

    /**
     * 设置会员注册项值2
     *
     * @param attributeValue2 会员注册项值2
     */
    public void setAttributeValue2(String attributeValue2) {
        this.attributeValue2 = attributeValue2;
    }

    /**
     * 获取会员注册项值3
     *
     * @return 会员注册项值3
     */
    @Length(max = 200)
    public String getAttributeValue3() {
        return attributeValue3;
    }

    /**
     * 设置会员注册项值3
     *
     * @param attributeValue3 会员注册项值3
     */
    public void setAttributeValue3(String attributeValue3) {
        this.attributeValue3 = attributeValue3;
    }

    /**
     * 获取会员注册项值4
     *
     * @return 会员注册项值4
     */
    @Length(max = 200)
    public String getAttributeValue4() {
        return attributeValue4;
    }

    /**
     * 设置会员注册项值4
     *
     * @param attributeValue4 会员注册项值4
     */
    public void setAttributeValue4(String attributeValue4) {
        this.attributeValue4 = attributeValue4;
    }

    /**
     * 获取会员注册项值5
     *
     * @return 会员注册项值5
     */
    @Length(max = 200)
    public String getAttributeValue5() {
        return attributeValue5;
    }

    /**
     * 设置会员注册项值5
     *
     * @param attributeValue5 会员注册项值5
     */
    public void setAttributeValue5(String attributeValue5) {
        this.attributeValue5 = attributeValue5;
    }

    /**
     * 获取会员注册项值6
     *
     * @return 会员注册项值6
     */
    @Length(max = 200)
    public String getAttributeValue6() {
        return attributeValue6;
    }

    /**
     * 设置会员注册项值6
     *
     * @param attributeValue6 会员注册项值6
     */
    public void setAttributeValue6(String attributeValue6) {
        this.attributeValue6 = attributeValue6;
    }

    /**
     * 获取会员注册项值7
     *
     * @return 会员注册项值7
     */
    @Length(max = 200)
    public String getAttributeValue7() {
        return attributeValue7;
    }

    /**
     * 设置会员注册项值7
     *
     * @param attributeValue7 会员注册项值7
     */
    public void setAttributeValue7(String attributeValue7) {
        this.attributeValue7 = attributeValue7;
    }

    /**
     * 获取会员注册项值8
     *
     * @return 会员注册项值8
     */
    @Length(max = 200)
    public String getAttributeValue8() {
        return attributeValue8;
    }

    /**
     * 设置会员注册项值8
     *
     * @param attributeValue8 会员注册项值8
     */
    public void setAttributeValue8(String attributeValue8) {
        this.attributeValue8 = attributeValue8;
    }

    /**
     * 获取会员注册项值9
     *
     * @return 会员注册项值9
     */
    @Length(max = 200)
    public String getAttributeValue9() {
        return attributeValue9;
    }

    /**
     * 设置会员注册项值9
     *
     * @param attributeValue9 会员注册项值9
     */
    public void setAttributeValue9(String attributeValue9) {
        this.attributeValue9 = attributeValue9;
    }



    /**
     * 获取地区
     *
     * @return 地区
     */
    @ManyToOne(fetch = FetchType.LAZY)
    public Area getArea() {
        return area;
    }

    /**
     * 设置地区
     *
     * @param area 地区
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * 获取会员等级
     *
     * @return 会员等级
     */
    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(nullable = false)
    public MemberRank getMemberRank() {
        return memberRank;
    }

    /**
     * 设置会员等级
     *
     * @param memberRank 会员等级
     */
    public void setMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    /**
     * 获取购物车
     *
     * @return 购物车
     */
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Cart getCart() {
        return cart;
    }

    /**
     * 设置购物车
     *
     * @param cart 购物车
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * 获取订单
     *
     * @return 订单
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<Order> getOrders() {
        return orders;
    }

    /**
     * 设置订单
     *
     * @param orders 订单
     */
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }



    /**
     * 获取收款单
     *
     * @return 收款单
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<Payment> getPayments() {
        return payments;
    }

    /**
     * 设置收款单
     *
     * @param payments 收款单
     */
    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }


    /**
     * 获取收货地址
     *
     * @return 收货地址
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("isDefault desc, createDate desc")
    public Set<Receiver> getReceivers() {
        return receivers;
    }

    /**
     * 设置收货地址
     *
     * @param receivers 收货地址
     */
    public void setReceivers(Set<Receiver> receivers) {
        this.receivers = receivers;
    }

    /**
     * 获取评论
     *
     * @return 评论
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("createDate desc")
    public Set<Review> getReviews() {
        return reviews;
    }

    /**
     * 设置评论
     *
     * @param reviews 评论
     */
    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }



    /**
     * 获取收藏商品
     *
     * @return 收藏商品
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lm_member_favorite_product")
    @OrderBy("createDate desc")
    public Set<Product> getFavoriteProducts() {
        return favoriteProducts;
    }

    /**
     * 设置收藏商品
     *
     * @param favoriteProducts 收藏商品
     */
    public void setFavoriteProducts(Set<Product> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    /**
     * 获取到货通知
     *
     * @return 到货通知
     */
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<ProductNotify> getProductNotifies() {
        return productNotifies;
    }

    /**
     * 获取安全密匙
     *
     * @return 安全密匙
     */
    @Embedded
    public SafeKey getSafeKey() {
        return safeKey;
    }

    /**
     * 设置安全密匙
     *
     * @param safeKey 安全密匙
     */
    public void setSafeKey(SafeKey safeKey) {
        this.safeKey = safeKey;
    }
    /**
     * 设置到货通知
     *
     * @param productNotifies 到货通知
     */
    public void setProductNotifies(Set<ProductNotify> productNotifies) {
        this.productNotifies = productNotifies;
    }

    /**
     * 获取接收的消息
     *
     * @return 接收的消息
     */
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<Message> getInMessages() {
        return inMessages;
    }

    /**
     * 设置接收的消息
     *
     * @param inMessages 接收的消息
     */
    public void setInMessages(Set<Message> inMessages) {
        this.inMessages = inMessages;
    }

    /**
     * 获取发送的消息
     *
     * @return 发送的消息
     */
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<Message> getOutMessages() {
        return outMessages;
    }

    /**
     * 设置发送的消息
     *
     * @param outMessages 发送的消息
     */
    public void setOutMessages(Set<Message> outMessages) {
        this.outMessages = outMessages;
    }

    /**
     * 获取会员注册项值
     *
     * @param memberAttribute 会员注册项
     * @return 会员注册项值
     */
    @Transient
    public Object getAttributeValue(MemberAttribute memberAttribute) {
        if (memberAttribute != null) {
            if (memberAttribute.getType() == MemberAttribute.Type.name) {
                return getName();
            } else if (memberAttribute.getType() == MemberAttribute.Type.gender) {
                return getGender();
            } else if (memberAttribute.getType() == MemberAttribute.Type.birth) {
                return getBirth();
            } else if (memberAttribute.getType() == MemberAttribute.Type.area) {
                return getArea();
            } else if (memberAttribute.getType() == MemberAttribute.Type.address) {
                return getAddress();
            } else if (memberAttribute.getType() == MemberAttribute.Type.zipCode) {
                return getZipCode();
            } else if (memberAttribute.getType() == MemberAttribute.Type.phone) {
                return getPhone();
            } else if (memberAttribute.getType() == MemberAttribute.Type.mobile) {
                return getMobile();
            } else if (memberAttribute.getType() == MemberAttribute.Type.checkbox) {
                if (memberAttribute.getPropertyIndex() != null) {
                    try {
                        String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
                        String propertyValue = (String) PropertyUtils.getProperty(this, propertyName);
                        if (propertyValue != null) {
                            return JsonUtils.toObject(propertyValue, List.class);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (memberAttribute.getPropertyIndex() != null) {
                    try {
                        String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
                        return (String) PropertyUtils.getProperty(this, propertyName);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 设置会员注册项值
     *
     * @param memberAttribute 会员注册项
     * @param attributeValue  会员注册项值
     */
    @Transient
    public void setAttributeValue(MemberAttribute memberAttribute, Object attributeValue) {
        if (memberAttribute != null) {
            if (attributeValue instanceof String && StringUtils.isEmpty((String) attributeValue)) {
                attributeValue = null;
            }
            if (memberAttribute.getType() == MemberAttribute.Type.name && (attributeValue instanceof String || attributeValue == null)) {
                setName((String) attributeValue);
            } else if (memberAttribute.getType() == MemberAttribute.Type.gender && (attributeValue instanceof Gender || attributeValue == null)) {
                setGender((Gender) attributeValue);
            } else if (memberAttribute.getType() == MemberAttribute.Type.birth && (attributeValue instanceof Date || attributeValue == null)) {
                setBirth((Date) attributeValue);
            } else if (memberAttribute.getType() == MemberAttribute.Type.area && (attributeValue instanceof Area || attributeValue == null)) {
                setArea((Area) attributeValue);
            } else if (memberAttribute.getType() == MemberAttribute.Type.address && (attributeValue instanceof String || attributeValue == null)) {
                setAddress((String) attributeValue);
            } else if (memberAttribute.getType() == MemberAttribute.Type.zipCode && (attributeValue instanceof String || attributeValue == null)) {
                setZipCode((String) attributeValue);
            } else if (memberAttribute.getType() == MemberAttribute.Type.phone && (attributeValue instanceof String || attributeValue == null)) {
                setPhone((String) attributeValue);
            } else if (memberAttribute.getType() == MemberAttribute.Type.mobile && (attributeValue instanceof String || attributeValue == null)) {
                setMobile((String) attributeValue);
            } else if (memberAttribute.getType() == MemberAttribute.Type.checkbox && (attributeValue instanceof List || attributeValue == null)) {
                if (memberAttribute.getPropertyIndex() != null) {
                    if (attributeValue == null || (memberAttribute.getOptions() != null && memberAttribute.getOptions().containsAll((List<?>) attributeValue))) {
                        try {
                            String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
                            PropertyUtils.setProperty(this, propertyName, JsonUtils.toJson(attributeValue));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                if (memberAttribute.getPropertyIndex() != null) {
                    try {
                        String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + memberAttribute.getPropertyIndex();
                        PropertyUtils.setProperty(this, propertyName, attributeValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 移除所有会员注册项值
     */
    @Transient
    public void removeAttributeValue() {
        setName(null);
        setGender(null);
        setBirth(null);
        setArea(null);
        setAddress(null);
        setZipCode(null);
        setPhone(null);
        setMobile(null);
        for (int i = 0; i < ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
            String propertyName = ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
            try {
                PropertyUtils.setProperty(this, propertyName, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }


}