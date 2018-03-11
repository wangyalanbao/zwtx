/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.OrderAirlineDao;
import com.hxlm.health.web.entity.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Dao - 航段
 * 
 * 
 * 
 */
@Repository("orderAirlineDaoImpl")
public class OrderAirlineDaoImpl extends BaseDaoImpl<OrderAirline, Long> implements OrderAirlineDao {

    public Page<OrderAirline> findPage(Order order,Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderAirline> criteriaQuery = criteriaBuilder.createQuery(OrderAirline.class);
        Root<OrderAirline> root = criteriaQuery.from(OrderAirline.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();

        if (order != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("tripOrder"), order));
        }
        criteriaQuery.where(restrictions);

        return super.findPage(criteriaQuery, pageable);
    }
}