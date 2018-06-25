package dao;

import java.util.ArrayList;

import model.MobileNum;
import model.Modelip;
import azul.BaseDao;

public class MobileNumDao extends BaseDao{
	public MobileNumDao() {
		init();
	}

	public String getImsi(String imsi)
	{
		String sql;
		String ret = "";
		sql = String.format("select * from %s where content='%s'"
				,baseTableName,imsi);
		
		MobileNum mobileNum = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			mobileNum = (MobileNum)objList.get(0);
			ret = mobileNum.getNum();
		}
		
		return ret;
	}
}
