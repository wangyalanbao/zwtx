package com.hxlm.health.web.plugin.weixinPay.util;

import com.alibaba.fastjson.JSON;

/**
 * Created by dengyang on 15/7/30.
 */
public class JsonUtil {

    private JsonUtil(){}

    public static <T> T parseObject(String json,Class<T> clazz){
        return JSON.parseObject(json, clazz);
    }

    public static String toJSONString(Object object){
        return JSON.toJSONString(object);
    }

}
