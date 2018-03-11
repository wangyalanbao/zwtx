/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import javax.persistence.FlushModeType;

import com.hxlm.health.web.dao.LogDao;
import com.hxlm.health.web.entity.Log;

import org.springframework.stereotype.Repository;

/**
 * Dao - 日志
 * 
 * 
 * 
 */
@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

	public void removeAll() {
		String jpql = "delete from Log log";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
	}

}