package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.OrderPickup;

/**
 * Created by guofeng on 2016/1/13.
 * DAO--订单接送人
 */
public interface OrderPickupDao extends BaseDao<OrderPickup,Long>{

    void updatePickup(Long id,String site,String name,String contact,String carNo);
}
