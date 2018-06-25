package model;
public class SysLogs implements java.io.Serializable{
private static final long serialVersionUID = 8555486049825119170L;
private Integer sys_logs_id;
private Integer sys_user_id;
private String ip;
private String act_type;
private String act;
private String date_time;
public Integer getSysLogsId(){
return sys_logs_id;
}
public void setSysLogsId(Integer sys_logs_id){
this.sys_logs_id=sys_logs_id;
}
public Integer getSysUserId(){
return sys_user_id;
}
public void setSysUserId(Integer sys_user_id){
this.sys_user_id=sys_user_id;
}
public String getIp(){
return ip;
}
public void setIp(String ip){
this.ip=ip;
}
public String getActType(){
return act_type;
}
public void setActType(String act_type){
this.act_type=act_type;
}
public String getAct(){
return act;
}
public void setAct(String act){
this.act=act;
}
public String getDateTime(){
return date_time;
}
public void setDateTime(String date_time){
this.date_time=date_time;
}
}