package model;

import sdkReq.GetAmountIndex;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import dao.ActivateDao;
import dao.CooperationDao;
import dao.DeviceInfoDao;

public class ProvinceData implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3170548659695530967L;
	public Integer id;
	public String game_id;
	public String BusinessNo;
	//public String channel;
	public String packet_id;
	public String province;
	public String date;
	public Integer pay;
	public Integer new_acount;
	public Integer login_acount;
	public Integer new_pay;
	public Integer fee_num;
	
	//电信
	public Integer telecom_new_acount;
	public Integer telecom_new_pay;
	public Integer telecom_pay;
	public Integer telecom_fee_num;
	
	//联通
	public Integer unicom_new_acount;
	public Integer unicom_new_pay;
	public Integer unicom_pay;
	public Integer unicom_fee_num;
	

	//移动
	public Integer mobile_new_acount;
	public Integer mobile_new_pay;
	public Integer mobile_pay;
	public Integer mobile_fee_num;
	
	
	//其它
	public Integer other_new_acount;
	public Integer other_new_pay;
	public Integer other_pay;
		
	//新增ARPU
	private String arpu;
	
	//付费占比
	private String payProportion;
		
	//较上一日浮动百分比
	private String floatProportion;
	//新增占比
	private String newAcountProportion;
	

	public String getNewAcountProportion() {
		return newAcountProportion;
	}

	public void setNewAcountProportion(String newAcountProportion) {
		this.newAcountProportion = newAcountProportion;
	}

	public Integer getMobileNewAcount() {
		return mobile_new_acount;
	}

	public void setOtherNewAcount(Integer other_new_acount) {
		this.other_new_acount = other_new_acount;
	}
	
	public Integer getOtherNewAcount() {
		return other_new_acount;
	}
	
	public Integer getOtherNewPay() {
		return other_new_pay;
	}

	public void setOtherNewPay(Integer other_new_pay) {
		this.other_new_pay = other_new_pay;
	}
	
	public Integer getOtherPay() {
		return other_pay;
	}

	public void setOtherPay(Integer other_pay) {
		this.other_pay = other_pay;
	}
	
	public void setMobileNewAcount(Integer mobile_new_acount) {
		this.mobile_new_acount = mobile_new_acount;
	}
	
	public Integer getMobileNewPay() {
		return mobile_new_pay;
	}

	public void setMobileNewPay(Integer mobile_new_pay) {
		this.mobile_new_pay = mobile_new_pay;
	}
	
	public Integer getMobilePay() {
		return mobile_pay;
	}

	public void setMobilePay(Integer mobile_pay) {
		this.mobile_pay = mobile_pay;
	}
	
	public Integer getUnicomNewAcount() {
		return unicom_new_acount;
	}

	public void setUnicomNewAcount(Integer unicom_new_acount) {
		this.unicom_new_acount = unicom_new_acount;
	}
	
	public Integer getUnicomNewPay() {
		return unicom_new_pay;
	}

	public void setUnicomNewPay(Integer unicom_new_pay) {
		this.unicom_new_pay = unicom_new_pay;
	}
	
	public Integer getUnicomPay() {
		return unicom_pay;
	}

	public void setUnicomPay(Integer unicom_pay) {
		this.unicom_pay = unicom_pay;
	}
	
	public void setTelecomNewAcount(Integer telecom_new_acount) {
		this.telecom_new_acount = telecom_new_acount;
	}
	public Integer getTelecomNewAcount() {
		return telecom_new_acount;
	}
	
	public Integer getTelecomNewPay() {
		return telecom_new_pay;
	}

	public void setTelecomNewPay(Integer telecom_new_pay) {
		this.telecom_new_pay = telecom_new_pay;
	}
	
	public Integer getTelecomPay() {
		return telecom_pay;
	}

	public void setTelecomPay(Integer telecom_pay) {
		this.telecom_pay = telecom_pay;
	}
	
	public String getArpu() {
		return arpu;
	}

	public void setArpu(String arpu) {
		this.arpu = arpu;
	}
	
	public String getFloatProportion() {
		return floatProportion;
	}

	public void setFloatProportion(String floatProportion) {
		this.floatProportion = floatProportion;
	}

	public String getPayProportion() {
		return payProportion;
	}


	public void setPayProportion(String payProportion) {
		this.payProportion = payProportion;
	}



	
	

	public Integer getTelecomFeeNum() {
		return telecom_fee_num;
	}

	public void setTelecomFeeNum(Integer telecom_fee_num) {
		this.telecom_fee_num = telecom_fee_num;
	}

	public Integer getUnicomFeeNum() {
		return unicom_fee_num;
	}

	public void setUnicomFeeNum(Integer unicom_fee_num) {
		this.unicom_fee_num = unicom_fee_num;
	}

	public Integer getMobileFeeNum() {
		return mobile_fee_num;
	}

	public void setMobileFeeNum(Integer mobile_fee_num) {
		this.mobile_fee_num = mobile_fee_num;
	}






	Object xlockdata = new Object();
	
	public  ProvinceData()
	{
		date = "";
		pay = 0;
		game_id = "";
		BusinessNo = "";
		//channel = "";
		packet_id = "";
		province = "";
		new_acount = 0;
		fee_num = 0;
		login_acount = 0;
		new_pay = 0;
		//电信
		telecom_new_acount = 0;
		telecom_new_pay = 0;
		telecom_pay = 0;
		telecom_fee_num = 0;
		
		//联通
		unicom_new_acount = 0;
		unicom_new_pay = 0;
		unicom_pay = 0;
		unicom_fee_num = 0;

		//移动
		mobile_new_acount = 0;
		mobile_new_pay = 0;
		mobile_pay = 0;
		mobile_fee_num = 0;
		
		//其它
		other_new_acount = 0;
		other_new_pay = 0;
		other_pay = 0;
	}
	
	
	public void clearData()
	{
		date = null;
		pay = null;
		game_id = null;
		BusinessNo = null;
		//channel = null;
		packet_id = null;
		province = null;
		new_acount = null;
		fee_num = null;
		login_acount = null;
		new_pay = null;
		
		//电信
		telecom_new_acount = null;
		telecom_new_pay = null;
		telecom_pay = null;
		telecom_fee_num = null;
		
		//联通
		unicom_new_acount = null;
		unicom_new_pay = null;
		unicom_pay = null;
		unicom_fee_num = null;

		//移动
		mobile_new_acount = null;
		mobile_new_pay = null;
		mobile_pay = null;
		mobile_fee_num = null;
		
		//其它
		other_new_acount = null;
		other_new_pay = null;
		other_pay = null;
	}
	
	public void FeeNumInc(int count)
	{
		synchronized(xlockdata)
		{
			if(fee_num == null)
			{
				fee_num = 0;
			}
			fee_num += count;
			
		}
	}	
	public void PayFeeNumInc(int count,String payway)
	{
		synchronized(xlockdata)
		{
			if(mobile_fee_num == null)
			{
				mobile_fee_num = 0;
			}
			if(telecom_fee_num == null)
			{
				telecom_fee_num = 0;
			}
			if(unicom_fee_num == null)
			{
				unicom_fee_num = 0;
			}
			if(payway.equals(Pay.MmPayType))
			{
				mobile_fee_num += count;
			}
			else if(payway.equals(Pay.TCPayType))
			{
				telecom_fee_num += count;
			}
			else if(payway.equals(Pay.UniPayType))
			{
				unicom_fee_num += count;
			}
		}
	}	
	
	
	public void NewAcountInc(int count,String payway)
	{
		synchronized(xlockdata)
		{
			if(new_acount == null)
			{
				new_acount = 0;
			}
			if(mobile_new_acount == null)
			{
				mobile_new_acount = 0;
			}
			if(telecom_new_acount == null)
			{
				telecom_new_acount = 0;
			}
			if(unicom_new_acount == null)
			{
				unicom_new_acount = 0;
			}
			if(other_new_acount == null)
			{
				other_new_acount = 0;
			}
			new_acount += count;
			
			if(payway.equals(Pay.MmPayType))
			{
				mobile_new_acount += count;
			}
			else if(payway.equals(Pay.TCPayType))
			{
				telecom_new_acount += count;
			}
			else if(payway.equals(Pay.UniPayType))
			{
				unicom_new_acount += count;
			}
			else 
			{
				other_new_acount += count;
			}
		}
	}	
	
	public void PayInc(int count,String payway)
	{
		synchronized(xlockdata)
		{
			if(pay == null)
			{
				pay = 0;
			}
			if(mobile_pay == null)
			{
				mobile_pay = 0;
			}
			if(telecom_pay == null)
			{
				telecom_pay = 0;
			}
			if(unicom_pay == null)
			{
				unicom_pay = 0;
			}
			if(other_pay == null)
			{
				other_pay = 0;
			}
			pay += count;
			
			if(payway.equals(Pay.MmPayType))
			{
				mobile_pay += count;
			}
			else if(payway.equals(Pay.TCPayType))
			{
				telecom_pay += count;
			}
			else if(payway.equals(Pay.UniPayType))
			{
				unicom_pay += count;
			}
			else 
			{
				other_pay += count;
			}
		}
	}	
	
	public void NewPayInc(int count,String payway)
	{
		synchronized(xlockdata)
		{
			if(new_pay == null)
			{
				new_pay = 0;
			}
			if(mobile_pay == null)
			{
				mobile_new_pay = 0;
			}
			if(telecom_new_pay == null)
			{
				telecom_new_pay = 0;
				
			}
			if(unicom_new_pay == null)
			{
				unicom_new_pay = 0;
			}
			if(other_new_pay == null)
			{
				other_new_pay = 0;
			}
			new_pay += count;
			
			if(payway.equals(Pay.MmPayType))
			{
				mobile_new_pay += count;
			}
			else if(payway.equals(Pay.TCPayType))
			{
				telecom_new_pay += count;
			}
			else if(payway.equals(Pay.UniPayType))
			{
				unicom_new_pay += count;
			}
			else 
			{
				other_new_pay += count;
			}
		}
	}	
	
	public void LoginAcountInc(int count)
	{
		synchronized(xlockdata)
		{
			if(login_acount == null)
			{
				login_acount = 0;
			}
			login_acount += count;
		}
	}	
	
	public void setLoginAcount(Integer login_acount ){
		 this.login_acount = login_acount;
	}	
	
	public Integer getLoginAcount( ){
		 return this.login_acount;
	}	
	
	public void setNewPay(Integer newPay ){
		 this.new_pay = newPay;
	}	
	
	public Integer getNewPay( ){
		 return this.new_pay;
	}	
	
	public void setNewAcount(Integer newAcount ){
		 this.new_acount = newAcount;
	}	
	
	public Integer getNewAcount( ){
		 return this.new_acount;
	}	
	
	public void  setFeeNum(Integer feeNum ){
		 this.fee_num = feeNum;
	}
	
	public Integer getFeeNum( ){
		return this.fee_num;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	public void setPacketId(String packet_id){		
			this.packet_id=packet_id;		
	}
	public String getPacketId( ){
		return this.packet_id;
	}
	
	/*public void setChannel(String channel){
		this.channel=channel;
	}
	public String getChannel( ){
		return this.channel;
	}*/
	
	public void setBusinessNo(String BusinessNo){
		this.BusinessNo=BusinessNo;
	}
	public String getBusinessNo( ){
		return this.BusinessNo;
	}
	
	public void setGameId(String game_id){
		this.game_id=game_id;
	}
	public String getGameId( ){
		return this.game_id;
	}
	
	public void setPay(Integer pay){	
			this.pay=pay;		
	}

	public Integer getPay( ){
		return this.pay;
	}
	
	public void setDate(String date){
		this.date=date;
	}
	
	public String getDate(){
		return this.date;
	}	
	
	
	
}
