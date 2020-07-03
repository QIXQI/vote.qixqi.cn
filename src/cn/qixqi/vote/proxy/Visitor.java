package cn.qixqi.vote.proxy;

import java.util.List;
import java.util.HashMap;
import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.entity.Vote;

public interface Visitor {
	// 用户注册
	public String userRegister(User user);
	
	// 用户登录
	public User userLogin(String key, String password);
	
	// 用户注销
	public String userLogout(int userId);
	
	// 更新头像
	public String updateAvatar(int userId, String userAvatar);
	
	// 更新用户个人信息
	public String updateUserInfo(int userId, HashMap<String, Object> map);
	
	// 发布投票
	public String publishVote(int userId, Vote vote);
	
	// 删除投票
	public String deleteVote(int voteId);
	
	// 获取投票
	public Vote getVote(int voteId);
	
	// 获取用户发布的投票
	public List<Vote> getVotes(int userId);
	
	// 投票添加选项
	
	
	
}
