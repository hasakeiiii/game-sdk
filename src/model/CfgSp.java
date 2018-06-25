package model;
public class CfgSp implements java.io.Serializable{
private static final long serialVersionUID = -6157153168647211484L;
private Integer cfg_sp_id;
private String sp_name;
private String sp_code;
public Integer getCfgSpId(){
return cfg_sp_id;
}
public void setCfgSpId(Integer cfg_sp_id){
this.cfg_sp_id=cfg_sp_id;
}
public String getSpName(){
return sp_name;
}
public void setSpName(String sp_name){
this.sp_name=sp_name;
}
public String getSpCode(){
return sp_code;
}
public void setSpCode(String sp_code){
this.sp_code=sp_code;
}
}