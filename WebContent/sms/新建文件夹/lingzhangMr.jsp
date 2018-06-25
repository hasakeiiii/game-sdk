<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.sql.*" %>
<%
//http://119.147.23.178:8080/sms/yisou/yisouMr.jsp?sid=123456789&status=success
//http://124.232.156.42:8080/sms/dingtong/dingtongMr.jsp?serviceid=1111&accessno=2222&userphone=33333&linkid=5555&flag=1
//common.JspUtil.p(request);                            
System.out.println("lingzhang.mr 1011-------------");   
String linkid=azul.JspUtil.getStr(request.getParameter("link_id"),"");
if(linkid.equals("")){
	System.out.println("lingzhang.linkid is null");
	return;
}
String msg=azul.JspUtil.getStr(request.getParameter("mr_status"),"");
if(azul.SpTool.mr("1011",linkid,"","",msg,"DELIVRD")){
	out.println("ok");
}
else{
	out.println("0");
}
%>