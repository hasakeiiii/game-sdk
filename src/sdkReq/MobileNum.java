package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MobileNumDao;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.FileUtil;
import util.JsonUtil;

public class MobileNum extends HttpServlet{


	private static final long serialVersionUID = -7749265620086362124L;
	/**
	 * 
	 */
	public void HandleReq(HttpServletRequest request,HttpServletResponse response)
	{
		PrintWriter out = null;
		
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String imsi = "";
		String mobileNum ="";
		 imsi = request.getParameter("imsi");
			
			MobileNumDao numDao = new MobileNumDao();
			mobileNum = numDao.getImsi(imsi);  //获取imsi的手机号
		
			
			

		JSONObject rsqjson = new JSONObject();
		rsqjson.put("mobile_num", mobileNum); 
		out.print(rsqjson.toString());
		out.close();
		
}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	    HandleReq(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
/*		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");*/
		
	//	HandleReq(str,response);
		HandleReq(request,response);
		
	}
}
