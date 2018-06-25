<%@ page import="com.yeepay.nonbankcard.NonBankcardService,com.yeepay.nonbankcard.NonBankcardPaymentResult" %>
<%!	
String formatString(String text){ 
	if(text == null) {
		return ""; 
	}
	return text;
}
%>
<%
request.setCharacterEncoding("GBK");
// 商户订单号
String p2_Order = formatString(request.getParameter("p2_Order"));

// 订单金额
String p3_Amt = formatString(request.getParameter("p3_Amt"));

// 是否较验订单金额
String p4_verifyAmt = formatString(request.getParameter("p4_verifyAmt"));

// 产品名称
String p5_Pid = formatString(request.getParameter("p5_Pid"));

// 产品类型
String p6_Pcat = formatString(request.getParameter("p6_Pcat"));

// 产品描述
String p7_Pdesc = formatString(request.getParameter("p7_Pdesc"));

// 交易成功通知地址
String p8_Url = formatString(request.getParameter("p8_Url"));

// 扩展信息
String pa_MP = new String(formatString(request.getParameter("pa_MP")).getBytes("iso-8859-1"),"gbk");

// 卡面额组
String pa7_cardAmt = formatString(request.getParameter("pa7_cardAmt"));

// 卡号组
String pa8_cardNo = formatString(request.getParameter("pa8_cardNo"));

// 卡密组
String pa9_cardPwd = formatString(request.getParameter("pa9_cardPwd"));

// 支付通道编码
String pd_FrpId = formatString(request.getParameter("pd_FrpId"));

// 应答机制
String pr_NeedResponse = formatString("1");

// 用户唯一标识
String pz_userId = formatString(request.getParameter("pz_userId"));

// 用户的注册时间
String pz1_userRegTime  = formatString(request.getParameter("pz1_userRegTime"));
try {
	NonBankcardPaymentResult rs = NonBankcardService.pay(p2_Order,
														 p3_Amt,
														 p4_verifyAmt,
														 p5_Pid,
														 p6_Pcat,
														 p7_Pdesc,
														 p8_Url,
														 pa_MP,
														 pa7_cardAmt,
														 pa8_cardNo,
														 pa9_cardPwd,
														 pd_FrpId,
														 pr_NeedResponse,
														 pz_userId,
														 pz1_userRegTime);
	out.println("提交返回参数列表<br/>");
	out.println("业务类型[r0_Cmd：" + rs.getR0_Cmd() + "]<br/>");
	out.println("订单的提交状态[r1_Code：" + rs.getR1_Code() + "]<br/>");
	out.println("交易订单号[r6_Order：" + rs.getR6_Order() + "] <br/>");
	out.println("订单的提交状态[rq_ReturnMsg：" + rs.getRq_ReturnMsg() + "]<br/>");
	/* 	该方法是根据《易宝支付非银行卡支付专业版接口文档 v3.0》生成一个模拟的交易结果通知串.
	 * 	商户使用模拟的交易结果通知串可以直接测试自己的交易结果接收程序(callback)的正确性.
	 * 	实际的交易结果通知机制以《易宝支付非银行卡支付专业版接口文档 v3.0》为准，该方法只是
	 * 	模拟了交易结果通知串.正式上线时请不要调用此方法。 
	 */
	// out.println(NonBankcardService.generationTestCallback(p2_Order,p3_Amt,p4_verifyAmt,p5_Pid,p6_Pcat,p7_Pdesc,p8_Url,pa_MP,pa7_cardAmt,pa8_cardNo,pa9_cardPwd,pd_FrpId,pr_NeedResponse,pz_userId,pz1_userRegTime));
} catch(Exception e) {
	out.println(e.getMessage());
}
%>