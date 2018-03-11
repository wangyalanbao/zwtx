package com.hxlm.health.web.yuntongxun.demoaccounts;


import com.hxlm.health.web.yuntongxun.common.*;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
* @Description: TODO(账号信息的获取) 
* @author： baohj
* @date： 2014年9月13日 下午1:39:31
 */
@Controller
public class AccountMsg {
	protected final Logger logger = Logger.getLogger(AccountMsg.class);
	public final static String SECRET_KEY = "a04daaca96294836bef207594a0a4df8";
	/*
	* @Title: helloworld 
	* @Description: 获取主账号详细信息
	* @param mail 注册账号
	* @param password 密码
	* @param response
	* @param request
	* @return String    返回类型
	* @throws
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/getAccountMsg")
	public String helloworld(String mail, String password,
			HttpServletResponse response, HttpServletRequest request) {
		//获取拼装后的访问地址
		String url = this.getUrl();
		HttpClient httpclient = CommonUtil.enableSSL();
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Content-Type", "application/xml;charset=utf-8");
		/* 获取包体 */
		String body = this.getvoiceCodeParm(mail,password);
		//请求包体加密
		body= Cryptos.toBase64QES(SECRET_KEY, body);
		logger.info(String.format("rest url：%s,\r\n request body：%s",url,body));
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
			String statusMsg="";
			if(errorCode.equals("000000")){
				this.createSelect(conResult,request);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (DocumentException e) {
			e.printStackTrace();
		}
		return "tiyanmoshiselectVoip_login";
	}
	/*
	* @Title: createSelect 
	* @Description: TODO(把返回的账号信息,放在下拉框中返回给页面) 
	* @param result
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@SuppressWarnings("rawtypes")
	public void createSelect(String result,HttpServletRequest request){
		StringBuffer sb=new StringBuffer();
		try {
			//把xml字符创result解析成dom对象
		 Document document = DocumentHelper.parseText(result);
		Element root = document.getRootElement();
		//主账号
		String main_account=root.element("main_account").getText();
		//主账号密码
		String main_token=root.element("main_token").getText();
		//应用
		Element Application=root.element("Application");
		String appId=Application.element("appId").getText();
		List subs = Application.elements("SubAccount");
		sb.append("<select onchange='changeVoip(this);' class='select'>");
		sb.append("<option value=''>请选择...</option>");
		for (Iterator it = subs.iterator(); it.hasNext();) {
		   Element node = (Element) it.next();
		   String sub_account=node.elementText("sub_account");
		   String sub_token=node.elementText("sub_token");
		   String voip_account=node.elementText("voip_account");
		   String voip_token=node.elementText("voip_token");
		   
		   sb.append("<option value='"+sub_account+"-"+sub_token+"-"+voip_account+"-"+voip_token+"'>"+voip_account+"</option>");
		}
		sb.append("<select class='select'>");
		request.setAttribute("main_account", main_account);
		request.setAttribute("main_token", main_token);
		request.setAttribute("appId", appId);
		request.setAttribute("voips", sb.toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/*
	* @Title: getvoiceCodeParm 
	* @Description: TODO(获取语音验证码xml) 
	* @param mail 用户名
	* @param psssword 密码
	* @return String    返回类型
	* @throws
	 */
	public String getvoiceCodeParm(String mail, String psssword) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("Request");
		root.addElement("user_name").setText(mail);
		root.addElement("user_pwd").setText(psssword);
		String xml = document.asXML();
		return xml;
	}
	//拼装请求地址
	public String getUrl() {
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/General/GetDemoAccounts";
		return url;
	}
	
	/*
	* @Title: getSubByAppid 
	* @Description: TODO(由应用id，获取所有子账号) 
	* @param application 应用id
	* @param mainToken  主账号
	* @param mainAccount 主账号密码
	* @param flag 
	* @param voip  当前用户子账号voip
	* @param response
	* @param request
	* @return void    返回类型
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSubByAppid")
	public  void getSubByAppid(String application, String mainToken,
			String mainAccount,String flag,String voip,
			HttpServletResponse response, HttpServletRequest request){
		String sig = MD5.md5Digest(mainAccount + mainToken
				+ CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/Accounts/" + mainAccount
				+ "/GetSubAccounts?sig=" + sig;
		PrintWriter out=null;
		try {
			out = response.getWriter();
		String Authorization = Base64.encode((mainAccount + ":" + CommonUtil
				.formatDate()).getBytes("UTF-8"));
		HttpClient httpclient = CommonUtil.enableSSL();
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Content-Type", "application/xml;charset=utf-8");
		httppost.setHeader("Authorization", Authorization);
		/* 获取包体 */
		Document document = DocumentHelper.createDocument();
		Element root1 = document.addElement("SubAccount");
		root1.addElement("appId").setText(application);
		String body = document.asXML();
		//请求包体加密
		logger.info(String.format("rest url：%s,\r\n request body：%s",url,body));
		StringBuffer sb=new StringBuffer();
		StringBuffer voips=new StringBuffer();
		StringBuffer contact=new StringBuffer();
		HttpEntity entity = new ByteArrayEntity(body.getBytes("utf-8"));
		httppost.setEntity(entity);
		HttpResponse httpresponse = httpclient.execute(httppost);
		String conResult = EntityUtils.toString(httpresponse.getEntity());
		int statusCode = httpresponse.getStatusLine().getStatusCode();
		logger.info("response statusCode:" + statusCode
				+ ";\r\n response body:" + conResult);
		Document doc = DocumentHelper.parseText(conResult);
		Element root = doc.getRootElement();
		//获取错误码
		String errorCode=root.element("statusCode").getText();
		//如果返回错误结果，打印日志
		response.setCharacterEncoding("utf-8");
		List<String> member=new ArrayList<String>();
		voips.append("<select class='select'  name='voip' id='voip'>");
		if(errorCode.equals(Constants.SuccessCode)){
			List<Element> elements=(List<Element>) root.elements("SubAccount");
			for(int i=0;i<elements.size();i++){
				Element e=elements.get(i);
				String voipAccount=e.elementText("voipAccount");
				if(voip!=null&&!voip.equals(voipAccount)){
					if("0".equals(flag)){
					/*联系人列表	*/
						contact.append("<li class='clearfix' style='cursor:hand' onclick='sendto(this,\"\");'>");
						contact.append("<img class='touxiang' src='images/b_touxiang.jpg' alt=''/>");
						contact.append("<p>"+voipAccount+"</p></li>");
					}else if("1".equals(flag)){
						voips.append("<option>"+voipAccount+"</option>");
					}else if("2".equals(flag)){
						member.add(voipAccount);
					}
				}
			}
			voips.append("</select>");
		}
		if("0".equals(flag))
		out.write(contact.toString());
		else if("1".equals(flag)){
			out.write(voips.toString());
			}else if("2".equals(flag)){
				out.write(member.toString().replace("[","").replace("]",""));
			}
			
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
}
