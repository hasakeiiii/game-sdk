<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://localhost:8080/dingxue/sms/tom/tomMr.jsp?linkid=1234567&status=0
System.out.println("dckj.mr 1017-------------------");
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("dckj.mr linkid is null");
	return;
}
String msg=request.getParameter("status")==null?"":request.getParameter("status");
if(azul.SpTool.mr("1017",linkid,"","",msg,"1")){
	out.println("OK");
}
else{
	out.println("ERR");
}
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy13/Mr.jsp?"+request.getQueryString());
}
%>