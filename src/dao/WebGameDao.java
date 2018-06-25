package dao;

import model.WebGame;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class WebGameDao extends BaseDao{
	public WebGameDao() {
		init();
	}
	
	public int pay(WebGame obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where exdata='%s'", baseTableName,obj.getExdata());
		int count = getRecordCount(sql);
	
		DebuUtil.log("Pc页游付费表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

}
