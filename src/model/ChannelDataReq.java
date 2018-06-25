package model;

import java.util.ArrayList;
import java.util.Date;

import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import dao.ActivateDao;
import dao.ChannelDataDao;
import dao.CooperationDao;
import dao.LiveDao;
import dao.LoginDao;
import dao.PayDao;
import dao.RegisterDao;

public class ChannelDataReq implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 354555725899103395L;
	
	public String date;
	public Integer aliPay;
	public Integer tenPay;
	public Integer yeePay;
	public Integer mmPay;
	public Integer tcPay;
	public Integer uniPay;
	public Integer unionPay;
	public Integer mzpay;
	public Integer onlypay;
	public Integer openPay;
	public Integer webPay;
	public Integer boxPay;
	public Integer ldPay;
	public Integer jdPay;
	public Integer woplusPay;
	public Integer wxPay;
	public Integer ananPay;
	public Integer powerPay;
	public Integer sentPay;
	//TODO ADDPAYTYPE
	
	public Integer newaliPay;
	public Integer newtenPay;
	public Integer newyeePay;
	public Integer newmmPay;
	public Integer newtcPay;
	public Integer newuniPay;
	public Integer newunionPay;
	public Integer newmzpay;
	public Integer newonlypay;
	public Integer newopenPay;
	public Integer newwebPay;
	public Integer newboxPay;
	public Integer newldPay;
	public Integer newjdPay;
	public Integer newwoplusPay;
	public Integer newwxPay;
	public Integer newananPay;
	public Integer newpowerPay;
	public Integer newsentPay;
	//TODO ADDPAYTYPE
	
	public Integer activityNum;
	public Integer activityRegNum;
	public Integer registerNum;	
	public Integer realregisterNum;
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
	
	public Integer day1Pay;	
	public Integer day2Pay;	
	public Integer day3Pay;
	public Integer day4Pay;	
	public Integer day5Pay;	
	public Integer day6Pay;	
	public Integer day7Pay;	
	
	public Integer allPayBefore;
	
	public Integer allRegisterNumBefore;
	public Integer allPayAccoutNumBefore;
	
	public Integer dayAllPayAcount;
	public Integer dayAllAcount;
	
	public Integer pay_times;//支付次数
	public Integer day1_pay_times;//新用户第一天的付费次数	
	public Integer day1_pay_acount;	//新用户第一天的付费用户数	
	
	public ChannelDataReq(String game_type,String game_id,String BusinesserNo,
			String channel,String packet_id,
			String date,String channelType,
			String dispaytype
			)
	{
		
		//if(packet_id.equals("720265001"))
		DebuUtil.log("GamePayData packet_id:"+packet_id+" date:"+date);
		
		ArrayList<Object> list = null;
		
		this.date = date;
		//mbCalculate = bCalculate;
		day1Num = 0;
		day7Num = 0;
		day30Num = 0;
		
		activityNum = 0;
		activityRegNum = 0;
		registerNum = 0;
		realregisterNum = 0;
		
		loginNum = 0;
		liveNum = 0;
		
		aliPay=0;
		tenPay=0;
		yeePay=0;
		mmPay=0;
		tcPay=0;
		uniPay=0;
		unionPay=0;
		mzpay=0;
		onlypay=0;
		openPay=0;
		webPay=0;
		boxPay=0;
		ldPay=0;
		jdPay=0;
		woplusPay=0;
		wxPay = 0;
		ananPay = 0;
		powerPay = 0;
		sentPay = 0;
		//TODO ADDPAYTYPE

		newaliPay=0;
		newyeePay=0;
		newmmPay=0;
		newtcPay=0;
		newuniPay=0;
		newtenPay=0;
		newunionPay=0;
		newmzpay=0;
		newonlypay=0;
		newopenPay=0;
		newwebPay=0;
		newboxPay=0;
		newldPay=0;
		newjdPay=0;
		newwoplusPay=0;
		newwxPay = 0;
		newananPay = 0;
		newpowerPay = 0;
		newsentPay = 0;
		//TODO ADDPAYTYPE
		
		allPayBefore=0;
		
		allRegisterNumBefore=0;
		allPayAccoutNumBefore=0;
		
		dayAllPayAcount = 0;
		dayAllAcount = 0;
		
		msmNum = 0;
		feeNum = 0;
		
		reqNum = 0;
		reqOkNum = 0;
		
		day1Pay = 0;	
		day2Pay = 0;	
		day3Pay = 0;
		day4Pay = 0;	
		day5Pay = 0;	
		day6Pay = 0;	
		day7Pay = 0;	
		
		pay_times = 0;//支付次数
		day1_pay_times = 0;//新用户第一天的付费次数	
		day1_pay_acount = 0;//新用户第一天的付费用户数	
		
		if(ConstValue.OPTIMIZE == 1)
		{
			//if((channel != null) || (game_id != null))
			{
				CooperationDao dao = new CooperationDao();
				if(!StringUtil.is_nullString(packet_id))
				{
					game_id = null;
					channel = null;
					BusinesserNo = null;
				}
				list = dao.getRecordsByAppNoChannel(game_type,game_id,BusinesserNo,channel,packet_id);
			}
			
			
			if(list.size() > 0)
			{
				for(int i=0;i<list.size();i++)
				{
				  Cooperation cooperation = (Cooperation)list.get(i);
				  //if(StringUtil.is_nullString(channelType))
				  //{
					  channelType = cooperation.settle_type;
				 // }
			      //calculate(game_id,BusinesserNo,channel,cooperation.packet_id,date);
				  
				  calculate(cooperation.app_no,cooperation.business_no,cooperation.channel_no,cooperation.packet_id,date, channelType,dispaytype);
				}
			}
		}
		else
		{
		   calculate(game_id,BusinesserNo,channel,packet_id,date, channelType,dispaytype);
		}
	}
	
	public static boolean isShow(String date)
	{
		boolean ret = true;
		
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days == 0)
		{
			ret = false;
		}
		else if(days == 1)
		{
			
			Date now=new java.util.Date();
			if(now.getHours() < 11)
			{
				ret = false;
			}			
		}
		
		return ret;
		
	}
	
	public void calculate(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype)
	{
		
			ChannelDataDao channelDataDao = new ChannelDataDao();
			ChannelData channelData = channelDataDao.getRecord(packet_id, date);
		
			if(channelData != null)
			{
				date = channelData.date;
				
				//channelData.get_settle();
				
				DebuUtil.log("channelData.mmPay:"+ channelData.mmPay+"packet_id="+packet_id+"date="+date);
				newaliPay += channelData.newaliPay;
				newtenPay += channelData.newtenPay;
				newyeePay += channelData.newyeePay;
				newmmPay += channelData.newmmPay;
				newopenPay += channelData.newopenPay;
				newboxPay += channelData.newboxPay;
				newwebPay += channelData.newwebPay;
				newldPay += channelData.newldPay;
				newjdPay += channelData.newjdPay;
				newwoplusPay += channelData.newwoPay;
				newwxPay += channelData.newwxPay;
				newananPay += channelData.newananPay;
				newpowerPay += channelData.newpowerPay;
				newsentPay += channelData.newsentPay;
				//TODO ADDPAYTYPE
				if(ConstValue.STAND_ALONE == 1)
				{
					newtcPay += channelData.newtcPay;
					newuniPay += channelData.newuniPay;
				}
				newunionPay += channelData.newunionPay;
				newmzpay += channelData.newmzPay;
				newonlypay += channelData.newonlyPay;
				//activityNum += channelData.activityNum;
				activityRegNum += channelData.activityRegNum;
				//registerNum += channelData.registerNum;	
				realregisterNum += channelData.realRegisterNum;	
				day1Num += channelData.day1Num;	
				day7Num += channelData.day7Num;		
				day30Num += channelData.day30Num;
				loginNum += channelData.loginNum;
				liveNum += channelData.liveNum;			
				allPayBefore += channelData.allPayBefore;	
				allRegisterNumBefore += channelData.allRegisterNumBefore;
				allPayAccoutNumBefore += channelData.allPayAccoutNumBefore;
				
				dayAllPayAcount += channelData.dayAllPayAcount;
				dayAllAcount += channelData.dayAllAcount;
			
				//if(ConstValue.PAY_SERVER == 1)
				{
					msmNum += channelData.msmNum;
					feeNum += channelData.feeNum;
					
					reqNum += channelData.reqNum;
					reqOkNum += channelData.reqOkNum;
				}
				
				day1Pay += channelData.day1Pay;
				day2Pay += channelData.day2Pay;
				day3Pay += channelData.day3Pay;
				day4Pay += channelData.day4Pay;	
				day5Pay += channelData.day5Pay;	
				day6Pay += channelData.day6Pay;	
				day7Pay += channelData.day7Pay;	
				
				pay_times  += channelData.pay_times;
				day1_pay_times  += channelData.day1_pay_times;
				day1_pay_acount  += channelData.day1_pay_acount;
				//DebuUtil.log("date="+date+" packet_id:"+ packet_id+"mm_pay="+channelData.mmPay);
				//DebuUtil.log("activityNum:"+ activityNum+"packet_id="+packet_id+"date="+date);
				if(dispaytype.equals("A"))
				{
						
					DebuUtil.log("channelType="+channelType);
					if(channelType.equals("CPA"))
					{
						if(isShow(date))
						{
							activityNum += channelData.settle;
						}
					}
					else if(channelType.equals("CPA_R"))
					{
						if(isShow(date))
						{
							registerNum += channelData.settle;
						}
					}
					else if(channelType.equals("CPS"))
					{						
						aliPay += channelData.settle_aliPay;
						yeePay += channelData.settle_yeePay;
						mmPay += channelData.settle_mmPay ;
						
						tcPay += channelData.settle_tcPay ;
						uniPay += channelData.settle_uniPay;	
						
						tenPay += channelData.settle_tenPay;
						unionPay += channelData.settle_unionPay;
						mzpay += channelData.settle_mzPay;
						openPay += channelData.settle_openPay;
						webPay += channelData.settle_webPay;
						boxPay += channelData.settle_boxPay;
						ldPay += channelData.settle_ldPay;
						jdPay += channelData.settle_jdPay;
						woplusPay += channelData.settle_woPay;
						wxPay += channelData.settle_wxPay;
						ananPay += channelData.settle_ananPay;
						powerPay += channelData.settle_powerPay;
						sentPay += channelData.settle_sentPay;
						//TODO ADDPAYTYPE
						
						activityNum += channelData.activityNum;
						registerNum += channelData.registerNum;
						//DebuUtil.log("date="+date+" packet_id:"+ packet_id+"CPSmmPay="+mmPay);
					}
					//channelData.get_settle();
					//channelDataDao.edit(channelData);
				}
				else if(!dispaytype.equals("A"))
				{
					aliPay += channelData.aliPay;
					tenPay += channelData.tenPay;
					yeePay += channelData.yeePay;
					mmPay += channelData.mmPay;
					tcPay += channelData.tcPay;
					uniPay += channelData.uniPay;					
					unionPay += channelData.unionPay;					
					//2015-05-22
					mzpay += channelData.mzPay;					
					onlypay += channelData.onlyPay;					
					openPay += channelData.openPay;
					webPay += channelData.webPay;
					boxPay += channelData.boxPay;
					woplusPay += channelData.woPay;
					ldPay += channelData.ldPay;
					jdPay += channelData.jdPay;
					wxPay += channelData.wxPay;
					
					activityNum += channelData.activityNum;
					registerNum += channelData.registerNum;	
					
				}
				
				
				return;
			}
			
	}
	
	
	
	public static  ArrayList<ChannelDataReq> get_GamedataList(String game_type,String game_id,String BusinesserNo,String channel,
			String packet_id,String channelType,String dispaytype,String begindate,String enddate)
	{
		ArrayList<ChannelDataReq> ret = new ArrayList<ChannelDataReq>();
		//DebuUtil.log("get_GamedataList");
		LiveDao liveDao = new LiveDao();
		ArrayList<String> dateList;// = liveDao.get_DateList(game_id,channel,packet_id,null,null);
		//int bCalculate = 1;
		dateList = liveDao.get_DateList(begindate,enddate);
		
		for(String datestr:dateList)
		{
			
			long small = 0;
			long big = 0;
			
			//DebuUtil.log("datestr="+datestr);
			
			if((begindate!= null) && (enddate!= null))
			{
				small = DateUtil.getDayDiff(begindate, datestr);
				big = DateUtil.getDayDiff(enddate, datestr);
			}
			//String time = DateUtil.getTime();
			//DebuUtil.log("时间:"+time);
			//datestr=datestr.substring(0, 10);
			if((small >= 0) && (big <=0))
			{								
			      ret.add(new ChannelDataReq(game_type,game_id, BusinesserNo,channel,packet_id,datestr,channelType,dispaytype));
				
			}
			// time = DateUtil.getTime();
			//DebuUtil.log("时间1:"+time);
		}
		return  ret;
	}
	
	public static  ArrayList<ChannelDataReq> get_GamedataList(String game_id,String BusinesserNo,String channel,
			String packet_id,String channelType,String dispaytype,String begindate,String enddate)
	{
		ArrayList<ChannelDataReq> ret = get_GamedataList("", game_id, BusinesserNo, channel,
				 packet_id, channelType, dispaytype, begindate, enddate);
		return  ret;
	}
}
