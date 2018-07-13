package com.wms.core.utils.common;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.wms.core.annotation.SecurityField;
import com.wms.core.exceptions.WMSException;

/**
 * 反射类相关的工具类
 *
 * @author xb
 *
 */
public class ClassUtil {
	private static final Log log = LogFactory.getLog(ClassUtil.class);

	/*
	 * 取得某一类所在包的所有类名 不含迭代
	 */
	@SuppressWarnings("unused")
	private static String[] getPackageAllClassName(String classLocation,
												   String packageName) {
		// 将packageName分解
		String[] packagePathSplit = packageName.split("[.]");
		StringBuffer realClassLocation = new StringBuffer(classLocation);
		int packageLength = packagePathSplit.length;
		for (int i = 0; i < packageLength; i++) {
			realClassLocation.append(realClassLocation).append(File.separator).append(packagePathSplit[i]);
		}
		File packeageDir = new File(realClassLocation.toString());
		if (packeageDir.isDirectory()) {
			String[] allClassName = packeageDir.list();
			return allClassName;
		}
		return null;
	}

	/**
	 * 从包package中获取所有的Class
	 *
	 * @param pack
	 * @return
	 */
	private static Set<Class<?>> getClasses(Package pack) {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = pack.getName();
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(
												packageName.length() + 1,
												name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class
													.forName(packageName + '.'
															+ className));
										} catch (ClassNotFoundException e) {
											log.error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	/**
	 * 从包package中获取所有的Class
	 *
	 * @param pack
	 * @return
	 */
	private static Set<Class<?>> getClasse(String packageName) {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(
												packageName.length() + 1,
												name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class
													.forName(packageName + '.'
															+ className));
										} catch (ClassNotFoundException e) {
											log.error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 *
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	private static void findAndAddClassesInPackageByFile(String packageName,
														 String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		if (null != dirfiles) {
			for (File file : dirfiles) {
				// 如果是目录 则继续扫描
				if (file.isDirectory()) {
					findAndAddClassesInPackageByFile(
							packageName + "." + file.getName(),
							file.getAbsolutePath(), recursive, classes);
				} else {
					// 如果是java类文件 去掉后面的.class 只留下类名
					String className = file.getName().substring(0,
							file.getName().length() - 6);
					try {
						// 添加到集合中去
						classes.add(Class
								.forName(packageName + '.' + className));
					} catch (ClassNotFoundException e) {
						log.error("添加用户自定义视图类错误 找不到此类的.class文件");
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 返回与参数类同包的其他类列表
	 *
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Set<Class<?>> getPackageClassesExceptMyself(Class clazz) {
		Set<Class<?>> result = getClasses(clazz.getPackage());
		result.remove(clazz);
		return result;
	}

	public static Set<Class<?>> getClassesFromPackage(String pack) {
		return getClasse(pack);
	}

	/**
	 * 获取该实体的字段结构
	 *
	 * @return 实体字段结构
	 * @throws WMSException
	 */
	public static Map<String, String> queryFields(Class<?> clazz)
			throws WMSException {
		Map<String, String> tmpMap = new HashMap<String, String>();
		try {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				String fieldName = null;
				String fieldType = null;
				if (method.getName().startsWith("set")) {
					fieldName = method.getName().substring(3, 4).toLowerCase()
							+ method.getName().substring(4,
							method.getName().length());
				}
				if (StringUtils.hasText(fieldName)) {
					String type = method.getParameterTypes()[0].getSimpleName();
					fieldType = ClassUtil.querySenchaType(type);
					if (StringUtils.hasText(fieldType)) {
						String getMethodName = "g"
								+ method.getName().substring(1);
						if (ObjectUtils.isEmpty(clazz.getMethod(getMethodName)
								.getAnnotation(SecurityField.class))) {
							tmpMap.put(fieldName, fieldType);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new WMSException(e.getMessage());
		}
		return tmpMap;
	}

	/**
	 * 按照给定的字段集合返回实体的字段结构
	 *
	 * @param fields指定字段的集合
	 * @return 实体字段结构
	 * @throws WMSException
	 */
	public static Map<String, String> queryFields(Class<?> clazz,
												  Collection<String> fields) throws WMSException {
		if (ObjectUtils.isEmpty(fields)) {
			return ClassUtil.queryFields(clazz);
		}
		Map<String, String> tmpMap = new HashMap<String, String>();
		try {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				String fieldName = null;
				String fieldType = null;
				if (method.getName().startsWith("set")) {
					fieldName = method.getName().substring(3, 4).toLowerCase()
							+ method.getName().substring(4,
							method.getName().length());

				}
				if (StringUtils.hasText(fieldName)) {
					String type = method.getParameterTypes()[0].getSimpleName();
					fieldType = ClassUtil.querySenchaType(type);
					if (StringUtils.hasText(fieldType)
							&& (null != fieldName && fields.contains(fieldName))) {
						String getMethodName = "g"
								+ method.getName().substring(1);
						if (ObjectUtils.isEmpty(clazz.getMethod(getMethodName)
								.getAnnotation(SecurityField.class))) {
							tmpMap.put(fieldName, fieldType);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new WMSException(e.getMessage());
		}
		return tmpMap;
	}

	/**
	 * 按照给定的字段集合返回实体的字段结构,包含级联字段
	 *
	 * @param fields 指定字段的集合
	 * @return 实体字段结构
	 * @throws WMSException
	 */
	public static Map<String, String> queryFieldsByLevel(Class<?> clazz,
														 Collection<String> fields, Object obj) throws WMSException {
		if (ObjectUtils.isEmpty(fields)) {
			return ClassUtil.queryFields(clazz);
		}
		Map<String, String> tmpMap = new HashMap<String, String>();
		try {
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				String fieldName = null;
				if (method.getName().startsWith("get")) {
					fieldName = method.getName().substring(3, 4).toLowerCase()
							+ method.getName().substring(4,
							method.getName().length());

				} else if (method.getName().startsWith("is")) {
					fieldName = method.getName().substring(2, 3).toLowerCase()
							+ method.getName().substring(3,
							method.getName().length());
				}
				if (null != fieldName
						&& fields.contains(fieldName)
						&& ObjectUtils.isEmpty(method
						.getAnnotation(SecurityField.class))) {
					tmpMap.put(fieldName, querySenchaType(method
							.getReturnType().getSimpleName()));
				} else if (null != fieldName) {
					for (String field : fields) {
						if (field.indexOf(fieldName + ".") != -1
								&& field.indexOf(".") != -1) {
							String[] fieldStrings = field.split("\\.");
							StringBuffer fn = new StringBuffer();
							for (String fieldTmp : fieldStrings) {
								String tmpFieldName = fieldTmp.substring(0, 1)
										.toUpperCase()
										+ fieldTmp.substring(1,
										fieldTmp.length());
								Method tmpMethod = obj.getClass().getMethod(
										"get" + tmpFieldName);
								if (ObjectUtils.isEmpty(tmpMethod
										.getAnnotation(SecurityField.class))) {
									obj = tmpMethod.invoke(obj);
									fn.append("_" + fieldTmp);
								} else {
									obj = null;
									break;
								}
								if (null == obj) {
									break;
								}
								// obj =
								// tmpMethod.invoke(obj)==null?"":tmpMethod.invoke(obj);
							}
							if (null != obj) {
								tmpMap.put(fieldName + "_" + fieldStrings[1],
										querySenchaType(obj.getClass()
												.getSimpleName()));
								tmpMap.put(fn.substring(1), querySenchaType(obj
										.getClass().getSimpleName()));
							}
							continue;
						}
					}
				}
			}
		}catch (NoSuchMethodException nsmex){
			throw new WMSException(nsmex.getMessage());
		}catch (InvocationTargetException itex){
			throw new WMSException(itex.getMessage());
		}catch (IllegalAccessException iaex){
			throw new WMSException(iaex.getMessage());
		}
		return tmpMap;
	}

	/**
	 * 通过java的数据类型的字符串获取对应的前端sencha的数据模型类型
	 *
	 * @param type
	 * @return 前端数据字段类型
	 */
	private static String querySenchaType(String type) {
		String fieldType = "";
		if (type.equals("Integer") || type.equals("int")) {
			fieldType = "int";
		} else if (type.equalsIgnoreCase("Short")) {
			fieldType = "number";
		} else if (type.equalsIgnoreCase("Long")) {
			fieldType = "number";
		} else if (type.equalsIgnoreCase("Float")) {
			fieldType = "number";
		} else if (type.equalsIgnoreCase("Double")) {
			fieldType = "number";
		} else if (type.equalsIgnoreCase("Byte")) {
			fieldType = "string";
		} else if (type.equals("Character")) {
			fieldType = "string";
		} else if (type.equals("String")) {
			fieldType = "string";
		} else if (type.equalsIgnoreCase("Boolean")) {
			fieldType = "boolean";
		} else if (type.equals("Date")) {
			fieldType = "date";
		}
		return fieldType;
	}

}