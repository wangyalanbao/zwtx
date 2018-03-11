package com.hxlm.health.web.yuntongxun.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/*
 * 
* @Description: TODO(访问http接口) 
* @author： baohj
* @date： 2014年9月13日 下午1:38:10
 */
public class HttpPostDemo {
	public static Logger logger = Logger.getLogger(HttpPostDemo.class);
	
	/*
	* @Title: send 
	* @Description: TODO(发起对接口的访问) 
	* @param url
	* @param map
	* @param body
	* @return String    返回类型
	* @throws
	 */
	public static String send(String url,String Authorization,String body){
		String conResult="";
		try{
		HttpClient httpclient = CommonUtil.enableSSL();
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Content-Type", "application/xml;charset=utf-8");
		httppost.setHeader("Authorization", Authorization);
		if(body!=null && !"".equals(body)){
			HttpEntity entity = new ByteArrayEntity(body.getBytes("utf-8"));
			httppost.setEntity(entity);
		}
		HttpResponse httpresponse = httpclient.execute(httppost);
		conResult = EntityUtils.toString(httpresponse.getEntity());
		logger.info(String.format("request url:%s;request body:%s;response body:%s", url,body,conResult));
		Document document = DocumentHelper.parseText(conResult);
		Element root = document.getRootElement();
		//获取错误码
		String errorCode=root.element("statusCode").getText();
		int statusCode = httpresponse.getStatusLine().getStatusCode();
		if(200!=statusCode || !errorCode.equals(Constants.SuccessCode)){
			logger.info(String.format("response statusCode:%s", errorCode));
			return null;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return conResult;
	}

}
