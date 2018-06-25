<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="azul.JspUtil"%>
<%
/*
phone  手机号码
addr   端口
msg    上行信息
linkid 唯一标识（每天linkid重复的不算）
status 状态，等于DELIVRD表示成功
fee 费用以分为单位
*/
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("nfkj linkid is null");
	return;
}
String sid="1014";

String status=request.getParameter("status")==null?"":request.getParameter("status");
String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
String spNum=request.getParameter("addr")==null?"":request.getParameter("addr");
int fee=JspUtil.getInt(request.getParameter("fee"),0)/100;
if(!"DELIVRD".equals(status)){
	System.out.println("nfkj status is not 1");
	return;
}
if(azul.SpTool.charge_ok(linkid,sid,fee,mobile,msg,spNum)){
	out.print("1");
}
else{
	out.print("0");
}

%>