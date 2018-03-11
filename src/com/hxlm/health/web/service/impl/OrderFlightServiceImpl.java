/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.OrderFlightDao;
import com.hxlm.health.web.entity.OrderFlight;
import com.hxlm.health.web.service.OrderFlightService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Service - 航班
 * 
 * 
 * 
 */
@Service("orderFlightServiceImpl")
public class OrderFlightServiceImpl extends BaseServiceImpl<OrderFlight, Long> implements OrderFlightService {

	@Resource(name = "orderFlightDaoImpl")
	public void setBaseDao(OrderFlightDao orderFlightDao) {
		super.setBaseDao(orderFlightDao);
	}

}