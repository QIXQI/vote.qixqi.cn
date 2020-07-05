package cn.qixqi.vote;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import cn.qixqi.vote.entity.LoginLog;
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
	public String getLoginLogs(HttpServletRequest request) {
		if (request.getParameter("userId") == null) {
			this.logger.error("userId 为空");
			return null;
		}
		int userId = Integer.parseInt(request.getParameter("userId"));
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		List<LoginLog> loginLogList = visitor.getLoginLogs(userId);
		
		System.out.println(loginLogList.toString());
		return null;
	}
}
