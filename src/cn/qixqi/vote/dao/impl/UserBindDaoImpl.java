package cn.qixqi.vote.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.qixqi.vote.dao.BaseDao;
import cn.qixqi.vote.dao.UserBindDao;

public class UserBindDaoImpl extends BaseDao implements UserBindDao{

	private Logger logger = LogManager.getLogger(UserBindDaoImpl.class.getName());
	
	@Override
	public String addQQBind(int userId, String qqOpenId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "insert into user_bind(user_id, qq_openid) values (?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			pst.setString(2, qqOpenId);
			pst.executeUpdate();
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	@Override
	public String deleteQQBind(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "delete from user_bind where user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "用户 " + userId + "没有绑定QQ";
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
	public Integer getUserIdByQQ(String qqOpenId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Integer userId = null;
		String sql = "select user_id from user_bind where qq_openid = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, qqOpenId);
			rs = pst.executeQuery();
			if (rs.next()) {
				userId = (Integer)rs.getObject(1);
			} else {
				this.logger.error("QQ " + qqOpenId + "没有绑定用户");
			}
		} catch(SQLException se) {
			userId = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return userId;
	}

	@Override
	public String getQQOpenId(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String qqOpenId = null;
		String sql = "select qq_openid from user_bind where user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			if (rs.next()) {
				qqOpenId = rs.getString(1);
			} else {
				this.logger.error("用户 " + userId + "没有绑定QQ");
			}
		} catch(SQLException se) {
			qqOpenId = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return qqOpenId;
	}

}
