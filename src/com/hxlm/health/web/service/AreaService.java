/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import java.util.List;

import com.hxlm.health.web.entity.Area;

/**
 * Service - 地区
 * 
 * 
 * 
 */
public interface AreaService extends BaseService<Area, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @return 顶级地区
	 */
	List<Area> findRoots();

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