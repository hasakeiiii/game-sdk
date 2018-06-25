<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.io.*" %>
<%
System.out.println("zslt.mo 1004-------------------");
InputStream in = request.getInputStream();
BufferedReader br = new BufferedReader(new InputStreamReader(in));
StringBuffer sb = new StringBuffer();
String s;
while ((s = br.readLine()) != null){
	sb.append(s);
}
br.close();
//System.out.println(sb.toString());
//String param="<?xml version=\"1.0\" encoding=\"GBK\"?><message><linkid>zslt0002</linkid><spid>cmcc-2000</spid><spcode>10662000</spcode><feecode>LS</feecode><feeprice>100</feeprice><toicp>123</toicp><product></product><feecategory></feecategory><channel></channel><mobile>13751180192</mobile><content>PDZDJF DSWS001000020M</content><isprovision></isprovision><issubscribe></issubscribe><createDate>2008-07-11 00:02:17</createDate></message>";
String param=sb.toString();
//azul.JspUtil.log("c://1.txt",param);
String linkid=azul.KeyTool.getParam(param,"linkid");
if(linkid.equals("")){
	System.out.println("zslt.mo linkid is null");
	return;
}
String mobile=azul.KeyTool.getParam(param,"mobile");
if(azul.KeyTool.mo(linkid,"1004",1,mobile,"","")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>