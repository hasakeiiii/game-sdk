<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/sp/sms/gaogu/gaoguMr.jsp?LinkId=123456789&Phone=13751180191&Status=DelivRD&SpNumber=AD2
//http://119.147.23.178:8080/sms/gaogu/gaoguMr.jsp?LinkId=123456789&Phone=13751180190&Status=DelivRD&SpNumber=6
String linkid=request.getParameter("LinkId")==null?"":request.getParameter("LinkId");
System.out.println("gaogu.mr 1003============");
if(linkid.equals("")){
	System.out.println("gaogu.mr linkid is null");
	return;
}
azul.JspUtil.p(request);
String mobile=request.getParameter("Phone")==null?"":request.getParameter("Phone");
String spNum=request.getParameter("SpNumber")==null?"":request.getParameter("SpNumber");
String stat=request.getParameter("Status")==null?"":request.getParameter("Status");
if(azul.SpTool.mr("1003",linkid,mobile,spNum,stat,"delivrd")){
	out.println("0");
}
else{
	out.println("ERR");
}
%>