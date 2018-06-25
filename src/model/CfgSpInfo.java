package model;
public class CfgSpInfo implements java.io.Serializable{
private static final long serialVersionUID = 802133936307597665L;
private Integer cfg_sp_info_id;
private String sid;
private String trone;
private String command;
private Integer operator;
private Integer price;
private String province;
private String city;
private String info;
private String replay;
private String max;
public Integer getCfgSpInfoId(){
return cfg_sp_info_id;
}
public void setCfgSpInfoId(Integer cfg_sp_info_id){
this.cfg_sp_info_id=cfg_sp_info_id;
}
public String getTrone(){
return trone;
}
public void setTrone(String trone){
this.trone=trone;
}
public String getCommand(){
return command;
}
public void setCommand(String command){
this.command=command;
}
public Integer getOperator(){
return operator;
}
public void setOperator(Integer operator){
this.operator=operator;
}
public Integer getPrice(){
return price;
}
public void setPrice(Integer price){
this.price=price;
}
public String getProvince(){
return province;
}
public void setProvince(String province){
this.province=province;
}
public String getCity(){
return city;
}
public void setCity(String city){
this.city=city;
}
public String getInfo(){
return info;
}
public void setInfo(String info){
this.info=info;
}
public String getReplay(){
return replay;
}
public void setReplay(String replay){
this.replay=replay;
}
public String getMax(){
return max;
}
public void setMax(String max){
this.max=max;
}
public String getSid() {
	return sid;
}
public void setSid(String sid) {
	this.sid = sid;
}
}