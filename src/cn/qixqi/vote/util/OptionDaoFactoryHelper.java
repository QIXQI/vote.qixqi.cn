package cn.qixqi.vote.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.qixqi.vote.factory.OptionDaoFactory;
import cn.qixqi.vote.factory.NormbalOptionDaoFactory;
import cn.qixqi.vote.factory.ImgOptionDaoFactory;
import cn.qixqi.vote.entity.AudioOption;
import cn.qixqi.vote.entity.ImgOption;
import cn.qixqi.vote.entity.NormbalOption;
import cn.qixqi.vote.entity.Option;
import cn.qixqi.vote.entity.VideoOption;
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
	
	@SuppressWarnings("unchecked")
	public static List<Option> getOptionList(String options, int optionType, String uploadPath){
		List<Option> optionList = new ArrayList<Option>();
		switch (optionType) {
			case OptionType.NORMBAL_OPTION:
				optionList = (List<Option>)(List<?>) JSON.parseArray(options, NormbalOption.class);
				break;
			case OptionType.IMG_OPTION:
				List<ImgOption> imgOptionList = JSON.parseArray(options, ImgOption.class);
				for (ImgOption imgOption : imgOptionList) {
					if (imgOption == null) {
						continue;
					}
					String oldUrl = imgOption.getImgUrl();
					String imgUrl = oldUrl.replaceFirst("temp", "images");
					imgOption.setImgUrl(imgUrl);;
					
					// 文件操作
					if (!FileHelper.fileMove(uploadPath + oldUrl.substring(5), uploadPath + imgUrl.substring(5))) {
						logger.error(uploadPath + oldUrl.substring(5) + " 文件移动失败");
					}
				}
				optionList = (List<Option>)(List<?>) imgOptionList;
				break;
			case OptionType.AUDIO_OPTION:
				List<AudioOption> audioOptionList = JSON.parseArray(options, AudioOption.class);
				for (AudioOption audioOption : audioOptionList) {
					if (audioOption == null) {
						continue;
					}
					String oldUrl = audioOption.getAudioUrl();
					String audioUrl = oldUrl.replaceFirst("temp", "audios");
					audioOption.setAudioUrl(audioUrl);
					
					// 文件操作
					if (!FileHelper.fileMove(uploadPath + oldUrl.substring(5), uploadPath + audioUrl.substring(5))) {
						logger.error(uploadPath + oldUrl.substring(5) + " 文件移动失败");
					}
				}
				optionList = (List<Option>)(List<?>) audioOptionList;
				break;
			case OptionType.VIDEO_OPTION:
				List<VideoOption> videoOptionList = JSON.parseArray(options, VideoOption.class);
				for (VideoOption videoOption : videoOptionList) {
					if (videoOption == null) {
						continue;
					}
					String oldUrl = videoOption.getVideoUrl();
					String videoUrl = oldUrl.replaceFirst("temp", "videos");
					videoOption.setVideoUrl(videoUrl);
					
					// 文件操作
					if (!FileHelper.fileMove(uploadPath + oldUrl.substring(5), uploadPath + videoUrl.substring(5))) {
						logger.error(uploadPath + oldUrl.substring(5) + " 文件移动失败");
					}
				}
				optionList = (List<Option>)(List<?>) videoOptionList;
				break;
			default:
				logger.error("选项类型: " + optionType + "不存在");
				break;
		}
		// 清空 List中的null
		optionList.removeAll(Collections.singletonList(null));
		return optionList;
	}
}
