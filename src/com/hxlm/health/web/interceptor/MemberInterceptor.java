/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.interceptor;

import java.io.Writer;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hxlm.health.web.ErrorMsg;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.service.MemberService;
import com.hxlm.health.web.Principal;
import com.hxlm.health.web.entity.Member;

import com.hxlm.health.web.util.WebUtils;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor - 会员权限
 * 
 * 
 * 
 */
public class MemberInterceptor extends HandlerInterceptorAdapter {

	/** 重定向视图名称前缀 */
	private static final String REDIRECT_VIEW_NAME_PREFIX = "redirect:";

	/** "重定向URL"参数名称 */
	private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";

	/** "会员"属性名称 */
	private static final String MEMBER_ATTRIBUTE_NAME = "member";

	/** 区分请求来源属性名称 */
	public static final String VERSION_NAME = "version";

	/** 默认登录URL */
	private static final String DEFAULT_LOGIN_URL = "/login.jhtml";

	/** 默认登录URL */
	private static final String MOBILE_LOGIN_URL = "/mobile/logins.jhtml";

	/** 登录URL */
	private String loginUrl = DEFAULT_LOGIN_URL;

	@Value("${url_escaping_charset}")
	private String urlEscapingCharset;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
		if (principal != null) {
			return true;
		} else {
			String version = request.getHeader(VERSION_NAME);
			if (version != null) {
				ErrorMsg errorMsg = new ErrorMsg();
				errorMsg.setCode(Status.UNLOGIN);
				errorMsg.setMessage("请先登录后在进行相关操作!");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				Writer writer = response.getWriter();
				JSONObject json = JSONObject.fromObject(errorMsg);
				writer.write(json.toString());
				writer.flush();
				writer.close();
				return false;
			} else {
				if (request.getRequestURI().contains("/mobile/")) {
					String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
					response.sendRedirect(request.getContextPath() + MOBILE_LOGIN_URL + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, urlEscapingCharset));
					return false;
				} else {
					String requestType = request.getHeader("X-Requested-With");
					if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
						response.addHeader("loginStatus", "accessDenied");
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
						return false;
					} else {
						if (request.getMethod().equalsIgnoreCase("GET")) {
							String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
							response.sendRedirect(request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, urlEscapingCharset));
						} else {
							response.sendRedirect(request.getContextPath() + loginUrl);
						}
						return false;
					}
				}
			}



//			String requestType = request.getHeader("X-Requested-With");
//			if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
//				response.addHeader("loginStatus", "accessDenied");
//				response.sendError(HttpServletResponse.SC_FORBIDDEN);
//				return false;
//			} else {
//				if (request.getMethod().equalsIgnoreCase("GET")) {
//					String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
//					response.sendRedirect(request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "=" + URLEncoder.encode(redirectUrl, urlEscapingCharset));
//				} else {
//					response.sendRedirect(request.getContextPath() + loginUrl);
//				}
//				return false;
//			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			if (!StringUtils.startsWith(viewName, REDIRECT_VIEW_NAME_PREFIX)) {
				modelAndView.addObject(MEMBER_ATTRIBUTE_NAME, memberService.getCurrent());
			}
		}
	}

	/**
	 * 获取登录URL
	 * 
	 * @return 登录URL
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * 设置登录URL
	 * 
	 * @param loginUrl
	 *            登录URL
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

}