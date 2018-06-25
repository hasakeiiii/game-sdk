package model;
public class BlackOrder implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1772270770422122445L;
	private Integer id;
	private String order_no;
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setOrderNo(String order_no){
		this.order_no=order_no;
	}
	public String getOrderNo(){
		return this.order_no;
	}	
	
}