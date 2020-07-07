package cn.qixqi.vote.proxy;

import java.util.List;
import java.util.Date;
import java.util.HashMap;

import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.entity.Vote;
import cn.qixqi.vote.entity.VoteLog;
import cn.qixqi.vote.entity.Option;
import cn.qixqi.vote.entity.LoginLog;

public interface Visitor {
	// ****************************
	// 用户
	// 用户注册
	public String userRegister(User user);
	
	// 用户登录
	public User userLogin(String key, String password);
	
	// 用户注销
	public String userLogout(int userId);
	
	// 更新头像
	public String updateAvatar(int userId, String userAvatar);
	
	// 获取头像
	public String getUserAvatar(int userId);
	
	// 更新用户个人信息
	public String updateUserInfo(int userId, HashMap<String, Object> map);
	
	// 更新密码
	public String resetPass(int userId, String userPassword);
	
	// *****************************
	// 投票
	// 发布投票
	public String publishVote(int userId, Vote vote);
	
	// 删除投票
	public String deleteVote(int voteId);
	
	// 获取投票
	public Vote getVote(int voteId);
	
	// 获取用户发布的投票
	public List<Vote> getVotes(int userId);
	
	// ***********************
	// 投票选项
	// 投票添加选项
	public String addOption(int optionType, int voteId, Option option);
	
	// 获取选项
	public Option getOption(int optionType, int optionId);
	
	// 获取选项列表
	public List<Option> getOptions(int optionType, int voteId);
	
	// 投票
	public String addPoll(int optionType, int optionId);
	
	// 删除选项
	public String deleteOption(int optionType, int optionId);
	
	// 更新选项
	public String updateOption(int optionType, int optionId, HashMap<String, Object> map);
	
	// *******************************************
	// 登录日志
	// 添加登录日志
	public String addLoginLog(LoginLog log);
	
	// 最近登录
	public LoginLog lastLoginLog(int userId);
	
	// 获取登录日志列表
	public List<LoginLog> getLoginLogs(int userId);
	
	// *******************************************
	// 投票日志
	// 添加投票日志
	public String addVoteLog(VoteLog voteLog);
	
	// 最近投票时间
	public Date lastVoteTime(int voteId, int userId);
	public Date lastVoteTime(int voteId, String thirdPartyId);
	
	// 某一投票的投票日志
	public List<VoteLog> getVoteLogsByVote(int voteId);
	
	// 某一用户的投票日志
	public List<VoteLog> getVoteLogsByUser(int userId);
	
	// 某一第三方用户的投票日志
	public List<VoteLog> getVoteLogsByThirdParty(String thirdPartyId);
	
	// *******************************************
	// 账号绑定
	// 绑定QQ
	public String addQQBind(int userId, String qqOpenId);
	
	// 取消绑定QQ
	public String deleteQQBind(int userId);
	
	// 获取 userId
	public Integer getUserIdByQQ(String qqOpenId);
	
	// 获取 qq_openid
	public String getQQOpenId(int userId);
}
