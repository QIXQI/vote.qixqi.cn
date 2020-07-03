package cn.qixqi.vote.dao;

import java.util.List;
import java.util.Date;
import cn.qixqi.vote.entity.VoteLog;

public interface VoteLogDao {
	public String addVoteLog(VoteLog voteLog);
	public Date lastVoteTime(int voteId, String voteIp);
	public List<VoteLog> getVoteLogsByVote(int voteId);
	public List<VoteLog> getVoteLogsByUser(int userId);
}
