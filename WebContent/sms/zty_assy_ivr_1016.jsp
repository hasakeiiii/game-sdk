<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="azul.JspUtil"%>
<%
//http://124.232.156.42:8080/sms/zsyj/zsyj_ivr.jsp?calledid=125904532&mobile=13751180190&start_time=2011-08-11&end_time=2011-08-11&fee=32.7
String linkid=request.getParameter("link_id")==null?"":request.getParameter("link_id");
String spnum=request.getParameter("spnumber")==null?"":request.getParameter("spnumber");
String mobile=request.getParameter("mo")==null?"":request.getParameter("mo");
String state=request.getParameter("state")==null?"":request.getParameter("state");
if("1".equals(state)){
	if(azul.SpTool.charge_ivr(linkid,"1016",1,mobile,spnum,"","")){
		out.print("0");
	}
	else if(azul.SpTool.charge_ivr(linkid,"1016",1,mobile,spnum + "-" + state,"","")){
		out.print("0");
	}
}
%>