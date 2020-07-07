package cn.qixqi.vote;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.JSONObject;

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
	
	@RequestMapping("resetPass.do")
	public String resetPass(HttpServletRequest request) {
		int userId = -1;
		if (request.getParameter("userId") != null) {
			userId = Integer.parseInt(request.getParameter("userId"));
		}
		String userPassword = request.getParameter("userPassword");
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		String result = visitor.resetPass(userId, userPassword);
		
		System.out.println(result);
		return null;
	}
	
	@RequestMapping("setUserInfo")
	public String setUserInfo(HttpServletRequest request) {
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
		String result = visitor.updateUserInfo(userId, map);
		
		System.out.println(result);
		return null;
	}
	
	@RequestMapping("setUserAvatar")
	public String setUserAvatar(HttpServletRequest request) {
		// 当天日期
		String thisDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		// 返回字符串
		String result = "";
		// 返回json字符串
		JSONObject message = new JSONObject();
		
		// 检测是否是多媒体上传
		if (!ServletFileUpload.isMultipartContent(request)) {
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
		
		System.out.println(message.toJSONString());
		return null;
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
		String[] dicts = oldAvatarUrl.split("/");
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
}
