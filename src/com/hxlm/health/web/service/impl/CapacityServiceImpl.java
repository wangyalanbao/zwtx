/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.dao.CapacityDao;
import com.hxlm.health.web.dao.ProductDao;
import com.hxlm.health.web.entity.Capacity;
import com.hxlm.health.web.entity.Product;
import com.hxlm.health.web.service.CapacityService;
import com.hxlm.health.web.service.StaticService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service - 运力
 * 
 * 
 * 
 */
@Service("capacityServiceImpl")
public class CapacityServiceImpl extends BaseServiceImpl<Capacity, Long> implements CapacityService {

	@Resource(name = "capacityDaoImpl")
	private CapacityDao capacityDao;

	@Resource(name = "capacityDaoImpl")
	public void setBaseDao(CapacityDao capacityDao) {
		super.setBaseDao(capacityDao);
	}

	
}