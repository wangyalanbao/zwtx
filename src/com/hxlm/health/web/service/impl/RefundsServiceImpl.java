/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import javax.annotation.Resource;

import com.hxlm.health.web.dao.SnDao;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.Payment;
import com.hxlm.health.web.entity.Sn;
import com.hxlm.health.web.service.RefundsService;
import com.hxlm.health.web.dao.RefundsDao;
import com.hxlm.health.web.entity.Refunds;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service - 退款单
 * 
 * 
 * 
 */
@Service("refundsServiceImpl")
public class RefundsServiceImpl extends BaseServiceImpl<Refunds, Long> implements RefundsService {

	@Resource(name = "refundsDaoImpl")
	private RefundsDao refundsDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	@Resource(name = "refundsDaoImpl")
	public void setBaseDao(RefundsDao refundsDao) {
		super.setBaseDao(refundsDao);
	}

	@Transactional(readOnly = true)
	public Refunds findBySn(String sn) {
		return refundsDao.findBySn(sn);
	}

	@Transactional(readOnly = true)
	public List<Refunds> createRefunds(Order order){
		List<Refunds> refundsList = new ArrayList<Refunds>();
		Iterator<Payment> iterator = order.getPayments().iterator();
		while(iterator.hasNext()){
			Refunds refunds = new Refunds();
			Payment payment = iterator.next();
			if(payment.getStatus() == Payment.Status.success){
				if(payment.getMethod() == Payment.Method.online){
					refunds.setMethod(Refunds.Method.online);
				}else if(payment.getMethod() == Payment.Method.offline){
					refunds.setMethod(Refunds.Method.offline);
				}else if(payment.getMethod() == Payment.Method.deposit){
					refunds.setMethod(Refunds.Method.deposit);
				}else if(payment.getMethod() == Payment.Method.cashcard){
					refunds.setMethod(Refunds.Method.cashcard);
				}
				refunds.setOrder(order);
				refunds.setStatus(Refunds.Status.noRefund);
				refunds.setSn(snDao.generate(Sn.Type.refunds));
				refunds.setAmount(payment.getAmount());
				refunds.setPaymentMethod(payment.getPaymentMethod());
				refunds.setPayee(payment.getPayer());
				refunds.setBank(payment.getBank());
				refunds.setAccount(payment.getAccount());
				refunds.setBuyerId(payment.getBuyerId());
				refunds.setBuyerEmail(payment.getBuyerEmail());
				refunds.setAlipayTradeNo(payment.getAlipayTradeNo());
				refunds.setOperator("");
				refunds.setPayment(payment);
				refunds = refundsDao.saveAndReturn(refunds);
				refundsList.add(refunds);
			}
		}
		return refundsList;
	}

}