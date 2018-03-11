package com.hxlm.health.web.plugin.weixinPay.bean;

import javax.xml.bind.annotation.adapters.*;

/**
 * Created by dengyang on 15/7/30.
 */
public class AdaptorCDATA extends javax.xml.bind.annotation.adapters.XmlAdapter<String, String> {

    @Override
    public String marshal(String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }

    @Override
    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }
}