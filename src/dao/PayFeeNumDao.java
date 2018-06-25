package dao;

import java.util.ArrayList;

import model.AppPay;
import model.PayFeeNum;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class PayFeeNumDao extends BaseDao{
	public PayFeeNumDao() {
		init();
	}
	
	public int addPayFeeNum(PayFeeNum obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and date ='%s' ", baseTableName,obj.getPacketId(),obj.getDate());
		int count = getRecordCount(sql);
	
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

	public int getPerNum(String packet_id,String date){
		
		int ret = 0;
		PayFeeNum payFeeNum = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and date ='%s' ", baseTableName,packet_id,date);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			payFeeNum = (PayFeeNum)objList.get(0);
			ret = payFeeNum.getPayPer();
		}
		
		return ret;
	}
	
	public int getFeeNum(String packet_id,String date){
		
		int ret = 0;
		PayFeeNum payFeeNum = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and date ='%s' ", baseTableName,packet_id,date);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			payFeeNum = (PayFeeNum)objList.get(0);
			ret = payFeeNum.getPayNum();
		}
		
		return ret;
	}
}
