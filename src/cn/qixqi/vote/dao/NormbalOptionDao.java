package cn.qixqi.vote.dao;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.NormbalOption;

public interface NormbalOptionDao {
	public String addOption(int voteId, NormbalOption option);
	public String deleteOption(int optionId);
	public String updateOption(int optionId, HashMap<String, Object> map);
	public NormbalOption getOption(int optionId);
	public List<NormbalOption> getOptions(int voteId);
	public String addPoll(int optionId);
}
