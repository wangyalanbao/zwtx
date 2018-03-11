package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.FlightPlanDao;
import com.hxlm.health.web.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by dengyang on 15/12/18.
 */

@Repository("flightPlanDaoImpl")
public class FlightPlanDaoImpl extends BaseDaoImpl<FlightPlan,Long> implements FlightPlanDao {

    public Page<FlightPlan> findPage(PlaneType typeId,PlaneBrand planeBrandId,Company company,Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FlightPlan> criteriaQuery = criteriaBuilder.createQuery(FlightPlan.class);
        Root<FlightPlan> root = criteriaQuery.from(FlightPlan.class);
        Join<FlightPlan,Airplane> airplaneJoin = root.join("airplane");
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();

        if (typeId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(airplaneJoin.get("typeId"), typeId));
        }
        if (planeBrandId != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(airplaneJoin.get("planeBrandId"), planeBrandId));
        }
        if (company != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("company"), company));
        }
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(airplaneJoin.<Long>get("id"), root.<Airplane>get("airplane")));
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    /**
     * 获取可以下单的调机航班计划
     * @param departure
     * @param destination
     * @param capacity
     * @param pageable
     * @return
     */
    public Page<FlightPlan> findPage(Airport departure, Airport destination, Integer capacity, Pageable pageable) {
        if(departure == null|| destination == null || capacity == null){
            return new Page<FlightPlan>(Collections.<FlightPlan>emptyList(),0,pageable);
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FlightPlan> criteriaQuery = criteriaBuilder.createQuery(FlightPlan.class);
        Root<FlightPlan> root = criteriaQuery.from(FlightPlan.class);
        Join<FlightPlan,Airplane> airplaneJoin = root.join("airplane");
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(airplaneJoin.<Long>get("id"), root.<Airplane>get("airplane")));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(airplaneJoin.<Integer>get("capacity"), capacity));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("departureId"), departure));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("destinationId"), destination));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), FlightPlan.Type.tuning));
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isReal"), false));
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    public void updateDulfill(Long id,Airport departureId,Airport destinationId,Date actualTakeoffTime,Date actualLandingTime){
        String jpql="update FlightPlan flightPlan set flightPlan.departureId = :departureId ,flightPlan.destinationId = :destinationId,flightPlan.actualTakeoffTime = :actualTakeoffTime,flightPlan.actualLandingTime = :actualLandingTime where flightPlan.id = :id";
        entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("departureId", departureId).setParameter("destinationId",destinationId).setParameter("actualTakeoffTime",actualTakeoffTime).setParameter("actualLandingTime",actualLandingTime).setParameter("id",id).executeUpdate();
    }

    //飞机查询任务
    public List<FlightPlan> findByAirplane(Airplane airplane) {
        try {
            String jpql = "select flightPlan from FlightPlan flightPlan where flightPlan.airplane = :airplane";
            return entityManager.createQuery(jpql, FlightPlan.class).setFlushMode(FlushModeType.COMMIT).setParameter("airplane", airplane).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    // 修改调机是否真实
    public void updateIsReal(Long id,Boolean isReal){
        String jpql="update FlightPlan flightPlan set flightPlan.isReal = :isReal  where flightPlan.id = :id";
        entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("isReal", isReal).setParameter("id",id).executeUpdate();
    }

    //查询相同结束时间
    public List<FlightPlan> findByTuningDate(String tuningDate,Airplane airplane) {
        try {
            String jpql = "select flightPlan from FlightPlan flightPlan where flightPlan.tuningDate = :tuningDate and flightPlan.airplane = :airplane";
            return entityManager.createQuery(jpql, FlightPlan.class).setFlushMode(FlushModeType.COMMIT).setParameter("tuningDate", tuningDate).setParameter("airplane",airplane).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    //SELECT takeoff_time FROM `lm_flight_plan` WHERE tuning_date = '**' GROUP BY takeoff_time;
    public List<Date> findByTakeoff(String tuningDate) {
        try {
            String jpql = "select flightPlan.takeoffTime from FlightPlan flightPlan where flightPlan.tuningDate = :tuningDate group by flightPlan.takeoffTime";
            return entityManager.createQuery(jpql, Date.class).setFlushMode(FlushModeType.COMMIT).setParameter("tuningDate", tuningDate).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
