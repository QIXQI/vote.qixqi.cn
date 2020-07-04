package cn.qixqi.vote.entity;

public class Option {
	private int optionId;
	private String optionDesc1;
	private String optoinDesc2;
	private String optionDesc3;
	private String optionDesc4;
	private String optionDesc5;
	private int optionPoll;
	
	public Option() {
		
	}

	
	public Option(String optionDesc1, String optoinDesc2, String optionDesc3, String optionDesc4, String optionDesc5) {
		super();
		this.optionDesc1 = optionDesc1;
		this.optoinDesc2 = optoinDesc2;
		this.optionDesc3 = optionDesc3;
		this.optionDesc4 = optionDesc4;
		this.optionDesc5 = optionDesc5;
	}

	public Option(int optionId, String optionDesc1, String optoinDesc2, String optionDesc3, String optionDesc4,
			String optionDesc5, int optionPoll) {
		super();
		this.optionId = optionId;
		this.optionDesc1 = optionDesc1;
		this.optoinDesc2 = optoinDesc2;
		this.optionDesc3 = optionDesc3;
		this.optionDesc4 = optionDesc4;
		this.optionDesc5 = optionDesc5;
		this.optionPoll = optionPoll;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public String getOptionDesc1() {
		return optionDesc1;
	}

	public void setOptionDesc1(String optionDesc1) {
		this.optionDesc1 = optionDesc1;
	}

	public String getOptoinDesc2() {
		return optoinDesc2;
	}

	public void setOptoinDesc2(String optoinDesc2) {
		this.optoinDesc2 = optoinDesc2;
	}

	public String getOptionDesc3() {
		return optionDesc3;
	}

	public void setOptionDesc3(String optionDesc3) {
		this.optionDesc3 = optionDesc3;
	}

	public String getOptionDesc4() {
		return optionDesc4;
	}

	public void setOptionDesc4(String optionDesc4) {
		this.optionDesc4 = optionDesc4;
	}

	public String getOptionDesc5() {
		return optionDesc5;
	}

	public void setOptionDesc5(String optionDesc5) {
		this.optionDesc5 = optionDesc5;
	}

	public int getOptionPoll() {
		return optionPoll;
	}

	public void setOptionPoll(int optionPoll) {
		this.optionPoll = optionPoll;
	}

	@Override
	public String toString() {
		return "Option [optionId=" + optionId + ", optionDesc1=" + optionDesc1 + ", optoinDesc2=" + optoinDesc2
				+ ", optionDesc3=" + optionDesc3 + ", optionDesc4=" + optionDesc4 + ", optionDesc5=" + optionDesc5
				+ ", optionPoll=" + optionPoll + "]";
	}
}
