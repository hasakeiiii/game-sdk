package sdkReq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MobileNum;
import model.Open189;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.StringUtil;
import dao.MobileNumDao;
import dao.Open189Dao;
import dao.PayDao;

public class MobileRsq extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460305344278405373L;
	
	public void HandleReq(HttpServletRequest request, HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = request.getParameter("result");
		String replys = request.getParameter("replys");
		String num = "";
		String content = "";
		MobileNumDao mobileNumDao = new MobileNumDao();
		MobileNum mobileNum = new MobileNum();
		if(result.equals("1000")){
			
				String[] replysarray=replys.split(";");
				
				for(int i =0;i < replysarray.length;i++){
					 String[] strarray=replysarray[i].split(",");
					 num = strarray[1];
					 content = strarray[2];
					 mobileNum.setNum(num);
					 mobileNum.setContent(content);
					 mobileNumDao.add(mobileNum); 
				}
			
			
	/*		 String[] strarray=replys.split(",");
			 num = strarray[1];
			 content = strarray[2];
			mobileNum.setNum(num);
			mobileNum.setContent(content);
			mobileNumDao.add(mobileNum); */
		}
		
		if(mobileNum != null)
		{			
			//JSONObject rsqjson = new JSONObject();
			out.print("OK");
		}
		
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
