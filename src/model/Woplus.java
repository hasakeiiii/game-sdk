package model;


import dao.CooperationDao;
import util.DateUtil;
import net.sf.json.JSONObject;


public class Woplus implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2161366044643189185L;
	public Integer id;
	public String order_id;
	public String status;
	public String msg;
	public String mobile_id;
	public String timestamp;
	
	public static String SUCCESS = "0000";
	
	public Woplus(JSONObject json) {
		order_id = json.getString("orderid");
		timestamp = json.getString("timestamp");
		status = json.getString("status");
		msg = json.getString("msg");
		mobile_id = json.getString("mobileid");		
	}

	public Woplus()
	{
		
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
	public void setOrderId(String order_id){
		this.order_id=order_id;
	}
	
	public String getOrderId(){
		return this.order_id;
	}
	
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus( ){
		return this.status;
	}
	
	public void setMsg(String msg){
		this.msg=msg;
	}
	public String getMsg( ){
		return this.msg;
	}
	
	public void setMobileId(String mobile_id){
		this.mobile_id=mobile_id;
	}
	public String getMobileId( ){
		return this.mobile_id;
	}	
	
	public void setTimestamp(String timestamp){
		this.timestamp=timestamp;
	}
	public String getTimestamp( ){
		return this.timestamp;
	}	
		
}
