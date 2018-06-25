package dao;

import model.Box;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class BoxDao extends BaseDao{
	public BoxDao() {
		init();
	}
	
	public int pay(Box obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where cpparam='%s'", baseTableName,obj.getCpparam());
		int count = getRecordCount(sql);
	
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

}
