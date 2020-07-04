package cn.qixqi.vote.dao;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.VideoOption;

public interface VideoOptionDao extends OptionDao{
	public String addOption(int voteId, VideoOption option);
	public VideoOption getOption(int optionId);
	public List<VideoOption> getOptions(int voteId);
}
