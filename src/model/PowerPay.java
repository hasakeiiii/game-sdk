package model;




public class PowerPay implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6427066215004296117L;
	public Integer id;
	public String channelNum;
	public String appId;
	public Integer amount;
	public String orderid;
	
	public String subChannelNum;
	public String imei;
	public String provider;
	public String province;
	public String mobile;
	public String number;
	public String dateTime;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}


	public PowerPay()
	{
		
	}
	
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}

	public String getChannelNum() {
		return channelNum;
	}

	public void setChannelNum(String channelNum) {
		this.channelNum = channelNum;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getSubChannelNum() {
		return subChannelNum;
	}

	public void setSubChannelNum(String subChannelNum) {
		this.subChannelNum = subChannelNum;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}	
	
		
}
