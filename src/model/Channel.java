package model;

public class Channel implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 322512949372605420L;

	public Integer id;
	public String no;
	public String name;
	public String addr;
	public String contact;
	public Integer login_user;
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
	public void setLoginUser(Integer login_user){
		this.login_user=login_user;
	}
	public Integer getLoginUser( ){
		return this.login_user;
	}
	
	public void setNo(String no){
		this.no=no;
	}
	public String getNo( ){
		return this.no;
	}

	public void setName(String name){
		this.name=name;
	}
	public String getName( ){
		return this.name;
	}	

	public void setAddr(String addr){
		this.addr=addr;
	}
	public String getAddr( ){
		return this.addr;
	}	
	
	public void setContact(String contact){
		this.contact=contact;
	}
	public String getContact( ){
		return this.contact;
	}		
}
