package cn.qixqi.vote;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.qixqi.vote.entity.LoginLog;
import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;


@Controller
public class Users {
	private Logger logger = LogManager.getLogger(Users.class.getName());
	
	// 文件上传配置
	private static final String UPLOAD_DIRECTORY = "images" + File.separator + "avatar";
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;	// 3M
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;		// 40M
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;	// 50M
	
	@RequestMapping("register.do")
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("username");
		String userEmail = request.getParameter("email");
		String userPhone = request.getParameter("phone");
		String userPassword = request.getParameter("password");
		User user = new User(userName, userPassword, userEmail, userPhone);
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		String result = visitor.userRegister(user);
		
		// 返回JSON数据
		// response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("login.do")
	public void login(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (session.getAttribute("user") != null) {
			session.removeAttribute("user");
			this.logger.info("切换账号，清空session");
		}
		String key = request.getParameter("login_field");
		String password = request.getParameter("password");
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		User user = visitor.userLogin(key, password);
		
		// session 保存用户信息
		if (user != null) {
			session.setAttribute("user", user);
		}
		
		// 添加登录日志
		if (user != null) {
			visitor = new ProxyVisitor(Priorities.USER);
			LoginLog loginLog = new LoginLog(user.getUserId(), getUserIp(request));
			if ("success".equals(visitor.addLoginLog(loginLog))){
				this.logger.info("添加登录日志成功");
			} else {
				this.logger.error("添加登录日志失败");
			}
		}
		
		// 返回JSON数据
		String result = user != null ? "success" : "error";
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("result", result);
		message.put("user", JSON.toJSONString(user));
		out.println(message.toJSONString());
	}
	
	@RequestMapping("getLoginUser.do")
	public void getLoginUser(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "noLogined";
		JSONObject message = new JSONObject();
		if (session.getAttribute("user") != null) {
			message.put("user", (User)session.getAttribute("user"));
			result = "success";
		}
		
		// 返回json数据
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		message.put("result", result);
		out.println(message.toJSONString());
	}

	@RequestMapping("logout.do")
	public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "success";
		if (request.getParameter("userId") == null) {
			result = "userId 为空";
			this.logger.error(result);
		} else if (session.getAttribute("user") == null){
			result = "您还未登录";
			this.logger.error(result);
		} else {
			int userId = Integer.parseInt(request.getParameter("userId"));
			User user = (User) session.getAttribute("user");
			if (userId != user.getUserId()) {
				result = "您还未登录";
				this.logger.error(result);
			} else {
				session.removeAttribute("user");
				Visitor visitor = new ProxyVisitor(Priorities.USER);
				result = visitor.userLogout(userId);
			}
		}
		
