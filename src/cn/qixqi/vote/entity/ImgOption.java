package cn.qixqi.vote.entity;

public class ImgOption extends Option{
	private String imgUrl;
	
	public ImgOption(){
		
	}
	
	public ImgOption(String optionDesc1, String optoinDesc2, String optionDesc3, String optionDesc4, String optionDesc5,
			String imgUrl) {
		super(optionDesc1, optoinDesc2, optionDesc3, optionDesc4, optionDesc5);
		this.imgUrl = imgUrl;
	}

	public ImgOption(int optionId, String optionDesc1, String optoinDesc2, String optionDesc3, String optionDesc4,
			String optionDesc5, int optionPoll, String imgUrl) {
		super(optionId, optionDesc1, optoinDesc2, optionDesc3, optionDesc4, optionDesc5, optionPoll);
		this.imgUrl = imgUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public String toString() {
		return "ImgOption [imgUrl=" + imgUrl + "]";
	}
}
