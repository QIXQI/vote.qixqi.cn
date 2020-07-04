package cn.qixqi.vote.proxy;

import java.util.List;
import java.util.HashMap;

import cn.qixqi.vote.entity.User;
import cn.qixqi.vote.entity.Vote;
import cn.qixqi.vote.entity.NormbalOption;
import cn.qixqi.vote.entity.ImgOption;
import cn.qixqi.vote.entity.AudioOption;
import cn.qixqi.vote.entity.VideoOption;

public interface Visitor {
	// 用户注册
	public String userRegister(User user);
	
	// 用户登录
	public User userLogin(String key, String password);
	
	// 用户注销
	public String userLogout(int userId);
	
	// 更新头像
	public String updateAvatar(int userId, String userAvatar);
	
	// 更新用户个人信息
	public String updateUserInfo(int userId, HashMap<String, Object> map);
	
	// 发布投票
	public String publishVote(int userId, Vote vote);
	
	// 删除投票
	public String deleteVote(int voteId);
	
	// 获取投票
	public Vote getVote(int voteId);
	
	// 获取用户发布的投票
	public List<Vote> getVotes(int userId);
	
	// 投票添加选项
	public String addOption(int voteId, NormbalOption normbalOption);
	public String addOption(int voteId, ImgOption imgOption);
	public String addOption(int voteId, AudioOption audioOption);
	public String addOption(int voteId, VideoOption videoOption);
	
	// 获取选项
	public NormbalOption getNormbalOption(int optionId);
	public ImgOption getImgOption(int optionId);
	public AudioOption getAudioOption(int optionId);
	public VideoOption getVideoOption(int optionId);
	
	// 获取选项列表
	public List<NormbalOption> getNormbalOptions(int voteId);
	public List<ImgOption> getImgOptions(int voteId);
	public List<AudioOption> getAudioOptions(int voteId);
	public List<VideoOption> getVideoOptions(int voteId);
	
	// 投票
	public String addPoll(int optionId);
	
	// 删除选项
	public String deleteOption(int optionId);
	
	// 更新选项
	public String updateOption(int optionId, HashMap<String, Object> map);
}
