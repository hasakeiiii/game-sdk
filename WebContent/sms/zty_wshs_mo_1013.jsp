<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMr.jsp?linkid=1234567&status=0
System.out.println("wshs.mr 1013-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("wshs.mr linkid is null");
	return;
}
int 
String spNum=request.getParameter("spnumber")==null?"":request.getParameter("spnumber");
String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
int fee=1;
if(msg.startsWith("8Da151AZT"))){
	fee=2;
}
if(azul.SpTool.mo(linkid,"1013",fee,mobile,msg,spNum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
if(msg.indexOf("8Da151AZT")>-1){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/wshs/mo.jsp?"+request.getQueryString());
}
%>