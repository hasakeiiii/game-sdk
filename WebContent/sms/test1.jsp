<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="azul.JspUtil"%>
<%
if(azul.SpTool.charge_ok("1111111111","1004",1,"13500000000","23k","106688998")){
	out.print("1");
}
else{
	out.print("0");
}
%>