<%@ page contentType="text/html;charset=utf-8"%>
<%
//http://119.147.23.178:8080/sms/yisou/yisouMo.jsp?linkid=123456789&omobile=13751180191&msg=9649611&dmobile=10669538
//http://124.232.156.42:8080/sms/esou/yisouMo.jsp?linkid=123456789aa&omobile=13751180192&msg=12a&dmobile=1066953856
System.out.println("yisou.mo 1006-------------");
String linkid=reques"C:/Documents and Settings/Administrator/桌面/yisouMr.jsp"t.getParameter("linkid")==null?"":request.getParameter("linkid");
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
if(azul.SpTool.mo(linkid,"1003",1,mobile,msg,spNum)){
	out.println("0");
}
else{
	out.println("1");
}
//同步给中泰源
	if(msg.indexOf("96496")>-1){
		azul.KeyTool.send("http://m.ztytech.com.cn/gate/easou/easouMo.jsp?"+request.getQueryString());
	}
%>
