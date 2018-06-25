package model;


import util.DateUtil;
import net.sf.json.JSONObject;


public class Open189 implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2485746413219117796L;
	public Integer id;
	public String status;
	public String out_trade_no;
	public String order_id;
	public Integer fee;
	
	public static String SUCCESS = "00";
	
	public Open189(JSONObject json) {
		status = json.getString("status");
		out_trade_no = json.getString("out_trade_no");
		order_id = json.getString("order_id");
		if(order_id ==null||order_id.equals("")){
			
			order_id = json.getString("order_no");
		}
		fee = json.getInt("fee");
	}
	
	public Open189()
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
	
	public void setOutTradeNo(String out_trade_no){
		this.out_trade_no=out_trade_no;
	}
	
	public String getOutTradeNo(){
		return this.out_trade_no;
	}
	
	public void setFee(Integer fee){
		this.fee=fee;
	}
	public Integer getFee(){
		return this.fee;
	}	
	
	
}
