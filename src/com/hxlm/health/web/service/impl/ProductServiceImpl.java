/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.*;
import com.hxlm.health.web.Order;
import com.hxlm.health.web.dao.GoodsDao;
import com.hxlm.health.web.dao.ProductCategoryDao;
import com.hxlm.health.web.dao.ProductDao;
import com.hxlm.health.web.dao.SnDao;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.entity.Product.OrderType;
import com.hxlm.health.web.service.ProductService;
import com.hxlm.health.web.service.StaticService;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.*;

/**
 * Service - 商品
 * 
 * 
 * 
 */
@Service("productServiceImpl")
public class ProductServiceImpl extends BaseServiceImpl<Product, Long> implements ProductService, DisposableBean {

	/** 查看点击数时间 */
	private long viewHitsTime = System.currentTimeMillis();

	@Resource(name = "productCategoryDaoImpl")
	private ProductCategoryDao productCategoryDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "ehCacheManager")
	private CacheManager cacheManager;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;
	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;

	@Resource(name = "productDaoImpl")
	public void setBaseDao(ProductDao productDao) {
		super.setBaseDao(productDao);
	}

	@Transactional(readOnly = true)
	public boolean snExists(String sn) {
		return productDao.snExists(sn);
	}

	@Transactional(readOnly = true)
	public Product findBySn(String sn) {
		return productDao.findBySn(sn);
	}

	/**
	 * 判断商品编号是否唯一
	 *
	 * @param previousSn 修改前商品编号(忽略大小写)
	 * @param currentSn  当前商品编号(忽略大小写)
	 * @return 商品编号是否唯一
	 */
	@Override
	public boolean snUnique(String previousSn, String currentSn) {
		return false;
	}

	/**
	 * 通过ID、编号、全称查找商品
	 *
	 * @param keyword 关键词
	 * @param isGift  是否为赠品
	 * @param count   数量
	 * @return 商品
	 */
	@Override
	public List<Product> search(String keyword, Boolean isGift, Integer count) {
		return null;
	}

	/**
	 * 通过ID、编号、全称、分类、标签查找商品
	 *
	 * @param keyword         关键词
	 * @param isGift          是否为赠品
	 * @param count           数量
	 * @param productCategory 分类
	 * @param tags
	 * @return 商品
	 */
	@Override
	public List<Product> search(String keyword, Boolean isGift, Integer count, ProductCategory productCategory, List<Tag> tags) {
		return null;
	}

	/**
	 * 查找已上架商品
	 *
	 * @param productCategory 商品分类
	 * @param beginDate       起始日期
	 * @param endDate         结束日期
	 * @param first           起始记录
	 * @param count           数量
	 * @return 已上架商品
	 */
	@Override
	public List<Product> findList(ProductCategory productCategory, Date beginDate, Date endDate, Integer first, Integer count) {
		return null;
	}

	/**
	 * 查找商品销售信息
	 *
	 * @param beginDate 起始日期
	 * @param endDate   结束日期
	 * @param count     数量
	 * @return 商品销售信息
	 */
	@Override
	public List<Object[]> findSalesList(Date beginDate, Date endDate, Integer count) {
		return null;
	}

	/**
	 * 查找收藏商品分页
	 *
	 * @param member   会员
	 * @param pageable 分页信息
	 * @return 收藏商品分页
	 */
	@Override
	public Page<Product> findPage(Member member, Pageable pageable) {
		return null;
	}

	/**
	 * 查询商品数量
	 *
	 * @param favoriteMember 收藏会员
	 * @param isMarketable   是否上架
	 * @param isList         是否列出
	 * @param isTop          是否置顶
	 * @param isGift         是否为赠品
	 * @param isOutOfStock   是否缺货
	 * @param isStockAlert   是否库存警告
	 * @return 商品数量
	 */
	@Override
	public Long count(Member favoriteMember, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert) {
		return null;
	}

	/**
	 * 判断会员是否已购买该商品
	 *
	 * @param member  会员
	 * @param product 商品
	 * @return 是否已购买该商品
	 */
	@Override
	public boolean isPurchased(Member member, Product product) {
		return false;
	}

	/**
	 * 查看并更新点击数
	 *
	 * @param id ID
	 * @return 点击数
	 */
	@Override
	public long viewHits(Long id) {
		return 0;
	}

	@Override
	public Result testMethod() {
		return null;
	}

	@Override
	public Long saveProduct(Product product) {
		return null;
	}

	@Override
	public void updatePrice(Long id, BigDecimal price) {

	}

	@Override
	public void updateStock(Long id, int stock) {

	}

	@Override
	public Product createAndReturn(String productCategoryName, BigDecimal price, String name, String introduction, int stock) {
		return null;
	}


	@Override
	public void destroy() throws Exception {

	}
}