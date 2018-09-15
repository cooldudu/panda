package com.wms.core.utils.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * 对象校验工具类
 * 
 */
public class ObjectUtils {
	/**
	 * 检验对象是否为空
	 * 
	 * @param obj
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
	 * @param s
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
	 * @param s
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

	public static Object getObject(Class<?> cls) throws NoSuchMethodException, InvocationTargetException {
		Object object = null;
		try {
			object = Class.forName(cls.getName()).getDeclaredConstructor().newInstance();
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
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 判断某个字符串是否存在于数组中
	 */
	public static boolean contains(String[] stringArray, String source) {
		var tempList = Arrays.asList(stringArray);
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
	 * @param columnName
	 * @return String
	 */
	public static String javaStyle(String columnName) {
		var patternStr = "(_[a-z])";
		var pattern = Pattern.compile(patternStr);
		var matcher = pattern.matcher(columnName);
		var buf = new StringBuffer();
		while (matcher.find()) {
			var replaceStr = matcher.group();
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
}
