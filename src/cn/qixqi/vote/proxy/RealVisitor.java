package cn.qixqi.vote.proxy;

import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.dao.impl.UserDaoImpl;

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
	
	
	
	
	
	
}
