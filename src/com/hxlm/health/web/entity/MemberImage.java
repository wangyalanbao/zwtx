package com.hxlm.health.web.entity;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Created by delll on 2015/6/27.
 * Entity--会员头像
 */
@Embeddable
public class MemberImage implements Serializable ,Comparable<MemberImage>{

    private static final long serialVersionUID = -6863963955046585519L;


    /** 标题 */
    private String title;

    /** 原图片 */
    private String source;

    /** 大图片 */
    private String large;

    /** 中图片 */
    private String medium;

    /** 缩略图 */
    private String thumbnail;

    /** 排序 */
    private Integer order;

    /** 文件 */
    private MultipartFile file;


    @Length(max = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Transient
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    /*排序*/
    @Min(0)
    @Column(name = "orders")
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * 判断是否为空
     *
     * @return 是否为空
     * @Transient
    public boolean isEmpty() {
    return (getFile() == null || getFile().isEmpty()) && (StringUtils.isEmpty(getSource()) || StringUtils.isEmpty(getLarge()) || StringUtils.isEmpty(getMedium()) || StringUtils.isEmpty(getThumbnail()));
    }
     */
    @Transient
    public boolean isEmpty(){
        return (getFile()==null||getFile().isEmpty())&&(StringUtils.isEmpty(getSource())||StringUtils.isEmpty(getLarge())||StringUtils.isEmpty(getMedium())||StringUtils.isEmpty(getThumbnail()));
    }

    /**
     * 实现compareTo方法
     * @param memberImage
     * @return
     *会员照片
     * 比较排序结果
     */
    @Override
    public int compareTo(MemberImage memberImage) {
        return new CompareToBuilder().append(getOrder(),memberImage.getOrder()).toComparison();
    }
}
