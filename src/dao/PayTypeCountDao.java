package dao;

import java.util.ArrayList;
import java.util.List;

import model.AppPay;
import model.PayTypeCount;
import model.Vague;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;

public class PayTypeCountDao extends CacheDao{

	public PayTypeCountDao() {
		init();
	}
	
	public PayTypeCount getRecord(String payType)
	{
		PayTypeCount payTypeCount = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where pay_type='%s'", baseTableName,payType);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			payTypeCount = (PayTypeCount)objList.get(0);
		}
		return payTypeCount;
	}
	
	public int getPaySettleCount(String payType){
		int ret = 100;
		PayTypeCount payTypeCount = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where pay_type='%s'", baseTableName,payType);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			payTypeCount = (PayTypeCount)objList.get(0);
			ret = payTypeCount.getSettleCount();
		}
		
		return ret;
	}
	
	public int addPayTypeCount(PayTypeCount obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("计费类型百分比 表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	public String EditPayTypeCount(PayTypeCount payTypeCount)
	{
		return edit(payTypeCount);
	}
}
