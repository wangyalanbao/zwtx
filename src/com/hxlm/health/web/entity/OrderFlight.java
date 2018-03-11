package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity--订单航班
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_order_flight")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_flight_sequence")
public class OrderFlight extends OrderEntity {

    private static final long serialVersionUID = 8976728206036579094L;
    /** 订单 */
    private Order tripOrder;
    /** 订单航段 */
    private OrderAirline orderAirline;
    /** 航班号 */
    private String flightNo;
    /** 登机时间 */
    private Date boardingTime;
    /** 登机地点 */
    private String boardingPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    public Order getTripOrder() {
        return tripOrder;
    }

    public void setTripOrder(Order order) {
        this.tripOrder = order;
    }

    @JsonProperty
    @ManyToOne(fetch = FetchType.LAZY)
    public OrderAirline getOrderAirline() {
        return orderAirline;
    }

    public void setOrderAirline(OrderAirline orderAirline) {
        this.orderAirline = orderAirline;
    }

    @JsonProperty
    @Length(max = 200)
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    @JsonProperty
    public Date getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(Date boardingTime) {
        this.boardingTime = boardingTime;
    }

    @JsonProperty
    @Length(max = 200)
    public String getBoardingPlace() {
        return boardingPlace;
    }

    public void setBoardingPlace(String boardingPlace) {
        this.boardingPlace = boardingPlace;
    }

}
