package dao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Activate;
import model.BusinessProfit;
import model.Cooperation;
import model.DeviceInfo;
import model.MmPay;
import model.Pay;
import model.PayTypeCount;
import model.ProvinceData;
import model.ProvinceDataList;
import sdkReq.GetAmountIndex;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import azul.CacheDao;

public class ProvinceDataDao extends CacheDao{
	public ProvinceDataDao() {
		init();
	}
	
	public ProvinceData getRecord(String packet_id,String date,String province)
	{
		ProvinceData provinceData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and date='%s' and province='%s' ", baseTableName,packet_id,date,province);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			provinceData = (ProvinceData)objList.get(0);
		}
	
		return provinceData;
	}
	public ProvinceData getRecord(String packet_id,String date)
	{
		ProvinceData provinceData = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' and date='%s' ", baseTableName,date,packet_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			provinceData = (ProvinceData)objList.get(0);
		}
	
		return provinceData;
	}
	
	
	public void AddRecord(ProvinceData provinceData)
	{
		this.add(provinceData);
	}
	
	public ProvinceData createRecord(String packet_id,String date,String province)
	{
		ProvinceData provinceData = null;
		//ArrayList<Object> objList;
		//String sql;
		//sql = String.format("select * from %s where packet_id='%s' and date='%s' and ='%s' ", baseTableName,packet_id,date,province);
		//objList = getList(sql);
		//DebuUtil.log(sql);
		/*if(objList.size() > 0)
		{
			channelData = (ChannelData)objList.get(0);
		}
		else*/
		{
			provinceData = new ProvinceData();
			provinceData.packet_id = packet_id;
			provinceData.date = date;
			provinceData.province = province;
			
			CooperationDao cooDao = new CooperationDao();
			Cooperation cooperation= cooDao.getRecord(packet_id);
			if(cooperation != null)
			{
				provinceData.game_id = cooperation.getAppNo();
				provinceData.BusinessNo = cooperation.getBusinessNo();
				//provinceData.channel = cooperation.getChannelNo();
			}
			
			
		}
		return provinceData;
	}
	
	
	public void pay(MmPay obj,boolean bSDK)
	{
		//DebuUtil.log("paySA obj.packet_id="+obj.packet_id);//
		String packet_id=obj.packet_id;
		String date = DateUtil.getDate();
		String code = GetAmountIndex.getRegionCode(obj.province, "");
		String province = GetAmountIndex.getRegionCode("", code);
		
		ProvinceData provinceData = ProvinceDataList.getProvinceData(packet_id,obj.date,province);
		if(provinceData != null)
		{			
			ProvinceData tempData = new ProvinceData();
			tempData.clearData();
			tempData.id = provinceData.id;
			
			
			if(obj.getRet() > 0)
			{
				provinceData.FeeNumInc(1);
				tempData.fee_num = provinceData.fee_num;
				if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("省份付费次数累加="+obj.province+provinceData.fee_num);
				}
			}
			
			provinceData.PayInc(obj.amount,Pay.MmPayType);
			tempData.pay = provinceData.pay;
			if(ConstValue.DEBUG == 1)
			{
				 DebuUtil.log("省份金额累加="+provinceData.pay);
			}
			edit(tempData);
		}
	}
	
	public void NewAccount(Activate obj,boolean bSDK)
	{
		//DebuUtil.log("paySA obj.packet_id="+obj.packet_id);//
		String packet_id=obj.packet_id;
		//String date = DateUtil.getDate();
		
		String code = GetAmountIndex.getRegionCode(obj.addr, "");
		String province = GetAmountIndex.getRegionCode("", code);
		ProvinceData provinceData = ProvinceDataList.getProvinceData(packet_id,obj.date,province);
		if(provinceData != null)
		{			
			
			DebuUtil.log("device_id="+obj.device_id);
			ProvinceData tempData = new ProvinceData();
			tempData.clearData();
			tempData.id = provinceData.id;
			
			DeviceInfoDao deviceInfoDao = new DeviceInfoDao();
			DeviceInfo deviceInfo = deviceInfoDao.getRecord(obj.device_id);
			String payWay = "";
			
	
			if(deviceInfo != null)
			{
				 payWay = deviceInfo.getSIMType(deviceInfo.getImsi());
			}


			DebuUtil.log("payway111111111="+payWay);
			provinceData.NewAcountInc(1,payWay);
			
			tempData.new_acount = provinceData.new_acount;
			tempData.mobile_new_acount = provinceData.mobile_new_acount;
			tempData.unicom_new_acount = provinceData.unicom_new_acount;
			tempData.telecom_new_acount = provinceData.telecom_new_acount;
			tempData.other_new_acount = provinceData.other_new_acount;
			//if(ConstValue.DEBUG == 1)
			{
				 DebuUtil.log("新增用户累加="+province+provinceData.new_acount);
			}
			edit(tempData);
		}
	}
	
	
	
	public void reviseNewPay(Pay obj,boolean bSDK)
	{
		//DebuUtil.log("paySA obj.packet_id="+obj.packet_id);//
		String packet_id=obj.packet_id;
		String date = DateUtil.getDate();
		String code = GetAmountIndex.getRegionCode(obj.ext, "");
		String province = GetAmountIndex.getRegionCode("", code);
		//DebuUtil.log("付费日期="+obj.date+"金额"+obj.amount);
		ProvinceData provinceData = ProvinceDataList.getProvinceData(packet_id,obj.date,province);
		if(provinceData != null)
		{			
			ProvinceData tempData = new ProvinceData();
			tempData.clearData();
			tempData.id = provinceData.id;
			
			if(obj.state == 1)
			{			
				int dayoff = -1;
				//计算新增付费
				String[] provincestrarray=obj.ext.split("_");    
				if(provincestrarray.length > 1)
				{
					dayoff =Integer.valueOf(provincestrarray[1]);
					
				}
				else
				{
					ActivateDao activateDao = new ActivateDao();
					Activate activate = activateDao.getActivateRecord(obj.device_id,obj.game_id);
					if(activate != null)
					{
						
						if(activate.date.equals(obj.date))
						{
							DebuUtil.log("付费日期="+obj.date+"激活日期="+activate.date+"金额"+obj.amount+provinceData.province);
							//DebuUtil.log("激活日期="+activate.date);
							//DebuUtil.log("省份金额新增付费累加="+provinceData.new_pay);
							dayoff = 0;
						}
					}
							
				}
				if(dayoff == 0)
				{
					DeviceInfoDao deviceInfoDao = new DeviceInfoDao();
					DeviceInfo deviceInfo = deviceInfoDao.getRecord(obj.device_id);
					String payWay = "";
					if(deviceInfo != null)
					{
						 payWay = deviceInfo.getSIMType(deviceInfo.getImsi());
					}
					provinceData.NewPayInc(obj.amount,payWay);
					tempData.new_pay = provinceData.new_pay;
					tempData.mobile_new_pay = provinceData.mobile_new_pay;
					tempData.telecom_new_pay = provinceData.telecom_new_pay;
					tempData.unicom_new_pay = provinceData.unicom_new_pay;
					
					//if(ConstValue.DEBUG == 1)
					{
						 DebuUtil.log("省份金额新增付费累加="+provinceData.new_pay+provinceData.province);
					}
					edit(tempData);
				}
			}
			
			
		}
	}
	
	public void Activate(Activate obj)
	{
		//DebuUtil.log("paySA obj.packet_id="+obj.packet_id);//
		String packet_id=obj.packet_id;
		//String date = DateUtil.getDate();
		
		String code = GetAmountIndex.getRegionCode(obj.addr, "");
		String province = GetAmountIndex.getRegionCode("", code);
		ProvinceData provinceData = ProvinceDataList.getProvinceData(packet_id,obj.date,province);
		if(provinceData != null)
		{			
			ProvinceData tempData = new ProvinceData();
			tempData.clearData();
			tempData.id = provinceData.id;
			
			provinceData.LoginAcountInc(1);
			tempData.login_acount = provinceData.login_acount;
			//if(ConstValue.DEBUG == 1)
			{
				 DebuUtil.log("活跃用户累加="+province+provinceData.login_acount);
			}
			edit(tempData);
		}
	}
	public void pay(Pay obj,boolean bSDK)
	{
		//DebuUtil.log("paySA obj.packet_id="+obj.packet_id);//
		String packet_id=obj.packet_id;
		String date = DateUtil.getDate();
		String code = GetAmountIndex.getRegionCode(obj.ext, "");
		String province = GetAmountIndex.getRegionCode("", code);
		
		ProvinceData provinceData = ProvinceDataList.getProvinceData(packet_id,obj.date,province);
		if(provinceData != null)
		{			
			ProvinceData tempData = new ProvinceData();
			tempData.clearData();
			tempData.id = provinceData.id;
			
			if(obj.state == 1)
			{
				provinceData.FeeNumInc(1);
				tempData.fee_num = provinceData.fee_num;
				//if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("省份付费次数累加="+obj.ext+provinceData.fee_num);
				}
				
				int dayoff = -1;
				//计算新增付费
				String[] provincestrarray=obj.ext.split("_");    
				if(provincestrarray.length > 1)
				{
					dayoff =Integer.valueOf(provincestrarray[1]);
					
				}
				else
				{
					ActivateDao activateDao = new ActivateDao();
					Activate activate = activateDao.getActivateRecord(obj.device_id,obj.game_id);
					if(activate != null)
					{
						if(activate.date.equals(obj.date))
						{
							dayoff = 0;
						}
					}
							
				}
				
				DeviceInfoDao deviceInfoDao = new DeviceInfoDao();
				DeviceInfo deviceInfo = deviceInfoDao.getRecord(obj.device_id);
				String payWay = "";
				if(deviceInfo != null)
				{
					 payWay = deviceInfo.getSIMType(deviceInfo.getImsi());
				}
				provinceData.PayFeeNumInc(1, payWay);
				tempData.mobile_fee_num = provinceData.mobile_fee_num;
				tempData.unicom_fee_num = provinceData.unicom_fee_num;
				tempData.telecom_fee_num = provinceData.telecom_fee_num;
				if(dayoff == 0)
				{
					
					provinceData.NewPayInc(obj.amount,payWay);
					tempData.new_pay = provinceData.new_pay;
					tempData.mobile_new_pay = provinceData.mobile_new_pay;
					tempData.telecom_new_pay = provinceData.telecom_new_pay;
					tempData.unicom_new_pay = provinceData.unicom_new_pay;
					tempData.other_new_pay = provinceData.other_new_pay;
				}
				
				provinceData.PayInc(obj.amount,payWay);
				tempData.pay = provinceData.pay;
				tempData.unicom_pay = provinceData.unicom_pay;
				tempData.telecom_pay = provinceData.telecom_pay;
				tempData.mobile_pay = provinceData.mobile_pay;
				tempData.other_pay = provinceData.other_pay;
				if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("省份金额累加="+provinceData.pay);
				}
				edit(tempData);
			}//if(obj.state == 1)
			
			
		}//if(provinceData != null)
		
	}
	/**
	 * 获取指定条件下的省份数据
	 * @param date
	 * @param gameNo
	 * @param businessNo
	 * @param apkNo
	 * @return
	 */
	public List<ProvinceData> getRecord(Date date,String gameNo,String businessNo,String channelNo, String apkNo,String payType){
		return getRecord( DateUtil.getDate(date), gameNo, businessNo, channelNo, apkNo,payType);
	}
	/**
	 * 获取指定条件下的省份数据
	 * @param date
	 * @param gameNo
	 * @param businessNo
	 * @param apkNo   
	 * @return	 */
	public List<ProvinceData> getRecord(String date,String gameNo,String businessNo,String channelNo, String apkNo,String payType){
		
		String dateStr =date;//DateUtil.getDate(date);
		
		//StringBuilder sql = new StringBuilder("select province,sum(new_acount),sum(pay),sum(fee_num),sum(new_pay) from province_data where date = '" + dateStr + "'");
		StringBuilder sql =  new StringBuilder("");
		switch(payType){
			case "mm"  : sql.append("select province,sum(mobile_new_acount),sum(mobile_pay),sum(mobile_fee_num),sum(mobile_new_pay),sum(new_acount) from province_data where date = '" + dateStr + "'"); break;
			case "uni" : sql.append("select province,sum(unicom_new_acount),sum(unicom_pay),sum(unicom_fee_num),sum(unicom_new_pay),sum(new_acount) from province_data where date = '" + dateStr + "'"); break;
			case "tel" : sql.append("select province,sum(telecom_new_acount),sum(telecom_pay),sum(telecom_fee_num),sum(telecom_new_pay),sum(new_acount) from province_data where date = '" + dateStr + "'"); break;
			case "" : sql.append("select province,sum(new_acount),sum(pay),sum(fee_num),sum(new_pay),sum(new_acount) from province_data where date = '" + dateStr + "'"); break;
		}
		if(!StringUtil.is_nullString(gameNo)){
			sql.append(" and game_id = '" + gameNo + "'");
		}
		if(!StringUtil.is_nullString(businessNo)){
			sql.append(" and business_no = '" + businessNo + "'");
		}
		if(!StringUtil.is_nullString(channelNo)){
			sql.append(" and packet_id in (select packet_id from cooperation where channel_no = '" + channelNo + "') ");
		}
		if(!StringUtil.is_nullString(apkNo)){
			sql.append(" and packet_id = '" + apkNo + "'");
		}
		
		sql.append("  GROUP BY province");
		
		sql.append(" order by sum(pay) desc");
		
		List<ArrayList> rawList = getObjectList(sql.toString());
		
		List<ProvinceData> list = new ArrayList<>();
		
		for(ArrayList al : rawList){
			String province = (String) al.get(0);
			BigDecimal newAcount = (BigDecimal) al.get(1);
			BigDecimal pay = (BigDecimal) al.get(2);
			BigDecimal feeNum = (BigDecimal) al.get(3);
			BigDecimal newPay = (BigDecimal) al.get(4);
			BigDecimal newAcountSum = (BigDecimal) al.get(5);
			String newAcountProprotion = "";
			ProvinceData pd = new ProvinceData();
			pd.setDate(dateStr);
			pd.setPay(pay.intValue());
			pd.setNewPay(newPay.intValue());
			pd.setBusinessNo(businessNo);
			pd.setFeeNum(feeNum.intValue());
			pd.setGameId(gameNo);
			pd.setNewAcount(newAcount.intValue());
			pd.setPacketId(apkNo);
			pd.setProvince(province);
			if(newAcountSum.compareTo(BigDecimal.ZERO)==0){
				newAcountProprotion ="-";
			}else{
			DecimalFormat format = new DecimalFormat("0.00");
			 newAcountProprotion = format.format(newAcount.intValue() *100 / newAcountSum.intValue());
			}
			pd.setNewAcountProportion(newAcountProprotion);
			list.add(pd);
		}
		return list;
	}
	
	/**
	 * 获得指定条件下所有省份付费总额
	 * @param date
	 * @param gameNo
	 * @param businessNo
	 * @param apkNo
	 * @return
	 */
	public int getAllFeeByDate(Date date,String gameNo,String businessNo,String channelNo, String apkNo,String payType){
		String dateStr = DateUtil.getDate(date);
		StringBuilder sql =  new StringBuilder("");
		switch(payType){
			case "mm"  : sql.append("select sum(mobile_pay) from province_data where 1=1"); break;
			case "uni" : sql.append("select sum(unicom_pay) from province_data where 1=1"); break;
			case "tel" : sql.append("select sum(telecom_pay) from province_data where 1=1"); break;
			case ""    : sql.append("select sum(pay) from province_data where 1=1"); break;
		}
		if(!StringUtil.is_nullString(gameNo)){
			sql.append(" and game_id = '" + gameNo + "'");
		}
		
		if(!StringUtil.is_nullString(businessNo)){
			sql.append(" and business_no = '" + businessNo + "'");
		}
		if(!StringUtil.is_nullString(channelNo)){
			sql.append(" and packet_id in (select packet_id from cooperation where channel_no = '" + channelNo + "') ");
		}
		if(!StringUtil.is_nullString(apkNo)){
			sql.append(" and packet_id = '" + apkNo + "'");
		}
		sql.append(" and date = '" + dateStr + "'");
		
		BigDecimal temp = (BigDecimal) getValue(sql.toString());
		
		int total  = temp == null ? 0 : temp.intValue();
		
		return total;
	}
	
	/**
	 * 获取指定日期下上一日某个省的付费金额
	 * @param date
	 * @param province
	 * @param gameNo
	 * @param businessNo
	 * @param apkNo
	 * @return
	 */
	public int getPreDayFee(Date date,String province,String gameNo,String businessNo,String channelNo, String apkNo){
		date = DateUtil.addDate("dd", -1, date);
		String dateStr = DateUtil.getDate(date);
		
		StringBuilder sql = new StringBuilder("select sum(pay) from province_data where 1 = 1 ");
		
		if(!StringUtil.is_nullString(dateStr)){
			sql.append(" and date = '" + dateStr + "'");
		}				
		
		if(province == null){
			sql.append(" and province is null ");
		}else{
			if("".equals(province.trim())){
				sql.append(" and province = ''");
			}else
				sql.append(" and province = '" + province + "'");
		}
		
		if(!StringUtil.is_nullString(gameNo)){
			sql.append(" and game_id = '" + gameNo + "'");
		}
		if(!StringUtil.is_nullString(businessNo)){
			sql.append(" and business_no = '" + businessNo + "'");
		}
		if(!StringUtil.is_nullString(channelNo)){
			sql.append(" and packet_id in (select packet_id from cooperation where channel_no = '" + channelNo + "') ");
		}
		if(!StringUtil.is_nullString(apkNo)){
			sql.append(" and packet_id = '" + apkNo + "'");
		}
		
		sql.append(" group by province");
		
		BigDecimal temp = (BigDecimal)getValue(sql.toString());
		
		int preDayFee ;
		if(temp == null){
			preDayFee = 0;
		}else{
			preDayFee = temp.intValue();
		}
		
		return preDayFee;
	}
	public Map<String,Object> getLtvList(String apkNo,String gameNo,String businessNo,Date beginDate,int pageSize,int curPage){
		
		StringBuilder sql  = new StringBuilder("SELECT * from province_data where 1=1");
		if(!StringUtil.is_nullString(apkNo)){
			sql.append(" and packet_id = '" + apkNo + "' ");
		}
		String str = String.format(" AND date = '" + DateUtil.getDate(beginDate) +"' ");
	//	sql.append(" AND date = '" + DateUtil.getDate(beginDate) +"' and packet_id  = '"+ apkNo +"'");
		sql.append(str);
		List<ProvinceData> list = getList(curPage,pageSize,sql.toString());
		
		int totalCount = getRecordCount(sql.toString());
		
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("totalPage",totalPage);
		return map;
	}
	
