package dao;

import java.util.ArrayList;
import java.util.List;

import model.AppPay;
import model.PayCompany;
import model.PayTypeCount;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;

public class PayCompanyDao extends CacheDao{

	public PayCompanyDao() {
		init();
	}
	
	public PayCompany getRecord(String no)
	{
		PayCompany payCompany = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,no);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			payCompany = (PayCompany)objList.get(0);
		}
		return payCompany;
	}
		
	public int addPayCompany(PayCompany obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("计费公司 表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	

	public List<PayCompany> getAllPayCompany(){
		String sql = "SELECT * FROM pay_company";
		List<PayCompany> list = getList(sql);
		return list;
	}
	
	public String EditPayCompany(PayCompany payCompany)
	{
		return edit(payCompany);
	}
}
