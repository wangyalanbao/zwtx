package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.OrderCateringDao;
import com.hxlm.health.web.entity.OrderCatering;
import com.hxlm.health.web.service.OrderCateringService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2016/1/13.
 * ServiceImpl--订单行李和配餐要求
 */
@Service("orderCateringServiceImpl")
public class OrderCateringServiceImpl extends BaseServiceImpl<OrderCatering,Long> implements OrderCateringService {

    @Resource(name = "orderCateringDaoImpl")
    private OrderCateringDao orderCateringDao;
    @Resource(name = "orderCateringDaoImpl")
    public void setBaseDao(OrderCateringDao orderCateringDao) {super.setBaseDao(orderCateringDao);}

    public void updateCatering(Long id,String luggageRequest,String drinkRequest,String foodRequest,String otherRequest){
         orderCateringDao.updateCatering(id, luggageRequest, drinkRequest, foodRequest, otherRequest);
    }
}
