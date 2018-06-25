package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.nonbankcard.NonBankcardPaymentResult;

import model.AliPay;
import model.CardPay;
import model.Mzcharge;
import model.Pay;
import model.TenPay;
import model.UnionPay;
import model.WxPay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import dao.CardPayDao;
import dao.MzchargeDao;
import dao.PayDao;

public class MzChargeOrder extends HttpServlet {

	// public HttpServletRequest mrequest1;
	// public HttpServletResponse mresponse;

	public MzChargeOrder() {
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
			HttpServletRequest request) {
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		String chargeway = "";
		String oder_info = "";

		response.setContentType("text/html;charset=utf8");
		DebuUtil.log("MzAlipayOrder收到数据：" + reqStr);
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Mzcharge mzobj = null;
		JSONObject json = null;
		if (reqStr != null) {
			json = JSONObject.fromObject(reqStr);
			mzobj = new Mzcharge(json);
			MzchargeDao mzDao = new MzchargeDao();
			ret = mzDao.addCharge(mzobj);
			chargeway = mzobj.getChargeWay();
		}

		if (ret == ConstValue.OK) {
			if (chargeway != null && chargeway.length() != 0) {
				if (chargeway.equals(Pay.AliPayType)) {
					oder_info = aliInfo(mzobj);
				} else if (chargeway.equals(Pay.TenPayType)) {
					oder_info = tenInfo(request, response, mzobj);
				} else if (chargeway.equals(Pay.UnionPayType)) {
					oder_info = unionInfo(mzobj);
				} else if (chargeway.equals("china_mobile")
						|| chargeway.equals("china_telecom")
						|| chargeway.equals("china_unicom")
						|| chargeway.equals("jcard")
						|| chargeway.equals("zycard")
						|| chargeway.equals("tscard")) {
					oder_info = yeeInfo(mzobj, json);
					DebuUtil.log2("MzAlipayOrder返回oder_info："+"  ");
				} else if (chargeway.equals(Pay.WxPayType)){
					oder_info = wxMobileInfo(mzobj);
				}
			}

			//DebuUtil.log("MzAlipayOrder返回oder_info：" + oder_info);
			out.print(oder_info);
		} else {
			out.print("");
		}

		out.close();

	}

	private String wxMobileInfo(Mzcharge mzobj) {
		String order_info;
		
		WxPay wxpay = new WxPay();
		wxpay.setGoodsName("拇指币");
		wxpay.setGoodsDetails("游戏币");
		wxpay.setPrice(mzobj.getAmount());
		wxpay.setPayNo(mzobj.getPayNo());
		
		order_info = wxpay.getWxpayMobileOrderString();
		
		return order_info;
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

	private String tenInfo(HttpServletRequest request,
			HttpServletResponse response, Mzcharge mzobj) {
		TenPay tenpay = new TenPay();
		tenpay.setPayNo(mzobj.getPayNo());
		/*
		 * byte[] bt = new byte[1]; String ss = ""; try { bt =
		 * "拇指币".getBytes("utf-8"); ss = new String(bt,"utf-8"); } catch
		 * (UnsupportedEncodingException e) { 
		 * e.printStackTrace(); }
		 */
		tenpay.setGoodsName("拇指币");
		tenpay.setGoodsDetails("游戏币");
		tenpay.setPrice(mzobj.getAmount());//

		String nosify_url = ConstValue.ServerUrl + ConstValue.path
				+ "/tennotify_url.jsp";
		String return_url = ConstValue.ServerUrl + ConstValue.path
				+ "/tenpayReturnUrl.jsp";

		String oder_info = tenpay.GetOrderString(request, response, nosify_url,
				return_url, mzobj.getRatio());
		DebuUtil.log("回应数据" + oder_info);
		DebuUtil.log("oder_info=" + oder_info);
		JSONObject rsqjson = new JSONObject();
		rsqjson.put("order_info", oder_info);

		return rsqjson.toString();
	}

	private String unionInfo(Mzcharge mzobj) {

		UnionPay unionpay = new UnionPay();
		unionpay.setPayNo(mzobj.getPayNo());
		unionpay.setGoodsName("拇指币");
		unionpay.setGoodsDetails("游戏币");
		unionpay.setPrice(mzobj.getAmount());

		String oder_info = unionpay.getTNNO(mzobj.getRatio());
		MzchargeDao chargeDao = new MzchargeDao();
		Mzcharge charge = chargeDao.getPayRecord(unionpay.getPayNo());
		if (charge != null) {
			charge.setExt(unionpay.goods_details);
			chargeDao.edit(charge);
		}
		DebuUtil.log("回应数据" + oder_info);
		DebuUtil.log("oder_info=" + oder_info);
		JSONObject rsqjson = new JSONObject();
		rsqjson.put("order_info", oder_info);
		return rsqjson.toString();
	}

	private String yeeInfo(Mzcharge mzobj, JSONObject json) {
		int bOK = 0;
		JSONObject rsqjson = new JSONObject();

		if (mzobj.getGameId().equals("166") || mzobj.getGameId().equals("188")) {

			rsqjson.put("code", -1);
			rsqjson.put("message", "该游戏不支持充值卡");
			rsqjson.put("pay_no", "");

		} else {
			CardPay cardPay = new CardPay(json);
			cardPay.username = mzobj.getUsername();
			cardPay.pay_no = cardPay.oder_no = mzobj.getPayNo();
			cardPay.goods_details = "游戏币";
			cardPay.goods_name = "拇指币";

			cardPay.url = ConstValue.ServerUrl + "/sdk/yeepay/callback.jsp";

			if (ConstValue.RDR == 1) {
				cardPay.url = ConstValue.RDRServer + ConstValue.path
						+ "/yeepay/callback.jsp";
			} else {
				cardPay.url = ConstValue.ServerUrl + ConstValue.path
						+ "/yeepay/callback.jsp";
			}
			DebuUtil.log("url=" + cardPay.url);
			bOK = 1;
			NonBankcardPaymentResult rs = cardPay.pay();
			if (rs != null) {
				// DebuUtil.log("回应数据"+oder_info);
				DebuUtil.log("提交返回参数列表");
				DebuUtil.log("业务类型[r0_Cmd：" + rs.getR0_Cmd());
				DebuUtil.log("订单的提交状态[r1_Code：" + rs.getR1_Code());
				DebuUtil.log("交易订单号[r6_Order：" + rs.getR6_Order());
				DebuUtil.log("订单的提交状态[rq_ReturnMsg：" + rs.getRq_ReturnMsg());

				if (rs.getR1_Code().equals("1")) {
					DebuUtil.log("卡状态等于1");
					cardPay.setRet(1);
				} else {
					cardPay.setRet(-1);
				}

				DebuUtil.log("保存卡记录");
				CardPayDao cardPayDao = new CardPayDao();
				cardPayDao.addCardPay(cardPay);

				DebuUtil.log("修改付费表ID");
				MzchargeDao chargedao = new MzchargeDao();
				Mzcharge charge = new Mzcharge();
				charge.setPayNo(rs.getR6_Order());
				charge.setThirPayId(cardPay.pay_no);
				DebuUtil.log("卡号=" + cardPay.no);
				chargedao.update(charge);

				rsqjson.put("code", rs.getR1_Code());
				rsqjson.put("pay_no", rs.getR6_Order());
				rsqjson.put("message", rs.getRq_ReturnMsg());
			}
			if (bOK == 0) {
				rsqjson = new JSONObject();
				rsqjson.put("code", -1);
				rsqjson.put("message", "");
				rsqjson.put("pay_no", "");
			}

		}
		return rsqjson.toString();
	}
	
	
}
