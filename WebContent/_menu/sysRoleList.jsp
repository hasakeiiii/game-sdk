<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
 <jsp:include page="../check.jsp?check_role=admin,sid" flush="true" /> 
<%
	String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
int uid=azul.JspUtil.getInt(request.getParameter("uid"),0);	
//Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
Integer ID = uid;
DebuUtil.log("gamelist");
UserinfoDao userinfoDao=new UserinfoDao();
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from userinfo";
if(!keyWord.equals("")){
	   pageSql+=" where username like '%"+keyWord+"%'";
}
pageSql+=" order by id asc";

int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=userinfoDao.getRecordCount(pageSql);//得到记录总数
DebuUtil.log("recordcount="+recordcount);
List<Userinfo> list=userinfoDao.getList(pageno,pagesize,pageSql);
DebuUtil.log("list="+list.size());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="90%">查询关键字&nbsp;&nbsp;<input name="keyWord" value="<%=keyWord%>" />&nbsp;&nbsp;
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td>
<td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>用户名称</td>
<td>用户密码</td>
<td>用户角色</td>
<td>操作</td></tr>
<%
	for(int i=0;i<list.size();i++){
    Userinfo userinfo=(Userinfo)list.get(i);
 
%>
<tr>
<td><%=userinfo.getId()%></td>
<td><%=userinfo.getUsername() %></td>
<td><%=userinfo.getPass() %></td>
<td><%=userinfo.getRole()%></td>
<td><a href="#" onclick="editAct('<%=userinfo.getId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="修改"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="#" onclick="deleteAct('<%=userinfo.getId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a>
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
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
    window.location="sysUserEdit.jsp?uid=<%=uid%>";
}
function editAct(id){
	window.location="sysUserEdit.jsp?uid=<%=uid%>&pageno=<%=pageno%>&id="+id;
}
function deleteAct(id){
if(window.confirm("确定删除选定记录?")){
 window.location="sysUserAction.jsp?op=delete&id="+id;
       }
}
function infoAct(id){
    //window.location="cfgSpInfo.jsp?id="+id;
}
function goSearch(){
    if(document.mainForm.keyWord.value==""){
	     error("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}
	document.mainForm.action="sysRoleList.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
