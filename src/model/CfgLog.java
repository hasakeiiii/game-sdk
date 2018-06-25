package model;
public class CfgLog implements java.io.Serializable{
private static final long serialVersionUID = -6677912612637992793L;
private Integer cfg_log_id;
private Integer mode;
private String param;
private String value;
private String date_time;
public Integer getCfgLogId(){
return cfg_log_id;
}
public void setCfgLogId(Integer cfg_log_id){
this.cfg_log_id=cfg_log_id;
}
public String getParam(){
return param;
}
public void setParam(String param){
this.param=param;
}
public String getValue(){
return value;
}
public void setValue(String value){
this.value=value;
}
public String getDateTime(){
return date_time;
}
public void setDateTime(String date_time){
this.date_time=date_time;
}
public Integer getMode() {
	return mode;
}
public void setMode(Integer mode) {
	this.mode = mode;
}
}