<%@ page contentType="text/html;charset=gb2312"%>
<%
String linkid=request.getParameter("L")==null?"":request.getParameter("L");
System.out.println("yunshi 1012-------------------");
if(linkid.equals("")){
	System.out.println("yunshi linkid is null");
	return;
}
String mobile =request.getParameter("P")==null?"":request.getParameter("P");
if(azul.KeyTool.charge(linkid,"1012",1,mobile,"","")){
	out.println("OK");
}
%>