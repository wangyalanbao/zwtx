package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hxlm.health.web.DateEditor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by guofeng on 2015/12/9.
 * entity-飞机数据
 *
 */
@Entity
@Table(name = "lm_airplane")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_airplane_sequence")
public class Airplane extends OrderEntity {

    private static final long serialVersionUID = -6675579952388330826L;

    private String name;
    private String image;

    /*包机类型，计费模式*/
    public enum ChargeType {
        //全包
        full,
        //任务
        task
    }

    /*机型级别*/
    public enum TypeLevel {
        //中程
        middle,
        //中远程
        middleDistant,
        //远程
        distant,
        //超远程
        maxDistant
    }

    /*飞机品牌*/
    private String brand;

    /*飞机型号*/
    private String type;

    /*飞机注册号*/
    private String regNo;

    /*飞机注册号前缀*/
    private String regNos;

    /*注册号*/
    private String number;

    /*最大飞行速度（km/h)*/
    private BigDecimal speed;

    /*航程（km）*/
    private Integer voyage;

    /*座位数*/
    private Integer capacity;

    /*出厂年份*/
    private String manuDate;

    /*包机类型*/
    private ChargeType chargeType;

    /*空载价/h 国内调机单价（元/小时）*/
    private BigDecimal emptyPrice;

    /*包机价/h 国内载客单价（元/小时）*/
    private BigDecimal loadedPrice;

    /*空载价/h 国外调机单价（元/小时）*/
    private BigDecimal extEmptyPrice;

    /*包机价/h 国外载客单价（元/小时）*/
    private BigDecimal extLoadedPrice;

    /*机舱设备 */
    private String descend;

    /*最大起飞重量（kg）*/
    private BigDecimal maxTakeoffWeight;

    /*空载重量（kg） */
    private BigDecimal emptyWeight;

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

    /*引擎型号*/
    private String engineType;

    /*引擎类型*/
    private String engineModel;

    /*引擎制造商*/
    private String engineManufacturer;

    /*油箱容量 */
    private BigDecimal fuelTankCapacity;

    /*耗油量（L/km）*/
    private BigDecimal oilConsumption;

    /*最大功率（kW）*/
    private BigDecimal maxPower;

    /*最大转速（r/min）*/
    private Integer maxPowerSpeed;

    /*巡航速度（km/h）*/
    private BigDecimal cruisingSpeed;

    /*升限（m）*/
    private BigDecimal ceiling;

    /*最短起飞距离（m）*/
    private BigDecimal minTakeoffDistance;

    /*最短着陆距离（m）*/
    private BigDecimal minLandingDistance;

    /*飞机舒适性指标*/
    private BigDecimal comfort;

    /*飞机详情*/
    private String content;

    /*是否可搜索预订*/
    private Boolean isBooking;

    /*飞机品牌id号*/
    private PlaneBrand brandId;

    /*飞机型号id*/
    private PlaneType typeId;

    /*特价航段*/
    private AirlineApecial airlineApecial;

    /*所在机场*/
    private  Airport airportId;

    /*飞机描述图片*/
    private List<AirplaneImage> airplaneImages=new ArrayList<AirplaneImage>();

    /*所在虚拟基地*/
    private Set<Airport> airports = new HashSet<Airport>();

    /*航班计划*/
    private Set<FlightPlan> flightPlans = new HashSet<FlightPlan>();

    /*最低消费小时数*/
    private String minimumHour;

    /*机型级别*/
    private TypeLevel typeLevel;

    /* 订单 */
    private List<Order> orderList = new ArrayList<Order>();

    @JsonProperty
    public TypeLevel getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(TypeLevel typeLevel) {
        this.typeLevel = typeLevel;
    }

    @JsonProperty
    public String getMinimumHour() {
        return minimumHour;
    }

    public void setMinimumHour(String minimumHour) {
        this.minimumHour = minimumHour;
    }

    @JsonProperty
    @OneToMany(mappedBy = "airplane", fetch = FetchType.LAZY)
    public Set<FlightPlan> getFlightPlans() {
        return flightPlans;
    }

    public void setFlightPlans(Set<FlightPlan> flightPlans) {
        this.flightPlans = flightPlans;
    }

    @JsonProperty
    @OneToMany(mappedBy = "airplane", fetch = FetchType.LAZY)
    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    /*可能是飞机图片用,老表有*/
    private String imange;

    /*未知用途字段,老表有*/
    private Integer status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lm_airplane_base")
    public Set<Airport> getAirports() {
        return airports;
    }

    public void setAirports(Set<Airport> airports) {
        this.airports = airports;
    }

    @JsonProperty
    @Valid
    @ElementCollection
    @CollectionTable(name = "lm_airplane_image")
    public List<AirplaneImage> getAirplaneImages() {
        return airplaneImages;
    }

