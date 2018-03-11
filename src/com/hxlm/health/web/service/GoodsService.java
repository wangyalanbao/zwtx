/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.Goods;

/**
 * Service - 货品
 * 
 * 
 * 
 */
public interface GoodsService extends BaseService<Goods, Long> {

    Long saveGoods(Goods goods);

}