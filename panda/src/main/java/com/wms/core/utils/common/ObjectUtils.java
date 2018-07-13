package com.wms.core.utils.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 对象校验工具类
 * 
 */
public class ObjectUtils {
	/**
	 * 检验对象是否为空
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		
		if (obj == null) {
			return true;
		} 
		else if("null".equals(obj)){
			return true;
		}
		else if (obj instanceof String && (obj.equals(""))) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)) {
			return true;
		} else if (obj instanceof Collection && ((Collection<?>) obj).isEmpty()
				&& ((Collection<?>) obj).size() <= 0) {
			return true;
		} else if (obj instanceof Map && ((Map<?, ?>) obj).isEmpty()
				&& ((Map<?, ?>) obj).size() <= 0) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length <= 0) {
			return true;
		}
		return false;

	}

	/**
	 * 返回字符串如为空返回默认值
	 * 
	 * @param param
	 * @return
	 */
	public static Integer getIntParameter(String param) {
		String str = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getParameter(param);
		return ConvertUtils.getInteger(str);
	}

	/**
	 * 返回字符串如为空返回默认值
	 * 
	 * @param param
	 * @return
	 */
	public static Integer getIntParameter(HttpServletRequest request,
			String param) {
		String str = request.getParameter(param);
		return ConvertUtils.getInteger(str);
	}

	/**
	 * 返回字符串如为空返回默认值
	 * 
	 * @param param
	 * @return
	 */
	public static String getParameter(String param, String defval) {
		String str = ContextUtils.getRequest().getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return defval;
		}
		return str.toString();
	}

	/**
	 * 返回字符串如为空返回默认值
	 * 
	 * @param param
	 * @return
	 */
	public static String getParameter(String param) {
		String str = ContextUtils.getRequest().getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return "";
		}
		return str.toString();
	}

	/**
	 * 返回字符串如为空返回默认值
	 * 
	 * @param param
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String param) {
		String str = request.getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return "";
		}
		return str.toString();
	}

	/**
	 * 返回字符串如为空返回默认值
	 * 
	 * @param str
	 * @param defval
	 * @return
	 */
	public static String getString(Object s, String defval) {
		if (isEmpty(s)) {
			return (defval);
		}
		return (s.toString().trim());
	}

	/**
	 * 返回Integer如为空返回默认值
	 * 
	 * @param str
	 * @param defval
	 * @return
	 */
	public static Integer getInteger(Integer s, Integer defval) {
		if (isEmpty(s)) {
			return (defval);
		}
		return (s);
	}

	/**
	 * 比较两个Integer的大小，返回较大的Integer
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static Integer getIntegerAbs(Integer s1, Integer s2) {
		if (Math.abs(s1) > Math.abs(s2)) {
			return Math.abs(s1);
		} else {
			return Math.abs(s2);
		}
	}

	/**
	 * 返回某Integer的负值
	 * 
	 * @param s1
	 * @return
	 */
	public static Integer getIntegerNegative(Integer s1) {
		return -s1;
	}

	public static Object getObject(Class<?> cls) {
		Object object = null;
		try {
			object = Class.forName(cls.getName()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 检验字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (StringUtils.isEmpty(str)) {
			return true;
		}
		return false;
	}

	/**
	 * 检验字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 判断某个字符串是否存在于数组中
	 */
	public static boolean contains(String[] stringArray, String source) {
		List<String> tempList = Arrays.asList(stringArray);
		if (tempList.contains(source)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * java风格编程：驼峰式命名<br/>
	 * eg:user_name -> userName
	 * 
	 * @param tableName
	 * @return String
	 */
	public static String javaStyle(String columnName) {
		String patternStr = "(_[a-z])";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(columnName);
		StringBuffer buf = new StringBuffer();
		while (matcher.find()) {
			String replaceStr = matcher.group();
			matcher.appendReplacement(buf, replaceStr.toUpperCase());
		}
		matcher.appendTail(buf);
		return buf.toString().replaceAll("_", "");
	}

	/**
	 * 生成UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();

	}

	/**
	 * 获取Map
	 */
	public static Map<String, Object> getHashMap() {
		return new HashMap<String, Object>();
	}

	/**
	 * 获取List
	 * 
	 * @param <T>
	 */
	public static <T> List<T> getArrayList() {
		return new ArrayList<T>();
	}

	/**
	 * 获取客户端IP
	 */
	public static String getIpAddr() {
		HttpServletRequest request = ContextUtils.getRequest();
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
