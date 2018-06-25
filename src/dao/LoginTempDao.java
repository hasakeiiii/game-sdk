package dao;

import azul.BaseDao;

public class LoginTempDao extends BaseDao{
	public LoginTempDao() {
		init();
	}
	
	public int getCount(String packet_id,String date)
	{
		int ret = 0;
		
		String sql;
		sql = String.format("select count(DISTINCT(login.username)) from %s where packet_id='%s' and date='%s' ", baseTableName,packet_id,date);
		ret = getCount(sql);
		
		return ret;
	}

}
