package cn.qixqi.vote.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.qixqi.vote.dao.BaseDao;
import cn.qixqi.vote.dao.VoteDao;
import cn.qixqi.vote.entity.Vote;

public class VoteDaoImpl extends BaseDao implements VoteDao{
	private Logger logger = LogManager.getLogger(VoteDaoImpl.class.getName());
	
	@Override
	public String addVote(int userId, Vote vote) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "insert into vote(user_id, vote_name, vote_type, vote_end_time, vote_desc1, vote_desc2, vote_desc3, vote_desc4, vote_desc5) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			pst.setString(2, vote.getVoteName());
			pst.setInt(3, vote.getVoteType());
			pst.setTimestamp(4, new Timestamp(vote.getVoteEndTime().getTime()));
			pst.setString(5, vote.getVoteDesc1());
			pst.setString(6, vote.getVoteDesc2());
			pst.setString(7, vote.getVoteDesc3());
			pst.setString(8, vote.getVoteDesc4());
			pst.setString(9, vote.getVoteDesc5());
			pst.executeUpdate();
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	@Override
	public String deleteVote(int voteId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "delete from vote where vote_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "投票" + voteId + "不存在";
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
	public String updateVote(int voteId, HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		String table = "vote";
		String whereSql = " where vote_id = " + voteId;
		return executeUpdate(table, whereSql, map);
	}

	@Override
	public Vote getVote(int voteId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Vote vote = null;
		String sql = "select vote_name, vote_type, vote_start_time, vote_end_time, vote_desc1, vote_desc2, vote_desc3, vote_desc4, vote_desc5, option_number " +
				"from vote where vote_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String voteName = rs.getString(1);
				int voteType = rs.getInt(2);
				Date voteStartTime = rs.getTimestamp(3);
				Date voteEndTime = rs.getTimestamp(4);
				String voteDesc1 = rs.getString(5);
				String voteDesc2 = rs.getString(6);
				String voteDesc3 = rs.getString(7);
				String voteDesc4 = rs.getString(8);
				String voteDesc5 = rs.getString(9);
				int optionNumber = rs.getInt(10);
				vote = new Vote(voteId, voteName, voteType, voteStartTime, voteEndTime, voteDesc1, voteDesc2, voteDesc3, voteDesc4, voteDesc5, optionNumber);
			} else {
				this.logger.error("投票" + voteId + "不存在");
			}
		} catch(SQLException se) {
			vote = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return vote;
	}

	@Override
	public List<Vote> getVotes(int userId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Vote> voteList = new ArrayList<Vote>();
		String sql = "select vote_id, vote_name, vote_type, vote_start_time, vote_end_time, vote_desc1, vote_desc2, vote_desc3, vote_desc4, vote_desc5, option_number " +
				"from vote where user_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, userId);
			rs = pst.executeQuery();
			while (rs.next()) {
				int voteId = rs.getInt(1);
				String voteName = rs.getString(2);
				int voteType = rs.getInt(3);
				Date voteStartTime = rs.getTimestamp(4);
				Date voteEndTime = rs.getTimestamp(5);
				String voteDesc1 = rs.getString(6);
				String voteDesc2 = rs.getString(7);
				String voteDesc3 = rs.getString(8);
				String voteDesc4 = rs.getString(9);
				String voteDesc5 = rs.getString(10);
				int optionNumber = rs.getInt(11);
				voteList.add(new Vote(voteId, voteName, voteType, voteStartTime, voteEndTime, voteDesc1, voteDesc2, voteDesc3, voteDesc4, voteDesc5, optionNumber));
			}
		} catch(SQLException se) {
			voteList = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return voteList;
	}

	@Override
	public String addOptionNum(int voteId, int num) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "update vote set option_number = option_number + " + num + " where vote_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "投票" + voteId + "不存在";
				this.logger.error(result);
			}
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

}
