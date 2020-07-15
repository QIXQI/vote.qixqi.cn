package cn.qixqi.vote.util;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileHelper {
	
	private static Logger logger = LogManager.getLogger(FileHelper.class.getName());
	
	public static boolean fileMove(String oldPath, String newPath) {
		boolean result = false;
		try{
			File file = new File(oldPath);
			File newFile = new File(newPath);
			if (! file.exists()) {
				logger.error(oldPath + "文件不存在");
			} else {
				File uploadDir = new File(newPath.substring(0, newPath.lastIndexOf(File.separator)));
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				result = file.renameTo(newFile);
			}
		} catch(Exception e) {
			result = false;
			logger.error(e.getMessage());
		}
		return result;
	}
}
