package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.NumberUtil;
import util.StringUtil;
import dao.ActivateDao;
import dao.ChannelDataDao;
import dao.CooperationDao;
import dao.LoginDao;
import dao.LoginTempDao;
import dao.PayDao;
import dao.RegisterDao;

public class ChannelData implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2902299304952266026L;

	public String date;
	public Integer aliPay;
	public Integer tenPay;
	public Integer yeePay;
	public Integer mmPay;
	public Integer tcPay;
	public Integer uniPay;
	public Integer mzPay;
	public Integer woPay;
	public Integer openPay;
	public Integer webPay;
	public Integer boxPay;
	public Integer jdPay;
	public Integer ldPay;
	public Integer wxPay;
	public Integer ananPay;
	public Integer powerPay;
	public Integer sentPay;
	//TODO ADDPAYTYPE
	
	public Integer onlyPay;
	public Integer unionPay = 0;
	public Integer newaliPay ;
	public Integer newtenPay;
	public Integer newyeePay;
	public Integer newonlyPay;
	public Integer newmmPay;
	public Integer newtcPay;
	public Integer newuniPay;
	public Integer newunionPay;
	public Integer newmzPay;
	public Integer newwoPay;
	public Integer newopenPay;
	public Integer newwebPay;
	public Integer newboxPay;
	public Integer newjdPay;
	public Integer newldPay;
	public Integer newwxPay;
	public Integer newananPay;
	public Integer newpowerPay;
	public Integer newsentPay;
	//TODO ADDPAYTYPE
	
	public Integer activityNum;
	public Integer activityRegNum;
	public Integer registerNum;	
	public Integer realRegisterNum;
	public Integer day1Num;	//
	public Integer day7Num;	
	public Integer day30Num;
	public Integer loginNum;
	public Integer liveNum;
	//public int mbCalculate;
	
	public Integer msmNum;
	public Integer feeNum;
	
	public Integer reqNum;
	public Integer reqOkNum;
	
	public Integer allPayBefore;
	
	public Integer allRegisterNumBefore;
	public Integer allPayAccoutNumBefore;
	
	
	public Integer dayAllPayAcount;//新用户的付费用户数
	public Integer dayAllAcount;//新用户数
	public Integer pay_times;//支付次数
	//public Integer new_pay_times;//新增支付次数
	public Integer day1_pay_times;//新用户第一天的付费次数	
	public Integer day1_pay_acount;	//新用户第一天的付费用户数	
	
	public Integer settle;
	public Integer settle_aliPay;
	public Integer settle_tenPay;
	public Integer settle_yeePay;
	public Integer settle_mzPay;//2015-05-12
	public Integer settle_mmPay;
	


	public Integer settle_tcPay;
	public Integer settle_uniPay;
	public Integer settle_unionPay;
	public Integer settle_woPay;
	public Integer settle_openPay;
	public Integer settle_webPay;
	public Integer settle_boxPay;
	public Integer settle_onlyPay;
	public Integer settle_jdPay;
	public Integer settle_ldPay;
	public Integer settle_wxPay;
	public Integer settle_ananPay;
	public Integer settle_powerPay;
	public Integer settle_sentPay;
	//TODO ADDPAYTYPE
	
	public Integer day1Pay;	
	public Integer day2Pay;	
	public Integer day3Pay;
	public Integer day4Pay;	
	public Integer day5Pay;	
	public Integer day6Pay;	
	public Integer day7Pay;	
	
	public String game_id;
	public String BusinessNo;
	public String channel;
	public String packet_id;
	public Integer id;
	
	public static int SETTLE_SHLD=10*100;
	Object xlockdata = new Object();

	
	
	public  ChannelData()
	{
		date = "";
		aliPay = 0;
		tenPay = 0;
		yeePay = 0;
		mmPay = 0;
		tcPay = 0;
		uniPay = 0;
		unionPay = 0;
		mzPay = 0;//2015-05-12
		onlyPay = 0;
		woPay = 0;
		openPay = 0;
		webPay = 0;
		boxPay = 0;
		ldPay = 0;
		jdPay = 0;
		wxPay = 0;
		ananPay = 0;
		powerPay = 0;
		sentPay = 0;
		//TODO ADDPAYTYPE
		newaliPay = 0;
		newtenPay = 0;
		newyeePay = 0;
		newmmPay = 0;
		newtcPay = 0;
		newuniPay = 0;
		newmzPay = 0;//2015-05-12
		newonlyPay = 0;
		newunionPay = 0;
		newwoPay = 0;
		newopenPay = 0;
		newwebPay = 0;
		newboxPay = 0;
		newjdPay = 0;
		newldPay = 0;
		newwxPay = 0;
		newananPay = 0;
		newpowerPay = 0;
		newsentPay = 0;
		//TODO ADDPAYTYPE
		
		activityNum = 0;
		activityRegNum = 0;
		registerNum = 0;	
		realRegisterNum = 0;
		day1Num = 0;	//
		day7Num = 0;	
		day30Num = 0;
		loginNum = 0;
		liveNum = 0;
		//public int mbCalculate;
		
		allPayBefore = 0;
		
		allRegisterNumBefore = 0;
		allPayAccoutNumBefore = 0;
		
		dayAllPayAcount = 0;
		dayAllAcount = 0;
		
		settle = 0;
		settle_aliPay = 0;
		settle_tenPay = 0;
		settle_yeePay = 0;
		settle_mmPay = 0;
		settle_tcPay = 0;
		settle_uniPay = 0;
		settle_unionPay = 0;
		settle_mzPay = 0;//2015-05-12
		settle_onlyPay = 0;
		settle_woPay = 0;
		settle_openPay = 0;
		settle_webPay = 0;
		settle_boxPay = 0;
		settle_jdPay = 0;
		settle_ldPay = 0;
		settle_wxPay = 0;
		settle_ananPay = 0;
		settle_powerPay = 0;
		settle_sentPay = 0;
		//TODO ADDPAYTYPE
		//msmNum = 0;
		//feeNum = 0;
		
		msmNum= 0;
		feeNum= 0;
		
		reqNum= 0;
		reqOkNum= 0;
		
		day1Pay = 0;	
		day2Pay = 0;	
		day3Pay = 0;
		day4Pay = 0;	
		day5Pay = 0;	
		day6Pay = 0;	
		day7Pay = 0;	
		
		game_id = "";
		BusinessNo = "";
		channel = "";
		packet_id = "";
		
		pay_times = 0;//支付次数
		day1_pay_times = 0;//新用户第一天的付费次数	
		day1_pay_acount = 0;	//新用户第一天的付费用户数	
		
		
	}
	
	
	
	public void clearData()
	{
	
		date = null;
		aliPay = null;
		tenPay = null;
		yeePay = null;
		mmPay = null;
		tcPay = null;
		uniPay = null;
		mzPay = null;//2015-05-12
		onlyPay = null;
		unionPay = null;
		woPay = null;
		openPay = null;
		webPay = null;
		boxPay = null;
		jdPay = null;
		ldPay = null;
		wxPay = null;
		ananPay = null;
		powerPay = null;
		sentPay = null;
		newaliPay = null;
		newtenPay = null;
		newyeePay = null;
		newmmPay = null;
		newtcPay = null;
		newuniPay = null;
		newunionPay = null;
		newmzPay = null;
		newonlyPay = null;
		newwoPay = null;
		newopenPay = null;
		newwebPay = null;
		newboxPay = null;
		newjdPay = null;
		newldPay = null;
		newwxPay = null;
		newananPay = null;
		newpowerPay = null;
		newsentPay = null;
		//TODO ADDPAYTYPE
		
		activityNum = null;
		activityRegNum = null;
		registerNum = null;	
		realRegisterNum = null;
		day1Num = null;	//
		day7Num = null;	
		day30Num = null;
		loginNum = null;
		liveNum = null;
		//public int mbCalculate;
		
		allPayBefore = null;
		
		allRegisterNumBefore = null;
		allPayAccoutNumBefore = null;
		
		dayAllPayAcount = null;
		dayAllAcount = null;
		
		settle = null;
		settle_aliPay = null;
		settle_tenPay = null;
		settle_yeePay = null;
		settle_mmPay = null;
		settle_tcPay = null;
		settle_uniPay = null;
		settle_unionPay = null;
		settle_mzPay = null;
		settle_onlyPay = null;
		settle_woPay = null;
		settle_openPay = null;
		settle_webPay = null;
		settle_boxPay = null;
		settle_jdPay = null;
		settle_ldPay = null;
		settle_wxPay = null;
		settle_ananPay = null;
		settle_powerPay = null;
		settle_sentPay = null;
		//TODO ADDPAYTYPE
		msmNum = null;
		feeNum  = null;
		reqNum= null;
		reqOkNum = null;
		
		day1Pay = null;	
		day2Pay = null;	
		day3Pay = null;
		day4Pay  = null;	
		day5Pay  = null;	
		day6Pay = null;	
		day7Pay = null;
		
		game_id = null;
		BusinessNo = null;
		channel = null;
		packet_id = null;
		
		pay_times  = null;//支付次数
		day1_pay_times   = null;//新用户第一天的付费次数	
		day1_pay_acount  = null;//新用户第一天的付费用户数	
	}
	
	
	
	
	public Integer getSentPay() {
		return sentPay;
	}



	public void setSentPay(Integer sentPay) {
		this.sentPay = sentPay;
	}



	public Integer getNewsentPay() {
		return newsentPay;
	}



	public void setNewsentPay(Integer newsentPay) {
		this.newsentPay = newsentPay;
	}



	public Integer getSettleSentPay() {
		return settle_sentPay;
	}



	public void setSettleSentPay(Integer settle_sentPay) {
		this.settle_sentPay = settle_sentPay;
	}



	public Integer getPowerPay() {
		return powerPay;
	}



	public void setPowerPay(Integer powerPay) {
		this.powerPay = powerPay;
	}



	public Integer getNewpowerPay() {
		return newpowerPay;
	}



	public void setNewpowerPay(Integer newpowerPay) {
		this.newpowerPay = newpowerPay;
	}



	public Integer getSettlePowerPay() {
		return settle_powerPay;
	}



	public void setSettlePowerPay(Integer settle_powerPay) {
		this.settle_powerPay = settle_powerPay;
	}



	public Integer getAnanPay() {
		return ananPay;
	}



	public void setAnanPay(Integer ananPay) {
		this.ananPay = ananPay;
	}



	public Integer getNewananPay() {
		return newananPay;
	}



	public void setNewananPay(Integer newananPay) {
		this.newananPay = newananPay;
	}



	public Integer getSettleAnanPay() {
		return settle_ananPay;
	}



	public void setSettleAnanPay(Integer settle_ananPay) {
		this.settle_ananPay = settle_ananPay;
	}



	public Integer getJdPay() {
		return jdPay;
	}



	public void setJdPay(Integer jdPay) {
		this.jdPay = jdPay;
	}


	public Integer getWxPay() {
		return wxPay;
	}



	public void setWxPay(Integer wxPay) {
		this.wxPay = wxPay;
	}

	public Integer getNewwxPay() {
		return newwxPay;
	}



	public void setNewwxPay(Integer newwxPay) {
		this.newwxPay = newwxPay;
	}



	public Integer getSettleWxPay() {
		return settle_wxPay;
	}



	public void setSettleWxPay(Integer settle_wxPay) {
		this.settle_wxPay = settle_wxPay;
	}

	
	public Integer getLdPay() {
		return ldPay;
	}



	public void setLdPay(Integer ldPay) {
		this.ldPay = ldPay;
	}



	public Integer getNewjdPay() {
		return newjdPay;
	}



	public void setNewjdPay(Integer newjdPay) {
		this.newjdPay = newjdPay;
	}



	public Integer getNewldPay() {
		return newldPay;
	}



	public void setNewldPay(Integer newldPay) {
		this.newldPay = newldPay;
	}



	public Integer getSettleJdPay() {
		return settle_jdPay;
	}



	public void setSettleJdPay(Integer settle_jdPay) {
		this.settle_jdPay = settle_jdPay;
	}



	public Integer getSettleLdPay() {
		return settle_ldPay;
	}



	public void setSettleLdPay(Integer settle_ldPay) {
		this.settle_ldPay = settle_ldPay;
	}



	public Integer getBoxPay() {
		return boxPay;
	}



	public void setBoxPay(Integer boxPay) {
		this.boxPay = boxPay;
	}



	public Integer getNewboxPay() {
		return newboxPay;
	}



	public void setNewboxPay(Integer newboxPay) {
		this.newboxPay = newboxPay;
	}



	public Integer getSettleBoxPay() {
		return settle_boxPay;
	}



	public void setSettleBoxPay(Integer settle_boxPay) {
		this.settle_boxPay = settle_boxPay;
	}



	public Integer getWebPay() {
		return webPay;
	}
	public void setWebPay(Integer webPay) {
		this.webPay = webPay;
	}
	public Integer getNewwebPay() {
		return newwebPay;
	}
	public void setNewwebPay(Integer newwebPay) {
		this.newwebPay = newwebPay;
	}
	public Integer getSettleWebPay() {
		return settle_webPay;
	}
	public void setSettleWebPay(Integer settle_webPay) {
		this.settle_webPay = settle_webPay;
	}


	 public void setDay1PayAcount(Integer day1_pay_acount){
			
			this.day1_pay_acount=day1_pay_acount;
		
	 }
	 public Integer getDay1PayAcount( ){
		   return this.day1_pay_acount;
	}
	     
    public void setDay1PayTimes(Integer day1_pay_times){
		
		this.day1_pay_times=day1_pay_times;
	
     }
     public Integer getDay1PayTimes( ){
	   return this.day1_pay_times;
     }
     
     
	public void setPayTimes(Integer payTimes){
		
		this.pay_times=payTimes;
	
     }
     public Integer getPayTimes( ){
	   return this.pay_times;
     }

	public void setSettleUnionPay(Integer settle_unionPay){
	
			this.settle_unionPay=settle_unionPay;
		
	}
	public void setSettleUnionPay2(Integer settle_unionPay){
		synchronized(this.settle_unionPay)
		{
			this.settle_unionPay=settle_unionPay;
		}
	}
	public Integer getSettleUnionPay( ){
		return this.settle_unionPay;
	}
	
	public void setSettleMmPay(Integer settle_mmPay){
		
			this.settle_mmPay=settle_mmPay;
		
	}
	
	public void setSettleMmPay2(Integer settle_mmPay){
		synchronized(this.settle_mmPay)
		{
			this.settle_mmPay=settle_mmPay;
		}
	}
    public void setSettleTcPay(Integer settle_tcPay){
		
		this.settle_tcPay=settle_tcPay;
	
    }
   
    public void setSettleWoPay(Integer settle_woPay){
		
		this.settle_woPay=settle_woPay;
	
    }
    public void setSettleOpenPay(Integer settle_openPay){
		
		this.settle_openPay=settle_openPay;
	
    }    
    public void setSettleTcPay2(Integer settle_tcPay){
		synchronized(this.settle_tcPay)
		{
			this.settle_tcPay=settle_tcPay;
		}
	}
    public void setSettleUniPay(Integer settle_uniPay){
		
  		this.settle_uniPay=settle_uniPay;
  	
      }
      public void setSettleuniPay2(Integer settle_uniPay){
  		synchronized(this.settle_uniPay)
  		{
  			this.settle_uniPay=settle_uniPay;
  		}
  	}
      public Integer getNewmzPay() {
  		return newmzPay;
  	}
  	public void setNewmzPay(Integer newmzPay) {
  		this.newmzPay = newmzPay;
  	}
  	public Integer getMzPay() {
  		return mzPay;
  	}
  	public void setMzPay(Integer mzPay) {
  		this.mzPay = mzPay;
  	}
  	public Integer getSettleMzPay() {
  		return settle_mzPay;
  	}
  	public void setSettleMzPay(Integer settle_mzPay) {
  		this.settle_mzPay = settle_mzPay;
  	}
	public Integer getSettleMmPay( ){
		return this.settle_mmPay;
	}
	public Integer getSettleTcPay( ){
		return this.settle_tcPay;
	}
	public Integer getSettleWoPay( ){
		return this.settle_woPay;
	}
	public Integer getSettleOpenPay( ){
		return this.settle_openPay;
	}
	public Integer getSettleUniPay( ){
		return this.settle_uniPay;
	}
	public Integer getOnlyPay() {
		return onlyPay;
	}
	public void setOnlyPay(Integer onlyPay) {
		this.onlyPay = onlyPay;
	}
	public Integer getNewonlyPay() {
		return newonlyPay;
	}
	public void setNewonlyPay(Integer newonlyPay) {
		this.newonlyPay = newonlyPay;
	}
	public Integer getSettleOnlyPay() {
		return settle_onlyPay;
	}
	public void setSettleOnlyPay(Integer settle_onlyPay) {
		this.settle_onlyPay = settle_onlyPay;
	}
	public void setSettleYeePay(Integer settle_yeePay){
	
			this.settle_yeePay=settle_yeePay;
		
	}
	public void setSettleYeePay2(Integer settle_yeePay){
		synchronized(this.settle_yeePay)
		{
			this.settle_yeePay=settle_yeePay;
		}
	}
	public Integer getSettleYeePay( ){
		return this.settle_yeePay;
	}
	
	public void setSettleTenPay(Integer settle_tenPay){
	
			this.settle_tenPay=settle_tenPay;
		
	}
	public void setSettleTenPay2(Integer settle_tenPay){
		synchronized(this.settle_tenPay)
		{
			this.settle_tenPay=settle_tenPay;
		}
	}
	public Integer getSettleTenPay( ){
		return this.settle_tenPay;
	}
	            
	public void setSettleAliPay(Integer settle_aliPay){
	
			this.settle_aliPay=settle_aliPay;
		
	}
	public void setSettleAliPay2(Integer settle_aliPay){
		synchronized(this.settle_aliPay)
		{
			this.settle_aliPay=settle_aliPay;
		}
	}
	public Integer getSettleAliPay( ){
		return this.settle_aliPay;
	}
	
	//1.激活，当天统计激活数activityNum加1，CPA结算值settle；
	//2。注册，如果设备第一次注册，当天数据注册数registerNum加1，allRegisterNumBefore加1,CPA_R结算值settle；,激活日期数据activityRegNum加1
	//3。登录，如果当天第一次登录，当天loginNum加上，如果是次留，激活日期数据day1Num则加1
	//4。支付，当天数据相对类型aliPay累加，allPayBefore累加,CPS结算值settle；，如果用户第一次付费，allPayAccoutNumBefore加1，激活日期数据相对类型newaliPay累加，
	//5.查询，按渠道找，如果表没有，则计算，然后填入表。
	//6.退出数据提义，升级和在线时长,激活日期数据realregisterNum加1
	
	/*public void setChannel(String channel){
		this.channel=channel;
	}*/
	
	/*public void Pay(Integer newuionPay)
	{
		if(obj.pay_type.equals(Pay.AliPayType))
		{
			channelData.aliPay += obj.amount;;
		}
		else if(obj.pay_type.equals(Pay.MmPayType))
		{
			channelData.mmPay += obj.amount;;
		}
		else if(obj.pay_type.equals(Pay.TenPayType))
		{
			channelData.tenPay += obj.amount;;
		}
		else if(obj.pay_type.equals(Pay.UnionPayType))
		{
			channelData.unionPay += obj.amount;;
		}
		else if(obj.pay_type.equals(Pay.YeePayType))
		{
			channelData.yeePay += obj.amount;;
		}
	}*/
	
	/*public void get_settle()
	{
		CooperationDao cooDao = new CooperationDao();
		Cooperation cooperation= cooDao.getRecord(packet_id);
		if(cooperation != null)
		{
		   if(cooperation.settle_type.equals("CPA"))
		   {
			   activityNum = settle;
		   }
		   else if(cooperation.settle_type.equals("CPA_R"))
		   {
			   registerNum = settle;
		   }
		   else if(cooperation.settle_type.equals("CPS"))
		   {
			   aliPay = settle_aliPay;
			   tenPay= settle_tenPay;
			   yeePay = settle_yeePay;
			   mmPay = settle_mmPay;
			   unionPay = settle_unionPay;
		   }
		}
		
	}*/
	
	public void activityNumInc(int count)
	{
		synchronized(activityNum)
		{
			activityNum += count;
		}
	}
	public void registerNumInc(int count)
	{
		synchronized(registerNum)
		{
			registerNum += count;
		}
	}
	public void allRegisterNumBeforeInc(int count)
	{
		synchronized(allRegisterNumBefore)
		{
			allRegisterNumBefore += count;
		}
	}
	
	public void dayAllPayAcountInc(int count)
	{
		synchronized(dayAllPayAcount)
		{
			dayAllPayAcount += count;
		}
	}
	
	public void dayAllAcountInc(int count)
	{
		synchronized(dayAllAcount)
		{
			dayAllAcount += count;
		}
	}
	
	
	public void activityRegNumInc(int count)
	{
		synchronized(activityRegNum)
		{
			activityRegNum += count;
		}
	}
	public void loginNumInc(int count)
	{
		synchronized(loginNum)
		{
			loginNum += count;
		}
	}
	public void day1NumInc(int count)
	{
		synchronized(day1Num)
		{
			day1Num += count;
		}
	}
	public void day7NumInc(int count)
	{
		synchronized(day7Num)
		{
			day7Num += count;
		}
	}	
	public void day30NumInc(int count)
	{
		synchronized(day30Num)
		{
			day30Num += count;
		}
	}	
	
	public void aliPayInc(int count)
	{
		synchronized(aliPay)
		{
			aliPay += count;
		}
	}	
	public void mmPayInc(int count)
	{
		synchronized(mmPay)
		{
			mmPay += count;
		}
	}	
	
	
	public void mmPaySettleInc(int count)
	{
		synchronized(settle_mmPay)
		{
			settle_mmPay += count;
		}
	}	
	public void tcPaySettleInc(int count)
	{
		synchronized(settle_tcPay)
		{
			settle_tcPay += count;
		}
	}	
	public void tcPayInc(int count)
	{
		synchronized(tcPay)
		{
			tcPay += count;
		}
	}	
	public void uniPayInc(int count)
	{
		synchronized(uniPay)
		{
			uniPay += count;
		}
	}	
	public void tenPayInc(int count)
	{
		synchronized(tenPay)
		{
			tenPay += count;
		}
	}	
	public void woPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(woPay == null)
			{
				woPay = 0;
			}
			woPay += count;
		}
	}	
	
	public void openPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(openPay == null)
			{
				openPay = 0;
			}
			openPay += count;
		}
	}	
	public void webPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(webPay == null)
			{
				webPay = 0;
			}
			webPay += count;
		}
	}	
	public void boxPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(boxPay == null)
			{
				boxPay = 0;
			}
			boxPay += count;
		}
	}	
	public void ldPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(ldPay == null)
			{
				ldPay = 0;
			}
			ldPay += count;
		}
	}	
	public void jdPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(jdPay == null)
			{
				jdPay = 0;
			}
			jdPay += count;
		}
	}	
	
	public void unionPayInc(int count)
	{
		synchronized(unionPay)
		{
			unionPay += count;
		}
	}	
	public void yeePayInc(int count)
	{
		synchronized(yeePay)
		{
			yeePay += count;
		}
	}	
	//2015-05-12
	public void mzPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if (mzPay==null) {
				mzPay = 0;
			}
			mzPay += count;
		}
	}
	public void wxPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if (wxPay==null) {
				wxPay = 0;
			}
			wxPay += count;
		}
	}
	public void ananPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if (ananPay==null) {
				ananPay = 0;
			}
			ananPay += count;
		}
	}
	public void powerPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if (powerPay==null) {
				powerPay = 0;
			}
			powerPay += count;
		}
	}
	public void sentPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if (sentPay==null) {
				sentPay = 0;
			}
			sentPay += count;
		}
	}
	public void onlyPayInc(int count)
	{
		synchronized(onlyPay)
		{
			onlyPay += count;
		}
	}
	/**
	 * 加入历史总计消费
	 * @param count
	 */
	public void allPayBeforeInc(int count)
	{
		synchronized(allPayBefore)
		{
			allPayBefore += count;
		}
	}	
	
	public void allPayAccoutNumBeforeInc(int count)
	{
		synchronized(allPayAccoutNumBefore)
		{
			allPayAccoutNumBefore += count;
		}
	}		
	public void newaliPayInc(int count)
	{
		synchronized(newaliPay)
		{
			newaliPay += count;
		}
	}
	public void newwoPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(newwoPay == null)
			{
				newwoPay = 0;
			}
			newwoPay += count;
		}
	}
	public void newopenPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(newopenPay == null)
			{
				newopenPay = 0;
			}
			newopenPay += count;
		}
	}
	public void newwebPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(newwebPay == null)
			{
				newwebPay = 0;
			}
			newwebPay += count;
		}
	}
	public void newboxPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(newboxPay == null)
			{
				newboxPay = 0;
			}
			newboxPay += count;
		}
	}
	public void newldPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(newldPay == null)
			{
				newldPay = 0;
			}
			newldPay += count;
		}
	}
	public void newjdPayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(newjdPay == null)
			{
				newjdPay = 0;
			}
			newjdPay += count;
		}
	}

	public void newmmPayInc(int count)
	{
		synchronized(newmmPay)
		{
			newmmPay += count;
		}
	}	
	public void newtcPayInc(int count)
	{
		synchronized(newtcPay)
		{
			newtcPay += count;
		}
	}	
	public void newuniPayInc(int count)
	{
		synchronized(newuniPay)
		{
			newuniPay += count;
		}
	}	
	public void newtenPayInc(int count)
	{
		synchronized(newtenPay)
		{
			newtenPay += count;
		}
	}	
	public void newunionPayInc(int count)
	{
		synchronized(newunionPay)
		{
			newunionPay += count;
		}
	}			
	
	public void newyeePayInc(int count)
	{
		synchronized(newyeePay)
		{
			newyeePay += count;
		}
	}
	//***********2015-07-17
	public void newmzPayInc(Integer count)
	{
		synchronized(xlockdata)
		{
			if(newmzPay == null)
			{
				newmzPay = 0;
			}
			newmzPay += count;
		}
	}
	public void newonlyPayInc(Integer count)
	{
		synchronized(xlockdata)
		{
			if(newonlyPay == null)
			{
				newonlyPay = 0;
			}
			newonlyPay += count;
		}
		
	}
	public void newwxPayInc(Integer count)
	{
		synchronized(xlockdata)
		{
			if(newwxPay == null)
			{
				newwxPay = 0;
			}
			newwxPay += count;
		}
		
	}
	public void newananPayInc(Integer count)
	{
		synchronized(xlockdata)
		{
			if(newananPay == null)
			{
				newananPay = 0;
			}
			newananPay += count;
		}
		
	}
	public void newsentPayInc(Integer count)
	{
		synchronized(xlockdata)
		{
			if(newsentPay == null)
			{
				newsentPay = 0;
			}
			newsentPay += count;
		}
		
	}
	public void newpowerPayInc(Integer count)
	{
		synchronized(xlockdata)
		{
			if(newpowerPay == null)
			{
				newpowerPay = 0;
			}
			newpowerPay += count;
		}
		
	}
	//***********2015-07-17
	public void realRegisterNumInc(int count)
	{
		synchronized(realRegisterNum)
		{
			realRegisterNum += count;
		}
	}		
	/**
	 * 按扣量计算对应的所有settle_XXXpay
	 * @param channelData
	 */
	public void settle(ChannelData channelData)
	{
		CooperationDao cooDao = new CooperationDao();
		Cooperation cooperation= cooDao.getRecord(packet_id);
		int ration = 0;
		if(cooperation != null)
		{
			ration = cooperation.getSettleRatio();
			String settle_type = cooperation.settle_type;
			if(StringUtil.is_nullString(settle_type))
			{
				return;
			}
		   if(settle_type.equals("CPA"))
		   {
			   //DebuUtil.log("CPA结算:activityNum="+activityNum);
			   synchronized(settle)
			   {
		          settle = activityNum*ration/100;
			   }
		      channelData.settle = settle;
		      //DebuUtil.log("CPA结算:settle="+settle);
		   }
		   else if(settle_type.equals("CPA_R"))
		   {
			   DebuUtil.log("CPA_R结算:registerNum="+registerNum);
			   synchronized(settle)
			   {
				   settle = registerNum*ration/100;
			   }
			   channelData.settle = settle;
			   //DebuUtil.log("CPA_R结算:settle="+settle);
		   }
		   else if(settle_type.equals("CPS"))
		   {
			   DebuUtil.log("CPS结算:");
			   synchronized(xlockdata)
			   {
				   channelData.settle_aliPay = settlePay(aliPay, ration);
				   channelData.settle_tenPay = settlePay(tenPay, ration);
				   channelData.settle_yeePay = settlePay(yeePay, ration);
				   channelData.settle_unionPay = settlePay(unionPay, ration);
				   channelData.settle_mzPay = settlePay(mzPay, ration);
				   channelData.settle_onlyPay = settlePay(onlyPay, ration);
				   channelData.settle_mmPay = settlePay(mmPay, ration);
				   channelData.settle_tcPay = settlePay(tcPay, ration);
				   channelData.settle_uniPay = settlePay(uniPay, ration);
				   channelData.settle_woPay = settlePay(woPay, ration);
				   channelData.settle_openPay = settlePay(openPay, ration);
				   channelData.settle_webPay = settlePay(webPay, ration);
				   channelData.settle_boxPay = settlePay(boxPay, ration);
				   channelData.settle_ldPay = settlePay(ldPay, ration);
				   channelData.settle_jdPay = settlePay(jdPay, ration);
				   channelData.settle_wxPay = settlePay(wxPay, ration);
				   DebuUtil.log("CPS结算结束");
			   }
			  /* synchronized(settle_mmPay)
			   {
				   //DebuUtil.log("mmPay:"+mmPay);
				   if(mmPay >= SETTLE_SHLD)
				   {
				      //settle_mmPay = ((mmPay*ration/100)/100)*100;
					   settle_mmPay = ((mmPay*ration)/100);
				   }
				   else
				   {
					   settle_mmPay = mmPay;
				   }
				   channelData.settle_mmPay = settle_mmPay;
				   //DebuUtil.log("settle_mmPay:"+settle_mmPay);
			   }
			   synchronized(settle_tcPay)
			   {
				   if(tcPay >= SETTLE_SHLD)
				   {
				      settle_tcPay = ((tcPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_tcPay = tcPay;
				   }
			   }
			   synchronized(settle_uniPay)
			   {
				   if(uniPay >= SETTLE_SHLD)
				   {
					   settle_uniPay = ((uniPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_uniPay = uniPay;
				   }
			   }*/
			   
		   }
		}
	}

