package model;


import util.DateUtil;
import net.sf.json.JSONObject;


public class WebGame implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2485746413219117796L;
	public Integer id;
	public String orderid;
	public String partner;
	public String subcid;
	public String mobileNum;
	public String consume;
	public Integer amount;
	public String saveTime;
	public String exdata;
	

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getSubcid() {
		return subcid;
	}

	public void setSubcid(String subcid) {
		this.subcid = subcid;
	}



	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getConsume() {
		return consume;
	}

	public void setConsume(String consume) {
		this.consume = consume;
	}


	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public String getExdata() {
		return exdata;
	}

	public void setExdata(String exdata) {
		this.exdata = exdata;
	}

	public WebGame()
	{
		
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
		
}
