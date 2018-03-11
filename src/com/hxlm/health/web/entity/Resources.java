package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofeng on 2016/2/17.
 * Entity -- 资源
 */

@Entity
@Table(name = "lm_resources")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_resources_sequence")
public class Resources extends OrderEntity {

    private static final long serialVersionUID = 1233070293694481265L;

    private String name;

    // 资源的媒体类型
    private String type;

    // 资源内容
    private String content;

    // 备注
    private String memo;

    // 资源文件
    private List<ResourcesWarehouse> resourcesWarehouses = new ArrayList<ResourcesWarehouse>();

    @JsonProperty
    @Length(max = 100)
    @Column(nullable = false)
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @JsonProperty
    @Length(max = 100)
    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    @JsonProperty
    @Lob
    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    @JsonProperty
    @Length(max = 200)
    public String getMemo() { return memo; }

    public void setMemo(String memo) { this.memo = memo; }

    @JsonProperty
    @Valid
    @ElementCollection
    @CollectionTable(name = "lm_resources_warehouse")
    public List<ResourcesWarehouse> getResourcesWarehouses() {
        return resourcesWarehouses;
    }

    public void setResourcesWarehouses(List<ResourcesWarehouse> resourcesWarehouses) {
        this.resourcesWarehouses = resourcesWarehouses;
    }


}
