package dao;

import model.Open189;
import model.TenPay;
import model.UnionPay;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class Open189Dao extends BaseDao{
	public Open189Dao() {
		init();
	}
	
	public int pay(Open189 obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where order_id='%s'", baseTableName,obj.getOrderId());
		int count = getRecordCount(sql);
	
		DebuUtil.log("天翼空间付费表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

}
