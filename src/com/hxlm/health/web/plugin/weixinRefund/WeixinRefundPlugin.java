/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.plugin.weixinRefund;

import com.hxlm.health.web.DateEditor;
import com.hxlm.health.web.entity.PluginConfig;
import com.hxlm.health.web.entity.Refunds;
import com.hxlm.health.web.plugin.RefundsPlugin;
import com.hxlm.health.web.plugin.weixinPay.WeixinPayPlugin;
import com.hxlm.health.web.plugin.weixinPay.bean.SecapiPayRefund;
import com.hxlm.health.web.plugin.weixinPay.util.util.RequestHandler;
import com.hxlm.health.web.plugin.weixinPay.util.util.TenpayUtil;
import com.hxlm.health.web.util.SettingUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

/**
 * Plugin - 微信支付退款
 * 
 * 
 * 
 */
//@Component("weixinRefundPlugin")
public class WeixinRefundPlugin extends RefundsPlugin {

	/**
	* 支付宝消息验证地址
	*/
	private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

	//服务号的应用号
	public static final String APPID = "wx674b879fc693ceb9";
	//服务号的应用密码
	public final static String APP_SECRECT = "9e62e2b4f2e267251ed2b73efbdd803d";
	public static final String MCH_ID = "1260659901";
	public static final String API_KEY = "yhdflpc12171227ky3hwxzfaaaaaaaaa";

	//HTTPS证书的本地路径
	private static String certLocalPath = "";

	@Override
	public String getName() {
		return "微信支付退款";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return SettingUtils.get().getSiteName();
	}

	@Override
	public String getSiteUrl() {
		return SettingUtils.get().getSiteUrl();
	}

	@Override
	public String getInstallUrl() {
		return "weixin_refund/install.jhtml";
	}

	@Override
	public String getUninstallUrl() {
		return "weixin_refund/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl() {
		return "weixin_refund/setting.jhtml";
	}

	@Override
	public String getRequestUrl() {
		return "https://api.mch.weixin.qq.com/secapi/pay/refund";
	}

	@Override
	public RequestMethod getRequestMethod() {
		return RequestMethod.get;
	}

	@Override
	public String getRequestCharset() {
		return "UTF-8";
	}

	@Override
	public Map<String, Object> getParameterMap(String sn, String description, HttpServletRequest request) {
		PluginConfig pluginConfig = getPluginConfig();
		Refunds refunds = getRefunds(sn);
		String currTime = TenpayUtil.getCurrTime();
		//8位日期
		String strTime = currTime.substring(8, currTime.length());
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		//10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		//随机数
		String nonce_str = strReq;
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("appid",APPID);
		parameterMap.put("mch_id",MCH_ID);
		parameterMap.put("nonce_str",nonce_str);
		parameterMap.put("transaction_id",refunds.getAlipayTradeNo());
		parameterMap.put("out_trade_no",refunds.getPayment().getSn());
		parameterMap.put("out_refund_no",sn);
		parameterMap.put("total_fee",refunds.getAmount().multiply(BigDecimal.valueOf(100)).intValue());
		parameterMap.put("refund_fee",refunds.getAmount().multiply(BigDecimal.valueOf(100)).intValue());
		parameterMap.put("op_user_id", pluginConfig.getAttribute("mchid"));

		parameterMap.put("sign", generateSign(parameterMap));
		return parameterMap;
	}

	@Override
	public String getParameterXml(String sn, HttpServletRequest request, HttpServletResponse response) {
		PluginConfig pluginConfig = getPluginConfig();
		Refunds refunds = getRefunds(sn);
		String currTime = TenpayUtil.getCurrTime();
		//8位日期
		String strTime = currTime.substring(8, currTime.length());
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		//10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		//随机数
		String nonce_str = strReq;
		SortedMap<String, String> parameterMap = new TreeMap<String, String>();
		parameterMap.put("appid",APPID);
		parameterMap.put("mch_id",MCH_ID);
		parameterMap.put("nonce_str",nonce_str);
		parameterMap.put("transaction_id",refunds.getAlipayTradeNo());
		parameterMap.put("out_trade_no",refunds.getPayment().getSn());
		parameterMap.put("out_refund_no",sn);
		parameterMap.put("total_fee",refunds.getAmount().multiply(BigDecimal.valueOf(100)).toString());
		parameterMap.put("refund_fee",refunds.getAmount().multiply(BigDecimal.valueOf(100)).toString());
		parameterMap.put("op_user_id", pluginConfig.getAttribute("mchid"));

		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(WeixinPayPlugin.APPID, WeixinPayPlugin.APP_SECRECT, WeixinPayPlugin.API_KEY);

		String sign = reqHandler.createSign(parameterMap);

		String xml="<xml>"+
				"<appid>"+ APPID+"</appid>"+
				"<mch_id>"+MCH_ID+"</mch_id>"+
				"<nonce_str>"+nonce_str+"</nonce_str>"+
				"<transaction_id>"+refunds.getAlipayTradeNo()+"</transaction_id>"+
				"<out_refund_no>"+sn+"</out_refund_no>"+
				"<out_trade_no>"+refunds.getPayment().getSn()+"</out_trade_no>"+
				"<total_fee>"+refunds.getAmount().multiply(BigDecimal.valueOf(100)).intValue()+"</total_fee>"+
				"<refund_fee>"+refunds.getAmount().multiply(BigDecimal.valueOf(100)).intValue()+"</refund_fee>"+
				"<op_user_id>"+pluginConfig.getAttribute("mchid")+"</op_user_id>"+
				"<sign>"+sign+"</sign>"+
				"</xml>";
		return xml;
	}

