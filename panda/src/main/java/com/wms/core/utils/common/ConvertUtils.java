package com.wms.core.utils.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialClob;
import org.springframework.util.StringUtils;

/**
 * 类型转换工具
 * @author xb
 *
 */
public class ConvertUtils {
	final static int BUFFER_SIZE = 4096;
	private static final char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	/**
	 * 字符串转数字
	 *
	 * @param object
	 * @return
	 */
	public static Integer getInteger(Object object) {
		Integer i = 0;
		if (ObjectUtils.isEmpty(object)) {
			return i;
		}
		try {
			i = (Integer.parseInt(getString(object)));
		} catch (NumberFormatException e) {
			return i;
		}
		return i;
	}

	/**
	 * 字符串转数字
	 *
	 * @param str
	 * @return
	 */
	public static Short getShort(String str) {
		Short i = null;
		if (ObjectUtils.isEmpty(str)) {
			return i;
		}
		try {
			i = (Short.parseShort(str));
		} catch (NumberFormatException e) {
			return i;
		}
		return i;
	}

	/**
	 * 字符串转Long
	 *
	 * @param str
	 * @return
	 */
	public static Long getLong(String str) {
		Long l = -1l;
		if (ObjectUtils.isEmpty(str)) {
			return l;
		}
		try {
			l = (Long.parseLong(str));
		} catch (NumberFormatException e) {
			return l;
		}
		return l;
	}

	/**
	 * 字符串转Double
	 *
	 * @param s
	 * @return
	 */
	public static Double getDouble(String s) {
		Double d = null;
		if (ObjectUtils.isEmpty(s)) {
			return d;
		}
		try {
			d = (Double.parseDouble(s));
		} catch (NumberFormatException e) {
			return d;
		}
		return d;
	}

	public static String getString(Object object) {
		if (ObjectUtils.isEmpty(object)) {
			return "";
		}
		return (object.toString().trim());
	}

	/**
	 * 字符串转Double
	 *
	 * @param object
	 * @return
	 */
	public static Double getDouble(Object object) {
		Double d = null;
		if (ObjectUtils.isEmpty(object)) {
			return d;
		}
		try {
			d = (Double.parseDouble(getString(object)));
		} catch (NumberFormatException e) {
			return d;
		}
		return d;
	}

	/**
	 * CLOB类型转换成String类型
	 */

	public String clobToString(Clob clob) throws SQLException, IOException {

		String reString = null;
		Reader is = null;
		var sb = new StringBuffer();
		BufferedReader br = null;
		try {
			is = clob.getCharacterStream();// 得到流
			br = new BufferedReader(is);
			var s = br.readLine();
			while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
				sb.append(s);
				s = br.readLine();
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}finally {
			if(null!= is){
				is.close();
			}
			if(null!=br){
				br.close();
			}
		}
		reString = sb.toString();
		return reString;
	}