    public void setAirplaneImages(List<AirplaneImage> airplaneImages) {
        this.airplaneImages = airplaneImages;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    public Airport getAirportId() {
        return airportId;
    }

    public void setAirportId(Airport airportId) {
        this.airportId = airportId;
    }

    @OneToOne(mappedBy = "planeId",fetch = FetchType.LAZY)
    public AirlineApecial getAirlineApecial() {
        return airlineApecial;
    }

    public void setAirlineApecial(AirlineApecial airlineApecial) {
        this.airlineApecial = airlineApecial;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public PlaneType getTypeId() {
        return typeId;
    }

    public void setTypeId(PlaneType typeId) {
        this.typeId = typeId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public PlaneBrand getBrandId() {
        return brandId;
    }

    public void setBrandId(PlaneBrand brandId) {
        this.brandId = brandId;
    }
    @JsonProperty
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    @JsonProperty
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @JsonProperty
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
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
    public String getManuDate() {
        return manuDate;
    }

    public void setManuDate(String manuDate) {
        this.manuDate = manuDate;
    }

    @JsonProperty
    public ChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(ChargeType chargeType) {
        this.chargeType = chargeType;
    }
    @JsonProperty
    public BigDecimal getEmptyPrice() {
        return emptyPrice;
    }

    public void setEmptyPrice(BigDecimal emptyPrice) {
        this.emptyPrice = emptyPrice;
    }
    @JsonProperty
    public BigDecimal getLoadedPrice() {
        return loadedPrice;
    }

    public void setLoadedPrice(BigDecimal loadedPrice) {
        this.loadedPrice = loadedPrice;
    }
    @JsonProperty
    public BigDecimal getExtEmptyPrice() {
        return extEmptyPrice;
    }

    public void setExtEmptyPrice(BigDecimal extEmptyPrice) {
        this.extEmptyPrice = extEmptyPrice;
    }
    @JsonProperty
    public BigDecimal getExtLoadedPrice() {
        return extLoadedPrice;
    }

    public void setExtLoadedPrice(BigDecimal extLoadedPrice) {
        this.extLoadedPrice = extLoadedPrice;
    }

    @JsonProperty
    @Lob
    public String getDescend() {
        return descend;
    }

    public void setDescend(String descend) {
        this.descend = descend;
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
    @JsonProperty
    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @JsonProperty
    public BigDecimal getComfort() {
        return comfort;
    }

    public void setComfort(BigDecimal comfort) {
        this.comfort = comfort;
    }
    @JsonProperty
    public Boolean getIsBooking() {
        return isBooking;
    }

    public void setIsBooking(Boolean isBooking) {
        this.isBooking = isBooking;
    }

    @Transient
    public String getRegNos() {
        return regNos;
    }

    public void setRegNos(String regNos) {
        this.regNos = regNos;
    }

    @JsonProperty
    @Lob
    public String getImange() {
        return imange;
    }

    public void setImange(String imange) {
        this.imange = imange;
    }

    @JsonProperty
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Transient
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 获取缩略图
     *
     * @return 缩略图
     */
    @Transient
    public String getThumbnail() {
        if (getAirplaneImages() != null && !getAirplaneImages().isEmpty()) {
            return getAirplaneImages().get(0).getThumbnail();
        }
        return null;
    }

    /**
     * 获取缩略图
     *
     * @return 缩略图
     */
    @Transient
    public void prepareRegNo() {
        /***
         * $reg_no = trim($vo['reg_no']);
         if (substr($reg_no, 0, 2) == 'B-'){
         $pre_reg_no = 'B-';
         $reg_no = substr($reg_no, 2);
         }elseif(substr($reg_no, 0, 3) == 'VP-'){
         $pre_reg_no = 'VP-';
         $reg_no = substr($reg_no, 3);
         }elseif(substr($reg_no, 0, 1) == 'N'){
         $pre_reg_no = 'N';
         $reg_no = substr($reg_no, 1);
         }
         */
        String tmpRegNo = getRegNo().trim();
        if (tmpRegNo.substring(0, 2).equals("B-")) {
            setRegNos("B-");
            setNumber(tmpRegNo.substring(2));
        } else if (tmpRegNo.substring(0, 3).equals("VP-")) {
            setRegNos("VP-");
            setNumber(tmpRegNo.substring(3));
        } else if (tmpRegNo.substring(0, 1).equals("N")) {
            setRegNos("N-");
            setNumber(tmpRegNo.substring(1));
        } else {
            setRegNos("");
            setNumber(tmpRegNo);
        }
    }

    /**
     * 判断该飞机是否可预定
     * @param dateList
     * @return
     */
    @Transient
    public Boolean isEffective(List<Date> dateList){
        if(dateList.size() <= 0){
            return true;
        }
        Date start = dateList.get(0);
        Date end = dateList.get(dateList.size()-1);
        for(FlightPlan flightPlan : this.getFlightPlans()){
            Date startPlan = DateEditor.formatDate(flightPlan.getTakeoffTime(), DateEditor.C_DATE_PATTON_DEFAULT);
            Date endPlan = DateEditor.formatDate(flightPlan.getLandingTime(), DateEditor.C_DATE_PATTON_DEFAULT);
            if(DateEditor.daysBetween(endPlan, start) > 0){ // 该计划已完成
                continue;
            }
            if(DateEditor.daysBetween(end, startPlan) > 0){ // 该计划在需求之外
                continue;
            }
            return false;
        }
        for(Order order : this.getOrderList()){
            if(order.getOrderStatus() == Order.OrderStatus.cancelled){
                continue;
            }
            Date startPlan = DateEditor.formatDate(order.getFirstTakeoffTime(), DateEditor.C_DATE_PATTON_DEFAULT);
            Date loadDate = order.getLastTakeoffTime();
            if(order.getOrderAirlines().size()>0){
                Float v_tmp_hour = order.getOrderAirlines().get(order.getOrderAirlines().size()-1).getTimeCost();
                Calendar loadCalendar = Calendar.getInstance();
                loadCalendar.setTime(loadDate);
                int minutes = (int)(v_tmp_hour*60);
                loadCalendar.add(Calendar.MINUTE, minutes);
                loadDate = loadCalendar.getTime();
            }

            Date endPlan = DateEditor.formatDate(loadDate, DateEditor.C_DATE_PATTON_DEFAULT);

            if(DateEditor.daysBetween(endPlan, start) > 0){ // 该计划已完成
                continue;
            }
            if(DateEditor.daysBetween(end, startPlan) > 0){ // 该计划在需求之外
                continue;
            }
            return false;
        }
        return true;
    }

}
