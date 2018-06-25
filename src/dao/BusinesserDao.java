package dao;

import java.util.ArrayList;
import java.util.List;

import model.App;
import model.Businesser;
import model.Channel;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;
import azul.CacheDao;

public class BusinesserDao extends CacheDao{

	public BusinesserDao() {
		init();
	}
	
	public  String getBusinesserName(String no)
	{
		String businesserName = "";
		Businesser businesser = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,no);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			businesser = (Businesser)objList.get(0);
			businesserName = businesser.name;
		}
		return businesserName;
	}
	
	public Businesser getRecord(String no)
	{
		Businesser cooperation = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,no);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cooperation = (Businesser)objList.get(0);
		}
		return cooperation;
	}
	
	public Businesser getRecord(int login_user)
	{
		Businesser cooperation = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where login_user=%d ", baseTableName,login_user);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cooperation = (Businesser)objList.get(0);
		}
		return cooperation;
	}
	
	public int addBusinesser(Businesser obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("商务 表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	
	/**
	 * 获取所有商务
	 * @return
	 */
	public List<Businesser> getAllBusinessers(){
		String sql = "SELECT * FROM businesser";
		List<Businesser> list = getList(sql);
		return list;
	}
}
