package cn.qixqi.vote.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.qixqi.vote.dao.BaseDao;
import cn.qixqi.vote.dao.UserDao;
import cn.qixqi.vote.entity.User;

public class UserDaoImpl extends BaseDao implements UserDao{
	private Logger logger = LogManager.getLogger(UserDaoImpl.class.getName());

	@Override
	public User getUser(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		User user = null;
		String sql = "select u.user_nickname, u.user_password, u.user_sex, up.user_priority, u.user_join_time, u.user_login_time, u.user_email, u.user_phone, us.user_status, u.user_avatar, u.user_birthday " + 
				"from user as u, user_priority as up, user_status as us " + 
				"where u.user_id = ? and u.user_priority_id = up.user_priority_id and u.user_status_id = us.user_status_id";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String userNickname = rs.getString(1);
				String userPassword = rs.getString(2);
				String userSex = rs.getString(3);
				String userPriority = rs.getString(4);
				Date userJoinTime = rs.getTimestamp(5);
				Date userLoginTime = rs.getTimestamp(6);
				String userEmail = rs.getString(7);
				String userPhone = rs.getString(8);
				String userStatus = rs.getString(9);
				String userAvatar = rs.getString(10);
				Date userBirthday = rs.getDate(11);
				user = new User(userId, userNickname, userPassword, userSex, userPriority, userJoinTime, userLoginTime, userEmail, userPhone, userStatus, userAvatar, userBirthday);
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
		String sql = "insert into user(user_nickname, user_password, user_email, user_phone) values (?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserNickname());
			pst.setString(2, user.getUserPassword());
			pst.setString(3, user.getUserEmail());
			pst.setString(4, user.getUserPhone());
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "添加用户失败";
				this.logger.error("添加用户失败");
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
				result = "删除用户失败";
				this.logger.error("删除用户失败");
			}			
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	@Override
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
	}

	@Override
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
	}

}
