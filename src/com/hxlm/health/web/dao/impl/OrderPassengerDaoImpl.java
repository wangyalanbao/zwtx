package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.OrderPassengerDao;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.Order;
import com.hxlm.health.web.entity.OrderAirline;
import com.hxlm.health.web.entity.OrderPassenger;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

/**
 * Created by guofeng on 2016/1/12.
 * DaoImpl-订单乘客信息
 */
@Repository("orderPassengerDaoImpl")
public class OrderPassengerDaoImpl extends BaseDaoImpl<OrderPassenger,Long> implements OrderPassengerDao {

    //证件号是否存在
    public boolean idCardNoExists(String idCardNo) {
        if (idCardNo == null) {
            return false;
        }
        String jpql = "select count(*) from OrderPassenger orderPassenger where orderPassenger.idCardNo = :idCardNo";
        Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("idCardNo", idCardNo).getSingleResult();
        return count > 0;
    }

    //	模糊查询
    public List<OrderPassenger> searth(String keyword,Integer count){

        if(StringUtils.isEmpty(keyword)){
            return Collections.<OrderPassenger> emptyList();
        }
        // 创建安全查询对象
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        // 安全查询主语句
        CriteriaQuery<OrderPassenger> criteriaQuery=criteriaBuilder.createQuery(OrderPassenger.class);
        // Root 定义查询的From子句中能出现的类型
        Root<OrderPassenger> root=criteriaQuery.from(OrderPassenger.class);
        criteriaQuery.select(root);
        // Predicate 过滤条件
        Predicate restrictions=criteriaBuilder.conjunction();
        //用户名，名称查找账号
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String>get("idCardNo"), "%" + keyword + "%"), criteriaBuilder.like(root.<String>get("name"), "%" + keyword + "%")));
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, null, null);
    }

    // 修改乘客的所属订单
    public void updateOrder(Long id,OrderAirline orderAirline,Order order){
        String jpql="update OrderPassenger orderPassenger set orderPassenger.orderAirline = :orderAirline , orderPassenger.tripOrder = :tripOrder where orderPassenger.id = :id";
        entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("orderAirline", orderAirline).setParameter("tripOrder",order).setParameter("id",id).executeUpdate();
    }
}
