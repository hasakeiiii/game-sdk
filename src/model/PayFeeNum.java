package model;

public class PayFeeNum {
	public Integer id;
	public String packet_id;
	public Integer payNum;
	public Integer payPer;
	public String date;
	
	
	
	
	public Integer getPayNum() {
		return payNum;
	}
	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}
	public Integer getPayPer() {
		return payPer;
	}
	public void setPayPer(Integer payPer) {
		this.payPer = payPer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPacketId() {
		return packet_id;
	}
	public void setPacketId(String packet_id) {
		this.packet_id = packet_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}


	
}
