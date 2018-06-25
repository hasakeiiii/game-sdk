<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ include file="../check.jsp"%>
<%
int cfg_log_id=azul.JspUtil.getInt(request.getParameter("cfg_log_id"),0);
CfgLogDao cfgLogDao=new CfgLogDao();
CfgLog cfgLog=(CfgLog)cfgLogDao.getById(cfg_log_id);
String param=cfgLog.getParam();
String date_time=cfgLog.getDateTime();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<h1>信息ID:<%=cfg_log_id%></h1>
<table width="100%" border="0">
<tr align="center">
    <td width="50%">参数</td>
    <td><%=param%></td>
</tr>
<tr align="center">
    <td width="50%">时间</td>
    <td><%=date_time%></td>
</tr>
  <tr>
    <td colspan="2" align="center">
        <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>
    </td>
  </tr>
</table>
</body>
</html>
