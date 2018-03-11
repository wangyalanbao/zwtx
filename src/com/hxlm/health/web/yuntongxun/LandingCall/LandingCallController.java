package com.hxlm.health.web.yuntongxun.LandingCall;


import com.hxlm.health.web.yuntongxun.common.CommonUtil;
import com.hxlm.health.web.yuntongxun.common.Constants;
import com.hxlm.health.web.yuntongxun.common.MD5;
import com.hxlm.health.web.yuntongxun.common.PropertiesHolder;
import com.hxlm.health.web.yuntongxun.voicecode.VoiceCodeController;
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
import org.ming.sample.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* @Description: TODO(营销外呼) 
* @author： baohj
* @date： 2014年9月13日 下午2:12:48
 */
@Controller
public class LandingCallController {
	protected final Logger logger = Logger.getLogger(VoiceCodeController.class);
	/*
	* @Title: helloworld 
	* @Description: TODO(营销外呼) 
	* @param application 应用id
	* @param numbers 接收外呼的电话号码
	* @param mainToken 主账号
	* @param mainAccount 主账号密码
	* @param restip
	* @param restpost
	* @param response
	* @param request
	* @return
	* @return String    返回类型
	* @throws
	 */
	@SuppressWarnings({ "unused", "finally" })
	@RequestMapping("/landingCall")
	public String landingCall(String application, String numbers, String mainToken,
			String mainAccount, String restip, String restpost,
			HttpServletResponse response, HttpServletRequest request) {
		String Authorization = "";
		String[] array=numbers.split(",");
		String statusMsg="";
		StringBuffer sb=new StringBuffer();
		try {
			Authorization = Base64.encode((mainAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		String url = this.getUrl(mainAccount, mainToken);
		HttpClient httpclient = CommonUtil.enableSSL();
	
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Content-Type", "application/xml;charset=utf-8");
		httppost.setHeader("Authorization", Authorization);
		String strResult = "";
		/* 获取包体 */
		for(int x=0;x<array.length;x++){
			String body = this.getvoiceCodeParm(application, array[x]);
			logger.info(String
					.format("主账号:%s,主账号密码:%s,restIP:%s,restPost:%s,Authorization:%s,url:%s,request body:%s",
							mainAccount, mainToken, restip, restpost,
							Authorization, url, body));
			
				HttpEntity entity = new ByteArrayEntity(body.getBytes("utf-8"));
				httppost.setEntity(entity);
				HttpResponse httpresponse = httpclient.execute(httppost);
				String conResult = EntityUtils.toString(httpresponse.getEntity());
				int statusCode = httpresponse.getStatusLine().getStatusCode();
				logger.info("response statusCode:" + statusCode
						+ ";\r\n response body:" + conResult);
				Document document = DocumentHelper.parseText(conResult);
				Element root = document.getRootElement();
				//获取错误码
				String errorCode=root.element("statusCode").getText();
				
				if(!errorCode.equals(Constants.SuccessCode)){
					 statusMsg=root.element("statusMsg").getText();
					
				}else{
					statusMsg="成功!";
				}
				 sb.append(String.format("电话号码:%s,服务器返回值:%s<br/>",array[x],statusMsg));
		}
		request.setAttribute(Constants.Message, sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Constants.Message,e.getMessage());
		}finally{
			return "error";
		} 
	}
	/*
	* @Title: getvoiceCodeParm 
	* @Description: TODO(组装包体) 
	* @param application 应用id
	* @param number 接收外呼的电话号码
	* @return String    返回类型
	* @throws
	 */
	public String getvoiceCodeParm(String application, String number) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("LandingCall");
		root.addElement("appId").setText(application);
		root.addElement("to").setText(number);
		root.addElement("mediaName").setText(Constants.VoiceFileName);
		root.addElement("displayNum").setText("110");
		String xml = document.asXML();
		return xml;
	}

	/*
	* @Title: getUrl 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param mainAccount 主账号
	* @param mainToken 主账号密码
	* @return String    返回类型
	* @throws
	 */
	public String getUrl(String mainAccount,
			String mainToken) {
		String sig = MD5.md5Digest(mainAccount + mainToken
				+ CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/Accounts/" + mainAccount
				+ "/Calls/LandingCalls?sig=" + sig;
		return url;
	}
	
}
