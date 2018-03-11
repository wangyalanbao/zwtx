package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.OrderCatering;

/**
 * Created by guofeng on 2016/1/13.
 * Dao - 订单行李和配餐要求
 */
public interface OrderCateringDao extends BaseDao<OrderCatering,Long> {

    void updateCatering(Long id,String luggageRequest,String drinkRequest,String foodRequest,String otherRequest);
}
