package model;

import java.util.ArrayList;

import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import dao.ChannelDataDao;
import dao.CooperationDao;
import dao.FeeDataDao;
import dao.ProvinceDataDao;

public class FeeDataList {

	static ArrayList<Object> dataList = new ArrayList<Object>();
	
	synchronized public static FeeData getFeeData(String packet_id,String date,String feename)
	{
		FeeData retfeeData = null;
		for(int i=0;i<dataList.size();i++)
		{
			FeeData feeData = (FeeData)dataList.get(i);
			if(feeData != null)
			{
				
				if(feeData.packet_id.equals(packet_id) && feeData.date.equals(date) && feeData.fee_name.equals(feename))
				{
					retfeeData = feeData;
					break;
				}
			}
		}
		
		if(retfeeData == null)
		{
			FeeDataDao feeDataDao = new FeeDataDao();
			if(ConstValue.DEBUG == 1)
			{
				 DebuUtil.log("FeeData 为空");
			}
			retfeeData = feeDataDao.getRecord(packet_id,date,feename);
			if(retfeeData == null)
			{
				retfeeData = feeDataDao.createRecord(packet_id,date,feename);
				feeDataDao.AddRecord(retfeeData);
			    retfeeData = feeDataDao.getRecord(packet_id,date,feename);
			}
			dataList.add(retfeeData);
		}
		
		return retfeeData;
	}
	
	
}
