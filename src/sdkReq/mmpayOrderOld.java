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

public class mmpayOrderOld extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9085956600868988306L;

	public static String url = ConstValue.RDRServerUrl+"/mmpay_sign";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("mmpay_sign doGet");
		ActivateOld.HandleReq(url,request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DebuUtil.log("mmpay_sign doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		ActivateOld.HandleReq(url,str,response);
	}	
}
