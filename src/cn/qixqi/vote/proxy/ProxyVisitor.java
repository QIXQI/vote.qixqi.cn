package cn.qixqi.vote.proxy;

import java.util.HashMap;
import java.util.List;

import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.entity.Vote;

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

	@Override
	public String publishVote(int userId, Vote vote) {
		// TODO Auto-generated method stub
		if (priority != Priorities.VISITOR) {
			return realVisitor.publishVote(userId, vote);
		} else {
			return "您还未登录";
		}
	}

	@Override
	public String deleteVote(int voteId) {
		// TODO Auto-generated method stub
		if (priority != Priorities.VISITOR) {
			return realVisitor.deleteVote(voteId);
		} else {
			return "您还未登录";
		}
	}

	@Override
	public Vote getVote(int voteId) {
		// TODO Auto-generated method stub
		return realVisitor.getVote(voteId);
	}

	@Override
	public List<Vote> getVotes(int userId) {
		// TODO Auto-generated method stub
		if (priority != Priorities.VISITOR) {
			return realVisitor.getVotes(userId);
		} else {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
}
