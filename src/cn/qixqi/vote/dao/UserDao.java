package cn.qixqi.vote.dao;

import java.util.HashMap;
import java.util.List;

import cn.qixqi.vote.entity.User;

public interface UserDao {
	public User getUser(int userId);
	public User getUser(String key, String password);
	public User getSimpleUser(int userId);
	public String getUserAvatar(int userId);
	public String addUser(User user);
	public String updateUser(int userId, HashMap<String, Object> map);
	public String resetPass(int userId, String oldPass, String newPass);
	public String deleteUser(int userId);
}
