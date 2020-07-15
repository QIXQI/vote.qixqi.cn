package cn.qixqi.vote;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.util.OptionDaoFactoryHelper;
import cn.qixqi.vote.util.TimeHelper;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;
import cn.qixqi.vote.factory.OptionType;
import cn.qixqi.vote.entity.Option;
import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.entity.NormbalOption;
import cn.qixqi.vote.entity.ImgOption;
import cn.qixqi.vote.entity.AudioOption;
import cn.qixqi.vote.entity.VideoOption;
import cn.qixqi.vote.entity.VoteLog;

@Controller
public class Options {
	private Logger logger = LogManager.getLogger(Options.class.getName());
	
	// 文件上传配置
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;	// 3M
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;		// 40M
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;	// 50M
	private static final String TEMP_DIR = "options" + File.separator + "temp";

	@RequestMapping("addOption.do")
	public String addOption(HttpServletRequest request) {
		String result = null;
		if (request.getParameter("optionType") == null || request.getParameter("voteId") == null) {
			this.logger.error("optionType 或 voteId 为空");
			return result;
		}
		int optionType = Integer.parseInt(request.getParameter("optionType"));
		int voteId = Integer.parseInt(request.getParameter("voteId"));
		String optionDesc1 = request.getParameter("optionDesc1");
		String optionDesc2 = request.getParameter("optionDesc2");
		String optionDesc3 = request.getParameter("optionDesc3");
		String optionDesc4 = request.getParameter("optionDesc4");
		String optionDesc5 = request.getParameter("optionDesc5");
		Option option = null;
		switch (optionType) {
			case OptionType.NORMBAL_OPTION:
				option = new NormbalOption(optionDesc1, optionDesc2, optionDesc3, optionDesc4, optionDesc5);
				break;
			case OptionType.IMG_OPTION:
				String imgUrl = request.getParameter("imgUrl");
				option = new ImgOption(optionDesc1, optionDesc2, optionDesc3, optionDesc4, optionDesc5, imgUrl);
				break;
			case OptionType.AUDIO_OPTION:
				String audioUrl = request.getParameter("audioUrl");
				option = new AudioOption(optionDesc1, optionDesc2, optionDesc3, optionDesc4, optionDesc5, audioUrl);
				break;
			case OptionType.VIDEO_OPTION:
				String videoUrl = request.getParameter("videoUrl");
				option = new VideoOption(optionDesc1, optionDesc2, optionDesc3, optionDesc4, optionDesc5, videoUrl);
				break;
			default:
				this.logger.error("选项类型 " + optionType + "不存在");
				return null;
		}
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		result = visitor.addOption(optionType, voteId, option);
		
		System.out.println(result);
		return null;
	}
	
	@RequestMapping("addOptions.do")
	public void addOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = null;
		if (request.getParameter("optionType") == null || request.getParameter("voteId") == null) {
			result = "optionType 或 voteId 为空";
			this.logger.error(result);
		} else {
			int optionType = Integer.parseInt(request.getParameter("optionType"));
			int voteId = Integer.parseInt(request.getParameter("voteId"));
			String options = request.getParameter("options");
			
			List<Option> optionList = OptionDaoFactoryHelper.getOptionList(options, optionType, request.getServletContext().getRealPath("."));
			this.logger.info("添加选项: " + JSON.toJSONString(optionList));
			
			// 调用后台，添加选项
			Visitor visitor = new ProxyVisitor(Priorities.USER);
			result = visitor.addOptions(optionType, voteId, optionList);
		}
		
