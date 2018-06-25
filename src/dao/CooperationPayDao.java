package dao;

import java.util.ArrayList;
import java.util.List;

import model.CooperationFee;
import model.CooperationPay;
import model.WhiteList;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;

public class CooperationPayDao extends CacheDao{
	public CooperationPayDao() {
		init();
	}
	
	
	public CooperationPay getRecord(String packet_id,String pay_id)
	{
		CooperationPay cooperationPay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and pay_id='%s' ", baseTableName,packet_id,pay_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cooperationPay = (CooperationPay)objList.get(0);
		}
		return cooperationPay;
	}
	public int addCooperationPay(CooperationPay obj){
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and pay_id = '%s' ", baseTableName,obj.getPacketId(),obj.getPayId());
		int count = getRecordCount(sql);
	
		DebuUtil.log("remind表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}

	public List<CooperationPay> getAllCooperationPay(){
		String sql = "SELECT * FROM cooperation_pay ";
		List<CooperationPay> list = getList(sql);
		return list;
	}
}
