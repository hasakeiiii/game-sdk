package model;
public class SysWarn implements java.io.Serializable{
private static final long serialVersionUID = -7077133445675025862L;
private Integer sys_warn_id;
private String keyword;
private Integer kind;
private String address;
public Integer getSysWarnId(){
return sys_warn_id;
}
public void setSysWarnId(Integer sys_warn_id){
this.sys_warn_id=sys_warn_id;
}
public Integer getKind(){
return kind;
}
public void setKind(Integer kind){
this.kind=kind;
}
public String getAddress(){
return address;
}
public void setAddress(String address){
this.address=address;
}
public String getKeyword(){
return keyword;
}
public void setKeyword(String keyword){
this.keyword=keyword;
}
}