package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.VersionsUpdateDao;
import com.hxlm.health.web.entity.SoftwareManage;
import com.hxlm.health.web.entity.VersionsUpdate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofeng on 2015/12/28.
 * DaoImpl--版本号
 */
@Repository("versionsUpdateDaoImpl")
public class VersionsUpdateDaoImpl extends BaseDaoImpl<VersionsUpdate,Long> implements VersionsUpdateDao{

    //通过渠道类型查询对应的下载地址2
    //SELECT * FROM lm_versions_update t  WHERE t.create_date = (SELECT MAX(t1.create_date) FROM `lm_versions_update` t1) ;
    public String address(String versionsNum){
        if(StringUtils.isEmpty(versionsNum)){
            return null;
        }try {
            String jpql="select versionsUpdate.downurl from VersionsUpdate versionsUpdate where versionsUpdate.versionsNum = :versionsNum  and versionsUpdate.createDate = (select max(versionsUpdates.createDate) from VersionsUpdate versionsUpdates where versionsUpdates.versionsNum = :versionsNum) order by versionsUpdate.createDate asc ";
            return entityManager.createQuery(jpql,String.class).setFlushMode(FlushModeType.COMMIT).setParameter("versionsNum",versionsNum).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
    //版本类型查找版本对象
    public VersionsUpdate versions(String versionsNum){
        if(StringUtils.isEmpty(versionsNum)){
            return null;
        }try {
            String jpql="select versionsUpdate from VersionsUpdate versionsUpdate where versionsUpdate.versionsNum = :versionsNum order by versionsUpdate.createDate desc";
            return entityManager.createQuery(jpql,VersionsUpdate.class).setFlushMode(FlushModeType.COMMIT).setParameter("versionsNum",versionsNum).getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    //判断版本类型是否存在
    public boolean channelTypesExists(String channelTypes){
        if(StringUtils.isEmpty(channelTypes)){
            return false;
        }
        String jpql="select count(*) from VersionsUpdate versionsUpdate where versionsUpdate.channelTypes = :channelTypes";
        Long count=entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("channelTypes",channelTypes).getSingleResult();
        return count>0;
    }

    //软件类型查找版本类型所对应的版本号
    public List<VersionsUpdate> vsesionUpdate(String sn, String channelTypesSn){
        if(sn == null || channelTypesSn == null|| StringUtils.isEmpty(channelTypesSn)|| StringUtils.isEmpty(sn)){
            return new ArrayList<VersionsUpdate>();
        }try {
            String jpql="select versionsUpdate from VersionsUpdate versionsUpdate where versionsUpdate.softwareManage.sn = :sn and versionsUpdate.softwareManage.channelTypesSn = :channelTypesSn order by versionsUpdate.createDate desc";
            return entityManager.createQuery(jpql, VersionsUpdate.class).setMaxResults(1).setFlushMode(FlushModeType.COMMIT).setParameter("sn",sn).setParameter("channelTypesSn",channelTypesSn).getResultList();
        }catch (NoResultException e){
            return null;
        }

    }

    public Page<VersionsUpdate> findPage(SoftwareManage softwareManage, Pageable pageable) {
        // 创建安全查询，CriteriaBuilder为安全查询创建工厂
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        // 创建安全查询的对象
        CriteriaQuery<VersionsUpdate> criteriaQuery = criteriaBuilder.createQuery(VersionsUpdate.class);
        // Root 定义查询的From子句中能出现的类型，它与SQL查询中的FROM子句类似
        Root<VersionsUpdate> root = criteriaQuery.from(VersionsUpdate.class);
        criteriaQuery.select(root);
        // Predicate 过滤条件
        Predicate restrictions = criteriaBuilder.conjunction();
        if (softwareManage != null) {
            // 构建表查询条件
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("softwareManage"), softwareManage));
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }
}
