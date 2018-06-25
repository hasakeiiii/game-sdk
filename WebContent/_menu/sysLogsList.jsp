<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<%
SysLogsDao sysLogsDao=new SysLogsDao();
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String act_type=azul.JspUtil.getStr(request.getParameter("act_type"),"");
int sys_user_id=azul.JspUtil.getInt(request.getParameter("sys_user_id"),-1);
String pageSql="select * from sys_logs where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and act like '%"+keyWord+"%'";
}
if(!act_type.equals("")){
	   pageSql+=" and act_type ='"+act_type+"'";
}
if(sys_user_id!=-1){
	   pageSql+=" and sys_user_id ="+sys_user_id;
}
pageSql+=" order by date_time desc";
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=sysLogsDao.getRecordCount(pageSql);//得到记录总数
ArrayList list=sysLogsDao.getList(pageno,pagesize,pageSql);

SysUserDao sysUserDao=new SysUserDao();
java.util.Map map=sysUserDao.getSelectMap("select sys_user_id,username from sys_user");
//azul.JspUtil.p(map);
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
<td>查询关键字&nbsp;&nbsp;<input name="keyWord" value="<%=keyWord%>" />&nbsp;&nbsp;
所属类型&nbsp;&nbsp;
<select name="act_type" id="act_type">
  <option value="">请选择</option>
  <option value="LOGIN">登陆</option>
  <option value="ADD">增加</option>
  <option value="EDIT">编辑</option>
  <option value="DELETE">删除</option>
</select>&nbsp;&nbsp;
操作用户&nbsp;&nbsp;
<select name="sys_user_id" id="sys_user_id">
  <option value="">请选择</option>
</select>&nbsp;&nbsp;
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a>
</td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>记录编号</td>
<td>操作用户</td>
<td>操作IP</td>
<td>操作类型</td>
<td>操作细节</td>
<td>操作时间</td>
</tr>
<%
for(int i=0;i<list.size();i++){
    SysLogs sysLogs=(SysLogs)list.get(i);
%>
<tr>
<td><%=(pageno-1)*pagesize+i+1%></td>
<td><%=sysLogs.getSysLogsId()%></td>
<td><%=azul.JspUtil.getLink(sysLogs.getSysUserId(),map)%></td>
<td><%=sysLogs.getIp()%></td>
<td><%=sysLogs.getActType()%></td>
<td title="<%=sysLogs.getAct()%>"><%=azul.JspUtil.subTxt(sysLogs.getAct(),80)%></td>
<td><%=sysLogs.getDateTime()%></td>
</tr>
<%
}
%>
</table>
</form>
<%
String linkParam="keyWord="+keyWord+"&act_type="+act_type;
out.print(azul.JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
<script>
<%
out.println(azul.JspUtil.initSelect("sys_user_id",map,sys_user_id));
%>
function goSearch(){
	document.mainForm.action="sysLogsList.jsp";
	document.mainForm.submit();
}
initElem("act_type","<%=act_type%>");
</script>
</body>
</html>
