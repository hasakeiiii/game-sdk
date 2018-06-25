package dao;

import java.util.ArrayList;

import util.DebuUtil;
import model.Cooperation;
import model.LinkManage;
import azul.CacheDao;

public class LinkManageDao extends CacheDao{
	public LinkManageDao() {
		init();
	}
	
	public LinkManage getRecord(String packetId)
	{
		LinkManage lm = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s'", baseTableName,packetId);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			lm = (LinkManage)objList.get(0);
		}
		return lm;
	}
	public String addItem(LinkManage linkmanage)
	{
		LinkManage lm = getRecord(linkmanage.packet_id);
		String ret = "-1";
		if(lm == null)
		{
			add(linkmanage);
			ret = "1";
		}
		return ret;
	}
	
	public String EditItem(LinkManage linkmanage)
	{
		LinkManage lm = getRecord(linkmanage.packet_id);
		String ret = "-1";
		if(lm != null)
		{
			edit(linkmanage);
			ret = "1";
		}
		return ret;
	}
	
	//获取cdn下载链接
	public String[] getCndUrl(String gameId){
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where game_id='%s' and state='是'",baseTableName,gameId);
		objList = getList(sql);
		LinkManage lm = new LinkManage();
		String[] cndUrl = new String[objList.size()];
		for(int i = 0;i < objList.size(); i++){
			lm = (LinkManage) objList.get(i);
			cndUrl[i] = lm.getCdnUrl();
		}
		return cndUrl;
	}
	//获取二级页面链接
	public String[] getWebUrl(String gameId){
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where game_id='%s' and state='是'",baseTableName,gameId);
		objList = getList(sql);
		LinkManage lm = new LinkManage();
		String[] webUrl = new String[objList.size()];
		for(int i = 0;i < objList.size(); i++){
			lm = (LinkManage) objList.get(i);
			webUrl[i] = lm.getWebUrl();
		}
		return webUrl;
	}
	
	public ArrayList<Object> getRecord()
	{
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s group by game_id ",baseTableName);
		objList = getList(sql);
		return objList;
	}
	
	//数据库link_manage表 游戏个数
	public int getGameSum(){
		String sql;
		int getGameSum;
		sql = String.format("select count(distinct game_id) from link_manage");
		getGameSum = getCount(sql);
		return getGameSum;
	}
	
	public String[] getGameId(){
		LinkManage lm = new LinkManage();
		int sum = this.getGameSum();
		String[] gameIdArray = new String[sum]; 
		
		for(int i = 0; i < sum; i++){
			lm = (LinkManage) this.getRecord().get(i);
			gameIdArray[i] = lm.getGameId();
		}
		return gameIdArray;
	}
	//获取游戏名称
	public String getGameName(String gameId){
		Object obj;
		String sql;
		sql = String.format("select name from app where no='%s'",gameId);
		obj = getValue(sql);
		return obj.toString();
	}
	
	
	
	//邮件中,通过游戏获取游戏名称
	public String getGameCdnId(String cdn){
		Object obj;
		String sql;
		sql = String.format("select game_id from link_manage where cdn_url='%s'",cdn);
		obj = getValue(sql);
		return obj.toString();
	}
	//邮件中,通过游戏获取游戏名称
	public String getGameWebId(String web){
		Object obj;
		String sql;
		sql = String.format("select game_id from link_manage where web_url='%s'",web);
		obj = getValue(sql);
		return obj.toString();
	}
}
