package dao;

import java.util.ArrayList;

import model.Activate;
import model.MsmList;
import model.ZhangZhong;
import azul.BaseDao;

public class ZhangZhongDao extends BaseDao{
	public ZhangZhongDao() {
		init();
	}

	
	//select * from msm_list where msg like  '%E0199BA7DB6845AB%'
	public ZhangZhong getRecord(String zzdata)
	{
		ZhangZhong msmList = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s  where zzdata='%s' ", baseTableName,zzdata);//where packet_id='217150001'
		objList = getList(sql);
		if(objList.size() > 0)
		{
			msmList = (ZhangZhong)objList.get(0);
		}
		return msmList;
	}
	
	public ZhangZhong getQiangRecord(String sid)
	{
		ZhangZhong msmList = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s  where sid='%s' ", baseTableName,sid);//where packet_id='217150001'
		objList = getList(sql);
		if(objList.size() > 0)
		{
			msmList = (ZhangZhong)objList.get(0);
		}
		return msmList;
	}

}
