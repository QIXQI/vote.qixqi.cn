package cn.qixqi.vote.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.qixqi.vote.factory.OptionDaoFactory;
import cn.qixqi.vote.factory.NormbalOptionDaoFactory;
import cn.qixqi.vote.factory.ImgOptionDaoFactory;
import cn.qixqi.vote.factory.AudioOptionDaoFactory;
import cn.qixqi.vote.factory.VideoOptionDaoFactory;
import cn.qixqi.vote.factory.OptionType;

public class OptionDaoFactoryHelper {
	
	private static Logger logger = LogManager.getLogger(OptionDaoFactoryHelper.class.getName());
	
	public static OptionDaoFactory getFactory(int optionType) {
		OptionDaoFactory optionDao = null;
		switch (optionType) {
			case OptionType.NORMBAL_OPTION:
				optionDao = new NormbalOptionDaoFactory();
				break;
			case OptionType.IMG_OPTION:
				optionDao = new ImgOptionDaoFactory();
				break;
			case OptionType.AUDIO_OPTION:
				optionDao = new AudioOptionDaoFactory();
				break;
			case OptionType.VIDEO_OPTION:
				optionDao = new VideoOptionDaoFactory();
				break;
			default:
				// 返回 null
				logger.error("选项类型: " + optionType + "不存在");
				break;
		}
		return optionDao;
	}
	
	
}
