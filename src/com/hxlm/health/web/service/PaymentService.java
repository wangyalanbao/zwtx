/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.Payment;
import com.hxlm.health.web.plugin.PaymentPlugin;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service - 收款单
 * 
 * 
 * 
 */
public interface PaymentService extends BaseService<Payment, Long> {

	/**
	 * 根据编号查找收款单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 收款单，若不存在则返回null
	 */
	Payment findBySn(String sn);

	/**
	 * 支付处理
	 * 
	 * @param payment
	 *            收款单
	 */
	void handle(Payment payment);

	Payment createPayment(String sn, Payment.Type type, Payment.Method method, Payment.Status status, String paymentMethod, PaymentPlugin paymentPlugin, BigDecimal fee, BigDecimal amount, Member member, Order order);

	void savePayments(List<Payment> payments, Boolean isAddRecord);

	void saveAliPayer(String buyer_email, String buyer_id, String trade_no, String sn);

	void savePayTradeNo(String trade_no, String sn);

	void updatePaymentStatus(Payment payment, Payment.Status status);


}