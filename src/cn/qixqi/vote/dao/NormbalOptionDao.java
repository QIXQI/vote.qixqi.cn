package cn.qixqi.vote.dao;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.NormbalOption;

public interface NormbalOptionDao extends OptionDao{
	public String addOption(int voteId, NormbalOption option);
	public NormbalOption getOption(int optionId);
	public List<NormbalOption> getOptions(int voteId);
}
