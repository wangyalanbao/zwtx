package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import java.io.Serializable;
/**
 * Created by guofeng on 2016/2/17.
 * Entity -- 资源库文件存放信息表
 */

@Embeddable
public class ResourcesWarehouse implements Serializable, Comparable<ProductImage>{


    private static final long serialVersionUID = 7377751422463603389L;
    /** 标题 */
    private String title;

    /** 原文件 */
    private String source;

    /** 排序 */
    private Integer order;

    /** 文件 */
    private MultipartFile file;

    // 资源的媒体类型
    private String type;

    @JsonProperty
    @Length(max = 100)
    @Column(nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取标题
     *
     * @return 标题
     */
    @Length(max = 200)
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title
     *            标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取原图片
     *
     * @return 原图片
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置原图片
     *
     * @param source
     *            原图片
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取排序
     *
     * @return 排序
     */
    @Min(0)
    @Column(name = "orders")
    public Integer getOrder() {
        return order;
    }

    /**
     * 设置排序
     *
     * @param order
     *            排序
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * 获取文件
     *
     * @return 文件
     */
    @Transient
    public MultipartFile getFile() {
        return file;
    }

    /**
     * 设置文件
     *
     * @param file
     *            文件
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * 判断是否为空
     *
     * @return 是否为空
     */
    @Transient
    public boolean isEmpty() {
        return (getFile() == null || getFile().isEmpty()) && (StringUtils.isEmpty(getSource()));
    }



    public int compareTo(ProductImage productImage) {
        return new CompareToBuilder().append(getOrder(), productImage.getOrder()).toComparison();
    }

}
