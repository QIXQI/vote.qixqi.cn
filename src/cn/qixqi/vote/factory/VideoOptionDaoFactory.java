package cn.qixqi.vote.factory;

import java.util.List;
import cn.qixqi.vote.entity.Option;
import cn.qixqi.vote.entity.VideoOption;
import cn.qixqi.vote.dao.OptionDao;
import cn.qixqi.vote.dao.VideoOptionDao;
import cn.qixqi.vote.dao.impl.VideoOptionDaoImpl;

public class VideoOptionDaoFactory extends OptionDaoFactory{

	private VideoOptionDao videoOptionDao;
	
	public VideoOptionDaoFactory() {
		super();
		// TODO Auto-generated constructor stub
		this.videoOptionDao = new VideoOptionDaoImpl();
	}

	@Override
	public OptionDao createOptionDao() {
		// TODO Auto-generated method stub
		return new VideoOptionDaoImpl();
	}
	
	// 添加选项
	@Override
	public String addOption(int voteId, Option option) {
		return this.videoOptionDao.addOption(voteId, option);
	}
	
	@Override
	public String addOptions(int voteId, List<Option> optionList) {
		// TODO Auto-generated method stub
		return this.videoOptionDao.addOptions(voteId, optionList);
	}

	// 获取选项
	@Override
	public Option getOption(int optionId) {
		return this.videoOptionDao.getOption(optionId);
	}
	
	// 获取选项列表
	@Override
	public List<Option> getOptions(int voteId){
		return this.videoOptionDao.getOptions(voteId);
	}
}
