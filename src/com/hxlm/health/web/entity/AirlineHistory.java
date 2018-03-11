package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by guofeng on 2015/12/10.
 * ENTITY--航线历史数据
 *始发机场ID --departure_id
 * 终点机场ID --destination_id
 */
@Entity
@Table(name = "lm_airline_history")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_airline_history_sequence")
public class AirlineHistory extends OrderEntity{

    private static final long serialVersionUID = 1786725070239826484L;

    /*耗时*/
    private Float timeCost;
    /*飞行月份*/
    private Integer month;
    /*是否真实录入*/
    private Boolean isVirtual;

    /*删除标志*/
//    private Boolean isDelete;
    /*排序号*/
//    private Integer sortNo;
    /*飞机型号id号*/
    private PlaneType typeId;

    @ManyToOne(fetch = FetchType.LAZY)
    public PlaneType getTypeId() {
        return typeId;
    }

    public void setTypeId(PlaneType typeId) {
        this.typeId = typeId;
    }

    @JsonProperty
    public Float getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Float timeCost) {
        this.timeCost = timeCost;
    }
    @JsonProperty
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
    @JsonProperty
    public Boolean getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Boolean isVirtual) {
        this.isVirtual = isVirtual;
    }

}
