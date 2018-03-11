package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.SoftwareManageDao;
import com.hxlm.health.web.entity.SoftwareManage;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

/**
 * Created by guofeng on 2015/12/28.
 * DaoImpl--软件类型
 */
@Repository("softwareManageDaoImpl")
public class SoftwareManageDaoImpl extends BaseDaoImpl<SoftwareManage,Long> implements SoftwareManageDao {

    public SoftwareManage findBySn(String sn) {
        if (sn == null) {
            return null;
        }
        try {
            String jpql = "select softwareManage from SoftwareManage softwareManage where lower(softwareManage.sn) = lower(:sn)";
            return entityManager.createQuery(jpql, SoftwareManage.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public SoftwareManage findByTypeSn(String typeSn) {
        if (typeSn == null) {
            return null;
        }
        try {
            String jpql = "select softwareManage from SoftwareManage softwareManage where lower(softwareManage.channelTypesSn) = lower(:typeSn)";
            return entityManager.createQuery(jpql, SoftwareManage.class).setFlushMode(FlushModeType.COMMIT).setParameter("typeSn", typeSn).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //软件类型查找版本类型所对应的软件
    public SoftwareManage findByChannelSn(String sn, String channelTypesSn){
        if(sn == null || channelTypesSn == null|| StringUtils.isEmpty(channelTypesSn)|| StringUtils.isEmpty(sn)){
            return null;
        }try {
            String jpql="select softwareManage from SoftwareManage softwareManage where softwareManage.sn = :sn and softwareManage.channelTypesSn = :channelTypesSn ";
            return entityManager.createQuery(jpql, SoftwareManage.class).setMaxResults(1).setFlushMode(FlushModeType.COMMIT).setParameter("sn",sn).setParameter("channelTypesSn",channelTypesSn).getSingleResult();
        }catch (NoResultException e){
            return null;
        }

    }
}
