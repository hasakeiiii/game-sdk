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

public class GamePayDataGen implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8239216521504814260L;

	public String date;
	public Integer aliPay;
	public Integer tenPay;
	public Integer yeePay;
	public Integer mmPay;
	public Integer tcPay;
	public Integer uniPay;
	public Integer unionPay;
	
	public Integer newaliPay;
	public Integer newtenPay;
	public Integer newyeePay;
	public Integer newmmPay;
	public Integer newtcPay;
	public Integer newuniPay;
	public Integer newunionPay;
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
	
	public Integer allPayBefore;
	
	public Integer allRegisterNumBefore;
	public Integer allPayAccoutNumBefore;
	
	public static String  yisheng="357100001";
	public static String  jiyou="358100001";
	
	public static void settleAll()
	{
		ArrayList List = new ArrayList(); 
		List.add(2);
		List.add(3);
		List.add(4);
		List.add(5);
		List.add(6);
		List.add(7);
		/*List.add(8);*/
		for(int i=0;i<List.size();i++)
		{
			int month = (Integer)List.get(i);
			String bDate = String.format("2014-0%d-01", month);
			
			int day = DateUtil.getDaysByMonth(2014, Integer.valueOf(month));
			
			String eDate = String.format("2014-0%d-%d", month,day);
			
			CooperationDao dao = new CooperationDao();
			ArrayList<Object> cooperationlist = null;
			
			
			cooperationlist = dao.getRecordsByAppNoChannel("","","","","");//211152001
			
			for(int index=0;index<cooperationlist.size();index++)
			{
			  Cooperation cooperation = (Cooperation)cooperationlist.get(index);
			  
			  int paycount =0;
			  int logincount = 0;
			  
			  PayDao payDao = new PayDao();
			  paycount = payDao.getAllpayByDate("","","",cooperation.packet_id,  bDate,eDate);
			  if(paycount < 1)
			  {
			   // LoginDao loginDao = new LoginDao();
			   // logincount = loginDao.getLoginNum("","","",cooperation.packet_id, bDate,eDate);
			  }
			  
			  if((logincount > 0) || (paycount >0))
			    GamePayDataGen.get_GamedataList("","","",cooperation.packet_id,"","A",bDate,eDate,0);
			}
			//GamePayData.get_GamedataList("","","","206151001","","",bDate,eDate);
			
		    //GamePayData.get_GamedataList("","","","","","",bDate,eDate);
		}
	}
	
	public static void checkAll()
	{
		ArrayList List = new ArrayList(); 
		//List.add(2);
		//List.add(3);
		/*List.add(4);
		List.add(5);
		List.add(6);
		List.add(7);*/
		List.add(8);
		for(int i=0;i<List.size();i++)
		{
			int month = (Integer)List.get(i);
			String bDate = "2014-08-26";//String.format("2014-0%d-01", month);
			
			int day = DateUtil.getDaysByMonth(2014, Integer.valueOf(month));
			if(month == 8)
			{
				//day = 1;
			}
			String eDate ="2014-08-26";//String.format("2014-0%d-%2d", month,day);
			//36566
			CooperationDao dao = new CooperationDao();
			ArrayList<Object> cooperationlist = null;
			
			
			cooperationlist = dao.getRecordsByAppNoChannel("","","","","");//211152001,222150001,239152001,203152001
			
			for(int index=0;index<cooperationlist.size();index++)
			{
			  Cooperation cooperation = (Cooperation)cooperationlist.get(index);
			  
			  int paycount =0;
			  int logincount = 0;
			  
			  PayDao payDao = new PayDao();
			  paycount = payDao.getAllpayByDate("","","",cooperation.packet_id,  "2014-08-01","2014-08-21");
			  if(paycount < 1)
			  {
			   // LoginDao loginDao = new LoginDao();
			   // logincount = loginDao.getLoginNum("","","",cooperation.packet_id, bDate,eDate);
			  }
			  
			    if((logincount > 0) || (paycount >0))
			    GamePayDataGen.get_GamedataList("","","",cooperation.packet_id,"","",bDate,eDate,1);
			}
			//GamePayData.get_GamedataList("","","","206151001","","",bDate,eDate);
			
		    //GamePayData.get_GamedataList("","","","","","",bDate,eDate);
		}
	}
	
	public static void requryAll()
	{
		ArrayList List = new ArrayList(); 
		//List.add(2);
		/*List.add(3);
		List.add(4);
		List.add(5);
		List.add(6);
		List.add(7);*/
		List.add(8);
		for(int i=0;i<List.size();i++)
		{
			int month = (Integer)List.get(i);
			String bDate = "2014-08-25";// String.format("2014-0%d-01", month);
			
			int day = DateUtil.getDaysByMonth(2014, Integer.valueOf(month));
			
			String eDate = "2014-08-26";//String.format("2014-0%d-%d", month,day);
			
			CooperationDao dao = new CooperationDao();
			ArrayList<Object> cooperationlist = null;
			
			
			cooperationlist = dao.getRecordsByAppNoChannel("","","","","");//211152001,222150001,239152001
			
			for(int index=0;index<cooperationlist.size();index++)
			{
			  Cooperation cooperation = (Cooperation)cooperationlist.get(index);
			  
			  int paycount =0;
			  int logincount = 0;
			  
			  PayDao payDao = new PayDao();
			  paycount = payDao.getAllpayByDate("","","",cooperation.packet_id, "2014-08-01","2014-08-21");
			  if(paycount < 1)
			  {
			   // LoginDao loginDao = new LoginDao();
			   // logincount = loginDao.getLoginNum("","","",cooperation.packet_id, bDate,eDate);
			  }
			  
			  if((logincount > 0) || (paycount >0))
			    GamePayDataGen.get_GamedataList("","","",cooperation.packet_id,"","",bDate,eDate,0);
			}
			//GamePayData.get_GamedataList("","","","206151001","","",bDate,eDate);
			
		    //GamePayData.get_GamedataList("","","","","","",bDate,eDate);
		}
	}
	
	public GamePayDataGen(String game_id,String BusinesserNo,
			String channel,String packet_id,
			String date,String channelType,
			String dispaytype,
			int bCheck)
	{
		
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
		newaliPay=0;
		newyeePay=0;
		newmmPay=0;
		newtcPay=0;
		newuniPay=0;
		newtenPay=0;
		newunionPay=0;
		
		allPayBefore=0;
		
		allRegisterNumBefore=0;
		allPayAccoutNumBefore=0;
		
		//if(ConstValue.OPTIMIZE == 1)
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
				list = dao.getRecordsByAppNoChannel("",game_id,BusinesserNo,channel,packet_id);
			}
			
			
			if(list.size() > 0)
			{
				for(int i=0;i<list.size();i++)
				{
				  Cooperation cooperation = (Cooperation)list.get(i);
				  if(StringUtil.is_nullString(channelType))
				  {
					  channelType = cooperation.settle_type;
				  }
			      //calculate(game_id,BusinesserNo,channel,cooperation.packet_id,date);
				  if(dispaytype.equals("A"))
				  {
					  settle(cooperation.app_no,cooperation.business_no,cooperation.channel_no,cooperation.packet_id,date, channelType,dispaytype);
				  }
				  else
				  {
				     calculate(cooperation.app_no,cooperation.business_no,cooperation.channel_no,cooperation.packet_id,date, channelType,dispaytype,bCheck);
				  }
				}
			}
		}
		/*else
		{
		   calculate(game_id,BusinesserNo,channel,packet_id,date, channelType,dispaytype);
		}*/
	}
	
	
	public void calculate_yisheng(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype)
	{
		//广州怡圣

	}

	public void calculate_ruixun(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype)
	{

		
	}

	public void calculate_puyu(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype)
	{

			
		
	}
	
	public void settle(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype)
	{
		ChannelDataDao channelDataDao = new ChannelDataDao();
		ChannelData channelData = channelDataDao.getRecord(packet_id, date);
		if(channelData != null)
		{
			if(dispaytype.equals("A"))
			{
				//&& (channelType.equals("CPA")||channelType.equals("CPA_R"))				
		     	if(channelType.equals("CPA"))
				{
					ActivateDao activateDao = new ActivateDao();
					 if((channelData.activityNum > 0) && (channelData.settle == 0))
				    channelData.settle = activateDao.get_actNum(game_id,BusinesserNo,channel,packet_id,date,channelType,dispaytype,0);
				}
				else if(channelType.equals("CPA_R"))
				{
				   RegisterDao registerDao = new RegisterDao();
				   if((channelData.registerNum > 0) && (channelData.settle == 0))
				   channelData.settle = registerDao.getRegisterNum(game_id,BusinesserNo,channel,packet_id,date,channelType,dispaytype,0);
				}
				else if(channelType.equals("CPS"))
				{
					PayDao payDao = new PayDao();
					int count = channelData.aliPay+channelData.yeePay+channelData.mmPay+channelData.tenPay+channelData.unionPay;
					int settl_count = channelData.settle_aliPay+channelData.settle_yeePay+channelData.settle_mmPay+channelData.settle_tenPay+channelData.settle_unionPay ;
					if(count > 0 && settl_count == 0)
					{
						channelData.settle_aliPay = payDao.getpayNum(game_id,packet_id,date,"alipay",channelType,dispaytype);
						channelData.settle_yeePay = payDao.getpayNum(game_id,packet_id,date,"yeepay",channelType,dispaytype);
						channelData.settle_mmPay = payDao.getpayNum(game_id,packet_id,date,"mmpay",channelType,dispaytype);
						channelData.settle_tcPay = payDao.getpayNum(game_id,packet_id,date,"tcpay",channelType,dispaytype);
						channelData.settle_uniPay = payDao.getpayNum(game_id,packet_id,date,"unipay",channelType,dispaytype);
						channelData.settle_tenPay = payDao.getpayNum(game_id,packet_id,date,"tenpay",channelType,dispaytype);
						channelData.settle_unionPay = payDao.getpayNum(game_id,packet_id,date,"unionpay",channelType,dispaytype);
					}
				}
				channelDataDao.edit(channelData);
			}
			
			
		}
	}
	
	public void calculate(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype,int bCheck)
	{	
		ChannelDataDao channelDataDao = new ChannelDataDao();
		ChannelData channelData = null;
		if(bCheck != 1)
		{
			channelData = channelDataDao.getRecord(packet_id, date);
			if(channelData != null)
			{
				date = channelData.date;
				
				//channelData.get_settle();
				aliPay = channelData.aliPay;
				tenPay = channelData.tenPay;
				yeePay = channelData.yeePay;
				mmPay = channelData.mmPay;
				tcPay = channelData.tcPay;
				uniPay = channelData.uniPay;
				unionPay = channelData.unionPay;
				
				newaliPay = channelData.newaliPay;
				newtenPay = channelData.newtenPay;
				newyeePay = channelData.newyeePay;
				newmmPay = channelData.newmmPay;
				newtcPay = channelData.newtcPay;
				newuniPay = channelData.newuniPay;
				newunionPay = channelData.newunionPay;
				activityNum = channelData.activityNum;
				activityRegNum = channelData.activityRegNum;
				registerNum = channelData.registerNum;	
				realregisterNum = channelData.realRegisterNum;	
				day1Num = channelData.day1Num;	
				day7Num = channelData.day7Num;		
				day30Num = channelData.day30Num;
				loginNum = channelData.loginNum;
				liveNum = channelData.liveNum;			
				allPayBefore = channelData.allPayBefore;	
				allRegisterNumBefore = channelData.allRegisterNumBefore;
				allPayAccoutNumBefore = channelData.allPayAccoutNumBefore;								
				return;
			}
		}
		else
		{
			channelData = ChannelDataList.getChannelData(packet_id, date);
		}
			
		
			LoginDao loginDao = new LoginDao();
			int num = loginDao.getLoginNum(game_id,BusinesserNo,channel,packet_id, date);
			int allpayNumBefore = 0;
			int CPA = 0;
			int bCalculate = 1;
			
		
			if(dispaytype.equals("A") && (channelType.equals("CPA")||channelType.equals("CPA_R")))
			{
				CPA = 1;
			}
			
			
			//////////////////////////////////////////////////////////
			PayDao payDao = new PayDao();
			allpayNumBefore = payDao.getAllpayNumBefore(game_id,BusinesserNo,channel,packet_id,date);
			
			
			if(allpayNumBefore > 0)
			{
				
			}
			else
			{
				if(num < 1)
				{
					bCalculate = 0;
				}
			}
			
			
			
			if(bCalculate == 1)
			{
				//DebuUtil.log("packet_id:"+packet_id);
				ActivateDao activateDao = new ActivateDao();
				if(CPA != 1)
				{
					day1Num += activateDao.get_1dateNum(game_id,BusinesserNo,channel, packet_id, date);
					day7Num += activateDao.get_7dateNum(game_id, BusinesserNo,channel,packet_id, date);
					day30Num += activateDao.get_30dateNum(game_id, BusinesserNo,channel,packet_id, date);
				}
				
				activityNum += activateDao.get_actNum(game_id,BusinesserNo,channel,packet_id,date,channelType,dispaytype,0);
				if(CPA != 1)
				activityRegNum += activateDao.getActivityRegNum(game_id,BusinesserNo,channel,packet_id,date);
				
				RegisterDao registerDao = new RegisterDao();
				registerNum += registerDao.getRegisterNum(game_id,BusinesserNo,channel,packet_id,date,channelType,dispaytype,0);
				if(CPA != 1)
				realregisterNum += registerDao.getRealRegisterNum(game_id,BusinesserNo,channel,packet_id, date);
				
				if(CPA != 1)
				allRegisterNumBefore += registerDao.getAllRegisterNumBefore(game_id,BusinesserNo,channel, packet_id, date);
				
			
				if(CPA != 1)
				{
					//LoginDao loginDao = new LoginDao();
					//num = loginDao.getLoginNum(game_id, packet_id, date);
					loginNum += num;
					
					//PayDao payDao = new PayDao();
					//allPayBefore += payDao.getAllpayNumBefore(game_id,packet_id,date);
					allPayBefore += allpayNumBefore;
					allPayAccoutNumBefore += payDao.getAllPayAccountNumBefore(game_id,BusinesserNo,channel, packet_id, date);
					aliPay+=payDao.getComPayNum(game_id,BusinesserNo,channel,packet_id,date,"alipay");
					yeePay+=payDao.getComPayNum(game_id,BusinesserNo,channel,packet_id,date,"yeepay");
					mmPay+=payDao.getComPayNum(game_id,BusinesserNo,channel,packet_id,date,"mmpay");
					tcPay+=payDao.getComPayNum(game_id,BusinesserNo,channel,packet_id,date,"tcpay");
					uniPay+=payDao.getComPayNum(game_id,BusinesserNo,channel,packet_id,date,"unipay");
					tenPay += payDao.getComPayNum(game_id,BusinesserNo,channel,packet_id,date,"tenpay");
					unionPay += payDao.getComPayNum(game_id,BusinesserNo,channel,packet_id,date,"unionpay");
					if(!StringUtil.is_nullString(packet_id))
					{
					   newaliPay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"alipay");
					   newtenPay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"tenpay");
					   newyeePay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"yeepay");
					   newmmPay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"mmpay");
					   newtcPay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"tcpay");
					   newuniPay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"unipay");
					   newunionPay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"unionpay");
					}
				}
				
				
			}
		
		
		//if(ConstValue.OPTIMIZE == 1)		
		//{
			//ChannelDataDao channelDataDao = new ChannelDataDao();
		    int iNew = 0;
		    if(channelData == null)
		    {
			    channelData = channelDataDao.createRecord(packet_id, date);
			    iNew = 1;
		    }
		    if(bCheck != 1)
		    {
		    	channelData.date = date;
				channelData.aliPay = aliPay;
				channelData.tenPay = tenPay;
				channelData.yeePay = yeePay;
				channelData.mmPay = mmPay;
				channelData.tcPay = tcPay;
				channelData.uniPay = uniPay;
				channelData.unionPay = unionPay;
				channelData.newaliPay = newaliPay;
				channelData.newtenPay = newtenPay;
				channelData.newyeePay = newyeePay;
				channelData.newmmPay = newmmPay;
				channelData.newtcPay = newtcPay;
				channelData.newuniPay = newuniPay;
				channelData.newunionPay = newunionPay;
				channelData.activityNum = activityNum;
				channelData.activityRegNum = activityRegNum;
				channelData.registerNum = registerNum;	
				channelData.realRegisterNum = realregisterNum;	
				channelData.day1Num = day1Num;	
				channelData.day7Num = day7Num;		
				channelData.day30Num = day30Num;
				channelData.loginNum = loginNum;
				channelData.liveNum = liveNum;			
				channelData.allPayBefore = allPayBefore;	
				channelData.allRegisterNumBefore = allRegisterNumBefore;
				channelData.allPayAccoutNumBefore = allPayAccoutNumBefore;
		    }
		    else
		    {
				channelData.setDate(date);
				channelData.setAliPay2(aliPay);
				channelData.setTenPay2(tenPay);
				channelData.setYeePay2(yeePay);
				channelData.setMmPay2(mmPay);
				channelData.setTcPay2(tcPay);
				channelData.setUniPay2(uniPay);
				channelData.setUnionPay2(unionPay);
				channelData.setNewaliPay2(newaliPay);
				channelData.setNewtenPay2(newtenPay);
				channelData.setNewyeePay2(newyeePay);
				channelData.setNewmmPay2(newmmPay);
				channelData.setNewtcPay2(newtcPay);
				channelData.setNewuniPay2(newuniPay);
				channelData.setNewunionPay2(newunionPay);
				channelData.setActivityNum2(activityNum);
				channelData.setActivityRegNum2(activityRegNum);
				channelData.setRegisterNum2(registerNum);
				channelData.setRealRegisterNum2(realregisterNum);
				channelData.setDay1Num2(day1Num);	
				channelData.setDay7Num2(day7Num);		
				channelData.setDay30Num2(day30Num);
				channelData.setLoginNum2(loginNum);
				channelData.setLiveNum2(liveNum);			
				channelData.setAllPayBefore2(allPayBefore);	
				channelData.setAllRegisterNumBefore2(allRegisterNumBefore);
				channelData.setAllPayAccoutNumBefore2(allPayAccoutNumBefore);
		    }
			Integer temp = null;
			channelData.settle(temp);
			
			int datepay = channelData.aliPay+channelData.tenPay+channelData.yeePay+channelData.mmPay;
			//if((datepay > 0) || (channelData.registerNum > 0))
			if(bCheck != 1)
			{
				channelDataDao.AddRecord(channelData);
			}
			else
			{
				channelDataDao.edit(channelData);
			}
		//}
		
	}
	
	
	public static  ArrayList<GamePayDataGen> get_GamedataList_gen(String game_id,String BusinesserNo,String channel,
			String packet_id,String channelType,String dispaytype,String begindate,String enddate,int bCheck)
	{
		ArrayList<GamePayDataGen> ret = new ArrayList<GamePayDataGen>();
		//DebuUtil.log("get_GamedataList");
		LiveDao liveDao = new LiveDao();
		ArrayList<String> dateList;// = liveDao.get_DateList(game_id,channel,packet_id,null,null);
		//int bCalculate = 1;
		dateList = liveDao.get_DateList(begindate,enddate);
		
		//Date curdate = DateUtil.strToDate(DateUtil.getDate());//new Date();
		//Date idate = DateUtil.addDate("dd", -1, curdate);
		//String strDate = DateUtil.getDate(idate);
		
		/*PayDao payDao = new PayDao();
		int paynum = payDao.getAllpayNumBefore(game_id,packet_id,enddate);
		
		if(paynum < 1)
		{
			 RegisterDao registerDao = new RegisterDao();
			 int allRegisterNumBefore = registerDao.getAllRegisterNumBefore(game_id, packet_id, enddate);
			 if(allRegisterNumBefore < 1)
			 {
				 bCalculate = 0;
			 }
		}*/
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
				if(game_id.equals("1000")&&channel.equals("357"))
				{
					packet_id = GamePayDataGen.yisheng;
				}
				else if(game_id.equals("1000")&&channel.equals("358"))
				{
					packet_id = GamePayDataGen.jiyou;
				}
				//ret.add(new GamePayData(game_id, BusinesserNo,channel,packet_id,datestr,channelType,dispaytype));				
			      ret.add(new GamePayDataGen(game_id, BusinesserNo,channel,packet_id,datestr,channelType,dispaytype,bCheck));
				
			}
			// time = DateUtil.getTime();
			//DebuUtil.log("时间1:"+time);
		}
		return  ret;
	}
	
	public static  ArrayList<GamePayDataGen> get_GamedataList(String game_id,String BusinesserNo,String channel,
			String packet_id,String channelType,String dispaytype,String begindate,String enddate,int bCheck)
	{
		ArrayList<GamePayDataGen> ret = new ArrayList<GamePayDataGen>();
		//DebuUtil.log("get_GamedataList");
		LiveDao liveDao = new LiveDao();
		ArrayList<String> dateList;// = liveDao.get_DateList(game_id,channel,packet_id,null,null);
		//int bCalculate = 1;
		dateList = liveDao.get_DateList(begindate,enddate);
		
		//Date curdate = DateUtil.strToDate(DateUtil.getDate());//new Date();
		//Date idate = DateUtil.addDate("dd", -1, curdate);
		//String strDate = DateUtil.getDate(idate);
		
		/*PayDao payDao = new PayDao();
		int paynum = payDao.getAllpayNumBefore(game_id,packet_id,enddate);
		
		if(paynum < 1)
		{
			 RegisterDao registerDao = new RegisterDao();
			 int allRegisterNumBefore = registerDao.getAllRegisterNumBefore(game_id, packet_id, enddate);
			 if(allRegisterNumBefore < 1)
			 {
				 bCalculate = 0;
			 }
		}*/
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
			      ret.add(new GamePayDataGen(game_id, BusinesserNo,channel,packet_id,datestr,channelType,dispaytype,bCheck));
				
			}
			// time = DateUtil.getTime();
			//DebuUtil.log("时间1:"+time);
		}
		return  ret;
	}
}

