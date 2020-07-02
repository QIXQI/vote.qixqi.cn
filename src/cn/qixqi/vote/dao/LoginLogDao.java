package cn.qixqi.vote.dao;

import java.util.List;
import cn.qixqi.vote.entity.LoginLog;

public interface LoginLogDao {
	public String addLoginLog(LoginLog log);
	public LoginLog lastLoginLog(int userId);
	public List<LoginLog> getLoginLogs(int userId);
}
