<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMo.jsp?linkid=1234567&mobile=13751180191&msg=796SSXCXACXAA
//azul.JspUtil.p(request);
System.out.println("jinnan.mo 1010-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("jinnan.mo linkid is null");
	return;
}
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
String spnum=request.getParameter("spnum")==null?"":request.getParameter("spnum");
int fee=1;
if(msg.indexOf("IHJD")>-1){
	fee=2;
}
if(azul.SpTool.mo(linkid,"1010",fee,mobile,msg,spnum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>