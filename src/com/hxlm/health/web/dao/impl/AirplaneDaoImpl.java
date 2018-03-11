package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.AirplaneDao;
import com.hxlm.health.web.entity.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

/**
 * Created by guofeng on 2015/12/14.
 * DaoImpl--飞机
 */
@Repository("airplaneDaoImpl")
public class AirplaneDaoImpl extends BaseDaoImpl<Airplane,Long> implements AirplaneDao {

    public Page<Airplane> findPage(PlaneType typeId,PlaneBrand planeBrandId,Company company,Airport airportId, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Airplane> criteriaQuery = criteriaBuilder.createQuery(Airplane.class);
        Root<Airplane> root = criteriaQuery.from(Airplane.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();

        if (typeId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("typeId"), typeId));
        }
        if (planeBrandId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brandId"), planeBrandId));
        }
        if (company != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("company"), company));
        }
        if (airportId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("airportId"), airportId));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    /**
     * 查询飞机列表
     * @param type
     * @param regNo
     * @param company
     * @param capacity
     * @return
     */
    public List<Airplane> findList(String type,String regNo,Company company,Integer capacity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Airplane> criteriaQuery = criteriaBuilder.createQuery(Airplane.class);
        Root<Airplane> root = criteriaQuery.from(Airplane.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();

        if (StringUtils.isNotEmpty(type)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (StringUtils.isNotEmpty(regNo)) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("regNo"), regNo));
        }
        if (capacity != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Integer>get("capacity"), capacity));
        }
        if (company != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("company"), company));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery,null,null,null,null);
    }

    public boolean regNoExists(String regNo) {
        if (regNo == null) {
            return false;
        }
        String jpql = "select count(*) from Airplane airplane where lower(airplane.regNo) = lower(:regNo)";
        Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("regNo", regNo).getSingleResult();
        return count > 0;
    }

    /**
     * 查询巡航最慢的飞机
     * @return
     */
    public Airplane findSlowest() {
        String jpql = "select airplane from Airplane airplane where airplane.cruisingSpeed > 0 ORDER BY airplane.cruisingSpeed";
        return entityManager.createQuery(jpql, Airplane.class).setFlushMode(FlushModeType.COMMIT).setMaxResults(1).getSingleResult();
    }

    /**
     * 根据座位数查询飞机
     *
     */
    public List<Airplane> findByCapacity(Integer passengerNum) {
        try {
            String jpql = "select airplane from Airplane airplane where airplane.capacity >= :passengerNum";
            return entityManager.createQuery(jpql, Airplane.class).setFlushMode(FlushModeType.COMMIT).setParameter("passengerNum", passengerNum).getResultList();
        } catch (NoResultException e) {
            return Collections.<Airplane> emptyList();
        }
    }

}
