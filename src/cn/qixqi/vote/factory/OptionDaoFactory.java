package cn.qixqi.vote.factory;

import java.util.HashMap;
import java.util.List;
import cn.qixqi.vote.dao.OptionDao;
import cn.qixqi.vote.entity.Option;

public abstract class OptionDaoFactory {
	private OptionDao optionDao;
	
	public OptionDaoFactory() {
		this.optionDao = this.createOptionDao();
	}
	
	// 创建 Option数据库访问对象
	public abstract OptionDao createOptionDao();
	
	// *********完全继承********************
	// 投票
	public String addPoll(int optionId) {
		return optionDao.addPoll(optionId);
	}
	
	// 删除选项
	public String deleteOption(int optionId) {
		return optionDao.deleteOption(optionId);
	}
	
	// 更新选项
	public String updateOption(int optionId, HashMap<String, Object> map) {
		return optionDao.updateOption(optionId, map);
	}
	
	// **************延迟到子类实现******************
	public abstract String addOption(int voteId, Option option);
	
	public abstract Option getOption(int optionId);
	
	public abstract List<Option> getOptions(int voteId);
}
