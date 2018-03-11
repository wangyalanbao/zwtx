package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entity--订单航段
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_order_airline")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_airline_sequence")
public class OrderAirline extends OrderEntity {

    private static final long serialVersionUID = 5907321648969303023L;
    /** 订单 */
    private Order tripOrder;
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
    /** 早餐份数  */
    private Integer breakfast;
    /** 午餐份数  */
    private Integer lunch;
    /** 甜点份数  */
    private Integer desert;
    /** 晚餐份数  */
    private Integer supper;
    /** 小吃份数 */
    private Integer snack;
    /** 水果份数  */
    private Integer fruit;
    /** 订单行李和配餐要求 */
    private OrderCatering orderCatering;
    /** 订单乘客 */
    private List<OrderPassenger> orderPassengers = new ArrayList<OrderPassenger>();;
    /** 订单接送人 */
    private OrderPickup orderPickup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    public Order getTripOrder() {
        return tripOrder;
    }

    public void setTripOrder(Order order) {
        this.tripOrder = order;
    }


    @JsonProperty
    public Long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Long departureId) {
        this.departureId = departureId;
    }

    @JsonProperty
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    @JsonProperty
    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    @JsonProperty
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
    @Min(0)
    public Integer getPassengerNum() {
        return passengerNum;
    }

    public void setPassengerNum(Integer passengerNum) {
        this.passengerNum = passengerNum;
    }

    @JsonProperty
    @Min(0)
    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    @JsonProperty
    @Min(0)
    public Integer getLunch() {
        return lunch;
    }

    public void setLunch(Integer lunch) {
        this.lunch = lunch;
    }

    @JsonProperty
    @Min(0)
    public Integer getDesert() {
        return desert;
    }

    public void setDesert(Integer desert) {
        this.desert = desert;
    }

    @JsonProperty
    @Min(0)
    public Integer getSupper() {
        return supper;
    }

    public void setSupper(Integer supper) {
        this.supper = supper;
    }

    @JsonProperty
    @Min(0)
    public Integer getSnack() {
        return snack;
    }

    public void setSnack(Integer snack) {
        this.snack = snack;
    }

    @JsonProperty
    @Min(0)
    public Integer getFruit() {
        return fruit;
    }

    public void setFruit(Integer fruit) {
        this.fruit = fruit;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderAirline")
    public OrderCatering getOrderCatering() {
        return orderCatering;
    }

    public void setOrderCatering(OrderCatering orderCatering) {
        this.orderCatering = orderCatering;
    }

    @JsonProperty
    @OneToMany(mappedBy = "orderAirline", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<OrderPassenger> getOrderPassengers() {
        return orderPassengers;
    }

    public void setOrderPassengers(List<OrderPassenger> orderPassengers) {
        this.orderPassengers = orderPassengers;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderAirline")
    public OrderPickup getOrderPickup() {
        return orderPickup;
    }

    public void setOrderPickup(OrderPickup orderPickup) {
        this.orderPickup = orderPickup;
    }
}
