package model;

public class DataBuf implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393162471174034318L;

	public Integer id;
	public String sqlstr;
	public String funname;
	public String date;
	public Integer count;
	public String packet_id;
	
	public void setCount(Integer count){
		this.count=count;
	}
	public Integer getCount(){
		return this.count;
	}
	
	public void setPacketId(String packet_id){
		this.packet_id=packet_id;
	}
	
	public String getPacketId(){
		return this.packet_id;
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}

	public void setFunname(String funname){
		this.funname=funname;
	}
	public String getFunname(){
		return this.funname;
	}
	public void setDate(String date){
		this.date=date;
	}
	public String getDate(){
		return this.date;
	}
	
	
	public void setSqlstr(String sqlstr){
		this.sqlstr=sqlstr;
	}
	public String getSqlstr(){
		return this.sqlstr;
	}
	
	
}
