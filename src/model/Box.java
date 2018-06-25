package model;


import util.DateUtil;
import net.sf.json.JSONObject;


public class Box implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5436814194998686056L;
	public Integer id;
	public String cpfee;
	public String cpparam;
	public String status;
	public String date;
	public Integer	amount;
	
	
	public Box(JSONObject json) {
		status = json.getString("status");
		cpfee = json.getString("cpfee");
		cpparam = json.getString("cpparam").substring(5);
		date = DateUtil.getDate();
	}
	
	public Box()
	{
		
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

	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus( ){
		return this.status;
	}

	public String getCpfee() {
		return cpfee;
	}

	public void setCpfee(String cpfee) {
		this.cpfee = cpfee;
	}

	public String getCpparam() {
		return cpparam;
	}

	public void setCpparam(String cpparam) {
		this.cpparam = cpparam;
	}
	
}
