<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<%
SysMenuMainDao sysMenuMainDao=new SysMenuMainDao();
String pageSql="select * from sys_menu_main order by sort";
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=sysMenuMainDao.getRecordCount(pageSql);
ArrayList list=sysMenuMainDao.getList(pageno,pagesize,pageSql);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
 <td width="90%" style="color:#FF0000">添加子菜单请点击主菜单名称进入编辑页面</td>
 <td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" alt="增加" width="88" height="23" border="0" align="absmiddle"/></a>&nbsp;&nbsp;</td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td width="4%">序号</td>
<td width="52%">名称</td>
<td width="17%">排序值</td>
<td width="18%">显示顺序</td>
<td width="9%">操作</td>
</tr>
<%
for(int i=0;i<list.size();i++){
    SysMenuMain sysMenuMain=(SysMenuMain)list.get(i);
	%>
	<tr>
	<td><%=(pageno-1)*pagesize+i+1%></td>
	<td><a href="sysMenuSubList.jsp?sys_menu_main_id=<%=sysMenuMain.getSysMenuMainId()%>&name=<%=azul.JspUtil.decode(sysMenuMain.getName())%>"><%=sysMenuMain.getName()%></a></td>
	<td>
	<%
	if(i!=0){
		out.println("<a href=\"#\" onClick=\"moveUp("+sysMenuMain.getSysMenuMainId()+","+sysMenuMain.getSort()+")\">上移</a>");
	}
	if(i!=list.size()-1){
		out.println("<a href=\"#\" onClick=\"moveDown("+sysMenuMain.getSysMenuMainId()+","+sysMenuMain.getSort()+")\">下移</a>");	
	}
	%>	</td>
	<td><%=sysMenuMain.getSort()%></td>
	<td><a href="#" onclick="editAct('<%=sysMenuMain.getSysMenuMainId()%>')"><img src="../_js/ico/btn_edit.gif" alt="修改" width="16" height="16" border="0"/></a>
	 &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=sysMenuMain.getSysMenuMainId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a>
	</td>
	</tr>
	<%
}
%>
</table>
</form>
<%
String linkParam="";
out.print(azul.JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
<script>
function addAct(){
    window.location="sysMenuMainEdit.jsp";
}
function editAct(sys_menu_main_id){
    window.location="sysMenuMainEdit.jsp?sys_menu_main_id="+sys_menu_main_id;
}
function deleteAct(sys_menu_main_id){
if(window.confirm("确定删除选定记录?")){
       window.location="sysMenuMainAction.jsp?op=delete&sys_menu_main_id="+sys_menu_main_id;
}
}
function moveUp(id,oldsort){
	window.location="sysMenuMainAction.jsp?op=up&sys_menu_main_id="+id+"&sort="+oldsort;
}
function moveDown(id,oldsort){
	window.location="sysMenuMainAction.jsp?op=down&sys_menu_main_id="+id+"&sort="+oldsort;
}
</script>
</body>
</html>

