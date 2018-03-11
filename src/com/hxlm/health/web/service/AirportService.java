package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.BaseDao;
import com.hxlm.health.web.entity.Airport;

import java.util.List;
import java.util.Set;

/**
 * Created by guofeng on 2015/12/14.
 * service--机场
 */
public interface AirportService extends BaseService<Airport,Long>  {

    Page<Airport> findPage(Boolean isVirtual, Pageable pageable);

    List<Airport> findIsVirtual();
    /**
     * 模糊查询
     * @param key
     * @param count
     * @return
     */
    List<Airport> search(String key, Integer count);

    /**
     * 距机场最近的虚拟基地
     */
    Airport findNearestAirport(Airport airport, Set<Airport> airports);

    /**
     * 获取中心虚拟基地
     * @param start
     * @param end
     * @param airports
     * @return
     */
    Airport findCenterAirport(Airport start, Airport end, Set<Airport> airports);

    /**
     * 查询所有中国机场
     */
    List<Airport> findByInland();
    /**
     * 查询所有国外机场
     */
    List<Airport> findByForeign();

    /**
     * 查询所有中国机场 地面代理外键空
     */
    List<Airport> findByInlandCostNull();
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
