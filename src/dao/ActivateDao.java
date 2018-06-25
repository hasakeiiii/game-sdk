package dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Activate;
import model.Cooperation;
import model.Register;
import sdkReq.GetAmountIndex;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import azul.BaseDao;

public class ActivateDao extends BaseDao{
	public ActivateDao() {
		init();
	}
	
	public void test_temp_54()
	{
		
		
		
		  ActivateDao activateDao = new ActivateDao(); 
		   ArrayList<Object> list2 = activateDao.getActivateList();
		   Activate activate = null;
		   RegisterDao registerDao = new RegisterDao();
		   Register register = null;
		   int reg = 0;
		   int act = 0;
		   int count = 0;
		   DebuUtil.log("激活数:"+list2.size());
		   for(int i=0;i<list2.size();i++)
		   {
		        activate=(Activate)list2.get(i);
		        
		        register = registerDao.getRegisterRecordByDevGame(activate.device_id,activate.game_id);
		        if(register != null)
		        {
		        	//if(register.username.length() >= 30)
		        	{
		        	DebuUtil.log("username"+"="+register.username);
		        	DebuUtil.log("usernameid"+"="+register.id);
		        	register.setValid(1);
		        	activate.setReg(1);
		        	registerDao.edit(register);
		        	activateDao.edit(activate);
		        	reg++;
		        	count ++;
		        	}
		        }
		        DebuUtil.log("总数="+list2.size()+"当前="+count);
		        //DebuUtil.log(activate.device_id+"个数:"+reg);
		        
		        reg++;
		   }
		   DebuUtil.log("count"+"="+count);
		   DebuUtil.log("激活数:"+list2.size());
		   DebuUtil.log("激活注册数:"+reg);
	}
	public void test()
	{
		Activate obj = new Activate();
		
		obj.setDeviceId("352746059407794");
		obj.setPacketId("100152001");
		obj.setGameId("152");
		
		obj.setDate("2014-11-18");//DateUtil.getDateTime()
		obj.setTime("12:10:10");
		obj.loginDate = null;
		//obj.time="18:41:47";
		//obj.type = "";
		sdkReq.Activate.activate(obj);
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
	
	
	
	public  void test_temp()
	{
		  ActivateDao activateDao = new ActivateDao(); 
		   ArrayList<Object> list2 = activateDao.getAllList();
		   Activate activate = null;
		   int reg = 0;
		   int act = 0;
		   int count = 0;
		  
		   for(int i=0;i<list2.size();i++)
		   {
		        activate=(Activate)list2.get(i);
		        
		        CooperationDao cooperationDao=new CooperationDao();
				Cooperation cooperation = cooperationDao.getRecord(activate.packet_id);
				if(cooperation != null)
				{
					activate.business_no = cooperation.getBusinessNo();
					activate.channel_no = cooperation.getChannelNo();
					activateDao.edit(activate);
				}
		        //DebuUtil.log(activate.device_id+"个数:"+reg);
				DebuUtil.log("激活数总数:"+list2.size()+"当前:"+reg);
		        reg++;
		   }
		  
		   
		  
	}
	
	public static int RealUser(Activate obj) {
		int ret = ConstValue.Fail;

		ChannelDataDao channelDataDao = new ChannelDataDao();
		channelDataDao.RealUser(obj);
		return ret;
	}
	
	public int getRegAccountNumByLevel(String game_id,String packet_id,String date,int from,int to)
	{
		int ret = 0;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' "
				,baseTableName,packet_id);
		
		sql+=" and date='"+date+"' ";//group by device_id,game_id
		sql+=" and level>="+from;
		if(to > 0)
		sql+=" and level<="+to;
		//ret = getRecordCount(sql);//getCount
		
		ret = getRecordCount(sql);
		
		DebuUtil.log(date+"级别数="+ret);
		//DebuUtil.log(sql);
		return ret;
	}
	
	public Activate chageOnlinetime(String imei,String game_id,String user,int time,int level)
	{
		//int ret = ConstValue.Fail;
		//DebuUtil.log("修改密码");
		
		Activate activate = getActivateRecord(imei,game_id);
		if(activate != null)
		{
			Boolean bNewUser = true;
			if(StringUtil.is_nullString(activate.loginDate))
			{
				activate.loginDate = null;
			}
			if(activate.onlinetime == null)
			{
				activate.onlinetime = 0;
			}
			if(activate.level == null)
			{
				activate.level = 0;
			}
			
			if(activate.is_real_user())
			{
				bNewUser = false;
			}
			
			/*if(activate.onlinetime == 0)
			{
				RegisterDao regdao = new RegisterDao();
				Register register = regdao.getRegisterRecord(user);
				if(register != null)
				{
					if(ConstValue.DEBUG == 1)
					{
						DebuUtil.log("用账号时长初始化");
					}
					if(register.onlinetime > activate.onlinetime)
					{
						activate.onlinetime = activate.onlinetime;
					}
					if(register.level > activate.level)
					{
						activate.level = activate.level;
					}
				}
			}*/
			
			activate.onlinetime += time;	
			if(level > activate.level)
			{
			   activate.level = level;
			}
			if(!activate.is_real_user())
			{
				bNewUser = false;
			}
			if(ConstValue.DEBUG == 1)
			{
				DebuUtil.log("修改在线时长和等级 onlinetime="+activate.onlinetime);
			}
			edit(activate);
			
			if(bNewUser)
			{
				if(activate.packet_id.equals("211270001"))
				DebuUtil.log2("修改在线时长和等级 设备="+activate.device_id+"游戏="+activate.game_id);
				RealUser(activate);				
			}
		}
		
		return activate;
		
	}
	
	
	public ArrayList<String> get_DateList(String game_id,String packet_id,String begindate,String enddate)
	{
	     ArrayList<String> ret;
	     
	     String sql;
			sql = String.format("select date from %s where game_id='%s' and packet_id='%s' group by date"
					,baseTableName,game_id,packet_id);
	     ret =getValueList(sql);
	     //DebuUtil.log("sql="+sql);
	     return ret;
	}
	
