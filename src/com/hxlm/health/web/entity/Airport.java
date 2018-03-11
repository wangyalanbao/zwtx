package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by guofeng on 2015/12/10.
 * entity--机场数据
 */
@Entity
@Table(name = "lm_airport")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_airport_sequence")
public class Airport extends OrderEntity {

    private static final long serialVersionUID = -5452059678123589930L;

    public enum Location {
        //0国内机场
        interior,
        //1国外机场
        external

    }

    /*机场四字码*/
    private String code_4;
    /*机场三字码*/
    private String code_3;
    /*机场名*/
    private String name;
    /*机场拼音*/
    private String pinyin;
    /*拼音缩写*/
    private String pShort;
    /*所在城市  中文名*/
    private String city;
    /*所在地区  中文名*/
    private String area;
    /*所在城市  英文名*/
    private String cityEnglish;
    /*所在地区 英文名*/
    private String areaEnglish;
    /*国内外机场 */
    private Location locations;
    /*经度*/
    private double longitude;
    /*纬度*/
    private double latitude;
    /*磁偏角*/
    private Float magVariation;
    /*标高 */
    private Integer meanSeaLevel;
    /*机场维护费，地面代理费*/
    private BigDecimal maintenanceCost;
    /*调机费*/
    private BigDecimal transferCost;
    /*延伸代理服务费用*/
    private BigDecimal agencyCost;
    /*境外航空器入境费用*/
    private BigDecimal overseasEnterCost;
    /*导航费用*/
    private BigDecimal navigationCost;
    /*机组、乘客配餐费用*/
    private BigDecimal cateringCost;
    /*机组交通费用*/
    private BigDecimal trafficCost;
    /*国际航班三关服务费用*/
    private BigDecimal customsCost;
    /*起降、停场费*/
    private BigDecimal siteCost;
    /*FBO、贵宾通道使用费*/
    private BigDecimal guestGalleryCost;
    /*场内摆渡费用*/
    private BigDecimal ferryCost;
    /*引导车、推车、地面勤务费用*/
    private BigDecimal groundServiceCost;
    /*贵宾休息室使用费用*/
    private BigDecimal guestLobbyCost;
    /*基地的飞机*/
    private Set<Airplane> airportId = new HashSet<Airplane>();
    /*有虚拟基地的飞机*/
    private Set<Airplane> airplanes = new HashSet<Airplane>();

    /* 是否虚拟基地 */
    private Boolean isVirtual;

    /*地面代理费*/
    private InlandCost inlandCost;

    @ManyToOne(fetch = FetchType.LAZY)
    public InlandCost getInlandCost() {
        return inlandCost;
    }

    public void setInlandCost(InlandCost inlandCost) {
        this.inlandCost = inlandCost;
    }

    @JsonProperty
    @ManyToMany(mappedBy = "airports", fetch = FetchType.LAZY)
    public Set<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(Set<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    @JsonProperty
    @OneToMany(mappedBy = "airportId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Airplane> getAirportId() {
        return airportId;
    }

    public void setAirportId(Set<Airplane> airportId) {
        this.airportId = airportId;
    }

    @JsonProperty
    @Length(max = 4)
    public String getCode_4() {
        return code_4;
    }

    public void setCode_4(String code_4) {
        this.code_4 = code_4;
    }

    @JsonProperty
    @Length(max = 3)
    public String getCode_3() {
        return code_3;
    }

    public void setCode_3(String code_3) {
        this.code_3 = code_3;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @JsonProperty
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @JsonProperty
    public String getpShort() {
        return pShort;
    }

    public void setpShort(String pShort) {
        this.pShort = pShort;
    }

    @JsonProperty
    public Location getLocations() {
        return locations;
    }

    public void setLocations(Location locations) {
        this.locations = locations;
    }

    @JsonProperty
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @JsonProperty
    public Float getMagVariation() {
        return magVariation;
    }

    public void setMagVariation(Float magVariation) {
        this.magVariation = magVariation;
    }

    @JsonProperty
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty
    public Integer getMeanSeaLevel() {
        return meanSeaLevel;
    }

    public void setMeanSeaLevel(Integer meanSeaLevel) {
        this.meanSeaLevel = meanSeaLevel;
    }

    @JsonProperty
    public BigDecimal getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(BigDecimal maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    @JsonProperty
    public BigDecimal getAgencyCost() {
        return agencyCost;
    }

    public void setAgencyCost(BigDecimal agencyCost) {
        this.agencyCost = agencyCost;
    }

    @JsonProperty
    public BigDecimal getOverseasEnterCost() {
        return overseasEnterCost;
    }

    public void setOverseasEnterCost(BigDecimal overseasEnterCost) {
        this.overseasEnterCost = overseasEnterCost;
    }

    @JsonProperty
    public BigDecimal getNavigationCost() {
        return navigationCost;
    }

    public void setNavigationCost(BigDecimal navigationCost) {
        this.navigationCost = navigationCost;
    }

    @JsonProperty
    public BigDecimal getCateringCost() {
        return cateringCost;
    }

    public void setCateringCost(BigDecimal cateringCost) {
        this.cateringCost = cateringCost;
    }

    @JsonProperty
    public BigDecimal getTrafficCost() {
        return trafficCost;
    }

    public void setTrafficCost(BigDecimal trafficCost) {
        this.trafficCost = trafficCost;
    }

    @JsonProperty
    public BigDecimal getCustomsCost() {
        return customsCost;
    }

    public void setCustomsCost(BigDecimal customsCost) {
        this.customsCost = customsCost;
    }

    @JsonProperty
    public BigDecimal getSiteCost() {
        return siteCost;
    }

    public void setSiteCost(BigDecimal siteCost) {
        this.siteCost = siteCost;
    }

    @JsonProperty
    public BigDecimal getGuestGalleryCost() {
        return guestGalleryCost;
    }

    public void setGuestGalleryCost(BigDecimal guestGalleryCost) {
        this.guestGalleryCost = guestGalleryCost;
    }

    @JsonProperty
    public BigDecimal getFerryCost() {
        return ferryCost;
    }

    public void setFerryCost(BigDecimal ferryCost) {
        this.ferryCost = ferryCost;
    }

    @JsonProperty
    public BigDecimal getGroundServiceCost() {
        return groundServiceCost;
    }

    public void setGroundServiceCost(BigDecimal groundServiceCost) {
        this.groundServiceCost = groundServiceCost;
    }

    @JsonProperty
    public BigDecimal getGuestLobbyCost() {
        return guestLobbyCost;
    }

    public void setGuestLobbyCost(BigDecimal guestLobbyCost) {
        this.guestLobbyCost = guestLobbyCost;
    }

    @JsonProperty
    public Boolean getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Boolean isVirtual) {
        this.isVirtual = isVirtual;
    }
    @JsonProperty
    public String getCityEnglish() {
        return cityEnglish;
    }

    public void setCityEnglish(String cityEnglish) {
        this.cityEnglish = cityEnglish;
    }
    @JsonProperty
    public String getAreaEnglish() {
        return areaEnglish;
    }

    public void setAreaEnglish(String areaEnglish) {
        this.areaEnglish = areaEnglish;
    }
    @JsonProperty
    public BigDecimal getTransferCost() {
        return transferCost;
    }

    public void setTransferCost(BigDecimal transferCost) {
        this.transferCost = transferCost;
    }
}
