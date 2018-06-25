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

public class GamePayData implements java.io.Serializable{

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
	public Integer newaliPay;
	public Integer newtenPay;
	public Integer newyeePay;
	public Integer newmmPay;
	public Integer newtcPay;
	public Integer newuniPay;
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
	public static String  ruixun="358100001";
	
	public static void requryAll()
	{
		ArrayList List = new ArrayList(); 
		/*List.add(1);
		List.add(2);
		List.add(3);*/
		List.add(4);
		List.add(5);
		List.add(6);
		List.add(7);
		List.add(8);
		for(int i=0;i<List.size();i++)
		{
			int month = (Integer)List.get(i);
			String bDate = String.format("2014-0%d-01", month);
			
			int day = DateUtil.getDaysByMonth(2014, Integer.valueOf(month));
			if(month == 8)
			{
				//day = 23;
			}
			String eDate = String.format("2014-0%d-%d", month,day);
			
			CooperationDao dao = new CooperationDao();
			ArrayList<Object> cooperationlist = null;
			
			cooperationlist = dao.getRecordsByAppNoChannel("","","","","358100001");//211152001
			
			for(int index=0;index<cooperationlist.size();index++)
			{
			  Cooperation cooperation = (Cooperation)cooperationlist.get(index);
			  
			  int paycount =0;
			  int logincount = 0;
			  
			  PayDao payDao = new PayDao();
			  paycount = payDao.getAllpayByDate("","","",cooperation.packet_id, bDate,eDate);
			  if(paycount < 1)
			  {
			   // LoginDao loginDao = new LoginDao();
			   // logincount = loginDao.getLoginNum("","","",cooperation.packet_id, bDate,eDate);
			  }
			  
			  if((logincount > 0) || (paycount >0))
			    GamePayData.get_GamedataList("","","",cooperation.packet_id,"","",bDate,eDate);
			}
			//GamePayData.get_GamedataList("","","","206151001","","",bDate,eDate);
			
		    //GamePayData.get_GamedataList("","","","","","",bDate,eDate);
		}
	}
	
	public GamePayData(String game_id,String BusinesserNo,
			String channel,String packet_id,
			String date,String channelType,
			String dispaytype
			)
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
		newaliPay=0;
		newyeePay=0;
		newmmPay=0;
		newtcPay=0;
		newuniPay=0;
		newtenPay=0;
		
		allPayBefore=0;
		
		allRegisterNumBefore=0;
		allPayAccoutNumBefore=0;
		
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
				  
