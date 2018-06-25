package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.HttpUtils;

import model.App;
import model.AppPay;
import model.AppPayData;
import model.AppPayDataList;
import model.ChannelData;
import model.ChannelDataList;
import model.Cooperation;
import model.CooperationFee;
import model.CooperationMoney;
import model.CooperationRemind;
import model.DeviceInfo;
import model.DevicePay;
import model.MmPayData;
import model.MoneyCount;
import model.NetFee;
import model.Pay;
import model.PayCompany;
import model.PayMkUrl;
import model.ProvinceData;
import model.ProvinceDataList;
import model.SysParam;
import model.Vague;
import model.VagueValue;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.JsonUtil;
import util.StringUtil;
import azul.IP;
import azul.LBS;
import dao.ActivateDao;
import dao.AppDao;
import dao.AppPayDao;
import dao.AppPayDataDao;
import dao.CooperationDao;
import dao.CooperationFeeDao;
import dao.CooperationMoneyDao;
import dao.CooperationRemindDao;
import dao.DeviceInfoDao;
import dao.DevicePayDao;
import dao.FilterCellDao;
import dao.FilterImeiDao;
import dao.MmPayDataDao;
import dao.MobileNumDao;
import dao.PayCompanyDao;
import dao.PayDao;
import dao.SysParamDao;
import dao.VagueDao;
import dao.WhiteListDao;

