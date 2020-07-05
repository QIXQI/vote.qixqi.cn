package cn.qixqi.vote;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.JSONObject;

import cn.qixqi.vote.entity.Vote;
import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;

@Controller
public class Votes {
	private Logger logger = LogManager.getLogger(Votes.class.getName());
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RequestMapping("publishVote.do")
	public String publishVote(HttpServletRequest request) {
		String result = null;
		if (request.getParameter("userId") == null || request.getParameter("voteType") == null || request.getParameter("voteEndTime") == null) {
			this.logger.error("userId, voteType 或 voteEndTime 为空");
			return result;
		}
		try {
			int userId = Integer.parseInt(request.getParameter("userId"));
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
			result = visitor.publishVote(userId, vote);
			
			System.out.println(result);
		} catch(ParseException pe) {
			result = null;
			this.logger.error(pe.getMessage());
		}
		return null;
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
	public String getVote(HttpServletRequest request) {
		if (request.getParameter("voteId") == null) {
			this.logger.error("voteId 为空");
			return null;
		}
		int voteId = Integer.parseInt(request.getParameter("voteId"));
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		Vote vote = visitor.getVote(voteId);
		
		System.out.println(vote.toString());
		return null;
	}
	
	@RequestMapping("getVotes.do")
	public String getVotes(HttpServletRequest request) {
		if (request.getParameter("userId") == null) {
			this.logger.error("userId 为空");
			return null;
		}
		int userId = Integer.parseInt(request.getParameter("userId"));
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		List<Vote> voteList = visitor.getVotes(userId);
		
		System.out.println(voteList.toString());
		return null;
	}
}
