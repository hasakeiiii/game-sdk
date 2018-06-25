package dao;

import java.util.ArrayList;
import java.util.List;







import model.AliPay;
import model.App;
import model.CardPay;
import model.Cooperation;
import model.FeeData;
import model.FeeDataList;
import model.MmPay;
import model.Pay;
import model.ProvinceData;
import model.ProvinceDataList;
import model.ReportFormData;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import azul.BaseDao;
import util.StringUtil;
import model.FeeData;
import azul.CacheDao;

public class FeeDataDao extends CacheDao{
	public FeeDataDao() {
		init();
	}
	

	public FeeData getRecord(String packet_id,String date,String feename)
	{
		FeeData feeData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and date='%s' and fee_name='%s' ", baseTableName,packet_id,date,feename);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			feeData = (FeeData)objList.get(0);
		}
		return feeData;
	}

	public List<FeeData> getFeeDateList(String fromDate,String toDate,String businessNo,String gameNo,String channelNo,String apkNo){
		//StringBuilder sql = new StringBuilder("select * from fee_data where 1 = 1");
		
		StringBuilder sql = new StringBuilder("select id,id as game_fee_timers,id as fee_timers,business_no,channel_no,packet_id,date,game_id,coin_name as fee_name,sum(amount) as amount,count(*) as order_timers from pay where 1 = 1");
		//ArrayList<Object> list = new ArrayList<Object>();
		StringBuilder wheresql = new StringBuilder();
		
		if(!StringUtil.is_nullString(gameNo)){
			wheresql.append(" and game_id = '" + gameNo + "'");
		}
		if(!StringUtil.is_nullString(businessNo)){
			wheresql.append(" and business_no = '" + businessNo + "'");
		}
		if(!StringUtil.is_nullString(channelNo)){
			wheresql.append(" and channel_no = '" + channelNo + "'");
		}
		if(!StringUtil.is_nullString(apkNo)){
			wheresql.append(" and packet_id = '" + apkNo + "'");
		}
		
		if(!StringUtil.is_nullString(fromDate)){
			wheresql.append(" and date >= '" + fromDate + "'");
		}
		
		if(!StringUtil.is_nullString(toDate)){
			wheresql.append(" and date <= '" + toDate + "'");
		}
		
		sql.append(wheresql);
		sql.append(" group by coin_name ");
		
		 List<FeeData> list = getList(sql.toString());
		 for(FeeData feeData:list)
		 {
			 //查询计费次数
			 sql = new StringBuilder("select count(*) as fee_timers,sum(amount) as amount from pay where 1 = 1 ");			 
			 sql.append(wheresql);
			 sql.append(" and coin_name = '" + feeData.fee_name + "'");
			 sql.append(" and state = 1 " );
			 
			 ArrayList<ArrayList> fee_timerslist = getObjectList(sql.toString());
			 System.out.println(sql.toString());
			if (fee_timerslist.size()>0) 
			{
				//自己看的数据
				for(int i = 0;i<fee_timerslist.size();i++)
				{
					ArrayList<Object> fee_timers = fee_timerslist.get(i);
					Object valueObject = fee_timers.get(0);
					int value = 0;
					if(valueObject != null)
					{
						value = Integer.valueOf(valueObject.toString());
					}
					feeData.setFeeTimers(value);	
					
					valueObject = fee_timers.get(1);
					if(valueObject != null)
					{
						value = Integer.valueOf(valueObject.toString());
					}
					else
					{
						value = 0;
					}
					feeData.setAcount(value);
				}
					
			}	
			
			
		 }
		 return list;
	}

		
	
	
	public void AddRecord(FeeData feeData)
	{
		if(ConstValue.DEBUG == 1)
		{
			 DebuUtil.log("添加FeeData");
		}
		this.add(feeData);
	}
	
	public FeeData createRecord(String packet_id,String date,String feename)
	{
		FeeData feeData = null;
		{
			feeData = new FeeData();
			feeData.packet_id = packet_id;
			feeData.date = date;
			feeData.fee_name = feename;
			
			CooperationDao cooDao = new CooperationDao();
			Cooperation cooperation= cooDao.getRecord(packet_id);
			if(cooperation != null)
			{
				feeData.game_id = cooperation.getAppNo();
				feeData.BusinessNo = cooperation.getBusinessNo();
				feeData.channel_no =  cooperation.getChannelNo();
				//provinceData.channel = cooperation.getChannelNo();
			}
			
			
		}
		return feeData;
	}
	
	public void pay(Pay obj,boolean bSDK)
	{
		//DebuUtil.log("paySA obj.packet_id="+obj.packet_id);//
		String packet_id=obj.packet_id;
		String date = DateUtil.getDate();
		FeeData feeData = FeeDataList.getFeeData(packet_id,date,obj.coin_name);
		if(feeData != null)
		{			
			FeeData tempData = new FeeData();
			tempData.clearData();
			tempData.id = feeData.id;
			
			if(obj.state == 1)
			{
				feeData.feeTimersInc(1);
				tempData.fee_timers = feeData.fee_timers;
				if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("计费点付费次数累加="+feeData.fee_timers+" 名字="+obj.coin_name);
				}
				feeData.amountInc(obj.amount);
				tempData.amount = feeData.amount;
				if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("计费点金额累加="+feeData.amount);
				}
			}
			else
			{
				feeData.orderTimersInc(1);
				tempData.order_timers = feeData.order_timers;
				
				if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("计费点点击次数累加="+feeData.order_timers+" 名字="+obj.coin_name);
				}
			}
			
			edit(tempData);
		}
	}
		
}
