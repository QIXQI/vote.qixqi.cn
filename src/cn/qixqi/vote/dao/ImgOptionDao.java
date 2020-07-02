package cn.qixqi.vote.dao;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.ImgOption;

public interface ImgOptionDao {
	public String addOption(int voteId, ImgOption option);
	public String deleteOption(int optionId);
	public String updateOption(int optionId, HashMap<String, Object> map);
	public ImgOption getOption(int optionId);
	public List<ImgOption> getOptions(int voteId);
	public String addPoll(int optionId);
}
