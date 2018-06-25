<%@ page contentType="text/html;charset=gb2312"%>
<%
String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
System.out.println("guanghua.mo 1019-------------------");
if(linkid.equals("")){
	System.out.println("guanghua linkid is null");
	return;
}
String mobile =request.getParameter("mobile")==null?"":request.getParameter("mobile");
String msg =request.getParameter("msg")==null?"":request.getParameter("msg");
String spnumber =request.getParameter("spnumber")==null?"":request.getParameter("spnumber"); 
if(azul.SpTool.charge_ok(linkid,"1019",1,mobile,msg,spnumber)){
	out.println("OK");
}
%>