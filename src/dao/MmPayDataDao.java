package dao;

import java.util.ArrayList;
import java.util.List;

import model.AppPay;
import model.MmPayData;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class MmPayDataDao extends BaseDao{
	
	public MmPayDataDao(){
		init();
		
	}
	
	public int addMmPayData(MmPayData obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where pay_id='%s'", baseTableName,obj.getPayId());
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
	
	public Object getByPayId(String payId) {
		Object obj ;
		String sql;
		sql = String.format("select * from %s where pay_id='%s'", baseModelName,payId);
		obj = getObject(sql);
				
		return obj;
	}
	
	
	public MmPayData getRecord(String pay_id)
	{
		MmPayData mmPayData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where pay_id='%s'", baseTableName,pay_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			mmPayData = (MmPayData)objList.get(0);
		}
		return mmPayData;
	}
	public MmPayData getQiangRecord(String pay_type)
	{
		MmPayData mmPayData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where pay_type='%s'", baseTableName,pay_type);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			mmPayData = (MmPayData)objList.get(0);
		}
		return mmPayData;
	}

	public MmPayData getRecordByLike(String pay_id)
	{
		MmPayData mmPayData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where pay_id like '%s%s'", baseTableName,pay_id,"%");
		objList = getList(sql);
		
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			mmPayData = (MmPayData)objList.get(0);
		}
		return mmPayData;
	}
	public String getPayType(String pay_id){
		
		String type = "";
		MmPayData mmPayData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where pay_id = '%s'", baseTableName,pay_id);
		objList = getList(sql);
		
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			mmPayData = (MmPayData)objList.get(0);
			type = mmPayData.getType();
		}
		return type;
	}
	public int getMoneyCount(String pay_id){
		
		int MoneyCount = 100;
		MmPayData mmPayData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where pay_id = '%s'", baseTableName,pay_id);
		objList = getList(sql);
		
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			mmPayData = (MmPayData)objList.get(0);
			MoneyCount = 100 - mmPayData.getMoneycount();
		}
		return MoneyCount;
	}
	public List<ArrayList> getDataByAppName(String appName){
		String sql = "select channel from mm_pay_data where pay_name = '" + appName + "'";
		List<ArrayList> list = getObjectList(sql);
		return list;
	}
	
	public List<ArrayList> getDataByAppPay(String appPayNo){
		String sql = "select name,no from mm_pay_data where app_pay_id ='" + appPayNo + "'";
				
		return getObjectList(sql);
	}
	public boolean getMmPayDataByAppPay(String appPayNo,int moneycount,int priority){
		
		boolean ret = false;
		MmPayDataDao mmPayDataDao = new MmPayDataDao();
		String sql = "SELECT * FROM mm_pay_data where app_pay_id ='" + appPayNo + "'";
		List<Object> list = getList(sql);
		for(int i = 0;i <list.size();i++){
			MmPayData mmPayData = (MmPayData)list.get(i);
			mmPayData.setMoneycount(moneycount);
			mmPayData.setPriority(priority);
			mmPayDataDao.edit(mmPayData);
			ret = true;
		}
		
		return ret;
		
	}
}
