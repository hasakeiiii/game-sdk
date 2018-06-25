package model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tenpay.RequestHandler;
import com.tenpay.client.TenpayHttpClient;
import com.tenpay.client.XMLClientResponseHandler;
import com.tenpay.util.TenpayUtil;
import com.tenpay.wap.WapPayInitRequestHandler;
import com.tenpay.wap.WapPayRequestHandler;

import net.sf.json.JSONObject;
import util.DebuUtil;
import util.Rsa;

public class TenPay implements java.io.Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6671696936978938999L;

	//合作身份者id，以2088开头的16位纯数字
	//public static final String DEFAULT_PARTNER = "2088211259390455";

	//收款支付宝账号
	//public static final String DEFAULT_SELLER = "ali@ztytech.com.cn";
	public static final String partner = "1219398501";
	//密钥
	public static final String key = "f14d4c61120849a38f9a51366db90449";
	
	//商户私钥，自助生成http://www.91muzhi.com:8080/sdk/tenpay_wap/payRequest.jsp
	//public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALys+oYaxqv4FYju8C1poM6qmHLjWPnXzqEJT0NxyFXgdaK/Qe9DcpcASod9mIAdlLIxJEyYNlWeonAJVYW8pQ+pTVGwI9n0iaT71ldWQzcMN3Dvi/+zpgw3HxxO7HJtEIlR84pvILv1yceCZCqqQ4O/4SemsG00oTiTyD3SM2ZvAgMBAAECgYBLToeX6ywNC7Icu7Hljll+45yBjri+0CJLKFoYw1uA21xYnxoEE9my54zX04uA502oafDhGYfmWLDhIvidrpP6oaluURb/gbV5Bdcm98gGGVgm6lpK+G5N/eawXDjP0ZjxXb114Y/Hn/oVFVM9OqcujFSV+Wg4JgJ4Mmtdr35gYQJBAPbhx030xPcep8/dL5QQMc7ddoOrfxXewKcpDmZJi2ey381X+DhuphQ5gSVBbbunRiDCEcuXFY+R7xrgnP+viWcCQQDDpN8DfqRRl+cUhc0z/TbnSPJkMT/IQoFeFOE7wMBcDIBoQePEDsr56mtc/trIUh/L6evP9bkjLzWJs/kb/i25AkEAtoOf1k/4NUEiipdYjzuRtv8emKT2ZPKytmGx1YjVWKpyrdo1FXMnsJf6k9JVD3/QZnNSuJJPTD506AfZyWS6TQJANdeF2Hxd1GatnaRFGO2y0mvs6U30c7R5zd6JLdyaE7sNC6Q2fppjmeu9qFYq975CKegykYTacqhnX4I8KEwHYQJAby60iHMAYfSUpu//f5LMMRFK2sVif9aqlYbepJcAzJ6zbiSG5E+0xg/MjEj/Blg9rNsqDG4RECGJG2nPR72O8g==";
	//public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8rPqGGsar+BWI7vAtaaDOqphy41j5186hCU9DcchV4HWiv0HvQ3KXAEqHfZiAHZSyMSRMmDZVnqJwCVWFvKUPqU1RsCPZ9Imk+9ZXVkM3DDdw74v/s6YMNx8cTuxybRCJUfOKbyC79cnHgmQqqkODv+EnprBtNKE4k8g90jNmbwIDAQAB";

	public Integer id;
	public String pay_no;
	public String ten_pay_no;
	public String goods_name;
	public String goods_details;
	public Integer ret;
	public Integer price;
	
	public String GetOrderString(HttpServletRequest request, HttpServletResponse response,String notify_url,String return_url,int ratio) 
	{
		String total_fee = String.format("%d", price);
		String subject = "";
		if(ratio > 0)
		{
		    subject = String.format("%d%s",(price*ratio/100) ,goods_name);
		}
		else
		{
			subject = String.format("%s",goods_name);
		}
		String ret = "";
		//帐号(财付通商户号或者财付通帐号)
		String bargainor_id = TenPay.partner;

		//密钥
		String key = TenPay.key;



		//创建支付初始化请求示例
		WapPayInitRequestHandler reqHandler = new WapPayInitRequestHandler(request, response);

		//初始化
		reqHandler.init();
		//设置密钥
		reqHandler.setKey(key);

		//-----------------------------
		//设置请求参数
		//-----------------------------
		//当前时间 yyyyMMddHHmmss
		String currTime = TenpayUtil.getCurrTime();
		//订单号，必须保持唯一。此处 用 时间+4个随机数 模拟 ，商户可自行替换
		String strReq = currTime + TenpayUtil.buildRandom(4);
		reqHandler.setParameter("sp_billno", pay_no);	//strReq	
		reqHandler.setParameter("desc", subject);//"wap测试"
		reqHandler.setParameter("bargainor_id", bargainor_id);					
		reqHandler.setParameter("total_fee", total_fee);
		reqHandler.setParameter("notify_url", notify_url);
		reqHandler.setParameter("callback_url", return_url);
		//获取请求带参数的url
		String requestUrl="";
		try {
			requestUrl = reqHandler.getRequestURL();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//获取debug信息
		String debuginfo = reqHandler.getDebugInfo();
		DebuUtil.log("debuginfo:" + debuginfo);
		DebuUtil.log("requestUrl:" + requestUrl);

		//创建TenpayHttpClient，后台通信
		TenpayHttpClient httpClient = new TenpayHttpClient();

		//设置请求内容
		httpClient.setReqContent(requestUrl);
		//远程调用
		if(httpClient.call()) {
			String resContent = httpClient.getResContent();
			DebuUtil.log("responseContent:" + resContent);
			
			//----------------------
			//应答处理,获取token_id
			//----------------------
			XMLClientResponseHandler resHandler = new XMLClientResponseHandler();
			try {
				resHandler.setContent(resContent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String token_id = resHandler.getParameter("token_id");
			if(!token_id.equals("")) {
				//生成支付请求
				WapPayRequestHandler wapPayRequestHandler = new WapPayRequestHandler(request, response);
				wapPayRequestHandler.init();
				wapPayRequestHandler.setParameter("token_id", token_id);
				String wapPayRequestUrl = "";
				try {
					wapPayRequestUrl = wapPayRequestHandler.getRequestURL();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ret = wapPayRequestUrl;
				DebuUtil.log(ret);
				//out.println("<br /><a href=\"" + wapPayRequestUrl + "\">财付通wap支付</a><br />");
			} else {
				//获取token_id调用失败 ，显示错误 页面
				//out.println("获取token_id调用失败:" + resHandler.getParameter("err_info"));
			}
		} else {
			DebuUtil.log("后台调用失败:" + httpClient.getResponseCode() + httpClient.getErrInfo());
			//后台调用失败 ，显示错误 页面
			//out.println("后台调用失败!");

		}
		return ret;
	}
	//"http://119.147.23.178:8080/sdk/notify_url.jsp"
	public String GetOrderString2(HttpServletRequest request, HttpServletResponse response,String notify_url,String return_url,int ratio)
	{
		String requestUrl = "";
		String currTime = TenpayUtil.getCurrTime();
		String total_fee = String.format("%d", price);
		String subject = String.format("%d%s",(price/100)*ratio ,goods_name);
		
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init();
		//设置密钥
		reqHandler.setKey(key);
		reqHandler.setGateUrl("https://gw.tenpay.com/gateway/pay.htm");
		
		//-----------------------------
		//设置支付参数
		//-----------------------------
		reqHandler.setParameter("partner", partner);		        //商户号
		reqHandler.setParameter("out_trade_no", pay_no);		//商家订单号
		reqHandler.setParameter("total_fee", total_fee);//商品金额,以分为单位
		reqHandler.setParameter("return_url", return_url);		    //交易完成后跳转的URL
		reqHandler.setParameter("notify_url", notify_url);		    //接收财付通通知的URL
		reqHandler.setParameter("body", subject);	                    //商品描述
		reqHandler.setParameter("bank_type", "DEFAULT");		    //银行类型
		reqHandler.setParameter("spbill_create_ip",request.getRemoteAddr());   //用户的公网ip
		reqHandler.setParameter("fee_type", "1");

		//系统可选参数
		reqHandler.setParameter("sign_type", "MD5");
		reqHandler.setParameter("service_version", "1.0");
		reqHandler.setParameter("input_charset", "UTF-8");
		reqHandler.setParameter("sign_key_index", "1");

		//业务可选参数
		reqHandler.setParameter("attach", "");
		reqHandler.setParameter("product_fee", "1");
		reqHandler.setParameter("transport_fee", "0");
		reqHandler.setParameter("time_start", currTime);
		reqHandler.setParameter("time_expire", "");

		reqHandler.setParameter("buyer_id", "");
		reqHandler.setParameter("goods_tag", "");
		
		//请求的url
		try {
			requestUrl = reqHandler.getRequestURL();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//info += "&sign=\"" + sign + "\"&" + getSignType();
		return requestUrl;
		
	}
	
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	
	
	public void setPayNo(String pay_no){
		this.pay_no=pay_no;
	}
	public String getPayNo( ){
		return this.pay_no;
	}
	
	public void setTenPayNo(String ten_pay_no){
		this.ten_pay_no=ten_pay_no;
	}
	public String getTenPayNo( ){
		return this.ten_pay_no;
	}
	
	public void setGoodsName(String goods_name){
		this.goods_name=goods_name;
	}
	public String getGoodsName( ){
		return this.goods_name;
	}	
	
	public void setGoodsDetails(String goods_details){
		this.goods_details=goods_details;
	}
	public String getGoodsDetails( ){
		return this.goods_details;
	}
	
	public void setPrice(Integer price){
		this.price=price;
	}
	public Integer getPrice(){
		return this.price;
	}	
	
	public void setRet(Integer ret){
		this.ret=ret;
	}
	public Integer getRet(){
		return this.ret;
	}		
	
}
