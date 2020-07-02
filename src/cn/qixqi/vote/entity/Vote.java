package cn.qixqi.vote.entity;

import java.util.Date;

public class Vote {
	private int voteId;
	private String voteName;
	private int voteType;
	private Date voteTime;
	private Date voteEndTime;
	private String voteDesc1;
	private String voteDesc2;
	private String voteDesc3;
	private String voteDesc4;
	private String voteDesc5;
	private int optionNumber;

	/**
	 * 添加投票 - 构造函数
	 * @param voteName
	 * @param voteType
	 * @param voteEndTime
	 * @param voteDesc1
	 * @param voteDesc2
	 * @param voteDesc3
	 * @param voteDesc4
	 * @param voteDesc5
	 */
	public Vote(String voteName, int voteType, Date voteEndTime, String voteDesc1, String voteDesc2, String voteDesc3,
			String voteDesc4, String voteDesc5) {
		super();
		this.voteName = voteName;
		this.voteType = voteType;
		this.voteEndTime = voteEndTime;
		this.voteDesc1 = voteDesc1;
		this.voteDesc2 = voteDesc2;
		this.voteDesc3 = voteDesc3;
		this.voteDesc4 = voteDesc4;
		this.voteDesc5 = voteDesc5;
	}

	public Vote(int voteId, String voteName, int voteType, Date voteTime, Date voteEndTime, String voteDesc1,
			String voteDesc2, String voteDesc3, String voteDesc4, String voteDesc5, int optionNumber) {
		super();
		this.voteId = voteId;
		this.voteName = voteName;
		this.voteType = voteType;
		this.voteTime = voteTime;
		this.voteEndTime = voteEndTime;
		this.voteDesc1 = voteDesc1;
		this.voteDesc2 = voteDesc2;
		this.voteDesc3 = voteDesc3;
		this.voteDesc4 = voteDesc4;
		this.voteDesc5 = voteDesc5;
		this.optionNumber = optionNumber;
	}

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	public String getVoteName() {
		return voteName;
	}

	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}

	public int getVoteType() {
		return voteType;
	}

	public void setVoteType(int voteType) {
		this.voteType = voteType;
	}

	public Date getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}

	public Date getVoteEndTime() {
		return voteEndTime;
	}

	public void setVoteEndTime(Date voteEndTime) {
		this.voteEndTime = voteEndTime;
	}

	public String getVoteDesc1() {
		return voteDesc1;
	}

	public void setVoteDesc1(String voteDesc1) {
		this.voteDesc1 = voteDesc1;
	}

	public String getVoteDesc2() {
		return voteDesc2;
	}

	public void setVoteDesc2(String voteDesc2) {
		this.voteDesc2 = voteDesc2;
	}

	public String getVoteDesc3() {
		return voteDesc3;
	}

	public void setVoteDesc3(String voteDesc3) {
		this.voteDesc3 = voteDesc3;
	}

	public String getVoteDesc4() {
		return voteDesc4;
	}

	public void setVoteDesc4(String voteDesc4) {
		this.voteDesc4 = voteDesc4;
	}

	public String getVoteDesc5() {
		return voteDesc5;
	}

	public void setVoteDesc5(String voteDesc5) {
		this.voteDesc5 = voteDesc5;
	}

	public int getOptionNumber() {
		return optionNumber;
	}

	public void setOptionNumber(int optionNumber) {
		this.optionNumber = optionNumber;
	}

	@Override
	public String toString() {
		return "Vote [voteId=" + voteId + ", voteName=" + voteName + ", voteType=" + voteType + ", voteTime=" + voteTime
				+ ", voteEndTime=" + voteEndTime + ", voteDesc1=" + voteDesc1 + ", voteDesc2=" + voteDesc2
				+ ", voteDesc3=" + voteDesc3 + ", voteDesc4=" + voteDesc4 + ", voteDesc5=" + voteDesc5
				+ ", optionNumber=" + optionNumber + "]";
	}
}
