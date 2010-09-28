package com.sw.cms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 常用日期处理函数
 * @author liyt
 *
 */
public final class DateComFunc {
	
	/**
	 * 按默认规则格式化日期，格式为：yyyy-MM-dd
	 * @param dDate  要格式化的日期
	 * @return
	 */
	public static String formatDate(Date dDate) {
		return formatDate(dDate,"yyyy-MM-dd");
	}	

	
	/**
	 * 按设置规则格式化日期
	 * @param dDate  要格式化的日期
	 * @param sFormat 格式化规则
	 * @return
	 */
	public static String formatDate(Date dDate, String sFormat) {
		String tmp = "";
		try {
			if (dDate != null) {
				SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
				String dateString = formatter.format(dDate);
				tmp = dateString;
			} else
				tmp = "";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tmp;
	}
	
	
	/**
	 * 按默认规则将字符型日期转化为日期类型,格式为：yyyy-MM-dd
	 * @param s   输入的字符型日期
	 * @return
	 */
	public static Date strToDate(String s) {
		return strToDate(s,"yyyy-MM-dd");
	}

	
	/**
	 * 将字符型日期转化为日期类型
	 * @param s   输入的字符型日期
	 * @param pattern  格式化规则
	 * @return
	 */
	public static Date strToDate(String s, String pattern) {

		SimpleDateFormat formatter = new SimpleDateFormat(pattern,
				java.util.Locale.US);
		Date date1 = null;
		try {
			Date theDate = formatter.parse(s);
			Date date = theDate;
			return date;
		} catch (Exception ex) {
			System.out.println("String " + s + " is error");
		}
		return date1;
	}

	/**
	 * turn the java.sql.Date to String with format "YYYY/MM/DD"
	 * 
	 */
	public static String convertSqlDateToString(java.sql.Date sd) {
		String sm;
		try {
			sm = sd.toString().substring(0, 4) + "/"
					+ sd.toString().substring(5, 7) + "/"
					+ sd.toString().substring(8, 10);
		} catch (Exception e) {
			sm = "";
		}
		return sm;
	}

	/**
	 * judge if the year is leap year
	 */

	public static boolean isLeap(int year) {

		boolean div4 = year % 4 == 0;
		boolean div100 = year % 100 == 0;
		boolean div400 = year % 400 == 0;
		return div4 && (!div100 || div400);
	}

	/**
	 * get the week number for date
	 */
	public static int getWeek(Date vDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(vDate);
		int week = cal.get(3);
		return week;

	}

	/**
	 * get the week day for date
	 */
	public static int getWeekDay(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(7);
	}

	/**
	 * get the TimeZOne of server
	 */
	public static String getServerTimeZone() {

		Calendar cal = Calendar.getInstance();
		TimeZone tz = cal.getTimeZone();
		return tz.getID();

	}

	/**
	 * get the date with normal format
	 * 
	 * @atuthor
	 * @return: Date type data
	 */
	public static Date getDate(int year, int month, int date) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		return new Date(cal.getTime().getTime());
	}

	/**
	 * get the diffrence between two date
	 */

	public static long dateDiff(Date date1, Date date2) {
		long date1ms = date1.getTime();
		long date2ms = date2.getTime();
		return date2ms - date1ms;
	}

	/**
	 * get the year for now
	 */
	public static int getYear() {
		Calendar Cal = Calendar.getInstance();
		return Cal.get(Calendar.YEAR);
	}

	/**
	 * get the year for input date
	 */
	public static int getYear(Date inputDate) {
		Calendar Cal = Calendar.getInstance();
		Cal.setTime(inputDate);

		return Cal.get(Calendar.YEAR);
	}

