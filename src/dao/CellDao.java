package dao;

import java.util.ArrayList;

import model.Cell;
import model.Cooperation;
import azul.BaseDao;

public class CellDao extends BaseDao{
	public CellDao() {
		init();
	}

	public String getLBS(int mcc,int mnc,int cell_id,int lac)
	{
		String sql;
		String ret = "";
		sql = String.format("select * from %s where mcc=%d and mnc=%d and cell_id=%d and lac=%d "
				,baseTableName,mcc, mnc, cell_id,lac);
		
		Cell cell = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cell = (Cell)objList.get(0);
			ret = cell.getAddr();
		}
		
		return ret;
	}
}
