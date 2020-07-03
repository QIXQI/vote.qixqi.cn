package cn.qixqi.vote.dao;

import java.util.HashMap;
import cn.qixqi.vote.entity.User;

public interface UserDao {
	public User getUser(String key, String password);
	public String addUser(User user);
	public String updateUser(int userId, HashMap<String, Object> map);
	public String deleteUser(int userId);
}
