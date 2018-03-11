package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.OrderPassengerDao;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.OrderAirline;
import com.hxlm.health.web.entity.OrderPassenger;
import com.hxlm.health.web.service.OrderPassengerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guofeng on 2016/1/12.
 * serviceImpl--订单乘客
 */
@Service("orderPassengerServiceImpl")
public class OrderPassengerServiceImpl extends BaseServiceImpl<OrderPassenger,Long> implements OrderPassengerService {
    @Resource(name = "orderPassengerDaoImpl")
    private OrderPassengerDao orderPassengerDao;
    @Resource(name = "orderPassengerDaoImpl")
    public void setBaseDao(OrderPassengerDao orderPassengerDao){
        super.setBaseDao(orderPassengerDao);
    }

    //证件号是否存在
    @Transactional(readOnly = true)
    public boolean idCardNoExists(String idCardNo) {
        return orderPassengerDao.idCardNoExists(idCardNo);
    }

    //	模糊查询
    @Transactional(readOnly = true)
    public List<OrderPassenger> searth(String keyword,Integer count){
        return orderPassengerDao.searth(keyword,count);
    }

    // 修改乘客的所属订单
    public void updateOrder(Long id,OrderAirline orderAirline,Order order){
        orderPassengerDao.updateOrder(id,orderAirline,order);
    }
}
