<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://124.232.156.42:8080/sms/zsyj/zsyj_ivr.jsp?calledid=125904532&mobile=13751180190&start_time=2011-08-11&end_time=2011-08-11&fee=32.7
String spnum=request.getParameter("spnumber")==null?"":request.getParameter("spnumber");
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
//azul.JspUtil.p(request);
String start_time=request.getParameter("starttime")==null?"":request.getParameter("starttime");
String end_time=request.getParameter("endtime")==null?"":request.getParameter("endtime");
String feeStr=request.getParameter("alltime")==null?"1":request.getParameter("alltime");
String linkid=request.getParameter("linkid")==null?"1":request.getParameter("linkid");
if(azul.SpTool.charge_ivr(linkid,"1019",Integer.valueOf(feeStr),mobile,spnum,start_time,end_time)){
	out.print("0");
}
%>