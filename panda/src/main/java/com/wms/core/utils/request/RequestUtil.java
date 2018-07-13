package com.wms.core.utils.request;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.wms.core.utils.common.ObjectUtils;

/**
 * request工具类
 *
 * @author xb
 *
 */
@SuppressWarnings("rawtypes")
public class RequestUtil {
	private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	private static String realPath = "";

	public static String getQueryStrings(HttpServletRequest request) {
		Enumeration<String> attributeNames = request.getAttributeNames();
		List<String> attributeNameList = new ArrayList<String>();

		// 取出所有attribute的name，但不包括currentUrl,requestUri
		while (attributeNames.hasMoreElements()) {
			String name = (String) attributeNames.nextElement();

			if (name.equals("javax.servlet.forward.servlet_path")
					|| name.equals("javax.servlet.forward.request_uri")
					|| name.equals("javax.servlet.forward.context_path")
					|| name.equals("javax.servlet.forward.query_string")
					|| name.equals("currentUrl") || name.equals("requestUri")
					|| name.equals("requestUrl") || name.equals("total")) {
				continue;
			}
			attributeNameList.add(name);
		}

		StringBuffer sb = new StringBuffer();
		Enumeration<String> parameters = request.getParameterNames();
		Vector<String> vc = new Vector<String>();
		while (parameters.hasMoreElements()) {
			String parameterName = parameters.nextElement();
			if (parameterName.equals("offset")) {
				continue;
			}
			vc.addElement(parameterName);
			sb.append("&").append(parameterName).append("=")
					.append(request.getParameter(parameterName));
		}

		for (int i = 0; i < attributeNameList.size(); i++) {
			String name = attributeNameList.get(i);
			Object obj = request.getAttribute(name);
			if (obj instanceof String) {
				if (!vc.contains(name)) {
					sb.append("&").append(name).append("=")
							.append((String) request.getAttribute(name));
				}
			}
		}
		return sb.toString();
	}

	public static String getBasePath() {
		String basePath = threadLocal.get();
		return basePath;
	}

	public static void setBasePath(String basePath) {
		threadLocal.set(basePath);
	}

	public static String getRealPath() {
		return realPath;
	}

	protected static void setRealPath(String realPath1) {
		realPath = realPath1;
		realPath = realPath.replace("\\", "/");
	}

	public static String makeParamUrl(Map<String, String> map) {
		StringBuffer result = new StringBuffer();
		if (!ObjectUtils.isEmpty(map)) {
			for (Map.Entry<String,String> entity : map.entrySet()) {
				String key = entity.getKey();
				String value = entity.getValue();
				if(StringUtils.hasText(result)){
					result.append("&"+key+"="+value);
				}else{
					result.append("?"+key+"="+value);
				}
			}
		}
		return result.toString();
	}

//	public static String getBrowser(HttpServletRequest request){
//		String Agent = request.getHeader("User-Agent");
//		Map info = Classifier.parse(Agent);
//		return (String) info.get("name");
//	}
//
//	public static String getOs(HttpServletRequest request){
//		String Agent = request.getHeader("User-Agent");
//		Map info = Classifier.parse(Agent);
//		return (String) info.get("os");
//	}

}
