package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by guofeng on 2015/12/28.
 * entity--软件类型
 */
@Entity
@Table(name = "lm_software_manage")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_software_manage_sequence")
public class SoftwareManage extends BaseEntity {

    private static final long serialVersionUID = -517788043018653013L;

    //软件名称
    private String name;
    //软件编码
    private String sn;

    //软件渠道
    private String channelTypes;
    //渠道编码
    private String channelTypesSn;
    //软件版本
    private Set<VersionsUpdate> versionsUpdates = new HashSet<VersionsUpdate>();
    //文章分类


    @JsonProperty
    public String getChannelTypes() {
        return channelTypes;
    }

    public void setChannelTypes(String channelTypes) {
        this.channelTypes = channelTypes;
    }
    @JsonProperty
    @Column(updatable = false)
    public String getChannelTypesSn() {
        return channelTypesSn;
    }

    public void setChannelTypesSn(String channelTypesSn) {
        this.channelTypesSn = channelTypesSn;
    }

    @JsonProperty
    @Column(updatable = false)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    @OneToMany(mappedBy = "softwareManage",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public Set<VersionsUpdate> getVersionsUpdates() {
        return versionsUpdates;
    }

    public void setVersionsUpdates(Set<VersionsUpdate> versionsUpdates) {
        this.versionsUpdates = versionsUpdates;
    }
}
