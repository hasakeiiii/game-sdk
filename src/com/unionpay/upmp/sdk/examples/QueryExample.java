package com.unionpay.upmp.sdk.examples;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.Pay;
import model.UnionPay;

import util.ConstValue;
import util.DebuUtil;

import com.unionpay.upmp.sdk.conf.UpmpConfig;
import com.unionpay.upmp.sdk.service.UpmpService;

import dao.PayDao;
import dao.UnionPayDao;

/**
 * 类名：交易信息查询接口实例类文件
 * 功能：交易信息查询接口实例
 * 版本：1.0
 * 日期：2012-10-11
 * 作者：中国银联UPMP团队
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */
public class QueryExample{

	public static void main(String[] args){
        // 请求要素
		Query("20140811165751","081116575189367");
		/*Map<String, String> req = new HashMap<String, String>();
		req.put("version", UpmpConfig.VERSION);// 版本号
		req.put("charset", UpmpConfig.CHARSET);// 字符编码
		req.put("transType", "01");// 交易类型
		req.put("merId", UpmpConfig.MER_ID);// 商户代码
		req.put("orderTime", "20140805102119");// 交易开始日期时间yyyyMMddHHmmss或yyyyMMdd
		req.put("orderNumber", "080510211917584");// 订单号
		// 保留域填充方法
        Map<String, String> merReservedMap = new HashMap<String, String>();
        merReservedMap.put("test", "test");
        req.put("merReserved", UpmpService.buildReserved(merReservedMap));// 商户保留域(可选)
		
		Map<String, String> resp = new HashMap<String, String>();
		boolean validResp = UpmpService.query(req, resp);
	       
        // 商户的业务逻辑
        if (validResp){
            // 服务器应答签名验证成功
        	//DebuUtil.log("resp="+resp.toString());
        }else {
            // 服务器应答签名验证失败
            
        }*/
	}
	public static void Query(String orderTime,String orderNumber){
		//String orderTime = pay.thir_pay_id;
		//String orderNumber = pay.pay_no;
        // 请求要素
		Map<String, String> req = new HashMap<String, String>();
		req.put("version", UpmpConfig.VERSION);// 版本号
		req.put("charset", UpmpConfig.CHARSET);// 字符编码
		req.put("transType", "01");// 交易类型
		req.put("merId", UpmpConfig.MER_ID);// 商户代码
		req.put("orderTime", orderTime);// 交易开始日期时间yyyyMMddHHmmss或yyyyMMdd,"20140805102119"
		req.put("orderNumber", orderNumber);// 订单号,080510211917584
		// 保留域填充方法
        Map<String, String> merReservedMap = new HashMap<String, String>();
        merReservedMap.put("test", "test");
        req.put("merReserved", UpmpService.buildReserved(merReservedMap));// 商户保留域(可选)
		
		Map<String, String> resp = new HashMap<String, String>();
		boolean validResp = UpmpService.query(req, resp);
	       
        // 商户的业务逻辑
        if (validResp){
            // 服务器应答签名验证成功
        	 DebuUtil.log("交易处理成功");
        	 String transStatus = resp.get("transStatus");// 交易状态
     		if (null != transStatus && transStatus.equals("00")) 
     		{
		         String sp_billno = resp.get("orderNumber");//request.getParameter("orderNumber");
		         String trade_no =  resp.get("qn");//request.getParameter("qn");
		         String out_trade_no = sp_billno;
		
				UnionPay obj = new UnionPay();
				obj.setPayNo(out_trade_no);
				obj.setTenPayNo(trade_no);
			
				obj.setRet(ConstValue.OK);
					
				UnionPayDao dao = new UnionPayDao();
	        	dao.pay(obj);
	        	
	        	PayDao paydao = new PayDao();
	        	Pay pay = new Pay();
	        	pay.pay_no = out_trade_no;
	        	pay.thir_pay_id = trade_no;
	        	
	    	    pay.state = 1;
	    	    int paystate = 0;
	    	    pay = paydao.update(pay);     
	            if(pay.rsqCallbackUrl(paystate) == 1)
	            {
	            	paydao.update(pay);
	            }	
     	  }
        }else {
            // 服务器应答签名验证失败
            
        }
	}
	
	public static void Query(Pay pay){
		String orderTime = pay.ext;
		String orderNumber = pay.pay_no;
        // 请求要素
		Map<String, String> req = new HashMap<String, String>();
		req.put("version", UpmpConfig.VERSION);// 版本号
		req.put("charset", UpmpConfig.CHARSET);// 字符编码
		req.put("transType", "01");// 交易类型
		req.put("merId", UpmpConfig.MER_ID);// 商户代码
		req.put("orderTime", orderTime);// 交易开始日期时间yyyyMMddHHmmss或yyyyMMdd,"20140805102119"
		req.put("orderNumber", orderNumber);// 订单号,080510211917584
		// 保留域填充方法
        Map<String, String> merReservedMap = new HashMap<String, String>();
        merReservedMap.put("test", "test");
        req.put("merReserved", UpmpService.buildReserved(merReservedMap));// 商户保留域(可选)
		
		Map<String, String> resp = new HashMap<String, String>();
		boolean validResp = UpmpService.query(req, resp);
	       
        // 商户的业务逻辑
        if (validResp){
            // 服务器应答签名验证成功
        	 DebuUtil.log("交易处理成功");
        	 String transStatus = resp.get("transStatus");// 交易状态
     		if (null != transStatus && transStatus.equals("00")) 
     		{
		         String sp_billno = resp.get("orderNumber");//request.getParameter("orderNumber");
		         String trade_no =  resp.get("qn");//request.getParameter("qn");
		         String out_trade_no = sp_billno;
		
				UnionPay obj = new UnionPay();
				obj.setPayNo(out_trade_no);
				obj.setTenPayNo(trade_no);
			
				obj.setRet(ConstValue.OK);
					
				UnionPayDao dao = new UnionPayDao();
	        	dao.pay(obj);
	        	
	        	PayDao paydao = new PayDao();
	        	//Pay pay = new Pay();
	        	//pay.pay_no = out_trade_no;
	        	pay.thir_pay_id = trade_no;
	        	
	    	    pay.state = 1;
	    	    int paystate = 0;
	    	    pay = paydao.update(pay);     
	            if(pay.rsqCallbackUrl(paystate) == 1)
	            {
	            	paydao.update(pay);
	            }	
     	  }
     	  else
     	  {
     		  PayDao paydao = new PayDao();
	        	//Pay pay = new Pay();
	        	//pay.pay_no = out_trade_no;
	        	//pay.thir_pay_id = trade_no;        	
	    	    pay.state = -1;	    	   
	    	    pay = paydao.update(pay);     
     	  }
        }else {
            // 服务器应答签名验证失败
            
        }
	}
	
	/**
	 * 订单号生成，该生产订单号仅用于测试，商户根据自己需要生成订单号
	 * @return
	 */
	public static String generateOrdrNo(){
		DateFormat formater = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        StringBuilder sb = new StringBuilder(formater.format(new Date()));
        return sb.toString();
	}
	
}
	