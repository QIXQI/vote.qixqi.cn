package cn.qixqi.vote.test;

import java.util.Date;

import cn.qixqi.vote.entity.*;

public class Test {
	public static void main(String[] args) {
		Option option = new ImgOption("1", "2", "3", "4", "5", "img");
		System.out.println(option.toString());
		ImgOption imgOption = (ImgOption) option;
		 System.out.println(imgOption.toString());
		 System.out.println(imgOption.getImgUrl());
	}
}
