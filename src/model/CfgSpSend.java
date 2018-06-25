package model;
public class CfgSpSend implements java.io.Serializable{
private static final long serialVersionUID = 7805863020545086251L;
private Integer cfg_sp_send_id;
private String sid;
private String msg;
private String spnum;
private String province;
private String mo;
private String mr;

public Integer getCfgSpSendId(){
return cfg_sp_send_id;
}
public void setCfgSpSendId(Integer cfg_sp_send_id){
this.cfg_sp_send_id=cfg_sp_send_id;
}
public String getSid(){
return sid;
}
public void setSid(String sid){
this.sid=sid;
}
public String getMsg(){
return msg;
}
public void setMsg(String msg){
this.msg=msg;
}
public String getSpnum(){
return spnum;
}
public void setSpnum(String spnum){
this.spnum=spnum;
}
public String getProvince(){
return province;
}           
public void setProvince(String province){
this.province=province;
}
public String getMo(){
return mo;
}           
public void setMo(String mo){
this.mo=mo;
}
public String getMr(){
return mr;
}           
public void setMr(String mr){
this.mr=mr;
}
}