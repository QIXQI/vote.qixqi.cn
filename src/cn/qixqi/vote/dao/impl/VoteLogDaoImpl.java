package cn.qixqi.vote.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.qixqi.vote.entity.VoteLog;
import cn.qixqi.vote.dao.BaseDao;
import cn.qixqi.vote.dao.VoteLogDao;

public class VoteLogDaoImpl extends BaseDao implements VoteLogDao{

	private Logger logger = LogManager.getLogger(VoteLogDaoImpl.class.getName());
	
	@Override
	public String addVoteLog(VoteLog voteLog) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "insert into vote_log(vote_id, user_id, vote_ip) values (?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteLog.getVoteId());
			if (voteLog.getUserId() == null) {
				// 游客投票
				pst.setNull(2, Types.INTEGER);
			} else {
				// 用户投票
				pst.setInt(2, voteLog.getUserId());
			}
			pst.setString(3, voteLog.getVoteIp());
			pst.executeUpdate();
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	@Override
	public Date lastVoteTime(int voteId, String voteIp) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Date voteTime = null;
		String sql = "select max(vote_time) " + 
				"from vote_log " + 
				"where vote_id = ? and vote_ip = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			pst.setString(2, voteIp);
			rs = pst.executeQuery();
			if (rs.next()) {
				voteTime = rs.getTimestamp(1);
			} else {
				voteTime = new Date(0); 	// 1970-01-01 00:00:00 与 null区别开来
				this.logger.info("IP: " + voteIp + " 还没有对" + voteId + "投过票");
			}
		} catch(SQLException se) {
			voteTime = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return voteTime;
	}

	@Override
	public List<VoteLog> getVoteLogsByVote(int voteId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<VoteLog> voteLogList = new ArrayList<VoteLog>();
		String sql = "select user_id, vote_ip, vote_time from vote_log where vote_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			rs = pst.executeQuery();
			while (rs.next()) {
				Integer userId = (Integer)rs.getObject(1);
				String voteIp = rs.getString(2);
				Date voteTime = rs.getTimestamp(3);
				voteLogList.add(new VoteLog(voteId, userId, voteIp, voteTime));
			}
		} catch(SQLException se) {
			voteLogList = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return voteLogList;
	}

	@Override
	public List<VoteLog> getVoteLogsByUser(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<VoteLog> voteLogList = new ArrayList<VoteLog>();
		String sql = "select vote_id, vote_ip, vote_time from vote_log where user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			while (rs.next()) {
				int voteId = rs.getInt(1);
				String voteIp = rs.getString(2);
				Date voteTime = rs.getTimestamp(3);
				voteLogList.add(new VoteLog(voteId, userId, voteIp, voteTime));
			}
		} catch(SQLException se) {
			voteLogList = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return voteLogList;
	}

}
