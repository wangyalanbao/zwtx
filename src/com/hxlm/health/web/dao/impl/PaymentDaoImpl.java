/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.PaymentDao;
import com.hxlm.health.web.entity.Payment;

import org.springframework.stereotype.Repository;

/**
 * Dao - 收款单
 * 
 * 
 * 
 */
@Repository("paymentDaoImpl")
public class PaymentDaoImpl extends BaseDaoImpl<Payment, Long> implements PaymentDao {

	public Payment findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select payment from Payment payment where lower(payment.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Payment.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	public void saveAliPayer(String buyer_email, String buyer_id, String trade_no, String sn) {
		String jpql = "update Payment payment set payment.buyerEmail = :buyerEmail,payment.buyerId = :buyerId,payment.alipayTradeNo = :alipayTradeNo where lower(payment.sn) = lower(:sn)";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("buyerEmail", buyer_email).setParameter("buyerId", buyer_id).setParameter("alipayTradeNo", trade_no).setParameter("sn", sn).executeUpdate();
	}

	public void savePayTradeNo(String trade_no, String sn) {
		String jpql = "update Payment payment set payment.alipayTradeNo = :alipayTradeNo where lower(payment.sn) = lower(:sn)";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("alipayTradeNo", trade_no).setParameter("sn", sn).executeUpdate();
	}

	public void updatePaymentStatus(String sn, Payment.Status status) {
		String jpql = "update Payment payment set payment.status = :status where lower(payment.sn) = lower(:sn)";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("status", status).setParameter("sn", sn).executeUpdate();
	}

}