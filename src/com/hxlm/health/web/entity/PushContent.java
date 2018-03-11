package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by dengyang on 15/11/25.
 * 推送内容
 */

@Entity
@Table(name = "lm_push_content")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_push_content_sequence")
public class PushContent extends BaseEntity {

    /** 内容 */
    private String content;

    /** 类型 */
    private String type;

    /** 推送方式（单推or群推） */
    private String way;

    /** 用户 */
    private Long userId;

    @JsonProperty
    @Lob
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}