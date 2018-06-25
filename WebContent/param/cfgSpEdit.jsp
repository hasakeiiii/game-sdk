<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<jsp:include page="../check.jsp?check=cfgSpEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int cfg_sp_id=azul.JspUtil.getInt(request.getParameter("cfg_sp_id"),0);
String sp_name="";//SP名称
String sp_code="";//SP代号
String sp_user="";//SP登录名称
String sp_pass="";//SP登录密码
String sp_address="";//SP地址
double sp_rate=0.0;//分成比例
String sp_link="";//联系人
if(cfg_sp_id!=0){
    pageTitle="编辑信息ID:"+cfg_sp_id;
    CfgSpDao cfgSpDao=new CfgSpDao();
    CfgSp cfgSp=(CfgSp)cfgSpDao.getById(cfg_sp_id);
    sp_name=cfgSp.getSpName();
    sp_code=cfgSp.getSpCode();
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
    if('<%=cfg_sp_id%>'=="0"){
        document.mainForm.action="cfgSpAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="cfgSpAction.jsp?op=edit&pageno=<%=pageno%>&cfg_sp_id=<%=cfg_sp_id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_sp_name">SP名称</td>
    <td width="33%"><input name="sp_name" alt="sp_name" value="<%=sp_name%>" maxlength="250"/></td>
<td>
<span id="warn_sp_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_sp_code">SP代号</td>
    <td width="33%"><input name="sp_code" alt="sp_code" value="<%=sp_code%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
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