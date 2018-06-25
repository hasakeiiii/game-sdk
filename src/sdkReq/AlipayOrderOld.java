package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.HttpUtils;

import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;

public class AlipayOrderOld extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5797976159375616538L;

	public static String url = ConstValue.RDRServerUrl+"/alipay_sign_fo";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("alipay_signOld doGet");
		HandleReq(url,request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DebuUtil.log("alipay_signOld doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		DebuUtil.log("url="+url);
		HandleReq(url,str,response);
	}
	
	public static String  HandleReq(String url,String reqStr, HttpServletResponse response)
	{
		String responseStr = null;
		
		DebuUtil.log("url="+url);
		//DebuUtil.log("reqStr="+reqStr);
		
		responseStr = HttpUtils.sendPostDataEncode(url,reqStr);	   
		   
		/*try {
			responseStr = HttpUtils.URLPostUTF8(url,reqStr);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(responseStr == null)
		{
			responseStr="";
		}
		
		DebuUtil.log("responseStr="+responseStr);
		
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		out.print(responseStr);
	    out.close();
	    
		return responseStr;
	}
}
