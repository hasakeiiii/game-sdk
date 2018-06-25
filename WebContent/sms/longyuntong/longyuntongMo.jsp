<%@ page contentType="text/html;charset=utf-8"%>
<%
//http://119.147.23.178:8080/sms/longyuntong/longyuntongMo.jsp?phone=13590379554&linkid=18000&desttermid=106291189&&content=CKLYtw
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("longyuntong mo.linkid is null");
	return;
}
String mobile=request.getParameter("phone")==null?"":request.getParameter("phone");
String msg=request.getParameter("content")==null?"":request.getParameter("content");
String spNum=request.getParameter("desttermid")==null?"":request.getParameter("desttermid");
int fee=1
if(spNum="1066968021"){
	fee=2;
}

if(azul.SpTool.mo(linkid,"1009",1,mobile,msg,spNum)){
	out.println("0");
}
else{
	out.println("1");
}
//同步给中泰源
if("000".equals(azul.SpTool.getInfoByLinkId(linkid)[1])){
	azul.KeyTool.send("http://m.ztytech.com.cn/gate/zy15/Mo.jsp?"+request.getQueryString());
}
%>