package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constant;

import model.Activate;
import model.CardPay;
import model.Cooperation;
import model.Mzcharge;
import model.Pay;
import model.Register;
import sdkReq.GetAmountIndex;
import sdkReq.YeepayOrder;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import azul.CacheDao;

public class PayDao extends CacheDao{
	public PayDao() {
		init();
	}
	public  static void test2()
	{
		Pay obj = new Pay();
		
		
		obj.setPayNo("0523010428-1003");
		obj.setUsername("15914051609");
		obj.setDeviceId("352746059407794");
		obj.setPacketId("100152001");//101001
		obj.setGameId("152");//005
		obj.setServerId("2");
		obj.setCpOrderId("140523010447128100010741");
		obj.setPayType("alipay");
		obj.setThirPayId("2014052313077815");
		obj.setAmount(100);
		obj.setRatio(10);
		obj.setState(ConstValue.OK);
		obj.setCoinName("金币");
		obj.setState(1);
		obj.setTime("01:04:28");//DateUtil.getTime()
		obj.setType("");
		obj.setDate("2014-11-18");//DateUtil.getDateTime()
		
		PayDao paydao = new PayDao();
		paydao.pay(obj,true);
		PayDao.pay(obj,paydao,true);
		
		/*int bret = 0;
		String ret ="";// String.format("pay_no=%s&username=%s&device_id=%s&game_id=%s&server_id=%s&cp_order_id=%s&pay_type=%s&amount=%d&payStatus=%d"
		   //,pay_no,username,device_id,game_id,server_id,cp_order_id,pay_type,amount,0);
		String content = "";
		String sign="";
		String url = null;
		String key="zty_156";
		JSONObject json =new  JSONObject();
		 json.put("username",obj.username);
		 /////////////////////////////////
		 //if(register != null)
		 json.put("user_id",148860);
		 ///////////////////////////////
		 //json.put("user_id",134570);/////////////////////////
		 
		 json.put("device_id",obj.device_id);
		 json.put("server_id",obj.server_id);
		 json.put("packet_id",obj.packet_id);
		 json.put("game_id",obj.game_id);
		 json.put("cp_order_id",obj.cp_order_id);
		 json.put("pay_type",obj.pay_type);
		 json.put("pay_no",obj.pay_no);
		 int payamount = 10;
		 if(obj.pay_type.equals("jcard"))
		 {
			 payamount = obj.amount*80/100;
		 }
		 json.put("amount",payamount);
		 json.put("payStatus",0);
		 ret = json.toString();*/
		 
		 //////////////////////////////////////
		 //byte [] buf = username.getBytes("UTF-8");
		/// for(byte tem:buf)
		// {
			 
		 //}
		 ///////////////////////////////////////
		 /*JSONObject json2 =new  JSONObject();
		 json2.put("u","钻石");
		 ret = json2.toString();
		 String name = DebuUtil.printfbuf(ret.getBytes());
		 DebuUtil.log("name="+name);*/
		 ////////////////////////////////////////
    	/*sign=util.Rsa.getMD5(ret+"&key="+key);
    	DebuUtil.log(ret+"&key="+key);
    	//DebuUtil.log(sign);
		content = Base64.encode(ret.getBytes());
		DebuUtil.log("content="+content);
		//content="eyJwYXlfbm8iOiIwNTIzMDEwNDI4LTEwMDMiLCJ1c2VybmFtZSI6ItDsuqOIkCIsInVzZXJfaWQiOjE0ODg2MCwiZGV2aWNlX2lkIjoiODYzNzM4MDI5ODE2NTkzIiwic2VydmVyX2lkIjoiNDEwIiwicGFja2V0X2lkIjoiMjgwMTU4MDAyIiwiZ2FtZV9pZCI6IjE1OCIsImNwX29yZGVyX2lkIjoiMTQwNTIzMDEwNDQ3MTI4MTAwMDEwNzQxIiwicGF5X3R5cGUiOiJhbGlwYXkiLCJhbW91bnQiOjEwMDAsInBheVN0YXR1cyI6MH0%3D";
		//content = new String(Base64.decode(content));
		//DebuUtil.log("content="+content);
		
		
		ret = String.format("content=%s&sign=%s",content,sign);
		DebuUtil.log("ret="+ret);
		
		content="eyJ1Ijoi6ZK755+zIn0=";
		content = new String(Base64.decode(content));
		DebuUtil.log("content="+content);*/
		
		//update(obj);
		//pay(obj);
		//obj = this.getPayRecord("0311173255-1135");
		//obj.rsqCallbackUrl(0);
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	public  void test_temp()
	{
		   PayDao payDao = new PayDao(); 
		   ArrayList<Object> list2 = payDao.getAllList();
		   Pay pay = null;
		   //RegisterDao registerDao = new RegisterDao();
		   //Register register = null;
		   int reg = 0;
		   int act = 0;
		   int count = 0;
		   //DebuUtil.log("激活数:"+list2.size());
		   for(int i=0;i<list2.size();i++)
		   {
		        pay=(Pay)list2.get(i);
		        CooperationDao cooperationDao=new CooperationDao();
				Cooperation cooperation = cooperationDao.getRecord(pay.packet_id);
				if(cooperation != null)
				{
					pay.business_no = cooperation.getBusinessNo();
					pay.channel_no = cooperation.getChannelNo();
					payDao.edit(pay);
				}
		   
		        reg++;
		        DebuUtil.log("付费总数:"+list2.size()+"当前:"+reg);
		   }
		  
	}
	
	public ArrayList<Object> getPayList(String sql)
	{
		ArrayList<Object> objList;
		//String sql = "select * from pay where state=1 and notisfy=0";
		objList = getList(sql);
		return objList;
	}
	
	public ArrayList<Object> getAllList()
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s ", baseTableName);//where packet_id='217150001'
		objList = getList(sql);
		//DebuUtil.log(sql);
		
		return objList;
	}
	public List<ArrayList> getAppPayList(String beginDate, String endDate, String BSS_NO, String gameNo, String channelNo, String pay_id)
	{
		//ArrayList<Object> objList;
		String sql = String.format("select sum(amount),game_id,packet_id,channel_no,app_pay_id from %s where state=1 and date >= '"+beginDate+"' and date <= '"+endDate+"' ",baseTableName);
		if(!StringUtil.is_nullString(BSS_NO))
		{
			sql += " and pay.business_no='"+BSS_NO+"'";
		}
		if(!StringUtil.is_nullString(gameNo))
		{
			sql += " and pay.app_pay_id  in (select pay_id from mm_pay_data where app_pay_id = '" + gameNo + "')";
		}
		if(!StringUtil.is_nullString(channelNo))
		{
			sql += " and pay.channel_no='"+channelNo+"'";
		}
		if(!StringUtil.is_nullString(pay_id))
		{

			sql += " and pay.app_pay_id   = '" + pay_id + "'";
		}
		sql += " group by packet_id order by channel_no ";
		System.out.print(sql);
		List<ArrayList> list = getObjectList(sql);
		return list;
	}
	
