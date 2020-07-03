package cn.qixqi.vote.entity;

import java.util.Date;

public class VoteLog {
	private int voteId;
	private Integer userId;			// 游客为空
	private String voteIp;
	private Date voteTime;
	
	/**
	 * 插入数据 - 构造函数
	 * @param voteId
	 * @param userId
	 * @param voteIp
	 */
	public VoteLog(int voteId, Integer userId, String voteIp) {
		super();
		this.voteId = voteId;
		this.userId = userId;
		this.voteIp = voteIp;
	}

	/**
	 * 查找数据 - 构造函数
	 * @param voteId
	 * @param userId
	 * @param voteIp
	 * @param voteTime
	 */
	public VoteLog(int voteId, Integer userId, String voteIp, Date voteTime) {
		super();
		this.voteId = voteId;
		this.userId = userId;
		this.voteIp = voteIp;
		this.voteTime = voteTime;
	}

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getVoteIp() {
		return voteIp;
	}

	public void setVoteIp(String voteIp) {
		this.voteIp = voteIp;
	}

	public Date getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}

	@Override
	public String toString() {
		return "VoteLog [voteId=" + voteId + ", userId=" + userId + ", voteIp=" + voteIp + ", voteTime=" + voteTime
				+ "]";
	}
}
