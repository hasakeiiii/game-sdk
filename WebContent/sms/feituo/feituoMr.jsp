<%@ page contentType="text/html;charset=utf-8"%>
<%
//common.JspUtil.p(request);
System.out.println("feituo.mr 9999-------------");
String linkid=azul.JspUtil.getStr(request.getParameter("link_id"),"");
if(linkid.equals("")){
	System.out.println("feituoMr.linkid is null");
	return;
}
String msg=azul.JspUtil.getStr(request.getParameter("stat"),"");
if(azul.KeyTool.mr(linkid,msg,"DELIVRD")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>