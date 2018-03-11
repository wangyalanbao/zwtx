/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.ShippingMethodDao;
import com.hxlm.health.web.entity.Area;
import com.hxlm.health.web.entity.ShippingMethod;

import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;

/**
 * Dao - 配送方式
 * 
 * 
 * 
 */
@Repository("shippingMethodDaoImpl")
public class ShippingMethodDaoImpl extends BaseDaoImpl<ShippingMethod, Long> implements ShippingMethodDao {

    public ShippingMethod findByReceiveArea(Area receiveArea){
        String jpql = "select sp from ShippingMethod sp where sp.receiveArea = :receiveArea";
        try {
            return entityManager.createQuery(jpql, ShippingMethod.class).setFlushMode(FlushModeType.COMMIT).setParameter("receiveArea", receiveArea.getParent()).getSingleResult();
        } catch (Exception e) {
           return null;
        }
    }

}