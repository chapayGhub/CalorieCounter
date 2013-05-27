package com.caloriecounter.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DBDateUtils {
	/**
	 * input format: "2012-1-1 12:30:20"
	 * 
	 * if exact time is not needed it can be:
	 * 
	 * "2012-1-1"
	 * 
	 * @param f
	 * @return
	 */
	@SuppressWarnings("all")
	public static Date getDateFromString(String f) {
		Date result = null;
		StringBuffer template = new StringBuffer("yyyy-MM-dd");
		if (f.split(" ").length > 1) {
			template.append(" hh:mm:ss");
		}

		try {
			SimpleDateFormat df = new SimpleDateFormat(template.toString(),
					Locale.US);
			result = df.parse(f);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("all")
	public static String getStringFromDate(Date d) {
		StringBuffer result = new StringBuffer();
		result.append(d.getYear());
		result.append("-");
		result.append(d.getMonth());
		result.append("-");
		result.append(d.getDate());
		return result.toString();
	}

	@SuppressWarnings("all")
	public static String getStringFromDate(Long d) {
		Date s = new Date(d);
		s.setYear(s.getYear() + 1900);
		return getStringFromDate(s);
	}

}
