package dao;

import model.AliPay;
import model.TenPay;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class TenPayDao extends BaseDao{
	public TenPayDao() {
		init();
	}
	
	public int pay(TenPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where pay_no='%s'", baseTableName,obj.getPayNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("财付通付费表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	
}
