package dao;

import java.util.ArrayList;
import java.util.List;


import model.Gamepack;
import azul.CacheDao;

public class GamepackDao extends CacheDao{
	public GamepackDao() {
		init();
	}
	public void test()
	{
		Gamepack obj = new Gamepack();
		
		obj.setGameId("001");
		//obj.setCpNo("002");
		obj.setFileName("game1");
		add(obj);
	
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	public Gamepack getApk(int id)
	{
		Gamepack obj = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from gamepack where id=%d", id);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			obj = (Gamepack)objList.get(0);
		}
		return obj;
	}
	public Gamepack getAppRecord(String app_no)
	{
		Gamepack obj = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,app_no);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			obj = (Gamepack)objList.get(0);
		}
		return obj;
	}
	
	public boolean checkByPacketNo(String packetNo) {
		boolean ret = false;
		Gamepack gamePack = null;
		gamePack = getRecordByPackNo(packetNo);
		if(gamePack!=null)
			ret = true;
		return ret;
	}

	public Gamepack getRecordByPackNo(String packetNo){
		Gamepack gamePack = null;
		String sql = String.format(
				"select * from %s where packet_no='%s'",
				baseTableName, packetNo);
		List list = getList(sql);
		if (list.size() > 0) {
			gamePack = (Gamepack) list.get(0);
		}
		
		return gamePack;
	}
	
	public List<ArrayList> getAppNameAndNoListByType(String gameType){
		String sql = "SELECT name,no from app where game_type = '%s'";
		sql = String.format(sql, gameType);
		
		List<ArrayList> list = getObjectList(sql);
		
		return list;
	}
	public List<ArrayList> getAppNameAndNoListByCompany(String company){
		String sql = "SELECT name,no from app where mm_company = '%s'";
		sql = String.format(sql, company);
		
		List<ArrayList> list = getObjectList(sql);
		
		return list;
	}
}
