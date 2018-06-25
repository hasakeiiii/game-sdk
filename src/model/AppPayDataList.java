package model;

import java.util.ArrayList;

import util.StringUtil;
import dao.AppPayDataDao;

public class AppPayDataList {

	static ArrayList<Object> dataList = new ArrayList<Object>();
	
	synchronized public static AppPayData getAppPayData(String app_pay_id,String date)
	{
		AppPayData retAppPayData = null;
		if(StringUtil.is_nullString(app_pay_id))
		{
			app_pay_id = "";
		}
		for(int i=0;i<dataList.size();i++)
		{
			AppPayData appPayData = (AppPayData)dataList.get(i);
			if(appPayData != null)
			{
				
				if(appPayData.appPayId.equals(app_pay_id)&&appPayData.date.equals(date))
				{
					retAppPayData = appPayData;
					break;
				}
			}
		}
		
		if(retAppPayData == null)
		{
			AppPayDataDao appPayDataDao = new AppPayDataDao();
			
			retAppPayData = appPayDataDao.getRecord(app_pay_id,date);
			if(retAppPayData == null)
			{
				retAppPayData = appPayDataDao.createRecord(app_pay_id,date);
				appPayDataDao.AddRecord(retAppPayData);
				retAppPayData = appPayDataDao.getRecord(app_pay_id,date);
			}
			dataList.add(retAppPayData);
		}
		
		return retAppPayData;
	}
	
	
}
