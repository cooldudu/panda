package com.wms.core.utils.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具类：获取当前日期的开始时间与结束时间
 *
 * @author xb
 * @since 2009-11-6
 */
public class DateTimeUtil {
	/**
	 * 获取days天后的日期
	 *
	 * @param days
	 * @return
	 */
	public static Date getEndDateOfTodayByDays(int days) {
		var now = System.currentTimeMillis();

		return new Date(now + days * 24L * 60 * 60 * 1000);
	}

	/**
	 * 更新时间的计算
	 *
	 * @return
	 */
	public static String datetochange(Date reflshDate) {
		if (reflshDate == null)
			return "";

		var c = Calendar.getInstance();

		c.setTime(reflshDate);

		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE), c.get(Calendar.SECOND));

		Long time = (new Date().getTime()) - (c.getTimeInMillis());

		var ss = 1000;
		var mi = ss * 60;
		var hh = mi * 60;
		var dd = hh * 24;

		var day = time / dd;
		var hour = (time - day * dd) / hh;
		var minute = (time - day * dd - hour * hh) / mi;

		var strDay = "" + day;
		var strHour = "" + hour;
		var strMinute = "" + minute;
		var leavetime = new StringBuffer();
		if (!strDay.equals("0")) {
			leavetime.append(strDay + "天");
		}
		if (!strHour.equals("0")) {
			leavetime.append(strHour + "小时");
		}
		leavetime.append(strMinute + "分钟前");
		return leavetime.toString();
	}

	//获取指定年月的月份天数
	public static int getCurrentMonthDay(String time) throws ParseException {
    	var foramt = new SimpleDateFormat("yyyy-MM");
		var a = Calendar.getInstance();
		a.setTime(foramt.parse(time));
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		var maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	/**
	 * 获取当前登录时间
	 *
	 * @param loginDate
	 * @return
	 */
	public static String dateLoginChange(Date loginDate) {
		if (loginDate == null)
			return "";

		var c = Calendar.getInstance();

		c.setTime(loginDate);

		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE), c.get(Calendar.SECOND));

		Long time = (new Date().getTime()) - (c.getTimeInMillis());

		var ss = 1000;
		var mi = ss * 60;
		var hh = mi * 60;
		var dd = hh * 24;

		var day = time / dd;
		var hour = (time - day * dd) / hh;
		var minute = (time - day * dd - hour * hh) / mi;

		var leavetime = new StringBuffer();
		if (day == 0 && hour == 0 && minute < 10) {
			leavetime.append("刚刚来过");
		} else {
			var strDay = "" + day;
			var strHour = "" + hour;
			var strMinute = "" + minute;

			if (!strDay.equals("0")) {
				leavetime.append(strDay + "天");
			}
			if (!strHour.equals("0")) {
				leavetime.append(strHour + "小时");
			}
			leavetime.append(strMinute + "分钟前");
		}
		return leavetime.toString();
	}

	/**
	 * 获取今天的开始时间
	 *
	 * @return
	 */
	public static Date getBeginDateTimeOfToday() {
		var calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getBeginDateTimeOfToday(int days) {
		var calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	//获取指定时间的前一天
	public static Date getBeforeDayTimeOfDay(Date date,int days) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	//获取指定当前时间的前N天
		public static Date getBeforeDayTimeOfDays(Date date,int days) {
			var calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);
			return calendar.getTime();
		}

	public static Date getBeginDateTimeOfTodayWithMonth(Date day,int months) {
		var calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.add(Calendar.MONTH, months);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取今天的结束时间
	 *
	 * @return
	 */
	public static Date getEndDateTimeOfToday() {
		var calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndDateTimeOfToday(int days) {
		var calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1 + days);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndDateTimeOfTodayWithMonth(Date day,int months) {
		var calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.add(Calendar.MONTH, months);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期的明天
	 *
	 * @return
	 */
	public static Date getTomorrowOfDate(Date date) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getBeforeHourOftoday(Date date,int hour) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到指定日期的15天后日期
	 *
	 * @return
	 */
	public static Date getHalfOfMonth(Date date) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 15);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期一个月之后的日期
	 *
	 * @return
	 */
	public static Date getOneOfMonth(Date date) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 30);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到指定日期一个半月之后的日期
	 *
	 * @return
	 */
	public static Date getOneHalfOfMonth(Date date) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 45);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期两个月之后的日期
	 *
	 * @return
	 */
	public static Date getTowOfMonth(Date date) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 60);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	@SuppressWarnings("deprecation")
	/**
	 * 获得日期的年份
	 * @param date日期
	 * @return
	 */
	public static String getYearNo(Date date) {
		var year = date.getYear();
		var yearNo = (year + "").substring(1);
		return yearNo;
	}

	/**
	 * 获取指定日期的月份，如果不够两位，前面用0占位
	 *
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getMonthNo(Date date) {
		var month = date.getMonth();
		var monthNo = month + 1 + "";
		if (month < 10) {
			monthNo = "0" + monthNo;
		}
		return monthNo;
	}

	/**
	 * 获取指定日期的年份
	 *
	 * @return
	 */
	public static String getYear(Date date) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);

		return String.valueOf(calendar.get(Calendar.YEAR));
	}

	/**
	 * 获取指定日期的月份
	 *
	 * @return
	 */
	public static String getMonthDay(Date date) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);

		return String.valueOf(calendar.get(Calendar.MONTH) + 1);

	}

	/**
	 * 获得指定日期的所属日期
	 */
	public static String getDay(Date date) {
		var calendar = Calendar.getInstance();
		calendar.setTime(date);

		return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * 计算两个整型日期之间的天数
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBetweenDate(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		Long interval = endDate.getTime() - startDate.getTime();
		interval = interval / (24 * 60 * 60 * 1000);
		return interval.intValue();
	}

	/**
	 * 计算两个整型日期之间的天数
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBwByString(String startDate, String endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}
		Long interval = 0l;
		try {
			interval = dstrToDate(endDate).getTime()
					- dstrToDate(startDate).getTime();
			interval = interval / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return interval.intValue();
	}

	/**
	 * 字符串转日期
	 *
	 *            (yyyy-MM-dd)
	 * @return
	 */
	public static Date dstrToDate(String dstr) {
		final var DAFAULT_DATE_FORMAT = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return getDate(dstr, DAFAULT_DATE_FORMAT);
	}
	
	/**
	 * 日期转字符串
	 *
	 *            (yyyy-MM-dd)
	 * @return
	 */
	public static String dateToStr(Date date) {
		final var DAFAULT_DATE_FORMAT = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return getStr(date, DAFAULT_DATE_FORMAT);
	}
	
	public static String getDateStr(Date date) {
		var format=new SimpleDateFormat("yyyyMMdd");
		return getStr(date, format);
	}
	
	public static String getMonthStr(Date date) {
		var format=new SimpleDateFormat("yyyyMM");
		return getStr(date, format);
	}

	private static Date getDate(String str, SimpleDateFormat format) {
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	private static String getStr(Date date, SimpleDateFormat format) {
		String str = null;
		str = format.format(date);
		return str;
	}

	/**
	 * 通过字符串创建一个Date对象
	 *
	 * @param time
	 *            格式如"2010-06-11"
	 * @return date
	 * @throws ParseException
	 */
	public static Date stringToDate(String time) throws ParseException {
		var df = DateFormat.getDateInstance();
		var date = df.parse(time);
		return date;
	}

	/**
	 * 得到当前的日期
	 *
	 * @return当前日期
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * 得到当前的日期格式是(yyyy-MM-DD)
	 *
	 * @return当前日期
	 */
	public static String getCurrentDate() {
		var sdf = new SimpleDateFormat("yyyy-MM-DD");
		return sdf.format(new Date());
	}

	/**
	 * 得到当前的时间格式是（HH:mm:ss）
	 *
	 * @return当前日期
	 */
	public static String getCurrentTime() {
		var sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date());
	}
	/**
	 * 得到当前的时间格式是（yyyy-MM-dd HH:mm:ss）
	 *
	 * @return当前日期
	 */
	public static String getCurrentDateTime() {
		var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 得到当前日期属于第几周
	 *
	 * @return
	 */
	public static String getCurrentWeek() {
		var sdf = new SimpleDateFormat("w");
		return sdf.format(new Date());
	}

	/**
	 * 得到当前日期所属的年份
	 *
	 * @return
	 */
	public static String getCurrentYear() {
		var sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}

	/**
	 * 得到当前日期所属的月份
	 *
	 * @return
	 */
	public static String getCurrentMonth() {
		var sdf = new SimpleDateFormat("MM");
		return sdf.format(new Date());
	}

	/**
	 * 得到当前日期所属的日期
	 *
	 * @return
	 */
	public static String getCurrentDay() {
		var sdf = new SimpleDateFormat("dd");
		return sdf.format(new Date());
	}

	public static String getWeekOfDate(Date date) {
		String[] weekOfDays = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
		var calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		var w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekOfDays[w];
	}

	/**
	 * 获取到制定日期的周一
	 *
	 * @param date
	 * @return
	 */
	public static Date getMondayOfDate(Date date) {
		var calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, -1);
		}
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	/**
	 * 获取制定日期7日后的日期
	 *
	 * @param date
	 * @return
	 */
	public static Date getSevenDaysAfter(Date date) {
		var calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.add(Calendar.DATE, 7);
		return calendar.getTime();
	}
	/**
	 * 获取制定日期上周周日
	 * @return
	 */
	public static Date getLastWeekSunday(Date date){

	    var calendar=Calendar.getInstance(Locale.CHINA);

	    calendar.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天

	    calendar.add(Calendar.WEEK_OF_MONTH,-1);//周数减一，即上周

	    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//日子设为星期天

	    return calendar.getTime();

	}

	/**
	 * 获取制定日期上周周一
	 * @return
	 */
	public static Date getLastWeek1Sunday(Date date){

	    var calendar=Calendar.getInstance(Locale.CHINA);

	    calendar.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天

	    calendar.add(Calendar.WEEK_OF_MONTH,-1);//周数减一，即上周

	    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//日子设为星期天

	    return calendar.getTime();

	}


	public static String formatCountToTime(Integer count){
		var resultStr = "";
		Integer hour = null;
		Integer hourTemp = null;
		var hourStr = "00:";

		Integer minute = null;
		Integer minuteTemp = null;
		String minuteStr = null;

		if(count>=3600){
			hour = count/3600;
			hourTemp = count%3600;
			if(hour<10){
				hourStr="0"+hour+":";
			}else{
				hourStr=hour+":";
			}
			if(null!=hourTemp){
				if(hourTemp>=60){
					minute = hourTemp/60;
					minuteTemp = hourTemp%60;
					if(minute<10){
						minuteStr = "0"+minute+":";
					}else{
						minuteStr = minute+":";
					}
					if(minuteTemp<10){
						resultStr = hourStr+minuteStr+"0"+minuteTemp;
					}else{
						resultStr = hourStr+minuteStr+minuteTemp;
					}
				}else{
					if(hourTemp<10){
						resultStr = hourStr+"00:0"+hourTemp;
					}else{
						resultStr = hourStr+"00:"+hourTemp;
					}
				}
			}else{
				resultStr = hourStr+"00:00";
			}
		}

		if(count>=60&&count<3600){
			minute = count/60;
			minuteTemp = count%60;
			if(minute<10){
				minuteStr = "0"+minute+":";
			}else{
				minuteStr = minute+":";
			}
			if(minuteTemp<10){
				resultStr = hourStr+minuteStr+"0"+minuteTemp;
			}else{
				resultStr = hourStr+minuteStr+minuteTemp;
			}
		}else{
			minuteStr = "00:";
		}
		if(count<60){
			if(count<10){
				resultStr = hourStr+minuteStr+"0"+count;
			}else{
				resultStr = hourStr+minuteStr+""+count;
			}
		}

		return resultStr;
	}
	
	/**
	 * 比较两个日期的大小
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 * @throws ParseException
	 */
	public static String compareDateStr(String dateStr1,String dateStr2) throws ParseException {
			dateStr1=dateStr1.replaceAll("\\.", "-");
			dateStr2=dateStr2.replaceAll("\\.", "-");
		 	var df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         	var dt1 = df.parse(dateStr1);
            var dt2 = df.parse(dateStr2);
	        if (dt2.getTime() >= dt1.getTime()) {
	            return "1";
	        } else {
	            return "2";
	        }
	}
	
	public static void main(String[] args) {
		var str=getMonthStr(new Date());
		System.out.println(str);
	}

}
