package cn.qixqi.vote.proxy;

import java.util.HashMap;

import cn.qixqi.vote.entity.User;

public class ProxyVisitor implements Visitor{
	private int priority;
	private RealVisitor realVisitor = new RealVisitor();
	
	public ProxyVisitor(int priority) {
		this.priority = priority;
	}
	
	@Override
	public String userRegister(User user) {
		if (priority == Priorities.VISITOR) {
			// 游客注册
			return realVisitor.userRegister(user);
		} else {
			return "已有账号，且已登录";
		}
	}

	@Override
	public User userLogin(String key, String password) {
		// TODO Auto-generated method stub
		if (priority == Priorities.VISITOR) {
			return realVisitor.userLogin(key, password);
		} else {
			// 已经登录
			return null;
		}
	}

	@Override
	public String userLogout(int userId) {
		// TODO Auto-generated method stub
		if (priority != Priorities.VISITOR) {
			return realVisitor.userLogout(userId);
		} else {
			return "您还未登录";
		}
	}

	@Override
	public String updateAvatar(int userId, String userAvatar) {
		// TODO Auto-generated method stub
		if (priority != Priorities.VISITOR) {
			return realVisitor.updateAvatar(userId, userAvatar);
		} else {
			return "您还未登录";
		}
	}

	@Override
	public String updateUserInfo(int userId, HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		if (priority != Priorities.VISITOR) {
			return realVisitor.updateUserInfo(userId, map);
		} else {
			return "您还未登录";
		}
	}
	
	
	
	
	
	
	
}
