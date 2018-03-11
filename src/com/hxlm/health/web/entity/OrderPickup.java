package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity--订单接送人
 * Created by zyr on 2015/12/16.
 */
@Entity
@Table(name = "lm_order_pickup")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_order_pickup_sequence")
public class OrderPickup extends OrderEntity {

    private static final long serialVersionUID = -5022494782273451145L;

    /**
     * 出发地类别
     */
    public enum Type{
        /** 始发地 */
        start,
        /** 目的地 */
        end
    }
    /** 订单 */
    private Order tripOrder;
    /** 订单航段 */
    private OrderAirline orderAirline;
    /** 机场id号 */
    private Long airportId;
    /** 出发地类别 */
    private Type type;
    /** 接送人姓名 */
    private String name;
    /** 接送人联系方式 */
    private String contact;
    /** 接送人车牌号 */
    private String carNo;
    /** 接送地点 */
    private String site;

    @JsonProperty
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    public Order getTripOrder() {
        return tripOrder;
    }

    public void setTripOrder(Order order) {
        this.tripOrder = order;
    }

    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false, updatable = false)
    public OrderAirline getOrderAirline() {
        return orderAirline;
    }

    public void setOrderAirline(OrderAirline orderAirline) {
        this.orderAirline = orderAirline;
    }

    @JsonProperty
    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    @JsonProperty
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @JsonProperty
    @Length(max = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    @Length(max = 50)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @JsonProperty
    @Length(max = 20)
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
}
