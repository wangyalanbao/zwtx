package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.DateEditor;
import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.Setting;
import com.hxlm.health.web.dao.AirportDao;
import com.hxlm.health.web.entity.Airplane;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.entity.SearchParams;
import com.hxlm.health.web.service.AirlineService;
import com.hxlm.health.web.service.AirportService;
import com.hxlm.health.web.service.CommonService;
import com.hxlm.health.web.util.SettingUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by guofeng on 2015/12/14.
 */
@Service("commonServiceImpl")
public class CommonServiceImpl implements CommonService {

    @Resource(name = "airportServiceImpl")
    private AirportService airportService;
    @Resource(name = "airlineServiceImpl")
    private AirlineService airlineService;


    /**
     * 航段报价
     * @param searchParamsList
     * 		     航段集合
     * @param airplane
     * 			飞机
     * @return
     */
    public BigDecimal price(List<SearchParams> searchParamsList, Airplane airplane){
        BigDecimal totalAmount = new BigDecimal(0);
        // 总时间
        Float v_total_hour = 0f;
        // 国内空载时间
        Float int_empty_hour = 0f;
        // 国内载客时间
        Float int_loaded_hour = 0f;
        // 国外空载时间
        Float ext_empty_hour = 0f;
        // 国外载客时间
        Float ext_loaded_hour = 0f;
        BigDecimal totalMaintenance = new BigDecimal(0);
        // 调正天数
        int  stopDay = 0;
        // 上一个航段的飞行时长
        float beforeLoadTime = 0f;

        // 飞机的虚拟基地
        Set<Airport> airportList = airplane.getAirports();

        for(int i = 0; i < searchParamsList.size(); i++){
            Float v_tmp_hour = 0f;
            Float v_tmp_hour2 = 0f;
            Float v_tmp_hour3 = 0f;
            SearchParams searchParams = searchParamsList.get(i);
            Airport start = searchParams.getStart();
            Airport end = searchParams.getEnd();
            // 起飞时间
            Date date =  searchParams.getTakeoffTime();
            Calendar takeoffCalendar = Calendar.getInstance();
            takeoffCalendar.setTime(date);
            // 始发机场不是虚拟基地
            if(!airportList.contains(searchParams.getStart())){
                // 距始发机场最近的虚拟基地
                Airport base = airportService.findNearestAirport(start, airportList);
                if(i == 0){ // 第一个航段
                    // 将飞机从最近虚拟基地调到始发机场的飞行时间
                    v_tmp_hour = airlineService.lineHour(base, start, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
                    if(start.getLocations() == Airport.Location.external || base.getLocations() == Airport.Location.external){
                        ext_empty_hour = ext_empty_hour + v_tmp_hour;
                        v_total_hour = v_total_hour + v_tmp_hour;
                    } else {
                        int_empty_hour = int_empty_hour + v_tmp_hour;
                        v_total_hour = v_total_hour + v_tmp_hour;
                    }
                    // 航段任务飞行时间
                    v_tmp_hour3 = airlineService.lineHour(start, end, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
                    if(start.getLocations() == Airport.Location.external || end.getLocations() == Airport.Location.external){
                        ext_loaded_hour = ext_loaded_hour + v_tmp_hour3;
                        v_total_hour = v_total_hour + v_tmp_hour3;
                    } else {
                        int_loaded_hour = int_loaded_hour + v_tmp_hour3;
                        v_total_hour = v_total_hour + v_tmp_hour3;
                    }
                    beforeLoadTime = v_tmp_hour3;
                } else { // 非第一个航段

                    // 上一个航段起飞时间
                    Date beforeTakeoffDate = searchParamsList.get(i-1).getTakeoffTime();
                    // 降落时间
                    Calendar loadCalendar = Calendar.getInstance();
                    loadCalendar.setTime(beforeTakeoffDate);
                    int minutes = (int)(beforeLoadTime*60);
                    loadCalendar.add(Calendar.MINUTE, minutes);
                    //停留天数
                    int days = DateEditor.daysBetween(loadCalendar.getTime(), searchParams.getTakeoffTime());
                    // 调机时间
                    Integer oldMonth = loadCalendar.get(Calendar.MONTH);
                    // 将飞机从始发机场调回最近虚拟基地的飞行时间(这是第一步)
                    v_tmp_hour2 = airlineService.lineHour(start, base, null, airplane.getTypeId(), oldMonth, Double.valueOf(airplane.getCruisingSpeed().toString()));
                    // 将飞机从最近虚拟基地调到始发机场的飞行时间（这是第二步）
                    v_tmp_hour = airlineService.lineHour(base, start, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
                    // 计算调机费用
                    BigDecimal airlinePrice = new BigDecimal(0);
                    if(start.getLocations() == Airport.Location.external || base.getLocations() == Airport.Location.external){
                        airlinePrice = airplane.getExtEmptyPrice().multiply(new BigDecimal(v_tmp_hour2 + v_tmp_hour));
                    } else {
                        airlinePrice = airplane.getEmptyPrice().multiply(new BigDecimal(v_tmp_hour2 + v_tmp_hour));
                    }
                    // 地面费用
                    BigDecimal maintenanceCost = start.getMaintenanceCost();
                    // 如果调机费用小于停留的费用
                    if(airlinePrice.compareTo(maintenanceCost.multiply(new BigDecimal(2))) < 0){
                        if(start.getLocations() == Airport.Location.external || base.getLocations() == Airport.Location.external){
                            ext_empty_hour = ext_empty_hour + v_tmp_hour2 + v_tmp_hour;
                            v_total_hour = v_total_hour + v_tmp_hour2 + v_tmp_hour;
                        } else {
                            int_empty_hour = int_empty_hour + v_tmp_hour2 + v_tmp_hour;
                            v_total_hour = v_total_hour + v_tmp_hour2 + v_tmp_hour;
                        }
                    } else {
                        stopDay = stopDay  + days;
                        totalMaintenance = totalMaintenance.add(maintenanceCost);
                    }

                    // 航段任务飞行时间
                    v_tmp_hour3 = airlineService.lineHour(start, end, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
                    if(start.getLocations() == Airport.Location.external || end.getLocations() == Airport.Location.external){
                        ext_loaded_hour = ext_loaded_hour + v_tmp_hour3;
                        v_total_hour = v_total_hour + v_tmp_hour3;
                    } else {
                        int_loaded_hour = int_loaded_hour + v_tmp_hour3;
                        v_total_hour = v_total_hour + v_tmp_hour3;
                    }
                    beforeLoadTime = v_tmp_hour3;
                }
            } else { // 始发机场是虚拟基地
                // 航段任务飞行时间
                v_tmp_hour3 = airlineService.lineHour(start, end, null, airplane.getTypeId(), takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
                if(start.getLocations() == Airport.Location.external || end.getLocations() == Airport.Location.external){
                    ext_loaded_hour = ext_loaded_hour + v_tmp_hour3;
                    v_total_hour = v_total_hour + v_tmp_hour3;
                } else {
                    int_loaded_hour = int_loaded_hour + v_tmp_hour3;
                    v_total_hour = v_total_hour + v_tmp_hour3;
                }

                // 不是第一航段
                if(i>0){
                    // 上一个航段起飞时间
                    Date beforeTakeoffDate = searchParamsList.get(i-1).getTakeoffTime();
                    // 降落时间
                    Calendar loadCalendar = Calendar.getInstance();
                    loadCalendar.setTime(beforeTakeoffDate);
                    int minutes = (int)(beforeLoadTime*60);
                    loadCalendar.add(Calendar.MINUTE, minutes);
                    //停留天数
                    int days = DateEditor.daysBetween(loadCalendar.getTime(),searchParams.getTakeoffTime());
                    stopDay = stopDay  + days;
                }

                beforeLoadTime = v_tmp_hour3;
            }
        }

        Airport endAirport = searchParamsList.get(searchParamsList.size()-1).getEnd();
        // 终点机场不是虚拟基地
        if(endAirport.getIsVirtual() == false){
            // 距终点机场最近的虚拟基地
            Airport base = airportService.findNearestAirport(endAirport, airportList);
            // 起飞时间
            Date date =  searchParamsList.get(searchParamsList.size()-1).getTakeoffTime();
            Calendar takeoffCalendar = Calendar.getInstance();
            takeoffCalendar.setTime(date);
            // 将飞机从始发机场调回最近虚拟基地的飞行时间(这是第一步)
            float tmp_hour = airlineService.lineHour(endAirport, base, null, airplane.getTypeId(),  takeoffCalendar.get(Calendar.MONTH), Double.valueOf(airplane.getCruisingSpeed().toString()));
            if(endAirport.getLocations() == Airport.Location.external || base.getLocations() == Airport.Location.external){
                ext_empty_hour = ext_empty_hour + tmp_hour;
                v_total_hour = v_total_hour + tmp_hour;
            } else {
                int_empty_hour = int_empty_hour + tmp_hour;
                v_total_hour = v_total_hour + tmp_hour;
            }
        }

        //旅程横跨的总天数
        int days = DateEditor.daysBetween(searchParamsList.get(0).getTakeoffTime(),searchParamsList.get(searchParamsList.size() - 1).getTakeoffTime());
        // 小于最低航行小时要求
        if(v_total_hour < days*2){
            DecimalFormat df = new DecimalFormat("#.00");
            int_loaded_hour = int_loaded_hour + Float.valueOf(df.format((days*2 - v_total_hour)));
        }

        totalAmount = airplane.getEmptyPrice().multiply(new BigDecimal(int_empty_hour)) //国内空载费用
                .add(airplane.getExtEmptyPrice().multiply(new BigDecimal(ext_empty_hour))) // 国外空载费用
                .add(airplane.getLoadedPrice().multiply(new BigDecimal(int_loaded_hour)) // 国内载客费用
                        .add(airplane.getExtLoadedPrice().multiply(new BigDecimal(ext_loaded_hour)))); // 国外载客费用
        DecimalFormat df = new DecimalFormat("#");

        return  new BigDecimal(df.format(totalAmount));
    }

    /**
     * 计算两个经纬度间的距离
     * @param lng1 经度1
     * @param lat1 纬度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return
     */
    public double distance(double lng1, double lat1, double lng2, double lat2){
        // 根据经纬度拉线计算两机场距离后附加的修正值
        Setting setting = SettingUtils.get();
        Float p_offset = setting.getpOffset();

        double a, b, R;
        R = 6367000; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (lng1 - lng2) * Math.PI / 180.0;
        // 距离
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2))*(1+p_offset);
        return d;
    }


}