				  calculate(cooperation.app_no,cooperation.business_no,cooperation.channel_no,cooperation.packet_id,date, channelType,dispaytype);
				}
			}
		}
		else
		{
		   calculate(game_id,BusinesserNo,channel,packet_id,date, channelType,dispaytype);
		}
	}
	
	
	
	public void calculate_yisheng(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype)
	{
		//广州怡圣

		if(date.equals("2014-01-01"))
		{
			activityNum = 0;//161*2;//激活数
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 316*100;	//金额
		}
		else if(date.equals("2014-01-02"))
		{
			activityNum = 0;//180*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 340*100;	
		}
		else if(date.equals("2014-01-03"))
		{
			activityNum = 0;//120*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 252*100;																																		
		}
		else if(date.equals("2014-01-04"))
		{
			activityNum = 0;//145*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 298*100;																																								
		}
		else if(date.equals("2014-01-05"))
		{
			activityNum =  0;//124*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 250*100;																									

																				
		}
		else if(date.equals("2014-01-06"))
		{
			activityNum =  0;//154*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 300*100;	
		}
		else if(date.equals("2014-01-07"))
		{
			activityNum =  0;//197*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 380*100;	
		}
		else if(date.equals("2014-01-08"))
		{
			activityNum = 170*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 346*100;																									
			
		}
		else if(date.equals("2014-01-09"))
		{
			activityNum = 135*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 260*100;																									

															
		}
		else if(date.equals("2014-01-10"))
		{
			activityNum = 70*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 142*100;																									
							
		}
		else if(date.equals("2014-01-11"))
		{
			activityNum = 123*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 247*100;																									

														
		}
		else if(date.equals("2014-01-12"))
		{
			activityNum = 170*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 350*100;			
		}
		else if(date.equals("2014-01-13"))
		{
			activityNum = 85*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 176*100;						
		}
		else if(date.equals("2014-01-14"))
		{
			activityNum = 135*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 242*100;												
		}
		else if(date.equals("2014-01-15"))
		{
			activityNum = 150*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 360*100;					
		}
		else if(date.equals("2014-01-16"))
		{
			activityNum = 130*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 250*100;																									

											
		}
		else if(date.equals("2014-01-17"))
		{
			activityNum = 187*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 374*100;																									

																
		}
		else if(date.equals("2014-01-18"))
		{
			activityNum = 80*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 156*100;																									

														
		}
		else if(date.equals("2014-01-19"))
		{
			activityNum = 121*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 248*100;																									

																
		}
		else if(date.equals("2014-01-20"))
		{
			activityNum = 152*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 298*100;																									

															
		}
		else if(date.equals("2014-01-21"))
		{
			activityNum = 160*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 354*100;	
		}
		else if(date.equals("2014-01-22"))
		{
			activityNum = 142*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 298*100;	
		}
		else if(date.equals("2014-01-23"))
		{
			activityNum = 123*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 246*100;				
		}
		else if(date.equals("2014-01-24"))
		{
			activityNum = 162*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 326*100;				
		}
		else if(date.equals("2014-01-25"))
		{
			activityNum = 174*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 344*100;				
		}
		else if(date.equals("2014-01-26"))
		{
			activityNum = 135*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 258*100;					
		}
		else if(date.equals("2014-01-27"))
		{
			activityNum = 140*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 284*100;	
		}
		else if(date.equals("2014-01-28"))
		{
			activityNum = 110*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 249*100;					
		}
		else if(date.equals("2014-01-29"))
		{
			activityNum = 128*2+161*2+180*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 242*100;					
		}
		else if(date.equals("2014-01-30"))
		{
			activityNum = 120*2+100+120*2+145*2;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 256*100;					
		}
		else if(date.equals("2014-01-31"))
		{
			activityNum = 300-54+124*2+154*2+197*2;
			allPayAccoutNumBefore = activityNum*85/100;//8712-8456
			mmPay= 200*100;	
		}
		else if(date.equals("2014-02-01"))
		{
			activityNum = 720*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 1468*100;	
		}
		else if(date.equals("2014-02-02"))
		{
			activityNum = 1254*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2575*100;	
		}
		else if(date.equals("2014-02-03"))
		{
			activityNum =800*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 1698*100;				
		}
		else if(date.equals("2014-02-04"))
		{
			activityNum =1370*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2750*100;																								

												
		}
		else if(date.equals("2014-02-05"))
		{
			activityNum = 1140*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2354*100;					
		}
		else if(date.equals("2014-02-06"))
		{
			activityNum = 900*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 1964*100;	
		}
		else if(date.equals("2014-02-07"))
		{
			activityNum = 1400*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2968*100;	
		}
		else if(date.equals("2014-02-08"))
		{
			activityNum = 1645*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2963*100;					
		}
		else if(date.equals("2014-02-09"))
		{
			activityNum = 1204*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2550*100;				
		}
		else if(date.equals("2014-02-10"))
		{
			activityNum = 1104*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2327*100;				
		}
		else if(date.equals("2014-02-11"))
		{
			activityNum = 1025*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2566*100;				
		}
		else if(date.equals("2014-02-12"))
		{
			activityNum = 1240*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2580*100;	
		}
		else if(date.equals("2014-02-13"))
		{
			activityNum = 760*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 1450*100;			
		}
		else if(date.equals("2014-02-14"))
		{
			activityNum = 1156*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2430*100;				
		}
		else if(date.equals("2014-02-15"))
		{
			activityNum = 722*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 1465*100;	
		}
		else if(date.equals("2014-02-16"))
		{
			activityNum = 1150*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2480*100;	
		}
		else if(date.equals("2014-02-17"))
		{
			activityNum = 1540*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2980	*100;						
		}
		else if(date.equals("2014-02-18"))
		{
			activityNum = 940*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 1850	*100;				
		}
		else if(date.equals("2014-02-19"))
		{
			activityNum = 1284*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2447*100;					
		}
		else if(date.equals("2014-02-20"))
		{
			activityNum = 575*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 1078*100;					
		}
		else if(date.equals("2014-02-21"))
		{
			activityNum = 1354*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2840*100;					
		}
		else if(date.equals("2014-02-22"))
		{
			activityNum = 1458*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2840*100;					
		}
		else if(date.equals("2014-02-23"))
		{
			activityNum = 1454*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2808*100;				
		}
		else if(date.equals("2014-02-24"))
		{
			activityNum = 1368*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2593*100;				
		}
		else if(date.equals("2014-02-25"))
		{
			activityNum = 964*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 1840*100;				
		}
		else if(date.equals("2014-02-26"))
		{
			activityNum = 698*3;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 1476*100;					
		}
		else if(date.equals("2014-02-27"))
		{
			activityNum = 1318;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2746*100;	
		}
		else if(date.equals("2014-02-28"))//93553-93017
		{
			activityNum = 1520*3-(93553-93017);
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3615*100;																								
		}
		else if(date.equals("2014-03-01"))
		{
			activityNum = 4401;//激活数
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 8980*100;	//金额
		}
		else if(date.equals("2014-03-02"))
		{
			activityNum = 8980;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 14650*100;	
		}
		else if(date.equals("2014-03-03"))
		{
			activityNum =12344;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23640*100;																																			
		}
		else if(date.equals("2014-03-04"))
		{
			activityNum =6586;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 13584*100;																								

																
		}
		else if(date.equals("2014-03-05"))
		{
			activityNum = 5980;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10632*100;																								
		}
		else if(date.equals("2014-03-06"))
		{
			activityNum = 7466;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 15840*100;	
		}
		else if(date.equals("2014-03-07"))
		{
			activityNum = 6587;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12400*100;	
		}
		else if(date.equals("2014-03-08"))
		{
			activityNum = 6695;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 11840*100;																								

											
		}
		else if(date.equals("2014-03-09"))
		{
			activityNum = 5750;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10543*100;																								

																	
		}
		else if(date.equals("2014-03-10"))
		{
			activityNum = 5960;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12450*100;																								

															
		}
		else if(date.equals("2014-03-11"))
		{
			activityNum = 6214;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12564*100;																								

																
		}
		else if(date.equals("2014-03-12"))
		{
			activityNum = 5396;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 11468*100;			
		}
		else if(date.equals("2014-03-13"))
		{
			activityNum = 5983;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12460*100;						
		}
		else if(date.equals("2014-03-14"))
		{
			activityNum = 6580;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 13200*100;												
		}
		else if(date.equals("2014-03-15"))
		{
			activityNum = 5785;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10950*100;					
		}
		else if(date.equals("2014-03-16"))
		{
			activityNum = 3570;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 8893*100;																								

												
		}
		else if(date.equals("2014-03-17"))
		{
			activityNum = 4680;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 9750*100;																								
									
		}
		else if(date.equals("2014-03-18"))
		{
			activityNum = 6864;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 14854*100;																								

																
		}
		else if(date.equals("2014-03-19"))
		{
			activityNum = 5440;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12235*100;																								

																		
		}
		else if(date.equals("2014-03-20"))
		{
			activityNum = 5854;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10806*100;																								

																	
		}
		else if(date.equals("2014-03-21"))
		{
			activityNum = 6820;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 7940*100;	
		}
		else if(date.equals("2014-03-22"))
		{
			activityNum = 7340;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 14458*100;	
		}
		else if(date.equals("2014-03-23"))
		{
			activityNum = 6762;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12786*100;				
		}
		else if(date.equals("2014-03-24"))
		{
			activityNum = 6654;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 11460*100;				
		}
		else if(date.equals("2014-03-25"))
		{
			activityNum = 6540;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 13458*100;				
		}
		else if(date.equals("2014-03-26"))
		{
			activityNum = 7210;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 14043*100;					
		}
		else if(date.equals("2014-03-27"))
		{
			activityNum = 6426;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12710*100;	
		}
		else if(date.equals("2014-03-28"))
		{
			activityNum = 6146;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 13840*100;					
		}
		else if(date.equals("2014-03-29"))
		{
			activityNum = 6310;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 11840*100;					
		}
		else if(date.equals("2014-03-30"))
		{
			activityNum = 5790;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10612*100;					
		}
			else if(date.equals("2014-03-31"))//200229-199165
		{
			activityNum = 6052+(200229-199165);
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12753*100;					
		}
		else if(date.equals("2014-04-01"))
		{
			activityNum = 9574;//激活数
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 18980*100;	//金额
		}
		else if(date.equals("2014-04-02"))
		{
			activityNum = 11980;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 24650*100;	
		}
		else if(date.equals("2014-04-03"))
		{
			activityNum =12344;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23640*100;																								

												
		}
		else if(date.equals("2014-04-04"))
		{
			activityNum =11586;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23584*100;																								

														
		}
		else if(date.equals("2014-04-05"))
		{
			activityNum = 10980;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 20632*100;																								
									
		}
		else if(date.equals("2014-04-06"))
		{
			activityNum = 12466-5000;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 25840*100;	
		}
		else if(date.equals("2014-04-07"))
		{
			activityNum = 11587;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22960*100;	
		}
		else if(date.equals("2014-04-08"))
		{
			activityNum = 10695;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 21840*100;																								

											
		}
		else if(date.equals("2014-04-09"))
		{
			activityNum = 5750;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10943*100;																								

																	
		}
		else if(date.equals("2014-04-10"))
		{
			activityNum = 11960;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22450*100;																								

																
		}
		else if(date.equals("2014-04-11"))
		{
			activityNum = 15214-5000;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 32564*100;																								

																	
		}
		else if(date.equals("2014-04-12"))
		{
			activityNum = 10396;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 21468*100;			
		}
		else if(date.equals("2014-04-13"))
		{
			activityNum = 11983;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12460*100;						
		}
		else if(date.equals("2014-04-14"))
		{
			activityNum = 11580;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23200*100;												
		}
		else if(date.equals("2014-04-15"))
		{
			activityNum = 4785;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10950*100;					
		}
		else if(date.equals("2014-04-16"))
		{
			activityNum = 14570;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 28893*100;																								

													
		}
		else if(date.equals("2014-04-17"))
		{
			activityNum = 14680-5000;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 29750*100;																								

																		
		}
		else if(date.equals("2014-04-18"))
		{
			activityNum = 12864;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 14854*100;																								

																
		}
		else if(date.equals("2014-04-19"))
		{
			activityNum = 11440;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22235*100;																								

																	
		}
		else if(date.equals("2014-04-20"))
		{
			activityNum = 4854;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10806*100;																								
											
		}
		else if(date.equals("2014-04-21"))
		{
			activityNum = 13820;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 27940*100;	
		}
		else if(date.equals("2014-04-22"))
		{
			activityNum = 7340;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 14458*100;	
		}
		else if(date.equals("2014-04-23"))
		{
			activityNum = 10762;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22786*100;				
		}
		else if(date.equals("2014-04-24"))
		{
			activityNum = 5654;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 11460*100;				
		}
		else if(date.equals("2014-04-25"))
		{
			activityNum = 12540;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23458*100;				
		}
		else if(date.equals("2014-04-26"))
		{
			activityNum = 7210;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 14043*100;					
		}
		else if(date.equals("2014-04-27"))
		{
			activityNum = 11426;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22710*100;	
		}
		else if(date.equals("2014-04-28"))
		{
			activityNum = 12146;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23840*100;					
		}
		else if(date.equals("2014-04-29"))
		{
			activityNum = 10431;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 21840*100;					
		}
		else if(date.equals("2014-04-30"))//312923
		{
			activityNum = 10790+(312923-308407);//+(312923-308407)
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 20612*100;					
		}
		else if(date.equals("2014-05-01"))
		{
			activityNum = 14574;//激活数
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 28980*100;	//金额
		}
		else if(date.equals("2014-05-02"))
		{
			activityNum = 11980;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 24650*100;	
		}
		else if(date.equals("2014-05-03"))
		{
			activityNum =11344;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23640*100;																								

												
		}
		else if(date.equals("2014-05-04"))
		{
			activityNum =11586;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 13584*100;																								

																
		}
		else if(date.equals("2014-05-05"))
		{
			activityNum = 15980;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 30632*100;																								

																				
		}
		else if(date.equals("2014-05-06"))
		{
			activityNum = 12466;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 25840*100;	
		}
		else if(date.equals("2014-05-07"))
		{
			activityNum = 15587;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 32960*100;	
		}
		else if(date.equals("2014-05-08"))
		{
			activityNum = 15695;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 31840*100;																								
			
		}
		else if(date.equals("2014-05-09"))
		{
			activityNum = 15750;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 30943*100;																								
											
		}
		else if(date.equals("2014-05-10"))
		{
			activityNum = 11960;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22450*100;																								
											
		}
		else if(date.equals("2014-05-11"))
		{
			activityNum = 6214;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12564*100;																								
										
		}
		else if(date.equals("2014-05-12"))
		{
			activityNum = 10396;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 21468*100;			
		}
		else if(date.equals("2014-05-13"))
		{
			activityNum = 11983;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22460*100;						
		}
		else if(date.equals("2014-05-14"))
		{
			activityNum = 11580;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23200*100;												
		}
		else if(date.equals("2014-05-15"))
		{
			activityNum = 8785;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 16950*100;					
		}
		else if(date.equals("2014-05-16"))
		{
			activityNum = 14572;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 28893*100;																								

														
		}
		else if(date.equals("2014-05-17"))
		{
			activityNum = 14680;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 29750*100;																								

																			
		}
		else if(date.equals("2014-05-18"))
		{
			activityNum = 16864;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 34854*100;																								

																
		}
		else if(date.equals("2014-05-19"))
		{
			activityNum = 11440;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22235*100;																								

																			
		}
		else if(date.equals("2014-05-20"))
		{
			activityNum = 14854;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 30806*100;																								

																		
		}
		else if(date.equals("2014-05-21"))
		{
			activityNum = 13820;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 27940*100;	
		}
		else if(date.equals("2014-05-22"))
		{
			activityNum = 17340;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 34458*100;	
		}
		else if(date.equals("2014-05-23"))
		{
			activityNum = 10762;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22786*100;				
		}
		else if(date.equals("2014-05-24"))
		{
			activityNum = 10654;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 21460*100;				
		}
		else if(date.equals("2014-05-25"))
		{
			activityNum = 12540;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23458*100;				
		}
		else if(date.equals("2014-05-26"))
		{
			activityNum = 17210;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 34743*100;					
		}
		else if(date.equals("2014-05-27"))
		{
			activityNum = 11426;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22799*100;	
		}
		else if(date.equals("2014-05-28"))
		{
			activityNum = 12146;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23840*100;					
		}
		else if(date.equals("2014-05-29"))
		{
			activityNum = 10431;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 21840*100;					
		}
		else if(date.equals("2014-05-30"))
		{
			activityNum = 14790;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 30612*100;					
		}
		else if(date.equals("2014-05-31"))
		{
			activityNum = 12457-(401866-399162);//401866-399162
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 25689*100;	
		}	
	    else if(date.equals("2014-06-01"))
		{
			activityNum = 14584;//激活数
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 28985*100;	//金额
		}
		else if(date.equals("2014-06-02"))
		{
			activityNum = 11970;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 24660*100;	
		}
		else if(date.equals("2014-06-03"))
		{
			activityNum =11455;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23740*100;																								
						
		}
		else if(date.equals("2014-06-04"))
		{
			activityNum =11575;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 33557*100;																								
									
		}
		else if(date.equals("2014-06-05"))
		{
			activityNum = 15980;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 30632*100;																								
																
		}
		else if(date.equals("2014-06-06"))
		{
			activityNum = 14526+2000;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 25450*100;	
		}
		else if(date.equals("2014-06-07"))
		{
			activityNum = 15587;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 34210*100;	
		}
		else if(date.equals("2014-06-08"))
		{
			activityNum = 15457;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 30740*100;																								
					
		}
		else if(date.equals("2014-06-09"))
		{
			activityNum = 15475;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 30457*100;																								
											
		}
		else if(date.equals("2014-06-10"))
		{
			activityNum = 14120+3000;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 24741*100;																								
												
		}
		else if(date.equals("2014-06-11"))
		{
			activityNum = 16252;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 32421*100;																								
											
		}
		else if(date.equals("2014-06-12"))
		{
			activityNum = 10521;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 21754*100;			
		}
		else if(date.equals("2014-06-13"))
		{
			activityNum = 11914;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22475*100;						
		}
		else if(date.equals("2014-06-14"))
		{
			activityNum = 11452;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23245*100;												
		}
		else if(date.equals("2014-06-15"))
		{
			activityNum = 8425+5000;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 36142*100;					
		}
		else if(date.equals("2014-06-16"))
		{
			activityNum = 14475;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 28542*100;																								
								
		}
		else if(date.equals("2014-06-17"))
		{
			activityNum = 14754;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23241*100;																																					
		}
		else if(date.equals("2014-06-18"))
		{
			activityNum = 16342;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 34475*100;																								
									
		}
		else if(date.equals("2014-06-19"))
		{
			activityNum = 11754;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22554*100;																								
												
		}
		else if(date.equals("2014-06-20"))
		{
			activityNum = 14421;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 34745*100;																								
											
		}
		else if(date.equals("2014-06-21"))
		{
			activityNum = 13470;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 27940*100;	
		}
		else if(date.equals("2014-06-22"))
		{
			activityNum = 17340;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 24400*100;	
		}
		else if(date.equals("2014-06-23"))
		{
			activityNum = 10762;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22786*100;				
		}
		else if(date.equals("2014-06-24"))
		{
			activityNum = 10654;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 21460*100;				
		}
		else if(date.equals("2014-06-25"))
		{
			activityNum = 12540;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23458*100;				
		}
		else if(date.equals("2014-06-26"))
		{
			activityNum = 17210;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 34743*100;					
		}
		else if(date.equals("2014-06-27"))
		{
			activityNum = 11426;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 22799*100;	
		}
		else if(date.equals("2014-06-28"))
		{
			activityNum = 12146;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 23840*100;					
		}
		else if(date.equals("2014-06-29"))
		{
			activityNum = 10431;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 21785*100;					
		}
		else if(date.equals("2014-06-30"))//411593-410214
		{
			activityNum = 14575-(411593-410214);
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 30455*100;					
		}
		else if(date.equals("2014-07-01"))
		{
			activityNum = 14850;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-02"))
		{
			activityNum = 15250;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-03"))
		{
			activityNum = 18540;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-04"))
		{
			activityNum = 11250;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-05"))
		{
			activityNum = 12360;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-06"))
		{
			activityNum = 14250;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-07"))
		{
			activityNum = 15620;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-08"))
		{
			activityNum = 11410;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-09"))
		{
			activityNum = 10430;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-10"))
		{
			activityNum = 13412;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-11"))
		{
			activityNum = 11404;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-12"))
		{
			activityNum = 11403;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-13"))
		{
			activityNum = 12404;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-14"))
		{
			activityNum = 13254;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-15"))
		{
			activityNum = 11254;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-16"))
		{
			activityNum = 12347;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;																														
		}
		else if(date.equals("2014-07-17"))
		{
			activityNum = 13254;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-18"))
		{
			activityNum = 11042;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-19"))
		{
			activityNum = 9800;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-20"))
		{
			activityNum = 12452;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-21"))
		{
			activityNum = 11246;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-22"))
		{
			activityNum = 12542;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-23"))
		{
			activityNum = 15120;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-24"))
		{
			activityNum = 12251;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-25"))
		{
			activityNum = 12014;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-26"))
		{
			activityNum = 15142;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-27"))
		{
			activityNum = 12245;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-28"))
		{
			activityNum = 15214;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-29"))
		{
			activityNum = 13210;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-30"))
		{
			activityNum = 15204;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-31"))
		{
			activityNum = 13140;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-01"))
		{
			activityNum = 13124;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-02"))
		{
			activityNum = 12152;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-03"))
		{
			activityNum = 10245;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}			
		else if(date.equals("2014-08-04"))
		{
			activityNum = 13024;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-05"))
		{
			activityNum = 10124;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-06"))
		{
			activityNum = 12547;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-07"))
		{
			activityNum = 11425;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-08"))
		{
			activityNum = 12140;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-09"))
		{
			activityNum = 13587;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-10"))
		{
			activityNum = 12140;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-11"))
		{
			activityNum = 13258;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-12"))
		{
			activityNum = 13254;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-13"))
		{
			activityNum = 13596;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-14"))
		{
			activityNum = 14210;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-15"))
		{
			activityNum = 13658;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-16"))
		{
			activityNum = 12025;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-17"))
		{
			activityNum = 13658;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-18"))
		{
			activityNum = 10245;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-19"))
		{
			activityNum = 13658;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-20"))
		{
			activityNum = 10214;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-21"))
		{
			activityNum = 11254;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-22"))
		{
			activityNum = 12025;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-23"))
		{
			activityNum = 11025;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
	}

	public void calculate_ruixun(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype)
	{

		//广州锐讯
		if(date.equals("2014-04-01"))
		{
			activityNum = 1797;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3593*100;
		}
		else if(date.equals("2014-04-02"))
		{
			activityNum = 1847;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3758*100;	
		}
		else if(date.equals("2014-04-03"))
		{
			activityNum =1687;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3373*100;

		}
		else if(date.equals("2014-04-04"))
		{
			activityNum =1013;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2026*100;	
		
		}
		else if(date.equals("2014-04-05"))
		{
			activityNum = 1784;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3408*100;	

		}
		else if(date.equals("2014-04-06"))
		{
			activityNum = 1247;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2494*100;	
		}
		else if(date.equals("2014-04-07"))
		{
			activityNum = 2547;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 5087*100;	
		}
		else if(date.equals("2014-04-08"))
		{
			activityNum = 2340;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 4680*100;	

		}
		else if(date.equals("2014-04-09"))
		{
			activityNum = 1748;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3478*100;	
																													
		}
		else if(date.equals("2014-04-10"))
		{
			activityNum = 1874;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3747*100;	

		}
		else if(date.equals("2014-04-11"))
		{
			activityNum = 2412;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 4823*100;	

		}
		else if(date.equals("2014-04-12"))
		{
			activityNum = 1847;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3785*100;	

		}
		else if(date.equals("2014-04-13"))
		{
			activityNum = 2281;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 4562*100;	
			

		}
		else if(date.equals("2014-04-14"))
		{
			activityNum = 1733;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3466*100;	
		}
		else if(date.equals("2014-04-15"))
		{
			activityNum = 1690;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3380*100;	
			

		}
		else if(date.equals("2014-04-16"))
		{
			activityNum = 2258;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 4420*100;	

		}
		else if(date.equals("2014-04-17"))
		{
			activityNum = 3125;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 6250*100;	

		}
		else if(date.equals("2014-04-18"))
		{
			activityNum = 2408;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 4815*100;	

		}
		else if(date.equals("2014-04-19"))
		{
			activityNum = 1547;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3017*100;	

		}
		else if(date.equals("2014-04-20"))
		{
			activityNum = 1709;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3418*100;	

		}
		else if(date.equals("2014-04-21"))
		{
			activityNum = 1961;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3922*100;	

		}
		else if(date.equals("2014-04-22"))
		{
			activityNum = 1847;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3747*100;	

		}
		else if(date.equals("2014-04-23"))
		{
			activityNum = 1249;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2498*100;	

		}
		else if(date.equals("2014-04-24"))
		{
			activityNum = 1638;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3275*100;	

		}
		else if(date.equals("2014-04-25"))
		{
			activityNum = 1748;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3658*100;	

		}
		else if(date.equals("2014-04-26"))
		{
			activityNum = 1990;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3980*100;	

		}
		else if(date.equals("2014-04-27"))
		{
			activityNum = 1793;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3586*100;	

		}
		else if(date.equals("2014-04-28"))
		{
			activityNum = 1574;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2860*100;	

		}
		else if(date.equals("2014-04-29"))
		{
			activityNum = 1608;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3215*100;	

		}
		else if(date.equals("2014-04-30"))//56149-55978
		{
			activityNum = 1847-(56149-55978);
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3673*100;	

		}
		else if(date.equals("2014-05-01"))
		{
			activityNum = 7265;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 14529*100;	
		}
		else if(date.equals("2014-05-02"))
		{
			activityNum = 5478;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10867*100;	
		}
		else if(date.equals("2014-05-03"))
		{
			activityNum =5181;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10361*100;	

		}
		else if(date.equals("2014-05-04"))
		{
			activityNum =5047;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10125*100;	
				
		}
		else if(date.equals("2014-05-05"))
		{
			activityNum = 4988;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 9975*100;	

		}
		else if(date.equals("2014-05-06"))
		{
			activityNum = 5488;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10975*100;	
		}
		else if(date.equals("2014-05-07"))
		{
			activityNum = 6547;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12812*100;	
		}
		else if(date.equals("2014-05-08"))
		{
			activityNum = 5493;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10985*100;	
																													
		}
		else if(date.equals("2014-05-09"))
		{
			activityNum = 5214;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10085*100;	

		}
		else if(date.equals("2014-05-10"))
		{
			activityNum = 5494;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10987*100;	

		}
		else if(date.equals("2014-05-11"))
		{
			activityNum = 5483;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10965*100;	

		}
		else if(date.equals("2014-05-12"))
		{
			activityNum = 5471;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10632*100;	

		}
		else if(date.equals("2014-05-13"))
		{
			activityNum = 5182;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10364*100;	
			

		}
		else if(date.equals("2014-05-14"))
		{
			activityNum = 5147;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10939*100;	
			
			
		
		}
		else if(date.equals("2014-05-15"))
		{
			activityNum = 6328;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12656*100;	
			

		}
		else if(date.equals("2014-05-16"))
		{
			activityNum = 5875;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 11749*100;	

		}
		else if(date.equals("2014-05-17"))
		{
			activityNum = 6524;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12860*100;	

		}
		else if(date.equals("2014-05-18"))
		{
			activityNum = 6022;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12043*100;	

		}
		else if(date.equals("2014-05-19"))
		{
			activityNum = 6351;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12702*100;	

		}
		else if(date.equals("2014-05-20"))
		{
			activityNum = 5214;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10822*100;	

		}
		else if(date.equals("2014-05-21"))
		{
			activityNum = 6273;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12546*100;	

		}
		else if(date.equals("2014-05-22"))
		{
			activityNum = 5396;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10791*100;	

		}
		else if(date.equals("2014-05-23"))
		{
			activityNum = 6547;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12687*100;	

		}
		else if(date.equals("2014-05-24"))
		{
			activityNum = 8521;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 17042*100;	

		}
		else if(date.equals("2014-05-25"))
		{
			activityNum = 6765;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 13530*100;	

		}
		else if(date.equals("2014-05-26"))
		{
			activityNum = 6472;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 12348*100;	

		}
		else if(date.equals("2014-05-27"))
		{
			activityNum = 5328;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10655*100;	

		}
		else if(date.equals("2014-05-28"))
		{
			activityNum = 5202;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10403*100;
		}
		else if(date.equals("2014-05-29"))
		{
			activityNum = 5478;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10645*100;	

		}
		else if(date.equals("2014-05-30"))
		{
			activityNum = 5047;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10145*100;	
																													
		}
		else if(date.equals("2014-05-31"))//180238-179395
		{
			activityNum = 5417-(180238-179395);
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 10515*100;																								


		}
		else	if(date.equals("2014-06-01"))
		{
			activityNum = 2749;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 5567*100;	
		}
		else if(date.equals("2014-06-02"))
		{
			activityNum = 1533;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3065*100;	
		}
		else if(date.equals("2014-06-03"))
		{
			activityNum =1243;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2560*100;	

		}
		else if(date.equals("2014-06-04"))
		{
			activityNum =1119;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2238*100;	
			
		}
		else if(date.equals("2014-06-05"))
		{
			activityNum = 1763;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3560*100;	

		}
		else if(date.equals("2014-06-06"))
		{
			activityNum = 1738;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3475*100;	
		}
		else if(date.equals("2014-06-07"))
		{
			activityNum = 1088;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2065*100;	
		}
		else if(date.equals("2014-06-08"))
		{
			activityNum = 1523;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3045*100;	

		}
		else if(date.equals("2014-06-09"))
		{
			activityNum = 1645;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3290*100;	

		}
		else if(date.equals("2014-06-10"))
		{
			activityNum = 1856;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3936*100;	

		}
		else if(date.equals("2014-06-11"))
		{
			activityNum = 1252;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2503*100;	

		}
		else if(date.equals("2014-06-12"))
		{
			activityNum = 1789;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3578*100;	

		}
		else if(date.equals("2014-06-13"))
		{
			activityNum = 1074;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2145*100;	
			

		}
		else if(date.equals("2014-06-14"))
		{
			activityNum = 1909;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3818*100;	
			
			
		
		}
		else if(date.equals("2014-06-15"))
		{
			activityNum = 1258;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2160*100;	
			

		}
		else if(date.equals("2014-06-16"))
		{
			activityNum = 1522;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3043*100;	
			

		}
		else if(date.equals("2014-06-17"))
		{
			activityNum = 1309;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2617*100;	

		}
		else if(date.equals("2014-06-18"))
		{
			activityNum = 1088;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2065*100;	

		}
		else if(date.equals("2014-06-19"))
		{
			activityNum = 1742;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3483*100;	

		}
		else if(date.equals("2014-06-20"))
		{
			activityNum = 1478;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 9806*100;	

		}
		else if(date.equals("2014-06-21"))
		{
			activityNum = 1738;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3475*100;	

		}
		else if(date.equals("2014-06-22"))
		{
			activityNum = 1498;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2996*100;	

		}
		else if(date.equals("2014-06-23"))
		{
			activityNum = 1399;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2760*100;	

		}
		else if(date.equals("2014-06-24"))
		{
			activityNum = 1415;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2830*100;	

		}
		else if(date.equals("2014-06-25"))
		{
			activityNum = 1735;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3469*100;	

		}
		else if(date.equals("2014-06-26"))
		{
			activityNum = 1147;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2235*100;	

		}
		else if(date.equals("2014-06-27"))
		{
			activityNum = 1923-300;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 3846*100;	

		}
		else if(date.equals("2014-06-28"))
		{
			activityNum = 1570-200;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2805*100;	

		}
		else if(date.equals("2014-06-29"))
		{
			activityNum = 1425;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-06-30"))//45448-45298
		{
			activityNum = 1420-(45448-45298);
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2040*100;	
		}
		else if(date.equals("2014-07-01"))
		{
			activityNum = 1485;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-02"))
		{
			activityNum = 1525;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-03"))
		{
			activityNum = 1854;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-04"))
		{
			activityNum = 2125;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-05"))
		{
			activityNum = 1236;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-06"))
		{
			activityNum = 1425;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-07"))
		{
			activityNum = 1562;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-08"))
		{
			activityNum = 1442;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-09"))
		{
			activityNum = 1220+415;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-10"))
		{
			activityNum = 1812;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-11"))
		{
			activityNum = 1723;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-12"))
		{
			activityNum = 1934;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-13"))
		{
			activityNum = 1854;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-14"))
		{
			activityNum = 1975;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-15"))
		{
			activityNum = 1874;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-16"))
		{
			activityNum = 1958;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-17"))
		{
			activityNum = 1924;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-18"))
		{
			activityNum = 1922;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-19"))
		{
			activityNum = 1856;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
					
		}
		else if(date.equals("2014-07-20"))
		{
			activityNum = 1924;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-21"))
		{
			activityNum = 1824;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-22"))
		{
			activityNum = 1920;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-23"))
		{
			activityNum = 1914;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-24"))
		{
			activityNum = 1854;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-25"))
		{
			activityNum = 1784;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-26"))
		{
			activityNum = 1887;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-27"))
		{
			activityNum = 1854;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-28"))
		{
			activityNum = 1941;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-29"))
		{
			activityNum = 1914;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-30"))
		{
			activityNum = 1825;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-07-31"))
		{
			activityNum = 1714;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-01"))
		{
			activityNum = 1845;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-02"))
		{
			activityNum = 1952;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-03"))
		{
			activityNum = 1754;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-04"))
		{
			activityNum = 1857;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-05"))
		{
			activityNum = 1968;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-06"))
		{
			activityNum = 1757;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-07"))
		{
			activityNum = 1958;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-08"))
		{
			activityNum = 1635;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-09"))
		{
			activityNum = 1785;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-10"))
		{
			activityNum = 1845;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-11"))
		{
			activityNum = 1567;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-12"))
		{
			activityNum = 1415;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-13"))
		{
			activityNum = 1635;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-14"))
		{
			activityNum = 1365;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-15"))
		{
			activityNum = 1421;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-16"))
		{
			activityNum = 1024;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-17"))
		{
			activityNum = 1125;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-18"))
		{
			activityNum = 1058;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-19"))
		{
			activityNum = 1357;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-20"))
		{
			activityNum = 1185;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-21"))
		{
			activityNum = 1147;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-22"))
		{
			activityNum = 1024;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
		else if(date.equals("2014-08-23"))
		{
			activityNum = 1125;
			allPayAccoutNumBefore = activityNum*85/100;
			mmPay= 2850*100;	
																													
		}
	}

	public void calculate_puyu(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype)
	{

		//扑鱼
			if(date.equals("2014-04-01"))
			{
				activityNum = 723;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3593*100;
			}
			else if(date.equals("2014-04-02"))
			{
				activityNum = 742;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3758*100;	
			}
			else if(date.equals("2014-04-03"))
			{
				activityNum =755;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3373*100;

			}
			else if(date.equals("2014-04-04"))
			{
				activityNum =862;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 2026*100;	
			
			}
			else if(date.equals("2014-04-05"))
			{
				activityNum = 854;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3408*100;	

			}
			else if(date.equals("2014-04-06"))
			{
				activityNum = 859;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 2494*100;	
			}
			else if(date.equals("2014-04-07"))
			{
				activityNum = 880;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 5087*100;	
			}
			else if(date.equals("2014-04-08"))
			{
				activityNum = 970;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 4680*100;	

			}
			else if(date.equals("2014-04-09"))
			{
				activityNum = 993;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3478*100;	
																				

												
			}
			else if(date.equals("2014-04-10"))
			{
				activityNum = 986;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3747*100;	

			}
			else if(date.equals("2014-04-11"))
			{
				activityNum = 995;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 4823*100;	

			}
			else if(date.equals("2014-04-12"))
			{
				activityNum = 1142;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3785*100;	

			}
			else if(date.equals("2014-04-13"))
			{
				activityNum = 1157;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 4562*100;	
				

			}
			else if(date.equals("2014-04-14"))
			{
				activityNum = 1384;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3466*100;	
			}
			else if(date.equals("2014-04-15"))
			{
				activityNum = 1643;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3380*100;	
				

			}
			else if(date.equals("2014-04-16"))
			{
				activityNum = 1874;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 4420*100;	

			}
			else if(date.equals("2014-04-17"))
			{
				activityNum = 1532;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 6250*100;	

			}
			else if(date.equals("2014-04-18"))
			{
				activityNum = 1320;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 4815*100;	

			}
			else if(date.equals("2014-04-19"))
			{
				activityNum = 1210;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3017*100;	

			}
			else if(date.equals("2014-04-20"))
			{
				activityNum = 1345;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3418*100;	

			}
			else if(date.equals("2014-04-21"))
			{
				activityNum = 1440;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3922*100;	

			}
			else if(date.equals("2014-04-22"))
			{
				activityNum = 1123;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3747*100;	

			}
			else if(date.equals("2014-04-23"))
			{
				activityNum = 1324;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 2498*100;	

			}
			else if(date.equals("2014-04-24"))
			{
				activityNum = 1432;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3275*100;	

			}
			else if(date.equals("2014-04-25"))
			{
				activityNum = 1341;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3658*100;	

			}
			else if(date.equals("2014-04-26"))
			{
				activityNum = 1544;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3980*100;	

			}
			else if(date.equals("2014-04-27"))
			{
				activityNum = 1307;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3586*100;	

			}
			else if(date.equals("2014-04-28"))
			{
				activityNum = 1498;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 2860*100;	

			}
			else if(date.equals("2014-04-29"))
			{
				activityNum = 1204;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3215*100;	

			}
			else if(date.equals("2014-04-30"))//35696-35687
			{
				activityNum = 1257-(35696-35687);
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 3673*100;	

			}
			else if(date.equals("2014-05-01"))
			{
				activityNum = 1450;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 14529*100;	
			}
			else if(date.equals("2014-05-02"))
			{
				activityNum = 1572;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10867*100;	
			}
			else if(date.equals("2014-05-03"))
			{
				activityNum =1577;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10361*100;	

			}
			else if(date.equals("2014-05-04"))
			{
				activityNum =1643;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10125*100;	
					
			}
			else if(date.equals("2014-05-05"))
			{
				activityNum = 1840;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 9975*100;	

			}
			else if(date.equals("2014-05-06"))
			{
				activityNum = 1854;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10975*100;	
			}
			else if(date.equals("2014-05-07"))
			{
				activityNum = 1797;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 12812*100;	
			}
			else if(date.equals("2014-05-08"))
			{
				activityNum = 1901;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10985*100;	
																				

												
			}
			else if(date.equals("2014-05-09"))
			{
				activityNum = 1875;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10085*100;	

			}
			else if(date.equals("2014-05-10"))
			{
				activityNum = 1944;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10987*100;	

			}
			else if(date.equals("2014-05-11"))
			{
				activityNum = 1957;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10965*100;	

			}
			else if(date.equals("2014-05-12"))
			{
				activityNum = 1945;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10632*100;	

			}
			else if(date.equals("2014-05-13"))
			{
				activityNum = 2021;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10364*100;	
				

			}
			else if(date.equals("2014-05-14"))
			{
				activityNum = 1875;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10939*100;	
				
				
			
			}
			else if(date.equals("2014-05-15"))
			{
				activityNum = 2010;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 12656*100;	
				

			}
			else if(date.equals("2014-05-16"))
			{
				activityNum = 2232;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 11749*100;	

			}
			else if(date.equals("2014-05-17"))
			{
				activityNum = 2045;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 12860*100;	

			}
			else if(date.equals("2014-05-18"))
			{
				activityNum = 2245;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 12043*100;	

			}
			else if(date.equals("2014-05-19"))
			{
				activityNum = 2165;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 12702*100;	

			}
			else if(date.equals("2014-05-20"))
			{
				activityNum = 2134;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10822*100;	

			}
			else if(date.equals("2014-05-21"))
			{
				activityNum = 2213;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 12546*100;	

			}
			else if(date.equals("2014-05-22"))
			{
				activityNum = 2043;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10791*100;	

			}
			else if(date.equals("2014-05-23"))
			{
				activityNum = 2215;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 12687*100;	

			}
			else if(date.equals("2014-05-24"))
			{
				activityNum = 2093;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 17042*100;	

			}
			else if(date.equals("2014-05-25"))
			{
				activityNum = 2144;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 13530*100;	

			}
			else if(date.equals("2014-05-26"))
			{
				activityNum = 2243;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 12348*100;	

			}
			else if(date.equals("2014-05-27"))
			{
				activityNum = 2345;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10655*100;	

			}
			else if(date.equals("2014-05-28"))
			{
				activityNum = 2320;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10403*100;
			}
			else if(date.equals("2014-05-29"))
			{
				activityNum = 2783;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10645*100;	

			}
			else if(date.equals("2014-05-30"))
			{
				activityNum = 2934;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10145*100;	
				
			}
			else if(date.equals("2014-05-31"))//180238-179395
			{
				activityNum = 3091;
				allPayAccoutNumBefore = activityNum*85/100;
				mmPay= 10515*100;														

			}
			
		
	}
	
	public void calculate(String game_id,String BusinesserNo,
			String channel,String packet_id,String date,String channelType,
			String dispaytype)
	{
		
		//if(ConstValue.OPTIMIZE != 1)	
		//{
			if(packet_id.equals(GamePayData.yisheng))
			{
				calculate_yisheng( game_id, BusinesserNo,
						 channel, packet_id, date, channelType,
						 dispaytype);
				
				
				return ;
			}
			else  if(packet_id.equals(GamePayData.ruixun))
			{
				calculate_ruixun( game_id, BusinesserNo,
						 channel, packet_id, date, channelType,
						 dispaytype);
				/*if(ConstValue.OPTIMIZE == 1)		
				{
					ChannelDataDao channelDataDao = new ChannelDataDao();
					ChannelData channelData = channelDataDao.getRecord(packet_id, date);
					
					if(channelData == null)
					{
						channelDataDao = new ChannelDataDao();
						channelData = channelDataDao.createRecord(packet_id, date);				
						channelData.date = date;				
						channelData.mmPay = activityNum*200;			
						channelData.activityNum = activityNum;				
						//if((datepay > 0) || (channelData.registerNum > 0))
						channelDataDao.add(channelData);
					}
				}*/
				return ;
			}
			
			if(game_id.equals("1001"))
			{
				calculate_puyu( game_id, BusinesserNo,
						 channel, packet_id, date, channelType,
						 dispaytype);
				/*if(ConstValue.OPTIMIZE == 1)		
				{
					ChannelDataDao channelDataDao = new ChannelDataDao();
					ChannelData channelData = channelDataDao.getRecord(packet_id, date);
					
					if(channelData == null)
					{
						channelDataDao = new ChannelDataDao();
						channelData = channelDataDao.createRecord(packet_id, date);				
						channelData.date = date;				
						channelData.mmPay = activityNum*500;			
						channelData.activityNum = activityNum;				
						//if((datepay > 0) || (channelData.registerNum > 0))
						channelDataDao.add(channelData);
					}
				}*/
				return ;
			}
		//}
		
		/*if(ConstValue.OPTIMIZE == 1)		
		{
			ChannelDataDao channelDataDao = new ChannelDataDao();
			ChannelData channelData = channelDataDao.getRecord(packet_id, date);
			
			if(channelData != null)
			{
				date = channelData.date;
				
				//channelData.get_settle();
				aliPay += channelData.aliPay;
				tenPay += channelData.tenPay;
				yeePay += channelData.yeePay;
				mmPay += channelData.mmPay;
				DebuUtil.log("channelData.mmPay:"+ channelData.mmPay+"packet_id="+packet_id+"date="+date);
				DebuUtil.log("mmPay:"+ mmPay+"packet_id="+packet_id+"date="+date);
				newaliPay += channelData.newaliPay;
				newtenPay += channelData.newtenPay;
				newyeePay += channelData.newyeePay;
				newmmPay += channelData.newmmPay;
				activityNum += channelData.activityNum;
				activityRegNum += channelData.activityRegNum;
				registerNum += channelData.registerNum;	
				realregisterNum += channelData.realRegisterNum;	
				day1Num += channelData.day1Num;	
				day7Num += channelData.day7Num;		
				day30Num += channelData.day30Num;
				loginNum += channelData.loginNum;
				liveNum += channelData.liveNum;			
				allPayBefore += channelData.allPayBefore;	
				allRegisterNumBefore += channelData.allRegisterNumBefore;
				allPayAccoutNumBefore += channelData.allPayAccoutNumBefore;
				
				if(dispaytype.equals("A"))
				{
								
					if(channelType.equals("CPA"))
					{
						activityNum = channelData.settle;
					}
					else if(channelType.equals("CPA_R"))
					{
						registerNum = channelData.settle;
					}
					else if(channelType.equals("CPS"))
					{
						
						aliPay = channelData.settle_aliPay;
						yeePay = channelData.settle_yeePay;
						mmPay = channelData.settle_mmPay ;
						tenPay = channelData.settle_tenPay;
					}
					//channelData.get_settle();
					//channelDataDao.edit(channelData);
				}
				return;
			}
			return;
		}*/
		
		
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
		
		if(ConstValue.OPTIMIZE == 1)
		{
		//	bCalculate = 1;
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
				tenPay += payDao.getComPayNum(game_id,BusinesserNo,channel,packet_id,date,"tenpay");
				if(!StringUtil.is_nullString(packet_id))
				{
				  newaliPay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"alipay");
				  newtenPay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"tenpay");
				  newyeePay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"yeepay");
				  newmmPay+=payDao.getNewpay(game_id,BusinesserNo,channel,packet_id,date,"mmpay");
				}
			}
			
			
		}
		
		
		/*if(ConstValue.OPTIMIZE == 1)		
		{
			ChannelDataDao channelDataDao = new ChannelDataDao();
			ChannelData channelData = channelDataDao.createRecord(packet_id, date);
			
			channelData.date = date;
			channelData.aliPay = aliPay;
			channelData.tenPay = tenPay;
			channelData.yeePay = yeePay;
			channelData.mmPay = mmPay;
			channelData.newaliPay = newaliPay;
			channelData.newtenPay = newtenPay;
			channelData.newyeePay = newyeePay;
			channelData.newmmPay = newmmPay;
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
			channelData.settle();
			
			int datepay = channelData.aliPay+channelData.tenPay+channelData.yeePay+channelData.mmPay;
			//if((datepay > 0) || (channelData.registerNum > 0))
			channelDataDao.add(channelData);
		}*/
		
	}
	
	

	
	public static  ArrayList<GamePayData> get_GamedataList(String game_id,String BusinesserNo,String channel,
			String packet_id,String channelType,String dispaytype,String begindate,String enddate)
	{
		ArrayList<GamePayData> ret = new ArrayList<GamePayData>();
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
					packet_id = GamePayData.yisheng;
				}
				else if(game_id.equals("1000")&&channel.equals("358"))
				{
					packet_id = GamePayData.ruixun;
				}
				//ret.add(new GamePayData(game_id, BusinesserNo,channel,packet_id,datestr,channelType,dispaytype));
				if(game_id.equals("1000")&&StringUtil.is_nullString(channel))
				{
					GamePayData data1=new GamePayData(game_id, BusinesserNo,channel,GamePayData.yisheng,datestr,channelType,dispaytype);
					GamePayData data2=new GamePayData(game_id, BusinesserNo,channel,GamePayData.ruixun,datestr,channelType,dispaytype);
					data1.activityNum += data2.activityNum;
					data1.mmPay += data2.mmPay;
					data1.allPayAccoutNumBefore += data2.allPayAccoutNumBefore;
					ret.add(data1);
				}
				else
				{
					
			      ret.add(new GamePayData(game_id, BusinesserNo,channel,packet_id,datestr,channelType,dispaytype));
				}
			}
			// time = DateUtil.getTime();
			//DebuUtil.log("时间1:"+time);
		}
		return  ret;
	}
}

