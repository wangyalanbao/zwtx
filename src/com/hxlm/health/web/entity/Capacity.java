package com.hxlm.health.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangyongr on 2015/12/9.
 * ENtity--运力确认
 */
@Entity
@Table(name = "lm_capacity")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "lm_capacity_sequence")
public class Capacity extends OrderEntity{

    private static final long serialVersionUID = -2721329039098980332L;

    public enum Status{
        /** 待确认 */
        unconfirmed,

        /** 部分确认 */
        confirming,

        /** 已确认 */
        confirmed,

        /** 已取消 */
        cancelled
    }

    /** 基本信息 */
    private Boolean base;

    /** 飞行任务 */
    private Boolean flightMission;

    /** 情报席 */
    private Boolean intelligenceMat;

    /** 机资席 */
    private Boolean machineMat;

    /** 乘务调度席 */
    private Boolean flightAttendantMat;

    /** MCC */
    private Boolean MCC;

    /** 服务保障席  */
    private Boolean serviceGuaranteeMat;

    /** 运资席  */
    private Boolean workingCapitalMat;

    /** 保安管理中心  */
    private Boolean securityManagementCenter;

    /** 配餐席  */
    private Boolean catering;

    /** 历史论证 */
    private Boolean historicalArgument;

    /** 外事主管 */
    private Boolean foreignSupervisor;

    /** 运控席 */
    private Boolean operationControl;

    /** 计财部 */
    private Boolean financeDepartment;


    /** 所属报价 */
    private Quote quote;

    /** 报价ID */
    private Long quoteId;

    /** 是否取消 */
    private Boolean isCancelled;

    
    public Boolean getBase() {
        return base;
    }

    public void setBase(Boolean base) {
        this.base = base;
    }

    
    public Boolean getFlightMission() {
        return flightMission;
    }

    public void setFlightMission(Boolean flightMission) {
        this.flightMission = flightMission;
    }

    
    public Boolean getIntelligenceMat() {
        return intelligenceMat;
    }

    public void setIntelligenceMat(Boolean intelligenceMat) {
        this.intelligenceMat = intelligenceMat;
    }

    
    public Boolean getMachineMat() {
        return machineMat;
    }

    public void setMachineMat(Boolean machineMat) {
        this.machineMat = machineMat;
    }

    
    public Boolean getFlightAttendantMat() {
        return flightAttendantMat;
    }

    public void setFlightAttendantMat(Boolean flightAttendantMat) {
        this.flightAttendantMat = flightAttendantMat;
    }

    
    public Boolean getMCC() {
        return MCC;
    }

    public void setMCC(Boolean MCC) {
        this.MCC = MCC;
    }

    
    public Boolean getServiceGuaranteeMat() {
        return serviceGuaranteeMat;
    }

    public void setServiceGuaranteeMat(Boolean serviceGuaranteeMat) {
        this.serviceGuaranteeMat = serviceGuaranteeMat;
    }

    
    public Boolean getWorkingCapitalMat() {
        return workingCapitalMat;
    }

    public void setWorkingCapitalMat(Boolean workingCapitalMat) {
        this.workingCapitalMat = workingCapitalMat;
    }

    
    public Boolean getSecurityManagementCenter() {
        return securityManagementCenter;
    }

    public void setSecurityManagementCenter(Boolean securityManagementCenter) {
        this.securityManagementCenter = securityManagementCenter;
    }

    
    public Boolean getCatering() {
        return catering;
    }

    public void setCatering(Boolean catering) {
        this.catering = catering;
    }

    public Boolean getForeignSupervisor() {
        return foreignSupervisor;
    }

    public void setForeignSupervisor(Boolean foreignSupervisor) {
        this.foreignSupervisor = foreignSupervisor;
    }

    public Boolean getOperationControl() {
        return operationControl;
    }

    public void setOperationControl(Boolean operationControl) {
        this.operationControl = operationControl;
    }

    public Boolean getFinanceDepartment() {
        return financeDepartment;
    }

    public void setFinanceDepartment(Boolean financeDepartment) {
        this.financeDepartment = financeDepartment;
    }

    public Boolean getHistoricalArgument() {
        return historicalArgument;
    }

    public void setHistoricalArgument(Boolean historicalArgument) {
        this.historicalArgument = historicalArgument;
    }

    
    public Boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(Boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @OneToOne(fetch = FetchType.LAZY)
    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    /**
     * 确认状态
     * @return
     */
    @Transient
    public Status getStatus(){
        if(getIsCancelled()){
            return  Status.cancelled;
        }
        if(this.getQuote().getHasExternal()){
            if(getBase()&&getCatering()&&getFlightAttendantMat()&&getFlightMission()&&getHistoricalArgument()&&getIntelligenceMat()&&getMachineMat()&&getMCC()&&getSecurityManagementCenter()
                    &&getServiceGuaranteeMat()&&getWorkingCapitalMat()&&getForeignSupervisor()&&getOperationControl()&&getFinanceDepartment()){
                return Status.confirmed;
            } else if((getBase()||getCatering()||getFlightAttendantMat()||getFlightMission()||getHistoricalArgument()||getIntelligenceMat()||getMachineMat()||getMCC()||getSecurityManagementCenter()
                    ||getServiceGuaranteeMat()||getWorkingCapitalMat()||getForeignSupervisor()||getOperationControl()||getFinanceDepartment())) {
                return Status.confirming;
            } else {
                return Status.unconfirmed;
            }
        } else {
            if(getBase()&&getCatering()&&getFlightAttendantMat()&&getFlightMission()&&getHistoricalArgument()&&getIntelligenceMat()&&getMachineMat()&&getMCC()&&getSecurityManagementCenter()
                    &&getServiceGuaranteeMat()&&getWorkingCapitalMat()){
                return Status.confirmed;
            } else if((getBase()||getCatering()||getFlightAttendantMat()||getFlightMission()||getHistoricalArgument()||getIntelligenceMat()||getMachineMat()||getMCC()||getSecurityManagementCenter()
                    ||getServiceGuaranteeMat()||getWorkingCapitalMat())) {
                return Status.confirming;
            } else {
                return Status.unconfirmed;
            }
        }
    }


}
