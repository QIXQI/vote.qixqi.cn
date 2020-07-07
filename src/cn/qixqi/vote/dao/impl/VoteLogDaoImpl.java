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
		String sql = "insert into vote_log(vote_id, user_id, third_party_id) values (?, ?, ?)";
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
			pst.setString(3, voteLog.getThirdPartyId());
			pst.executeUpdate();
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}
	
	@Override
	public Date lastVoteTime(int voteId, int userId) {
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Date voteTime = null;
		String sql = "select max(vote_time) from vote_log where vote_id = ? and user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			pst.setInt(2, userId);
			rs = pst.executeQuery();
			if (rs.next()) {
				voteTime = rs.getTimestamp(1);
			} else {
				this.logger.info("用户 " + userId + "还没有投过 " + voteId);
			}
		} catch(SQLException se) {
			voteTime = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return voteTime;
	}
	
	@Override
	public Date lastVoteTime(int voteId, String thirdPartyId) {
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Date voteTime = null;
		String sql = "select max(vote_time) from vote_log where vote_id = ? and third_party_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			pst.setString(2, thirdPartyId);
			rs = pst.executeQuery();
			if (rs.next()) {
				voteTime = rs.getTimestamp(1);
			} else {
				this.logger.info("第三方用户 " + thirdPartyId + "还没有投过 " + voteId);
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
		String sql = "select user_id, third_party_id, vote_time from vote_log where vote_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			rs = pst.executeQuery();
			while (rs.next()) {
				Integer userId = (Integer)rs.getObject(1);
				String thirdPartyId = rs.getString(2);
				Date voteTime = rs.getTimestamp(3);
				voteLogList.add(new VoteLog(voteId, userId, thirdPartyId, voteTime));
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
		String sql = "select vote_id, third_party_id, vote_time from vote_log where user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			while (rs.next()) {
				int voteId = rs.getInt(1);
				String thirdPartyId = rs.getString(2);
				Date voteTime = rs.getTimestamp(3);
				voteLogList.add(new VoteLog(voteId, userId, thirdPartyId, voteTime));
			}
		} catch(SQLException se) {
			voteLogList = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return voteLogList;
	}

	@Override
	public List<VoteLog> getVoteLogsByThirdParty(String thirdPartyId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<VoteLog> voteLogList = new ArrayList<VoteLog>();
		String sql = "select vote_id, user_id, vote_time from vote_log where third_party_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, thirdPartyId);
			rs = pst.executeQuery();
			while (rs.next()) {
				int voteId = rs.getInt(1);
				Integer userId = (Integer) rs.getObject(2);
				Date voteTime = rs.getTimestamp(3);
				voteLogList.add(new VoteLog(voteId, userId, thirdPartyId, voteTime));
			}
		} catch(SQLException se) {
			voteLogList = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return voteLogList;
	}
}
