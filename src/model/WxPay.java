package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import util.ConstValue;

import com.ipaynow.util.PreSignMessageUtil;
import com.ipaynow.utils.MD5;

public class WxPay implements Serializable{

	private static final long serialVersionUID = 7511645625498205072L;
	
	private Integer id;
	private String pay_no;
	private String wx_pay_no;
	private String goods_name;
	private String goods_details;
	private Integer ret;
	private Integer price;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPayNo() {
		return pay_no;
	}
	public void setPayNo(String pay_no) {
		this.pay_no = pay_no;
	}
	public String getWxPayNo() {
		return wx_pay_no;
	}
	public void setWxPayNo(String wx_pay_no) {
		this.wx_pay_no = wx_pay_no;
	}
	public String getGoodsName() {
		return goods_name;
	}
	public void setGoodsName(String goods_name) {
		this.goods_name = goods_name;
	}
	public String getGoodsDetails() {
		return goods_details;
	}
	public void setGoodsDetails(String goods_details) {
		this.goods_details = goods_details;
	}
	public Integer getRet() {
		return ret;
	}
	public void setRet(Integer ret) {
		this.ret = ret;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
	public String getWxpayMobileOrderString(){
		
		String appId = "1440128870435711";
		String mhtOrderNo = this.getPayNo();
		String mhtOrderType = "01";
		String mhtCurrencyType = "156";
		//支付金额
		String mhtOrderAmt;
		String mhtOrderDetail;
		String notifyUrl = "";
		String mhtOrderName;
		if (ConstValue.DEBUG == 1) {
			mhtOrderAmt = this.getPrice() + "";
			mhtOrderDetail = "支付测试";
			mhtOrderName = "拇指币" + this.getPrice() / 100 + "元测试";
			notifyUrl = ConstValue.DEBUG_SERVER_URL + "/sdk/nowpay/notify";
		}else{
			mhtOrderAmt = this.getPrice() + "";
			mhtOrderName = this.getPrice() / 100 + "元拇指币充值";
			mhtOrderDetail = mhtOrderName;
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
		
		try { 
			signature = MD5.md5(preSignStr + "&" + MD5.md5(appkey,"utf-8"), "utf-8"); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
		
		return preSignStr + "&mhtSignature=" + signature + "&mhtSignType=MD5";
	}
}
