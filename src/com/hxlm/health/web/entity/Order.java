/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hxlm.health.web.util.SettingUtils;
import com.hxlm.health.web.Setting;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 订单
 *
 */
@Entity
@Table(name = "lm_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_sequence")
public class Order extends OrderEntity {

	private static final long serialVersionUID = 8370942500343156156L;

	/** 订单名称分隔符 */
	private static final String NAME_SEPARATOR = " ";

	/**
	 * 订单状态
	 */
	public enum OrderStatus {

		/** 未确认 */
		unconfirmed,

		/** 已确认 */
		confirmed,

		/** 已完成 */
		completed,

		/** 已取消 */
		cancelled
	}

	/**
	 * 支付状态
	 */
	public enum PaymentStatus {

		/** 未支付 */
		unpaid,

		/** 部分支付 */
		partialPayment,

		/** 已支付 */
		paid,

		/** 部分退款 */
		partialRefunds,

		/** 已退款 */
		refunded
	}

	/**
	 * 配送状态
	 */
	public enum ShippingStatus {

		/** 未发货 */
		unshipped,

		/** 部分发货 */
		partialShipment,

		/** 已发货 */
		shipped,

		/** 部分退货 */
		partialReturns,

		/** 已退货 */
		returned
	}

	/** 订单编号 */
	private String sn;

	/** 飞机 */
	private Airplane airplane;

	/** 订单状态 */
	private OrderStatus orderStatus;

	/** 支付状态 */
	private PaymentStatus paymentStatus;

	/** 客户评价 */
	private String rank;

	/** 是否为特价航段 1为特价；0=任务 */
	private Boolean isSpecial;

	/** 特价航线id号 当为特价时此字段不为0 */
	private Long specialId;

	/** 第一航段起飞时间 */
	private Date firstTakeoffTime;

	/** 最后航段起飞时间 */
	private Date lastTakeoffTime;

	/** 操作员 */
	private Admin operator;

	/** 会员 */
	private Member member;

	/** 会员 */
	private Customer customerId;

	/** 订单航段  */
	private List<OrderAirline> orderAirlines = new ArrayList<OrderAirline>();

	/** 订单航班 */
	private List<OrderFlight> orderFlights = new ArrayList<OrderFlight>();

	/** 订单行李和配餐要求 */
	private List<OrderCatering> orderCaterings = new ArrayList<OrderCatering>();

	/** 订单乘客 */
	private List<OrderPassenger> orderPassengers = new ArrayList<OrderPassenger>();

	/** 订单接送人 */
	private List<OrderPickup> orderPickups = new ArrayList<OrderPickup>();

	/** 订单邮寄发票 */
	private List<OrderInvoice> orderInvoices = new ArrayList<OrderInvoice>();

	/** 配送状态 */
	private ShippingStatus shippingStatus;

	/** 订单分类  */
	private ProductCategory category;

	/** 支付手续费 */
	private BigDecimal fee;

	/** 运费 */
	private BigDecimal freight;

	/** 促销折扣 */
	private BigDecimal promotionDiscount;

	/** 优惠券折扣 */
	private BigDecimal couponDiscount;

	/** 调整金额 */
	private BigDecimal offsetAmount;

	/** 订单总金额(订单参考总价) */
	private BigDecimal totalAmount;

	/** 已付金额 */
	private BigDecimal amountPaid;

	/** 赠送积分 */
	private Long point;

	/** 收件人 */
	private String consignee;

	/** 地区名称 */
	private String areaName;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String telephone;

	/** 是否开据发票 */
	private Boolean isInvoice;

	/** 发票抬头 */
	private String invoiceTitle;

	/** 税金 */
	private BigDecimal tax;

	/** 备注 */
	private String memo;

	/** 促销 */
	private String promotion;

	/** 到期时间 */
	private Date expire;

	/** 锁定到期时间 */
	private Date lockExpire;

	/** 是否已分配库存 */
	private Boolean isAllocatedStock;

	/** 支付方式名称 */
	private String paymentMethodName;

	/** 配送方式名称 */
	private String shippingMethodName;

	/** 地区 */
	private Area area;

	/** 支付方式 */
	private PaymentMethod paymentMethod;

	/** 发票信息 */
	private List<Shipping> shippings = new ArrayList<Shipping>();

	/** 订单项 */
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();

	/** 订单日志 */
	private Set<OrderLog> orderLogs = new HashSet<OrderLog>();

