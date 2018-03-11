/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import com.hxlm.health.web.entity.PushContent;

import java.util.Date;
import java.util.List;

/**
 * Dao - 推送内容
 * 
 * 
 * 
 */
public interface PushContentDao extends BaseDao<PushContent, Long> {

    List<PushContent> findListDate(Date time);
}