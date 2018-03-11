/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.entity.Refunds;
import com.hxlm.health.web.dao.RefundsDao;

import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

/**
 * Dao - 退款单
 * 
 * 
 * 
 */
@Repository("refundsDaoImpl")
public class RefundsDaoImpl extends BaseDaoImpl<Refunds, Long> implements RefundsDao {

    public Refunds findBySn(String sn) {
        if (sn == null) {
            return null;
        }
        String jpql = "select refunds from Refunds refunds where lower(refunds.sn) = lower(:sn)";
        try {
            return entityManager.createQuery(jpql, Refunds.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}