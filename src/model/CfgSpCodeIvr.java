package model;
public class CfgSpCodeIvr implements java.io.Serializable{
private static final long serialVersionUID = -6785544669528435456L;
private Integer cfg_sp_code_ivr_id;
private String sid;
private String cid;
private String sp_code;
private Double rate;
private String url;
public Integer getCfgSpCodeIvrId(){
return cfg_sp_code_ivr_id;
}
public void setCfgSpCodeIvrId(Integer cfg_sp_code_ivr_id){
this.cfg_sp_code_ivr_id=cfg_sp_code_ivr_id;
}
public String getSid(){
return sid;
}
public void setSid(String sid){
this.sid=sid;
}
public String getCid(){
return cid;
}
public void setCid(String cid){
this.cid=cid;
}
public String getSpCode(){
return sp_code;
}
public void setSpCode(String sp_code){
this.sp_code=sp_code;
}
public Double getRate() {
	return rate;
}
public void setRate(Double rate) {
	this.rate = rate;
}
public String getUrl(){
return url;
}
public void setUrl(String url){
this.url=url;
}
}