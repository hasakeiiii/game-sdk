package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DateUtil;
import dao.CooperationPayDao;
import dao.MmPayDataDao;
import dao.PayDao;


public class CooperationFee implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5747273887337957356L;
	private Integer id;
	private String packet_id;
	private String payUrl;
	private String app_no;
	private String channel_no;
	private String mm_channel_no;
	
	private String mobile_pay_type;//移动主计费点
	private String moblie_pay_id;
	
	private String mobile_pay1_type;//移动副计费点
	private String moblie_pay1_id;
	private String moblie_pay1_param;//计费参数，比如省份列表	
	
	private String mobile_pay2_type;//移动副计费点
	private String moblie_pay2_id;
	private String moblie_pay2_param;//计费参数，比如省份列表
	
	private String mobile_pay3_type;//移动副计费点
	private String moblie_pay3_id;
	private String moblie_pay3_param;//计费参数，比如省份列表
	
	private String mobile_pay4_type;//移动副4计费点
	private String mobile_pay4_id;
	private String mobile_pay5_type;//移动副5计费点
	private String mobile_pay5_id;
	private String mobile_pay6_type;//移动副6计费点
	private String mobile_pay6_id;
	private String mobile_pay7_type;//移动副7计费点
	private String mobile_pay7_id;
	
	private String telcom_pay_type;//电信主计费点
	private String telcom_pay_id;
	
	private String telcom_pay1_type;//电信副计费点
	private String telcom_pay1_id;
	private String telcom_pay1_param;//计费参数，比如省份列表

	private String telcom_pay2_type;//电信副计费点
	private String telcom_pay2_id;
	private String telcom_pay2_param;//计费参数，比如省份列表

	private String telcom_pay3_type;//电信副计费点
	private String telcom_pay3_id;
	private String telcom_pay3_param;//计费参数，比如省份列表
	
	private String unicom_pay_type;//联通主计费点
	private String unicom_pay_id;
	
	private String unicom_pay1_type;//联通副计费点
	private String unicom_pay1_id;
	private String unicom_pay1_param;//计费参数，比如省份列表
	
	private String unicom_pay2_type;//联通副计费点
	private String unicom_pay2_id;
	private String unicom_pay2_param;//计费参数，比如省份列表
	
	private String unicom_pay3_type;//联通副计费点
	private String unicom_pay3_id;
	private String unicom_pay3_param;//计费参数，比如省份列表
	
	
	public NetFee getPayId(List<String> list, String province, String packet_id){
		
		NetFee netFee = new NetFee();
		String pay_id = "";
		String pay_type = "";
		PayDao payDao = new PayDao();
		CooperationPayDao cooPayDao = new CooperationPayDao();
		CooperationPay cooPay = new CooperationPay();
		int limitMoney = 0;//计费点限额
		String date = DateUtil.getDate();
	
		 for(int i = 0;i < list.size(); i ++){
			 Map provinceExceedList = new HashMap<String,String>();
			 if(list.get(i).contains(";")){
				 
				 String[] strarray=list.get(i).split(";");
				
				 pay_id = strarray[0]; 
				 pay_type = strarray[1];
				 
			 }
			 
			 cooPay = cooPayDao.getRecord(packet_id, pay_id);
			 if(cooPay == null){
				netFee.pay_id = pay_id;
				netFee.pay_type = pay_type;
				 break;
			 }
			 int money = payDao.getPayIdMoney(province, pay_id, date);//计费点已计费的金额
		
			 String provincestr = cooPay.getPayDataProvince();
			 String provinceExceedstr = cooPay.getPayData();
			 String[] provincestrarray=provincestr.split("_");     		
			 String[] provinceExceedstrarray=provinceExceedstr.split("_"); 
				for(int j = 0; j < provincestrarray.length; j ++)
				{
					if(provincestrarray[j].contains(province)){
						provincestrarray[j] = province;
					}
					provinceExceedList.put(provincestrarray[j], provinceExceedstrarray[j]);
				}
			if(provinceExceedList != null){
				
				limitMoney = Integer.valueOf((String)provinceExceedList.get(province))*100;
			}
			if(limitMoney !=0 && limitMoney - money > 0){
				netFee.pay_id = pay_id;
				netFee.pay_type = pay_type;
				break;
			}
			
		 }
		
		return netFee;
	}
	public static boolean isContains(String param,String addr,String payId, String payType){ 
		
			boolean ret = false;
			boolean ret1 = false;//判断计费点地区参数  是否包含在 IP获取到的地区
			boolean ret2 = false;//计费点编号不能为空
			
			boolean ret3 = false;//计费方式不能为0；0为关闭
			
			if(param ==null){
				return false;
			}
			if(param.equals("")){
				ret1 = true;
			}else{
			String[] addrarray=param.split("_");
			for(int i = 0;i <addrarray.length;i++ ){
				if(addr.contains(addrarray[i])){
					
					ret1 = true;
					
					}
				}
			}
			if(!payId.equals("")){
				ret2 = true;
			}
			if(!payType.equals("0")){
				ret3 = true;
			}
			
			if(ret1&&ret2&&ret3){
				ret = true;
			}
			return ret;
	}
	
	public NetFee getPayInfor(String pay_way,String province, String packet_id, int white,String addr)//计费点自动切换2015-07-10 开始不用。
	{
		NetFee netFee = new NetFee();
	
		
		List<String> list = new ArrayList<String>();
		if(pay_way.equals(Pay.MmPayType))
		{
			
			if(moblie_pay_id != null&&!moblie_pay_id.equals("")){
				
				list.add(moblie_pay_id+';'+getType(moblie_pay_id));
			}
			
		
			if(isContains(moblie_pay1_param,addr,moblie_pay1_id,getType(moblie_pay1_id))){
				
				list.add(moblie_pay1_id+';'+getType(moblie_pay1_id));
			}
			if(isContains(moblie_pay2_param,addr,moblie_pay2_id,getType(moblie_pay2_id))){
				
				list.add(moblie_pay2_id+';'+getType(moblie_pay2_id));
			}
			if(isContains(moblie_pay3_param,addr,moblie_pay3_id,getType(moblie_pay3_id))){
	
				list.add(moblie_pay3_id+';'+getType(moblie_pay3_id));
			}
//				Iterator it = list.iterator();
			netFee = getPayId(list, province,packet_id);
			
			
			 
	/*		netFee.pay_id = moblie_pay_id;
			netFee.pay_type = mobile_pay_type;
			//netFee.pay_param = payUrl;
			if(moblie_pay1_param.contains(province))
			{
				netFee.pay_id = moblie_pay1_id;
				netFee.pay_type = mobile_pay1_type;
				netFee.pay_param = moblie_pay1_param;
			}
			else if(moblie_pay2_param.contains(province))
			{
				netFee.pay_id = moblie_pay2_id;
				netFee.pay_type = mobile_pay2_type;
				netFee.pay_param = moblie_pay2_param;
			}
			else if(moblie_pay3_param.contains(province))
			{
				netFee.pay_id = moblie_pay3_id;
				netFee.pay_type = mobile_pay3_type;
				netFee.pay_param = moblie_pay3_param;
			}*/
		}
		else if(pay_way.equals(Pay.TCPayType))
		{
			if(telcom_pay_id != null&&!telcom_pay_type.equals("0")&&!telcom_pay_type.equals("")){
				
				list.add(telcom_pay_id+';'+telcom_pay_type);
			}
			if(isContains(telcom_pay1_param,addr,telcom_pay1_id,telcom_pay1_type)){
				
				list.add(telcom_pay1_id+';'+telcom_pay1_type);
			}
			if(isContains(telcom_pay2_param,addr,telcom_pay2_id,telcom_pay2_type)){
				
				list.add(telcom_pay2_id+';'+telcom_pay2_type);
			}
			if(isContains(telcom_pay3_param,addr,telcom_pay3_id,telcom_pay3_type)){
	
				list.add(telcom_pay3_id+';'+telcom_pay3_type);
			}
		
			netFee = getPayId(list, province,packet_id);
				
			/*netFee.pay_id = telcom_pay_id;
			netFee.pay_type = telcom_pay_type;
			//netFee.pay_param = payUrl;
			if(telcom_pay1_param.contains(province))
			{
				netFee.pay_id = telcom_pay1_id;
				netFee.pay_type =telcom_pay1_type;
				netFee.pay_param = telcom_pay1_param;
			}
			else if(telcom_pay2_param.contains(province))
			{
				netFee.pay_id = telcom_pay2_id;
				netFee.pay_type = telcom_pay2_type;
				netFee.pay_param = telcom_pay2_param;
			}
			else if(telcom_pay3_param.contains(province))
			{
				netFee.pay_id = telcom_pay3_id;
				netFee.pay_type = telcom_pay3_type;
				netFee.pay_param = telcom_pay3_param;
			}*/
		}
		else if(pay_way.equals(Pay.UniPayType))
		{
			
			if(unicom_pay_id != null&&!unicom_pay_type.equals("0")&&!unicom_pay_type.equals("")){
				
				list.add(unicom_pay_id+';'+unicom_pay_type);
			}
			if(isContains(unicom_pay1_param,addr,unicom_pay1_id,unicom_pay1_type)){
				
				list.add(unicom_pay1_id+';'+unicom_pay1_type);
			}
			if(isContains(unicom_pay2_param,addr,unicom_pay2_id,unicom_pay2_type)){
				
				list.add(unicom_pay2_id+';'+unicom_pay2_type);
			}
			if(isContains(unicom_pay3_param,addr,unicom_pay3_id,unicom_pay3_type)){
	
				list.add(unicom_pay3_id+';'+unicom_pay3_type);
			}
		
			netFee = getPayId(list, province,packet_id);
			/*netFee.pay_id = unicom_pay_id;
			netFee.pay_type = unicom_pay_type;
			//netFee.pay_param = payUrl;
			if(unicom_pay1_param.contains(province))
			{
				netFee.pay_id = unicom_pay1_id;
				netFee.pay_type =unicom_pay1_type;
				netFee.pay_param = unicom_pay1_param;
			}
			else if(unicom_pay2_param.contains(province))
			{
				netFee.pay_id = unicom_pay2_id;
				netFee.pay_type = unicom_pay2_type;
				netFee.pay_param = unicom_pay2_param;
			}
			else if(unicom_pay3_param.contains(province))
			{
				netFee.pay_id = unicom_pay3_id;
				netFee.pay_type = unicom_pay3_type;
				netFee.pay_param = unicom_pay3_param;
			}*/
		}
		if(netFee.pay_type == null){
			netFee.pay_type ="0";
			
			if(white ==1&&pay_way.equals(Pay.MmPayType)){
				netFee.pay_id = moblie_pay_id;
				netFee.pay_type = mobile_pay_type;
			}
			if(white ==1&&pay_way.equals(Pay.TCPayType)){
				netFee.pay_id = telcom_pay_id;
				netFee.pay_type = telcom_pay_type;
			}
			if(white ==1&&pay_way.equals(Pay.UniPayType)){
				netFee.pay_id = unicom_pay_id;
				netFee.pay_type = unicom_pay_type;
			}
		}
		return netFee;
	}
	
	public String getType(String pay_id){
		String type = "";
		MmPayDataDao mmdao = new MmPayDataDao();
		
		type = mmdao.getPayType(pay_id);
		
		
		return type;
	}
	
	public List<String> getPay(String pay_way)
	{
		NetFee netFee = new NetFee();
	
		List<String> payIdlist = new ArrayList<String>();
		if(pay_way.equals(Pay.MmPayType))
		{
			if(moblie_pay_id != null&&!moblie_pay_id.equals("")){
			netFee.pay_id = moblie_pay_id;
			netFee.pay_type = getType(moblie_pay_id);
//			netFee.pay_param = payUrl;
			if(!netFee.pay_type.equals("")){
				
				payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
			}
			}if(moblie_pay1_id != null&&!moblie_pay1_id.equals("")){
				netFee.pay_id = moblie_pay1_id;
				netFee.pay_type = getType(moblie_pay1_id);
//				netFee.pay_param = moblie_pay1_param;
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(moblie_pay2_id != null&&!moblie_pay2_id.equals("")){
				netFee.pay_id = moblie_pay2_id;
				netFee.pay_type = getType(moblie_pay2_id);
//				netFee.pay_param = moblie_pay2_param;
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(moblie_pay3_id != null&&!moblie_pay3_id.equals("")){
				netFee.pay_id = moblie_pay3_id;
				netFee.pay_type = getType(moblie_pay3_id);
//				netFee.pay_param = moblie_pay3_param;
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(mobile_pay4_id != null&&!mobile_pay4_id.equals("")){
				netFee.pay_id = mobile_pay4_id;
				netFee.pay_type = getType(mobile_pay4_id);
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(mobile_pay5_id != null&&!mobile_pay5_id.equals("")){
				netFee.pay_id = mobile_pay5_id;
				netFee.pay_type = getType(mobile_pay5_id);
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(mobile_pay6_id != null&&!mobile_pay6_id.equals("")){
				netFee.pay_id = mobile_pay6_id;
				netFee.pay_type = getType(mobile_pay6_id);
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(mobile_pay7_id != null&&!mobile_pay7_id.equals("")){
				netFee.pay_id = mobile_pay7_id;
				netFee.pay_type = getType(mobile_pay7_id);
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}
		}
		else if(pay_way.equals(Pay.TCPayType))
		{
			if(telcom_pay_id != null&&!telcom_pay_id.equals("")){	
			netFee.pay_id = telcom_pay_id;
			netFee.pay_type = getType(telcom_pay_id);
			//netFee.pay_param = payUrl;
			if(!netFee.pay_type.equals("")){
				
				payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
			}
			}if(telcom_pay1_id != null&&!telcom_pay1_id.equals("")){
				netFee.pay_id = telcom_pay1_id;
				netFee.pay_type = getType(telcom_pay1_id);
				netFee.pay_param = telcom_pay1_param;
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(telcom_pay2_id != null&&!telcom_pay2_id.equals("")){
				netFee.pay_id = telcom_pay2_id;
				netFee.pay_type = getType(telcom_pay2_id);
				netFee.pay_param = telcom_pay2_param;
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(telcom_pay3_id != null&&!telcom_pay3_id.equals("")){
				netFee.pay_id = telcom_pay3_id;
				netFee.pay_type = getType(telcom_pay3_id);
				netFee.pay_param = telcom_pay3_param;
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}
		}
		else if(pay_way.equals(Pay.UniPayType))
		{
			
			if(unicom_pay_id != null&&!unicom_pay_id.equals("")){
			netFee.pay_id = unicom_pay_id;
			netFee.pay_type = getType(unicom_pay_id);
			//netFee.pay_param = payUrl;
			if(!netFee.pay_type.equals("")){
				
				payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
			}
			}if(unicom_pay1_id != null&&!unicom_pay1_id.equals("")){
				netFee.pay_id = unicom_pay1_id;
				netFee.pay_type = getType(unicom_pay1_id);
				netFee.pay_param = unicom_pay1_param;
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(unicom_pay2_id != null&&!unicom_pay2_id.equals("")){
				netFee.pay_id = unicom_pay2_id;
				netFee.pay_type = getType(unicom_pay2_id);
				netFee.pay_param = unicom_pay2_param;
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}if(unicom_pay3_id != null&&!unicom_pay3_id.equals("")){
				netFee.pay_id = unicom_pay3_id;
				netFee.pay_type = getType(unicom_pay3_id);
				netFee.pay_param = unicom_pay3_param;
				if(!netFee.pay_type.equals("")){
					
					payIdlist.add(netFee.pay_id+";"+netFee.pay_type);
				}
			}
		}
		return payIdlist;
	}
	
	
	public String getMobilePay4Id() {
		return mobile_pay4_id;
	}
	public void setMobilePay4Id(String moblie_pay4_id) {
		this.mobile_pay4_id = moblie_pay4_id;
	}
	public String getMobilePay5Id() {
		return mobile_pay5_id;
	}
	public void setMobilePay5Id(String moblie_pay5_id) {
		this.mobile_pay5_id = moblie_pay5_id;
	}
	public String getMobilePay6Id() {
		return mobile_pay6_id;
	}
	public void setMobilePay6Id(String moblie_pay6_id) {
		this.mobile_pay6_id = moblie_pay6_id;
	}
	public String getMobilePay7Id() {
		return mobile_pay7_id;
	}
	public void setMobilePay7Id(String moblie_pay7_id) {
		this.mobile_pay7_id = moblie_pay7_id;
	}
	public String getMobilePay4Type() {
		return mobile_pay4_type;
	}
	public void setMobilePay4Type(String mobile_pay4_type) {
		this.mobile_pay4_type = mobile_pay4_type;
	}
	public String getMobilePay5Type() {
		return mobile_pay5_type;
	}
	public void setMobilePay5Type(String mobile_pay5_type) {
		this.mobile_pay5_type = mobile_pay5_type;
	}
	public String getMobilePay6Type() {
		return mobile_pay6_type;
	}
	public void setMobilePay6Type(String mobile_pay6_type) {
		this.mobile_pay6_type = mobile_pay6_type;
	}
	public String getMobilePay7Type() {
		return mobile_pay7_type;
	}
	public void setMobilePay7Type(String mobile_pay7_type) {
		this.mobile_pay7_type = mobile_pay7_type;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public String getMobilePay1Type() {
		return mobile_pay1_type;
	}
	public void setMobilePay1Type(String mobile_pay1_type) {
		this.mobile_pay1_type = mobile_pay1_type;
	}
	public String getMobliePay1Id() {
		return moblie_pay1_id;
	}
	public void setMobliePay1Id(String moblie_pay1_id) {
		this.moblie_pay1_id = moblie_pay1_id;
	}
	public String getMobliePay1Param() {
		return moblie_pay1_param;
	}
	public void setMobliePay1Param(String moblie_pay1_param) {
		this.moblie_pay1_param = moblie_pay1_param;
	}
	public String getMobilePay2Type() {
		return mobile_pay2_type;
	}
	public void setMobilePay2Type(String mobile_pay2_type) {
		this.mobile_pay2_type = mobile_pay2_type;
	}
	public String getMobliePay2Id() {
		return moblie_pay2_id;
	}
	public void setMobliePay2Id(String moblie_pay2_id) {
		this.moblie_pay2_id = moblie_pay2_id;
	}
	public String getMobliePay2Param() {
		return moblie_pay2_param;
	}
	public void setMobliePay2Param(String moblie_pay2_param) {
		this.moblie_pay2_param = moblie_pay2_param;
	}
	public String getMobilePay3Type() {
		return mobile_pay3_type;
	}
	public void setMobilePay3Type(String mobile_pay3_type) {
		this.mobile_pay3_type = mobile_pay3_type;
	}
	public String getMobliePay3Id() {
		return moblie_pay3_id;
	}
	public void setMobliePay3Id(String moblie_pay3_id) {
		this.moblie_pay3_id = moblie_pay3_id;
	}
	public String getMobliePay3Param() {
		return moblie_pay3_param;
	}
	public void setMobliePay3Param(String moblie_pay3_param) {
		this.moblie_pay3_param = moblie_pay3_param;
	}
	public String getTelcomPayType() {
		return telcom_pay_type;
	}
	public void setTelcomPayType(String telcom_pay_type) {
		this.telcom_pay_type = telcom_pay_type;
	}
	public String getTelcomPayId() {
		return telcom_pay_id;
	}
	public void setTelcomPayId(String telcom_pay_id) {
		this.telcom_pay_id = telcom_pay_id;
	}
	public String getTelcomPay1Type() {
		return telcom_pay1_type;
	}
	public void setTelcomPay1Type(String telcom_pay1_type) {
		this.telcom_pay1_type = telcom_pay1_type;
	}
	public String getTelcomPay1Id() {
		return telcom_pay1_id;
	}
	public void setTelcomPay1Id(String telcom_pay1_id) {
		this.telcom_pay1_id = telcom_pay1_id;
	}
	public String getTelcomPay1Param() {
		return telcom_pay1_param;
	}
	public void setTelcomPay1Param(String telcom_pay1_param) {
		this.telcom_pay1_param = telcom_pay1_param;
	}
	public String getTelcomPay2Type() {
		return telcom_pay2_type;
	}
	public void setTelcomPay2Type(String telcom_pay2_type) {
		this.telcom_pay2_type = telcom_pay2_type;
	}
	public String getTelcomPay2Id() {
		return telcom_pay2_id;
	}
	public void setTelcomPay2Id(String telcom_pay2_id) {
		this.telcom_pay2_id = telcom_pay2_id;
	}
	public String getTelcomPay2Param() {
		return telcom_pay2_param;
	}
	public void setTelcomPay2Param(String telcom_pay2_param) {
		this.telcom_pay2_param = telcom_pay2_param;
	}
	public String getTelcomPay3Type() {
		return telcom_pay3_type;
	}
	public void setTelcomPay3Type(String telcom_pay3_type) {
		this.telcom_pay3_type = telcom_pay3_type;
	}
	public String getTelcomPay3Id() {
		return telcom_pay3_id;
	}
	public void setTelcomPay3Id(String telcom_pay3_id) {
		this.telcom_pay3_id = telcom_pay3_id;
	}
	public String getTelcomPay3Param() {
		return telcom_pay3_param;
	}
	public void setTelcomPay3Param(String telcom_pay3_param) {
		this.telcom_pay3_param = telcom_pay3_param;
	}
	public String getUnicomPayType() {
		return unicom_pay_type;
	}
	public void setUnicomPayType(String unicom_pay_type) {
		this.unicom_pay_type = unicom_pay_type;
	}
	public String getUnicomPayId() {
		return unicom_pay_id;
	}
	public void setUnicomPayId(String unicom_pay_id) {
		this.unicom_pay_id = unicom_pay_id;
	}
	public String getUnicomPay1Type() {
		return unicom_pay1_type;
	}
	public void setUnicomPay1Type(String unicom_pay1_type) {
		this.unicom_pay1_type = unicom_pay1_type;
	}
	public String getUnicomPay1Id() {
		return unicom_pay1_id;
	}
	public void setUnicomPay1Id(String unicom_pay1_id) {
		this.unicom_pay1_id = unicom_pay1_id;
	}
	public String getUnicomPay1Param() {
		return unicom_pay1_param;
	}
	public void setUnicomPay1Param(String unicom_pay1_param) {
		this.unicom_pay1_param = unicom_pay1_param;
	}
	public String getUnicomPay2Type() {
		return unicom_pay2_type;
	}
	public void setUnicomPay2Type(String unicom_pay2_type) {
		this.unicom_pay2_type = unicom_pay2_type;
	}
	public String getUnicomPay2Id() {
		return unicom_pay2_id;
	}
	public void setUnicomPay2Id(String unicom_pay2_id) {
		this.unicom_pay2_id = unicom_pay2_id;
	}
	public String getUnicomPay2Param() {
		return unicom_pay2_param;
	}
	public void setUnicomPay2Param(String unicom_pay2_param) {
		this.unicom_pay2_param = unicom_pay2_param;
	}
	public String getUnicomPay3Type() {
		return unicom_pay3_type;
	}
	public void setUnicomPay3Type(String unicom_pay3_type) {
		this.unicom_pay3_type = unicom_pay3_type;
	}
	public String getUnicomPay3Id() {
		return unicom_pay3_id;
	}
	public void setUnicomPay3Id(String unicom_pay3_id) {
		this.unicom_pay3_id = unicom_pay3_id;
	}
	public String getUnicomPay3Param() {
		return unicom_pay3_param;
	}
	public void setUnicomPay3Param(String unicom_pay3_param) {
		this.unicom_pay3_param = unicom_pay3_param;
	}
	public Integer getId(){
		return this.id;
	}	
	
	public String getPacketId() {
		return packet_id;
	}
	public void setPacketId(String packet_id) {
		this.packet_id = packet_id;
	}
	
	public void setPayUrl(String payUrl){
		this.payUrl = payUrl;
	}
	public String getPayUrl(){
		return this.payUrl;
	}
	
	public void setMobilePayType(String mobile_pay_type){
		this.mobile_pay_type = mobile_pay_type;
	}
	public String getMobilePayType(){
		return this.mobile_pay_type;
	}
	
	public void setMobliePayId(String moblie_pay_id){
		this.moblie_pay_id = moblie_pay_id;
	}
	public String getMobliePayId(){
		return this.moblie_pay_id;
	}
	public String getMmChannelNo() {
		return mm_channel_no;
	}
	public void setMmChannelNo(String mm_channel_no) {
		this.mm_channel_no = mm_channel_no;
	}
	public String getChannelNo() {
		return channel_no;
	}
	public void setChannelNo(String channel_no) {
		this.channel_no = channel_no;
	}
	public String getAppNo() {
		return app_no;
	}
	public void setAppNo(String app_no) {
		this.app_no = app_no;
	}
	
	
}
