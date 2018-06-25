<%@ page contentType="text/html;charset=utf-8"%>
<%
//azul.JspUtil.p(request);
System.out.println("yunshi 1009-------------");
String linkid=request.getParameter("L")==null?"":request.getParameter("L");
if(linkid.equals("")){
	System.out.println("yunshi linkid is null");
	return;
}
String mobile=request.getParameter("P")==null?"":request.getParameter("P");
if(azul.KeyTool.charge(linkid,"1009",2,mobile,"","")){
	out.println("OK");
}
%>