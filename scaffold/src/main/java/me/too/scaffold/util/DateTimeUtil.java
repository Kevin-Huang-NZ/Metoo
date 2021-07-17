package me.too.scaffold.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");


	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	public static String getCurrentTimeInString() {
		return String.valueOf(getCurrentTimeInLong());
	}
	
	public static String formatTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	public static String getTimeYMDHms(long timeInMillis) {
		return formatTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}
	
	public static String getTimeYMD(long timeInMillis) {
		return formatTime(timeInMillis, DATE_FORMAT_DATE);
	}
	
	public static String getCurrentTimeYMDHms() {
		return formatTime(getCurrentTimeInLong(), DEFAULT_DATE_FORMAT);
	}
	public static String getCurrentTimeYMD() {
		return formatTime(getCurrentTimeInLong(), DATE_FORMAT_DATE);
	}
}