		// 返回 json
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("getOption.do")
	public String getOption(HttpServletRequest request) {
		String result = null;
		if (request.getParameter("optionType") == null || request.getParameter("optionId") == null) {
			this.logger.error("optionType 或 optionId 为空");
			return result;
		}
		int optionType = Integer.parseInt(request.getParameter("optionType"));
		int optionId= Integer.parseInt(request.getParameter("optionId"));
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		Option option = visitor.getOption(optionType, optionId);
		if (option == null) {
			this.logger.error("选项类型 " + optionType + "; 选项 " + optionId + "不存在");
			return null;
		}
		// 如果是返回json字符串，则option不需要转换为子类，如果是jsp绑定了，则可能需要
		switch (optionType) {
			case OptionType.NORMBAL_OPTION:
				NormbalOption normbalOption = (NormbalOption) option;
				System.out.println(normbalOption.toString());
				break;
			case OptionType.IMG_OPTION:
				ImgOption imgOption = (ImgOption) option;
				System.out.println(imgOption.toString());
				break;
			case OptionType.AUDIO_OPTION:
				AudioOption audioOption = (AudioOption) option;
				System.out.println(audioOption.toString());
				break;
			case OptionType.VIDEO_OPTION:
				VideoOption videoOption = (VideoOption) option;
				System.out.println(videoOption.toString());
				break;
			default:
				this.logger.error("选项类型 " + optionType + "不存在");
				return null;
		}
		return null;
	}
	
	@RequestMapping("getOptions.do")
	public void getOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "success";
		JSONObject message = new JSONObject();
		if (request.getParameter("optionType") == null || request.getParameter("voteId") == null) {
			result = "optionType 或 voteId 为空";
			this.logger.error(result);
		} else {
			int optionType = Integer.parseInt(request.getParameter("optionType"));
			int voteId = Integer.parseInt(request.getParameter("voteId"));
			Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
			List<Option> optionList = visitor.getOptions(optionType, voteId);
			if (optionList == null) {
				result = "选项类型 " + optionType + "; 投票 " + voteId + "不存在选项";
				this.logger.error(result);
			} else {
				message.put("optionList", optionList);
			}
			// 如果是返回json字符串，则option不需要转换为子类，如果是jsp绑定了，则可能需要
			/* switch (optionType) {
				case OptionType.NORMBAL_OPTION:
					List<NormbalOption> normbalOptionList = (List<NormbalOption>)(List<?>)optionList;
					System.out.println(normbalOptionList.toString());
					break;
				case OptionType.IMG_OPTION:
					List<ImgOption> imgOptionList = (List<ImgOption>)(List<?>) optionList;
					System.out.println(imgOptionList.toString());
					break;
				case OptionType.AUDIO_OPTION:
					List<AudioOption> audioOptionList = (List<AudioOption>)(List<?>) optionList;
					System.out.println(audioOptionList.toString());
					break;
				case OptionType.VIDEO_OPTION:
					List<VideoOption> videoOptionList = (List<VideoOption>)(List<?>) optionList;
					System.out.println(videoOptionList.toString());
					break;
				default:
					this.logger.error("选项类型 " + optionType + "不存在");
					return null;
			} */
		}
		
