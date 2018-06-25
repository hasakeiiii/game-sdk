package dao;

import java.util.ArrayList;

import model.CooperationFee;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;

public class CooperationFeeDao extends CacheDao{
	public CooperationFeeDao() {
		init();
	}
	
	
	public CooperationFee getRecord(String packet_id)
	{
		CooperationFee cooperationFee = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' ", baseTableName,packet_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cooperationFee = (CooperationFee)objList.get(0);
		}
		return cooperationFee;
	}
	public int addCooperationFee(CooperationFee obj){
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where packet_id='%s'", baseTableName,obj.getPacketId());
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
	public boolean remindIsExists(String packetId){
		String sql = "select * from %s where packet_id='%s'";
		sql = String.format(sql, baseTableName,packetId);
		
		int count = getRecordCount(sql);
		
		return count == 1;
	}
}
