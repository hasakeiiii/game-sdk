package model;

public class FilterImei implements java.io.Serializable{
	private static final long serialVersionUID = 2424138548841370019L;
	/**
	 * 
	 */
	public Integer id;
	public String imei;

	
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
}