	public List<ArrayList> getRecordByTask(){
		String dateStr = DateUtil.getDate(new Date());
		String sql = "select date,sum(amount),game_id,channel_no,packet_id from pay where state=1 and date = '" + dateStr + "' group by packet_id order by channel_no";
		return getObjectList(sql);
	}
	public ArrayList<Object> getAppListPayList(String beginDate, String endDate, String BSS_NO, String gameNo, String channelNo, String pay_id)
	{
		ArrayList<Object> objList;
		String sql = String.format("select * from pay where state=1 and date >= '"+beginDate+"' and date <= '"+endDate+"' ");
		if(!StringUtil.is_nullString(BSS_NO))
		{
			sql += " and pay.business_no='"+BSS_NO+"'";
		}
		if(!StringUtil.is_nullString(gameNo))
		{
			sql += " and pay.app_pay_id  in (select pay_id from mm_pay_data where app_pay_id = '" + gameNo + "')";
		}
		if(!StringUtil.is_nullString(channelNo))
		{
			sql += " and pay.channel_no='"+channelNo+"'";
		}
		if(!StringUtil.is_nullString(pay_id))
		{
			sql += " and pay.app_pay_id  = '" + pay_id + "'";
		}
		System.out.print(sql);
		objList = getList(sql);
		return objList;
	}
	
	public int getAllPayAccountNumBefore(String game_id,String business_no,
			String channel_no,String packet_id,String date)
	{
		int ret = 0;
		if(!StringUtil.is_nullString(packet_id))
		{
			ret = getAllPayAccountNumBefore( game_id, packet_id, date);
		}
		else
		{
			String sql;
			sql = String.format("select * from %s where date<='%s' and state=1 "
					,baseTableName,date);
			
			if(!StringUtil.is_nullString(game_id))
			{
			   sql+=" and game_id='"+game_id+"'";
			}
			
			if(!StringUtil.is_nullString(business_no))
			{
			   sql+=" and business_no='"+business_no+"'";
			}
			
			if(!StringUtil.is_nullString(channel_no))
			{
			   sql+=" and channel_no='"+channel_no+"'";
			}
			
			sql += " group by device_id,game_id";
			
			//ret = getRecordCount(sql);
			
			long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
			if(days >= 1)
			{
				DataBufDao dao = new DataBufDao();
				ret = dao.GetCount(sql,"getAllPayAccountNumBefore_bc",date,packet_id);
			}
			else
			{
			   ret = getRecordCount(sql);
			}
			DebuUtil.log("sql:"+sql);
			DebuUtil.log("ret:"+ret);
		}
		return ret;
	}
	public int getAllPayAccountNumBefore(String game_id,String packet_id,String date)
	{
		int ret = 0;
		String sql;
		
		sql = String.format("select * from %s where packet_id='%s' and date<='%s' and state=1 "
				,baseTableName,packet_id,date);
		
		/*if(!StringUtil.is_nullString(packet_id))
		{
		   sql+=" and packet_id='"+packet_id+"'";
		}*/
		
		sql += " group by device_id,game_id";
		
		//ret = getRecordCount(sql);
		
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"getAllPayAccountNumBefore",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		}
	
	
		DebuUtil.log(date+"时间付费注册总数="+ret);
		
