package viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import dao.CooperationDao;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import model.App;
import model.ChannelDataReq;
import model.Cooperation;
import model.Userinfo;

public class SearchCom {

	public static Map<String,String> getGameTypeMap(String role)
	{
		Map<String,String> gameTypeMap = new HashMap<String,String>();
	    if(role.equals(Userinfo.adminOnline) || role.equals(Userinfo.operationOnline))
		{
			   gameTypeMap.put(App.ON_LINE, "网游");
			     
		}
		else if(role.equals(Userinfo.adminOffline) || role.equals(Userinfo.operationOffline))
		{
			   gameTypeMap.put(App.OFF_LINE, "单机");
			   gameTypeMap.put(App.MM_ON_LINE, "MM网游");
		}
		else
		{
			   gameTypeMap.put("", "请选择");
			   gameTypeMap.put(App.OFF_LINE, "单机");
			   gameTypeMap.put(App.MM_ON_LINE, "MM网游");
			   gameTypeMap.put(App.ON_LINE, "网游");
		}
	    return gameTypeMap;
    }
	
	public static void getGameTypeMap(HttpServletRequest request,String role)
	{
		Map<String,String> gameTypeMap = new HashMap<String,String>();
	    if(role.equals(Userinfo.adminOnline) || role.equals(Userinfo.operationOnline))
		{
			gameTypeMap.put(App.ON_LINE, "网游");
			     
		}
		else if(role.equals(Userinfo.adminOffline) || role.equals(Userinfo.operationOffline))
		{
			 gameTypeMap.put(App.OFF_LINE, "单机");
			 gameTypeMap.put(App.MM_ON_LINE, "MM网游");
		}
		else
		{
			 gameTypeMap.put("", "请选择");
			 gameTypeMap.put(App.OFF_LINE, "单机");
			 gameTypeMap.put(App.MM_ON_LINE, "MM网游");
			 gameTypeMap.put(App.ON_LINE, "网游");
		}
	    request.setAttribute("GameTypeMap", gameTypeMap);
	    return ;
    }
	
	public static String getGameType(String role)
	{
		String game_type = "";
	    if(role.equals(Userinfo.adminOnline) || role.equals(Userinfo.operationOnline))
		{
			       game_type = App.ON_LINE;
		}
		else if(role.equals(Userinfo.adminOffline) || role.equals(Userinfo.operationOffline))
		{
			 
			   if(StringUtil.is_nullString(game_type))
			   {  
			           game_type = App.OFF_LINE;
			   }
		}
	    if(role.equals("SA"))
	    {
	    	game_type = App.OFF_LINE;
	    }
	    
		return game_type;
    }
	
	public static Map getYearMap()
	{
		Map yearMap=new HashMap<String,String>();
		yearMap.put("2014", "2014");
		yearMap.put("2015", "2015");
		return yearMap;
	}
	
	public static Map getMonthMap()
	{
		Map monthMap=new HashMap<String,String>();
		monthMap.put("01", "1");
		monthMap.put("02", "2");
		monthMap.put("03", "3");
		monthMap.put("04", "4");
		monthMap.put("05", "5");
		monthMap.put("06", "6");
		monthMap.put("07", "7");
		monthMap.put("08", "8");
		monthMap.put("09", "9");
		monthMap.put("10", "10");
		monthMap.put("11", "11");
		monthMap.put("12", "12");
		return monthMap;
	}
	
	public static Map getGameMap(String game_type,String game_no,String channel_no,String business_no,String cp_no,String role)
	{
        String appsql = getGameMapSQL( game_type, game_no, channel_no, business_no,cp_no,role);
		
		DebuUtil.log("appsql="+appsql);
		CooperationDao cooperationDao=new CooperationDao();
		Map gameMap=cooperationDao.getSelectMap(appsql);
		//DebuUtil.log("gameMap="+gameMap.toString());
		return gameMap;
	}
	
