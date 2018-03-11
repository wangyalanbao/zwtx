package com.hxlm.health.web.service;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.entity.*;

import java.util.Date;
import java.util.List;

/**
 * Created by dengyang on 15/12/18.
 */
public interface FlightPlanService extends BaseService<FlightPlan,Long>{

    Page<FlightPlan> findPage(PlaneType typeId,PlaneBrand planeBrandId,Company company,Pageable pageable);

    //航班计划完成
    void updateDulfill(Long id,Airport departureId,Airport destinationId,Date actualTakeoffTime,Date actualLandingTime);

    //飞机查询任务
    List<FlightPlan> findByAirplane(Airplane airplane);

    /**
     * 计算航段价格（飞行费用+地面费用）
     * @param flightPlan
     * @return
     */
    FlightPlan price(FlightPlan flightPlan);

    void updateIsReal(Long id,Boolean isReal);

    //查询相同结束时间
    List<FlightPlan> findByTuningDate(String tuningDate,Airplane airplane);
    /**
     * 获取可以下单的调机航班计划
     * @param departure
     * @param destination
     * @param capacity
     * @param pageable
     * @return
     */
    Page<FlightPlan> findPage(Airport departure, Airport destination, Integer capacity, Pageable pageable);

    List<Date> findByTakeoff(String tuningDate);
}
