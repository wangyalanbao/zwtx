package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.PlaneTypeDao;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.entity.PlaneType;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by guofeng on 2015/12/11.
 * daoImpl--飞机型号
 */
@Repository("planeTypeDaoImpl")
public class PlaneTypeDaoImpl extends BaseDaoImpl<PlaneType ,Long> implements PlaneTypeDao {

    public Page<PlaneType> findPage(PlaneBrand planeBrand, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PlaneType> criteriaQuery = criteriaBuilder.createQuery(PlaneType.class);
        Root<PlaneType> root = criteriaQuery.from(PlaneType.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (planeBrand != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brandId"), planeBrand));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }
}
