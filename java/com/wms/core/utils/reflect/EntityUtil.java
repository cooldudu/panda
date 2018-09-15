package com.wms.core.utils.reflect;



/**
 * 反射工具类
 *
 */
public class EntityUtil {
	/**
	 * 根据实体名称获取Class对象
	 * @param className
	 * @return
	 */
	public static Class<?> getClassByName(String className){
		Class<?> clazz=null;
		try {
			clazz=Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

}
