package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by guofeng on 2015/12/9.
 * ENtity--报价航段
 */
@Entity
@Table(name = "lm_quote_airline")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_quote_airline_sequence")
public class QuoteAirline extends OrderEntity{

    private static final long serialVersionUID = 4872365222931316773L;
    /*飞行时长*/
    private Float timeCost;

    /*始发机场ID*/
    private Long departureId;

    /*始发机场*/
    private Airport departure;

    /*终点机场Id*/
    private Long destinationId;

    /*终点机场*/
    private Airport destination;

    /*中转机场d*/
    private Long centerId;

    /** 中转机场 */
    private Airport center;

    /** 起飞时间  */
    private Date takeoffTime;

    /** 所属报价 */
    private Quote quote;

    /** 是否为特价航段 1为特价；0=任务 */
    private Boolean isSpecial;

    /** 乘客数 */
    private Integer passengerNum;

    /**
     * 调机类型
     */
    public enum SpecialType{
        /** 前调机 */
        before,
        /** 后调机 */
        after,
        /** 连续航段间调机 */
        succession,
        /** 间断航段间调机 */
        interrupted,
        /** 增补间断航段调机 */
        connect,
    }

    /** 调机类型 */
    private SpecialType specialType;

    /** 是否删除 */
    private Boolean isDelete;

    @JsonProperty
    public Long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Long departureId) {
        this.departureId = departureId;
    }

    @JsonProperty
    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    @JsonProperty
    @ManyToOne(fetch = FetchType.LAZY)
    public Airport getDeparture() {
        return departure;
    }

    public void setDeparture(Airport departure) {
        this.departure = departure;
    }

    @JsonProperty
    @ManyToOne(fetch = FetchType.LAZY)
    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    @JsonProperty
    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    @JsonProperty
    @ManyToOne(fetch = FetchType.LAZY)
    public Airport getCenter() {
        return center;
    }

    public void setCenter(Airport center) {
        this.center = center;
    }

    @JsonProperty
    public Float getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Float timeCost) {
        this.timeCost = timeCost;
    }

    @JsonProperty
    public Date getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(Date takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

    @JsonProperty
    @ManyToOne(fetch = FetchType.LAZY)
    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    @JsonProperty
    public Boolean getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    @JsonProperty
    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @JsonProperty
    public SpecialType getSpecialType() {
        return specialType;
    }

    public void setSpecialType(SpecialType specialType) {
        this.specialType = specialType;
    }

    @JsonProperty
    public Integer getPassengerNum() {
        return passengerNum;
    }

    public void setPassengerNum(Integer passengerNum) {
        this.passengerNum = passengerNum;
    }

    /**
     * 获取飞行小时单价
     * @return
     */
    @JsonProperty
    @Transient
    public BigDecimal getUnitPrice() {
        BigDecimal unitPrice = new BigDecimal(0);
        if(this.getIsSpecial()){ // 调机
            if(!getIsExternal()){
                unitPrice = this.getQuote().getAirplane().getEmptyPrice();
            } else {
                unitPrice = this.getQuote().getAirplane().getExtEmptyPrice();
            }
        } else { // 任务
            if(!getIsExternal()){
                unitPrice = this.getQuote().getAirplane().getLoadedPrice();
            } else {
                unitPrice = this.getQuote().getAirplane().getExtLoadedPrice();
            }
        }
        return unitPrice;
    }

    /**
     * 获取地面代理费
     * @return
     */
    @JsonProperty
    @Transient
    public BigDecimal getMaintenanceCost() {
        BigDecimal maintenanceCost = new BigDecimal(0);
        if(this.getIsSpecial()){ // 调机
            if(this.getSpecialType() == SpecialType.before || this.getSpecialType() == SpecialType.after || this.getSpecialType() == SpecialType.connect){
                maintenanceCost = maintenanceCost.add(this.getDeparture().getInlandCost().getTransferCost()).add(this.getDestination().getInlandCost().getTransferCost());
            } else {
                maintenanceCost = maintenanceCost.add(this.getDeparture().getInlandCost().getTransferCost()).add(this.getDestination().getInlandCost().getTransferCost())
                        .add(this.getCenter().getInlandCost().getTransferCost().multiply(new BigDecimal(2)));
            }

        } else { // 任务
            maintenanceCost = maintenanceCost.add(this.getDeparture().getInlandCost().getAgencyCost()).add(this.getDestination().getInlandCost().getAgencyCost());
        }

        return maintenanceCost;
    }

    /**
     * 获取飞行费用
     * @return
     */
    @JsonProperty
    @Transient
    public BigDecimal getFlyingCost(){
        BigDecimal flyingCost = new BigDecimal(0);
        if(this.getIsSpecial()){ // 调机
            if(!getIsExternal()){
                flyingCost = flyingCost.add(this.getQuote().getAirplane().getEmptyPrice().multiply(new BigDecimal(this.getTimeCost())));
            } else {
                flyingCost = flyingCost.add(this.getQuote().getAirplane().getExtEmptyPrice().multiply(new BigDecimal(this.getTimeCost())));
            }
        } else { // 任务
            if(!getIsExternal()){
                flyingCost = flyingCost.add(this.getQuote().getAirplane().getLoadedPrice().multiply(new BigDecimal(this.getTimeCost())));
            } else {
                flyingCost = flyingCost.add(this.getQuote().getAirplane().getExtLoadedPrice().multiply(new BigDecimal(this.getTimeCost())));
            }
        }
            return flyingCost;
    }

    /**
     * 是否国外航段
     * @return
     */
    @JsonProperty
    @Transient
    public Boolean getIsExternal(){
        if(this.getDeparture().getLocations() == Airport.Location.interior && this.getDestination().getLocations() == Airport.Location.interior){
            return false;
        } else {
            return true;
        }
    }

}
