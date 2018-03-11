package com.hxlm.health.web.dao;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airline;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.entity.PlaneType;

import java.util.List;

/**
 * Created by guofeng on 2015/12/14.
 * Dao--行线
 */
public interface AirlineDao extends BaseDao<Airline,Long> {

    Page<Airline> findPage(Airport departureId,Airport destinationId,PlaneBrand brandId,PlaneType typeId,Integer month, Pageable pageable);
    List<Airline> findList(Airport departureId,Airport destinationId,PlaneBrand brandId,PlaneType typeId,Integer month);
}
