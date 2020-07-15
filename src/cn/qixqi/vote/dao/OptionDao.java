package cn.qixqi.vote.dao;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.Option;

public interface OptionDao {
	// ******************************
	// 完全继承
	public String deleteOption(int optionId);
	public String addPoll(List<Integer> optionIdList);
	public String updateOption(int optionId, HashMap<String, Object> map);

	// ******************************
	// 不完全继承：可以通过子类 -> 父类 -> 子类，实现代码复用
	public String addOption(int voteId, Option option);
	public String addOptions(int voteId, List<Option> optionList);
	public Option getOption(int optionId);
	public List<Option> getOptions(int voteId);
}
