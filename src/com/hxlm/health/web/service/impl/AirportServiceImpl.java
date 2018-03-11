package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.Page;
import com.hxlm.health.web.Pageable;
import com.hxlm.health.web.dao.AirportDao;
import com.hxlm.health.web.entity.Airport;
import com.hxlm.health.web.service.AirportService;
import com.hxlm.health.web.service.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by guofeng on 2015/12/14.
 */
@Service("airportServiceImpl")
public class AirportServiceImpl extends BaseServiceImpl<Airport,Long> implements AirportService {

    @Resource(name = "airportDaoImpl")
    private AirportDao airportDao;
    @Resource(name = "airportDaoImpl")
    public void setBaseDao(AirportDao airportDao) {super.setBaseDao(airportDao);}
    @Resource(name = "commonServiceImpl")
    private CommonService commonService;

    @Transactional(readOnly = true)
    public  Page<Airport> findPage(Boolean isVirtual, Pageable pageable) {
        return airportDao.findPage(isVirtual, pageable);
    }

    public List<Airport> findIsVirtual(){
        return airportDao.findByIsVirtual();
    }

    /**
     * 模糊查询
     *
     * @param key
     * @param count
     * @return
     */
    @Override
    public List<Airport> search(String key, Integer count) {
        return airportDao.search(key, count);
    }

    /**
     * 距机场最近的虚拟基地
     */
    public Airport findNearestAirport(Airport airport, Set<Airport> airports){
        Airport tempAirport = new Airport();
        double distance = 0;
        for(Airport base : airports){
            double tempDistance = commonService.distance(airport.getLongitude(), airport.getLatitude(), base.getLongitude(), base.getLatitude());
            if(distance == 0 || distance > tempDistance){
                distance = tempDistance;
                tempAirport = base;
            }
        }
        return  tempAirport;
    }

    /**
     * 获取中心虚拟基地
     * @param start
     * @param end
     * @param airports
     * @return
     */
    public Airport findCenterAirport(Airport start, Airport end, Set<Airport> airports){
        Airport tempAirport = new Airport();
        double distance =0;
        for(Airport base : airports){
            double distance1 = commonService.distance(start.getLongitude(), start.getLatitude(), base.getLongitude(), base.getLatitude());
            double distance2 = commonService.distance(end.getLongitude(), end.getLatitude(), base.getLongitude(), base.getLatitude());
            double tempDistance = distance1 + distance2;
            if(distance == 0 || distance > tempDistance){
                distance = tempDistance;
                tempAirport = base;
            }
        }

        return  tempAirport;
    }

    /**
     * 查询所有中国机场
     */
    @Transactional(readOnly = true)
    public List<Airport> findByInland(){
        return airportDao.findByInland();
    }
    /**
     * 查询所有中国机场 地面代理外键空
     */
    @Transactional(readOnly = true)
    public List<Airport> findByInlandCostNull(){
        return airportDao.findByInlandCostNull();
    }

    /**
     * 查询所有国外机场
     */
    @Transactional(readOnly = true)
    public List<Airport> findByForeign(){
        return airportDao.findByForeign();
    }

    /**
     * 查询所有国外机场 地面代理外键空
     */
    @Transactional(readOnly = true)
    public List<Airport> findByForeignCostNull(){
        return airportDao.findByForeignCostNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Airport> findByArea(String area) {
        return airportDao.findByArea(area);
    }

    @Transactional(readOnly = true)
    public List<Airport> findByAreas(List<String> areas) {
        return airportDao.findByAreas(areas);
    }
    /**
     * 用城市名查询机场
     * @param citys
     * @return
     */
    @Transactional(readOnly = true)
    public List<Airport> findByCitys(List<String> citys){
        return airportDao.findByCitys(citys);
    }
}
