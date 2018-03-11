package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.DateEditor;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.AirplaneDao;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.service.AirlineService;
import com.hxlm.health.web.service.AirplaneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by guofeng on 2015/12/14.
 * serviceImpl--飞机
 */
@Service("airplaneServiceImpl")
public class AirplaneServiceImpl extends BaseServiceImpl<Airplane,Long> implements AirplaneService{

    @Resource(name = "airplaneDaoImpl")
    private AirplaneDao airplaneDao;
    @Resource(name = "airplaneDaoImpl")
    public void setBaseDao(AirplaneDao airplaneDao){
         super.setBaseDao(airplaneDao);
    }

    @Resource(name = "airlineServiceImpl")
    private AirlineService airlineService;

    public Page<Airplane> findPage(PlaneType typeId,PlaneBrand planeBrandId,Company company,Airport airportId, Pageable pageable){
        return airplaneDao.findPage( typeId, planeBrandId, company, airportId, pageable);
    }

    @Transactional(readOnly = true)
    public boolean regNoExists(String regNo){
        return airplaneDao.regNoExists(regNo);
    }

    /**
     * 根据座位数查询飞机
     *
     * @param passengerNum
     */
    @Override
    public List<Airplane> findByCapacity(Integer passengerNum) {
        return airplaneDao.findByCapacity(passengerNum);
    }

    /**
     * 查询飞机列表
     *
     * @param type
     * @param regNo
     * @param company
     * @param capacity
     * @return
     */
    @Override
    public List<Airplane> findList(String type, String regNo, Company company, Integer capacity) {
        return airplaneDao.findList(type,regNo,company,capacity);
    }

    /**
     * 查询巡航最慢的飞机
     *
     * @return
     */
    @Override
    public Airplane findSlowest() {
        return airplaneDao.findSlowest();
    }

    /**
     * 查询可以下单的飞机
     * @param quoteAirlineList
     * @param type
     * @param regNo
     * @param company
     * @param capacity
     * @return
     */
    public List<Airplane> findEffectiveList(List<QuoteAirline> quoteAirlineList, String type, String regNo, Company company, Integer capacity){
        List<Airplane> airplaneList = airplaneDao.findList(type,regNo,company,capacity);
        // 客户所需的飞行日期
        List<Date> dateList = new ArrayList<Date>();
        Airplane airplane = airplaneDao.findSlowest();
        for(QuoteAirline quoteAirline:quoteAirlineList){
            // 起飞时间
            Date takeoffTime = quoteAirline.getTakeoffTime();
            if(!dateList.contains(DateEditor.format(takeoffTime))){
                dateList.add(DateEditor.formatDate(takeoffTime, DateEditor.C_DATE_PATTON_DEFAULT));
            }
            Calendar takeoffCalendar = Calendar.getInstance();
            takeoffCalendar.setTime(takeoffTime);
            // 最长飞行时长
            Float v_tmp_hour = airlineService.lineHour(quoteAirline.getDeparture(), quoteAirline.getDestination(), null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
            // 降落时间
            Calendar loadCalendar = Calendar.getInstance();
            loadCalendar.setTime(takeoffTime);
            int minutes = (int)(v_tmp_hour*60);
            loadCalendar.add(Calendar.MINUTE, minutes);
            if(!dateList.contains(DateEditor.formatDate(loadCalendar.getTime(), DateEditor.C_DATE_PATTON_DEFAULT))){
                dateList.add(DateEditor.formatDate(loadCalendar.getTime(), DateEditor.C_DATE_PATTON_DEFAULT));
            }
        }
        Iterator<Airplane> iterator = airplaneList.iterator();
        while (iterator.hasNext()) {
            if(!iterator.next().isEffective(dateList)){
                iterator.remove();
            }
        }
        return airplaneList;
    }

    @Override
    @Transactional(readOnly = true)
    public Airplane find(Long id){
        Airplane airplane = super.find(id);
        if (airplane != null) {
            String tmpRegNo = airplane.getRegNo().trim();
            if (tmpRegNo.substring(0, 2).equals("B-")) {
                airplane.setRegNos("B-");
                airplane.setNumber(tmpRegNo.substring(2));
            } else if (tmpRegNo.substring(0, 3).equals("VP-")) {
                airplane.setRegNos("VP-");
                airplane.setNumber(tmpRegNo.substring(3));
            } else if (tmpRegNo.substring(0, 1).equals("N")) {
                airplane.setRegNos("N-");
                airplane.setNumber(tmpRegNo.substring(1));
            } else {
                airplane.setRegNos("");
                airplane.setNumber(tmpRegNo);
            }
        }

        return airplane;
    }
}
