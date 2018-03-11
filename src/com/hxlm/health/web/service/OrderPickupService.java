package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.OrderPickup;

/**
 * Created by guofeng on 2016/1/13.
 * Service--订单接送人
 */
public interface OrderPickupService extends BaseService<OrderPickup,Long> {

    void updatePickup(Long id,String site,String name,String contact,String carNo);
}
