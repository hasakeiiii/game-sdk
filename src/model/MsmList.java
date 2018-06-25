package model;

import util.DateUtil;

public class  MsmList implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7353417624032110795L;
	public Integer id;
	public String chn;
	public String imei;
	public String imsi;
	public String xexdata;
	public String num;
	public String msg;
    public String code;
    public String date;
	public String time;
	public String addr;
	public String phonenum;
	
	public Integer amount;
	public Integer reqtime;
	
	public void setPhonenum(String phonenum){
		this.phonenum=phonenum;
	}
	public String getPhonenum(){
		return this.phonenum;
	}	
	
	
	public void setAddr(String addr){
		this.addr=addr;
	}
	public String getAddr(){
		return this.addr;
	}	
	
	public void setReqtime(Integer reqtime){
		this.reqtime=reqtime;
	}
	public Integer getReqtime(){
		return this.reqtime;
	}	
	
	public void setAmount(Integer amount){
		this.amount=amount;
	}
	public Integer getAmount(){
		return this.amount;
	}	
	
    public void setCode(String code){
  		this.code = code;
  	}
  	public String getCode(){
  		return this.code;
  	}
  	
    public void setMsg(String msg){
  		this.msg = msg;
  	}
  	public String getMsg(){
  		return this.msg;
  	}
  	
    public void setNum(String num){
  		this.num = num;
  	}
  	public String getNum(){
  		return this.num;
  	}
  	
    public void setXexdata(String xexdata){
		this.xexdata = xexdata;
	}
	public String getXexdata(){
		return this.xexdata;
	}
	
	
    public void setImsi(String imsi){
		this.imsi = imsi;
	}
	public String getImsi(){
		return this.imsi;
	}
	
    public void setImei(String imei){
		this.imei = imei;
	}
	public String getImei(){
		return this.imei;
	}
	
	public void setChn(String chn){
		this.chn = chn;
	}
	public String getChn(){
		return this.chn;
	}
	
	
	
	public MsmList()
	{
		chn = "";
		imei = "";
		imsi = "";
		xexdata = "";
		num = "";
		msg = "";
		code = "";
		
		date = DateUtil.getDate();
		time = DateUtil.getTime();
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
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
	
}
