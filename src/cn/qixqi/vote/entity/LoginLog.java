package cn.qixqi.vote.entity;

import java.util.Date;

public class LoginLog {
	private int userId;
	private String loginIp;
	private Date loginTime;
	
	/**
	 * 插入数据 - 构造函数
	 * @param userId
	 * @param loginIp
	 */
	public LoginLog(int userId, String loginIp) {
		super();
		this.userId = userId;
		this.loginIp = loginIp;
	}

	/**
	 * 查询数据 - 构造函数
	 * @param userId
	 * @param loginIp
	 * @param loginTime
	 */
	public LoginLog(int userId, String loginIp, Date loginTime) {
		super();
		this.userId = userId;
		this.loginIp = loginIp;
		this.loginTime = loginTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return "LoginLog [userId=" + userId + ", loginIp=" + loginIp + ", loginTime=" + loginTime + "]";
	}
}
