package cn.qixqi.vote.proxy;

import java.util.HashMap;
import cn.qixqi.vote.entity.User;

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
	
}
