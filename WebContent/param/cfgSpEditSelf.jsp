<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<%
String sessionSid=azul.JspUtil.getStr(session.getAttribute("sid"),"");
if("".equals(sessionSid)){
   %>
   <script>error("无法得到SP编号");</script>
   <%
   return;
}
CfgSpDao cfgSpDao=new CfgSpDao();
CfgSp cfgSp=(CfgSp)cfgSpDao.loadBySql("select * from cfg_sp where sp_code='"+sessionSid+"'");

int cfg_sp_id=cfgSp.getCfgSpId();
String pageTitle="编辑信息ID:"+cfg_sp_id;
String sp_name=cfgSp.getSpName();
String sp_code=cfgSp.getSpCode();
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
    if(document.mainForm.sp_pass.value!="" && document.mainForm.sp_pass.value!=document.mainForm.re_pass.value){
       error("两次输入密码不相同");
       return;
    }
    document.mainForm.action="cfgSpAction.jsp?op=editSelf&cfg_sp_id=<%=cfg_sp_id%>";
    document.mainForm.submit();
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
    <td width="33%"><input name="sp_code" alt="sp_code" readonly value="<%=sp_code%>" maxlength="250"/></td>
<td>
<span id="warn_sp_code"></span></td>
</tr>
  <tr align=center>
    <td colspan=3>
        <a href="#" onClick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a>
    </td>
  </tr>
</table>
</form>
</body>
</html>
