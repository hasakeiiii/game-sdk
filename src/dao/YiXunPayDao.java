package dao;

import model.YiXunPay;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class YiXunPayDao extends BaseDao{
	public YiXunPayDao() {
		init();
	}
	
	public int pay(YiXunPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from yi_xun_pay where order_id='%s'",obj.getOrderId());
		int count = getRecordCount(sql);
	
		DebuUtil.log("北京易迅付费表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

}
