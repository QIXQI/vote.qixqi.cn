package cn.qixqi.vote.entity;

import java.util.Date;

public class User {
	private int userId;
	private String userNickname;
	private String userPassword;
	private String userSex;
	private String userPriority;
	private Date userJoinTime;
	private Date userLoginTime;
	private String userEmail;
	private String userPhone;
	private String userStatus;
	private String userAvatar;
	private Date userBirthday;
	
	/**
	 * 注册用户 - 构造函数
	 * @param userNickname
	 * @param userPassword
	 * @param userEmail
	 * @param userPhone
	 */
	public User(String userNickname, String userPassword, String userEmail, String userPhone) {
		super();
		this.userNickname = userNickname;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
	}

	/**
	 * 获取用户信息 - 构造函数
	 * @param userId
	 * @param userNickname
	 * @param userPassword
	 * @param userSex
	 * @param userPriority
	 * @param userJoinTime
	 * @param userLoginTime
	 * @param userEmail
	 * @param userPhone
	 * @param userStatus
	 * @param userAvatar
	 * @param userBirthday
	 */
	public User(int userId, String userNickname, String userPassword, String userSex, String userPriority,
			Date userJoinTime, Date userLoginTime, String userEmail, String userPhone, String userStatus,
			String userAvatar, Date userBirthday) {
		super();
		this.userId = userId;
		this.userNickname = userNickname;
		this.userPassword = userPassword;
		this.userSex = userSex;
		this.userPriority = userPriority;
		this.userJoinTime = userJoinTime;
		this.userLoginTime = userLoginTime;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userStatus = userStatus;
		this.userAvatar = userAvatar;
		this.userBirthday = userBirthday;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserPriority() {
		return userPriority;
	}

	public void setUserPriority(String userPriority) {
		this.userPriority = userPriority;
	}

	public Date getUserJoinTime() {
		return userJoinTime;
	}

	public void setUserJoinTime(Date userJoinTime) {
		this.userJoinTime = userJoinTime;
	}

	public Date getUserLoginTime() {
		return userLoginTime;
	}

	public void setUserLoginTime(Date userLoginTime) {
		this.userLoginTime = userLoginTime;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userNickname=" + userNickname + ", userPassword=" + userPassword
				+ ", userSex=" + userSex + ", userPriority=" + userPriority + ", userJoinTime=" + userJoinTime
				+ ", userLoginTime=" + userLoginTime + ", userEmail=" + userEmail + ", userPhone=" + userPhone
				+ ", userStatus=" + userStatus + ", userAvatar=" + userAvatar + ", userBirthday=" + userBirthday + "]";
	}
}
