<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check=VagueEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int id=azul.JspUtil.getInt(request.getParameter("id"),0);

String name="";
String no="";
String shield_region = "";
String shield_time = "";
int limit_money = 0;
int limit_user_money = 0;
String test = "";

if(id!=0){
    pageTitle="编辑信息ID:"+id;
	PayCompanyDao payCompanyDao=new PayCompanyDao();
	PayCompany payCompany=(PayCompany)payCompanyDao.getById(id);
    name = payCompany.getName();
    no = payCompany.getNo();
    shield_region = payCompany.getShieldRegion();
    shield_time = payCompany.getShieldTime();
    limit_money = payCompany.getLimitMoney();
    limit_user_money = payCompany.getLimitUserMoney();
    test = payCompany.getTest();

}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
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
    if('<%=id%>'=="0"){
        document.mainForm.action="PayCompanyAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="PayCompanyAction.jsp?op=edit&pageno=<%=pageno%>&id=<%=id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_no">计费公司标示</td>
    <td width="33%"><input name="no" alt="no" value="<%=no%>" maxlength="250"/></td>
<td>
<span id="warn_sp_name">要写公司的小写首字母</span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_name">计费公司名称</td>
    <td width="33%"><input name="name" alt="name" value="<%=name%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_limit_money">总限额</td>
    <td width="33%"><input name="limit_money" alt="limit_money" value="<%=limit_money%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_limit_user_money">单人限额</td>
    <td width="33%"><input name="limit_user_money" alt="limit_user_money" value="<%=limit_user_money%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_shield_region">屏蔽地区</td>
    <td width="33%"><input name="shield_region" alt="shield_region" value="<%=shield_region%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span>不填代表屏蔽全国</td>
</tr>
<tr align="center">
    <td width="33%" id="title_shield_time">屏蔽时间</td>
    <td width="33%"><input name="shield_time" alt="shield_time" value="<%=shield_time%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_test">备注</td>
    <td width="33%"><input name="test" alt="test" value="<%=test%>" maxlength="250"/></td>
<td>
<span id="warn_sp_no"></span></td>
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