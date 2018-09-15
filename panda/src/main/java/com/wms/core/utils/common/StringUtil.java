package com.wms.core.utils.common;

import org.springframework.util.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 *
 * @author xb
 *
 */
public class StringUtil {
	/**
	 * 帮助输入框中所输入的字符串首字母
	 *
	 * @param query
	 * @return
	 */
	public static String getFirstCharForQueryString(String query)
			throws Exception {
		var firstChar = "";
		if (query.length() >= 1) {
			firstChar = query.substring(0, 1);
			return firstChar;
		} else {
			throw new Exception("输入条件必须至少有一个字符");
		}
	}

	/**
	 * 生成一个含有数字和字母的随机数
	 *
	 * @param show
	 *            随机数位数
	 * @return
	 */
	public static String getRandomNumber(int show) {
		Random random;
		BufferedImage image;
		Font font;
		var distance = 15;
		var str = "Ab1Cd7EfG5hJk8Lm6NPqRSTuVwXYz0123456789";
		random = new Random();
		image = new BufferedImage(70, 25, BufferedImage.TYPE_3BYTE_BGR);
		font = new Font("Arial", Font.PLAIN, 20);
		var d = image.getGraphics();
		d.setColor(Color.WHITE);
		d.fillRect(0, 0, image.getWidth(), image.getHeight());
		d.setColor(new Color(random.nextInt(100) + 100,
				random.nextInt(100) + 100, random.nextInt(100) + 100));
		for (var i = 0; i < 10; i++) {
			d.drawLine(random.nextInt(image.getWidth()),
					random.nextInt(image.getHeight()),
					random.nextInt(image.getWidth()),
					random.nextInt(image.getHeight()));
		}
		d.setColor(Color.BLACK);
		d.setFont(font);
		var checkCode = "";
		char tmp = 0;
		var x = -distance;
		for (var i = 0; i < show; i++) {
			tmp = str.charAt(random.nextInt(str.length() - 1));
			checkCode = checkCode + tmp;
			x = x + distance;
			d.setColor(new Color(random.nextInt(100) + 50,
					random.nextInt(100) + 50, random.nextInt(100) + 50));
			d.drawString(tmp + "", x,
					random.nextInt(image.getHeight() - (font.getSize()))
							+ (font.getSize()));
		}
		return checkCode;
	}

	/**
	 * 判断两个以逗号隔开的字符串中的内容是否相同
	 *
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean judgeSetString(String source, String target) {
		if (StringUtils.hasText(source) && StringUtils.hasText(target)) {
			if (source.indexOf(",") != -1 && target.indexOf(",") != -1) {
				Set<Long> sourceSet = new HashSet<Long>();
				Set<Long> targetSet = new HashSet<Long>();
				inSet(sourceSet, source);
				inSet(targetSet, target);
				if (sourceSet.hashCode()==target.hashCode()) {
					return true;
				} else {
					return false;
				}
			} else if (source.indexOf(",") == -1 && target.indexOf(",") == -1) {
				if (source.equals(target)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private static void inSet(Set<Long> set, String str) {
		var strarr = str.split(",");
		for (var a : strarr) {
			set.add(Long.parseLong(a));
		}
	}

	/**
	 * 根据spring的类名获取容器对象名称
	 *
	 * @param simpleClassName
	 * @return
	 */
	public static String getSpringBeanName(String simpleClassName) {
		if (StringUtils.hasText(simpleClassName)) {
			return simpleClassName.substring(0, 1).toLowerCase()
					+ simpleClassName.substring(1, simpleClassName.length());
		}
		return simpleClassName;
	}
	
	public static List<String> splitStr(String str,String regex){
		List<String> list=new ArrayList<>();
		while(true){
			list.add(str.substring(0, str.indexOf(regex)));
			str=str.substring(str.indexOf(regex)+1);
			if(str.length()==0) {
				list.add(str);
				break;
			}
		}
		return list;
	}

	/**
	 * 替换字符串的转义字符
	 *
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		var dest = "";
		if (str != null) {
			var p = Pattern.compile("\\s*|\t|\r|\n");
			var m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 替换字符串的转义字符
	 *
	 * @param str
	 * @return
	 */
	public static String replaceString(String str) {
		var dest = "";
		if (str != null) {
			var p = Pattern.compile("\\t|\r|\n");
			var m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static String queryGetMethodString(String field) {
		var result = new StringBuffer();
		if (!ObjectUtils.isEmpty(field) && field.length() > 1) {
			result.append("get");
			result.append(field.substring(0, 1).toUpperCase());
			result.append(field.substring(1, field.length()));
		}
		return result.toString();
	}

	public static String querySetMethodString(String field) {
		var result = new StringBuffer();
		if (!ObjectUtils.isEmpty(field) && field.length() > 1) {
			result.append("set");
			result.append(field.substring(0, 1).toUpperCase());
			result.append(field.substring(1, field.length()));
		}
		return result.toString();
	}

	/**
	 * 对数据进行处理保留小数点后两位
	 * @param num1
	 * @param num2
	 * @return
	 */
	public static String getnumRate(int num1, int num2,String num) {
		String result = null ;
		// 创建一个数值格式化对象
		var numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);// 设置精确到小数点后2位
		if("1".equals(num)){//1-
			if(num2 >0){
				result = numberFormat.format((1-(float)num1 / (float)num2)* 100)+"%";
			}
			else if(num2==0){
				result = "100%";
			}
			else{
				result = "0.00%";
			}
		}else {
			if(num2 >0){
				result = numberFormat.format((float)num1 /(float)num2* 100)+"%";
			}else{
				result = "0.00%";
			}
		}

		return result;
	}

	/**
	 * Object对象转为String对象
	 * @param obj
	 * @return
	 */
	public static String object2String(Object obj){
		return obj==null?"":obj.toString();
	}
	/**
	 * 处理特殊字符
	 * @param src
	 * @return
	 */
	public static String encoding(String src) {
		if (src == null)
		return "";
		var result = new StringBuilder();
		if (StringUtils.hasText(src)) {
		src = src.trim();
		for (var pos = 0; pos < src.length(); pos++) {
		switch (src.charAt(pos)) {
		case '\"':
		result.append("&quot;");
		break;
		case '<':
		result.append("&lt;");
		break;
		case '>':
		result.append("&gt;");
		break;
		case '\'':
		result.append("&apos;");
		break;
		/*case '&':
		result.append("&amp;");
		break;*/
		case '%':
		result.append("&pc;");
		break;
		case '_':
		result.append("&ul;");
		break;
		case '#':
		result.append("&shap;");
		break;
		case '?':
		result.append("&ques;");
		break;
		default:
		result.append(src.charAt(pos));
		break;
		}
		}
		}
		return result.toString();
		}
}