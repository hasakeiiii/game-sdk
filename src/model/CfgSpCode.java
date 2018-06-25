package model;
public class CfgSpCode implements java.io.Serializable{
private static final long serialVersionUID = -5650203454744014882L;
private Integer cfg_sp_code_id;
private String sid;
private String cid;
private String sp_code;
private String province;
private Double rate;
public Integer getCfgSpCodeId(){
return cfg_sp_code_id;
}
public void setCfgSpCodeId(Integer cfg_sp_code_id){
this.cfg_sp_code_id=cfg_sp_code_id;
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
public String getProvince(){
return province;
}           
public void setProvince(String province){
this.province=province;
}
public Double getRate() {
return rate;
}
public void setRate(Double rate) {
this.rate = rate;
}
}