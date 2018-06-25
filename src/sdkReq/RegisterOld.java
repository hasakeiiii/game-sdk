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
import util.MySocket;

public class RegisterOld extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6121451032678394268L;

	public static String url = ConstValue.RDRServerUrl+"/register";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("register doGet");
		ActivateOld.HandleReq(url,request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*byte bufs[] = new byte[20];
		String host = "127.0.0.1";
		int port = 4700;
		String ret = MySocket.sendPacket(bufs, host, port);*/
		
		DebuUtil.log("register doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		ActivateOld.HandleReq(url,str,response);
	}	
}
