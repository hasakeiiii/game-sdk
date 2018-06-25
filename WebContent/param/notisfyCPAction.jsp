<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>

<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
<%

PayDao paydao=new PayDao();
int id=azul.JspUtil.getInt(request.getParameter("id"),1);
Pay pay =(Pay)paydao.getById(id);


String act_flag="-1";
String msg="补发失败";
String toPage="cfgUserPayList.jsp?";
String op=azul.JspUtil.getStr(request.getParameter("op"),"");


int ret = 0;
if(pay.getState() == 1)
{
	ret = pay.rsqCallbackUrl(0);
	paydao.update(pay);
}
if(ret == 1)
{
	act_flag="1";
}
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=act_flag%>"=="-1"){
    error("<%=msg%>",callback);
}
else{
	ok("补发成功",callback);
}
function callback(){
	location="<%=toPage%>";
}
</script>
</body>
</html>