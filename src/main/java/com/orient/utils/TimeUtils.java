package com.orient.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public static String getNowTimeFormatted()
	{
		LocalDateTime ldt = LocalDateTime.now();
		return dtf.format(ldt);
	}
}
