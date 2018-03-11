/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import javax.annotation.Resource;

import com.hxlm.health.web.dao.PaymentMethodDao;
import com.hxlm.health.web.entity.PaymentMethod;
import com.hxlm.health.web.service.PaymentMethodService;

import org.springframework.stereotype.Service;

/**
 * Service - 支付方式
 * 
 * 
 * 
 */
@Service("paymentMethodServiceImpl")
public class PaymentMethodServiceImpl extends BaseServiceImpl<PaymentMethod, Long> implements PaymentMethodService {

	@Resource(name = "paymentMethodDaoImpl")
	public void setBaseDao(PaymentMethodDao paymentMethodDao) {
		super.setBaseDao(paymentMethodDao);
	}

}