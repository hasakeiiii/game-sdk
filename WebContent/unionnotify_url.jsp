
<%
 /**
  * 功能：异步通知页面
  * 版本：1.0
  * 日期：2012-10-11
  * 作者：中国银联UPMP团队
  * 版权：中国银联
  * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
  * */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.unionpay.upmp.sdk.service.*"%>
<%@ page import="util.*"%>
<%@ page import="model.*"%>
<%@ page import="dao.*"%>  
<%@ page import="java.io.*"%> 
<%

/*InputStream   in=request.getInputStream();
byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
String str = new String(buf,"UTF-8");
DebuUtil.log("notisrsq="+str+"\r\n");*/

	//获取银联POST过来异步通知信息
	Map<String,String> params = new HashMap<String,String>();

	Map requestParams = request.getParameterMap();
	String str = "";
	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			valueStr = (i == values.length - 1) ? valueStr + values[i]
					: valueStr + values[i] + ",";
		}
		params.put(name, valueStr);
		//str += "&"+name+"="+;
	}
	//DebuUtil.log("notisrsq="+params.toString());
	if(UpmpService.verifySignature(params)){// 服务器签名验证成功
		DebuUtil.log("服务器签名验证成功");
		//请在这里加上商户的业务逻辑程序代码
		//获取通知返回参数，可参考接口文档中通知参数列表(以下仅供参考)
		String transStatus = request.getParameter("transStatus");// 交易状态
		if (null != transStatus && transStatus.equals("00")) {
		     // 交易处理成功
		    /*DebuUtil.log("rsq:orderTime="+request.getParameter("orderTime"));
		     DebuUtil.log("&settleDate="+request.getParameter("settleDate"));
		     DebuUtil.log("&orderNumber="+request.getParameter("orderNumber"));
		     DebuUtil.log("&exchangeRate="+request.getParameter("exchangeRate"));
		     DebuUtil.log("&signature="+request.getParameter("signature"));
		     DebuUtil.log("&settleCurrency="+request.getParameter("settleCurrency"));
		     DebuUtil.log("&qn="+request.getParameter("qn"));*/
		     
		     //String pay_result = request.getParameter("pay_result");
		     DebuUtil.log("交易处理成功");
	         String sp_billno = request.getParameter("orderNumber");
	         String trade_no = request.getParameter("qn");
	         String out_trade_no = sp_billno;
	
			UnionPay obj = new UnionPay();
			obj.setPayNo(out_trade_no);
			obj.setTenPayNo(trade_no);
		
			obj.setRet(ConstValue.OK);
				
			UnionPayDao dao = new UnionPayDao();
        	dao.pay(obj);
        	
        	if(ConstValue.OPTIMIZE == 1)
        	{
	        	/* PayDao paydao = new PayDao();2015-05-08
	        	paydao.payNotisfy(out_trade_no,trade_no,1); */
	        	HandleChargePay.handleChargePay(out_trade_no, trade_no);
        	}
        	else
        	{
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
		} else {
		}
	
		out.println("success");
	}else{// 服务器签名验证失败
		DebuUtil.log("服务器签名验证失败");
		out.println("fail");
	}
%>
