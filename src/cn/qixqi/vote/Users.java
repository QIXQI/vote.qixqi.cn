package cn.qixqi.vote;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;


@Controller
public class Users {
	private Logger logger = LogManager.getLogger(Users.class.getName());
	
	@RequestMapping("register.do")
	public String register(HttpServletRequest request) {
		String userName = request.getParameter("username");
		String userEmail = request.getParameter("email");
		String userPhone = request.getParameter("phone");
		String userPassword = request.getParameter("password");
		User user = new User(userName, userPassword, userEmail, userPhone);
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		String result = visitor.userRegister(user);
		
		System.out.println(result);
		return null;
	}
	
	@RequestMapping("login.do")
	public String login(HttpServletRequest request) {
		String key = request.getParameter("login_field");
		String password = request.getParameter("password");
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		User user = visitor.userLogin(key, password);
		
		if (user != null) {
			System.out.println(user.toString());
		} else {
			System.out.println("null");
		}
		return null;
	}

	@RequestMapping("logout.do")
	public String logout(HttpServletRequest request) {
		if (request.getParameter("userId") == null) {
			return null;
		}
		int userId = Integer.parseInt(request.getParameter("userId"));
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		String result = visitor.userLogout(userId);
		
		System.out.println(result);
		return null;
	}
}