	public static String  getGameMapSQL(String game_type,String game_no,String channel_no,String business_no,String cp_no,String role)
	{
		String appsql ;//= "select app.no,app.name from app where 1=1 ";
		appsql = "select DISTINCT(app.no),app.name from app,cooperation where app.no=cooperation.app_no ";
		if(StringUtil.is_nullString(game_no) || role.equals(Userinfo.CPA) || role.equals(Userinfo.CPA_R) || role.equals(Userinfo.CPS))
		{
			if(!StringUtil.is_nullString(channel_no))//channel_no
			{
				appsql += " and cooperation.channel_no='"+channel_no+"'";
			}
			
		}
		
		
		if(!StringUtil.is_nullString(business_no))
		{
			appsql += " and cooperation.business_no='"+business_no+"'";
		}
		
		if(!StringUtil.is_nullString(game_type))
		{
			appsql+=" and app.game_type='"+game_type+"' ";
		}
		
		DebuUtil.log("role="+role);
		
		if(role.equals(Userinfo.CP))
		{
			appsql = "select app.no,app.name from app where 1=1 ";
			appsql += " and cp_no='"+cp_no+"'";
		}
		appsql+=" and app.game_type<>'0' ";
		
		appsql+=" order by app.id desc ";
		
		return appsql;
	}
	
	public static Map getGameMap(String game_type,String game_no,String channel_no,String business_no)
	{
		String appsql = getGameMapSQL( game_type, game_no, channel_no, business_no,"","");
		
		DebuUtil.log("appsql="+appsql);
		CooperationDao cooperationDao=new CooperationDao();
		Map gameMap=cooperationDao.getSelectMap(appsql);
		DebuUtil.log("gameMap="+gameMap.toString());
		return gameMap;
	}
	
	public static Map getBusinessMap(String game_type,String game_no,String channel_no,String business_no,String role)
	{
        String bssSql = getBusinessMapSQL( game_type, game_no, channel_no, business_no,role);
		
		CooperationDao cooperationDao=new CooperationDao();
		Map gameMap=cooperationDao.getSelectMap(bssSql);
		return gameMap;
	}
	
	public static String getBusinessMapSQL(String game_type,String game_no,String channel_no,String business_no,String role)
	{
		String bssSql = "select businesser.no,businesser.name from businesser  ";
		if(StringUtil.is_nullString(business_no))
		{
			bssSql = "select DISTINCT(businesser.no),businesser.name from businesser,cooperation  where businesser.no=cooperation.business_no ";
			if(!StringUtil.is_nullString(game_no))//game_no
			{
				bssSql += " and cooperation.app_no='"+game_no+"'";
			}
			if(!StringUtil.is_nullString(channel_no))
			{
				bssSql += " and cooperation.channel_no='"+channel_no+"'";
			}
		}
		bssSql+=" order by businesser.id desc ";
		
		return bssSql;
	}
	
	public static Map getBusinessMap(String game_type,String game_no,String channel_no,String business_no)
	{
		String bssSql = getBusinessMapSQL( game_type, game_no, channel_no, business_no,"");
		
		CooperationDao cooperationDao=new CooperationDao();
		Map gameMap=cooperationDao.getSelectMap(bssSql);
		return gameMap;
	}
	
	public static Map getChannelMap(String game_type,String game_no,String channel_no,String business_no,String role)
	{
        String channelsql=getChannelMapSQL( game_type, game_no, channel_no, business_no, role);
		
		CooperationDao cooperationDao=new CooperationDao();
		Map channelMap=cooperationDao.getSelectMap(channelsql);
		return channelMap;
	}
	
	public static String getChannelMapSQL(String game_type,String game_no,String channel_no,String business_no,String role)
	{
		String channelsql;

		//if(StringUtil.is_nullString(channel_no))
		{
			channelsql = String.format("select channel_no,channel.name from cooperation,channel where channel.no=cooperation.channel_no ");
			if(!StringUtil.is_nullString(game_no))
			{
				channelsql += " and cooperation.app_no='"+game_no+"'";
			}
			if(!StringUtil.is_nullString(business_no))
			{
				channelsql += " and cooperation.business_no='"+business_no+"'";
			}
			channelsql += " GROUP BY channel_no";
		}
		
	
		return channelsql;
	}
	
	public static Map getChannelMap(String game_type,String game_no,String channel_no,String business_no)
	{
		String channelsql=getChannelMapSQL( game_type, game_no, channel_no, business_no, "");
		
		CooperationDao cooperationDao=new CooperationDao();
		Map channelMap=cooperationDao.getSelectMap(channelsql);
		return channelMap;
	}
	
