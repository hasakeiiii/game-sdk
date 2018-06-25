package dao;

import model.PowerPay;
import model.WebGame;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class PowerPayDao extends BaseDao{
	public PowerPayDao() {
		init();
	}
	
	public int pay(PowerPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where orderid='%s'", baseTableName,obj.getOrderid());
		int count = getRecordCount(sql);
	
		DebuUtil.log("动力计费 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

}
