<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="azul.JspUtil"%>
<%
/*
LINKID link_id
手机号码 mo
内容 msg
端口 mo_to
报告详情 rpmsg 【DELIVRD成功, 彩信 1000成功】
*/
String linkid=request.getParameter("link_id")==null?"":request.getParameter("link_id");
if(linkid.equals("")){
	System.out.println("ayss linkid is null");
	return;
}
String sid="1016";

String status=request.getParameter("rpmsg")==null?"":request.getParameter("rpmsg");
String mobile=request.getParameter("mo")==null?"":request.getParameter("mo");
String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
String spNum=request.getParameter("mo_to")==null?"":request.getParameter("mo_to");
int fee=1;
if(msg.indexOf("6101")>-1 || msg.indexOf("2517")>-1){
	fee=2;
}
if(!"DELIVRD".equals(status)){
	System.out.println("ayss status is not 1");
	return;
}
if(azul.SpTool.charge_ok(linkid,sid,fee,mobile,msg,spNum)){
	out.print("OK");
}
else{
	out.print("ERROR");
}
//同步给中泰源
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy12/Mr.jsp?"+request.getQueryString());
}
%>