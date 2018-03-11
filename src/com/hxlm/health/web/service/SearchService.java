/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import java.math.BigDecimal;

import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.entity.Product;
import com.hxlm.health.web.entity.Product.OrderType;

/**
 * Service - 搜索
 * 
 * 
 * 
 */
public interface SearchService {

	/**
	 * 创建索引
	 */
	void index();

	/**
	 * 创建索引
	 * 
	 * @param type
	 *            索引类型
	 */
	void index(Class<?> type);


	/**
	 * 创建索引
	 * 
	 * @param product
	 *            商品
	 */
	void index(Product product);

	/**
	 * 删除索引
	 */
	void purge();

	/**
	 * 删除索引
	 * 
	 * @param type
	 *            索引类型
	 */
	void purge(Class<?> type);


	/**
	 * 删除索引
	 * 
	 * @param product
	 *            商品
	 */
	void purge(Product product);

	/**
	 * 搜索商品分页
	 * 
	 * @param keyword
	 *            关键词
	 * @param startPrice
	 *            最低价格
	 * @param endPrice
	 *            最高价格
	 * @param orderType
	 *            排序类型
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Product> search(String keyword, BigDecimal startPrice, BigDecimal endPrice, OrderType orderType, Pageable pageable);

}