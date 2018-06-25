<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.sql.*" %>
<%
//http://119.147.23.178:8080/sms/yisou/yisouMr.jsp?sid=123456789&status=success
//http://124.232.156.42:8080/sms/dingtong/dingtongMr.jsp?serviceid=1111&accessno=2222&userphone=33333&linkid=5555&flag=1
//common.JspUtil.p(request);                            
System.out.println("dingtong.mr 1007-------------");   
String linkid=azul.JspUtil.getStr(request.getParameter("linkid"),"");
if(linkid.equals("")){
	System.out.println("dingtong.linkid is null");
	return;
}
String msg=azul.JspUtil.getStr(request.getParameter("flag"),"");
if(azul.SpTool.mr("1007",linkid,"","",msg,"1")){
	out.println("ok");
}
else{
	out.println("0");
}
%>