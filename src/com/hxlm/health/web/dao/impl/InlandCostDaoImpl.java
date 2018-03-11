package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.InlandCostDao;
import com.hxlm.health.web.entity.InlandCost;
import javax.persistence.FlushModeType;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

/**
 * Created by guofeng on 2016/1/5.
 * DaoImpl--国内地面代理费用
 */
@Repository("inlandCostDaoImpl")
public class InlandCostDaoImpl extends BaseDaoImpl<InlandCost,Long> implements InlandCostDao {

    //城市查询机场
    public InlandCost findByArea(String area) {
        try {
            String jpql = "select airport from InlandCost airport where airport.area = :area";
            return entityManager.createQuery(jpql, InlandCost.class).setFlushMode(FlushModeType.COMMIT).setParameter("area", area).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
