package cn.qixqi.vote.entity;

import java.util.Date;

public class User {
	private int userId;
	private String userName;
	private String userPassword;
	private String userSex;
	private int userPriority;
	private Date userJoinTime;
	private Date userLoginTime;
	private String userEmail;
	private String userPhone;
	private int userStatus;
	private String userAvatar;
	private Date userBirthday;
	
	/**
	 * 获取用户简略信息 - 构造函数
	 * @param userId
	 * @param userName
	 * @param userPriority
	 * @param userStatus
	 * @param userAvatar
	 */
	public User(int userId, String userName, int userPriority, int userStatus, String userAvatar) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPriority = userPriority;
		this.userStatus = userStatus;
		this.userAvatar = userAvatar;
	}

	/**
	 * 注册用户 - 构造函数
	 * @param userName
	 * @param userPassword
	 * @param userEmail
	 * @param userPhone
	 */
	public User(String userName, String userPassword, String userEmail, String userPhone) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
	}

	/**
	 * 获取用户信息 - 构造函数
	 * @param userId
	 * @param userName
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
	public User(int userId, String userName, String userSex, int userPriority,
			Date userJoinTime, Date userLoginTime, String userEmail, String userPhone, int userStatus,
			String userAvatar, Date userBirthday) {
		super();
		this.userId = userId;
		this.userName = userName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public int getUserPriority() {
		return userPriority;
	}

	public void setUserPriority(int userPriority) {
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

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
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
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", userSex="
				+ userSex + ", userPriority=" + userPriority + ", userJoinTime=" + userJoinTime + ", userLoginTime="
				+ userLoginTime + ", userEmail=" + userEmail + ", userPhone=" + userPhone + ", userStatus=" + userStatus
				+ ", userAvatar=" + userAvatar + ", userBirthday=" + userBirthday + "]";
	}
}
