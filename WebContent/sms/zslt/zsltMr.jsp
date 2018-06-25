<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.io.*" %>
<%
System.out.println("zslt.mr 1004-------------------");
InputStream in = request.getInputStream();
BufferedReader br = new BufferedReader(new InputStreamReader(in));
StringBuffer sb = new StringBuffer();
String s;
while ((s = br.readLine()) != null){
	sb.append(s);
}
br.close();
//String param="<?xml version=\"1.0\" encoding=\"GBK\"?><message><msgid></msgid><mobile></mobile><statestr>DELIVRD</statestr><linkid>zslt0002</linkid><createDate>2008-07-10 00:05:39</createDate></message>";
String param=sb.toString();
String linkid=azul.KeyTool.getParam(param,"linkid");
if(linkid.equals("")){
	System.out.println("zslt.mr linkid is null");
	return;
}
String msg=azul.KeyTool.getParam(param,"statestr");
if(azul.KeyTool.mr(linkid,msg,"DELIVRD")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>