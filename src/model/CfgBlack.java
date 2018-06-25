package model;
public class CfgBlack implements java.io.Serializable {
private static final long serialVersionUID = -3764769895896241908L;
private Integer cfg_black_id;
private String mobile;
private Integer kind;
public Integer getCfgBlackId(){
return cfg_black_id;
}
public void setCfgBlackId(Integer cfg_black_id){
this.cfg_black_id=cfg_black_id;
}
public String getMobile(){
return mobile;
}
public void setMobile(String mobile){
this.mobile=mobile;
}
public Integer getKind(){
return kind;
}
public void setKind(Integer kind){
this.kind=kind;
}
}