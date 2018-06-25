package dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


import com.yeepay.HttpUtils;

import sdkReq.IdentifyServlet;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.ExcelUtil;
import util.StringUtil;

import model.Activate;
import model.ChannelData;
import model.ChannelDataList;
import model.Cooperation;
import model.Login;
import model.MmPay;
import model.Pay;
import model.Register;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import azul.CacheDao;

public class ChannelDataDao extends CacheDao{
	public ChannelDataDao() {
		init();
	}
	ArrayList<String> packetlist = new ArrayList<String>();
	
	public ChannelData createRecord_test(String packet_id,String date)
	{
		ChannelData channelData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and date<='%s' oder by date limit 1", baseTableName,packet_id,date);
		objList = getList(sql);
		//DebuUtil.log(sql);
		/*if(objList.size() > 0)
		{
			channelData = (ChannelData)objList.get(0);
		}
		else*/
		{
			channelData = new ChannelData();
			channelData.packet_id = packet_id;
			channelData.date = date;
			
			CooperationDao cooDao = new CooperationDao();
			Cooperation cooperation= cooDao.getRecord(packet_id);
			if(cooperation != null)
			{
				channelData.game_id = cooperation.getAppNo();
				channelData.BusinessNo = cooperation.getBusinessNo();
				channelData.channel = cooperation.getChannelNo();
			}
			
			Date curdate= new Date();
	    	Date lastdate = DateUtil.addDate("dd", -1, curdate);
	    	sql = String.format("select * from %s where packet_id='%s' and date='%s'", baseTableName,packet_id,DateUtil.getDate(lastdate));
			objList = getList(sql);
			if(objList.size() > 0)
			{
				ChannelData lastchannelData = (ChannelData)objList.get(0);
				channelData.allPayAccoutNumBefore = lastchannelData.allPayAccoutNumBefore;
				channelData.allPayBefore = lastchannelData.allPayBefore;
				channelData.allRegisterNumBefore = lastchannelData.allRegisterNumBefore;
				//channelData. = lastchannelData.allRegisterNumBefore;
			}
			//this.add(channelData);
		}
		return channelData;
	}
	
	
	public ChannelData getLastRecord(String packet_id,String date)
	{
		ChannelData channelData = null;
		ArrayList<Object> objList;
		String sql;
		//////select * from channel_data where packet_id='315167001' and date<='2014-10-25' order by date limit 1
		sql = String.format("select * from %s where packet_id='%s' and date<='%s'  order by date limit 1 ", baseTableName,packet_id,date);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			channelData = (ChannelData)objList.get(0);
		}
		else
		{
		}
		return channelData;
	}
	
	public ChannelData getRecord(String packet_id,String date)
	{
		ChannelData channelData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and date='%s'", baseTableName,packet_id,date);
		objList = getList(sql);
		//if(packet_id.equals("720265001"))
		//DebuUtil.log2(sql);
		if(objList.size() > 0)
		{
			channelData = (ChannelData)objList.get(0);
		}
		else
		{
			/*channelData = new ChannelData();
			channelData.packet_id = packet_id;
			channelData.date = date;
			
			CooperationDao cooDao = new CooperationDao();
			Cooperation cooperation= cooDao.getRecord(packet_id);
			if(cooperation != null)
			{
				channelData.game_id = cooperation.getAppNo();
				channelData.BusinessNo = cooperation.getBusinessNo();
				channelData.channel = cooperation.getChannelNo();
			}
			
			Date curdate= new Date();
	    	Date lastdate = DateUtil.addDate("dd", -1, curdate);
	    	sql = String.format("select * from %s where packet_id='%s' and date='%s'", baseTableName,packet_id,DateUtil.getDate(lastdate));
			objList = getList(sql);
			if(objList.size() > 0)
			{
				ChannelData lastchannelData = (ChannelData)objList.get(0);
				channelData.allPayAccoutNumBefore = lastchannelData.allPayAccoutNumBefore;
				channelData.allPayBefore = lastchannelData.allPayBefore;
				channelData.allRegisterNumBefore = lastchannelData.allRegisterNumBefore;
				//channelData. = lastchannelData.allRegisterNumBefore;
			}*/
			//this.add(channelData);
		}
		return channelData;
	}
	
	public void AddRecord(ChannelData channelData)
	{
		this.add(channelData);
	}
	public ChannelData createRecord(String packet_id,String date)
	{
		ChannelData channelData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and date='%s'", baseTableName,packet_id,date);
		objList = getList(sql);
		//DebuUtil.log(sql);
		/*if(objList.size() > 0)
		{
			channelData = (ChannelData)objList.get(0);
		}
		else*/
		{
			channelData = new ChannelData();
			channelData.packet_id = packet_id;
			channelData.date = date;
			
			CooperationDao cooDao = new CooperationDao();
			Cooperation cooperation= cooDao.getRecord(packet_id);
			if(cooperation != null)
			{
				channelData.game_id = cooperation.getAppNo();
				channelData.BusinessNo = cooperation.getBusinessNo();
				channelData.channel = cooperation.getChannelNo();
			}
			
			Date curdate= new Date();
	    	Date lastdate = DateUtil.addDate("dd", -1, curdate);
	    	//select * from channel_data where packet_id='315167001' and date<='2014-10-25' order by date limit 1
			sql = String.format("select * from %s where packet_id='%s' and date='%s'", baseTableName,packet_id,DateUtil.getDate(lastdate));
	    	//sql = String.format("select * from %s where packet_id='%s' and date<='%s' order by date limit 1 ", baseTableName,packet_id,DateUtil.getDate(lastdate));
			objList = getList(sql);
			if(objList.size() > 0)
			{
				ChannelData lastchannelData = (ChannelData)objList.get(0);
				channelData.allPayAccoutNumBefore = lastchannelData.allPayAccoutNumBefore;
				channelData.allPayBefore = lastchannelData.allPayBefore;
				channelData.allRegisterNumBefore = lastchannelData.allRegisterNumBefore;
				//channelData. = lastchannelData.allRegisterNumBefore;
			}
			//this.add(channelData);
		}
		return channelData;
	}
	
	//
	public int getDayPayNum2(String packet,String date)
	{
		 String sql = String.format("select register.username from register,pay  where register.packet_id='%s' and  register.date='%s' and register.username=pay.username and pay.state=1 group by register.username ",packet,date);
		 RegisterDao regDao = new RegisterDao();
		 int count = regDao.getRecordCount(sql);
		
          return count;
	}
	
	public int getDayPayNum(String packet,String date)
	{
		 String sql = String.format("select * from %s where  packet_id='%s' and date='%s' ", "register",packet,date);
		 RegisterDao regDao = new RegisterDao();
		 ArrayList<Object>  objList = regDao.getRegisterList(sql);
		 int count = 0;
		 
		 PayDao payDao = new PayDao();
		 
		 for(int i=0;i<objList.size();i++)
			{
			    Register register =(Register)objList.get(i);
				//int pay = channelData.newaliPay;
			    Pay pay = payDao.getPayRecordByUserName(register.getUsername());
			    //Pay pay = payDao.getPayRecordByDev();
			    if(register.getUsername().equals("135125833041"))
			    {
			    	 //DebuUtil.log("username:" +pay.username);
			    }
				if((pay != null) && (pay.state  == 1))
				{
					// DebuUtil.log("username:" +pay.username);
					count ++;
				}
				
			
			}
          return count;
	}
	
	public void settleDota()
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		//String packet_id = "223152001";
		sql = String.format("select * from %s where packet_id='100167001' and date>='2014-12-31' ", baseTableName);//and packet_id='211152001' and date='2014-05-04'
		objList = getList(sql);
		
		for(int i=0;i<objList.size();i++)
		{
			ChannelData channelData =(ChannelData)objList.get(i);
			
		
		   int daypay = channelData.mmPay+channelData.tcPay+channelData.uniPay+channelData.aliPay+channelData.yeePay+channelData.tenPay+channelData.unionPay;
		   channelData.newmmPay = channelData.mmPay;
		   channelData.newtcPay = channelData.tcPay;
		   channelData.newuniPay = channelData.uniPay;
		   channelData.newaliPay = channelData.aliPay;
		   channelData.newyeePay = channelData.yeePay;
		   channelData.newtenPay = channelData.tenPay;
		   channelData.newunionPay = channelData.unionPay;
		   int dayAllPay = 0;
		   if(daypay < 1)
		   {
			    dayAllPay = 0;
		   }
		   else
		   {
			   
			   dayAllPay =channelData.registerNum*4/100;
		   }
		  // if(daypay > 0)//
			{
				if(channelData.date.equals("2014-10-01"))
				{
				 DebuUtil.log("正在计算:"+channelData.packet_id+" , " +channelData.date);
				}
				  //getDayPayNum(channelData.packet_id,channelData.date);//
				 channelData.setDayAllPayAcount(dayAllPay);
				 ChannelDataDao cannelDataDao= new ChannelDataDao();
				// channelData.dayAllPayAcount = channelData.registerNum*4/400;
				 channelData.dayAllAcount = channelData.registerNum;
				 cannelDataDao.edit(channelData);
				 DebuUtil.log("计算结果:"+channelData.packet_id+" , " +channelData.date+",dayAllPay="+dayAllPay);
			}
			
			//channelData.dayAllAcount = channelData.activityRegNum;
			// ChannelDataDao cannelDataDao= new ChannelDataDao();
			// cannelDataDao.edit(channelData);
			//DebuUtil.log("dayAllAcount:"+channelData.dayAllAcount);
		
		}
		
		return ;
	}
	
	public void settleDayPayNum()
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		//String packet_id = "223152001";
		sql = String.format("select * from %s where  date>='2014-11-21' and date<'2014-11-23'  ", baseTableName);//and packet_id='211152001' and date='2014-05-04',date<'2014-11-24'
		objList = getList(sql);
		
		//settleDota();
		for(int i=0;i<objList.size();i++)
		{
			ChannelData channelData =(ChannelData)objList.get(i);
		
		  // int daypay = channelData.newmmPay+channelData.newtcPay+channelData.newuniPay+channelData.newaliPay+channelData.newyeePay+channelData.newtenPay+channelData.newunionPay;
			//if(channelData.packet_id.length() >= 10)
			//{
			//	daypay = 0;
			//}
		  // if((daypay > 0))//
			{
				 DebuUtil.log("正在计算:"+channelData.packet_id+" , " +channelData.date);
				 int dayAllPay = getDayPayNum2(channelData.packet_id,channelData.date);//
				// int dayAllPay2 = getDayPayNum(channelData.packet_id,channelData.date);//
				// if(dayAllPay != dayAllPay2)
				 {
				//	 DebuUtil.log("计算结果错误");
				 }
				 //channelData.setDayAllPayAcount(dayAllPay);
				 ChannelDataDao cannelDataDao= new ChannelDataDao();
				 
				 //channelData.dayAllAcount = channelData.activityRegNum;
				 cannelDataDao.edit(channelData);
				 DebuUtil.log("计算结果:"+channelData.packet_id+" , " +channelData.date+",dayAllPay="+dayAllPay);
			}
			
		
		}
		
		return ;
	}
	
	public int getDayPayNum(String packet,String date,String payType)
	{
		 String sql = String.format("select sum(amount) from %s where  packet_id='%s' and date='%s' and state = 1 and pay_type='%s' ", "pay",packet,date,payType);
		 //RegisterDao regDao = new RegisterDao();
		 //ArrayList<Object>  objList = regDao.getRegisterList(sql);
		 int count = 0;
		
		 PayDao payDao = new PayDao();
		 count = payDao.getCount(sql);
		 
		 DebuUtil.log("正在计算:"+packet+" , " +date+","+payType+","+count);
         return count;
	}
	
	public void settlePay()
	{ 
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		//String packet_id = "223152001";
		sql = String.format("select * from %s where  date>='2015-01-01' and date<'2015-02-01' ", baseTableName);//and packet_id='211152001' and date='2014-05-04',date<'2014-11-24'
		objList = getList(sql);
		
		//settleDota();
		for(int i=0;i<objList.size();i++)
		{
			ChannelData channelData =(ChannelData)objList.get(i);
		
		  // int daypay = channelData.newmmPay+channelData.newtcPay+channelData.newuniPay+channelData.newaliPay+channelData.newyeePay+channelData.newtenPay+channelData.newunionPay;
			//if(channelData.packet_id.length() >= 10)
			//{
			//	daypay = 0;
			//}
		  // if((daypay > 0))//
			{
				 ///DebuUtil.log("正在计算:"+channelData.packet_id+" , " +channelData.date);
				if(channelData.packet_id.length() < 10)
				{
				 DebuUtil.log("正在计算:"+channelData.packet_id+" , " +channelData.aliPay);
				 channelData.aliPay =  getDayPayNum(channelData.packet_id,channelData.date,"alipay");//
				 channelData.yeePay =  getDayPayNum(channelData.packet_id,channelData.date,"yeepay");//
				 channelData.unionPay  =  getDayPayNum(channelData.packet_id,channelData.date,"unionpay");//
				 channelData.tenPay  =  getDayPayNum(channelData.packet_id,channelData.date,"tenpay");//
				/* channelData.tcPay  =  getDayPayNum(channelData.packet_id,channelData.date,"tcpay");//
				 channelData.uniPay  =  getDayPayNum(channelData.packet_id,channelData.date,"unipay");//
				 channelData.mmPay  =  getDayPayNum(channelData.packet_id,channelData.date,"mmpay");*/
				 ChannelDataDao cannelDataDao= new ChannelDataDao();			
				 cannelDataDao.edit(channelData);
				}
				// DebuUtil.log("计算结果:"+channelData.packet_id+" , " +channelData.date+",dayAllPay="+dayAllPay);
			}
			
		
		}
		
		return ;
	}
	
	public static Map<String,String> getMapByReqStr(String str)
    {
    	//String ip = "packet_id=1235&date=2014-12-20&mmpay=6532";
        StringTokenizer token=new StringTokenizer(str,"&");  
        Map<String,String> map = new HashMap<String,String>(); 
        while(token.hasMoreElements()){  
        	String splitstr = token.nextToken();
        	String results[]=splitstr.split("=");
        	if(results.length > 1)
        	{
        	   map.put(results[0], results[1]);
        	}
        } 
        
        return map;
      // System.out.print("date="+map.get("date")); 
       
    }
	
	public void settleXingXingPay(String date)
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		//String packet_id = "223152001";
		sql = String.format("select * from %s where (game_id='199' or game_id='5028' or game_id='5029' or game_id='5030' or game_id='5031' or game_id='5032' or game_id='5033') and date='%s' ", baseTableName,date);//and packet_id='2200003386'   and date='2014-05-04',date<'2014-11-24'
		//sql = String.format("select * from %s where () and date='%s' ", baseTableName,date);//and packet_id='2200003386'   and date='2014-05-04',date<'2014-11-24'
		
		objList = getList(sql);
		
		//settleDota();
		for(int i=0;i<objList.size();i++)
		{
			ChannelData channelData =(ChannelData)objList.get(i);
		
			{
				DebuUtil.log2("正在计算:"+channelData.packet_id+" , " +channelData.date);
				//if(channelData.packet_id.length() < 10)//28W
				{
				
				  sql = String.format("select sum(amount) from mm_pay where   channel='%s' and date='%s' and ret=1 ",channelData.packet_id,channelData.date);					
				  channelData.mmPay  =  getCount(sql);//
				  channelData.settle_mmPay = channelData.mmPay;
				  DebuUtil.log2("mmPay:"+channelData.mmPay);
				  /*sql = String.format("select sum(amount) from pay where  pay_type='unipay' and packet_id='%s' and date='%s' and state=1 ",channelData.packet_id,channelData.date);	
				  channelData.uniPay  =  getCount(sql);//
				  channelData.settle_uniPay = channelData.uniPay;
				  
				  sql = String.format("select sum(amount) from pay where  pay_type='tcpay'and packet_id='%s' and date='%s' and state=1 ",channelData.packet_id,channelData.date);	
				  channelData.tcPay  =  getCount(sql);//
				  channelData.settle_tcPay = channelData.tcPay;*/
				  
				  ChannelDataDao cannelDataDao= new ChannelDataDao();			
				  cannelDataDao.edit(channelData);
				}
				// DebuUtil.log("计算结果:"+channelData.packet_id+" , " +channelData.date+",dayAllPay="+dayAllPay);
			}
			
		
		}
		
		return ;
	}
	
	
	public void settleXingXingPay()
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		//String packet_id = "223152001";
		sql = String.format("select * from %s where (game_id='5028' or game_id='5029' or game_id='5030' or game_id='5031' or game_id='5032' or game_id='5033') and date>='2015-02-01' ", baseTableName);//and packet_id='2200003386'   and date='2014-05-04',date<'2014-11-24'
		objList = getList(sql);
		
		//settleDota();
		for(int i=0;i<objList.size();i++)
		{
			ChannelData channelData =(ChannelData)objList.get(i);
		
			{
				 DebuUtil.log("正在计算:"+channelData.packet_id+" , " +channelData.date);
				//if(channelData.packet_id.length() < 10)//28W
				{
				
				  sql = String.format("select sum(amount) from mm_pay where   channel='%s' and date='%s' and ret=1 ",channelData.packet_id,channelData.date);					
				  channelData.mmPay  =  getCount(sql);//
				  channelData.settle_mmPay = channelData.mmPay;
				  
				  /*sql = String.format("select sum(amount) from pay where  pay_type='unipay' and packet_id='%s' and date='%s' and state=1 ",channelData.packet_id,channelData.date);	
				  channelData.uniPay  =  getCount(sql);//
				  channelData.settle_uniPay = channelData.uniPay;
				  
				  sql = String.format("select sum(amount) from pay where  pay_type='tcpay'and packet_id='%s' and date='%s' and state=1 ",channelData.packet_id,channelData.date);	
				  channelData.tcPay  =  getCount(sql);//
				  channelData.settle_tcPay = channelData.tcPay;*/
				  
				  ChannelDataDao cannelDataDao= new ChannelDataDao();			
				  cannelDataDao.edit(channelData);
				}
				// DebuUtil.log("计算结果:"+channelData.packet_id+" , " +channelData.date+",dayAllPay="+dayAllPay);
			}
			
		
		}
		
		return ;
	}
	
	
	
	public void xiuzengshuju()
	{
		Activate activate = null;
		ArrayList<Object> objList27,objList28;
		String sql;
		//String packet_id = "223152001";
		sql = String.format("select * from %s where date='%s'", baseTableName,"2014-10-27");
		objList27 = getList(sql);
		
		sql = String.format("select * from %s where (date='%s' or date='%s')", baseTableName,"2014-10-28","2014-10-29");
		//sql += " and packet_id='"+packet_id+"'";
		objList28 = getList(sql);
		//DebuUtil.log(sql);
		
		for(int i=0;i<objList28.size();i++)
		{
			ChannelData channelData28 =(ChannelData)objList28.get(i);
			ChannelData channelData27 = null;
			boolean bedidt = false;
			DebuUtil.log(channelData28.packet_id);
			for(int j=0;j<objList27.size();j++)
			{
				channelData27 =(ChannelData)objList27.get(j);
				if(channelData27.packet_id.equals(channelData28.packet_id))
				{
					
					if(channelData27.date.equals("2014-10-27"))
					{
						if(channelData27.allPayAccoutNumBefore > channelData28.allPayAccoutNumBefore)
						{
							channelData28.allPayAccoutNumBefore += channelData27.allPayAccoutNumBefore;
							bedidt = true;
						}
						if(channelData27.allPayBefore > channelData28.allPayBefore)
						{
							channelData28.allPayBefore += channelData27.allPayBefore;
							bedidt = true;
						}
						if(channelData27.allRegisterNumBefore > channelData28.allRegisterNumBefore)
						{
							channelData28.allRegisterNumBefore += channelData27.allRegisterNumBefore;
							bedidt = true;
						}
					}
					break;
				}
			}
			
				
			if(bedidt)
			{
				ChannelData curchannelData28 = ChannelDataList.getChannelData2(channelData28.packet_id, channelData28.date);
				if(curchannelData28 != null)
				{
					curchannelData28.setAllPayAccoutNumBefore2(channelData28.allPayAccoutNumBefore);
					curchannelData28.setAllPayBefore2( channelData28.allPayBefore);
					curchannelData28.setAllRegisterNumBefore2(channelData28.allRegisterNumBefore);
				}
				edit(channelData28);
			}
		
		}
		
		return ;
	}
	
	//1.激活，当天统计激活数activityNum加1，CPA结算值settle；
	public void Activate(Activate activate,int bSA)
	{
		ChannelData channelData = ChannelDataList.getChannelData(activate.packet_id,activate.date);
		
		if(channelData != null)
		{
			
			DebuUtil.log("激活数加一:"+channelData.activityNum);
			channelData.activityNumInc(1);
			if(bSA == 1)
			{
				//channelData.dayAllAcountInc(1);
			}
			/*synchronized(channelData.activityNum)
			{
				channelData.activityNum ++;
			}*///
			DebuUtil.log("激活数:"+channelData.activityNum);
			ChannelData tempData = new ChannelData();
			tempData.clearData();
			tempData.id = channelData.id;
			tempData.activityNum = channelData.activityNum;
			if(bSA == 1)
			{
				//tempData.dayAllAcount = channelData.dayAllAcount;
			}
			
			 channelData.settle(tempData);
			
			this.edit(tempData);
			
		}
		
	}
		
	//2。注册，如果设备第一次注册，当天数据注册数registerNum加1，allRegisterNumBefore加1,CPA_R结算值settle；,激活日期数据activityRegNum加1
	public void register(Register obj)
	{
		ChannelData channelData = ChannelDataList.getChannelData(obj.packet_id,obj.date);
		if(channelData != null)
		{
			
						
			DebuUtil.log("注册数加一,registerNum="+channelData.registerNum);
			DebuUtil.log("总注册数加一,registerNum="+channelData.allRegisterNumBefore);
			/*synchronized(channelData.registerNum)
			{
				channelData.registerNum ++;
			}*/
			channelData.registerNumInc(1);
			channelData.allRegisterNumBeforeInc(1);
			/*synchronized(channelData.allRegisterNumBefore)
			{
				channelData.allRegisterNumBefore ++;
			}*/
			DebuUtil.log("registerNum="+channelData.registerNum);
			DebuUtil.log("allRegisterNumBefore="+channelData.allRegisterNumBefore);
			
			ChannelData tempData = new ChannelData();
			tempData.clearData();
			tempData.id = channelData.id;
			tempData.registerNum = channelData.registerNum;
			tempData.allRegisterNumBefore = channelData.allRegisterNumBefore;
			
			channelData.settle(tempData);
			
			this.edit(tempData);
			
			
			ActivateDao activateDao = new ActivateDao();
			Activate activate = activateDao.getActivateRecord(obj.device_id,obj.game_id);
			if(activate != null)
			{
				channelData = ChannelDataList.getChannelData(activate.packet_id,activate.date);
				if(channelData != null)
				{
										
					DebuUtil.log("激活注册数加一,activityRegNum="+channelData.activityRegNum);
					/*synchronized(channelData.activityRegNum)
					{
						channelData.activityRegNum ++;
					}*/
					//DebuUtil.log("注册激活数,dayAllAcount="+channelData.dayAllAcount);
					channelData.activityRegNumInc(1);
					DebuUtil.log("activityRegNum="+channelData.activityRegNum);
					//channelData.dayAllAcountInc(1);
					
					tempData = new ChannelData();
					tempData.clearData();
					tempData.id = channelData.id;
					tempData.activityRegNum = channelData.activityRegNum;
					//tempData.dayAllAcount = channelData.dayAllAcount;
					this.edit(tempData);
					
				}
			}
		}
	}
		
	//3。登录，如果当天第一次登录，当天loginNum加上，如果是次留，激活日期数据day1Num则加1	
	void login(Login obj,int alive,int bnew)
	{	
		ChannelData channelData = ChannelDataList.getChannelData(obj.packet_id,obj.date);
		 
		if(channelData != null)
		{
						
			DebuUtil.log("登录数加一,loginNum="+channelData.loginNum);
			/*synchronized(channelData.loginNum)
			{
				channelData.loginNum ++;
			}*/
			channelData.loginNumInc(1);
			ChannelData tempData = new ChannelData();
			tempData.clearData();
			tempData.id = channelData.id;
			tempData.loginNum = channelData.loginNum;
			edit(tempData);
			
			
			if((alive > 0) || (bnew == 1))
			{
				DebuUtil.log("alive="+alive);
				//
				ActivateDao activateDao = new ActivateDao();
				Activate tactivate = activateDao.getActivateRecord(obj.device_id,obj.game_id);
				if(tactivate != null)
				{
					//
					//channelData = getRecord(tactivate.packet_id,tactivate.date);
					channelData = ChannelDataList.getChannelData(tactivate.packet_id,tactivate.date);
				}
				DebuUtil.log("channelData="+channelData);
				if(channelData != null)
				{					
					if(bnew == 1)
					{
						channelData.dayAllAcountInc(1);
					    tempData = new ChannelData();
						tempData.clearData();
						tempData.id = channelData.id;
					    tempData.dayAllAcount = channelData.dayAllAcount;	
					    DebuUtil.log("激活登录数加一,dayAllAcount="+channelData.dayAllAcount);
					}
					
					 if(alive == Activate.DATEODay)
						 
					{
						 if(obj.packet_id.equals("211270001"))
						 {
							 DebuUtil.log2("次留数累加 设备="+obj.device_id+"游戏="+obj.game_id);
						 }
					    /*synchronized(channelData.day1Num)		
					    {
					    	channelData.day1Num ++;
					    }*/
					    channelData.day1NumInc(1);
					    tempData = new ChannelData();
						tempData.clearData();
						tempData.id = channelData.id;
					    tempData.day1Num = channelData.day1Num;					    						
						
					}
					else if(alive == Activate.DATESDay)
					{
						
					    /*synchronized(channelData.day7Num)
					    {
					       channelData.day7Num ++;
					    }*/
						channelData.day7NumInc(1);
					    tempData = new ChannelData();
						tempData.clearData();
						tempData.id = channelData.id;
					    tempData.day7Num = channelData.day7Num;
						    						
					}
					else if(alive == Activate.DATEMDay)
					{
						/*synchronized(channelData.day30Num)
						{							
						    channelData.day30Num ++;						  						    
						}*/
						channelData.day30NumInc(1);
				        tempData = new ChannelData();
						tempData.clearData();
						tempData.id = channelData.id;
					    tempData.day30Num = channelData.day30Num;
					}
					DebuUtil.log("次留数加一,alive="+alive);
					edit(tempData);
				}
			}
			
		}
		
	}
	
	
	public void paySA(MmPay obj)
	{
		DebuUtil.log("paySA obj.packet_id="+obj.packet_id);
		String packet_id=obj.packet_id;
		String date = DateUtil.getDate();
		ChannelData channelData = ChannelDataList.getChannelData(packet_id,date);
		if(channelData != null)
		{			
			ChannelData tempData = new ChannelData();
			tempData.clearData();
			tempData.id = channelData.id;
			
			if(obj.getRet() > 0)
			{
				channelData.FeeNumInc(1);
				tempData.feeNum = channelData.feeNum;
			}
			
			channelData.MsmNumInc(1);
			tempData.msmNum = channelData.msmNum;
			
			/*int ratio = channelData.feeNum*100/channelData.msmNum;
			if(ratio < 50)
			{
				if(!packetlist.contains(obj.packet_id))
				{
			        IdentifyServlet.sendSMS("18025341241","渠道"+obj.packet_id+"转化低");
			        packetlist.add(obj.packet_id);
				}
			}*/
			//if(tempData.feeNum>tempData.msmNum)
			//{
				//tempData.msmNum = tempData.feeNum;
			//}
			//DebuUtil.log3("tempData.feeNum="+tempData.feeNum);
			//DebuUtil.log3("tempData.msmNum="+tempData.msmNum);
			edit(tempData);
		}
	}
	
	
	
	public void smsReq(String packet,int state)
	{
		String packet_id=packet;
		String date = DateUtil.getDate();
		ChannelData channelData = ChannelDataList.getChannelData(packet_id,date);
		if(channelData != null)
		{			
			// DebuUtil.log("channelData");
			ChannelData tempData = new ChannelData();
			tempData.clearData();
			tempData.id = channelData.id;
			
			//DebuUtil.log("state="+state);
			if(state == 1)
			{
				channelData.ReqOkNumInc(1);
				tempData.reqOkNum = channelData.reqOkNum;
				//DebuUtil.log("tempData.reqOkNum="+tempData.reqOkNum);
			}
			
			channelData.ReqNumInc(1);
			tempData.reqNum = channelData.reqNum;
			
			edit(tempData);
		}
	}
	
	//支付请求统计
	public void payReq(Pay obj)
	{
		ChannelData channelData = null;
		Activate tactivate = null;
	
		ActivateDao activateDao = new ActivateDao();
	    tactivate = activateDao.getActivateRecord(obj.device_id,obj.game_id);
		
	   // if(obj.device_id.equals("865903029745048"))
		//{
		   // DebuUtil.log2("obj.device_id="+obj.device_id);
		//}
	    
		if(tactivate != null)
		{
			//channelData = getRecord(tactivate.packet_id,tactivate.date);
			//this.add(channelData);
			 channelData = ChannelDataList.getChannelData(tactivate.packet_id,tactivate.date);
		}
		
		if(channelData != null)
		{			
			//synchronized(channelData.allPayBefore)
			//{
			ChannelData tempData = new ChannelData();
			tempData.clearData();
			tempData.id = channelData.id;
			
			channelData.ReqOkNumInc(1);
			tempData.reqOkNum = channelData.reqOkNum;
			
			edit(tempData);
		}
		
	}
	
	//4。支付，当天数据相对类型aliPay累加，allPayBefore累加,CPS结算值settle；，如果用户第一次付费，allPayAccoutNumBefore加1，激活日期数据相对类型newaliPay累加，
	/**
	 * 将数据统计到channeldata表中
	 * @param obj
	 * @param bSDK
	 */
	public void pay(Pay obj,boolean bSDK)
	{
		ChannelData channelData = ChannelDataList.getChannelData(obj.packet_id,obj.date);
		 
		if(obj.game_id.equals("172"))
		{
			//
			//DebuUtil.log3("paytype: "+obj.pay_type);
		}
		if(channelData != null)
		{			
			//synchronized(channelData.allPayBefore)
			//{
			ChannelData tempData = new ChannelData();
			tempData.clearData();
			tempData.id = channelData.id;
			
			if(ConstValue.DEBUG == 1)
			{
				 DebuUtil.log("对应类型金额累加 packet_id="+obj.packet_id);
			}
			
			//DebuUtil.log("对应类型金额累加");
			if(obj.pay_type.equals(Pay.AliPayType))
			{
				//channelData.aliPay += obj.amount;
				channelData.aliPayInc(obj.amount);
				tempData.aliPay = channelData.aliPay;
			}
			else if(obj.pay_type.equals(Pay.MmPayType))
			{
				
				//channelData.mmPay += obj.amount;
				channelData.mmPayInc(obj.amount);
				tempData.mmPay = channelData.mmPay;
			}
			else if(obj.pay_type.equals(Pay.TCPayType))
			{
				//channelData.mmPay += obj.amount;
				
				//if(ConstValue.STAND_ALONE == 1)
				{
					channelData.tcPayInc(obj.amount);
					tempData.tcPay = channelData.tcPay;
					//DebuUtil.log3("tcPay: "+tempData.tcPay);
				}
			}
			else if(obj.pay_type.equals(Pay.UniPayType))
			{
				//channelData.mmPay += obj.amount;
				//if(ConstValue.STAND_ALONE == 1)
				{
					channelData.uniPayInc(obj.amount);
					tempData.uniPay = channelData.uniPay;
				}
			}
			else if(obj.pay_type.equals(Pay.TenPayType))
			{
				//channelData.tenPay += obj.amount;
				channelData.tenPayInc(obj.amount);
				tempData.tenPay = channelData.tenPay;
			}
			else if(obj.pay_type.equals(Pay.UnionPayType))
			{
				//channelData.unionPay += obj.amount;
				channelData.unionPayInc(obj.amount);
				tempData.unionPay = channelData.unionPay;
			}
			else if(obj.pay_type.equals(Pay.YeePayType))
			{
				//channelData.yeePay += obj.amount;
				channelData.yeePayInc(obj.amount);
				tempData.yeePay = channelData.yeePay;
			}
			else if(obj.pay_type.equals(Pay.WoPayType))
			{
				//channelData.yeePay += obj.amount;
				channelData.woPayInc(obj.amount);
				tempData.woPay = channelData.woPay;
			}
			else if(obj.pay_type.equals(Pay.OpenPayType))
			{
				//channelData.yeePay += obj.amount;
				channelData.openPayInc(obj.amount);
				tempData.openPay = channelData.openPay;
			}
			else if(obj.pay_type.equals(Pay.JDPayType))
			{
				//channelData.yeePay += obj.amount;
				channelData.webPayInc(obj.amount);
				tempData.webPay = channelData.webPay;
			}
			else if(obj.pay_type.equals(Pay.FanHeType))
			{
				//channelData.yeePay += obj.amount;
				channelData.boxPayInc(obj.amount);
				tempData.boxPay = channelData.boxPay;
			}
			else if(obj.pay_type.equals(Pay.MzPayType))
			{
				//channelData.yeePay += obj.amount;
				channelData.mzPayInc(obj.amount);
				tempData.mzPay = channelData.mzPay;
			}
			else if(obj.pay_type.equals(Pay.OnlyPayType))
			{
				//channelData.yeePay += obj.amount;
				channelData.onlyPayInc(obj.amount);
				tempData.onlyPay = channelData.onlyPay;
				DebuUtil.log("channeldata表添加专属币充值金额onlyPay="+channelData.onlyPay);
			}

			//channelData.allPayBefore += obj.amount;
			channelData.allPayBeforeInc(obj.amount);
			tempData.allPayBefore = channelData.allPayBefore;
			
			
			//DebuUtil.log("充值金额累加,allPayBefore="+channelData.allPayBefore);
			if((bSDK == true))//(ConstValue.bNew == 1) &&
			{
				//channelData.payTimesInc(1);				
				//tempData.pay_times = channelData.pay_times;
				if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("付费次数累加");
				}
				//增加支付次数			
				if( (bSDK == true))//(ConstValue.bNew == 1) &&
				{
					channelData.FeeNumInc(1);				
					tempData.feeNum = channelData.feeNum;
				}
			}
			CooperationDao cooDao = new CooperationDao();
			Cooperation cooperation= cooDao.getRecord(obj.packet_id);
			
			if(bSDK == false)//if(ConstValue.PAY_SERVER == 1)
			{
				if(obj.type.equals("A"))
				{
					if(obj.pay_type.equals(Pay.MmPayType))
					{					
						channelData.mmPaySettleInc(obj.amount);//
						tempData.settle_mmPay = channelData.settle_mmPay;
					}					
					
				}
			}
			else
			{
			   channelData.settle(tempData);
			}
			
			int bnew = 0;//
			
			if(bSDK)//if(ConstValue.PAY_SERVER != 1)
			{
				PayDao payDao = new PayDao();
				String sql = String.format("select * from pay where device_id='%s' and game_id='%s' and state=1", obj.device_id,obj.game_id);
				ArrayList payList= payDao.getList(sql);
				if(obj.game_id.equals("167"))
				{
					//DebuUtil.log3("sql="+sql);
					//DebuUtil.log3("payList.size()="+payList.size());
					//
				}
				if(payList.size() == 1)
				{
					//channelData.allPayAccoutNumBefore ++;
					//DebuUtil.log3("allPayAccoutNumBefore="+ channelData.allPayAccoutNumBefore);
					bnew = 1;
					channelData.allPayAccoutNumBeforeInc(1);
					tempData.allPayAccoutNumBefore = channelData.allPayAccoutNumBefore;
					//DebuUtil.log3("allPayAccoutNumBefore="+ channelData.allPayAccoutNumBefore);
				}
			}
			
			edit(tempData);
			//}
			Activate tactivate = null;
			channelData = null;
			
			if(bSDK)//if(ConstValue.PAY_SERVER != 1)
			{
			    ActivateDao activateDao = new ActivateDao();
			    tactivate = activateDao.getActivateRecord(obj.device_id,obj.game_id);
			}
			
			if(tactivate != null)
			{
				//channelData = getRecord(tactivate.packet_id,tactivate.date);
				//this.add(channelData);
				 channelData = ChannelDataList.getChannelData(tactivate.packet_id,tactivate.date);
			}
			
			if(channelData != null)
			{
				tempData = new ChannelData();
				tempData.clearData();
				tempData.id = channelData.id;
				
				if(bnew == 1)
				{
					channelData.dayAllPayAcountInc(1);;				
					tempData.dayAllPayAcount = channelData.dayAllPayAcount;
					
					
					//DebuUtil.log("新增付费用户累加");//
				}
				//增加支付次数			
				/*if((ConstValue.bNew == 1) && (bSDK == true))
				{
					channelData.FeeNumInc(1);				
					tempData.feeNum = channelData.feeNum;
				}*/
				
				//按天统计
				//if(ConstValue.bNew == 1)
				{
					String curdate = DateUtil.getDate();
					long datedif = 0;
					datedif = DateUtil.getDayDiff(tactivate.date, curdate);
					if(datedif == 0)
					{
						channelData.Day1PayInc(obj.amount);				
						tempData.day1Pay = channelData.day1Pay;
						
						if((bSDK == true))//(ConstValue.bNew == 1) && 
						{
							if(bnew == 1)
							{
							   channelData.day1PayAcountInc(1);//支付用户数				
							   tempData.day1_pay_acount = channelData.day1_pay_acount;
							   if(ConstValue.DEBUG == 1)
							   {
								   DebuUtil.log("新增付费用户累加");
							   }
							}
							channelData.day1PayTimesInc(1);				
							tempData.day1_pay_times = channelData.day1_pay_times;
							if(ConstValue.DEBUG == 1)
							{
								 DebuUtil.log("新增付费次数累加");
							}
						}
						
						
					}
					else if(datedif == 1)
					{
						channelData.Day2PayInc(obj.amount);				
						tempData.day2Pay = channelData.day2Pay;
					}
					else if(datedif == 2)
					{
						channelData.Day3PayInc(obj.amount);				
						tempData.day3Pay = channelData.day3Pay;
					}
					else if(datedif == 3)
					{
						channelData.Day4PayInc(obj.amount);				
						tempData.day4Pay = channelData.day4Pay;
					}
					else if(datedif == 4)
					{
						channelData.Day5PayInc(obj.amount);				
						tempData.day5Pay = channelData.day5Pay;
					}
					else if(datedif == 5)
					{
						channelData.Day6PayInc(obj.amount);				
						tempData.day6Pay = channelData.day6Pay;
					}
					else if(datedif == 6)
					{
						channelData.Day7PayInc(obj.amount);				
						tempData.day7Pay = channelData.day7Pay;
					}
				}
				
				if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("统计新增用户付费 obj.pay_type="+obj.pay_type);
				}
				
				if(obj.pay_type.equals(Pay.AliPayType))
				{				
					channelData.newaliPayInc(obj.amount);				
					tempData.newaliPay = channelData.newaliPay;
				
				}
				else if(obj.pay_type.equals(Pay.MmPayType))
				{											
					//channelData.newmmPay += obj.amount;	
					channelData.newmmPayInc(obj.amount);
					tempData.newmmPay = channelData.newmmPay;
					
				}
				else if(obj.pay_type.equals(Pay.TCPayType))
				{											
					//channelData.newmmPay += obj.amount;	
					if(ConstValue.STAND_ALONE == 1)
					{
						channelData.newtcPayInc(obj.amount);
						tempData.newtcPay = channelData.newtcPay;
					}
					
				}
				else if(obj.pay_type.equals(Pay.UniPayType))
				{											
					//channelData.newmmPay += obj.amount;	
					if(ConstValue.STAND_ALONE == 1)
					{
						channelData.newuniPayInc(obj.amount);
						tempData.newuniPay = channelData.newuniPay;
						if(ConstValue.DEBUG == 1)
						{
							 DebuUtil.log("tempData.newuniPay="+tempData.newuniPay);
						}
					}
					
				}
				else if(obj.pay_type.equals(Pay.TenPayType))
				{					
					//channelData.newtenPay += obj.amount;	
					channelData.newtenPayInc(obj.amount);
					tempData.newtenPay = channelData.newtenPay;						
					
				}
				else if(obj.pay_type.equals(Pay.UnionPayType))
				{					
					//channelData.newunionPay += obj.amount;	
					channelData.newunionPayInc(obj.amount);
					tempData.newunionPay = channelData.newunionPay;						
					
				}
				else if(obj.pay_type.equals(Pay.YeePayType))
				{					
					//channelData.newyeePay += obj.amount;	
					channelData.newyeePayInc(obj.amount);
					tempData.newyeePay = channelData.newyeePay;											
				}
				else if(obj.pay_type.equals(Pay.WoPayType))
				{					
					//channelData.newyeePay += obj.amount;	
					channelData.newwoPayInc(obj.amount);
					tempData.newwoPay = channelData.newwoPay;											
				}
				else if(obj.pay_type.equals(Pay.OpenPayType))
				{					
					//channelData.newyeePay += obj.amount;	
					channelData.newopenPayInc(obj.amount);
					tempData.newopenPay = channelData.newopenPay;											
				}
				else if(obj.pay_type.equals(Pay.JDPayType))
				{					
					//channelData.newyeePay += obj.amount;	
					channelData.newwebPayInc(obj.amount);
					tempData.newwebPay = channelData.newwebPay;											
				}
				else if(obj.pay_type.equals(Pay.FanHeType))
				{					
					//channelData.newyeePay += obj.amount;	
					channelData.newboxPayInc(obj.amount);
					tempData.newboxPay = channelData.newboxPay;											
				}
				else if(obj.pay_type.equals(Pay.MzPayType))
				{					
					//channelData.newyeePay += obj.amount;	
					channelData.newmzPayInc(obj.amount);
					tempData.newmzPay = channelData.newmzPay;											
				}
				else if(obj.pay_type.equals(Pay.OnlyPayType))
				{					
					//channelData.newyeePay += obj.amount;	
					channelData.newonlyPayInc(obj.amount);
					tempData.newonlyPay = channelData.newonlyPay;											
				}
			   //DebuUtil.log("新增对应类型金额累加");
			   edit(tempData);
				
			}
			
		}
	}
	
	//6.退出数据提义，升级和在线时长,激活日期数据realregisterNum加1,
		public void RealUser(Activate act)
		{
			ChannelData channelData  = null;
			ActivateDao activateDao = new ActivateDao();
			Activate activate = act;//activateDao.getActivateRecord(act.device_id,act.game_id);
			if(activate != null)
			{
				DebuUtil.log("找到激活数据");//
				channelData = ChannelDataList.getChannelData(activate.packet_id,activate.date);
				 
				if(channelData != null)
				{								
					DebuUtil.log("有效用户数加一,realRegisterNum="+channelData.realRegisterNum);
					/*synchronized(channelData.realRegisterNum)
					{
						channelData.realRegisterNum ++;
					}*/
					channelData.realRegisterNumInc(1);
					ChannelData tempData = new ChannelData();
					tempData.clearData();
					tempData.id = channelData.id;
					tempData.realRegisterNum = channelData.realRegisterNum;
					edit(tempData);
				}
			}
		}
		
	//6.退出数据提义，升级和在线时长,激活日期数据realregisterNum加1,
	public void RealUser(Register reg)
	{
		ChannelData channelData  = null;
		ActivateDao activateDao = new ActivateDao();
		Activate activate = activateDao.getActivateRecord(reg.device_id,reg.game_id);
		if(activate != null)
		{
			DebuUtil.log("找到激活数据");//
			channelData = ChannelDataList.getChannelData(activate.packet_id,activate.date);
			 
			if(channelData != null)
			{								
				DebuUtil.log("有效用户数加一,realRegisterNum="+channelData.realRegisterNum);
				/*synchronized(channelData.realRegisterNum)
				{
					channelData.realRegisterNum ++;
				}*/
				channelData.realRegisterNumInc(1);
				ChannelData tempData = new ChannelData();
				tempData.clearData();
				tempData.id = channelData.id;
				tempData.realRegisterNum = channelData.realRegisterNum;
				edit(tempData);
			}
		}
	}
	
	public void reviseData(String sql)
	{
		ChannelData channelData = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			channelData = (ChannelData)objList.get(i);
			channelData.reviseData();
			this.edit(channelData);
		}
	}
	
	public void genData(String sql)
	{
		ChannelData channelData = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			channelData = (ChannelData)objList.get(i);
			channelData.genData();
		}
	}
	
	public void changeUSer(String sql)
	{
		ChannelData channelData = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			channelData = (ChannelData)objList.get(i);
			channelData.changeUser();
			this.edit(channelData);
		}
	}
	
	
	public void changeIMEI(String sql)
	{
		ChannelData channelData = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			channelData = (ChannelData)objList.get(i);
			channelData.changeIMEI();
			
		}
	}
	
	
	public void genLogin(String sql)
	{
		ArrayList<Object> objList;
		objList = getList(sql);
		for(int i=0;i<objList.size();i++)
		{
			ChannelData channelData =(ChannelData)objList.get(i);
			//channelData.genLogin();
		}
	}
	
	public void changeTime(String sql)
	{
		ChannelData channelData = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			channelData = (ChannelData)objList.get(i);
			channelData.changeTime();
			
		}
	}
	
	/**
	 * 定时任务需要调用的方法
	 * @return
	 */
	public List<ArrayList> getRecordByTask(){
		String dateStr = DateUtil.getDate(new Date());
		String sql = "select "
				+ "channel_data.date,app.mm_company,app.name,channel.name,channel.no,channel_data.req_num,channel_data.req_ok_num,"
				+ "channel_data.msm_num,channel_data.fee_num,channel_data.mm_pay"
				+ " from channel_data,app,channel where channel_data.channel = channel.no and channel_data.game_id = app.no "
				+ " and channel_data.date = '" + dateStr + "'";
		return getObjectList(sql);
	}
	
	public Map<String,Object> getDataQueryList(String gameNo,String channelNo,String businessNo,String apkNo,Date beginDate,Date endDate,int pageSize,int curPage){

		StringBuilder sql = new StringBuilder(
				"select DATE_FORMAT(date,'%Y-%m-%d'),"
						+"sum(activity_num),"
						+"sum(login_num),"
						+"truncate(sum(day1_num)/sum(day_all_acount)*100,2) ,"
						+"truncate(sum(day7_num)/sum(day_all_acount)*100,2) ,"
						+"sum(day_all_pay_acount),"
						+"sum(fee_num),"
						+"truncate(sum(mm_pay+tc_pay+union_pay+open_pay+web_pay+wo_pay)/100,2),"
						+"truncate(sum(day_all_pay_acount)/sum(login_num)*100,2),"
						+"truncate(sum(fee_num)/sum(req_ok_num)*100,2),"
						+"truncate(sum(mm_pay+tc_pay+union_pay+open_pay+web_pay+wo_pay)/sum(day_all_pay_acount)/100,2),"
						+"truncate(sum(mm_pay+tc_pay+union_pay+open_pay+web_pay+wo_pay)/sum(activity_num)/100,2),"
						+"truncate(sum(fee_num)/sum(day_all_pay_acount),2) from channel_data where 1 = 1 ");
		StringBuilder commonSql = new StringBuilder("");
		if(!StringUtil.is_nullString(gameNo)){
			commonSql.append(" and game_id = '" + gameNo + "'");
		}
		if(!StringUtil.is_nullString(channelNo)){
			sql.append(" and channel = '" + channelNo + "'");
		}
		if(!StringUtil.is_nullString(businessNo)){
			commonSql.append(" and business_no = '" + businessNo + "'");
		}
		if(!StringUtil.is_nullString(apkNo)){
			commonSql.append(" and packet_id = '" + apkNo + "'");
		}
		String str = String.format(" and date >= '" + DateUtil.getDate(beginDate) +"' and date <= '" + DateUtil.getDate(endDate) +"'  group by date");
		sql.append(commonSql).append(str);
		List<ArrayList> list = new ArrayList();
		List<ArrayList> ontimelist = new ArrayList();
		List<ArrayList> onlinelist = new ArrayList();
		list = getObjectList(sql.toString());
		
		
		
		 LiveDao dao = new LiveDao();
		 ArrayList<String> dateList = dao.get_DateList(DateUtil.getDate(beginDate), DateUtil.getDate(endDate));
		 for(int i = 0; i< dateList.size();i++){
			 String date = dateList.get(i);
			 String dateSql = " and date = '" + date + "' ";
			 StringBuilder onlineSql  = new StringBuilder(
						"select sum(onlinetime) from activate where 1 = 1 ");
			 if(!StringUtil.is_nullString(channelNo)){
					onlineSql.append(" and channel_no = '" + channelNo + "'");
				}
			 onlineSql.append(commonSql).append(dateSql);
			 onlinelist= getObject(onlineSql.toString());
			 ontimelist.add((ArrayList) onlinelist);
		 }
		
		
		List<ArrayList> newList = new ArrayList();
		for(int i = 0;i< ontimelist.size(); i++){
			ArrayList al = list.get(i);
			ArrayList timeal = ontimelist.get(i);
			Object onlinetime = null;
			if(timeal.get(0)==null||list.get(i).get(1).equals("0")){
				onlinetime = 0;
			}else{
			 onlinetime = Math.round(Integer.parseInt(String.valueOf(timeal.get(0)))/Integer.parseInt(String.valueOf(list.get(i).get(1))));
			}
			al.add(onlinetime);
			newList.add(al);
		}
		//ExcelUtil.exportExcel(null, null, newList);
		DebuUtil.log(sql.toString());//
		//DebuUtil.log(onlineSql.toString());//
		int totalCount = getRecordCount(sql.toString());
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", newList);
		map.put("totalCount", totalCount);
		map.put("totalPage",totalPage);
		return map;
	}

	public Map<String,Object> getChaQueryList(String gameNo,String businessNo,String apkNo,Date beginDate,int pageSize,int curPage){
		
		StringBuilder sql  = new StringBuilder("SELECT packet_id,"
			//	+ "sum(new_acount),"
				+ "SUM(mobile_new_acount),"
				+ "truncate(SUM(mobile_new_acount)/sum(new_acount)*100,2),"
				+ "truncate(sum(new_pay)/100,2),"
				+ "truncate(sum(new_pay)/sum(new_acount)/100,2),"
				+ "sum(login_acount),"
				+ "truncate(sum(mobile_pay)/100,2),"
				+ "truncate(sum(unicom_pay)/100,2),"
				+ "truncate(sum(telecom_pay)/100,2) from province_data where 1=1 ");
		if(!StringUtil.is_nullString(gameNo)){
			sql.append(" and game_id = '" + gameNo + "'");
		}
		if(!StringUtil.is_nullString(businessNo)){
			sql.append(" and business_no = '" + businessNo + "'");
		}
		if(!StringUtil.is_nullString(apkNo)){
			sql.append(" and packet_id = '" + apkNo + "'");
		}
		String str = String.format(" and date = '" + DateUtil.getDate(beginDate) +"' group by packet_id");
		sql.append(str);
		List<ArrayList> list = new ArrayList();
		List<ArrayList> coolist = new ArrayList();
		List<ArrayList> channlist = new ArrayList();
		List<ArrayList> onLinelist = new ArrayList();
		List<ArrayList> newList = new ArrayList();
		list = getObjectList(sql.toString());
		for(int i =0; i< list.size();i++){
			String packet_id  = (String) list.get(i).get(0);
			String cooSql = "select channel.name,businesser.name,settle_type from cooperation,channel,businesser where cooperation.packet_id =  '" + packet_id +"' "
					+ "and cooperation.channel_no = channel.no and cooperation.business_no = businesser.no ";
			
			coolist = getObject(cooSql);
			String chanSql = "select sum(activity_num),"
					+ "truncate(sum(day1_num)/sum(day_all_acount)*100,2),"
					+"sum(day_all_pay_acount),"
					+"sum(fee_num),"
					+"truncate(sum(mm_pay+tc_pay+union_pay+open_pay+web_pay+wo_pay)/100,2),"
					+"truncate(sum(day_all_pay_acount)/sum(login_num)*100,2),"
					+"truncate(sum(fee_num)/sum(req_ok_num)*100,2),"
					+"truncate(sum(mm_pay+tc_pay+union_pay+open_pay+web_pay+wo_pay)/sum(day_all_pay_acount)/100,2),"
					+"truncate(sum(mm_pay+tc_pay+union_pay+open_pay+web_pay+wo_pay)/sum(activity_num)/100,2),"
					+ "truncate(sum(web_pay)/100,2)"
					+" from channel_data where packet_id = '" + packet_id +"' and date = '" + DateUtil.getDate(beginDate) +"'";
			channlist = getObject(chanSql);
			String onLineSql = "select truncate(sum(onlinetime)/count(*),2) from activate where date = '" + DateUtil.getDate(beginDate) +"' and packet_id = '" + packet_id +"' ";
			onLinelist = getObject(onLineSql);
			ArrayList al = list.get(i);
			al.addAll(coolist);
			al.addAll(channlist);
			al.addAll(onLinelist);
			ArrayList newal = new ArrayList();
			DecimalFormat df=new DecimalFormat("0.00");
			newal.add(DateUtil.getDate(beginDate));
			newal.add(al.get(0));
			newal.add(al.get(9));
			newal.add(al.get(10));
			newal.add(al.get(11));
			newal.add(al.get(12));
			newal.add(al.get(1));
			newal.add(al.get(2));
			newal.add(al.get(3));
			newal.add(al.get(4));
			newal.add(al.get(5));
			newal.add(al.get(13));
			newal.add(al.get(14));
			newal.add(al.get(15));
			newal.add(al.get(16));
			newal.add(al.get(17));
			newal.add(al.get(18));
			if(al.size()>20){
			newal.add(al.get(19));
			newal.add(al.get(20));
			newal.add(al.get(22));
			}else{
			newal.add(0);
			newal.add(0);
			newal.add(0);
			}
			Double mobilePay = Double.parseDouble(al.get(6).toString()) + Double.parseDouble(al.get(21).toString());
			newal.add(df.format(mobilePay));
			newal.add(al.get(7));
			newal.add(al.get(8));
			newList.add(newal);
			
		}
		int totalCount = getRecordCount(sql.toString());
		
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", newList);
		map.put("totalCount", totalCount);
		map.put("totalPage",totalPage);
		return map;
		}
	

}
