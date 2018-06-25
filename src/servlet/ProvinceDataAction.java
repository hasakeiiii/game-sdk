package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProvinceData;
import net.sf.json.JSONObject;
import util.HighchartsDataHolder;
import util.HighchartsVO;
import util.StringUtil;
import dao.ProvinceDataDao;

/**
 * Servlet implementation class ProvinceDataAction
 */
public class ProvinceDataAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProvinceDataAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int day = Integer.parseInt(request.getParameter("day"));
		
		String gameNo = request.getParameter("gameNo");
		String businessNo = request.getParameter("businessNo");
		String apkNo = request.getParameter("apkNo");
		String channelNo = request.getParameter("channelNo");
		String payType = request.getParameter("payType");
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day); 
		Date date = cal.getTime();
		
		ProvinceDataDao dao = new ProvinceDataDao();
		
		//指定日期下的所有省份金额总和
		int total = dao.getAllFeeByDate(date,gameNo,businessNo,channelNo,apkNo,payType) / 100;
		
		JSONObject jo = new JSONObject();
		if(total != 0){
			
			//获取指定条件下有多少个省份的数据集合
			List<ProvinceData> list = dao.getRecord(date,gameNo,businessNo,channelNo,apkNo,payType);
			
			HighchartsVO hvo = new HighchartsVO();
			HighchartsDataHolder hdh = new HighchartsDataHolder();
			hdh.setName("省份");
			hvo.getSeries().add(hdh);
			for(ProvinceData pd : list){
				//付费占比
				String payProportion = calcPorprotion(pd.getPay() / 100 , total);
				pd.setPayProportion(payProportion);
				String newPayARPUStr = "0.0";
				if(pd.new_acount > 0)
				{
				    newPayARPUStr = String.format("%.2f",pd.new_pay/100.0/pd.new_acount);
				}
				pd.setArpu(newPayARPUStr);
				//上一天的某省份的总收入
				int preDayFee = dao.getPreDayFee(date, pd.getProvince(),gameNo,businessNo,channelNo,apkNo) / 100;
				if(StringUtil.is_nullString(pd.getProvince()))
					pd.setProvince("未知省份");
				//向highchartsVo中添加省份名称数据
				hvo.getCategories().add(pd.getProvince());
				
				String floatProportion = "";
				if(preDayFee == 0){
					floatProportion = "divideByZero";
				}else{
					int todayFee = pd.getPay() / 100;
					//浮动百分比
					try {
						floatProportion = calcPorprotion((todayFee - preDayFee),preDayFee);
					} catch (Exception e) {
						floatProportion = "divideByZero";
					}
					
				}
				
				pd.setFloatProportion(floatProportion);
				
				hdh.getData().add(Double.parseDouble(payProportion));
				jo.put("vo", hvo);
				jo.put("info", "success");
				jo.put("list", list);
			}
		}else{
			jo.put("info", "noData");
		}

		out.print(jo.toString());
		out.flush();
		out.close();
	}
	
	
	private String calcPorprotion(int i1,int i2){
		DecimalFormat format = new DecimalFormat("0.00");
		return format.format(i1 * 100.0 / i2);
	}
	
}
