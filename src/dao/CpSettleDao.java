package dao;

import java.util.ArrayList;
import java.util.List;

import model.App;
import model.CpSettle;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;

public class CpSettleDao extends CacheDao{
	public CpSettleDao() {
		init();
	}
	
	public CpSettle getAppRecord(String gameId,String date)
	{
		CpSettle cpSettle = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where game_id='%s' and date = '%s'", baseTableName,gameId,date);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			cpSettle = (CpSettle)objList.get(0);
		}
		return cpSettle;
	}
	
	
	public int addCpSettle(CpSettle obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where game_id='%s' and date = '%s'", baseTableName,obj.getGameId(),obj.getDate());
		int count = getRecordCount(sql);
		DebuUtil.log("应用表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	
}