public Map<String,Object> getThrProMonList(String apkNo,String gameNo,String businessNo,Date beginDate,Date endDate,int pageSize,int curPage){
		
		StringBuilder sql  = new StringBuilder("SELECT DATE_FORMAT(date,'%Y-%m-%d'),sum(mobile_new_acount),SUM(mobile_new_pay),sum(mobile_pay),sum(unicom_new_acount),sum(unicom_new_pay),sum(unicom_pay),sum(telecom_new_acount),sum(telecom_new_pay),sum(telecom_pay),sum(other_new_acount),sum(other_new_pay),sum(other_pay) from province_data where 1=1 ");
		if(!StringUtil.is_nullString(gameNo)){
			sql.append(" and game_id = '" + gameNo + "' ");
		}
		if(!StringUtil.is_nullString(apkNo)){
			sql.append(" and packet_id = '" + apkNo + "' ");
		}
		if(!StringUtil.is_nullString(businessNo)){
			sql.append(" and business_no = '" + businessNo + "' ");
		}
		String str = String.format(" and date >= '" + DateUtil.getDate(beginDate) +"' and date <= '" + DateUtil.getDate(endDate) +"'  group by date");
	//	sql.append(" AND date = '" + DateUtil.getDate(beginDate) +"' and packet_id  = '"+ apkNo +"'");
		sql.append(str);
		List<ArrayList> list = new ArrayList();
		list = getObjectList(sql.toString());
		//List<ProvinceData> list = getList(curPage,pageSize,sql.toString());
		
		int totalCount = getRecordCount(sql.toString());
		
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("totalPage",totalPage);
		return map;
	}

	public Map<String,Object> getBusinessProfit(String apkNo,Date beginDate,Date endDate,int pageSize,int curPage){
		Map<String,Object> map = new HashMap<String,Object>();
		
//		packetlist = getObjectList(packetidsql.toString());
		
		StringBuilder sql = new StringBuilder("select DATE_FORMAT(province_data.date,'%Y-%m-%d'),sum(province_data.mobile_pay),sum(province_data.telecom_pay),sum(province_data.unicom_pay),channel_data.settle,channel_data.fee_num,"
				+ " channel_data.mm_pay,channel_data.web_pay,channel_data.box_pay,channel_data.open_pay,channel_data.ld_pay,channel_data.jd_pay,channel_data.anan_pay  from province_data,channel_data where 1=1 and province_data.packet_id = '"+apkNo+"' "
				+ " and province_data.packet_id = channel_data.packet_id and province_data.date = channel_data.date ");
		String daystr = String.format(" and province_data.date >= '" + DateUtil.getDate(beginDate) +"' and province_data.date <= '" + DateUtil.getDate(endDate) +"'  group by province_data.date");
		sql.append(daystr);
		List<BusinessProfit> list = new ArrayList<>();
		List<ArrayList> rawList = getObjectList(sql.toString());
		CooperationDao cooDao = new CooperationDao();
		Cooperation coo = cooDao.getRecord(apkNo);
		PayTypeCountDao  payCountDao = new PayTypeCountDao();
		int mmSettle = payCountDao.getPaySettleCount("mmpay");
		int webSettle = payCountDao.getPaySettleCount("jdpay");
		int boxSettle = payCountDao.getPaySettleCount("fanhe");
		int openSettle = payCountDao.getPaySettleCount("openpay");
		int ldSettle = payCountDao.getPaySettleCount("leDong");
		int jdSettle = payCountDao.getPaySettleCount("mmjd");
		int ananSettle = payCountDao.getPaySettleCount("ananpay");
		for(ArrayList al : rawList){
			String date = (String) al.get(0);
			BigDecimal mobilePay = (BigDecimal) al.get(1);
			BigDecimal telePay = (BigDecimal) al.get(2);
			BigDecimal unicomPay = (BigDecimal) al.get(3);
			int settle = (int) al.get(4);
			int payNum = (int) al.get(5);
			int sumPay = (mobilePay.intValue()+telePay.intValue()+unicomPay.intValue())/100;
			int mmPay = (int) al.get(6);
			int webPay = (int) al.get(7);
			int boxPay = (int) al.get(8);
			int openPay = (int) al.get(9);
			int ldPay = (int) al.get(10);
			int jdPay = (int) al.get(11);
			int ananPay = (int) al.get(12);
			int profit = ((mmPay - mmPay*mmSettle/100) + (webPay - webPay*webSettle/100) + (boxPay - boxPay*boxSettle/100) + (openPay - openPay*openSettle/100) + (ldPay - ldPay*ldSettle/100) + (jdPay - jdPay*jdSettle/100) + (ananPay - ananPay*ananSettle/100))/100 - sumPay*coo.cpscount/100 -settle*coo.cpacount/100;
			BusinessProfit bp = new BusinessProfit();
			bp.setDate(date);
			bp.setMobilePay(mobilePay.intValue()/100);
			bp.setTelePay(telePay.intValue()/100);
			bp.setUnicomPay(unicomPay.intValue()/100);
			bp.setActivate(settle);
			bp.setCpaCount(coo.cpacount);
			bp.setCpsCount(coo.cpscount);
			bp.setInstall(0);
			bp.setSumPay(sumPay);
			bp.setPayNum(payNum);
			bp.setProfit(profit);
			list.add(bp);
		}
		int totalCount = getRecordCount(sql.toString());
		int totalPage = (totalCount + pageSize - 1) / pageSize;
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("totalPage",totalPage);
		map.put("msg","查询成功");
		map.put("ret", true);
		return map;
	}
}
