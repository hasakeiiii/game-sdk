<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ include file="../check.jsp?check=cfgBlackEdit.jsp"%>
<%
String pageTitle="添加信息";
int kind=0;
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int cfg_black_id=azul.JspUtil.getInt(request.getParameter("cfg_black_id"),0);
String mobile="";//手机号码
if(cfg_black_id!=0){
    pageTitle="编辑信息ID:"+cfg_black_id;
    CfgBlackDao cfgBlackDao=new CfgBlackDao();
    CfgBlack cfgBlack=(CfgBlack)cfgBlackDao.getById(cfg_black_id);
    mobile=cfgBlack.getMobile();
    kind=cfgBlack.getKind();
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
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script language="javascript">
window.onload = function() {
	CheckForm.formBind("mainForm");
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if('<%=cfg_black_id%>'=="0"){
        document.mainForm.action="cfgBlackAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="cfgBlackAction.jsp?op=edit&pageno=<%=pageno%>&cfg_black_id=<%=cfg_black_id%>";
        document.mainForm.submit();
    }
}
</script>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_kind">屏蔽类型</td>
    <td width="33%"><input name="kind" type="radio" value="0" checked="checked" />
    扣费<input name="kind" type="radio" value="1" />
    上行短信中心号码
    <input name="kind" type="radio" value="2" />
    上行MID</td>
    <td>
<span id="warn_kind"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_mobile">手机号码</td>
    <td width="33%"><input name="mobile" alt="mobile" value="<%=mobile%>" maxlength="250"/></td>
<td>
<span id="warn_mobile"></span></td>
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
<script>
initElem("kind","<%=kind%>");
</script>
