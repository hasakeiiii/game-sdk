<%@ page contentType="text/html;charset=gb2312"%>
<%
//common.JspUtil.p(request);
System.out.println("hb.mr 9999-------------");
String linkid=request.getParameter("link_id")==null?"":request.getParameter("link_id");
if(linkid.equals("")){
	System.out.println("hb.mr linkid is null");
	return;
}
String stat=request.getParameter("stat")==null?"":request.getParameter("stat");
if(azul.KeyTool.mr(linkid,stat,"DELIVRD")){
	out.println("OK");
}
else{
	out.println("ERR");
}
%>