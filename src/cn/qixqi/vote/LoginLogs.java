package cn.qixqi.vote;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Controller;

import cn.qixqi.vote.entity.LoginLog;
import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;

@Controller
public class LoginLogs {
	private Logger logger = LogManager.getLogger(LoginLogs.class.getName());
	
	@RequestMapping("getLastLogin.do")
	public String getLastLogin(HttpServletRequest request) {
		if (request.getParameter("userId") == null) {
			this.logger.error("userId 为空");
			return null;
		}
		int userId = Integer.parseInt(request.getParameter("userId"));
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		LoginLog lastLogin = visitor.lastLoginLog(userId);
		
		System.out.println(lastLogin.toString());
		return null;
	}
	
	@RequestMapping("getLoginLogs.do")
	public void getLoginLogs(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "success";
		JSONObject message = new JSONObject();
		if (session.getAttribute("user") == null) {
			result = "您还未登录";
			this.logger.error(result);
		} else {
			User user = (User) session.getAttribute("user");
			Visitor visitor = new ProxyVisitor(Priorities.USER);
			List<LoginLog> loginLogList = visitor.getLoginLogs(user.getUserId());
			message.put("loginLogList", loginLogList);
		}

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		message.put("result", result);
		out.println(message.toJSONString());
	}
}
