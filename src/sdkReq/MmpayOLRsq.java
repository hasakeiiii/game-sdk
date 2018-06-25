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

public class MmpayOLRsq extends HttpServlet {

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
	    
		//}
	}
	
	
	
	public static String getNodeValue(Node node){
		try{
			return node.getFirstChild().getNodeValue();
		}catch(Exception e){}
		return null;
	}
	
	
	public static void parseMMRsq(String xmlstr,model.MmPay obj) 
	{
		// step 1: 获得dom解析器工厂（工作的作用是用于创建具体的解析器）
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		//model.Activate m_activate = null;
		
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
    			//m_activate = handleExData(ret,obj);
    			obj.pay_no = ret;
    			
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
    				//bFail = true;
    			}
    		}
    		
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
	
		
	   return ;
			
	}


	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        InputStream in = req.getInputStream();
        ByteArrayOutputStream baos = null;
        String xmlStr = null; 
        
        DebuUtil.log("移动MM通知来了");       
		
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
              parseMMRsq(xmlStr,obj);
             if(ConstValue.DEBUG == 1)
             DebuUtil.log("MM回应 ="+xmlStr);
             String respXml = String.format(respXmlformat, obj.TransactionID);//
 	    	 DebuUtil.log("回应MM="+respXml);
             writeDataResponse(respXml,resp);//respXml
             handle(obj);
             
        }catch(Exception e){
            System.out.println(e);
        }finally { if (baos != null)  baos.close();
        if (in != null) in.close();}
             
     }
	

	
	public static void handle(model.MmPay obj)
	{
			
		    int bRSQ = 0;
            int bpaySA = 0;
      
            obj.date = DateUtil.getDate();
            obj.time = DateUtil.getTime();
          
       
	    	
            MmPayDao dao = new MmPayDao();
    	    int ret = dao.isExit(obj);//dao.mmPay(obj);
    	    
            
    	    if(ret != ConstValue.Fail)
    	    {
    	    
	    	    PayDao paydao = new PayDao();
	    	    Pay pay = null;
	    	    
	    	    pay = paydao.getPayRecord(obj.getTradeNo(),"mmpay");
	    	    
	    	    if(pay == null)
	    	    {
		    	   /* if(!StringUtil.is_nullString(obj.cp_order_no))
		    		{
		    			if(ConstValue.DEBUG == 1)
		    			{
		    				DebuUtil.log("通过CP订单号找pay信息");
		    			}
		    			pay = paydao.getPayRecordByCpOrder(obj.cp_order_no);
		    		}*/
		    	    if(!StringUtil.is_nullString(obj.pay_no))
		    		{		    			
		    			pay = paydao.getPayRecord(obj.pay_no);
		    		}
	    	    }
    	    
		    	dao.mmPay(obj); 
    	    		    	    
	 
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
    	    		
    	    	    paydao.edit(pay);
    	    	 	    	
    	    		  
	    	    	paydao.payNotisfy(pay,obj.bSDK);
	    	    }
	    	    	
    	    	   	    
	    	    
    	    }
       
	}
	
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

	
}
