<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://124.232.156.42:8080/sms/xsc/xscMo.jsp?LinkID=14152502155309767354aa&status=DELIVRD
System.out.println("xsc.mr 1002-------------------");
String linkid=request.getParameter("LinkID")==null?"":request.getParameter("LinkID");
if(linkid.equals("")){
	System.out.println("xsc.mr linkid is null");
	return;
}
String UserPhone=request.getParameter("UserPhone")==null?"":request.getParameter("UserPhone");
String msg=request.getParameter("status")==null?"":request.getParameter("status");
if(azul.SpTool.mr("1002",linkid,UserPhone,"",msg,"DELIVRD")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>