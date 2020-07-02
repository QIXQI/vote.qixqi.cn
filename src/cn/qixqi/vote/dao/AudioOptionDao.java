package cn.qixqi.vote.dao;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.AudioOption;

public interface AudioOptionDao {
	public String addOption(int voteId, AudioOption option);
	public String deleteOption(int optionId);
	public String updateOption(int optionId, HashMap<String, Object>map);
	public AudioOption getOption(int optionId);
	public List<AudioOption> getOptions(int voteId);
	public String addPoll(int optionId);
}
