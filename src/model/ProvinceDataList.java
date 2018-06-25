package model;

import java.util.ArrayList;

import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import dao.ChannelDataDao;
import dao.CooperationDao;
import dao.ProvinceDataDao;

public class ProvinceDataList {

	static ArrayList<Object> dataList = new ArrayList<Object>();
	
	synchronized public static ProvinceData getProvinceData(String packet_id,String date,String province)
	{
		ProvinceData retprovinceData = null;
		if(StringUtil.is_nullString(province))
		{
			province = "";
		}
		for(int i=0;i<dataList.size();i++)
		{
			ProvinceData provinceData = (ProvinceData)dataList.get(i);
			if(provinceData != null)
			{
				
				if(provinceData.packet_id.equalsIgnoreCase(packet_id) && provinceData.date.equals(date) && provinceData.province.equals(province))
				{
					retprovinceData = provinceData;
					break;
				}
			}
		}
		
		if(retprovinceData == null)
		{
			ProvinceDataDao provinceDataDao = new ProvinceDataDao();
			
			retprovinceData = provinceDataDao.getRecord(packet_id,date,province);
			if(retprovinceData == null)
			{
			   retprovinceData = provinceDataDao.createRecord(packet_id,date,province);
			   provinceDataDao.AddRecord(retprovinceData);
			   retprovinceData = provinceDataDao.getRecord(packet_id,date,province);
			}
			dataList.add(retprovinceData);
		}
		
		return retprovinceData;
	}
	
	
}
