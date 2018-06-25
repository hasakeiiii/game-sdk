package sdkReq;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;

public class InitOld extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3409724261326206501L;
	public static String url = ConstValue.RDRServerUrl+"/init";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("InitOld doGet");
		ActivateOld.HandleReq(url,request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DebuUtil.log("InitOld doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		ActivateOld.HandleReq(url,str,response);
	}
}
