/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;

import com.hxlm.health.web.dao.GoodsDao;
import com.hxlm.health.web.dao.ProductDao;
import com.hxlm.health.web.dao.SnDao;
import com.hxlm.health.web.entity.Goods;
import com.hxlm.health.web.entity.Product;
import com.hxlm.health.web.entity.Sn;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 货品
 * 
 * 
 * 
 */
@Repository("goodsDaoImpl")
public class GoodsDaoImpl extends BaseDaoImpl<Goods, Long> implements GoodsDao {

	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;





}