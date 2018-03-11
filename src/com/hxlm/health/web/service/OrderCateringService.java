package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.OrderCatering;

/**
 * Created by guofeng on 2016/1/13.
 * Service --订单行李和配餐要求
 */
public interface OrderCateringService extends BaseService<OrderCatering,Long> {

    void updateCatering(Long id,String luggageRequest,String drinkRequest,String foodRequest,String otherRequest);
}