	/** 收款单 */
	private Set<Payment> payments = new HashSet<Payment>();

	/** 退款单 */
	private Set<Refunds> refunds = new HashSet<Refunds>();

	/** 退货单 */
	private Set<Returns> returns = new HashSet<Returns>();

	/**
	 * 获取订单编号
	 * 
	 * @return 订单编号
	 */
	@JsonProperty
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	/**
	 * 获取飞机
	 *
	 * @return 飞机
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	/**
	 * 获取客户评价
	 *
	 * @return 客户评价
	 */
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	/**
	 * 获取是否为特价航段
	 *
	 * @return 是否为特价航段
	 */
	public Boolean getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	/**
	 * 获取特价航线id号
	 *
	 * @return 特价航线id号
	 */
	public Long getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Long specialId) {
		this.specialId = specialId;
	}

	/**
	 * 获取第一航段起飞时间
	 *
	 * @return 第一航段起飞时间
	 */
	public Date getFirstTakeoffTime() {
		return firstTakeoffTime;
	}

	public void setFirstTakeoffTime(Date firstTakeoffTime) {
		this.firstTakeoffTime = firstTakeoffTime;
	}

	/**
	 * 获取最后航段起飞时间
	 *
	 * @return 最后航段起飞时间
	 */
	public Date getLastTakeoffTime() {
		return lastTakeoffTime;
	}

	public void setLastTakeoffTime(Date lastTakeoffTime) {
		this.lastTakeoffTime = lastTakeoffTime;
	}

	/**
	 * 设置订单编号
	 * 
	 * @param sn
	 *            订单编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取订单状态
	 * 
	 * @return 订单状态
	 */
	@JsonProperty
	@Column(nullable = false)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 设置订单状态
	 * 
	 * @param orderStatus
	 *            订单状态
	 */
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 获取支付状态
	 * 
	 * @return 支付状态
	 */
	@JsonProperty
	@Column(nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * 设置支付状态
	 * 
	 * @param paymentStatus
	 *            支付状态
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * 获取配送状态
	 *
	 * @return 配送状态
	 */
	@JsonProperty
	@Column(nullable = false)
	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	/**
	 * 设置配送状态
	 *
	 * @param shippingStatus
	 *            配送状态
	 */
	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	/**
	 *  获取订单分类
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public ProductCategory getCategory() {
		return category;
	}

	/**
	 * 设置订单分类
	 * @param category
	 */
	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	/**
	 * 获取支付手续费
	 * 
	 * @return 支付手续费
	 */
	@JsonProperty
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * 设置支付手续费
	 * 
	 * @param fee
	 *            支付手续费
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * 获取运费
	 * 
	 * @return 运费
	 */
	@JsonProperty
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * 设置运费
	 * 
	 * @param freight
	 *            运费
	 */
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	/**
	 * 获取促销折扣
	 * 
	 * @return 促销折扣
	 */
	@Column(updatable = false, precision = 21, scale = 6)
	public BigDecimal getPromotionDiscount() {
		return promotionDiscount;
	}

	/**
	 * 设置促销折扣
	 * 
	 * @param promotionDiscount
	 *            促销折扣
	 */
	public void setPromotionDiscount(BigDecimal promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	/**
	 * 获取优惠券折扣
	 * 
	 * @return 优惠券折扣
	 */
	@Column(updatable = false, precision = 21, scale = 6)
	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	/**
	 * 设置优惠券折扣
	 * 
	 * @param couponDiscount
	 *            优惠券折扣
	 */
	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	/**
	 * 获取调整金额
	 * 
	 * @return 调整金额
	 */
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6)
	public BigDecimal getOffsetAmount() {
		return offsetAmount;
	}

	/**
	 * 设置调整金额
	 * 
	 * @param offsetAmount
	 *            调整金额
	 */
	public void setOffsetAmount(BigDecimal offsetAmount) {
		this.offsetAmount = offsetAmount;
	}

	@JsonProperty
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * 获取已付金额
	 * 
	 * @return 已付金额
	 */
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	/**
	 * 设置已付金额
	 * 
	 * @param amountPaid
	 *            已付金额
	 */
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@JsonProperty
	@Min(0)
	public Long getPoint() {
		return point;
	}

	/**
	 * 设置赠送积分
	 * 
	 * @param point
	 *            赠送积分
	 */
	public void setPoint(Long point) {
		this.point = point;
	}

	/**
	 * 获取收货人
	 * 
	 * @return 收货人
	 */
	@Length(max = 200)
	public String getConsignee() {
		return consignee;
	}

	/**
	 * 设置收货人
	 * 
	 * @param consignee
	 *            收货人
	 */
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	/**
	 * 获取地区名称
	 * 
	 * @return 地区名称
	 */
	@JsonProperty
	public String getAreaName() {
		return areaName;
	}

	/**
	 * 设置地区名称
	 * 
	 * @param areaName
	 *            地区名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
	 * @param address
	 *            地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取邮编
	 * 
	 * @return 邮编
	 */
	@JsonProperty
	@Length(max = 200)
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 * 
	 * @param zipCode
	 *            邮编
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
	@Column(nullable = false)
	public String getTelephone() {
		return telephone;
	}

	/**
	 * 设置电话
	 * 
	 * @param phone
	 *            电话
	 */
	public void setTelephone(String phone) {
		this.telephone = phone;
	}

	/**
	 * 获取是否开据发票
	 * 
	 * @return 是否开据发票
	 */
	public Boolean getIsInvoice() {
		return isInvoice;
	}

	/**
	 * 设置是否开据发票
	 * 
	 * @param isInvoice
	 *            是否开据发票
	 */
	public void setIsInvoice(Boolean isInvoice) {
		this.isInvoice = isInvoice;
	}

	/**
	 * 获取发票抬头
	 * 
	 * @return 发票抬头
	 */
	@Length(max = 200)
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	/**
	 * 设置发票抬头
	 * 
	 * @param invoiceTitle
	 *            发票抬头
	 */
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	/**
	 * 获取税金
	 * 
	 * @return 税金
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(precision = 21, scale = 6)
	public BigDecimal getTax() {
		return tax;
	}

	/**
	 * 设置税金
	 * 
	 * @param tax
	 *            税金
	 */
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	/**
	 * 获取附言
	 * 
	 * @return 附言
	 */
	@JsonProperty
	@Length(max = 200)
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置附言
	 * 
	 * @param memo
	 *            附言
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取促销
	 * 
	 * @return 促销
	 */
	@Column(updatable = false)
	public String getPromotion() {
		return promotion;
	}

	/**
	 * 设置促销
	 * 
	 * @param promotion
	 *            促销
	 */
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	/**
	 * 获取到期时间
	 * 
	 * @return 到期时间
	 */
	public Date getExpire() {
		return expire;
	}

	/**
	 * 设置到期时间
	 * 
	 * @param expire
	 *            到期时间
	 */
	public void setExpire(Date expire) {
		this.expire = expire;
	}

	/**
	 * 获取锁定到期时间
	 * 
	 * @return 锁定到期时间
	 */
	public Date getLockExpire() {
		return lockExpire;
	}

	/**
	 * 设置锁定到期时间
	 * 
	 * @param lockExpire
	 *            锁定到期时间
	 */
	public void setLockExpire(Date lockExpire) {
		this.lockExpire = lockExpire;
	}

	/**
	 * 获取是否已分配库存
	 * 
	 * @return 是否已分配库存
	 */
	public Boolean getIsAllocatedStock() {
		return isAllocatedStock;
	}

	/**
	 * 设置是否已分配库存
	 * 
	 * @param isAllocatedStock
	 *            是否已分配库存
	 */
	public void setIsAllocatedStock(Boolean isAllocatedStock) {
		this.isAllocatedStock = isAllocatedStock;
	}

	/**
	 * 获取支付方式名称
	 * 
	 * @return 支付方式名称
	 */
	@JsonProperty
	@Column(nullable = false)
	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	/**
	 * 设置支付方式名称
	 * 
	 * @param paymentMethodName
	 *            支付方式名称
	 */
	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	/**
	 * 获取配送方式名称
	 * 
	 * @return 配送方式名称
	 */
	@JsonProperty
	public String getShippingMethodName() {
		return shippingMethodName;
	}

	/**
	 * 设置配送方式名称
	 * 
	 * @param shippingMethodName
	 *            配送方式名称
	 */
	public void setShippingMethodName(String shippingMethodName) {
		this.shippingMethodName = shippingMethodName;
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
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 获取支付方式
	 * 
	 * @return 支付方式
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * 设置支付方式
	 * 
	 * @param paymentMethod
	 *            支付方式
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}



	/**
	 * 获取操作员
	 * 
	 * @return 操作员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Admin getOperator() {
		return operator;
	}

	/**
	 * 设置操作员
	 * 
	 * @param operator
	 *            操作员
	 */
	public void setOperator(Admin operator) {
		this.operator = operator;
	}

	/**
	 * 获取会员
	 *
	 * @return 会员
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 *
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 客户
	 * @return
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public List<Shipping> getShippings() {
		return shippings;
	}

	public void setShippings(List<Shipping> shipping) {
		this.shippings = shipping;
	}

	/**
	 * 获取订单项
	 * 
	 * @return 订单项
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("isGift asc")
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * 设置订单项
	 * 
	 * @param orderItems
	 *            订单项
	 */
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * 获取订单航段
	 *
	 * @return 订单航段
	 */
	@JsonProperty
	@OneToMany(mappedBy = "tripOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<OrderAirline> getOrderAirlines() {
		return orderAirlines;
	}

	public void setOrderAirlines(List<OrderAirline> orderAirlines) {
		this.orderAirlines = orderAirlines;
	}

	/**
	 * 获取订单航班
	 *
	 * @return 单航班
	 */
	@JsonProperty
	@OneToMany(mappedBy = "tripOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<OrderFlight> getOrderFlights() {
		return orderFlights;
	}

	public void setOrderFlights(List<OrderFlight> orderFlights) {
		this.orderFlights = orderFlights;
	}

	/**
	 * 获取订单行李和配餐要求
	 *
	 * @return 订单行李和配餐要求
	 */
	@JsonProperty
	@OneToMany(mappedBy = "tripOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<OrderCatering> getOrderCaterings() {
		return orderCaterings;
	}

	public void setOrderCaterings(List<OrderCatering> orderCaterings) {
		this.orderCaterings = orderCaterings;
	}

	/**
	 * 获取订单乘客
	 *
	 * @return 订单乘客
	 */
	@JsonProperty
	@OneToMany(mappedBy = "tripOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<OrderPassenger> getOrderPassengers() {
		return orderPassengers;
	}

	public void setOrderPassengers(List<OrderPassenger> orderPassengers) {
		this.orderPassengers = orderPassengers;
	}

	/**
	 * 获取订单乘客
	 *
	 * @return 订单乘客
	 */
	@JsonProperty
	@OneToMany(mappedBy = "tripOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<OrderPickup> getOrderPickups() {
		return orderPickups;
	}

	public void setOrderPickups(List<OrderPickup> orderPickups) {
		this.orderPickups = orderPickups;
	}

	/**
	 * 获取订单邮寄发票
	 *
	 * @return 订单邮寄发票
	 */
	@JsonProperty
	@OneToMany(mappedBy = "tripOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<OrderInvoice> getOrderInvoices() {
		return orderInvoices;
	}

	public void setOrderInvoices(List<OrderInvoice> orderInvoices) {
		this.orderInvoices = orderInvoices;
	}

	/**
	 * 获取订单日志
	 * 
	 * @return 订单日志
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<OrderLog> getOrderLogs() {
		return orderLogs;
	}

	/**
	 * 设置订单日志
	 * 
	 * @param orderLogs
	 *            订单日志
	 */
	public void setOrderLogs(Set<OrderLog> orderLogs) {
		this.orderLogs = orderLogs;
	}



	/**
	 * 获取收款单
	 * 
	 * @return 收款单
	 */
	@JsonProperty
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<Payment> getPayments() {
		return payments;
	}

	/**
	 * 设置收款单
	 * 
	 * @param payments
	 *            收款单
	 */
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * 获取退款单
	 * 
	 * @return 退款单
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<Refunds> getRefunds() {
		return refunds;
	}

	/**
	 * 设置退款单
	 * 
	 * @param refunds
	 *            退款单
	 */
	public void setRefunds(Set<Refunds> refunds) {
		this.refunds = refunds;
	}



	/**
	 * 获取退货单
	 * 
	 * @return 退货单
	 */
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<Returns> getReturns() {
		return returns;
	}

	/**
	 * 设置退货单
	 * 
	 * @param returns
	 *            退货单
	 */
	public void setReturns(Set<Returns> returns) {
		this.returns = returns;
	}

	/**
	 * 获取订单名称
	 * 
	 * @return 订单名称
	 */
	@JsonProperty
	@Transient
	public String getName() {
		StringBuffer name = new StringBuffer();
		if (getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && orderItem.getFullName() != null) {
					name.append(NAME_SEPARATOR).append(orderItem.getFullName());
				}
			}
			if (name.length() > 0) {
				name.deleteCharAt(0);
			}
		}
		return name.toString();
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		int weight = 0;
		if (getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null) {
					weight += orderItem.getTotalWeight();
				}
			}
		}
		return weight;
	}

	/**
	 * 获取商品数量
	 * 
	 * @return 商品数量
	 */
	@Transient
	public int getQuantity() {
		int quantity = 0;
		if (getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && orderItem.getQuantity() != null) {
					quantity += orderItem.getQuantity();
				}
			}
		}
		return quantity;
	}

	/**
	 * 获取已发货数量
	 * 
	 * @return 已发货数量
	 */
	@Transient
	public int getShippedQuantity() {
		int shippedQuantity = 0;
		if (getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && orderItem.getShippedQuantity() != null) {
					shippedQuantity += orderItem.getShippedQuantity();
				}
			}
		}
		return shippedQuantity;
	}

	/**
	 * 获取已退货数量
	 * 
	 * @return 已退货数量
	 */
	@Transient
	public int getReturnQuantity() {
		int returnQuantity = 0;
		if (getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && orderItem.getReturnQuantity() != null) {
					returnQuantity += orderItem.getReturnQuantity();
				}
			}
		}
		return returnQuantity;
	}

	/**
	 * 获取商品价格
	 * 
	 * @return 商品价格
	 */
	@Transient
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		if (getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && orderItem.getSubtotal() != null) {
					price = price.add(orderItem.getSubtotal());
				}
			}
		}
		return price;
	}

	/**
	 * 获取订单金额
	 * 
	 * @return 订单金额
	 */
	@Transient
	public BigDecimal getAmount() {
		BigDecimal amount = getTotalAmount();
		if (getFee() != null) {
			amount = amount.add(getFee());
		}
		if (getFreight() != null) {
			amount = amount.add(getFreight());
		}
		if (getPromotionDiscount() != null) {
			amount = amount.subtract(getPromotionDiscount());
		}
		if (getCouponDiscount() != null) {
			amount = amount.subtract(getCouponDiscount());
		}
		if (getOffsetAmount() != null) {
			amount = amount.add(getOffsetAmount());
		}
		if (getTax() != null) {
			amount = amount.add(getTax());
		}
		return amount.compareTo(new BigDecimal(0)) > 0 ? amount : new BigDecimal(0);
	}

	/**
	 * 获取应付金额
	 * 
	 * @return 应付金额
	 */
	@Transient
	public BigDecimal getAmountPayable() {
		BigDecimal amountPayable = getAmount().subtract(getAmountPaid());
		return amountPayable.compareTo(new BigDecimal(0)) > 0 ? amountPayable : new BigDecimal(0);
	}

	/**
	 * 是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean isExpired() {
		return getExpire() != null && new Date().after(getExpire());
	}

	/**
	 * 获取订单项
	 * 
	 * @param sn
	 *            商品编号
	 * @return 订单项
	 */
	@Transient
	public OrderItem getOrderItem(String sn) {
		if (sn != null && getOrderItems() != null) {
			for (OrderItem orderItem : getOrderItems()) {
				if (orderItem != null && sn.equalsIgnoreCase(orderItem.getSn())) {
					return orderItem;
				}
			}
		}
		return null;
	}

	/**
	 * 判断是否已锁定
	 * 
	 * @param operator
	 *            操作员
	 * @return 是否已锁定
	 */
	@Transient
	public boolean isLocked(Admin operator) {
		return getLockExpire() != null && new Date().before(getLockExpire()) && ((operator != null && !operator.equals(getOperator())) || (operator == null && getOperator() != null));
	}

	/**
	 * 计算税金
	 * 
	 * @return 税金
	 */
	@Transient
	public BigDecimal calculateTax() {
		BigDecimal tax = new BigDecimal(0);
		Setting setting = SettingUtils.get();
		if (setting.getIsTaxPriceEnabled()) {
			BigDecimal amount = getTotalAmount();
			if (getPromotionDiscount() != null) {
				amount = amount.subtract(getPromotionDiscount());
			}
			if (getCouponDiscount() != null) {
				amount = amount.subtract(getCouponDiscount());
			}
			if (getOffsetAmount() != null) {
				amount = amount.add(getOffsetAmount());
			}
			tax = amount.multiply(new BigDecimal(setting.getTaxRate().toString()));
		}
		return setting.setScale(tax);
	}



}