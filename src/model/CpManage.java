package model;



public class CpManage implements java.io.Serializable{

	private static final long serialVersionUID = 5747273887337957356L;
	private Integer id;
	private String name;
	private String address;
	private String web;
	private String remarks;
	
	private Integer cp_no;
	private Integer login_user;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getCpNo() {
		return this.cp_no;
	}
	public void setCpNo(Integer cp_no) {
		this.cp_no = cp_no;
	}
	public Integer getLoginUser() {
		return this.login_user;
	}
	public void setLoginUser(Integer login_user) {
		this.login_user = login_user;
	}
	
	
	
}
