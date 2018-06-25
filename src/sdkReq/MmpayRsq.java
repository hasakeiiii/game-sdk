package sdkReq;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.App;
import model.Cooperation;
import model.MmPayData;
import model.Pay;
import model.ZhangZhong;
import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.yeepay.HttpUtils;

import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.SaConfiguration;
import util.StringUtil;
import dao.ActivateDao;
import dao.AppDao;
import dao.CooperationDao;
import dao.DevicePayDao;
import dao.FeeDataDao;
import dao.MmPayDao;
import dao.MmPayDataDao;
import dao.PayDao;
import dao.ProvinceDataDao;
import dao.ZhangZhongDao;

public class MmpayRsq extends HttpServlet {

	static Integer  connNum = 0;
	static String respXmlformat="<?xml version=\"1.0\" encoding=\"UTF-8\"?><SyncAppOrderResp xmlns=\"http://www.monternet.com/dsmp/schemas/\"><TransactionID>%s</TransactionID><MsgType>SyncAppOrderResp</MsgType><Version>1.0.0</Version><hRet>0</hRet></SyncAppOrderResp>";

	static String RedirectURL = "";//"http://211.154.152.59:8080/sdk/getcon";
	static String RedirectPCKT = "";
	public static String NOURL="nourl";
	
	//public model.Activate m_activate;
	
	public static void incrConnNum()
	{
	    synchronized(connNum)
	    {
	    	connNum ++;
	    }
	}
	
	public static void dscConnNum()
	{
	    synchronized(connNum)
	    {
	    	connNum --;
	    }
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//if(ConstValue.bNew ==1)
		//{
	    initData();
		//}
	}
	
	public static void initData()
	{
		SaConfiguration configuration =SaConfiguration.getInstance("msmpay");
		String str = configuration.getValue("RedirectPCKT");
		DebuUtil.log("RedirectPCKT="+str);
		if(!StringUtil.is_nullString(str))
		{
			RedirectPCKT = str;
		}
		str = configuration.getValue("RedirectURL");
		DebuUtil.log("RedirectURL="+str);
		if(!StringUtil.is_nullString(str))
		{
			RedirectURL = str;
		}
		
	}
	
	public static String getNodeValue(Node node){
		try{
			return node.getFirstChild().getNodeValue();
		}catch(Exception e){}
		return null;
	}
	
