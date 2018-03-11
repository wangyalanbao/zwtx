package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity--报价
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_quote")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_quote_sequence")
public class Quote extends OrderEntity {

    private static final long serialVersionUID = -5565055057670973033L;

    /** 飞行类型 */
    public enum PlanType{
        /** 任务-单程 */
        oneWay,
        /** 任务-往返 */
        returnWay,
        /** 任务-多程 */
        moreWay,
        /** 调机 */
        transfer
    }
    /** 确认状态 */
    public enum Status{
        /** 未确认 */
        unconfirmed,

        /** 确认中 */
        confirming,

        /** 已确认 */
        confirmed,

        /** 已取消 */
        cancelled
    }

    /** 报价单号 */
    private String sn;

    /** 飞机注册号 */
    private String regNo;

    /** 飞机型号*/
    private String type;

    /** 飞机Id */
    private Long airplaneId;

    /** 飞机 */
    private Airplane airplane;

    /** 类型 */
    private PlanType planType;

    /*始发机场ID*/
    private Long departureId;

    /*终点机场Id*/
    private Long destinationId;

    /*始发机场*/
    private Airport departure;

    /*终点机场*/
    private Airport destination;

    /** 起飞时间  */
    private Date takeoffTime;

    /** 总价 */
    private BigDecimal totalAmount;

    /** 自定义 */
    private BigDecimal customAmount;

    /** 状态 */
    private Status status;

    /** 调正天数 */
    private Integer stopDays;

    /** 实际消费小时数 */
    private String actualHours;

    /** 地面费用 */
    private BigDecimal totalMaintenance;

    /** 总航程跨越天数(不包含前后调机) */
    private Integer totalDay;

    /** 总飞行时长 */
    private String totalHour;

    /** 最低消费小时数 */
    private String lowestHour;

    /** 补齐小时数 */
    private String lackHour;

    /** 运力 */
    private Capacity capacity;

    /** 航段 */
    private  List<QuoteAirline> quoteAirlineList = new ArrayList<QuoteAirline>();

    /** 自身的集合 */
    private List<Quote> quotes = new ArrayList<Quote>();

    /** 用户 */
    private Customer customer;

    @JsonProperty
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    @JsonProperty
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty
    public Long getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
    }

    @JsonProperty
    @ManyToOne(fetch = FetchType.LAZY)
    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    @JsonProperty
    public Quote.PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(Quote.PlanType planType) {
        this.planType = planType;
    }

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
    public Date getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(Date takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

    @JsonProperty
    @OneToMany(mappedBy = "quote", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<QuoteAirline> getQuoteAirlineList() {
        return quoteAirlineList;
    }

    public void setQuoteAirlineList(List<QuoteAirline> quoteAirlineList) {
        this.quoteAirlineList = quoteAirlineList;
    }

    @JsonProperty
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Transient
    public BigDecimal getCustomAmount() {
        return customAmount;
    }

    public void setCustomAmount(BigDecimal customAmount) {
        this.customAmount = customAmount;
    }

    @JsonProperty
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonProperty
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @JsonProperty
    public Integer getStopDays() {
        return stopDays;
    }

    public void setStopDays(Integer stopDays) {
        this.stopDays = stopDays;
    }

    @JsonProperty
    public String getActualHours() {
        return actualHours;
    }

    public void setActualHours(String actualHours) {
        this.actualHours = actualHours;
    }

    @JsonProperty
    public BigDecimal getTotalMaintenance() {
        return totalMaintenance;
    }

    public void setTotalMaintenance(BigDecimal totalMaintenance) {
        this.totalMaintenance = totalMaintenance;
    }

    @JsonProperty
    public Integer getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Integer totalDay) {
        this.totalDay = totalDay;
    }

    @JsonProperty
    public String getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(String totalHour) {
        this.totalHour = totalHour;
    }

    @JsonProperty
    public String getLowestHour() {
        return lowestHour;
    }

    public void setLowestHour(String lowestHour) {
        this.lowestHour = lowestHour;
    }

    @JsonProperty
    public String getLackHour() {
        return lackHour;
    }

    public void setLackHour(String lackHour) {
        this.lackHour = lackHour;
    }

    @JsonProperty
    @Transient
    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    @OneToOne(mappedBy = "quote", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }


    /**
     * 是否包含国外航段
     * @return
     */
    @JsonProperty
    @Transient
    public Boolean getHasExternal(){
        for(QuoteAirline quoteAirline : this.getQuoteAirlineList()){
            if(quoteAirline.getIsExternal()){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取航程简要
     * @return
     */
    @JsonProperty
    @Transient
    public String getFlightBriefly(){
        String flight = "";
            if(this.getPlanType() == PlanType.oneWay){
                flight = this.getDeparture().getName() + "-" + this.getDestination().getName();
            } else if(this.getPlanType() == PlanType.returnWay){
                flight = this.getDeparture().getName() + "-" + this.getDestination().getName() + "-" + this.getDeparture().getName();
            } else {
                for(QuoteAirline quoteAirline : this.getQuoteAirlineList()){
                    flight = flight + quoteAirline.getDeparture().getName() + "-";
                }
                flight = flight + this.getDestination().getName();
            }
        return flight;
    }

    @Transient
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
