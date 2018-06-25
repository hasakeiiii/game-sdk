<%@ page contentType="text/html;charset=utf-8"%>
<%
//common.JspUtil.p(request);
System.out.println("gzt.mr 1003-------------");
String linkid=request.getParameter("smsId")==null?"":request.getParameter("smsId");
if(linkid.equals("")){
	System.out.println("gzt.mr linkid is null");
	return;
}
String msg=request.getParameter("state")==null?"":request.getParameter("state");
if(azul.KeyTool.mr(linkid,msg,"DELIVRD")){
	out.println("0");
}
%>