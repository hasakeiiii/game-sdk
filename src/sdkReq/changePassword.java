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

import net.sf.json.JSONObject;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.StringUtil;
import dao.LoginDao;
import dao.RegisterDao;


public class changePassword extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8538943172128041738L;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void HandleReq(String reqStr, HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		model.Login obj = null;
		String newPassword = "";
		model.Register register = null;
		
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
			obj = new model.Login(json);
			newPassword = json.getString("newpassword");
			//LoginDao dao = new LoginDao();
			//ret = dao.login(obj);
			
			RegisterDao dao = new RegisterDao();
			register=dao.chagepass(obj.getUsername(),obj.getPass(),newPassword); 
		}
	
		JSONObject rsqjson = new JSONObject(); 
		if(register != null)
		{
			DebuUtil.log("修改成功");    
			String bind = "false";
			//if(ConstValue.bNew == 1)
			{
				String phone = register.getPhoneNum();
				DebuUtil.log("获取到的手机号码是："+phone);
				if (!StringUtil.is_nullString(phone)&&!phone.isEmpty()) {
					bind = "true";
				}
			}
			rsqjson.put("bindstatus", bind);//为客户端提供判断是否绑定过
			rsqjson.put("result", 1);//
			rsqjson.put("LOGIN_ACCOUNT", register.getUsername());
			rsqjson.put("ACCOUNT_ID", register.getId());     
			rsqjson.put("sign", "");
			rsqjson.put("amount", 0);
			rsqjson.put("max_amount", 0);	
			rsqjson.put("message", "修改成功！");
		}
		else
		{
			rsqjson.put("result", -1);
			rsqjson.put("message", "账号或密码错误！");
			
		}
		String jsonstr = rsqjson.toString();
		/*String rsq = "";
		try {
			rsq =new String(jsonstr.getBytes(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		out.print(jsonstr);
		out.close();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("changePassword doGet");
		HandleReq(request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("changePassword doPost");
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
