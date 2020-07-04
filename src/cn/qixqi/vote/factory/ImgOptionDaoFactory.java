package cn.qixqi.vote.factory;

import java.util.List;
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
	public String addOption(int voteId, ImgOption imgOption) {
		return this.imgOptionDao.addOption(voteId, imgOption);
	}
	
	// 获取选项
	public ImgOption getOption(int optionId) {
		return this.imgOptionDao.getOption(optionId);
	}
	
	// 获取选项列表
	public List<ImgOption> getOptions(int voteId){
		return this.getOptions(voteId);
	}

}
