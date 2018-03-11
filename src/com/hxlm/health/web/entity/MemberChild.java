package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by delll on 2015/6/24.
 * entity--会员子账户
 */
@Entity
@Table(name = "lm_member_child")
@SequenceGenerator(name = "sequenceGenerator" ,sequenceName = "lm_member_child_sequence")
public class MemberChild extends BaseEntity {

    private static final long serialVersionUID = -781475333439603492L;

    /*性别*/
    public enum Gender{
        /*男*/
        male,
        /*女*/
        female
    }

    /*是否有医保*/
    public enum Medicare{
        /*有*/
        yes,
        /*无*/
        no
    }
    /*称呼*/
    private String name;
    /** 性别 */
    private Gender gender;
   /* 身份证varchar(20),*/
    private String IDCard;
   /* 医保卡varchar(20),*/
    private Medicare isMedicare;
    /*生日*/
    private String birthday;
    /*手机号*/
    private String mobile;
    /*体重*/
    private Integer weight;
    /*会员号*/
    private Member member;





    /*身份证号*/
//    @NotEmpty
    @JsonProperty
    @Column(length = 20)
    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    /*医保卡号*/
//    @NotEmpty
    @JsonProperty
    @Column(nullable = false,length = 20)
    public Medicare getIsMedicare() {
        return isMedicare;
    }

    public void setIsMedicare(Medicare isMedicare) {
        this.isMedicare = isMedicare;
    }






    /*获取称呼*/
    @JsonProperty
    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /*获取性别*/
    @JsonProperty
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /*获取生日*/
    @JsonProperty
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    /*手机号*/
    @JsonProperty
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /*体重*/
    @JsonProperty
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    /*会员外键*/
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
