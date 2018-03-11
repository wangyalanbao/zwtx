/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Payment;

/**
 * Dao - 收款单
 * 
 * 
 * 
 */
public interface PaymentDao extends BaseDao<Payment, Long> {

	/**
	 * 根据编号查找收款单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 收款单，若不存在则返回null
	 */
	Payment findBySn(String sn);

	void saveAliPayer(String buyer_email, String buyer_id, String trade_no, String sn);

	void savePayTradeNo(String trade_no, String sn);

	void updatePaymentStatus(String sn, Payment.Status status);
	//现金卡查询收款单列表
}