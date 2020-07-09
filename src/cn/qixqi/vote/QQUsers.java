package cn.qixqi.vote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;

@Controller
public class QQUsers {
	private Logger logger = LogManager.getLogger(QQUsers.class.getName());
	
	@RequestMapping("qqLogin.do")
	public void qqLogin(HttpServletRequest request, HttpServletResponse response) {
		
	}
}
