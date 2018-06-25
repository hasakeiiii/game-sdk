package dao;

import java.util.ArrayList;

import model.CooperationRemind;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class CooperationRemindDao extends BaseDao {
	public CooperationRemindDao() {
		init();
	}

	public int addCooperationRemind(CooperationRemind obj){
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

	public CooperationRemind getRecord(String packet_id)
	{
		CooperationRemind cooperationRemind = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s'", baseTableName,packet_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cooperationRemind = (CooperationRemind)objList.get(0);
		}
		return cooperationRemind;
	}
	
	public boolean remindIsExists(String packetId){
		String sql = "select * from %s where packet_id='%s'";
		sql = String.format(sql, baseTableName,packetId);
		
		int count = getRecordCount(sql);
		
		return count == 1;
	}

	public void editRemindByPacketId(CooperationRemind remind){
		StringBuilder sql = new StringBuilder("update " + baseTableName + " set ");
		sql.append(" req_num=" + remind.getReqNum());
		sql.append(", mm_pay=" + remind.getMmPay());
		sql.append(", operator_first=" + remind.getOperatorFirst());
		sql.append(", operator_second=" + remind.getOperatorSecond());
		sql.append(", proportion=" + remind.getProportion());
		sql.append(", pay_proportion=" + remind.getPayProportion());
		sql.append(", mm_day_pay_province='" + remind.getMmDayPayProvince() + "'");
		sql.append(", province='" + remind.getProvince() + "'");
		sql.append(", mm_day_channel_province='" + remind.getMmDayChannelProvince() + "'");
		sql.append(", channel_province='" + remind.getChannelProvince() + "'");
		sql.append(" where packet_id='" + remind.getPacketId() + "'");
		
		executeUpdate(sql.toString());
		
	}
}
