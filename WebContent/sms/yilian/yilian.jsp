<%@ page contentType="text/html;charset=utf-8"%>
<%
//azul.JspUtil.p(request);
System.out.println("yilian 1001-------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("yilian linkid is null");
	return;
}
String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
if(azul.KeyTool.charge(linkid,"1001",1,mobile,"","")){
	out.println("OK");
}
%>