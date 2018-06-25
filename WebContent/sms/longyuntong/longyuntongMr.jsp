<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.sql.*" %>
<%
//http://119.147.23.178:8080/sms/longyuntong/longyuntongMr.jsp?linkid=18000&status=0
//common.JspUtil.p(request);
System.out.println("longyuntong.mr 1009-------------");
String linkid=azul.JspUtil.getStr(request.getParameter("linkid"),"");
if(linkid.equals("")){
	System.out.println("longyuntong.linkid is null");
	return;
}
String msg=azul.JspUtil.getStr(request.getParameter("status"),"");
if(azul.SpTool.mr("1009",linkid,"","",msg,"0")){
	out.println("0");
}
else{
	out.println("1");
}
//同步给中泰源
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy15/Mr.jsp?"+request.getQueryString());
}
%>