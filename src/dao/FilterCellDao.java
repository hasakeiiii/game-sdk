package dao;

import java.util.ArrayList;

import model.Cell;
import model.Cooperation;
import model.FilterCell;
import azul.BaseDao;

public class FilterCellDao extends BaseDao{
	public FilterCellDao() {
		init();
	}

	public String getLBS(int cell_id,int lac)
	{
		String sql;
		String ret = "";
		sql = String.format("select * from %s where cell_id=%d and lac=%d "
				,baseTableName,cell_id,lac);
		
		FilterCell cell = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cell = (FilterCell)objList.get(0);
			ret = cell.getAddr();
		}
		
		return ret;
	}
	public boolean getFilter(int cell_id,int lac)
	{
		String sql;
		boolean ret = false;
		sql = String.format("select * from %s where cell_id=%d and lac=%d "
				,baseTableName,cell_id,lac);
		
		FilterCell cell = null;
		ArrayList<Object> objList;
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cell = (FilterCell)objList.get(0);
			ret = true;
		}
		
		return ret;
	}
}
