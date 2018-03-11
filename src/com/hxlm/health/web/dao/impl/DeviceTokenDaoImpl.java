package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.DeviceTokenDao;
import com.hxlm.health.web.entity.DeviceToken;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import java.util.List;

/**
 * Created by dengyang on 15/11/25.
 */

@Repository("deviceTokenDaoImpl")
public class DeviceTokenDaoImpl extends BaseDaoImpl<DeviceToken, Long> implements DeviceTokenDao {

    public DeviceToken findByToken(String token) {
        String jpql = "select deviceToken from DeviceToken deviceToken where deviceToken.deviceToken = :token";
        try {
            return entityManager.createQuery(jpql, DeviceToken.class).setFlushMode(FlushModeType.COMMIT).setParameter("token", token).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    public void deleteDeviceTokens(List<String> tokens) {
        String jpql = "delete from DeviceToken dt where dt.deviceToken in (:tokens)";
        entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("tokens", tokens).executeUpdate();
    }


}
