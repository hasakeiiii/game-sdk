<%@page import="java.util.Random"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%
Date   now   =   new   Date();  
SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyyMMddHHmmss");
String   datetimes   =   dateFormat.format(now);  
Random random = new Random();
String linkid="1004"+ datetimes + (random.nextInt(900000)+100000);
String sid="1004";
int fee=15;
String stat=request.getParameter("destaddr")==null?"-999":request.getParameter("destaddr");
String serviceid=request.getParameter("serviceid")==null?"-999":request.getParameter("serviceid");
if("0003".equals(stat)&&"DELIVRD".equals(serviceid)){

	String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
	String message=request.getParameter("message")==null?"":request.getParameter("message");
	//System.out.println("---"+linkid+"---"+sid+"---"+fee+"---"+mobile+"---"+message+"---"+"10000001");
	if(azul.SpTool.charge_ok(linkid,sid,fee,mobile,message,"10000001")){
		out.println("ok");
	}
	else{
		out.println("ERR");
	}
}
%>