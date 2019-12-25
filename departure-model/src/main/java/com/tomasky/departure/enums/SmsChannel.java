package com.tomasky.departure.enums;

public enum SmsChannel {

	/** 营销通道 */
	SEND_TYPE_SALE("营销通道",1) ,
	
	/** 普通通道 */
	SEND_TYPE_AUTO("普通通道",2) ,
	
	/** VIP通道 */
	SEND_TYPE_VIP("VIP通道",3) ;
	
	SmsChannel(String channelName, int channelId){
		this.channelName = channelName;
		this.channelId = channelId;
	}
	
	private String channelName;
	
	private int channelId;

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	


}
