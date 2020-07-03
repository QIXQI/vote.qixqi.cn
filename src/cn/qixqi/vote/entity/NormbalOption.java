package cn.qixqi.vote.entity;

public class NormbalOption extends Option {

	/**
	 * 工厂方法创建
	 */
	public NormbalOption() {
		super();
	}
	
	/**
	 * 查找选项 - 构造函数
	 * @param optionId
	 * @param optionDesc1
	 * @param optoinDesc2
	 * @param optionDesc3
	 * @param optionDesc4
	 * @param optionDesc5
	 * @param optionPoll
	 */
	public NormbalOption(int optionId, String optionDesc1, String optoinDesc2, String optionDesc3, String optionDesc4,
			String optionDesc5, int optionPoll) {
		super(optionId, optionDesc1, optoinDesc2, optionDesc3, optionDesc4, optionDesc5, optionPoll);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 插入选项 - 构造函数
	 * @param optionDesc1
	 * @param optoinDesc2
	 * @param optionDesc3
	 * @param optionDesc4
	 * @param optionDesc5
	 */
	public NormbalOption(String optionDesc1, String optoinDesc2, String optionDesc3, String optionDesc4,
			String optionDesc5) {
		super(optionDesc1, optoinDesc2, optionDesc3, optionDesc4, optionDesc5);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
