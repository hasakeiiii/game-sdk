package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import model.Cooperation;
import model.CooperationRemind;
import net.sf.json.JSONObject;
import util.StringUtil;
import dao.AppDao;
import dao.CooperationDao;
import dao.CooperationRemindDao;
import dao.MmPayDataDao;

public class CooperationRemindAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CooperationRemindAction() {
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
		
		String cmd = request.getParameter("cmd");
		
		if("remind".equals(cmd)){
			handleRemind(request,response);
		}else if("operateRemind".equals(cmd)){
			operateRemind(request,response);
		}
		
		
	}

	private void operateRemind(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		String op = request.getParameter("op");
		
		//如果为null就是-1
		Integer reqNum = StringUtil.is_nullString(request.getParameter("reqNum")) ? -1 : Integer.parseInt(request.getParameter("reqNum"));
		Integer mmPay = StringUtil.is_nullString(request.getParameter("mmPay")) ? -1 : Integer.parseInt(request.getParameter("mmPay"));
		//大于号是1小于号是0 空为-1
		Integer operatorFirst = StringUtil.is_nullString(request.getParameter("operator1")) ? -1 : Integer.parseInt(request.getParameter("operator1"));
		Integer operatorSecond = StringUtil.is_nullString(request.getParameter("operator2")) ? -1 : Integer.parseInt(request.getParameter("operator2"));
		Integer proportion = StringUtil.is_nullString(request.getParameter("proportion")) ? -1 : Integer.parseInt(request.getParameter("proportion"));
		Integer payProportion = StringUtil.is_nullString(request.getParameter("payProportion")) ? -1 : Integer.parseInt(request.getParameter("payProportion"));
		
		
		
		String channelNo = request.getParameter("channelNo");
		String appNo = request.getParameter("appNo");
		String mmChannelNo = request.getParameter("mmChannel");
		String packetId = request.getParameter("packetId");
		
		String mmDayPayProvince = request.getParameter("mmDayPayProvince");
		String province = request.getParameter("province");
		String mmDayChannelProvince = request.getParameter("mmDayChannelProvince");
		String channelProvince = request.getParameter("channelProvince");
		
		String unDayPayProvince = request.getParameter("unDayPayProvince");
		String unProvince = request.getParameter("unProvince");
		String unDayChannelProvince = request.getParameter("unDayChannelProvince");
		String unChannelProvince = request.getParameter("unChannelProvince");
		
		String telDayPayProvince = request.getParameter("telDayPayProvince");
		String telProvince = request.getParameter("telProvince");
		String telDayChannelProvince = request.getParameter("telDayChannelProvince");
		String telChannelProvince = request.getParameter("telChannelProvince");
		
		String mmAddr = request.getParameter("mmAddr");
		String unAddr = request.getParameter("unAddr");
		String telAddr = request.getParameter("telAddr");
		String mmAddrTime = request.getParameter("mmAddrTime");
		String unAddrTime = request.getParameter("unAddrTime");
		String telAddrTime = request.getParameter("telAddrTime");
		
		CooperationRemind remind = new CooperationRemind();
		
		remind.setReqNum(reqNum);
		remind.setAppNo(appNo);
		remind.setMmPay(mmPay);
		remind.setOperatorFirst(operatorFirst);
		remind.setOperatorSecond(operatorSecond);
		remind.setProportion(proportion);
		remind.setPayProportion(payProportion);
		remind.setChannelNo(channelNo);
		remind.setPacketId(packetId);
		remind.setMmChannelNo(mmChannelNo);
		remind.setMmDayPayProvince(mmDayPayProvince);
		remind.setProvince(province);
		remind.setMmDayChannelProvince(mmDayChannelProvince);
		remind.setChannelProvince(channelProvince);
		remind.setUnChannelProvince(unChannelProvince);
		remind.setUnDayChannelProvince(unDayChannelProvince);
		remind.setUnDayPayProvince(unDayPayProvince);
		remind.setUnProvince(unProvince);
		remind.setTelChannelProvince(telChannelProvince);
		remind.setTelDayChannelProvince(telDayChannelProvince);
		remind.setTelDayPayProvince(telDayPayProvince);
		remind.setTelProvince(telProvince);
		remind.setMmAddr(mmAddr);
		remind.setUnAddr(unAddr);
		remind.setTelAddr(telAddr);
		remind.setMmAddrTime(mmAddrTime);
		remind.setUnAddrTime(unAddrTime);
		remind.setTelAddrTime(telAddrTime);
		CooperationRemindDao dao = new CooperationRemindDao();
		
		
		JSONObject jo = new JSONObject();
		if("editRemind".equals(op)){
			CooperationRemind temp = dao.getRecord(packetId);
			remind.setId(temp.getId());
			dao.edit(remind);
			jo.put("info", "editOk");
		}else if ("saveRemind".equals(op)){
			dao.addCooperationRemind(remind);
			jo.put("info", "addOk");
		}
		
		PrintWriter out = response.getWriter();
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
	
	private void handleRemind(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		Integer cooperationId = Integer.parseInt(request.getParameter("cooperationId"));
		String appName = new String(request.getParameter("appName").getBytes("iso-8859-1"),"utf-8");
		String channelName = new String(request.getParameter("channelName").getBytes("iso-8859-1"),"utf-8");
		CooperationRemindDao cooperationRemindDao = new CooperationRemindDao();
		
		
		CooperationDao coDao = new CooperationDao();
		Cooperation co = (Cooperation)coDao.getById(cooperationId);
		String packetId = co.getPacketId();

		//判断这个报警信息是否已经存在
		boolean flag = cooperationRemindDao.remindIsExists(packetId);
		//如果存在则
		if(flag){
			CooperationRemind cooRemind = (CooperationRemind)cooperationRemindDao.getRecord(packetId);
			request.setAttribute("cooRemind", cooRemind);
		}
		
		AppDao appDao = new AppDao();
		App app = appDao.getAppRecord(co.getAppNo());
		
		String company = app.getMmCompany();
		
		switch(company){
			case "mzhy" : company = "拇指互娱"; break;
			case "zty" : company = "中泰源"; break;
			case "mzyw" : company = "拇指游玩"; break;
			default : company = "无公司";
		}
		
		MmPayDataDao mmPayDataDao = new MmPayDataDao();
		List<ArrayList> rawList = mmPayDataDao.getDataByAppName(appName);
		
		
		StringBuilder sb = new StringBuilder("");
		
		for(int i = 0,size = rawList.size();i < size;i++){
			ArrayList list = rawList.get(i);
			String str = (String)list.get(0);
			sb.append(str + (i == (size - 1) ?  "" : ","));
		}
		
	
		
		request.setAttribute("appName", appName);
		request.setAttribute("channelName", channelName);
		request.setAttribute("company", company);
		request.setAttribute("co", co);
		request.setAttribute("mmChannel", sb.toString());
		

		request.getRequestDispatcher("../param/cfgCooperationRemind.jsp").forward(request, response);
	}
}
