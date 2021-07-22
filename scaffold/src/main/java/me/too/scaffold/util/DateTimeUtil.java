package me.too.scaffold.util;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
//import org.apache.commons.lang3.time.DateUtils;

public class DateTimeUtil {
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String YMD = "yyyy-MM-dd";
	public static final String MD = "MM-dd";
	public static final String HMS = "HH:mm:ss";
	public static final String HM = "HH:mm";

	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}
	
	public static String format(long timeInMillis, String dateFormat) {
		return DateFormatUtils.format(timeInMillis, dateFormat);
	}
	
	public static String format(Date date, String dateFormat) {
		return DateFormatUtils.format(date, dateFormat);
	}
	
	public static String format(Timestamp timestamp, String dateFormat) {
		return DateFormatUtils.format(timestamp, dateFormat);
	}
	
	public static Timestamp getTimestamp(long timeInMillis) {
		return new Timestamp(timeInMillis);
	}
	
	public static Timestamp getTimestampCurrent() {
		return new Timestamp(getCurrentTimeInLong());
	}
}
