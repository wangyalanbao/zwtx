/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.OrderAirlineDao;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.OrderAirline;
import com.hxlm.health.web.service.OrderAirlineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Service - 航段
 * 
 * 
 * 
 */
@Service("orderAirlineServiceImpl")
public class OrderAirlineServiceImpl extends BaseServiceImpl<OrderAirline, Long> implements OrderAirlineService {

	@Resource(name = "orderAirlineDaoImpl")
	private OrderAirlineDao orderAirlineDao;
	@Resource(name = "orderAirlineDaoImpl")
	public void setBaseDao(OrderAirlineDao orderAirlineDao) {
		super.setBaseDao(orderAirlineDao);
	}

	@Transactional(readOnly = true)
	public Page<OrderAirline> findPage(Order order,Pageable pageable){
		return orderAirlineDao.findPage(order,pageable);
	}

}