	@Override
	public SecapiPayRefund getSecapiPayRefund(String sn, HttpServletRequest request, HttpServletResponse response){
		SecapiPayRefund secapiPayRefund = new SecapiPayRefund();
		PluginConfig pluginConfig = getPluginConfig();
		Refunds refunds = getRefunds(sn);
		String currTime = TenpayUtil.getCurrTime();
		//8位日期
		String strTime = currTime.substring(8, currTime.length());
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		//10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		//随机数
		String nonce_str = strReq;
		SortedMap<String, String> parameterMap = new TreeMap<String, String>();
		parameterMap.put("appid",APPID);
		parameterMap.put("mch_id",pluginConfig.getAttribute("mchid"));
		parameterMap.put("nonce_str",nonce_str);
		parameterMap.put("transaction_id",refunds.getAlipayTradeNo());
		parameterMap.put("out_trade_no",refunds.getPayment().getSn());
		parameterMap.put("out_refund_no",sn);
		parameterMap.put("total_fee",refunds.getAmount().multiply(BigDecimal.valueOf(100)).toString());
		parameterMap.put("refund_fee",refunds.getAmount().multiply(BigDecimal.valueOf(100)).toString());
		parameterMap.put("op_user_id", pluginConfig.getAttribute("mchid"));

		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(WeixinPayPlugin.APPID, WeixinPayPlugin.APP_SECRECT, WeixinPayPlugin.API_KEY);

		String sign = reqHandler.createSign(parameterMap);

		secapiPayRefund.setAppid(pluginConfig.getAttribute("appid"));
		secapiPayRefund.setMch_id(pluginConfig.getAttribute("mchid"));
		secapiPayRefund.setNonce_str(nonce_str);
		secapiPayRefund.setTransaction_id(refunds.getAlipayTradeNo());
		secapiPayRefund.setOut_trade_no(refunds.getPayment().getSn());
		secapiPayRefund.setOut_refund_no(sn);
		secapiPayRefund.setTotal_fee(refunds.getAmount().multiply(BigDecimal.valueOf(100)).intValue());
		secapiPayRefund.setRefund_fee(refunds.getAmount().multiply(BigDecimal.valueOf(100)).intValue());
		secapiPayRefund.setOp_user_id(pluginConfig.getAttribute("mchid"));
		secapiPayRefund.setSign(sign);
		return secapiPayRefund;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyNotifyWeiXin(Map refundsMap, HttpServletRequest request, HttpServletResponse response) {
		SortedMap<String, String> parameterMap = new TreeMap<String, String>(refundsMap);
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(WeixinPayPlugin.APPID, WeixinPayPlugin.APP_SECRECT, WeixinPayPlugin.API_KEY);

		String sign = reqHandler.createSign(parameterMap);

		boolean isSign = false;
		if(sign.equals(refundsMap.get("sign"))){
			return true;
		}else{
			System.out.println("生成的签名结果不正确，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean verifyNotify(HttpServletRequest request) {
		String responseTxt = "true";
		if(request.getParameter("notify_id") != null) {
			String notify_id = request.getParameter("notify_id");
			responseTxt = verifyResponse(notify_id);
		}
		if(!responseTxt.equals("true")){
			System.out.println("获取远程服务器ATN结果不是true，与服务器设置问题、合作身份者ID、notify_id一分钟失效有关");
		}

		boolean isSign = false;
		if(generateSign(request.getParameterMap()).equals(request.getParameter("sign"))){
			isSign = true;
		}
		if(!isSign){
			System.out.println("生成的签名结果不正确，与安全校验码、请求时的参数格式（如：带自定义参数等）、编码格式有关");
		}
		if(isSign && responseTxt.equals("true")){
			return true;
		}

		return false;
	}

	@Override
	public String getNotifyMessage(String sn, NotifyMethod notifyMethod, HttpServletRequest request) {
		if (notifyMethod == NotifyMethod.async) {
			return "success";
		}
		return null;
	}

	@Override
	public Integer getTimeout() {
		return 21600;
	}

	/**
	 * 生成签名
	 * 
	 * @param parameterMap
	 *            参数
	 * @return 签名
	 */
	private String generateSign(Map<String, ?> parameterMap) {
		PluginConfig pluginConfig = getPluginConfig();
		return DigestUtils.md5Hex(joinKeyValue(new TreeMap<String, Object>(parameterMap), null, pluginConfig.getAttribute("key"), "&", true, "sign_type", "sign"));
	}

	/**
	 * 获取远程服务器ATN结果,验证返回URL
	 * @param notify_id 通知校验ID
	 * @return 服务器ATN结果
	 * 验证结果集：
	 * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
	 * true 返回正确信息
	 * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private  String verifyResponse(String notify_id) {
		//获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		PluginConfig pluginConfig = getPluginConfig();
		String partner = pluginConfig.getAttribute("partner");
		String veryfy_url = HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;

		return checkUrl(veryfy_url);
	}

	/**
	 * 获取远程服务器ATN结果
	 * @param urlvalue 指定URL路径地址
	 * @return 服务器ATN结果
	 * 验证结果集：
	 * invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空
	 * true 返回正确信息
	 * false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	 */
	private static String checkUrl(String urlvalue) {
		String inputLine = "";

		try {
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection
					.getInputStream()));
			inputLine = in.readLine().toString();
		} catch (Exception e) {
			e.printStackTrace();
			inputLine = "";
		}

		return inputLine;
	}

}