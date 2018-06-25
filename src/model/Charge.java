package model;
public class Charge implements java.io.Serializable{
private static final long serialVersionUID = 6965379459333836724L;
private Integer charge_id;
private String linkid;
private String sid;
private String cid;
private int fee;
private String fee_type;
private Integer operator;
private String province;
private String city;
private String mobile;
private String msg;
private String spnum;
private Integer ok;
private String date_time;
public Integer getChargeId(){
return charge_id;
}
public void setChargeId(Integer charge_id){
this.charge_id=charge_id;
}
public String getLinkid(){
return linkid;
}
public void setLinkid(String linkid){
this.linkid=linkid;
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
public Integer getFee(){
return fee;
}
public void setFee(Integer fee){
this.fee=fee;
}
public String getFeeType(){
return fee_type;
}
public void setFeeType(String fee_type){
this.fee_type=fee_type;
}
public String getMobile(){
return mobile;
}
public void setMobile(String mobile){
this.mobile=mobile;
}
public Integer getOperator() {
	return operator;
}
public void setOperator(Integer operator) {
	this.operator = operator;
}
public String getProvince() {
	return province;
}
public void setProvince(String province) {
	this.province = province;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public String getSpnum() {
	return spnum;
}
public void setSpnum(String spnum) {
	this.spnum = spnum;
}
public Integer getOk(){
return ok;
}
public void setOk(Integer ok){
this.ok=ok;
}
public String getDateTime(){
return date_time;
}
public void setDateTime(String date_time){
this.date_time=date_time;
}
}