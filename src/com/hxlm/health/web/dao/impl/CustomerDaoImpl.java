package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.CustomerDao;
import com.hxlm.health.web.entity.Customer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by guofeng on 2015/12/23.
 * DaoImpl--客户
 */
@Repository("customerDaoImpl")
public class CustomerDaoImpl extends BaseDaoImpl<Customer,Long> implements CustomerDao {

    public Page<Customer> findPage(Boolean charted, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();

        if (charted != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("charted"), charted));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

    /**
     * 根据手机号模糊查询
     * @param telephone
     * @param count
     * @return
     */
    public List<Customer> findByTelephone(String telephone, Integer count){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (StringUtils.isNotEmpty(telephone)) {
            restrictions = criteriaBuilder.and(restrictions,  criteriaBuilder.like(root.<String>get("telephone"),"%" + telephone + "%"));
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, count, null, null);
    }

}
