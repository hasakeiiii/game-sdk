package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.AppPayData;
import model.AppPayDataList;
import model.Cooperation;
import model.FeeData;
import model.Pay;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import azul.CacheDao;

public class AppPayDataDao extends CacheDao{
	public AppPayDataDao() {
		init();
	}
	

	public AppPayData getRecord(String app_pay_id,String date)
	{
		AppPayData appPayData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from app_pay_data where   app_pay_id='%s' and date = '%s' ",app_pay_id,date);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			appPayData = (AppPayData)objList.get(0);
		}
		return appPayData;
	}


		
	
	
	public void AddRecord(AppPayData appPayData)
	{
		if(ConstValue.DEBUG == 1)
		{
			 DebuUtil.log("添加AppPayData");
		}
		this.add(appPayData);
	}
	
	public AppPayData createRecord(String app_pay_id,String date)
	{
		AppPayData appPayData = null;
		{
			appPayData = new AppPayData();
			appPayData.appPayId = app_pay_id;
			appPayData.date = date;
			
			
		}
		return appPayData;
	}
	
	public void pay(Pay obj,boolean bSDK)
	{
		String date = DateUtil.getDate();
		AppPayData appPayData = AppPayDataList.getAppPayData(obj.app_pay_id,date);
		if(appPayData != null)
		{			
			AppPayData tempData = new AppPayData();
			tempData.clearData();
			tempData.id = appPayData.id;
			
			if(obj.state == 1)
			{
				appPayData.amountInc(obj.amount);
				tempData.amount = appPayData.amount;
				if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("计费点金额累加="+appPayData.amount);
				}
			}
			
			edit(tempData);
		}
	}
	
	public int getAppPayMoney(String appPayId,String date)//计算该计费游戏金额
	{
		int ret =0;
		StringBuilder sql= new StringBuilder("select sum(amount) from app_pay_data,mm_pay_data where 1=1  ");
		sql.append(" and date = '" + date + "' and app_pay_data.app_pay_id = mm_pay_data.pay_id and  app_pay_data.app_pay_id in (select pay_id from mm_pay_data where app_pay_id = '" + appPayId + "') ");
		
		ret = getCount(sql.toString());
		
		return ret;
	}
	
	public  int getPayCompanyMoney(String no,String date)//计算该计费公司金额
	{
		int ret =0;
		StringBuilder sql= new StringBuilder("select no from app_pay where 1=1 and company =  '" + no + "'");
		List<ArrayList> appPayList = getObjectList(sql.toString());
		for(int i = 0;i<appPayList.size();i++){
			String appPayId = appPayList.get(i).get(0).toString();
			ret += getAppPayMoney(appPayId, date);
		}
		return ret;
	}
	public List<ArrayList> getTaskByAppPay(){
		String dateStr = DateUtil.getDate(new Date());
		String sql = "select app_pay_data.date,app_pay.`name`,sum(app_pay_data.amount),app_pay.company from app_pay_data,mm_pay_data,app_pay  where app_pay_data.date = '" + dateStr + "' and  app_pay_data.app_pay_id = mm_pay_data.pay_id and mm_pay_data.app_pay_id = app_pay.`no`  group by app_pay.`no` ";
		return getObjectList(sql);
	}
}
