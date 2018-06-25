package model;

public class MmPay implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6285761736456943451L;
	public Integer id;
	public String channel;
	public String app_id;
	public String app_key;
	public String app_code;
	public String trade_no;
	public String order_no;
	public int amount;
	public int ret;
	public String ExData;
	public String packet_id;
	public String MSISDN;
	public String date;
	public String time;
	public Integer notisfy;
	 
	public String TransactionID;
	public String type="";
	public String province;
	public boolean bZhangZhong = false;
	public String imei;
	public String cp_order_no;
	public String pay_no;
	public boolean  bSDK = true;
	
	public void setProvince(String province){
		this.province=province;
	}	
	
	public String getProvince(){
		return this.province;
	}	
	
	public void setNotisfy(Integer notisfy){
		this.notisfy=notisfy;
	}
	public Integer getNotisfy(){
		return this.notisfy;
	}
	
	public void setDate(String date){
		this.date=date;
	}	
	
	public String getDate(){
		return this.date;
	}	
	
	public void setTime(String time){
		this.time=time;
	}	
	
	public String getTime(){
		return this.time;
	}	
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	
	
	public void setChannel(String channel){
		this.channel=channel;
	}
	public String getChannel(){
		return this.channel;
	}	
	
	public void setAppId(String app_id){
		this.app_id=app_id;
	}
	public String getAppId(){
		return this.app_id;
	}
	
	public void setAppKey(String app_key){
		this.app_key=app_key;
	}
	public String getAppKey(){
		return this.app_key;
	}
	
	public void setAppCode(String app_code){
		this.app_code=app_code;
	}
	public String getAppCode(){
		return this.app_code;
	}	
	
	public void setTradeNo(String trade_no){
		this.trade_no=trade_no;
	}
	public String getTradeNo(){
		return this.trade_no;
	}
	
	public void setOrderNo(String order_no){
		this.order_no=order_no;
	}
	public String getOrderNo(){
		return this.order_no;
	}	
	
	public void setAmount(Integer amount){
		this.amount=amount;
	}
	public Integer getAmount(){
		return this.amount;
	}
	
	public void setRet(Integer ret){
		this.ret=ret;
	}
	public Integer getRet(){
		return this.ret;
	}	
}
