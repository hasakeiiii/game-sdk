<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check=sysMenuSubEdit.jsp" flush="true" />
<%
String pageTitle="添加信息";
int sys_menu_main_id=azul.JspUtil.getInt(request.getParameter("sys_menu_main_id"),0);
String name=azul.JspUtil.getStr(request.getParameter("name"),"");
String param="sys_menu_main_id="+sys_menu_main_id+"&name="+name;
int sys_menu_sub_id=azul.JspUtil.getInt(request.getParameter("sys_menu_sub_id"),0);
String title="";
String links="";
int display=1;
int color=0;


SysMenuMainDao sysMenuMainDao=new SysMenuMainDao();
java.util.Map<String,String> menuMap = new HashMap<String, String>();
menuMap=sysMenuMainDao.getSelectMap("select sys_menu_main_id,name from sys_menu_main");

if(sys_menu_sub_id!=0){
	pageTitle="编辑信息ID:"+sys_menu_sub_id;
    SysMenuSubDao sysMenuSubDao=new SysMenuSubDao();
    SysMenuSub sysMenuSub=(SysMenuSub)sysMenuSubDao.getById(sys_menu_sub_id);
    title=sysMenuSub.getTitle();
	links=sysMenuSub.getLinks();
	display=sysMenuSub.getDisplay();
	color=sysMenuSub.getColor();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/></head>
<body>
<h1><%=pageTitle%></h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
				<td width="50%" id="title_sys_menu_main_id">所属菜单</td>
				<td width="50%">
				<select name="sys_menu_main_id" id="sys_menu_main_id">
						<option value="" size="50">请选择</option>
				</select></td>
				<td><span id="warn_sys_menu_main_id"></span></td>
</tr>
<tr align="center">
    <td width="50%" id="title_title">子菜单名称</td>
    <td><input name="title" value="<%=title%>" size="50"  maxlength="25" alt="title"/>
    <span id="warn_title"></span></td>
</tr>
<tr align="center">
    <td width="50%" id="title_links">子菜单链接</td>
    <td><input name="links" value="<%=links%>" size="50"  maxlength="100" alt="links"/>
    <span id="warn_links"></span></td>
</tr>
<tr align="center">
    <td width="50%">菜单颜色</td>
    <td><input type="radio" name="color" value="0" checked="checked"/>
      未设定&nbsp;&nbsp;&nbsp;
      <input type="radio" name="color" value="1"/>
      <span class="tdRed">红色</span>
      <input type="radio" name="color" value="2"/>
      <span class="tdOrgen">桔色</span>
      <input type="radio" name="color" value="3"/>
<span class="tdBlue">蓝色</span>
<input type="radio" name="color" value="4"/>
<span class="tdGreen">绿色</span></td>
</tr>
<tr align="center">
  <td>管理员是否可见</td>
  <td><input type="radio" name="display" value="0"/>
    不可见
    &nbsp;&nbsp;&nbsp;
    <input type="radio" name="display" value="1"/>
可见 </td>
</tr>
  <tr align=center>
    <td colspan=2>
        <a href="#" onclick="btnSave()"><img src="../_js/ico/btn_save.gif" border="0" alt="确定" align="absmiddle"/></a>&nbsp;&nbsp;&nbsp;
        <a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a>    </td>
  </tr>
</table>
</form>
</body>
</html>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>

<script type="text/javascript">
<%out.println(azul.JspUtil.initSelect("sys_menu_main_id",menuMap, sys_menu_main_id));%>
</script>

<script language="javascript">
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if("<%=sys_menu_sub_id%>"=="0"){
        document.mainForm.action="sysMenuSubAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="sysMenuSubAction.jsp?op=edit&<%=param%>&sys_menu_sub_id=<%=sys_menu_sub_id%>";
        document.mainForm.submit();
    }
}
initElem("display","<%=display%>");
initElem("color","<%=color%>");
</script>