	public ArrayList<Object> getActivateList()
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s ", baseTableName);//where packet_id='217150001'
		objList = getList(sql);
		//DebuUtil.log(sql);
		
		return objList;
	}
	
	public int get_actNum(String game_id,String business_no,String channel_no,
			String packet_id,String date,String channelType,String type,int bAction)
	{
		int ret = 0;
		if(!StringUtil.is_nullString(packet_id))
		{
			ret = get_actNum(game_id,packet_id,date,channelType,type,bAction);
		}
		else
		{
			String sql;
			sql = String.format("select * from %s where date='%s'"
					,baseTableName,date);
			
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
			
		
			/*if(!StringUtil.is_nullString(packet_id))
			{
			   sql+=" and packet_id='"+packet_id+"'";
			}*/
			
			if(channelType.equals("CPA"))
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
			//sql+="and date like '"+date+"%'";
			//ret = getRecordCount(sql);
			long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
			if(days >= 1)
			{
				DataBufDao dao = new DataBufDao();
				ret = dao.GetCount(sql,"get_actNum_bc",date,packet_id);
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
	
	public int get_actNum(String game_id,String packet_id,String date,String channelType,String type,int bAction)
	{
		int ratio;
		String sql = "";
		sql = String.format("select settle_ratio from cooperation where app_no='%s' and packet_id='%s'"
				,game_id,packet_id);
		ratio = getCount(sql);
		return get_actNum2(game_id,packet_id,date,channelType,type,bAction,ratio);
	}
	
	
	public int get_actNum2(String game_id,String packet_id,String date,String channelType,String type,int bAction,int ratio)
	{
		int ret = 0;
		String sql;
		int btimeok = 1;
			
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days == 0)
		{
			btimeok = 0;
		}
		else if(days == 1)
		{
			if(bAction == 0)
			{
				Date now=new java.util.Date();
				if(now.getHours() < 11)
				{
					DebuUtil.log("次日不到11点不结算");
					btimeok = 0;
				}
			}
		}
		
		if(StringUtil.is_nullString(packet_id))
		{
			btimeok = 0;
		}
		
		if((channelType.equals("CPA")) && (btimeok == 1))
		{
			
			sql = String.format("select * from %s where packet_id='%s' and date='%s' and type<>'C' and type<>'A' limit 1"
					,baseTableName,packet_id,date);
			//DebuUtil.log(sql);
			ArrayList<Object> objList;
			objList = getList(sql);
	        if(objList.size() > 0)
	        {
	        	Activate obj;
	        	obj = (Activate)objList.get(0);
	        	String str = obj.type;
	        	//DebuUtil.log(str);
	        	/*if((str.equals("A") ||str.equals("C")) &&(days != 0))
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
	        		//		,game_id,packet_id);
	        		//settle_ratio = getCount(sql);
	        		settle_ratio = ratio;
	        		DebuUtil.log("结算比例= "+settle_ratio);
	        		sql = String.format("select * from %s where packet_id='%s' and date='%s'"
	        				,baseTableName,packet_id,date);
	        		
	        		ret = getRecordCount(sql);
	        		DebuUtil.log("激活总数= "+ret);
	        		ANum = (int)((ret*settle_ratio)/100.0+0.5);
	        		CNum = ret - ANum;
	        		DebuUtil.log("CNum= "+CNum);
	        		DebuUtil.log("ANum= "+ANum);
	        		
	    			
	        		/*sql = String.format("UPDATE %s SET type = '' WHERE game_id='%s' and packet_id='%s' and date='%s'"
	        				,baseTableName,game_id,packet_id,date);
        			super.executeUpdate(sql);*/
        			
	        		sql = String.format("select * from %s where packet_id='%s' and date='%s' and type='A'"
	        				,baseTableName,packet_id,date);
	        		int ANumO = getRecordCount(sql);
	        		int count = ANum-ANumO;
	        		DebuUtil.log("ANumO= "+ANumO);
	        		if((count > 0))
	        		{
	        			DebuUtil.log("count= "+count);
	        			sql = String.format("UPDATE %s SET type = 'A' WHERE packet_id='%s' and date='%s'  and type<>'C' and type<>'A' limit %d"
		        				,baseTableName,packet_id,date,ANum);
	        			DebuUtil.log("执行A结算");
	        			//DebuUtil.log(sql);
	        			super.executeUpdate(sql);
	        		}
	        		
	        		sql = String.format("select * from %s where packet_id='%s' and date='%s' and type='C'"
	        				,baseTableName,packet_id,date);
	        		int CNumO = getRecordCount(sql);
	        		count = CNum-CNumO;
	        		DebuUtil.log("CNumO= "+CNumO);
	        		if((count > 0))
	        		{
	        			DebuUtil.log("count= "+count);
	        			sql = String.format("UPDATE %s SET type = 'C' WHERE packet_id='%s' and date='%s' and type<>'C' and type<>'A' limit %d"
		        				,baseTableName,packet_id,date,CNum);
	        			DebuUtil.log("执行C结算");
	        			//DebuUtil.log(sql);
	        			super.executeUpdate(sql);
	        		}
	        		
	        		//if(days == 0)
	        		{
	        			//DebuUtil.log("当天不结算");
	        			if(type.equals("A"))
	    	        	{
	    	        		return ANum;
	    	        	}
	        			else if(type.equals("C"))
	    	        	{
	    	        		return CNum;
	    	        	}
	        		}
	        		
	        	}
	        }
			
		}
		sql = String.format("select * from %s where packet_id='%s' and date='%s'"
				,baseTableName,packet_id,date);
		
		/*if(!StringUtil.is_nullString(packet_id))
		{
		   sql+=" and packet_id='"+packet_id+"'";
		}*/
		
		if(channelType.equals("CPA"))
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
		//sql+="and date like '"+date+"%'";
		//ret = getRecordCount(sql);
		//long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"get_actNum",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		}
		
		//DebuUtil.log(date+"激活总数="+ret);
		return ret;
		
	}
	
	
	public int get_1dateNum(String game_id,String business_no,String channel_no,String packet_id,String date)
	{
		int ret;
		ret = get_dateNum( game_id,business_no,channel_no, packet_id, date, "dateo");
		//DebuUtil.log(date+"次日存数="+ret);
		return ret;
	}
	public int get_1dateNum(String game_id,String packet_id,String date)
	{
		int ret;
		ret = get_dateNum( game_id, "","",packet_id, date, "dateo");
		//DebuUtil.log(date+"次日存数="+ret);
		return ret;
	}
	
	//select * from activate where date='2014-07-18' and dateo=1 and game_id=156
	public int get_dateNum(String game_id,String business_no,String channel_no,String packet_id,String date,String type)
	{
		int ret = 0;
		String sql;
		//sql = String.format("select * from %s where packet_id='%s' and date='%s' and %s=1 "
		//		,baseTableName,packet_id,date,type);
		
		sql = String.format("select * from %s where date='%s' and %s=1"
				,baseTableName,date,type);
		
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
		
		//DataBufDao
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		int day = 2;
		if(type.equals("dates"))
		{
			day = 7+1;
		}
		else if(type.equals("datem"))
		{
			day = 30+1;
		}
		if(days >= day)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"get_dateNum",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		}
		DebuUtil.log("sql:"+sql);
		DebuUtil.log("ret:"+ret);
		
		return ret;
	}
	
	public int get_7dateNum(String game_id,String business_no,String channel_no,String packet_id,String date)
	{
		int ret;
		ret = get_dateNum( game_id,business_no,channel_no, packet_id, date, "dates");
		//DebuUtil.log(date+"次日存数="+ret);
		return ret;
	}
	public int get_7dateNum(String game_id,String packet_id,String date)
	{
		int ret;
		ret = get_dateNum( game_id,"","", packet_id, date, "dates");
		//DebuUtil.log(date+"7日存数="+ret);
		return ret;
	}
	public int get_30dateNum(String game_id,String business_no,String channel_no,String packet_id,String date)
	{
		int ret;
		ret = get_dateNum( game_id,business_no,channel_no, packet_id, date, "datem");
		//DebuUtil.log(date+"次日存数="+ret);
		return ret;
	}
	public int get_30dateNum(String game_id,String packet_id,String date)
	{
		int ret;
		ret = get_dateNum( game_id,"","", packet_id, date, "datem");
		//DebuUtil.log(date+"月存数="+ret);
		return ret;
	}
	

	public int getActivityRegNum(String game_id,String business_no,String channel_no,String packet_id,String date)
	{
		int ret = 0;
		
		if(!StringUtil.is_nullString(packet_id))
		{
			ret = getActivityRegNum(game_id, packet_id, date);
		}
		else
		{
			String sql;
			sql = String.format("select device_id from %s where date='%s' and reg=1 "
					,baseTableName,date);
			
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
			if(days >= 2)
			{
				DataBufDao dao = new DataBufDao();
				ret = dao.GetCount(sql,"getActivityRegNum_bc",date,packet_id);
			}
			else
			{
			   ret = getRecordCount(sql);
			}
			
			DebuUtil.log("sql:"+sql);
			DebuUtil.log("ret:"+ret);
		}
		
		
		//DebuUtil.log(sql);
		return ret;
	}
	public int getActivityRegNum(String game_id,String packet_id,String date)
	{
		int ret = 0;
		String sql;
		
		/*sql = String.format("select activate.device_id from %s,register where activate.packet_id='%s' and activate.date='%s' "
				,baseTableName,packet_id,date);
		
		sql += " and activate.game_id=register.game_id and activate.device_id=register.device_id group by activate.device_id,activate.game_id ";
		*///ret = getRecordCount(sql);
		sql = String.format("select device_id from %s where packet_id='%s' and date='%s' and reg=1 "
				,baseTableName,packet_id,date);
		
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 2)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"getActivityRegNum",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		}
		
		return ret;
	}
	
	public int do_alive(Activate activate)
	{
		int ret = 0;
		/*String sql;
		sql = String.format("select * from %s where device_id='%s' and game_id='%s'", baseTableName,DeviceId,GameId);
		int count = 0;// = getRecordCount(sql);
		Activate activate = null;
		boolean ret = false;
		
		ArrayList<Object> objList;
		//String sql;
		//sql = String.format("select * from %s where pay_no='%s'", baseTableName,pay_no);
		objList = getList(sql);
		count = objList.size();*/
		//if(count > 0)
		if(activate != null)
		{
			long days = 0;
			Date enddate = DateUtil.strToDate(DateUtil.getDate());//new Date(); 
			Date begindate;
			int bUpdate = 0;
			
			//activate = (Activate)objList.get(0);
			
			begindate = DateUtil.strToDate(activate.date);
			days = DateUtil.getDayDiff(begindate,enddate);
		
			DebuUtil.log("相差天数="+days);
			
			if(days == Activate.DATEODay)
			{
				if(activate.dateo != 1)
				{
					activate.dateo = 1;
					bUpdate = 1;
					ret = Activate.DATEODay;
				}
			}
			else if(days == Activate.DATESDay)
			{
				if(activate.dates != 1)
				{
					activate.dates = 1;
					bUpdate = 1;
					ret = Activate.DATESDay;
				}
			}
			else if(days == Activate.DATEMDay)
			{
				if(activate.datem != 1)
				{
					activate.datem = 1;
					bUpdate = 1;
					ret = Activate.DATEMDay;
				}
			}
			
			if(bUpdate == 1)
			{
				edit(activate);
				//ret = true;
			}
		}
		
		return ret;
	}
	
	public Activate getActivateRecord(String device_id,String game_id)
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where device_id='%s' and game_id='%s'", baseTableName,device_id,game_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			activate = (Activate)objList.get(0);
		}
		return activate;
	}
	
	public Activate getRecord(String device_id)//,String packet_id
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where device_id='%s' ", baseTableName,device_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			activate = (Activate)objList.get(0);
		}
		return activate;
	}
	
	 public static int activate(Activate obj,ActivateDao objDao,int bSA)
	{
		int ret = ConstValue.Fail;
		/*String sql;
		sql = String.format("select * from %s where device_id='%s' and game_id='%s'", objDao.baseTableName,obj.getDeviceId(),obj.getGameId());
		int count = objDao.getRecordCount(sql);
		
		if(count < 1)//
		{
			objDao.add(obj);
			
			ChannelDataDao channelDataDao = new ChannelDataDao();
			channelDataDao.Activate(obj);
			
		    ret = ConstValue.OK;
		   
		   //DebuUtil.log(sql);
		   DebuUtil.log("设备"+obj.getDeviceId()+"激活成功");
		}
		else//
		{
			DebuUtil.log("设备"+obj.getDeviceId()+"已经激活过");
		}*/
		//ChannelDataList
		ChannelDataDao channelDataDao = new ChannelDataDao();
		channelDataDao.Activate(obj, bSA);
		return ret;
	}
	
	 public  void reviseActivate()
	 {
	    Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where date='2015-05-18' ", baseTableName);
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			activate = (Activate)objList.get(i);
			ChannelDataDao channelDataDao = new ChannelDataDao();
			channelDataDao.Activate(activate, 1);
		}
	}
	 
	public  void reviseProvinceNewAccount()
	{
	    Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where date>='2015-05-05' limit 1 ", baseTableName);
		objList = getList(sql);
		for(int i= 0; i < objList.size(); i++)
		{
			activate = (Activate)objList.get(i);
			
			String code = GetAmountIndex.getRegionCode(activate.addr, "");
			activate.addr = GetAmountIndex.getRegionCode("", code);
			ProvinceDataDao provinceDataDao = new ProvinceDataDao();
			provinceDataDao.NewAccount(activate,true);
		}
	}
	
	public int activate(Activate obj,int bSA)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where device_id='%s' and game_id='%s'", baseTableName,obj.getDeviceId(),obj.getGameId());
		int count = getRecordCount(sql);
		//Activate activate = null;
		//do_alive(obj.getDeviceId(),obj.getGameId());
		
		/*LiveDao liveDao = new LiveDao();
		Live live = new Live();
		live.game_id = obj.game_id;
		live.packet_id = obj.packet_id;
		live.device_id = obj.device_id;
		live.date = obj.date;
		live.time = obj.time;
		liveDao.add(live);*/
		
	
		
		if(count < 1)//
		{
			if(bSA == 1)
			{
			   obj.loginDate = DateUtil.getDate();
			   
		    }
		   add(obj);
		   ret = ConstValue.OK;
		   //DebuUtil.log(sql);
		   if(ConstValue.OPTIMIZE == 1)
		   {
			   activate(obj,this, bSA);
			   
			   if(bSA == 1)
				{
				   model.Login login = new model.Login();
				   login.setDate(obj.getDate());
				   login.setDeviceId(obj.getDeviceId());
				   login.setGameId(obj.getGameId());
				   login.setPacketId(obj.getPacketId());
				   login.setPass("");
				   login.setTime(obj.getTime());
				   login.setUsername("");
				   LoginDao.loginData(login,null,0,1);
				   //省份用户统计
				   ProvinceDataDao provinceDataDao = new ProvinceDataDao();
				   provinceDataDao.NewAccount(obj,true);
				   provinceDataDao.Activate(obj);
			    }
		   }
		   DebuUtil.log("设备"+obj.getDeviceId()+"激活成功");
		   
		}
		else
		{
			DebuUtil.log("设备"+obj.getDeviceId()+"已经激活过");
			if(bSA == 1)
			{
			  ActivateDao activatedao = new ActivateDao();
			  Activate activate = activatedao.getActivateRecord(obj.getDeviceId(), obj.getGameId());
			  
			  int alive = activatedao.do_alive(activate);
			  
			  
			  if(activate != null)
			  {
				  
				  activate.addr = obj.addr;		
				  if(ConstValue.DEBUG == 1)
				  {
					  DebuUtil.log("保存用户省份="+activate.addr);
				  }
				  
				  int bnew = 0;
				  if(StringUtil.is_nullString(activate.loginDate))
				  {
					  bnew = 1;
				  }
				  
				  if((!StringUtil.is_nullString(activate.loginDate)) && activate.loginDate.equals(obj.date))
				  {					  
					  activatedao.edit(activate);
				  }
				  else
				  {
					  if(bSA == 1)
					  {					  
						   //省份用户统计
						   ProvinceDataDao provinceDataDao = new ProvinceDataDao();					  
						   provinceDataDao.Activate(obj);
					   }
					  
					  if(ConstValue.DEBUG == 1)
					   {
							DebuUtil.log("今天用户第一次登录");
					   }
					   model.Login login = new model.Login();
					   login.setDate(obj.getDate());
					   login.setDeviceId(obj.getDeviceId());
					   login.setGameId(obj.getGameId());
					   login.setPacketId(obj.getPacketId());
					   login.setPass("");
					   login.setTime(obj.getTime());
					   login.setUsername("");
					   LoginDao.loginData(login,activate,alive,bnew);
					   activate.addr = obj.addr;
					   activate.loginDate = obj.date;
					   activatedao.edit(activate);
					  
				  }
			   }
			}
		}
		return ret;
		
	}
	public List<ArrayList> getActivateTime(String date, String channel_no)
	{
	     
	     String sql;
			sql = String.format("select Time ,count(1) as count from %s where date = '%s' and channel_no ='%s' and reg = '1' group by hour(time)"
					,baseTableName,date,channel_no);
		List<ArrayList> list = getObjectList(sql);
	     //DebuUtil.log("sql="+sql);
	     return list;
	}
