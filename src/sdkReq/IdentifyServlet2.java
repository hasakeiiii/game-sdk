package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TempSms;
import net.sf.json.JSONObject;
import util.DebuUtil;
import util.FileUtil;

import dao.RegisterDao;
import dao.TempSmsDao;
/**
 * 处理用户提交的验证码，判断是否与下发的验证码一致
 * @author Administrator
 *
 */
public class IdentifyServlet2 extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public IdentifyServlet2() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqStr = request.getQueryString();
		handleRequest(reqStr, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		handleRequest(str, response);
		
	}

	private void handleRequest(String reqstr,
			HttpServletResponse response) {
		String result = "";
		String code = "";
		String addtime = "";
		String adddate = "";
		String message = "";
		response.setContentType("text/html;charset=utf8");
		
		DebuUtil.log("请求内容："+reqstr);
		JSONObject reqjson = JSONObject.fromObject(reqstr);
		code = reqjson.getString("identiCode");
		adddate = reqjson.getString("addDate");
		addtime = reqjson.getString("addTime");
		
		TempSmsDao tempSmsDao = new TempSmsDao();
		//验证验证码
		result = tempSmsDao.checkCode(code, addtime, adddate);
		
		if (result.equals("000")) {
			message = "绑定成功";
		}else if(result.equals("001")){
			message = "验证码不存在";
		}else if (result.equals("002")) {
			message = "验证码超时";
		}
		
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject(); 
			json.put("result", result);
			json.put("message", message);
			json.put("addTime", addtime);
			json.put("addDate", adddate);
			
			out.println(json.toString()); 
			DebuUtil.log("返回："+json.toString());
			out.flush();
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
