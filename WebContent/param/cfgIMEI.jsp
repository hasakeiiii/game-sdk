<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DevicePayDao  devicePayDao=new DevicePayDao();

String imei=azul.JspUtil.getStr(request.getParameter("imei"),"");
	 

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="90%">查询IMEI&nbsp;&nbsp;<input name="imei" value="<%=imei%>" />&nbsp;&nbsp;<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td><td width="10%" align="right"></td></tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>IMEI</td>
<td>已计费金额</td>
<td>时间</td>
<td>已计费金额(月)</td>
</tr>
<%

if(!imei.equals("")){
		  
	DevicePay devicePay = devicePayDao.getRecord(imei);
	int amount = devicePay.getAmount()/100;
	int monthAmount = devicePay.getMonthAmount()/100;
	
%>
<tr>

<td><%=devicePay.getImei()%></td>
<td><%=amount%>元</td>
<td><%=devicePay.getDate()%></td>
<td><%=monthAmount%>元</td>
</tr>
<%
} 

%>
</table>
</form>

<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
   
}

function infoAct(id){
    //window.location="cfgSpInfo.jsp?id="+id;
}
function goSearch(){
    if(document.mainForm.imei.value==""){
	     error("请输入IMEI");
		 document.mainForm.imei.focus();
		 return;
	}
	document.mainForm.action="cfgIMEI.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