		return ret;
	}
	//select * from pay where state=1 and pay_type !='mmpay'
	public int getNewpay(String game_id,String business_no,String channel_no,String packet_id,String date,String payType)
	{
		int ret;
		String sql="";
		String con = "";
		
		if(!StringUtil.is_nullString(packet_id))
		{
			con += " and pay.packet_id='"+packet_id+"'";
		}
		else
		{
			if(!StringUtil.is_nullString(game_id))
			{
				con += " and pay.game_id='"+game_id+"'";
			}
			
			if(!StringUtil.is_nullString(business_no))
			{
				con += " and pay.business_no='"+business_no+"'";
			}
			
			if(!StringUtil.is_nullString(channel_no))
			{
				con += " and pay.channel_no='"+channel_no+"'";
			}
		}
		
		sql = String.format("select sum(amount) from pay,register where 1=1 %s and pay.pay_type='%s' and pay.state=1 and pay.username=register.username and register.date='%s' "
				,con,payType,date);
			
		ret = getCount(sql);//
		
		DebuUtil.log("sql:"+sql);
		DebuUtil.log("ret:"+ret);
		
		return ret;
	}
	public int getNewpay(String game_id,String packet_id,String date,String payType)
	{
		int ret;
		String sql="";
				
		sql = String.format("select sum(amount) from pay,register where pay.packet_id='%s' and pay.pay_type='%s' and pay.state=1 and pay.username=register.username and register.date='%s' "
				,packet_id,payType,date);
			
		ret = getCount(sql);//
		
		return ret;
	}
	
	public int getAllpayByDate(String game_id,String business_no,String channel_no,String packet_id,String begindate,String enddate)
	{
		int ret = 0;
		
		
			String sql;
			sql = String.format("select sum(amount) from %s where date>='%s' and date<='%s'and state=1"
					,baseTableName,begindate,enddate);
			
			/*if(!StringUtil.is_nullString(packet_id))
			{
			   sql+=" and packet_id='"+packet_id+"'";
			}*/
			
			//ret = getCount(sql);
			
			
			if(!StringUtil.is_nullString(game_id))
			{
				sql += " and game_id='"+game_id+"'";
			}
			
			if(!StringUtil.is_nullString(business_no))
			{
				sql += " and business_no='"+business_no+"'";
			}
			
			if(!StringUtil.is_nullString(channel_no))
			{
				sql += " and channel_no='"+channel_no+"'";
			}
			
			if(!StringUtil.is_nullString(packet_id))
			{
				sql += " and packet_id='"+packet_id+"'";
			}
			
			ret = getCount(sql);
			
			DebuUtil.log("sql:"+sql);
			DebuUtil.log("ret:"+ret);
	
		
		
		//DebuUtil.log(sql);
		return ret;
	}
	
	public int getAllpayNumBefore(String game_id,String business_no,String channel_no,String packet_id,String date)
	{
		int ret = 0;
		
		if(!StringUtil.is_nullString(packet_id))
		{
			ret = getAllpayNumBefore(game_id, packet_id, date);
		}
		else
		{
			String sql;
			sql = String.format("select sum(amount) from %s where date<='%s' and state=1"
					,baseTableName,date);
			
			/*if(!StringUtil.is_nullString(packet_id))
			{
			   sql+=" and packet_id='"+packet_id+"'";
			}*/
			
			//ret = getCount(sql);
			
			
			if(!StringUtil.is_nullString(game_id))
			{
				sql += " and game_id='"+game_id+"'";
			}
			
			if(!StringUtil.is_nullString(business_no))
			{
				sql += " and business_no='"+business_no+"'";
			}
			
			if(!StringUtil.is_nullString(channel_no))
			{
				sql += " and channel_no='"+channel_no+"'";
			}
			
		
			long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
			if(days >= 1)
			{
				DataBufDao dao = new DataBufDao();
				ret = dao.GetCount2(sql,"getAllpayNumBefore_bc",date,packet_id);
			}
			else
			{
			   ret = getCount(sql);
			}
			DebuUtil.log("sql:"+sql);
			DebuUtil.log("ret:"+ret);
		}
		
		
		
		//DebuUtil.log(sql);
		return ret;
	}
	public int getAllpayNumBefore(String game_id,String packet_id,String date)
	{
		int ret;
		String sql;
		
		sql = String.format("select sum(amount) from %s where packet_id='%s' and date<='%s' and state=1"
				,baseTableName,packet_id,date);
		
		/*if(!StringUtil.is_nullString(packet_id))
		{
		   sql+=" and packet_id='"+packet_id+"'";
		}*/
		
		//ret = getCount(sql);
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount2(sql,"getAllpayNumBefore",date,packet_id);
		}
		else
		{
		   ret = getCount(sql);
		}
		
		return ret;
	}
	
	
	public int getComPayNum(String game_id,String business_no,String channel_no,String packet_id
			,String date,String payType)
	{
		String sql = "";
		int ret = 0;
		
		sql = String.format("select sum(amount) from %s where state=1"
				,baseTableName);
		
		if(!StringUtil.is_nullString(packet_id))
		{
			sql += " and packet_id='"+packet_id+"'";
		}
		else
		{
			if(!StringUtil.is_nullString(game_id))
			{
				sql += " and game_id='"+game_id+"'";
			}
			
			if(!StringUtil.is_nullString(business_no))
			{
				sql += " and business_no='"+business_no+"'";
			}
			
			if(!StringUtil.is_nullString(channel_no))
			{
				sql += " and channel_no='"+channel_no+"'";
			}
			
		}
		
		if(!StringUtil.is_nullString(date))
		{
			sql += " and date='"+date+"'";
		}
		
		if(!StringUtil.is_nullString(payType))
		{
			sql += " and pay_type='"+payType+"'";
		}
		
		
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount2(sql,"getComPayNum",date,packet_id);
		}
		else
		{
		   ret = getCount(sql);
		}
		
		return ret;
		
	}
	
	public int getpayNum(String game_id,String packet_id,String date,String payType,String channelType,String type)
	{
		int ratio = 0;
		String sql = "";
		sql = String.format("select settle_ratio from cooperation where app_no='%s' and packet_id='%s'"
				,game_id,packet_id);
		ratio = getCount(sql);
		return getpayNum2(game_id,packet_id,date,payType,channelType,type,ratio);
	}
	public int getpayNum2(String game_id,String packet_id,String date,String payType,String channelType,String type,int ratio)
	{
		int ret;
		String sql;
		
		if(date.equals("2014-03-21"))
		{
			sql="";
		}
		if((channelType.equals("CPS")) && (!StringUtil.is_nullString(packet_id)))
		{
			DebuUtil.log("payType="+payType);
			sql = String.format("select * from %s where packet_id='%s' and pay_type='%s' and date='%s' and state=1 and type<>'C' and type<>'A' limit 1"
					,baseTableName,packet_id,payType,date);
			ArrayList<Object> objList;
			objList = getList(sql);
	        if(objList.size() > 0)
	        {
	        	/*Pay obj;
	        	obj = (Pay)objList.get(0);
	        	String str = obj.type;
	        	
	        	if(str.equals("A") ||str.equals("C"))
	        	{
	        		
	        		DebuUtil.log("已经结算过了");
	        	}
	        	else*/
	        	{
	        		int settle_ratio = 0;
	        		int ANum = 0;
	        		int CNum = 0;
	        		
	        		DebuUtil.log("开始结算 ");
	        		//sql = String.format("select settle_ratio from cooperation where app_no='%s' and packet_id='%s'"
	        			//	,game_id,packet_id);
	        		//settle_ratio = getCount(sql);
	        		settle_ratio = ratio;
	        		DebuUtil.log("结算比例= "+settle_ratio);
	        		//sql = String.format("select * from %s where game_id='%s' and packet_id='%s' and date='%s'"
	        		//		,baseTableName,game_id,packet_id,date);
	        		
	        		sql = String.format("select * from %s where packet_id='%s' and pay_type='%s' and date='%s' and state=1 "
	    					,baseTableName,packet_id,payType,date);
	        		
	        		ret = getRecordCount(sql);
	        		DebuUtil.log("计费总数= "+ret);
	        		//DebuUtil.log("sql= "+sql);
	        			        		
	        		ANum = (int)((ret*settle_ratio)/100.0+0.5);
	        		CNum = ret - ANum;
	        		
	        		DebuUtil.log("CNum= "+CNum);
	        		DebuUtil.log("ANum= "+ANum);
	        			        		
	        		//long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
	        		sql = String.format("select * from %s where packet_id='%s' and pay_type='%s' and date='%s' and state=1 and type='C' "
	    					,baseTableName,packet_id,payType,date);
	        		int CNumO = getRecordCount(sql);
	        		int count = CNum-CNumO;
	        		DebuUtil.log("CNumO= "+CNumO);
	        		
	        		if((count > 0))
	        		{
	        			DebuUtil.log("count= "+count);
	        			sql = String.format("UPDATE %s SET type = 'C' WHERE packet_id='%s'  and pay_type='%s' and date='%s' and state=1 and type<>'C' and type<>'A' limit %d"
		        				,baseTableName,packet_id,payType,date,CNum);
	        			DebuUtil.log("执行C结算");
	        			//DebuUtil.log(sql);
	        			super.executeUpdate(sql);
	        		}
	        		
	        		
	        		sql = String.format("select * from %s where packet_id='%s' and pay_type='%s' and date='%s' and state=1 and type='A' "
	    					,baseTableName,packet_id,payType,date);
	        		int ANumO = getRecordCount(sql);
	        		 count = ANum-ANumO;
	        		DebuUtil.log("ANumO= "+ANumO);
	        		if((count > 0))
	        		{
	        			DebuUtil.log("count= "+count);
	        			sql = String.format("UPDATE %s SET type = 'A' WHERE packet_id='%s' and pay_type='%s' and date='%s' and state=1 and type<>'C' and type<>'A' limit %d"
		        				,baseTableName,packet_id,payType,date,ANum);
	        			DebuUtil.log("执行A结算");
	        			//DebuUtil.log(sql);
	        			super.executeUpdate(sql);
	        		}
	        		
	        		/*if(days == 0)
	        		{
	        			//DebuUtil.log("当天不结算");
	        			sql = String.format("select sum(amount) from %s where game_id='%s' and packet_id='%s' and pay_type='%s' and date='%s' and state=1"
	        					,baseTableName,game_id,packet_id,payType,date);
	        			ret = getCount(sql);
	        			CNum = (ret*(100-settle_ratio))/100;
		        		ANum = ret - CNum;
		        		if(payType.equals("yeepay"))
		        		{
		        			ANum = ((ANum/1000)*1000);
		        		}
		        		else
		        		{
		        			ANum = ((ANum/100)*100);
		        		}
	        			if(type.equals("A"))
	    	        	{
	    	        		return ANum;
	    	        	}
	        			else if(type.equals("C"))
	    	        	{
	    	        		
	    	        		return CNum;
	    	        	}
	        		}*/
	        	}
	        }
		}
		
		sql = String.format("select sum(amount) from %s where packet_id='%s' and pay_type='%s' and date='%s' and state=1"
				,baseTableName,packet_id,payType,date);
		
		/*if(!StringUtil.is_nullString(packet_id))
		{
		   sql+=" and packet_id='"+packet_id+"'";
		}*/
		
		if(channelType.equals("CPS"))
		{
			if(type.equals("A"))
			{
				sql += " and type='A'";
			}
			else if(type.equals("C"))
			{
				sql += " and type='C'";
			}
		}
		
		//ret = getCount(sql);
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount2(sql,"getpayNum",date,packet_id);
		}
		else
		{
		   ret = getCount(sql);
		}
		
		//DebuUtil.log(sql);
		DebuUtil.log(date+payType+"付费总数="+ret);
		return ret;
	}
	
	public Pay getPayRecord(String order_id,String type)
	{
		Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where thir_pay_id='%s' and pay_type='%s'", baseTableName,order_id,type);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			pay = (Pay)objList.get(0);
		}
		return pay;
	}
	
	public Pay getPayRecordByUserName(String userName)
	{
		Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where username='%s' and state=1", baseTableName,userName);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			pay = (Pay)objList.get(0);
		}
		return pay;
	}
	
	public Pay getPayRecord(String pay_no)
	{
		Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where pay_no='%s'", baseTableName,pay_no);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			pay = (Pay)objList.get(0);
		}
		return pay;
	}
	
	public Pay getPayRecordByCpOrder(String CpOrder)
	{
		Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where cp_order_id='%s'", baseTableName,CpOrder);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			pay = (Pay)objList.get(0);
		}
		return pay;
	}
	
	
	public Pay getPayRecordByDev(String imei)
	{
		Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where device_id='%s'", baseTableName,imei);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			pay = (Pay)objList.get(0);
		}
		return pay;
	}
	
	public boolean yeepayNotisfy(Pay pay ,int state)
	{
		boolean ret = false;
		if((state == 1) && (pay.order_amount > 0))
		{
			DebuUtil.log("充值成功");
			String pay_type = pay.pay_type;
			if(pay_type.equals(Pay.YeePayType))
			{
				DebuUtil.log("易宝充值");
				CardPayDao  cardPayDao = new CardPayDao();
			   	CardPay cardpay = cardPayDao.getPayRecordByPayNO(pay.pay_no);
			   	int yeeAmount = YeepayOrder.getYeediscount(cardpay.pay_type, pay.game_id, cardpay.amount);
			   	if(pay.amount > yeeAmount)
			   	{
			   		pay.amount = yeeAmount;
			   	}
			   //	pay.amount = cardpay.amount;
				if(pay.amount != pay.order_amount)//订单金额跟付款金额不一致需要处理
				{
					DebuUtil.log("金额不相等");
					Mzcharge mzcharge = new Mzcharge(pay);
					MzchargeDao mzchargeDao = new MzchargeDao();
					int addRet = mzchargeDao.addCharge(mzcharge);
					if(addRet == ConstValue.OK)
					{
						DebuUtil.log("添加拇指币充值成功");
						//转成拇指币
					    mzchargeDao.chargeNotify(mzcharge.getPayNo(), mzcharge.getThirPayId(), 1);
					    ret = true;					  				    
					    
					}
				}
			}
		}
		return ret;
	}
	
	public void payNotisfy(String pay_no,String thir_pay_id ,int state)
	{
		Pay pay = getPayRecord(pay_no);
		
		//dealMz(pay_no,pay);
		
		if(pay == null)
		{
			DebuUtil.log("pay记录找不到");
			return;
		}
		if(pay.state == state)
		{
			DebuUtil.log("状态一样state="+state);
			return;
		}
		//易宝余额处理，先转成拇指币下发商品
		if(yeepayNotisfy(pay,state))
		{
			if(pay.amount > pay.order_amount)//用拇指币支付
		    {
		    	RegisterDao registerDao = new RegisterDao(); 
		    	if(registerDao.pay(pay.getUsername(), pay.getOrderAmount()/100))
		    	{
		    		DebuUtil.log("拇指币支付成功");
		    		pay.pay_type = Pay.MzPayType;
		    		pay.thir_pay_id = Pay.getOutTradeNoINC();
		    		pay.amount = pay.order_amount;
		    		pay.state = state;					
		    	}
		    }
		}
		else
		{
	    	pay.thir_pay_id = thir_pay_id;
			pay.state = state;
			
		}
		
		edit(pay);
		payNotisfy(pay,true);
	    return ;
	    
	}   
	/**
	 * 根据订单号判断是否需要补发拇指币
	 * @param pay_no
	 * @param pay 
	 */
	private void dealMz(String pay_no, Pay pay) {
		CardPayDao cardPayDao = new CardPayDao();
		CardPay cardPayObj = cardPayDao.getPayRecordByPayNO(pay_no);
		if (cardPayObj!=null) {
			String payway = cardPayObj.pay_type;
			int cardPayAmount = cardPayObj.amount;
			int payAmout = pay.amount;
			int temp=0;
			if (payway.equals("china_mobile")
				|| payway.equals("china_telecom")
				|| payway.equals("china_unicom")) {
				temp = cardPayAmount-payAmout/100;
				
			}else if(payway.equals("jcard")
					||payway.equals("zycard")
					||payway.equals("tscard")){
				temp = cardPayAmount*80/100-payAmout/100;
				
			}
			if(temp>0){
				//addMz(pay,temp);
			}
		}
		
		
	}
	public void payNotisfy(Pay pay)
	{
		payNotisfy(pay,true);
	}
	/**
	 * 统计channeldata表的各项数据，并根据充值的情况判断是否通知cp，当充值为购买专属币时不通知cp
	 * @param pay
	 * @param bSDK
	 */
	public void payNotisfy(Pay pay,boolean bSDK)
	{		
		
    	//pay.thir_pay_id = thir_pay_id;
		//pay.state = state;
	    int paystate = 0;
	    //pay = update(pay);  
	    
	    if(pay.state == 1)
	    {	        
	    	pay(pay,this,bSDK);			      	     
	        
			if(pay.rsqCallbackUrl(paystate) == 1)
		    {
		       update(pay);
		    }
				
	    }
	    else
	    {
	    	
	    	if(AppDao.isStandAloneGame(pay.game_id))
	    	{
		    	if(pay.rsqSACallbackUrl() == 1)
			    {
			       update(pay);
			    }
	    	}
	    }
	    return ;
	}
	
	
	public Pay update(Pay obj)
	{
	    PayDao paydao = new PayDao();
	    Pay pay = paydao.getPayRecord(obj.pay_no);
	    if(pay != null)
	    {
	    	DebuUtil.log("找到修改记录 ");
	    	//pay.packet_id = obj.channel;
	    	pay.thir_pay_id = obj.thir_pay_id;
	    	pay.state = obj.state;
	    	if(pay.notisfy != 1)//lsl
	    	{
	    	    pay.notisfy = obj.notisfy;
	    	}
	    	pay.ncount = obj.ncount;
	    	edit(pay);
	    }
	    return pay;
	}   
	
	
	public  void reviseAddr()
	{
		Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where date='2015-05-06' and state=1 ", baseTableName);
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			pay = (Pay)objList.get(i);
			if(StringUtil.is_nullString(pay.ext))
			{
				ActivateDao activateDao = new ActivateDao();
				Activate activate = activateDao.getActivateRecord(pay.device_id, pay.game_id) ;
				if(activate != null)
				{
					pay.ext = activate.addr;
					this.edit(pay);
				}
			}
			String code = GetAmountIndex.getRegionCode(pay.ext, "");
			pay.ext = GetAmountIndex.getRegionCode("", code);
			ProvinceDataDao provinceDataDao = new ProvinceDataDao();
			provinceDataDao.pay(pay,true);
		}
	}
	
	public  void revisenewpay()
	{
		Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where date>='2015-05-06' and state=1 ", baseTableName);//and packet_id='62376_0000' and ext = '广东' 
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			pay = (Pay)objList.get(i);
			
			ProvinceDataDao provinceDataDao = new ProvinceDataDao();
			provinceDataDao.reviseNewPay(pay,true);
		}
	}
	public  void revisepay()
	{
		Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where date>='2015-05-06' and state=1 ", baseTableName);//and packet_id='62376_0000' and ext = '广东' 
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			pay = (Pay)objList.get(i);
			
			ProvinceDataDao provinceDataDao = new ProvinceDataDao();
			provinceDataDao.pay(pay,true);
		}
	}
	
	/*public  void changepay()
	{
		Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where (packet_id='741269001' or  packet_id='741270001' or  packet_id='741265001' or  packet_id='741279001') and LENGTH(username)<8 and state=1  ", baseTableName);//and packet_id='62376_0000' and ext = '广东' 
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			pay = (Pay)objList.get(i);
			
			RegisterDao registerDao = new RegisterDao();
			sql = String.format("select * from register where packet_id='%s' and date='%s' order by id desc limit %d  ",pay.packet_id,pay.date,objList.size());//where packet_id='217150001'
			ArrayList<Object> registerList = registerDao.getList(sql);
			Map<String,String> map=new HashMap<String,String>();   
			
		    if(registerList.size() > 0)
		    {
			Register register = (Register)registerList.get(0);
			DebuUtil.log("changeuser packet_id="+pay.packet_id+" date="+pay.date +"username="+pay.username);
			DebuUtil.log("changeuser register.username="+register.username +" register.device_id="+register.device_id);
			//map.put(pay.username, pay.username);
			//pay.packet_id2 = pay.username;
			pay.username = register.username;
			pay.device_id = register.device_id;
			edit(pay);
		    }
			
	    }
	}*/
	public  void changepay()
	{
		Pay pay = null;
		ArrayList<Object> objList;
		
		//ArrayList<ArrayList> imeilist = getObjectList("SELECT device_id FROM pay WHERE packet_id='741270001' and date>='2015-07-01' and date<'2015-08-01' and state=1 GROUP BY  device_id HAVING count(device_id)>1");
		String imeiSTr = "SELECT device_id FROM pay WHERE (packet_id='741269001' or  packet_id='741270001' or  packet_id='741265001' or  packet_id='741279001')  and date>='2015-06-15' and date<'2015-09-01' and state=1 GROUP BY  device_id HAVING count(device_id)>1";
		List<Pay> imeilist =getTemplateList(Pay.class,0, 2000, imeiSTr);
		for(Pay imeiPay:imeilist)
		{
			String sql;
			//sql = String.format("select * from %s where (packet_id='741269001' or  packet_id='741270001' or  packet_id='741265001' or  packet_id='741279001') and LENGTH(username)<8 and state=1  ", baseTableName);//and packet_id='62376_0000' and ext = '广东' 
			sql = "SELECT * from pay  WHERE (packet_id='741269001' or  packet_id='741270001' or  packet_id='741265001' or  packet_id='741279001') and device_id='"+imeiPay.device_id+"'";//and packet_id='62376_0000' and ext = '广东' 
			DebuUtil.log("pay.device_id="+imeiPay.device_id);
			
			objList = getList(sql);
			for(int i= 0; i < objList.size(); i++)
			{
				pay = (Pay)objList.get(i);
				
				RegisterDao registerDao = new RegisterDao();
				sql = String.format("select * from register where packet_id='%s' and date='%s' order by id desc limit %d  ",pay.packet_id,pay.date,objList.size());//where packet_id='217150001'
				ArrayList<Object> registerList = registerDao.getList(sql);
				Map<String,String> map=new HashMap<String,String>();   
				DebuUtil.log("sql="+sql);
			    if(registerList.size() > 0)
			    {
					Register register = (Register)registerList.get(i);
					DebuUtil.log("changeuser packet_id="+pay.packet_id+" date="+pay.date +"username="+pay.username);
					DebuUtil.log("changeuser register.username="+register.username +" register.device_id="+register.device_id);
					//map.put(pay.username, pay.username);
					//pay.packet_id2 = pay.username;
					pay.username = register.username;
					pay.device_id = register.device_id;
					edit(pay);
			    }
				
		    }
		}
	}
	
	public static int pay(Pay obj,PayDao payDao,boolean bSDK)
	{
		int ret = ConstValue.Fail;
		if(obj.state == 1)
		{
			if(AppDao.isStandAloneGame(obj.game_id))
			{
				 //正向按省份统计
	 			ProvinceDataDao provinceDataDao = new ProvinceDataDao();
			    provinceDataDao.pay(obj,true);
	    		
		        //正向计费点统计
				FeeDataDao feeDataDao = new FeeDataDao();
			    feeDataDao.pay(obj,true);

			    AppPayDataDao appPayDataDao = new AppPayDataDao();
			    appPayDataDao.pay(obj,true);

			}
			ChannelDataDao channelDataDao = new ChannelDataDao();
			channelDataDao.pay(obj,bSDK);
			
			
		}
		return ret;
	}
	 
	 public Pay getOldPay(String user,String date)
	 {
		
		 Pay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where username='%s' and date='%s' ORDER BY time ", baseTableName,user,date);//and packet_id='62376_0000' and ext = '广东' 
		objList = getList(sql);
		
		if(objList.size() > 0)
		{
			pay = (Pay)objList.get(0);			
		}
		 return pay;
	 }
	//判断返现包
	 public static boolean isFanXianPacket(String channel)
	 {
		 boolean ret = false;
		 if (channel==null) {
			 return ret;
		}
		 if(channel.equals("800")||channel.equals("600")||channel.equals("700"))
		 {
			 ret = true;
		 }
		 return ret;
	 }
	 public void getPayPacketID(Pay obj)
	 {
		//付费跟导入渠道走
	   RegisterDao regDao = new RegisterDao();
	   Register reg = null;
	   
	   if(!StringUtil.is_nullString(obj.getUsername()))
	   {
		   reg = regDao.getRegisterRecord(obj.getUsername());
	   }
	   if(reg != null)
	   {
		   ActivateDao activateDao = new ActivateDao();
		   Activate activate = activateDao.getActivateRecord(reg.getDeviceId(), obj.getGameId());//lsl,reg.getGameId()
		   String PacketId = reg.packet_id;
		   String PacketId2 = obj.getPacketId();
		   String channel = obj.packet_id.substring(0, 3);
		   if(activate == null)//lsl
		   {				  
			   activate = activateDao.getActivateRecord(obj.getDeviceId(), obj.getGameId());
		   }
		   
		   if(activate != null)
		   {
			   PacketId = activate.getPacketId();
		   }
		   
		   if(channel.equals("800")||channel.equals("600")||channel.equals("700"))//
		   {
			   Pay pay = getOldPay(obj.username, obj.date);
			   if(pay != null)
			   {
				   obj.device_id = pay.device_id;
			       obj.ip = pay.ip;
			   }
			   PacketId = PacketId2;
		   }
		   //////////////////////////////////////////////////
		   CooperationDao cooperationDao=new CooperationDao();
		   Cooperation cooperation = cooperationDao.getRecord(PacketId);
		   if(cooperation == null)
		   {
			   
			   PacketId = "100"+obj.getGameId()+"001";
			   cooperation = cooperationDao.getRecord(PacketId);
		   }
		   else
		   {
			   String game_id = cooperation.getAppNo();
			   if(!game_id.equals(obj.getGameId()))
			   {
				   PacketId = "100"+obj.getGameId()+"001";
				   cooperation = cooperationDao.getRecord(PacketId);
			   }
		   }
		   if(cooperation != null)
		   {
				String business_no = cooperation.getBusinessNo();
				String channel_no = cooperation.getChannelNo();
				String game_id = cooperation.getAppNo();
				if(game_id.equals(obj.getGameId()))
				{
					obj.setBusinessNo(business_no);
					obj.setChannelNo(channel_no);
				}
				else
				{
					PacketId = "100"+obj.getGameId()+"001";
				}
		   }
		   
			////////////////////////////////////////
			
		   DebuUtil.log("修改导入渠道:"+PacketId2+"->"+PacketId);
		   obj.setPacketId2(PacketId2);
		   obj.setPacketId(PacketId);
		   
	   }
		   
	 }
	 /**
	  * 将支付数据添加到pay表中
	  * @param obj
	  * @return
	  */
	 public int pay(Pay obj)
	 {
		 return pay(obj,true);
	 }
	 
	public int pay(Pay obj,boolean bSDK)
	{
		int ret = ConstValue.Fail;
		String sql;
		
		int count = 0;
		
		
		if(!StringUtil.is_nullString(obj.getCpOrderId()))//if(obj.getCpOrderId())//13.64－10.8=2.8 3/15=20%
		{
			sql = String.format("select * from %s where cp_order_id='%s' and state=1 and game_id<>'173' ", baseTableName,obj.getCpOrderId());
			count = getRecordCount(sql);
		}
	
		if(bSDK)//if(ConstValue.PAY_SERVER != 1)
		{
			if(count < 1)			
			{
				sql = String.format("select * from %s where pay_no='%s'", baseTableName,obj.getPayNo());
				count = getRecordCount(sql);
			}
			else
			{
				DebuUtil.log3("cp_order_id已经存在"+obj.getCpOrderId());
			}
			
			if(count>0)
			{
				DebuUtil.log3("pay_no已经存在"+obj.getPayNo());
			}
		}
		
		//DebuUtil.log("付费表 结果="+count);//
		if(count < 1)
		{
		   //付费跟导入渠道走
			getPayPacketID(obj);
			if(obj.pay_type.equals("onlypay")){
				obj.packet_id = "100"+obj.game_id+"001";
			}
		   /*RegisterDao regDao = new RegisterDao();
		   Register reg = regDao.getRegisterRecord(obj.getUsername());
		   if(reg != null)
		   {
			   ActivateDao activateDao = new ActivateDao();
			   Activate activate = activateDao.getActivateRecord(reg.getDeviceId(), obj.getGameId());//lsl,reg.getGameId()
			   String PacketId = reg.packet_id;
			   String PacketId2 = obj.getPacketId();
			   String channel = obj.packet_id.substring(0, 3);
			   if(activate == null)//lsl
			   {				  
				   activate = activateDao.getActivateRecord(obj.getDeviceId(), obj.getGameId());
			   }
			   
			   if(activate != null)
			   {
				   PacketId = activate.getPacketId();
			   }
			   
			   if(channel.equals("800"))//
			   {
				   PacketId = PacketId2;
			   }
			   //////////////////////////////////////////////////
			   CooperationDao cooperationDao=new CooperationDao();
			   Cooperation cooperation = cooperationDao.getRecord(PacketId);
			   if(cooperation != null)
			   {
					String business_no = cooperation.getBusinessNo();
					String channel_no = cooperation.getChannelNo();
					obj.setBusinessNo(business_no);
					obj.setChannelNo(channel_no);
			   }
				////////////////////////////////////////
				
			   DebuUtil.log("修改导入渠道:"+PacketId2+"->"+PacketId);
			   obj.setPacketId2(PacketId2);
			   obj.setPacketId(PacketId);
			   
		   }*/
		   
			//DebuUtil.log3("pay_no已经存在"+obj.getPayNo());
		   add(obj);
		   
		  // if(ConstValue.OPTIMIZE == 1)
		   //{
			 //  pay(obj,this);
		  // }
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		  
		}
		else
		{
			
		}
		return ret;
	}	
	
	public Map<String,Object> getPayInfoList(String gameNo,String channelNo,String businessNo,String payType,Date beginDate,Date endDate,int pageSize,int curPage){
		
		StringBuilder sql = new StringBuilder("SELECT * from pay where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql.append(" AND business_no = '" + businessNo +"'");
		if(!StringUtil.is_nullString(payType))
			sql.append(" AND state = '" + payType +"'");
		sql.append(" AND date BETWEEN '" + DateUtil.getDate(beginDate) +"' AND '" + DateUtil.getDate(endDate) + "'");
		//按时间倒叙
		sql.append(" ORDER BY CONCAT(pay.date,pay.time) DESC");
		
		List<Pay> list = getList(curPage,pageSize,sql.toString());
		
		int totalCount = getRecordCount(sql.toString());
		
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("totalPage",totalPage);
		return map;
	}
	
	public Map<String,Object> getPayInfoList(String gameNo,String channelNo,String businessNo,String payType,Date beginDate,int pageSize,int curPage){
		StringBuilder sql = new StringBuilder("SELECT * from pay where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql.append(" AND business_no = '" + businessNo +"'");
		if(!StringUtil.is_nullString(payType))
			sql.append(" AND state = '" + payType +"'");
		sql.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql.append(" ORDER BY CONCAT(pay.date,pay.time) DESC");
		
		System.out.println(sql.toString());
		
		List<Pay> list = getList(curPage,pageSize,sql.toString());
		
		int totalCount = getRecordCount(sql.toString());
		
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("totalPage",totalPage);
		
		return map;
	}
	public List<ArrayList> getAppPayMoney(String gameId, String payId, String company ,Date beginTime ,Date endTime)
	{
		int ret;
		StringBuilder sql= new StringBuilder("select DATE_FORMAT(date,'%Y-%m-%d'),sum(amount) from pay where 1=1");
		sql.append(" and state=1 and date >= '" + DateUtil.getDate(beginTime)+ "' and date < '"+DateUtil.getDate(endTime) +"'");
		if(!StringUtil.is_nullString(payId)){
			sql.append(" AND app_pay_id = '" + payId + "'");
			}
		if(!StringUtil.is_nullString(gameId)){
			
			sql.append(" AND  app_pay_id in (select pay_id from mm_pay_data where app_pay_id = '" + gameId + "') ");
			}
		
		if(!StringUtil.is_nullString(company)){
			sql.append(" AND game_id in (select no from app where mm_company = '" + company + "')");
			}	
		
		sql.append(" group by date ");
	/*	sql = String.format("select sum(amount) from pay where  app_pay_id ='%s'  and state=1 and date >= '" + DateUtil.getDate(beginTime)+ "' and date < '"+DateUtil.getDate(endTime) +"'"
				,payId);*/
			
		List<ArrayList> list = getObjectList(sql.toString());;//
		
		return list;
	}
	public int getPayIdMoney(String ext, String payId,String date)//计算该计费点分省的金额
	{
		int ret =0;
		StringBuilder sql= new StringBuilder("select sum(amount) from pay where 1=1 ");
		sql.append(" and state=1 and date = '"+ date +"' and app_pay_id = '"+ payId+"' and ext like '"+ext+"%'  ");

		ret = getCount(sql.toString());
		
		return ret;
	}
	public int getPayMoney(String payId)//计算该计费点总金额
	{
		int ret =0;
		StringBuilder sql= new StringBuilder("select sum(amount) from pay where 1=1 ");
		sql.append(" and state=1 and  app_pay_id = '"+ payId+"' ");
		
		ret = getCount(sql.toString());
		
		return ret;
	}
	public int getUserPayMoney(String payId ,String device_id)//计算该计费点单用户金额
	{
		int ret =0;
		StringBuilder sql= new StringBuilder("select sum(amount) from pay where 1=1 ");
		sql.append(" and state=1 and  app_pay_id = '"+ payId+"' and device_id = '"+ device_id+"' ");
		
		ret = getCount(sql.toString());
		
		return ret;
	}
	public int getAppPayMoney(String appPayId,String device_id,String ext)//计算该计费游戏金额
	{
		int ret =0;
		StringBuilder sql= new StringBuilder("select sum(amount) from pay,mm_pay_data where 1=1  ");
		if(!StringUtil.is_nullString(device_id)){
			sql.append(" and pay.device_id = '"+ device_id+"'  ");
		}
		sql.append(" and pay.state=1  ");
		if(!StringUtil.is_nullString(ext)){
			sql.append(" AND pay.ext like '"+ext+"%' ");
		}
		if(!StringUtil.is_nullString(appPayId)){
			sql.append(" AND pay.app_pay_id = mm_pay_data.pay_id and  pay.app_pay_id in (select pay_id from mm_pay_data where app_pay_id = '" + appPayId + "') ");
		}
		ret = getCount(sql.toString());
		
		return ret;
	}
}