public Map<String,Object> getUserLevelList(String apkNo,Date beginDate,int pageSize,int curPage){
		
	  List<String> timelist = new ArrayList<String>();
	  List<String> userLevellist = new ArrayList<String>();
	  timelist.add("00:00:00-01:00:00");
	  timelist.add("01:00:00-02:00:00");
	  timelist.add("02:00:00-03:00:00");
	  timelist.add("03:00:00-04:00:00");
	  timelist.add("04:00:00-05:00:00");
	  timelist.add("05:00:00-06:00:00");
	  timelist.add("06:00:00-07:00:00");
	  timelist.add("07:00:00-08:00:00");
	  timelist.add("08:00:00-09:00:00");
	  timelist.add("09:00:00-10:00:00");
	  timelist.add("10:00:00-11:00:00");
	  timelist.add("11:00:00-12:00:00");
	  timelist.add("12:00:00-13:00:00");
	  timelist.add("13:00:00-14:00:00");
	  timelist.add("14:00:00-15:00:00");
	  timelist.add("15:00:00-16:00:00");
	  timelist.add("16:00:00-17:00:00");
	  timelist.add("17:00:00-18:00:00");
	  timelist.add("18:00:00-19:00:00");
	  timelist.add("19:00:00-20:00:00");
	  timelist.add("20:00:00-21:00:00");
	  timelist.add("21:00:00-22:00:00");
	  timelist.add("22:00:00-23:00:00");
	  timelist.add("23:00:00-24:00:00");
	  
	  userLevellist.add("0");
	  userLevellist.add("1");
	  userLevellist.add("2");
	  userLevellist.add("3");
	  userLevellist.add("4-10");
	  userLevellist.add("11-20");
	  userLevellist.add("21-30");
	  userLevellist.add("31-40");
	  userLevellist.add("40");

	  List<ArrayList> list = new ArrayList();
	  List<String> list1 = new ArrayList<String>();
	  for(String time:timelist)
	  {		
		  String begintime = time.split("-")[0];
		  String endtime= time.split("-")[1];
		  
		  for(String userLevel:userLevellist)
		  {	
			  int beginLevel = 0;
			  int endLevel = 0;
			  if(userLevel.contains("-")){
				  beginLevel = Integer.parseInt(userLevel.split("-")[0]);
				  endLevel = Integer.parseInt(userLevel.split("-")[1]);
			  }else{
				  beginLevel = Integer.parseInt(userLevel);
			  }
			  StringBuilder sql = new StringBuilder("select count(1) as count from activate where 1=1");
				
/*				if(!StringUtil.is_nullString(gameNo))
					sql.append(" AND game_id = '" + gameNo + "'");
				if(!StringUtil.is_nullString(channelNo))
					sql.append(" AND channel_no ='" + channelNo + "'");*/
				if(!StringUtil.is_nullString(apkNo))
					sql.append(" AND packet_id = '" + apkNo +"'");
				sql.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
				//按时间倒叙
				String str = "";
				if(beginLevel == 40){
					 str =String.format(" and level >= %d  and time >='%s' and time < '%s'" ,beginLevel,begintime,endtime);
				}else if(beginLevel <= 3){
					 str =String.format(" and level = %d  and time >='%s' and time < '%s'" ,beginLevel,begintime,endtime);
				}else{
					 str =String.format(" and level >= %d  and level < %d and time >='%s' and time < '%s'" ,beginLevel,endLevel,begintime,endtime);	
				}
				sql.append(str);
				Object list2 = getValue(sql.toString());
				list1.add(Long.toString((long) list2));
				
		  }
		  list1.add(time);
		  list.add((ArrayList) list1);
		  list1 = new ArrayList<String>();
	  }
/*StringBuilder sql = new StringBuilder("select time ,count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql.append(" AND business_no = '" + businessNo +"'");
		sql.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql.append(" group by hour(time)");
		
		//List<Object> list = getList(curPage,pageSize,sql.toString());
	//	List<ArrayList> list = getObjectList(sql.toString());
		System.out.println("list------"+list.size());
StringBuilder sql2 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql2.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql2.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql2.append(" AND business_no = '" + businessNo +"'");
		sql2.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql2.append("and onlinetime < '300' group by hour(time)");
		List<ArrayList> list3 = getObjectList(sql2.toString());*/
/*StringBuilder sql3 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql3.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql3.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql3.append(" AND business_no = '" + businessNo +"'");
		sql3.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql3.append("and onlinetime < '900' and onlinetime > '300' group by hour(time)");
		List<ArrayList> list4 = getObjectList(sql3.toString());
		
StringBuilder sql4 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql4.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql4.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql4.append(" AND business_no = '" + businessNo +"'");
		sql4.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql4.append("and onlinetime < '1800' and onlinetime > '900' group by hour(time)");
		List<ArrayList> list5 = getObjectList(sql4.toString());
	
StringBuilder sql5 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql5.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql5.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql5.append(" AND business_no = '" + businessNo +"'");
		sql5.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql5.append("and onlinetime < '3600' and onlinetime > '1800' group by hour(time)");
		List<ArrayList> list6 = getObjectList(sql5.toString());
		
StringBuilder sql6 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql6.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql6.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql6.append(" AND business_no = '" + businessNo +"'");
		sql6.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql6.append("and onlinetime < '10800' and onlinetime > '3600' group by hour(time)");
		List<ArrayList> list7 = getObjectList(sql6.toString());
		
StringBuilder sql7 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql7.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql7.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql7.append(" AND business_no = '" + businessNo +"'");
		sql7.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql7.append("and onlinetime < '10800' and onlinetime > '3600' group by hour(time)");
		List<ArrayList> list8 = getObjectList(sql7.toString());
		
StringBuilder sql8 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql8.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql8.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql8.append(" AND business_no = '" + businessNo +"'");
		sql8.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql8.append("and onlinetime < '18000' and onlinetime > '10800' group by hour(time)");
		List<ArrayList> list9 = getObjectList(sql8.toString());
		
StringBuilder sql9 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql9.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql9.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql9.append(" AND business_no = '" + businessNo +"'");
		sql9.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql9.append("and onlinetime < '43200' and onlinetime > '18000' group by hour(time)");
		List<ArrayList> list10 = getObjectList(sql9.toString());
		
StringBuilder sql10 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql10.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql10.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql10.append(" AND business_no = '" + businessNo +"'");
		sql10.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql10.append("and onlinetime < '86400' and onlinetime > '43200' group by hour(time)");
		List<ArrayList> list11 = getObjectList(sql10.toString());
		
StringBuilder sql11 = new StringBuilder("select count(1) as count from activate where 1=1");
		
		if(!StringUtil.is_nullString(gameNo))
			sql11.append(" AND game_id = '" + gameNo + "'");
		if(!StringUtil.is_nullString(channelNo))
			sql11.append(" AND channel_no ='" + channelNo + "'");
		if(!StringUtil.is_nullString(businessNo))
			sql11.append(" AND business_no = '" + businessNo +"'");
		sql11.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
		//按时间倒叙
		sql11.append("and onlinetime > '86400' group by hour(time)");
		List<ArrayList> list12 = getObjectList(sql11.toString());*/
//=========================		//System.out.println("list3------"+list3.size());
		
	//	List<ArrayList<Object>> list2 = new ArrayList<>();
		
		
/*		for(int i = 0;i < list.size();i++){
	//		ArrayList al = list.get(i);
			ArrayList al2 = list3.get(i);
			ArrayList al4 = list4.get(i);
			ArrayList al5 = list5.get(i);
			ArrayList al6 = list6.get(i);
			ArrayList al7 = list7.get(i);
			ArrayList al8 = list8.get(i);
			ArrayList al9 = list9.get(i);
			ArrayList al10 = list10.get(i);
			ArrayList al11 = list11.get(i);
			ArrayList al12 = list12.get(i);
		//	java.sql.Time time = (Time) al.get(0);
	//		Long bd = (Long) al.get(1);
			Long bd3 = (Long) al2.get(0);
			Long bd4 = (Long) al4.get(0);
			Long bd5 = (Long) al5.get(0);
			Long bd6 = (Long) al6.get(0);
			Long bd7 = (Long) al7.get(0);
			Long bd8 = (Long) al8.get(0);
			Long bd9 = (Long) al9.get(0);
			Long bd10 = (Long) al10.get(0);
			Long bd11 = (Long) al11.get(0);
			Long bd12 = (Long) al12.get(0);
			
			ArrayList<Object> newList = new ArrayList<>();
	//		newList.add(DateUtil.getDate(new java.util.Date(time.getTime()),"HH:00:00"));
		//	newList.add(bd.intValue());
			newList.add(bd3.intValue());
			newList.add(bd4.intValue());
			newList.add(bd5.intValue());
			newList.add(bd6.intValue());
			newList.add(bd7.intValue());
			newList.add(bd8.intValue());
			newList.add(bd9.intValue());
			newList.add(bd10.intValue());
			newList.add(bd11.intValue());
			newList.add(bd12.intValue());
			list2.add(newList);
		}*/
//	     Iterator it1 = list.iterator();
//	        while(it1.hasNext()){
//	            System.out.println(it1.next());
//	        }
	 
	/*	for(int i = 0;i < list3.size();i++){
			
			ArrayList al = list3.get(i);
			
			Long bd3 = (Long) al.get(0);
		//	newList.add(bd3.intValue());
		}*/
	
		//list2.addAll((Collection<? extends ArrayList<Object>>) list3);
		
	//	int totalCount = getRecordCount(sql.toString());
		
	//	int totalPage = (totalCount + pageSize - 1) / pageSize;
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("list", list);
		map.put("totalCount", 24);
		map.put("totalPage",1);
		return map;
	}
