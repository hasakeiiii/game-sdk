package model;
/**
 * @author mzyw_linilq
 * @version 1.0
 * 设备信息数据类
 * 
 */
public class DevicePay {

	public Integer id;
	//private String packet_id;
	private String imei;
	private String imsi;
	private String date;
	
	private Integer amount;
	
	private Integer month_amount;
	private Integer month;
	//private String sid;

	
	public Integer getMonthAmount() {
		return month_amount;
	}
	public void setMonthAmount(Integer month_amount) {
		this.month_amount = month_amount;
	}
	
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	
	
	
}
