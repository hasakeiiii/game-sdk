<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<%
CfgLogDao cfgLogDao=new CfgLogDao();
String param=JspUtil.getStr(request.getParameter("param"),"");
String startDate=JspUtil.getStr(request.getParameter("startDate"),"");
String endDate=JspUtil.getStr(request.getParameter("endDate"),"");
String[] arr=azul.JspUtil.getDateDay(startDate,endDate);
startDate=arr[0];
endDate=arr[1];

StringBuffer paramSB=new StringBuffer();
StringBuffer conditionStrSB=new StringBuffer("select * from cfg_log where 1=1");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"param",param,"like");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",startDate,">=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",endDate,"<=");
conditionStrSB.append(" order by date_time desc");
String pageSql=conditionStrSB.toString();
String linkParam=paramSB.toString();

int pagesize=30;
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=cfgLogDao.getRecordCount(pageSql+conditionStrSB.toString());
ArrayList list=cfgLogDao.getList(pageno,pagesize,pageSql+conditionStrSB.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<script type="text/javascript" src="../_js/meizzDate.js"></script>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="90%">查询关键字&nbsp;&nbsp;<input name="param" value="<%=param%>" />&nbsp;起始日期&nbsp;&nbsp;
  <input name="startDate" type="text" id="startDate" value="<%=startDate%>" onfocus="calendar(this)" style="width:70px" />&nbsp;截止日期&nbsp;
  <input name="endDate" type="text" id="endDate" value="<%=endDate%>"  onclick="calendar(this)" style="width:70px"/>&nbsp;
  <a href="#" onclick="_jsSearch('cfgLogList.jsp')"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td>
<td width="10%" align="right"></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td width="4%" title="">序号</td>
<td width="82%">参数</td>
<td width="14%">时间</td>
</tr>
<%
for(int i=0;i<list.size();i++){
    CfgLog cfgLog=(CfgLog)list.get(i);
%>
<tr>
<td title="<%=cfgLog.getCfgLogId()%>"><%=(pageno-1)*pagesize+i+1%></td>
<td><%=cfgLog.getParam()%></td>
<td><%=cfgLog.getDateTime()%></td>
</tr>
<%
}
%>
</table>
</form>
<%
out.print(JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>