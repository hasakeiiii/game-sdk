package model;
public class CfgStat implements java.io.Serializable{
private static final long serialVersionUID = 1368652916681018607L;
private Integer cfg_stat_id;
private String sid;
private String cid;
private String province;
private String msg;
private Integer max_num;
public Integer getCfgStatId(){
return cfg_stat_id;
}
public void setCfgStatId(Integer cfg_stat_id){
this.cfg_stat_id=cfg_stat_id;
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
public String getProvince(){
return province;
}
public void setProvince(String province){
this.province=province;
}
public String getMsg(){
return msg;
}
public void setMsg(String msg){
this.msg=msg;
}
public Integer getMaxNum(){
return max_num;
}
public void setMaxNum(Integer max_num){
this.max_num=max_num;
}
}