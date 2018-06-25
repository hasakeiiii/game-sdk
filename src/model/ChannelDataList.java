package model;

import java.util.ArrayList;

import util.DateUtil;
import util.DebuUtil;
import dao.ChannelDataDao;
import dao.CooperationDao;

public class ChannelDataList {

	static ArrayList<Object> dataList = new ArrayList<Object>();
	

	synchronized public static ChannelData getChannelData2(String packet_id,String date)
	{
		ChannelData retchannelData = null;
		for(int i=0;i<dataList.size();i++)
		{
			ChannelData channelData = (ChannelData)dataList.get(i);
			if(channelData.packet_id.equals(packet_id) && channelData.date.equals(date))
			{
				retchannelData = channelData;
			}
		}
		
		if(retchannelData == null)
		{
			ChannelDataDao channelDataDao = new ChannelDataDao();
			
			retchannelData = channelDataDao.getRecord(packet_id,date);
			if(retchannelData != null)
			{
				dataList.add(retchannelData);
			}
			
		}
		
		return retchannelData;
	}
	/**
	 * 获取当日channeldata表的记录，如果访日未产生数据，则生成一个记录返回
	 * @param packet_id
	 * @param date
	 * @return
	 */
	synchronized public static ChannelData getChannelData(String packet_id,String date)
	{
		ChannelData retchannelData = null;
		for(int i=0;i<dataList.size();i++)
		{
			ChannelData channelData = (ChannelData)dataList.get(i);
			if(channelData != null)
			{
				
				if(channelData.packet_id.equalsIgnoreCase(packet_id) && channelData.date.equals(date))
				{
					retchannelData = channelData;
				}
			}
		}
		
		if(retchannelData == null)
		{
			ChannelDataDao channelDataDao = new ChannelDataDao();
			
			retchannelData = channelDataDao.getRecord(packet_id,date);
			if(retchannelData == null)
			{
			   retchannelData = channelDataDao.createRecord(packet_id,date);
			   channelDataDao.AddRecord(retchannelData);
			   retchannelData = channelDataDao.getRecord(packet_id,date);
			}
			dataList.add(retchannelData);
		}
		
		return retchannelData;
	}
	
	
}
