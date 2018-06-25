package dao;

import java.util.ArrayList;

import model.App;
import model.CardPay;
import model.Pay;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class CardPayDao extends BaseDao{
	public CardPayDao() {
		init();
	}
	public void test()
	{
		CardPay obj = new CardPay();
		
		obj.setNo("415245154414554");
		obj.setPass("667057555");
		obj.setAmount(50);
		obj.setRet(ConstValue.OK);
		addCardPay(obj);
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	
	public CardPay getPayRecordByPayNO(String pay_no)
	{
		CardPay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where pay_no='%s'", baseTableName,pay_no);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			pay = (CardPay)objList.get(0);
		}
		return pay;
	}
	
	public CardPay getPayRecord(String card_no)
	{
		CardPay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,card_no);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			pay = (CardPay)objList.get(0);
		}
		return pay;
	}
	
	public int addCardPay(CardPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where pay_no='%s'", baseTableName,obj.pay_no);
		int count = getRecordCount(sql);
	
		DebuUtil.log("点卡付费表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

}
