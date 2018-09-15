package com.wms.core.utils.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 调用properties配置文件数据的工具类
 *
 * @author xb
 * @since 2009-11-6
 */
public final class ResourceBundleUtil
{
	/**
	 * 默认文件名称
	 */
	public static final String DEFAULT_PROPERTY_FILE_NAME = "application";

	/**
	 * 根据文件名称和键名称获取对应的字符串数据
	 *
	 * @param propertyFileName 文件名不写后缀
	 * @param propertyName
	 * @return
	 */
	public static String getString(String propertyFileName, String propertyName) {
		return ResourceBundle.getBundle(propertyFileName).getString(
				propertyName);
	}

	/**
	 * 根据文件名称和键名称获取对应的长整形数据
	 *
	 * @param propertyFileName
	 * @param propertyName
	 * @return
	 */
	public static Long getLong(String propertyFileName, String propertyName) {
		try {
			return Long.parseLong(ResourceBundle.getBundle(propertyFileName)
					.getString(propertyName));
		} catch (Exception ex) {
			return Long.parseLong("0");
		}
	}

	/**
	 * 根据文件名称和键名称获取对应的整形数据
	 *
	 * @param propertyFileName
	 * @param propertyName
	 * @return
	 */
	public static Integer getInteger(String propertyFileName,
									 String propertyName) {
		try {
			return Integer.parseInt(ResourceBundle.getBundle(propertyFileName)
					.getString(propertyName));
		} catch (Exception ex) {
			return Integer.parseInt("0");
		}
	}

	/**
	 * 根据文件名称和键名称获取对应的短整形数据
	 *
	 * @param propertyFileName
	 * @param propertyName
	 * @return
	 */
	public static Short getShort(String propertyFileName,
								 String propertyName) {
		try {
			return Short.parseShort(ResourceBundle.getBundle(propertyFileName)
					.getString(propertyName));
		} catch (Exception ex) {
			return Short.parseShort("0");
		}
	}

	/**
	 * 根据文件名称和键名称获取对应的布尔类型数据
	 *
	 * @param propertyFileName
	 * @param propertyName
	 * @return
	 */
	public static Boolean getBoolean(String propertyFileName,
									 String propertyName) {
		try {
			return Boolean.parseBoolean(ResourceBundle.getBundle(propertyFileName)
					.getString(propertyName));
		} catch (Exception ex) {
			return false;
		}
	}

	public static Properties getProperties(String propertyFileName){
		var i = propertyFileName.lastIndexOf(".");
		if(i==-1){
			propertyFileName +=".properties";
		}
		var in = getInputStream("/"+propertyFileName);
		var p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	public static InputStream getInputStream(String path){
		return ResourceBundleUtil.class.getResourceAsStream(path);
	}
}
