package cn.qixqi.vote.dao;

import java.util.HashMap;
import cn.qixqi.vote.entity.User;

public interface UserDao {
	public User getUser(int userId);
	public String addUser(User user);
	public String updateUser(int userId, HashMap<String, Object> map);
	public String deleteUser(int userId);
	public String updateStatus(int userId, String status);
	public String updatePriority(int userId, String priority);
}
