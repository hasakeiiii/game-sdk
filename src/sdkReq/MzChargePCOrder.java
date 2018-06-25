package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AliPay;
import model.CardPay;
import model.Mzcharge;
import model.Pay;
import model.TenPay;
import model.UnionPay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;

import com.alipay.config.AlipayConfig;
import com.alipay.config.AlipayPcConfig;
import com.alipay.util.AlipayCore;
import com.alipay.util.AlipaySubmit;
import com.ipaynow.utils.NowPayUtils;
import com.ipaynow.utils.PayRequest;
import com.yeepay.nonbankcard.NonBankcardPaymentResult;

import dao.CardPayDao;
import dao.MzchargeDao;

public class MzChargePCOrder extends HttpServlet {

	// public HttpServletRequest mrequest1;
	// public HttpServletResponse mresponse;

	public MzChargePCOrder() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// mrequest = request;
		// mresponse = response;
		DebuUtil.log("MzAlipayOrder doGet");
		HandleReq(request.getQueryString(), response, request);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("MzAlipayOrder doPost");
		InputStream in = request.getInputStream();
		byte[] buf = FileUtil.getByteArrayFromInputstream(in, -1);

		String str = new String(buf, "UTF-8");

		HandleReq(str, response, request);

	}

	public void init() throws ServletException {
		// Put your code here
	}

	private static final long serialVersionUID = 6478257179450016854L;

	public void HandleReq(String reqStr, HttpServletResponse response,
			HttpServletRequest request) throws UnsupportedEncodingException {
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		String chargeway = "";
		String oder_info = "";

		response.setContentType("text/html;charset=utf8");
		DebuUtil.log("MzPCOrder收到数据：" + reqStr);
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Mzcharge mzobj = null;
		JSONObject json = null;
		if (reqStr != null) {
			json = JSONObject.fromObject(reqStr);
			mzobj = new Mzcharge();
			mzobj.initByPc(json);
			MzchargeDao mzDao = new MzchargeDao();
			ret = mzDao.addChargeByPc(mzobj);
			chargeway = mzobj.getChargeWay();
		}

		if (ret == ConstValue.OK) {
			if (chargeway != null && chargeway.length() != 0) {
				if (chargeway.equals(Pay.AliPayType)) {
					oder_info = aliPcInfo(mzobj);
				}else if (chargeway.equals(Pay.WxPayType)){
					oder_info = wxPcInfo(mzobj);
				}
			}
			//DebuUtil.log("MzAlipayOrder返回oder_info：" + oder_info);
			out.print(oder_info);
		} else {
			out.print("");
		}

		out.close();

	}
	
	private String wxPcInfo(Mzcharge mzobj) {
		
		String appId = "1438832847570566";
		String appKey = "A554N6i5iDISwNJJXIQWXURuuGhFO6E0";
		String mhtOrderNo = mzobj.getPayNo();
		String mhtOrderName = mzobj.getAmount() / 100 + "元拇指币充值";
		String mhtCurrencyType = "156";
		String mhtOrderAmt = mzobj.getAmount() + "";
		//String mhtOrderAmt = "1";
		String mhtOrderDetail = mzobj.getAmount() / 100 + "元拇指币";
		String mhtOrderType = "01";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String mhtOrderStartTime = dateFormat.format(new Date());
		String notifyUrl = ConstValue.ServerUrl + "/sdk/nowpay/notify";
		String frontNotifyUrl = "http://www.91muzhi.com/topup/center";
		String mhtCharset = "UTF-8";
		String deviceType = "02";
		String payChannelType = "";
		String mhtReserved = "";
		String mhtSignType = "MD5";
		
		PayRequest payRequest = new PayRequest();
		payRequest.setAppId(appId);
		payRequest.setMhtOrderNo(mhtOrderNo);
		payRequest.setMhtOrderName(mhtOrderName);
		payRequest.setMhtCurrencyType(mhtCurrencyType);
		payRequest.setMhtOrderAmt(mhtOrderAmt);
		payRequest.setMhtOrderDetail(mhtOrderDetail);
		
		payRequest.setMhtOrderStartTime(mhtOrderStartTime);
		payRequest.setNotifyUrl(notifyUrl);
		payRequest.setFrontNotifyUrl(frontNotifyUrl);
		payRequest.setPayChannelType(payChannelType);
		payRequest.setMhtReserved(mhtReserved);
		
		//构建请求对象，并生成签名
		NowPayUtils.buildPayData(payRequest, appKey);
		
		JSONObject jo = new JSONObject();
		jo.put("appId", appId);
		jo.put("mhtOrderNo", mhtOrderNo);
		jo.put("mhtOrderName", mhtOrderName);
		jo.put("mhtCurrencyType", mhtCurrencyType);
		jo.put("mhtOrderAmt", mhtOrderAmt);
		jo.put("mhtOrderDetail", mhtOrderDetail);
		jo.put("mhtOrderType", mhtOrderType);
		jo.put("mhtOrderStartTime", mhtOrderStartTime);
		jo.put("notifyUrl", notifyUrl);
		jo.put("frontNotifyUrl", frontNotifyUrl);
		jo.put("mhtCharset", mhtCharset);
		jo.put("mhtSignType", mhtSignType);
		jo.put("mhtSignature", payRequest.getMhtSignature());
		jo.put("funcode", payRequest.getFuncode());
		jo.put("deviceType", deviceType);
		jo.put("payChannelType", payChannelType);
		jo.put("mhtReserved", mhtReserved);
		
		return jo.toString();
	}

	private String aliPcInfo(Mzcharge mzobj) throws UnsupportedEncodingException{
		//支付类型
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		//String notify_url = ConstValue.ServerUrl + ConstValue.path + "/alipaypc/notify_url.jsp";
		String notify_url = ConstValue.ServerUrl + "/sdk/alipaypc/notify_url.jsp";
		//需http://格式的完整路径，不能加?id=123这类自定义参数

		//页面跳转同步通知页面路径
		//String return_url = ConstValue.ServerUrl + ConstValue.path + "/alipaypc/return_url.jsp";
		String return_url = "";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		//商户订单号
		String out_trade_no = mzobj.getPayNo();
		//商户网站订单系统中唯一订单号，必填

		//订单名称
		//String subject = URLEncoder.encode(mzobj.getAmount()/100 + "元拇指币充值","utf-8");
		String subject = mzobj.getAmount()/100 + "元拇指币充值";
		//必填

		//付款金额
		String total_fee = mzobj.getAmount()/ 100 + "";
		//必填

		//订单描述
		String body = "";
		//商品展示地址
		String show_url = "";
		//防钓鱼时间戳
		String anti_phishing_key = "";
		//若要使用请调用类文件submit中的query_timestamp函数

		//客户端的IP地址
		String exter_invoke_ip = "";
		//非局域网的外网IP地址，如：221.0.0.1
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_email", AlipayPcConfig.seller_email);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("anti_phishing_key", anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
		
		Map<String, String> sPara = AlipaySubmit.buildRequestPara(sParaTemp);
		
		String link = AlipayCore.createLinkString(sPara);
		
		//JSONObject jo = JSONObject.fromObject(sPara);
		
		return link;
	}

	private String aliInfo(Mzcharge mzobj) {
		String oder_info;
		AliPay alipay = new AliPay();
		alipay.setPayNo(mzobj.getPayNo());
		alipay.setGoodsName("拇指币");
		alipay.setGoodsDetails("游戏币");
		alipay.setPrice(mzobj.getAmount());
		String url = ConstValue.ServerUrl + ConstValue.path + "/notify_url.jsp";
		if (ConstValue.DEBUG == 1) {
			url = ConstValue.ServerUrlDebug + ConstValue.path
					+ "/notify_url.jsp";
		} else {
			url = ConstValue.ServerUrl + ConstValue.path + "/notify_url.jsp";
		}
		oder_info = alipay.GetOrderString(url, mzobj.getRatio());
		return oder_info;
	}

	
}
