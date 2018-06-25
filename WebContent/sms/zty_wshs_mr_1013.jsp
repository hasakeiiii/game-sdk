<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMr.jsp?linkid=1234567&status=0
System.out.println("wshs.mr 1013-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("wshs.mr linkid is null");
	return;
}
String msg=request.getParameter("statestr")==null?"":request.getParameter("statestr");
String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
if(azul.SpTool.mr("1013",linkid,mobile,"",msg,"DELIVRD")){
	out.println("OK");
}
else{
	out.println("ERR");
}
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/wshs/mr.jsp?"+request.getQueryString());
}
%>