package dao;

import java.util.ArrayList;

import model.App;
import model.Channel;
import model.Operater;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;

public class OperaterDao extends CacheDao{
	public OperaterDao() {
		init();
	}
	
	public void test()
	{
		Operater obj = new Operater();
		
		obj.setNo("001");
		//obj.setCpNo("002");
		obj.setName("game1");
		add(obj);
	
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	
	public Operater getRecord(String no)
	{
		Operater cooperation = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,no);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cooperation = (Operater)objList.get(0);
		}
		return cooperation;
	}
	
	
	public int addOperater(Operater obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("渠道表结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	
}
