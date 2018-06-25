package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import util.DebuUtil;

import com.unionpay.upmp.sdk.conf.UpmpConfig;
import com.unionpay.upmp.sdk.service.UpmpService;

public class UnionPay implements java.io.Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 7098180323139326967L;
	public Integer id;
	public String pay_no;
	public String ten_pay_no;
	public String goods_name;
	public String goods_details;
	public Integer ret;
	public Integer price;

	public  String  getTNNO(int ratio){
		String ret = "";
		String total_fee = String.format("%d", price);
		String subject = "";//String.format("%d%s",(price/100)*ratio ,goods_name);
		
		if(ratio > 0)
		{
		    subject = String.format("%d%s",(price*ratio/100) ,goods_name);
		}
		else
		{
			subject = String.format("%s",goods_name);
		}
        // 请求要素
		Map<String, String> req = new HashMap<String, String>();
		req.put("version", UpmpConfig.VERSION);// 版本号
		req.put("charset", UpmpConfig.CHARSET);// 字符编码
		req.put("transType", "01");// 交易类型
		req.put("merId", UpmpConfig.MER_ID);// 商户代码
		req.put("backEndUrl", UpmpConfig.MER_BACK_END_URL);// 通知URL
		req.put("frontEndUrl", UpmpConfig.MER_FRONT_END_URL);// 前台通知URL(可选)
		req.put("orderDescription", subject);// 订单描述(可选)
		req.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));// 交易开始日期时间yyyyMMddHHmmss
		req.put("orderTimeout", "");// 订单超时时间yyyyMMddHHmmss(可选)
		req.put("orderNumber", pay_no);//订单号(商户根据自己需要生成订单号)
		req.put("orderAmount", total_fee);// 订单金额
        req.put("orderCurrency", "156");// 交易币种(可选)
        req.put("reqReserved", "透传信息");// 请求方保留域(可选，用于透传商户信息)
        
        goods_details = req.get("orderTime");    
        
        // 保留域填充方法
        Map<String, String> merReservedMap = new HashMap<String, String>();
        merReservedMap.put("test", "test");
        req.put("merReserved", UpmpService.buildReserved(merReservedMap));// 商户保留域(可选)
		
        DebuUtil.log("getTNNO\r\n");
        
		Map<String, String> resp = new HashMap<String, String>();
		boolean validResp = UpmpService.trade(req, resp);
	       
        // 商户的业务逻辑
        if (validResp){
            // 服务器应答签名验证成功
        	//DebuUtil.log("resp="+resp.toString());
            ret = resp.get("tn");
        }else {
            // 服务器应答签名验证失败
            
        }
        return ret;
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
