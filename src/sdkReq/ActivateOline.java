package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import model.Cooperation;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.JsonUtil;
import util.NetUtil;
import util.StringUtil;
import dao.ActivateDao;
import dao.AppDao;
import dao.CooperationDao;

public class ActivateOline extends HttpServlet{

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
		String identiCodeUrl = "";
		String imei="";//
		String imsi="";//
		//String strAppKey = "";
		//String strChannelID = "";
		//String app_id = "";
		String cpaywaysign = "";//支付方式标记字符串
		int yeediscount = 80;//易宝折扣
		Long LMoney = (long) 0;
		int white = 0; 
		String appPayId = "";
		String province = "";
		response.setContentType("text/html;charset=utf8");
		boolean bSA = false;
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//out.print(debugqStr);//
		if(reqStr != null)
		{
			DebuUtil.log("请求参数="+reqStr);
			String ip = NetUtil.getIpAddr(request);
			JSONObject json = JSONObject.fromObject(reqStr);
			model.Activate obj = new model.Activate(json);
			obj.addr = ip;
			imsi = JsonUtil.getString(json, "imsi");
			DebuUtil.log("设备="+obj.getDeviceId());

			imei = obj.getDeviceId();
			DebuUtil.log("PacketId="+obj.getPacketId());
			ActivateDao dao = new ActivateDao();
			AppDao appDao = new AppDao();
			app = appDao.getAppRecord(obj.getGameId());
			//int bSA = 0;
			bSA = App.isStandAloneGame(app);
			
			
			//String provinceCode = GetAmountIndex.getRegionCode(obj.addr, "");
			//province = GetAmountIndex.getRegionCode("", provinceCode);
			activate(obj);
			
		
			
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
				cpaywaysign = app.getPaywaysign();//2015-07-07支付标记
				yeediscount = app.getYeediscount();
				if (yeediscount==0) {
					yeediscount = 80;
				}
				
				if(ConstValue.STAND_ALONE == 1)
				{
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
			}

		}

		
		

	
		//if(ret == ConstValue.OK)//http://119.147.23.178:8080/sdk/TestServlet
		{
			JSONObject rsqjson = new JSONObject();
			
			
			
			if(!bSA)
			{
				rsqjson.put("changepassword_url", ConstValue.ServerUrl+ConstValue.path+"/changePassword");
				rsqjson.put("register_url", ConstValue.ServerUrl+ConstValue.path+"/register");
				rsqjson.put("login_url", ConstValue.ServerUrl+ConstValue.path+"/login");
				rsqjson.put("alipay_wap_url", ConstValue.ServerUrl+ConstValue.path+"/alipay_wap");
				rsqjson.put("reqCodeUrl", ConstValue.ServerUrl+ConstValue.path+"/IdentifyServlet");
				rsqjson.put("identiCodeUrl", ConstValue.ServerUrl+ConstValue.path+"/IdentifyServlet2");
			}
			rsqjson.put("afdf", ConstValue.ServerUrl+ConstValue.path+"/afdf");
			rsqjson.put("url", ConstValue.ServerUrl+ConstValue.path+"/%s");
				
			
			rsqjson.put("dipcon", dipcon);
			rsqjson.put("dipcon2", dipcon2);
			rsqjson.put("dipurl", dipurl);
			rsqjson.put("noturl", noturl);
			rsqjson.put("exiturl", exiturl);
			rsqjson.put("payways","ali");
			rsqjson.put("cpaywaysign", cpaywaysign);
			rsqjson.put("yeediscount", yeediscount);
			
			
			
			
			//if(ConstValue.DEBUG  == 1)
			{
				DebuUtil.log("激活回应数据="+rsqjson.toString());
			}
			
			out.print(rsqjson.toString());
		}
		out.close();
		
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
