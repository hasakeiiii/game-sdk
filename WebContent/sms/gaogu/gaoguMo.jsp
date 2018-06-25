<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/sp/sms/gaogu/gaoguMo.jsp?LinkId=123456789&Phone=13751180191&Msg=6
//http://119.147.23.178:8080/sms/gaogu/gaoguMo.jsp?LinkId=123456789&Phone=13751180190&Msg=6
//azul.JspUtil.p(request);
String linkid=request.getParameter("LinkId")==null?"":request.getParameter("LinkId");
System.out.println("gaogu.mo 1003-------------------");
if(linkid.equals("")){
	System.out.println("gaogu.mo linkid is null");
	return;
}
String mobile=request.getParameter("Phone")==null?"":request.getParameter("Phone");
String momsg=request.getParameter("Msg")==null?"":request.getParameter("Msg");
if(azul.SpTool.mo(linkid,"1003",2,mobile,momsg,"")){
	out.println("0");
}
else{
	out.println("ERR");
}
%>