package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Pay;
import net.sf.json.JSONObject;
import dao.PayDao;

public class PayInfoAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PayInfoAction() {
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
		
		String gameNo = request.getParameter("gameNo");
		String channelNo = request.getParameter("channelNo");
		String businessNo = request.getParameter("businessNo");
		String payType = request.getParameter("payType");
		Integer year = Integer.parseInt(request.getParameter("year"));
		Integer month = Integer.parseInt(request.getParameter("month"));
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		Integer curPage = Integer.parseInt(request.getParameter("curPage"));
		String dayStr = request.getParameter("day");
		Integer day = "请选择".equals(dayStr) ? -1 : Integer.parseInt(dayStr); 
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		Date beginDate = null;
		Date endDate = null;
		PayDao dao = new PayDao();
		List<Pay> list;
		Map<String,Object> rawMap = null;
		//如果不是-1代表按日查询,是-1则按月查询
		if(day != -1){
			beginDate = cal.getTime();
			rawMap = dao.getPayInfoList(gameNo, channelNo, businessNo, payType,beginDate,pageSize,curPage);
			list = (List<Pay>) rawMap.get("list");
		}else{
			cal.set(Calendar.DAY_OF_MONTH, 1);
			beginDate = cal.getTime();
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			endDate = cal.getTime();
			rawMap = dao.getPayInfoList(gameNo, channelNo, businessNo, payType,beginDate, endDate,pageSize,curPage);
			list = (List<Pay>) rawMap.get("list");
		}
		
		JSONObject jo = new JSONObject();
		
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(Pay.AliPayType, "支付宝");
		map.put(Pay.MmPayType, "移动");
		map.put(Pay.TenPayType, "财付通");
		map.put(Pay.UnionPayType, "银联");
		map.put(Pay.YeePayType, "话费充值卡");
		map.put(Pay.UniPayType, "联通");
		map.put(Pay.TCPayType, "电信");
		map.put("", "NULL");
		map.put(null, "NULL");
		
		Iterator<Pay> car = list.iterator();
		while(car.hasNext()){
			Pay pay = car.next();
			String payTypeStr = pay.getPayType();
			if(map.containsKey(payTypeStr))
				pay.setPayType(map.get(payTypeStr));
		}
		
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
