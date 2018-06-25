package sdkReq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ConstValue;
import util.DebuUtil;
import model.WebGame;
import net.sf.json.JSONObject;
import dao.PayDao;
import dao.WebGameDao;

public class WebGameRsq extends HttpServlet{


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
		
		WebGame webGame = new WebGame();
		
		webGame.orderid = request.getParameter("orderid");
		webGame.partner = request.getParameter("partner");
		webGame.subcid = request.getParameter("subcid");
		webGame.mobileNum = request.getParameter("number");
		webGame.consume = request.getParameter("consume");
		webGame.amount = Integer.parseInt(request.getParameter("price"));
		webGame.saveTime = request.getParameter("savetime");
		webGame.exdata = request.getParameter("exdata");
		WebGameDao webDao =new WebGameDao();
		int addret = webDao.pay(webGame);
		if(addret == ConstValue.OK){
			
			PayDao paydao = new PayDao();
			pay = paydao.getPayRecordByCpOrder(webGame.getExdata());
			
			if(pay != null)
		    {
		    	DebuUtil.log("页游付费成功处理");	
		    	if(pay.amount.equals(webGame.amount))
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
		
			
		out.print("ok");
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
