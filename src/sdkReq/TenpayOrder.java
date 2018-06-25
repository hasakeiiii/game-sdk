package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AliPay;
import model.TenPay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.NetUtil;
import dao.PayDao;

public class TenpayOrder extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4345387895617971140L;

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
			String ip = NetUtil.getIpAddr(request);
			obj.ip = ip;
			PayDao dao = new PayDao();
			ret = dao.pay(obj);
		}
		if(ret == ConstValue.OK)
		{
			TenPay tenpay = new TenPay();
			tenpay.setPayNo(obj.getPayNo());
			tenpay.setGoodsName(obj.getCoinName());
			tenpay.setGoodsDetails("游戏币");
			tenpay.setPrice(obj.getAmount());//
			
				/////////////////////////////////////
				//ConstValue.ServerUrl = "http://www.91muzhi.com:8080";
				/////////////////////////////////////////
			String nosify_url = ConstValue.ServerUrl+ConstValue.path+"/tennotify_url.jsp";
			String return_url = ConstValue.ServerUrl+ConstValue.path+"/tenpayReturnUrl.jsp";
			
			//DebuUtil.log("url="+url);
			String oder_info = tenpay.GetOrderString(request,response,nosify_url,return_url,obj.getRatio());
			//DebuUtil.log("回应数据"+oder_info);
			DebuUtil.log("oder_info="+oder_info);
			JSONObject rsqjson = new JSONObject();  
			rsqjson.put("order_info", oder_info);
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
		DebuUtil.log("TenpayOrder doGet");
		HandleReq(request.getQueryString(),request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("TenpayOrder doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
			
		HandleReq(str,request,response);
		
	}
	
}
