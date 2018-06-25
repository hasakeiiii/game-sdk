package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RegisterDao;

import net.sf.json.JSONObject;

import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.SendMsmUtils;

public class FindPswServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public FindPswServlet() {
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

	private void handleRequest(String reqstr, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf8");
		String phoneNum;
		
		DebuUtil.log("短信内容："+reqstr);
		JSONObject reqjson = JSONObject.fromObject(reqstr);
		phoneNum = reqjson.getString("phoneNum");
		RegisterDao rdao = new RegisterDao();
		model.Register register = rdao.getRecordByPhoneNum(phoneNum);
		
		String result = "fail";
		String[] content = new String[2];
		String username = "";
		String psw = "";
		if (register!=null) {
			username = register.getUsername();
			psw = register.getPass();
			content[0] = username;
			content[1] = psw;
			//result = SendMsmUtils.sendSMS(phoneNum,content,null);
			result = SendMsmUtils.sendSMS(phoneNum,content,"17170");
			
		}
		
		
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("result", result);
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
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
