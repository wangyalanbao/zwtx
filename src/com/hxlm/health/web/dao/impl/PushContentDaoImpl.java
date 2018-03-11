/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.PushContentDao;
import com.hxlm.health.web.entity.PushContent;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;
import java.util.Date;
import java.util.List;

/**
 * Dao - 推送内容
 * 
 * 
 * 
 */
@Repository("pushContentDaoImpl")
public class PushContentDaoImpl extends BaseDaoImpl<PushContent, Long> implements PushContentDao {

    //根据前台传回的时间查询推送内容，如果时间为空就差询全部的推送内容，不为空则查询传过来的时间到当前时间区间的推送内容
    @Override
    public List<PushContent> findListDate(Date time) {
        if(time == null){
            String jpql = "select pushContent from PushContent pushContent order by pushContent.createDate desc";
            return entityManager.createQuery(jpql,PushContent.class).setFlushMode(FlushModeType.COMMIT).getResultList();
        }else{
            String jpql = "select pushContent from PushContent pushContent where pushContent.createDate >:time order by pushContent.createDate desc";
            return entityManager.createQuery(jpql,PushContent.class).setFlushMode(FlushModeType.COMMIT).setParameter("time",time).getResultList();
        }
    }
}