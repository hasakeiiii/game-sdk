<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%@ include file="../check.jsp"%>
<%
String pageSql="select * from cfg_cid_url_ivr";
int pagesize=30;
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
CfgCidUrlIvrDao cfgCidUrlIvrDao=new CfgCidUrlIvrDao();
int recordcount=cfgCidUrlIvrDao.getRecordCount(pageSql);
ArrayList list=cfgCidUrlIvrDao.getList(pageno,pagesize,pageSql);
String linkParam="";

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
<td width="90%"></td><td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a></td></tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>CID</td>
<td>同步地址</td>
<td>操作</td></tr>
<%
for(int i=0;i<list.size();i++){
    CfgCidUrlIvr cfgCidUrlIvr=(CfgCidUrlIvr)list.get(i);
%>
<tr>
<td title="<%=cfgCidUrlIvr.getCfgCidUrlIvrId()%>"><%=(pageno-1)*pagesize+i+1%></td>
<td><%=azul.JspUtil.getLink(cfgCidUrlIvr.getCid(),cidMap)%></td>
<td><%=cfgCidUrlIvr.getUrl()%></td>
<td><a href="#" onclick="editAct('<%=cfgCidUrlIvr.getCfgCidUrlIvrId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="编辑"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=cfgCidUrlIvr.getCfgCidUrlIvrId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a></td>
</tr>
<%
}
%>
</table>
</form>
<%
out.print(JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<script>
function addAct(){
    window.location="cfgCidUrlIvrEdit.jsp";
}
function editAct(cfg_cid_url_ivr_id){
    window.location="cfgCidUrlIvrEdit.jsp?pageno=<%=pageno%>&cfg_cid_url_ivr_id="+cfg_cid_url_ivr_id;
}
function infoAct(cfg_cid_url_ivr_id){
    window.location="cfgCidUrlIvrInfo.jsp?cfg_cid_url_ivr_id="+cfg_cid_url_ivr_id;
}
function deleteAct(cfg_cid_url_ivr_id){
    if(window.confirm("确定删除选定信息?")){
        document.mainForm.action="cfgCidUrlIvrAction.jsp?op=delete&cfg_cid_url_ivr_id="+cfg_cid_url_ivr_id;
        document.mainForm.submit();
    }
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableRowBlank.js"></script>
</body>
</html>