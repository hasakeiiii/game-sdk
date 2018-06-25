<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMr.jsp?linkid=1234567&status=0
System.out.println("jinnan.mr 1010-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("jinnan.mr linkid is null");
	return;
}
String msg=request.getParameter("report")==null?"":request.getParameter("report");
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
if(azul.SpTool.mr("1010",linkid,mobile,"",msg,"DELIVRD")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>