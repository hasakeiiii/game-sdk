<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ include file="../check.jsp"%>
<%
String pageTitle="编辑信息";
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int sys_hint_id=JspUtil.getInt(request.getParameter("sys_hint_id"),0);
String page_name="";//提示页面
String info="";//内容
int ok=1;
if(sys_hint_id!=0){
    pageTitle="编辑信息ID:"+sys_hint_id;
    SysHintDao sysHintDao=new SysHintDao();
    SysHint sysHint=(SysHint)sysHintDao.getById(sys_hint_id);
    page_name=sysHint.getPageName();
    info=sysHint.getInfo();
    info=azul.JspUtil.undecode(info);
	ok=sysHint.getOk();
}
SysHintDao sysHintDao=new SysHintDao();
java.util.Map map=sysHintDao.getSelectMap("select links,concat(title,'(',links,')') from sys_menu_sub");
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
    <td width="33%" id="title_page_name">提示页面</td>
    <td width="33%">
	 <select name="page_name" id="page_name">
        <option value="-1">请选择</option>
        <option value="_menu/frame_right.jsp">系统初始页面(frame_right.jsp)</option>
      </select></td>
<td>
<span id="warn_page_name"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_ok">提醒类型</td>
    <td width="33%"><input type="radio" value="0" name="ok" checked="checked"/> 无效<input type="radio" value="1" name="ok"/> 有效</td>
<td>
<span id="warn_ok"></span></td>
</tr>
<tr align="center">
    <td width="33%" id="title_info">内容</td>
    <td width="33%"><textarea name="info" cols="30" rows="8" alt="info"><%=info%></textarea>
    </td>
<td>
<span id="warn_info"></span></td>
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
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script language="javascript">
initElem("ok","<%=ok%>");
window.onload = function() {
	CheckForm.formBind("mainForm");
}
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    var paramKey="";
    if('<%=sys_hint_id%>'=="0"){
        document.mainForm.action="sysHintAction.jsp?op=add"+paramKey;
        document.mainForm.submit();
    }
    else{
        document.mainForm.action="sysHintAction.jsp?op=edit&pageno=<%=pageno%>&sys_hint_id=<%=sys_hint_id%>"+paramKey;
        document.mainForm.submit();
    }
}
<%
out.println(azul.JspUtil.initSelect("page_name",map,page_name));
%>
</script>
