package com.wms.core.utils.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.regex.Pattern;

import com.wms.core.utils.common.ConvertUtils;
import com.wms.core.utils.common.ObjectUtils;

/**
 *通过反射来动态调用get 和 set 方法
 */
public class ReflectHelper {

	private Class<? extends Object> cls;
	/**
	 * 传过来的对象
	 */
	public Object obj;

	/**
	 * 存放get方法
	 */
	private Hashtable<String, Method> getMethods = null;
	/**
	 * 存放父类get方法
	 */
	private Hashtable<String, Method> getSuperMethods = null;

	/**
	 * 存放set方法
	 */
	private Hashtable<String, Method> setMethods = null;
	/**
	 * 存放父类set方法
	 */
	private Hashtable<String, Method> setSuperMethods = null;

	/**
	 * 定义构造方法 -- 一般来说是个pojo
	 *
	 * @param o
	 *            目标对象
	 */
	public ReflectHelper(Object o) {
		obj = o;
		initMethods();
		initSuperMethods();
	}

	/**
	 * 定义构造方法
	 */
	public ReflectHelper(Class<? extends Object> cls) {
		this.cls = cls;
		initObject();
		initMethods();
	}

	public void initObject() {
		try {
			this.obj = Class.forName(cls.getName()).getDeclaredConstructor().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @desc 初始化
	 */
	public void initMethods() {
		getMethods = new Hashtable<String, Method>();
		setMethods = new Hashtable<String, Method>();
		cls = obj.getClass();
		var methods = cls.getMethods();
		// 定义正则表达式，从方法中过滤出getter / setter 函数.
		var gs = "get(\\w+)";
		var getM = Pattern.compile(gs);
		var is = "is(\\w+)";/*处理变量声明为boolean情况，建议变量声明为Boolean*/
		var isM = Pattern.compile(is);
		var ss = "set(\\w+)";
		var setM = Pattern.compile(ss);
		// 把方法中的"set" 或者 "get" 去掉
		var rapl = "$1";
		String param;
		for (int i = 0; i < methods.length; ++i) {
			var m = methods[i];
			var methodName = m.getName();
			if (Pattern.matches(gs, methodName)) {
				param = getM.matcher(methodName).replaceAll(rapl).toLowerCase();
				getMethods.put(param, m);
			}else if (Pattern.matches(is, methodName)) {
				param = isM.matcher(methodName).replaceAll(rapl).toLowerCase();
				getMethods.put(param, m);
			}
			else if (Pattern.matches(ss, methodName)) {
				param = setM.matcher(methodName).replaceAll(rapl).toLowerCase();
				setMethods.put(param, m);
			}
		}
	}

	/**
	 *
	 */
	public void initSuperMethods() {
		getSuperMethods = new Hashtable<String, Method>();
		setSuperMethods = new Hashtable<String, Method>();
		cls = obj.getClass();
		Method[] superMethods = cls.getSuperclass().getMethods();
		// 定义正则表达式，从方法中过滤出getter / setter 函数.
		String gs = "get(\\w+)";
		Pattern getM = Pattern.compile(gs);
		String is = "is(\\w+)";
		Pattern isM = Pattern.compile(is);
		String ss = "set(\\w+)";
		Pattern setM = Pattern.compile(ss);
		// 把方法中的"set" 或者 "get" 去掉
		String rapl = "$1";
		String param;
		for (int i = 0; i < superMethods.length; ++i) {
			Method m = superMethods[i];
			String methodName = m.getName();
			if (Pattern.matches(gs, methodName)) {
				param = getM.matcher(methodName).replaceAll(rapl).toLowerCase();
				getSuperMethods.put(param, m);
			}else if (Pattern.matches(is, methodName)) {
				param = isM.matcher(methodName).replaceAll(rapl).toLowerCase();
				getSuperMethods.put(param, m);
			}
			else if (Pattern.matches(ss, methodName)) {
				param = setM.matcher(methodName).replaceAll(rapl).toLowerCase();
				setSuperMethods.put(param, m);
			}
		}
	}

	/**
	 *
	 * @desc 调用set方法
	 */
	public boolean setMethodValue(String property, Object object) {
		if (property.indexOf("_") != -1) {
			String parentproperty = "";
			parentproperty = property.substring(0, property.indexOf("_"));// 引用对象
			String fieldName = property.substring(property.indexOf("_") + 1);// 引用对象字段名
			Object obj = getMethodValue(parentproperty);
			if (!ObjectUtils.isEmpty(obj)) {
				ReflectHelper reflectHelper = new ReflectHelper(obj);
				return reflectHelper.setMethodValue(fieldName, object);
			} else {
				return false;
			}
		}
		Method m = setMethods.get(property.toLowerCase());
		if (m != null) {
			try {
				// 调用目标类的setter函数
				m.invoke(obj, converter(m, object));
				return true;
			} catch (Exception ex) {
				System.out.println("invoke getter on " + property + " error: " + ex.toString());
				return false;
			}
		}
		return false;
	}

	/**
	 * 得到方法运行的结果
	 * @desc 调用get方法
	 */
	@SuppressWarnings("unchecked")
	public <T> T getMethodValue(String property) {
		Object value = null;
		String fieldName = "";
		if (property.indexOf("_") != -1) {
			String parentproperty = property.substring(0, property.indexOf("_"));// 引用对象
			fieldName = property.substring(property.indexOf("_") + 1);// 引用对象字段名
			Object obj = getMethodValue(parentproperty);
			if (!ObjectUtils.isEmpty(obj)) {
				ReflectHelper reflectHelper = new ReflectHelper(obj);
				value = reflectHelper.getMethodValue(fieldName);
			} else {
				value = null;
			}
		}
		Method m = getMethods.get(property.toLowerCase());
		if (m != null) {
			try {
				value = m.invoke(obj, new Object[] {});
			} catch (Exception ex) {
				System.out.println("invoke getter on " + property + " error: " + ex.toString());
			}
		}
		return (T) value;
	}

	/**
	 *调用get方法
	 */
	public <T> T getMethodValue(String property, T defval) {
		if (!ObjectUtils.isEmpty(property)) {
			return getMethodValue(property);
		} else {
			return defval;
		}
	}

	/**
	 *
	 * @desc 调用set方法
	 */
	public Object getSuperMethodValue(String property) {
		Object value = null;
		Method m = getSuperMethods.get(property.toLowerCase());
		if (m != null) {
			try {
				value = m.invoke(obj, new Object[] {});
			} catch (Exception ex) {
				System.out.println("invoke getter on " + property + " error: " + ex.toString());
			}
		}
		return value;
	}

	/**
	 * 类型强制转换
	 */
	public Object converter(Method m, Object object) {
		Type[] paramTypeList = m.getGenericParameterTypes();// 方法的参数列表
		if (paramTypeList[0].toString().equals("class java.lang.String")) {
			object = ConvertUtils.getString(object);
		}
		if (paramTypeList[0].toString().equals("class java.lang.Double")) {
			object = ConvertUtils.getDouble(object);
		}
		if (paramTypeList[0].toString().equals("class java.lang.Integer")) {
			object = ConvertUtils.getInteger(object);
		}
		return object;

	}
}