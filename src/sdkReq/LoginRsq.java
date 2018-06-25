package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import net.sf.json.JSONObject;
import dao.AppDao;
import dao.LoginDao;
import dao.RegisterDao;

import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;

public class LoginRsq extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -690990853951229569L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void HandleReq(HttpServletRequest request, HttpServletResponse response)
	{
		PrintWriter out = null;
		String rsq = "success";
		response.setContentType("text/html;charset=utf8");
		//String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String level = request.getParameter("level");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RegisterDao dao = new RegisterDao();
		DebuUtil.log("username="+username);
		DebuUtil.log("level="+level);
		model.Register reg = dao.chageLevel(username, Integer.valueOf(level)); 
		if(reg == null)
		{
			rsq = "fail";
		}
		out.print(rsq);
		out.close();
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("LoginRsq doGet");
		HandleReq(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("LoginRsq doPost");
		
		HandleReq(request,response);
	}	
}
