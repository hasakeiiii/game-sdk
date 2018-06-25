package dao;

import model.PayData;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class PayDataDao extends BaseDao{
	public PayDataDao() {
		init();
	}
	
	public int add(PayData obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
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
