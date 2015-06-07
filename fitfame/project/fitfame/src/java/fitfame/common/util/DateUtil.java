﻿package fitfame.common.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class DateUtil {
	private static final SimpleDateFormat ALL_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"HH:mm:ss");
	
	public static final String DateFormat_yyyyMMdd = "yyyyMMdd";
	public static final String DATEFORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATEFORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATEFORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static String getTimeFlag() {
		return String.valueOf(new Date().getTime());
	}

	public static String format(Date date, String format) {
		if (date == null)
			return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatDate(Date date) {
		if (date == null)
			return null;
		try {
			return DATE_FORMAT.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatTime(Date date) {
		if (date == null)
			return null;
		try {
			return TIME_FORMAT.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatAll(Date date) {
		if (date == null)
			return null;
		try {
			return ALL_FORMAT.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parse(String str, String format) {
		if (StringUtils.isEmpty(str))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date parseAll(String str){
		return parse(str,DATEFORMAT_YYYY_MM_DD_HH_MM_SS);
	}
	/**
	 * 获得当前日期，时间为0
	 * 
	 * @return
	 */
	public static Date getNowDate() {
		return getDate(new Date());
	}

	/**
	 * 获得当前日期，时间为0
	 * 
	 * @return
	 */
	public static Date getDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得前几天的日期
	 * 
	 * @param dayNumber
	 * @return
	 */
	public static Date getTargetDate(Date date, int dayNumber) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, dayNumber);
		return calendar.getTime();
	}

	/**
	 * 获得后几天的日期
	 * 
	 * @param dayNumber
	 * @return
	 */
	public static Date getAfterDate(int dayNumber) {
		return getTargetDate(new Date(), dayNumber);
	}

	/**
	 * 获得后几天的日期
	 * 
	 * @param date
	 * @param dayNumber
	 * @return
	 */
	public static Date getAfterDate(Date date, int dayNumber) {
		return getTargetDate(date, dayNumber);
	}

	/**
	 * 获得前几天的日期
	 * 
	 * @param dayNumber
	 * @return
	 */
	public static Date getBeforeDate(int dayNumber) {
		return getTargetDate(new Date(), -dayNumber);
	}

	/**
	 * 获得前几天的日期
	 * 
	 * @param date
	 * @param dayNumber
	 * @return
	 */
	public static Date getBeforeDate(Date date, int dayNumber) {
		return getTargetDate(date, -dayNumber);
	}

	/**
	 * 增加或减少时间
	 * 
	 * @param date
	 * @param field
	 * @param value
	 * @return
	 */
	public static Date add(Date date, int field, int value) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, value);
		return c.getTime();
	}

	/**
	 * 获得几分钟以后的时间，接受负数
	 * 
	 * @param date
	 * @param min
	 * @return
	 */
	public static Date getAddMin(Date date, int min) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, min);
		return c.getTime();
	}

	/**
	 * 获得几秒钟以后的时间，接收负数
	 * 
	 * @param match_oldhqdatetime_cache
	 * @param i
	 * @return
	 */
	public static Date getAddSecond(Date date, int second) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, second);
		return c.getTime();
	}

	/**
	 * 获得今天收盘的时间
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getClosingTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 15);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获得今天收盘后的时间
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getClosedTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 15);
		c.set(Calendar.MINUTE, 30);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获得从开盘时间到指定时间的时间差，分钟；日期相同
	 * 
	 * @param nowDateTime
	 * @return
	 */
	public static long getOpenTimeToNow(Date nowDateTime) {
		// 早开盘时间
		Calendar c1 = Calendar.getInstance();
		c1.setTime(nowDateTime);
		c1.set(Calendar.HOUR_OF_DAY, 9);
		c1.set(Calendar.MINUTE, 30);
		c1.set(Calendar.SECOND, 0);

		// 早收盘时间
		Calendar c2 = Calendar.getInstance();
		c2.setTime(nowDateTime);
		c2.set(Calendar.HOUR_OF_DAY, 11);
		c2.set(Calendar.MINUTE, 30);
		c2.set(Calendar.SECOND, 0);

		// 午开盘时间
		Calendar c3 = Calendar.getInstance();
		c3.setTime(nowDateTime);
		c3.set(Calendar.HOUR_OF_DAY, 13);
		c3.set(Calendar.MINUTE, 0);
		c3.set(Calendar.SECOND, 0);

		// 午收盘时间
		Calendar c4 = Calendar.getInstance();
		c4.setTime(nowDateTime);
		c4.set(Calendar.HOUR_OF_DAY, 15);
		c4.set(Calendar.MINUTE, 0);
		c4.set(Calendar.SECOND, 0);

		// 开盘时间(分钟）

		long min = 0;
		// 收盘后
		if (nowDateTime.after(c4.getTime())) {
			min = 240;
		} else if (nowDateTime.after(c3.getTime())) {
			min = 120 + (nowDateTime.getTime() - c3.getTime().getTime())
					/ (1000 * 60);
		} else if (nowDateTime.after(c2.getTime())) {
			min = 120;
		} else if (nowDateTime.after(c1.getTime())) {
			min = (nowDateTime.getTime() - c1.getTime().getTime())
					/ (1000 * 60);
		}
		return min;
	}

	/**
	 * 拼接日期和时间到新的Date对象
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	public static Date DateAndTime(Date date, Date time) {
		Calendar d = Calendar.getInstance();
		d.setTime(date);
		Calendar t = Calendar.getInstance();
		t.setTime(time);

		t.set(Calendar.YEAR, d.get(Calendar.YEAR));
		t.set(Calendar.MONTH, d.get(Calendar.MONTH));
		t.set(Calendar.DATE, d.get(Calendar.DATE));
		return t.getTime();
	}

	/**
	 * 取出日期中的某个字段
	 * 
	 * @param date
	 *            日期
	 * @param field
	 *            字段
	 * @return
	 */
	public static int extract(Date date, int field) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(field);
	}

	public static final String START_TIME = "START_TIME";
	public static final String END_TIME = "END_TIME";

	/**
	 * 解析时间区间字符串<br/> 格式：8:00-9:00,15:00-16:30
	 * 
	 * @param timeRegion
	 * @return
	 */
	public static List<Map<String, Date>> parseTimeRegion(String timeRegion)
			throws ParseException {
		List<Map<String, Date>> result = new ArrayList<Map<String, Date>>();
		String[] tl = timeRegion.split(",");
		for (String t : tl) {
			String[] sel = t.split("-");
			Map<String, Date> resultM = new HashMap<String, Date>();
			if (sel != null && sel.length == 2) {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				Date dStart = sdf.parse(sel[0]);
				Date dEnd = sdf.parse(sel[1]);
				resultM.put(START_TIME, dStart);
				resultM.put(END_TIME, dEnd);
			}
			result.add(resultM);
		}
		return result;
	}

	/**
	 * 判断时间是否在所选择的时间区间内，只判断时间,[]区间
	 * 
	 * @param timeRegion
	 * @return
	 */
	public static boolean isInTimeRegion(List<Map<String, Date>> timeRegion,
			Date time) {
		boolean result = false;
		Date nowdate = new Date();
		for (Map<String, Date> m : timeRegion) {
			//如果time不在开始时间与结束时间之间
			if ((DateAndTime(nowdate, m.get(START_TIME)).getTime() <= DateAndTime(
					nowdate, time).getTime())
					&& (DateAndTime(nowdate, time).getTime() <= DateAndTime(
							nowdate, m.get(END_TIME)).getTime())) {
				result=true;
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 三个级别的cache
	 */
	public static Date SuperHighLevelDate()
	{
		Date date = new Date();
		date.setTime(date.getTime() + 24 * 60 * 60 * 1000);
		return date;
	}
	
	public static Date HighLevelDate()
	{
		Date date = new Date();
		date.setTime(date.getTime() + 60 * 60 * 1000);
		return date;
	}
	
	public static Date MiddleLevelDate()
	{
		Date date = new Date();
		date.setTime(date.getTime() + 20 * 60 * 1000);
		return date;
	}
	
	public static Date LowLevelDate()
	{
		Date date = new Date();
		date.setTime(date.getTime() + 60 * 1000);
		return date;
	}
	
	/**
	 * 当前时间
	 * @param args
	 */
	public static long CurrentTime()
	{
		Date date = new Date();
		return date.getTime();
	}
	
	
	public static void main(String[] args) {
		String ym = DateUtil.formatDate(new Date());
		System.out.println(ym.substring(0, 4));
		System.out.println(ym.substring(5, 7));
		System.out.println(ym.substring(8, 10));
		
		System.out.println(DateUtil.formatDate(new Date()));
	}
}
