package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Register;
import model.TempSms;
import net.sf.json.JSONObject;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.SendMsmUtils;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import dao.RegisterDao;
import dao.TempSmsDao;

public class IdentifyServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public IdentifyServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqStr = request.getQueryString();
		handleRequest(reqStr, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		handleRequest(str, response);

	}

	public void handleRequest(String reqstr,
			HttpServletResponse response) {
		
		String identiCode = "";
		String phoneNum, userName,psd;
		String addtime = "";
		String adddate = "";
		String message = "";
		String result = "";
		String ver = "";
		String[] content = new String[2]; 
		DebuUtil.log("短信内容："+reqstr);
		JSONObject reqjson = JSONObject.fromObject(reqstr);
		phoneNum = reqjson.getString("phoneNum");
		userName = reqjson.getString("userName");
		psd = reqjson.optString("psd");
		ver = reqjson.optString("ver");
		if (!ver.isEmpty()&&(ver.compareTo("1.38.1")>0)) {//档版本号不为空时才进行账号绑定
			RegisterDao rdao = new RegisterDao();
			Register ret = new Register();
			ret = rdao.getRegisterRecord(userName);
			if (ret==null) {
				result = "004";
			}else{
				if (!psd.equals(ret.pass)) {
					result = "004";
				}else{
					// 账号未绑定，进入绑定验证逻辑
					identiCode = getIdentiCode();
					addtime = DateUtil.getTime();
					adddate = DateUtil.getDate();

					TempSms tempSms = new TempSms();
					tempSms.setAddTime(addtime);
					tempSms.setAddDate(adddate);
					tempSms.setPhoneNum(phoneNum);
					tempSms.setIdentiCode(identiCode);
					tempSms.setCheckinTime(null);
					tempSms.setUserName(userName);
					DebuUtil.log("短信内容："+tempSms.toString());
					// 将信息写入tempsms表中
					TempSmsDao tempSmsDao = new TempSmsDao();
					
					result = tempSmsDao.addTempSmsRecord(tempSms);
				}
			}
		}else{
			result = "006";
		}
		String ret1 = "success";
		if (result.equals("000")) {
			DebuUtil.log3("添加成功，返回result：" + result);
			message = "验证码获取成功";
			content[0] = identiCode;
			content[1] = "2";
			// 发送验证码
			//ret1 = SendMsmUtils.sendSMS(phoneNum, identiCode,null);
			ret1 = SendMsmUtils.sendSMS(phoneNum, content,"17168");
			DebuUtil.log3("phoneNum：" + phoneNum+"\n identiCode:"+identiCode);
		}else if(result.equals("001")){
			message = "验证码获取失败，请重新点击获取";
			DebuUtil.log3("添加失败，返回result：" + result);
		}else if(result.equals("002")){
			message = "号码已绑定过账号，请使用其他号码绑定";
			DebuUtil.log3("添加失败，返回result：" + result);
			
		}else if(result.equals("004")){
			message = "账号或密码错误";
			DebuUtil.log3("添加失败，返回result：" + result);
			
		}else if(result.equals("006")){
			message = "该版本不支持账号绑定";
			DebuUtil.log3("添加失败，返回result：" + result);
			
		}
		if (ret1.equals("fail")) {
			result = "005";
			message = "服务器忙，请稍后再试。";
		}
		response.setContentType("text/html;charset=utf8");
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("addTime", addtime);
			json.put("addDate", adddate);
			json.put("result", result);
			json.put("message", message);
			out.println(json.toString());
			DebuUtil.log("返回：" + json.toString());
			out.flush();
			out.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	/**
	 * 根据情况生成验证码
	 * 
	 * @param callNumber
	 * @return
	 */
	private String getIdentiCode() {
		String i = (Math.random()*1000000+0.1)+"";
		System.out.println(i);
		String identiCode[] = i.split("\\.");
		System.out.println(identiCode[0]);
		return identiCode[0];
	}

	

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
