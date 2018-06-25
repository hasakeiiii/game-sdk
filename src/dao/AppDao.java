package dao;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.Request;

import model.AliPay;
import model.App;
import model.CardPay;
import util.ConstValue;
import util.DebuUtil;
import util.StringUtil;
import azul.BaseDao;
import azul.CacheDao;

public class AppDao extends CacheDao{
	public AppDao() {
		init();
	}
	public void test()
	{
		App obj = new App();
		
		obj.setNo("001");
		//obj.setCpNo("002");
		obj.setName("game1");
		add(obj);
	
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	
	public App getAppRecord(String app_no)
	{
		App obj = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,app_no);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			obj = (App)objList.get(0);
		}
		return obj;
	}
	
/*	public App getAppCp(String cp_no)
	{
		App obj = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where cp_no='%s'", baseTableName,cp_no);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			obj = (App)objList.get(0);
		}
		return obj;
	}
	*/
	public static boolean isStandAloneGame(String app_no)
	{
		boolean ret = false;
		AppDao appDao = new AppDao();
		App app = appDao.getAppRecord(app_no);//
		ret = App.isStandAloneGame(app);
		return ret;
	}
	
	
	
	public int addApp(App obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
		DebuUtil.log("应用表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	public List<ArrayList> getAppCp(String cpNo){
		String sql = "SELECT no,cpcount,cpcpcount from app where  cp_no = '%s' ";
		sql = String.format(sql, cpNo);
		
		List<ArrayList> list = getObjectList(sql);
		
		return list;
	}
	public List<ArrayList> getAppNameAndNoListByType(String gameType){
		String sql = "SELECT name,no from app where game_type = '%s'";
		sql = String.format(sql, gameType);
		
		List<ArrayList> list = getObjectList(sql);
		
		return list;
	}
	public List<ArrayList> getAppNameAndNoListByType(String gameType,String cpNo){
		String sql = "SELECT name,no from app where game_type = '%s' and cp_no = '%s' ";
		sql = String.format(sql, gameType , cpNo);
		
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
