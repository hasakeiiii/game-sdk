package servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DateUtil;
import util.ExcelUtil;
import dao.ChannelDataDao;

public class ChannelQueryExcelAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ChannelQueryExcelAction() {
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
		//PrintWriter out = response.getWriter();
		String op = request.getParameter("op");
		String gameNo = request.getParameter("gameNo");
		String channelNo = request.getParameter("channelNo");
		String businessNo = request.getParameter("businessNo");
		String apkNo = request.getParameter("apkNo");
		Integer year = Integer.parseInt(request.getParameter("year"));
		Integer month = Integer.parseInt(request.getParameter("month"));
//		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
//		Integer curPage = Integer.parseInt(request.getParameter("curPage"));
		String dayStr = request.getParameter("day");
		Integer day = "请选择".equals(dayStr) ? -1 : Integer.parseInt(dayStr); 
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		Date beginDate = null;
		Date endDate = null;
/*		Cooperation coo = new Cooperation();
		CooperationDao cooDao = new CooperationDao();
		coo = (Cooperation) cooDao.getApkNo(gameNo, channelNo, businessNo);
		apkNo = coo.getPacketId();*/

		
		ChannelDataDao dao = new ChannelDataDao();
		List<ArrayList> list;
		Map<String,Object> rawMap = null;
		
			beginDate = cal.getTime();
			rawMap = dao.getChaQueryList(gameNo,businessNo,apkNo,beginDate,1,30);
			list = (List<ArrayList>) rawMap.get("list");
			HSSFWorkbook wb = ExcelUtil.channelQueryXLS(0, list);
			
			
			//FileOutputStream fileOut;
			//String path = "E:\\";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String filename = "qdsj".concat(sdf.format(new Date())).concat(".xls");
		/*	try {
				String target = path.concat(filename);
				fileOut = new FileOutputStream(target);
				wb.write(fileOut);
				fileOut.close();

			} catch (Exception e) {
				e.printStackTrace();
			}*/

			//response.setContentType("text/html;charset=utf-8");
			response.setHeader("Content-disposition", "attachment; filename="+filename);
			response.setContentType("octets/stream"); 
			//response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
			OutputStream out = response.getOutputStream();
			wb.write(out);
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