		// 返回json数据
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("resetPass.do")
	public void resetPass(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = null;
		if (session.getAttribute("user") == null) {
			result = "您还未登录";
			this.logger.error(result);
		} else if (request.getParameter("userId") == null) {
			result = "userId 为空";
			this.logger.error(result);
		} else {
			int userId = Integer.parseInt(request.getParameter("userId"));
			String oldPass = request.getParameter("oldPass");
			String newPass = request.getParameter("newPass");
			
			User user = (User) session.getAttribute("user");
			if (userId != user.getUserId()) {
				result = "请更改当前登录用户的密码";
				this.logger.error(result);
			} else {
				Visitor visitor = new ProxyVisitor(Priorities.USER);
				result = visitor.resetPass(userId, oldPass, newPass);
			}
		}
		
		// 返回json数据
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("getSimpleUser.do")
	public void getSimpleUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userId = -1;
		if (request.getParameter("userId") != null) {
			userId = Integer.parseInt(request.getParameter("userId"));
		}
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		User user = visitor.getSimpleUser(userId);
		
		// 返回json数据
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		String result = user == null ? "user为空" : "success";
		message.put("result", result);
		message.put("user", user);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("setUserInfo.do")
	public void setUserInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = null;
		if (session.getAttribute("user") == null) {
			result = "您还未登录";
			this.logger.error(result);
		} else {
			int userId = -1;
			HashMap<String, Object> map = new HashMap<String, Object>();
			if (request.getParameter("userId") != null) {
				userId = Integer.parseInt(request.getParameter("userId"));
			}
			if (request.getParameter("userName") != null) {
				map.put("user_name", request.getParameter("userName"));
			}
			if (request.getParameter("userSex") != null) {
				map.put("user_sex", request.getParameter("userSex"));
			}
			if (request.getParameter("userEmail") != null) {
				map.put("user_email", request.getParameter("userEmail"));
			}
			if (request.getParameter("userPhone") != null) {
				map.put("user_phone", request.getParameter("userPhone"));
			}
			if (request.getParameter("userBirthday") != null) {
				map.put("user_birthday", request.getParameter("userBirthday"));
			}
			Visitor visitor = new ProxyVisitor(Priorities.USER);
			result = visitor.updateUserInfo(userId, map);
			
			if ("success".equals(result)) {
				// 更新session
				User user = visitor.getUserInfo(userId);
				session.setAttribute("user", user);
			}
		}
		
		
		// 返回json数据
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("setUserAvatar.do")
	public void setUserAvatar(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 当天日期
		String thisDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		// 返回字符串
		String result = "";
		// 返回json字符串
		JSONObject message = new JSONObject();
		
		if (session.getAttribute("user") == null) {
			result = "您还未登录";
		}
		// 检测是否是多媒体上传
		else if (!ServletFileUpload.isMultipartContent(request)) {
			// 不是多媒体上传
			result = "Error: 表单必须包含 enctype=multipart/form-data";
		} else {
			// 多媒体上传
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置内存临界值 - 超过后生成临时文件，保存到临时文件夹内
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			// 设置临时文目录
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置最大文件上传值
			upload.setFileSizeMax(MAX_FILE_SIZE);
			// 设置最大请求值
			upload.setSizeMax(MAX_REQUEST_SIZE);
			// 中文处理
			upload.setHeaderEncoding("UTF-8");
			
			// 构造文件上传路径
			String avatarPath = request.getServletContext().getRealPath(".") + File.separator + UPLOAD_DIRECTORY;
			String suffixPath = File.separator + thisDate.substring(0, 4) + File.separator + thisDate.substring(5, 7) + File.separator + thisDate.substring(8) + File.separator;
			String uploadPath = avatarPath + suffixPath;
			String avatarUrl = "/vote/images/avatar" + suffixPath;
			
			// 目录不存在则创建
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}
			
			try {
				// 解析请求
				@SuppressWarnings("unchecked")
				List<?> formItems = upload.parseRequest(request);
				
				if (formItems != null && formItems.size() > 0) {
					// 初始化数据
					int userId = -1;
					// 创建迭代器
					Iterator iter = formItems.iterator();
					while (iter.hasNext()) {
						FileItem item = (FileItem) iter.next();
						if (item.isFormField()) {
							// 表单元素
							if ("userId".equals(item.getFieldName())) {
								userId = Integer.parseInt(item.getString());
							}
						} else {
							// 文件元素
							String fileName = new File(item.getName()).getName();
							// 使用 userId，替换文件名
							fileName = userId + fileName.substring(fileName.lastIndexOf("."));
							
							Visitor visitor = new ProxyVisitor(Priorities.USER);
							result = updateAvatar(visitor, userId, avatarUrl, fileName, avatarPath);
							
							if ("success".equals(result)) {
								// 保存到硬盘，如果存在则覆盖
								String filePath = uploadPath + fileName;
								File storeFile = new File(filePath);
								item.write(storeFile);
								
								// 添加更新后的头像链接
								message.put("avatarUrl", avatarUrl + fileName);
								
								// 更新 session
								User user = visitor.getUserInfo(userId);
								session.setAttribute("user", user);
							}
						}
					}
				} else {
					result = "empty";
				}
			} catch(Exception e){
				result = e.getMessage();
				this.logger.error(e.getMessage());
			}
			
		}
		message.put("result", result);
		
		
		// 返回json字符串
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.println(message.toJSONString());
	}
	
	/**
	 * 更改头像逻辑
	 * 	 1. 首先上传文件二进制数据到服务器，然后使用userId更改文件名
	 *   2. 查找数据库，获取以前的头像连接
	 *   	- 查找失败，直接退出
	 *   	- 查找成功，进行第3步
	 *   3. 更新数据库，更新头像链接 
	 *   	- 更新失败，直接退出
	 *   	- 更新成功
	 *   		- 以前头像链接是默认的，不用删除，返回成功，保存文件到系统
	 *   		- 删除以前头像，返回成功，保存文件到系统
	 */
	private String updateAvatar(Visitor visitor, int userId, String avatarUrl, String fileName, String avatarPath) {
		String result = "";
		
		//  查询头像url
		result = visitor.getUserAvatar(userId);
		if (result == null) {
			this.logger.error("用户 " + userId + "查询头像失败");
			return "查询头像失败";
		}
		// 查询头像成功
		String oldAvatarUrl = result;
		
		// 数据库更改头像链接
		result = visitor.updateAvatar(userId, avatarUrl + fileName);
		if (!"success".equals(result)) {
			result = "数据库更新头像链接失败";
			return result;
		}
		
		// 数据库更新头像链接成功
		// 解析头像链接，删除以前的头像
		// winows 与 unix 分隔符不一致
		String splitReg = File.separator.equals("/") ? "/" : "\\\\";
		String[] dicts = oldAvatarUrl.split(splitReg);
		String oldFileName = dicts[dicts.length - 1];
		if (oldFileName.length() >= 7 && oldFileName.substring(0, 7).equals("default")) {
			// 默认头像，不删除
			result = "success";
		} else {
			String oldFilePath = avatarPath + File.separator + dicts[dicts.length - 4] + File.separator + dicts[dicts.length - 3] + File.separator + dicts[dicts.length - 2] + File.separator + oldFileName;
			File oldFile = new File(oldFilePath);
			oldFile.delete();
			result = "success";
		}
		return result;
	}
	
    // 获取用户ip
    private String getUserIp(HttpServletRequest request){
        // 优先获取 X-Real-IP
        String ip = request.getHeader("X-Real-IP");
        String ERROR_IP = "127.0.0.1";
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("x-forwarded-for");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
            if("0:0:0:0:0:0:0:1".equals(ip)){
                ip = ERROR_IP;
            }
        }
        if("unknown".equalsIgnoreCase(ip)){
            ip = ERROR_IP;
            return ip;
        }
        int index = ip.indexOf(',');
        if(index >= 0){
            ip = ip.substring(0, index);
        }
        return ip;
    }
}
