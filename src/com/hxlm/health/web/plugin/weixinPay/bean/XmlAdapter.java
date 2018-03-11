package com.hxlm.health.web.plugin.weixinPay.bean;

/**
 * Created by dengyang on 15/7/30.
 */
public abstract class XmlAdapter<ValueType, BoundType> {
    protected XmlAdapter() {
    }

    public abstract BoundType unmarshal(ValueType var1) throws Exception;

    public abstract ValueType marshal(BoundType var1) throws Exception;
}