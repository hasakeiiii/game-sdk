<%@ page contentType="text/html;charset=gb2312"%>
<%
System.out.println("yq.mo 9999-------------------");
String linkid=request.getParameter("id")==null?"":request.getParameter("id");
if(linkid.equals("")){
	System.out.println("yq.mo linkid is null");
	return;
}
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
if(azul.KeyTool.mo(linkid,"9999",1,mobile,"","")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>