public Map<String,Object> getOnTimeList(String apkNo,Date beginDate,int pageSize,int curPage){
	
	  List<String> timelist = new ArrayList<String>();
	  List<String> onlinetimelist = new ArrayList<String>();
	  timelist.add("00:00:00-01:00:00");
	  timelist.add("01:00:00-02:00:00");
	  timelist.add("02:00:00-03:00:00");
	  timelist.add("03:00:00-04:00:00");
	  timelist.add("04:00:00-05:00:00");
	  timelist.add("05:00:00-06:00:00");
	  timelist.add("06:00:00-07:00:00");
	  timelist.add("07:00:00-08:00:00");
	  timelist.add("08:00:00-09:00:00");
	  timelist.add("09:00:00-10:00:00");
	  timelist.add("10:00:00-11:00:00");
	  timelist.add("11:00:00-12:00:00");
	  timelist.add("12:00:00-13:00:00");
	  timelist.add("13:00:00-14:00:00");
	  timelist.add("14:00:00-15:00:00");
	  timelist.add("15:00:00-16:00:00");
	  timelist.add("16:00:00-17:00:00");
	  timelist.add("17:00:00-18:00:00");
	  timelist.add("18:00:00-19:00:00");
	  timelist.add("19:00:00-20:00:00");
	  timelist.add("20:00:00-21:00:00");
	  timelist.add("21:00:00-22:00:00");
	  timelist.add("22:00:00-23:00:00");
	  timelist.add("23:00:00-24:00:00");
	  
	  onlinetimelist.add("0-300");
	  onlinetimelist.add("300-900");
	  onlinetimelist.add("900-1800");
	  onlinetimelist.add("1800-3600");
	  onlinetimelist.add("3600-10800");
	  onlinetimelist.add("10800-18000");
	  onlinetimelist.add("18000-43200");
	  onlinetimelist.add("43200-86400");
	  onlinetimelist.add("86400");
	  List<ArrayList> list = new ArrayList();
	  List<String> list1 = new ArrayList<String>();
	  for(String time:timelist)
	  {		
		  String begintime = time.split("-")[0];
		  String endtime= time.split("-")[1];
		  
		  for(String onlinetime:onlinetimelist)
		  {	
			  int beginonlinetime = 0;
			  int endonlinetime = 0;
			  if(onlinetime.contains("-")){
				  beginonlinetime = Integer.parseInt(onlinetime.split("-")[0]);
				  endonlinetime = Integer.parseInt(onlinetime.split("-")[1]);
			  }else{
				  beginonlinetime = Integer.parseInt(onlinetime);
			  }
			  StringBuilder sql = new StringBuilder("select  count(1) as count from activate where 1=1");
				
//				if(!StringUtil.is_nullString(gameNo))
//					sql.append(" AND game_id = '" + gameNo + "'");
//				if(!StringUtil.is_nullString(channelNo))
//					sql.append(" AND channel_no ='" + channelNo + "'");
				if(!StringUtil.is_nullString(apkNo))
					sql.append(" AND packet_id = '" + apkNo +"'");
				sql.append(" AND date = '" + DateUtil.getDate(beginDate) +"'");
				//按时间倒叙
				String str = "";
				if(endonlinetime == 0){
					 str =String.format(" and onlinetime >= %d  and time >='%s' and time < '%s'" ,beginonlinetime,begintime,endtime);
				}else{
					 str =String.format(" and onlinetime >= %d  and onlinetime <= %d and time >='%s' and time < '%s'" ,beginonlinetime,endonlinetime,begintime,endtime);	
				}
				sql.append(str);
				Object list2 = getValue(sql.toString());
				list1.add(Long.toString((long) list2));
		  }
		  list1.add(time);
		  list.add((ArrayList) list1);
		  list1 = new ArrayList<String>();
	  }
	Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("list", list);
		map.put("totalCount", 24);
		map.put("totalPage",1);
		return map;
	}
