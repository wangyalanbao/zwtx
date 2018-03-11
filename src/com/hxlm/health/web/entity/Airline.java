package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by guofeng on 2015/12/9.
 * ENtity--航线数据
 */
@Entity
@Table(name = "lm_airline")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_airline_sequence")
public class Airline extends OrderEntity{

    private static final long serialVersionUID = 5769581775620508267L;
    /*耗时飞行时长*/
    private Float timeCost;

    /*飞行月份*/
    private Integer month;

    /*是否真实录入（0=真实；1=虚拟）*/
    private Boolean isVirtual;

    /*始发机场ID*/
    private Airport departureId;

    /*终点机场ID*/
    private Airport destinationId;

    /*飞机品牌id号*/
    private PlaneBrand brandId;

    /*飞机型号id*/
    private PlaneType typeId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    public PlaneBrand getBrandId() {
        return brandId;
    }

    public void setBrandId(PlaneBrand brandId) {
        this.brandId = brandId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public PlaneType getTypeId() {
        return typeId;
    }

    public void setTypeId(PlaneType typeId) {
        this.typeId = typeId;
    }

    @JsonProperty
    public Float getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Float timeCost) {
        this.timeCost = timeCost;
    }

    @JsonProperty
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @JsonProperty
    public Boolean getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Boolean isVirtual) {
        this.isVirtual = isVirtual;
    }


}
