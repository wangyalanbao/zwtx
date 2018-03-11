/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import com.hxlm.health.web.dao.AreaDao;
import com.hxlm.health.web.entity.Area;

import com.hxlm.health.web.entity.Area;
import org.springframework.stereotype.Repository;

/**
 * Dao - 地区
 * 
 * 
 * 
 */
@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {

	public List<Area> findRoots(Integer count) {
		String jpql = "select area from Area area where area.parent is null order by area.order asc";
		TypedQuery<Area> query = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}


	public Area findByName(String name) {
		String jpql = "select area from Area area where area.name like :name";
		TypedQuery<Area> query = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT).setParameter("name","%" + name + "%");
		query.setMaxResults(1);
		try {
			return query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 查找子地区
	 * @param area
	 * @param count
	 * @return
	 */
	public List<Area> findChildren(Area area, Integer count) {
		TypedQuery<Area> query;
		if (area != null) {
			String jpql = "select area from Area area where area.treePath like :treePath order by area.order asc";
			query = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT).setParameter("treePath", "%" + Area.TREE_PATH_SEPARATOR + area.getId() + Area.TREE_PATH_SEPARATOR + "%");
		} else {
			String jpql = "select area from Area area order by area.order asc";
			query = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT);
		}
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

}