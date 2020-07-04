package cn.qixqi.vote.dao;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.AudioOption;

public interface AudioOptionDao extends OptionDao{
	public String addOption(int voteId, AudioOption option);
	public AudioOption getOption(int optionId);
	public List<AudioOption> getOptions(int voteId);
}
