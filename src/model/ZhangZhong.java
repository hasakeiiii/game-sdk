package model;

import java.net.URLEncoder;

import com.alipay.config.AlipayConfig;

import net.sf.json.JSONObject;
import util.DateUtil;
import util.Rsa;



public class ZhangZhong implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1896080468727174132L;
	public Integer id;
	
	public String data;
    public String zzdata;
    public String date;
	public String time;
	private String imei;
	private String sid;
	private Integer amount;
	
	
	
	public ZhangZhong()
	{
		date = DateUtil.getDate();
		time = DateUtil.getTime();
	}
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
	public void setZzdata(String zzdata){
		this.zzdata=zzdata;
	}	
	
	public String getZzdata(){
		return this.zzdata;
	}	
	
	public void setData(String data){
		this.data=data;
	}	
	
	public String getData(){
		return this.data;
	}	
	
	public void setDate(String date){
		this.date=date;
	}	
	
	public String getDate(){
		return this.date;
	}	
	
	public void setTime(String time){
		this.time=time;
	}	
	
	public String getTime(){
		return this.time;
	}		
	public void setSid(String sid){
		this.sid=sid;
	}	
	
	public String getSid(){
		return this.sid;
	}	
	public void setAmount(Integer amount){
		this.amount=amount;
	}
	public Integer getAmount(){
		return this.amount;
	}	

}
