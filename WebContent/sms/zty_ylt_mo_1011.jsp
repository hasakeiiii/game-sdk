<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.io.*"%>
<%@ page import="azul.JspUtil"%>
<%
System.out.println("youletong.mo 1011-------------------");
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
<linkid>11215226486368967764</linkid><!—-运营商为消息生成的临时ID用于短信上行、下行及状态相匹配-->
<feeprice>100</feeprice><!—-资费，单位为分 -->
<mobile></mobile><!—-手机号码-->
<content>1234</content><!—上行内容（指令）-->
<spcode>10668001</spcode><!—-运营商端口号，如：10668001123其中10662000就是取值-->
<toicp>123</toicp><!—-长号码，如：10668001123，其中123为长号码-->
</message>
*/
String param= sb.toString();
String sid="1011";
String linkid=azul.KeyTool.getParam(param,"linkid");
if(linkid.equals("")){
	System.out.println("youletong.mo linkid is null");
	return;
}
String mobile=azul.KeyTool.getParam(param,"mobile");
String msg=azul.KeyTool.getParam(param,"content");
String spnum=azul.KeyTool.getParam(param,"spcode")+azul.KeyTool.getParam(param,"toicp");
int fee=JspUtil.getInt(azul.KeyTool.getParam(param,"feeprice"),0)/100;
if(azul.SpTool.mo(linkid,sid,fee,mobile,msg,spnum)){
	out.println("0");
}
else{
	out.println("-1");
}
%>