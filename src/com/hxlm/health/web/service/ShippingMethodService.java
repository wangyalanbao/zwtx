/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.Area;
import com.hxlm.health.web.entity.ShippingMethod;

/**
 * Service - 配送方式
 * 
 * 
 * 
 */
public interface ShippingMethodService extends BaseService<ShippingMethod, Long> {

    ShippingMethod findByReceiveArea(Area receiveArea);

}