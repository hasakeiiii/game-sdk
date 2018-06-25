<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://124.232.156.42:8080/sms/zsyj/zsyj_ivr.jsp?calledid=125904532&mobile=13751180190&start_time=2011-08-11&end_time=2011-08-11&fee=32.7
String spnum=request.getParameter("destaddr")==null?"":request.getParameter("destaddr");
String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
//azul.JspUtil.p(request);
String start_time=request.getParameter("starttime")==null?"":request.getParameter("starttime");
String end_time=request.getParameter("stoptime")==null?"":request.getParameter("stoptime");
if(azul.SpTool.charge_ivr("","1004",-1,mobile,spnum,start_time,end_time)){
	out.print("0");
}
%>