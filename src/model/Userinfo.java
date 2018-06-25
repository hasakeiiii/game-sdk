package model;

import java.util.HashMap;

public class Userinfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3625256260986301010L;
	public Integer id;
	public String username;
	public String pass;
	public String role;
	
	public static String admin="admin";//超级管理员权限
	public static String adminOffline="admin_offline";//超级管理员单机
	public static String adminOnline="admin_online";//超级管理员网游
	public static String operationOnline="oper_online";//网游运营
	public static String operationOffline="oper_offline";//单机运营
	public static String business="BSS";//商务角色
	public static String ceo="GUEST";//总经办角色
	public static String customService = "C_S";//客服
	public static String finance = "finance";
	public static String CPS = "CPS";//CPS渠道
	public static String CPA = "CPA";//CPA渠道
	public static String CPA_R = "CPA_R";//CPA_R渠道
	public static String CP = "CP";//CP游戏开发商
	
	public static java.util.Map getRoleMap()
	{
		java.util.Map roleMap=new HashMap<String,String>();
		roleMap.put(Userinfo.admin, "系统管理员");
		roleMap.put(Userinfo.adminOffline, "单机系统管理员");
		roleMap.put(Userinfo.adminOnline, "网游系统管理员");
		roleMap.put(Userinfo.operationOnline, "网游运营");
		roleMap.put(Userinfo.operationOffline, "单机运营");
		roleMap.put(Userinfo.business, "商务");
		roleMap.put(Userinfo.ceo, "总经办");
		roleMap.put(Userinfo.customService, "客服");
		roleMap.put(Userinfo.finance, "财务");
		roleMap.put(Userinfo.CPS, "CPS渠道");
		roleMap.put(Userinfo.CPA, "CPA渠道");
		roleMap.put(Userinfo.CPA_R, "CPA_R渠道");
		roleMap.put(Userinfo.CP, "游戏开发商");
		return roleMap;
	}
	
	public String getAdmin(){
		return admin;
	}
	public String getAdminOffline(){
		return adminOffline;
	}
	public String getAdminOnline(){
		return adminOnline;
	}
	public String getOperationOnline(){
		return operationOnline;
	}
	public String getOperationOffline(){
		return operationOffline;
	}
	public String getBusiness(){
		return business;
	}
	public String getCeo(){
		return ceo;
	}
	public String getCustomService(){
		return customService;
	}
	public String getFinance(){
		return finance;
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	
	public void setUsername(String username){
		this.username=username;
	}
	public String getUsername(){
		return this.username;
	}
	
	public void setPass(String pass){
		this.pass=pass;
	}	
	public String getPass(){
		return this.pass;
	}	
	
	public void setRole(String role){
		this.role=role;
	}	
	public String getRole(){
		return this.role;
	}
}
