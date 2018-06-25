package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Pay;
import model.UnionPay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import dao.PayDao;

public class TCpayOrder extends HttpServlet{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7884377512091954916L;

	public void HandleReq(String reqStr,HttpServletRequest request, HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.Pay obj = null;
		if(reqStr != null)
		{
			DebuUtil.log("请求数据"+reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			obj = new model.Pay(json);
			PayDao dao = new PayDao();
			ret = dao.pay(obj);
		}
		if(ret == ConstValue.OK)
		{
			int price = obj.getAmount()/100;
			JSONObject rsqjson = new JSONObject();  
			rsqjson.put("pay_no", obj.getPayNo());
			rsqjson.put("price", String.format("%d", price));
			rsqjson.put("name",String.format("%d%s", price*obj.ratio,obj.getCoinName()));
			out.print(rsqjson.toString());
			
		}
		else
		{
			out.print("");
		}
		out.close();
		
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("TCpayOrder doGet");
		HandleReq(request.getQueryString(),request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("TCpayOrder doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
			
		HandleReq(str,request,response);
		
	}

}
