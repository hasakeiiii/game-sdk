package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import model.Cooperation;
import model.CooperationFee;
import model.CooperationRemind;
import net.sf.json.JSONObject;
import dao.AppDao;
import dao.CooperationDao;
import dao.CooperationFeeDao;
import dao.CooperationRemindDao;
import dao.MmPayDataDao;

public class CooperationUnAction extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CooperationUnAction() {
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
		
		String unicom_pay_type = request.getParameter("unicom_pay_type");
		String unicom_pay1_type = request.getParameter("unicom_pay1_type");
		String unicom_pay2_type = request.getParameter("unicom_pay2_type");
		String unicom_pay3_type = request.getParameter("unicom_pay3_type");
		String unicom_pay_id = request.getParameter("unicom_pay_id");
		String unicom_pay1_id = request.getParameter("unicom_pay1_id");
		String unicom_pay2_id = request.getParameter("unicom_pay2_id");
		String unicom_pay3_id = request.getParameter("unicom_pay3_id");
		String unicom_pay1_param = request.getParameter("unicom_pay1_param");
		String unicom_pay2_param = request.getParameter("unicom_pay2_param");
		String unicom_pay3_param = request.getParameter("unicom_pay3_param");
		
		String channelNo = request.getParameter("channelNo");
		String appNo = request.getParameter("appNo");
		String mmChannelNo = request.getParameter("mmChannel");
		String packetId = request.getParameter("packetId");
		
	/*	String mmDayPayProvince = request.getParameter("mmDayPayProvince");
		String province = request.getParameter("province");
		String mmDayChannelProvince = request.getParameter("mmDayChannelProvince");
		String channelProvince = request.getParameter("channelProvince");*/
		
		CooperationFee fee = new CooperationFee();
		
		fee.setAppNo(appNo);
		fee.setChannelNo(channelNo);
		fee.setPacketId(packetId);
		fee.setMmChannelNo(mmChannelNo);
		fee.setUnicomPayType(unicom_pay_type);
		fee.setUnicomPayId(unicom_pay_id);
		fee.setUnicomPay1Type(unicom_pay1_type);
		fee.setUnicomPay2Type(unicom_pay2_type);
		fee.setUnicomPay3Type(unicom_pay3_type);
		fee.setUnicomPay1Id(unicom_pay1_id);
		fee.setUnicomPay2Id(unicom_pay2_id);
		fee.setUnicomPay3Id(unicom_pay3_id);
		fee.setUnicomPay1Param(unicom_pay1_param);
		fee.setUnicomPay2Param(unicom_pay2_param);
		fee.setUnicomPay3Param(unicom_pay3_param);

		CooperationFeeDao dao = new CooperationFeeDao();
		
		
		JSONObject jo = new JSONObject();
		if("editRemind".equals(op)){
			CooperationFee temp = dao.getRecord(packetId);
			fee.setId(temp.getId());
			dao.edit(fee);
			jo.put("info", "editOk");
		}else if ("saveRemind".equals(op)){
			dao.addCooperationFee(fee);
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
		CooperationFeeDao cooperationFeeDao = new CooperationFeeDao();
		
		
		CooperationDao coDao = new CooperationDao();
		Cooperation co = (Cooperation)coDao.getById(cooperationId);
		String packetId = co.getPacketId();

		boolean flag = cooperationFeeDao.remindIsExists(packetId);

		if(flag){
			CooperationFee cooFee = (CooperationFee)cooperationFeeDao.getRecord(packetId);
			request.setAttribute("cooFee", cooFee);
		}
		
		AppDao appDao = new AppDao();
		App app = appDao.getAppRecord(co.getAppNo());
		
		String company = app.getMmCompany();
		
		
		switch(company){
			case "mzhy" : company = "拇指互娱"; break;
			case "zty" : company = "中泰源"; break;
			case "mzyw" : company = "拇指游玩"; break;
			case "jy" : company = "竟游"; break;
			default : company = "无公司";
		}
		
		MmPayDataDao mmPayDataDao = new MmPayDataDao();
		List<ArrayList> rawList = mmPayDataDao.getDataByAppName(appName);
		
		Map<String,String> pay_idMap = mmPayDataDao.getSelectMap1("select pay_id,pay_name from mm_pay_data");
		Map<String,String> pay_TypeMap = new HashMap<String,String>();

		pay_TypeMap.put("13","盒饭");
		pay_TypeMap.put("12","乐动");
		pay_TypeMap.put("6","联通十分科技");
		pay_TypeMap.put( "0","关");
		
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
		request.setAttribute("map", pay_idMap);
		request.setAttribute("paytypemap", pay_TypeMap);
		request.setAttribute("mmChannel", sb.toString());
		
		request.getRequestDispatcher("../param/cfgCooperationUn.jsp").forward(request, response);

	}
}
