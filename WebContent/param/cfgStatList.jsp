<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%@ include file="../check.jsp"%>
<%
String keyWord=JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from cfg_stat order by sid";
CfgStatDao cfgStatDao=new CfgStatDao();
ArrayList list=cfgStatDao.getList(pageSql);
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
<td>SID</td>
<td>CID</td>
<td>省份</td>
<td>命令</td>
<td>剩余数量</td>
<td>上限</td>
<td>操作</td></tr>
<%
for(int i=0;i<list.size();i++){
    CfgStat cfgStat=(CfgStat)list.get(i);
    int num=0;
    String temp=cfgStat.getSid()+"_"+cfgStat.getCid()+"_"+cfgStat.getProvince()+"_"+cfgStat.getMsg();
    if(azul.CacheSp.limitMap.containsKey(temp)){
    	num=azul.CacheSp.limitMap.get(temp);
    }
%>
<tr>
<td title="<%=cfgStat.getCfgStatId()%>"><%=i+1%></td>
<td><%=cfgStat.getSid()%></td>
<td><%=cfgStat.getCid()%></td>
<td><%=cfgStat.getProvince()%></td>
<td><%=cfgStat.getMsg()%></td>
<td><%=num%></td>
<td><%=cfgStat.getMaxNum()%></td>
<td><a href="#" onclick="editAct('<%=cfgStat.getCfgStatId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="编辑"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="deleteAct('<%=cfgStat.getCfgStatId()%>')"><img src="../_js/ico/btn_delete.gif" border="0" alt="删除"/></a></td>
</tr>
<%
}
%>
</table>
</form>
<script>
function addAct(){
    window.location="cfgStatEdit.jsp";
}
function editAct(cfg_stat_id){
    window.location="cfgStatEdit.jsp?cfg_stat_id="+cfg_stat_id;
}
function deleteAct(cfg_stat_id){
    if(window.confirm("确定删除选定信息?")){
        document.mainForm.action="cfgStatAction.jsp?op=delete&cfg_stat_id="+cfg_stat_id;
        document.mainForm.submit();
    }
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
</body>
</html>