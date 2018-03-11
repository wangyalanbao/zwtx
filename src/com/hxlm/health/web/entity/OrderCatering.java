package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Entity--订单行李和配餐要求
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_order_history")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_history_sequence")
public class OrderCatering extends OrderEntity {

    private static final long serialVersionUID = 1287072284469892277L;
    /** 订单 */
    private Order tripOrder;
    /** 订单航段 */
    private OrderAirline orderAirline;
    /** 行李需求描述 */
    private String luggageRequest;
    /** 酒水需求描述 */
    private String drinkRequest;
    /** 配餐口味要求描述 */
    private String foodRequest;
    /** 其它情况说明 */
    private String otherRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    public Order getTripOrder() {
        return tripOrder;
    }

    public void setTripOrder(Order order) {
        this.tripOrder = order;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public OrderAirline getOrderAirline() {
        return orderAirline;
    }

    public void setOrderAirline(OrderAirline orderAirline) {
        this.orderAirline = orderAirline;
    }

    @JsonProperty
    @Length(max = 1024)
    public String getLuggageRequest() {
        return luggageRequest;
    }

    public void setLuggageRequest(String luggageRequest) {
        this.luggageRequest = luggageRequest;
    }

    @JsonProperty
    @Length(max = 1024)
    public String getDrinkRequest() {
        return drinkRequest;
    }

    public void setDrinkRequest(String drinkRequest) {
        this.drinkRequest = drinkRequest;
    }

    @JsonProperty
    @Length(max = 1024)
    public String getFoodRequest() {
        return foodRequest;
    }

    public void setFoodRequest(String foodRequest) {
        this.foodRequest = foodRequest;
    }

    @JsonProperty
    @Length(max = 1024)
    public String getOtherRequest() {
        return otherRequest;
    }

    public void setOtherRequest(String otherRequest) {
        this.otherRequest = otherRequest;
    }
}
