<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMr.jsp?linkid=1234567&status=0
System.out.println("tom.mr 1005-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("tom.mr linkid is null");
	return;
}
String msg=request.getParameter("status")==null?"":request.getParameter("status");
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
if(azul.SpTool.mr("1002",linkid,mobile,"",msg,"0")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>