public Map<String,Object> getGameTimeList(String apkNo,Date beginDate,int pageSize,int curPage){
	
	List<String> timelist = new ArrayList<String>();
	  timelist.add("00:00:00-01:00:00");
	  timelist.add("01:00:00-02:00:00");
	  timelist.add("02:00:00-03:00:00");
	  timelist.add("03:00:00-04:00:00");
	  timelist.add("04:00:00-05:00:00");
	  timelist.add("05:00:00-06:00:00");
	  timelist.add("06:00:00-07:00:00");
	  timelist.add("07:00:00-08:00:00");
	  timelist.add("08:00:00-09:00:00");
	  timelist.add("09:00:00-10:00:00");
	  timelist.add("10:00:00-11:00:00");
	  timelist.add("11:00:00-12:00:00");
	  timelist.add("12:00:00-13:00:00");
	  timelist.add("13:00:00-14:00:00");
	  timelist.add("14:00:00-15:00:00");
	  timelist.add("15:00:00-16:00:00");
	  timelist.add("16:00:00-17:00:00");
	  timelist.add("17:00:00-18:00:00");
	  timelist.add("18:00:00-19:00:00");
	  timelist.add("19:00:00-20:00:00");
	  timelist.add("20:00:00-21:00:00");
	  timelist.add("21:00:00-22:00:00");
	  timelist.add("22:00:00-23:00:00");
	  timelist.add("23:00:00-24:00:00");
	  List<ArrayList> list = new ArrayList();
	  List<String> list1 = new ArrayList<String>();
	  for(String time:timelist)
	  {
		  String begintime = time.split("-")[0];
		  String endtime= time.split("-")[1];
		  StringBuilder sql = new StringBuilder("select  count(1) as count from activate where 1=1");
		  StringBuilder sql2 = new StringBuilder("select  count(1) as count from activate where  reg = 1");
		  StringBuilder sql3 = new StringBuilder("select  count(1) as count from activate where  (onlinetime > 300 or level > 10) ");
		  StringBuilder sql4 = new StringBuilder("select COUNT(DISTINCT device_id) as count from activate where device_id in (select pay.device_id from pay) ");
		  StringBuilder sql5 = new StringBuilder("select COUNT(device_id) as count from activate where device_id in (select pay.device_id from pay) ");
		  StringBuilder sql6 = new StringBuilder("select  count(1) as count from activate where dateo = 1");
		  StringBuilder sql7 = new StringBuilder("select  count(1) as count from activate where dates = 1");
		  StringBuilder sql8 = new StringBuilder("select  count(1) as count from activate where datem = 1");
		  StringBuilder sql9 = new StringBuilder("select  sum(amount) as count from pay,activate where pay.date >= '" + DateUtil.getDate(beginDate) +"'");

		  String str =String.format(" and packet_id = '%s' AND date = '" + DateUtil.getDate(beginDate) +"' and time >='%s' and time < '%s'" ,apkNo,begintime,endtime);
		  String str2 =String.format(" and pay.state = 1 and activate.packet_id = '%s' and pay.device_id = activate.device_id AND activate.date = '" + DateUtil.getDate(beginDate) +"' and activate.time >='%s' and activate.time < '%s'" ,apkNo,begintime,endtime);	
		  sql.append(str);
		  sql2.append(str);
		  sql3.append(str);
		  sql4.append(str);
		  sql5.append(str);
		  sql6.append(str);
		  sql7.append(str);
		  sql8.append(str);
		  sql9.append(str2);
		Object activateCount = getValue(sql.toString());
		Object registerCount = getValue(sql2.toString());
		Object vaildCount = getValue(sql3.toString());
		Object rechargeUserCount = getValue(sql4.toString());
		Object rechargeCount = getValue(sql5.toString());
		Object dayCount = getValue(sql6.toString());
		Object weekCount = getValue(sql7.toString());
		Object monthCount = getValue(sql8.toString());
		Object moneyCount = getValue(sql9.toString());
	
		list1.add(Long.toString((long)activateCount));
		list1.add(Long.toString((long)registerCount));
		list1.add(Long.toString((long)vaildCount));
		list1.add(Long.toString((long)rechargeUserCount));
		list1.add(Long.toString((long)rechargeCount));
		list1.add(Long.toString((long)dayCount));
		list1.add(Long.toString((long)weekCount));
		list1.add(Long.toString((long)monthCount));
		if(moneyCount ==null)
		{
			moneyCount = 0;
		}
		list1.add(moneyCount.toString());
		list1.add(time);
		
		list.add((ArrayList) list1);
		list1 = new ArrayList<String>();
	
	  }
	Map<String,Object> map = new HashMap<String,Object>();
	map.put("list", list);
	map.put("totalCount", 24);
	map.put("totalPage",1);
	return map;
	
	}
