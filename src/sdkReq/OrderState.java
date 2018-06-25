package sdkReq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Pay;
import net.sf.json.JSONObject;
import dao.PayDao;

public class OrderState extends HttpServlet{


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
		String order = "";
		String state ="";
		String amount = "";
		order = request.getParameter("order");
			
			PayDao payDao = new PayDao();
			Pay pay = new Pay();
			 pay = payDao.getPayRecordByCpOrder(order); 
			 if(pay != null){
			 state = String.valueOf(pay.getState());
			 amount = String.valueOf(pay.getAmount());
			 }
			

		JSONObject rsqjson = new JSONObject();
		if(state.equals("1")){
			rsqjson.put("ret", "1"); 
			rsqjson.put("fee", amount); 
		}
		if(state.equals("-1")){
			rsqjson.put("ret", "0"); 
			rsqjson.put("fee", amount); 
		}
		out.print(rsqjson.toString());
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
