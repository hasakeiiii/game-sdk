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
import model.Open189;
import model.Pay;
import model.Woplus;
import net.sf.json.JSONObject;

import com.yeepay.nonbankcard.NonBankcardPaymentResult;

import dao.CardPayDao;
import dao.Open189Dao;
import dao.PayDao;
import dao.WoplusDao;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.JsonUtil;
import util.StringUtil;

public class Open189Rsq extends HttpServlet{

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
			DebuUtil.log2("open同步数据"+reqStr);
			json = JSONObject.fromObject(reqStr);
			Open189 open189 = new Open189(json);
			Open189Dao open189dao = new Open189Dao();
			int addret = open189dao.pay(open189);
			if(addret == ConstValue.OK)
			{
				if(open189.getStatus().equals(open189.SUCCESS)||open189.getStatus().equals("3"))
				{
					PayDao paydao = new PayDao();
					DebuUtil.log("支付订单号="+open189.getOutTradeNo());
					if(!StringUtil.is_nullString(open189.getOutTradeNo()))
					{
						pay = paydao.getPayRecordByCpOrder(open189.getOutTradeNo());
					    if(pay != null)
					    {
					    	DebuUtil.log("付费成功处理");	
					    	pay.amount += open189.fee;
					    	DebuUtil.log("amount="+pay.amount+"order_amount="+pay.order_amount);	
					    	if(pay.amount.equals(pay.order_amount))
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
			out.print("SUCCESS");
		}
		
		out.close();
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("openpayRsq doGet");
		HandleReq(request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("openpayRsq doPost");
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
