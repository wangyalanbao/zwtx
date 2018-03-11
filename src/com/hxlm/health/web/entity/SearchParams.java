package com.hxlm.health.web.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * 预订搜索内容
 * Created by zyr on 2015/12/30.
 */
public class SearchParams {
    // 起飞机场
    Airport start;
    // 降落机场
    Airport end;
    /** 起飞时间  */
    private Date takeoffTime;
    /** 乘客数  */
    private Integer passengerNum;
    /** 在始发机场停留天数 */
    private Integer stops;
    /** 排序 */
    private Integer order;

    public Integer getStops() {
        return stops;
    }

    public void setStops(Integer stops) {
        this.stops = stops;
    }

    public Airport getStart() {
        return start;
    }

    public void setStart(Airport start) {
        this.start = start;
    }

    public Airport getEnd() {
        return end;
    }

    public void setEnd(Airport end) {
        this.end = end;
    }

    public Date getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(Date takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

    public Integer getPassengerNum() {
        return passengerNum;
    }

    public void setPassengerNum(Integer passengerNum) {
        this.passengerNum = passengerNum;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
