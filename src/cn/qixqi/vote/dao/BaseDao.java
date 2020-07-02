package cn.qixqi.vote.dao;

import java.sql.*;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseDao {
	private Logger logger = LogManager.getLogger(BaseDao.class.getName());
	
	public Connection getConnection() {
		try {
			Context cxt = new InitialContext();
			DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/vote");
			Connection conn = ds.getConnection();
			return conn;
		} catch(NamingException ne){
			this.logger.error(ne.getMessage());
			return null;
		} catch(SQLException se) {
			this.logger.error(se.getMessage());
			return null;
		}
	}
	
	public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch(SQLException se){
			this.logger.error(se.getMessage());
		}
	}
	
	public int executeUpdate(String preparedSql, Object[] params) {
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int num = 0;
		try {
			pst = conn.prepareStatement(preparedSql);
			if (params != null) {
				for (int i=0; i<params.length; i++) {
					pst.setObject(i+1, params[i]);
				}
			}
			num = pst.executeUpdate();
		} catch(SQLException se) {
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return num;
	}
	
	public String executeUpdate(String table, String whereSql, HashMap<String, Object> map) {
		if (map == null || map.size() == 0) {
			return "更新参数为空";
		}
		Connection conn = getConnection();
		PreparedStatement pst = null;
		String result = "success";
		try {
			StringBuilder sb = new StringBuilder();
			int size = map.size();
			int index = 0;
			sb.append("update ");
			sb.append(table);
			sb.append(" set ");
			for (String key : map.keySet()) {
				if (index == size - 1) {
					sb.append(key);
					sb.append(" = ? ");
				} else {
					sb.append(key);
					sb.append(" = ?, ");
				}
				index ++;
			}
			sb.append(whereSql);
			pst = conn.prepareStatement(sb.toString());
			index = 1;
			for (Object value : map.values()) {
				pst.setObject(index, value);
				index ++;
			}
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				result = table + "更新条数为0";
			}
		} catch(SQLException se) {
			result = se.getMessage();
			this.logger.error(se.getMessage());
		}
		closeAll(conn, pst, null);
		return result;
	}

}
