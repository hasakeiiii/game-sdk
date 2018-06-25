<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%@ include file="../check.jsp"%>
<%
SysWarnDao sysWarnDao=new SysWarnDao();
String keyWord=JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from sys_warn where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and address like '%"+keyWord+"%'";
}
int pagesize=30;
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=sysWarnDao.getRecordCount(pageSql);
ArrayList list=sysWarnDao.getList(pageno,pagesize,pageSql);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="90%">查询关键字&nbsp;&nbsp;<input name="keyWord" value="<%=keyWord%>" />&nbsp;&nbsp;<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td><td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a></td></tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>警告配置ID</td>
<td>警告类型</td>
<td>调用关键字</td>
<td>警告地址</td>
<td>操作</td></tr>
<%
for(int i=0;i<list.size();i++){
    SysWarn sysWarn=(SysWarn)list.get(i);
%>
<tr>
<td><%=(pageno-1)*pagesize+i+1%></td>
<td><%=sysWarn.getSysWarnId()%></td>
<td><%=sysWarn.getKind()==0?"邮件提醒":"短信提醒"%></td>
<td><%=sysWarn.getKeyword()%></td>
<td><%=sysWarn.getAddress()%></td>
<td><a href="#" onclick="editAct('<%=sysWarn.getSysWarnId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="编辑"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=sysWarn.getSysWarnId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a></td>
</tr>
<%
}
%>
</table>
</form>
<%
String linkParam="";
out.print(JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<script>
function addAct(){
    window.location="sysWarnEdit.jsp";
}
function editAct(sys_warn_id){
    window.location="sysWarnEdit.jsp?pageno=<%=pageno%>&sys_warn_id="+sys_warn_id;
}
function infoAct(sys_warn_id){
    window.location="sysWarnInfo.jsp?sys_warn_id="+sys_warn_id;
}
function deleteAct(sys_warn_id){
    if(window.confirm("确定删除选定信息?")){
        document.mainForm.action="sysWarnAction.jsp?op=delete&sys_warn_id="+sys_warn_id;
        document.mainForm.submit();
    }
}
function goSearch(){
    if(document.mainForm.keyWord.value==""){
	     alert("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}
	document.mainForm.action="sysWarnList.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
