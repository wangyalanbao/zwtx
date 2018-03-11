package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.OrderAirline;
import com.hxlm.health.web.entity.OrderPassenger;

import java.util.List;

/**
 * Created by guofeng on 2016/1/12.
 * service--订单乘客
 */
public interface OrderPassengerService extends BaseService<OrderPassenger,Long> {

    //证件号是否存在
    boolean idCardNoExists(String idCardNo);

    //	模糊查询
    List<OrderPassenger> searth(String keyword,Integer count);

    // 修改乘客的所属订单
    void updateOrder(Long id,OrderAirline orderAirline,Order order);
}
