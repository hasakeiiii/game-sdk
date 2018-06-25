package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import dao.ProvinceDataDao;

public class BussinessProfitAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BussinessProfitAction() {
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

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String op = request.getParameter("op");
		String gameNo = request.getParameter("gameNo");
		String channelNo = request.getParameter("channelNo");
		String businessNo = request.getParameter("businessNo");
		String apkNo = request.getParameter("apkNo");
		Integer year = Integer.parseInt(request.getParameter("year"));
		Integer month = Integer.parseInt(request.getParameter("month"));
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer curPage = Integer.parseInt(request.getParameter("curPage"));
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		cal.set(year, month - 1,1);
		cal1.set(year, month - 1,31);
		Date beginDate = null;
		Date endDate = null;

		
		ProvinceDataDao dao = new ProvinceDataDao();
		Map<String,Object> rawMap = null;
		List<ArrayList> list;
			beginDate = cal.getTime();
			endDate = cal1.getTime();
			rawMap = dao.getBusinessProfit(apkNo,beginDate,endDate,pageSize,curPage);
//			List<BusinessProfit> list = dao.getBusinessProfit(gameNo,channelNo,businessNo,apkNo,beginDate,endDate);
			list = (List<ArrayList>) rawMap.get("list");
		
		JSONObject jo = new JSONObject();
		//Iterator<ProvinceData> provinceData = list.iterator();
		jo.put("info", "");
		if(list.size() == 0){
			jo.put("info", "暂无数据");
		}
		jo.put("map", rawMap);
		System.out.println(jo);
		out.print(jo); 
		out.flush();
		out.close();
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
