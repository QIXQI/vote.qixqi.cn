package cn.qixqi.vote.dao;

public interface UserBindDao {
	public String addQQBind(int userId, String qqOpenId);
	
	public String deleteQQBind(int userId);
	
	public Integer getUserIdByQQ(String qqOpenId);
	
	public String getQQOpenId(int userId);
}
