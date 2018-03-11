/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hxlm.health.web.Filter;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.*;

/**
 * Service - 订单
 * 
 * 
 * 
 */
public interface OrderService extends BaseService<Order, Long> {

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 若不存在则返回null
	 */
	Order findBySn(String sn);

	/**
	 * 查找订单
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 订单
	 */
	List<Order> findList(Member member, Integer count, List<Filter> filters, List<com.hxlm.health.web.Order> orders);

	List<Order> findList(Member member, Integer count, Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus, Order.ShippingStatus shippingStatus);

	/**
	 * 查找订单分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPage(Member member, Pageable pageable);

	/**
	 * 查找订单分页
	 *
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param isSpecial
	 *            是否特价
	 * @param hasExpired
	 *            是否已过期
	 * @param company
	 *            航空公司
	 * @param paymentMethod
	 *            支付方式
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Order> findPage(Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus,Boolean isSpecial, Company company, Boolean hasExpired, PaymentMethod paymentMethod,
						 Date startDate, Date endDate, Pageable pageable);
	/**
	 * 查询订单数量
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param shippingStatus
	 *            配送状态
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long count(Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus, Order.ShippingStatus shippingStatus, Boolean hasExpired);

	/**
	 * 查询等待支付订单数量
	 * 
	 * @param member
	 *            会员
	 * @return 等待支付订单数量
	 */
	Long waitingPaymentCount(Member member);

	/**
	 * 查询等待发货订单数量
	 * 
	 * @param member
	 *            会员
	 * @return 等待发货订单数量
	 */
	Long waitingShippingCount(Member member);

	/**
	 * 获取销售额
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 销售额
	 */
	BigDecimal getSalesAmount(Date beginDate, Date endDate);

	/**
	 * 获取销售量
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 销售量
	 */
	Integer getSalesVolume(Date beginDate, Date endDate);

	/**
	 * 释放过期订单库存
	 */
	List<Order> releaseStock();

	// 生成订单
	Order buildOrder(Product product, int reserveNums, Member member, Boolean isInvoice, String invoiceTitle, String memo, BigDecimal totalFee);

//	/**
//	 * 生成订单
//	 *
//	 * @param cart
//	 *            购物车
//	 * @param receiver
//	 *            收货地址
//	 * @param paymentMethod
//	 *            支付方式
//	 * @param shippingMethod
//	 *            配送方式
//	 * @param couponCode
//	 *            优惠码
//	 * @param isInvoice
//	 *            是否开据发票
//	 * @param invoiceTitle
//	 *            发票抬头
//	 * @param useBalance
//	 *            是否使用余额
//	 * @param memo
//	 *            附言
//	 * @return 订单
//	 */
//	Order build(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo);

	// 创建订单
	Order createOrder(Product product, int reserveNums, Member member, Boolean isInvoice, String invoiceTitle, String memo, Admin operator, BigDecimal totalFee);

//	/**
//	 * 创建订单
//	 *
//	 * @param cart
//	 *            购物车
//	 * @param receiver
//	 *            收货地址
//	 * @param paymentMethod
//	 *            支付方式
//	 * @param shippingMethod
//	 *            配送方式
//	 * @param couponCode
//	 *            优惠码
//	 * @param isInvoice
//	 *            是否开据发票
//	 * @param invoiceTitle
//	 *            发票抬头
//	 * @param useBalance
//	 *            是否使用余额
//	 * @param memo
//	 *            附言
//	 * @param operator
//	 *            操作员
//	 * @return 订单
//	 */
//	Order create(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod, CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo, Admin operator);

	/**
	 * 创建订单
	 *
	 * @param cart
	 *            购物车
	 * @param receiver
	 *            收货地址
	 * @param paymentStatus
	 *            支付状态
	 * @param isInvoice
	 *            是否开据发票
	 * @param invoiceTitle
	 *            发票抬头
	 * @param memo
	 *            附言
	 * @param operator
	 *            操作员
	 * @return 订单
	 */
	Order create(Cart cart, PaymentMethod paymentMethod, Receiver receiver, boolean isInvoice, String invoiceTitle, String memo, Order.PaymentStatus paymentStatus, Admin operator);

//	Order create(Cart cart, PaymentMethod paymentMethod, Receiver receiver, boolean isInvoice, String invoiceTitle, String memo, Order.PaymentStatus paymentStatus, ShippingMethod shippingMethod, Admin operator);

	/**
	 * 更新订单
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void update(Order order, Admin operator);

	/**
	 * 订单确认
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void confirm(Order order, Admin operator);

	/**
	 * 订单完成
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void complete(Order order, Admin operator);

	/**
	 * 订单取消
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void cancel(Order order, Admin operator);

	/**
	 * 订单支付
	 * 
	 * @param order
	 *            订单
	 * @param payment
	 *            收款单
	 * @param operator
	 *            操作员
	 */
	void payment(Order order, Payment payment, Admin operator);

	/**
	 * 订单退款
	 * 
	 * @param order
	 *            订单
	 * @param refunds
	 *            退款单
	 * @param operator
	 *            操作员
	 */
	void refunds(Order order, Refunds refunds, Admin operator);

	/**
	 * 订单发货
	 *
	 * @param order
	 *            订单
	 * @param shipping
	 *            发货单
	 * @param operator
	 *            操作员
	 */
	void shipping(Order order, Shipping shipping, Admin operator);

	/**
	 * 订单退货
	 * 
	 * @param order
	 *            订单
	 * @param returns
	 *            退货单
	 * @param operator
	 *            操作员
	 */
	void returns(Order order, Returns returns, Admin operator);

	Order updateOrderMemo(Long id, Boolean isInvoice, String invoiceTitle, String memo);

	void updatePaymentStatus(Order order, Order.PaymentStatus paymentStatus, Order.OrderStatus orderStatus);

	void updatePaymentStatus(Order order, Order.PaymentStatus paymentStatus, Order.OrderStatus orderStatus, BigDecimal amountPaid);

	/**
	 * 查询未确认订单
	 * @return
	 */
	List<Order> unconfirmedList();

	/**
	 * 创建代客订单
	 * @param airplane 飞机
	 * @param customer  客户
	 * @param orderAirlines  航段
	 * @param totalFee  总金额
	 * @param isSpecial  是否特价
	 * @param specialId  特价航班计划id
	 * @param operator 操作员
	 * @return
	 */
	Order buildOrder(Airplane airplane, Customer customer, List<OrderAirline> orderAirlines, BigDecimal totalFee, Boolean isSpecial,Long specialId, Admin operator);
}