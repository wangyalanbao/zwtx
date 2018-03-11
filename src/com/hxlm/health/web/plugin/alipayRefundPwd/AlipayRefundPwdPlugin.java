/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.plugin.alipayRefundPwd;

import com.hxlm.health.web.DateEditor;
import com.hxlm.health.web.Setting;
import com.hxlm.health.web.entity.PluginConfig;
import com.hxlm.health.web.entity.Refunds;
import com.hxlm.health.web.plugin.RefundsPlugin;
import com.hxlm.health.web.plugin.weixinPay.bean.SecapiPayRefund;
import com.hxlm.health.web.util.SettingUtils;
import com.hxlm.health.web.yuntongxun.common.MD5;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

/**
 * Plugin - 支付宝(即时批量退款有密)
 * 
 * 
 * 
 */
@Component("alipayRefundPwdPlugin")
public class AlipayRefundPwdPlugin extends RefundsPlugin {

	/**
	* 支付宝消息验证地址
	*/
	private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

	@Override
	public String getName() {
		return "支付宝(即时批量退款有密)";
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
		return "alipay_refund_pwd/install.jhtml";
	}

	@Override
	public String getUninstallUrl() {
		return "alipay_refund_pwd/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl() {
		return "alipay_refund_pwd/setting.jhtml";
	}

	@Override
	public String getRequestUrl() {
		return "https://mapi.alipay.com/gateway.do";
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
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("service", "refund_fastpay_by_platform_pwd");
		parameterMap.put("partner", pluginConfig.getAttribute("partner"));
		parameterMap.put("_input_charset", "utf-8");
		parameterMap.put("sign_type", "MD5");
		parameterMap.put("notify_url", getNotifyUrl(sn, NotifyMethod.async));

		parameterMap.put("seller_user_id", pluginConfig.getAttribute("partner"));
		parameterMap.put("refund_date", DateEditor.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		parameterMap.put("batch_no", DateEditor.format(new Date(),"yyyyMMddHHmmss") +"healthlm");
		parameterMap.put("batch_num", "1");
		NumberFormat nf = NumberFormat.getInstance();
		parameterMap.put("detail_data", (refunds.getAlipayTradeNo() + "^"  + nf.format(refunds.getAmount()) +"^" +"协商退款").toString());
		parameterMap.put("sign", generateSign(parameterMap));
		return parameterMap;
	}

	@Override
	public String getParameterXml(String sn, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public SecapiPayRefund getSecapiPayRefund(String sn, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public boolean verifyNotifyWeiXin(Map refundsMap, HttpServletRequest request, HttpServletResponse response) {
		return false;
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