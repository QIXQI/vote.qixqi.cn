package cn.qixqi.vote.test;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.io.File;
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
		
		System.out.println("--------------------------------------");
		Object object = null;
		Vote vote = (Vote) object;
		if (vote == null) {
			System.out.println("null");
		} else {
			System.out.println("not null");
		}
		
		
		System.out.println("------------------------------------------");
		System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
		System.out.println(UUID.randomUUID().toString());
		
		System.out.println("---------------------------------------");
		String test = "/vote/images/avatar\\2020\\07\\15\\16.jpg";
		String[] tests = test.split("\\\\");
		if (File.separator.equals("/")) {
			System.out.println("yes");
		}
		if (File.separator.equals("\\")) {
			System.out.println("操");
		}
		for(String str: tests) {
			System.out.println(str);
		}
	}
}
