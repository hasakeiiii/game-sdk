<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ include file="../check.jsp"%>
<%
String pageTitle="编辑信息";
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int sys_warn_id=JspUtil.getInt(request.getParameter("sys_warn_id"),0);
int kind=0;//提醒类型
String key="";//所属子项
String address="";//提醒地址
if(sys_warn_id!=0){
    pageTitle="编辑信息ID:"+sys_warn_id;
    SysWarnDao sysWarnDao=new SysWarnDao();
    SysWarn sysWarn=(SysWarn)sysWarnDao.getById(sys_warn_id);
    kind=sysWarn.getKind();
    key=sysWarn.getKeyword();
    address=sysWarn.getAddress();
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
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="33%" id="title_kind">警告类型</td>
    <td width="33%"><input type="radio" value="0" name="kind" checked="checked"/> 邮件提醒<input type="radio" value="1" name="kind"/> 短信提醒</td>
<td>
<span id="warn_kind"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_key">警告内容<span class="tdRed">不要重复的页面调用关键字</span></td>
    <td width="33%">
      <textarea name="key" alt="key" cols="40" rows="5"><%=key%></textarea></td>
    <td>
<span id="warn_key"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_address">警告地址<br>
      <span class="tdRed">(如果是短信请输入手机号码，如果是邮件请输入邮件地址。<br>多个地址以英文，区分</span></td>
    <td width="33%"><textarea name="address" alt="address" cols="40" rows="5"><%=address%></textarea></td>
<td>
<span id="warn_address"></span></td>
</tr>
  <tr align=center>
    <td colspan=3>
        <a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="保存" align="absmiddle"/></a>&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>    </td>
  </tr>
</table>
</form>
</body>
</html>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script language="javascript">
initElem("kind","<%=kind%>");
window.onload = function() {
	CheckForm.formBind("mainForm");
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    var paramKey="";
    if('<%=sys_warn_id%>'=="0"){
        document.mainForm.action="sysWarnAction.jsp?op=add"+paramKey;
        document.mainForm.submit();
    }
    else{
        document.mainForm.action="sysWarnAction.jsp?op=edit&pageno=<%=pageno%>&sys_warn_id=<%=sys_warn_id%>"+paramKey;
        document.mainForm.submit();
    }
}
</script>
