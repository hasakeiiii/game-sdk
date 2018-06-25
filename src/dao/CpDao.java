package dao;

import model.Channel;
import model.Cp;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class CpDao extends BaseDao{
	public CpDao() {
		init();
	}
	public  void test()
	{
		Cp obj = new Cp();
		
		obj.setNo("001");
		obj.setName("中泰源");
		obj.setAddr("shenzhen");
		obj.setContact("lsl");
		addCp(obj);
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	
	public int addCp(Cp obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("CP表结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
}
