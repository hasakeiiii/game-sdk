package dao;

import java.util.ArrayList;
import java.util.Date;

import model.Activate;
import model.Cooperation;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import azul.BaseDao;

public class LiveDao extends BaseDao{
	public LiveDao() {
		init();
	}

	
	public ArrayList<String> get_DateList(String begindatestr,String enddatestr)
	{
		ArrayList<String> ret = new ArrayList<String>();
		
		
		Date curdate = DateUtil.strToDate(DateUtil.getDate());//new Date();
		Date begindate;
		Date enddate;
		
		begindate = DateUtil.strToDate(begindatestr);
		enddate = DateUtil.strToDate(enddatestr);
		
		for(int i = 0; i < 31; i++)
		{
			long days = 0;
			long days1 = 0;
			Date idate = DateUtil.addDate("dd", i, begindate);
			
			days = DateUtil.getDayDiff(curdate,idate);
			days1 = DateUtil.getDayDiff(idate,enddate);
			
			if((days <= 0)&&(days1 >= 0))
			{
				
				ret.add(DateUtil.getDate(idate));
			}
			else
			{
				break;
			}
		}
		return ret;
	}
	
	public ArrayList<String> get_DateList(String game_id,String BusinesserNo,String channel,String packet_id,String begindate,String enddate)
	{
	     ArrayList<String> ret = new ArrayList<String>();
	     ArrayList<Object> list = null;
	     
	     String sql;
	     
	     CooperationDao dao = new CooperationDao();
	     
		if(!StringUtil.is_nullString(packet_id))
		{
			game_id = null;
			channel = null;
			 /*ArrayList<String> tlist = null;
			 
			  sql = String.format("select date from %s where packet_id='%s' group by date"
						,baseTableName,packet_id);
			  DebuUtil.log("sql="+sql);
			  tlist =getValueList(sql);
			  ret.addAll(tlist);*/
				  
		}
		else if(!StringUtil.is_nullString(game_id))
		{
			
		}
		list = dao.getRecordsByAppNoChannel(game_id,BusinesserNo,channel,packet_id);
			
		 
	     
	    if(list.size() > 0)
		{
			for(int i=0;i<list.size();i++)
			{
			  ArrayList<String> tlist = null;
			  Cooperation cooperation = (Cooperation)list.get(i);
			  sql = String.format("select date from %s where packet_id='%s' group by date"
						,baseTableName,cooperation.packet_id);
			  DebuUtil.log("sql="+sql);
			  tlist =getValueList(sql);
			  ret.addAll(tlist);
			}
		}
	     
	     return ret;
	}
	
}
