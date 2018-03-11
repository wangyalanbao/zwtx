package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofeng on 2016/1/5.
 * entity--国内机场代理费
 */
@Entity
@Table(name = "lm_inland_cost")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_inland_cost_sequence")
public class InlandCost extends BaseEntity {
    private static final long serialVersionUID = 3679108528372935875L;

    public enum Airfield {
        //0国内机场
        interior,
        //1国外机场
        external

    }
    //名称
    private String name;
    //任务地面代理费
    private BigDecimal agencyCost;
    //调机时地面代理费
    private BigDecimal transferCost;

    // 描述
    private String description;
    // 机场
    private List<Airport> airports=new ArrayList<Airport>();
    //储存地址
    private String area;
    //国内外机场
    private Airfield airfield;

    @JsonProperty
    public Airfield getAirfield() {
        return airfield;
    }

    public void setAirfield(Airfield airfield) {
        this.airfield = airfield;
    }

    @JsonProperty
    @Lob
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public BigDecimal getAgencyCost() {
        return agencyCost;
    }

    public void setAgencyCost(BigDecimal agencyCost) {
        this.agencyCost = agencyCost;
    }
    @JsonProperty
    public BigDecimal getTransferCost() {
        return transferCost;
    }

    public void setTransferCost(BigDecimal transferCost) {
        this.transferCost = transferCost;
    }
    @JsonProperty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonProperty
    @OneToMany(mappedBy = "inlandCost", fetch = FetchType.LAZY)
    public List<Airport> getAirports() {
        return airports;
    }

    public void setAirports(List<Airport> airports) {
        this.airports = airports;
    }
}
