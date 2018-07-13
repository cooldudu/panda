package com.wms.core.utils.context;

import javax.servlet.http.HttpServletRequest;

/**
 * 判断系统的验证规则是不是数字证书的方式
 * @author xb
 *
 */
public class ContextUtil {
	/**
	 * 判断系统的验证规则是不是digest数字证书的方式
	 * @param request
	 * @return
	 */
	public static boolean useDigest(HttpServletRequest request){
		String contextConfigLocation = request.getSession().getServletContext()
				.getInitParameter("contextConfigLocation");
		if (contextConfigLocation
				.indexOf("/WEB-INF/applicationContext-digest.xml") != -1){
			return true;
		}
		return false;
	}
}
