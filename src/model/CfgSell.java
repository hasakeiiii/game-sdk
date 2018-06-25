package model;
public class CfgSell implements java.io.Serializable{
private static final long serialVersionUID = 4267425487855832645L;
private Integer cfg_sell_id;
private Integer sys_user_id;
private String name;
private String sell_user;
private String sell_pass;
private String address;
private String contact;
public Integer getCfgSellId(){
return cfg_sell_id;
}
public void setCfgSellId(Integer cfg_sell_id){
this.cfg_sell_id=cfg_sell_id;
}
public Integer getSysUserId(){
return sys_user_id;
}
public void setSysUserId(Integer sys_user_id){
this.sys_user_id=sys_user_id;
}
public String getName(){
return name;
}
public void setName(String name){
this.name=name;
}
public String getSellUser(){
return sell_user;
}
public void setSellUser(String sell_user){
this.sell_user=sell_user;
}
public String getSellPass(){
return sell_pass;
}
public void setSellPass(String sell_pass){
this.sell_pass=sell_pass;
}
public String getAddress(){
return address;
}
public void setAddress(String address){
this.address=address;
}
public String getContact(){
return contact;
}
public void setContact(String contact){
this.contact=contact;
}
}