	public Map<String,String> GetChannelDataFromSrv(String url,String zzdata)
	{
        Map<String,String> map = new HashMap<String,String>();    
    	String msmrsq="";
    	 Map<String,String> resmap = new HashMap<String,String>();    
    	 
        map.put("zzdata", zzdata);    
        //map.put("date", date); 
        
		try {
			List list= HttpUtils.URLGet(url, map,1000*20);
			for(int i = 0; i < list.size() ; i++)
			{
				String str = (String) list.get(i);
				msmrsq += str;
			}
			if(!StringUtil.is_nullString(msmrsq))
			{
				resmap = CooperationDao.getMapByReqStr(msmrsq);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resmap;
	}
	
	
	public static model.Activate parseMMRsq(String xmlstr,model.MmPay obj) 
	{
		// step 1: 获得dom解析器工厂（工作的作用是用于创建具体的解析器）
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		model.Activate m_activate = null;
		
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
		int OrderType = 0;
		for(int i=0;i<size;i++)
		{
			Node node =list.item(i);
    		String tagName=node.getNodeName();
    		//System.out.println("tagName:" + tagName);
    		if(tagName.equalsIgnoreCase("AppID"))
    		{
    			String ret=getNodeValue(node);
    			obj.app_id = ret;
    			//System.out.println("AppID:" + ret);
    		}
    		else if(tagName.equalsIgnoreCase("PayCode"))
    		{
    			String ret=getNodeValue(node);
    			obj.app_code = ret;
    			//System.out.println("PayCode:" + ret);
    		}
    		else if(tagName.equalsIgnoreCase("TradeID"))
    		{
    			String ret=getNodeValue(node);
    			obj.trade_no = ret;
    			//System.out.println("TradeID:" + ret);
    		}
    		else if(tagName.equalsIgnoreCase("ChannelID"))
    		{
    			String ret=getNodeValue(node);
    			obj.channel = ret;
    			//System.out.println("ChannelID:" + ret);
    		}
    		else if(tagName.equalsIgnoreCase("TotalPrice"))
    		{
    			String ret=getNodeValue(node);
    			obj.amount = Integer.valueOf(ret);
    			//System.out.println("TotalPrice:" + ret);
    		}
    		else if(tagName.equalsIgnoreCase("OrderPayment"))
    		{
    			
    			String ret=getNodeValue(node);
    			paytype = Integer.valueOf(ret);
    			//DebuUtil.log3("OrderPayment:"+ret);
    			//System.out.println("TotalPrice:" + ret);
    		}
    		//
    		else if(tagName.equalsIgnoreCase("OrderID"))
    		{
    			String OrderID=getNodeValue(node);
    			obj.setOrderNo(OrderID);
    			if(OrderID.endsWith("00000000000000000000"))
    			{
    				bFail = true;
    			}    			
    			//System.out.println("TotalPrice:" + ret);
    		}    		
    		else if(tagName.equalsIgnoreCase("ExData"))
    		{
    			String ret=getNodeValue(node);
    			m_activate = handleExData(ret,obj);
    			
    		}
    		else if(tagName.equalsIgnoreCase("MSISDN"))
    		{
    			String ret=getNodeValue(node);
    			obj.MSISDN = ret;
    		}
    		else if(tagName.equalsIgnoreCase("TransactionID"))
    		{
    			String ret=getNodeValue(node);
    			obj.TransactionID = ret;
    		}//OrderType
    		else if(tagName.equalsIgnoreCase("ActionID"))
    		{
    			String ret=getNodeValue(node);
    			int ActionID = Integer.valueOf(ret);
    			
    			if((ActionID == 2) || ((ActionID == 3)))//if(ActionID != 1)
    			{
    				bFail = true;
    			}
    		}
    		else if(tagName.equalsIgnoreCase("OrderType"))
    		{
    			String ret=getNodeValue(node);
    			 OrderType = Integer.valueOf(ret);
    			
    			DebuUtil.log("OrderType="+OrderType);
    			if(OrderType == 0)//if(ActionID != 1)
    			{
    				bFail = true;
    			}
    		}
    		
    		
    		//ReturnCode
    		//
		}
		
		
		if(bFail && (!obj.app_id.equals("300008918716")))
		{
			obj.setRet(-1);
		}
		else
		{
			//obj.app_id,300008918716
			obj.setRet(paytype);
		}
		/*if(obj.app_id.equals("300008918716"))
		{
			DebuUtil.log2("obj.app_id="+obj.app_id+"obj.ret="+obj.getRet());
			obj.setRet(1);
		}*/
		
		//if(ConstValue.PAY_SERVER == 1)
		//{
			
			if((obj.bSDK == false) && (obj.bZhangZhong == false) && (obj.amount >= 100))
			{
				ZhangZhongDao zhangZhongDao = new ZhangZhongDao();
			    ZhangZhong zhangZhong =zhangZhongDao.getRecord(obj.ExData);
			    if(zhangZhong != null)
			    {	    		    			
	    			obj.imei = zhangZhong.getImei();
	    			//DebuUtil.log2("修改透传参数obj.ExData"+obj.ExData);
			    }
			}
		//}
		
	   return m_activate;
			
	}

	public static String getPayUrl(Cooperation cooperation)
	{
		String strUrl = "";   
		if(cooperation != null)
		{				
			strUrl = cooperation.getPayUrlb();	
			if(StringUtil.is_nullString(strUrl))
			{
				strUrl = "";
			}
	    }
		return strUrl;
	}

	
	 public static int genRan()
	 {
	        java.util.Random r = new java.util.Random();		
			int ran = r.nextInt();
			if(ran < 0)
			{
				ran = 0- ran;
			}
			ran = ran%100;
			return ran;
    }
	 public static boolean isZhangzhong(String appid)
	 {
		 boolean ret = false;
		 
		 MmPayDataDao mmPayDataDao = new MmPayDataDao();
		 MmPayData  mmPayData = mmPayDataDao.getRecordByLike(appid);
		 if(mmPayData != null)
		 {
			 if(mmPayData.getConpanyName().equals(GetAmountIndex.zhangzhong))
			 {
				 ret = true;
			 }
			 
		 }
		 return ret;
	 }
	 
	 public static Cooperation getPacket(model.MmPay obj)
	 {
		Cooperation cooperation = null;
		String msmrsq = "";	
		if(StringUtil.is_nullString(obj.packet_id))
		{
			if(!StringUtil.is_nullString(obj.ExData))
			{
				if(obj.ExData.contains("_"))
				{
					 String splitstr = obj.ExData;
					 String[] strarray=splitstr.split("_");     		
					 obj.packet_id = strarray[0];
				}
				else
				{
					obj.packet_id = obj.ExData;
				}
			}
		}
		
		CooperationDao cooperationDao=new CooperationDao();
		cooperation = cooperationDao.getRecord(obj.packet_id);    
		
		if(cooperation == null)
		{
			obj.packet_id = obj.packet_id = obj.app_id.substring(7, 7+5)+"_"+obj.channel.substring(6, 6+4);;
			cooperation = cooperationDao.getRecord(obj.packet_id);  
		}
		
		
		if(cooperation == null)
		{
			obj.packet_id = obj.channel;
			cooperation = cooperationDao.getRecord(obj.packet_id);  
		}
		
		return cooperation;
	 }
	 
	public static String notisty(Cooperation cooperation,model.MmPay obj,String Url,String MMRsq)
	{
		String strUrl = "";   
		String msmrsq = "";
		String orderON = "";
		String userdata = "";
		//String packet_id = "";
		
		if(!StringUtil.is_nullString(obj.packet_id))
		{			
			
			strUrl = getPayUrl(cooperation);
			
		}
	
		///////////////////////////////////
		userdata = obj.packet_id;/////////////////////////
        ///////////////////////////////////////////
		
	
	if(!StringUtil.is_nullString(Url))
	{
		strUrl = Url;
	}
	
	int ration = 100-cooperation.getSettleRatio();
	int ran = genRan();
	if(ran < ration)
	{
		strUrl = "";
		obj.type="C";
	}
	else
	{
		obj.type="A";
	}
	
	 if(strUrl.length() > 0)
     {
		 //else
		 {
	    	Map<String,String> map=new HashMap<String,String>();    
	    	
	        map.put("app_id", obj.app_id);    
	        map.put("app_code", obj.app_code); 
	        map.put("order_no", obj.order_no); 
	        map.put("trade_no", obj.trade_no);  
	        map.put("amount", obj.amount+""); 
	        map.put("num", obj.MSISDN);
	        map.put("channel", obj.channel);
	        map.put("state", obj.ret+""); //http://XXXX/MMServlet?app_id=应用编号&app_code=计费代码&trade_no=交易号amount=金额（分）&state=1(1等于成功)
	        //if(obj.app_id.equals("300008481286"))
	        {
	        	map.put("userdata", userdata);
	        }
	        DebuUtil.log("转发同步信息:"+strUrl);//;NX0VhTkDVHk=;30000867335608;8270636EC88913989C2A8E903ABA3061;;0
	        
			try {
				List list= HttpUtils.URLGet(strUrl, map,8000);
				for(int i = 0; i < list.size() ; i++)
				{
					String str = (String) list.get(i);
					msmrsq += str;
				}
				if(!StringUtil.is_nullString(msmrsq))
				{
					obj.setNotisfy(1);
				}
				DebuUtil.log("同步结果:"+msmrsq);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
		
      }
	 else
	 {
		 msmrsq = NOURL;
		 obj.setNotisfy(1);
	 }
       
        
	  return msmrsq;
		
	}
	
	public static void handleSA(model.MmPay obj)
	{
		PayDao paydao = new PayDao();
		Pay sapay = new Pay();
    	
    	//////////////////////////////////////////////////////
    	// if(ConstValue.PAY_SERVER == 1)
    	// {
		 DebuUtil.log("paySA  begin");
	     MmPayDao.paySA(obj);
	     DebuUtil.log("paySA  end");
    	// }
    	//////////////////////////////////////////////////////
    	sapay.amount= obj.getAmount();
    	sapay.date = DateUtil.getDate();
    	sapay.time = DateUtil.getTime();
    	sapay.pay_no = Pay.getOutTradeNoINC();
    	sapay.thir_pay_id = obj.getTradeNo();
    	
    	sapay.device_id="";	    	    	
    	sapay.game_id="";
    	sapay.server_id="";
    	sapay.cp_order_id="";
    	sapay.username="";	
    	sapay.packet_id = "";	    	    		
    	sapay.ratio=0;
    	sapay.coin_name="";
    	sapay.pay_type = "mmpay";	    	    	
    	sapay.type = obj.type;
    	if(obj.getRet() >= 0)
    	{
    		sapay.state = 1;
    	}
    	else
    	{
    		sapay.state = 0;
    	}
    	sapay.business_no = "";
    	sapay.channel_no = "";
    	sapay.ncount = 0;
    	if(!StringUtil.is_nullString(obj.packet_id))
    	{
    		sapay.packet_id=obj.packet_id;
    		CooperationDao cooperationDao=new CooperationDao();
    		Cooperation cooperation = cooperationDao.getRecord(sapay.packet_id);
    		if(cooperation != null)
    		{
    			sapay.game_id = cooperation.getAppNo();
    			sapay.business_no = cooperation.getBusinessNo();
    			sapay.channel_no = cooperation.getChannelNo();
    			//DebuUtil.log3("sapay.game_id: "+sapay.game_id);
    			AppDao appdao = new AppDao();
    			App appinfo = appdao.getAppRecord(sapay.game_id);
    			if(StringUtil.is_nullString(appinfo.getUrl()))
    			{
    				sapay.ncount = 10;
    			}
    		}
    	}
    	//DebuUtil.log3("sapay.packet_id: "+sapay.packet_id);
    	//DebuUtil.log3("sapay.state: "+sapay.state);
    	paydao.pay(sapay,obj.bSDK);
    	if(ConstValue.OPTIMIZE == 1)
		{
    		 PayDao.pay(sapay,paydao,obj.bSDK);
		}
	}
	
	public static boolean isGMServerGame(String channel)
	{
		boolean ret = false;
		
		if (channel.equals("3003984822")
				|| channel.equals("3003984824")
				|| channel.equals("3003984832")
				|| channel.equals("3003984833")
				|| channel.equals("3003984835")
				|| channel.equals("3003984837")
				|| channel.equals("3003984839")
				|| channel.equals("3003984841")
				|| channel.equals("3003984827")
				|| channel.equals("3003984828")
				|| channel.equals("3003984846")
				|| channel.equals("3003984847")
				|| channel.equals("3003984848")
				|| channel.equals("3003984849")
				|| channel.equals("3003984850")
				|| channel.equals("3003984879")
				|| channel.equals("3003984886")
				|| channel.equals("3003984887")
				|| channel.equals("3003984888")
				|| channel.equals("3003984889")
				|| channel.equals("3003984877")) 
		{
			ret = true;
		}
        return ret;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        InputStream in = req.getInputStream();
        ByteArrayOutputStream baos = null;
        String xmlStr = null;
  
        
        DebuUtil.log("移动MM通知来了");
        
        /*DebuUtil.log("当前请求数="+connNum);
		//请求太多不处理
		if(connNum >= 10)
		{
			DebuUtil.log("请求太多不处理");
			return ;
		}*/
		
        try{
         
           /* baos = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int read = 0;
            while((read = in.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            
            xmlStr = new String(baos.toByteArray(),"utf-8");*/
            
            byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
            DebuUtil.log("移动MM通知来了1");
    		 xmlStr = new String(buf,"UTF-8");
    		 //DebuUtil.log2("移动MM通知来了2"+xmlStr);
    
            ///in.close();///////////////////////////
           
           // baos.close();//////////////////////////
            if(ConstValue.RDR == 1)
    		{
            	//xmlStr = URLDecoder.decode(xmlStr,"UTF-8");	
    		}
            
            //DebuUtil.log3("read xmlStr: "+xmlStr);
            //System.out.println("read xmlStr: "+xmlStr);
           
           
            //String respXmlformat="<?xml version=\"1.0\" encoding=\"UTF-8\"?><SyncAppOrderResp xmlns=\"http://www.monternet.com/dsmp/schemas/\"><TransactionID>%s</TransactionID><MsgType>SyncAppOrderResp</MsgType><Version>1.0.0</Version><hRet>0</hRet></SyncAppOrderResp>";
             model.MmPay obj = new model.MmPay();
             model.Activate m_activate = parseMMRsq(xmlStr,obj);
             if(ConstValue.DEBUG == 1)
             DebuUtil.log2("MM回应 ="+xmlStr);
             String respXml = String.format(respXmlformat, obj.TransactionID);//
 	    	 DebuUtil.log("回应MM="+respXml);
             writeDataResponse(respXml,resp);//respXml
             handle(m_activate, xmlStr, obj);
             
        }catch(Exception e){
            System.out.println(e);
        }finally { if (baos != null)  baos.close();
        if (in != null) in.close();}
             
     }
	
	public static model.Pay makePay(model.Activate m_activate, model.MmPay obj)
	{
		model.Pay pay = null;
		if(m_activate != null)
		{
			pay = new Pay();
			pay.device_id= m_activate.device_id;	    	    	
	    	pay.game_id=m_activate.game_id;
	    	pay.server_id="";
	    	pay.cp_order_id="";
	    	pay.username=m_activate.device_id;	
	    	pay.packet_id = m_activate.packet_id;	    	    		
	    	pay.ratio=0;
	    	pay.coin_name="";
	    	pay.pay_type = Pay.MmPayType;	    	    	
	    	pay.type = "";
	    	pay.amount = obj.amount;
	    	pay.state = 1;
	    	pay.business_no = m_activate.business_no;
	    	pay.channel_no = m_activate.channel_no;
	    	pay.thir_pay_id = obj.getTradeNo();
	    	pay.pay_no = obj.getTradeNo();
	    	pay.date = DateUtil.getDate();
	    	pay.time = DateUtil.getTime();
	    	pay.ext = obj.province;
	    	if(ConstValue.DEBUG == 1)
	    	{
	    		DebuUtil.log("生成pay信息");
	    	}
		}
		else
		{
			if((obj.bSDK))//&& !StringUtil.is_nullString(obj.imei)
			{
				CooperationDao cooperationDao=new CooperationDao();
	    		Cooperation cooperation = cooperationDao.getRecord(obj.packet_id);
	    		if(cooperation != null)
	    		{
	    			pay = new Pay();
	    			pay.device_id= obj.imei;	    	    	
	    	    	pay.game_id=cooperation.app_no;
	    	    	pay.server_id="";
	    	    	pay.cp_order_id="";
	    	    	pay.username=obj.imei;	
	    	    	pay.packet_id = cooperation.packet_id;	    	    		
	    	    	pay.ratio=0;
	    	    	pay.coin_name="";
	    	    	pay.pay_type = Pay.MmPayType;	    	    	
	    	    	pay.type = "";
	    	    	pay.amount = obj.amount;
	    	    	pay.state = 1;
	    	    	pay.business_no = cooperation.business_no;
	    	    	pay.channel_no = cooperation.channel_no;
	    	    	pay.thir_pay_id = obj.getTradeNo();
	    	    	pay.pay_no = obj.getTradeNo();
	    	    	pay.date = DateUtil.getDate();
	    	    	pay.time = DateUtil.getTime();
	    	    	if(ConstValue.DEBUG == 1)
	    	    	{
	    	    		DebuUtil.log("生成pay信息");
	    	    	}
	    		}
			}
		}
		return pay;
	}
	
	public static void handle(model.Activate m_activate,String mmrsq , model.MmPay obj)
	{
			
		    int bRSQ = 0;
            int bpaySA = 0;
      
            obj.date = DateUtil.getDate();
            obj.time = DateUtil.getTime();
          
            
            if(ConstValue.PAY_SERVER == 1)//if(!StringUtil.is_nullString(obj.ExData))////
    	    {
            	bpaySA = 1;
    	    }
            
            /////////////////////////////////////////////////////////////
            //if(bpaySA == 1)
            {
            	
            	
            	 /*if(obj.bZhangZhong)//if(isZhangzhong(obj.app_id))
        		 {
        			 try {
        				 String zzurl = "http://121.14.38.20:25498/iap/SyncAppOrderReq";
        				 DebuUtil.log("同步给掌中");
        				 //obj.packet_id = obj.channel;
        				 HttpUtils.URLPostUTF8(zzurl, mmrsq);
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		 }*/
        
				@SuppressWarnings("unused")
				boolean zgm = false;
				if (isGMServerGame(obj.channel)) 
				{
					zgm = true;
				}
			
            	 /*if(ConstValue.PAY_SERVER == 1)
            	 {
            		  if (zgm == true)
            		  {
             				try
             				{
             					String zzurl = "http://gm.91muzhi.com:8080/sdk/MmpayRsq";
             					DebuUtil.log("同步给网游服");
             					HttpUtils.URLPostUTF8(zzurl, mmrsq);
             				}
             				catch (IOException e)
             				{
             					e.printStackTrace();
             				}
            		  }
            	 }*/
          	 
            }
	    	/////////////////////////////////////////////////////
	    	
            MmPayDao dao = new MmPayDao();
    	    int ret = dao.isExit(obj);//dao.mmPay(obj);
    	    
    	   // String respXml = String.format(respXmlformat, obj.getOrderNo());
           // writeDataResponse(respXml,resp);//respXml
            
    	    if(ret != ConstValue.Fail)
    	    {
    	    	///notisty(obj);///////////////////
    	    	
	    	    PayDao paydao = new PayDao();
	    	    Pay pay = null;
	    	    
	    	    if((obj.bSDK))///if((obj.bSDK) && (obj.ret >= 0))//计费成功再找pay
	    	    {
	    	    	pay = paydao.getPayRecord(obj.getTradeNo(),"mmpay");
	    	    	if(ConstValue.DEBUG == 1)
	    	    	{
	    	    	     DebuUtil.log("接SDK，通过交易号找pay信息="+obj.getTradeNo());
	    	    	     if(pay != null)
	    	    	     {
	    	    	    	 DebuUtil.log("找到pay信息");
	    	    	     }
	    	    	}
	    	    	if(pay == null)//根据订单号找
	    	    	{
	    	    		if(!StringUtil.is_nullString(obj.cp_order_no))
	    	    		{
	    	    			if(ConstValue.DEBUG == 1)
	    	    			{
	    	    				DebuUtil.log("通过CP订单号找pay信息");
	    	    			}
	    	    			pay = paydao.getPayRecordByCpOrder(obj.cp_order_no);
	    	    		}
	    	    	}
	    	    	
	    	    	if(pay == null)//根据IMEI找
    	    		{
    	    			if(!StringUtil.is_nullString(obj.imei))
    	    			{
    	    				
    	    				ActivateDao activateDao = new ActivateDao();
    	    				m_activate = activateDao.getRecord(obj.imei);
    	    				if(m_activate != null)
    	    				{
    	    					obj.packet_id = m_activate.packet_id;
    	    					String code = GetAmountIndex.getRegionCode(m_activate.addr, "");
    	    					obj.province = GetAmountIndex.getRegionCode("", code);
    	    					
    	    					if(ConstValue.DEBUG == 1)
    	    					{
    	    						 DebuUtil.log("通过激活表找用户省份="+obj.province);
    	    					}
    	    				}
    	    				else
    	    				{
    	    					obj.packet_id = obj.app_id.substring(7, 7+5)+"_"+obj.channel.substring(6, 6+4);//obj.app_code.substring(arg0, arg1);
    	    				}
    	    				if(ConstValue.DEBUG == 1)
    	    				{
    	    					DebuUtil.log("接SDK,IMEI="+obj.ExData+" packet_id="+obj.packet_id);
    	    				}
    	    			}
    	    			pay = makePay(m_activate,obj);
    	    		}
    	    		else
    	    		{
    	    			 obj.province = pay.ext;
    	    			 obj.packet_id = pay.packet_id;
    	    			 if(StringUtil.is_nullString(obj.province))
    	    			 {
    	    				 obj.province = "";
    	    			 }
    	    			 if(ConstValue.DEBUG == 1)
    					 {
    						 DebuUtil.log("通过支付表找用户省份="+obj.province);
    					 }
    	    		}
	    	    	if(pay != null)
	    	    	{
	    	    		obj.packet_id = pay.packet_id;
	    	    	}
	    	    }
	    	    
    	    	//通知渠道
       		    Cooperation cooperation = getPacket(obj);
       		    if(cooperation != null)
       		    {
		            String rsq = notisty(cooperation,obj,"",mmrsq);///////////////////
			    	if(!StringUtil.is_nullString(rsq))
			    	{
			    		obj.setNotisfy(1);
			    	}
       		    }
		    	dao.mmPay(obj); 
    	    	
		    	if((cooperation != null) && (cooperation.getPayMark().equals(Cooperation.ZZNET_FEE)))//同步给掌中
	       		{
		    		
	       			 try {
	       				 String zzurl = "http://121.14.38.20:25498/iap/SyncAppOrderReq";
	       				 DebuUtil.log("同步给掌中");
	       				 //obj.packet_id = obj.channel;
	       				 HttpUtils.URLPostUTF8(zzurl, mmrsq);
	       			} catch (IOException e) {
	       				// TODO Auto-generated catch block
	       				e.printStackTrace();
	       			}
	       		 }
		    	if(obj.app_key.startsWith("A2015")){
		    		
		    		
		    		DebuUtil.log("刷卡回调掌中="+obj.app_key);
		    		 try {
	       				 String zzurl = "http://121.14.38.20:25498/iap/SyncAppOrderReq";
	       				 DebuUtil.log("同步给掌中");
	       				 //obj.packet_id = obj.channel;
	       				 HttpUtils.URLPostUTF8(zzurl, mmrsq);
	       			} catch (IOException e) {
	       				// TODO Auto-generated catch block
	       				e.printStackTrace();
	       			}
		    	}
		    	
	    	    /*else
	    	    {
		    	    if(bpaySA == 0)
		    	    {
		    	    	pay = paydao.getPayRecord(obj.getTradeNo(),"mmpay");
		    	    }	    	   
	    	    }*/
	    	    /*if(obj.amount >= 100)
    			{
    	    		String datestr = DateUtil.getDate();
    				DevicePayDao devicePayDao = new DevicePayDao();
    				
    				DebuUtil.log("IMEI:"+obj.imei+"付费:"+obj.amount);
    				devicePayDao.addPay(obj.imei,obj.amount,datestr);
    			}*/
	    	    if(obj.amount >= 100)
    			{
    	    		String datestr = DateUtil.getDate();
    				DevicePayDao devicePayDao = new DevicePayDao();
    				if(pay != null)
    				{
    					obj.imei = pay.device_id;
    				}
    				if(!StringUtil.is_nullString(obj.imei))
    				{
	    				DebuUtil.log("IMEI:"+obj.imei+"付费:"+obj.amount);
	    				devicePayDao.addPay(obj.imei,obj.amount,datestr);
    				}
    			}
	    	    //if(ConstValue.bNew == 1)	    	    
	    	    {
	    	    	if(obj.bSDK == false)
    	    		{
	    	    		
		    			handleSA(obj);
    	    		}
	    	    	else
	    	    	{
	    	    		
	    	    		
	    	    		if((pay != null))//&& (pay.state != 1)
	 		    	    {
	    	    			
	    	    			
	 	    	    	   if(pay.amount != obj.amount)
	 					   {
	 						   pay.amount = obj.amount;
	 					   }
	 	    	    	  
	 	    	    	   if(obj.ret >= 0)
	 	    	    	   {
	 	    	    		   pay.state = 1;	 	    	    		
	 	    	    	   }
	 	    	    		if(pay.getId() == null)
	 	    	    		{
	 	    	    			
	 	    	    			paydao.add(pay);
	 	    	    		}
	 	    	    		else
	 	    	    		{
	 	    	    		   paydao.edit(pay);
	 	    	    		}
	 	    	    		
	 	    	    		 //正向按省份统计
 	    	    			/*ProvinceDataDao provinceDataDao = new ProvinceDataDao();
 	    	   			    provinceDataDao.pay(obj,true);
	 	    	    		if(ConstValue.bNew == 1)
	 	    	 	        {
	 	    	 		        //正向计费点统计
	 	    	    			FeeDataDao feeDataDao = new FeeDataDao();
	 	    	   			    feeDataDao.pay(pay,true);
	 	    	 	        }*/
	 	    	    		  
	 		    	    	paydao.payNotisfy(pay,obj.bSDK);
	 		    	    }
	    	    	}
    	    	    
	    	    }
	    	    
    	    }
       
	}
	/*public static void handleTest(model.Activate m_activate,String mmrsq , model.MmPay obj)
	{
	      obj.date = DateUtil.getDate();
          obj.time = DateUtil.getTime();
          

	    	///notisty(obj);///////////////////
          MmPayDao dao = new MmPayDao();
          
  	    PayDao paydao = new PayDao();
  	    Pay pay = null;
  	    
  	    if((obj.bSDK))///if((obj.bSDK) && (obj.ret >= 0))//计费成功再找pay
  	    {
  	    	pay = paydao.getPayRecord(obj.getTradeNo(),"mmpay");
  	    	if(ConstValue.DEBUG == 1)
  	    	{
  	    	     DebuUtil.log("接SDK，通过交易号找pay信息="+obj.getTradeNo());
  	    	     if(pay != null)
  	    	     {
  	    	    	 DebuUtil.log("找到pay信息");
  	    	     }
  	    	}
  	    	if(pay == null)//根据订单号找
  	    	{
  	    		if(!StringUtil.is_nullString(obj.cp_order_no))
  	    		{
  	    			if(ConstValue.DEBUG == 1)
  	    			{
  	    				DebuUtil.log("通过CP订单号找pay信息");
  	    			}
  	    			pay = paydao.getPayRecordByCpOrder(obj.cp_order_no);
  	    		}
  	    	}
  	    	
  	    	if(pay == null)//根据IMEI找
	    		{
	    			if(!StringUtil.is_nullString(obj.imei))
	    			{
	    				
	    				ActivateDao activateDao = new ActivateDao();
	    				m_activate = activateDao.getRecord(obj.imei);
	    				if(m_activate != null)
	    				{
	    					obj.packet_id = m_activate.packet_id;
	    					String code = GetAmountIndex.getRegionCode(m_activate.addr, "");
	    					obj.province = GetAmountIndex.getRegionCode("", code);
	    					
	    					if(ConstValue.DEBUG == 1)
	    					{
	    						 DebuUtil.log("通过激活表找用户省份="+obj.province);
	    					}
	    				}
	    				else
	    				{
	    					obj.packet_id = obj.app_id.substring(7, 7+5)+"_"+obj.channel.substring(6, 6+4);//obj.app_code.substring(arg0, arg1);
	    				}
	    				if(ConstValue.DEBUG == 1)
	    				{
	    					DebuUtil.log("接SDK,IMEI="+obj.ExData+" packet_id="+obj.packet_id);
	    				}
	    			}
	    			pay = makePay(m_activate,obj);
	    		}
	    		else
	    		{
	    			 obj.province = pay.ext;
	    			 obj.packet_id = pay.packet_id;
	    			 if(StringUtil.is_nullString(obj.province))
	    			 {
	    				 obj.province = "";
	    			 }
	    			 if(ConstValue.DEBUG == 1)
					 {
						 DebuUtil.log("通过支付表找用户省份="+obj.province);
					 }
	    		}
  	    	if(pay != null)
  	    	{
  	    		obj.packet_id = pay.packet_id;
  	    		if(pay.state == 1){
  	    			return;
  	    		}
  	    	}
  	    }
  	    
	    	//通知渠道
 		    Cooperation cooperation = getPacket(obj);
 		    if(cooperation != null)
 		    {
	            String rsq = notisty(cooperation,obj,"",mmrsq);///////////////////
		    	if(!StringUtil.is_nullString(rsq))
		    	{
		    		obj.setNotisfy(1);
		    	}
 		    }
	    	dao.mmPay(obj); 
	    	
	    	if((cooperation != null) && (cooperation.getPayMark().equals(Cooperation.ZZNET_FEE)))//同步给掌中
     		{
	    		
     			 try {
     				 String zzurl = "http://121.14.38.20:25498/iap/SyncAppOrderReq";
     				 DebuUtil.log("同步给掌中");
     				 //obj.packet_id = obj.channel;
     				 HttpUtils.URLPostUTF8(zzurl, mmrsq);
     			} catch (IOException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
     		 }
	    	
  	    else
  	    {
	    	    if(bpaySA == 0)
	    	    {
	    	    	pay = paydao.getPayRecord(obj.getTradeNo(),"mmpay");
	    	    }	    	   
  	    }
  	    if(obj.amount >= 100)
			{
	    		String datestr = DateUtil.getDate();
				DevicePayDao devicePayDao = new DevicePayDao();
				
				DebuUtil.log("IMEI:"+obj.imei+"付费:"+obj.amount);
				devicePayDao.addPay(obj.imei,obj.amount,datestr);
			}
  	    if(obj.amount >= 100)
			{
	    		String datestr = DateUtil.getDate();
				DevicePayDao devicePayDao = new DevicePayDao();
				if(pay != null)
				{
					obj.imei = pay.device_id;
				}
				if(!StringUtil.is_nullString(obj.imei))
				{
  				DebuUtil.log("IMEI:"+obj.imei+"付费:"+obj.amount);
  				devicePayDao.addPay(obj.imei,obj.amount,datestr);
				}
			}
  	    //if(ConstValue.bNew == 1)	    	    
  	    {
  	    	if(obj.bSDK == false)
	    		{
  	    		
	    			handleSA(obj);
	    		}
  	    	else
  	    	{
  	    		
  	    		
  	    		if((pay != null))//&& (pay.state != 1)
		    	    {
  	    			
  	    			
	    	    	   if(pay.amount != obj.amount)
					   {
						   pay.amount = obj.amount;
					   }
	    	    	  
	    	    	   if(obj.ret >= 0)
	    	    	   {
	    	    		   pay.state = 1;	 	    	    		
	    	    	   }
	    	    		if(pay.getId() == null)
	    	    		{
	    	    			
	    	    			paydao.add(pay);
	    	    		}
	    	    		else
	    	    		{
	    	    		   paydao.edit(pay);
	    	    		}
	    	    		
	    	    		 //正向按省份统计
   	    			ProvinceDataDao provinceDataDao = new ProvinceDataDao();
   	   			    provinceDataDao.pay(obj,true);
	    	    		if(ConstValue.bNew == 1)
	    	 	        {
	    	 		        //正向计费点统计
	    	    			FeeDataDao feeDataDao = new FeeDataDao();
	    	   			    feeDataDao.pay(pay,true);
	    	 	        }
	    	    		  
		    	    	paydao.payNotisfy(pay,obj.bSDK);
		    	    }
  	    	}
	    	    
  	    }
  	    
	    
	}*/
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	    doGet(req, resp);
	}
	
	 public void writeDataResponse(String data,HttpServletResponse response) throws IOException {
	       OutputStream os = null;
	       try {
	           byte[] dataByte = data.getBytes("UTF-8");
	           os = response.getOutputStream();
	           response.setContentType("text/xml;charset=UTF-8");
	           os.write(dataByte);
	           os.flush();
	       }
	       catch (IOException e) {//
	           throw e;
	       }
	       finally {
	           if (os != null)  os.close(); 
	       }
	   }

	public static model.Activate handleExData(String exData,model.MmPay obj) 
	{
		String ret = exData;
		model.Activate m_activate = null;
		obj.ExData = ret;
		obj.app_key = ret;
		if(!StringUtil.is_nullString(ret))
		{    				
			//ret="3169025\"548";
			
				if(ret.contains("_"))
				{
							
				}
				else
				{   
					if(ret.length() == 15)//接SDK
					{	    						
						obj.imei = obj.ExData;			
					}
					else if(ret.length() == 14)//接SDK
					{	    						
						obj.cp_order_no = obj.ExData;			
					}
					else if(ret.length() == 13)//掌中乐游破解
					{
						obj.bZhangZhong = true;
						if(ConstValue.DEBUG == 1)
						{
							 DebuUtil.log("掌中乐游破解");
						}
					}
					else
					{
						obj.packet_id = obj.ExData;
						if(ConstValue.DEBUG == 1)
						{
							DebuUtil.log("透传参数是渠道号="+obj.ExData);
							
						}
					}
				}
			
			
			//if(ret.contains("\""))//掌中乐游
			if(obj.bZhangZhong == true)
			{
				DebuUtil.log("修改透传参数obj.ExData"+obj.ret);
			    ZhangZhongDao zhangZhongDao = new ZhangZhongDao();
			    ZhangZhong zhangZhong =zhangZhongDao.getRecord(ret);
			    if(zhangZhong != null)
			    {
			    	obj.ExData = zhangZhong.getData();
	    			obj.app_key = zhangZhong.getData();
	    			obj.bZhangZhong = true;
	                obj.imei = zhangZhong.getImei();
	    			//DebuUtil.log2("修改透传参数obj.ExData"+obj.ExData);
			    }
			   // if(ConstValue.bNew == 1)
			    {
			    	//obj.bSDK = false;
			    }
				//obj.packet_id = obj.channel;
			}
			
			if(obj.ExData.contains("_"))
			{
				 String splitstr = obj.ExData;
				 String[] strarray=splitstr.split("_");     		
				 obj.packet_id = strarray[0];
				 
				 //if(ConstValue.bNew == 1)
				 {
					 if(strarray.length >= 3)
					 {
						 obj.bSDK = false;
						 obj.province = GetAmountIndex.getRegionCode("", strarray[2]);
						 
						 if(ConstValue.DEBUG == 1)
						 {
							DebuUtil.log("用户省份="+obj.province);
						 }
						 if(ConstValue.DEBUG == 1)
						 {
							 DebuUtil.log("不接SDK版本");
						 }
					 }
				 }
			}
			/*else
			{
				obj.packet_id = obj.ExData;
			}*/
		}
		else	
		{
			obj.packet_id = obj.channel;
		}
		
		/*if(!StringUtil.is_nullString(obj.imei))
		{
			obj.packet_id = obj.app_id.substring(7, 7+5)+"_"+obj.channel.substring(6, 6+4);//obj.app_code.substring(arg0, arg1);
			ActivateDao activateDao = new ActivateDao();
			m_activate = activateDao.getRecord(obj.imei,obj.packet_id);
			if(m_activate != null)
			{
				obj.packet_id = m_activate.packet_id;
				String code = GetAmountIndex.getRegionCode(m_activate.addr, "");
				obj.province = GetAmountIndex.getRegionCode("", code);
				
				if(ConstValue.DEBUG == 1)
				{
					 DebuUtil.log("用户省份="+obj.province);
				}
			}
			else
			{
				
			}
			if(ConstValue.DEBUG == 1)
			{
				DebuUtil.log("接SDK,IMEI="+obj.ExData+" packet_id="+obj.packet_id);
			}
		}*/
		return m_activate;
		
	}
}
