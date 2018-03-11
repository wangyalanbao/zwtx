package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.Setting;
import com.hxlm.health.web.dao.AirlineDao;
import com.hxlm.health.web.dao.AirplaneDao;
import com.hxlm.health.web.entity.Airline;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.PlaneBrand;
import com.hxlm.health.web.entity.PlaneType;
import com.hxlm.health.web.service.AirlineService;
import com.hxlm.health.web.service.CommonService;
import com.hxlm.health.web.util.SettingUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by guofeng on 2015/12/14.
 * serviceImpl--行线
 */
@Service("airlineServiceImpl")
public class AirlineServiceImpl  extends BaseServiceImpl<Airline,Long> implements AirlineService{
    @Resource(name = "airlineDaoImpl")
    private AirlineDao airlineDao;
    @Resource(name = "airlineDaoImpl")
    public void setBaseDao(AirlineDao airlineDao){
        super.setBaseDao(airlineDao);
    }

    @Resource(name = "commonServiceImpl")
    private CommonService commonService;

    public Page<Airline> findPage(Airport departureId,Airport destinationId,PlaneBrand brandId,PlaneType typeId,Integer month, Pageable pageable){
        return airlineDao.findPage(departureId,destinationId,brandId,typeId,month,pageable);
    }

    @Override
    public List<Airline> findList(Airport departureId, Airport destinationId, PlaneBrand brandId, PlaneType typeId, Integer month) {
        return airlineDao.findList(departureId,destinationId,brandId,typeId,month);
    }

    /**
     * 获取航线飞行时间(单位：小时)
     * @param departure 始发机场
     * @param destination 终点机场
     * @param brandId 飞机品牌
     * @param typeId 飞机型号
     * @param month 月份
     * @param cruisingSpeed 机巡航速度
     * @return
     */
    public Float lineHour(Airport departure, Airport destination, PlaneBrand brandId, PlaneType typeId, Integer month, double cruisingSpeed){

        Float timeCost = 0f;
        // 起飞前和降落附加的时间总和，各取10分钟后为20分钟即0.33小时，单位为小时
        Float append = 0.33f;
        List<Airline> airlineList = airlineDao.findList(departure,destination,brandId,typeId,month);
        if(airlineList.size() == 1){
            timeCost =  airlineList.get(0).getTimeCost();
        } else if(airlineList.size() > 1) {
            Float sum = 0f;
            for(Airline airline : airlineList){
                sum = sum + airline.getTimeCost();
            }
            timeCost = sum/airlineList.size();
        } else if(airlineList.size() <= 0){
            // 计算两个经纬度间的距离
            double d =  commonService.distance(departure.getLongitude(), departure.getLatitude(), destination.getLongitude(), destination.getLatitude());

            // 时间
            if(cruisingSpeed <= 0){
                timeCost = 0f;
            } else {
                timeCost = (float)(d/1000/cruisingSpeed);
            }
        }
        timeCost = timeCost + append;
        DecimalFormat df = new DecimalFormat("##0.00");
        timeCost = Float.valueOf(df.format(timeCost));
        return  timeCost;
    }
}
