/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import javax.annotation.Resource;

import com.hxlm.health.web.dao.ShippingMethodDao;
import com.hxlm.health.web.entity.Area;
import com.hxlm.health.web.entity.ShippingMethod;
import com.hxlm.health.web.service.ShippingMethodService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 配送方式
 * 
 * 
 * 
 */
@Service("shippingMethodServiceImpl")
public class ShippingMethodServiceImpl extends BaseServiceImpl<ShippingMethod, Long> implements ShippingMethodService {

	@Resource(name = "shippingMethodDaoImpl")
	private ShippingMethodDao shippingMethodDao;

	@Resource(name = "shippingMethodDaoImpl")
	public void setBaseDao(ShippingMethodDao shippingMethodDao) {
		super.setBaseDao(shippingMethodDao);
	}

	@Transactional(readOnly = true)
	public ShippingMethod findByReceiveArea(Area receiveArea) {
		if (receiveArea == null) {
			return null;
		}
		ShippingMethod shippingMethod = shippingMethodDao.findByReceiveArea(receiveArea);
		if (shippingMethod == null) {
			shippingMethod = shippingMethodDao.find(3L);
		}
		return shippingMethod;
	}
}