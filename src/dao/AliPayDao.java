package dao;

import model.AliPay;
import util.ConstValue;
import util.DebuUtil;
import util.StringUtil;
import azul.BaseDao;

public class AliPayDao extends BaseDao{
	public AliPayDao() {
		init();
	}
	public void test()
	{
		AliPay obj = new AliPay();
		
		obj.setPayNo("0124180931-6737");
		obj.setAliPayNo("2014012438936581");
		//obj.setGoodsName("元宝");
		///obj.setGoodsDetails("中泰源公司提供");
		obj.setRet(ConstValue.OK);
		alipay(obj);
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	
	public int alipay(AliPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where pay_no='%s'", baseTableName,obj.getPayNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("支付宝付费表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
}
