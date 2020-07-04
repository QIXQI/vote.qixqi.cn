package cn.qixqi.vote.dao;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.ImgOption;

public interface ImgOptionDao extends OptionDao{
	public String addOption(int voteId, ImgOption option);
	public ImgOption getOption(int optionId);
	public List<ImgOption> getOptions(int voteId);
}
