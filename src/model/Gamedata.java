package model;

import java.util.ArrayList;
import java.util.Date;

import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;

import dao.ActivateDao;
import dao.CooperationDao;
import dao.LiveDao;
import dao.LoginDao;
import dao.PayDao;
import dao.RegisterDao;

public class Gamedata implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8239216521504814260L;

	public String date;
	public Integer aliPay;
	public Integer tenPay;
	public Integer yeePay;
	public Integer mmPay;
	public Integer newaliPay;
	public Integer newtenPay;
	public Integer newyeePay;
	public Integer newmmPay;
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
	
	public Gamedata(String game_id,String BusinesserNo,
			String channel,String packet_id,
			String date,String channelType,
			String dispaytype
			)
	{
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
		yeePay=0;
		mmPay=0;
		tenPay=0;
		newaliPay=0;
		newyeePay=0;
		newmmPay=0;
		newtenPay=0;
		
		allPayBefore=0;
		
		allRegisterNumBefore=0;
		allPayAccoutNumBefore=0;
		
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
		      calculate(cooperation.app_no,cooperation.channel_no,cooperation.packet_id,date,channelType,dispaytype);
			}
		}
	}
	
	public void calculate(String game_id,String channel,String packet_id,String date,String channelType,String dispaytype)
	{
		LoginDao loginDao = new LoginDao();
		int num = loginDao.getLoginNum(game_id, packet_id, date);
		int allpayNumBefore = 0;
		int CPA = 0;
		int bCalculate = 1;
		
		if(dispaytype.equals("A") && (channelType.equals("CPA")||channelType.equals("CPA_R")))
		{
			CPA = 1;
		}
		
		//////////////////////////////////////////////////////////
		PayDao payDao = new PayDao();
		allpayNumBefore = payDao.getAllpayNumBefore(game_id,packet_id,date);
		
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
				day1Num += activateDao.get_1dateNum(game_id, packet_id, date);
				day7Num += activateDao.get_7dateNum(game_id, packet_id, date);
				day30Num += activateDao.get_30dateNum(game_id, packet_id, date);
			}
			
			activityNum += activateDao.get_actNum(game_id,packet_id,date,channelType,dispaytype,0);
			if(CPA != 1)
			activityRegNum += activateDao.getActivityRegNum(game_id,packet_id,date);
			
			RegisterDao registerDao = new RegisterDao();
			registerNum += registerDao.getRegisterNum(game_id,packet_id,date,channelType,dispaytype,0);
			if(CPA != 1)
			realregisterNum += registerDao.getRealRegisterNum(game_id, packet_id, date);
			
			if(CPA != 1)
			allRegisterNumBefore += registerDao.getAllRegisterNumBefore(game_id, packet_id, date);
			
			if(CPA != 1)
			{
				//LoginDao loginDao = new LoginDao();
				//num = loginDao.getLoginNum(game_id, packet_id, date);
				loginNum += num;
				
				//PayDao payDao = new PayDao();
				//allPayBefore += payDao.getAllpayNumBefore(game_id,packet_id,date);
				allPayBefore += allpayNumBefore;
				allPayAccoutNumBefore += payDao.getAllPayAccountNumBefore(game_id, packet_id, date);
				aliPay+=payDao.getpayNum(game_id,packet_id,date,"alipay",channelType,dispaytype);
				yeePay+=payDao.getpayNum(game_id,packet_id,date,"yeepay",channelType,dispaytype);
				mmPay+=payDao.getpayNum(game_id,packet_id,date,"mmpay",channelType,dispaytype);
				tenPay+=payDao.getpayNum(game_id,packet_id,date,"tenpay",channelType,dispaytype);
				
				newaliPay+=payDao.getNewpay(game_id,packet_id,date,"alipay");
				newyeePay+=payDao.getNewpay(game_id,packet_id,date,"yeepay");
				newmmPay+=payDao.getNewpay(game_id,packet_id,date,"mmpay");
				newtenPay+=payDao.getNewpay(game_id,packet_id,date,"tenpay");
			}
			
		}
	}
	
	public static  ArrayList<Gamedata> get_GamedataList(String game_id,String BusinesserNo,String channel,
			String packet_id,String channelType,String dispaytype,String begindate,String enddate)
	{
		ArrayList<Gamedata> ret = new ArrayList<Gamedata>();
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
			
			//datestr=datestr.substring(0, 10);
			if((small >= 0) && (big <=0))
			{
			   ret.add(new Gamedata(game_id, BusinesserNo,channel,packet_id,datestr,channelType,dispaytype));
			}
		}
		return  ret;
	}
}
