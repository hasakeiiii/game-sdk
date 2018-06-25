package sdkReq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.alipay.sign.Base64;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import model.DeviceInfo;
import model.Pay;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.StringUtil;
import dao.ActivateDao;
import dao.DeviceInfoDao;
import dao.LoginDao;

public class Init extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6442490577821492819L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void HandleReq(String reqStr, HttpServletResponse response)
	{
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf8");
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//reqStr = request.getQueryString();
		//debugqStr = reqStr;
		/////////////////////////////////////////////////////////////////////
		
		///////////////////////////////////////////////////////////////////
		//out.print(debugqStr);device
		if(reqStr != null)
		{
		   DebuUtil.log(DateUtil.getDateTime()+"请求参数"+reqStr);
		//}
		//if(ret == ConstValue.OK)
		//{
		   JSONObject json = JSONObject.fromObject(reqStr);
		   
			JSONObject rsqjson = new JSONObject();
		
			/* id
			 	jso.put("packageId", packageId);//20
	           、
	            jso.put("imei", imei);//20
	            jso.put("imsi", imsi);//20
	            jso.put("model", model);//30
	            
	             jso.put("mac", mac);
	            jso.put("osVersion", osVersion);
	            jso.put("networkType", networkType);
	            jso.put("screenWidth", screenWidth);
	            jso.put("screenHeight", screenHeight);
	            jso.put("ver", Constants.GAME_SDK_VERSION);*/
			
			
			
			
			String strvalue = StringUtil.getString(json, "imei");
			if(StringUtil.is_nullString(strvalue))
			{
				strvalue = Pay.getOutTradeNo();
			}
			else
			{
				addToDeviceInfo(json);
			}
			rsqjson.put("device_id",strvalue);//imei
			out.print(rsqjson.toString());
		}
		
		out.close();
		
	}
/**
 * 将json中的设备信息加入device_info表中
 * @param json
 */
	private void addToDeviceInfo(JSONObject json) {
		DeviceInfoDao deviceDao = new DeviceInfoDao();
		
		ArrayList<Object> result = deviceDao.getObject("select * from device_info where imei='"+json.getString("imei")+"'");
		if (!result.isEmpty()) {
			return;
		}
		DebuUtil.log(DateUtil.getDateTime()+"检查到相关数据为空，可以加如数据");
		
		DeviceInfo deviceInfo = new DeviceInfo();
		
		
		deviceInfo.setPackageId(json.getString("packageId"));
		
		if(json.has("imei"))
		deviceInfo.setImei(StringUtil.getString(json,"imei"));
		
		
		if(json.has("imsi"))
		deviceInfo.setImsi(StringUtil.getString(json,"imsi"));
		deviceInfo.setModel(json.getString("model"));
		
		if(json.has("mac"))
		deviceInfo.setMac(json.getString("mac"));
		deviceInfo.setOsVersion(json.getString("osVersion"));
		deviceInfo.setNetworkType(json.getString("networkType"));
		deviceInfo.setScreenWidth(json.getString("screenWidth"));
		deviceInfo.setScreenHeight(json.getString("screenHeight"));
		deviceInfo.setVer(json.getString("ver"));
		
		deviceDao.add(deviceInfo);//lsl
	}
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("init doGet");
		HandleReq(request.getQueryString(),response);
		

//out.close();
	}
	


	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("init doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		//BufferedReader   br=new BufferedReader(in); 
		if(ConstValue.RDR == 1)
		{
		    //str = URLDecoder.decode(str,"UTF-8");	
		}
		
		HandleReq(str,response);
	}
}
