package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.OrderCateringDao;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.OrderAirline;
import com.hxlm.health.web.entity.OrderCatering;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;

/**
 * Created by guofeng on 2016/1/13.
 * DaoImpl - 订单行李和配餐要求
 */
@Repository("orderCateringDaoImpl")
public class OrderCateringDaoImpl extends BaseDaoImpl<OrderCatering,Long> implements OrderCateringDao {

    // 修改
    public void updateCatering(Long id,String luggageRequest,String drinkRequest,String foodRequest,String otherRequest){
        String jpql="update OrderCatering oc set oc.luggageRequest = :luggageRequest ,oc.drinkRequest = :drinkRequest ,oc.foodRequest = :foodRequest ,oc.otherRequest = :otherRequest where oc.id = :id";
        entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("luggageRequest", luggageRequest).setParameter("drinkRequest", drinkRequest).setParameter("foodRequest",foodRequest).setParameter("otherRequest",otherRequest).setParameter("id",id).executeUpdate();
    }
}
