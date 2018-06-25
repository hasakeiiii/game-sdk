package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Mzcharge;
import net.sf.json.JSONObject;
import util.StringUtil;
import dao.MzchargeDao;
import dao.RegisterDao;
/**
 * 充值查询通用接口
 * @author wuchengf
 *
 */
public class RechargeQuery extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		
		String cmd = request.getParameter("cmd");
		
		if(!StringUtil.is_nullString(cmd)){
			if("queryBalance".equals(cmd)){
				queryBalance(request,response);
			}else if ("queryRechargeRecords".equals(cmd)){
				queryRechargeRecords(request,response);
			}
		}
		
	}

	private void queryRechargeRecords(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MzchargeDao dao = new MzchargeDao();
		List<Mzcharge> list = Collections.emptyList();
		
		String username = request.getParameter("username");
		String pageStr = request.getParameter("page");
		String rowsStr = request.getParameter("rows");
		
		
		if(StringUtil.is_nullString(rowsStr) || StringUtil.is_nullString(pageStr) || StringUtil.is_nullString(username)){
			return;
		}else{
			int rows = Integer.parseInt(rowsStr);
			int page = Integer.parseInt(pageStr);
			Map<String,Object> map = dao.findRecordsByUsername(username,page,rows);
			
			List rawList = (ArrayList) map.get("list");
			int total = (Integer)map.get("total");
			
			JSONObject jo = new JSONObject();
			jo.put("list", rawList);
			jo.put("total", total);
			
			PrintWriter out = response.getWriter();
			out.print(jo.toString());
			out.flush();
			out.close();
		}
			
		
	}

	private void queryBalance(HttpServletRequest request, HttpServletResponse response) throws IOException {
		RegisterDao dao = new RegisterDao();
		
		String username = request.getParameter("username");
		if(!StringUtil.is_nullString(username)){
			int balance = dao.getBalanceByName(username);
			JSONObject jo = new JSONObject();
			jo.put("account_balance", balance);
			
			PrintWriter out = response.getWriter();
			out.print(jo.toString());
			out.flush();
			out.close();
		}
	}

}
