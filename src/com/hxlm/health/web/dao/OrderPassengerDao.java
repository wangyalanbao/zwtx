package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.OrderAirline;
import com.hxlm.health.web.entity.OrderPassenger;

import javax.persistence.FlushModeType;
import java.util.List;

/**
 * Created by guofeng on 2016/1/12.
 * Dao-订单乘客信息
 */
public interface OrderPassengerDao extends BaseDao<OrderPassenger,Long> {

    //证件号是否存在
    boolean idCardNoExists(String idCardNo);
    //	模糊查询
    List<OrderPassenger> searth(String keyword,Integer count);
    // 修改乘客的所属订单
    void updateOrder(Long id,OrderAirline orderAirline,Order order);
}
