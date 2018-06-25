package sdkReq;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.Pay;
import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.ConstValue;
import util.DebuUtil;
import dao.MmPayDao;
import dao.PayDao;
import dao.RegisterDao;




public class TestServlet extends HttpServlet {


	public static  void parseMMRsq(String xmlstr,model.MmPay obj) 
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
		
		NodeList list = document.getElementsByTagName("SyncAppOrderReq");
		
		for(int i = 0; i < list.getLength(); i++)
		{
			Element element = (Element)list.item(i);
			
			String content = element.getElementsByTagName("AppID").item(0).getFirstChild().getNodeValue();
			obj.app_id = content;
			System.out.println("AppID:" + content);
			
			content = element.getElementsByTagName("PayCode").item(0).getFirstChild().getNodeValue();
			obj.app_code = content;
			System.out.println("PayCode:" + content);
			
			content = element.getElementsByTagName("TradeID").item(0).getFirstChild().getNodeValue();
			obj.trade_no = content;
			System.out.println("TradeID:" + content);
			
			content = element.getElementsByTagName("ChannelID").item(0).getFirstChild().getNodeValue();
			obj.channel = content;
			System.out.println("ChannelID:" + content);
			
			content = element.getElementsByTagName("TotalPrice").item(0).getFirstChild().getNodeValue();
			obj.amount = Integer.valueOf(content);
			System.out.println("TotalPrice:" + content);
			obj.ret = 0;
			System.out.println("--------------------------------------");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        InputStream in = req.getInputStream();
        ByteArrayOutputStream baos = null;
        String xmlStr = null;
        
        System.out.println("移动MM通知来了");
        model.MmPay obj = null;
		
		
        try{
         
            baos = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int read = 0;
            while((read = in.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            xmlStr = new String(baos.toByteArray(),"utf-8");
            System.out.println("read xmlStr: "+xmlStr);
            
            //xml2vo
            /*SyncXMLUtils utils=new SyncXMLUtils();
            AppOrderQueryReq appOrderQueryReq = (AppOrderQueryReq)utils.xml2Vo(xmlStr, "AppOrderQueryReq",  AppOrderQueryReq.class.getName());
            System.out.println("appOrderQueryReq.getAppID=============>"+appOrderQueryReq.getAppID());
            String respXml="<?xml version='1.0' encoding='UTF-8'?><AppOrderQueryResp><MsgType>AppOrderQueryResp</MsgType><Version>1.0.0</Version><hRet>106</hRet><OrderInfo><OrderID></OrderID><StartTime></StartTime><ExpiredTime></ExpiredTime><ValidTimes>0</ValidTimes><UserID></UserID><SPServiceID></SPServiceID></OrderInfo></AppOrderQueryResp>";
         
            AppOrderQueryResp _resp=(AppOrderQueryResp)utils.xml2Vo(respXml, "AppOrderQueryResp",  AppOrderQueryResp.class.getName());
            System.out.println("MsgType=============>"+_resp.gethRet());*/
            String respXml="<?xml version='1.0' encoding='UTF-8'?><AppOrderQueryResp><MsgType>AppOrderQueryResp</MsgType><Version>1.0.0</Version><hRet>106</hRet><OrderInfo><OrderID></OrderID><StartTime></StartTime><ExpiredTime></ExpiredTime><ValidTimes>0</ValidTimes><UserID></UserID><SPServiceID></SPServiceID></OrderInfo></AppOrderQueryResp>";
            parseMMRsq(xmlStr,obj);
            
    		
            MmPayDao dao = new MmPayDao();
    	    dao.mmPay(obj);
    	    PayDao paydao = new PayDao();
    	    Pay pay = paydao.getPayRecord(obj.trade_no, "mm");
    	    if(pay != null)
    	    {
    	    	pay.packet_id = obj.channel;
    	    	pay.thir_pay_id = obj.trade_no;
    	    	pay.state = ConstValue.OK;
    	    }
    	    else
    	    {
    	    	
    	    }
            writeDataResponse(respXml,resp);//respXml
        }catch(Exception e){
            System.out.println(e);
        }finally { if (baos != null)  baos.close(); }
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
	       catch (IOException e) {
	           throw e;
	       }
	       finally {
	           if (os != null)  os.close(); 
	       }
	   }

}

