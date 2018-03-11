package com.hxlm.health.web.dao;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airport;

import java.util.List;

/**
 * Created by guofeng on 2015/12/14.
 * Dao --机场
 */
public interface AirportDao  extends BaseDao<Airport ,Long> {

    Page<Airport> findPage(Boolean isVirtual, Pageable pageable);
    List<Airport> findByIsVirtual();

    /**
     * 模糊查询
     * @param key
     * @param count
     * @return
     */
    List<Airport> search(String key, Integer count);

    /**
     * 查询所有中国机场
     */
    List<Airport> findByInland();

    /**
     * 查询所有中国机场 地面代理外键空
     */
    List<Airport> findByInlandCostNull();
    /**
     * 查询所有国外机场
     */
     List<Airport> findByForeign();
    /**
     * 查询所有国外机场 地面代理外键空
     */
     List<Airport> findByForeignCostNull();

    /**
     * 按国家查询机场
     * @param area
     * @return
     */
    List<Airport> findByArea(String area);

    List<Airport> findByAreas(List<String> areas);

    /**
     * 用城市名查询机场
     * @param citys
     * @return
     */
    List<Airport> findByCitys(List<String> citys);

}
