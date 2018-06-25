package dao;

import java.util.ArrayList;

import model.Activate;
import model.ChannelData;
import model.Cooperation;
import model.Login;
import model.Pay;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import azul.BaseDao;

public class LoginDao extends BaseDao{
	public LoginDao() {
		init();
	}
	
	public Login getRecord(String username)//,String packet_id
	{
		Login login = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where username='%s' ", baseTableName,username);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			login = (Login)objList.get(0);
		}
		return login;
	}
	
	public int getLoginNum2(String game_id,String packet_id,String date)
	{
		int ret = 0;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' "
				,baseTableName,packet_id);
		
		sql+=" and date='"+date+"' ";//group by device_id,game_id
		//ret = getRecordCount(sql);//getCount
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"getLoginNum2",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		}
		DebuUtil.log(date+"登录总数="+ret);
		//DebuUtil.log(sql);
		return ret;
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
		   LoginDao loginDao = new LoginDao(); 
		   ArrayList<Object> list2 = loginDao.getAllList();
		   Login login = null;
		   //RegisterDao registerDao = new RegisterDao();
		   //Register register = null;
		   int reg = 0;
		   int act = 0;
		   int count = 0;
		   //DebuUtil.log("激活数:"+list2.size());
		   for(int i=0;i<list2.size();i++)
		   {
			   login=(Login)list2.get(i);
		        CooperationDao cooperationDao=new CooperationDao();
				Cooperation cooperation = cooperationDao.getRecord(login.packet_id);
				if(cooperation != null)
				{
					login.business_no = cooperation.getBusinessNo();
					login.channel_no = cooperation.getChannelNo();
					loginDao.edit(login);
				}
		   
		        reg++;
		        DebuUtil.log("登录总数:"+list2.size()+"当前:"+reg);
		   }
		  
	}
	
	public int getLoginNum(String game_id,String business_no,
			String channel_no,String packet_id,String begindate,String enddate)
	{
		int ret = 0;
	
		
		String sql;
		sql = String.format("select * from %s where date>='%s' and date<='%s'"
				,baseTableName,begindate,enddate);
		
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
		//sql+=" group by device_id,game_id";//group by device_id,game_id
		//ret = getRecordCount(sql);//getCount
		//long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		
		
		
		ret = getRecordCount(sql);
		
		DebuUtil.log("sql:"+sql);
		DebuUtil.log("ret:"+ret);
		//DebuUtil.log(date+"登录总数="+ret);
		
		//DebuUtil.log(sql);
		return ret;
	}
	

	public int getLoginNum(String game_id,String business_no,
			String channel_no,String packet_id,String date)
	{
		int ret = 0;
	
		if(!StringUtil.is_nullString(packet_id))
		{
			ret = getLoginNum(game_id, packet_id, date);
		}
		else
		{
			String sql;
			sql = String.format("select * from %s where date='%s' "
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
			
			
			sql+=" group by device_id,game_id";//group by device_id,game_id
			//ret = getRecordCount(sql);//getCount
			long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
			
			
			if(days >= 1)
			{
				DataBufDao dao = new DataBufDao();
				ret = dao.GetCount(sql,"getLoginNum_bc",date,packet_id);
			}
			else
			{
			   ret = getRecordCount(sql);
			}
			DebuUtil.log("sql:"+sql);
			DebuUtil.log("ret:"+ret);
			//DebuUtil.log(date+"登录总数="+ret);
		}
		
		
		//DebuUtil.log(sql);
		return ret;
	}
	
	public int getLoginNum(String game_id,String packet_id,String date)
	{
		int ret = 0;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' "
				,baseTableName,packet_id);
		
		sql+=" and date='"+date+"' group by device_id,game_id";//group by device_id,game_id
		//ret = getRecordCount(sql);//getCount
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"getLoginNum",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		}
		//DebuUtil.log(date+"登录总数="+ret);
		//DebuUtil.log(sql);
		return ret;
	}
	
	public void test()
	{
		Login obj = new Login();
		obj.setUsername("15914051609");
		obj.setDeviceId("123456789");
		obj.setPacketId("001");
		obj.setGameId("005");
		obj.setDate(DateUtil.getDateTime());
		add(obj);
		DebuUtil.log("添加登陆成功");
	}
	
	public static void loginData(Login obj,Activate activate,int alive,int bnew)
	{
		ChannelDataDao channelDataDao = new ChannelDataDao();
		channelDataDao.login(obj, alive,bnew);
	}
	
	
	//3。登录，如果当天第一次登录，当天loginNum加上，如果是次留，激活日期数据day1Num则加1
	public int login(Login obj)
	{
		int ret = ConstValue.Fail;
		String retValue="";
		RegisterDao registerDao = new RegisterDao();
		ret = registerDao.login(obj.getUsername(), obj.getPass());
		
		/*if(obj.device_id.equals("99000535142483"))
		{
			DebuUtil.log3("name="+obj.getUsername()+"pass="+obj.getPass());
			DebuUtil.log3("registerDao.login ret="+ret);//
		}*/
		
		if(ret == ConstValue.OK)
		{
		  retValue = add(obj);//lsl
		  if(!retValue.equals("1"))
		  {
			  ret = ConstValue.Fail;
		  }
		  ActivateDao activatedao = new ActivateDao();
		  Activate activate = activatedao.getActivateRecord(obj.getDeviceId(), obj.getGameId());
		  
		  int alive = activatedao.do_alive(activate);
		  
		  if((activate != null) && (ConstValue.OPTIMIZE == 1))
		  {
			  
			  obj.setCurPacketId(activate.packet_id);
			  
			  int bnew = 0;
			  if(StringUtil.is_nullString(activate.loginDate))
			  {
				  bnew = 1;
			  }
			  
			  if((!StringUtil.is_nullString(activate.loginDate)) && activate.loginDate.equals(obj.date))
			  {
				  
			  }
			  else
			  {
				  loginData(obj,activate,alive,bnew);
				  
				  activate.loginDate = obj.date;
				  retValue = activatedao.edit(activate);
				  /*if(obj.device_id.equals("99000535142483"))//当然呀
					{
						DebuUtil.log3("registerDao.login retValue="+retValue);
					}*/
				  if(!retValue.equals("1"))
				  {
					  ret = ConstValue.Fail;
				  }
			  }
		  }
		  
		  
		  //DebuUtil.log("添加登陆成功");
		}
		
		/*if(obj.device_id.equals("99000535142483"))
		{
			DebuUtil.log3("return ret="+ret);
		}*/
		return ret;
	}
	
	
	
}
