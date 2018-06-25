<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMo.jsp?linkid=1234567&mobile=13751180191&msg=796SSXCXACXAA
//azul.JspUtil.p(request);
System.out.println("jinchang.mo 1007-------------------");
String linkid=request.getParameter("LinkId")==null?"":request.getParameter("LinkId");
if(linkid.equals("")){
	System.out.println("jinchang.mo linkid is null");
	return;
}
String mobile=request.getParameter("Mo_Phone")==null?"":request.getParameter("Mo_Phone");
String msg=request.getParameter("Mo_Msg")==null?"":request.getParameter("Mo_Msg");
boolean ok=azul.KeyTool.mo(linkid,"1007",2,mobile,msg,"");
if(ok){
	out.println("1");
}
else{
	out.println("ERR");
}
%>