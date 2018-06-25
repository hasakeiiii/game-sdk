<%@ page contentType="text/html;charset=gb2312"%>
<%
//http://124.232.156.42:8080/sms/zsyj_mingwei_1012.jsp?mo_from=13885248629&content=9MZC4A&mo_to=10665366001&linkid=161717291373921202211&status=1
//azul.JspUtil.p(request);

String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("mingwei linkid is null");
	return;
}
String status=request.getParameter("status")==null?"":request.getParameter("status");
String mobile=request.getParameter("mo_from")==null?"":request.getParameter("mo_from");
String msg=request.getParameter("content")==null?"":request.getParameter("content");
String spNum=request.getParameter("mo_to")==null?"":request.getParameter("mo_to");
int fee=1;
if(!"1".equals(status)){
	System.out.println("mingwei status is not 1");
	return;
}
if(azul.SpTool.charge_ok(linkid,"1012",fee,mobile,msg,spNum)){
	out.print("1");
}
else{
	out.print("0");
}
%>