<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<jsp:include page="../check.jsp" flush="true"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<%
SysRoleMenu sysRoleMenu=new SysRoleMenu();
//int sys_role_menu_id=common.JspUtil.getInt(request.getParameter("sys_role_menu_id"));
//String sys_role=common.JspUtil.getStr(request.getParameter("sys_role"));
//int sys_menu_sub_id=common.JspUtil.getInt(request.getParameter("sys_menu_sub_id"));
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
JspUtil.populate(sysRoleMenu, request);
SysRoleMenuDao sysRoleMenuDao=new SysRoleMenuDao();

String act_flag="-1";
String msg="操作失败";
String toPage="sysRoleMenuList.jsp?pageno="+pageno+"&user_role="+sysRoleMenu.getSysRole();
SysRoleMenu sysRoleMenuTemp=(SysRoleMenu)sysRoleMenuDao.loadBySql("select * from sys_role_menu where sys_role='"+sysRoleMenu.getSysRole()+"'");
if(sysRoleMenuTemp==null){
	act_flag=sysRoleMenuDao.add(sysRoleMenu);
}
else{
	sysRoleMenu.setSysRoleMenuId(sysRoleMenuTemp.getSysRoleMenuId());
	act_flag=sysRoleMenuDao.edit(sysRoleMenu);
}
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
if("<%=act_flag%>"=="-1"){
    error("<%=msg%>",callback);
}
else{
	ok("操作成功",callback);
}
function callback(){
	location="<%=toPage%>";
}
</script>
