package cn.qixqi.vote.entity;

public class AudioOption extends Option{
	private String audioUrl;
	
	public AudioOption(String optionDesc1, String optoinDesc2, String optionDesc3, String optionDesc4,
			String optionDesc5, String audioUrl) {
		super(optionDesc1, optoinDesc2, optionDesc3, optionDesc4, optionDesc5);
		this.audioUrl = audioUrl;
	}

	public AudioOption(int optionId, String optionDesc1, String optoinDesc2, String optionDesc3, String optionDesc4,
			String optionDesc5, int optionPoll, String audioUrl) {
		super(optionId, optionDesc1, optoinDesc2, optionDesc3, optionDesc4, optionDesc5, optionPoll);
		this.audioUrl = audioUrl;
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	@Override
	public String toString() {
		return "AudioOption [audioUrl=" + audioUrl + "]";
	}
}
