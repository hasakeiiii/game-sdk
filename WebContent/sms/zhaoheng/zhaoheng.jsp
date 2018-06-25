<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.sql.*" %>
<%
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=&phone=13751180191&message=T12
//http://localhost:8080/sp/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=0003&phone=13751180191&spnum=3
//http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&destaddr=&phone=13751180191&message=T12
//http://119.147.23.178:8080/sms/beiqingbao/beiqingbao.jsp?linkid=123456789&serviceid=delivrd&destaddr=0003&phone=13751180191&spnum=1066889925
//System.out.println(request.getQueryString());

String linkid=request.getParameter("reserved")==null?"":request.getParameter("reserved");
if(linkid.equals("")){
	System.out.println("zhaoheng linkid is null");
	return;
}
String sid="1008";
String stat=request.getParameter("status")==null?"-999":request.getParameter("status");
if(!"-999".equals(stat)){
	System.out.println("zhaoheng.mr "+sid+"===============");
	String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
	if(azul.SpTool.mr(sid,linkid,mobile,"",stat,"DELIVRD")){
		out.println("ok");
	}
	else{
		out.println("ERR");
	}
}
else{
	System.out.println("zhaoheng.mo "+sid+"-------------------");
	String mobile=request.getParameter("orgmobile")==null?"":request.getParameter("orgmobile");
	String momsg=request.getParameter("cp")==null?"":request.getParameter("cp");
	String spnum=request.getParameter("destmobile")==null?"":request.getParameter("destmobile");
	if(azul.SpTool.mo(linkid,sid,2,mobile,momsg,spnum)){
		out.println("ok");
	}
	else{
		out.println("ERR");
	}
}
%>