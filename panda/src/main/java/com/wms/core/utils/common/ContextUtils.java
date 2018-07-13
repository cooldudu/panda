package com.wms.core.utils.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.wms.core.utils.encoding.EncodingType;

/**
 * 上下文工具类
 * 
 */
public class ContextUtils {
	/**
	 * spring mvc下获取request
	 */
	public static HttpServletRequest getRequest() {

		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	/**
	 * spring mvc下获取response
	 */
	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletWebRequest) RequestContextHolder
				.getRequestAttributes()).getResponse();
		response.setCharacterEncoding(EncodingType.UTF_8);
		return response;
	}

	/**
	 * spring mvc下获取session
	 */
	public static HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}
}
