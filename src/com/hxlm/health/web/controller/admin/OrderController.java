/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.controller.admin;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hxlm.health.web.*;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.service.*;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 订单
 * 
 * 
 * 
 */
@Controller("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController extends BaseController {

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	@Resource(name = "shippingServiceImpl")
	private ShippingService shippingService;
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	@Resource(name = "companyServiceImpl")
	private CompanyService companyService;
	@Resource(name = "airlineServiceImpl")
	private AirlineService airlineService;
	@Resource(name = "airportServiceImpl")
	private AirportService airportService;
	@Resource(name = "orderAirlineServiceImpl")
	private OrderAirlineService orderAirlineService;
	@Resource(name = "orderFlightServiceImpl")
	private OrderFlightService orderFlightService;
	@Resource(name = "orderPassengerServiceImpl")
	private OrderPassengerService orderPassengerService;
	@Resource(name = "orderCateringServiceImpl")
	private OrderCateringService orderCateringService;
	@Resource(name = "orderPickupServiceImpl")
	private OrderPickupService orderPickupService;
	@Resource(name = "quoteServiceImpl")
	private QuoteService quoteService;
	@Resource(name = "airplaneServiceImpl")
	private AirplaneService airplaneService;
	@Resource(name = "planeTypeServiceImpl")
	private PlaneTypeService planeTypeService;
	@Resource(name = "flightPlanServiceImpl")
	private FlightPlanService flightPlanService;
	@Resource(name = "customerServiceImpl")
	private CustomerService customerService;

	/**
	 * 检查锁定
	 */
	@RequestMapping(value = "/check_lock", method = RequestMethod.POST)
	public @ResponseBody
	Message checkLock(Long id) {
		Order order = orderService.find(id);
		if (order == null) {
			return Message.warn("admin.common.invalid");
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			if (order.getOperator() != null) {
				return Message.warn("admin.order.adminLocked", order.getOperator().getUsername());
			} else {
				return Message.warn("admin.order.memberLocked");
			}
		} else {
			order.setLockExpire(DateUtils.addSeconds(new Date(), 20));
			order.setOperator(admin);
			orderService.update(order);
			return SUCCESS_MESSAGE;
		}
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		Order order = orderService.find(id);
		model.addAttribute("methods", Payment.Method.values());
		model.addAttribute("refundsMethods", Refunds.Method.values());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		model.addAttribute("order", order);
		if(order.getShippings().size() > 0){
			model.addAttribute("shipping", order.getShippings().get(0));
		}
		return "/admin/order/view";
	}

	/**
	 * 确认
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirm(Long id, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);
		Admin admin = adminService.getCurrent();
		if (order != null && !order.isExpired() && order.getOrderStatus() == Order.OrderStatus.unconfirmed && !order.isLocked(admin)) {
			orderService.confirm(order, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else {
			addFlashMessage(redirectAttributes, Message.warn("admin.common.invalid"));
		}
		return "redirect:view.jhtml?id=" + id;
	}

	/**
	 * 完成
	 */
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public String complete(Long id, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);
		Admin admin = adminService.getCurrent();
		if (order != null && !order.isExpired() && order.getOrderStatus() == Order.OrderStatus.confirmed && !order.isLocked(admin)) {
			orderService.complete(order, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else {
			addFlashMessage(redirectAttributes, Message.warn("admin.common.invalid"));
		}
		return "redirect:view.jhtml?id=" + id;
	}

	/**
	 * 取消
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel(Long id, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);
		Admin admin = adminService.getCurrent();
		if (order != null && !order.isExpired() && order.getOrderStatus() == Order.OrderStatus.unconfirmed && !order.isLocked(admin)) {
			orderService.cancel(order, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else {
			addFlashMessage(redirectAttributes, Message.warn("admin.common.invalid"));
		}
		return "redirect:view.jhtml?id=" + id;
	}

	/**
	 * 支付
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String payment(Long orderId, Long paymentMethodId, Payment payment, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		payment.setOrder(order);
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		payment.setPaymentMethod(paymentMethod != null ? paymentMethod.getName() : null);
		if (!isValid(payment)) {
			return ERROR_VIEW;
		}
		if (order.isExpired() || order.getOrderStatus() != Order.OrderStatus.confirmed) {
			return ERROR_VIEW;
		}
		if (order.getPaymentStatus() != Order.PaymentStatus.unpaid && order.getPaymentStatus() != Order.PaymentStatus.partialPayment) {
			return ERROR_VIEW;
		}
		if (payment.getAmount().compareTo(new BigDecimal(0)) <= 0 || payment.getAmount().compareTo(order.getAmountPayable()) > 0) {
			return ERROR_VIEW;
		}
		Member member = order.getMember();
		if (payment.getMethod() == Payment.Method.deposit && payment.getAmount().compareTo(member.getBalance()) > 0) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		payment.setSn(snService.generate(Sn.Type.payment));
		payment.setType(Payment.Type.payment);
		payment.setStatus(Payment.Status.success);
		payment.setFee(new BigDecimal(0));
		payment.setOperator(admin.getUsername());
		payment.setPaymentDate(new Date());
		payment.setPaymentPluginId(null);
		payment.setExpire(null);
		payment.setMember(null);
		orderService.payment(order, payment, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 退款
	 */
	@RequestMapping(value = "/refunds", method = RequestMethod.POST)
	public String refunds(Long orderId, Long paymentMethodId, Refunds refunds, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		refunds.setOrder(order);
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		refunds.setPaymentMethod(paymentMethod != null ? paymentMethod.getName() : null);
		refunds.setStatus(Refunds.Status.noRefund);
		if (!isValid(refunds)) {
			return ERROR_VIEW;
		}
		if (order.isExpired() || order.getOrderStatus() != Order.OrderStatus.confirmed) {
			return ERROR_VIEW;
		}
		if (order.getPaymentStatus() != Order.PaymentStatus.paid && order.getPaymentStatus() != Order.PaymentStatus.partialPayment && order.getPaymentStatus() != Order.PaymentStatus.partialRefunds) {
			return ERROR_VIEW;
		}
		if (refunds.getAmount().compareTo(new BigDecimal(0)) <= 0 || refunds.getAmount().compareTo(order.getAmountPaid()) > 0) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		refunds.setSn(snService.generate(Sn.Type.refunds));
		refunds.setOperator(admin.getUsername());
		orderService.refunds(order, refunds, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 *  发票寄送
	*/
	@RequestMapping(value = "/shipping", method = RequestMethod.POST)
	public String shipping(Long orderId, Long shippingMethodId, Long deliveryCorpId, Long areaId, Shipping shipping, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		if (order == null) {
			return ERROR_VIEW;
		}
		if(shipping.getId() != null){
			shipping = shippingService.find(shipping.getId());
		}

		shipping.setOrder(order);
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		shipping.setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
		DeliveryCorp deliveryCorp = shippingMethod.getDefaultDeliveryCorp();
		shipping.setDeliveryCorp(deliveryCorp != null ? deliveryCorp.getName() : null);
		shipping.setDeliveryCorpUrl(deliveryCorp != null ? deliveryCorp.getUrl() : null);
		shipping.setDeliveryCorpCode(deliveryCorp != null ? deliveryCorp.getCode() : null);
		Area area = areaService.find(areaId);
		shipping.setArea(area);
		Admin admin = adminService.getCurrent();

		shipping.setOperator(admin.getUsername());
		if(shipping.getId() == null){
			shipping.setSn(snService.generate(Sn.Type.shipping));
			shippingService.save(shipping);
		}else {
			shippingService.update(shipping);
		}

		order.setShippingStatus(Order.ShippingStatus.shipped);
		order.setExpire(null);
		order.setShippingMethodName(shipping.getShippingMethod());
		order.setConsignee(shipping.getConsignee());

		orderService.update(order);

		orderService.shipping(order,shipping,admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 退货
	 */

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		Order order = orderService.find(id);
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("order", order);
		if(order.getShippings().size() > 0){
			model.addAttribute("shipping", order.getShippings().get(0));
		}
		model.addAttribute("airlineNum",order.getOrderAirlines().size());
		return "/admin/order/edit";
	}

	/**
	 * 订单项添加
	 */
	@RequestMapping(value = "/order_item_add", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> orderItemAdd(String productSn) {
		Map<String, Object> data = new HashMap<String, Object>();
		Product product = productService.findBySn(productSn);
		if (product == null) {
			data.put("message", Message.warn("admin.order.productNotExist"));
			return data;
		}
		if (!product.getIsMarketable()) {
			data.put("message", Message.warn("admin.order.productNotMarketable"));
			return data;
		}
		if (product.getIsOutOfStock()) {
			data.put("message", Message.warn("admin.order.productOutOfStock"));
			return data;
		}
		data.put("sn", product.getSn());
		data.put("fullName", product.getFullName());
		data.put("price", product.getPrice());
		data.put("weight", product.getWeight());
		data.put("isGift", product.getIsGift());
		data.put("message", SUCCESS_MESSAGE);
		return data;
	}

	/**
	 * 计算
	 */
	@RequestMapping(value = "/calculate", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> calculate(Order order, Long areaId, Long paymentMethodId, Long shippingMethodId) {
		Map<String, Object> data = new HashMap<String, Object>();
		for (Iterator<OrderItem> iterator = order.getOrderItems().iterator(); iterator.hasNext();) {
			OrderItem orderItem = iterator.next();
			if (orderItem == null || StringUtils.isEmpty(orderItem.getSn())) {
				iterator.remove();
			}
		}
		order.setArea(areaService.find(areaId));
		order.setPaymentMethod(paymentMethodService.find(paymentMethodId));
		if (!isValid(order)) {
			data.put("message", Message.warn("admin.common.invalid"));
			return data;
		}
		Order pOrder = orderService.find(order.getId());
		if (pOrder == null) {
			data.put("message", Message.error("admin.common.invalid"));
			return data;
		}
		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem.getId() != null) {
				OrderItem pOrderItem = orderItemService.find(orderItem.getId());
				if (pOrderItem == null || !pOrder.equals(pOrderItem.getOrder())) {
					data.put("message", Message.error("admin.common.invalid"));
					return data;
				}
				Product product = pOrderItem.getProduct();
				if (product != null && product.getStock() != null) {
					if (pOrder.getIsAllocatedStock()) {
						if (orderItem.getQuantity() > product.getAvailableStock() + pOrderItem.getQuantity()) {
							data.put("message", Message.warn("admin.order.lowStock"));
							return data;
						}
					} else {
						if (orderItem.getQuantity() > product.getAvailableStock()) {
							data.put("message", Message.warn("admin.order.lowStock"));
							return data;
						}
					}
				}
			} else {
				Product product = productService.findBySn(orderItem.getSn());
				if (product == null) {
					data.put("message", Message.error("admin.common.invalid"));
					return data;
				}
				if (product.getStock() != null && orderItem.getQuantity() > product.getAvailableStock()) {
					data.put("message", Message.warn("admin.order.lowStock"));
					return data;
				}
			}
		}
		Map<String, Object> orderItems = new HashMap<String, Object>();
		for (OrderItem orderItem : order.getOrderItems()) {
			orderItems.put(orderItem.getSn(), orderItem);
		}
		order.setFee(pOrder.getFee());
		order.setPromotionDiscount(pOrder.getPromotionDiscount());
		order.setCouponDiscount(pOrder.getCouponDiscount());
		order.setAmountPaid(pOrder.getAmountPaid());
		data.put("weight", order.getWeight());
		data.put("price", order.getPrice());
		data.put("quantity", order.getQuantity());
		data.put("amount", order.getAmount());
		data.put("orderItems", orderItems);
		data.put("message", SUCCESS_MESSAGE);
		return data;
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Order order, Long areaId, Long paymentMethodId, Long shippingMethodId, RedirectAttributes redirectAttributes) {
		Admin admin = adminService.getCurrent();
		for (Iterator<OrderAirline> iterator = order.getOrderAirlines().iterator(); iterator.hasNext();) {
			OrderAirline orderAirline = iterator.next();
			if (orderAirline == null) {
				iterator.remove();
			}
		}
		order.setArea(areaService.find(areaId));
		order.setPaymentMethod(paymentMethodService.find(paymentMethodId));
		order.setPaymentMethodName(paymentMethodService.find(paymentMethodId) == null?"":paymentMethodService.find(paymentMethodId).getName());
		if (!isValid(order)) {
			return ERROR_VIEW;
		}
		Order pOrder = orderService.find(order.getId());
		if (pOrder == null) {
			return ERROR_VIEW;
		}
		if (pOrder.isExpired() || pOrder.getOrderStatus() != Order.OrderStatus.unconfirmed) {
			return ERROR_VIEW;
		}
		if (pOrder.isLocked(admin)) {
			return ERROR_VIEW;
		}
//		if (!order.getIsInvoice()) {
//			order.setInvoiceTitle(null);
//			order.setTax(new BigDecimal(0));
//		}
		order.setAirplane(pOrder.getAirplane());
		// 航段
		Date firstTakeoffTime = null;
		Date lastTakeoffTime = null;
		if(order.getOrderAirlines().size() > 0){
			firstTakeoffTime = order.getOrderAirlines().get(0).getTakeoffTime();
			lastTakeoffTime = order.getOrderAirlines().get(order.getOrderAirlines().size() -1 ).getTakeoffTime();
		}
		BigDecimal totalAmount = pOrder.getTotalAmount();
		List<QuoteAirline> quoteAirlineList = new ArrayList<QuoteAirline>();
		List<OrderAirline> orderAirlineList = order.getOrderAirlines();
		// 飞机
		Airplane airplane = order.getAirplane();
		// 报价
		Quote tempQuote = new Quote();
		for(int i=0;i<orderAirlineList.size();i++){
			OrderAirline orderAirline = orderAirlineList.get(i);
			orderAirline.setTripOrder(order);
			// 起飞机场
			Airport start = airportService.find(orderAirline.getDepartureId());
			// 降落机场
			Airport end = airportService.find(orderAirline.getDestinationId());
			// 起飞时间
			Date date =  orderAirline.getTakeoffTime();
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);

			// 飞行时间
			Float timeCost = airlineService.lineHour(start, end,null,airplane.getTypeId(),ca.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
			orderAirline.setTimeCost(timeCost);
			if(orderAirline.getId() != null){
				OrderAirline  tempOrderAirline = orderAirlineService.find(orderAirline.getId());
				tempOrderAirline.setDepartureId(start.getId());
				tempOrderAirline.setDeparture(start.getName());
				tempOrderAirline.setDestinationId(end.getId());
				tempOrderAirline.setDestination(end.getName());
				tempOrderAirline.setTakeoffTime(date);
				tempOrderAirline.setPassengerNum(orderAirline.getPassengerNum());
				tempOrderAirline.setTimeCost(timeCost);
				orderAirlineService.update(tempOrderAirline);
			}else {
				orderAirlineService.save(orderAirline);
			}

			QuoteAirline quoteAirline = new QuoteAirline();
			quoteAirline.setDepartureId(start.getId());
			quoteAirline.setDeparture(start);
			quoteAirline.setDestinationId(end.getId());
			quoteAirline.setDestination(end);
			quoteAirline.setTakeoffTime(date);
			quoteAirline.setQuote(tempQuote);
			quoteAirlineList.add(quoteAirline);
		}

		tempQuote.setDepartureId(quoteAirlineList.get(0).getDepartureId());
		tempQuote.setDeparture(quoteAirlineList.get(0).getDeparture());
		tempQuote.setDestinationId(quoteAirlineList.get(quoteAirlineList.size() - 1).getDestinationId());
		tempQuote.setDestination(quoteAirlineList.get(quoteAirlineList.size() - 1).getDestination());
		tempQuote.setTakeoffTime(quoteAirlineList.get(0).getTakeoffTime());
		tempQuote.setRegNo(airplane.getRegNo());
		tempQuote.setType(airplane.getType());
		tempQuote.setAirplane(airplane);
		tempQuote.setAirplaneId(airplane.getId());
		tempQuote.setStatus(Quote.Status.unconfirmed);
		tempQuote.setQuoteAirlineList(quoteAirlineList);
		tempQuote = quoteService.price(tempQuote);

		order.setSn(pOrder.getSn());
		order.setOrderStatus(pOrder.getOrderStatus());
		order.setPaymentStatus(pOrder.getPaymentStatus());
		order.setShippingStatus(pOrder.getShippingStatus());
		order.setFee(pOrder.getFee());
		order.setPromotionDiscount(pOrder.getPromotionDiscount());
		order.setCouponDiscount(pOrder.getCouponDiscount());
		order.setAmountPaid(pOrder.getAmountPaid());
		order.setPromotion(pOrder.getPromotion());
		order.setExpire(pOrder.getExpire());
		order.setLockExpire(null);
		order.setIsAllocatedStock(pOrder.getIsAllocatedStock());
		order.setOperator(null);
		order.setMember(pOrder.getMember());
		order.setOrderLogs(pOrder.getOrderLogs());
		order.setPayments(pOrder.getPayments());
		order.setRefunds(pOrder.getRefunds());
		order.setReturns(pOrder.getReturns());
		order.setOrderCaterings(pOrder.getOrderCaterings());
		order.setOrderPassengers(pOrder.getOrderPassengers());
		order.setOrderPickups(pOrder.getOrderPickups());
		order.setIsSpecial(pOrder.getIsSpecial());
		order.setSpecialId(pOrder.getSpecialId());
		order.setRank(pOrder.getRank());
		order.setFirstTakeoffTime(firstTakeoffTime);
		order.setLastTakeoffTime(lastTakeoffTime);
		order.setTotalAmount(tempQuote.getTotalAmount());
		orderService.update(order, admin);

		Shipping shipping = order.getShippings().get(0);
		// 发票信息
		if(shipping.getId() != null){
			shipping = shippingService.find(shipping.getId());
		}

		shipping.setOrder(order);
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		if(shippingMethod != null){
			shipping.setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
			DeliveryCorp deliveryCorp = shippingMethod.getDefaultDeliveryCorp();
			shipping.setDeliveryCorp(deliveryCorp != null ? deliveryCorp.getName() : null);
			shipping.setDeliveryCorpUrl(deliveryCorp != null ? deliveryCorp.getUrl() : null);
			shipping.setDeliveryCorpCode(deliveryCorp != null ? deliveryCorp.getCode() : null);
		}
		Area area = areaService.find(areaId);
		shipping.setArea(area);

		shipping.setOperator(admin.getUsername());
		if(shipping.getId() == null){
			shipping.setSn(snService.generate(Sn.Type.shipping));
			shippingService.save(shipping);
		}else {
			shippingService.update(shipping);
		}

		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Order.OrderStatus orderStatus, Order.PaymentStatus paymentStatus, Boolean isSpecial, Long companyId,  Boolean hasExpired, Long categoryId, String startTime, String endTime, Pageable pageable, ModelMap model) {
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("paymentStatus", paymentStatus);
		model.addAttribute("isSpecial", isSpecial);
		model.addAttribute("hasExpired", hasExpired);
		model.addAttribute("categoryList", productCategoryService.findRoots());
		model.addAttribute("paymentMethodList", paymentMethodService.findAll());
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("companyId", companyId);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		List<Company> companyList = companyService.findAll();
		model.addAttribute("companyList", companyList);
		Company company = companyService.find(companyId);
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date startDate =null;
		Date endDate =null;
		try {
			if(StringUtils.isNotEmpty(startTime)){
				startDate = sdf.parse(startTime);
			}
			if(StringUtils.isNotEmpty(endTime)){
				endDate = sdf.parse(endTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			if(StringUtils.isNotEmpty(pageable.getSearchValue())){
				pageable.setSearchProperty("sn");
			}
			model.addAttribute("page", orderService.findPage(orderStatus, paymentStatus, isSpecial, company, hasExpired, null, startDate, endDate, pageable));
			return "/admin/order/list";
		}

	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			Admin admin = adminService.getCurrent();
			for (Long id : ids) {
				Order order = orderService.find(id);
				if (order != null && order.isLocked(admin)) {
					return Message.error("admin.order.deleteLockedNotAllowed", order.getSn());
				}
			}
			orderService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 删除航段
	 */
	@RequestMapping(value = "/deleteFlight", method = RequestMethod.POST)
	public @ResponseBody
	Message deleteFlight(Long id) {
		if (id != null) {
			orderFlightService.delete(id);
			return SUCCESS_MESSAGE;
		}
		return Message.error("不存在该航班");
	}

	/**
	 * 增加航班
	 */
	@RequestMapping(value = "/saveFlight", method = RequestMethod.GET)
	public @ResponseBody OrderFlight saveFlight(Long orderId, Long flightId, String flightNo, Long orderAirlineId,  Date boardingTime, String boardingPlace){
		OrderAirline orderAirline = orderAirlineService.find(orderAirlineId);
		OrderFlight orderFlight = new OrderFlight();
		orderFlight.setOrderAirline(orderAirline);
		orderFlight.setTripOrder(orderService.find(orderId));
		orderFlight.setFlightNo(flightNo);
		orderFlight.setBoardingTime(boardingTime);
		orderFlight.setBoardingPlace(boardingPlace);
		orderFlight.setId(flightId);
		if(orderFlight.getId() == null){
			return orderFlightService.saveAndReturn(orderFlight);
		}else {
			return orderFlightService.update(orderFlight);
		}
	}
	/**
	 * 根据id获取订单航班
	 */
	@RequestMapping(value = "/getFlight", method = RequestMethod.GET)
	public @ResponseBody OrderFlight getFlight(Long id){
		return orderFlightService.find(id);
	}

	/**
	 * 订单导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView export(Long[] ids,  ModelMap model) {
		List<Order> orderList = orderService.findList(ids);
		String filename = "ExportOrderList";
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMdd");
		filename = filename + sdf.format(new Date())  + ".xls";
		String sheetName = "订单列表";
		String[] titles = {"订单编号", "买家会员名", "买家应付货款", "支付手续费", "买家应付邮费", "促销折扣", "优惠券折扣", "订单总金额", "返点积分",
							"买家已付金额", "订单状态", "配送状态", "快递单号", "收货人", "收货地区", "详细地址", "邮编", "联系电话", "发票抬头", "税金", "支付方式", "配送方式",
							"订单创建时间", "订单收款时间", "宝贝名称", "宝贝总数量"
							};
		String[] properties = {"sn", "member.username", "amount", "fee", "freight","promotionDiscount", "couponDiscount", "totalAmount", "point",
							"amountPaid", "orderStatus", "shippingStatus", "%shippings%", "consignee", "areaName", "address", "zipCode", "phone", "invoiceTitle", "tax", "paymentMethodName", "shippingMethodName",
							"createDate", "%payments%", "%orderItems%", "quantity"};
		String[] contents = new String[4];
		contents[0] = "订单状态:";
		contents[1] = "unconfirmed：未确认, confirmed：已确认, completed：已完成, cancelled：已取消";
		contents[2] = "配送状态:";
		contents[3] = "unshipped：未发货, partialShipment：部分发货, shipped：已发货, partialReturns：部分退货, returned：已退货";
		ExcelView excelView = new ExcelView(filename, sheetName,properties,titles,null,null,orderList,contents);
		return new ModelAndView(excelView,model);
	}



	/**
	 * 代客订单
	 * @param quote
	 * @param regNo
	 * @param type
	 * @param companyId
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/valetList", method = RequestMethod.GET)
	public String valetList(Quote quote,String regNo, String type, Long companyId, Pageable pageable, ModelMap model){
		model.addAttribute("companys",companyService.findAll());
		model.addAttribute("planeTypes",planeTypeService.findAll());
		model.addAttribute("companyId",companyId);
		model.addAttribute("regNo",regNo);
		model.addAttribute("type",type);
		model.addAttribute("sexs", Customer.Sex.values());
		model.addAttribute("identityTypes", Customer.IdentityType.values());
		List<QuoteAirline> quoteAirlineList = quote.getQuoteAirlineList();
		// 航段的最大座位数
		int passengerNum = 0;
		for(QuoteAirline quoteAirline : quoteAirlineList){
			quoteAirline.setDeparture(airportService.find(quoteAirline.getDepartureId()));
			quoteAirline.setDestination(airportService.find(quoteAirline.getDestinationId()));
			if(passengerNum < quoteAirline.getPassengerNum()){
				passengerNum = quoteAirline.getPassengerNum();
			}
		}
		Company company = companyService.find(companyId);
		if(quote.getQuoteAirlineList().size() == 0){
			model.addAttribute("initial", true);
		} else {
			quote.setDepartureId(quoteAirlineList.get(0).getDepartureId());
			quote.setDeparture(quoteAirlineList.get(0).getDeparture());
			quote.setDestinationId(quoteAirlineList.get(quoteAirlineList.size()-1).getDestinationId());
			quote.setDestination(quoteAirlineList.get(quoteAirlineList.size()-1).getDestination());
			quote.setTakeoffTime(quoteAirlineList.get(0).getTakeoffTime());
			model.addAttribute("quote", quote);

			// 调机的情况
			if(quote.getPlanType() == Quote.PlanType.transfer){
				Airport departure = quoteAirlineList.size()>0?quoteAirlineList.get(0).getDeparture():null;
				Airport destination = quoteAirlineList.size()>0?quoteAirlineList.get(0).getDestination():null;
				Date date = quoteAirlineList.size()>0?quoteAirlineList.get(0).getTakeoffTime():null;
				List<Filter> filterList = new ArrayList<Filter>();
				if(departure != null){
					Filter filter = new Filter();
					filter.setProperty("departureId");
					filter.setValue(departure);
					filter.setOperator(Filter.Operator.eq);
					filterList.add(filter);
				} else {
					return "/admin/order/valetList";
				}
				if(destination != null){
					Filter filter = new Filter();
					filter.setProperty("destinationId");
					filter.setValue(destination);
					filter.setOperator(Filter.Operator.eq);
					filterList.add(filter);
				} else {
					return "/admin/order/valetList";
				}
				// 起飞时间
				if(date != null){
					SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
					try {
						date = format.parse(format.format(date));
					} catch (ParseException e) {
						date = new Date();
					}
					Filter filter = new Filter();
					filter.setProperty("takeoffTime");
					filter.setValue(date);
					filter.setOperator(Filter.Operator.eq);
					filterList.add(filter);
				} else {
					return "/admin/order/valetList";
				}
				// 航空公司
				if(company != null){
					Filter filter = new Filter();
					filter.setProperty("company");
					filter.setValue(company);
					filter.setOperator(Filter.Operator.eq);
					filterList.add(filter);
				}
				// 飞机注册号
				if(StringUtils.isNotEmpty(regNo)){
					Filter filter = new Filter();
					filter.setProperty("regNo");
					filter.setValue(regNo);
					filter.setOperator(Filter.Operator.like);
					filterList.add(filter);
				}
				// 飞机型号
				if(StringUtils.isNotEmpty(type)){
					Filter filter = new Filter();
					filter.setProperty("airplaneType");
					filter.setValue(type);
					filter.setOperator(Filter.Operator.eq);
					filterList.add(filter);
				}
				// 状态为用户可见
				Filter status_filter = new Filter();
				status_filter.setProperty("status");
				status_filter.setValue(FlightPlan.FlightPlanStatus.usershow);
				status_filter.setOperator(Filter.Operator.eq);
				filterList.add(status_filter);

				// 计划类型:调机
				Filter type_filter = new Filter();
				type_filter.setProperty("type");
				type_filter.setValue(FlightPlan.Type.tuning);
				type_filter.setOperator(Filter.Operator.eq);
				filterList.add(type_filter);

				pageable.setFilters(filterList);
				pageable.setOrderProperty("specialprice");
				pageable.setOrderDirection(com.hxlm.health.web.Order.Direction.desc);
				Page<FlightPlan> flightPlanPage = flightPlanService.findPage(pageable);
				Iterator<FlightPlan> iterator = flightPlanPage.getContent().iterator();
				while (iterator.hasNext()) {
					FlightPlan flightPlan = iterator.next();
					if(flightPlan.getAirplane() == null || flightPlan.getAirplane().getCapacity() < passengerNum){
						iterator.remove();
					}
				}
				model.addAttribute("flightPlanPage", flightPlanPage);
				pageable.setPageSize(20);
				Page<Quote> page = new Page<Quote>(new ArrayList<Quote>(),20,pageable);
				model.addAttribute("page", page);
				return "/admin/order/valetList";
			} else {
				List<Quote> quotes = new ArrayList<Quote>();
				// 飞机列表
				List<Airplane> airplaneList = airplaneService.findEffectiveList(quoteAirlineList,type,regNo,company,passengerNum);
				for (Airplane airplane : airplaneList){
					Quote tempQuote = new Quote();
					tempQuote.setPlanType(quote.getPlanType());
					tempQuote.setDepartureId(quote.getDepartureId());
					tempQuote.setDeparture(quote.getDeparture());
					tempQuote.setDestinationId(quote.getDestinationId());
					tempQuote.setDestination(quote.getDestination());
					tempQuote.setQuoteAirlineList(quoteAirlineList);
					tempQuote.setTakeoffTime(quote.getTakeoffTime());
					tempQuote.setRegNo(airplane.getRegNo());
					tempQuote.setType(airplane.getType());
					tempQuote.setAirplane(airplane);
					tempQuote.setAirplaneId(airplane.getId());
					tempQuote.setStatus(Quote.Status.unconfirmed);

					tempQuote = quoteService.price(tempQuote);
					quotes.add(tempQuote);
				}

				Comparator<Quote> quoteComparator = new Comparator<Quote>() {
					@Override
					public int compare(Quote quote1, Quote quote2) {
						return quote1.getTotalAmount().compareTo(quote2.getTotalAmount());
					}
				};
				Collections.sort(quotes, quoteComparator);
				pageable.setPageSize(20);
				Page<Quote> page = new Page<Quote>(new ArrayList<Quote>(),quotes.size(),pageable);
				model.addAttribute("page", page);
				int pageNum = pageable.getPageNumber();
				int totalPage = quotes.size()/20 + 1;
				if(quotes.size()%20==0){
					totalPage = totalPage - 1;
				}
				if(pageNum > totalPage){
					pageNum = totalPage;
				}
				if(pageNum > 1){
					int i = 0;
					Iterator<Quote> iterator = quotes.iterator();
					while (iterator.hasNext()){
						if(i>(pageNum-1)*20){
							break;
						}
						iterator.next();
						iterator.remove();
						i++;
					}
				}
				if(pageNum < totalPage){
					List<Quote> quoteList = new ArrayList<Quote>();
					for(int n=0; n<20;n++){
						quoteList.add(quotes.get(n));
					}
					model.addAttribute("quotes", quoteList);
				} else {
					model.addAttribute("quotes", quotes);
				}
				return "/admin/order/valetList";
			}

		}
		pageable.setPageSize(20);
		Page<Quote> page = new Page<Quote>(new ArrayList<Quote>(),20,pageable);
		model.addAttribute("page", page);
		return "/admin/order/valetList";
	}

	/**
	 * 创建代客订单
	 * @param quote
	 * @param flightPlanId
	 * @param customerId
	 * @return
	 */
	@RequestMapping(value = "/createValetOrder", method = RequestMethod.GET)
	public String createValetOrder(Quote quote, Long flightPlanId, Long customerId){
		Customer customer = quote.getCustomer();
		if(customerId == null){
			customer = customerService.saveAndGet(customer);
		} else {
			customer = customerService.find(customerId);
		}
		Admin admin = adminService.getCurrent();
		List<QuoteAirline> quoteAirlineList = quote.getQuoteAirlineList();
		// 航段的最大座位数
		int passengerNum = 0;
		for(QuoteAirline quoteAirline : quoteAirlineList){
			quoteAirline.setDeparture(airportService.find(quoteAirline.getDepartureId()));
			quoteAirline.setDestination(airportService.find(quoteAirline.getDestinationId()));
			if(passengerNum < quoteAirline.getPassengerNum()){
				passengerNum = quoteAirline.getPassengerNum();
			}
		}
		if(quote.getQuoteAirlineList().size() > 0){
			quote.setDepartureId(quoteAirlineList.get(0).getDepartureId());
			quote.setDeparture(quoteAirlineList.get(0).getDeparture());
			quote.setDestinationId(quoteAirlineList.get(quoteAirlineList.size()-1).getDestinationId());
			quote.setDestination(quoteAirlineList.get(quoteAirlineList.size()-1).getDestination());
			quote.setTakeoffTime(quoteAirlineList.get(0).getTakeoffTime());
		}
		// 订单航段
		List<OrderAirline> orderAirlineList = new ArrayList<OrderAirline>();
		Order order = new Order();
		// 调机的情况
		if(flightPlanId != null){
			FlightPlan flightPlan = flightPlanService.find(flightPlanId);
			// 判断是否可预定

			Airplane airplane = flightPlan.getAirplane();
			OrderAirline orderAirline = new OrderAirline();
			orderAirline.setDepartureId(flightPlan.getDepartureId().getId());
			orderAirline.setDeparture(flightPlan.getDeparture());
			orderAirline.setDestinationId(flightPlan.getDestinationId().getId());
			orderAirline.setDestination(flightPlan.getDestination());
			orderAirline.setTakeoffTime(quote.getTakeoffTime());
			orderAirline.setTimeCost(Float.valueOf(flightPlan.getTimeCost().toString()));
			orderAirline.setPassengerNum(passengerNum);
			orderAirline.setTripOrder(order);
			orderAirlineList.add(orderAirline);
			order.setOrderAirlines(orderAirlineList);
			order = orderService.buildOrder(airplane,customer,orderAirlineList,flightPlan.getSpecialprice(),true,flightPlanId, admin);
			orderAirline.setTripOrder(order);
			orderAirlineService.save(orderAirline);
			flightPlan.setStatus(FlightPlan.FlightPlanStatus.reserve);
			flightPlanService.update(flightPlan);
		} else {

			Quote selectQuote = quote.getQuotes().size() > 0 ? quote.getQuotes().get(0):null;
			Airplane airplane = airplaneService.find(selectQuote.getAirplaneId());
			for(QuoteAirline quoteAirline : selectQuote.getQuoteAirlineList()){
				quoteAirline.setDeparture(airportService.find(quoteAirline.getDepartureId()));
				quoteAirline.setDestination(airportService.find(quoteAirline.getDestinationId()));
			}
			for (QuoteAirline quoteAirline:selectQuote.getQuoteAirlineList()){
				if(quoteAirline.getIsSpecial() != null && quoteAirline.getIsSpecial() == true){
					continue;
				}
				OrderAirline orderAirline = new OrderAirline();
				orderAirline.setDepartureId(quoteAirline.getDepartureId());
				orderAirline.setDeparture(quoteAirline.getDeparture().getName());
				orderAirline.setDestinationId(quoteAirline.getDestinationId());
				orderAirline.setDestination(quoteAirline.getDestination().getName());
				orderAirline.setTakeoffTime(quoteAirline.getTakeoffTime());
				orderAirline.setTimeCost(Float.valueOf(quoteAirline.getTimeCost().toString()));
				orderAirline.setPassengerNum(quoteAirline.getPassengerNum());
				orderAirline.setTripOrder(order);
				orderAirlineList.add(orderAirline);
			}
			order.setOrderAirlines(orderAirlineList);
			order = orderService.buildOrder(airplane,customer,orderAirlineList,selectQuote.getTotalAmount(),false,flightPlanId, admin);
			for (OrderAirline orderAirline:orderAirlineList){
				orderAirline.setTripOrder(order);
				orderAirlineService.save(orderAirline);
			}
		}
		return "redirect:valetList.jhtml";
	}


}