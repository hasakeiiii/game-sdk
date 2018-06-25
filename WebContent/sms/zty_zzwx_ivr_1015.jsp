<%@ page contentType="text/html;charset=gb2312"%>
<%
/*
?mobile=xxx&linkid=xxx&feetime=xxx&spnumber=xxx
mobile=(手机号)
&spnumber=(通道号) 
&linkid=短信唯一标识
&feetime=(通话时间, 单位 分钟)
*/
String spnum=request.getParameter("spnumber")==null?"":request.getParameter("spnumber");
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
String start_time=request.getParameter("starttime")==null?"":request.getParameter("starttime");
String end_time=request.getParameter("stoptime")==null?"":request.getParameter("stoptime");
if(azul.IvrTool.charge_ivr("1015",-1,mobile,spnum,start_time,end_time)){
	out.print("0");
}
%>