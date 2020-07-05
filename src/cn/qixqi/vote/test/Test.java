package cn.qixqi.vote.test;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import cn.qixqi.vote.entity.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;

public class Test {
	public static void main(String[] args) {
		Option option = new ImgOption("1", "2", "3", "4", "5", "img");
		System.out.println(JSON.toJSONString(option));
		ImgOption imgOption = (ImgOption) option;
		System.out.println(JSON.toJSONString(imgOption));
		List<Option> optionList = new ArrayList<Option>();
		optionList.add(option);
		optionList.add(imgOption);
		System.out.println(JSON.toJSONString(optionList));
		List<ImgOption> imgOptionList = (List<ImgOption>)(List<?>)optionList;
		System.out.println(JSON.toJSONString(optionList));
	}
}
