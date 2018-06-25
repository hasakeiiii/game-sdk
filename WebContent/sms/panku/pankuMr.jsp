<%@ page contentType="text/html;charset=utf-8"%>
<%
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
//linkid="14332005613389231248";status="DELIVRD";
System.out.println("panku.mr 1000-------------");
if(linkid.equals("")){
	System.out.println("pankuMr.linkid is null");
	return;
}
String msg=request.getParameter("status")==null?"":request.getParameter("status");
if(azul.KeyTool.mr(linkid,msg,"DELIVRD")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>