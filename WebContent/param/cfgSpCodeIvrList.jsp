<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>

<%
CfgSpCodeIvrDao cfgSpCodeIvrDao=new CfgSpCodeIvrDao();
String pageSql="select * from cfg_sp_code_ivr order by cid,sid";

List list=cfgSpCodeIvrDao.getList(pageSql);

CfgSpDao cfgSpDao=new CfgSpDao();
Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp");

CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
java.util.Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(name,'(',cid,')') from cfg_company order by name");
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
<td width="90%">&nbsp;</td>
<td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a></td></tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>SID</td>
<td>CID</td>
<td>命令</td>
<td>比例</td>
<td>操作</td></tr>
<%
for(int i=0;i<list.size();i++){
    CfgSpCodeIvr cfgSpCodeIvr=(CfgSpCodeIvr)list.get(i);
%>
<tr>
<td title="<%=cfgSpCodeIvr.getCfgSpCodeIvrId()%>"><%=i+1%></td>
<td><%=azul.JspUtil.getLink(cfgSpCodeIvr.getSid(),sidMap)%></td>
<td><%=azul.JspUtil.getLink(cfgSpCodeIvr.getCid(),cidMap)%></td>
<td><%=cfgSpCodeIvr.getSpCode()%></td>
<td><%=cfgSpCodeIvr.getRate()%></td>
<td><a href="#" onclick="editAct('<%=cfgSpCodeIvr.getCfgSpCodeIvrId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="编辑"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=cfgSpCodeIvr.getCfgSpCodeIvrId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a></td>
</tr>
<%
}
%>
</table>
</form>
<script>
function addAct(){
    window.location="cfgSpCodeIvrEdit.jsp";
}
function editAct(cfg_sp_code_ivr_id){
    window.location="cfgSpCodeIvrEdit.jsp?cfg_sp_code_ivr_id="+cfg_sp_code_ivr_id;
}
function deleteAct(cfg_sp_code_ivr_id){
    if(window.confirm("确定删除选定信息?")){
        document.mainForm.action="cfgSpCodeIvrAction.jsp?op=delete&cfg_sp_code_ivr_id="+cfg_sp_code_ivr_id;
        document.mainForm.submit();
    }
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
</body>
</html>
