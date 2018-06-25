package dao;

import model.WxPay;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class WxPayDao extends BaseDao{
	public WxPayDao() {
		init();
	}
	
	public int wxpay(WxPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where pay_no='%s'", baseTableName,obj.getPayNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("微信支付费表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("微信支付添加成功");
		}
		return ret;
	}
	
}
