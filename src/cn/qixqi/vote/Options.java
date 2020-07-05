package cn.qixqi.vote;

import java.util.Date;
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
		switch (optionType) {
			case OptionType.NORMBAL_OPTION:
				
			case OptionType.IMG_OPTION:
			
			case OptionType.AUDIO_OPTION:
			
			case OptionType.VIDEO_OPTION:
			
			default:
		}
		
		
		
		
		
		return null;
	}
}
