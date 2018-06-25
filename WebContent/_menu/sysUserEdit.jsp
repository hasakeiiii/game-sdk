<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="model.*" %>
<%@ page import="dao.*" %>
<jsp:include page="../check.jsp?check=sysUserEdit.jsp" flush="true" />
<%
int id=azul.JspUtil.getInt(request.getParameter("id"),0);
String username="";
String userpass="";
String userrole="";
if(id!=0){
    UserinfoDao userinfoDao=new UserinfoDao();
    Userinfo sysUser=(Userinfo)userinfoDao.getById(id);
    username=sysUser.getUsername();
    userpass=sysUser.getPass();
    userrole=sysUser.getRole();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/Check.js"></script>
<script type="text/javascript" src="../_js/CheckForm.js"></script>
<script language="javascript">
function btnSave(){
    if(!CheckForm.checkForm("mainForm"))return;
    if('<%=id%>'=="0"){
        document.mainForm.action="sysUserAction.jsp?op=add";
        document.mainForm.submit();
    }
    else{//编辑功能
        document.mainForm.action="sysUserAction.jsp?op=edit&id=<%=id%>";
        document.mainForm.submit();
    }
}
</script>
<h1>添加系统用户</h1>
<form name="mainForm" id="mainForm" method="post"  style="margin:0;padding:0">
<table width="100%" border="0">
<tr align="center">
    <td width="50%" id="title_username">用户帐号</td>
    <td><input name="username" alt="username" value="<%=username%>"/><span id="warn_username"></span></td>
</tr>
<tr align="center">
    <td width="50%">用户角色</td>
    <td>
    <select name="role" id="role">
  <option value="<%=userrole%>">请选择</option>
  <option value="admin">管理员</option>
  <option value="cid">厂商</option>
  <option value="sid">SP</option>
  <option value="sell">商务</option>
  <option value="edit">编辑</option>
  <option value="tech">技术</option>
  <option value="prod">产品</option>
</select>
    </td>
</tr>
<tr align="center">
    <td width="50%" id="title_userpass">用户密码</td>
    <td><input name="userpass" type="text" alt="userpass" value="<%=userpass%>"/><span id="warn_userpass"></span></td>
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
