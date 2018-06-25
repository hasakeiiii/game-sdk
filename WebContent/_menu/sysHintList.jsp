<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%@ include file="../check.jsp"%>
<%
SysHintDao sysHintDao=new SysHintDao();
String keyWord=JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from sys_hint where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and info like '%"+keyWord+"%')";
}
int pagesize=30;
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=sysHintDao.getRecordCount(pageSql);
ArrayList list=sysHintDao.getList(pageno,pagesize,pageSql);

java.util.Map map=sysHintDao.getSelectMap("select links,concat(title,'(',links,')') from sys_menu_sub");
/*

SysHintDao sysHintDao=new SysHintDao();
out.println(sysHintDao.getHint(request));

*/
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
<td>提示页面</td>
<td>是否生效</td>
<td>内容</td>
<td>操作</td></tr>
<%
for(int i=0;i<list.size();i++){
    SysHint sysHint=(SysHint)list.get(i);
    String title=azul.JspUtil.getLink(sysHint.getPageName(),map);
    if("_menu/frame_right.jsp".equals(title)){
    	title="<span class=\"tdRed\">系统初始页面(frame_right.jsp)</span>";
    }
%>
<tr>
<td><%=(pageno-1)*pagesize+i+1%></td>
<td><%=title%></td>
<td><%=sysHint.getOk()==0?"无效":"有效"%></td>
<td><%=azul.JspUtil.subTxt(azul.JspUtil.undecode(sysHint.getInfo()),100)%></td>
<td><a href="#" onclick="editAct('<%=sysHint.getSysHintId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="编辑"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=sysHint.getSysHintId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a></td>
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
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
<script>
function addAct(){
    window.location="sysHintEdit.jsp";
}
function editAct(sys_hint_id){
    window.location="sysHintEdit.jsp?pageno=<%=pageno%>&sys_hint_id="+sys_hint_id;
}
function infoAct(sys_hint_id){
    window.location="sysHintInfo.jsp?sys_hint_id="+sys_hint_id;
}
function deleteAct(sys_hint_id){
    if(window.confirm("确定删除选定信息?")){
        document.mainForm.action="sysHintAction.jsp?op=delete&sys_hint_id="+sys_hint_id;
        document.mainForm.submit();
    }
}
function goSearch(){
    if(document.mainForm.keyWord.value==""){
	     alert("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}
	document.mainForm.action="sysHintList.jsp";
	document.mainForm.submit();
}
</script>
</body>
</html>