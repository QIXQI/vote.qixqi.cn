package cn.qixqi.vote.entity;

import java.util.Date;

public class VoteLog {
	private int voteId;
	private Integer userId;			// 本网站的 userId
	private String thirdPartyId;	// 第三方登录用户的 openid
	private Date voteTime;
	
	/**
	 * 插入数据 - 构造函数
	 * @param voteId
	 * @param userId
	 * @param thirdPartyId
	 */
	public VoteLog(int voteId, Integer userId, String thirdPartyId) {
		super();
		this.voteId = voteId;
		this.userId = userId;
		this.thirdPartyId = thirdPartyId;
	}

	/**
	 * 查找数据 - 构造函数
	 * @param voteId
	 * @param userId
	 * @param thirdPartyId
	 * @param voteTime
	 */
	public VoteLog(int voteId, Integer userId, String thirdPartyId, Date voteTime) {
		super();
		this.voteId = voteId;
		this.userId = userId;
		this.thirdPartyId = thirdPartyId;
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

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public Date getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}

	@Override
	public String toString() {
		return "VoteLog [voteId=" + voteId + ", userId=" + userId + ", thirdPartyId=" + thirdPartyId + ", voteTime="
				+ voteTime + "]";
	}
}
