package sdkReq;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.alipay.sign.Base64;
import com.yeepay.HttpUtils;













import model.Cooperation;
import model.MmPayData;
import model.MsmList;
import model.Pay;
//import mobi.zty.sdk.game.Constants;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.StringUtil;
import dao.CooperationDao;
import dao.MmPayDataDao;
import dao.MsmListDao;
import dao.RegisterDao;

public class MSMPayReq extends HttpServlet{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8783499538690341229L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void HandleReq(HttpServletRequest request, HttpServletResponse response)
	{
		////: http://14.18.206.189:8080/sdk/getcon?amount=200&gameId=180&imsi=460026894200239&imei=69630019000979&packet_id=381180001
		String amountstr = request.getParameter("amount");
		String gameId = request.getParameter("gameId");
		String imsi = request.getParameter("imsi");
		String imei = request.getParameter("imei");
		String packet_id = request.getParameter("packet_id");
		//String userdata =  request.getParameter("userdata");
		String orderNO = "";
		int amount = Integer.valueOf(amountstr);
		DebuUtil.log("req="+request.getQueryString());
		if(packet_id.equals("405200001"))
		{
			//orderNO =  request.getParameter("orderNO");
		}
		orderNO =  request.getParameter("orderNO");
		GetAmountIndex.HandleReq(amount, imei, imsi, packet_id, gameId,orderNO, response);
		
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DebuUtil.log("gameExit doGet");
		//response.;
		HandleReq(request,response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DebuUtil.log("gameExit doPost");
		/*InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");*/
		//request.;
		//String str = "http://211.154.152.59:8080/sdk/getcon?amount=1&packet_id=2141&imei=499000316129545&imsi=460029999154959";
		//response.sendRedirect(str);
		if(ConstValue.RDR == 1)
		{
		    //str = URLDecoder.decode(str,"UTF-8");	1&2&
		}
		/////////////////////////////////////
		HandleReq(request,response);
		/////////////////////////////////////
	}
	

}