public Map<String,Object> getLtvList(String apkNo,Date beginDate,int pageSize,int curPage){
	
	List<String> timelist = new ArrayList<String>();
	  timelist.add("00:00:00-01:00:00");
	  timelist.add("01:00:00-02:00:00");
	  timelist.add("02:00:00-03:00:00");
	  timelist.add("03:00:00-04:00:00");
	  timelist.add("04:00:00-05:00:00");
	  timelist.add("05:00:00-06:00:00");
	  timelist.add("06:00:00-07:00:00");
	  timelist.add("07:00:00-08:00:00");
	  timelist.add("08:00:00-09:00:00");
	  timelist.add("09:00:00-10:00:00");
	  timelist.add("10:00:00-11:00:00");
	  timelist.add("11:00:00-12:00:00");
	  timelist.add("12:00:00-13:00:00");
	  timelist.add("13:00:00-14:00:00");
	  timelist.add("14:00:00-15:00:00");
	  timelist.add("15:00:00-16:00:00");
	  timelist.add("16:00:00-17:00:00");
	  timelist.add("17:00:00-18:00:00");
	  timelist.add("18:00:00-19:00:00");
	  timelist.add("19:00:00-20:00:00");
	  timelist.add("20:00:00-21:00:00");
	  timelist.add("21:00:00-22:00:00");
	  timelist.add("22:00:00-23:00:00");
	  timelist.add("23:00:00-24:00:00");
	  List<ArrayList> list = new ArrayList();
	  List<String> list1 = new ArrayList<String>();
	  for(String time:timelist)
	  {
		  String begintime = time.split("-")[0];
		  String endtime= time.split("-")[1];
		  StringBuilder sql = new StringBuilder("select  count(1) as count from activate where 1=1");
		  StringBuilder sql2 = new StringBuilder("select  count(1) as count from activate where reg = 1");
		  StringBuilder sql3 = new StringBuilder("select  sum(amount) as count from pay,activate where pay.date = '" + DateUtil.getDate(beginDate) +"'");
		  StringBuilder sql4 = new StringBuilder("select  sum(amount) as count from pay,activate where pay.date >= '" + DateUtil.getDate(beginDate) +"' and pay.date <= (select date_add('" + DateUtil.getDate(beginDate) +"', interval 1 day))");
		  StringBuilder sql5 = new StringBuilder("select  sum(amount) as count from pay,activate where pay.date >= '" + DateUtil.getDate(beginDate) +"' and pay.date <= (select date_add('" + DateUtil.getDate(beginDate) +"', interval 2 day))");
		  StringBuilder sql6 = new StringBuilder("select  sum(amount) as count from pay,activate where pay.date >= '" + DateUtil.getDate(beginDate) +"' and pay.date <= (select date_add('" + DateUtil.getDate(beginDate) +"', interval 4 day))");
		  StringBuilder sql7 = new StringBuilder("select  sum(amount) as count from pay,activate where pay.date >= '" + DateUtil.getDate(beginDate) +"' and pay.date <= (select date_add('" + DateUtil.getDate(beginDate) +"', interval 9 day))");
		  StringBuilder sql8 = new StringBuilder("select  sum(amount) as count from pay,activate where pay.date >= '" + DateUtil.getDate(beginDate) +"' and pay.date <= (select date_add('" + DateUtil.getDate(beginDate) +"', interval 14 day))");
		  StringBuilder sql9 = new StringBuilder("select  sum(amount) as count from pay,activate where pay.date >= '" + DateUtil.getDate(beginDate) +"' and pay.date <= (select date_add('" + DateUtil.getDate(beginDate) +"', interval 29 day))");
		  String str =String.format(" and activate.packet_id = '%s' AND date = '" + DateUtil.getDate(beginDate) +"' and time >='%s' and time < '%s'" ,apkNo,begintime,endtime);
		  String str2 =String.format(" and pay.state = 1 and activate.packet_id = '%s' and pay.device_id = activate.device_id AND activate.date = '" + DateUtil.getDate(beginDate) +"' and activate.time >='%s' and activate.time < '%s'" ,apkNo,begintime,endtime);	
		  sql.append(str);
		  sql2.append(str);
		  sql3.append(str2);
		  sql4.append(str2);
		  sql5.append(str2);
		  sql6.append(str2);
		  sql7.append(str2);
		  sql8.append(str2);
		  sql9.append(str2);
		  
			Object activateCount = getValue(sql.toString());
			Object registerCount = getValue(sql2.toString());
			Object Count1 = getValue(sql3.toString());
			Object Count2 = getValue(sql4.toString());
			Object Count3 = getValue(sql5.toString());
			Object Count5 = getValue(sql6.toString());
			Object Count10 = getValue(sql7.toString());
			Object Count15 = getValue(sql8.toString());
			Object Count30 = getValue(sql9.toString());
			list1.add(Long.toString((long)activateCount));
			list1.add(Long.toString((long)registerCount));
			if(Count1 == null){
				Count1 = 0;
			}
			if(Count2 == null){
				Count2 = 0;
			}
			if(Count3 == null){
				Count3 = 0;
			}
			if(Count5 == null){
				Count5 = 0;
			}
			if(Count10 == null){
				Count10 = 0;
			}
			if(Count15 == null){
				Count15 = 0;
			}
			if(Count30 == null){
				Count30 = 0;
			}
			list1.add(Count1.toString());
			list1.add(Count2.toString());
			list1.add(Count3.toString());
			list1.add(Count5.toString());
			list1.add(Count10.toString());
			list1.add(Count15.toString());
			list1.add(Count30.toString());
			list1.add(time);
			list.add((ArrayList) list1);
			list1 = new ArrayList<String>();
	  }
	  
	Map<String,Object> map = new HashMap<String,Object>();
	map.put("list", list);
	map.put("totalCount", 24);
	map.put("totalPage",1);
	return map;
	}
}
