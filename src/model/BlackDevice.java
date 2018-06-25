package model;
public class BlackDevice implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7545467374084798482L;
	private Integer id;
	private String device_id;
	
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setDeviceId(String device_id){
		this.device_id=device_id;
	}
	public String getDeviceId( ){
		return this.device_id;
	}
}