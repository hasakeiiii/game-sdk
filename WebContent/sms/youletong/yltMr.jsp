<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.io.*" %>
<%
System.out.println("youletong.mr 1011-------------------");
InputStream in = request.getInputStream();
BufferedReader br = new BufferedReader(new InputStreamReader(in));
StringBuffer sb = new StringBuffer();
String s;
while ((s = br.readLine()) != null){
	sb.append(s);
}
br.close();
/*
String param="<?xml version=\"1.0\" encoding=\"GBK\"?>
<message>
<linkid></linkid><!—-运营商为消息生成的临时ID，用于匹配MO-->
<mobile></mobile><!—-手机号码-->
<statestr></statestr><!—-状态报告，DELIVRD为成功，其余均为失败-->
</message>
*/
String sid="1011";
String param=sb.toString();
String linkid=azul.KeyTool.getParam(param,"linkid");
if(linkid.equals("")){
	System.out.println("youletong.mr linkid is null");
	return;
}
String msg=azul.KeyTool.getParam(param,"statestr");
if(azul.SpTool.mr(sid,linkid,"","","","DELIVRD")){
	out.println("0");
}
else{
	out.println("-1");
}
%>