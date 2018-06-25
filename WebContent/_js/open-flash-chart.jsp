<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>信息统计饼状图</title>
</head>
<body>
<%
String flashType=azul.JspUtil.getStr(request.getParameter("flashType"),"pie");
if("pie".equals(flashType)){
%>
	<%=common.ChartFlash.chartPie(request.getParameter("title"),request.getParameter("info"),request.getParameter("rowStr"),request.getParameter("colStr")).render()%>
<%
}
else{
	%>
	<%=common.ChartFlash.chartBar(request.getParameter("title"),request.getParameter("info"),request.getParameter("max"),request.getParameter("rowStr"),request.getParameter("colStr")).render()%>
<%
}
%>
</body>
</html>