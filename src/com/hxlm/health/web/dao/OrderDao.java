/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hxlm.health.web.Filter;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.entity.Order.OrderStatus;
import com.hxlm.health.web.entity.Order.PaymentStatus;
import com.hxlm.health.web.entity.Order.ShippingStatus;

/**
 * Dao - 订单
 * 
 * 
 * 
 */
public interface OrderDao extends BaseDao<Order, Long> {

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 订单，若不存在则返回null
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
	Long count(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired);

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

	// 修改订单是否开发票以及备注等信息
	Order updateOrderMemo(Long id, Boolean isInvoice, String invoiceTitle, String memo);

	List<Order> findList(Member member, Integer count, Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus, Order.ShippingStatus shippingStatus);

	//查询当天已支付且未发货，
	List<Order> unconfirmedList();
}