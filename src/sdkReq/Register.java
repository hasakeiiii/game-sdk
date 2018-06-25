package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import model.Cooperation;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.StringUtil;
import dao.AppDao;
import dao.CooperationDao;
import dao.LoginDao;
import dao.RegisterDao;

public class Register extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8860898703901206020L;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public static void setResContent(HttpServletResponse response)
	{
		/* 设置格式为text/json    */
		response.setContentType("text/json"); 
        /*设置字符集为'UTF-8'*/
		try {
			((ServletRequest) response).setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void HandleReq(String reqStr, HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		String devID="";
		String GameID="";
		String ver="";
		String packet_id="";
		
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.Register obj = null;
		RegisterDao dao = new RegisterDao();
		if(reqStr != null)
		{
			DebuUtil.log("请求数据"+reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			obj = new model.Register(json);
			
			devID = obj.getDeviceId();
			GameID = obj.getGameId();
			packet_id = obj.getPacketId();
			ver = json.getString("ver");
			
			ret = dao.register(obj);//
			
		}
		
		obj=dao.getRegisterRecord(obj.getUsername());
		
		if((ret == ConstValue.OK) && (obj != null))
		{
			
			JSONObject rsqjson = new JSONObject();          
			rsqjson.put("result", 1);
			rsqjson.put("message", "登陆成功！");
			rsqjson.put("LOGIN_ACCOUNT", obj.getUsername());   
			rsqjson.put("ACCOUNT_ID", obj.getId());
			String phone= "";
			String bind = "false";
			phone = obj.getPhoneNum();
			DebuUtil.log("Register返回的绑定号码是："+phone);
			if (!StringUtil.is_nullString(phone)&&!phone.isEmpty()) {
				bind = "true";
			}
			DebuUtil.log("Register返回的绑定状态是："+bind);
			rsqjson.put("bindstatus", bind);//为客户端提供判断是否绑定过
			
			AppDao appdao = new AppDao();
			App appinfo = appdao.getAppRecord(GameID);
			String sign="";
			String key = "key";
			
			if(appinfo != null)
			{
				key = appinfo.getMykey();
			}
			sign = util.Rsa.getMD5(obj.getUsername()+key);
			rsqjson.put("sign", sign);
			
			/*if(ver.length() > 4)
			{
	            Date curdate = DateUtil.strToDate(DateUtil.getDate());
				
	            String datestr = String.format("%d-%02d-00", 1900+curdate.getYear(),curdate.getMonth()+1);
				String sql;
				sql = String.format("select sum(amount) from pay where username='%s' and game_id='%s' and date>='%s' and state=1 and pay_type='mmpay'",obj.getUsername(),GameID,datestr);
				int amount = dao.getCount(sql);
				int max_amount = 10;
				
				CooperationDao cooperationDao = new CooperationDao();
				Cooperation cooperation =cooperationDao.getRecord(packet_id);
				if(cooperation != null)
				{
					max_amount = cooperation.getSettleValue();
				}
				
				
				DebuUtil.log("sql="+sql);
				DebuUtil.log("max_amount="+max_amount);
				rsqjson.put("amount", amount/100);
				rsqjson.put("max_amount", max_amount);
			}*/
			
			out.print(rsqjson.toString());
		}
		else
		{
			JSONObject rsqjson = new JSONObject(); 
			if(obj == null){
				rsqjson.put("result", -2);
				rsqjson.put("message", "没填密码！");
			}else{
				rsqjson.put("result", -1);
				rsqjson.put("message", "账号已被注册！");
			}
			out.print(rsqjson.toString());
		}
		out.close();
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("register doGet");
		HandleReq(request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("register doPost");
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
