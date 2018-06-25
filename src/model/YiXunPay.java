package model;

import net.sf.json.JSONObject;

public class YiXunPay {
	private Integer id;
	private Integer amount;
	private String dateTime;
	private String status;
	private String orderId;
	private String payType;
	private String payOrderNo;
	
	public YiXunPay(JSONObject json) {
		status = json.getString("orderstatus");
		float price = Float.parseFloat((json.getString("amt")));
		amount = (int)price * 100;
		payOrderNo = json.getString("orderid");
		orderId = json.getString("reqOrderId");
		dateTime = json.getString("orderdate");
		payType = json.getString("paytype");
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayOrderNo() {
		return payOrderNo;
	}
	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}
	
	
}
