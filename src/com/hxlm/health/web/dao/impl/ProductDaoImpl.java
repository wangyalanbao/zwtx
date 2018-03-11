/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.hxlm.health.web.dao.GoodsDao;
import com.hxlm.health.web.dao.ProductDao;
import com.hxlm.health.web.dao.SnDao;
import com.hxlm.health.web.util.SettingUtils;
import com.hxlm.health.web.Filter;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.Setting;
import com.hxlm.health.web.entity.Attribute;
import com.hxlm.health.web.entity.Goods;
import com.hxlm.health.web.entity.Member;
import com.hxlm.health.web.entity.OrderItem;
import com.hxlm.health.web.entity.Product;
import com.hxlm.health.web.entity.Product.OrderType;
import com.hxlm.health.web.entity.ProductCategory;
import com.hxlm.health.web.entity.Tag;

import com.hxlm.health.web.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 商品
 * 
 * 
 * 
 */
@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {

	private static final Pattern pattern = Pattern.compile("\\d*");

	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;

    /**
     * 判断商品编号是否存在
     *
     * @param sn 商品编号(忽略大小写)
     * @return 商品编号是否存在
     */
    @Override
    public boolean snExists(String sn) {
        return false;
    }

    /**
     * 根据商品编号查找商品
     *
     * @param sn 商品编号(忽略大小写)
     * @return 商品，若不存在则返回null
     */
    @Override
    public Product findBySn(String sn) {
        return null;
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
     * 查找商品
     *
     * @param goods    货品
     * @param excludes 排除商品
     * @return 商品
     */
    @Override
    public List<Product> findList(Goods goods, Set<Product> excludes) {
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

    @Override
    public void updatePrice(Long id, BigDecimal price) {

    }

    @Override
    public void updateStock(Long id, int stock) {

    }
}