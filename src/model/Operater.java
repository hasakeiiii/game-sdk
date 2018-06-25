package model;

public class Operater {
	public Integer id;
	public String no;
	public String name;
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

	
}
