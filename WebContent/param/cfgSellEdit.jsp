<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<jsp:include page="../check.jsp?check=cfgSellEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int cfg_sell_id=azul.JspUtil.getInt(request.getParameter("cfg_sell_id"),0);
String name="";//销售员名称
String sell_user="";//登录用户名
String sell_pass="";//登录密码
String address="";//销售员信息
String contact="";//联系方式
if(cfg_sell_id!=0){
    pageTitle="编辑信息ID:"+cfg_sell_id;
    CfgSellDao cfgSellDao=new CfgSellDao();
    CfgSell cfgSell=(CfgSell)cfgSellDao.getById(cfg_sell_id);
    name=cfgSell.getName();
    sell_user=cfgSell.getSellUser();
    sell_pass=cfgSell.getSellPass();
    address=cfgSell.getAddress();
    contact=cfgSell.getContact();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
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
    if('<%=cfg_sell_id%>'=="0"){
        document.mainForm.action="cfgSellAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="cfgSellAction.jsp?op=edit&pageno=<%=pageno%>&cfg_sell_id=<%=cfg_sell_id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_name">销售员名称</td>
    <td width="33%"><input name="name" alt="name" value="<%=name%>" maxlength="250"/></td>
<td>
<span id="warn_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_sell_user">登录用户名</td>
    <td width="33%"><input name="sell_user" alt="sell_user" value="<%=sell_user%>" maxlength="250"/></td>
<td>
<span id="warn_sell_user"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_sell_pass">登录密码</td>
    <td width="33%"><input name="sell_pass" type="password" <%if(cfg_sell_id==0)out.print("alt=\"sell_pass\"");%>/></td>
<td>
<span id="warn_sell_pass"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_re_pass">重复密码</td>
    <td width="33%"><input name="re_pass" type="password" <%if(cfg_sell_id==0)out.print("alt=\"sell_pass\"");%>/></td>
<td>
<span id="warn_re_pass"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_address">销售员信息</td>
    <td width="33%"><input name="address" value="<%=address%>" maxlength="250"/></td>
<td>
<span id="warn_address"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_contact">联系方式</td>
    <td width="33%"><input name="contact" value="<%=contact%>" maxlength="250"/></td>
<td>
<span id="warn_contact"></span></td>
</tr>
  <tr align=center>
    <td colspan=3>
        <a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a>&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>
    </td>
  </tr>
</table>
</form>
</body>
</html>
