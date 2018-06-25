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

import model.Activate;
import model.App;
import model.Cooperation;
import model.Pay;
import model.Register;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.NetUtil;
import util.StringUtil;
import dao.ActivateDao;
import dao.AppDao;
import dao.CooperationDao;
import dao.LoginDao;
import dao.PayDao;
import dao.RegisterDao;

public class Login extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1165363128197330651L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public static void setResContent(HttpServletResponse response)
	{
		/* 设置格式为text/json    */
		//response.setContentType("text/json"); 
        /*设置字符集为'UTF-8'*/
		/*try {
			((ServletRequest) response).setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void HandleReq(String reqStr, HttpServletRequest request,HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		model.Login obj = null;
		String devID="";
		String GameID="";
		String ver="";
		//String packet_id="";
		int adff2 = 0;
		int adff3 = 0;
		
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
			String ip = NetUtil.getIpAddr(request);
			
			obj = new model.Login(json);
			obj.ip = ip;
			if(PayDao.isFanXianPacket(obj.channel_no))
			{
			    PayDao payDao = new PayDao();
			    Pay pay = payDao.getOldPay(obj.username, obj.date);
			    if(pay != null)
			    {
				   obj.device_id = pay.device_id;
			       obj.ip = pay.ip;
			    }
				//obj.ip = "";
			}
			LoginDao dao = new LoginDao();
			devID = obj.getDeviceId();
		    GameID = obj.getGameId();
			//packet_id = obj.getPacketId();
			ver = json.getString("ver");
	
			
			ret = dao.login(obj);
			/*if(devID.equals("99000535142483"))//
			{
				DebuUtil.log3("login ret="+ret);
			}*/
		}
		
		JSONObject rsqjson = new JSONObject(); 
		model.Register register = new Register();
		RegisterDao dao = new RegisterDao();
		register = dao.getRegisterRecord(obj.getUsername());
		
	
		if (ret == ConstValue.OK) {
			
		/////*******************2015-03-25*****为客户端提供判断是否绑定过****************//////	
			String bind = "false";
			//if(ConstValue.bNew == 1)
			{
				String phone = register.getPhoneNum();
				DebuUtil.log("获取到的手机号码是："+phone);
				if (!StringUtil.is_nullString(phone)&&!phone.isEmpty()) {
					bind = "true";
				}
			}
		/////*******************2015-03-25******为客户端提供判断是否绑定过***************//////		
			
			rsqjson.put("result", 1);
			rsqjson.put("LOGIN_ACCOUNT", register.getUsername());
			rsqjson.put("ACCOUNT_ID", register.getId());  
			rsqjson.put("message", "登陆成功！");
			DebuUtil.log("返回的绑定状态是："+bind);
			rsqjson.put("bindstatus", bind);//为客户端提供判断是否绑定过
			
			AppDao appdao = new AppDao();
			App appinfo = appdao.getAppRecord(GameID);
			String sign="";
			
			String key = "key";
			
			
			if(appinfo != null)
			{
				key = appinfo.getMykey();
			}
			String signstr = register.getUsername()+key;
			sign = util.Rsa.getMD5(signstr);
			rsqjson.put("sign", sign);
			if(register.getUsername().equals("2345679"))
			{
				DebuUtil.log3("sign="+sign+" signstr="+signstr);
			}
			if(ver.length() > 4)
			{
	            Date curdate = DateUtil.strToDate(DateUtil.getDate());
				
	            String datestr = String.format("%d-%02d-00", 1900+curdate.getYear(),curdate.getMonth()+1);
				String sql;
				sql = String.format("select sum(amount) from pay where username='%s' and game_id='%s' and date>='%s' and state=1 and pay_type='mmpay'",obj.getUsername(),GameID,datestr);
				int amount = 10;//dao.getCount(sql);
				int max_amount = 10;
				
				/*CooperationDao cooperationDao = new CooperationDao();
				Cooperation cooperation =cooperationDao.getRecord(packet_id);
				if(cooperation != null)
				{
					max_amount = cooperation.getSettleValue();
				}*/
				
				/*if(GameID.equals("151"))
				{
					max_amount = 5000;
				}
				else if(GameID.equals("150"))
				{
					max_amount = 5000;
				}
				else if(GameID.equals("154"))
				{
					max_amount = 5000;
				}*/
				
				//DebuUtil.log("sql="+sql);
				if(Pay.isFen(GameID))
				{
					rsqjson.put("amount", amount);
					rsqjson.put("max_amount", max_amount*100);
				}
				else
				{
					rsqjson.put("amount", amount/100);
					rsqjson.put("max_amount", max_amount);
				}
			}
		}
		else
		{
			  
			rsqjson.put("result", -1);
			
			String msg = getResultMsg(ret, register);
			
			rsqjson.put("message", msg);

		}
		String jsonstr = rsqjson.toString();
		
		out.print(jsonstr);
		out.close();
	}

	
	/**
	 * 分析反馈信息，给出失败提示
	 * @param ret 失败标记
	 * @param register
	 * @return
	 */
	private String getResultMsg(int ret, model.Register register) {
		String msg = "";
		if(ret == ConstValue.AccountNoExistFail){
			msg = "账号不存在，请确认是否输入正确账号。";
		}else{
			
			int locktime = register.getLockedTime();
			
			if (ret == ConstValue.AccountLocked) {
				
				int now = (int)(System.currentTimeMillis()/1000);
				int tem = locktime+10*60-now;
				msg = "账号已锁住,"+tem+"秒后解除。";
			} else if(ret == ConstValue.PasswordFail){
				int pass_failedtimes = register.getPassfailTimes();
				DebuUtil.log("失败次数："+pass_failedtimes);
				int tem = 4-pass_failedtimes;
				msg = "密码错误，您还可以输入"+tem+"次";
			}

		}
		return msg;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("login doGet");
		HandleReq(request.getQueryString(),request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("login doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		if(ConstValue.RDR == 1)
		{
		    //str = URLDecoder.decode(str,"UTF-8");	
		}
		HandleReq(str,request,response);
	}
}
