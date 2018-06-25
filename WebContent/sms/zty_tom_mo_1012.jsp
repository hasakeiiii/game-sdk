<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMo.jsp?linkid=1234567&mobile=13751180191&msg=796SSXCXACXAA
//azul.JspUtil.p(request);
System.out.println("tom.mo 1012-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("tom.mo linkid is null");
	return;
}
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
String longnum=request.getParameter("longnum")==null?"":request.getParameter("longnum");
String feeStr=request.getParameter("feecode")==null?"":request.getParameter("feecode");
int fee=1;
if(!"".equals(feeStr)){
	fee=Integer.valueOf(feeStr)/100;
}
if(azul.SpTool.mo(linkid,"1012",fee,mobile,msg,longnum)){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>