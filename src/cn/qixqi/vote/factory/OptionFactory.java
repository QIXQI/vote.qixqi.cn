package cn.qixqi.vote.factory;

import cn.qixqi.vote.entity.Option;
import cn.qixqi.vote.entity.NormbalOption;
import cn.qixqi.vote.entity.ImgOption;
import cn.qixqi.vote.entity.AudioOption;
import cn.qixqi.vote.entity.VideoOption;

public class OptionFactory {
	// 获取指定类型的投票选项
	public Option getOption(int optionType) {
		switch (optionType) {
			case OptionType.NORMBAL_OPTION:
				return new NormbalOption();
			case OptionType.IMG_OPTION:
				return new ImgOption();
			case OptionType.AUDIO_OPTION:
				return new AudioOption();
			case OptionType.VIDEO_OPTION:
				return new VideoOption();
			default:
				// throw new UnknownTypeException();
				return null;
		}
	}
}
