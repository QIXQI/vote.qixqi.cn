package cn.qixqi.vote.entity;

public class VideoOption extends Option{
	private String videoUrl;

	public VideoOption(int optionId, String optionDesc1, String optoinDesc2, String optionDesc3, String optionDesc4,
			String optionDesc5, int optionPoll, String videoUrl) {
		super(optionId, optionDesc1, optoinDesc2, optionDesc3, optionDesc4, optionDesc5, optionPoll);
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@Override
	public String toString() {
		return "VideoOption [videoUrl=" + videoUrl + "]";
	}
}
