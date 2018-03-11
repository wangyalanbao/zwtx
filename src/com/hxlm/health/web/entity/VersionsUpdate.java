package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Created by guofeng on 2015/12/28.
 * entity--版本号
 */
@Entity
@Table(name = "lm_versions_update")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_versions_update_sequence")
public class VersionsUpdate extends BaseEntity {

    private static final long serialVersionUID = -5806164853592796230L;

    //下载地址
    private String downurl;
    //版本号
    private String versionsNum;

    //软件名
    private SoftwareManage softwareManage;

    @ManyToOne(fetch = FetchType.LAZY)
    public SoftwareManage getSoftwareManage() {
        return softwareManage;
    }

    public void setSoftwareManage(SoftwareManage softwareManage) {
        this.softwareManage = softwareManage;
    }

    //版本号
    @JsonProperty
    @Length(max = 100)
    public String getVersionsNum() {
        return versionsNum;
    }

    public void setVersionsNum(String versionsNum) {
        this.versionsNum = versionsNum;
    }

    //下载地址
    @JsonProperty
    @Length(max = 200)
    public String getDownurl() {
        return downurl;
    }

    public void setDownurl(String downurl) {
        this.downurl = downurl;
    }

}
