package dao;

import java.util.ArrayList;
import java.util.Date;

import model.Cooperation;
import model.CooperationFee;
import model.CooperationMoney;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;

public class CooperationMoneyDao extends CacheDao{
	public CooperationMoneyDao() {
		init();
	}
	
	
	public CooperationMoney getRecord(String packet_id)
	{
		CooperationMoney cooperationMoney = null;
		ArrayList<Object> objList;
		String sql;
		sql = "select * from cooperation_money where packet_id like '%"+packet_id+"%' ";
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cooperationMoney = (CooperationMoney)objList.get(0);
		}
		return cooperationMoney;
	}
	public int addCooperationMoney(CooperationMoney obj){
		int ret = ConstValue.Fail;
		String sql;
		sql = "select * from cooperation_money where packet_id like '%"+obj.packetId+"%' ";
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
	public String addItem(CooperationMoney cooperationMoney)
	{
		CooperationMoney coop = getRecord(cooperationMoney.packetId);
		String ret = "-1";
		if(coop == null)
		{
			add(cooperationMoney);
			ret = "1";
		}
		return ret;
	}
	public String EditItem(CooperationMoney cooperationMoney)
	{
		/*String game_id = cooperation.app_no;
		String packet_id = cooperation.packet_id;
		String datestr;
		String channelType = cooperation.settle_type;
		String dispaytype="";
		
		Date now=new java.util.Date();
		now=util.DateUtil.addDate("dd",-1,now);
		datestr = util.DateUtil.getDate(now);*/
		
		
		//Gamedata.get_GamedataList(cooperation.app_no,cooperation.packet_id,cooperation.settle_type,"",util.DateUtil.getDate(now),util.DateUtil.getDate(now));;
		return edit(cooperationMoney);
	}
	public int deleteCooperationMoney(String id){
		int ret = ConstValue.Fail;
		String sql = "id = "+id;
		   delete(sql);
		   ret = ConstValue.OK;
		   DebuUtil.log("删除成功");
		return ret;
	}

}
