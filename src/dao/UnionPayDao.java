package dao;

import model.TenPay;
import model.UnionPay;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class UnionPayDao extends BaseDao{
	public UnionPayDao() {
		init();
	}
	
	public int pay(UnionPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where pay_no='%s'", baseTableName,obj.getPayNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("银联付费表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

}
