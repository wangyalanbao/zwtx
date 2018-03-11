package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.Setting;
import com.hxlm.health.web.dao.AirlineDao;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.util.SettingUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by guofeng on 2015/12/14.
 * daoImpl--行线
 */
@Repository("airlineDaoImpl")
public class AirlineDaoImpl extends BaseDaoImpl<Airline,Long> implements AirlineDao {

    public Page<Airline> findPage(Airport departureId,Airport destinationId,PlaneBrand brandId,PlaneType typeId,Integer month, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Airline> criteriaQuery = criteriaBuilder.createQuery(Airline.class);
        Root<Airline> root = criteriaQuery.from(Airline.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (departureId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("departureId"), departureId));
        }
        if (destinationId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("destinationId"), destinationId));
        }
        if (brandId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brandId"), brandId));
        }
        if (typeId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("typeId"), typeId));
        }
        if (month != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("month"), month));
        }

        criteriaQuery.where(restrictions);

        return super.findPage(criteriaQuery, pageable);
    }

    public List<Airline> findList(Airport departureId,Airport destinationId,PlaneBrand brandId,PlaneType typeId,Integer month) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Airline> criteriaQuery = criteriaBuilder.createQuery(Airline.class);
        Root<Airline> root = criteriaQuery.from(Airline.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (departureId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("departureId"), departureId));
        }
        if (destinationId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("destinationId"), destinationId));
        }
        if (brandId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brandId"), brandId));
        }
        if (typeId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("typeId"), typeId));
        }
        if (month != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("month"), month));
        }

        criteriaQuery.where(restrictions);

        return super.findList(criteriaQuery,null,null,null,null);
    }
}