/**
 * 将金额按照折扣计算加入对应的settleXXPay
 * @param pay
 * @param ration
 * @return
 */
	private int settlePay(Integer pay, int ration) {
		int settle_pay = 0;
		if (pay==null) {
			pay = 0;
		}
		if(pay >= SETTLE_SHLD)
		   {
		      settle_pay =((pay*ration/100)/100)*100;
		   }
		   else
		   {
			   settle_pay = pay;
		   }
		   return settle_pay;
	}



	public void settle(Integer  pamaRatio)
	{
		int ration = 0;
		
		CooperationDao cooDao = new CooperationDao();
		Cooperation cooperation= cooDao.getRecord(packet_id);
		
		if(cooperation != null)
		{
			if(pamaRatio == null)
			{
			   ration = cooperation.getSettleRatio();
			}
			else
			{
				ration = pamaRatio;
			}
			
			String settle_type = cooperation.settle_type;
			if(StringUtil.is_nullString(settle_type))
			{
				return;
			}
		   if(settle_type.equals("CPA"))
		   {
			   //DebuUtil.log("CPA结算:activityNum="+activityNum);		
			   synchronized(settle)
			   {
		          settle = activityNum*ration/100;
			   }
		      //DebuUtil.log("CPA结算:settle="+settle);
		   }
		   else if(settle_type.equals("CPA_R"))
		   {
			   //DebuUtil.log("CPA_R结算:registerNum="+registerNum);
			   synchronized(settle)
			   {
				   settle = registerNum*ration/100;
			   }
			   //DebuUtil.log("CPA_R结算:settle="+settle);
		   }
		   else if(settle_type.equals("CPS"))
		   {
			   //DebuUtil.log("CPS结算:settle="+settle);
			   //if(aliPay >= SETTLE_SHLD)
			   {
				   synchronized(settle_aliPay)
				   {
			           settle_aliPay = ((aliPay*ration/100)/100)*100;	
				   }
			   }
			   //if(tenPay >= SETTLE_SHLD)
			   {
				   synchronized(settle_tenPay)
				   {
					   settle_tenPay =((tenPay*ration/100)/100)*100;	
				   }
				  
			   }
			   //if(yeePay >= SETTLE_SHLD)
			   {
				   synchronized(settle_yeePay)
				   {
					   settle_yeePay = ((yeePay*ration/100)/100)*100;	
				   }
				   //DebuUtil.log("CPS结算:yeePay="+yeePay);
				  // DebuUtil.log("CPS结算:settle_yeePay="+settle_yeePay);
			   }
			   //if(mmPay >= SETTLE_SHLD)
			   {
				   synchronized(settle_mmPay)
				   {
					   settle_mmPay = ((mmPay*ration/100)/100)*100;	
				   }
			   }
			   
			   if(ConstValue.STAND_ALONE == 1)
			   {
				   synchronized(settle_tcPay)
				   {
					   settle_tcPay = ((tcPay*ration/100)/100)*100;	
				   }
				   
				   synchronized(settle_uniPay)
				   {
					   settle_uniPay = ((uniPay*ration/100)/100)*100;	
				   }
			   }
			   
			   //if(unionPay >= SETTLE_SHLD)
			   {
				   synchronized(settle_unionPay)
				   {
					   settle_unionPay = ((unionPay*ration/100)/100)*100;	
				   }
			   }
			   
			   synchronized(xlockdata)
			   {
				   //DebuUtil.log("yeePay:"+yeePay);
				   if(settle_mzPay == null)
				   {
					   settle_mzPay = 0;
				   }
				   if(settle_woPay == null)
				   {
					   settle_woPay = 0;
				   }
				   if(settle_openPay == null)
				   {
					   settle_openPay = 0;
				   }
				   if(settle_webPay == null)
				   {
					   settle_webPay = 0;
				   }
				   if(settle_boxPay == null)
				   {
					   settle_boxPay = 0;
				   }
				   if(settle_ldPay == null)
				   {
					   settle_ldPay = 0;
				   }
				   if(settle_jdPay == null)
				   {
					   settle_jdPay = 0;
				   }
				   if(mzPay == null)
				   {
					   mzPay = 0;
				   }
				   if(woPay == null)
				   {
					   woPay = 0;
				   }
				   if(openPay == null)
				   {
					   openPay = 0;
				   }
				   if(webPay == null)
				   {
					   webPay = 0;
				   }
				   if(boxPay == null)
				   {
					   boxPay = 0;
				   }
				   if(ldPay == null)
				   {
					   ldPay = 0;
				   }
				   if(jdPay == null)
				   {
					   jdPay = 0;
				   }
				   if(wxPay == null)
				   {
					   wxPay = 0;
				   }
				   if(mzPay >= SETTLE_SHLD)
				   {
					   settle_mzPay = ((mzPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_mzPay = mzPay;
				   }
				   if(woPay >= SETTLE_SHLD)
				   {
					   settle_woPay = ((woPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_woPay = woPay;
				   }
				   if(openPay >= SETTLE_SHLD)
				   {
					   settle_openPay = ((openPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_openPay = openPay;
				   }
				   if(webPay >= SETTLE_SHLD)
				   {
					   settle_webPay = ((webPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_webPay = webPay;
				   }
				   if(boxPay >= SETTLE_SHLD)
				   {
					   settle_boxPay = ((boxPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_boxPay = boxPay;
				   }
				   if(ldPay >= SETTLE_SHLD)
				   {
					   settle_ldPay = ((ldPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_ldPay = ldPay;
				   }
				   if(jdPay >= SETTLE_SHLD)
				   {
					   settle_jdPay = ((jdPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_jdPay = jdPay;
				   }
				   if(wxPay >= SETTLE_SHLD)
				   {
					   settle_wxPay = ((wxPay*ration/100)/100)*100;	
				   }
				   else
				   {
					   settle_wxPay = wxPay;
				   }
				   //channelData.settle_mzPay = settle_mzPay;
				   //DebuUtil.log("settle_yeePay:"+settle_yeePay);
			   }
		   }
		}
	}
	
	
	
	public void setNewunionPay(Integer newunionPay){
	
			this.newunionPay=newunionPay;
		
	}
	public void setNewunionPay2(Integer newunionPay){
		synchronized(this.newunionPay)
		{
			this.newunionPay=newunionPay;
		}
	}
	public Integer getNewunionPay(){
		return this.newunionPay;
	}	
	
	public void setUnionPay(Integer unionPay){	
		
			this.unionPay=unionPay;	
		
	}
	public void setWoPay(Integer woPay){	
		
		this.woPay=woPay;	
	
    }
	public void setOpenPay(Integer openPay){	
		
		this.openPay=openPay;	
	
    }
	public void setUnionPay2(Integer unionPay){	
		synchronized(this.unionPay)
		{
			this.unionPay=unionPay;	
		}
	}
	public Integer getUnionPay(){
		return this.unionPay;
	}	
	
	public void setSettle(Integer set){
		
			this.settle=set;
		
	}
    public void setSettle2(Integer set){
		
		synchronized(this.settle)
		{
			this.settle=set;
		}
	}
	public Integer getSettle(){
		return this.settle;
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
	
	public void setChannel(String channel){
		this.channel=channel;
	}
	public String getChannel( ){
		return this.channel;
	}
	
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
	
	public void setAllPayAccoutNumBefore(Integer allPayAccoutNumBefore){
	
			this.allPayAccoutNumBefore=allPayAccoutNumBefore;
		
	}
	public void setAllPayAccoutNumBefore2(Integer allPayAccoutNumBefore){
		synchronized(this.allPayAccoutNumBefore)
		{
			this.allPayAccoutNumBefore=allPayAccoutNumBefore;
		}
	}
	public Integer getAllPayAccoutNumBefore( ){
		return this.allPayAccoutNumBefore;
	}
	
	public void setAllRegisterNumBefore(Integer allRegisterNumBefore){
			this.allRegisterNumBefore=allRegisterNumBefore;
		
	}
	
	public void setDayAllPayAcount(Integer dayAllPay ){
		this.dayAllPayAcount=dayAllPay;
	}
	
	public void setDayAllAcount(Integer dayAllAcount ){
		 this.dayAllAcount = dayAllAcount;
	}	
	
	
	public void setAllRegisterNumBefore2(Integer allRegisterNumBefore){
		synchronized(this.allRegisterNumBefore)
		{
			this.allRegisterNumBefore=allRegisterNumBefore;
		}
	}
	
	public void setDayAllPayAcount2(Integer dayAllPayAcount){
		synchronized(this.dayAllPayAcount)
		{
			this.dayAllPayAcount=dayAllPayAcount;
		}
	}
	
	public void setDayAllAcount2(Integer dayAllAcount){
		synchronized(this.dayAllAcount)
		{
			this.dayAllAcount=dayAllAcount;
		}
	}
	
	
	public Integer getAllRegisterNumBefore( ){
		return this.allRegisterNumBefore;
	}
	
	public Integer getDayAllPayAcount( ){
		return this.dayAllPayAcount;
	}
	
	public Integer getDayAllAcount( ){
		return this.dayAllAcount;
	}	
	
	
	public void setAllPayBefore(Integer allPayBefore){
	
			this.allPayBefore=allPayBefore;
		
	}
	public void setAllPayBefore2(Integer allPayBefore){
		synchronized(this.allPayBefore)
		{
			this.allPayBefore=allPayBefore;
		}
	}
	public Integer getAllPayBefore( ){
		return this.allPayBefore;
	}
	
	
	public void setLiveNum(Integer liveNum){
	
			this.liveNum=liveNum;
		
	}
	public void setLiveNum2(Integer liveNum){
		synchronized(this.liveNum)
		{
			this.liveNum=liveNum;
		}
	}
	public Integer getLiveNum( ){
		return this.liveNum;
	}
	


	public Integer getReqOkNum( ){
		return this.reqOkNum;
	}
	
	public void  setReqOkNum(Integer reqOkNum ){
		 this.reqOkNum = reqOkNum;
	}
	
	public Integer getReqNum( ){
		return this.reqNum;
	}
	
	public void  setReqNum(Integer reqNum ){
		 this.reqNum = reqNum;
	}
	
	public void  setFeeNum(Integer feeNum ){
		 this.feeNum = feeNum;
	}
	
	public Integer getFeeNum( ){
		return this.feeNum;
	}
	
	public void  setMsmNum(Integer msmNum ){
		 this.msmNum = msmNum;
	}
	
	public Integer getMsmNum( ){
		return this.msmNum;
	}
	
	public void ReqOkNumInc(int count)
	{
		synchronized(xlockdata)
		{
			if(reqOkNum == null)
			{
				reqOkNum = 0;
			}
			reqOkNum += count;
		}
	}	
	
	public void ReqNumInc(int count)
	{
		synchronized(xlockdata)
		{
			if(reqNum == null)
			{
				reqNum = 0;
			}
			reqNum += count;
		}
	}	
	
	public void payTimesInc(int count)
	{
		synchronized(xlockdata)
		{	
			if(pay_times == null)
			{
				pay_times = 0;
			}
			pay_times += count;
		}
	}	
	
	
	/**
	 * 统计支付次数
	 * @param count
	 */
	public void FeeNumInc(int count)
	{
		synchronized(xlockdata)
		{
			if(feeNum == null)
			{
				feeNum = 0;
			}
			feeNum += count;
		}
	}	
	
	public void MsmNumInc(int count)
	{
		synchronized(xlockdata)
		{
			if(msmNum == null)
			{
				msmNum = 0;
			}
			msmNum += count;
		}
	}	
	
	public void day1PayTimesInc(int count)
	{
		synchronized(xlockdata)
		{
			if(day1_pay_times == null)
			{
				day1_pay_times = 0;
			}
			day1_pay_times += count;
		}
	}	
	
	public void day1PayAcountInc(int count)
	{
		synchronized(xlockdata)
		{
			if(day1_pay_acount == null)
			{
				day1_pay_acount = 0;
			}
			day1_pay_acount += count;
		}
	}	
	
	public void setMmpay2(int count)
	{
		synchronized(xlockdata)
		{
			mmPay = count;
		}
	}	
	public void setReqOkNum2(int count)
	{
		synchronized(xlockdata)
		{
			reqOkNum = count;
		}
	}	
	
	public void setReqNum2(int count)
	{
		synchronized(xlockdata)
		{			
			reqNum = count;
		}
	}	
	
	public void setFeeNum2(int count)
	{
		synchronized(xlockdata)
		{
			feeNum = count;
		}
	}	
	
	public void setMsmNum2(int count)
	{
		synchronized(xlockdata)
		{
			
			msmNum = count;
		}
	}	
	
	
	public void setLoginNum(Integer loginNum){

			this.loginNum=loginNum;
		
	}
	public void setLoginNum2(Integer loginNum){
		synchronized(this.loginNum)
		{
			this.loginNum=loginNum;
		}
	}
	public Integer getLoginNum( ){
		return this.loginNum;
	}
	
	public void setDay30Num(Integer day30Num){
	
			this.day30Num=day30Num;
		
	}
	public void setDay30Num2(Integer day30Num){
		synchronized(this.day30Num)
		{
			this.day30Num=day30Num;
		}
	}
	public Integer getDay30Num( ){
		return this.day30Num;
	}
	
	public void setDay7Num(Integer day7Num){
	
			this.day7Num=day7Num;
		
	}
	public void setDay7Num2(Integer day7Num){
		synchronized(this.day7Num)
		{
			this.day7Num=day7Num;
		}
	}
	public Integer getDay7Num( ){
		return this.day7Num;
	}
	
	public void setDay1Num(Integer day1Num){
	
			this.day1Num=day1Num;
		
	}
	public void setDay1Num2(Integer day1Num){
		synchronized(this.day1Num)
		{
			this.day1Num=day1Num;
		}
	}
	public Integer getDay1Num( ){
		return this.day1Num;
	}
	
	public void setDay1Pay(Integer day1Pay){		
		this.day1Pay=day1Pay;
    }	
	public Integer getDay1Pay( ){
		return this.day1Pay;
	}
	public void setDay1Pay2(Integer day1Pay){
		synchronized(this.day1Pay)
		{
			this.day1Pay=day1Pay;
		}
	}
	public void setDay2Pay(Integer day2Pay){		
		this.day2Pay=day2Pay;
    }
	public Integer getDay2Pay( ){
		return this.day2Pay;
	}
	public void setDay2Pay2(Integer day2Pay){
		synchronized(this.day2Pay)
		{
			this.day2Pay=day2Pay;
		}
	}
	public void setDay3Pay(Integer day3Pay){		
		this.day3Pay=day3Pay;
    }
	public Integer getDay3Pay( ){
		return this.day3Pay;
	}
	public void setDay3Pay2(Integer day3Pay){
		synchronized(this.day3Pay)
		{
			this.day3Pay=day3Pay;
		}
	}
	public void setDay4Pay(Integer day4Pay){		
		this.day4Pay=day4Pay;
    }
	public Integer getDay4Pay( ){
		return this.day4Pay;
	}
	public void setDay4Pay2(Integer day4Pay){
		synchronized(this.day4Pay)
		{
			this.day4Pay=day4Pay;
		}
	}
	public void setDay5Pay(Integer day5Pay){		
		this.day5Pay=day5Pay;
    }
	public Integer getDay5Pay( ){
		return this.day5Pay;
	}
	public void setDay5Pay2(Integer day5Pay){
		synchronized(this.day5Pay)
		{
			this.day5Pay=day5Pay;
		}
	}
	public void setDay6Pay(Integer day6Pay){		
		this.day6Pay=day6Pay;
    }
	public Integer getDay6Pay( ){
		return this.day6Pay;
	}
	public void setDay7Pay(Integer day7Pay){		
		this.day7Pay=day7Pay;
    }
	public Integer getDay7Pay( ){
		return this.day7Pay;
	}
	public void setDay7Pay2(Integer day7Pay){
		synchronized(this.day7Pay)
		{
			this.day7Pay=day7Pay;
		}
	}
	public void Day1PayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(day1Pay == null)
			{
				day1Pay = 0;
			}
			day1Pay += count;
		}
	}	
	public void Day2PayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(day2Pay == null)
			{
				day2Pay = 0;
			}
			day2Pay += count;
		}
	}		
	public void Day3PayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(day3Pay == null)
			{
				day3Pay = 0;
			}
			day3Pay += count;
		}
	}		
	public void Day4PayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(day4Pay == null)
			{
				day4Pay = 0;
			}
			day4Pay += count;
		}
	}		
	public void Day5PayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(day5Pay == null)
			{
				day5Pay = 0;
			}
			day5Pay += count;
		}
	}	
	public void Day6PayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(day6Pay == null)
			{
				day6Pay = 0;
			}
			day6Pay += count;
		}
	}	
	public void Day7PayInc(int count)
	{
		synchronized(xlockdata)
		{
			if(day7Pay == null)
			{
				day7Pay = 0;
			}
			day7Pay += count;
		}
	}	
	
	public void setRealRegisterNum(Integer realregisterNum){
	
			this.realRegisterNum=realregisterNum;
		
	}
	public void setRealRegisterNum2(Integer realregisterNum){
		synchronized(this.realRegisterNum)
		{
			this.realRegisterNum=realregisterNum;
		}
	}
	public Integer getRealRegisterNum( ){
		return this.realRegisterNum;
	}
	
	public void setRegisterNum(Integer registerNum){
	
			this.registerNum=registerNum;
		
	}
	public void setRegisterNum2(Integer registerNum){
		synchronized(this.registerNum)
		{
			this.registerNum=registerNum;
		}
	}
	public Integer getRegisterNum( ){
		return this.registerNum;
	}
	
	public void setActivityRegNum(Integer activityRegNum){
		
			this.activityRegNum=activityRegNum;
		
	}
	public void setActivityRegNum2(Integer activityRegNum){
		synchronized(this.activityRegNum)
		{
			this.activityRegNum=activityRegNum;
		}
	}
	public Integer getActivityRegNum( ){
		return this.activityRegNum;
	}
	
	public void setActivityNum(Integer activityNum){
		
			this.activityNum=activityNum;
		
	}
	public void setActivityNum2(Integer activityNum){
		synchronized(this.activityNum)
		{
			this.activityNum=activityNum;
		}
	}
	public Integer getActivityNum( ){
		return this.activityNum;
	}
	
	public void setNewmmPay(Integer newmmPay){
	
			this.newmmPay=newmmPay;
		
	}
	public void setNewtcPay(Integer newtcPay){
		
		this.newtcPay=newtcPay;
	
   }
	
   public void setNewwoPay(Integer newwoPay){
		
		this.newwoPay=newwoPay;
	
   }

   public void setNewopenPay(Integer newopenPay){
		
		this.newopenPay=newopenPay;
	
  }
   
	public void setNewuniPay(Integer newuniPay){
		
		this.newuniPay=newuniPay;
	
   }
	
	public void setNewmmPay2(Integer newmmPay){
		synchronized(this.newmmPay)
		{
			this.newmmPay=newmmPay;
		}
	}
	public void setNewtcPay2(Integer newtcPay){
		synchronized(this.newtcPay)
		{
			this.newtcPay=newtcPay;
		}
	}
	public void setNewuniPay2(Integer newuniPay){
		synchronized(this.newuniPay)
		{
			this.newuniPay=newuniPay;
		}
	}
	public Integer getNewmmPay( ){
		return this.newmmPay;
	}
	public Integer getNewtcPay( ){
		return this.newtcPay;
	}
	public Integer getNewuniPay( ){
		return this.newuniPay;
	}
	public Integer getNewwoPay( ){
		return this.newwoPay;
	}
	public Integer getNewopenPay( ){
		return this.newopenPay;
	}
	public void setNewyeePay(Integer newyeePay){
	
			this.newyeePay=newyeePay;
		
	}
	public void setNewyeePay2(Integer newyeePay){
		synchronized(this.newyeePay)
		{
			this.newyeePay=newyeePay;
		}
	}
	public Integer getNewyeePay( ){
		return this.newyeePay;
	}
	
	public void setNewtenPay(Integer newtenPay){
	
			this.newtenPay=newtenPay;
		
	}
	public void setNewtenPay2(Integer newtenPay){
		synchronized(this.newtenPay)
		{
			this.newtenPay=newtenPay;
		}
	}
	public Integer getNewtenPay( ){
		return this.newtenPay;
	}
	
	
	public void setNewaliPay(Integer newaliPay){
	
			this.newaliPay=newaliPay;
		
	}
	public void setNewaliPay2(Integer newaliPay){
		synchronized(this.newaliPay)
		{
			this.newaliPay=newaliPay;
		}
	}
	public Integer getNewaliPay( ){
		return this.newaliPay;
	}
	
	
	public void setTcPay(Integer tcPay){
		
		this.tcPay=tcPay;
	
    }
	public void setMmPay(Integer mmPay){
	
			this.mmPay=mmPay;
		
	}
    public void setUniPay(Integer uniPay){
		
		this.uniPay=uniPay;
	
    }
	public void setMmPay2(Integer mmPay){
		synchronized(this.mmPay)
		{
			this.mmPay=mmPay;
		}
	}
	public void setTcPay2(Integer tcPay){
		synchronized(this.tcPay)
		{
			this.tcPay=tcPay;
		}
	}
	public void setUniPay2(Integer uniPay){
		synchronized(this.uniPay)
		{
			this.uniPay=uniPay;
		}
	}
	public Integer getMmPay( ){
		return this.mmPay;
	}
	
	public Integer getTcPay( ){
		return this.tcPay;
	}
	public Integer getUniPay( ){
		return this.uniPay;
	}	
	public Integer getWoPay( ){
		return this.woPay;
	}	
	public Integer getOpenPay( ){
		return this.openPay;
	}
	public void setDate(String date){
		this.date=date;
	}
	public String getDate(){
		return this.date;
	}	
	
	public void setAliPay(Integer aliPay){
		
			this.aliPay=aliPay;
		
	}
	
	
	
	public void setAliPay2(Integer aliPay){
		synchronized(this.aliPay)
		{
			this.aliPay=aliPay;
		}
	}
	public Integer getAliPay( ){
		return this.aliPay;
	}
	
	public void setTenPay(Integer tenPay){
		
			this.tenPay=tenPay;
		
	}
	public void setTenPay2(Integer tenPay){
		synchronized(this.tenPay)
		{
			this.tenPay=tenPay;
		}
	}
	public Integer getTenPay( ){
		return this.tenPay;
	}
	
	public void setYeePay(Integer yeePay){	
			this.yeePay=yeePay;
		
	}
	public void setYeePay2(Integer yeePay){
		synchronized(this.yeePay)
		{
			this.yeePay=yeePay;
		}
	}
	public Integer getYeePay( ){
		return this.yeePay;
	}

	
    public void reviseData()
    {
    	DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+" revise");
    	
    	PayDao payDao = new PayDao();
		String sql = String.format("select * from pay where packet_id='%s' and date='%s' and state=1 ",this.packet_id,this.date);//where packet_id='217150001'
		ArrayList<Object> payList = payDao.getList(sql);	
		this.dayAllPayAcount = payList.size();//修改付费数
		DebuUtil.log("dayAllPayAcount="+this.dayAllPayAcount);
		this.dayAllAcount = dayAllPayAcount*NumberUtil.getRandom(35, 45);//修改付费数
		this.activityNum = dayAllAcount*100/NumberUtil.getRandom(70, 90);
		this.registerNum =dayAllAcount+NumberUtil.getRandom(0, 2);
		this.activityRegNum = dayAllAcount-NumberUtil.getRandom(0, 2);
		this.realRegisterNum = this.dayAllAcount*NumberUtil.getRandom(20, 30)/100;
		this.day1Num = this.dayAllAcount*NumberUtil.getRandom(20, 30)/100;
		this.day7Num = this.dayAllAcount*NumberUtil.getRandom(6, 10)/100;
		this.day30Num = this.dayAllAcount*NumberUtil.getRandom(3, 5)/100;
		
		newaliPay = aliPay;
		newyeePay = yeePay;
		newtenPay = tenPay;
		newmzPay = mzPay;
		newunionPay = unionPay;
    }
    
    public void reviseData2()
    {
    	DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+" revise");
    	
    	/*Map<String,Integer> map=new HashMap<String,Integer>();   
        map.put("741269001", 250); 
        map.put("741270001", 250); 
        map.put("741265001", 250); 
        map.put("741279001", 250); */
    	int maxARUP = 250;
    	int minARUP = 200;
    	
    	PayDao payDao = new PayDao();
		String sql = String.format("select * from pay where packet_id='%s' and date='%s' and state=1 ",this.packet_id,this.date);//where packet_id='217150001'
		ArrayList<Object> payList = payDao.getList(sql);	
		this.dayAllPayAcount = payList.size();//修改付费数
		
		
		sql = String.format("select sum(amount)/100 from pay where packet_id='%s' and date='%s' and state=1 ",this.packet_id,this.date);
		/*int sumpay = payDao.getCount(sql);
		if(this.dayAllPayAcount > 0)
		{
			int  average = sumpay/this.dayAllPayAcount;
			if(average < minARUP)
			{
				average = minARUP;
			}
			else if (average > maxARUP)
			{
				average = maxARUP;
			}
			this.dayAllPayAcount = sumpay/average;
			DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"修改ARUP="+this.dayAllPayAcount);
			
		}*/
		DebuUtil.log("dayAllPayAcount="+this.dayAllPayAcount);
		this.dayAllAcount = dayAllPayAcount*NumberUtil.getRandom(33-2, 33+2);//修改付费数
		this.activityNum = dayAllAcount*100/NumberUtil.getRandom(70, 90);
		this.registerNum =dayAllAcount+NumberUtil.getRandom(0, 3);
		this.activityRegNum = dayAllAcount-NumberUtil.getRandom(0, 3);
		this.realRegisterNum = this.dayAllAcount*NumberUtil.getRandom(20, 30)/100;
		this.day1Num = this.dayAllAcount*NumberUtil.getRandom(20, 30)/100;
		this.day7Num = this.dayAllAcount*NumberUtil.getRandom(9, 11)/100;
		this.day30Num = this.dayAllAcount*NumberUtil.getRandom(3, 5)/100;
		
		Date lastdate = DateUtil.strToDate(this.date);
		lastdate=DateUtil.addDate("dd", -1, lastdate);	
		ChannelDataDao channelDataDao = new ChannelDataDao();
		ChannelData channelData =channelDataDao.getRecord(this.packet_id, DateUtil.getDate(lastdate));
		if(channelData != null)
		{
			this.loginNum = channelData.loginNum*NumberUtil.getRandom(85, 95)/100+this.registerNum;
		}
		/*newaliPay = aliPay;
		newyeePay = yeePay;
		newtenPay = tenPay;
		newunionPay = unionPay;*/
		msmNum = dayAllPayAcount;	
		dayAllPayAcount = getPayAcount(msmNum,this.date);
		
    }
    
    public int getPayAcount(int payAcount,String datestr)
    {
    	int days = (int)DateUtil.getDayDiff(datestr,util.DateUtil.getDate());
		int ret  = payAcount/10*(1+days);
		if(days >= 9)
		{
			ret = payAcount;
		}
		return ret;
    }
    
	public void genData()
	{
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"activityNum="+this.activityNum);
		//处理激活数
		int activity_num = 0;
		ActivateDao activateDao = new ActivateDao();
		RegisterDao registerDao = new RegisterDao();
		
		String sql = String.format("select * from activate where packet_id='%s' and date='%s' ",this.packet_id,this.date);//where packet_id='217150001'
		activity_num = this.activityNum;//-activateDao.getRecordCount(sql);
				
		for(int i = 0;i < activity_num; i++)
		{
			DebuUtil.log("activate packet_id="+this.packet_id+" date="+this.date +"index="+i);
			Activate activate = new Activate(this.packet_id,this.date);
			activateDao.add(activate);
		}
		
		//登录数
		//idate=DateUtil.addDate("dd", dayoff, curDate);
		//ChannelDataDao channelDataDao = new ChannelDataDao();
		  // String sql =String.format("select * from channel_data where packet_id='%s' and date='%s' ");
		//处理注册数
		sql = String.format("select * from register where packet_id='%s' and date='%s' ",this.packet_id,this.date);
		int register_num = this.registerNum;//-registerDao.getRecordCount(sql);
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"registerNum="+this.registerNum);
		if(register_num > 0)
		{
			 sql = String.format("select * from activate where packet_id='%s' and date='%s' order by device_id limit %d ",this.packet_id,this.date,register_num);//where packet_id='217150001'
			ArrayList<Object> activityList = activateDao.getList(sql);		
			DebuUtil.log(sql);
			for(int i = 0; i < activityList.size(); i++)
			{
				Activate activate = (Activate)activityList.get(i);
				Register register = new Register(activate.packet_id,activate.device_id,activate.date,activate.time);
				DebuUtil.log("register packet_id="+this.packet_id+" date="+this.date +"index="+i);
				registerDao.add(register);
			}
		}
		
		//处理留存
		int day1_num = this.day1Num;
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"day1Num="+this.day1Num);
		if(day1_num > 0)
		{
		    String str = String.format("update activate set dateo=1 where packet_id='%s' and date='%s' order by device_id limit %d ",this.packet_id,this.date,day1_num);//where packet_id='217150001'
		    registerDao.executeUpdate(str);
		    DebuUtil.log(str);
		}
		
		int day7_num = this.day7Num;
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"day7Num="+this.day7Num);
		if(day7_num > 0)
		{
		    String str = String.format("update activate set dates=1 where packet_id='%s' and date='%s' order by device_id limit %d ",this.packet_id,this.date,day7_num);//where packet_id='217150001'
		    registerDao.executeUpdate(str);
		    DebuUtil.log(str);
		}
		
		int day30_num = this.day30Num;
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"day30Num="+this.day30Num);
		if(day30_num > 0)
		{
		    String str = String.format("update activate set datem=1 where packet_id='%s' and date='%s' order by device_id limit %d ",this.packet_id,this.date,day30_num);//where packet_id='217150001'
		    registerDao.executeUpdate(str);
		    DebuUtil.log(str);
		}
		
		//int register_num = this.day30Num;
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"register_num="+register_num);
		if(register_num > 0)
		{
		    String str = String.format("update activate set reg=1 where packet_id='%s' and date='%s' order by device_id limit %d ",this.packet_id,this.date,register_num);//where packet_id='217150001'
		    registerDao.executeUpdate(str);
		    DebuUtil.log(str);
		}
	}
	
	public void genActivateRegister2()
	{
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"activityNum="+this.activityNum);
		//处理激活数
		int activity_num = 0;
		ActivateDao activateDao = new ActivateDao();
		RegisterDao registerDao = new RegisterDao();
		
		String sql = String.format("select * from activate where packet_id='%s' and date='%s' ",this.packet_id,this.date);//where packet_id='217150001'
		activity_num = this.activityNum-activateDao.getRecordCount(sql);
				
		for(int i = 0;i < activity_num; i++)
		{
			DebuUtil.log("activate packet_id="+this.packet_id+" date="+this.date +"index="+i);
			Activate activate = new Activate(this.packet_id,this.date);
			activateDao.add(activate);
			
		}
		
		//登录数
		//idate=DateUtil.addDate("dd", dayoff, curDate);
		//ChannelDataDao channelDataDao = new ChannelDataDao();
		  // String sql =String.format("select * from channel_data where packet_id='%s' and date='%s' ");
		//处理注册数
		sql = String.format("select * from register where packet_id='%s' and date='%s' ",this.packet_id,this.date);
		int register_num = this.registerNum-registerDao.getRecordCount(sql);
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"registerNum="+this.registerNum);
		if(register_num > 0)
		{
			sql = String.format("select * from activate where packet_id='%s' and date='%s' order by device_id limit %d ",this.packet_id,this.date,register_num);//where packet_id='217150001'
			ArrayList<Object> activityList = activateDao.getList(sql);		
			DebuUtil.log(sql);
			for(int i = 0; i < activityList.size(); i++)
			{
				Activate activate = (Activate)activityList.get(i);
				activate.reg=1;
				activateDao.edit(activate);
				
				Register register = new Register(activate.packet_id,activate.device_id,activate.date,activate.time);
				DebuUtil.log("register packet_id="+this.packet_id+" date="+this.date +"index="+i);
				registerDao.add(register);
			}
		}
		
		//处理留存
		sql = String.format("select * from activate where packet_id='%s' and date='%s' and dateo=1 ",this.packet_id,this.date);
		int day1_num = this.day1Num-activateDao.getRecordCount(sql);
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"day1Num="+this.day1Num);
		if(day1_num > 0)
		{
		    //String str = String.format("update activate set dateo=1 where packet_id='%s' and date='%s' order by device_id limit %d ",this.packet_id,this.date,day1_num);//where packet_id='217150001'
		    //registerDao.executeUpdate(str);
		    //DebuUtil.log(str);
			sql = String.format("select * from activate where packet_id='%s' and date='%s' and dateo<>1 ",this.packet_id,this.date);//where packet_id='217150001'
			ArrayList<Object> activityList = activateDao.getList(sql);	
			ArrayList<Integer> indexList = new ArrayList<Integer>();
			for(int i = 0; i < day1_num; i++)
			{			
				int index = getRandomIndex(0,activityList.size(),indexList);
				Activate activate = (Activate)activityList.get(index);
				if(activate != null)
				{
					String str = String.format("update activate set dateo=1 where device_id='%s' ",activate.device_id);//where packet_id='217150001'
					activateDao.executeUpdate(str);
				}
			}
		}
		
		sql = String.format("select * from activate where packet_id='%s' and date='%s' and dates=1 ",this.packet_id,this.date);
		int day7_num = this.day7Num-activateDao.getRecordCount(sql);
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"day7Num="+this.day7Num);
		if(day7_num > 0)
		{   
		    sql = String.format("select * from activate where packet_id='%s' and date='%s' and dates<>1 ",this.packet_id,this.date);//where packet_id='217150001'
			ArrayList<Object> activityList = activateDao.getList(sql);	
			ArrayList<Integer> indexList = new ArrayList<Integer>();
			for(int i = 0; i < day7_num; i++)
			{			
				int index = getRandomIndex(0,activityList.size(),indexList);
				Activate activate = (Activate)activityList.get(index);
				if(activate != null)
				{
					String str = String.format("update activate set dates=1 where device_id='%s' ",activate.device_id);//where packet_id='217150001'
					activateDao.executeUpdate(str);
				}
			}
		}
		
		sql = String.format("select * from activate where packet_id='%s' and date='%s' and datem=1 ",this.packet_id,this.date);
		int day30_num = this.day30Num-activateDao.getRecordCount(sql);
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"day30Num="+this.day30Num);
		if(day30_num > 0)
		{		    	    
		    sql = String.format("select * from activate where packet_id='%s' and date='%s' and datem<>1 ",this.packet_id,this.date);//where packet_id='217150001'
			ArrayList<Object> activityList = activateDao.getList(sql);	
			ArrayList<Integer> indexList = new ArrayList<Integer>();
			for(int i = 0; i < day30_num; i++)
			{			
				int index = getRandomIndex(0,activityList.size(),indexList);
				Activate activate = (Activate)activityList.get(index);
				if(activate != null)
				{
					String str = String.format("update activate set datem=1 where device_id='%s' ",activate.device_id);//where packet_id='217150001'
					activateDao.executeUpdate(str);
				}
			}
		}
		
		//int register_num = this.day30Num;//
		/*DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"register_num="+register_num);
		sql = String.format("select * from activate where packet_id='%s' and date='%s' and reg=1  ",this.packet_id,this.date);
		register_num=this.registerNum-activateDao.getRecordCount(sql);
		if(register_num > 0)
		{
		    String str = String.format("update activate set reg=1 where packet_id='%s' and date='%s' order by device_id limit %d ",this.packet_id,this.date,register_num);//where packet_id='217150001'
		    registerDao.executeUpdate(str);
		    DebuUtil.log(str);
		}*/
	}
	
	public static int findi(ArrayList<Integer> indexList,int value)
	{
		int bFind = 0;
		for(Integer index:indexList)
		{
			if(index == value)
			{
				bFind = 1;
				break;
			}
		}
		return bFind;
	}
	
	
	public static int getRandomIndex(int min,int max,ArrayList<Integer> indexList)
	{
		int ret = 0;
		int bFind = 0;
		if(max > 0)
		{
			ret = NumberUtil.getRandom(min, max);//
		}
		
		bFind = findi(indexList,ret);
		if(bFind == 1)
		{
			for(int i = min; i<max;i ++)
			{
				if(findi(indexList,i) == 0)
				{
					ret = i;
					
					break;
				}
			}
		}
		indexList.add(ret);
		return ret;
	}
	
	public static void changeUserPay(Pay pay,Register register)
	{
		if((register != null) && (pay != null))
		{
			register.type="P";
			RegisterDao registerDao = new RegisterDao();
			registerDao.edit(register);
			
			ActivateDao activateDao = new ActivateDao(); 
			Activate activate = activateDao.getRecord(register.device_id);
			activate.loginDate = Register.addDay(pay.date, NumberUtil.getRandom(1, 10));
			activateDao.edit(activate);
			
			//DebuUtil.log("changeuser packet_id="+this.packet_id+" date="+this.date +"username="+pay.username);
			
			//map.put(pay.username, pay.username);
			//if(pay != null)
			{
				pay.packet_id2 = pay.username;
				pay.username = register.username;
				pay.device_id = register.device_id;
				pay.type="B";
				PayDao payDao = new PayDao();
				payDao.edit(pay);
			}
		}
	}
	
	public void changeUserPay(ArrayList<Object> registerList,ArrayList<Object> payList,int count)
	{
		ArrayList<Integer> payindexList = new ArrayList<Integer>();//new ArrayList<Integer>;
		ArrayList<Integer> regindexList = new ArrayList<Integer>();
		
		DebuUtil.log("changeuser payList.size()="+payList.size());
		DebuUtil.log("changeuser registerList.size()="+registerList.size());
		for(int i = 0;i < count; i++)
		{
			
			int regindex =getRandomIndex(0,registerList.size(), regindexList);// NumberUtil.getRandom(1, registerList.size());
			int payTimes = 0;
			boolean bPayMany = false;
			do
			{
				int payindex = getRandomIndex(0,payList.size(), payindexList);//NumberUtil.getRandom(1, payList.size()-1);//
				
				int PayManyRandom = NumberUtil.getRandom(0, 100);
				if(PayManyRandom < 4)
				{
					//bPayMany = true;
				}
				payTimes ++;
				
				DebuUtil.log("changeuser packet_id="+this.packet_id+" date="+this.date +" payindex="+payindex+" regindex="+regindex);
				
				Pay pay = null;
				if(payList.size() > 0)					
				{
					pay = (Pay)payList.get(payindex);
				}
				if(registerList.size() > 0)
				{
					Register register = (Register)registerList.get(regindex);
					changeUserPay(pay,register);
					
				}		
				
				if((payTimes >= 3) || (bPayMany == false) )
				{
					break;
				}
			} while(true);
		}
     }

	
	public void updateData()
	{
		PayDao payDao = new PayDao();
		
		String sql =  String.format("SELECT count(*) from activate where date='%s' and packet_id='%s'  and device_id IN(SELECT device_id FROM pay where date>='%s')" 
				,this.date,this.packet_id,this.date);
		dayAllPayAcount = payDao.getCount(sql);
		
		sql =  String.format("SELECT sum(amount) FROM pay WHERE device_id IN(SELECT device_id FROM activate WHERE  packet_id='%s'  AND  date='%s')  and state=1 and pay_type='%s' AND packet_id='%s' "
				,this.packet_id,this.date,"alipay",this.packet_id);
		newaliPay = payDao.getCount(sql);
		
		sql =  String.format("SELECT sum(amount) FROM pay WHERE device_id IN(SELECT device_id FROM activate WHERE  packet_id='%s'  AND  date='%s')  and state=1 and pay_type='%s' AND packet_id='%s' "
				,this.packet_id,this.date,"yeepay",this.packet_id);
		newyeePay = payDao.getCount(sql);
		
		sql =  String.format("SELECT sum(amount) FROM pay WHERE device_id IN(SELECT device_id FROM activate WHERE  packet_id='%s'  AND  date='%s')  and state=1 and pay_type='%s' AND packet_id='%s' "
				,this.packet_id,this.date,"tenpay",this.packet_id);
		newtenPay = payDao.getCount(sql);
		
		sql =  String.format("SELECT sum(amount) FROM pay WHERE device_id IN(SELECT device_id FROM activate WHERE  packet_id='%s'  AND  date='%s')  and state=1 and pay_type='%s' AND packet_id='%s' "
				,this.packet_id,this.date,"unionpay",this.packet_id);
		newunionPay = payDao.getCount(sql);
		
		sql =  String.format("SELECT sum(amount) FROM pay WHERE device_id IN(SELECT device_id FROM activate WHERE  packet_id='%s'  AND  date='%s')  and state=1 and pay_type='%s' AND packet_id='%s' "
				,this.packet_id,this.date,"mzpay",this.packet_id);
		newmzPay = payDao.getCount(sql);
		DebuUtil.log("newaliPay="+newaliPay+" newyeePay="+newyeePay +"newtenPay="+newtenPay +"newunionPay="+newunionPay);
	}
	
	public void changeUserPay()
	{
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"changeNewPay");
		//处理激活数
		dayAllPayAcount = getPayAcount(msmNum,this.date);
		DebuUtil.log("dayAllPayAcount="+this.dayAllPayAcount);
		
		PayDao payDao = new PayDao();
		Date enddate = DateUtil.strToDate(this.date);
		enddate=DateUtil.addDate("dd", 9, enddate);
		String sql = String.format("select * from pay where packet_id='%s' and date>='%s' and date<='%s' and state=1 and  (type is null or type<>'B' ) ",this.packet_id,this.date,DateUtil.getDate(enddate));//where packet_id='217150001'
		ArrayList<Object> payList = payDao.getList(sql);	
		
		//按付费用户分配付费用户
		sql = String.format("SELECT * FROM pay WHERE device_id IN(SELECT device_id FROM activate WHERE  packet_id='%s'  AND  date='%s')  and state=1 and type='B' AND packet_id='%s' ",this.packet_id,this.date,this.packet_id);//where packet_id='217150001'
		int paynum = payDao.getRecordCount(sql);
		//this.dayAllPayAcount = payList.size();//修改付费数  
		//SELECT * FROM pay WHERE device_id IN(SELECT device_id FROM activate WHERE  packet_id='%s'  AND  date='%s')  and state=1 and type='B' AND packet_id='%s';
		//修改用户名,
		int count = dayAllPayAcount-paynum;
		if(count > 0)
		{
			RegisterDao registerDao = new RegisterDao();
			sql = String.format("SELECT * FROM register WHERE  (type is null or type<>'P') and device_id IN(select device_id from activate where packet_id='%s' and date='%s' and dateo=1 ) ",this.packet_id,this.date);//where packet_id='217150001'
			ArrayList<Object> registerList = registerDao.getList(sql);
			//Map<String,String> map=new HashMap<String,String>();   
			changeUserPay(registerList,payList,count);						
		}	
		
		updateData();
		
	}	
		
	public void changeUser()
	{
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"changeUser");//
		//处理激活数
		
		PayDao payDao = new PayDao();
		String sql = String.format("select * from pay where packet_id='%s' and date='%s' and state=1 ",this.packet_id,this.date);//where packet_id='217150001'
		ArrayList<Object> payList = payDao.getList(sql);	
		this.dayAllPayAcount = payList.size();//修改付费数
		DebuUtil.log("dayAllPayAcount="+this.dayAllPayAcount);
		//修改用户名
		if(dayAllPayAcount > 0)
		{
			RegisterDao registerDao = new RegisterDao();
			sql = String.format("select * from register where packet_id='%s' and date='%s' order by device_id limit %d  ",this.packet_id,this.date,dayAllPayAcount);//where packet_id='217150001'
			ArrayList<Object> registerList = registerDao.getList(sql);
			Map<String,String> map=new HashMap<String,String>();   
			
			for(int i = 0;i < payList.size(); i++)
			{
				Pay pay = (Pay)payList.get(i);
				//if(map.get(pay.username) == null)
				{
					Register register = (Register)registerList.get(i);
					DebuUtil.log("changeuser packet_id="+this.packet_id+" date="+this.date +"username="+pay.username);
					//map.put(pay.username, pay.username);
					pay.packet_id2 = pay.username;
					pay.username = register.username;
					pay.device_id = register.device_id;
					payDao.edit(pay);
					
				}				
				
			}
		}
		
		newaliPay = aliPay;
		newyeePay = yeePay;
		newtenPay = tenPay;
		newunionPay = unionPay;
		DebuUtil.log("newaliPay="+newaliPay+" newyeePay="+newyeePay +"newtenPay="+newtenPay +"newunionPay="+newunionPay);
		
	}	
		
	public void genLogin()
	{
		String sql = String.format("select * from register where packet_id='%s' and date='%s' ", this.packet_id,this.date);
		RegisterDao registerDao = new RegisterDao(); 
		ArrayList<Object> registerList = registerDao.getList(sql);
		//ArrayList<Object> registerList = null;
		int count = 0;
		int dayoff = 0;
		int daystep = (this.day1Num-this.day7Num+3)/6;
		if(daystep < 1)
		{
			daystep = 1;
		}
		String datestr=date;
		Date curDate= DateUtil.strToDate(date);
		Date idate;
		
		//产生当天新用户登录数据
		count = registerList.size();
		genLogin(registerList,count,datestr,this.packet_id);
		
		
		//产生次留登录
		dayoff = 1;
		idate=DateUtil.addDate("dd", dayoff, curDate);
		String day1datest=datestr = DateUtil.getDate(idate);
		//count = this.loginNum-this.registerNum;
		sql = String.format("select * from register where packet_id='%s' and  date='%s' and device_id in (SELECT device_id from activate WHERE  packet_id='%s' and  date='%s' and dateo=1) ", this.packet_id,datestr, this.packet_id,datestr);
		DebuUtil.log("产生次留登录sql:"+sql);
		registerList = registerDao.getList(sql);
		int day1count = count= registerList.size();
		genLogin(registerList,count,day1datest,this.packet_id);
		
		//产生周留登录
		dayoff = 7;
		idate=DateUtil.addDate("dd", dayoff, curDate);
		String day7datest=datestr = DateUtil.getDate(idate);
		//count = this.loginNum-this.registerNum;
		sql = String.format("select * from register where packet_id='%s' and  date='%s' and device_id in (SELECT device_id from activate WHERE  packet_id='%s' and  date='%s' and dates=1) ", this.packet_id,datestr, this.packet_id,datestr);
		DebuUtil.log("产生周留登录sql:"+sql);
		registerList = registerDao.getList(sql);
		int day7count = count = registerList.size();
		genLogin(registerList,count,day7datest,this.packet_id);
		
		//产生月留登录
		dayoff = 30;
		idate=DateUtil.addDate("dd", dayoff, curDate);
		String day30datest=datestr = DateUtil.getDate(idate);
		//count = this.loginNum-this.registerNum;
		sql = String.format("select * from register where packet_id='%s' and  date='%s' and device_id in (SELECT device_id from activate WHERE  packet_id='%s' and  date='%s' and datem=1) ", this.packet_id,datestr, this.packet_id,datestr);
		DebuUtil.log("产生月留登录sql:"+sql);
		registerList = registerDao.getList(sql);
		int day30count =count = registerList.size();
		genLogin(registerList,count,day30datest,this.packet_id);
		
		//产生旧用户登录
		dayoff = -10;
		idate=DateUtil.addDate("dd", dayoff, curDate);
		datestr = DateUtil.getDate(idate);
		LoginTempDao loginTempDao = new LoginTempDao();
		count = this.loginNum-loginTempDao.getCount(packet_id, date);//-day1count-day6count-day30count;
		sql = String.format("select * from register where packet_id='%s' and  date>='%s' and date<'%s' and date<>'%s'  and date<>'%s'  and date<>'%s' limit %d ",this.packet_id,datestr,this.date,day1datest,day7datest,day30datest,count);
		DebuUtil.log("产生旧用户登录sql:"+sql);
		registerList = registerDao.getList(sql);
		count = registerList.size();
		genLogin(registerList,count,date,this.packet_id);
		
		/*for(int i=0; i < 30; i ++)//8天
		{
			dayoff = i;
			if(i == 0)
			{
				count = this.registerNum;
			}
			else if(i == 1)
			{
			   count = this.day1Num;
			}
			else if(i <= 9)
			{
				count = count-daystep;
			}
			
			if(i == 6)
			{
				daystep = (daystep-daystep*2/10);				
			}
			idate=DateUtil.addDate("dd", dayoff, curDate);
			datestr = DateUtil.getDate(idate);
			genLogin(registerList,count,datestr,this.packet_id);
		}*/
		
		
	}
	
	void genLogin(ArrayList<Object> registerList,int count,String date,String pakcet_id)
	{                   
		
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days < 0)
		{
			return;
		}
		 Register register = null;
		 LoginTempDao loginTempDao = new LoginTempDao();	
		 DebuUtil.log("genLogin packet_id:"+packet_id+" date:"+date+"count:"+count);
		 if(count > registerList.size())
		 {
			 count = registerList.size();
		 }
	    for(int i=0;i<count;i++)
	    {
		   register=(Register)registerList.get(i);	
		   String time = "";
		   String rDate = register.date;
		   if(rDate.equals(date))
		   {
			   time = register.time;
		   }
		   LoginTemp login = new LoginTemp(packet_id,register.username,register.device_id,date,time);
		   loginTempDao.add(login);
		   DebuUtil.log("genLogin username:"+register.username+" date:"+date+"time:"+time);
	    }
	}
	

	public void genPayUserLogin()
	{
		String sql = String.format("select * from pay where packet_id='%s' and date='%s' and state=1 ", this.packet_id,this.date);
		PayDao payDao = new PayDao(); 
		ArrayList<Pay> payList = payDao.getList(sql);
		//ArrayList<Object> registerList = null;
		int count = 0;
		int dayoff = 0;
		int daystep = (this.day1Num-this.day7Num+3)/6;
		if(daystep < 1)
		{
			daystep = 1;
		}
		String datestr=date;
		Date curDate= DateUtil.strToDate(date);
		Date idate;
		
		LoginTempDao loginTempDao = new LoginTempDao();
		
		for(Pay pay:payList)
		{
			int LogindayCount = NumberUtil.getRandom(10, 20);
			for(int i=0; i < LogindayCount; i ++)//生成X天登录
			{
				dayoff = i;
				idate=DateUtil.addDate("dd", dayoff, curDate);
				datestr = DateUtil.getDate(idate);
				
				int loginTime =  NumberUtil.getRandom(2, 5);
				for(int j=0; j < loginTime; j ++)//生成一天X次登录
				{
					String  time = "";
					if(j == 0)
					{
						time = NumberUtil.getRandom(8, 10)+":"+NumberUtil.getRandom(0, 59)+":"+NumberUtil.getRandom(0, 59);
					}
					else if(j == 1)
					{
						time = NumberUtil.getRandom(5, 7)+":"+NumberUtil.getRandom(0, 59)+":"+NumberUtil.getRandom(0, 59);
					}
					LoginTemp login = new LoginTemp(packet_id,pay.username,pay.device_id,datestr,time);
					loginTempDao.add(login);
					DebuUtil.log("genLogin username:"+pay.username+" datestr:"+date+"time:"+time);
				}
			}
		}
		
		
		
	}
	

	
	public void changeIMEI()
	{
		/*DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"changeIMEI");
		//处理激活数
		
		PayDao payDao = new PayDao();
		String sql = String.format("select * from pay where packet_id='%s' and date='%s' and state=1 ",this.packet_id,this.date);//where packet_id='217150001'
		ArrayList<Object> payList = payDao.getList(sql);	
		
		//修改用户名
		if(payList.size() > 0)
		{
			for(int i = 0;i < payList.size(); i++)
			{
				Pay pay = (Pay)payList.get(i);
				String device_id = "86"+NumberUtil.getRandom(0, 9)+StringUtil.getNo(12);
				DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"device_id="+device_id);
				
				sql = String.format("update activate set device_id='%s' where device_id='%s'", device_id,pay.device_id);	
				payDao.executeUpdate(sql);
				sql = String.format("update pay set device_id='%s' where device_id='%s'", device_id,pay.device_id);	
				payDao.executeUpdate(sql);
				sql = String.format("update register set device_id='%s' where device_id='%s'", device_id,pay.device_id);	
				payDao.executeUpdate(sql);
				//sql = String.format("update login set device_id='%s' where device_id='%s'", device_id,pay.device_id);	
				//payDao.executeUpdate(sql);
				
			}
		}
		
		DebuUtil.log("newaliPay="+newaliPay+" newyeePay="+newyeePay +"newtenPay="+newtenPay +"newunionPay="+newunionPay);*/
		
	}	
	
	public void makeRealUser()
	{
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"makeRealUser");
		
		ActivateDao activateDao = new ActivateDao(); 
		String sql = String.format("select * from activate where packet_id='%s' and date='%s' ",this.packet_id,this.date);//where packet_id='217150001'
		ArrayList<Object> activateList = activateDao.getList(sql);	
		int count = 0;//
		if(activateList.size() > 0)
		{
			for(int i = 0;i < activateList.size(); i++)
			{
				Activate activate = (Activate)activateList.get(i);
				DebuUtil.log("device_id="+activate.device_id);		
				
				if(activate.dateo == 1)
				{					
					activate.level = NumberUtil.getRandom(10, 35);
					activate.onlinetime = NumberUtil.getRandom(300, 10050);
					activateDao.edit(activate);
				}				
											
			}
		}
		
	}
	
	public void changeTime()
	{
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"changeTime");
		
		//处理激活数
		
		PayDao payDao = new PayDao();
		String sql = String.format("select * from pay where packet_id='%s' and date='%s' and state=1 ",this.packet_id,this.date);//where packet_id='217150001'
		ArrayList<Object> payList = payDao.getList(sql);	
		
		//修改用户名
		if(payList.size() > 0)
		{
			for(int i = 0;i < payList.size(); i++)
			{
				Pay pay = (Pay)payList.get(i);
				DebuUtil.log("device_id="+pay.device_id);
				
				ActivateDao activateDao = new ActivateDao(); 
				Activate activate = activateDao.getRecord(pay.device_id);
				if(activate != null)
				{
					activate.time = Register.reduseTime(pay.date, pay.time);
					activateDao.edit(activate);
				}
				
				RegisterDao registerDao = new RegisterDao(); 
				Register register = registerDao.getRegisterRecordByDev(pay.device_id);
				if((register != null) && (activate != null))
				{
					 register.time = Register.addTime(activate.date, activate.time);
					 registerDao.edit(register);
					 
					 
					 //Login login = new Login(packet_id,register.username,register.device_id,date,register.time);
					 //loginDao.add(login);
				}
								
			}
		}
		
		//修改时间
		
		ActivateDao activateDao = new ActivateDao(); 
		sql = String.format("select * from activate where packet_id='%s' and date='%s' ",this.packet_id,this.date);//where packet_id='217150001'
		ArrayList<Object> activateList = activateDao.getList(sql);	
		if(activateList.size() > 0)
		{
			for(int i = 0;i < activateList.size(); i++)
			{
				Activate activate = (Activate)activateList.get(i);
				DebuUtil.log("device_id="+activate.device_id);
				
				RegisterDao registerDao = new RegisterDao(); 
				Register register = registerDao.getRegisterRecordByDev(activate.device_id);
				if(register != null)
				{
					register.time = Register.addTime(activate.date, activate.time);
					registerDao.edit(register);
				}
				if(activate.dateo == 1)
				{
					activate.loginDate = Register.addDay(activate.date, NumberUtil.getRandom(1, 35));
					activate.level = NumberUtil.getRandom(10, 35);
					activate.onlinetime = NumberUtil.getRandom(300, 10050);
				}
				if(activate.dates == 1)
				{
					activate.loginDate = Register.addDay(activate.date, NumberUtil.getRandom(7, 35));
					activate.level = NumberUtil.getRandom(10, 35);
					activate.onlinetime = NumberUtil.getRandom(300, 10050);
				}
				if(activate.datem == 1)
				{
					activate.loginDate = Register.addDay(activate.date, NumberUtil.getRandom(30, 35));
					activate.level = NumberUtil.getRandom(10, 35);
					activate.onlinetime = NumberUtil.getRandom(300, 10050);
				}
				if(StringUtil.is_nullString(activate.loginDate))
				{
					activate.loginDate = null;
				}
				activateDao.edit(activate);
											
			}
		}
		
		
		
	}	
	
	
	public void changeLoginNum()
	{
		Date lastdate = DateUtil.strToDate(this.date);
		lastdate=DateUtil.addDate("dd", -1, lastdate);	
		ChannelDataDao channelDataDao = new ChannelDataDao();
		ChannelData channelData =channelDataDao.getRecord(this.packet_id, DateUtil.getDate(lastdate));
		if(channelData != null)
		{
			this.loginNum = channelData.loginNum*NumberUtil.getRandom(85, 95)/100+this.registerNum;//
			DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"loginNum="+this.loginNum);
		}
	}
	
	public void genMothon()
    {
    	DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+" revise");	
		//this.day30Num = this.dayAllAcount*NumberUtil.getRandom(3, 5)/100;	
    	int day30_num = this.day30Num;
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"day30Num="+this.day30Num);
		RegisterDao registerDao = new RegisterDao();
		if(day30_num > 0)
		{
		    String str = String.format("update activate set datem=1 where packet_id='%s' and date='%s' and (dates=1 or dateo=1) order by device_id limit %d ",this.packet_id,this.date,day30_num);//where packet_id='217150001'
		    registerDao.executeUpdate(str);
		    DebuUtil.log(str);
		}
    }
	
	
	public void changeUserName()
	{
		DebuUtil.log("packet_id="+this.packet_id+" date="+this.date+"changeUser");//
		//处理激活数
		
		PayDao payDao = new PayDao();
		String sql = String.format("select * from pay where packet_id='%s' and date='%s' and state=1 ",this.packet_id,this.date);//where packet_id='217150001'
		ArrayList<Object> payList = payDao.getList(sql);	
		this.dayAllPayAcount = payList.size();//修改付费数
		DebuUtil.log("dayAllPayAcount="+this.dayAllPayAcount);
		//修改用户名
		if(dayAllPayAcount > 0)
		{
			RegisterDao registerDao = new RegisterDao();
			
			
			for(int i = 0;i < payList.size(); i++)
			{
				Pay pay = (Pay)payList.get(i);
				//if(map.get(pay.username) == null)
				{
					Register register = registerDao.getRegisterRecord(pay.username);
					int lenth = NumberUtil.getRandom(6, 12);;	
					
					register.username = StringUtil.getNo(lenth);
					registerDao.edit(register);
					
					DebuUtil.log("changeuser packet_id="+this.packet_id+" date="+this.date +"username="+pay.username);
					//map.put(pay.username, pay.username);
					pay.username = register.username;
					payDao.edit(pay);
					
				}				
				
			}
		}
		
		
	}	
	
	
	
}
