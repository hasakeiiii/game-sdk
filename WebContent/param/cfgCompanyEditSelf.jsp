<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<jsp:include page="../check.jsp?check_role=cid" flush="true" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<%
String sessionCid=azul.JspUtil.getStr(session.getAttribute("cid"),"");
if("".equals(sessionCid)){
   %>
   <script>error("无法得到厂商编号");</script>
   <%
   return;
}
CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
CfgCompany cfgCompany=(CfgCompany)cfgCompanyDao.loadBySql("select * from cfg_company where cid='"+sessionCid+"'");
int cfg_company_id=cfgCompany.getCfgCompanyId();
String pageTitle="编辑信息ID:"+cfg_company_id;
String name=cfgCompany.getName();
String cid=cfgCompany.getCid();
String cid_user=cfgCompany.getCidUser();
String cid_pass=cfgCompany.getCidPass();
double rate=cfgCompany.getRate();
String address=cfgCompany.getAddress();
String contact=cfgCompany.getContact();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script language="javascript">
window.onload = function() {
	CheckForm.formBind("mainForm");
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if(document.mainForm.cid_pass.value!="" && document.mainForm.cid_pass.value!=document.mainForm.re_pass.value){
       error("两次输入密码不相同");
       return;
    }
    document.mainForm.action="cfgCompanyAction.jsp?op=editSelf&cfg_company_id=<%=cfg_company_id%>";
    document.mainForm.submit();
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_cid">公司CID</td>
    <td width="33%"><input name="cid" alt="cid" value="<%=cid%>" readonly maxlength="33"/></td>
<td>
<span id="warn_cid"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_name">公司名称</td>
    <td width="33%"><input name="name" alt="name" value="<%=name%>" readonly maxlength="250"/></td>
<td>
<span id="warn_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cid_user">登录用户名</td>
    <td width="33%"><input name="cid_user" alt="cid_user" value="<%=cid_user%>" maxlength="250"/></td>
<td>
<span id="warn_cid_user"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cid_pass">登录密码</td>
    <td width="33%"><input name="cid_pass" type="password" value="" maxlength="250"/></td>
<td>
<span id="warn_cid_pass"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_cid_pass">重复密码</td>
    <td width="33%"><input name="re_pass" type="password" value="" maxlength="250"/></td>
<td>
<span id="warn_cid_pass"></span></td>
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
