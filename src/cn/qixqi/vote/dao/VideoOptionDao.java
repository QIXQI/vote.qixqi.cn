package cn.qixqi.vote.dao;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.VideoOption;

public interface VideoOptionDao {
	public String addOption(int voteId, VideoOption option);
	public String deleteOption(int optionId);
	public String updateOption(int optionId, HashMap<String, Object> map);
	public VideoOption getOption(int optionId);
	public List<VideoOption> getOptions(int voteId);
	public String addPoll(int optionId);
}
