/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.OrderItemDao;
import com.hxlm.health.web.entity.OrderItem;

import org.springframework.stereotype.Repository;

/**
 * Dao - 订单项
 * 
 * 
 * 
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

}