package cn.qixqi.vote.util;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeHelper {
	private static Logger logger = LogManager.getLogger(TimeHelper.class.getName());
	private static long nd = 1000 * 24 * 60 * 60;	// 一天毫秒数
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static long getIntervalDay(Date before, Date after) {
		long interval = after.getTime() - before.getTime();
		long day = interval / nd;
		return day;
	}
	
	public static boolean isSameDay(Date before, Date after) {
		String beforeStr = sdf.format(before);
		String afterStr = sdf.format(after);
		return beforeStr.equals(afterStr);
	}
}
