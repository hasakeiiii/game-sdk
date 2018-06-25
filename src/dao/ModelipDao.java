package dao;

import java.util.ArrayList;

import model.Modelip;
import azul.BaseDao;

public class ModelipDao extends BaseDao{
	public ModelipDao() {
		init();
	}

	public String getIP(String IP)
	{
		String sql;
		String ret = "";
		sql = String.format("select * from %s where ip='%s'"
				,baseTableName,IP);
		
		Modelip ip = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			ip = (Modelip)objList.get(0);
			ret = ip.getAddr();
		}
		
		return ret;
	}
}