		// 返回 JSON 字符串
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("addPoll.do")
	public void addPoll(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = "success";
		if (session.getAttribute("user") == null) {
			result = "您还未登录";
			this.logger.error(result);
		} else if (request.getParameter("optionType") == null || request.getParameter("voteId") == null || request.getParameter("optionIdList") == null) {
			result = "optionType, voteId 或optionIdList 为空";
			this.logger.error(result);
		} else {
			int optionType = Integer.parseInt(request.getParameter("optionType"));
			int voteId = Integer.parseInt(request.getParameter("voteId"));
			List<Integer> optionIdList = JSON.parseArray(request.getParameter("optionIdList"), Integer.class);
			User user = (User) session.getAttribute("user");
			Visitor visitor = new ProxyVisitor(Priorities.USER);
			Date lastVoteTime = visitor.lastVoteTime(voteId, user.getUserId());
			if (TimeHelper.isSameDay(lastVoteTime, new Date())) {
				result = "今天进已经投过票了，请明天再来！";
			} else {
				result = visitor.addPoll(optionType, optionIdList);
				VoteLog voteLog = new VoteLog(voteId, user.getUserId(), null);
				result = visitor.addVoteLog(voteLog);
			}
		}
		
		// 返回 JSON 字符串
		response.setContentType("application/json; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		JSONObject message = new JSONObject();
		message.put("result", result);
		out.println(message.toJSONString());
	}
	
	@RequestMapping("deleteOption.do")
	public String deleteOption(HttpServletRequest request) {
		String result = null;
		if (request.getParameter("optionType") == null || request.getParameter("optionId") == null) {
			this.logger.error("optionType 或 optionId 为空");
			return result;
		}
		int optionType = Integer.parseInt(request.getParameter("optionType"));
		int optionId = Integer.parseInt(request.getParameter("optionId"));
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		result = visitor.deleteOption(optionType, optionId);
		
		System.out.println(result);
		return null;
	}
	
	@RequestMapping("updateOption.do")
	public String updateOption(HttpServletRequest request) {
		String result = null;
		if (request.getParameter("optionType") == null || request.getParameter("optionId") == null) {
			this.logger.error("optionType 或 optionId 为空");
			return result;
		}
		int optionType = Integer.parseInt(request.getParameter("optionType"));
		int optionId = Integer.parseInt(request.getParameter("optionId"));
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("optionDesc1") != null) {
			map.put("option_desc1", request.getParameter("optionDesc1"));
		}
		if (request.getParameter("optionDesc2") != null) {
			map.put("option_desc2", request.getParameter("optionDesc2"));
		}
		if (request.getParameter("optionDesc3") != null) {
			map.put("option_desc3", request.getParameter("optionDesc3"));
		}
		if (request.getParameter("optionDesc4") != null) {
			map.put("option_desc4", request.getParameter("optionDesc4"));
		}
		if (request.getParameter("optionDesc5") != null) {
			map.put("option_desc5", request.getParameter("optionDesc5"));
		}
		Visitor visitor = new ProxyVisitor(Priorities.USER);
		result = visitor.updateOption(optionType, optionId, map);
		
		System.out.println(result);
		return null;
	}
	
	@RequestMapping("optionFileUpload.do")
	public void optionFileUpload(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String result = null;
		// 返回json字符串
		JSONObject message = new JSONObject();
		
		if (session.getAttribute("user") == null) {
			result = "您还未登录";
			this.logger.error(result);
		} else {
			// 当天日期
			String thisDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			
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
				
				// 构造上传文件路径
				String uploadPath = request.getServletContext().getRealPath(".") + File.separator + TEMP_DIR + 
						File.separator + thisDate.substring(0, 4) + File.separator + thisDate.substring(5, 7) + File.separator + thisDate.substring(8) + File.separator;
				String optionUrl = "/vote/options/temp" + File.separator + thisDate.substring(0, 4) + File.separator + thisDate.substring(5, 7) + File.separator + thisDate.substring(8) + File.separator;
				
				// 目录不存在则创建
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				
				try {
					// 解析请求
		            List<FileItem> formItems = upload.parseRequest(request);
					if (formItems != null && formItems.size() > 0) {
						for (FileItem item : formItems) {
							if (!item.isFormField()) {
								// 文件元素
								String fileName = new File(item.getName()).getName();
								// 使用 uuid 代替文件名
								fileName = UUID.randomUUID().toString().replaceAll("-","") + fileName.substring(fileName.lastIndexOf("."));
								
								// 保存到硬盘
								String filePath = uploadPath + fileName;
								File storeFile = new File(filePath);
								item.write(storeFile);
								
								result = "success";
								message.put("fileUrl", optionUrl + fileName);
								this.logger.info(optionUrl + fileName);
								
								// 只接受一个
								break;
							}
						}
					} else {
						result = "您还未上传文件！";
						this.logger.error(result);
					}
				}catch(Exception e){
					result = e.getMessage();
					this.logger.error(e.getMessage());
				}
			}
		}
		
		// 返回 json字符串
		message.put("result", result);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(message.toJSONString());
	}
}
