package model;

public class Modelip implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7171130357198988147L;
	public Integer id;
	public String ip;
	public String addr;
	
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setAddr(String addr){
		this.addr=addr;
	}
	public String getAddr( ){
		return this.addr;
	}
}

