package sdkReq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PowerPay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import dao.PayDao;
import dao.PowerPayDao;

public class PowerRsq extends HttpServlet{


	private static final long serialVersionUID = -7749265620086362124L;
	/**
	 * 
	 */
	public void HandleReq(HttpServletRequest request,HttpServletResponse response)
	{
		PrintWriter out = null;
		
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.Pay pay = null;
		JSONObject json = null;
		
		PowerPay powerPay = new PowerPay();
		
		powerPay.orderid = request.getParameter("cpparams");
		powerPay.channelNum = request.getParameter("channelNum");
		powerPay.appId = request.getParameter("appId");
		powerPay.subChannelNum = request.getParameter("subChannelNum");
		powerPay.imei = request.getParameter("imei");
		powerPay.amount = Integer.parseInt(request.getParameter("price"));
		powerPay.provider = request.getParameter("provider");
		powerPay.province = request.getParameter("province");
		powerPay.mobile = request.getParameter("mobile");
		powerPay.number = request.getParameter("number");
		powerPay.dateTime = DebuUtil.getDateTime();
		String sign = request.getParameter("sign");

		PowerPayDao powerPayDao =new PowerPayDao();
		int addret = powerPayDao.pay(powerPay);
		if(addret == ConstValue.OK){
			
			PayDao paydao = new PayDao();
			pay = paydao.getPayRecordByCpOrder(powerPay.getOrderid());
			
			if(pay != null)
		    {
		    	DebuUtil.log("动力付费成功处理");	
		    	if(pay.amount.equals(powerPay.amount))
		    	{
		    	   pay.state = 1;
		    	}
			    paydao.edit(pay);
			    if(pay.state == 1)
			    {
			    	paydao.payNotisfy(pay,true);
			    }
		    }
		}

		//JSONObject rsqjson = new JSONObject();
		
			
		out.print("0||");
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
