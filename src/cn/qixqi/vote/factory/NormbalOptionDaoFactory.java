package cn.qixqi.vote.factory;

import cn.qixqi.vote.entity.Option;
import cn.qixqi.vote.entity.NormbalOption;
import cn.qixqi.vote.dao.OptionDao;

import java.util.List;

import cn.qixqi.vote.dao.NormbalOptionDao;
import cn.qixqi.vote.dao.impl.NormbalOptionDaoImpl;

public class NormbalOptionDaoFactory extends OptionDaoFactory{
	private NormbalOptionDao normbalOptionDao;
	
	public NormbalOptionDaoFactory() {
		super();
		// TODO Auto-generated constructor stub
		this.normbalOptionDao = new NormbalOptionDaoImpl();
	}

	@Override
	public OptionDao createOptionDao() {
		// TODO Auto-generated method stub
		return new NormbalOptionDaoImpl();
	}
	
	// 添加选项
	@Override
	public String addOption(int voteId, Option option) {
		return this.normbalOptionDao.addOption(voteId, option);
	}
	
	// 获取选项
	@Override
	public Option getOption(int optionId) {
		return this.normbalOptionDao.getOption(optionId);
	}

	// 获取选项列表
	@Override
	public List<Option> getOptions(int voteId){
		return this.normbalOptionDao.getOptions(voteId);
	}
}
