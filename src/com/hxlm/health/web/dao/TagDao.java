/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import java.util.List;

import com.hxlm.health.web.entity.Tag;

/**
 * Dao - 标签
 * 
 * 
 * 
 */
public interface TagDao extends BaseDao<Tag, Long> {

	/**
	 * 查找标签
	 * 
	 * @param type
	 *            类型
	 * @return 标签
	 */
	List<Tag> findList(Tag.Type type);

}