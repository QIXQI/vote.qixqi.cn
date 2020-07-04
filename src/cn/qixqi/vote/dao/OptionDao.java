package cn.qixqi.vote.dao;

import java.util.HashMap;

public interface OptionDao {
	public String deleteOption(int optionId);
	public String addPoll(int optionId);
	public String updateOption(int optionId, HashMap<String, Object> map);

}
