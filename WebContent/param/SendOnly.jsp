<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="dao.*,java.math.*,org.jfree.chart.*,org.jfree.chart.plot.PiePlot,org.jfree.chart.title.TextTitle,org.jfree.chart.labels.*,  
org.jfree.data.general.DefaultPieDataset,java.text.NumberFormat"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />

<%
	String pageTitle = "专属币发放";
	int pageno = azul.JspUtil.getInt(request.getParameter("pageno"), 1);
	int id = azul.JspUtil.getInt(request.getParameter("id"), 0);

	String username = "";
	String amount = "";
	String gameNo = azul.JspUtil.getStr(request.getParameter("gameno"),
			"162");
	String tpayno = "";
	String gsno =  azul.JspUtil.getStr(request.getParameter("gsno"),"275");
	AppDao appdao = new AppDao();
	App app = appdao.getAppRecord(gameNo);
	String onlyoff = app.off.toString();
	String operator = "涂天聪";
	
	UserinfoDao userinfoDao = new UserinfoDao();
	HashMap gsnoMap = new HashMap();	
	String gssql = "select id,concat(username,'(',CAST(id AS CHAR),')') from userinfo where 1=1 and role='GS'";
	
	gsnoMap = userinfoDao.getSelectMap(gssql);
	HashMap gameMap = new HashMap();
	String appsql = "select no,concat(name,'(',no,')') from app where 1=1 and game_type='on_line'";
	gameMap = appdao.getSelectMap(appsql);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css" />

</head>

<body>
	<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
		media="screen" />
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
	<script type="text/javascript" src="../_js/Check.js"></script>
	<script type="text/javascript" src="../_js/CheckForm.js"></script>
	<script language="javascript">
	window.onload = function() {
	CheckForm.formBind("mainForm");
	}
	function btnSave(){
    	if(!CheckForm.checkForm("mainForm"))return;
        document.mainForm.action="SendOnlyAction.jsp";
        document.mainForm.submit();
    	
		}
	</script>


	<h1><%=pageTitle%></h1>

	<form name="mainForm"  id="mainForm" method="post" style="margin:0;padding:0">
		<table>
			<tr align="center">
				<td width="33%" id="title_gameno">游戏名称</td>
				<td width="33%"><select name="gameno" id="gameno"
					onchange=gamenochg(this)></select></td>
			</tr>
			<tr align="center">
				<td width="33%" id="title_username">拇指账号</td>
				<td width="33%"><input name="username" alt="username"
					value="<%=username%>" maxlength="250" /></td>
			</tr>

			<tr align="center">
				<td width="33%" id="title_amount">充值金额</td>
				<td width="33%"><input name="amount" alt="amount"
					value="<%=amount%>" maxlength="250" /></td>
			</tr>
			
			<tr align="center">
				<td width="33%" id="title_tpayno">第三方支付订单号</td>
				<td width="33%"><input name="tpayno" alt="tpayno"
					value="<%=tpayno%>" maxlength="250" /></td>
			</tr>

			<tr align="center">
				<td width="33%" id="title_onlyoff">折扣</td>
				<td width="33%"><input name="onlyoff" alt="onlyoff"
					value="<%=onlyoff%>" maxlength="250" /></td>
			</tr>

			<tr align="center">
				<td width="33%" id="title_gsno">充值GS</td>
				<td width="33%"><select name="gsno" id="gsno"></select></td>
			</tr>
			<tr align="center">
				<td width="33%" id="title_operator">操作员</td>
				<td width="33%"><input name="operator" alt="operator"
					value="<%=operator%>" maxlength="250" /></td>
			</tr>
		</table>
		<table>
			<tr align=center>
				<td colspan=3><a href="#" onclick="btnSave()"><img
						src="../_js/ico/btn_save.gif" border="0" alt="保存"
						align="absmiddle" /> </a>&nbsp;&nbsp;&nbsp; <a href="#"
					onclick="javascript:history.back()"><img
						src="../_js/ico/btn_back.gif" border="0" alt="返回"
						align="absmiddle" /> </a>
				</td>
			</tr>
		</table>
	</form>



	<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
		media="screen" />


	<script type="text/javascript" src="../_js/elemUtil.js"></script>
	<script type="text/javascript">
		function gamenochg(obj) {
			document.mainForm.action = "SendOnly.jsp";
			document.mainForm.submit();
		}
	</script>
</body>
</html>
<script type="text/javascript">
	
<%
	out.println(azul.JspUtil.initSelect("gameno", gameMap, gameNo));
	out.println(azul.JspUtil.initSelect("gsno", gsnoMap, gsno));

%>
	
</script>
