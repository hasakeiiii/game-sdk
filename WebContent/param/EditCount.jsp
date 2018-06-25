<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
String packet_id=azul.JspUtil.getStr(request.getParameter("packet_id"),"");
String datestr=azul.JspUtil.getStr(request.getParameter("datestr"),"");
String ratio=azul.JspUtil.getStr(request.getParameter("ratio"),"");
//String action=azul.JspUtil.getStr(request.getParameter("op"),"");
//datestr="2014-03-21";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<script type="text/javascript" src="../_js/dateTimePacker.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="10%">包体ID&nbsp;&nbsp;<input name="packet_id" value="<%=packet_id%>" />&nbsp;&nbsp;
</td>
<td width="10%">日期&nbsp;&nbsp;
<input name="datestr" value="<%=datestr%>" />&nbsp;&nbsp;</td>
<td width="10%">比例&nbsp;&nbsp;<input name="ratio" value="<%=ratio%>" />&nbsp;&nbsp;
</td>

<td width="10%">
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"/>
</a></td>
</tr>
</table>

</form>

<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
   
}
function editAct(id){
    //window.location="notisfyCPAction.jsp?id="+id;
	//PayDao  payDao=new PayDao();
}
function infoAct(id){
    //window.location="cfgSpInfo.jsp?id="+id;
}
function goSearch(){
    /*if(document.mainForm.keyWord.value==""){
	     error("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}*/
	document.mainForm.action="EditCountAction.jsp?op=ation";
	document.mainForm.submit();
}
</script>
</body>
</html>
