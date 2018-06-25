<%@page language="java" contentType="text/html;charset=gbk"%>
<%@page import="com.yeepay.nonbankcard.NonBankcardService"%>
<%@ page import="util.*" %>
<%!	
String formatString(String text){ 
	if(text == null) {
		return ""; 
	}
	return text;
}
%>
<%
// 业务类型
String r0_Cmd = formatString(request.getParameter("r0_Cmd"));
// 支付结果
String r1_Code = formatString(request.getParameter("r1_Code"));
// 商户编号
String p1_MerId = formatString(request.getParameter("p1_MerId"));
// 商户订单号
String p2_Order = formatString(request.getParameter("p2_Order"));
// 成功金额
String p3_Amt = formatString(request.getParameter("p3_Amt"));
// 支付方式
String p4_FrpId = formatString(request.getParameter("p4_FrpId"));
// 卡序列号组
String p5_CardNo = formatString(request.getParameter("p5_CardNo"));
// 确认金额组
String p6_confirmAmount = formatString(request.getParameter("p6_confirmAmount"));
// 实际金额组
String p7_realAmount = formatString(request.getParameter("p7_realAmount"));
// 卡状态组
String p8_cardStatus = formatString(request.getParameter("p8_cardStatus"));
// 扩展信息
String p9_MP = formatString(request.getParameter("p9_MP"));
// 支付余额 注：此项仅为订单成功,并且需要订单较验时才会有值。失败订单的余额返部返回原卡密中
String pb_BalanceAmt = formatString(request.getParameter("pb_BalanceAmt"));
// 余额卡号  注：此项仅为订单成功,并且需要订单较验时才会有值
String pc_BalanceAct = formatString(request.getParameter("pc_BalanceAct"));
// 签名数据
String hmac	= formatString(request.getParameter("hmac"));

				
out.println("success");
out.println("支付结果返回参数列表<br/>");
out.println("业务类型[r0_Cmd：" + r0_Cmd + "]<br/>");
out.println("支付结果[r1_Code：" + r1_Code + "]<br/>");
out.println("商户编号[p1_MerId：" + p1_MerId + "] <br/>");
out.println("商户订单号[p2_Order：" + p2_Order + "]<br/>");
out.println("成功金额[p3_Amt：" + p3_Amt + "]<br/>");
out.println("支付方式[p4_FrpId：" + p4_FrpId + "]<br/>");
out.println("卡序列号组[p5_CardNo：" + p5_CardNo + "]<br/>");
out.println("确认金额组[p6_confirmAmount：" + p6_confirmAmount + "]<br/>");
out.println("实际金额组[p7_realAmount：" + p7_realAmount + "]<br/>");
out.println("卡状态组[p8_cardStatus：" + p8_cardStatus + "]<br/>");
out.println("扩展信息[p9_MP：" + p9_MP + "]<br/>");
out.println("支付余额[pb_BalanceAmt：" + pb_BalanceAmt + "]<br/>");
out.println("余额卡号[pc_BalanceAct：" + pc_BalanceAct + "]<br/>");
out.println("签名数据[hmac：" + hmac + "]<br/>");

try {
	//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
	NonBankcardService.verifyCallback(r0_Cmd,r1_Code,p1_MerId,p2_Order,p3_Amt,p4_FrpId,p5_CardNo,p6_confirmAmount,p7_realAmount,p8_cardStatus,p9_MP,pb_BalanceAmt,pc_BalanceAct,hmac);
	// 应答机制收到支付结果通知时必须回写以"success"开头的字符串
		out.println("success");
} catch(Exception e) {
	out.println(e.getMessage());
}
%>