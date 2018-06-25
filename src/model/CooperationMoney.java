package model;

public class CooperationMoney implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6411294650310654674L;
	public Integer id;
	public String appPay;
	public String channelNo;
	public String packetId;
	public Integer limitMoney;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppPay() {
		return appPay;
	}
	public void setAppPay(String appPay) {
		this.appPay = appPay;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public Integer getLimitMoney() {
		return limitMoney;
	}
	public void setLimitMoney(Integer limitMoney) {
		this.limitMoney = limitMoney;
	}
	
}
