package cn.qixqi.vote.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.qixqi.vote.entity.LoginLog;
import cn.qixqi.vote.dao.BaseDao;
import cn.qixqi.vote.dao.LoginLogDao;

public class LoginLogDaoImpl extends BaseDao implements LoginLogDao{
	private Logger logger = LogManager.getLogger(LoginLogDaoImpl.class.getName());

	@Override
	public String addLoginLog(LoginLog log) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "insert into login_log(user_id, login_ip) values (?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, log.getUserId());
			pst.setString(2, log.getLoginIp());
			pst.executeUpdate();
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	@Override
	public LoginLog lastLoginLog(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		LoginLog loginLog = null;
		String sql = "select ll1.login_ip, ll1.login_time " + 
				"from login_log as ll1, (select max(login_time) lastTime from login_log where user_id = ?) as ll2 " + 
				"where ll1.user_id = ? and ll1.login_time = ll2.lastTime";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			pst.setInt(2, userId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String loginIp = rs.getString(1);
				Date loginTime = rs.getTimestamp(2);
				loginLog = new LoginLog(userId, loginIp, loginTime);
			} else {
				this.logger.info("用户" + userId + "最近没有登录");
			}
		} catch(SQLException se) {
			loginLog = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return loginLog;
	}

	@Override
	public List<LoginLog> getLoginLogs(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<LoginLog> logList = new ArrayList<LoginLog>();
		String sql = "select login_ip, login_time from login_log where user_id = ? order by login_time desc";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			while (rs.next()) {
				String loginIp = rs.getString(1);
				Date loginTime = rs.getTimestamp(2);
				logList.add(new LoginLog(userId, loginIp, loginTime));
			}
		} catch(SQLException se) {
			logList = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return logList;
	}
	
}
