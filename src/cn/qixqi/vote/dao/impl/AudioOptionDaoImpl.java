package cn.qixqi.vote.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cn.qixqi.vote.dao.BaseDao;
import cn.qixqi.vote.dao.AudioOptionDao;
import cn.qixqi.vote.entity.AudioOption;
import cn.qixqi.vote.entity.Option;

public class AudioOptionDaoImpl extends BaseDao implements AudioOptionDao{

	private Logger logger = LogManager.getLogger(AudioOptionDaoImpl.class.getName());
	
	@Override
	public String addOption(int voteId, Option option) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "insert into audio_option(vote_id, audio_url, option_desc1, option_desc2, option_desc3, option_desc4, option_desc5) values (?, ?, ?, ?, ?, ?, ?)";
		try {
			AudioOption audioOption = (AudioOption) option;
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			pst.setString(2, audioOption.getAudioUrl());
			pst.setString(3, audioOption.getOptionDesc1());
			pst.setString(4, audioOption.getOptionDesc2());
			pst.setString(5, audioOption.getOptionDesc3());
			pst.setString(6, audioOption.getOptionDesc4());
			pst.setString(7, audioOption.getOptionDesc5());
			pst.executeUpdate();
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	@Override
	public String addOptions(int voteId, List<Option> optionList) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "insert into audio_option(vote_id, audio_url, option_desc1, option_desc2, option_desc3, option_desc4, option_desc5) values (?, ?, ?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			for (Option option : optionList) {
				AudioOption audioOption = (AudioOption) option;
				pst.setInt(1, voteId);
				pst.setString(2, audioOption.getAudioUrl());
				pst.setString(3, audioOption.getOptionDesc1());
				pst.setString(4, audioOption.getOptionDesc2());
				pst.setString(5, audioOption.getOptionDesc3());
				pst.setString(6, audioOption.getOptionDesc4());
				pst.setString(7, audioOption.getOptionDesc5());
				pst.addBatch();
			}
			pst.executeBatch();
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

	@Override
	public String deleteOption(int optionId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "delete from audio_option where option_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, optionId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = "选项" + optionId + "不存在";
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
	public String updateOption(int optionId, HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		String table = "audio_option";
		String whereSql = " where option_id = " + optionId;
		return executeUpdate(table, whereSql, map);
	}

	@Override
	public Option getOption(int optionId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		Option option = null;
		String sql = "select audio_url, option_desc1, option_desc2, option_desc3, option_desc4, option_desc5, option_poll from audio_option where option_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, optionId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String audioUrl = rs.getString(1);
				String optionDesc1 = rs.getString(2);
				String optionDesc2 = rs.getString(3);
				String optionDesc3 = rs.getString(4);
				String optionDesc4 = rs.getString(5);
				String optionDesc5 = rs.getString(6);
				int optionPoll = rs.getInt(7);
				option = new AudioOption(optionId, optionDesc1, optionDesc2, optionDesc3, optionDesc4, optionDesc5, optionPoll, audioUrl);
			} else {
				this.logger.error("选项" + optionId + "不存在");
			}
		} catch(SQLException se) {
			option = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return option;
	}

	@Override
	public List<Option> getOptions(int voteId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Option> optionList = new ArrayList<Option>();
		String sql = "select option_id, audio_url, option_desc1, option_desc2, option_desc3, option_desc4, option_desc5, option_poll from audio_option where vote_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			rs = pst.executeQuery();
			while (rs.next()) {
				int optionId = rs.getInt(1);
				String audioUrl = rs.getString(2);
				String optionDesc1 = rs.getString(3);
				String optionDesc2 = rs.getString(4);
				String optionDesc3 = rs.getString(5);
				String optionDesc4 = rs.getString(6);
				String optionDesc5 = rs.getString(7);
				int optionPoll = rs.getInt(8);
				optionList.add(new AudioOption(optionId, optionDesc1, optionDesc2, optionDesc3, optionDesc4, optionDesc5, optionPoll, audioUrl));
			}
		} catch(SQLException se) {
			optionList = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return optionList;
	}

	@Override
	public String addPoll(List<Integer> optionIdList) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "update audio_option set option_poll = option_poll + 1 where option_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			for (int optionId : optionIdList) {
				pst.setInt(1, optionId);
				pst.addBatch();
			}
			int[] rows = pst.executeBatch();
			for (int row : rows) {
				if ( row <= 0) {
					result = "optionIdList 中有optionId不存在";
					this.logger.error(result);
					break;
				}
			}
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

}
