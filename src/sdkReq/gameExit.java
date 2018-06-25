package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import model.Register;
import net.sf.json.JSONObject;
import dao.ActivateDao;
import dao.AppDao;
import dao.LoginDao;
import dao.RegisterDao;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.StringUtil;

public class gameExit extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6508388175612809205L;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public static void test()
	{
		JSONObject json = new JSONObject();
		json.put("login_account","");
		json.put("device_id","654678444245212");
		json.put("game_id","199");
		json.put("packet_id","168199001");
		//DebuUtil.log("Username="+Username);
		json.put("adff2",5000);
		json.put("adff3",5);
		HandleReq(json);
	}
	
	public static void HandleReq(JSONObject json)
	{
		String Username="";
		int time = 0;
		String ver="";
		String device_id="";
		String game_id = "";
		String packet_id = "";
		int level = 0;
		
		Username = StringUtil.getString(json,"login_account");
		device_id = StringUtil.getString(json,"device_id");
		game_id = StringUtil.getString(json,"game_id");
		packet_id = StringUtil.getString(json,"packet_id");
		//DebuUtil.log("Username="+Username);
		time = StringUtil.getInt(json,"adff2");
		level = StringUtil.getInt(json,"adff3");
		//DebuUtil.log("time="+time);
		if (device_id.equals("866002024678392")) {
			String t = DateUtil.getDateTime();
			DebuUtil.log3("时间："+t+"\n用户名："+Username
					+"\n设备号："+device_id
					+"\n游戏id："+game_id
					+"\n新增时长："+time
					+"\n等级："+level);
		}
		time = getOnLineTiome(time);
		AppDao appDao = new AppDao();
		App app = appDao.getAppRecord(game_id);
		int bSA = 0;
		if((app != null) && (app.gameType != null))
		{
			if(app.gameType.equals(app.OFF_LINE))
			{
				bSA = 1;
				Username = device_id;
			}
		}
		
		
		ActivateDao dao = new ActivateDao();
		dao.chageOnlinetime(device_id,game_id,Username,time,level); 
		/*RegisterDao dao = new RegisterDao();
		
		if(bSA == 1)
		{
			Register register = dao.getRegisterRecord(Username);
			if(register == null)
			{
				Register userReg= new Register(json,0);
				dao.add(userReg);
			}
			else
			{
			    dao.chageOnlinetime(Username,time,level); 
			}
		}
		else
		{
			dao.chageOnlinetime(Username,time,level); 
		}*/
		
	}
	public static int getOnLineTiome(int time){
		 
		if(time < 0 ||time > 1000){

			time=0;
		}
		
		return time;
	}
	public void HandleReq(String reqStr, HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		//model.Login obj = null;
		
		
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(reqStr != null)
		{
			DebuUtil.log("请求数据"+reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			HandleReq(json);
			/* user.put("device_id", deviceId);
	            user.put("packet_id", packetId);
	            user.put("game_id", gameId);
	            user.put("login_account", adff0);
	            
	            user.put("adff2", adff2);
	            user.put("adff3", adff3);*/
	            
		
		}
		
		
			
		out.print("OK");
		out.close();
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("gameExit doGet");
		HandleReq(request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("gameExit doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		if(ConstValue.RDR == 1)
		{
		    //str = URLDecoder.decode(str,"UTF-8");	
		}
		HandleReq(str,response);
	}
	
}
