/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.interceptor;

import com.hxlm.health.web.ErrorMsg;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.Result;
import com.hxlm.health.web.Status;
import com.hxlm.health.web.util.JsonUtils;
import com.hxlm.health.web.util.WebUtils;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.UUID;

/**
 * Interceptor - 令牌
 * 
 * 
 * 
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	/** "令牌"属性名称 */
	private static final String TOKEN_ATTRIBUTE_NAME = "token";

	/** "令牌"Cookie名称 */
	private static final String TOKEN_COOKIE_NAME = "token";

	/** "令牌"参数名称 */
	private static final String TOKEN_PARAMETER_NAME = "token";

	/** 错误消息 */
	private static final String ERROR_MESSAGE = "Bad or missing token!";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = WebUtils.getCookie(request, TOKEN_COOKIE_NAME);
		if (request.getMethod().equalsIgnoreCase("POST")) {
			String requestType = request.getHeader("X-Requested-With");
			if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
				if (token != null && token.equals(request.getHeader(TOKEN_PARAMETER_NAME))) {
					return true;
				} else {
					response.addHeader("tokenStatus", "accessDenied");
				}
			} else {
				if (token != null && token.equals(request.getParameter(TOKEN_PARAMETER_NAME))) {
					return true;
				}
			}

			if (token == null) {
				token = UUID.randomUUID().toString();
				WebUtils.addCookie(request, response, TOKEN_COOKIE_NAME, token);
			}
			//response.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE);

			if (request.getRequestURI().contains("/app/register") || request.getRequestURI().contains("/app/login")) {
				return true;
			}

			ErrorMsg errorMsg = new ErrorMsg();
			errorMsg.setCode(Status.TOKEN_ERROR);
			errorMsg.setMessage("TOKEN错误!");

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Writer writer = response.getWriter();
			JSONObject json = JSONObject.fromObject(errorMsg);

			writer.write(JsonUtils.toJson(errorMsg));
			writer.flush();
			writer.close();
			return false;
		} else {
			if (token == null) {
				token = UUID.randomUUID().toString();
				WebUtils.addCookie(request, response, TOKEN_COOKIE_NAME, token);
			}
			request.setAttribute(TOKEN_ATTRIBUTE_NAME, token);
			return true;
		}
	}

}