	public static Map getPacketMap(String game_type,String game_no,String channel_no,String business_no)
	{
		Map  packetMap=new HashMap<String,String>();
		
		String sql = "select packet_id,packet_id from Cooperation where 1=1 ";
		int count = 0;
		if(!StringUtil.is_nullString(game_no) )
		{
			sql += " and app_no='"+game_no+"'";
			count ++;			
		}
		if(!StringUtil.is_nullString(channel_no) )
		{
			sql += " and channel_no='"+channel_no+"'";
			count ++;	
						
		}
		if(!StringUtil.is_nullString(business_no))
		{
			sql += " and business_no='"+business_no+"'";
			count ++;	
						
		}
		if(count >= 2)
		{
			CooperationDao cooperationDao=new CooperationDao();
			packetMap=cooperationDao.getSelectMap(sql);
		}
		return packetMap;
	}
	
	public static ArrayList<ChannelDataReq> getList(String selyear,String selmonth,String game_no,String channel_no,String business_no,int channel_id,String distype,String game_type)
	{
		int day = DateUtil.getDaysByMonth(Integer.valueOf(selyear), Integer.valueOf(selmonth));
		
		String beginDate=selyear+"-"+selmonth+"-01";
		String endDate=selyear+"-"+selmonth+"-"+day;
		ArrayList<ChannelDataReq> list;
		
		if(channel_id != -1)
		{
			CooperationDao cooperationDao=new CooperationDao();
			Cooperation cooperation = (Cooperation)cooperationDao.getById(channel_id);
			DebuUtil.log("GamePayData1"+cooperation.packet_id);
			DebuUtil.log("game_no="+game_no+"business_no="+business_no
					+"channel_no="+channel_no+"distype="+distype+"beginDate="+beginDate+"endDate="+endDate);
			list = ChannelDataReq.get_GamedataList(game_type,game_no,"","",cooperation.packet_id,cooperation.settle_type,distype,beginDate,endDate);
		}
		else
		{
			DebuUtil.log("game_no="+game_no+"business_no="+business_no
			+"channel_no="+channel_no+"distype="+distype+"beginDate="+beginDate+"endDate="+endDate+" game_type="+game_type+" channel_id="+channel_id);
			list = ChannelDataReq.get_GamedataList(game_type,game_no,business_no,channel_no,"","",distype,beginDate,endDate);
			
		}
		return list;
	}
	
	public static ArrayList<ChannelDataReq> getList(String selyear,String selmonth,String game_no,String channel_no,String business_no,String packet_id,String distype,String game_type)
	{
		int day = DateUtil.getDaysByMonth(Integer.valueOf(selyear), Integer.valueOf(selmonth));
		
		String beginDate=selyear+"-"+selmonth+"-01";
		String endDate=selyear+"-"+selmonth+"-"+day;
		ArrayList<ChannelDataReq> list;
		
		if(!StringUtil.is_nullString(packet_id))
		{
			CooperationDao cooperationDao=new CooperationDao();
			Cooperation cooperation = (Cooperation)cooperationDao.getRecord(packet_id);
			DebuUtil.log("GamePayData1"+cooperation.packet_id);
			DebuUtil.log("game_no="+game_no+"business_no="+business_no
					+"channel_no="+channel_no+"distype="+distype+"beginDate="+beginDate+"endDate="+endDate);
			list = ChannelDataReq.get_GamedataList(game_type,game_no,"","",cooperation.packet_id,cooperation.settle_type,distype,beginDate,endDate);
		}
		else
		{
			DebuUtil.log("game_no="+game_no+"business_no="+business_no
			+"channel_no="+channel_no+"distype="+distype+"beginDate="+beginDate+"endDate="+endDate+" game_type="+game_type+" packet_id="+packet_id);
			list = ChannelDataReq.get_GamedataList(game_type,game_no,business_no,channel_no,"","",distype,beginDate,endDate);
			
		}
		return list;
	}
	
	public static String getCurMonth(String selmonth)
	{
		if(StringUtil.is_nullString(selmonth))
		{
			Date curdate = new Date();
			selmonth =String.format("%02d",curdate.getMonth()+1);
		}
		return selmonth;
	}
	
	public static Map getDayMap(String selyear,String selmonth)
	{
	    Map<String,String> dayMap = new HashMap<String,String>();
	    for(int i=1;i<=31;i++)
	    {
			if(i<10)
				dayMap.put("0"+i, i+"");
			else
				dayMap.put(i+"", i+"");
		}
	    return dayMap;
	}
	public static String getCurYear(String selyear)
	{
		if(StringUtil.is_nullString(selyear))
		{
		   Date curdate = new Date();
		   selyear =String.format("%d",1900+curdate.getYear());
		}

		return selyear;
	}
	
}
