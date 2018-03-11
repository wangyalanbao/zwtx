package com.hxlm.health.web.service.impl;


import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.FlightPlanDao;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.service.AirlineService;
import com.hxlm.health.web.service.FlightPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dengyang on 15/12/18.
 */

@Service("flightPlanServiceImpl")
public class FlightPlanServiceImpl extends BaseServiceImpl<FlightPlan,Long> implements FlightPlanService {

    @Resource(name = "flightPlanDaoImpl")
    private FlightPlanDao flightPlanDao;
    @Resource(name = "flightPlanDaoImpl")
    public void setBase(FlightPlanDao flightPlanDao){
        super.setBaseDao(flightPlanDao);
    }

    @Resource(name = "airlineServiceImpl")
    private AirlineService airlineService;

    @Transactional(readOnly = true)
    public Page<FlightPlan> findPage(PlaneType typeId,PlaneBrand planeBrandId,Company company,Pageable pageable) {
        return flightPlanDao.findPage(typeId,planeBrandId,company,pageable);
    }

    @Transactional(readOnly = true)
    public void updateDulfill(Long id,Airport departureId,Airport destinationId,Date actualTakeoffTime,Date actualLandingTime){
        flightPlanDao.updateDulfill(id,departureId,destinationId,actualTakeoffTime,actualLandingTime);
    }

    //飞机查询任务
    public List<FlightPlan> findByAirplane(Airplane airplane){
        return flightPlanDao.findByAirplane(airplane);
    }


    /**
     * 计算航段价格（飞行费用+地面费用）
     * @param flightPlan
     * @return
     */
    public FlightPlan price(FlightPlan flightPlan){
        // 原价
        BigDecimal originalPrice = new BigDecimal(0);

        // 特价
        BigDecimal specialprice = new BigDecimal(0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(flightPlan.getTakeoffTime());
        // 飞机
        Airplane airplane = flightPlan.getAirplane();
        // 起点
        Airport start = flightPlan.getDepartureId();
        // 终点
        Airport end = flightPlan.getDestinationId();
        // 飞行时间
        Float time = airlineService.lineHour(start, end, null, airplane.getTypeId(), calendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
        if(flightPlan.getDepartureId().getLocations() == Airport.Location.interior && flightPlan.getDestinationId().getLocations() == Airport.Location.interior){
            originalPrice = originalPrice.add(airplane.getLoadedPrice().multiply(new BigDecimal(time)))
                        .add(start.getInlandCost().getAgencyCost()).add(end.getInlandCost().getAgencyCost());
            specialprice = specialprice.add(airplane.getEmptyPrice().multiply(new BigDecimal(time)))
                    .add(start.getInlandCost().getTransferCost()).add(end.getInlandCost().getTransferCost());
        } else {
            originalPrice = originalPrice.add(airplane.getExtLoadedPrice().multiply(new BigDecimal(time)))
                    .add(start.getInlandCost().getAgencyCost()).add(end.getInlandCost().getAgencyCost());
            specialprice = specialprice.add(airplane.getExtEmptyPrice().multiply(new BigDecimal(time)))
                    .add(start.getInlandCost().getTransferCost()).add(end.getInlandCost().getTransferCost());
        }
        flightPlan.setTimeCost(new BigDecimal(time));
        flightPlan.setOriginalPrice(originalPrice);
        flightPlan.setSpecialprice(specialprice);
        return  flightPlan;
    }
    @Transactional(readOnly = true)
    public void updateIsReal(Long id,Boolean isReal){
        flightPlanDao.updateIsReal(id, isReal);
    }

    //查询相同结束时间
    @Transactional(readOnly = true)
    public List<FlightPlan> findByTuningDate(String tuningDate,Airplane airplane){
        return flightPlanDao.findByTuningDate(tuningDate, airplane);
    }


    /**
     * 获取可以下单的调机航班计划
     *
     * @param departure
     * @param destination
     * @param capacity
     * @param pageable
     * @return
     */
    @Override
    public Page<FlightPlan> findPage(Airport departure, Airport destination, Integer capacity, Pageable pageable) {
        return flightPlanDao.findPage(departure,destination,capacity,pageable);
    }
    @Transactional(readOnly = true)
    public List<Date> findByTakeoff(String tuningDate){
        return flightPlanDao.findByTakeoff(tuningDate);
    }
}
