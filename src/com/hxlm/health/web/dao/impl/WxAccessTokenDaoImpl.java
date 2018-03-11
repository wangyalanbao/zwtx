package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.WxAccessTokenDao;
import com.hxlm.health.web.entity.Cart;
import com.hxlm.health.web.entity.WxAccessToken;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import java.util.Date;

/**
 * Created by dengyang on 15/9/1.
 */
@Repository("wxAccessTokenDaoImpl")
public class WxAccessTokenDaoImpl extends BaseDaoImpl<WxAccessToken, Long> implements WxAccessTokenDao {

    public void truncateToken() {
        String jpql = "delete from WxAccessToken wx";
        entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
    }
}
