package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.Airline;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.entity.PlaneType;

import java.util.List;

/**
 * Created by guofeng on 2015/12/14.
 * service--行线
 */
public interface AirlineService extends BaseService<Airline,Long> {

    Page<Airline> findPage(Airport departureId,Airport destinationId,PlaneBrand brandId,PlaneType typeId,Integer month, Pageable pageable);

    List<Airline> findList(Airport departureId,Airport destinationId,PlaneBrand brandId,PlaneType typeId,Integer month);

    Float lineHour(Airport departure, Airport destination, PlaneBrand brandId, PlaneType typeId, Integer month, double cruisingSpeed);
}
