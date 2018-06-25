package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import model.ChannelData;
import model.ChannelDataList;
import model.DevicePay;
import model.ProvinceData;
import model.ProvinceDataList;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import azul.BaseDao;
/**
 * 
 * @author mzyw_linilq
 * @version 1.0
 * 操作设备数据信息表 device_info
 *
 */
public class DevicePayDao extends BaseDao {

	//static int ExceedAmount = 3000;
	
	public DevicePayDao (){
		init();
	}
	
	public DevicePay getRecord(String imei)
	{
		DevicePay devicePay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where imei='%s' ", baseTableName,imei);//where packet_id='217150001'
		objList = getList(sql);
		if(objList.size() > 0)
		{
			devicePay = (DevicePay)objList.get(0);
		}
		//DebuUtil.log(sql);
		
		return devicePay;
	}
	
	public boolean isExceed(String imei,int amount,int exceedAmount,String date,int exceed_month_pay,int exceed_channel_pay,String packetID,String province,Map provinceUserExceedList,Map channelProvinceExceedList)
	{
		boolean ret = false;
		DevicePay devicePay = getRecord(imei);
		int payAmount = amount;
		int payMonthAmount = 0;
		int payChannelAmount = 0;
		int payProvinceAmount = 0;
		Date curdate = new Date();
		int curMonth = curdate.getMonth();
		ChannelData channelData = ChannelDataList.getChannelData(packetID,DateUtil.getDate());
		
	//	String UserProvince = null;
		int exceed_province_user_pay = 0;
		int exceed_province_pay = 0;
		
		if(channelProvinceExceedList != null)
		{
			String provinceExceedAmountstr = (String)channelProvinceExceedList.get(province);
			if(!StringUtil.is_nullString(provinceExceedAmountstr))
			{
				exceed_province_pay = Integer.valueOf(provinceExceedAmountstr)*100;
			}
			
		}

		//取当前省份计费金额
		ProvinceData provinceData = null;
		if(!StringUtil.is_nullString(province))
		{
			provinceData = ProvinceDataList.getProvinceData(packetID, date, province);
			if(provinceData != null)
			{
				payProvinceAmount = provinceData.getPay();	
			}
		}
		payProvinceAmount += +amount;
		
		if(provinceUserExceedList != null)
		{
			String provinceUserExceedAmountstr = (String)provinceUserExceedList.get(province);
			if(!StringUtil.is_nullString(provinceUserExceedAmountstr))
			{
				exceed_province_user_pay = Integer.valueOf(provinceUserExceedAmountstr)*100;
			}
		}
		
		if(ConstValue.DEBUG == 1)
		{
			channelData = ChannelDataList.getChannelData(packetID,"2015-04-17");
		}
		if(channelData != null)
		{
			payChannelAmount = channelData.getMmPay()+amount ;
		}
		if(devicePay != null)
		{
			if(devicePay.getDate().equals(date))
			{
				payAmount = devicePay.getAmount()+amount ;
			}
			if(devicePay.getMonth() == curMonth)
			{
				payMonthAmount = devicePay.getMonthAmount()+amount ;
			}
		}
		
		if(ConstValue.DEBUG == 1)
		{
			DebuUtil.log("渠道当前支付="+payChannelAmount);
			DebuUtil.log("渠道限额="+exceed_channel_pay);
		}
		if(payAmount > exceedAmount)
		{
			ret = true;
		}
		if(payMonthAmount > exceed_month_pay)
		{
			ret = true;
		}
		if(payChannelAmount > exceed_channel_pay)
		{
			ret = true;
		}
		if(exceed_province_user_pay > 0)
		{
			if(payAmount > exceed_province_user_pay)
			{
				ret = true;
			}
		}
		if(exceed_province_pay > 0)
		{
			if(payProvinceAmount > exceed_province_pay)
			{
				ret = true;
			}
		}
		return ret;
		
	}
	
	public void addPay(String imei,int amount,String date)
	{
		//String sql;
		DevicePay devicePay = getRecord(imei);
		if(amount < 100)//小于两块不处理
		{
		//	return;
		}
		Date curdate = new Date();
		int curMonth = curdate.getMonth();
		if(devicePay != null)
		{
			if(devicePay.getDate().equals(date))
			{
				int iValue = devicePay.getAmount();
				iValue += amount;
				devicePay.setAmount(iValue);
			}
			else
			{
				devicePay.setAmount(amount);
				devicePay.setDate(date);
			}
			
			if(devicePay.getMonth() == curMonth)
			{
				int iValue = devicePay.getMonthAmount();
				iValue += amount;
				devicePay.setMonthAmount(iValue);
			}
			else
			{
				devicePay.setMonthAmount(amount);
				devicePay.setMonth(curMonth);
			}
			edit(devicePay);
		}
		else
		{
			devicePay = new DevicePay();
			devicePay.setImei(imei);;
			devicePay.setAmount(amount);
			devicePay.setDate(date);
			devicePay.setMonthAmount(amount);
			devicePay.setMonth(curMonth);
			add(devicePay);
		}
		
	}	
	
	
}