	/**
	 * get the month for now
	 */
	public static int getMonth() {
		Calendar Cal = Calendar.getInstance();
		return (Cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * get the month for input date
	 */
	public static int getMonth(Date inputDate) {
		Calendar Cal = Calendar.getInstance();
		Cal.setTime(inputDate);
		return (Cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * get the day for now
	 */
	public static int getDay() {
		Calendar Cal = Calendar.getInstance();
		return Cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * get the day for input Date
	 */
	public static int getDay(Date inputDate) {
		Calendar Cal = Calendar.getInstance();
		Cal.setTime(inputDate);
		return Cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * turn today to long format with yymmdd
	 */
	public static long to6DigNumber() {
		Calendar Cal = Calendar.getInstance();
		long result = 0L;
		result = getYear() % 100 * 10000L + getMonth() * 100L + getDay();
		return result;

	}

	/**
	 * turn tinput date to long format with yymmdd
	 */
	public static long to6DigNumber(Date inputDate) {
		Calendar Cal = Calendar.getInstance();
		Cal.setTime(inputDate);
		long result = 0L;
		result = getYear() % 100 * 10000L + getMonth() * 100L + getDay();
		return result;

	}

	// ====================Adjust a
	// date=============================================
	/**
	 * add many hour to a date varible
	 */
	public static Date addHour(Date dDate, long iNbHour) {

		long datems = dDate.getTime();
		long hourMs = iNbHour * (long) 60 * (long) 60 * (long) 1000;
		long newDateMs = datems + hourMs;
		Date result = new Date(newDateMs);
		return result;

	}

	/**
	 * add many minute to a date varible
	 */
	public static Date addMinute(Date dDate, long iNbMinute) {

		long datems = dDate.getTime();
		long minuteMs = iNbMinute * (long) 60 * (long) 1000;
		long newDateMs = datems + minuteMs;
		Date result = new Date(newDateMs);
		return result;

	}

	/**
	 * add many month to a date varible
	 */
	public static Date addMonth(Date dDate, int iNbMonth) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dDate);
		int month = cal.get(2);
		month += iNbMonth;
		int year = month / 12;
		month %= 12;
		cal.set(2, month);
		if (year != 0) {
			int oldYear = cal.get(1);
			cal.set(1, year + oldYear);
		}
		return cal.getTime();
	}

	/**
	 * add many day to a date varible
	 */
	public static Date addDay(Date dDate, long iNbDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dDate);
		cal.add(5, (int) iNbDay);
		Date result = cal.getTime();
		return result;

	}

	/**
	 * add many second to a date varible
	 */
	public static Date addSecond(Date dDate, long iNbSecond) {

		long datems = dDate.getTime();
		long secondms = iNbSecond * (long) 1000;
		long newDateMs = datems + secondms;
		Date result = new Date(newDateMs);
		return result;

	}

	/**
	 * add many year to a date varible
	 */
	public static Date addYear(Date dDate, int iNbYear) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dDate);
		int oldYear = cal.get(1);
		cal.set(1, iNbYear + oldYear);
		return cal.getTime();

	}

	/**
	 * Converts java.util.Date to java.sql.Date.
	 * 
	 * @param date
	 *            java.util.Date that wanted be converted.
	 * @return java.sql.Date that has been converted.
	 */
	public static java.sql.Date converlUtilDate(Date date) {
		java.sql.Date sqlDate = null;
		sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	/**
	 * Converts java.sql.Date to java.util.Date.
	 * 
	 * @param date
	 *            java.sql.Date that wanted be converted.
	 * @return java.util.Date that has been converted.
	 */
	public static Date convertSqlDate(java.sql.Date date) {
		Date utilDate = null;
		utilDate = new Date(date.getTime());
		return utilDate;
	}

	public static String getDateFormat(Date dt, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
		String ret = sdf.format(cal.getTime());
		return ret;
	}

	public static String getToday() {
		String strReturn = "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		// int hh = cal.get(Calendar.HOUR);
		// int time = cal.get(Calendar.MINUTE);
		String m = "1";
		String d = "1";
		if (month < 10) {
			m = "0" + String.valueOf(month);
		} else {
			m = String.valueOf(month);
		}
		if (day < 10) {
			d = "0" + String.valueOf(day);
		} else {
			d = String.valueOf(day);
		}
		strReturn = String.valueOf(year) + "-" + m + "-" + d;

		return strReturn;
	}

	public static String getYestoday() {
		String strReturn = "";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		String m = "1";
		String d = "1";
		if (month < 10) {
			m = "0" + String.valueOf(month);
		} else {
			m = String.valueOf(month);
		}
		if (day < 10) {
			d = "0" + String.valueOf(day);
		} else {
			d = String.valueOf(day);
		}
		strReturn = String.valueOf(year) + "-" + m + "-" + d;

		return strReturn;
	}

	public static String getCurDay() {
		String strReturn = "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		// int hh = cal.get(Calendar.HOUR);
		// int time = cal.get(Calendar.MINUTE);
		String m = "1";
		String d = "1";
		if (month < 10) {
			m = "0" + String.valueOf(month);
		} else {
			m = String.valueOf(month);
		}
		if (day < 10) {
			d = "0" + String.valueOf(day);
		} else {
			d = String.valueOf(day);
		}
		strReturn = String.valueOf(year) + m + d;

		return strReturn;
	}

	public static boolean isInCurWeek(Date cdate) { // 判断一个日期是否在本周
		boolean is = false;

		Calendar calendar = Calendar.getInstance();
		int curDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		Calendar startCl = Calendar.getInstance(); // 本周起始
		Calendar endCl = Calendar.getInstance(); // 本周结束
		Calendar cl = Calendar.getInstance(); // 待比较日期

		calendar.add(5, 7 - curDayOfWeek);
		endCl.setTime(calendar.getTime());

		calendar.add(5, -6);
		startCl.setTime(calendar.getTime());

		cl.setTime(cdate);

		if (cl.getTimeInMillis() > startCl.getTimeInMillis()
				&& cl.getTimeInMillis() <= endCl.getTimeInMillis()) {
			is = true;
		}

		return is;
	}

	/**
	 * 返回指定日期，所在月的第一天
	 * @param strDate
	 * @return
	 */
	public static String getMonthFirstDay(String strDate) {
		String returnDate = "";
		if(strDate.length() >= 10){
			returnDate = strDate.substring(0,7) + "-01";
		}
		return returnDate;
	}
	
	
	/**
	 * 返回当前时间
	 * @return
	 */
	public static String getCurTime(){
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 两年日期间年差，不满一年计0
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getYearSub(String startDate,String endDate){
		try{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = df.parse(startDate);
			Date date2 = df.parse(endDate);
			long millDate = date2.getTime() - date1.getTime();
			long temD = millDate/(1000*60*60*24);
			return new Integer(temD/365+"").intValue();
		}catch(Exception e){
			return 0;
		}
	}

	public static void main(String[] args) {
		System.out.println(DateComFunc.getYearSub("2008-11-08","2010-03-03"));
	}
}