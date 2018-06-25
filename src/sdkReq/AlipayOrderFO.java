package sdkReq;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AliPay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import dao.PayDao;

public class AlipayOrderFO extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6478257179450016854L;
	public void HandleReq(String reqStr, HttpServletResponse response)
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
			AliPay alipay = new AliPay();
			alipay.setPayNo(obj.getPayNo());
			alipay.setGoodsName(obj.getCoinName());
			alipay.setGoodsDetails("游戏币");
			alipay.setPrice(obj.getAmount());//
			String url =ConstValue.ServerUrl+ConstValue.path+"/notify_url.jsp";
			if(ConstValue.RDR == 1)
			{
				url = ConstValue.RDRServer+ConstValue.path+"/notify_url.jsp";
			}
			else
			{
				url = ConstValue.ServerUrl+ConstValue.path+"/notify_url.jsp";
			}
			//DebuUtil.log("url="+url);
			String oder_info = alipay.GetOrderString(url,obj.getRatio());
			//DebuUtil.log("回应数据"+oder_info);
			DebuUtil.log("oder_info="+oder_info);
			out.print(oder_info);
			
		}
		else
		{
			out.print("");
		}
		out.close();
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("AlipayOrder doGet");
		HandleReq(request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("AlipayOrder doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
	
		//if(ConstValue.RDR == 1)
		{
		    str = URLDecoder.decode(str,"UTF-8");	
		}
		HandleReq(str,response);
		
	}
}
