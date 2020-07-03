package cn.qixqi.vote.test;

import cn.qixqi.vote.entity.VoteLog;

public class Test {
	public static void main(String[] args) {
		VoteLog voteLog = new VoteLog(1, null, "111");
		System.out.println(voteLog.toString());
	}
}
