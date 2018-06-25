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
<script type="text/javascript" src="../_js/TableColor.js"></script>
<%
SysUserDao sysUserDao=new SysUserDao();
ArrayList list=sysUserDao.getList("select * from sys_user order by role");

CfgSellDao cfgSellDao=new CfgSellDao();
java.util.Map sellMap=cfgSellDao.getSelectMap("select sell_user,name from cfg_sell");

CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
java.util.Map cidMap=cfgCompanyDao.getSelectMap("select cid_user,name from cfg_company");
%>
<body>
<h1>系统用户列表</h1>
<table id="TableColor" width="100%" border="0">
  <tr>
    <td>序号</td>
    <td>角色属性</td>
    <td>用户角色</td>
    <td>用户名称</td>
    <td>用户登陆名称</td>
  </tr>
  <%
	for(int i=0;i<list.size();i++){
		SysUser sysUser=(SysUser)list.get(i);
		String username=sysUser.getUsername();
		String userRole="<span class=\"tdBlue\">公司后台用户</span>";
		String info=username;
		if(sellMap.containsKey(username)){
			userRole="<span class=\"tdRed\">商务</span>";
			info=sellMap.get(username).toString();
		}
		else if(cidMap.containsKey(username)){
			userRole="<span class=\"tdGreen\">合作厂商</span>";
			info=cidMap.get(username).toString();
		}
	%>
  <tr>
    <td><%=i+1%></td>
    <td><%=sysUser.getRole()%></td>
    <td><%=userRole%></td>
    <td><%=info%></td>
    <td><%=username%></td>
  </tr>
  <%
	}
	%>
</table>
</body>
</html>