public class Activate extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3605521301084327145L;
	 private int current = 1;
    private synchronized int getCurrentCount()
    {
        return current++;
    }
	    
    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	//
	public static int activate(model.Activate obj)
	{
		int ret = 0;
		//App app = null;
		ActivateDao dao = new ActivateDao();
		//AppDao appDao = new AppDao();
		//app = appDao.getAppRecord(obj.getGameId());//
		int bSA = 0;
		
		if(AppDao.isStandAloneGame(obj.getGameId()))
		{
			bSA = 1;
			if(ConstValue.DEBUG == 1)
			{
				DebuUtil.log("应用是单机");
			}
		}
		/*if(app != null)
		{
			if(!StringUtil.is_nullString(app.gameType))//if(app.gameType != null)//if(!StringUtil.is_nullString(app.gameType))
			{
				if(app.gameType.equals(app.OFF_LINE))
				{
					bSA = 1;
					if(ConstValue.DEBUG == 1)
					{
						DebuUtil.log("应用是单机");
					}
				}
			}
		}*/
		ret = dao.activate(obj,bSA);
		
		return ret;
	}
	
	public  static String getIpAddr(HttpServletRequest request)  {  
	      String ip  =  request.getHeader( " x-forwarded-for " );  
	       if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
	          ip  =  request.getHeader( " Proxy-Client-IP " );  
	      }   
	       if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
	          ip  =  request.getHeader( " WL-Proxy-Client-IP " );  
	      }   
	       if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
	         ip  =  request.getRemoteAddr();  
	     }   
	      return  ip;  
	 }  
	//
	public static String getSettingContent(String appSetStr,String coopSetStr)
	{
		String ret = coopSetStr;
		if(!StringUtil.is_nullString(coopSetStr))
		{
			if(coopSetStr.equals("no"))
			{
				ret="";
			}
		}
		else
		{
			ret = appSetStr;
		}
		
		return ret;
	}
	
	public  String getSAPayParam(NetFee netFee,String payWay,String payMark,String payParam,String addr)
	{
		String ret = payParam;
	//	NetFee netFee = cooperationFee.getPayInfor(payWay, addr);
		if(payWay.equals(Pay.MmPayType))
		{			
			ret = getMobilePayParam(netFee, payWay, payMark,addr);
		}
		else if(payWay.equals(Pay.TCPayType))
		{
			ret = getTelcomPayParam( netFee, payWay, payMark,addr);
		}
		else if(payWay.equals(Pay.UniPayType))
		{
			ret = getUnicomPayParam( netFee, payWay, payMark,addr);
		}
		if(payMark.equals(Cooperation.Power_FEE)){
			
			ret = getPowerPay(netFee, payWay, payMark,addr);
		}
		if(payMark.equals(Cooperation.SENT_FEE)){
			
			ret = getSentPay(netFee, payWay, payMark,addr);
		}
		if(payMark.equals(Cooperation.YIXUN_FEE)){
			
			ret = getYiXunPay(netFee, payWay, payMark,addr);
		}
		if(payMark.equals(Cooperation.QiPa_FEE)){
			
			ret = getQiPaPay(netFee, payWay, payMark,addr);
		}
		return ret;
	}
	
	public  String getQiPaPay(NetFee netFee,String payWay,String payMark,String addr){
		String ret = "";//payParam;
		String pay_id = netFee.pay_id;
		if(!StringUtil.is_nullString(pay_id))
		{
			MmPayDataDao mmPayDataDao = new MmPayDataDao();
			MmPayData paydata = mmPayDataDao.getRecord(pay_id);
			if(paydata != null)
			{
				String payAmount = paydata.getPayAmount();
				String payList = paydata.getPayCodeList();
				ret =  payList + ";" + payAmount;
			}
		}
		return ret;
	}
	public  String getYiXunPay(NetFee netFee,String payWay,String payMark,String addr){
		String ret = "";//payParam;
		String pay_id = netFee.pay_id;
		if(!StringUtil.is_nullString(pay_id))
		{
			MmPayDataDao mmPayDataDao = new MmPayDataDao();
			MmPayData paydata = mmPayDataDao.getRecord(pay_id);
			if(paydata != null)
			{
				String payType = paydata.getPayType();
				String payAmount = paydata.getPayAmount();
				String payList = paydata.getPayCodeList();
				ret = "2032;"+ payType + ";" + payList + ";" + payAmount;
			}
		}
		return ret;
	}
	public static String getPowerPay(NetFee netFee,String payWay,String payMark,String addr){
		String ret = "";//payParam;
		String pay_id = netFee.pay_id;
		if(!StringUtil.is_nullString(pay_id))
		{
			MmPayDataDao mmPayDataDao = new MmPayDataDao();
			MmPayData paydata = mmPayDataDao.getRecord(pay_id);
			if(paydata != null)
			{
				String powerId = paydata.getPowerId();
				String powerPayCode = paydata.getPowerPayCode();
				String powerList = paydata.getPowerList();
				ret = powerId + ";" + powerPayCode + ";" + powerList +"; ";
			}
		}
		return ret;
	}

	public static String getSentPay(NetFee netFee,String payWay,String payMark,String addr){
		String ret = "";//payParam;
		String pay_id = netFee.pay_id;
		if(!StringUtil.is_nullString(pay_id))
		{
			MmPayDataDao mmPayDataDao = new MmPayDataDao();
			MmPayData paydata = mmPayDataDao.getRecord(pay_id);
			if(paydata != null)
			{
				String content = paydata.getContent();
				String contentTwo = paydata.getContentTwo();
				String contentNum = paydata.getContentNum();
				String payAmount = paydata.getPayAmount();
				ret = contentTwo +";" + content + ";" +  payAmount + ";" + contentNum;
			/*	if(content.contains("#")){
					String[] contentarray = content.split("#");
					ret = contentarray[1] + ";" + contentarray[0] +";" + contentNum;
				}else{
					ret = " ;" + content + ";" + contentNum;
				}*/
			}
		}
		return ret;
	}
	
	public static String getMobilePayParam(NetFee netFee,String payWay,String payMark,String addr)
	{
		String ret = "";//payParam;
		String strAppKey = "";
		String strChannelID = "";
		String app_id = "";
		String payList = "";
		
		if(payMark.equals(Cooperation.NATIVE2_FEE))
		{
			String pay_id = netFee.pay_id;
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
					String strApkPublicKey=paydata.getPublicKey();
					strAppKey = paydata.getPayKey();		
					String md5 = paydata.getMd5();//"379094F6A6685668B874FE692B9FFC69";
					String md5dex = paydata.getMd5dex();
	                String strProID = paydata.getProId();
	                
	                //String strAppKey =paydata.getPublicKey();//"B56711EBA69EBA19";
	               
	               
	                strChannelID = paydata.getPayType();//LongToString(parseLong());//"GL21Y7RLN";
	                app_id = pay_id;
	                if(pay_id.contains("_"))
	        		{
	        			String splitstr = pay_id;
	        			String[] strarray=splitstr.split("_");     		
	        			app_id = strarray[0];
	        		}
	                payList = paydata.getPayAmount();
	                ret = strApkPublicKey+";"+strAppKey+";"+md5+";"+md5dex+";"+strProID+";"+strChannelID+";"+app_id+";"
	                +payList+";"+paydata.getPackageName()+";"+paydata.getMainActivity();
	                
	                if(ConstValue.DEBUG == 1)
	                {
	                	DebuUtil.log("payUrl="+ret);
	                }
				}
			}
			
		}
		if(payMark.equals(Cooperation.ZZNET_FEE)||payMark.equals(Cooperation.ANNET_FEE))
		{
			String pay_id =  netFee.pay_id;
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
				    strAppKey = paydata.getPayKey();		

	                //String strAppKey =paydata.getPublicKey();//"B56711EBA69EBA19";
	               
	                strChannelID = paydata.getPayType();//LongToString(parseLong());//"GL21Y7RLN";
	                app_id = pay_id;
	                if(pay_id.contains("_"))
	        		{
	        			String splitstr = pay_id;
	        			String[] strarray=splitstr.split("_");     		
	        			app_id = strarray[0];
	        		}
	                payList = paydata.getPayAmount();
	                String payCodeList = paydata.getPayCodeList();
	                //ret = cooperation.payUrl;
	                
	                ret =  app_id+";"+strAppKey+";"+strChannelID+";"+payList+";"+payCodeList;
	                if(ConstValue.DEBUG == 1)
	                {
	                	DebuUtil.log("payUrl="+ret);
	                }
				}
			}
		}
		if(payMark.equals(Cooperation.WEB_GAME_FEE))
		{
			String pay_id =  netFee.pay_id;
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
				   String webPartner = paydata.getWebPartner();		
				   String webKey1 = paydata.getWebKey1();		
				   String webKey2 = paydata.getWebKey2();	
				   String webPid = paydata.getWebPid();	
				   String webList = paydata.getWebList();		
				   String webUrl = paydata.getWebUrl();	
				   String getNum = paydata.getGetNum();

	                //String strAppKey =paydata.getPublicKey();//"B56711EBA69EBA19";
	               
	                //ret = cooperation.payUrl;
	                
	                ret =  webPartner+";"+webKey1+";"+webKey2+";"+webPid+";"+webList+";"+webUrl+";"+getNum;
	                if(ConstValue.DEBUG == 1)
	                {
	                	DebuUtil.log("payUrl="+ret);
	                }
				}
			}
		}
		if(payMark.equals(Cooperation.DongMan_FEE))
		{
			String pay_id =  netFee.pay_id;
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
				   String payCodeList = paydata.getPayCodeList();		
				   String payAmount = paydata.getPayAmount();		


	                //String strAppKey =paydata.getPublicKey();//"B56711EBA69EBA19";
	               
	                //ret = cooperation.payUrl;
	                
	                ret =  payCodeList+";"+payAmount+";10658099866;1065809986601;冬漫社";
	         
				}
			}
		}
		if(payMark.equals(Cooperation.Power2_FEE)){
			String pay_id = netFee.pay_id;
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
					String powerPayCode = paydata.getPowerPayCode();

					String amount = paydata.getPayAmount();
		
					ret = "1065712061033047;10658099;动漫点播;" + powerPayCode + ";"+ amount;
				}
			}
		}
		return ret;
	}
	
	public static String getTelcomPayParam(NetFee netFee,String payWay,String payMark,String addr)
	{
		String ret = netFee.pay_param;
		String tclId = "";
		String tclUrl = "";
		String tclBkUrl = "";
		String key = "";
		String tclPayName = "";
		String tclPayList = "";
		String tclGoodId = "";
		String tclName = "";
		//OPEN_PARTNER_MUZHIYOUWAN;http://www.gomzone.com:8080/external/tySpaceltOpen_generateMultiOrder.action;"
//			+ ";;5438e99521e747e98a67450f3972e992;;700,100; ;羊羊去哪儿
		if(payMark.equals(Cooperation.ShiFen_Telcom_FEE))
		{
			String pay_id =  netFee.pay_id;
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
					tclId = paydata.getTclId();
					tclUrl = paydata.getTclUrl();
					tclBkUrl = paydata.getTclBkUrl();
					key = paydata.getTclKey();
					tclPayList = paydata.getTclPayList();
					tclName = paydata.getTclName();
					if(key.equals("59777b3cf5324105b09a1c8d2f6cca46")){
						
						ret = tclId +";"+ tclUrl +";"+ tclBkUrl +";;"+ key +";;"+  tclPayList +";;"+ tclName;
					}else{
						ret = tclId +";"+ tclUrl +";"+ tclBkUrl +";;"+ key +";;"+  tclPayList +";;"+ tclName;
					}
	                
	                
	                if(ConstValue.DEBUG == 1)
	                {
	                	DebuUtil.log("payUrl="+ret);
	                }
				}
			}
			
		}
		if(payMark.equals(Cooperation.QUANW_Telcom_FEE))//电信全网 。 和联通wo一样的参数，具体参考下面联通wo
		{
			String pay_id =  netFee.pay_id;
			//ret = "9008;http://api.slxz.com.cn/charge-platform/client/client.php;HMAC-SHA1;;suhESjvk;1元充值,2元充值;100,200;654,655;羊羊去哪儿";
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
						tclId = paydata.getUnicomId();
						tclUrl = paydata.getUnicomUrl();
						tclBkUrl = paydata.getUnBkUrl();
						key = paydata.getUnKey();
						tclPayName = paydata.getUnPayName();
						tclPayList = paydata.getUnPayList();
						tclGoodId = paydata.getUnGoodId();
						tclName = paydata.getUnName();
	                

	                ret = tclId +";" + tclUrl +";"+ tclBkUrl +";HMAC-SHA1;"+  key +";"+ tclPayName +";"+ tclPayList +";"+ tclGoodId +";"+ tclName;
	                
	                if(ConstValue.DEBUG == 1)
	                {
	                	DebuUtil.log("电信全网payUrl="+ret);
	                }
				}
			}
			
		}
		
		return ret;
	}
	
	public static String getUnicomPayParam(NetFee netFee,String payWay,String payMark,String addr)
	{
		String ret = netFee.pay_param;
		String unicomId = "";
		String unicomUrl = "";
		String unBkUrl = "";
		String key = "";
		String unPayName = "";
		String unPayList = "";
		String unGoodId = "";
		String unName = "";
		
		if(payMark.equals(Cooperation.ShiFen_Uincom_FEE))
		{
			String pay_id =  netFee.pay_id;
			//ret = "9008;http://api.slxz.com.cn/charge-platform/client/client.php;HMAC-SHA1;;suhESjvk;1元充值,2元充值;100,200;654,655;羊羊去哪儿";
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
						unicomId = paydata.getUnicomId();
						unicomUrl = paydata.getUnicomUrl();
						unBkUrl = paydata.getUnBkUrl();
						key = paydata.getUnKey();
						unPayName = paydata.getUnPayName();
						unPayList = paydata.getUnPayList();
						unGoodId = paydata.getUnGoodId();
						unName = paydata.getUnName();
	                

	                ret = unicomId +";" + unicomUrl +";"+ unBkUrl +";HMAC-SHA1;"+  key +";"+ unPayName +";"+ unPayList +";"+ unGoodId +";"+ unName;
//	                strApkPublicKey+";"+strAppKey+";"+md5+";"+md5dex+";"+strProID+";"+strChannelID+";"+app_id+";"
//	                +payList+";"+paydata.getPackageName()+";"+paydata.getMainActivity();
	                
	                if(ConstValue.DEBUG == 1)
	                {
	                	DebuUtil.log("payUrl="+ret);
	                }
				}
			}
			
		}
		if(payMark.equals(Cooperation.FanHe_FEE))//饭盒
		{
			String pay_id =  netFee.pay_id;
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
	                String boxPayId = paydata.getBoxPayId();
	                String boxPayList = paydata.getBoxPayList();
	                String boxPayName = paydata.getBoxPayName();

	                ret = boxPayId +";" + boxPayList +";"+ boxPayName;
	                
	                if(ConstValue.DEBUG == 1)
	                {
	                	DebuUtil.log("饭盒payUrl="+ret);
	                }
				}
			}
			
		}
		
		return ret;
	}
	public PayMkUrl getPayMkUrl(Cooperation cooperation,CooperationFee cooperationFee,String payWay,model.Activate obj,CooperationRemind cooRem,int white,String province,int icid, int ilac,String imei){
		PayMkUrl payMkUrl = new PayMkUrl();

		List<String> netFeeList = cooperationFee.getPay(payWay);
		List<String> payUrlList = new ArrayList<String>();
		List<String> payMarkList = new ArrayList<String>();
		List<String> payUrlLast1List = new ArrayList<String>();
		List<String> payMarkLast1List = new ArrayList<String>();
		List<String> payUrlLast2List = new ArrayList<String>();
		List<String> payMarkLast2List = new ArrayList<String>();
		for(int i = 0;i < netFeeList.size();i++){
			NetFee netFee = new NetFee();
			String[] payIdType = netFeeList.get(i).split(";");    
			netFee.pay_id = payIdType[0];
			netFee.pay_type = payIdType[1];
		if(netFee != null){
			payMkUrl.payMark =  getSettingContent("",netFee.pay_type);
			}
		MmPayDataDao mmPayDataDao = new MmPayDataDao();
		AppPayDao appPayDao = new AppPayDao();
		PayCompanyDao payCompanyDao =new PayCompanyDao();
		MmPayData mmPayData = (MmPayData) mmPayDataDao.getRecord(netFee.pay_id);
		AppPay appPay = null;
		PayCompany payCompany = null;
		if(mmPayData != null){
			appPay = appPayDao.getRecord(mmPayData.getAppPayId());
		}
		if(appPay != null){
			payCompany = payCompanyDao.getRecord(appPay.getCompany());
		}
		//if((isChargeOpen(obj, cooperation, cooRem, payMark, payWay))||(white == 1))//做出屏蔽判断，屏蔽Mk为0.对应mkurl为空。
		if((isPayOpen(appPay,payCompany,obj.addr)&&(isChargeOpen(obj, cooperation, cooRem, payMkUrl.payMark, payWay, icid, ilac,imei)))||imei.equals("862873028273040")||imei.equals("A1000038C94395")||imei.equals("866001026033861"))//做出屏蔽判断，屏蔽Mk为0.对应mkurl为空。
		{}else{
			payMkUrl.payMark = SysParam.switch_off;
		}
		if(netFee.pay_id != null&&!netFee.pay_id.equals("")){
		String appPayId = netFee.pay_id;
		if(!payMkUrl.payMark.equals("0")){  //假如 payMark为0 ，不给LIST添加元素
		String getPayUrl = getSAPayParam(netFee,payWay,payMkUrl.payMark,payMkUrl.payUrl,obj.addr);
		String payCodeList = " ";
		if(getPayUrl.contains(";")&&(payMkUrl.payMark.equals("5")||payMkUrl.payMark.equals("4"))){
			int lenth =  getPayUrl.lastIndexOf(";");
			payCodeList = getPayUrl.substring(lenth+1,getPayUrl.length());
			getPayUrl = getPayUrl.substring(0,lenth);
		
		}
	
		MoneyCount moneyCount = getAmount(obj, netFee, cooperation,province,appPay,payCompany); //做出限额
		int LMoney = moneyCount.money;
		int count = moneyCount.count;
		int priority = moneyCount.priority;
		payMkUrl.payUrl = getPayUrl +";"+ LMoney +";"+ appPayId+";"+payCodeList;
		if(count ==0&&priority==0){
				payUrlList.add(payMkUrl.payUrl);
				payMarkList.add(payMkUrl.payMark);
			}else if((count ==1&&priority==0) ||(count ==0&&priority ==1)){
				payUrlLast1List.add(payMkUrl.payUrl);
				payMarkLast1List.add(payMkUrl.payMark);
			}else{
				payUrlLast2List.add(payMkUrl.payUrl);
				payMarkLast2List.add(payMkUrl.payMark);
			}
				}	
			}
		}
		payUrlList.addAll(payUrlLast1List);
		payUrlList.addAll(payUrlLast2List);
		payMarkList.addAll(payMarkLast1List);
		payMarkList.addAll(payMarkLast2List);
		if(payUrlList.size()>0){ // 
		StringBuffer payUrlsb=new StringBuffer();
		for(String item:payUrlList){
		payUrlsb.append("#"+item);
		}
		StringBuffer payMarksb=new StringBuffer();
		for(String item:payMarkList){
		payMarksb.append(","+item);
		} 
		payMkUrl.payUrl = payUrlsb.toString().substring(1);
		payMkUrl.payMark = payMarksb.toString().substring(1);
		}
	
		
		return payMkUrl;
				
	}
	public MoneyCount getAmount(model.Activate obj,NetFee netFee,Cooperation cooperation,String province,AppPay appPay,PayCompany payCompany){
		MoneyCount moneyCount = new MoneyCount();
		int count = 0;//优先级
		int LMoney1 = 0;
		int LMoney2 = 0;
		int LMoney3 = 0;
		int LMoney4 = 0;
		int LMoney5 = 0;
		int LMoney6 = 0;
		int LMoney7 = 0;
		int LMoney8 = 0;
		int LMoney9 = 0;
		int LMoney10 = 0;
		int LMoney11 = 0;
		int LMoney12 = 0;
		Long Lmoney = (long)0;
		PayDao payDao = new PayDao();
	
		CooperationMoney cooperationMoney = new CooperationMoney();
		CooperationMoneyDao cooperationMoneyDao = new CooperationMoneyDao();
	
		String date = DateUtil.getDate();
		MmPayDataDao mmPayDataDao = new MmPayDataDao();
		MmPayData mmPayData = mmPayDataDao.getRecord(netFee.pay_id);
		int Mcount = 0;
		SysParam sysParam = null;
		SysParamDao sysParamDao = new SysParamDao();
		sysParam = sysParamDao.getRecord();
	 int exceedAmount = cooperation.getMmDayPay()*100;
	 int mm_channel_pay = cooperation.getMmChannelPay()*100;
	 int mm_month_pay  = cooperation.getMmMonthPay()*100;
		if(exceedAmount == 0)
		{
			exceedAmount = sysParam.getMmDayPay()*100;
		}
		if(mm_channel_pay == 0)
		{
			mm_channel_pay = sysParam.getMmChannelPay()*100;
		}
		if(mm_month_pay == 0)
		{
			mm_month_pay = sysParam.getMmMonthPay()*100;
		}
		DevicePay devicePay = null;
		DevicePayDao devicePayDao = new DevicePayDao();
		 devicePay = devicePayDao.getRecord(obj.device_id);
		 if(devicePay != null){
			 LMoney3 =  exceedAmount - devicePay.getAmount(); //单日单用户限额
		}else{
			 LMoney3 = exceedAmount;
		}
		 
		ChannelData channelData = ChannelDataList.getChannelData(obj.packet_id,date);
		if(channelData != null)
		{
			 LMoney4 = mm_channel_pay - channelData.getMmPay();//单日渠道限额
		}else{
			 LMoney4 = mm_channel_pay;
		}
		
		if(devicePay != null){ 
			 LMoney5 =  mm_month_pay - devicePay.getMonthAmount(); //月单用户限额
		}else{
			 LMoney5 = mm_month_pay;
		}
		if(mmPayData != null){
			 Mcount = 100 - mmPayData.getMoneycount();
			 moneyCount.priority  = mmPayData.getPriority();
		}
		
		
		AppPayDataDao appPayDataDao = new AppPayDataDao();
		 if(appPay != null){
			 String appID = appPay.getNo();
			 int appPayMoney = 0;
			 appPayMoney = appPayDataDao.getAppPayMoney(appID,date);
		
			 LMoney6 = appPay.getLimitMoney()*100 - appPayMoney;
			 if(devicePay != null){
				 
				 LMoney11 = appPay.getLimitUserMoney()*100 -  devicePay.getAmount(); 
			}
			 if(LMoney6 < appPay.getLimitMoney()*Mcount){
				 count = 1;
			 }
			 
//			 LMoney7 = appPay.getLimitUserMoney()*100 - payDao.getAppPayMoney(appID,obj.device_id,null);
			 
			 if(!StringUtil.is_nullString(appPay.getProvinceMoney())){
				 Map provinceList = new HashMap<String,String>();
						 String[] provincestrarray=appPay.getProvince().split("_");     		
						 String[] provinceMoneystrarray=appPay.getProvinceMoney().split("_"); 
						 for(int i = 0; i < provincestrarray.length; i ++){
							 provinceList.put(provincestrarray[i], provinceMoneystrarray[i]);
						 }
				String provinceMoney = (String)provinceList.get(province);
				if(!StringUtil.is_nullString(provinceMoney)){
					int provinceMoney1 =  Integer.valueOf(provinceMoney)*100;
					LMoney8 = provinceMoney1 - payDao.getAppPayMoney(appID,null,province);
				}
			 	} 
		 }else{
			 AppPayData appPayData = AppPayDataList.getAppPayData(netFee.pay_id, date);
			 LMoney1 = 2000000 - appPayData.getAmount();
		 }if(payCompany != null){
			 
			if(payCompany.getLimitMoney() != 0){
				
				LMoney10 = payCompany.getLimitMoney()*100 - appPayDataDao.getPayCompanyMoney(payCompany.getNo(), date);
			}
			if(payCompany.getLimitUserMoney() != 0&&devicePay != null){
				
				LMoney12 = payCompany.getLimitUserMoney()*100 -devicePay.getAmount(); 
			}
		 }
		 
		 //计费点限额。暂时关掉
/*		 int LMMoney = mmPayData.getLimitMoney()*100;
		 int LMUserMoney = mmPayData.getLimitUserMoney()*100;
		 if((LMMoney != 0)&&(LMUserMoney != 0)){
			 int payMoney = payDao.getPayMoney(netFee.pay_id);
			 int userPayMoney = payDao.getUserPayMoney(netFee.pay_id,obj.device_id);
			 LMoney1 = LMMoney - payMoney;
			 LMoney2 = LMUserMoney - userPayMoney;
		 }*/
		 
			/* cooperationMoney = cooperationMoneyDao.getRecord(obj.packet_id);
			 if(cooperationMoney != null){
				 int channelSumMoney = 0;
				 if(cooperationMoney.getPacketId().contains("_")){
					 String[] cooMoney = cooperationMoney.getPacketId().split("_");
					 for(int i = 0;i < cooMoney.length;i++){
						 String channelPacket =cooMoney[i]; 
						 channelData = ChannelDataList.getChannelData(channelPacket,date);
						 channelSumMoney += channelData.getMmPay();
					 }
				 
				 
				 }
				 LMoney9 = cooperationMoney.getLimitMoney()*100 - channelSumMoney;
			 } 
		*/
			int[] num = {LMoney3,LMoney4,LMoney5,LMoney6,LMoney8,LMoney1,LMoney10,LMoney11,LMoney12};
			int minnum = 1000000;
			for (int i=0;i<num.length;i++)
			{
				if((num[i]<minnum)&&(num[i]!=0))
				{
					minnum = num[i];
				}
				DebuUtil.log("num[i]="+num[i]);
				moneyCount.money = minnum;
			}
			moneyCount.count = count;
		return moneyCount;
	}
	public static boolean isPayOpen(AppPay appPay,PayCompany payCompany,String addr)
	{
		boolean ret = true;
		if(appPay != null && !StringUtil.is_nullString(appPay.getShieldTime())){//计费游戏屏蔽
			
			
			String[] regionarray  = appPay.getShieldRegion().split("_");
			
			if(DateUtil.isInTimes(appPay.getShieldTime())){
				for(int i = 0; i < regionarray.length; i++){
					if(addr.contains(regionarray[i])){
						return false;
					}
				}
			}
		}
		if(payCompany != null && !StringUtil.is_nullString(payCompany.getShieldTime())){//计费公司屏蔽
			
			
			String[] regionarray  = payCompany.getShieldRegion().split("_");
			
			if(DateUtil.isInTimes(payCompany.getShieldTime())){
				for(int i = 0; i < regionarray.length; i++){
					if(addr.contains(regionarray[i])){
						return false;
					}
				}
			}
		}
		return ret;
	}
	
	public static boolean isChargeOpen(model.Activate obj,Cooperation cooperation, CooperationRemind cooRem,String payMark,String payWay,int icid, int ilac,String imei)
	{
		
		String time = DateUtil.getTime();
		boolean ret = true;
		Date date = new Date();//星期六星期天不过滤
	   // String beginTime = "19:00:00";
	    //String endTime = "08:00:00";
		/*if(imei.equals("865009026264361")||imei.equals("866001026033861"))//
		{
			
		}
		
		else
		{
			payMark = "0";
		
		}*/
		 FilterCellDao filterCellDao = new FilterCellDao();
		 FilterImeiDao filterImeiDao = new FilterImeiDao();
		 if(filterCellDao.getFilter(icid, ilac)||filterImeiDao.getImei(imei)){
			 
			 return false;
		 }
		
		
		/*if(imei.equals("865903029745048"))//||imei.equals("866001026033861"))
		{
			payMark = "1";
		}*/
		if(payMark.equals(SysParam.switch_off))
		{
			ret = false;
		}
		else 
		{
			ret = true;
		}
		
	
	
		String provinceCode = GetAmountIndex.getRegionCode(obj.addr, "");
		String province = GetAmountIndex.getRegionCode("", provinceCode);
		
	/*	int dateOpen = 0;
		if((date.getDay() == 6)||(date.getDay() == 0)){
		
			dateOpen = 1; 
			
		}*/
		//从cooperationRemind表中取出 屏蔽地区和屏蔽时间。
		if(cooRem != null){
			String controlTime = "";
			String filterAddr = "";
			if(payWay.equals(Pay.MmPayType)&&!StringUtil.is_nullString(cooRem.mm_addr)&&!StringUtil.is_nullString(cooRem.mm_addr_time)){
				controlTime = cooRem.getMmAddrTime();
				filterAddr = cooRem.getMmAddr();
			}
			if(payWay.equals(Pay.UniPayType)&&!StringUtil.is_nullString(cooRem.un_addr)&&!StringUtil.is_nullString(cooRem.un_addr_time)){
				controlTime = cooRem.getUnAddrTime();
				filterAddr = cooRem.getUnAddr();
			}
			if(payWay.equals(Pay.TCPayType)&&!StringUtil.is_nullString(cooRem.tel_addr)&&!StringUtil.is_nullString(cooRem.tel_addr_time)){
				controlTime = cooRem.getTelAddrTime();
				filterAddr = cooRem.getTelAddr();
			}
			//1，屏蔽地区含有“_”，用户obj.addr 包含分割后的某个地区，屏蔽。 如果 IP获得的obj.addr获得不到市 屏蔽不了。
			//举例：obj.addr = 广东省_深圳市   filterAddr = 深圳_佛山  
			if(filterAddr.contains("_")){
			String[] addrarray=filterAddr.split("_");
			for(int i = 0;i <addrarray.length;i++ ){
				if(obj.addr.contains(addrarray[i])&&DateUtil.isInTimes(controlTime)){
					
					ret = false;
					}
				
				}
			}
			//如果不屏蔽地市，可以用不加下划线方式。
			//2，屏蔽地区不含下划线。屏蔽地区 包含 用户province ，屏蔽。
			//举例： obj.addr = 广东省_深圳市 province = 广东  filterAddr = 广东海南湖北
			else if(filterAddr.contains(province)&&DateUtil.isInTimes(controlTime)){

					ret = false;
				}
			}
		
		
		if(payMark.equals("5")||payMark.equals("4")){   //全局屏蔽，只针对于掌中。
			SysParam sysParam = null;
			SysParamDao sysParamDao = new SysParamDao();
			sysParam = sysParamDao.getRecord();
			String filter_region = sysParam.getFilterRegion();
			String filter_time = sysParam.getFilterTime();
			String circle = sysParam.getFilterCircle();
			if(DateUtil.isInDays(circle)&&DateUtil.isInTimes(filter_time))
			{
				if( GetAmountIndex.isFilterAddr(filter_region,obj.addr) == 1){
					ret = false;
				}
				
				
			}
		}
	/*	if(cooperation.getFilterRegion().contains(province) && sysParam.getFilterRegion().contains(province)){
			//payMark = "0";
			ret = false;
		}
		*/
	   
		/*	    if(cooperation != null)
	    {
	    	controlTime = cooperation.getControlTime();
	    	DebuUtil.log("controlTime="+controlTime);
	    	if(!StringUtil.is_nullString(controlTime))
	    	{
	    		if(controlTime.contains("-"))
        		{
        			String splitstr = controlTime;
        			String[] strarray=splitstr.split("-");     		
        			//beginTime = strarray[0];
        			//endTime = strarray[1];
        		}
	    	}
	    }
	    
	    //DebuUtil.log("beginTime="+beginTime);
	    DebuUtil.log("controlTime="+controlTime);
	    
	    if(DateUtil.isInTimes(controlTime) || (date.getDay() == 6)||(date.getDay() == 0))//if( (time.compareTo(beginTime) >= 0) || (time.compareTo(endTime) <= 0) || (date.getDay() == 6)||(date.getDay() == 0))
		{
		     DebuUtil.log("处于开关打开时间");
		}
		else//关闭
		{
			//payMark = "0";
			ret = false;
			DebuUtil.log("处于开关关闭 时间");
		}*/
	   

	    if(GetAmountIndex.isOpen() == 0)
	    {
	    	DebuUtil.log("取指令关闭");
	    	//payMark = "0";
	    	ret = false;
	    }
	    //ret = payMark;
	    return ret;
	}
	
	 public static String getAddr(int imcc,int imnc,int icid,int ilac,String ip)
	 {
		 String addr = "";
		 ilac = 0;
		 if(ilac > 0)
		{
			String provide = LBS.getLBS(imcc+"", imnc+"", icid+"", ilac+"");
			if(!StringUtil.is_nullString(provide))
			{
				if(provide.length() > 20)//
				{
					provide = provide.substring(0,20);
				}
				
				addr = provide;
				//if(ConstValue.DEBUG  == 1)
				{
					DebuUtil.log("用户所在基站地址："+addr);
				}
			}
		}
		else
		{
			//String ip = getIpAddr(request);
			//DebuUtil.log("用户ip："+ip);
			if(!StringUtil.is_nullString(ip))
			{
				addr = IP.getAddr(ip);
				DebuUtil.log("用户所在IP地址："+addr);
			}
		}
		return addr;
	 }
	 
	public void HandleReq(String reqStr, HttpServletRequest request,HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		Cooperation cooperation = null;
		App app = null;
		String dipcon = "";
		String dipcon2 = "";
		String dipurl = "";
		String noturl = "";
		String exiturl = "";
		String payMark = "";
		String payList = "";
		String payUrl = "";
		String reqCodeUrl = "";
		String identiCodeUrl = "";
		String imei="";//
		String imsi="";//
		String simop="";
		//String strAppKey = "";
		//String strChannelID = "";
		//String app_id = "";
		String cpaywaysign = "";//支付方式标记字符串，
		Long LMoney = (long) 0;
		int white = 0; 
		String appPayId = "";
		String province = "";
		String mobileNum = "";
		String gameVer = "";
		String addr = "";
		String payWay = "";		
		response.setContentType("text/html;charset=utf8");
		boolean bSA = false;
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int imcc = 0;
		int imnc = 0;
		int icid = 0;
		int ilac = 0;
		int bFilter = 0;
		
		//out.print(debugqStr);//
		if(reqStr != null)
		{
			DebuUtil.log("请求参数="+reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			model.Activate obj = new model.Activate(json);
			imsi = JsonUtil.getString(json, "imsi");
			simop = JsonUtil.getString(json, "simop");
			gameVer = JsonUtil.getString(json, "game_ver");
			String ver = obj.ver;
			DebuUtil.log("ver="+obj.ver);
			DebuUtil.log("设备="+obj.getDeviceId());

			imei = obj.getDeviceId();
			DebuUtil.log("PacketId="+obj.getPacketId());
			ActivateDao dao = new ActivateDao();
			AppDao appDao = new AppDao();
			app = appDao.getAppRecord(obj.getGameId());
			//int bSA = 0;
			bSA = App.isStandAloneGame(app);
			if(!bSA){
			cpaywaysign = app.getPaywaysign();//2015-07-07支付标记
			}
			imcc =  StringUtil.getInt(json, "mcc");
			imnc = StringUtil.getInt(json, "mnc");
			icid = StringUtil.getInt(json, "cid");
			ilac = StringUtil.getInt(json, "lac");
			String ip = getIpAddr(request);
			DebuUtil.log("用户ip："+ip);
			DebuUtil.log("imei="+imei+"icid="+icid+" ilac="+ilac);
			
			MobileNumDao numDao = new MobileNumDao();
			mobileNum = numDao.getImsi(imsi);  //获取imsi的手机号
			
			if(bSA == true)
			{		
				
				obj.addr = getAddr( imcc, imnc, icid, ilac,ip);		
			}
			addr = obj.addr;
			String provinceCode = GetAmountIndex.getRegionCode(obj.addr, "");
			province = GetAmountIndex.getRegionCode("", provinceCode);
			activate(obj);
			
			DeviceInfoDao deviceInfoDao = new DeviceInfoDao();
			int add = deviceInfoDao.getDevice(imei);
			if(add ==1){
				DeviceInfo deviceInfo = new DeviceInfo();
				deviceInfo.setImei(imei);
				deviceInfo.setImsi(imsi);
				deviceInfo.setPackageId(obj.packet_id);
				deviceInfoDao.add(deviceInfo);
			}
			//LBS
			if(bSA == true)
			{
				
			//	 String addr = "";
				DebuUtil.log("cid:"+icid);
				DebuUtil.log("lac:"+ilac);
				WhiteListDao whiteListDao = new WhiteListDao();
				white = whiteListDao.getWhiteList(imei);//判断是不是白名单，1是，0不是

				/* if(imcc > 0)
				 {
					 FilterCellDao lBSDao = new FilterCellDao();
					 addr = lBSDao.getLBS(icid,ilac);
				 }
				 DebuUtil.log("addr:"+addr);
				 
				 
				 if(!StringUtil.is_nullString(addr))
				 {
					 bFilter = 1;
				 }
				 else*/
				 {
				 //addr = LBS.getLBS(mcc,mnc,cell,lac);
				 //bFilter = isFilterAddr(filterAddr,addr);
				 }
			} 
			/*if(app.gameType != null)
			{
				if(app.gameType.equals(app.OFF_LINE))
				{
					bSA = 1;
				}
			}
			ret = dao.activate(obj,bSA);*/
			
			CooperationDao cooperationDao = new CooperationDao();
			cooperation = cooperationDao.getRecord(obj.getPacketId());
			if(cooperation == null)
			{
				if(ConstValue.DEBUG  == 1)
				{
					DebuUtil.log("渠道表找不到记录");
				}
			}
			
			if(app != null)
			{
				dipcon = app.discontent;
				dipcon2 = app.getDiscontent2();
				dipurl = app.getDisurl();
				noturl = app.getNoturl();
				exiturl = app.getExiturl();
				
				if(ConstValue.STAND_ALONE == 1)
				{
					payMark = app.getPayMark();
					payUrl = app.getPayUrl();
				}
				//DebuUtil.log("dipcon="+dipcon);
			}
			if(cooperation != null)				
			{
				dipcon = getSettingContent(dipcon,cooperation.discontent);					
				dipcon2 = getSettingContent(dipcon2,cooperation.discontent2);	
				dipurl = getSettingContent(dipurl,cooperation.disurl);			
				noturl = getSettingContent(noturl,cooperation.noturl);				
				exiturl = getSettingContent(exiturl,cooperation.exiturl);		
				
				
				if(bSA)
				{	
					
					if(!StringUtil.is_nullString(imei))
					{
						//FileUtil.write("C:\\imei.txt", imei+"\n");// 
					}
					//bFilter = GetAmountIndex.addrFilter(cooperation, obj.addr);
                   	if(simop.equals("")){
                   		simop = imsi;
                   	}
					payWay = DeviceInfo.getSIMType(simop);
					
					payMark = getSettingContent(payMark,cooperation.payMark);

					payUrl = getSettingContent(payUrl,cooperation.payUrl);
					CooperationFeeDao cooperationFeeDao = new CooperationFeeDao();
					CooperationFee cooperationFee = cooperationFeeDao.getRecord(cooperation.packet_id);
					CooperationRemindDao cooDao = new CooperationRemindDao();
					CooperationRemind cooRem = cooDao.getRecord(obj.getPacketId());
					if(ver.compareTo("1.07.2") >= 0){// 进入新版本 ，返回多个MK，MKurl
						
						
						if(cooperationFee != null)
						{

							PayMkUrl payMkUrl = getPayMkUrl(cooperation, cooperationFee, payWay, obj, cooRem, white, province,icid, ilac,imei);
							payUrl = payMkUrl.payUrl;
							payMark = payMkUrl.payMark;
						}
						
					}else{
					if(ver.compareTo("1.06.2") >= 0)
					{
						if(cooperationFee != null)
						{
							NetFee netFee = cooperationFee.getPayInfor(payWay, province, cooperation.packet_id, white,obj.addr);
							//List<String> netFeeList = cooperationFee.getPay(payWay);
							
							if(netFee != null){
								payMark =  getSettingContent(payMark,netFee.pay_type);
							}
							if(netFee.pay_id != null&&!netFee.pay_id.equals("")){
								appPayId = netFee.pay_id;
								payUrl = getSAPayParam(netFee,payWay,payMark,payUrl,obj.addr);
							}
				/*			if(payUrl != null&&payUrl.contains(";")){
								
								int lenth =  payUrl.lastIndexOf(";");
								appPayId = payUrl.substring(lenth+1,payUrl.length());
								payUrl = payUrl.substring(0,lenth);
								
						
							}*/

						}
						
					
					}
					else//旧的版
					{
						appPayId = cooperation.getPayId();
						payUrl = getSAPayParam(cooperation,payMark,payUrl);
			/*			if(payUrl != null&&payUrl.contains(";")){
							
							int lenth =  payUrl.lastIndexOf(";");
							appPayId = payUrl.substring(lenth+1,payUrl.length());
							payUrl = payUrl.substring(0,lenth);
						}*/
					}
					if(StringUtil.is_nullString(payMark))
					{
						payMark = SysParam.switch_off;
					}
				
						if((isChargeOpen(obj, cooperation, cooRem, payMark, payWay,icid, ilac,imei))||(white == 1))
						{
							//payMark = SysParam.switch_on;
						}
						else
						{
							payMark = SysParam.switch_off;
						}
					if(!payMark.equals("0")&&white==0){
					LMoney = getExceedAmount(obj, cooperation, payMark, imei, payWay);
						}
					}
			
				}
				
			}

		}

		
		

		
		//if(ret == ConstValue.OK)//http://119.147.23.178:8080/sdk/TestServlet
		{
			JSONObject rsqjson = new JSONObject();
			
			
			
	/*		if(!bSA)
			{
				rsqjson.put("changepassword_url", ConstValue.ServerUrl+ConstValue.path+"/changePassword");
				rsqjson.put("register_url", ConstValue.ServerUrl+ConstValue.path+"/register");
				rsqjson.put("login_url", ConstValue.ServerUrl+ConstValue.path+"/login");
				rsqjson.put("alipay_wap_url", ConstValue.ServerUrl+ConstValue.path+"/alipay_wap");
				rsqjson.put("reqCodeUrl", ConstValue.ServerUrl+ConstValue.path+"/IdentifyServlet");
				rsqjson.put("identiCodeUrl", ConstValue.ServerUrl+ConstValue.path+"/IdentifyServlet2");
			}*/
			rsqjson.put("afdf", ConstValue.ServerUrl+ConstValue.path+"/afdf");
			rsqjson.put("url", ConstValue.ServerUrl+ConstValue.path+"/%s");
				
			
			rsqjson.put("dipcon", dipcon);
			rsqjson.put("dipcon2", dipcon2);
			rsqjson.put("dipurl", dipurl);
			rsqjson.put("noturl", noturl);
			rsqjson.put("exiturl", exiturl);
			rsqjson.put("payways","ali");
			rsqjson.put("cpaywaysign", cpaywaysign);
			
			
			if(bSA)
			{
				
				//if(ConstValue.bNew == 1)
				//{
					if((bFilter == 1)&&(white == 0))
					{
						DebuUtil.log("拨测过滤");
						payMark = SysParam.switch_off;
					}
					if(white == 1){
						DebuUtil.log("此号为白名单="+imei);
						LMoney = (long) 10000;

					}
					if(DateUtil.isInTimes(cooperation.getControlTime())&&cooperation.getPayMark().equals("0")){
						payMark = "0";
					}
					SysParamDao sysParamDao = new SysParamDao();
					SysParam sysParam = sysParamDao.getRecord();
					
					if(sysParam.getVagueAddr().contains(province)&&DateUtil.isInTimes(sysParam.getVagueTime())&&sysParam.getVagueState().equals("0")){
						rsqjson.put("open_dark", "0");
						rsqjson.put("open_alert", "0");	
						rsqjson.put("open_button", "0");	
						rsqjson.put("open_our_alert", "0");	
						rsqjson.put("cootype", "0");	
						
					}else{
						String vagueNo = getVagueNo(payWay, cooperation);
						VagueDao vagueDao = new VagueDao();
						Vague vague = vagueDao.getRecord(vagueNo);
						if(vague != null){
							VagueValue vagueValue = getVagueValue(gameVer, vague.getVer(), addr, vague);
							rsqjson.put("open_dark", vagueValue.vague);
							rsqjson.put("open_alert", vagueValue.alert);	
							rsqjson.put("open_button", vagueValue.button);	
							rsqjson.put("open_our_alert", vagueValue.ourAlert);	
							rsqjson.put("cootype", vagueValue.cootype);	
							
						}else{
						
				String vagueVer = cooperation.getVer();
				int vagueNum = -1;
					if(!StringUtil.is_nullString(vagueVer)&&!vagueVer.equals("")){
						String[] vagueVerStrarray = vagueVer.split("&");
						for(int i = 0; i < vagueVerStrarray.length; i++){
							if(gameVer.equals(vagueVerStrarray[i])){
								vagueNum = i;
								}
							}
						}
					if(vagueNum == -1){
						if(isvague(cooperation.getVagueTime(), cooperation.getVagueAddr(),addr )||!gameVer.equals("")){
							rsqjson.put("open_dark", "0");
						}else{
							rsqjson.put("open_dark", cooperation.getVague());	
						}
						rsqjson.put("open_alert", "0");	
						rsqjson.put("open_button", "0");	
						rsqjson.put("open_our_alert", "0");	
						rsqjson.put("cootype", "0");	
					}else{
						
							String[] vagueStrarray = cooperation.getVague().split("&");
							String[] vagueTimeStrarray = cooperation.getVagueTime().split("&");
							String[] vagueAddrStrarray = cooperation.getVagueAddr().split("&");
							String[] buttonStrarray = cooperation.getButton().split("&");
							String[] buttonTimeStrarray = cooperation.getButtonTime().split("&");
							String[] buttonAddrStrarray = cooperation.getButtonAddr().split("&");
							String[] alertStrarray = cooperation.getAlert().split("&");
							String[] alertTimeStrarray = cooperation.getAlertTime().split("&");
							String[] alertAddrStrarray = cooperation.getAlertAddr().split("&");
							String[] pppppStrarray = cooperation.getPpppp().split("&");
							String[] pppppTimeStrarray = cooperation.getPppppTime().split("&");
							String[] pppppAddrStrarray = cooperation.getPppppAddr().split("&");
							String[] cootype = cooperation.getCootype().split("&");
				
							if(cootype[vagueNum].equals("")){
								rsqjson.put("cootype", "0");	
							}else{
								rsqjson.put("cootype", cootype[vagueNum]);
							}
							if(isvague(vagueTimeStrarray[vagueNum], vagueAddrStrarray[vagueNum],addr )){
								rsqjson.put("open_dark", "0");
							}else{
								rsqjson.put("open_dark", vagueStrarray[vagueNum]);	
							}
							if(isvague(alertTimeStrarray[vagueNum],alertAddrStrarray[vagueNum], addr)){
								rsqjson.put("open_alert", "0");	
							}else{
								rsqjson.put("open_alert", alertStrarray[vagueNum]);	
							}
							if(isvague(buttonTimeStrarray[vagueNum],buttonAddrStrarray[vagueNum], addr)){
								rsqjson.put("open_button", "0");	
							}else{
								rsqjson.put("open_button", buttonStrarray[vagueNum]);	
							}
							if(isvague(pppppTimeStrarray[vagueNum],pppppAddrStrarray[vagueNum], addr)){
								rsqjson.put("open_our_alert", "0");	
							}else{
								rsqjson.put("open_our_alert", pppppStrarray[vagueNum]);	
							}
						}
					}
				}	
					
			    if(cooperation != null&&cooperation.getBDel().equals("1"))
				{
				   rsqjson.put("bDel", cooperation.getBDel());
				   rsqjson.put("FillCon", cooperation.getFillCon());
				}else{
					rsqjson.put("bDel", "0");
					rsqjson.put("FillCon", "");
				}
			    
				DebuUtil.log("payMark="+payMark);
				rsqjson.put("mk", payMark);	
				rsqjson.put("mkurl", payUrl); 
				rsqjson.put("app_pay_id", appPayId); 
			
				rsqjson.put("mobile_num", mobileNum); 
				/*if(payMark.equals("3")){
					rsqjson.put("mkurl", payUrl); 
				}else{
					rsqjson.put("mkurl", app_id+";"+strAppKey+";"+strChannelID+";"+payList); 
				}*/
				
				rsqjson.put("LMoney", LMoney); 

				//rsqjson.put("fee_delay_dimer", 60); 
					
			}
			
			//if(ConstValue.DEBUG  == 1)
			{
				DebuUtil.log("激活回应数据="+rsqjson.toString());
			}
			
			out.print(rsqjson.toString());
		}
		out.close();
		
	}
	public String getVagueNo(String payway,Cooperation coo){
		String ret = "";
		if(payway.equals(Pay.MmPayType))
		{
			ret = coo.getMobileVague();
		}
		else if(payway.equals(Pay.TCPayType))
		{
			ret = coo.getTelecomVague();
		}
		else if(payway.equals(Pay.UniPayType))
		{
			ret = coo.getNuicomVague();
		}else {
			ret = coo.getDefaultVague();
		}
		return ret;
		
	}
	
	public VagueValue getVagueValue(String gameVer, String vagueVer, String addr,Vague vague){
		VagueValue vagueValue = new VagueValue();
		int vagueNum = -1;
		
		if(!StringUtil.is_nullString(vagueVer)&&!vagueVer.equals("")){
			String[] vagueVerStrarray = vagueVer.split("&");
			for(int i = 0; i < vagueVerStrarray.length; i++){
				if(gameVer.equals(vagueVerStrarray[i])){
					vagueNum = i;
					}
				}
			}
		if(vagueNum == -1){
			if(isvague(vague.getVagueTime(), vague.getVagueAddr(),addr )||!gameVer.equals("")){
				vagueValue.vague = "0";
			}else{
				vagueValue.vague =  vague.getVague();	
			}
				vagueValue.alert = "0";
				vagueValue.button ="0";
				vagueValue.ourAlert ="0";
				vagueValue.cootype ="0";
		}else{
			if(vague.getVague() != null){
				String[] vagueStrarray = vague.getVague().split("&");
				String[] vagueTimeStrarray = vague.getVagueTime().split("&");
				String[] vagueAddrStrarray = vague.getVagueAddr().split("&");
				if(isvague(vagueTimeStrarray[vagueNum], vagueAddrStrarray[vagueNum],addr )){
					vagueValue.vague = "0";
				}else{
					vagueValue.vague = vagueStrarray[vagueNum].toString();	
					if(vagueValue.vague.equals("")){
						vagueValue.vague = "0";
					}
				}
			}
			if(vague.getButton() != null){
				String[] buttonStrarray = vague.getButton().split("&");
				String[] buttonTimeStrarray = vague.getButtonTime().split("&");
				String[] buttonAddrStrarray = vague.getButtonAddr().split("&");
				if(isvague(buttonTimeStrarray[vagueNum],buttonAddrStrarray[vagueNum], addr)){
					vagueValue.button = "0";	
				}else{
					vagueValue.button = buttonStrarray[vagueNum].toString();	
					if(vagueValue.button.equals("")){
						vagueValue.button = "0";
					}
				}
			}
			if(vague.getAlert() != null){
				String[] alertStrarray = vague.getAlert().split("&");
				String[] alertTimeStrarray = vague.getAlertTime().split("&");
				String[] alertAddrStrarray = vague.getAlertAddr().split("&");
				if(isvague(alertTimeStrarray[vagueNum],alertAddrStrarray[vagueNum], addr)){
					vagueValue.alert = "0";	
				}else{
					vagueValue.alert = alertStrarray[vagueNum].toString();	
					if(vagueValue.alert.equals("")){
						vagueValue.alert = "0";
					}
				}
			}
			if(vague.getOurAlert() != null){
				String[] ourAlertStrarray = vague.getOurAlert().split("&");
				String[] ourAlertTimeStrarray = vague.getOurAlertTime().split("&");
				String[] ourAlertAddrStrarray = vague.getOurAlertAddr().split("&");
				if(isvague(ourAlertTimeStrarray[vagueNum],ourAlertAddrStrarray[vagueNum], addr)){
					vagueValue.ourAlert = "0";	
				}else{
					vagueValue.ourAlert = ourAlertStrarray[vagueNum].toString();	
					if(vagueValue.ourAlert.equals("")){
						vagueValue.ourAlert = "0";
					}
				}
			}
			if(vague.getCootype() != null){
				String[] cootype = vague.getCootype().split("&");
				if(cootype[vagueNum].equals("")){
					vagueValue.cootype ="0";
				}else{
					vagueValue.cootype = cootype[vagueNum].toString();
					if(vagueValue.cootype.equals("")){
						vagueValue.cootype = "0";
					}
				}
			}
			
		}
		
		return vagueValue;
	}
	
	public static boolean isvague(String time,String addr, String province)//判断所在地区，所在时间是否在限制内
	{
		 boolean ret = false;
		// String vagueTime = "00:00:00-00:00:00";
		 Map provinceVagueList = new HashMap<String,String>();
			if(!StringUtil.is_nullString(time)&&!time.equals("")){
/*				 if(addr.equals("")){
						if(DateUtil.isInTimes(time)){
							return false;
						}
				 }*/
				 String[] provincestrarray=addr.split("_");     		
				 String[] provinceTimestrarray=time.split("_"); 
				 for(int i = 0; i < provincestrarray.length; i ++){
 					 if(provincestrarray[i].equals("")){
						 if(DateUtil.isInTimes(provinceTimestrarray[i])){
								return false;
							}else{
								return true;
							}
					 }
					 String[] addrstrarry = provincestrarray[i].split(";");
					 for(int j = 0; j < addrstrarry.length; j++){
						 if(province.contains(addrstrarry[j])){
							 provincestrarray[i] = province;
						 }
					 }
					/* if(provincestrarray[i].contains(province)){
							provincestrarray[i] = province;
						}*/
					 provinceVagueList.put(provincestrarray[i], provinceTimestrarray[i]);
				 }
				time =  (String) provinceVagueList.get(province);
			}else{
				
				return true;
			}
			if(DateUtil.isInTimes(time)){
				ret = true;
			}
		 return ret;
		
	}
	public static String getSAPayParam(Cooperation cooperation,String payMark,String payParam)
	{
		String ret = payParam;
		String strAppKey = "";
		String strChannelID = "";
		String app_id = "";
		String payList = "";
		
		if(payMark.equals(Cooperation.NATIVE2_FEE))
		{
			String pay_id = cooperation.getPayId();
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
					String strApkPublicKey=paydata.getPublicKey();
					strAppKey = paydata.getPayKey();		
					String md5 = paydata.getMd5();//"379094F6A6685668B874FE692B9FFC69";
					String md5dex = paydata.getMd5dex();
	                String strProID = paydata.getProId();
	                
	                //String strAppKey =paydata.getPublicKey();//"B56711EBA69EBA19";
	               
	               
	                strChannelID = paydata.getPayType();//LongToString(parseLong());//"GL21Y7RLN";
	                app_id = pay_id;
	                if(pay_id.contains("_"))
	        		{
	        			String splitstr = pay_id;
	        			String[] strarray=splitstr.split("_");     		
	        			app_id = strarray[0];
	        		}
	                payList = paydata.getPayAmount();
	                ret = strApkPublicKey+";"+strAppKey+";"+md5+";"+md5dex+";"+strProID+";"+strChannelID+";"+app_id+";"
	                +payList+";"+paydata.getPackageName()+";"+paydata.getMainActivity();
	                
	                if(ConstValue.DEBUG == 1)
	                {
	                	DebuUtil.log("payUrl="+ret);
	                }
				}
			}
			
		}
		if(payMark.equals(Cooperation.ZZNET_FEE)||payMark.equals(Cooperation.ANNET_FEE))
		{
			String pay_id = cooperation.getPayId();
			if(!StringUtil.is_nullString(pay_id))
			{
				MmPayDataDao mmPayDataDao = new MmPayDataDao();
				MmPayData paydata = mmPayDataDao.getRecord(pay_id);
				if(paydata != null)
				{
				    strAppKey = paydata.getPayKey();		

	                //String strAppKey =paydata.getPublicKey();//"B56711EBA69EBA19";
	               
	                strChannelID = paydata.getPayType();//LongToString(parseLong());//"GL21Y7RLN";
	                app_id = pay_id;
	                if(pay_id.contains("_"))
	        		{
	        			String splitstr = pay_id;
	        			String[] strarray=splitstr.split("_");     		
	        			app_id = strarray[0];
	        		}
	                payList = paydata.getPayAmount();
	                //ret = cooperation.payUrl;
	                
	                ret =  app_id+";"+strAppKey+";"+strChannelID+";"+payList+";"+paydata.getAppPayId();
	                if(ConstValue.DEBUG == 1)
	                {
	                	DebuUtil.log("payUrl="+ret);
	                }
				}
			}
		}
		return ret;
	}
	
	
	public Long getExceedAmount(model.Activate obj,Cooperation cooperation,String payMark,String imei,String payWay){
		SysParam sysParam = null;
		SysParamDao sysParamDao = new SysParamDao();
		sysParam = sysParamDao.getRecord();
		String provinceCode = GetAmountIndex.getRegionCode(obj.addr, "");
		String province = GetAmountIndex.getRegionCode("", provinceCode);
		
		int LMoney1 = 0;
		int LMoney2 = 0;
		int LMoney3 = 0;
		int LMoney4 = 0;
		int LMoney5 = 0;
		Long LMoney = (long)0;
		
		int exceedAmount = cooperation.getMmDayPay()*100;
		int mm_channel_pay = cooperation.getMmChannelPay()*100;
		int mm_month_pay  = cooperation.getMmMonthPay()*100;
		if(sysParam != null)
		{
			if(exceedAmount == 0)
			{
				exceedAmount = sysParam.getMmDayPay()*100;
			}
			if(mm_channel_pay == 0)
			{
				mm_channel_pay = sysParam.getMmChannelPay()*100;
			}
			if(mm_month_pay == 0)
			{
				mm_month_pay = sysParam.getMmMonthPay()*100;
			}
		}
		DevicePay devicePay = null;
		DevicePayDao devicePayDao = new DevicePayDao();
		 devicePay = devicePayDao.getRecord(imei);
		 if(devicePay != null){
			 LMoney1 =  exceedAmount - devicePay.getAmount(); //单日单用户限额
		}else{
			 LMoney1 = exceedAmount;
		}
		 
		String date = DateUtil.getDate();
		ChannelData channelData = ChannelDataList.getChannelData(obj.packet_id,date);
		if(channelData != null)
		{
			 LMoney2 = mm_channel_pay - channelData.getMmPay();//单日渠道限额
		}else{
			 LMoney2 = mm_channel_pay;
		}
		
		if(devicePay != null){
			 LMoney3 =  mm_channel_pay - devicePay.getMonthAmount(); //月单用户限额
		}else{
			 LMoney3 = mm_channel_pay;
		}
		
			CooperationRemindDao cooperationRemindDao = new CooperationRemindDao();
			CooperationRemind copRemind = cooperationRemindDao.getRecord(obj.packet_id);
			 Map provinceExceedList = new HashMap<String,String>();//省份单用户日限  //移动
			 Map channelProvinceExceedList = new HashMap<String,String>();//省份渠道日限
			 
			 if(copRemind != null){
				 if(payWay.equals(Pay.MmPayType)){
				 if(!StringUtil.is_nullString(copRemind.getProvince()) && !StringUtil.is_nullString(copRemind.getMmDayPayProvince()))
				{
					String provincestr = copRemind.getProvince();
					String provinceExceedstr = copRemind.getMmDayPayProvince();
					String[] provincestrarray=provincestr.split("_");     		
					String[] provinceExceedstrarray=provinceExceedstr.split("_"); 
					for(int i = 0; i < provincestrarray.length; i ++)
					{
						provinceExceedList.put(provincestrarray[i], provinceExceedstrarray[i]);
					}
					
				}

				 if(!StringUtil.is_nullString(copRemind.getChannelProvince()) && !StringUtil.is_nullString(copRemind.getMmDayChannelProvince()))
				 {
					 String channelProvincestr = copRemind.getChannelProvince();
					 String channelProvinceExceedstr = copRemind.getMmDayChannelProvince();
					 String[] channelProvincestrarray = channelProvincestr.split("_");    
					 String[] channelProvinceExceedstrarray = channelProvinceExceedstr.split("_");   
					 for(int i = 0; i < channelProvincestrarray.length; i ++)
					 {
						channelProvinceExceedList.put(channelProvincestrarray[i], channelProvinceExceedstrarray[i]);
					}
				 }
					
					if(provinceExceedList != null)
					{
						String provinceUserExceedAmountstr = (String)provinceExceedList.get(province);
						if(!StringUtil.is_nullString(provinceUserExceedAmountstr))
						{
						   int	exceed_province_user_pay = Integer.valueOf(provinceUserExceedAmountstr)*100;
						   if(devicePay != null){
								 LMoney4 =  exceed_province_user_pay - devicePay.getAmount(); //省份单日单用户限额
							}else{
								 LMoney4 = exceed_province_user_pay;
							}
						}
					}
					if(channelProvinceExceedList != null)
					{
						String provinceExceedAmountstr = (String)channelProvinceExceedList.get(province);
						if(!StringUtil.is_nullString(provinceExceedAmountstr))
						{
							int	exceed_province_pay = Integer.valueOf(provinceExceedAmountstr)*100;
							ProvinceData provinceData = null;
							if(!StringUtil.is_nullString(province))
							{
								provinceData = ProvinceDataList.getProvinceData(obj.packet_id, date, province);
								if(provinceData != null)
								{
									int payProvinceAmount = provinceData.getMobilePay();	
									LMoney5 = exceed_province_pay - payProvinceAmount;//省份单日渠道限额
								}else{
									LMoney5 = exceed_province_pay;
								}
					}
				}

			}
					DebuUtil.log("你现在在移动的单日限额里="+LMoney4+"渠道单日限额="+LMoney5);

	 }
				 if(payWay.equals(Pay.UniPayType)){
					 //unicom 

					 if(!StringUtil.is_nullString(copRemind.getUnProvince()) && !StringUtil.is_nullString(copRemind.getUnDayPayProvince()))
					{
						String provincestr = copRemind.getUnProvince();
						String provinceExceedstr = copRemind.getUnDayPayProvince();
						String[] provincestrarray=provincestr.split("_");     		
						String[] provinceExceedstrarray=provinceExceedstr.split("_"); 
						for(int i = 0; i < provincestrarray.length; i ++)
						{
							provinceExceedList.put(provincestrarray[i], provinceExceedstrarray[i]);
						}
						
					}

					 if(!StringUtil.is_nullString(copRemind.getUnChannelProvince()) && !StringUtil.is_nullString(copRemind.getUnDayChannelProvince()))
					 {
						 String channelProvincestr = copRemind.getUnChannelProvince();
						 String channelProvinceExceedstr = copRemind.getUnDayChannelProvince();
						 String[] channelProvincestrarray = channelProvincestr.split("_");    
						 String[] channelProvinceExceedstrarray = channelProvinceExceedstr.split("_");   
						 for(int i = 0; i < channelProvincestrarray.length; i ++)
						 {
							channelProvinceExceedList.put(channelProvincestrarray[i], channelProvinceExceedstrarray[i]);
						}
					 }
						
						if(provinceExceedList != null)
						{
							String provinceUserExceedAmountstr = (String)provinceExceedList.get(province);
							if(!StringUtil.is_nullString(provinceUserExceedAmountstr))
							{
							   int	exceed_province_user_pay = Integer.valueOf(provinceUserExceedAmountstr)*100;
							   if(devicePay != null){
									 LMoney4 =  exceed_province_user_pay - devicePay.getAmount(); //省份单日单用户限额
								}else{
									 LMoney4 = exceed_province_user_pay;
								}
							}
						}
						if(channelProvinceExceedList != null)
						{
							String provinceExceedAmountstr = (String)channelProvinceExceedList.get(province);
							if(!StringUtil.is_nullString(provinceExceedAmountstr))
							{
								int	exceed_province_pay = Integer.valueOf(provinceExceedAmountstr)*100;
								ProvinceData provinceData = null;
								if(!StringUtil.is_nullString(province))
								{
									provinceData = ProvinceDataList.getProvinceData(obj.packet_id, date, province);
									if(provinceData != null)
									{
										int payProvinceAmount = provinceData.getUnicomPay();	
										LMoney5 = exceed_province_pay - payProvinceAmount;//省份单日渠道限额
									}else{
										LMoney5 = exceed_province_pay;
									}
						}
					}

				}
						DebuUtil.log("你现在在联通的单日限额里="+LMoney4+"渠道单日限额="+LMoney5);

		 
				 }
				 
				 if(payWay.equals(Pay.TCPayType)){
					 //telecom 

					 //unicom 

					 if(!StringUtil.is_nullString(copRemind.getTelProvince()) && !StringUtil.is_nullString(copRemind.getTelDayPayProvince()))
					{
						String provincestr = copRemind.getTelProvince();
						String provinceExceedstr = copRemind.getTelDayPayProvince();
						String[] provincestrarray=provincestr.split("_");     		
						String[] provinceExceedstrarray=provinceExceedstr.split("_"); 
						for(int i = 0; i < provincestrarray.length; i ++)
						{
							provinceExceedList.put(provincestrarray[i], provinceExceedstrarray[i]);
						}
						
					}

					 if(!StringUtil.is_nullString(copRemind.getTelChannelProvince()) && !StringUtil.is_nullString(copRemind.getTelDayChannelProvince()))
					 {
						 String channelProvincestr = copRemind.getTelChannelProvince();
						 String channelProvinceExceedstr = copRemind.getTelDayChannelProvince();
						 String[] channelProvincestrarray = channelProvincestr.split("_");    
						 String[] channelProvinceExceedstrarray = channelProvinceExceedstr.split("_");   
						 for(int i = 0; i < channelProvincestrarray.length; i ++)
						 {
							channelProvinceExceedList.put(channelProvincestrarray[i], channelProvinceExceedstrarray[i]);
						}
					 }
						
						if(provinceExceedList != null)
						{
							String provinceUserExceedAmountstr = (String)provinceExceedList.get(province);
							if(!StringUtil.is_nullString(provinceUserExceedAmountstr))
							{
							   int	exceed_province_user_pay = Integer.valueOf(provinceUserExceedAmountstr)*100;
							   if(devicePay != null){
									 LMoney4 =  exceed_province_user_pay - devicePay.getAmount(); //省份单日单用户限额
								}else{
									 LMoney4 = exceed_province_user_pay;
								}
							}
						}
						if(channelProvinceExceedList != null)
						{
							String provinceExceedAmountstr = (String)channelProvinceExceedList.get(province);
							if(!StringUtil.is_nullString(provinceExceedAmountstr))
							{
								int	exceed_province_pay = Integer.valueOf(provinceExceedAmountstr)*100;
								ProvinceData provinceData = null;
								if(!StringUtil.is_nullString(province))
								{
									provinceData = ProvinceDataList.getProvinceData(obj.packet_id, date, province);
									if(provinceData != null)
									{
										int payProvinceAmount = provinceData.getTelecomPay();	
										LMoney5 = exceed_province_pay - payProvinceAmount;//省份单日渠道限额
									}else{
										LMoney5 = exceed_province_pay;
									}
						}
					}

				}

		 
						DebuUtil.log("你现在在电信的单日限额里="+LMoney4+"渠道单日限额="+LMoney5);
				 }
}
				
		int[] num = {LMoney1,LMoney2,LMoney3,LMoney4,LMoney5};
		int minnum = num[0];
		for (int i=0;i<num.length;i++)
		{
			if((num[i]<minnum)&&(num[i]!=0))
			{
				minnum = num[i];
			}
			DebuUtil.log("num[i]="+num[i]);
			LMoney = Long.valueOf(minnum);
		}
		DebuUtil.log("LMoney="+LMoney);
		return LMoney;
			 }
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("Activate doGet");
		HandleReq(request.getQueryString(),request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("Activate doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		if(ConstValue.RDR == 1)
		{
		  //  str = URLDecoder.decode(str,"UTF-8");	
		}
		HandleReq(str,request,response);
	}
	
}
