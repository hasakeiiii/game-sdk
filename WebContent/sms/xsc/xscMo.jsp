<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://124.232.156.42:8080/dingxue/sms/xsc/xscMo.jsp?LinkID=14152502155309767354aa&UserPhone=13751180191&Content=9802&longnum=1062880898
//azul.JspUtil.p(request);
System.out.println("xsc.mo 1002-------------------");
String linkid=request.getParameter("LinkID")==null?"":request.getParameter("LinkID");
if(linkid.equals("")){
	System.out.println("xsc.mo linkid is null");
	return;
}
String mobile=request.getParameter("UserPhone")==null?"":request.getParameter("UserPhone");
String msg=request.getParameter("Content")==null?"":request.getParameter("Content");
String longnum=request.getParameter("DestPhone")==null?"":request.getParameter("DestPhone");
int fee=1;
if(azul.SpTool.mo(linkid,"1002",fee,mobile,msg,longnum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>