package cn.qixqi.vote.proxy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.vote.entity.LoginLog;
import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.entity.Vote;
import cn.qixqi.vote.entity.VoteLog;
import cn.qixqi.vote.dao.impl.UserDaoImpl;
import cn.qixqi.vote.dao.impl.VoteDaoImpl;
import cn.qixqi.vote.dao.impl.LoginLogDaoImpl;
import cn.qixqi.vote.dao.impl.VoteLogDaoImpl;
import cn.qixqi.vote.dao.impl.UserBindDaoImpl;
import cn.qixqi.vote.factory.*;
import cn.qixqi.vote.util.OptionDaoFactoryHelper;
import cn.qixqi.vote.entity.Option;

public class RealVisitor implements Visitor{
	
	private Logger logger = LogManager.getLogger(RealVisitor.class.getName());

	@Override
	public String userRegister(User user) {
		UserDaoImpl ui = new UserDaoImpl();
		String result = ui.addUser(user);
		if ("success".equals(result)) {
			this.logger.info("用户注册成功：" + user.getUserName());
		} else {
			this.logger.error("用户注册失败：" + user.getUserName() + ", " + result);
		}
		return result;
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
			map.put("user_login_time", new Date());
			String result = ui.updateUser(user.getUserId(), map);
			if ("success".equals(result)) {
				user = ui.getUser(user.getUserId());
				this.logger.info("用户 " + user.getUserId() + "更新为在线状态成功");
			} else {
				this.logger.error("用户 " + user.getUserId() + "更新为在线状态失败: " + result);
			}
		}
		return user;
	}

	@Override
	public User getSimpleUser(int userId) {
		// TODO Auto-generated method stub
		UserDaoImpl ui = new UserDaoImpl();
		return ui.getSimpleUser(userId);
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
	public User getUserInfo(int userId) {
		// TODO Auto-generated method stub
		UserDaoImpl ui = new UserDaoImpl();
		User user = ui.getUser(userId);
		if (user == null) {
			this.logger.error("用户" + userId + " 获取信息失败");
		} else {
			this.logger.info("用户" + userId + " 获取信息成功");
		}
		return user;
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
	public String getUserAvatar(int userId) {
		// TODO Auto-generated method stub
		UserDaoImpl ui = new UserDaoImpl();
		String result = ui.getUserAvatar(userId);
		if (result != null) {
			this.logger.info("用户 " + userId + "获取头像成功: " + result);
		} else {
			this.logger.error("用户 " + userId + "获取头像失败");
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
	public String resetPass(int userId, String oldPass, String newPass) {
		// TODO Auto-generated method stub
		UserDaoImpl ui = new UserDaoImpl();
		String result = ui.resetPass(userId, oldPass, newPass);
		if ("success".equals(result)) {
			this.logger.info("用户 " + userId + "更新密码 " + newPass + "成功");
		} else {
			this.logger.error("用户 " + userId + "更新密码 " + newPass + "失败: " + result);
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
	public Vote getVote(String voteName) {
		// TODO Auto-generated method stub
		VoteDaoImpl vi = new VoteDaoImpl();
		return vi.getVote(voteName);
	}

	@Override
	public List<Vote> getVotes(int userId) {
		// TODO Auto-generated method stub
		VoteDaoImpl vi = new VoteDaoImpl();
		return vi.getVotes(userId);
	}

	@Override
	public List<Vote> getVotes() {
		// TODO Auto-generated method stub
		VoteDaoImpl vi = new VoteDaoImpl();
		return vi.getVotes();
	}

	@Override
	public String addOption(int optionType, int voteId, Option option) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = OptionDaoFactoryHelper.getFactory(optionType);
		if (factory == null) {
			String result = "选项类型: " + optionType + "不存在";
			this.logger.error(result);
			return result;
		}
		return factory.addOption(voteId, option);
	}

	@Override
	public String addOptions(int optionType, int voteId, List<Option> optionList) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = OptionDaoFactoryHelper.getFactory(optionType);
		if (factory == null) {
			String result = "选项类型: " + optionType + "不存在";
			this.logger.error(result);
			return result;
		}
		return factory.addOptions(voteId, optionList);
	}

	@Override
	public Option getOption(int optionType, int optionId) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = OptionDaoFactoryHelper.getFactory(optionType);
		if (factory == null) {
			this.logger.error("选项类型: " + optionType + "不存在");
			return null;
		}
		return factory.getOption(optionId);
	}

	@Override
	public List<Option> getOptions(int optionType, int voteId) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = OptionDaoFactoryHelper.getFactory(optionType);
		if (factory == null) {
			this.logger.error("选项类型: " + optionType + "不存在");
			return null;
		}
		return factory.getOptions(voteId);
	}

	@Override
	public String addPoll(int optionType, List<Integer> optionIdList) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = OptionDaoFactoryHelper.getFactory(optionType);
		if (factory == null) {
			String result = "选项类型: " + optionType + "不存在";
			this.logger.error(result);
			return result;
		}
		return factory.addPoll(optionIdList);
	}

	@Override
	public String deleteOption(int optionType, int optionId) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = OptionDaoFactoryHelper.getFactory(optionType);
		if (factory == null) {
			String result = "选项类型: " + optionType + "不存在";
			this.logger.error(result);
			return result;
		}
		return factory.deleteOption(optionId);
	}

	@Override
	public String updateOption(int optionType, int optionId, HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		OptionDaoFactory factory = OptionDaoFactoryHelper.getFactory(optionType);
		if (factory == null) {
			String result = "选项类型: " + optionType + "不存在";
			this.logger.error(result);
			return result;
		}
		return factory.updateOption(optionId, map);
	}

	@Override
	public String addLoginLog(LoginLog log) {
		// TODO Auto-generated method stub
		LoginLogDaoImpl li = new LoginLogDaoImpl();
		return li.addLoginLog(log);
	}

	@Override
	public LoginLog lastLoginLog(int userId) {
		// TODO Auto-generated method stub
		LoginLogDaoImpl li = new LoginLogDaoImpl();
		return li.lastLoginLog(userId);
	}

	@Override
	public List<LoginLog> getLoginLogs(int userId) {
		// TODO Auto-generated method stub
		LoginLogDaoImpl li = new LoginLogDaoImpl();
		return li.getLoginLogs(userId);
	}

	@Override
	public String addVoteLog(VoteLog voteLog) {
		// TODO Auto-generated method stub
		VoteLogDaoImpl vi = new VoteLogDaoImpl();
		return vi.addVoteLog(voteLog);
	}

	@Override
	public Date lastVoteTime(int voteId, int userId) {
		// TODO Auto-generated method stub
		VoteLogDaoImpl vi = new VoteLogDaoImpl();
		return vi.lastVoteTime(voteId, userId);
	}

	@Override
	public Date lastVoteTime(int voteId, String thirdPartyId) {
		// TODO Auto-generated method stub
		VoteLogDaoImpl vi = new VoteLogDaoImpl();
		return vi.lastVoteTime(voteId, thirdPartyId);
	}

	@Override
	public List<VoteLog> getVoteLogsByVote(int voteId) {
		// TODO Auto-generated method stub
		VoteLogDaoImpl vi = new VoteLogDaoImpl();
		return vi.getVoteLogsByVote(voteId);
	}

	@Override
	public List<VoteLog> getVoteLogsByUser(int userId) {
		// TODO Auto-generated method stub
		VoteLogDaoImpl vi = new VoteLogDaoImpl();
		return vi.getVoteLogsByUser(userId);
	}

	@Override
	public List<VoteLog> getVoteLogsByThirdParty(String thirdPartyId) {
		// TODO Auto-generated method stub
		VoteLogDaoImpl vi = new VoteLogDaoImpl();
		return vi.getVoteLogsByThirdParty(thirdPartyId);
	}

	@Override
	public String addQQBind(int userId, String qqOpenId) {
		// TODO Auto-generated method stub
		UserBindDaoImpl ubi = new UserBindDaoImpl();
		return ubi.addQQBind(userId, qqOpenId);
	}

	@Override
	public String deleteQQBind(int userId) {
		// TODO Auto-generated method stub
		UserBindDaoImpl ubi = new UserBindDaoImpl();
		return ubi.deleteQQBind(userId);
	}

	@Override
	public Integer getUserIdByQQ(String qqOpenId) {
		// TODO Auto-generated method stub
		UserBindDaoImpl ubi = new UserBindDaoImpl();
		return ubi.getUserIdByQQ(qqOpenId);
	}

	@Override
	public String getQQOpenId(int userId) {
		// TODO Auto-generated method stub
		UserBindDaoImpl ubi = new UserBindDaoImpl();
		return ubi.getQQOpenId(userId);
	}
}
