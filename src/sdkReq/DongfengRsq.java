package sdkReq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DongfengPay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import dao.DongfengPayDao;
import dao.PayDao;

public class DongfengRsq extends HttpServlet{


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
		
		DongfengPay dongfengPay = new DongfengPay();
		
//		dongfengPay.orderid = request.getParameter("cpparams");
		dongfengPay.shortcode = request.getParameter("shortcode");
		dongfengPay.msg = request.getParameter("msg");
		dongfengPay.uid = request.getParameter("uid");
		dongfengPay.orderid = request.getParameter("tid");
		float price = Float.parseFloat(request.getParameter("price"));
		dongfengPay.amount = (int) (price*100);
		dongfengPay.optype = request.getParameter("optype");
		dongfengPay.status = request.getParameter("status");
		dongfengPay.dateTime = request.getParameter("timestamp");
		DongfengPayDao dongfengPayDao =new DongfengPayDao();
		int addret = dongfengPayDao.pay(dongfengPay);
		if(addret == ConstValue.OK&&dongfengPay.status.equals("1000")){
			
			PayDao paydao = new PayDao();
			pay = paydao.getPayRecordByCpOrder(dongfengPay.getOrderid());
			
			if(pay != null)
		    {
		    	DebuUtil.log("动力付费成功处理");	
		    	if(pay.amount.equals(dongfengPay.amount))
		    	{
		    	   pay.state = 1;
		    	}
			    paydao.edit(pay);
			    if(pay.state == 1)
			    {
			    	paydao.payNotisfy(pay,true);
			    }
		    }
			System.out.println("添加东丰计费成功");
		}

		//JSONObject rsqjson = new JSONObject();
		
			
		out.print("1");
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
