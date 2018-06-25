package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;

import com.ipaynow.util.PreSignMessageUtil;
import com.ipaynow.utils.MD5;

import dao.PayDao;

public class WxMobilePayOrder extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4345387895617971140L;

	public void HandleReq(String reqStr,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		
		response.setContentType("text/html;charset=utf8");
		
		System.out.println("app  微信支付");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		model.Pay obj = null;
		if(reqStr != null) {
			DebuUtil.log("请求数据"+reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			obj = new model.Pay(json);
			PayDao dao = new PayDao();
			ret = dao.pay(obj);
		}
		if (ret == ConstValue.OK) {
			String appId = "1440128870435711";
			String mhtOrderNo = obj.getPayNo();
			String mhtOrderType = "01";
			String mhtCurrencyType = "156";
			//支付金额
			String mhtOrderAmt;
			String mhtOrderName;
			String mhtOrderDetail;
			String notifyUrl = "";
			if (ConstValue.DEBUG == 1) {
				mhtOrderAmt = obj.getAmount() + "";
				mhtOrderName = "支付测试";
				mhtOrderDetail = "支付测试";
				notifyUrl = ConstValue.DEBUG_SERVER_URL + "/sdk/nowpay/notify";
			}else{
				mhtOrderAmt = obj.getAmount() + "";
				mhtOrderDetail = obj.getAmount() / 100 + "元拇指币充值";
				mhtOrderName = mhtOrderDetail;
				notifyUrl = ConstValue.ServerUrl + "/sdk/nowpay/notify";
			}
			
			String mhtOrderTimeOut = "3600";
			String mhtOrderStartTime = new SimpleDateFormat("yyyyMMddHHmmss",Locale.CHINA).format(new Date());
			String payChannelType = "13";
			String mhtCharset = "UTF-8";
			String consumerId = "";
			String consumerName = "";
			String mhtReserved = "";
			
			
			PreSignMessageUtil psm = new PreSignMessageUtil();
			
			psm.appId = appId;
			psm.mhtCharset = mhtCharset;
			psm.mhtCurrencyType = mhtCurrencyType;
			psm.mhtOrderAmt = mhtOrderAmt;
			psm.mhtOrderDetail = mhtOrderDetail;
			psm.mhtOrderName = mhtOrderName;
			psm.mhtOrderNo = mhtOrderNo;
			psm.mhtOrderStartTime = mhtOrderStartTime;
			psm.mhtOrderTimeOut = mhtOrderTimeOut;
			psm.mhtOrderType = mhtOrderType;
			psm.notifyUrl = notifyUrl;
			psm.payChannelType = payChannelType;
			psm.consumerId = consumerId;
			psm.consumerName = consumerName;
			psm.mhtReserved = mhtReserved;
			
			
			String preSignStr = psm.generatePreSignMessage();
			
			
			String appkey = "qJWYNkMVds5ksBQ37xCyyvldtxITaH9a";
			
			
			String signature = "";
			System.out.println("preSignStr==" + preSignStr);
			try { 
				signature = MD5.md5(preSignStr + "&" + MD5.md5(appkey,"utf-8"), "utf-8"); 
			} catch (Exception e) { 
				e.printStackTrace(); 
			} 
			
			out.print(preSignStr + "&mhtSignature=" + signature + "&mhtSignType=MD5");
			out.flush();
		}
		else {
			out.print("");
		}
		out.close();
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("WxMobilePayOrder doGet");
		HandleReq(request.getQueryString(),request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("WxMobilePayOrder doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		HandleReq(str,request,response);
	}
	
}
