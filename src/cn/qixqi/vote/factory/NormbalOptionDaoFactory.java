package cn.qixqi.vote.factory;

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
	public String addOption(int voteId, NormbalOption normbalOption) {
		return this.normbalOptionDao.addOption(voteId, normbalOption);
	}
	
	// 获取选项
	public NormbalOption getOption(int optionId) {
		return this.normbalOptionDao.getOption(optionId);
	}

	// 获取选项列表
	public List<NormbalOption> getOptions(int voteId){
		return this.getOptions(voteId);
	}
}
