package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by guofeng on 2015/12/10.
 * entity --特价航段
 */
@Entity
@Table(name = "lm_airline_special")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_airline_special_sequence")
public class AirlineApecial extends OrderEntity {
    private static final long serialVersionUID = -6739592337560468937L;

    /*状态（0=用户不可见，1=用户可见，2=已被预订）*/
    public enum Status{
        //0=用户不可见
        novisible,
        //1=用户可见
        visible,
        //2=已被预订
        schedule
    }

    /*始发机场ID*/
    private Airport departureId;
    /*始发机场名 */
    private String departure;
    /*终点机场ID*/
    private Airport destinationId;
    /*终点机场名 */
    private String destination;
    /*飞机ID*/
    private Airplane planeId;
    /*航段原价 */
    private BigDecimal originPrice;
    /*航段特价 */
    private BigDecimal price;
    /*状态*/
    private Status status;
    /*起飞时间 */
    private Date takeoffTime;

    @ManyToOne(fetch = FetchType.LAZY)
    public Airport getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Airport departureId) {
        this.departureId = departureId;
    }
    @JsonProperty
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Airport getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Airport destinationId) {
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
    @OneToOne(fetch = FetchType.LAZY)
    public Airplane getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Airplane planeId) {
        this.planeId = planeId;
    }
    @JsonProperty
    public BigDecimal getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(BigDecimal originPrice) {
        this.originPrice = originPrice;
    }
    @JsonProperty
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    @JsonProperty
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    @JsonProperty
    public Date getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(Date takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

}
