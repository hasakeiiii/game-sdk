<%@ page contentType="text/html;charset=utf-8"%>
<%
//http://119.147.23.178:8080/sms/yisou/yisouMo.jsp?linkid=123456789&omobile=13751180191&msg=9649611&dmobile=10669538
//http://124.232.156.42:8080/sms/esou/yisouMo.jsp?linkid=123456789aa&omobile=13751180192&msg=12a&dmobile=1066953856
System.out.println("dingtong.mo 1007-------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("dingtong mo.linkid is null");
	return;
}
String mobile=request.getParameter("userphone")==null?"":request.getParameter("userphone");
String msg=request.getParameter("msgcontent")==null?"":request.getParameter("msgcontent");
String spNum=request.getParameter("accessno")==null?"":request.getParameter("accessno");
if(azul.SpTool.mo(linkid,"1007",1,mobile,msg,spNum)){
	out.println("ok");
}
else{
	out.println("0");
}
//if(msg.indexOf("96496")>-1){
//	azul.SpTool.send("http://m.ztytech.com.cn/gate/easou/easouMo.jsp?"+request.getQueryString());
//}
%>
