package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CardPay;
import model.Pay;
import model.Woplus;
import net.sf.json.JSONObject;

import com.yeepay.nonbankcard.NonBankcardPaymentResult;

import dao.CardPayDao;
import dao.PayDao;
import dao.WoplusDao;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.JsonUtil;
import util.StringUtil;

public class WoplusRsq extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460305344278405373L;
	
	public void HandleReq(HttpServletRequest request, HttpServletResponse response)
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
		JSONObject json = null;
		if(request != null)
		{
			//DebuUtil.log2("wo同步数据"+reqStr);
			//json = JSONObject.fromObject(reqStr);
			Woplus woplus = new Woplus();
			woplus.status = request.getParameter("status");
			woplus.order_id = request.getParameter("orderid");
			woplus.timestamp = request.getParameter("timestamp");
			woplus.msg = request.getParameter("msg");
			WoplusDao woplusdao = new WoplusDao();
			int addret = woplusdao.pay(woplus);
			if(addret == ConstValue.OK)
			{
				if(woplus.getStatus().equals(Woplus.SUCCESS))
				{
					PayDao paydao = new PayDao();
					DebuUtil.log("支付订单号="+woplus.getOrderId());
					if(!StringUtil.is_nullString(woplus.getOrderId()))
					{
					    obj = paydao.getPayRecordByCpOrder(woplus.getOrderId());
					    if(obj != null)
					    {
					    	DebuUtil.log("付费成功处理");
						    obj.state = 1;
						    paydao.edit(obj);
						    paydao.payNotisfy(obj,true);
					    }
					}
				}
			}
			
		}
		
		
		if(obj != null)
		{
			
			JSONObject rsqjson = new JSONObject();
			rsqjson.put("retcode", Woplus.SUCCESS);
			rsqjson.put("retmsg", "SUCCESS");
			//object.getInt("code"), object.getString("message")
			out.print(rsqjson.toString());
		}
		
		out.close();
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("WopayRsq doGet");
		HandleReq(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("WopayRsq doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		
		if(ConstValue.RDR == 1)
		{
		    //str = URLDecoder.decode(str,"UTF-8");	
		}
		
		HandleReq(request,response);
		
	}
}
