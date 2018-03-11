/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.Area;
import com.hxlm.health.web.entity.ShippingMethod;

/**
 * Dao - 配送方式
 * 
 * 
 * 
 */
public interface ShippingMethodDao extends BaseDao<ShippingMethod, Long> {

    ShippingMethod findByReceiveArea(Area receiveArea);

}