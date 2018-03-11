/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.Refunds;

/**
 * Dao - 退款单
 * 
 * 
 * 
 */
public interface RefundsDao extends BaseDao<Refunds, Long> {

    Refunds findBySn(String sn);

}