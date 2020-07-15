package cn.qixqi.vote;

import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Controller;

import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.entity.VoteLog;
import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;

@Controller
public class VoteLogs {
	private Logger logger = LogManager.getLogger(VoteLogs.class.getName());
	
	@RequestMapping("getLastVoteTime.do")
	public String getLastVoteTime(HttpServletRequest request) {
		if (request.getParameter("voteId") == null || request.getParameter("userId") == null) {
			this.logger.error("voteId 或 userId 为空");
			return null;
		}
		int voteId = Integer.parseInt(request.getParameter("voteId"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		Date lastTime = visitor.lastVoteTime(voteId, userId);
		
		System.out.println(lastTime.toString());
		return null;
	}
	
	@RequestMapping("getLastVoteTimeByTP.do")
	public String getLastVoteTimeByTP(HttpServletRequest request) {
		if (request.getParameter("voteId") == null) {
			this.logger.error("voteId 为空");
			return null;
		}
		int voteId = Integer.parseInt(request.getParameter("voteId"));
		String thirdPartyId = request.getParameter("thirdPartyId");
		Visitor visitor = new ProxyVisitor(Priorities.THIRD_PARTY_USER);
		Date lastTime = visitor.lastVoteTime(voteId, thirdPartyId);
		
		System.out.println(lastTime.toString());
		return null;
	}
	
	
	@RequestMapping("getVoteLogsByVote.do")
	public void getVoteLogsByVote(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "success";
		JSONObject message = new JSONObject();
		if (request.getParameter("voteId") == null) {
			result = "voteId 为空";
			this.logger.error(result);
		} else {
			int voteId = Integer.parseInt(request.getParameter("voteId"));
			Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
			List<VoteLog> voteLogList = visitor.getVoteLogsByVote(voteId);
			message.put("voteLogList", voteLogList);
		}
		
		// 返回 json
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	
	@RequestMapping("getVoteLogsByUser.do")
	public void getVoteLogsByUser(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "success";
		JSONObject message = new JSONObject();
		if (session.getAttribute("user") == null) {
			result = "您还未登录";
			this.logger.error(result);
		} else {
			User user = (User) session.getAttribute("user");
			Visitor visitor = new ProxyVisitor(Priorities.USER);
			List<VoteLog> voteLogList = visitor.getVoteLogsByUser(user.getUserId());
			message.put("voteLogList", voteLogList);
		}

		// 返回 json
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("getVoteLogsByTP.do")
	public String getVoteLogsByTP(HttpServletRequest request) {
		String thirdPartyId = request.getParameter("thirdPartyId");
		Visitor visitor = new ProxyVisitor(Priorities.THIRD_PARTY_USER);
		List<VoteLog> voteLogList = visitor.getVoteLogsByThirdParty(thirdPartyId);
		
		System.out.println(voteLogList.toString());
		return null;
	}
}
