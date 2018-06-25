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
<script type="text/javascript" src="../_js/dtree/dtree.js"></script>
</head>
<%
String user_role=azul.JspUtil.getStr(request.getParameter("user_role"),"cid");
SysRoleMenuDao sysRoleMenuDao=new SysRoleMenuDao();
SysRoleMenu sysRoleMenu=(SysRoleMenu)sysRoleMenuDao.loadBySql("select * from sys_role_menu where sys_role='"+user_role+"'");
String Privilege="";
if(sysRoleMenu!=null){
	Privilege=sysRoleMenu.getSysMenuSub();
}
SysMenuMainDao sysMenuMainDao=new SysMenuMainDao();
ArrayList listMain=sysMenuMainDao.getList("select * from sys_menu_main order by sort");
SysMenuSubDao sysMenuSubDao=new SysMenuSubDao();
%>
<body>
<h1>系统权限</h1>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" border="0" class="table_noborder">
  <tr>
    <td width="35%" align="center" valign="top">
	<table id="TableColor" width="100%" border="0">
      <tr>
        <td>序号</td>
		<td>用户角色</td>
        </tr>
      <tr>
        <td><input type="radio" name="user_role" value="Administrators" onclick="javascript:location='sysRoleMenuList.jsp?user_role=Administrators'"/></td>
		<td>超级管理员</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="admin" onclick="javascript:location='sysRoleMenuList.jsp?user_role=admin'"/></td>
		<td>admin</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="business" onclick="javascript:location='sysRoleMenuList.jsp?user_role=business'"/></td>
		<td>商务</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="ceo" onclick="javascript:location='sysRoleMenuList.jsp?user_role=ceo'"/></td>
		<td>ceo</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="finance" onclick="javascript:location='sysRoleMenuList.jsp?user_role=finance'"/></td>
		<td>财务</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="customService" onclick="javascript:location='sysRoleMenuList.jsp?user_role=customService'"/></td>
		<td>customService</td>
      </tr>
	 <tr>
        <td><input type="radio" name="user_role" value="CPS" onclick="javascript:location='sysRoleMenuList.jsp?user_role=CPS'"/></td>
		<td>CPS</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="CPA" onclick="javascript:location='sysRoleMenuList.jsp?user_role=CPA'"/></td>
		<td>CPA</td>
      </tr>
	 <tr>
        <td><input type="radio" name="user_role" value="CPA_R" onclick="javascript:location='sysRoleMenuList.jsp?user_role=CPA_R'"/></td>
		<td>CPA_R</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="C_S" onclick="javascript:location='sysRoleMenuList.jsp?user_role=C_S'"/></td>
		<td>C_S</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="BSS" onclick="javascript:location='sysRoleMenuList.jsp?user_role=BSS'"/></td>
		<td>BSS</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="GUEST" onclick="javascript:location='sysRoleMenuList.jsp?user_role=GUEST'"/></td>
		<td>游客</td>
      </tr>

       <tr>
        <td><input type="radio" name="user_role" value="SA" onclick="javascript:location='sysRoleMenuList.jsp?user_role=SA'"/></td>
		<td>SA</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="GS" onclick="javascript:location='sysRoleMenuList.jsp?user_role=GS'"/></td>
		<td>GS</td>
      </tr>
      <tr>
        <td><input type="radio" name="user_role" value="CP" onclick="javascript:location='sysRoleMenuList.jsp?user_role=CP'"/></td>
		<td>CP</td>
      </tr>
    </table>
	</td>
	<td width="3%">&nbsp;</td>
    <td width="62%" valign="top" id="radioList">
	<a href="#" onclick="d.openAll()">展开</a>&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="d.closeAll()">合并</a>&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="allSelect('radioList')">全选</a>&nbsp;&nbsp;&nbsp;
	<a href="#" onclick="oppSelect('radioList')">反选</a>&nbsp;&nbsp;&nbsp;
	<input type="button" name="textfield22" value="确认角色权限" onclick="saveRole()"/>
	<script>
    var d = new dTree('d');
    d.add(0,-1,'');  //目录
    <%
	for(int i=0;i<listMain.size();i++){
		SysMenuMain sysMenuMain=(SysMenuMain)listMain.get(i);
    %>
        d.add(<%=i + 1%>,0,"<strong><%=sysMenuMain.getName()%></strong>");
        <%
        ArrayList listSub=sysMenuSubDao.getList("select * from sys_menu_sub where sys_menu_main_id="+sysMenuMain.getSysMenuMainId()+" order by sort");
		for(int j=0;j<listSub.size();j++){
			SysMenuSub sysMenuSub=(SysMenuSub)listSub.get(j);
			if(Privilege.indexOf(","+sysMenuSub.getSysMenuSubId()+",")>-1){
			%>
			d.add(<%=i + 100 + j%>,<%=i + 1%>,"<input type='checkbox' name='chkValue' value='<%=sysMenuSub.getSysMenuSubId()%>'/>&nbsp;<%=sysMenuSub.getTitle()%>&nbsp;&nbsp;&nbsp;<%=sysMenuSub.getLinks()%>","javascript:void(0)");
//  			d.add(<%=i + 1000 + j%>,<%=i + 100 + j%>,"<input type='checkbox' name='chkValue' value='<%=sysMenuSub.getSysMenuSubId()%>'/>&nbsp;新增","javascript:void(0)");
			<%
			}else{
			%>
			    d.add(<%=i + 100 + j%>,<%=i + 1%>,"<input type='checkbox' name='chkValue' value='<%=sysMenuSub.getSysMenuSubId()%>'/>&nbsp;<%=sysMenuSub.getTitle()%>&nbsp;&nbsp;&nbsp;<%=sysMenuSub.getLinks()%>","javascript:void(0)");
//  			    d.add(<%=i + 1000 + j%>,<%=i + 100 + j%>,"<input type='checkbox' name='chkValue' value='<%=sysMenuSub.getSysMenuSubId()%>'/>&nbsp;新增","javascript:void(0)");
			<%
	
           }
       }
   }
   %>
   document.write(d);
    </script>
	</td>
  </tr>
</table>
</form>
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script>
function saveRole(){
    var key=getRadio("user_role");
	if(key==""){
	    error("请选择需要修改权限的用户");
		return;
	}
	var role_key=getCheck("chkValue");
	if(role_key==""){
	    error("请选择用户权限");
		return;
	}
	document.mainForm.action="sysRoleMenuAction.jsp?sys_role="+key+"&sys_menu_sub="+role_key;
    document.mainForm.submit();	
}
//初始化当前选择用户权限
initElem("user_role","<%=user_role%>");
initElem("chkValue","<%=Privilege%>");
</script>
</body>
</html>
