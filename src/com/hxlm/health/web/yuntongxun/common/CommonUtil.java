package com.hxlm.health.web.yuntongxun.common;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CommonUtil {
	
	/*
	* @Title: formatDate 
	* @Description: 格式化当前时间
	* @return：String
	* @throws
	 */
	public static String formatDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}
	
	/*
	* @Title: formatDate 
	* @Description: 格式化时间
	* @param date
	* @return：String
	* @throws
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/*
	* @Title: getRandom 
	* @Description: 生成随机数 lenght:随机数的个数
	* @param lenght 生成字符串的字符数
	* @return：String
	* @throws
	 */
	public static String getRandom(int lenght) {
		String[] randomValues = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < lenght; i++) {
			Double number = Math.random() * (randomValues.length - 1);
			str.append(randomValues[number.intValue()]);
		}

		return str.toString();
	}
	
	/*
	* @Title: enableSSL 
	* @Description: TODO(跳过证书验证) 
	* @return:HttpClient
	* @throws
	 */
	@SuppressWarnings("deprecation")
	public static HttpClient enableSSL() {
		// 调用ssl
		try {
			HttpClient httpclient = new DefaultHttpClient();
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
			org.apache.http.conn.ssl.SSLSocketFactory sf = new org.apache.http.conn.ssl.SSLSocketFactory(
					sslcontext);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Scheme https = new Scheme("https", 8080, (SchemeSocketFactory) sf);
			httpclient.getConnectionManager().getSchemeRegistry()
					.register(https);
			return httpclient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 重写验证方法，取消检测ssl
	 */
	private static TrustManager truseAllManager = new X509TrustManager() {
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};
	
	/*
	* @Title: getRoot 
	* @Description: 获取xml字符创result的根节点
	* @param result
	* @return Element
	* @throws
	 */
	public static Element getRoot(String result){
		Document document=null;
		Element root=null;
		try {
			document = DocumentHelper.parseText(result);
			root = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return root;
	}
	/*
	* @Title: createXml 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param rootName 根节点 
	* @param data
	* @return
	* @return String    返回类型
	* @throws
	 */
	public static String createXml(String rootName,Map<String,String> data){
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(rootName);
		if(data!=null){
			for(String key:data.keySet()){
				root.addElement(key).setText(data.get(key));
			}	
		}
		String xml = document.asXML();
		return xml;
	}
	/*
	* @Title: getAuthorizationByAccount 
	* @Description: TODO(获取Authorization信息,用于http头信息) 
	* @param account
	* @return
	* @return String    返回类型
	* @throws
	 */
	public static String getAuthorizationByAccount(String account) {
		String Authorization="";
		try {
			Authorization = Base64.encode((account + ":" + formatDate()).getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Authorization;
	}
	/*
	* @Title: getUrlByMainAccount 
	* @Description: TODO(通过主账号信息，获取url) 
	* @param mainAccount
	* @param mainToken
	* @return String    返回类型
	* @throws
	 */
	public static String getUrlByMainAccount(String mainAccount,String mainToken,String apiName){
		String sig = MD5.md5Digest(mainAccount + mainToken
				+ CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/Accounts/" + mainAccount
				+ "/"+apiName+"?sig=" + sig;
		return url;
		
	}

/*
* @Title: getUrlBySubAccount 
* @Description: TODO(通过子账号信息,获取url) 
* @param subAccount
* @param subToken
* @param apiName 接口名称
* @return
* @return String    返回类型
* @throws
 */
	public static String getUrlBySubAccount(String subAccount,String subToken,String apiName){
		String sig = MD5.md5Digest(subAccount + subToken + CommonUtil.formatDate());
		String url = "https://" + PropertiesHolder.getValue(Constants.RESTIP) + ":" + PropertiesHolder.getValue(Constants.RESTPOST)
				+ "/2013-12-26/SubAccounts/" + subAccount
				+ "/"+apiName+"?sig=" + sig;
		return url;
	}
	
}
