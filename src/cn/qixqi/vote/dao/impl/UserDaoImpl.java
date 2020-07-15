package cn.qixqi.vote.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.qixqi.vote.dao.BaseDao;
import cn.qixqi.vote.dao.UserDao;
import cn.qixqi.vote.entity.User;

public class UserDaoImpl extends BaseDao implements UserDao{

	private Logger logger = LogManager.getLogger(UserDaoImpl.class.getName());
	
	/**
	 * 身份验证后二次查询时使用，查询速度较快
	 */
	@Override
	public User getUser(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		User user = null;
		String sql = "select user_name, user_sex, user_priority_id, user_join_time, user_login_time, user_email, user_phone, user_status_id, user_avatar, user_birthday " +
				"from user " + 
				"where user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String userName = rs.getString(1);
				String userSex = rs.getString(2);
				int userPriorityId = rs.getInt(3);
				Date userJoinTime = rs.getTimestamp(4);
				Date userLoginTime = rs.getTimestamp(5);
				String userEmail = rs.getString(6);
				String userPhone = rs.getString(7);
				int userStatusId = rs.getInt(8);
				String userAvatar = rs.getString(9);
				Date userBirthday = rs.getDate(10);
				user = new User(userId, userName, userSex, userPriorityId, userJoinTime, userLoginTime, userEmail, userPhone, userStatusId, userAvatar, userBirthday);
				this.logger.info("查询用户 " + userId + "成功");
			} else {
				this.logger.error("查询用户 " + userId + "失败");
			}
		} catch(SQLException se) {
			user = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return user;
	}

	@Override
	public User getSimpleUser(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		User user = null;
		String sql = "select user_name, user_priority_id, user_status_id, user_avatar " +
				"from user where user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String userName = rs.getString(1);
				int userPriorityId = rs.getInt(2);
				int userStatusId = rs.getInt(3);
				String userAvatar = rs.getString(4);
				user = new User(userId, userName, userPriorityId, userStatusId, userAvatar);
				this.logger.info("查询用户 " + userId + "成功");
			} else {
				this.logger.error("查询用户 " + userId + "失败");
			}
		} catch(SQLException se) {
			user = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return user;
	}

	@Override
	public String getUserAvatar(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String result = null;
		String sql = "select user_avatar from user where user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getString(1);
				this.logger.info("用户 " + userId + "头像链接为: " + result);
			} else {
				this.logger.error("用户 " + userId + "不存在");
			}
		} catch(SQLException se) {
			result = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return result;
	}

	/**
	 * 用户名/邮箱/手机号 + 密码
	 */
	@Override
	public User getUser(String key, String password) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		User user = null;
		String sql = "select user_id, user_name, user_sex, user_priority_id, user_join_time, user_login_time, user_email, user_phone, user_status_id, user_avatar, user_birthday " + 
				"from user " + 
				"where (user_name = ? or user_email = ? or user_phone = ?) and user_password = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, key);
			pst.setString(2, key);
			pst.setString(3, key);
			pst.setString(4, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				int userId = rs.getInt(1);
				String userName = rs.getString(2);
				String userSex = rs.getString(3);
				int userPriorityId = rs.getInt(4);
				Date userJoinTime = rs.getTimestamp(5);
				Date userLoginTime = rs.getTimestamp(6);
				String userEmail = rs.getString(7);
				String userPhone = rs.getString(8);
				int userStatusId = rs.getInt(9);
				String userAvatar = rs.getString(10);
				Date userBirthday = rs.getDate(11);
				user = new User(userId, userName, userSex, userPriorityId, userJoinTime, userLoginTime, userEmail, userPhone, userStatusId, userAvatar, userBirthday);
				this.logger.info("key: " + key + "; password: " + password + " 登录成功");
			} else {
				this.logger.info("key: " + key + "; password: " + password + " 登录失败");
			}
		} catch(SQLException se) {
			user = null;
			this.logger.error(se.getMessage());                   
		}
		closeAll(conn, pst, rs);
		return user;
	}

	@Override
	public String addUser(User user) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "insert into user(user_name, user_password, user_email, user_phone) values (?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserName());
			pst.setString(2, user.getUserPassword());
			pst.setString(3, user.getUserEmail());
			pst.setString(4, user.getUserPhone());
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "添加用户失败: " + user.getUserName();
				this.logger.error(result);
			}
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	@Override
	public String updateUser(int userId, HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		String table = "user";
		String whereSql = " where user_id = " + userId;
		return executeUpdate(table, whereSql, map);
	}

	@Override
	public String resetPass(int userId, String oldPass, String newPass) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "update user set user_password = ? where user_id = ? and user_password = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, newPass);
			pst.setInt(2, userId);
			pst.setString(3, oldPass);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "用户 " + userId + "原密码错误";
				this.logger.error(result);
			}
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	@Override
	public String deleteUser(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "delete from user where user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "删除用户" + userId + "失败";
				this.logger.error(result);
			}			
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	/* @Override
	public String updateStatus(int userId, String status) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "update user u, user_status us " + 
				"set u.user_status_id = us.user_status_id " + 
				"where u.user_id = ? and us.user_status = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			pst.setString(2, status);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "更新用户状态失败";
				this.logger.error("更新用户状态失败");
			}
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}*/

	/* @Override
	public String updatePriority(int userId, String priority) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "update user u, user_priority up " + 
				"set u.user_priority_id = up.user_priority_id " + 
				"where u.user_id = ? and  up.user_priority = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			pst.setString(2, priority);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "更新用户权限失败";
				this.logger.error("更新用户权限失败");
			}
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	} */

}
