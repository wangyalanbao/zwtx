package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.AirportDao;
import com.hxlm.health.web.entity.Airport;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by guofeng on 2015/12/14.
 * DaoImpl-- 机场
 */
@Repository("airportDaoImpl")
public class AirportDaoImpl extends BaseDaoImpl<Airport,Long> implements AirportDao {

    public Page<Airport> findPage(Boolean isVirtual, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Airport> criteriaQuery = criteriaBuilder.createQuery(Airport.class);
        Root<Airport> root = criteriaQuery.from(Airport.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();

        if (isVirtual != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isVirtual"), isVirtual));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    /**
     * 查询所有虚拟基地
     *
     */
    public List<Airport> findByIsVirtual() {
        try {
            String jpql = "select airport from Airport airport where airport.isVirtual = true";
//            String jpql = "select airport from Airport airport where  airport.isVirtual = true";
            return entityManager.createQuery(jpql, Airport.class).setFlushMode(FlushModeType.COMMIT).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 模糊查询
     *
     */
    public List<Airport> search(String key, Integer count) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Airport> criteriaQuery = criteriaBuilder.createQuery(Airport.class);
        Root<Airport> root = criteriaQuery.from(Airport.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(
                criteriaBuilder.like(root.<String>get("name"), "%" + key + "%"),
                criteriaBuilder.like(root.<String>get("code_3"), "%" + key + "%"),
                criteriaBuilder.like(root.<String>get("code_4"), "%" + key + "%"),
                criteriaBuilder.like(root.<String>get("city"), "%" + key + "%")));
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, null, null);
    }

    /**
     * 查询所有中国机场
     * SELECT *  FROM lm_airport  WHERE area = '中国' or area = 'China'
     */
    public List<Airport> findByInland() {
        String str1="中国";
        String str2="China";
        try {
            String jpql = "select airport from Airport airport where airport.area = :str1 or airport.area = :str2";
            return entityManager.createQuery(jpql, Airport.class).setFlushMode(FlushModeType.COMMIT).setParameter("str1", str1).setParameter("str2",str2).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 查询所有中国机场
     *SELECT *  FROM lm_airport  WHERE (area = '中国' or area = 'China') and ISNULL(inland_cost)
     */
    public List<Airport> findByInlandCostNull() {
        String str1="中国";
        String str2="China";
        try {
            String jpql = "select airport from Airport airport where (airport.area = :str1 or airport.area = :str2 )and airport.inlandCost is null";
            return entityManager.createQuery(jpql, Airport.class).setFlushMode(FlushModeType.COMMIT).setParameter("str1", str1).setParameter("str2",str2).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 查询所有国外机场
     * SELECT *  FROM lm_airport  WHERE area <> '中国' AND area <> 'China' group by area
     */
    public List<Airport> findByForeign() {
        String str1="中国";
        String str2="China";
        try {
            String jpql = "select airport from Airport airport where airport.area <> :str1 and airport.area <> :str2  group by airport.area";
            return entityManager.createQuery(jpql, Airport.class).setFlushMode(FlushModeType.COMMIT).setParameter("str1", str1).setParameter("str2", str2).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 查询所有国外机场
     */
    public List<Airport> findByForeignCostNull() {
        String str1="中国";
        String str2="China";
        try {
            String jpql = "select airport from Airport airport where (airport.area <> :str1 and airport.area <> :str2) and airport.inlandCost is null group by airport.area ";
            return entityManager.createQuery(jpql, Airport.class).setFlushMode(FlushModeType.COMMIT).setParameter("str1", str1).setParameter("str2", str2).getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    /**
     * 用国家名查询机场
     * @param area
     * @return
     */
    public List<Airport> findByArea(String area) {
            try {
                String jpql = "select airport from Airport airport where airport.area = :area ";
                return entityManager.createQuery(jpql, Airport.class).setFlushMode(FlushModeType.COMMIT).setParameter("area", area).getResultList();
            } catch (NoResultException e) {
                return null;
            }
        }
    /**
     * 用国家名查询机场
     * @param areas
     * @return
     */
//    public List<Airport> findByAreas(String [] areas) {
    public List<Airport> findByAreas(List<String> areas) {
        try {
            String jpql = "select airport from Airport airport where airport.area in (:areas)";
            return entityManager.createQuery(jpql, Airport.class).setFlushMode(FlushModeType.COMMIT).setParameter("areas", areas).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 用城市名查询机场
     * @param citys
     * @return
     */
    public List<Airport> findByCitys(List<String> citys) {
        String str1="中国";
        String str2="China";
        try {
            String jpql = "select airport from Airport airport where (airport.area = :str1 or airport.area = :str2) and airport.city in (:citys)";
            return entityManager.createQuery(jpql, Airport.class).setFlushMode(FlushModeType.COMMIT).setParameter("str1", str1).setParameter("str2", str2).setParameter("citys", citys).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
