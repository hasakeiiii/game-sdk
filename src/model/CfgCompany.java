package model;
public class CfgCompany implements java.io.Serializable{
private static final long serialVersionUID = 3472298228705916248L;
private Integer cfg_company_id;
private Integer sys_user_id;
private Integer cfg_sell_id;
private String name;
private String cid;
private String cid_user;
private String cid_pass;
private Double rate;
private Double gprs;
private Integer scale;
private Integer scale_anchor;
private Integer open;
private String address;
private String contact;
public Integer getCfgCompanyId(){
return cfg_company_id;
}
public void setCfgCompanyId(Integer cfg_company_id){
this.cfg_company_id=cfg_company_id;
}
public Integer getSysUserId(){
return sys_user_id;
}
public void setSysUserId(Integer sys_user_id){
this.sys_user_id=sys_user_id;
}
public Integer getCfgSellId(){
return cfg_sell_id;
}
public void setCfgSellId(Integer cfg_sell_id){
this.cfg_sell_id=cfg_sell_id;
}
public String getName(){
return name;
}
public void setName(String name){
this.name=name;
}
public String getCid(){
return cid;
}
public void setCid(String cid){
this.cid=cid;
}
public String getCidUser(){
return cid_user;
}
public void setCidUser(String cid_user){
this.cid_user=cid_user;
}
public String getCidPass(){
return cid_pass;
}
public void setCidPass(String cid_pass){
this.cid_pass=cid_pass;
}
public Double getRate(){
return rate;
}
public void setRate(Double rate){
this.rate=rate;
}
public Double getGprs(){
return gprs;
}
public void setGprs(Double gprs){
this.gprs=gprs;
}
public Integer getScale(){
return scale;
}
public void setScale(Integer scale){
this.scale=scale;
}
public Integer getScaleAnchor(){
return scale_anchor;
}
public void setScaleAnchor(Integer scale_anchor){
this.scale_anchor=scale_anchor;
}
public Integer getOpen(){
return open;
}
public void setOpen(Integer open){
this.open=open;
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