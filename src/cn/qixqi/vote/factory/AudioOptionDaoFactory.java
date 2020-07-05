package cn.qixqi.vote.factory;

import java.util.List;
import cn.qixqi.vote.entity.Option;
import cn.qixqi.vote.entity.AudioOption;
import cn.qixqi.vote.dao.OptionDao;
import cn.qixqi.vote.dao.AudioOptionDao;
import cn.qixqi.vote.dao.impl.AudioOptionDaoImpl;

public class AudioOptionDaoFactory extends OptionDaoFactory{

	private AudioOptionDao audioOptionDao;
	
	public AudioOptionDaoFactory() {
		super();
		// TODO Auto-generated constructor stub
		this.audioOptionDao = new AudioOptionDaoImpl();
	}

	@Override
	public OptionDao createOptionDao() {
		// TODO Auto-generated method stub
		return new AudioOptionDaoImpl();
	}
	
	// 添加选项
	@Override
	public String addOption(int voteId, Option option) {
		return this.audioOptionDao.addOption(voteId, option);
	}
	
	// 获取选项
	@Override
	public Option getOption(int optionId) {
		return this.audioOptionDao.getOption(optionId);
	}
	
	// 获取选项列表
	@Override
	public List<Option> getOptions(int voteId){
		return this.audioOptionDao.getOptions(voteId);
	}

}
