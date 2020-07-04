package cn.qixqi.vote.proxy;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.vote.entity.AudioOption;
import cn.qixqi.vote.entity.ImgOption;
import cn.qixqi.vote.entity.NormbalOption;
import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.entity.VideoOption;
import cn.qixqi.vote.entity.Vote;
import cn.qixqi.vote.dao.impl.UserDaoImpl;
import cn.qixqi.vote.dao.impl.VoteDaoImpl;
import cn.qixqi.vote.factory.*;

public class RealVisitor implements Visitor{
	
	private Logger logger = LogManager.getLogger(RealVisitor.class.getName());

	@Override
	public String userRegister(User user) {
		UserDaoImpl ui = new UserDaoImpl();
		return ui.addUser(user);
	}

	@Override
	public User userLogin(String key, String password) {
		// TODO Auto-generated method stub
		UserDaoImpl ui = new UserDaoImpl();
		User user = ui.getUser(key, password);
		if (user != null) {
			// 更新用户状态
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("user_status_id", Status.ONLINE);
			String result = ui.updateUser(user.getUserId(), map);
			if ("success".equals(result)) {
				user.setUserStatus(Status.ONLINE);
				this.logger.info("用户 " + user.getUserId() + "更新为在线状态成功");
			} else {
				this.logger.error("用户 " + user.getUserId() + "更新为在线状态失败: " + result);
			}
		}
		return user;
	}

	@Override
	public String userLogout(int userId) {
		// TODO Auto-generated method stub
		UserDaoImpl ui = new UserDaoImpl();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user_status_id", Status.OFFLINE);
		String result = ui.updateUser(userId, map);
		if ("success".equals(result)) {
			this.logger.info("用户 " + userId + "注销成功");
		} else {
			this.logger.error("用户 " + userId + "注销失败: " + result);
		}
		return result;
	}

	@Override
	public String updateAvatar(int userId, String userAvatar) {
		// TODO Auto-generated method stub
		UserDaoImpl ui = new UserDaoImpl();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("user_avatar", userAvatar);
		String result = ui.updateUser(userId, map);
		if ("success".equals(result)) {
			this.logger.info("用户 " + userId + "更新头像成功");
		} else {
			this.logger.error("用户 " + userId + "更新头像失败: " + result);
		}
		return result;
	}

	@Override
	public String updateUserInfo(int userId, HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		UserDaoImpl ui = new UserDaoImpl();
		String result = ui.updateUser(userId, map);
		if ("success".equals(result)) {
			this.logger.info("用户 " + userId + "更新个人信息成功");
		} else {
			this.logger.error("用户 " + userId + "更新个人信息失败: " + result);
		}
		return result;
	}

	@Override
	public String publishVote(int userId, Vote vote) {
		// TODO Auto-generated method stub
		VoteDaoImpl vi = new VoteDaoImpl();
		String result = vi.addVote(userId, vote);
		if ("success".equals(result)) {
			this.logger.info("用户 " + userId + "发布投票成功");
		} else {
			this.logger.error("用户 " + userId + "发布投票失败: " + result);
		}
		return result;
	}

	@Override
	public String deleteVote(int voteId) {
		// TODO Auto-generated method stub
		VoteDaoImpl vi = new VoteDaoImpl();
		String result = vi.deleteVote(voteId);
		if ("success".equals(result)) {
			this.logger.info("删除投票 " + voteId + "成功");
		} else {
			this.logger.error("删除投票 " + voteId + "失败: " + result);
		}
		return result;
	}

	@Override
	public Vote getVote(int voteId) {
		// TODO Auto-generated method stub
		VoteDaoImpl vi = new VoteDaoImpl();
		return vi.getVote(voteId);
	}

	@Override
	public List<Vote> getVotes(int userId) {
		// TODO Auto-generated method stub
		VoteDaoImpl vi = new VoteDaoImpl();
		return vi.getVotes(userId);
	}

	@Override
	public String addOption(int voteId, NormbalOption normbalOption) {
		// TODO Auto-generated method stub
		NormbalOptionDaoFactory factory = new NormbalOptionDaoFactory();
		return factory.addOption(voteId, normbalOption);
	}

	@Override
	public String addOption(int voteId, ImgOption imgOption) {
		// TODO Auto-generated method stub
		ImgOptionDaoFactory factory = new ImgOptionDaoFactory();
		return factory.addOption(voteId, imgOption);
	}

	@Override
	public String addOption(int voteId, AudioOption audioOption) {
		// TODO Auto-generated method stub
		AudioOptionDaoFactory factory = new AudioOptionDaoFactory();
		return factory.addOption(voteId, audioOption);
	}

	@Override
	public String addOption(int voteId, VideoOption videoOption) {
		// TODO Auto-generated method stub
		VideoOptionDaoFactory factory = new VideoOptionDaoFactory();
		return factory.addOption(voteId, videoOption);
	}

	@Override
	public NormbalOption getNormbalOption(int optionId) {
		// TODO Auto-generated method stub
		NormbalOptionDaoFactory factory = new NormbalOptionDaoFactory();
		return factory.getOption(optionId);
	}

	@Override
	public ImgOption getImgOption(int optionId) {
		// TODO Auto-generated method stub
		ImgOptionDaoFactory factory = new ImgOptionDaoFactory();
		return factory.getOption(optionId);
	}

	@Override
	public AudioOption getAudioOption(int optionId) {
		// TODO Auto-generated method stub
		AudioOptionDaoFactory factory = new AudioOptionDaoFactory();
		return factory.getOption(optionId);
	}

	@Override
	public VideoOption getVideoOption(int optionId) {
		// TODO Auto-generated method stub
		VideoOptionDaoFactory factory = new VideoOptionDaoFactory();
		return factory.getOption(optionId);
	}

	@Override
	public List<NormbalOption> getNormbalOptions(int voteId) {
		// TODO Auto-generated method stub
		NormbalOptionDaoFactory factory = new NormbalOptionDaoFactory();
		return factory.getOptions(voteId);
	}

	@Override
	public List<ImgOption> getImgOptions(int voteId) {
		// TODO Auto-generated method stub
		ImgOptionDaoFactory factory = new ImgOptionDaoFactory();
		return factory.getOptions(voteId);
	}

	@Override
	public List<AudioOption> getAudioOptions(int voteId) {
		// TODO Auto-generated method stub
		AudioOptionDaoFactory factory = new AudioOptionDaoFactory();
		return factory.getOptions(voteId);
	}

	@Override
	public List<VideoOption> getVideoOptions(int voteId) {
		// TODO Auto-generated method stub
		VideoOptionDaoFactory factory = new VideoOptionDaoFactory();
		return factory.getOptions(voteId);
	}

	@Override
	public String addPoll(int optionId) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = new NormbalOptionDaoFactory();
		return factory.addPoll(optionId);
	}

	@Override
	public String deleteOption(int optionId) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = new NormbalOptionDaoFactory();
		return factory.deleteOption(optionId);
	}

	@Override
	public String updateOption(int optionId, HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = new NormbalOptionDaoFactory();
		return factory.updateOption(optionId, map);
	}
	
	
	
	
	
	
}
