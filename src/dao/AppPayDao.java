package dao;

import java.util.ArrayList;
import java.util.List;

import model.AppPay;
import model.WhiteList;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;

public class AppPayDao extends CacheDao{

	public AppPayDao() {
		init();
	}
	
	public AppPay getRecord(String no)
	{
		AppPay appPay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,no);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			appPay = (AppPay)objList.get(0);
		}
		return appPay;
	}
	
	public int addAppPay(AppPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("计费游戏 表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	
/*	public  int getWhiteList(String imei)//判断是否在白名单内，0不在，1在。
	{
		int ret = 0;
		String sql;
		sql = String.format("select * from %s where imei='%s'",baseTableName,imei);
		int count = getRecordCount(sql);
	
		DebuUtil.log("是否在白名单内="+count);
		if(count > 0)
		{
		ret = 1;
		}
		return ret;
	}	
	
	public WhiteList getImeiWhite(String imei)  //暂时没用到
	{
		WhiteList whiteList = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,imei);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			whiteList = (WhiteList)objList.get(0);
		}
		return whiteList;
	}*/

	public List<AppPay> getAllAppPay(){
		String sql = "SELECT * FROM app_pay";
		List<AppPay> list = getList(sql);
		return list;
	}
}
