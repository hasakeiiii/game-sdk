<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://124.232.156.42:8080/sms/zsyj/zsyj_ivr.jsp?calledid=125904532&mobile=13751180190&start_time=2011-08-11&end_time=2011-08-11&fee=32.7
System.out.println("esou.mo 1003-------------------");
String spnum=request.getParameter("dmobile")==null?"":request.getParameter("dmobile");
String mobile=request.getParameter("omobile")==null?"":request.getParameter("omobile");
//azul.JspUtil.p(request);
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
String beg_time=linkid.substring(0,linkid.indexOf(","));
String end_time=linkid.substring(linkid.indexOf(",")+1,linkid.length());
String fee=request.getParameter("msg")==null?"0":request.getParameter("msg");
if(fee.indexOf(".")>-1){
	fee=fee.substring(0,fee.indexOf("."));
}
if(azul.SpTool.charge_ivr("","1003",Integer.valueOf(fee),mobile,spnum,beg_time,end_time)){
	out.print("0");
}
%>