package cn.qixqi.vote;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import cn.qixqi.vote.proxy.Visitor;
import cn.qixqi.vote.proxy.ProxyVisitor;
import cn.qixqi.vote.proxy.Priorities;
import cn.qixqi.vote.factory.OptionType;
import cn.qixqi.vote.entity.Option;
import cn.qixqi.vote.entity.NormbalOption;
import cn.qixqi.vote.entity.ImgOption;
import cn.qixqi.vote.entity.AudioOption;
import cn.qixqi.vote.entity.VideoOption;

@Controller
public class Options {
	private Logger logger = LogManager.getLogger(Options.class.getName());

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
	public String getOptions(HttpServletRequest request) {
		String result = null;
		if (request.getParameter("optionType") == null || request.getParameter("voteId") == null) {
			this.logger.error("optionType 或 voteId 为空");
			return result;
		}
		int optionType = Integer.parseInt(request.getParameter("optionType"));
		int voteId = Integer.parseInt(request.getParameter("voteId"));
		Visitor visitor = new ProxyVisitor(Priorities.VISITOR);
		List<Option> optionList = visitor.getOptions(optionType, voteId);
		if (optionList == null) {
			this.logger.error("选项类型 " + optionType + "; 投票 " + voteId + "不存在选项");
			return null;
		}
		// 如果是返回json字符串，则option不需要转换为子类，如果是jsp绑定了，则可能需要
		switch (optionType) {
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
		}
		
		return null;
	}
	
	@RequestMapping("addPoll.do")
	public String addPoll(HttpServletRequest request) {
		String result = null;
		if (request.getParameter("optionType") == null || request.getParameter("optionId") == null) {
			this.logger.error("optionType 或 optionId 为空");
			return result;
		}
		int optionType = Integer.parseInt(request.getParameter("optionType"));
		int optionId = Integer.parseInt(request.getParameter("optionId"));
		Visitor visitor = new ProxyVisitor(Priorities.THIRD_PARTY_USER);
		result = visitor.addPoll(optionType, optionId);
		
		System.out.println(result);
		return null;
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
}
