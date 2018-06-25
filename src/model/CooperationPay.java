package model;



public class CooperationPay implements java.io.Serializable{


	private static final long serialVersionUID = 1051114409214549314L;
	/**
	 * 
	 */
	private Integer id;
	private String packet_id;
	private String app_no;
	private String channel_no;
	private String pay_id;
	
	//计费点限额
	private String pay_data;
	private String pay_data_province;
	

	
	
	public String getPayId() {
		return pay_id;
	}
	public void setPayId(String pay_id) {
		this.pay_id = pay_id;
	}
	public String getPayData() {
		return pay_data;
	}
	public void setPayData(String pay_data) {
		this.pay_data = pay_data;
	}
	public String getPayDataProvince() {
		return pay_data_province;
	}
	public void setPayDataProvince(String pay_data_province) {
		this.pay_data_province = pay_data_province;
	}
	
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
	public String getPacketId() {
		return packet_id;
	}
	public void setPacketId(String packet_id) {
		this.packet_id = packet_id;
	}
	
	public String getChannelNo() {
		return channel_no;
	}
	public void setChannelNo(String channel_no) {
		this.channel_no = channel_no;
	}
	public String getAppNo() {
		return app_no;
	}
	public void setAppNo(String app_no) {
		this.app_no = app_no;
	}
	
	
}
