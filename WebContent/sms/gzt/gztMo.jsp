<%@ page contentType="text/html;charset=utf-8"%>
<%
String linkid=request.getParameter("smsId")==null?"":request.getParameter("smsId");
System.out.println("gzt.mo 1003-------------------");
if(linkid.equals("")){
	System.out.println("gzt.mo linkid is null");
	return;
}
String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
String msg=request.getParameter("moContent")==null?"":java.net.URLEncoder.encode(request.getParameter("moContent"));
if(azul.KeyTool.mo(linkid,"1003",5,mobile,msg,"")){
	out.println("OK");
}
%>