<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="azul.JspUtil"%>
<%

String linkid=request.getParameter("linkid")==null?"":request.getParameter("linkid");
if(linkid.equals("")){
	System.out.println("wshs linkid is null");
	return;
}

String mobile=request.getParameter("mobile")==null?"":request.getParameter("mobile");
String msg=request.getParameter("momsg")==null?"":request.getParameter("momsg");
String spNum=request.getParameter("spNum")==null?"":request.getParameter("spNum");

System.out.println("messge-----" + msg);
%>