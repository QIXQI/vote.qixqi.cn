package cn.qixqi.vote.factory;

import java.util.List;
import cn.qixqi.vote.entity.Option;
import cn.qixqi.vote.entity.ImgOption;
import cn.qixqi.vote.dao.OptionDao;
import cn.qixqi.vote.dao.ImgOptionDao;
import cn.qixqi.vote.dao.impl.ImgOptionDaoImpl;

public class ImgOptionDaoFactory extends OptionDaoFactory{

	private ImgOptionDao imgOptionDao;
	
	public ImgOptionDaoFactory() {
		super();
		// TODO Auto-generated constructor stub
		this.imgOptionDao = new ImgOptionDaoImpl();
	}

	@Override
	public OptionDao createOptionDao() {
		// TODO Auto-generated method stub
		return new ImgOptionDaoImpl();
	}
	
	// 添加选项
	@Override
	public String addOption(int voteId, Option option) {
		return this.imgOptionDao.addOption(voteId, option);
	}
	
	@Override
	public String addOptions(int voteId, List<Option> optionList) {
		// TODO Auto-generated method stub
		return this.imgOptionDao.addOptions(voteId, optionList);
	}

	// 获取选项
	@Override
	public Option getOption(int optionId) {
		return this.imgOptionDao.getOption(optionId);
	}
	
	// 获取选项列表
	@Override
	public List<Option> getOptions(int voteId){
		return this.imgOptionDao.getOptions(voteId);
	}

}
