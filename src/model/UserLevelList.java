package model;

import java.util.ArrayList;

import util.DateUtil;
import util.StringUtil;
import dao.ActivateDao;
import dao.ChannelDataDao;
import dao.CooperationDao;
import dao.LiveDao;
import dao.LoginDao;
import dao.PayDao;
import dao.RegisterDao;


public class UserLevelList implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9020383335951423265L;

	public String date;
	public Integer registerNum;	
	public Integer levle1Num;
	public Integer levle2Num;	
	public Integer levle3Num;
	public Integer levle4Num;
	public Integer levle5Num;
	
	public UserLevelList(String game_id,String BusinesserNo,String channel,String packet_id,String date,String channelType,String dispaytype)
	{
		ArrayList<Object> list = null;
		
		this.date = date;
		
		registerNum = 0;
		levle1Num = 0;
		levle2Num = 0;	
		levle3Num = 0;
		levle4Num = 0;
		levle5Num = 0;
		
		//if((channel != null) || (game_id != null))
		{
			CooperationDao dao = new CooperationDao();
			if(!StringUtil.is_nullString(packet_id))
			{
				game_id = null;
				channel = null;
				BusinesserNo = null;
			}
			list = dao.getRecordsByAppNoChannel(game_id,BusinesserNo,channel,packet_id);
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
		int num = 1;//loginDao.getLoginNum2(game_id, packet_id, date);
		
		if(num > 0)
		{
			//DebuUtil.log("packet_id:"+packet_id);
			ActivateDao activateDao = new ActivateDao();
			RegisterDao registerDao = new RegisterDao();
			//ChannelData channelData = ChannelDataList.getChannelData( packet_id, date);
			//registerNum += channelData.dayAllAcount;
			ChannelDataDao channelDataDao = new ChannelDataDao();
			ChannelData channelData = channelDataDao.getRecord(packet_id, date);
			//registerNum += registerDao.getRegAccountNum(game_id, packet_id, date);
			if(channelData != null)
			{
				registerNum += channelData.dayAllAcount;
			}
			levle1Num += activateDao.getRegAccountNumByLevel(game_id, packet_id, date,3,10);
			levle2Num += activateDao.getRegAccountNumByLevel(game_id, packet_id, date,11,20);
			levle3Num += activateDao.getRegAccountNumByLevel(game_id, packet_id, date,21,30);
			levle4Num += activateDao.getRegAccountNumByLevel(game_id, packet_id, date,31,40);
			levle5Num += activateDao.getRegAccountNumByLevel(game_id, packet_id, date,41,0);
		}
	}
	
	public static  ArrayList<UserLevelList> get_UserLevelList(String game_id,String BusinesserNo,String channel,
			String packet_id,String channelType,String dispaytype,String begindate,String enddate)
	{
		ArrayList<UserLevelList> ret = new ArrayList<UserLevelList>();
		//DebuUtil.log("get_GamedataList");
		LiveDao liveDao = new LiveDao();
		ArrayList<String> dateList;// = liveDao.get_DateList(game_id,channel,packet_id,null,null);
		
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
			
			//datestr=datestr.substring(0, 10);
			if((small >= 0) && (big <=0))
			{
			   ret.add(new UserLevelList(game_id, BusinesserNo,channel,packet_id,datestr,channelType,dispaytype));
			}
		}
		return  ret;
	}	
	

}
