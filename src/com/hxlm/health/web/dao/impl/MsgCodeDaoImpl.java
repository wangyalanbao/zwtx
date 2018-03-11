package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.MsgCodeDao;
import com.hxlm.health.web.entity.MsgCode;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;

@Repository("msgCodeDaoImpl")
public class MsgCodeDaoImpl extends BaseDaoImpl<MsgCode,Long> implements MsgCodeDao {

    public MsgCode findOne(String mobile) {
        String jpql = "select msgCode from MsgCode msgCode where msgCode.mobile = :mobile order by msgCode.createDate desc limit 1";
        try {
            return entityManager.createQuery(jpql,MsgCode.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile",mobile).getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
