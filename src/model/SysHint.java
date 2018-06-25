package model;
public class SysHint implements java.io.Serializable{
private static final long serialVersionUID = 6521164851362961261L;
private Integer sys_hint_id;
private String page_name;
private String info;
private Integer ok;
public Integer getSysHintId(){
return sys_hint_id;
}
public void setSysHintId(Integer sys_hint_id){
this.sys_hint_id=sys_hint_id;
}
public String getPageName(){
return page_name;
}
public void setPageName(String page_name){
this.page_name=page_name;
}
public String getInfo(){
return info;
}
public void setInfo(String info){
this.info=info;
}
public Integer getOk(){
return ok;
}
public void setOk(Integer ok){
this.ok=ok;
}
}