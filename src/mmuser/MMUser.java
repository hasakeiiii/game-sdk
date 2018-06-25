package mmuser;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

import util.Rsa;
import util.StringUtil;
import model.ChannelData;
import model.Cooperation;
import model.DevModel;
import model.DeviceInfo;
import model.MmPayData;
import model.MmUser;
import net.sf.json.JSONObject;

import com.yeepay.HttpUtils;

import dao.CooperationDao;
import dao.DevModelDao;
import dao.DeviceInfoDao;
import dao.MmPayDataDao;
import dao.MmUserDao;

public class MMUser {

	//计费信息
	String packageName = "com.ldkb2.zty";//"com.pansen.zumalocal";//"com.yykjmm.xccs.apk";
	String MainActivity ="com.ldkb2.zty.ArcadeRaiden";//"mobi.shoumeng.game.demo.MainActivity";//"com.kai.popstar.PopStar";// 
	
	String channel = "3003980307";//"3003985784";
	String appid = "300008381491";//"300008844659";
	String appKey = "300008381491";//"300008844659";
	String versionName = "1.0";
	String MMversionName = "1.2.3";
	int versionCode = 1;
	
	//设备信息
	String mac = "d4:97:0b:69:c7:07";
	String deviceId = "865903029745048";
	String imsi = "460029140953268";
	String deviceDetail = "MI+3C";
	String manufacturer = "Xiaomi";
	String model = "MI 3C";
	String accessPoint = "wifi";
	int screenHeight = 1920;
	int screenDensity = 480;
	int screenWidth = 1080;
	int modelID;
	String  resolution = screenWidth+"*"+screenHeight;
	String cpuRatioCur = "1728000";	
	String menoryRatio ="1893832";
	String cpuRatioMax = "2265600";
	String languageCode = "zh";
	String countryCode = "CN";
	String carrierName = "China+Mobile";
	
	String cid = "7f9c9fae657f9ebe4a";
	String logJsonAry = "";
	
	//系统信息
	String OsVersion="4.4.4";
	String phoneOs = "android+"+OsVersion;
	String sdkVersion = "3.1.1";
	String protocolVersion = "3.1.0";
	
	//String appid = "300008702230";
	String sid = "";

	//long sendTime =1422511917406L;// System.currentTimeMillis();//1422511917327L;
	//long actTime = 1422511917320L;
	//String cid = "7f9c9fae657f9ebe4a";//cid =  String.valueOf(Rsa.getMD5(replace(deviceId)).toCharArray(), 7, 18);
	
	//int i1 = new Random().nextInt(10);
	//String paramContext="";
    //String sid = "f9585dbed037d81c";
    
	long event1_sendTime =  1422511917327L;// System.currentTimeMillis();//1422511917327L;
	long event1_logTime = 1422511917195L;//比sendTime少100毫秒
	
	long act_sendTime =1422511918591L;// System.currentTimeMillis();//1422511917327L;
	long act_logTime = 1422511917320L;
	
	long sys_sendTime = 1422511917320L;
	
	long event2_sendTime =  1422511919015L;
	long event2_logTime = 1422511917992L;
	long playTime = 0;
	
	
	public static int genRan(int min,int max)
	{
        java.util.Random r = new java.util.Random();		
		int ran = r.nextInt();
		if(ran < 0)
		{
			ran = 0- ran;
		}
		ran = ran%(max-min)+min;
		return ran;
   }
	 
   public void setDevModel(DevModel devModel) 
   {
	   deviceDetail = devModel.getDeviceDetail();
	   manufacturer = devModel.getManufacturer();
	   model = devModel.getModel();		
	   screenHeight = devModel.getScreenHeight();	
	   screenWidth = devModel.getScreenWidth();
	   resolution = screenWidth+"*"+screenHeight;
	   modelID = devModel.getId();
	   
  }
	 
  public void setAppInfor(MmPayData mmPayData) 
  {
	  if(!StringUtil.is_nullString(mmPayData.getPackageName()))
	  {
		  packageName = mmPayData.getPackageName();//"com.pansen.zumalocal";
	  }
	  if(!StringUtil.is_nullString(mmPayData.getMainActivity()))
	  {
	      MainActivity = mmPayData.getMainActivity();// "mobi.shoumeng.game.demo.MainActivity";		
	  }
	  String paytype = mmPayData.getPayType();//"0000000000";
	  String pay_id = mmPayData.getPayId();
	  if(pay_id.contains("_"))
	  {
		  String splitstr = pay_id;
		  String[] strarray=splitstr.split("_");     		
		  pay_id = strarray[0];
	  }
	  channel = paytype;
	  if(paytype.contains("_"))
	  {
		  String splitstr = paytype;
		  String[] strarray=splitstr.split("_");     		
		  channel = strarray[0];
	  }
	  appid = pay_id;
	  appKey = pay_id;
  }
  
