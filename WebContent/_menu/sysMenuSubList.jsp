<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<%
int sys_menu_main_id=azul.JspUtil.getInt(request.getParameter("sys_menu_main_id"),0);
String name=azul.JspUtil.getStr(request.getParameter("name"),"");
String param="sys_menu_main_id="+sys_menu_main_id+"&name="+name;
SysMenuSubDao sysMenuSubDao=new SysMenuSubDao();
SysMenuMainDao sysMenuMainDao =new SysMenuMainDao();
String pageSql="select * from sys_menu_sub where sys_menu_main_id="+sys_menu_main_id+" order by sort";
ArrayList list=sysMenuSubDao.getList(pageSql);
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
 <td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" alt="增加" width="88" height="23" border="0" align="absmiddle"/></a>&nbsp;&nbsp;</td>
 </tr>
</table>
<table width="100%" border="0" id="TableColor">
<tr>
<td>序号</td>
<td>所属菜单</td>
<td>子菜单名称</td>
<td>子菜单链接</td>
<td>颜色</td>
<td>排序</td>
<td>顺序</td>
<td>操作</td>
</tr>

<%
for(int i=0;i<list.size();i++){
    SysMenuSub sysMenuSub=(SysMenuSub)list.get(i);
    int SubId = sysMenuSub.getSysMenuMainId();
    SysMenuMain sysMenuMain = sysMenuMainDao.getRecord(SubId);
    String MainName="";
    if(sysMenuMain != null)
    {
    MainName = sysMenuMain.getName();
    }
    int color=sysMenuSub.getColor();
    int canSee=sysMenuSub.getDisplay();
    String canSeeStr="";
    if(canSee==0){
    	canSeeStr="<span class=\"tdRed\">(不可见)</span>";
    }
    String colorStr="未设定";
    if(1==color){
    	colorStr="<span class=\"tdRed\">红色</span>";
    }
    else if(2==color){
    	colorStr="<span class=\"tdOrgen\">桔色</span>";
    }
    else if(3==color){
    	colorStr="<span class=\"tdBlue\">蓝色</span>";
    }
    else if(4==color){
    	colorStr="<span class=\"tdGreen\">绿色</span>";
    }
	%>
	<tr>
	<td><%=i+1%></td>
	<td><%=MainName%></td>
	<td><%=sysMenuSub.getTitle()%><%=canSeeStr%></td>
	<td><%=sysMenuSub.getLinks()%></td>
	<td><%=colorStr%></td>
	<td>
	<%
	if(i!=0){
		out.println("<a href=\"#\" onClick=\"moveUp("+sysMenuSub.getSysMenuSubId()+","+sysMenuSub.getSort()+")\">上移</a>&nbsp;&nbsp;");
	}
	if(i!=list.size()-1){
		out.println("<a href=\"#\" onClick=\"moveDown("+sysMenuSub.getSysMenuSubId()+","+sysMenuSub.getSort()+")\">下移</a>");	
	}
	%>	</td>
	<td><%=sysMenuSub.getSort()%></td>
	<td><a href="#" onclick="editAct(<%=sysMenuSub.getSysMenuSubId()%>)"><img src="../_js/ico/btn_edit.gif" alt="修改" width="16" height="16" border="0"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=sysMenuSub.getSysMenuSubId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a></td>
	</tr>
	<%
}
%>
</table>
</form>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script>
function addAct(){
    window.location="sysMenuSubEdit.jsp?<%=param%>";
}
function editAct(sys_menu_sub_id){
    window.location="sysMenuSubEdit.jsp?op=edit&sys_menu_sub_id="+sys_menu_sub_id+"&<%=param%>";
}
function deleteAct(sys_menu_sub_id){
    if(window.confirm("确定删除选定记录?")){
        window.location="sysMenuSubAction.jsp?op=delete&sys_menu_main_id=<%=sys_menu_main_id%>&name=<%=name%>&sys_menu_sub_id="+sys_menu_sub_id;
    }
}
function moveUp(id,oldsort){
	window.location="sysMenuSubAction.jsp?op=up&sys_menu_sub_id="+id+"&sort="+oldsort+"&<%=param%>";
}
function moveDown(id,oldsort){
	window.location="sysMenuSubAction.jsp?op=down&sys_menu_sub_id="+id+"&sort="+oldsort+"&<%=param%>";
}
</script>
</body>
</html>