package model;

import java.net.URLEncoder;

import com.alipay.config.AlipayConfig;

import net.sf.json.JSONObject;

import util.Rsa;



public class AliPay implements java.io.Serializable{


	//合作身份者id，以2088开头的16位纯数字
	/*public static final String DEFAULT_PARTNER = "2088211259390455";//中泰源//
	public static final String DEFAULT_SELLER = "ali@ztytech.com.cn";*/
	/*public static final String DEFAULT_PARTNER = "2088311744918107";//拇指
	//收款支付宝账号
	public static final String DEFAULT_SELLER = "ali@91muzhi.com";//拇指
	//商户私钥，自助生成
	public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALys+oYaxqv4FYju8C1poM6qmHLjWPnXzqEJT0NxyFXgdaK/Qe9DcpcASod9mIAdlLIxJEyYNlWeonAJVYW8pQ+pTVGwI9n0iaT71ldWQzcMN3Dvi/+zpgw3HxxO7HJtEIlR84pvILv1yceCZCqqQ4O/4SemsG00oTiTyD3SM2ZvAgMBAAECgYBLToeX6ywNC7Icu7Hljll+45yBjri+0CJLKFoYw1uA21xYnxoEE9my54zX04uA502oafDhGYfmWLDhIvidrpP6oaluURb/gbV5Bdcm98gGGVgm6lpK+G5N/eawXDjP0ZjxXb114Y/Hn/oVFVM9OqcujFSV+Wg4JgJ4Mmtdr35gYQJBAPbhx030xPcep8/dL5QQMc7ddoOrfxXewKcpDmZJi2ey381X+DhuphQ5gSVBbbunRiDCEcuXFY+R7xrgnP+viWcCQQDDpN8DfqRRl+cUhc0z/TbnSPJkMT/IQoFeFOE7wMBcDIBoQePEDsr56mtc/trIUh/L6evP9bkjLzWJs/kb/i25AkEAtoOf1k/4NUEiipdYjzuRtv8emKT2ZPKytmGx1YjVWKpyrdo1FXMnsJf6k9JVD3/QZnNSuJJPTD506AfZyWS6TQJANdeF2Hxd1GatnaRFGO2y0mvs6U30c7R5zd6JLdyaE7sNC6Q2fppjmeu9qFYq975CKegykYTacqhnX4I8KEwHYQJAby60iHMAYfSUpu//f5LMMRFK2sVif9aqlYbepJcAzJ6zbiSG5E+0xg/MjEj/Blg9rNsqDG4RECGJG2nPR72O8g==";
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8rPqGGsar+BWI7vAtaaDOqphy41j5186hCU9DcchV4HWiv0HvQ3KXAEqHfZiAHZSyMSRMmDZVnqJwCVWFvKUPqU1RsCPZ9Imk+9ZXVkM3DDdw74v/s6YMNx8cTuxybRCJUfOKbyC79cnHgmQqqkODv+EnprBtNKE4k8g90jNmbwIDAQAB";
*/
	/**
	 * 
	 */
	private static final long serialVersionUID = 3056952546715344159L;
	public Integer id;
	public String pay_no;
	public String ali_pay_no;
	public String goods_name;
	public String goods_details;
	public Integer ret;
	public Integer price;
	//"http://119.147.23.178:8080/sdk/notify_url.jsp"
	public String GetOrderString(String notify_url,int ratio)
	{
		String info = getNewOrderInfo(notify_url,ratio);
		String sign = Rsa.sign(info,AlipayConfig.private_key);
		//sign = URLEncoder.encode(sign);
		
		JSONObject rsqjson = new JSONObject();  
		
		rsqjson.put("order_info", info);
		rsqjson.put("sign", sign);
		rsqjson.put("sign_type", getSignType());
		
		//info += "&sign=\"" + sign + "\"&" + getSignType();
		return rsqjson.toString();
		
	}
	private String getSignType() {
		return "RSA";
	}
	@SuppressWarnings("deprecation")
	private String getNewOrderInfo(String notify_url,int ratio) {
		float p = price;
		p= (p/100);
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(AlipayConfig.partner);
		sb.append("\"&out_trade_no=\"");
		sb.append(pay_no);
		sb.append("\"&subject=\"");
		String subject = "";
		if(ratio > 0)
		{
		    subject = String.format("%d%s",(price*ratio/100) ,goods_name);
		}
		else
		{
			subject = String.format("%s",goods_name);
		}
		//String.format("%d%s",(price/100)*ratio ,goods_name);
		sb.append(subject);//goods_name//lsl
		sb.append("\"&body=\"");
		sb.append(goods_details);//goods_details//lsl
		sb.append("\"&total_fee=\"");
		sb.append(p);//price
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode(notify_url));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"UTF-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("http://m.alipay.com"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(AlipayConfig.SELLER);

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		
		return new String(sb);
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
	
	public void setAliPayNo(String ali_pay_no){
		this.ali_pay_no=ali_pay_no;
	}
	public String getAliPayNo( ){
		return this.ali_pay_no;
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
