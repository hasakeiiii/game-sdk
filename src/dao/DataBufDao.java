package dao;

import java.util.ArrayList;

import model.App;
import model.DataBuf;
import util.DebuUtil;
import azul.BaseDao;

public class DataBufDao extends BaseDao{
	public DataBufDao() {
		init();
	}
	
	public DataBuf getDataBufRecord(String sqlstr)
	{
		DataBuf obj = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where sqlstr='%s'", baseTableName,sqlstr);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			obj = (DataBuf)objList.get(0);
		}
		return obj;
	}
	
	public int GetCount(String sql,String funname,String date,String packet_id)
	{
		int ret = 0;
		String sign = util.Rsa.getMD5(sql);
		DataBuf buf = getDataBufRecord(sign);
		
		if(buf != null)
		{
			ret = buf.getCount();
		}
		else
		{
			ret = getRecordCount(sql);
			buf = new DataBuf();
			buf.setSqlstr(sign);
			buf.setCount(ret);
			buf.funname= funname;
			buf.date = date;
			buf.packet_id = packet_id;
			this.add(buf);
		}
		
		return ret;
	}

	public int GetCount2(String sql,String funname,String date,String packet_id)
	{
		int ret = 0;
		String sign = util.Rsa.getMD5(sql);
		DataBuf buf = getDataBufRecord(sign);
		
		if(buf != null)
		{
			ret = buf.getCount();
		}
		else
		{
			ret = getCount(sql);
			buf = new DataBuf();
			buf.setSqlstr(sign);
			buf.setCount(ret);
			buf.funname= funname;
			buf.date = date;
			buf.packet_id = packet_id;
			this.add(buf);
		}
		
		return ret;
	}
	
}
