/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.Refunds;

import java.util.List;

/**
 * Service - 退款单
 * 
 * 
 * 
 */
public interface RefundsService extends BaseService<Refunds, Long> {

    Refunds findBySn(String sn);

    List<Refunds> createRefunds(Order order);
}