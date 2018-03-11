/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.MemberChild;
import com.hxlm.health.web.entity.Product;

import java.util.Map;

/**
 * Service - 静态化
 * 
 * 
 * 
 */
public interface StaticService {

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @param model
	 *            数据
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath, Map<String, Object> model);

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath);


	/**
	 * 生成静态
	 * 
	 * @param product
	 *            商品
	 * @return 生成数量
	 */
	int build(Product product);


	/**
	 * 生成静态子会员
	 */

	int build(MemberChild memberChild);
	/**
	 * 生成首页静态
	 * 
	 * @return 生成数量
	 */
	int buildIndex();

	/**
	 * 生成Sitemap
	 * 
	 * @return 生成数量
	 */
	int buildSitemap();

	/**
	 * 生成其它静态
	 * 
	 * @return 生成数量
	 */
	int buildOther();

	/**
	 * 生成所有静态
	 * 
	 * @return 生成数量
	 */
	int buildAll();

	/**
	 * 删除静态
	 * 
	 * @param staticPath
	 *            静态文件路径
	 * @return 删除数量
	 */
	int delete(String staticPath);



	/**
	 * 删除静态
	 * 
	 * @param product
	 *            商品
	 * @return 删除数量
	 */
	int delete(Product product);

	/**
	 * 删除首页静态
	 * 
	 * @return 删除数量
	 */
	int deleteIndex();

	/**
	 * 删除其它静态
	 * 
	 * @return 删除数量
	 */
	int deleteOther();

}