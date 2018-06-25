<%@ page contentType="text/html;charset=utf-8"%>
<%
//http://119.147.23.178:8080/sms/yisou/yisouMo.jsp?linkid=123456789&omobile=13751180191&msg=9649611&dmobile=10669538
//http://124.232.156.42:8080/sms/esou/yisouMo.jsp?linkid=123456789aa&omobile=13751180192&msg=12a&dmobile=1066953856
System.out.println("lingzhang.mo 1011-------------");
String linkid=request.getParameter("link_id")==null?"":request.getParameter("link_id");
if(linkid.equals("")){
	System.out.println("lingzhang mo.linkid is null");
	return;
}
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
String msg=request.getParameter("mo_content")==null?"":request.getParameter("mo_content");
String spNum=request.getParameter("mo_number")==null?"":request.getParameter("mo_number");
if(azul.SpTool.mo(linkid,"1011",1,mobile,msg,spNum)){
	out.println("ok");
}
else{
	out.println("0");
}
//if(msg.indexOf("96496")>-1){
//	azul.SpTool.send("http://m.ztytech.com.cn/gate/easou/easouMo.jsp?"+request.getQueryString());
//}
%>
