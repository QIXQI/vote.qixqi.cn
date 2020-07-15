package cn.qixqi.vote;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.entity.Vote;
import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;

@Controller
public class Votes {
	private Logger logger = LogManager.getLogger(Votes.class.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping("publishVote.do")
	public void publishVote(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result;
		if (session.getAttribute("user") == null) {
			result = "您还未登录";
			this.logger.error(result);
		}
		else if (request.getParameter("voteType") == null || request.getParameter("voteEndTime") == null) {
			result = "voteType 或 voteEndTime 为空";
			this.logger.error(result);
		} else {
			User user = (User) session.getAttribute("user");
			try {
				String voteName = request.getParameter("voteName");
				int voteType = Integer.parseInt(request.getParameter("voteType"));
				Date voteEndTime = sdf.parse(request.getParameter("voteEndTime"));
				String voteDesc1 = request.getParameter("voteDesc1");
				String voteDesc2 = request.getParameter("voteDesc2");
				String voteDesc3 = request.getParameter("voteDesc3");
				String voteDesc4 = request.getParameter("voteDesc4");
				String voteDesc5 = request.getParameter("voteDesc5");
				Vote vote = new Vote(voteName, voteType, voteEndTime, voteDesc1, voteDesc2, voteDesc3, voteDesc4, voteDesc5);
				Visitor visitor = new ProxyVisitor(Priorities.USER);
				result = visitor.publishVote(user.getUserId(), vote);
				
				// 将投票放到 session
				if ("success".equals(result)) {
					vote = visitor.getVote(voteName);
					session.setAttribute("vote", vote);
				}
				
			} catch(ParseException pe) {
				result = pe.getMessage();
				this.logger.error(pe.getMessage());
			}
		}
		
		// 返回 json
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("getVoteByName.do")
	public void getVoteByName(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Vote temp = (Vote) session.getAttribute("vote");
		Vote vote;
		if (request.getParameter("voteName") == null) {
			vote = null;
		} else if (temp != null && temp.getVoteName().equals(request.getParameter("voteName"))) {
			vote = temp;
		} else {
			Visitor visitor = new ProxyVisitor(Priorities.USER);
			vote = visitor.getVote(request.getParameter("voteName"));
			if (vote != null) {
				session.setAttribute("vote", vote);
			}
		}
		
		// 返回 json
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("vote", vote);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("deleteVote.do")
	public String deleteVote(HttpServletRequest request) {
		String result = null;
		if (request.getParameter("voteId") == null) {
			this.logger.error("voteId 为空");
			return result;
		}
		int voteId = Integer.parseInt(request.getParameter("voteId"));
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		result = visitor.deleteVote(voteId);
		
		System.out.println(result);
		return null;
	}
	
	@RequestMapping("getVote.do")
	public void getVote(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Vote vote = null;
		String result = "success";
		if (request.getParameter("voteId") == null) {
			result = "voteId 为空";
			this.logger.error(result);
		} else {
			int voteId = Integer.parseInt(request.getParameter("voteId"));
			Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
			vote = visitor.getVote(voteId);
		}
		
		// 返回 json
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("result", result);
		message.put("vote", vote);
		out.println(message.toJSONString());
	}
	
	
	@RequestMapping("getVotes.do")
	public void getVotes(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "success";
		JSONObject message = new JSONObject();
		if (session.getAttribute("user") == null) {
			result = "您还未登录";
			this.logger.error(result);
		} else {
			User user = (User) session.getAttribute("user");
			Visitor visitor = new ProxyVisitor(Priorities.USER);
			List<Vote> voteList = visitor.getVotes(user.getUserId());
			message.put("voteList", voteList);
		}
		
		// 返回json
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("getAllVotes.do")
	public void getAllVotes(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		List<Vote> voteList = visitor.getVotes();
		
		// 返回json
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		if (voteList == null) {
			message.put("result", "voteList is null");
		} else {
			message.put("result", "success");
			message.put("voteList", JSON.toJSONString(voteList));
		}
		out.println(message.toJSONString());
	}
}
