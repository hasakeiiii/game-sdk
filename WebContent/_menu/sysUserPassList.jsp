<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<%
SysUserDao sysUserDao=new SysUserDao();
ArrayList list=sysUserDao.getList("select * from sys_user order by role");
SysUser sessionSysUser=(SysUser)session.getAttribute("sysUser");
SysRoleDao sysRoleDao=new SysRoleDao();
SysRole sysRole=(SysRole)sysRoleDao.loadBySql("select * from sys_role where sys_user_id=" + sessionSysUser.getSysUserId());
String Privilege="";
if(sysRole!=null){
	Privilege=sysRole.getSysMenuSub();
}

ArrayList roleList=sysRoleDao.getList();
SysMenuMainDao sysMenuMainDao=new SysMenuMainDao();
ArrayList listMain=sysMenuMainDao.getList("select * from sys_menu_main order by sort");
SysMenuSubDao sysMenuSubDao=new SysMenuSubDao();
%>
<body>
<h1>系统用户密码角色</h1>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" height="49" class="table_noborder">
<tr>
<td><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a>&nbsp;&nbsp;</td>
</tr>
</table>
<table width="100%" border="0" class="table_noborder">
  <tr>
    <td width="36%" align="center" valign="top">
	<table id="TableColor" width="100%" border="0">
      <tr>
        <td>序号</td>
        <td>用户名称</td>
        <td>用户角色</td>
        </tr>
      <%
	for(int i=0;i<list.size();i++){
		SysUser sysUser=(SysUser)list.get(i);
		String username=sysUser.getUsername();
	%>
      <tr>
        <td><%=i+1%><input type="radio" name="user" value="<%=sysUser.getSysUserId()%>"/></td>
        <td><%=username%></td>
        <td><%=sysUser.getRole()%></td>
      </tr>
    <%
	}
	%>
    </table></td>
    <td width="32%" align="center" valign="top">
		<h1>修改用户登陆名</h1>
      用户登陆名&nbsp;&nbsp;
      <input name="username" />
      <br>
      <p>&nbsp;
	  <input type="button" name="textfield23" value="确定修改" onclick="saveName()"/></p><br />
	<h1>修改用户密码</h1>
      用户旧密码&nbsp;&nbsp;
      <input type="password" name="oldPass" />
      <br>
      &nbsp;
      用户新密码&nbsp;&nbsp;<input type="password" name="userpass" /><br>
      &nbsp;
	  请重复密码&nbsp;&nbsp;<input type="password" name="rePass" /><p>&nbsp;
	  <input type="button" name="textfield2" value="确定修改" onclick="save()"/></p><br />
	  
<h1>用户角色</h1>
      <select name="role" id="role" style="width:120px">
  <option value="">请选择</option>
  <option value="admin">管理员</option>
  <option value="cid">厂商</option>
  <option value="sell">业务员</option>
  <option value="edit">编辑</option>
</select>
<p><input type="button" value="确定修改" onclick="saveRole()"/></p>	  
	  </td>
  </tr>
</table>
</form>
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script>
function addAct(){
    window.location="sysUserEdit.jsp";
}
function save(){
    var key=getRadio("user");
	if(key==""){
	    error("请选择需要修改的用户");
		return;
	}
	if(document.mainForm.oldPass.value==""){
	    error("请输入旧密码");
		return;
	}
	if(document.mainForm.userpass.value==""){
	    error("请输入新密码");
		return;
	}
	if(document.mainForm.rePass.value==""){
	    error("请输入重复密码");
		return;
	}
	if(document.mainForm.userpass.value!=document.mainForm.rePass.value){
	    error("两次输入密码不相同");
		return;
	}
	document.mainForm.action="sysUserAction.jsp?op=editPass&sys_user_id="+key;
    document.mainForm.submit();	
}
function saveRole(){
    var key=getRadio("user");
	if(key==""){
	    error("请选择需要修改的用户");
		return;
	}

	location="sysUserAction.jsp?op=saveRole&sys_user_id="+key+"&role="+getElem("role");
}
function saveName(){
    var key=getRadio("user");
	if(key==""){
	    error("请选择需要修改的用户");
		return;
	}
	if(document.mainForm.username.value==""){
	    error("请输入用户名");
		return;
	}
	if(document.mainForm.username.value.toLowerCase().indexOf("admin")!=-1){
		error("用户名不能包含admin字符");
		return;
	}
	location="sysUserAction.jsp?op=editName&sys_user_id="+key+"&username="+document.mainForm.username.value;
}
</script>
</body>
</html>
