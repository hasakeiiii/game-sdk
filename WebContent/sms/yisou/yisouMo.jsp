<%@ page contentType="text/html;charset=utf-8"%>
<%
//http://119.147.23.178:8080/sms/yisou/yisouMo.jsp?linkid=123456789bb&omobile=13751180192&msg=12a&dmobile=1066953856
//http://124.232.156.42:8080/sms/esou/yisouMo.jsp?linkid=123456789aa&omobile=13751180192&msg=12a&dmobile=1066953856
System.out.println("yisou.mo 1006-------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("yisou mo.linkid is null");
	return;
}
else if(linkid.indexOf(",")>-1){
    linkid=linkid.substring(0,linkid.indexOf(","));
}
String mobile=request.getParameter("omobile")==null?"":request.getParameter("omobile");
String msg=request.getParameter("msg")==null?"":request.getParameter("msg");
String spNum=request.getParameter("dmobile")==null?"":request.getParameter("dmobile");
int fee=1;
if(msg.indexOf("1A154")>-1){
	fee=2;
}
if(azul.SpTool.mo(linkid,"1006",fee,mobile,msg,spNum)){
	out.println("0");
}
else{
	out.println("1");
}
if(msg.indexOf("96496")>-1 || msg.indexOf("1A154")>-1){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/easou/easouMo.jsp?"+request.getQueryString());
}
%>
