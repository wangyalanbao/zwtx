package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.*;

import java.util.List;

/**
 * Created by guofeng on 2015/12/14.
 * service -- 飞机
 */
public interface AirplaneService extends BaseService<Airplane,Long> {

    Page<Airplane> findPage(PlaneType typeId,PlaneBrand planeBrandId,Company company,Airport airportId, Pageable pageable);

    //检查注册号是否存在
    boolean regNoExists(String regNo);

    /**
     * 根据座位数查询飞机
     *
     */
    List<Airplane> findByCapacity(Integer passengerNum);

    /**
     * 查询飞机列表
     * @param type
     * @param regNo
     * @param company
     * @param capacity
     * @return
     */
    List<Airplane> findList(String type,String regNo,Company company,Integer capacity);

    /**
     * 查询巡航最慢的飞机
     * @return
     */
    Airplane findSlowest();

    /**
     * 查询可以下单的飞机
     * @param quoteAirlineList
     * @param type
     * @param regNo
     * @param company
     * @param capacity
     * @return
     */
    List<Airplane> findEffectiveList(List<QuoteAirline> quoteAirlineList, String type, String regNo, Company company, Integer capacity);
}
