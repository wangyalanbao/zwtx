/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao;

import java.util.List;

import com.hxlm.health.web.entity.Area;

/**
 * Dao - 地区
 * 
 * 
 * 
 */
public interface AreaDao extends BaseDao<Area, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Area> findRoots(Integer count);

	Area findByName(String name);

	/**
	 * 查找子地区
	 * @param area
	 * @param count
	 * @return
	 */
	List<Area> findChildren(Area area, Integer count);

}