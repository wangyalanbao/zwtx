package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by delll on 2015/8/4.
 * entity--用户反馈
 */
@Entity
@Table(name = "lm_member_suggest")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lm_member_suggest_sequence")
public class MemberSuggest extends BaseEntity {

    private static final long serialVersionUID = -1438604475478217545L;
    /*反馈内容*/
    private String content;

    /*会员号*/
    private Member member;

    @JsonProperty
    @Length(max = 30)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /*会员外键*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
