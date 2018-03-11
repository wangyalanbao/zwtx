package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by guofeng on 2015/12/9.
 *entity--飞机品牌
 */
@Entity
@Table(name = "lm_plane_brand")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_plane_brand_sequence")
public class PlaneBrand extends OrderEntity{

    private static final long serialVersionUID = -2429430885953800530L;
    /*品牌名称*/
    private String name;

    /*飞机型号*/
    private Set<PlaneType> planeTypes = new HashSet<PlaneType>();

    @JsonProperty
    @OneToMany(mappedBy = "brandId",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public Set<PlaneType> getPlaneTypes() {
        return planeTypes;
    }

    public void setPlaneTypes(Set<PlaneType> planeTypes) {
        this.planeTypes = planeTypes;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
