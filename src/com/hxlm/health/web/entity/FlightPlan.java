package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dengyang on 15/12/15.
 *
 * 航班计划
 */

@Entity
@Table(name = "lm_flight_plan")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_flight_plan_sequence")
public class FlightPlan extends OrderEntity {

    private static final long serialVersionUID = -8352199669987273774L;

    // 计划类型
    public enum Type {
        // 载客
        carry,
        // 调机
        tuning,
        // 维修
        service
    }

    // 时区
    public enum Timezone {
        // 北京
        beijing,
        // UTC
        utc
    }

    // 航段状态
    public enum FlightPlanStatus {
        // 用户可见
        usershow,
        // 用户不可见
        userhidden,
        // 已被预订
        reserve
    }



    // 始发机场名
    private String departure;

    /*始发机场ID*/
    private Airport departureId;

    /*终点机场ID*/
    private Airport destinationId;

    // 终点机场名
    private String destination;

    // 计划飞行时间
    private BigDecimal timeCost;

    // 计划类型
    private Type type;

    // 飞机
    private Airplane airplane;

    // 计划起飞时间
    private Date takeoffTime;

    // 计划降落时间
    private Date landingTime;

    // 时区
    private Timezone timezone;

    // 实际起飞时间
    private Date actualTakeoffTime;

    // 实际降落时间
    private Date actualLandingTime;

    // 实际飞行时间
    private BigDecimal flyingTime;

    // 航段状态
    private FlightPlanStatus status;

    // 所属订单
    private Long orderId;

    /*飞机型号*/
    private String airplaneType;

    //飞机注册号
    private String regNo;

    // 原价（调机时）
    private BigDecimal originalPrice;

    // 特价（调机时）
    private BigDecimal specialprice;
    //是否真实调机
    private Boolean isReal;
    //调机时间
    private String tuningDate;
    // 机组联系方式
    //private String stuffContact;
    // 机长
    //private String captain;
    // 副驾驶
    //private String copilot;
    // 乘务员
    //private String attendants;
    // 机务人员
    //private String stuff;
    // 添加时间
    //private Date addTime;
    // 修改时间
    //private Date modifyTime;

    @JsonProperty
    public String getTuningDate() {
        return tuningDate;
    }

    public void setTuningDate(String tuningDate) {
        this.tuningDate = tuningDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Airport getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Airport departureId) {
        this.departureId = departureId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Airport getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Airport destinationId) {
        this.destinationId = destinationId;
    }

    @JsonProperty
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }


    @JsonProperty
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @JsonProperty
    @Min(0)
    public BigDecimal getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(BigDecimal timeCost) {
        this.timeCost = timeCost;
    }

    @JsonProperty
    @Column(nullable = false)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    @JsonProperty
    public Date getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(Date takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

    @JsonProperty
    public Date getLandingTime() {
        return landingTime;
    }

    public void setLandingTime(Date landingTime) {
        this.landingTime = landingTime;
    }

    @JsonProperty
    public Timezone getTimezone() {
        return timezone;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

    @JsonProperty
    public Date getActualTakeoffTime() {
        return actualTakeoffTime;
    }

    public void setActualTakeoffTime(Date actualTakeoffTime) {
        this.actualTakeoffTime = actualTakeoffTime;
    }

    @JsonProperty
    public Date getActualLandingTime() {
        return actualLandingTime;
    }

    public void setActualLandingTime(Date actualLandingTime) {
        this.actualLandingTime = actualLandingTime;
    }

    @JsonProperty
    public BigDecimal getFlyingTime() {
        return flyingTime;
    }

    public void setFlyingTime(BigDecimal flyingTime) {
        this.flyingTime = flyingTime;
    }

    @JsonProperty
    public FlightPlanStatus getStatus() {
        return status;
    }

    public void setStatus(FlightPlanStatus status) {
        this.status = status;
    }
    @JsonProperty
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @JsonProperty
    public String getAirplaneType() {
        return airplaneType;
    }

    public void setAirplaneType(String airplaneType) {
        this.airplaneType = airplaneType;
    }

    @JsonProperty
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getSpecialprice() {
        return specialprice;
    }

    public void setSpecialprice(BigDecimal specialprice) {
        this.specialprice = specialprice;
    }
    @JsonProperty
    public Boolean getIsReal() {
        return isReal;
    }

    public void setIsReal(Boolean isReal) {
        this.isReal = isReal;
    }
}
