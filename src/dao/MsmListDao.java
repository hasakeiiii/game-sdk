package dao;

import java.util.ArrayList;

import model.Activate;
import model.MsmList;
import azul.BaseDao;

public class MsmListDao extends BaseDao{
	public MsmListDao() {
		init();
	}

	
	//select * from msm_list where msg like  '%E0199BA7DB6845AB%'
	MsmList getMsmListByTradeNo(String tradeNO)
	{
		MsmList msmList = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s  where msg like  '%s%s%s' ", baseTableName,"%",tradeNO,"%");//where packet_id='217150001'
		objList = getList(sql);
		if(objList.size() > 0)
		{
			msmList = (MsmList)objList.get(0);
		}
		return msmList;
	}
	
}
