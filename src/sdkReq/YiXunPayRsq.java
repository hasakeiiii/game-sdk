package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.YiXunPay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.StringUtil;
import dao.PayDao;
import dao.YiXunPayDao;

public class YiXunPayRsq extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460305344278405373L;
	
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
		
		model.Pay pay = null;
		JSONObject json = null;
		if(reqStr != null)
		{
			DebuUtil.log2("易迅同步数据"+reqStr);
			json = JSONObject.fromObject(reqStr);
			YiXunPay yixun = new YiXunPay(json);
			YiXunPayDao yixunDao = new YiXunPayDao();
			int addret = yixunDao.pay(yixun);
			if(addret == ConstValue.OK)
			{
				if(yixun.getStatus().equals("200"))
				{
					PayDao paydao = new PayDao();
					if(!StringUtil.is_nullString(yixun.getOrderId()))
					{
						pay = paydao.getPayRecordByCpOrder(yixun.getOrderId());
					    if(pay != null)
					    {
					    	DebuUtil.log("付费成功处理");	
					    	if(pay.amount.equals(yixun.getAmount()))
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
				}
			}
			
		}
		
		
		if(pay != null)
		{			
			JSONObject rsqjson = new JSONObject();
			rsqjson.put("resultCode", "200");
			out.print(rsqjson.toString());
		}
		
		out.close();
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("yixunpayRsq doGet");
		HandleReq(request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("yixunpayRsq doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		
		if(ConstValue.RDR == 1)
		{
		    //str = URLDecoder.decode(str,"UTF-8");	
		}
		
		HandleReq(str,response);
		
	}
}
