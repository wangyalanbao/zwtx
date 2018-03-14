/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import com.hxlm.health.web.dao.AdPositionDao;
import com.hxlm.health.web.entity.AdPosition;
import org.springframework.stereotype.Repository;

import javax.persistence.FlushModeType;

/**
 * Dao - 广告位
 * 
 * 
 * 
 */
@Repository("adPositionDaoImpl")
public class AdPositionDaoImpl extends BaseDaoImpl<AdPosition, Long> implements AdPositionDao {

}