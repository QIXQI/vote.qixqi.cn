package cn.qixqi.vote.dao;

import java.util.HashMap;
import java.util.List;
import cn.qixqi.vote.entity.Vote;

public interface VoteDao {
	public String addVote(int userId, Vote vote);
	public String deleteVote(int voteId);
	public String updateVote(int voteId, HashMap<String, Object> map);
	public Vote getVote(int voteId);
	public Vote getVote(String voteName);
	public List<Vote> getVotes(int userId);
	public List<Vote> getVotes();
	public String addOptionNum(int voteId, int num);
}
