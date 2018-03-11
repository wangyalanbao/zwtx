/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import com.hxlm.health.web.*;
import com.hxlm.health.web.dao.*;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.service.*;
import com.hxlm.health.web.util.SettingUtils;
import com.hxlm.health.web.Setting.StockAllocationTime;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 订单
 * 
 * 
 * 
 */
@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {

	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;
	@Resource(name = "orderItemDaoImpl")
	private OrderItemDao orderItemDao;
	@Resource(name = "orderLogDaoImpl")
	private OrderLogDao orderLogDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "memberRankDaoImpl")
	private MemberRankDao memberRankDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "paymentDaoImpl")
	private PaymentDao paymentDao;
	@Resource(name = "shippingDaoImpl")
	private ShippingDao shippingDao;
	@Resource(name = "refundsDaoImpl")
	private RefundsDao refundsDao;
	@Resource(name = "returnsDaoImpl")
	private ReturnsDao returnsDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;

	@Resource(name = "orderDaoImpl")
	public void setBaseDao(OrderDao orderDao) {
		super.setBaseDao(orderDao);
	}

	@Transactional(readOnly = true)
	public Order findBySn(String sn) {
		return orderDao.findBySn(sn);
	}

	@Transactional(readOnly = true)
	public List<Order> findList(Member member, Integer count, List<Filter> filters, List<com.hxlm.health.web.Order> orders) {
		return orderDao.findList(member, count, filters, orders);
	}

	@Transactional(readOnly = true)
	public List<Order> findList(Member member, Integer count, Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus, Order.ShippingStatus shippingStatus){
		return  orderDao.findList(member,count,orderStatus,paymentStatus,shippingStatus);
	}

	@Transactional(readOnly = true)
	public Page<Order> findPage(Member member, Pageable pageable) {
		return orderDao.findPage(member, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Order> findPage(Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus,Boolean isSpecial, Company company, Boolean hasExpired, PaymentMethod paymentMethod,
								Date startDate, Date endDate, Pageable pageable) {
		return orderDao.findPage(orderStatus, paymentStatus, isSpecial,company, hasExpired, paymentMethod, startDate, endDate, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus, Order.ShippingStatus shippingStatus, Boolean hasExpired) {
		return orderDao.count(orderStatus, paymentStatus, shippingStatus, hasExpired);
	}

	@Transactional(readOnly = true)
	public Long waitingPaymentCount(Member member) {
		return orderDao.waitingPaymentCount(member);
	}

	@Transactional(readOnly = true)
	public Long waitingShippingCount(Member member) {
		return orderDao.waitingShippingCount(member);
	}

	@Transactional(readOnly = true)
	public BigDecimal getSalesAmount(Date beginDate, Date endDate) {
		return orderDao.getSalesAmount(beginDate, endDate);
	}

	@Transactional(readOnly = true)
	public Integer getSalesVolume(Date beginDate, Date endDate) {
		return orderDao.getSalesVolume(beginDate, endDate);
	}

	public List<Order> releaseStock() {
		return orderDao.releaseStock();
	}

	// 生成订单，不保存，先进行基本的设置
	@Transactional(readOnly = true)
	public Order buildOrder(Product product, int reserveNums, Member member, Boolean isInvoice, String invoiceTitle, String memo, BigDecimal totalFee) {
		Assert.notNull(product);
		Assert.notNull(member);

		Order order = new Order();
		// 设置支付方式的名称
		order.setPaymentMethodName("网上支付");
		// 配送状态
		order.setShippingStatus(Order.ShippingStatus.unshipped);
		// 订单总金额
		order.setTotalAmount(totalFee);
		// 支付手续费
		order.setFee(new BigDecimal(0));
		// 促销折扣
		order.setPromotionDiscount(new BigDecimal(0));
		// 设置优惠券折扣
		order.setCouponDiscount(new BigDecimal(0));
		// 设置调整金额
		order.setOffsetAmount(new BigDecimal(0));
		// 设置赠送积分
		order.setPoint(0L);
		// 设置留言备注
		order.setMemo(memo);
		// 设置用户
		order.setMember(member);
		// 设置支付状态
		order.setPaymentStatus(Order.PaymentStatus.unpaid);
		// 设置已付金额，这个方法可以实现优惠价格
		order.setAmountPaid(new BigDecimal(0));
		// 设置订单状态
		order.setOrderStatus(Order.OrderStatus.unconfirmed);
		// 设置收货人
		order.setConsignee(member.getUsername());
		// 设置地址
		order.setAddress(member.getAddress());
		// 设置运费
		order.setFreight(new BigDecimal(0));
		// 设置电话
		order.setTelephone(member.getUsername());
		// 设置邮编
		order.setZipCode(member.getZipCode());
		// 设置支付方式
		order.setPaymentMethod(null);
		// 设置订单分类
		order.setCategory(productCategoryService.findRoot(product,null));

		// 设置是否开发票
		Setting setting = SettingUtils.get();
		if (setting.getIsInvoiceEnabled() && isInvoice && StringUtils.isNotEmpty(invoiceTitle)) {
			order.setIsInvoice(true);
			order.setInvoiceTitle(invoiceTitle);
			order.setTax(order.calculateTax());
		} else {
			order.setIsInvoice(false);
			order.setTax(new BigDecimal(0));
		}

		// 设置订单里的商品
		List<OrderItem> orderItems = order.getOrderItems();
		OrderItem orderItem = new OrderItem();
		orderItem.setSn(product.getSn());
		orderItem.setName(product.getName());
		orderItem.setFullName(product.getFullName());
		orderItem.setPrice(product.getPrice());
		orderItem.setWeight(product.getWeight());
		orderItem.setThumbnail(product.getThumbnail());
		orderItem.setIsGift(false);
		orderItem.setQuantity(reserveNums);
		orderItem.setShippedQuantity(0);
		orderItem.setReturnQuantity(0);
		orderItem.setProduct(product);
		orderItem.setOrder(order);
		orderItems.add(orderItem);

		order.setOrderItems(orderItems);

		return order;
	}

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
	// 生成代客订单
	@Transactional(readOnly = true)
	public Order buildOrder(Airplane airplane, Customer customer, List<OrderAirline> orderAirlines, BigDecimal totalFee, Boolean isSpecial,Long specialId, Admin operator) {
		Assert.notNull(airplane);
		Assert.notNull(customer);
		Assert.notEmpty(orderAirlines);

		Order order = new Order();
		// 设置订单编号
		order.setSn(snDao.generate(Sn.Type.order));
		// 设置飞机
		order.setAirplane(airplane);
		// 设置航空公司
		order.setCompany(airplane.getCompany());
		// 设置锁定到期时间
		order.setLockExpire(DateUtils.addSeconds(new Date(), 20));
		// 设置操作人
		order.setOperator(operator);
		// 设置是否特价
		order.setIsSpecial(isSpecial);
		if(isSpecial){
			order.setSpecialId(specialId);
		}
		//  第一航段起飞时间
		order.setFirstTakeoffTime(orderAirlines.get(0).getTakeoffTime());
		// 最后航段起飞时间
		order.setLastTakeoffTime(orderAirlines.get(orderAirlines.size() - 1).getTakeoffTime());
		// 设置支付方式的名称
		order.setPaymentMethodName("online");
		// 配送状态
		order.setShippingStatus(Order.ShippingStatus.unshipped);
		// 订单总金额
		order.setTotalAmount(totalFee);
		// 支付手续费
		order.setFee(new BigDecimal(0));
		// 促销折扣
		order.setPromotionDiscount(new BigDecimal(0));
		// 设置优惠券折扣
		order.setCouponDiscount(new BigDecimal(0));
		// 设置调整金额
		order.setOffsetAmount(new BigDecimal(0));
		// 设置赠送积分
		order.setPoint(0L);
		// 设置用户
		order.setCustomerId(customer);
		// 设置支付状态
		order.setPaymentStatus(Order.PaymentStatus.unpaid);
		// 设置已付金额，这个方法可以实现优惠价格
		order.setAmountPaid(new BigDecimal(0));
		// 设置订单状态
		order.setOrderStatus(Order.OrderStatus.unconfirmed);
		// 设置收货人
		order.setConsignee(customer.getRealName());
		// 设置运费
		order.setFreight(new BigDecimal(0));
		// 设置电话
		order.setTelephone(customer.getTelephone());
		// 设置支付方式
		order.setPaymentMethod(null);

		// 设置是否开发票
		order.setIsInvoice(true);

		orderDao.persist(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.create);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		return order;
	}

	// 生成订单
	@Transactional(readOnly = true)
	public Order build(Cart cart, Receiver receiver, PaymentMethod paymentMethod, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo) {
		Assert.notNull(cart);
		Assert.notNull(cart.getMember());
		Assert.notEmpty(cart.getCartItems());

		Order order = new Order();
		order.setShippingStatus(Order.ShippingStatus.unshipped);
		order.setFee(new BigDecimal(0));
		order.setCouponDiscount(new BigDecimal(0));
		order.setOffsetAmount(new BigDecimal(0));
		order.setTotalAmount(new BigDecimal(0));
		order.setMemo(memo);
		order.setMember(cart.getMember());
		// 设置订单分类
		order.setCategory(productCategoryService.findRoot(null,cart));

		if (receiver != null) {
			order.setConsignee(receiver.getConsignee());
			order.setAreaName(receiver.getAreaName());
			order.setAddress(receiver.getAddress());
			order.setZipCode(receiver.getZipCode());
			order.setTelephone(receiver.getPhone());
			order.setArea(receiver.getArea());
		}

		order.setPaymentMethod(paymentMethod);


		order.setFreight(new BigDecimal(0));

		List<OrderItem> orderItems = order.getOrderItems();
		for (CartItem cartItem : cart.getCartItems()) {
			if (cartItem != null && cartItem.getProduct() != null) {
				Product product = cartItem.getProduct();
				OrderItem orderItem = new OrderItem();
				orderItem.setSn(product.getSn());
				orderItem.setName(product.getName());
				orderItem.setFullName(product.getFullName());
				orderItem.setPrice(cartItem.getPrice());
				orderItem.setWeight(product.getWeight());
				orderItem.setThumbnail(product.getThumbnail());
				orderItem.setIsGift(false);
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnQuantity(0);
				orderItem.setProduct(product);
				orderItem.setOrder(order);
				orderItems.add(orderItem);
			}
		}

		Setting setting = SettingUtils.get();
		if (setting.getIsInvoiceEnabled() && isInvoice && StringUtils.isNotEmpty(invoiceTitle)) {
			order.setIsInvoice(true);
			order.setInvoiceTitle(invoiceTitle);
			order.setTax(order.calculateTax());
		} else {
			order.setIsInvoice(false);
			order.setTax(new BigDecimal(0));
		}

		if (useBalance) {
			Member member = cart.getMember();
			if (member.getBalance().compareTo(order.getAmount()) >= 0) {
				order.setAmountPaid(order.getAmount());
			} else {
				order.setAmountPaid(member.getBalance());
			}
		} else {
			order.setAmountPaid(new BigDecimal(0));
		}

		if (order.getAmountPayable().compareTo(new BigDecimal(0)) == 0) {
			order.setOrderStatus(Order.OrderStatus.confirmed);
			order.setPaymentStatus(Order.PaymentStatus.paid);
		} else if (order.getAmountPayable().compareTo(new BigDecimal(0)) > 0 && order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
			order.setOrderStatus(Order.OrderStatus.confirmed);
			order.setPaymentStatus(Order.PaymentStatus.partialPayment);
		} else {
			order.setOrderStatus(Order.OrderStatus.unconfirmed);
			order.setPaymentStatus(Order.PaymentStatus.unpaid);
		}

		if (paymentMethod != null && paymentMethod.getTimeout() != null && order.getPaymentStatus() == Order.PaymentStatus.unpaid) {
			order.setExpire(DateUtils.addMinutes(new Date(), paymentMethod.getTimeout()));
		}

		return order;
	}

	// 创建订单
	// 同时需要修改商品库存
	public Order createOrder(Product product, int reserveNums, Member member, Boolean isInvoice, String invoiceTitle, String memo, Admin operator, BigDecimal totalFee){
		Assert.notNull(product);
		Assert.notNull(member);

		Order order = buildOrder(product, reserveNums, member, isInvoice, invoiceTitle, memo, totalFee);

		// 设置订单编号
		order.setSn(snDao.generate(Sn.Type.order));
		// 设置锁定到期时间
		order.setLockExpire(DateUtils.addSeconds(new Date(), 20));
		order.setOperator(operator);

		// 设置是否已分配库存
		Setting setting = SettingUtils.get();
		// 库存分配时间点是下订单;或者，库存分配时间点是支付的时点同时订单支付状态为部分支付或者已经支付，则修改已分配库存状态为
		if (setting.getStockAllocationTime() == StockAllocationTime.order || (setting.getStockAllocationTime() == StockAllocationTime.payment && (order.getPaymentStatus() == Order.PaymentStatus.partialPayment || order.getPaymentStatus() == Order.PaymentStatus.paid))) {
			order.setIsAllocatedStock(true);
		} else {
			order.setIsAllocatedStock(false);
		}

		orderDao.persist(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.create);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		// 修改库存，当库存分配时间点等于
		if (setting.getStockAllocationTime() == StockAllocationTime.order || (setting.getStockAllocationTime() == StockAllocationTime.payment && (order.getPaymentStatus() == Order.PaymentStatus.partialPayment || order.getPaymentStatus() == Order.PaymentStatus.paid))) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() + (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
		}

		return order;
	}

	// 创建订单
	public Order create(Cart cart, PaymentMethod paymentMethod, Receiver receiver, boolean isInvoice, String invoiceTitle, String memo, Order.PaymentStatus paymentStatus, Admin operator) {
		Assert.notNull(cart);
		Assert.notNull(cart.getMember());
		Assert.notEmpty(cart.getCartItems());
		Assert.notNull(receiver);
		Assert.notNull(paymentMethod);

		Order order = build(cart, receiver, paymentMethod, isInvoice, invoiceTitle, false, memo);

		order.setPaymentMethod(paymentMethod);
		order.setSn(snDao.generate(Sn.Type.order));
		order.setPaymentStatus(paymentStatus);
		order.setPaymentMethodName("网上支付");
		// 订单总金额
		order.setTotalAmount(cart.getPrice());

		order.setFreight(new BigDecimal(0));

		if (paymentMethod.getMethod() == PaymentMethod.Method.online) {
			order.setLockExpire(DateUtils.addSeconds(new Date(), 20));
			order.setOperator(operator);
		}

		Setting setting = SettingUtils.get();
		if (setting.getStockAllocationTime() == StockAllocationTime.order || (setting.getStockAllocationTime() == StockAllocationTime.payment && (order.getPaymentStatus() == Order.PaymentStatus.partialPayment || order.getPaymentStatus() == Order.PaymentStatus.paid))) {
			order.setIsAllocatedStock(true);
		} else {
			order.setIsAllocatedStock(false);
		}

		orderDao.persist(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.create);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		if (setting.getStockAllocationTime() == StockAllocationTime.order || (setting.getStockAllocationTime() == StockAllocationTime.payment && (order.getPaymentStatus() == Order.PaymentStatus.partialPayment || order.getPaymentStatus() == Order.PaymentStatus.paid))) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() + (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
		}

		return order;
	}

	// 创建订单
	public Order create(Cart cart, Receiver receiver, PaymentMethod paymentMethod, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo, Admin operator) {
		Assert.notNull(cart);
		Assert.notNull(cart.getMember());
		Assert.notEmpty(cart.getCartItems());
		Assert.notNull(receiver);
		Assert.notNull(paymentMethod);

		Order order = build(cart, receiver, paymentMethod, isInvoice, invoiceTitle, useBalance, memo);

		order.setSn(snDao.generate(Sn.Type.order));
		if (paymentMethod.getMethod() == PaymentMethod.Method.online) {
			order.setLockExpire(DateUtils.addSeconds(new Date(), 20));
			order.setOperator(operator);
		}

		Setting setting = SettingUtils.get();
		if (setting.getStockAllocationTime() == StockAllocationTime.order || (setting.getStockAllocationTime() == StockAllocationTime.payment && (order.getPaymentStatus() == Order.PaymentStatus.partialPayment || order.getPaymentStatus() == Order.PaymentStatus.paid))) {
			order.setIsAllocatedStock(true);
		} else {
			order.setIsAllocatedStock(false);
		}

		orderDao.persist(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.create);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		Member member = cart.getMember();
		if (order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
			memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
			member.setBalance(member.getBalance().subtract(order.getAmountPaid()));
			memberDao.merge(member);
		}

		if (setting.getStockAllocationTime() == StockAllocationTime.order || (setting.getStockAllocationTime() == StockAllocationTime.payment && (order.getPaymentStatus() == Order.PaymentStatus.partialPayment || order.getPaymentStatus() == Order.PaymentStatus.paid))) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() + (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
		}

		return order;
	}



	public void update(Order order, Admin operator) {
		Assert.notNull(order);

		Order pOrder = orderDao.find(order.getId());

//		if (pOrder.getIsAllocatedStock()) {
//			for (OrderItem orderItem : pOrder.getOrderItems()) {
//				if (orderItem != null) {
//					Product product = orderItem.getProduct();
//					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
//					if (product != null && product.getStock() != null) {
//						product.setAllocatedStock(product.getAllocatedStock() - (orderItem.getQuantity() - orderItem.getShippedQuantity()));
//						productDao.merge(product);
//						orderDao.flush();
//						staticService.build(product);
//					}
//				}
//			}
//			for (OrderItem orderItem : order.getOrderItems()) {
//				if (orderItem != null) {
//					Product product = orderItem.getProduct();
//					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
//					if (product != null && product.getStock() != null) {
//						product.setAllocatedStock(product.getAllocatedStock() + (orderItem.getQuantity() - orderItem.getShippedQuantity()));
//						productDao.merge(product);
//						productDao.flush();
//						staticService.build(product);
//					}
//				}
//			}
//		}

		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.modify);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void confirm(Order order, Admin operator) {
		Assert.notNull(order);

		order.setOrderStatus(Order.OrderStatus.confirmed);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.confirm);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void complete(Order order, Admin operator) {
		Assert.notNull(order);

		Member member = order.getMember();
		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);

		if (order.getShippingStatus() == Order.ShippingStatus.partialShipment || order.getShippingStatus() == Order.ShippingStatus.shipped) {
			member.setPoint(member.getPoint() + order.getPoint());
		}

		if (order.getShippingStatus() == Order.ShippingStatus.unshipped || order.getShippingStatus() == Order.ShippingStatus.returned) {

		}

		member.setAmount((order.getAmountPaid() == null? BigDecimal.ZERO:order.getAmountPaid()).add(member.getAmount() == null? BigDecimal.ZERO:member.getAmount()));
		if (member.getMemberRank() == null || !member.getMemberRank().getIsSpecial()) {
			MemberRank memberRank = memberRankDao.findByAmount(member.getAmount());
			if (memberRank != null && memberRank.getAmount().compareTo(member.getMemberRank() == null ? new BigDecimal(0):member.getMemberRank().getAmount()) > 0) {
				member.setMemberRank(memberRank);
			}
		}
		memberDao.merge(member);

		if (order.getIsAllocatedStock()) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() - (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
			order.setIsAllocatedStock(false);
		}

		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem != null) {
				Product product = orderItem.getProduct();
				productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
				if (product != null) {
					Integer quantity = orderItem.getQuantity();
					Calendar nowCalendar = Calendar.getInstance();
					Calendar weekSalesCalendar = DateUtils.toCalendar(product.getWeekSalesDate());
					Calendar monthSalesCalendar = DateUtils.toCalendar(product.getMonthSalesDate());
					if (nowCalendar.get(Calendar.YEAR) != weekSalesCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.WEEK_OF_YEAR) > weekSalesCalendar.get(Calendar.WEEK_OF_YEAR)) {
						product.setWeekSales((long) quantity);
					} else {
						product.setWeekSales(product.getWeekSales() + quantity);
					}
					if (nowCalendar.get(Calendar.YEAR) != monthSalesCalendar.get(Calendar.YEAR) || nowCalendar.get(Calendar.MONTH) > monthSalesCalendar.get(Calendar.MONTH)) {
						product.setMonthSales((long) quantity);
					} else {
						product.setMonthSales(product.getMonthSales() + quantity);
					}
					product.setSales(product.getSales() + quantity);
					product.setWeekSalesDate(new Date());
					product.setMonthSalesDate(new Date());
					productDao.merge(product);
					orderDao.flush();
					staticService.build(product);
				}
			}
		}

		order.setOrderStatus(Order.OrderStatus.completed);
		order.setExpire(null);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.complete);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void cancel(Order order, Admin operator) {
		Assert.notNull(order);

		if (order.getIsAllocatedStock()) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() - (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
			order.setIsAllocatedStock(false);
		}

		order.setOrderStatus(Order.OrderStatus.cancelled);
		order.setExpire(null);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.cancel);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void updatePaymentStatus(Order order, Order.PaymentStatus paymentStatus, Order.OrderStatus orderStatus) {
		Assert.notNull(order);

		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);

		Setting setting = SettingUtils.get();
		if (!order.getIsAllocatedStock() && setting.getStockAllocationTime() == StockAllocationTime.payment) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() + (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
					}
				}
			}
			order.setIsAllocatedStock(true);
		}

		order.setOrderStatus(orderStatus);
		order.setPaymentStatus(paymentStatus);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.payment);
		orderLog.setOperator(null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void updatePaymentStatus(Order order, Order.PaymentStatus paymentStatus, Order.OrderStatus orderStatus, BigDecimal amountPaid) {
		Assert.notNull(order);

		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);

		Setting setting = SettingUtils.get();
		if (!order.getIsAllocatedStock() && setting.getStockAllocationTime() == StockAllocationTime.payment) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() + (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
					}
				}
			}
			order.setIsAllocatedStock(true);
		}

		order.setOrderStatus(orderStatus);
		order.setPaymentStatus(paymentStatus);
		order.setAmountPaid(amountPaid);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.payment);
		orderLog.setOperator(null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void payment(Order order, Payment payment, Admin operator) {
		Assert.notNull(order);
		Assert.notNull(payment);
		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);

		payment.setOrder(order);
		paymentDao.merge(payment);
		if (payment.getMethod() == Payment.Method.deposit) {
			Member member = order.getMember();
			memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
			member.setBalance(member.getBalance().subtract(payment.getAmount()));
			memberDao.merge(member);

		}

		Setting setting = SettingUtils.get();
		if (!order.getIsAllocatedStock() && setting.getStockAllocationTime() == StockAllocationTime.payment) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() + (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
			order.setIsAllocatedStock(true);
		}

		order.setAmountPaid(order.getAmountPaid().add(payment.getAmount()));
		order.setFee(payment.getFee());
		order.setExpire(null);
		if (order.getAmountPaid().compareTo(order.getAmount()) >= 0) {
			order.setOrderStatus(Order.OrderStatus.confirmed);
			order.setPaymentStatus(Order.PaymentStatus.paid);
		} else if (order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
			order.setOrderStatus(Order.OrderStatus.confirmed);
			order.setPaymentStatus(Order.PaymentStatus.partialPayment);
		}
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.payment);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void refunds(Order order, Refunds refunds, Admin operator) {
		Assert.notNull(order);
		Assert.notNull(refunds);

		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);

		refunds.setOrder(order);
		refundsDao.persist(refunds);
		if (refunds.getMethod() == Refunds.Method.deposit) {
			Member member = order.getMember();
			memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
			member.setBalance(member.getBalance().add(refunds.getAmount()));
			memberDao.merge(member);

		}

		order.setAmountPaid(order.getAmountPaid().subtract(refunds.getAmount()));
		order.setExpire(null);
		if (order.getAmountPaid().compareTo(new BigDecimal(0)) == 0) {
			order.setPaymentStatus(Order.PaymentStatus.refunded);
		} else if (order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
			order.setPaymentStatus(Order.PaymentStatus.partialRefunds);
		}
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.refunds);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	/**
	 * 订单发票寄送
	 *
	 * @param order    订单
	 * @param shipping 发货单
	 * @param operator
	 */
	@Override
	public void shipping(Order order, Shipping shipping, Admin operator) {
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.shipping);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void returns(Order order, Returns returns, Admin operator) {
		Assert.notNull(order);
		Assert.notNull(returns);
		Assert.notEmpty(returns.getReturnsItems());

		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);

		returns.setOrder(order);
		returnsDao.persist(returns);
		for (ReturnsItem returnsItem : returns.getReturnsItems()) {
			OrderItem orderItem = order.getOrderItem(returnsItem.getSn());
			if (orderItem != null) {
				orderItemDao.lock(orderItem, LockModeType.PESSIMISTIC_WRITE);
				orderItem.setReturnQuantity(orderItem.getReturnQuantity() + returnsItem.getQuantity());
			}
		}
		if (order.getReturnQuantity() >= order.getShippedQuantity()) {
			order.setShippingStatus(Order.ShippingStatus.returned);
		} else if (order.getReturnQuantity() > 0) {
			order.setShippingStatus(Order.ShippingStatus.partialReturns);
		}
		order.setExpire(null);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.returns);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	@Transactional
	public Order updateOrderMemo(Long id, Boolean isInvoice, String invoiceTitle, String memo) {
		return orderDao.updateOrderMemo(id, isInvoice, invoiceTitle, memo);
	}

	@Override
	public void delete(Order order) {
		if (order.getIsAllocatedStock()) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock() - (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
		}
		super.delete(order);
	}

	/**
	 * 查询未确认订单
	 * @return
	 */
	public List<Order> unconfirmedList(){
		return orderDao.unconfirmedList();
	}

}