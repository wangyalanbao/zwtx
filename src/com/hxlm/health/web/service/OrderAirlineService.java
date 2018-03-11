/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.OrderAirline;

/**
 * Service - 航段
 * 
 * 
 * 
 */
public interface OrderAirlineService extends BaseService<OrderAirline, Long> {

    Page<OrderAirline> findPage(Order order,Pageable pageable);
}