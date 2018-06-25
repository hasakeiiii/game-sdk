<%@ page contentType="text/html;charset=gb2312"%>
<%
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
System.out.println("guanghua.mo 1011-------------------");
if(linkid.equals("")){
	System.out.println("guanghua linkid is null");
	return;
}
String mobile =request.getParameter("mobile")==null?"":request.getParameter("mobile");
if(azul.KeyTool.charge(linkid,"1011",1,mobile,"","")){
	out.println("OK");
}
%>