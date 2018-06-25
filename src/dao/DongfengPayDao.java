package dao;

import model.DongfengPay;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class DongfengPayDao extends BaseDao{
	public DongfengPayDao() {
		init();
	}
	
	public int pay(DongfengPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where orderid='%s'", baseTableName,obj.getOrderid());
		int count = getRecordCount(sql);
	
		DebuUtil.log("东丰计费 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

}