  public static char getChar()
  {
      char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f','g', 'h', 'i','j', 'k', 'l', 'm','n', 'o', 'p', 'q', 'r', 's'
			,'t', 'u', 'v', 'w', 'x', 'y','z', 'A', 'B', 'C', 'D', 'E','F', 'G', 'H', 'I', 'J', 'K'
			,'L', 'M', 'N', 'O', 'P', 'Q','R', 'S', 'T', 'U', 'V', 'W','X', 'Y', 'Z'};
      int index = genRan(0 ,hexDigits.length-1);
      return hexDigits[index];
  }
  
   public MMUser(String imei,String imsi,String mac,String packet_id) 
   {
	   
	 this.mac = mac;
	 this.imsi = imsi;
	 this.deviceId = imei;
	 
	 event1_sendTime = System.currentTimeMillis();
	 event1_logTime = event1_sendTime - genRan(10,20);
	 
	 act_sendTime = event1_sendTime + genRan(400,500);
     act_logTime = event1_sendTime - genRan(2,10);
     
     sys_sendTime = event1_sendTime + genRan(30,40);
     
     event2_sendTime = System.currentTimeMillis() + genRan(1000,1400);
	 event2_logTime = event1_sendTime + genRan(300,400);
	   
	 if(StringUtil.is_nullString(mac))
	 {
		 this.mac = String.format("%c%c:%c%c:%c%c:%c%c:%c%c:%c%c",getChar(),getChar(),getChar(),getChar(),getChar()
				 ,getChar(),getChar(),getChar(),getChar(),getChar(),getChar(),getChar());
	 }
	  /* event1_sendTime = 1422511917327L;
	   event1_logTime = 1422511917195L;
	 
	 act_sendTime = 1422511918591L;
     act_logTime = 1422511917320L;
     
     sys_sendTime = 1422511917406L;
     
     event2_sendTime = 1422511919015L;
	 event2_logTime = 1422511917992L;*/
		 
	 cid =  String.valueOf(Rsa.getMD5(replace(deviceId)).toCharArray(), 7, 18);
	 int i1 = new Random().nextInt(10);
	 sid = String.valueOf(Rsa.getMD5(System.currentTimeMillis() + appKey + replace(deviceId)/*g.a(paramContext,deviceId)*/ + mac + i1).toCharArray(), 8, 16);//"f9585dbed037d81c";
     
	 CooperationDao cooperationDao = new CooperationDao();
	 Cooperation cop = cooperationDao.getRecord(packet_id);
	 if(cop != null)
	 {
		 String pay_id = cop.getPayId();			
		 MmPayDataDao mmPayDataDao = new MmPayDataDao();
		 MmPayData paydata = mmPayDataDao.getRecord(pay_id);
		 setAppInfor(paydata);
	 }
	 DevModelDao devModelDao = new DevModelDao();
	 DevModel devModel = devModelDao.getDevModel();
	 setDevModel(devModel);	
		 
   }
   
   public MMUser(String imei,String imsi,String mac) 
   {
	   
	 this.mac = mac;
	 this.imsi = imsi;
	 this.deviceId = imei;
	 
	 event1_sendTime = System.currentTimeMillis();
	 event1_logTime = event1_sendTime - genRan(10,20);
	 
	 act_sendTime = event1_sendTime + genRan(400,500);
     act_logTime = event1_sendTime - genRan(2,10);
     
     sys_sendTime = event1_sendTime + genRan(30,40);
     
     event2_sendTime = System.currentTimeMillis() + genRan(1000,1400);
	 event2_logTime = event1_sendTime + genRan(300,400);
	   
	  /* event1_sendTime = 1422511917327L;
	   event1_logTime = 1422511917195L;
	 
	 act_sendTime = 1422511918591L;
     act_logTime = 1422511917320L;
     
     sys_sendTime = 1422511917406L;
     
     event2_sendTime = 1422511919015L;
	 event2_logTime = 1422511917992L;*/
		 
	 cid =  String.valueOf(Rsa.getMD5(replace(deviceId)).toCharArray(), 7, 18);
	 int i1 = new Random().nextInt(10);
	 sid = String.valueOf(Rsa.getMD5(System.currentTimeMillis() + appKey + replace(deviceId)/*g.a(paramContext,deviceId)*/ + mac + i1).toCharArray(), 8, 16);//"f9585dbed037d81c";
     
   }
   
   public static void doInitAllUser_t(ArrayList<Object> objList)
   {
	   for(int i=0;i<objList.size();i++)
	   {
		   DeviceInfo deviceInfo =(DeviceInfo)objList.get(i);
		   if((!StringUtil.is_nullString(deviceInfo.getImei())) && (!StringUtil.is_nullString(deviceInfo.getImsi()))  && (!StringUtil.is_nullString(deviceInfo.getMac())) )
		   {
			   MMUser mmUser = new MMUser(deviceInfo.getImei(),deviceInfo.getImsi(),deviceInfo.getMac());
			   mmUser.initUser();
			   deviceInfo.setSid(mmUser.sid);
			   DeviceInfoDao  deviceInfoDao = new DeviceInfoDao();
			   deviceInfoDao.edit(deviceInfo);
			   
		   }
	   }
   }
   
   public static void doInitUser(String imei,String imsi,String mac,String packet_id) 
   {
	   MmUserDao mmUserDao = new MmUserDao();
	   MmUser mmUserT = mmUserDao.getRecord(imei,packet_id);
	   
	   if(mmUserT == null)
	   {
		   MMUser mmUser = new MMUser(imei,imsi,mac,packet_id);
		   mmUser.initUser();
		   
		   MmUser mmUserM = new MmUser();
		   mmUserM.setImei(imei);
		   mmUserM.setImsi(imsi);
		   mmUserM.setMac(mmUser.mac);
		   mmUserM.setModel(mmUser.modelID);
		   mmUserM.setSid(mmUser.sid);
		   mmUserM.setPacketId(packet_id);	   
		   mmUserDao.add(mmUserM);
	   }
	   else
	   {
		   MMUser mmUser = new MMUser(mmUserT.getImei(),mmUserT.getImsi(),mmUserT.getMac());
		   mmUser.doact(mmUserT.getSid());
		   mmUser.doacttime(mmUserT.getSid());
		  // doacttime(String sid)
	   }
	   
   }
   
   public static void doInitAllUser(ArrayList<Object> objList)//成批刷启动用户数
   {
	   for(int i=0;i<objList.size();i++)
	   {
		   DeviceInfo deviceInfo =(DeviceInfo)objList.get(i);
		   if((!StringUtil.is_nullString(deviceInfo.getImei())) && (!StringUtil.is_nullString(deviceInfo.getImsi()))  && (!StringUtil.is_nullString(deviceInfo.getMac())) )
		   {
			   MMUser mmUser = new MMUser(deviceInfo.getImei(),deviceInfo.getImsi(),deviceInfo.getMac());
			   mmUser.initUser();	
			   deviceInfo.setSid(mmUser.sid);
			   DeviceInfoDao  deviceInfoDao = new DeviceInfoDao();
			   deviceInfoDao.edit(deviceInfo);
		   }
	   }
   }
   
   public static void doActAllUser(ArrayList<Object> objList)
   {
	   for(int i=0;i<objList.size();i++)
	   {
		   DeviceInfo deviceInfo =(DeviceInfo)objList.get(i);
		   if((!StringUtil.is_nullString(deviceInfo.getSid())) )
		   {
			   MMUser mmUser = new MMUser(deviceInfo.getImei(),deviceInfo.getImsi(),deviceInfo.getMac());
			   mmUser.doact(deviceInfo.getSid());
			   mmUser.doacttime(deviceInfo.getSid());
		   }
	   }
   }
   
   
   public  void initUser()
   {
	   posteventlog1();
	   
	   /*try {
			Thread.sleep(act_sendTime-event1_sendTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	   postactlog();
	   
	   
	   /* try {
			Thread.sleep(sys_sendTime-event1_sendTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	   postsyslog();
	   
	   /*try {
			Thread.sleep(event2_sendTime-event1_sendTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	   posteventlog2();
	   
	   doacttime(sid);
	   
   }
   //{"sid":"f9585dbed037d81c","packageName":"com.pansen.zumalocal","sendTime":1422511918591,"versionCode":1,"mac":"d4:97:0b:69:c7:07","pid":1,"versionName":"1.0","cid":"7f9c9fae657f9ebe4a","phoneOs":"android+4.4.4","sdkVersion":"3.1.1","cpuRatioCur":"1728000","logJsonAry":[{"flowConsumpRev":0,"flowConsumpSnd":0,"sid":"f9585dbed037d81c","logs":"onResume|mobi.shoumeng.game.demo.MainActivity|1422511917320|0|\n"}],"protocolVersion":"3.1.0","menoryRatio":"1893832","cpuRatioMax":"2265600","manufacturer":"Xiaomi","accessPoint":"wifi","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}
   //{"sid":"a18c16242689adc7","packageName":"com.pansen.zumalocal","sendTime":1422511918591,"versionCode":1,"mac":"d4:97:0b:69:c7:07","pid":1,"versionName":"1.0","cid":"7f9c9fae657f9ebe4a","phoneOs":"android+4.4.4","sdkVersion":"3.1.1","cpuRatioCur":"1728000","logJsonAry":[{"flowConsumpRev":0,"flowConsumpSnd":0,"sid":"a18c16242689adc7","logs":"onResume|mobi.shoumeng.game.demo.MainActivity|1422511917320|0|\n"}],"protocolVersion":"3.1.0","menoryRatio":"1893832","cpuRatioMax":"2265600","manufacturer":"Xiaomi","accessPoint":"wifi","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}

	 //第二步激活数据
	 public  void  postactlog()
	 {
	    	String str = "";
	    	/*String packageName = "com.pansen.zumalocal";
	    	String mac = "d4:97:0b:69:c7:07";
	    	String cid = "7f9c9fae657f9ebe4a";
	    	String OsVersion="4.4.4";
	    	String phoneOs = "android+"+OsVersion;
	    	String sdkVersion = "3.1.1";
	    	String logJsonAry = "";
	    	String protocolVersion = "3.1.0";
	    	String manufacturer = "Xiaomi";
	    	String versionName = "1.0";
	    	String MMversionName = "1.2.3";
	    	String accessPoint = "wifi";
	    	String deviceDetail = "MI+3C";
	    	String channel = "0000000000";
	    	String deviceId = "865903029745048";
	    	String appKey = "300008702230";
	    	//String appid = "300008702230";
	    	String imsi = "460029140953268";
	    	String model = "MI 3C";
	    	String  resolution = "1080*1920";
	    	String cpuRatioCur = "1728000";
	    	String MainActivity = "mobi.shoumeng.game.demo.MainActivity";
	    	String menoryRatio ="1893832";
	    	String cpuRatioMax = "2265600";*/
	    	
	    	String strUrl = "http://da.mmarket.com/mmsdk/mmsdk?func=mmsdk:postactlog&appkey="+appKey+"&channel="+channel+"&code=105";
	    	
	    	//long act_sendTime =1422511918591L;// System.currentTimeMillis();//1422511917327L;
	    	//long act_logTime = 1422511917320L;
	    	
	    
	    	//String logJsonAry = "";
	    	//String MainActivity = "mobi.shoumeng.game.demo.MainActivity";
	    	
	    	int i1 = new Random().nextInt(10);
	    	//String paramContext="";
	       // sid = String.valueOf(Rsa.getMD5(System.currentTimeMillis() + appKey + replace(deviceId)/*g.a(paramContext,deviceId)*/ + mac + i1).toCharArray(), 8, 16);//"f9585dbed037d81c";
	        
	        //sid = "f9585dbed037d81c";
	        
	        JSONObject logJson= new JSONObject();
	        int flowConsumpRev = 0;
	        int flowConsumpSnd = 0;
	        logJson.put("flowConsumpRev",flowConsumpRev);
	        logJson.put("flowConsumpSnd",flowConsumpSnd);
	        logJson.put("sid",sid);
	        logJson.put("logs","onResume|"+MainActivity+"|"+act_logTime+"|"+playTime+"|\n");
	    	logJsonAry = "["+logJson.toString()+"]";
	    	
	    	JSONObject reqjson = new JSONObject();
			reqjson.put("sid",sid);
			reqjson.put("packageName",packageName);
			reqjson.put("sendTime",act_sendTime);
			reqjson.put("versionCode",1);
			reqjson.put("mac",mac);
			reqjson.put("pid",1);
			reqjson.put("versionName",versionName);
			reqjson.put("cid",cid);
			reqjson.put("phoneOs",phoneOs);
			reqjson.put("sdkVersion",sdkVersion);
			reqjson.put("cpuRatioCur",cpuRatioCur);	
			reqjson.put("logJsonAry",logJsonAry);
			reqjson.put("protocolVersion",protocolVersion);
			reqjson.put("menoryRatio",menoryRatio);
			reqjson.put("cpuRatioMax",cpuRatioMax);
			//
			reqjson.put("manufacturer",manufacturer);
			reqjson.put("accessPoint",accessPoint);
			reqjson.put("deviceDetail",deviceDetail);
			reqjson.put("channel",channel);
			reqjson.put("deviceId",deviceId);
			reqjson.put("appKey",appKey);
		
			System.out.println("strUrl="+strUrl);
			
			str = reqjson.toString();
			System.out.println("str="+str);
			
			d td= d.a();
			byte [] bytes =td.a(str);
			//System.out.println(byteToStr (bytes));
			
			try {
				String rsq =HttpUtils.URLPostUTF8(strUrl, bytes);
				System.out.println("rsq"+rsq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	    }
	    
	 //常规启动初始化,
	 public  void doact(String sid)
	 {
		 event1_sendTime = System.currentTimeMillis();
		 event1_logTime = event1_sendTime - genRan(10,20);
		 this.sid = sid;
		 
		 String logJson1="_pay_init";
	    	String logJson2_2 = appid+"@@"+packageName+"@@"+versionName+"@@"+"0000000000@@99999999"+"@@"+imsi+"@@"+deviceId+"@@"+model+"@@"+OsVersion+"@@"+resolution
	    			+"@@SMS@@@@"+"@@"+MMversionName+"@@"+event1_logTime/1000+"@@"+event1_logTime/1000+"@@"+"1";
	    	String logJson2 = channel+"@@"+imsi+"@@"+manufacturer+"@@"+model+"@@"+phoneOs+"@@"+mac+"@@"+accessPoint+"@@"+resolution+"@@"+URLEncoder.encode(logJson2_2);
	    	logJson2 = URLEncoder.encode(logJson2);
	    	String logJson3="0";
	    	String logJson4=event1_logTime+"\n";//1422511917195
	    	
	    	logJsonAry = logJson1+"|"+logJson2+"|"+logJson3+"|"+logJson4;
	    	posteventlog(event1_sendTime,logJsonAry);
		 
	 }
	 
	 //在线时长,
	 public  void doacttime(String sid)
	 {
		
		 playTime = genRan(60516,65324);
		 act_sendTime = event1_sendTime+playTime+231;
		 act_logTime = act_sendTime-playTime;
		 postactlog();
		 
	 }
	    //初始化第一步
	    public  void posteventlog1()
	    {
	    	//sid = "";
	    	//cid = "7f9c9fae657f9ebe4a";
	    	//long event1_sendTime =  1422511917327L;// System.currentTimeMillis();//1422511917327L;
	    	//long event1_logTime = 1422511917195L;//比sendTime少100毫秒
	    	
	    	String logJson1="_pay_init";
	    	String logJson2_2 = appid+"@@"+packageName+"@@"+versionName+"@@"+"0000000000@@99999999"+"@@"+imsi+"@@"+deviceId+"@@"+model+"@@"+OsVersion+"@@"+resolution
	    			+"@@SMS@@@@"+"@@"+MMversionName+"@@"+event1_logTime/1000+"@@"+event1_logTime/1000+"@@"+"1";
	    	String logJson2 = channel+"@@"+imsi+"@@"+manufacturer+"@@"+model+"@@"+phoneOs+"@@"+mac+"@@"+accessPoint+"@@"+resolution+"@@"+URLEncoder.encode(logJson2_2);
	    	logJson2 = URLEncoder.encode(logJson2);
	    	String logJson3="0";
	    	String logJson4=event1_logTime+"\n";//1422511917195
	    	
	    	logJsonAry = logJson1+"|"+logJson2+"|"+logJson3+"|"+logJson4;
	    	posteventlog(event1_sendTime,logJsonAry);
	    }
	    //初始化第四步
	    public  void posteventlog2()
	    {
	    	//sid = "f9585dbed037d81c";
	    	//cid = "7f9c9fae657f9ebe4a";
	    	//long sendTime =  1422511919015L;// System.currentTimeMillis();//1422511917327L;
	    	//long beginTime = 1422511917992L;
	    	
	    	//long event2_sendTime =  1422511919015L;// System.currentTimeMillis();//1422511917327L;
	    	//long event2_logTime = 1422511917992L;//比sendTime少100毫秒
	    	
	    	
	    	String logJson1="#applist";
	    	logJson1 = URLEncoder.encode(logJson1);
	    	//String logJson2_2 = "";
	    	String logJson2 = URLEncoder.encode("微信")+"@@com.tencent.mm@@6.0.2.57_r966533";;//"微信@@com.tencent.mm@@6.0.2.57_r966533@@##新浪体育@@cn.com.sina.sports@@2.8.1.2@@##WPS Office@@cn.wps.moffice_eng@@6.5@@##小米商城@@com.xiaomi.shop@@3.0.20150109@@##自选股@@com.tencent.portfolio@@3.7.1@@##手机管家@@com.tencent.qqpimsecure@@5.3.0.2960mini@@##米聊@@com.xiaomi.channel@@1.0.1167@@##携程旅行@@ctrip.android.view@@6.1.2@@##语音设置@@com.iflytek.speechcloud@@1.0.10051@@##山水手环@@com.ble@@2.0.1@@##平安口袋银行@@com.pingan.pabank.activity@@2.2.1@@##QQ空间@@com.qzone@@5.0.2.188@@##百度地图@@com.baidu.BaiduMap@@7.8.0@@##应用宝@@com.tencent.android.qqdownloader@@5.0.0@@##Demo@@com.pansen.zumalocal@@1.0@@##炒股公开课@@com.hexin.openclass@@3.0.1@@##QQ浏览器@@com.tencent.mtt@@5.6.1.1301@@##高德地图@@com.autonavi.minimap@@7.1.3.572@@##碰碰贴@@com.xiaomi.tag@@2.0.4@@##smsTest@@com.example.smstest@@1.0@@##58同城@@com.wuba@@5.8.2.0@@##财经证券新闻@@com.eastmoney.android.tokyo@@1.3@@##百度输入法小米版@@com.baidu.input_mi@@4.1.4.29@@##多看阅读@@com.duokan.reader@@3.3.5@@##星星祖玛@@com.pansen.zumalocal.lenovo@@1.5.0@@##QQ@@com.tencent.mobileqq@@5.3.1@@##豌豆荚连接服务@@com.wandoujia.phoenix2.usbproxy@@3.52.1@@##乐商店@@com.lenovo.leos.appstore@@6.15.10.88@@##微博@@com.sina.weibo@@5.0.0@@##豌豆荚@@com.wandoujia.phoenix2@@4.18.1@@##后台@@com.zty.ompic25@@1.0.1@@##酷狗音乐@@com.kugou.android@@7.0.9@@##招商银行@@cmb.pb@@3.0.2@@##";//channel+"@@"+imsi+"@@"+manufacturer+"@@"+model+"@@"+phoneOs+"@@"+mac+"@@"+accessPoint+"@@"+resolution+"@@"+URLEncoder.encode(logJson2_2);
	    	logJson2 = URLEncoder.encode(logJson2);
	    	logJson2="%25E5%25BE%25AE%25E4%25BF%25A1%40%40com.tencent.mm%40%406.0.2.57_r966533%40%40%23%23%25E6%2596%25B0%25E6%25B5%25AA%25E4%25BD%2593%25E8%2582%25B2%40%40cn.com.sina.sports%40%402.8.1.2%40%40%23%23WPS%2BOffice%40%40cn.wps.moffice_eng%40%406.5%40%40%23%23%25E5%25B0%258F%25E7%25B1%25B3%25E5%2595%2586%25E5%259F%258E%40%40com.xiaomi.shop%40%403.0.20150109%40%40%23%23%25E8%2587%25AA%25E9%2580%2589%25E8%2582%25A1%40%40com.tencent.portfolio%40%403.7.1%40%40%23%23%25E6%2589%258B%25E6%259C%25BA%25E7%25AE%25A1%25E5%25AE%25B6%40%40com.tencent.qqpimsecure%40%405.3.0.2960mini%40%40%23%23%25E7%25B1%25B3%25E8%2581%258A%40%40com.xiaomi.channel%40%401.0.1167%40%40%23%23%25E6%2590%25BA%25E7%25A8%258B%25E6%2597%2585%25E8%25A1%258C%40%40ctrip.android.view%40%406.1.2%40%40%23%23%25E8%25AF%25AD%25E9%259F%25B3%25E8%25AE%25BE%25E7%25BD%25AE%40%40com.iflytek.speechcloud%40%401.0.10051%40%40%23%23%25E5%25B1%25B1%25E6%25B0%25B4%25E6%2589%258B%25E7%258E%25AF%40%40com.ble%40%402.0.1%40%40%23%23%25E5%25B9%25B3%25E5%25AE%2589%25E5%258F%25A3%25E8%25A2%258B%25E9%2593%25B6%25E8%25A1%258C%40%40com.pingan.pabank.activity%40%402.2.1%40%40%23%23QQ%25E7%25A9%25BA%25E9%2597%25B4%40%40com.qzone%40%405.0.2.188%40%40%23%23%25E7%2599%25BE%25E5%25BA%25A6%25E5%259C%25B0%25E5%259B%25BE%40%40com.baidu.BaiduMap%40%407.8.0%40%40%23%23%25E5%25BA%2594%25E7%2594%25A8%25E5%25AE%259D%40%40com.tencent.android.qqdownloader%40%405.0.0%40%40%23%23Demo%40%40com.pansen.zumalocal%40%401.0%40%40%23%23%25E7%2582%2592%25E8%2582%25A1%25E5%2585%25AC%25E5%25BC%2580%25E8%25AF%25BE%40%40com.hexin.openclass%40%403.0.1%40%40%23%23QQ%25E6%25B5%258F%25E8%25A7%2588%25E5%2599%25A8%40%40com.tencent.mtt%40%405.6.1.1301%40%40%23%23%25E9%25AB%2598%25E5%25BE%25B7%25E5%259C%25B0%25E5%259B%25BE%40%40com.autonavi.minimap%40%407.1.3.572%40%40%23%23%25E7%25A2%25B0%25E7%25A2%25B0%25E8%25B4%25B4%40%40com.xiaomi.tag%40%402.0.4%40%40%23%23smsTest%40%40com.example.smstest%40%401.0%40%40%23%2358%25E5%2590%258C%25E5%259F%258E%40%40com.wuba%40%405.8.2.0%40%40%23%23%25E8%25B4%25A2%25E7%25BB%258F%25E8%25AF%2581%25E5%2588%25B8%25E6%2596%25B0%25E9%2597%25BB%40%40com.eastmoney.android.tokyo%40%401.3%40%40%23%23%25E7%2599%25BE%25E5%25BA%25A6%25E8%25BE%2593%25E5%2585%25A5%25E6%25B3%2595%25E5%25B0%258F%25E7%25B1%25B3%25E7%2589%2588%40%40com.baidu.input_mi%40%404.1.4.29%40%40%23%23%25E5%25A4%259A%25E7%259C%258B%25E9%2598%2585%25E8%25AF%25BB%40%40com.duokan.reader%40%403.3.5%40%40%23%23%25E6%2598%259F%25E6%2598%259F%25E7%25A5%2596%25E7%258E%259B%40%40com.pansen.zumalocal.lenovo%40%401.5.0%40%40%23%23QQ%40%40com.tencent.mobileqq%40%405.3.1%40%40%23%23%25E8%25B1%258C%25E8%25B1%2586%25E8%258D%259A%25E8%25BF%259E%25E6%258E%25A5%25E6%259C%258D%25E5%258A%25A1%40%40com.wandoujia.phoenix2.usbproxy%40%403.52.1%40%40%23%23%25E4%25B9%2590%25E5%2595%2586%25E5%25BA%2597%40%40com.lenovo.leos.appstore%40%406.15.10.88%40%40%23%23%25E5%25BE%25AE%25E5%258D%259A%40%40com.sina.weibo%40%405.0.0%40%40%23%23%25E8%25B1%258C%25E8%25B1%2586%25E8%258D%259A%40%40com.wandoujia.phoenix2%40%404.18.1%40%40%23%23%25E5%2590%258E%25E5%258F%25B0%40%40com.zty.ompic25%40%401.0.1%40%40%23%23%25E9%2585%25B7%25E7%258B%2597%25E9%259F%25B3%25E4%25B9%2590%40%40com.kugou.android%40%407.0.9%40%40%23%23%25E6%258B%259B%25E5%2595%2586%25E9%2593%25B6%25E8%25A1%258C%40%40cmb.pb%40%403.0.2%40%40%23%23";
	    	String logJson3="0";
	    	String logJson4=event2_logTime+"\n";//1422511917195
	    	
	    	logJsonAry = logJson1+"|"+logJson2+"|"+logJson3+"|"+logJson4;
	    	//logJsonAry = URLEncoder.encode(logJsonAry);;
	    	posteventlog(event2_sendTime,logJsonAry);
	    	//{"sid":"f9585dbed037d81c","packageName":"com.pansen.zumalocal","sendTime":1422511919015,"versionCode":1,"mac":"d4:97:0b:69:c7:07","pid":1,"versionName":"1.0","cid":"7f9c9fae657f9ebe4a","phoneOs":"android+4.4.4","sdkVersion":"3.1.1","logJsonAry":"%23applist|%25E5%25BE%25AE%25E4%25BF%25A1%40%40com.tencent.mm%40%406.0.2.57_r966533%40%40%23%23%25E6%2596%25B0%25E6%25B5%25AA%25E4%25BD%2593%25E8%2582%25B2%40%40cn.com.sina.sports%40%402.8.1.2%40%40%23%23WPS%2BOffice%40%40cn.wps.moffice_eng%40%406.5%40%40%23%23%25E5%25B0%258F%25E7%25B1%25B3%25E5%2595%2586%25E5%259F%258E%40%40com.xiaomi.shop%40%403.0.20150109%40%40%23%23%25E8%2587%25AA%25E9%2580%2589%25E8%2582%25A1%40%40com.tencent.portfolio%40%403.7.1%40%40%23%23%25E6%2589%258B%25E6%259C%25BA%25E7%25AE%25A1%25E5%25AE%25B6%40%40com.tencent.qqpimsecure%40%405.3.0.2960mini%40%40%23%23%25E7%25B1%25B3%25E8%2581%258A%40%40com.xiaomi.channel%40%401.0.1167%40%40%23%23%25E6%2590%25BA%25E7%25A8%258B%25E6%2597%2585%25E8%25A1%258C%40%40ctrip.android.view%40%406.1.2%40%40%23%23%25E8%25AF%25AD%25E9%259F%25B3%25E8%25AE%25BE%25E7%25BD%25AE%40%40com.iflytek.speechcloud%40%401.0.10051%40%40%23%23%25E5%25B1%25B1%25E6%25B0%25B4%25E6%2589%258B%25E7%258E%25AF%40%40com.ble%40%402.0.1%40%40%23%23%25E5%25B9%25B3%25E5%25AE%2589%25E5%258F%25A3%25E8%25A2%258B%25E9%2593%25B6%25E8%25A1%258C%40%40com.pingan.pabank.activity%40%402.2.1%40%40%23%23QQ%25E7%25A9%25BA%25E9%2597%25B4%40%40com.qzone%40%405.0.2.188%40%40%23%23%25E7%2599%25BE%25E5%25BA%25A6%25E5%259C%25B0%25E5%259B%25BE%40%40com.baidu.BaiduMap%40%407.8.0%40%40%23%23%25E5%25BA%2594%25E7%2594%25A8%25E5%25AE%259D%40%40com.tencent.android.qqdownloader%40%405.0.0%40%40%23%23Demo%40%40com.pansen.zumalocal%40%401.0%40%40%23%23%25E7%2582%2592%25E8%2582%25A1%25E5%2585%25AC%25E5%25BC%2580%25E8%25AF%25BE%40%40com.hexin.openclass%40%403.0.1%40%40%23%23QQ%25E6%25B5%258F%25E8%25A7%2588%25E5%2599%25A8%40%40com.tencent.mtt%40%405.6.1.1301%40%40%23%23%25E9%25AB%2598%25E5%25BE%25B7%25E5%259C%25B0%25E5%259B%25BE%40%40com.autonavi.minimap%40%407.1.3.572%40%40%23%23%25E7%25A2%25B0%25E7%25A2%25B0%25E8%25B4%25B4%40%40com.xiaomi.tag%40%402.0.4%40%40%23%23smsTest%40%40com.example.smstest%40%401.0%40%40%23%2358%25E5%2590%258C%25E5%259F%258E%40%40com.wuba%40%405.8.2.0%40%40%23%23%25E8%25B4%25A2%25E7%25BB%258F%25E8%25AF%2581%25E5%2588%25B8%25E6%2596%25B0%25E9%2597%25BB%40%40com.eastmoney.android.tokyo%40%401.3%40%40%23%23%25E7%2599%25BE%25E5%25BA%25A6%25E8%25BE%2593%25E5%2585%25A5%25E6%25B3%2595%25E5%25B0%258F%25E7%25B1%25B3%25E7%2589%2588%40%40com.baidu.input_mi%40%404.1.4.29%40%40%23%23%25E5%25A4%259A%25E7%259C%258B%25E9%2598%2585%25E8%25AF%25BB%40%40com.duokan.reader%40%403.3.5%40%40%23%23%25E6%2598%259F%25E6%2598%259F%25E7%25A5%2596%25E7%258E%259B%40%40com.pansen.zumalocal.lenovo%40%401.5.0%40%40%23%23QQ%40%40com.tencent.mobileqq%40%405.3.1%40%40%23%23%25E8%25B1%258C%25E8%25B1%2586%25E8%258D%259A%25E8%25BF%259E%25E6%258E%25A5%25E6%259C%258D%25E5%258A%25A1%40%40com.wandoujia.phoenix2.usbproxy%40%403.52.1%40%40%23%23%25E4%25B9%2590%25E5%2595%2586%25E5%25BA%2597%40%40com.lenovo.leos.appstore%40%406.15.10.88%40%40%23%23%25E5%25BE%25AE%25E5%258D%259A%40%40com.sina.weibo%40%405.0.0%40%40%23%23%25E8%25B1%258C%25E8%25B1%2586%25E8%258D%259A%40%40com.wandoujia.phoenix2%40%404.18.1%40%40%23%23%25E5%2590%258E%25E5%258F%25B0%40%40com.zty.ompic25%40%401.0.1%40%40%23%23%25E9%2585%25B7%25E7%258B%2597%25E9%259F%25B3%25E4%25B9%2590%40%40com.kugou.android%40%407.0.9%40%40%23%23%25E6%258B%259B%25E5%2595%2586%25E9%2593%25B6%25E8%25A1%258C%40%40cmb.pb%40%403.0.2%40%40%23%23|0|1422511917992\n","protocolVersion":"3.1.0","manufacturer":"Xiaomi","accessPoint":"wifi","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}
	    	//{"sid":"f9585dbed037d81c","packageName":"com.pansen.zumalocal","sendTime":1422511919015,"versionCode":1,"mac":"d4:97:0b:69:c7:07","pid":1,"versionName":"1.0","cid":"7f9c9fae657f9ebe4a","phoneOs":"android+4.4.4","sdkVersion":"3.1.1","logJsonAry":"%23applist|%25E5%25BE%25AE%25E4%25BF%25A1%40%40com.tencent.mm%40%406.0.2.57_r966533%40%40%23%23%25E6%2596%25B0%25E6%25B5%25AA%25E4%25BD%2593%25E8%2582%25B2%40%40cn.com.sina.sports%40%402.8.1.2%40%40%23%23WPS%2BOffice%40%40cn.wps.moffice_eng%40%406.5%40%40%23%23%25E5%25B0%258F%25E7%25B1%25B3%25E5%2595%2586%25E5%259F%258E%40%40com.xiaomi.shop%40%403.0.20150109%40%40%23%23%25E8%2587%25AA%25E9%2580%2589%25E8%2582%25A1%40%40com.tencent.portfolio%40%403.7.1%40%40%23%23%25E6%2589%258B%25E6%259C%25BA%25E7%25AE%25A1%25E5%25AE%25B6%40%40com.tencent.qqpimsecure%40%405.3.0.2960mini%40%40%23%23%25E7%25B1%25B3%25E8%2581%258A%40%40com.xiaomi.channel%40%401.0.1167%40%40%23%23%25E6%2590%25BA%25E7%25A8%258B%25E6%2597%2585%25E8%25A1%258C%40%40ctrip.android.view%40%406.1.2%40%40%23%23%25E8%25AF%25AD%25E9%259F%25B3%25E8%25AE%25BE%25E7%25BD%25AE%40%40com.iflytek.speechcloud%40%401.0.10051%40%40%23%23%25E5%25B1%25B1%25E6%25B0%25B4%25E6%2589%258B%25E7%258E%25AF%40%40com.ble%40%402.0.1%40%40%23%23%25E5%25B9%25B3%25E5%25AE%2589%25E5%258F%25A3%25E8%25A2%258B%25E9%2593%25B6%25E8%25A1%258C%40%40com.pingan.pabank.activity%40%402.2.1%40%40%23%23QQ%25E7%25A9%25BA%25E9%2597%25B4%40%40com.qzone%40%405.0.2.188%40%40%23%23%25E7%2599%25BE%25E5%25BA%25A6%25E5%259C%25B0%25E5%259B%25BE%40%40com.baidu.BaiduMap%40%407.8.0%40%40%23%23%25E5%25BA%2594%25E7%2594%25A8%25E5%25AE%259D%40%40com.tencent.android.qqdownloader%40%405.0.0%40%40%23%23Demo%40%40com.pansen.zumalocal%40%401.0%40%40%23%23%25E7%2582%2592%25E8%2582%25A1%25E5%2585%25AC%25E5%25BC%2580%25E8%25AF%25BE%40%40com.hexin.openclass%40%403.0.1%40%40%23%23QQ%25E6%25B5%258F%25E8%25A7%2588%25E5%2599%25A8%40%40com.tencent.mtt%40%405.6.1.1301%40%40%23%23%25E9%25AB%2598%25E5%25BE%25B7%25E5%259C%25B0%25E5%259B%25BE%40%40com.autonavi.minimap%40%407.1.3.572%40%40%23%23%25E7%25A2%25B0%25E7%25A2%25B0%25E8%25B4%25B4%40%40com.xiaomi.tag%40%402.0.4%40%40%23%23smsTest%40%40com.example.smstest%40%401.0%40%40%23%2358%25E5%2590%258C%25E5%259F%258E%40%40com.wuba%40%405.8.2.0%40%40%23%23%25E8%25B4%25A2%25E7%25BB%258F%25E8%25AF%2581%25E5%2588%25B8%25E6%2596%25B0%25E9%2597%25BB%40%40com.eastmoney.android.tokyo%40%401.3%40%40%23%23%25E7%2599%25BE%25E5%25BA%25A6%25E8%25BE%2593%25E5%2585%25A5%25E6%25B3%2595%25E5%25B0%258F%25E7%25B1%25B3%25E7%2589%2588%40%40com.baidu.input_mi%40%404.1.4.29%40%40%23%23%25E5%25A4%259A%25E7%259C%258B%25E9%2598%2585%25E8%25AF%25BB%40%40com.duokan.reader%40%403.3.5%40%40%23%23%25E6%2598%259F%25E6%2598%259F%25E7%25A5%2596%25E7%258E%259B%40%40com.pansen.zumalocal.lenovo%40%401.5.0%40%40%23%23QQ%40%40com.tencent.mobileqq%40%405.3.1%40%40%23%23%25E8%25B1%258C%25E8%25B1%2586%25E8%258D%259A%25E8%25BF%259E%25E6%258E%25A5%25E6%259C%258D%25E5%258A%25A1%40%40com.wandoujia.phoenix2.usbproxy%40%403.52.1%40%40%23%23%25E4%25B9%2590%25E5%2595%2586%25E5%25BA%2597%40%40com.lenovo.leos.appstore%40%406.15.10.88%40%40%23%23%25E5%25BE%25AE%25E5%258D%259A%40%40com.sina.weibo%40%405.0.0%40%40%23%23%25E8%25B1%258C%25E8%25B1%2586%25E8%258D%259A%40%40com.wandoujia.phoenix2%40%404.18.1%40%40%23%23%25E5%2590%258E%25E5%258F%25B0%40%40com.zty.ompic25%40%401.0.1%40%40%23%23%25E9%2585%25B7%25E7%258B%2597%25E9%259F%25B3%25E4%25B9%2590%40%40com.kugou.android%40%407.0.9%40%40%23%23%25E6%258B%259B%25E5%2595%2586%25E9%2593%25B6%25E8%25A1%258C%40%40cmb.pb%40%403.0.2%40%40%23%23|0|1422511917195\n","protocolVersion":"3.1.0","manufacturer":"Xiaomi","accessPoint":"wifi","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}

	    	//35,-124,90
	    	//-73,-76,-84,83,40,-98,118,-5,-50,49,-87,-68,79,-96,-47,122,73,48,50,-84,78,-107,-105,-108,3,105,66,75,88,65,45,-110,127,10,-5,-101,-39,74,-31,76,-107,-83,107,-118,52,55,-2,-15,3,-20,-37,-57,119,-2,-64,15,-4,-18,-45,4,40,-83,-125,-59,-28,2,-30,44,-22,47,-67,-40,25,48,-84,94,98,80,99,9,-12,1,90,-17,81,107,-35,-88,-125,16,-65,-127,-32,95,57,25,-25,-121,-50,53,-100,-85,49,14,34,39,-55,-82,-9,39,79,74,-10,-110,-31,-67,-107,3,14,-120,54,-14,-115,-46,-106,6,-39,-32,21,7,97,-43,26,-8,-119,5,-94,-59,-61,-93,-82,84,115,26,-126,-38,-29,79,-2,-40,103,-97,9,-54,14,-84,-116,24,127,64,94,55,96,-126,112,118,81,95,-108,124,114,39,-107,-55,-23,64,-94,-60,-97,-121,-64,-68,39,77,-29,-22,103,89,12,99,76,-100,87,-71,124,-35,39,45,60,96,-61,-5,88,-22,-36,66,30,-78,-25,-77,73,88,-38,118,-13,17,55,68,94,-37,-118,-101,-51,113,-52,-125,36,11,-31,-94,-55,-18,-102,-79,119,73,5,12,11,36,110,84,9,-128,-46,-107,25,-112,9,38,126,-78,-29,-22,-64,-123,72,-31,50,-16,-68,-2,57,-43,-78,108,55,76,-93,-26,118,26,-92,3,87,81,-13,-90,-30,-17,125,-12,-9,-55,65,-39,-124,-24,-125,67,25,1,-3,-71,-24,-2,-93,6,-41,-67,108,84,104,104,-86,-43,31,60,78,-26,-39,-61,32,-60,-48,-39,38,121,-32,-75,-68,-45,-119,-112,125,76,-33,22,-51,18,93,49,112,42,-29,-79,62,125,85,-121,61,-96,60,-86,-21,7,10,-19,-23,18,-23,-123,-123,-62,-26,16,-20,14,-3,-88,59,-98,-112,-84,-98,-123,-61,-84,-57,-111,67,53,-124,-124,-114,-106,26,5,114,126,-85,63,-55,-83,-119,96,-38,5,79,120,51,-90,-70,124,-100,-17,-91,-28,-118,-122,11,118,110,-108,-49,-2,-68,86,82,78,110,-72,-40,-54,-126,41,66,-36,-128,-60,-20,-64,-31,-67,17,56,-77,-64,-80,-121,89,-33,-103,-79,-15,94,-11,17,-109,-87,28,35,-3,-73,-57,-36,5,49,-89,87,92,32,61,-52,-43,-9,-75,71,-48,-20,112,-22,-62,-2,125,48,55,112,65,23,53,102,-62,-32,93,-47,-35,23,62,49,-85,-20,62,-103,111,88,90,85,10,17,79,85,103,59,37,-9,30,84,-19,-15,101,-78,113,-75,108,-1,21,-93,-106,-48,29,65,40,-127,85,62,117,105,24,52,70,-109,-61,-81,34,-108,76,-87,-17,93,118,-77,-43,26,-67,-39,-118,-31,-120,101,-79,-36,-115,1,73,-13,-86,96,-43,-123,83,49,107,40,49,-65,29,-58,90,-120,-108,52,54,89,73,11,-49,58,-54,75,111,-92,-27,-46,103,76,60,119,-66,10,-13,-80,32,27,-74,26,-101,-33,47,-62,-108,12,-79,-3,34,-12,-41,97,77,3,-116,-59,123,-37,127,-99,-88,83,118,-49,-70,66,30,125,-81,59,79,-67,-72,-46,-110,-97,125,-96,37,-126,95,-101,-100,-70,50,34,-41,-11,113,-25,-16,-90,-51,34,-118,-43,90,122,37,-107,26,18,43,110,-73,42,59,7,-34,51,48,-63,64,49,118,-102,-68,-20,-9,-93,-13,74,-90,-24,-37,-99,-99,64,21,14,92,120,-73,-12,66,-2,-37,-55,5,95,-103,33,38,81,97,-54,106,-117,-25,19,50,126,25,38,59,88,-76,32,19,70,-3,19,-64,-112,-120,-92,-8,115,35,-117,111,-93,95,99,123,-123,75,-53,-87,-116,-54,-2,-21,116,63,82,-1,127,-74,9,-121,125,86,-61,77,40,-93,88,-42,107,77,-115,-99,99,47,-71,29,-8,40,78,113,-113,-84,-47,7,43,-80,-106,64,-72,-92,95,-75,96,19,62,-74,29,104,19,-127,-4,0,107,79,105,-29,-59,-93,-69,-64,-55,-89,-77,-32,-8,4,-2,-43,96,124,24,33,0,104,-15,-71,113,94,22,-112,82,114,82,-23,-65,-86,-25,-34,-90,-106,-125,-30,73,-28,23,114,101,97,126,-111,53,33,-21,-112,-89,41,-61,63,97,-52,11,41,-12,-114,105,29,-81,-109,-66,-36,-118,-36,96,88,-91,-101,-50,-60,-15,53,-111,76,77,-19,63,38,-23,-49,-103,84,-84,-52,103,-4,-13,13,-10,-70,-99,-57,-120,4,-40,-93,-96,-36,67,-10,8,-91,-74,-45,-112,107,-30,67,22,71,-79,85,43,45,55,101,81,5,-7,-15,-25,-31,-5,49,-70,-71,-43,-46,2,-59,-86,84,-34,70,78,-62,71,71,0,109,-90,67,118,125,-29,-47,30,13,76,29,-25,110,-19,73,-36,-36,5,-16,-105,-121,-52,-115,72,50,88,118,-91,23,-31,34,107,-99,104,-74,93,25,-114,-62,27,-2,122,-76,11,85,-32,-71,26,-31,42,-86,-76,-82,102,42,86,-100,-122,-30,-104,45,41,43,-63,-93,-124,-66,-72,-72,-62,-4,56,8,-72,0,70,-101,-65,31,83,-101,-101,-106,110,0,-14,125,-57,-112,-85,-12,-64,-109,-47,32,-83,-64,-122,73,-44,-80,-65,54,-26,24,-11,123,-3,36,34,113,-59,-98,49,-87,30,-6,-3,-35,-69,-122,-94,58,-90,28,70,-98,-95,-73,70,101,-1,51,105,59,62,105,-28,-34,-114,123,117,-73,-44,-58,74,-110,-121,56,77,97,-88,-23,-40,-97,31,-97,96,-124,88,-68,-41,-36,-70,-83,-117,-39,1,-8,38,-78,28,-34,56,62,104,108,126,-40,101,-96,92,-24,8,-52,-12,97,82,-74,52,50,-70,-16,-7,-3,-52,89,-25,-110,47,63,-79,-79,60,64,-90,13,-54,-85,121,93,-67,50,10,-83,-23,-34,39,107,17,-21,106,111,40,26,110,91,-69,-31,-45,-72,-96,-87,69,-124,22,75,98,-21,66,-10,117,41,-117,88,-51,-118,-12,-82,-89,36,-30,13,14,-116,100,102,54,73,21,-3,-16,-63,-70,-4,-88,86,56,122,100,46,-117,-121,-123,103,7,44,12,103,-11,-84,-95,-33,90,79,-25,86,-44,0,86,-117,-119,-73,-5,-71,29,-78,21,111,-66,66,29,-40,88,42,-16,26,8,26,52,7,-113,23,3,46,-70,-15,-2,-35,48,-126,-33,41,82,3,-108,53,-46,-7,105,-24,-21,-93,-86,85,-101,-110,11,-126,35,3,19,-90,-33,48,44,115,-92,-61,-40,-122,-40,2,10,-77,3,-66,37,-71,-88,57,-23,-7,-115,-37,-102,68,27,-118,-83,110,-4,86,-34,62,-66,9,-95,-111,-80,89,87,-119,-68,-108,-25,122,-74,-43,85,-8,-118,57,-13,-33,-77,-46,15,-75,54,-7,-27,-1,16,-85,9,18,79,67,16,-12,15,-19,-112,114,-126,115,-92,-90,-124,16,-81,97,85,-33,24,121,123,-94,101,85,41,-110,89,-13,-23,-23,-118,-77,-68,107,57,-30,-13,13,4,-104,125,-88,24,-115,-94,-90,-84,39,-83,42,22,-62,-26,-19,82,-100,26,-33,-46,-3,3,-105,79,-88,1,66,56,76,-94,117,57,-44,-10,-86,-115,-77,-111,-111,40,-51,69,-67,-15,102,7,100,27,22,-124,86,-50,100,-69,-73,107,123,-106,33,-56,57,-105,113,-2,30,-34,44,-45,30,-80,-64,7,-84,-114,-25,-39,-52,121,71,62,126,-103,-112,46,39,31,34,-74,-82,90,79,105,-23,60,-119,-40,51,10,-90,101,-111,-53,-51,-102,4,-47,118,57,-88,-112,-66,0,105,-51,6,127,101,-55,38,-77,-113,-85,37,126,-30,-4,34,-52,61,-1,60,-75,37,-23,-57,89,89,-97,-34,30,54,-39,-9,6,-118,-56,111,-75,-83,41,42,-42,-25,38,-113,75,101,-49,-14,-32,23,-71,-26,-113,123,87,56,-116,13,111,-28,94,-114,35,-124,118,79,74,-21,-74,46,119,-55,-6,-35,84,-96,80,102,2,-42,-89,126,-26,-84,81,64,55,110,-89,-105,11,69,-99,-91,42,-14,88,-59,-23,-14,93,-2,-79,-110,33,-66,-51,30,-99,-122,-115,63,118,13,60,-81,-80,79,27,-71,87,-49,-80,119,64,-41,65,-110,-116,55,-41,-70,-71,49,-117,-48,-101,105,22,-115,29,-125,-79,59,-31,-34,36,75,-53,-2,-36,-84,13,5,33,115,-89,-103,21,-115,-110,-36,-66,49,-14,-118,124,100,-67,-82,-82,-109,102,-97,-30,78,87,-19,-33,-5,-36,85,-121,-18,-2,98,-83,-97,52,100,-35,13,62,13,30,44,8,-122,5,-37,107,103,-20,-74,81,63,27,-97,-12,-99,-14,85,87,-15,20,-66,56,-33,118,27,89,6,-21,-75,55,66,7,-106,-31,62,-75,-34,12,-36,-105,-21,6,33,118,69,76,101,20,-110,-38,-75,92,73,-52,-23,108,35,-65,-82,75,111,-120,-56,-90,31,-77,92,99,87,40,11,-42,8,-118,29,-49,56,97,20,-42,-128,-17,96,-58,-3,60,-121,115,93,-126,-105,60,7,110,33,80,104,15,33,-2,91,-57,103,4,-41,71,-116,-67,-14,42,-75,-89,-56,-104,-95,-113,-86,-38,22,-106,8,125,-2,30,70,48,3,84,-53,-58,-102,64,44,34,77,-106,60,-110,-95,-20,29,31,29,-79,-108,109,52,83,121,-31,-102,51,-88,65,42,-4,18,61,55,-67,-94,-124,-70,-15,119,44,-85,-117,-127,-28,95,64,23,-42,-121,-80,56,107,-10,75,5,40,105,117,-86,-94,-56,75,16,10,-80,-70,-33,-15,-78,-124,7,124,-78,-101,-102,123,99,70,119,32,118,84,-111,-36,0,2,120,17,-72,-10,-121,15,78,70,-122,37,123,-93,5,2,4,-41,22,-67,4,-43,16,-37,-95,91,-33,49,-40,-102,17,102,-120,-107,5,86,43,41,84,-4,-50,-88,28,54,-98,109,123,-75,71,94,95,86,28,0,-24,0,109,-109,3,18,-31,102,62,-121,-108,32,-112,-3,-38,95,-27,-74,-56,34,65,31,60,59,-76,-51,126,-15,-8,-41,-71,-34,-125,-87,-127,58,-120,-59,-68,24,-65,13,53,-69,-24,117,82,55,61,91,93,118,50,120,-59,110,60,88,-90,-61,-91,19,10,-112,27,20,82,17,74,-117,-116,20,117,-94,-85,-50,1,111,118,-20,79,-57,41,-7,87,20,7,77,118,63,-70,75,-63,-1,-66,15,95,-22,99,-128,-109,51,98,-42,-56,-66,-6,41,-95,-74,9,6,-37,-13,-73,-119,-43,-32,57,107,40,125,53,100,28,114,-96,-39,-102,28,-59,6,59,22,97,-124,52,65,17,-61,-70,116,36,8,109,12,-65,-55,46,66,111,94,118,-52,84,-1,40,0,-28,-38,-124,82,-92,53,14,-114,-121,114,-66,-83,-127,118,124,94,24,17,-50,-50,-59,-57,-55,-55,-9,-61,120,21,36,-45,85,40,-31,-27,-40,-23,-94,-41,74,-4,26,74,-112,-94,-1,-40,82,-77,83,-36,-59,-32,78,-127,46,97,120,-124,108,-48,91,32,85,-45,-99,56,21,-45,9,-39,105,113,2,-126,109,33,-121,60,6,37,-84,78,-5,59,30,110,2,60,-22,-41,76,-97,87,-51,-116,-54,-118,40,29,107,58,38,-118,19,-123,11,-42,47,-51,-128,-115,38,115,42,40,-12,-45,58,18,-90,15,12,56,-106,18,49,-93,90,-42,-21,-82,24,-120,86,98,88,96,-110,32,64,63,7,-33,125,51,-123,42,-81,79,55,4,124,88,71,-61,27,-102,-30,23,122,-126,-48,27,5,77,-56,17,-118,-80,50,41,49,-127,82,74,111,-20,-95,83,-16,122,-107,-3,-122,96,-94,-119,59,93,73,101,-123,-15,8,-57,-124,-16,5,7,43,-77,-90,44,70,70,17,103,84,-33,77,86,80,-70,57,-89,-107,-69,-47,-61,2,-28,-60,107,43,-32,-52,-94,-24,118,29,90,-115,92,-12,102,-19,36,59,-104,68,12,37,7,-118,-8,-40,77,-18,-124,-101,-49,21,16,101,4,-34,120,-115,89,73,122,-123,106,109,-8,-85,75,76,-56,98,110,-106,-53,0,75,-107,89,-48,-35,-11,66,-73,-113,-57,-19,93,121,-81,-66,39,94,53,-10,68,-95,-123,79,40,87,120,27,87,93,91,-10,-79,-66,-19,-62,53,25,12,18,-83,54,-54,16,-84,-110,-107,-126,119,-58,-80,-116,25,65,73,-118,43,57,-114,54,-89,-69,-120,-118,-14,-70,-54,-55,-102,88,107,-126,124,18,-50,35,118,-34,-77,-86,28,-2,-68,75,74,101,27,93,-28,-10,-112,-93,-56,-102,91,-123,-74,-122,122,98,-92,73,-105,79,-40,21,17,-104,35,-83,-75,-36,9,-46,31,-35,-43,5,-121,44,-111,-127,22,-19,45,12,27,58,-94,-9,-117,39,-88,-47,81,61,91,47,36,65,102,-36,23,8,-65,-23,-72,-116,83,125,110,-42,62,109,120,52,89,11,2,114,88,97,119,88,-108,95,-99,-14,-24,22,-61,80,73,2,30,57,-74,119,-88,86,-19,-51,9,80,119,-69,-75,-6,-39,4,-77,16,42,123,-36,-121,-25,76,19,30,101,92,-50,-40,33,19,47,73,-22,-22,-30,66,-40,-82,-95,7,-2,99,-48,43,82,-127,101,-15,-48,27,98,-118,109,-17,30,122,-52,12,93,-108,15,-73,-30,-10,31,11,-102,35,34,-11,-25,-75,93,67,25,22,16,55,-124,-67,47,67,91,-49,119,59,-19,124,75,44,-6,-51,7,-66,-69,28,-50,5,-31,-113,-52,107,-109,-77,79,42,13,-113,101,-4,-70,66,-30,-5,45,-124,24,85,-4,1,12,123,-93,18,-55,98,124,-67,32,24,-22,-27,-25,44,-69,99,8,-89,11,113,80,-88,-13,94,-47,-10,-24,-123,-106,-35,52,81,1,54,-36,-56,20,-108,-17,-22,-89,90,53,60,50,76,60,91,64,-76,-70,13,-95,-80,44,73,-60,-65,-88,70,-17,66,-117,-25,32,-117,56,-80,81,-67,-90,45,88,-55,-27,-21,100,-94,-18,-49,-123,-126,-84,-122,112,111,-87,-6,-43,106,-24,-50,-50,-46,-66,119,55,71,64,53,93,-64,-35,41,75,118,13,27,58,-29,83,1,-65,-32,-1,88,27,-86,-54,-92,69,-73,36,49,-51,48,49,-4,98,-75,13,28,49,-101,-78,-104,-31,85,117,-28,-109,-99,87,10,-61,15,-88,33,-89,-96,-111,72,104,-110,-57,100,87,-55,21,-41,-44,110,-72,30,32,70,75,87,-41,50,-58,56,84,-57,122,66,61,-96,-20,111,84,4,-126,-107,104,2,57,66,94,57,105,-6,-36,-89,96,80,-83,119,38,-112,56,-12,-8,56,-69,-119,-24,-58,78,-25,-112,110,64,-96,118,87,-72,100,29,-44,25,-112,30,15,-17,69,73,-31,-121,-121,-86,81,9,44,-111,24,56,74,-36,-57,100,69,48,-20,-126,17,38,-78,45,-12,115,21,40,-5,-55,91,97,105,-72,-26,56,-45,57,55,-121,-8,85,-124,115,29,-96,107,-99,-60,89,-112,50,-20,-121,11,-43,-48,82,10,14,51,-83,-82,15,-71,-17,-98,40,52,-119,43,-74,-91,-107,86,-23,56,-101,104,-40,19,-121,73,-122,94,108,61,35,-22,-30,-84,-83,12,-95,47,106,-60,-102,-23,-55,11,-16,-96,-122,-123,27,-18,-50,-45,-52,100,72,-7,-17,-101,-109,41,-51,110,49,-120,14,2,-119,-85,-78,114,-128,74,-75,99,-122,-124,-57,-113,96,55,77,-91,-39,70,-44,-43,44,-122,39,44,55,83,-14,44,-91,-55,80,-110,-87,-59,123,-64,40,109,-47,118,92,-107,126,-85,32,-103,-32,-121,-4,37,95,-18,21,12,77,-113,-98,77,72,-16,32,-22,-21,-100,39,20,73,-37,-116,10,-112,-99,-38,-27,-80,47,-112,126,90,-122,-110,-113,-25,8,105,67,72,63,12,122,-69,52,66,126,-81,127,25,61,-14,-105,-100,71,74,-53,86,-6,-60,-42,61,78,83,80,-117,68,-94,73,87,110,-77,-35,-26,-1,82,-24,-73,83,39,-121,-86,57,73,101,50,121,-30,67,-20,-12,41,61,-48,-44,92,-73,-120,-80,52,86,6,76,-13,47,37,-7,20,-2,111,-79,10,50,-103,-41,-64,-64,-87,22,-6,37,-106,-76,88,-80,27,11,-33,84,12,96,-127,0,-127,115,-63,81,127,-81,44,14,-124,-7,-65,88,-42,-102,101,-54,0,78,48,-97,-104,-94,104,45,-52,-122,50,-38,-15,30,32,60,47,-78,77,-1,-87,-127,-90,4,41,-24,21,-20,17,95,31,72,-107,-127,-81,113,84,-50,-81,-93,116,-39,0,-74,114,18,32,-75,-91,-98,-116,25,-51,10,44,125,-76,40,97,122,31,113,13,38,81,125,102,63,-66,34,-36,57,33,121,-20,-68,8,55,116,-36,6,-109,-16,-51,9,-16,-95,51,-3,-8,44,-116,-62,72,58,44,-81,105,-27,75,24,53,77,124,89,-56,123,-19,22,-109,108,-19,-117,-49,-17,-98,37,-63,103,-50,-128,-70,6,125,95,-127,-57,7,-117,-110,-2,-14,-58,-52,13,19,-8,65,73,-123,-33,32,-51,-18,-59,25,-88,53,7,-6,104,-6,23,78,89,93,72,-123,53,-87,-75,-83,-33,95,73,5,-49,60,5,-53,29,-118,-114,123,-49,69,56,121,-73,14,-71,-67,-97,-121,-34,20,-3,-22,96,-53,-98,79,60,-126,-92,-83,-118,80,-67,56,13,15,32,-81,98,32,-89,30,73,52,-95,-42,94,8,80,-122,-12,-38,-73,-21,94,33,-52,-57,-63,-11,-96,-112,-125,-57,114,-57,-82,-69,118,-95,-14,-45,26,102,-51,-80,95,-83,-20,99,40,-122,8,85,111,-70,-96,7,-55,78,70,65,6,-56,85,100,66,15,-51,31,48,9,-59,26,91,-60,-46,69,-30,-50,22,-85,50,52,23,22,97,9,-45,-45,29,-108,0,8,64,41,40,21,9,-35,-4,68,111,12,-54,45,26,125,-98,14,-59,-91,20,-100,81,-96,-65,-26,-66,113,8,-66,-62,64,22,22,-120,-93,84,-52,86,-38,126,-104,-26,-13,19,-33,58,117,89,-19,-118,-93,82,45,81,114,-115,-108,-3,-56,101,12,67,-111,101,-126,-39,89,102,116,-47,-21,-106,-41,-47,-11,-124,111,-120,-26,-2,-70,-11,15,-32,-52,122,-40,28,107,-71,65,63,85,-27,83,54,15,49,114,-71,-65,-78,116,-56,5,-79,-69,8,-75,-19,97,34,-56,-41,-31,-47,-89,56,-2,-69,3,102,-47,-121,-77,-24,103,55,-87,122,-73,-125,-100,-84,-90,51,107,-8,-20,56,127,8,-113,-62,-119,38,-84,81,94,60,77,40,-11,107,107,-87,37,89,-97,58,-51,53,-86,-87,121,-72,49,4,-33,-56,76,-84,0,74,127,117,-28,-6,-84,-59,77,-5,34,-110,55,-50,-33,-31,84,95,-24,-51,-106,93,-77,88,-35,75,31,20,-75,17,-19,-128,28,-36,56,-107,-121,-35,75,-73,32,-83,-83,-3,-56,-22,68,-40,-89,77,8,116,115,-26,52,-77,-29,-8,-91,-78,-120,-79,-17,10,108,-60,-81,93,11,-67,10,104,-38,-29,65,-12,-71,-113,80,-2,-81,71,68,15,50,76,40,-25,
	        //-73,-76,-84,83,40,-98,118,-5,-50,49,-87,-68,79,-96,-47,122,73,48,50,-84,78,-107,-105,-108,3,105,66,75,88,65,45,-110,127,10,-5,-101,-39,74,-31,76,-107,-83,107,-118,52,55,-2,-15,3,-20,-37,-57,119,-2,-64,15,-4,-18,-45,4,40,-83,-125,-59,-28,2,-30,44,-22,47,-67,-40,25,48,-84,94,98,80,99,9,-12,1,90,-17,81,107,-35,-88,-125,16,-65,-127,-32,95,57,25,-25,-121,-50,53,-100,-85,49,14,34,39,-55,-82,-9,39,79,74,-10,-110,-31,-67,-107,3,14,-120,54,-14,-115,-46,-106,6,-39,-32,21,7,97,-43,26,-8,-119,5,-94,-59,-61,-93,-82,84,115,26,-126,-38,-29,79,-2,-40,103,-97,9,-54,14,-84,-116,24,127,64,94,55,96,-126,112,118,81,95,-108,124,114,39,-107,-55,-23,64,-94,-60,-97,-121,-64,-68,39,77,-29,-22,103,89,12,99,76,-100,87,-71,124,-35,39,45,60,96,-61,-5,88,-22,-36,66,30,-78,-25,-77,73,88,-38,118,-13,17,55,68,94,-37,-118,-101,-51,113,-52,-125,36,11,-31,-94,-55,-18,-102,-79,119,73,5,12,11,36,110,84,9,-128,-46,-107,25,-112,9,38,126,-78,-29,-22,-64,-123,72,-31,50,-16,-68,-2,57,-43,-78,108,55,76,-93,-26,118,26,-92,3,87,81,-13,-90,-30,-17,125,-12,-9,-55,65,-39,-124,-24,-125,67,25,1,-3,-71,-24,-2,-93,6,-41,-67,108,84,104,104,-86,-43,31,60,78,-26,-39,-61,32,-60,-48,-39,38,121,-32,-75,-68,-45,-119,-112,125,76,-33,22,-51,18,93,49,112,42,-29,-79,62,125,85,-121,61,-96,60,-86,-21,7,10,-19,-23,18,-23,-123,-123,-62,-26,16,-20,14,-3,-88,59,-98,-112,-84,-98,-123,-61,-84,-57,-111,67,53,-124,-124,-114,-106,26,5,114,126,-85,63,-55,-83,-119,96,-38,5,79,120,51,-90,-70,124,-100,-17,-91,-28,-118,-122,11,118,110,-108,-49,-2,-68,86,82,78,110,-72,-40,-54,-126,41,66,-36,-128,-60,-20,-64,-31,-67,17,56,-77,-64,-80,-121,89,-33,-103,-79,-15,94,-11,17,-109,-87,28,35,-3,-73,-57,-36,5,49,-89,87,92,32,61,-52,-43,-9,-75,71,-48,-20,112,-22,-62,-2,125,48,55,112,65,23,53,102,-62,-32,93,-47,-35,23,62,49,-85,-20,62,-103,111,88,90,85,10,17,79,85,103,59,37,-9,30,84,-19,-15,101,-78,113,-75,108,-1,21,-93,-106,-48,29,65,40,-127,85,62,117,105,24,52,70,-109,-61,-81,34,-108,76,-87,-17,93,118,-77,-43,26,-67,-39,-118,-31,-120,101,-79,-36,-115,1,73,-13,-86,96,-43,-123,83,49,107,40,49,-65,29,-58,90,-120,-108,52,54,89,73,11,-49,58,-54,75,111,-92,-27,-46,103,76,60,119,-66,10,-13,-80,32,27,-74,26,-101,-33,47,-62,-108,12,-79,-3,34,-12,-41,97,77,3,-116,-59,123,-37,127,-99,-88,83,118,-49,-70,66,30,125,-81,59,79,-67,-72,-46,-110,-97,125,-96,37,-126,95,-101,-100,-70,50,34,-41,-11,113,-25,-16,-90,-51,34,-118,-43,90,122,37,-107,26,18,43,110,-73,42,59,7,-34,51,48,-63,64,49,118,-102,-68,-20,-9,-93,-13,74,-90,-24,-37,-99,-99,64,21,14,92,120,-73,-12,66,-2,-37,-55,5,95,-103,33,38,81,97,-54,106,-117,-25,19,50,126,25,38,59,88,-76,32,19,70,-3,19,-64,-112,-120,-92,-8,115,35,-117,111,-93,95,99,123,-123,75,-53,-87,-116,-54,-2,-21,116,63,82,-1,127,-74,9,-121,125,86,-61,77,40,-93,88,-42,107,77,-115,-99,99,47,-71,29,-8,40,78,113,-113,-84,-47,7,43,-80,-106,64,-72,-92,95,-75,96,19,62,-74,29,104,19,-127,-4,0,107,79,105,-29,-59,-93,-69,-64,-55,-89,-77,-32,-8,4,-2,-43,96,124,24,33,0,104,-15,-71,113,94,22,-112,82,114,82,-23,-65,-86,-25,-34,-90,-106,-125,-30,73,-28,23,114,101,97,126,-111,53,33,-21,-112,-89,41,-61,63,97,-52,11,41,-12,-114,105,29,-81,-109,-66,-36,-118,-36,96,88,-91,-101,-50,-60,-15,53,-111,76,77,-19,63,38,-23,-49,-103,84,-84,-52,103,-4,-13,13,-10,-70,-99,-57,-120,4,-40,-93,-96,-36,67,-10,8,-91,-74,-45,-112,107,-30,67,22,71,-79,85,43,45,55,101,81,5,-7,-15,-25,-31,-5,49,-70,-71,-43,-46,2,-59,-86,84,-34,70,78,-62,71,71,0,109,-90,67,118,125,-29,-47,30,13,76,29,-25,110,-19,73,-36,-36,5,-16,-105,-121,-52,-115,72,50,88,118,-91,23,-31,34,107,-99,104,-74,93,25,-114,-62,27,-2,122,-76,11,85,-32,-71,26,-31,42,-86,-76,-82,102,42,86,-100,-122,-30,-104,45,41,43,-63,-93,-124,-66,-72,-72,-62,-4,56,8,-72,0,70,-101,-65,31,83,-101,-101,-106,110,0,-14,125,-57,-112,-85,-12,-64,-109,-47,32,-83,-64,-122,73,-44,-80,-65,54,-26,24,-11,123,-3,36,34,113,-59,-98,49,-87,30,-6,-3,-35,-69,-122,-94,58,-90,28,70,-98,-95,-73,70,101,-1,51,105,59,62,105,-28,-34,-114,123,117,-73,-44,-58,74,-110,-121,56,77,97,-88,-23,-40,-97,31,-97,96,-124,88,-68,-41,-36,-70,-83,-117,-39,1,-8,38,-78,28,-34,56,62,104,108,126,-40,101,-96,92,-24,8,-52,-12,97,82,-74,52,50,-70,-16,-7,-3,-52,89,-25,-110,47,63,-79,-79,60,64,-90,13,-54,-85,121,93,-67,50,10,-83,-23,-34,39,107,17,-21,106,111,40,26,110,91,-69,-31,-45,-72,-96,-87,69,-124,22,75,98,-21,66,-10,117,41,-117,88,-51,-118,-12,-82,-89,36,-30,13,14,-116,100,102,54,73,21,-3,-16,-63,-70,-4,-88,86,56,122,100,46,-117,-121,-123,103,7,44,12,103,-11,-84,-95,-33,90,79,-25,86,-44,0,86,-117,-119,-73,-5,-71,29,-78,21,111,-66,66,29,-40,88,42,-16,26,8,26,52,7,-113,23,3,46,-70,-15,-2,-35,48,-126,-33,41,82,3,-108,53,-46,-7,105,-24,-21,-93,-86,85,-101,-110,11,-126,35,3,19,-90,-33,48,44,115,-92,-61,-40,-122,-40,2,10,-77,3,-66,37,-71,-88,57,-23,-7,-115,-37,-102,68,27,-118,-83,110,-4,86,-34,62,-66,9,-95,-111,-80,89,87,-119,-68,-108,-25,122,-74,-43,85,-8,-118,57,-13,-33,-77,-46,15,-75,54,-7,-27,-1,16,-85,9,18,79,67,16,-12,15,-19,-112,114,-126,115,-92,-90,-124,16,-81,97,85,-33,24,121,123,-94,101,85,41,-110,89,-13,-23,-23,-118,-77,-68,107,57,-30,-13,13,4,-104,125,-88,24,-115,-94,-90,-84,39,-83,42,22,-62,-26,-19,82,-100,26,-33,-46,-3,3,-105,79,-88,1,66,56,76,-94,117,57,-44,-10,-86,-115,-77,-111,-111,40,-51,69,-67,-15,102,7,100,27,22,-124,86,-50,100,-69,-73,107,123,-106,33,-56,57,-105,113,-2,30,-34,44,-45,30,-80,-64,7,-84,-114,-25,-39,-52,121,71,62,126,-103,-112,46,39,31,34,-74,-82,90,79,105,-23,60,-119,-40,51,10,-90,101,-111,-53,-51,-102,4,-47,118,57,-88,-112,-66,0,105,-51,6,127,101,-55,38,-77,-113,-85,37,126,-30,-4,34,-52,61,-1,60,-75,37,-23,-57,89,89,-97,-34,30,54,-39,-9,6,-118,-56,111,-75,-83,41,42,-42,-25,38,-113,75,101,-49,-14,-32,23,-71,-26,-113,123,87,56,-116,13,111,-28,94,-114,35,-124,118,79,74,-21,-74,46,119,-55,-6,-35,84,-96,80,102,2,-42,-89,126,-26,-84,81,64,55,110,-89,-105,11,69,-99,-91,42,-14,88,-59,-23,-14,93,-2,-79,-110,33,-66,-51,30,-99,-122,-115,63,118,13,60,-81,-80,79,27,-71,87,-49,-80,119,64,-41,65,-110,-116,55,-41,-70,-71,49,-117,-48,-101,105,22,-115,29,-125,-79,59,-31,-34,36,75,-53,-2,-36,-84,13,5,33,115,-89,-103,21,-115,-110,-36,-66,49,-14,-118,124,100,-67,-82,-82,-109,102,-97,-30,78,87,-19,-33,-5,-36,85,-121,-18,-2,98,-83,-97,52,100,-35,13,62,13,30,44,8,-122,5,-37,107,103,-20,-74,81,63,27,-97,-12,-99,-14,85,87,-15,20,-66,56,-33,118,27,89,6,-21,-75,55,66,7,-106,-31,62,-75,-34,12,-36,-105,-21,6,33,118,69,76,101,20,-110,-38,-75,92,73,-52,-23,108,35,-65,-82,75,111,-120,-56,-90,31,-77,92,99,87,40,11,-42,8,-118,29,-49,56,97,20,-42,-128,-17,96,-58,-3,60,-121,115,93,-126,-105,60,7,110,33,80,104,15,33,-2,91,-57,103,4,-41,71,-116,-67,-14,42,-75,-89,-56,-104,-95,-113,-86,-38,22,-106,8,125,-2,30,70,48,3,84,-53,-58,-102,64,44,34,77,-106,60,-110,-95,-20,29,31,29,-79,-108,109,52,83,121,-31,-102,51,-88,65,42,-4,18,61,55,-67,-94,-124,-70,-15,119,44,-85,-117,-127,-28,95,64,23,-42,-121,-80,56,107,-10,75,5,40,105,117,-86,-94,-56,75,16,10,-80,-70,-33,-15,-78,-124,7,124,-78,-101,-102,123,99,70,119,32,118,84,-111,-36,0,2,120,17,-72,-10,-121,15,78,70,-122,37,123,-93,5,2,4,-41,22,-67,4,-43,16,-37,-95,91,-33,49,-40,-102,17,102,-120,-107,5,86,43,41,84,-4,-50,-88,28,54,-98,109,123,-75,71,94,95,86,28,0,-24,0,109,-109,3,18,-31,102,62,-121,-108,32,-112,-3,-38,95,-27,-74,-56,34,65,31,60,59,-76,-51,126,-15,-8,-41,-71,-34,-125,-87,-127,58,-120,-59,-68,24,-65,13,53,-69,-24,117,82,55,61,91,93,118,50,120,-59,110,60,88,-90,-61,-91,19,10,-112,27,20,82,17,74,-117,-116,20,117,-94,-85,-50,1,111,118,-20,79,-57,41,-7,87,20,7,77,118,63,-70,75,-63,-1,-66,15,95,-22,99,-128,-109,51,98,-42,-56,-66,-6,41,-95,-74,9,6,-37,-13,-73,-119,-43,-32,57,107,40,125,53,100,28,114,-96,-39,-102,28,-59,6,59,22,97,-124,52,65,17,-61,-70,116,36,8,109,12,-65,-55,46,66,111,94,118,-52,84,-1,40,0,-28,-38,-124,82,-92,53,14,-114,-121,114,-66,-83,-127,118,124,94,24,17,-50,-50,-59,-57,-55,-55,-9,-61,120,21,36,-45,85,40,-31,-27,-40,-23,-94,-41,74,-4,26,74,-112,-94,-1,-40,82,-77,83,-36,-59,-32,78,-127,46,97,120,-124,108,-48,91,32,85,-45,-99,56,21,-45,9,-39,105,113,2,-126,109,33,-121,60,6,37,-84,78,-5,59,30,110,2,60,-22,-41,76,-97,87,-51,-116,-54,-118,40,29,107,58,38,-118,19,-123,11,-42,47,-51,-128,-115,38,115,42,40,-12,-45,58,18,-90,15,12,56,-106,18,49,-93,90,-42,-21,-82,24,-120,86,98,88,96,-110,32,64,63,7,-33,125,51,-123,42,-81,79,55,4,124,88,71,-61,27,-102,-30,23,122,-126,-48,27,5,77,-56,17,-118,-80,50,41,49,-127,82,74,111,-20,-95,83,-16,122,-107,-3,-122,96,-94,-119,59,93,73,101,-123,-15,8,-57,-124,-16,5,7,43,-77,-90,44,70,70,17,103,84,-33,77,86,80,-70,57,-89,-107,-69,-47,-61,2,-28,-60,107,43,-32,-52,-94,-24,118,29,90,-115,92,-12,102,-19,36,59,-104,68,12,37,7,-118,-8,-40,77,-18,-124,-101,-49,21,16,101,4,-34,120,-115,89,73,122,-123,106,109,-8,-85,75,76,-56,98,110,-106,-53,0,75,-107,89,-48,-35,-11,66,-73,-113,-57,-19,93,121,-81,-66,39,94,53,-10,68,-95,-123,79,40,87,120,27,87,93,91,-10,-79,-66,-19,-62,53,25,12,18,-83,54,-54,16,-84,-110,-107,-126,119,-58,-80,-116,25,65,73,-118,43,57,-114,54,-89,-69,-120,-118,-14,-70,-54,-55,-102,88,107,-126,124,18,-50,35,118,-34,-77,-86,28,-2,-68,75,74,101,27,93,-28,-10,-112,-93,-56,-102,91,-123,-74,-122,122,98,-92,73,-105,79,-40,21,17,-104,35,-83,-75,-36,9,-46,31,-35,-43,5,-121,44,-111,-127,22,-19,45,12,27,58,-94,-9,-117,39,-88,-47,81,61,91,47,36,65,102,-36,23,8,-65,-23,-72,-116,83,125,110,-42,62,109,120,52,89,11,2,114,88,97,119,88,-108,95,-99,-14,-24,22,-61,80,73,2,30,57,-74,119,-88,86,-19,-51,9,80,119,-69,-75,-6,-39,4,-77,16,42,123,-36,-121,-25,76,19,30,101,92,-50,-40,33,19,47,73,-22,-22,-30,66,-40,-82,-95,7,-2,99,-48,43,82,-127,101,-15,-48,27,98,-118,109,-17,30,122,-52,12,93,-108,15,-73,-30,-10,31,11,-102,35,34,-11,-25,-75,93,67,25,22,16,55,-124,-67,47,67,91,-49,119,59,-19,124,75,44,-6,-51,7,-66,-69,28,-50,5,-31,-113,-52,107,-109,-77,79,42,13,-113,101,-4,-70,66,-30,-5,45,-124,24,85,-4,1,12,123,-93,18,-55,98,124,-67,32,24,-22,-27,-25,44,-69,99,8,-89,11,113,80,-88,-13,94,-47,-10,-24,-123,-106,-35,52,81,1,54,-36,-56,20,-108,-17,-22,-89,90,53,60,50,76,60,91,64,-76,-70,13,-95,-80,44,73,-60,-65,-88,70,-17,66,-117,-25,32,-117,56,-80,81,-67,-90,45,88,-55,-27,-21,100,-94,-18,-49,-123,-126,-84,-122,112,111,-87,-6,-43,106,-24,-50,-50,-46,-66,119,55,71,64,53,93,-64,-35,41,75,118,13,27,58,-29,83,1,-65,-32,-1,88,27,-86,-54,-92,69,-73,36,49,-51,48,49,-4,98,-75,13,28,49,-101,-78,-104,-31,85,117,-28,-109,-99,87,10,-61,15,-88,33,-89,-96,-111,72,104,-110,-57,100,87,-55,21,-41,-44,110,-72,30,32,70,75,87,-41,50,-58,56,84,-57,122,66,61,-96,-20,111,84,4,-126,-107,104,2,57,66,94,57,105,-6,-36,-89,96,80,-83,119,38,-112,56,-12,-8,56,-69,-119,-24,-58,78,-25,-112,110,64,-96,118,87,-72,100,29,-44,25,-112,30,15,-17,69,73,-31,-121,-121,-86,81,9,44,-111,24,56,74,-36,-57,100,69,48,-20,-126,17,38,-78,45,-12,115,21,40,-5,-55,91,97,105,-72,-26,56,-45,57,55,-121,-8,85,-124,115,29,-96,107,-99,-60,89,-112,50,-20,-121,11,-43,-48,82,10,14,51,-83,-82,15,-71,-17,-98,40,52,-119,43,-74,-91,-107,86,-23,56,-101,104,-40,19,-121,73,-122,94,108,61,35,-22,-30,-84,-83,12,-95,47,106,-60,-102,-23,-55,11,-16,-96,-122,-123,27,-18,-50,-45,-52,100,72,-7,-17,-101,-109,41,-51,110,49,-120,14,2,-119,-85,-78,114,-128,74,-75,99,-122,-124,-57,-113,96,55,77,-91,-39,70,-44,-43,44,-122,39,44,55,83,-14,44,-91,-55,80,-110,-87,-59,123,-64,40,109,-47,118,92,-107,126,-85,32,-103,-32,-121,-4,37,95,-18,21,12,77,-113,-98,77,72,-16,32,-22,-21,-100,39,20,73,-37,-116,10,-112,-99,-38,-27,-80,47,-112,126,90,-122,-110,-113,-25,8,105,67,72,63,12,122,-69,52,66,126,-81,127,25,61,-14,-105,-100,71,74,-53,86,-6,-60,-42,61,78,83,80,-117,68,-94,73,87,110,-77,-35,-26,-1,82,-24,-73,83,39,-121,-86,57,73,101,50,121,-30,67,-20,-12,41,61,-48,-44,92,-73,-120,-80,52,86,6,76,-13,47,37,-7,20,-2,111,-79,10,50,-103,-41,-64,-64,-87,22,-6,37,-106,-76,88,-80,27,11,-33,84,12,96,-127,0,-127,115,-63,81,127,-81,44,14,-124,-7,-65,88,-42,-102,101,-54,0,78,48,-97,-104,-94,104,45,-52,-122,50,-38,-15,30,32,60,47,-78,77,-1,-87,-127,-90,4,41,-24,21,-20,17,95,31,72,-107,-127,-81,113,84,-50,-81,-93,116,-39,0,-74,114,18,32,-75,-91,-98,-116,25,-51,10,44,125,-76,40,97,122,31,113,13,38,81,125,102,63,-66,34,-36,57,33,121,-20,-68,8,55,116,-36,6,-109,-16,-51,9,-16,-95,51,-3,-8,44,-116,-62,72,58,44,-81,105,-27,75,24,53,77,124,89,-56,123,-19,22,-109,108,-19,-117,-49,-17,-98,37,-63,103,-50,-128,-70,6,125,95,-127,-57,7,-117,-110,-2,-14,-58,-52,13,19,-8,65,73,-123,-33,32,-51,-18,-59,25,-88,53,7,-6,104,-6,23,78,89,93,72,-123,53,-87,-75,-83,-33,95,73,5,-49,60,5,-53,29,-118,-114,123,-49,69,56,121,-73,14,-71,-67,-97,-121,-34,20,-3,-22,96,-53,-98,79,60,-126,-92,-83,-118,80,-67,56,13,15,32,-81,98,32,-89,30,73,52,-95,-42,94,8,80,-122,-12,-38,-73,-21,94,33,-52,-57,-63,-11,-96,-112,-125,-57,114,-57,-82,-69,118,-95,-14,-45,26,102,-51,-80,95,-83,-20,99,40,-122,8,85,111,-70,-96,7,-55,78,70,65,6,-56,85,100,66,15,-51,31,48,9,-59,26,91,-60,-46,69,-30,-50,22,-85,50,52,23,22,97,9,-45,-45,29,-108,0,8,64,41,40,21,9,-35,-4,68,111,12,-54,45,26,125,-98,14,-59,-91,20,-100,81,-96,-65,-26,-66,113,8,-66,-62,64,22,22,-120,-93,84,-52,86,-38,126,-104,-26,-13,19,-33,58,117,89,-19,-118,-93,82,45,81,114,-115,-108,-3,-56,101,12,67,-111,101,-126,-39,89,102,35,-124,90,32,109,-108,115,112,-4,58,24,101,-36,-7,28,-20,-9,-73,48,-45,-28,71,83,105,-56,31,-10,121,76,66,-59,-98,4,-56,0,119,-7,91,117,-103,-16,115,104,112,-37,105,-48,14,-78,103,9,4,-97,94,-28,80,48,43,102,-32,42,-117,56,64,55,88,109,86,6,-84,28,-66,-119,74,-19,-107,-103,68,3,35,56,-3,-49,-105,-84,-124,-63,-53,-91,119,111,-32,-40,49,107,-2,52,61,-57,32,-2,34,-57,-1,-113,108,-90,-81,77,-31,85,-43,-42,38,-24,-63,33,57,-59,-54,-15,-18,-102,-35,-92,-66,116,-58,-63,-80,2,-23,-44,27,-90,92,-46,-89,-66,81,95,41,106,80,49,-120,-71,-121,-104,79,46,24,126,85,99,103,-17,-33,97,-94,-28,-30,-18,88,13,-44,-115,52,83,-127,-50,71,-1,-63,106,-110,-48,46,-80,-47,-86,36,-10,-32,92,26,-45,60,-114,-38,37,14,

	    }
	    public  void posteventlog(long sendTime,String logJsonAry)
	    {
	    	String str = "";
	    	/*String sid = "";
	    	String packageName = "com.pansen.zumalocal";
	    	String mac = "d4:97:0b:69:c7:07";
	    	String cid = "7f9c9fae657f9ebe4a";
	    	String OsVersion="4.4.4";
	    	String phoneOs = "android+"+OsVersion;
	    	String sdkVersion = "3.1.1";
	    	String logJsonAry = "";
	    	String protocolVersion = "3.1.0";
	    	String manufacturer = "Xiaomi";
	    	String versionName = "1.0";
	    	String MMversionName = "1.2.3";
	    	String accessPoint = "wifi";
	    	String deviceDetail = "MI+3C";
	    	String channel = "0000000000";
	    	String deviceId = "865903029745048";
	    	String appKey = "300008702230";
	    	String appid = "300008702230";
	    	String imsi = "460029140953268";
	    	String model = "MI 3C";
	    	String  resolution = "1080*1920";*/
	    
	    	//String strUrl = "http://da.mmarket.com/mmsdk/mmsdk?func=mmsdk:posteventlog&appkey=300008702230&channel=0000000000&code=105";
	    	String strUrl = "http://da.mmarket.com/mmsdk/mmsdk?func=mmsdk:posteventlog&appkey="+appKey+"&channel="+channel+"&code=105";
	    	
	    	JSONObject reqjson = new JSONObject();
			reqjson.put("sid",sid);
			reqjson.put("packageName",packageName);//1422440187872
			                                       //1422440187
			reqjson.put("sendTime",sendTime);
			reqjson.put("versionCode",1);
			reqjson.put("mac",mac);
			reqjson.put("pid",1);
			reqjson.put("versionName",versionName);
			reqjson.put("cid",cid);
			reqjson.put("phoneOs",phoneOs);
			reqjson.put("sdkVersion",sdkVersion);
			reqjson.put("logJsonAry",logJsonAry);
			reqjson.put("protocolVersion",protocolVersion);
			reqjson.put("manufacturer",manufacturer);
			reqjson.put("accessPoint",accessPoint);
			reqjson.put("deviceDetail",deviceDetail);
			reqjson.put("channel",channel);
			reqjson.put("deviceId",deviceId);
			reqjson.put("appKey",appKey);
		
			str = reqjson.toString();
			System.out.println("strUrl="+strUrl);
			System.out.println("str="+str);
			d td= d.a();
			byte [] bytes =td.a(str);
			//System.out.println(byteToStr (bytes));
			
			try {
				String rsq =HttpUtils.URLPostUTF8(strUrl, bytes);
				System.out.println("rsq"+rsq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    //第三步系统消息
	    public  void postsyslog()
	    {
	    	String str = "";
	    	/*String packageName = "com.pansen.zumalocal";
	    	String mac = "d4:97:0b:69:c7:07";
	    	
	    	String OsVersion="4.4.4";
	    	String phoneOs = "android+"+OsVersion;
	    	String sdkVersion = "3.1.1";
	    	String logJsonAry = "";
	    	String protocolVersion = "3.1.0";
	    	String manufacturer = "Xiaomi";
	    	String versionName = "1.0";
	    	String MMversionName = "1.2.3";
	    	String accessPoint = "wifi";
	    	String deviceDetail = "MI+3C";
	    	String channel = "0000000000";
	    	String deviceId = "865903029745048";
	    	String appKey = "300008702230";
	    	//String appid = "300008702230";
	    	String imsi = "460029140953268";
	    	String model = "MI 3C";
	    	String  resolution = "1080*1920";
	    	String cpuRatioCur = "1728000";
	    	String MainActivity = "mobi.shoumeng.game.demo.MainActivity";
	    	String menoryRatio ="1893832";
	    	String cpuRatioMax = "2265600";
	    	int screenHeight = 1920;
	    	int screenDensity = 480;
	    	int screenWidth = 1080;
	    	String languageCode = "zh";
	    	String countryCode = "CN";
	    	String carrierName = "China+Mobile";*/
	    	
	    	//long sys_sendTime =1422511917406L;//1422857462783L;// System.currentTimeMillis();//1422511917327L;
	    	//long actTime = 1422511917320L;
	    	
	    	//long sendTime =  1422511917327L;// System.currentTimeMillis();//1422511917327L;
	    	//long beginTime = 1422511917195L;
	    	
	    	//cid = "7f9c9fae657f9ebe4a";//cid =  String.valueOf(Rsa.getMD5(replace(deviceId)).toCharArray(), 7, 18);
	    	
	    	//int i1 = new Random().nextInt(10);
	    	//String paramContext="";
	        //sid = "f9585dbed037d81c";//String.valueOf(Rsa.getMD5(System.currentTimeMillis() + appKey + replace(deviceId)/*g.a(paramContext,deviceId)*/ + mac + i1).toCharArray(), 8, 16);
	        
	        /*JSONObject logJson= new JSONObject();
	        int flowConsumpRev = 0;
	        int flowConsumpSnd = 0;
	        logJson.put("flowConsumpRev",flowConsumpRev);
	        logJson.put("flowConsumpSnd",flowConsumpSnd);
	        logJson.put("sid",sid);
	        logJson.put("logs","onResume|"+MainActivity+"|"+actTime+"|0|\n");
	    	logJsonAry = "["+logJson.toString()+"]";*/
	    	
	        //{"screenHeight":1920,"packageName":"com.pansen.zumalocal","sendTime":1422857462783,"countryCode":"CN","versionCode":1,"pid":1,"mac":"d4:97:0b:69:c7:07","versionName":"1.0","phoneOS":"android+4.4.4","cid":"7f9c9fae657f9ebe4a","screenDensity":480,"sdkVersion":"3.1.1","languageCode":"zh","protocolVersion":"3.1.0","screenWidth":1080,"manufacturer":"Xiaomi","carrierName":"China+Mobile","accessPoint":"wifi","imsi":"460029140953268","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}
	        //{"screenHeight":1920,"packageName":"com.pansen.zumalocal","sendTime":1422857462783,"countryCode":"CN","versionCode":1,"pid":1,"mac":"d4:97:0b:69:c7:07","versionName":"1.0","phoneOs":"android+4.4.4","cid":"7f9c9fae657f9ebe4a","screenDensity":480,"sdkVersion":"3.1.1","languageCode":"zh","protocolVersion":"3.1.0","screenWidth":1080,"manufacturer":"Xiaomi","carrierName":"China+Mobile","accessPoint":"wifi","imsi":"460029140953268","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}

	        //31,-117,8,0,0,0,0,0,0,0,5,-63,-57,-94,107,64,0,0,-48,-67,95,-55,66,-25,90,-68,-123,24,-67,-121,24,-55,78,-17,100,116,-66,-2,-99,19,5,58,-23,29,22,-9,4,108,-102,27,-69,48,114,-3,109,-95,62,94,68,90,123,71,-44,-54,-119,90,8,80,-105,90,-7,25,21,77,69,34,37,-82,-60,-3,-121,-81,93,57,79,123,95,58,-9,115,57,-95,-20,-123,-114,-105,98,99,-74,-84,82,-120,83,-18,-98,24,-60,45,-88,49,-52,-13,67,96,66,-16,104,-60,115,-24,-107,-110,16,-76,31,67,23,105,-82,30,121,74,-45,118,-9,26,79,-18,-122,44,-13,49,106,62,48,50,-101,103,14,34,-113,68,-114,89,-79,-11,41,103,-120,45,64,125,22,1,77,-67,42,50,98,5,-80,-97,126,53,32,-75,-47,76,79,95,36,72,-18,-46,55,-76,-13,91,56,39,-61,116,82,-21,88,9,-67,-90,102,-43,-23,-85,-30,-78,24,115,-88,-94,-26,-94,29,23,-13,-66,-121,12,-38,122,-115,-39,22,45,84,34,126,7,18,-40,-22,-80,-45,-100,-69,55,51,55,110,-7,42,121,2,-17,74,-6,-75,-64,113,-69,-118,-128,-37,-114,39,-48,47,-26,-13,-40,-59,-74,-121,120,119,95,-5,27,-78,101,-10,-60,-108,-43,35,111,75,-23,100,83,68,-79,41,92,-123,2,-40,110,-98,-58,-10,-30,109,-119,-120,66,-118,105,75,35,-50,-90,88,31,73,38,62,-86,108,99,7,-27,-16,-18,23,-113,31,71,15,31,77,-58,5,-28,-14,99,-106,-34,72,-80,-42,99,-100,-45,-65,90,121,118,13,-68,-20,-126,9,-74,-125,-34,92,-4,11,-126,70,-109,-85,31,-70,55,50,-101,71,27,-110,-49,51,-55,55,-69,82,69,-48,-2,17,-46,-92,19,125,56,75,-114,109,-128,81,-106,95,-119,125,-101,55,-90,109,127,23,8,102,-59,-28,108,-13,-3,8,-4,-127,97,-78,58,-71,-49,26,-124,-95,66,-78,-65,-46,50,85,-90,126,102,83,-58,124,19,30,17,-60,77,122,81,-110,112,-110,-26,118,-19,23,68,-119,61,-108,50,55,2,119,-113,10,12,-10,126,-33,-69,23,-110,66,96,-109,-75,-4,41,50,23,-14,122,18,19,47,-18,123,-31,-4,-76,63,-126,-78,-117,-115,25,-107,43,-43,-84,-67,-52,7,-2,30,118,-95,84,111,2,-12,-107,94,-25,-116,16,-115,67,-112,-60,85,124,96,105,75,-22,2,-102,-52,-120,-50,-127,-121,-120,-60,39,-116,104,-83,-9,-111,-69,23,68,78,102,-7,-31,-54,-46,48,14,83,-61,-127,44,-91,20,-20,95,-47,-107,-115,94,98,-85,-77,-50,-46,-34,68,88,113,20,14,-119,-2,64,86,-124,-63,79,48,6,-8,89,53,58,50,-46,-14,-77,-58,-38,30,51,31,118,115,112,-37,-99,-90,-14,0,111,-111,12,-4,-46,33,-86,127,-1,-2,3,-67,-34,-120,-102,-31,2,0,0,  
	        //31,-117,8,0,0,0,0,0,0,0,5,-63,-57,-94,107,64,0,0,-48,-67,95,-55,66,-25,90,-68,-123,24,-67,-121,24,-55,78,-17,100,116,-66,-2,-99,19,5,58,-23,29,22,-9,4,108,-102,27,-69,48,114,-3,109,-95,62,94,68,90,123,71,-44,-54,-119,90,8,80,-105,90,-7,25,21,77,69,34,37,-82,-60,-3,-121,-81,93,57,79,123,95,58,-9,115,57,-95,-20,-123,-114,-105,98,99,-74,-84,82,-120,83,-18,-98,24,-60,45,-88,49,-52,-13,67,96,66,-16,104,-60,115,-24,-107,-110,16,-76,31,67,23,105,-82,30,121,74,-45,118,-9,26,79,-18,-122,44,-13,49,106,62,48,50,-101,103,14,34,-113,68,-114,89,-79,-11,41,103,-120,45,64,125,22,1,77,-67,42,50,98,5,-80,-97,126,53,32,-75,-47,76,79,95,36,72,-18,-46,55,-76,-13,91,56,39,-61,116,82,-21,88,9,-67,-90,102,-43,-23,-85,-30,-78,24,115,-88,-94,-26,-94,29,23,-13,-66,-121,12,-38,122,-115,-39,22,45,84,34,126,7,18,-40,-22,-80,-45,-100,-69,55,51,55,110,-7,42,121,2,-17,74,-6,-75,-64,113,-69,-118,-128,-37,-114,39,-48,47,-26,-13,-40,-59,-74,-121,120,119,95,-5,27,-78,101,-10,-60,-108,-43,35,111,75,-23,100,83,68,-79,41,92,-123,2,-40,110,-98,-58,-10,-30,109,-119,-120,66,-118,105,75,35,-50,-90,88,31,73,38,62,-86,108,99,7,-27,-16,-18,23,-113,31,71,15,31,77,-58,5,-28,-14,99,-106,-34,72,-80,-42,99,-100,-45,-65,90,121,118,13,-68,-20,-126,9,-74,-125,-34,92,-4,11,-126,70,-109,-85,31,-70,55,50,-101,71,27,-110,-49,51,-55,55,-69,82,69,-48,-2,17,-46,-92,19,125,56,75,-114,109,-128,81,-106,95,-119,125,-101,55,-90,109,127,23,8,102,-59,-28,108,-13,-3,8,-4,-127,97,-78,58,-71,-49,26,-124,-95,66,-78,-65,-46,50,85,-90,126,102,83,-58,124,19,30,17,-60,77,122,81,-110,112,-110,-26,118,-19,23,68,-119,61,-108,50,55,2,119,-113,10,12,-10,126,-33,-69,23,-110,66,96,-109,-75,-4,41,50,23,-14,122,18,19,47,-18,123,-31,-4,-76,63,-126,-78,-117,-115,25,-107,43,-43,-84,-67,-52,7,-2,30,118,-95,84,111,2,-12,-107,94,-25,-116,16,-115,67,-112,-60,85,124,96,105,75,-22,2,-102,-52,-120,-50,-127,-121,-120,-60,39,-116,104,-83,-9,-111,-69,23,68,78,102,-7,-31,-54,-46,48,14,83,-61,-127,44,-91,20,-20,95,-47,-107,-115,94,98,-85,-77,-50,-46,-34,68,88,113,20,14,-119,-2,64,86,-124,-63,79,48,6,-8,89,53,58,50,-46,-14,-77,-58,-38,30,51,31,118,115,112,-37,-99,-90,-14,0,111,-111,12,-4,-46,33,-86,127,-1,-2,3,-67,-34,-120,-102,-31,2,0,0,

	    	String strUrl = "http://da.mmarket.com/mmsdk/mmsdk?func=mmsdk:postsyslog&appkey="+appKey+"&channel="+channel+"&code=105";
            //"http://da.mmarket.com/mmsdk/mmsdk?func=mmsdk:postsyslog&appkey=300008702230&channel=0000000000&code=105";
	    	
	        JSONObject reqjson = new JSONObject();
			reqjson.put("screenHeight",screenHeight);
			reqjson.put("packageName",packageName);
			reqjson.put("sendTime",sys_sendTime);
			reqjson.put("countryCode",countryCode);
			reqjson.put("versionCode",versionCode);//
			reqjson.put("pid",1);
			reqjson.put("mac",mac);
			reqjson.put("versionName",versionName);
			reqjson.put("phoneOS",phoneOs);
			reqjson.put("cid",cid);
			reqjson.put("screenDensity",screenDensity);
			reqjson.put("sdkVersion",sdkVersion);
			reqjson.put("languageCode",languageCode);
			reqjson.put("protocolVersion",protocolVersion);
			reqjson.put("screenWidth",screenWidth);
			reqjson.put("manufacturer",manufacturer);
			reqjson.put("carrierName",carrierName);
			reqjson.put("accessPoint",accessPoint);
			reqjson.put("imsi",imsi);
			reqjson.put("deviceDetail",deviceDetail);
			reqjson.put("channel",channel);
			reqjson.put("deviceId",deviceId);
			reqjson.put("appKey",appKey);
		    //{"screenHeight":1920,"packageName":"com.pansen.zumalocal","sendTime":1422511917406,"countryCode":"CN","versionCode":1,"pid":1,"mac":"d4:97:0b:69:c7:07","versionName":"1.0","phoneOS":"android+4.4.4","cid":"7f9c9fae657f9ebe4a","screenDensity":480,"sdkVersion":"3.1.1","languageCode":"zh","protocolVersion":"3.1.0","screenWidth":1080,"manufacturer":"Xiaomi","carrierName":"China+Mobile","accessPoint":"wifi","imsi":"460029140953268","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}
			//{"screenHeight":1920,"packageName":"com.pansen.zumalocal","sendTime":1422511917406,"countryCode":"CN","versionCode":1,"pid":1,"mac":"d4:97:0b:69:c7:07","versionName":"1.0","phoneOs":"android+4.4.4","cid":"7f9c9fae657f9ebe4a","screenDensity":480,"sdkVersion":"3.1.1","languageCode":"zh","protocolVersion":"3.1.0","screenWidth":1080,"manufacturer":"Xiaomi","carrierName":"China+Mobile","accessPoint":"wifi","imsi":"460029140953268","deviceDetail":"MI+3C","channel":"0000000000","deviceId":"865903029745048","appKey":"300008702230"}

			System.out.println("strUrl="+strUrl);
			
			str = reqjson.toString();
			System.out.println("str="+str);
			int index = 0;
			byte last=0;
			byte next=0;
			byte cur=0;
			/*for(;;)
			{
				 byte [] bts = str.getBytes();
				 if(index == 0)
				 {
					 last = next = cur = bts[index];
				 }
				 else 
				 {
					 last = cur;
					 
				 }
				index ++;
				cur = bts[index];
				next = bts[index+1];
				if(last==79 && cur==115 && next==34 )
				{
					System.out.println("index="+index);
					break;
				}
			}*/
			//System.out.println(byteToStr (str.getBytes()));//////
			d td= d.a();
			byte [] bytes =td.a(str);
			//System.out.println(byteToStr (bytes));
			
			try {
				String rsq =HttpUtils.URLPostUTF8(strUrl, bytes);
				System.out.println("rsq"+rsq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    public static String  byteToStr2(byte[] bts)
	    {
	    	String str = "";
	    	for(byte bt:bts)
	    	{
	    		String t = String.format("%d,", bt);
	    		str += t;
	    	}
	    	return str;
	    }
	    
	    public static String replace(String paramString)
	    {
	      if (paramString == null) {
	        return "";
	      }
	      if (paramString.length() > 30) {
	        paramString = paramString.substring(0, 29);
	      }
	      return paramString.replace("\\", "").replace("|", "");
	    }
	    
	    
}
