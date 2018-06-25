<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ include file="../check.jsp"%>
<%
CfgBlackDao cfgBlackDao=new CfgBlackDao();
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from cfg_black where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and mobile like '%"+keyWord+"%'";
}
pageSql+=" order by kind,mobile";
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=cfgBlackDao.getRecordCount(pageSql);//得到记录总数
List list=cfgBlackDao.getList(pageno,pagesize,pageSql);
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
<td>BLACK_ID</td>
<td>类型</td>
<td>手机号码</td>
<td>操作</td></tr>
<%
for(int i=0;i<list.size();i++){
    CfgBlack cfgBlack=(CfgBlack)list.get(i);
    int kind=cfgBlack.getKind();
    String kindStr="扣费";
    if(kind==1){
    	kindStr="上行短信中心号码";
    }
    else if(kind==2){
    	kindStr="上行MID";
    }
%>
<tr>
<td><%=(pageno-1)*pagesize+i+1%></td>
<td><%=cfgBlack.getCfgBlackId()%></td>
<td><%=kindStr%></td>
<td><%=cfgBlack.getMobile()%></td>
<td><a href="#" onclick="editAct('<%=cfgBlack.getCfgBlackId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="修改"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=cfgBlack.getCfgBlackId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a></td>
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
<script>
function addAct(){
    window.location="cfgBlackEdit.jsp";
}
function editAct(cfg_black_id){
    window.location="cfgBlackEdit.jsp?pageno=<%=pageno%>&cfg_black_id="+cfg_black_id;
}
function deleteAct(cfg_black_id){
    if(window.confirm("确定删除选定记录?")){
        document.mainForm.action="cfgBlackAction.jsp?op=delete&cfg_black_id="+cfg_black_id;
        document.mainForm.submit();
    }
}
function goSearch(){
    if(document.mainForm.keyWord.value==""){
	     alert("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}
	document.mainForm.action="cfgBlackList.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
