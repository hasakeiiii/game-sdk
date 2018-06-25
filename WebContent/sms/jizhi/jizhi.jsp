<%@ page contentType="text/html;charset=gb2312"%>
<%
System.out.println("jizhi 1005-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("jizhi linkid is null");
	return;
}
String mobile =request.getParameter("mobile")==null?"":request.getParameter("mobile");
if(azul.KeyTool.charge(linkid,"1005",100,mobile,"","")){
	out.println("OK");
}
%>