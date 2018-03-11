package com.hxlm.health.web.yuntongxun.voicecode;


import com.hxlm.health.web.yuntongxun.common.CommonUtil;
import com.hxlm.health.web.yuntongxun.common.Constants;
import com.hxlm.health.web.yuntongxun.common.MD5;
import com.hxlm.health.web.yuntongxun.common.PropertiesHolder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.ming.sample.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/*
* @Description: TODO(语音验证码) 
* @author： baohj
* @date： 2014年9月13日 下午2:06:30
 */
@Controller
public class VoiceCodeController {
	protected final Logger logger = Logger.getLogger(VoiceCodeController.class);
	/*
	* @Title: helloworld 
	* @Description: TODO(获取验证码) 
	* @param application 应用id
	* @param number 接收验证码的电话号码
	* @param mainToken 主账号密码
	* @param mainAccount 主账号
	* @param restip 云通讯服务器ip
	* @param restpost 云通讯服务器post
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/getCode")
	public void getCode(String application, String number, String mainToken,
			String mainAccount, String restip, String restpost,
			HttpServletResponse response, HttpServletRequest request) {
		String Authorization = "";
		try {
			Authorization = Base64.encode((mainAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String url = this.getUrl(mainAccount, mainToken);
		HttpClient httpclient = CommonUtil.enableSSL();
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Content-Type", "application/xml;charset=utf-8");
		httppost.setHeader("Authorization", Authorization);
		String strResult = "";
		String code=CommonUtil.getRandom(4);
		//保存语音验证码
		request.getSession().setAttribute("voiceCode",code);
		/* 获取包体 */
		String body = this.getvoiceCodeParm(application, number,code);
		logger.info(String
				.format("主账号:%s,主账号密码:%s,restIP:%s,restPost:%s,语音验证码：%s,Authorization:%s,url:%s,request body:%s",
						mainAccount, mainToken, restip, restpost,code,
						Authorization, url, body));
		PrintWriter out=null;
		try {
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
			//如果返回错误结果，打印日志
			response.setCharacterEncoding("utf-8");
			 out = response.getWriter();
			String statusMsg="";
			if(!errorCode.equals("000000")){
				 statusMsg=root.element("statusMsg").getText();
			}
				
			out.write(statusMsg);
			out.flush();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

	/*
	* @Title: getvoiceCodeParm 
	* @Description: TODO(获取语音验证码xml包体 ) 
	* @param application 应用id
	* @param number 接收验证码的电话号码
	* @param code 验证码
	* @return String    返回类型
	* @throws
	 */
	public String getvoiceCodeParm(String application, String number,String code) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("VoiceVerify");
		root.addElement("appId").setText(application);
		root.addElement("verifyCode").setText(code);
		root.addElement("playTimes").setText("2");
		root.addElement("to").setText(number);
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
				+ "/Calls/VoiceVerify?sig=" + sig;
		return url;
	}
	
	/*
	* @Title: checkCode 
	* @Description: TODO(判断验证验证码是否正确) 
	* @param code 验证码
	* @param response
	* @param request
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping("/checkCode")
	public String  checkCode(String code,HttpServletResponse response, HttpServletRequest request){
		String voiceCode=(String) request.getSession().getAttribute("voiceCode");
		String msg="";
		if(code!=null&&voiceCode!=null&&code.equals(voiceCode)){
			msg="成功!";
		}else{
			msg="失败!";
		}
		logger.info(String.format("正确的验证码为:%s,您输入的验证码为:%s,验证结果：%s",voiceCode,code,msg));
		request.setAttribute("msg", String.format("正确的验证码为:%s,您输入的验证码为:%s,验证结果：%s",voiceCode,code,msg));
		return "error";
	}
}