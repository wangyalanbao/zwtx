package com.hxlm.health.web.test;

import com.hxlm.health.web.Result;
import com.hxlm.health.web.controller.admin.BaseController;
import com.hxlm.health.web.util.JsonUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guofeng on 2017/3/8.
 */
@Controller("ceshi")
@RequestMapping(value = "/get")
public class Ceshi extends BaseController{

    @RequestMapping(value = "/val",method = RequestMethod.GET)
    @ResponseBody
    public Map post(){
       Map<String,Object> jsonMap = new HashMap<String,Object>();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("time",1488860272000L);
        //
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        try {
            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();

            if(!map.isEmpty()){
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    String name = entry.getKey();
                    String value = ConvertUtils.convert(entry.getValue());
                    if (StringUtils.isNotEmpty(name)) {
                        nameValuePairs.add(new BasicNameValuePair(name, value));
                    }
                }
            }
            HttpGet httpGet = new HttpGet("http://localhost:8080/shop/push_content/push.jhtml"+"?"+EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
            //打印url
            System.out.print("=========================="+httpGet.getURI()+"=============================");

            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                //打印相依状态
                System.out.println("==========================="+response.getStatusLine()+"===========================");
                if(httpEntity != null){
                    String a = EntityUtils.toString(httpEntity);
                    System.out.println("================================"+a+"============================");
                    try {
                        jsonMap = JsonUtils.jsonToMap(new JSONObject(a));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("================================"+jsonMap.toString()+"==============================");
                }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonMap;
    }
}
