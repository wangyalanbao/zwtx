package com.hxlm.health.web.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity--订单航段变更临时表
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_order_airline_cache")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_airline_cache_sequence")
public class OrderAirlineCache extends OrderEntity {

    private static final long serialVersionUID = -4496926000388914789L;
    /** 订单ID */
    private Long orderId;
    /** 订单航段ID */
    private Long orderAirlineId;
    /** 始发机场ID */
    private Long departureId;
    /** 始发机场名 */
    private String departure;
    /** 终点机场ID */
    private Long destinationId;
    /** 终点机场名  */
    private String destination;
    /** 参考飞行时间 单位：小时 */
    private Float timeCost;
    /** 起飞时间  */
    private Date takeoffTime;
    /** 乘客数  */
    private Integer passengerNum;
    /** 预处理状态（0=未处理；1=已处理） */
    private Boolean status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderAirlineId() {
        return orderAirlineId;
    }

    public void setOrderAirlineId(Long orderAirlineId) {
        this.orderAirlineId = orderAirlineId;
    }

    public Long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Long departureId) {
        this.departureId = departureId;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Float getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Float timeCost) {
        this.timeCost = timeCost;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
