package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.FeeData;
import net.sf.json.JSONObject;
import util.DateUtil;
import dao.FeeDataDao;

/**
 * Servlet implementation class FeeDataAction
 */
public class FeeDataAction extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		
		
		String cmd = request.getParameter("cmd");
		if("reportForm".equals(cmd)){
			handleReportForm(request,response);
		}
		
	}
	
	private void handleReportForm(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		Integer fromYear = Integer.parseInt(request.getParameter("fromYear"));
		Integer fromMonth = Integer.parseInt(request.getParameter("fromMonth"));
		Integer fromDay = Integer.parseInt(request.getParameter("fromDay"));
		
		Integer toYear = Integer.parseInt(request.getParameter("endYear"));
		Integer toMonth = Integer.parseInt(request.getParameter("endMonth"));
		Integer toDay = Integer.parseInt(request.getParameter("endDay"));
		
		String gameNo = request.getParameter("gameNo");
		String businessNo = request.getParameter("businessNo");
		String channelNo = request.getParameter("channelNo");
		String apkNo = request.getParameter("apkNo");
		
		Calendar cal = Calendar.getInstance();
		cal.set(fromYear, fromMonth - 1, fromDay);
		Date fromDate = cal.getTime();
		cal.set(toYear, toMonth - 1, toDay);
		Date toDate = cal.getTime();
		
		String fromDateStr = DateUtil.getDate(fromDate);
		String toDateStr = DateUtil.getDate(toDate);
		
		FeeDataDao dao = new FeeDataDao();
		
		List<FeeData> list = dao.getFeeDateList(fromDateStr, toDateStr, businessNo, gameNo, channelNo,apkNo);
		
		JSONObject jo = new JSONObject();
		jo.put("list", list);
		
		out.print(jo);
		out.flush();
		out.close();
	}

}
