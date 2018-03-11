package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by guofeng on 2015/12/11.
 * entity --航空公司
 */
@Entity
@Table(name = "lm_company")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_company_sequence")
public class Company extends OrderEntity {

    private static final long serialVersionUID = -6853740646729436585L;

    /*公司名 */
    private String name;

    /*七大洲*/
    private String continents;

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getContinents() {
        return continents;
    }

    public void setContinents(String continents) {
        this.continents = continents;
    }
}
