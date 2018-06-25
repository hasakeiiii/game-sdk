package dao;
import java.util.ArrayList;

import model.Pay;
import model.SysParam;
import azul.CacheDao;
public class SysParamDao extends CacheDao{
	static SysParam m_sysParam = null;
public SysParamDao() {
	init();
}

public String  addRecord(SysParam sysParam)
{
	String ret = this.add(sysParam);
	m_sysParam = getRecordFromTable();
	return ret;
}

public String editRecord(SysParam sysParam)
{
	String ret =this.edit(sysParam);
	m_sysParam = getRecordFromTable();
	return ret;
}

public SysParam getRecord()
{
	if(m_sysParam == null)
	{
		m_sysParam = getRecordFromTable();
	}
	return m_sysParam;
}

public SysParam getRecordFromTable()
{
	SysParam sysParam = null;
	ArrayList<Object> objList;
	String sql;
	sql = String.format("select * from %s ", baseTableName);
	objList = getList(sql);
	if(objList.size() > 0)
	{
		sysParam = (SysParam)objList.get(0);
	}
	return sysParam;
}

}