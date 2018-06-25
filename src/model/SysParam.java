package model;
public class SysParam implements java.io.Serializable{
private static final long serialVersionUID = 2012544467454708230L;
private Integer id;
private String switch_state;
private String begin_time;
private String end_time;
private String switch_time;

private int mm_day_pay;//单用户日限金额
private int mm_month_pay;//单用户月限金额
private int mm_channel_pay;//渠道日限金额

public String filter_region;//屏蔽地区
public String filter_time;//屏蔽时间
public String filter_circle;//屏蔽周期

public static String switch_off = "0";
public static String switch_on = "1";

private String vagueState;
public String vagueAddr;
public String vagueTime;


public String getVagueState() {
	return vagueState;
}
public void setVagueState(String vagueState) {
	this.vagueState = vagueState;
}
public String getVagueAddr() {
	return vagueAddr;
}
public void setVagueAddr(String vagueAddr) {
	this.vagueAddr = vagueAddr;
}
public String getVagueTime() {
	return vagueTime;
}
public void setVagueTime(String vagueTime) {
	this.vagueTime = vagueTime;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}

public void setFilterCircle(String filter_circle){
	this.filter_circle = filter_circle;
}
public String getFilterCircle(){
	return this.filter_circle;
}

public void setFilterTime(String filter_time){
	this.filter_time = filter_time;
}
public String getFilterTime(){
	return this.filter_time;
}

public void setFilterRegion(String filter_region){
	this.filter_region = filter_region;
}
public String getFilterRegion(){
	return this.filter_region;
}

public String getSwitchTime(){
	return switch_time;
}
public void setSwitchTime(String switch_time){
	this.switch_time=switch_time;
}

public String getBeginTime(){
	return begin_time;
	
}
public void setBeginTime(String begin_time){
	this.begin_time=begin_time;
}
public String getEndTime(){
	return end_time;
}
public void setEndTime(String end_time){
	this.end_time=end_time;
}
public String getSwitchState(){
	return switch_state;
}
public void setSwitchState(String switch_state){
	this.switch_state=switch_state;
}

public void setMmChannelPay(Integer mm_channel_pay){
		this.mm_channel_pay = mm_channel_pay;
	}
	public Integer getMmChannelPay(){
		return this.mm_channel_pay;
	}
	
public void setMmMonthPay(Integer mm_month_pay){
		this.mm_month_pay = mm_month_pay;
	}
	public Integer getMmMonthPay(){
		return this.mm_month_pay;
	}
	
public void setMmDayPay(Integer mm_day_pay){
	this.mm_day_pay = mm_day_pay;
}
public Integer getMmDayPay(){
	return this.mm_day_pay;		
}

}