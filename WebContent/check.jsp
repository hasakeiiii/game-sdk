<%@page import="model.Userinfo"%>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="java.util.*"%>
<%
DebuUtil.log("check.jsp");
String roleMsg="";
if(session.getAttribute("sysUser")==null){
	 roleMsg="登陆超时请重新登陆";
}
else{
	boolean can=false;
	  Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
  	  String check=azul.JspUtil.getStr(request.getParameter("check"),"");
  	  SysMenuSubDao sysMenuSubDao=new SysMenuSubDao();
  	  SysMenuSub sysMenuSub=(SysMenuSub)sysMenuSubDao.loadBySql("select * from sys_menu_sub where links= '"+check+"'");
  	  if(sysMenuSub!=null)
  	  {
  	     String sysMenu="";
  	     String sysRole="";
  	     sysMenu = sysMenuSub.getSysMenuSubId().toString();
  	     System.out.print("1111"+sysMenu);
  	     SysRoleMenuDao sysRoleMenuDao = new SysRoleMenuDao();
         SysRoleMenu sysRoleMenu=(SysRoleMenu)sysRoleMenuDao.loadBySql("select * from sys_role_menu where sys_role ='" + sysUser.getRole()+"'");
         sysRole = sysRoleMenu.getSysMenuSub();
         System.out.print("222"+sysRole);
         if(sysRole.contains(sysMenu)){
         
         }
         else{
               %>
	          <script>
	        alert("您没有该菜单权限");
	        history.go(-1);
	          </script>
	<%
         }
  	  }

  	
// 	 if(!"".equals(check_role)){
// 		model.Userinfo user=(model.Userinfo)session.getAttribute("sysUser");
// 		String userRole=user.getRole();
// 		String[] arr=check_role.split(",");
// 		for(int i=0;i<arr.length;i++){
// 			if(userRole.equals(arr[i])){
// 				can=true;
// 				break;
// 			}
// 		}
// 		if(!can){
// 			roleMsg="您没有该菜单权限";
// 		}
// 	} 
}
if(!"".equals(roleMsg)){
	%>
	<script>
	alert("<%=roleMsg%>");
	parent.location="../index.jsp";
	</script>
	<%
	return;
}
%>