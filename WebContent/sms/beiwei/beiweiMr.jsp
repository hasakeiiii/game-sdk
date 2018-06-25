<%@ page contentType="text/html;charset=utf-8"%>
<%
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=&phone=13751180191&message=T12
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=0003&phone=13751180191&spnum=3
//http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=&phone=13751180191&message=T12
//http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&serviceid=delivrd&destaddr=0003&phone=13751180191&spnum=1066889925
System.out.println("beiwei.mr 1005-------------");
String linkid=azul.JspUtil.getStr(request.getParameter("linkid"),"");
if(linkid.equals("")){
	System.out.println("beiwei.linkid is null");
	return;
}
String msg=azul.JspUtil.getStr(request.getParameter("status"),"");
if(azul.SpTool.mr("1005",linkid,"","",msg,"1")){
	out.println("ok");
}
else{
	out.println("ERR");
}
%>