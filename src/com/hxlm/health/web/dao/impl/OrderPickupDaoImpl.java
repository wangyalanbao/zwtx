package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.OrderPickupDao;
import com.hxlm.health.web.entity.OrderPickup;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;

/**
 * Created by guofeng on 2016/1/13.
 * DaoImpl--订单接送人
 */
@Repository("orderPickupDaoImpl")
public class OrderPickupDaoImpl extends BaseDaoImpl<OrderPickup,Long> implements OrderPickupDao {

    // 修改
    public void updatePickup(Long id,String site,String name,String contact,String carNo){
        String jpql="update OrderPickup op set op.name = :name ,op.contact = :contact ,op.carNo = :carNo ,op.site = :site where op.id = :id";
        entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).setParameter("contact", contact).setParameter("carNo",carNo).setParameter("site",site).setParameter("id",id).executeUpdate();
    }
}