	/**
	 * 将String转成Clob
	 *
	 * @param str
	 */
	public static Clob stringToClob(String str) {
		if (null == str)
			return null;
		else {
			try {
				Clob c = new SerialClob(str.toCharArray());
				return c;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 解析页面经过encodeURI编码的字符串
	 *
	 * @param str
	 */
	public static String getURLDecoder(String str) {
		var s = "";
		try {
			s = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 字符串转Long
	 *
	 * @param l
	 * @return
	 */
	public static Integer getInteger(Long l) {
		Integer i = null;
		if (ObjectUtils.isEmpty(l)) {
			return i;
		}
		try {
			i = l.intValue();
		} catch (NumberFormatException e) {
			return i;
		}
		return i;
	}

	/**
	 * List转换为数组
	 *
	 * @param <T>
	 * @param <T>
	 */
	public static <T> Object[] listToArray(List<T> list) {
		var strs = list.toArray(new Object[list.size()]);
		return strs;
	}
	/**
	 * 将String转换成InputStream
	 *
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static byte[] stringTObyte(String in) {
		byte[] bytes = null;
		try {
			bytes = inputStreamTOByte(stringTOInputStream(in));
		} catch (IOException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}
	/**
	 * 将InputStream转换成byte数组
	 *
	 * @param in
	 *            InputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] inputStreamTOByte(InputStream in) throws IOException {

		var outStream = new ByteArrayOutputStream();
		var data = new byte[BUFFER_SIZE];
		var count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return outStream.toByteArray();
	}

	/**
	 * 数组转换为字符串
	 */
	public static String arrayToString(String[] strs) {
		var sb = new StringBuffer();
		if (!ObjectUtils.isEmpty(strs)) {
			var i = 0;
			for (String string : strs) {
				sb.append(string);
				i++;
				if (i < strs.length) {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> mapToList(Map<String, Object> maps) {

		List<T> list = new ArrayList<T>();
		for (var entry : maps.entrySet()) {
			list.add((T) entry.getValue());
		}
		return list;
	}
	/**
	 * 将byte数组转换成InputStream
	 *
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream byteTOInputStream(byte[] in) {

		var is = new ByteArrayInputStream(in);
		return is;
	}

	/**
	 * 将String转换成InputStream
	 *
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream stringTOInputStream(String in) throws Exception {

		var is = new ByteArrayInputStream(in.getBytes("UTF-8"));
		return is;
	}
	/**
	 * 将InputStream转换成String
	 *
	 * @param in
	 *            InputStream
	 * @return String
	 * @throws Exception
	 *
	 */
	public static String inputStreamTOString(InputStream in) {

		var outStream = new ByteArrayOutputStream();
		var data = new byte[BUFFER_SIZE];
		String string = null;
		var count = 0;
		try {
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
		} catch (IOException e) {
			e.printStackTrace();
		}

		data = null;
		try {
			string = new String(outStream.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}
	
	public static <T> List<T> convertList2Beans(List<Map<String, Object>> list, Class<T> clz) {
		return beanConvertList2Beans(list, clz);
	}
	
	public static <T> List<T>  beanConvertList2Beans(List<Map<String, Object>> lists, Class<T> clz){
		if (lists == null || lists.isEmpty()) {
			return null;
		}
		List<T> results = new ArrayList<T>();
		for (var map : lists) {
			T t = convertMap2Bean(map, clz);
			if (t != null) {
				results.add(t);
			}
		}
		return results;
	}
	
	/**
	 * 转换Map<String,Object> 转换成T对应的对象
	 * 
	 * @param clz 需要转换的结果类型
	 */
	@SuppressWarnings("deprecation")
	public static <T> T convertMap2Bean(Map<String, Object> bean, Class<T> clz) {
		if (bean == null) {
			return null;
		}
		
		try {
			T t = clz.getDeclaredConstructor().newInstance();
			var fields = t.getClass().getDeclaredFields();
			fields = getSuperFields(clz, Arrays.asList(fields));// 获取父类的属性列表
			for (var field : fields) {
				var key = field.getName();// 获取属性名
				if(bean.get(key)==null){continue;}
				var value = bean.get(key).toString();
				if (value == null || "null".equals(value)) {continue;}// 如果对应属性的值，bean中不存在，返回继续遍历
				
				var visiable = field.isAccessible();
				field.setAccessible(Boolean.TRUE);
				var object = getValueFromField(field, key, value);
				field.set(t, object);
				field.setAccessible(visiable);
			}
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 通过反射获取值
	 */
	public static Object getValueFromField(Field field, String name, String value) {
		var temp = value;
		Object object = value;
		var type = field.getType().getSimpleName();
		
		if (StringUtils.isEmpty(temp) && ("Short,short,int,Integer,Long,long,Double,double,Float,float".contains(type))) {
			temp = "0";
		}

		if ("short".equalsIgnoreCase(type)) { // Short
			object = Short.valueOf(temp);
		} else if ("char".equals(type) || "Character".equals(type)) { // Character
			if (!StringUtils.isEmpty(temp)){
				object = temp.charAt(0);
			}
		} else if ("int".equals(type) || "Integer".equals(type)) { // Integer
			object = Integer.valueOf(temp);
		} else if ("long".equalsIgnoreCase(type)) { // Long
			object = Long.valueOf(temp);
		} else if ("byte".equalsIgnoreCase(type)) { // Byte
			object = Byte.valueOf(temp);
		} else if ("float".equalsIgnoreCase(type)) { // Float
			object = Float.valueOf(temp);
		} else if ("double".equalsIgnoreCase(type)) { // Double
			object = Double.valueOf(temp);
		} else if ("boolean".equalsIgnoreCase(type)) { // Boolean
			if(temp.equals("1")){//转换1/0
				temp = "true";
			}
			object = Boolean.valueOf(temp);
		} else if ("Date".equals(type)) { // Date
			object = parse(temp);
		} else if ("byte[]".equalsIgnoreCase(type)) { // byte[]
			object = string2Bytes(temp);
		} 
		return object;
	}
	
	/**
	 * 获取所有父类的属性列表
	 */
	public static Field[] getSuperFields(Class<?> subCls, List<Field> subFields) {
		var superClass = subCls.getSuperclass();
		List<Field> tempSubFields = new ArrayList<Field>(subFields);
		if (superClass != null && superClass != Object.class) {
			var superFields = superClass.getDeclaredFields();
			tempSubFields.addAll(Arrays.asList(superFields));
			if (superClass.getSuperclass() != Object.class){
				getSuperFields(superClass, tempSubFields);// 递归调用
			}
		}
		return tempSubFields.toArray(new Field[subFields.size()]);
	}
	
	/**
	 * 通过反射获取值
	 */
	public static Object getValueFromField(Object object, Field field) {
		try {
			var value = field.get(object);
			
			var type = field.getType().getSimpleName();
			if ("short".equals(type)) { // Short
				value = field.getShort(object);
			} else if ("char".equals(type)) { // char
				value = field.getChar(object);
			} else if ("int".equals(type)) { // int
				value = field.getInt(object);
			} else if ("long".equals(type)) { // long
				value = field.getLong(object);
			} else if ("byte".equals(type)) { // byte
				value = field.getByte(object);
			} else if ("float".equals(type)) { // float
				value = field.getFloat(object);
			} else if ("double".equals(type)) { // double
				value = field.getDouble(object);
			} else if ("boolean".equals(type)) { // boolean
				value = field.getBoolean(object);
			} else if ("Date".equals(type)) { // Date
				value = format((Date) value);
			} else if ("byte[]".equalsIgnoreCase(type)) { // byte[]
				if (value != null) {
					value = bytes2String((byte[]) value);
				}
			} else if ("String".equalsIgnoreCase(type)) {
				if (value == null) {
					value = "";
				}
			}
			return value;
		} catch (IllegalAccessException e) {
			return null;
		}
	}
	
	/**
	 * 将Date 对象转换成String
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return date == null ? "" : dateFormat.format(date);
	}
	
	public static Date parse(String date) {
		var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return StringUtils.isEmpty(date) ? null : dateFormat.parse(date);
		} catch (ParseException e) {
		}
		return null;
	}
	/**
	 * convert byte[] to String
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytes2String(byte[] bytes) {
		var builder = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			builder.append(HEXCHAR[(bytes[i] & 0xf0) >>> 4]);
			builder.append(HEXCHAR[bytes[i] & 0x0f]);
		}
		return builder.toString();
	}
	
	/**
	 * convert String to byte[]
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] string2Bytes(String str) {
		var bytes = new byte[str.length() / 2];
		for (var i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
}
