package dao;

import java.util.ArrayList;

import model.FilterImei;
import azul.BaseDao;

public class FilterImeiDao extends BaseDao{
	public FilterImeiDao() {
		init();
	}

	public boolean getImei(String imei)
	{
		String sql;
		boolean ret = false;
		sql = String.format("select * from %s where imei='%s' "
				,baseTableName,imei);
		
		FilterImei iemi = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			iemi = (FilterImei)objList.get(0);
			ret = true;
		}
		
		return ret;
	}
}
