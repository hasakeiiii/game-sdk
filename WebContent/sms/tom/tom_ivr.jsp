<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://124.232.156.42:8080/sms/zsyj/zsyj_ivr.jsp?calledid=125904532&mobile=13751180190&start_time=2011-08-11&end_time=2011-08-11&fee=32.7
String spnum=request.getParameter("calledid")==null?"":request.getParameter("calledid");
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
//azul.JspUtil.p(request);
String start_time=request.getParameter("start_time")==null?"":request.getParameter("start_time");
String end_time=request.getParameter("end_time")==null?"":request.getParameter("end_time");
String fee=request.getParameter("fee")==null?"0":request.getParameter("fee");
if(fee.indexOf(".")>-1){
	fee=fee.substring(0,fee.indexOf("."));
}
if(azul.SpTool.charge_ivr("","1005",Integer.valueOf(fee),mobile,spnum,start_time,end_time)){
	out.print("0");
}
%>