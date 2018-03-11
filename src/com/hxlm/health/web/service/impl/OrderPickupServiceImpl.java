package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.OrderPickupDao;
import com.hxlm.health.web.entity.OrderPickup;
import com.hxlm.health.web.service.OrderPickupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by guofeng on 2016/1/13.
 * ServiceImpl--订单接送人
 */
@Service("orderPickupServiceImpl")
public class OrderPickupServiceImpl extends BaseServiceImpl<OrderPickup,Long> implements OrderPickupService {

    @Resource(name = "orderPickupDaoImpl")
    private OrderPickupDao orderPickupDao;
    @Resource(name = "orderPickupDaoImpl")
    public void setBaseDao(OrderPickupDao orderPickupDao) {super.setBaseDao(orderPickupDao);}

    @Transactional(readOnly = true)
    public void updatePickup(Long id,String site,String name,String contact,String carNo){
        orderPickupDao.updatePickup(id, site, name, contact, carNo);
    }
}
