package cn.qixqi.vote;

import java.util.List;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import cn.qixqi.vote.entity.VoteLog;
import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;

@Controller
public class VoteLogs {
	private Logger logger = LogManager.getLogger(VoteLogs.class.getName());
	
	@RequestMapping("getLastVoteTime.do")
	public String getLastVoteTime(HttpServletRequest request) {
		if (request.getParameter("voteId") == null) {
			this.logger.error("voteId 为空");
			return null;
		}
		int voteId = Integer.parseInt(request.getParameter("voteId"));
		String voteIp = request.getParameter("voteIp");
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		Date lastTime = visitor.lastVoteTime(voteId, voteIp);
		
		System.out.println(lastTime.toString());
		return null;
	}
	
	@RequestMapping("getVoteLogsByVote.do")
	public String getVoteLogsByVote(HttpServletRequest request) {
		if (request.getParameter("voteId") == null) {
			this.logger.error("voteId 为空");
			return null;
		}
		int voteId = Integer.parseInt(request.getParameter("voteId"));
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		List<VoteLog> voteLogList = visitor.getVoteLogsByVote(voteId);
		
		System.out.println(voteLogList.toString());
		return null;
	}
	
	@RequestMapping("getVoteLogsByUser.do")
	public String getVoteLogsByUser(HttpServletRequest request) {
		if (request.getParameter("userId") == null) {
			this.logger.error("userId 为空");
			return null;
		}
		int userId = Integer.parseInt(request.getParameter("userId"));
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		List<VoteLog> voteLogList = visitor.getVoteLogsByUser(userId);
		
		System.out.println(voteLogList.toString());
		return null;
	}
}
