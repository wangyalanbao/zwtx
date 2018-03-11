/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.PaymentDao;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.plugin.PaymentPlugin;
import com.hxlm.health.web.service.MemberService;
import com.hxlm.health.web.service.PaymentService;
import com.hxlm.health.web.entity.Payment.Status;
import com.hxlm.health.web.entity.Payment.Type;
import com.hxlm.health.web.service.OrderService;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 收款单
 * 
 * 
 * 
 */
@Service("paymentServiceImpl")
public class PaymentServiceImpl extends BaseServiceImpl<Payment, Long> implements PaymentService {

	@Resource(name = "paymentDaoImpl")
	private PaymentDao paymentDao;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Resource(name = "paymentDaoImpl")
	public void setBaseDao(PaymentDao paymentDao) {
		super.setBaseDao(paymentDao);
	}

	@Transactional(readOnly = true)
	public Payment findBySn(String sn) {
		return paymentDao.findBySn(sn);
	}

	public void handle(Payment payment) {
		paymentDao.refresh(payment, LockModeType.PESSIMISTIC_WRITE);
		if (payment != null && payment.getStatus() == Status.wait) {
			if (payment.getType() == Type.payment) {
				Order order = payment.getOrder();
				if (order != null) {
					orderService.payment(order, payment, null);
				}
			} else if (payment.getType() == Type.recharge) {
				Member member = payment.getMember();
				if (member != null) {
					memberService.update(member, null, payment.getEffectiveAmount(), null, null);
				}
			}
			payment.setStatus(Status.success);
			payment.setPaymentDate(new Date());
			paymentDao.merge(payment);
		}
	}

	@Transactional
	public Payment createPayment(String sn, Type type, Payment.Method method, Status status, String paymentMethod, PaymentPlugin paymentPlugin, BigDecimal fee, BigDecimal amount, Member member, Order order) {
		Payment payment = new Payment();
		// 设置订单号
		payment.setSn(sn);
		payment.setMember(member);
		payment.setOrder(order);
		// 支付类型：订单和预充值
		payment.setType(type);
		// 支付方式：在线，线下，现金卡
		payment.setMethod(method);
		// 支付状态
		payment.setStatus(status);
		// 设置支付方式：拼起来的字段
		payment.setPaymentMethod(paymentMethod);
		// 应付金额
		payment.setAmount(amount);
		// 手续费
		payment.setFee(fee);
		if (paymentPlugin != null){
			payment.setPaymentPluginId(paymentPlugin.getId());
			payment.setExpire(paymentPlugin.getTimeout() != null ? DateUtils.addMinutes(new Date(), paymentPlugin.getTimeout()) : null);
		}

		return super.saveAndReturn(payment);
	}

	@Transactional
	public void savePayments(List<Payment> payments, Boolean isAddRecord) {
		for (Payment payment : payments) {
			super.save(payment);
			if (isAddRecord) {
				// 同时增加现金卡消费记录

			}
		}

	}

	@Transactional
	public void saveAliPayer(String buyer_email, String buyer_id, String trade_no, String sn) {
		paymentDao.saveAliPayer(buyer_email, buyer_id, trade_no, sn);
	}

	@Transactional
	public void savePayTradeNo(String trade_no, String sn) {
		paymentDao.savePayTradeNo(trade_no, sn);
	}

	@Transactional
	public void updatePaymentStatus(Payment payment, Status status) {
		paymentDao.updatePaymentStatus(payment.getSn(), status);
	}


}