package model;
public class ChargeIvr implements java.io.Serializable{
private static final long serialVersionUID = -4003841023456671193L;
private Integer charge_ivr_id;
private String linkid;
private String sid;
private String cid;
private int fee;
private String fee_type;
private String mobile;
private String spnum;
private Integer operator;
private String province;
private String city;
private String beg_time;
private String end_time;
private String date_time;
public Integer getChargeIvrId(){
return charge_ivr_id;
}
public void setChargeIvrId(Integer charge_ivr_id){
this.charge_ivr_id=charge_ivr_id;
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
public String getSpnum(){
return spnum;
}
public void setSpnum(String spnum){
this.spnum=spnum;
}
public Integer getOperator(){
return operator;
}
public void setOperator(Integer operator){
this.operator=operator;
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
public String getBegTime(){
return beg_time;
}
public void setBegTime(String beg_time){
this.beg_time=beg_time;
}
public String getEndTime(){
return end_time;
}
public void setEndTime(String end_time){
this.end_time=end_time;
}
public String getDateTime(){
return date_time;
}
public void setDateTime(String date_time){
this.date_time=date_time;
}
}