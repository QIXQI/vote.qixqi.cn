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
import cn.qixqi.vote.dao.NormbalOptionDao;
import cn.qixqi.vote.entity.NormbalOption;
import cn.qixqi.vote.entity.Option;

public class NormbalOptionDaoImpl extends BaseDao implements NormbalOptionDao {
	
	private Logger logger = LogManager.getLogger(NormbalOptionDaoImpl.class.getName());
	
	@Override
	public String addOption(int voteId, Option option) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "insert into normbal_option(vote_id, option_desc1, option_desc2, option_desc3, option_desc4, option_desc5) values (?, ?, ?, ?, ?, ?)";
		try {
			NormbalOption normbalOption = (NormbalOption) option;
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			pst.setString(2, normbalOption.getOptionDesc1());
			pst.setString(3, normbalOption.getOptoinDesc2());
			pst.setString(4, normbalOption.getOptionDesc3());
			pst.setString(5, normbalOption.getOptionDesc4());
			pst.setString(6, normbalOption.getOptionDesc5());
			pst.executeUpdate();
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
		String sql = "delete from normbal_option where option_id = ?";
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
		String table = "normbal_option";
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
		String sql = "select option_desc1, option_desc2, option_desc3, option_desc4, option_desc5, option_poll from normbal_option where option_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, optionId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String optionDesc1 = rs.getString(1);
				String optionDesc2 = rs.getString(2);
				String optionDesc3 = rs.getString(3);
				String optionDesc4 = rs.getString(4);
				String optionDesc5 = rs.getString(5);
				int optionPoll = rs.getInt(6);
				option = new NormbalOption(optionId, optionDesc1, optionDesc2, optionDesc3, optionDesc4, optionDesc5, optionPoll);	
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
		String sql = "select option_id, option_desc1, option_desc2, option_desc3, option_desc4, option_desc5, option_poll from normbal_option where vote_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, voteId);
			rs = pst.executeQuery();
			while (rs.next()) {
				int optionId = rs.getInt(1);
				String optionDesc1 = rs.getString(2);
				String optionDesc2 = rs.getString(3);
				String optionDesc3 = rs.getString(4);
				String optionDesc4 = rs.getString(5);
				String optionDesc5 = rs.getString(6);
				int optionPoll = rs.getInt(7);
				optionList.add(new NormbalOption(optionId, optionDesc1, optionDesc2, optionDesc3, optionDesc4, optionDesc5, optionPoll));
			}
		} catch(SQLException se) {
			optionList = null;
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, rs);
		return optionList;
	}

	@Override
	public String addPoll(int optionId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		String sql = "update normbal_option set option_poll = option_poll + 1 where option_id = ?";
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

}
