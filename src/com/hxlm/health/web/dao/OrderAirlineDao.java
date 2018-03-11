/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.OrderAirline;

/**
 * Dao - 航段
 * 
 * 
 * 
 */
public interface OrderAirlineDao extends BaseDao<OrderAirline, Long> {


    Page<OrderAirline> findPage(Order order,Pageable pageable);
}