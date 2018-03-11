package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by guofeng on 2015/12/9.
 * ENTITY --飞机型号
 */
@Entity
@Table(name = "lm_plane_type")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_plane_type_sequence")
public class PlaneType extends OrderEntity {

    private static final long serialVersionUID = 3782148823413237196L;

    /*飞机品牌id号*/
    private PlaneBrand brandId;
    /*型号名称*/
    private String typeName;

    /*座位数*/
    private Integer capacity;
    /*最大起飞重量（kg）*/
    private BigDecimal maxTakeoffWeight;
    /*空载重量（kg） */
    private BigDecimal emptyWeight;
    /*出厂年份*/
    private Date manuDate;
    /*优点*/
    private String advantage;
    /*缺点*/
    private String shortage;
    /*机身长度（m）*/
    private BigDecimal lengths;
    /*机身宽度（m）*/
    private BigDecimal width;
    /*机身高度（m） */
    private BigDecimal height;
    /*客舱长度（m）*/
    private BigDecimal passengerCabinLength;
    /*客舱宽度（m）*/
    private BigDecimal passengerCabinWidth;
    /*客舱高度（m）*/
    private BigDecimal passengerCabinHeight;
    /*行李舱容量（m³）*/
    private BigDecimal luggageCompartment;
    /*发动机型号引擎型号*/
    private String engineType;
    /*发动机类型引擎类型*/
    private String engineModel;
    /*发动机引擎制造商*/
    private String engineManufacturer;
    /*油箱容量 */
    private BigDecimal fuelTankCapacity;
    /*耗油量（L/km）*/
    private BigDecimal oilConsumption;
    /*最大功率（kW）*/
    private BigDecimal maxPower;
    /*最大转速（r/min）*/
    private Integer maxPowerSpeed;
    /*最大飞行速度（km/h)*/
    private BigDecimal speed;
    /*航程（km）*/
    private Integer voyage;
    /*升限（m）*/
    private BigDecimal ceiling;
    /*最短起飞距离（m）*/
    private BigDecimal minTakeoffDistance;
    /*最短着陆距离（m）*/
    private BigDecimal minLandingDistance;
    /*巡航速度（km/h）*/
    private BigDecimal cruisingSpeed;
    /*机舱设备*/
    private String descend;

    @JsonProperty
    @Lob
    public String getDescend() {
        return descend;
    }

    public void setDescend(String descend) {
        this.descend = descend;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public PlaneBrand getBrandId() {
        return brandId;
    }

    public void setBrandId(PlaneBrand brandId) {
        this.brandId = brandId;
    }
    @JsonProperty
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @JsonProperty
    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }
    @JsonProperty
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    @JsonProperty
    public Integer getVoyage() {
        return voyage;
    }

    public void setVoyage(Integer voyage) {
        this.voyage = voyage;
    }

    @JsonProperty
    public BigDecimal getMaxTakeoffWeight() {
        return maxTakeoffWeight;
    }

    public void setMaxTakeoffWeight(BigDecimal maxTakeoffWeight) {
        this.maxTakeoffWeight = maxTakeoffWeight;
    }
    @JsonProperty
    public BigDecimal getEmptyWeight() {
        return emptyWeight;
    }

    public void setEmptyWeight(BigDecimal emptyWeight) {
        this.emptyWeight = emptyWeight;
    }

    public Date getManuDate() {
        return manuDate;
    }

    public void setManuDate(Date manuDate) {
        this.manuDate = manuDate;
    }

    @JsonProperty
    @Lob
    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }
    @JsonProperty
    @Lob
    public String getShortage() {
        return shortage;
    }

    public void setShortage(String shortage) {
        this.shortage = shortage;
    }

    @JsonProperty
    public BigDecimal getLengths() {
        return lengths;
    }

    public void setLengths(BigDecimal lengths) {
        this.lengths = lengths;
    }

    @JsonProperty
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    @JsonProperty
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @JsonProperty
    public BigDecimal getPassengerCabinLength() {
        return passengerCabinLength;
    }

    public void setPassengerCabinLength(BigDecimal passengerCabinLength) {
        this.passengerCabinLength = passengerCabinLength;
    }

    @JsonProperty
    public BigDecimal getPassengerCabinWidth() {
        return passengerCabinWidth;
    }

    public void setPassengerCabinWidth(BigDecimal passengerCabinWidth) {
        this.passengerCabinWidth = passengerCabinWidth;
    }

    @JsonProperty
    public BigDecimal getLuggageCompartment() {
        return luggageCompartment;
    }

    public void setLuggageCompartment(BigDecimal luggageCompartment) {
        this.luggageCompartment = luggageCompartment;
    }

    @JsonProperty
    public BigDecimal getPassengerCabinHeight() {
        return passengerCabinHeight;
    }

    public void setPassengerCabinHeight(BigDecimal passengerCabinHeight) {
        this.passengerCabinHeight = passengerCabinHeight;
    }
    @JsonProperty
    @Lob
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }
    @JsonProperty
    @Lob
    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }
    @JsonProperty
    @Lob
    public String getEngineManufacturer() {
        return engineManufacturer;
    }

    public void setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
    }
    @JsonProperty
    public BigDecimal getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(BigDecimal fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }
    @JsonProperty
    public BigDecimal getOilConsumption() {
        return oilConsumption;
    }

    public void setOilConsumption(BigDecimal oilConsumption) {
        this.oilConsumption = oilConsumption;
    }
    @JsonProperty
    public BigDecimal getMaxPower() {
        return maxPower;
    }

    public void setMaxPower(BigDecimal maxPower) {
        this.maxPower = maxPower;
    }
    @JsonProperty
    public Integer getMaxPowerSpeed() {
        return maxPowerSpeed;
    }

    public void setMaxPowerSpeed(Integer maxPowerSpeed) {
        this.maxPowerSpeed = maxPowerSpeed;
    }
    @JsonProperty
    public BigDecimal getCeiling() {
        return ceiling;
    }

    public void setCeiling(BigDecimal ceiling) {
        this.ceiling = ceiling;
    }
    @JsonProperty
    public BigDecimal getCruisingSpeed() {
        return cruisingSpeed;
    }

    public void setCruisingSpeed(BigDecimal cruisingSpeed) {
        this.cruisingSpeed = cruisingSpeed;
    }
    @JsonProperty
    public BigDecimal getMinLandingDistance() {
        return minLandingDistance;
    }

    public void setMinLandingDistance(BigDecimal minLandingDistance) {
        this.minLandingDistance = minLandingDistance;
    }
    @JsonProperty
    public BigDecimal getMinTakeoffDistance() {
        return minTakeoffDistance;
    }

    public void setMinTakeoffDistance(BigDecimal minTakeoffDistance) {
        this.minTakeoffDistance = minTakeoffDistance;
    }


}
