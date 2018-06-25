<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check=sysMenuMainEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int sys_menu_main_id=azul.JspUtil.getInt(request.getParameter("sys_menu_main_id"),0);
String name="";
if(sys_menu_main_id!=0){
	pageTitle="编辑信息ID:"+sys_menu_main_id;
    SysMenuMainDao sysMenuMainDao=new SysMenuMainDao();
    SysMenuMain sysMenuMain=(SysMenuMain)sysMenuMainDao.getById(sys_menu_main_id);
    name=sysMenuMain.getName();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/></head>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script language="javascript">
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if("<%=sys_menu_main_id%>"=="0"){
        document.mainForm.action="sysMenuMainAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="sysMenuMainAction.jsp?op=edit&sys_menu_main_id=<%=sys_menu_main_id%>";
        document.mainForm.submit();
    }
}
</script>
<body>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="50%" id="title_name">主菜单名称</td>
    <td><input name="name" value="<%=name%>" size="50" maxlength="25" alt="name"/>
    <span id="warn_name"></span></td>
</tr>
  <tr align=center>
    <td colspan=2>
        <a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="确定" align="absmiddle"/></a>&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>
    </td>
  </tr>
</table>
</form>
</body>
</html>
