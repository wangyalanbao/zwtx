package com.hxlm.health.web.yuntongxun.callback;


import com.hxlm.health.web.yuntongxun.common.CommonUtil;
import com.hxlm.health.web.yuntongxun.common.Constants;
import com.hxlm.health.web.yuntongxun.common.MD5;
import com.hxlm.health.web.yuntongxun.common.PropertiesHolder;
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
import java.io.UnsupportedEncodingException;

/*
* @Description: 双向回拨
* @author baohj
* @date 2014年9月13日 下午1:16:07 
 */
@Controller
public class CallbackController {
	protected final Logger logger = Logger.getLogger(CallbackController.class);
/*
 * 
* @Title: callback 
* @Description: 访问云通讯平台双向回拨接口
* @param called 被叫号码
* @param caller  主叫号码
* @param subToken 子账号密码
* @param subAccount  子账号
* @param response
* @param request
* @return：String
* @throws
* junit.framework.TestCase
 */
	@SuppressWarnings({ "unused", "finally" })
	@RequestMapping("/callback")
	public String callback(String called, String caller, String subToken,
			String subAccount,
			HttpServletResponse response, HttpServletRequest request) {
		String Authorization = "";
		try {
			Authorization = Base64.encode((subAccount + ":" + CommonUtil
					.formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//获取双向回拨的地址
		String url = this.getUrl(subAccount, subToken);
		HttpClient httpclient = CommonUtil.enableSSL();
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Content-Type", "application/xml;charset=utf-8");
		httppost.setHeader("Authorization", Authorization);
		String strResult = "";
		String code=CommonUtil.getRandom(4);
		//保存语音验证码
		request.getSession().setAttribute("voiceCode",code);
		/* 获取包体 */
		String body = this.getvoiceCodeParm(caller,called);
		logger.info(String
				.format("子账号:%s,子账号密码:%s,语音验证码：%s,Authorization:%s,url:%s,request body:%s",
						subAccount, subToken,code,
						Authorization, url, body));
		String statusMsg="";
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
			
			if(!errorCode.equals(Constants.SuccessCode)){
				 statusMsg=root.element("statusMsg").getText();
			}else{
				statusMsg="双向回拨成功,可以进行通话!";
			}
			request.setAttribute(Constants.Message, statusMsg);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute(Constants.Message, e.getMessage());
		}finally{
			return "error";
		}
	}
	/*
	 * 
	* @Title: getvoiceCodeParm 
	* @Description: 获取双向回拨的包体信息
	* @param caller 主叫号码
	* @param called 被叫号码
	* @return：String
	* @throws
	 */
	public String getvoiceCodeParm(String caller,String called) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("CallBack");
		root.addElement("from").setText(caller);
		root.addElement("to").setText(called);
		//被叫侧显号
		root.addElement("customerSerNum").setText(caller);
		//主叫侧显号
		root.addElement("fromSerNum").setText(called);
		String xml = document.asXML();
		return xml;
	}
	
	/*
	* @Title: getUrl 
	* @Description: 获取访问地址
	* @param subAccount 子账号
	* @param subToken 子账号密码
	* @return：String
	* @throws
	 */
	public String getUrl(String subAccount,String subToken) {
		String sig = MD5.md5Digest(subAccount + subToken
				+ CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/Calls/Callback?sig=" + sig;
		return url;
	}
}
