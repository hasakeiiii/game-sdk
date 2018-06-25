<%@ page contentType="text/html;charset=gb2312"%>
<%
System.out.println("yq.mr 9999-------------");
String linkid=request.getParameter("id")==null?"":request.getParameter("id");
if(linkid.equals("")){
	System.out.println("yq.mr linkid is null");
	return;
}
String msg=request.getParameter("succ")==null?"":request.getParameter("succ");
if(azul.KeyTool.mr(linkid,msg,"2")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>