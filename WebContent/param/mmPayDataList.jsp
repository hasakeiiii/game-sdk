<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
	DebuUtil.log("mmpaydatalist");
MmPayDataDao mmPayDatadao = new MmPayDataDao();
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from mm_pay_data where 1=1 ";
if(!keyWord.equals("")){
	   pageSql+="and (pay_name like '%"+keyWord+"%' or pay_id like '%"+keyWord+"%' or pay_type like '%"+keyWord+"%')";
}
pageSql +=" order by id desc ";
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=mmPayDatadao.getRecordCount(pageSql);//得到记录总数
DebuUtil.log("recordcount="+recordcount);
List<Object> list=mmPayDatadao.getList(pageno,pagesize,pageSql);
DebuUtil.log("list="+list.size());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>mm支付数据管理</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="90%">查询关键字&nbsp;&nbsp;<input name="keyWord" value="<%=keyWord%>" />&nbsp;&nbsp;
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td>
<td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a></td></tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>计费名称</td>
<td>计费编号</td>
<td>类型</td>
<td>金额列表</td>
</tr>
<%
for(int i=0;i<list.size();i++){
    MmPayData mmPayData=(MmPayData)list.get(i);
%>
<tr>
<td><%=mmPayData.getId()%></td>
<td><%=mmPayData.getPayName()%></td>
<td><%=mmPayData.getPayId()%></td>
<td><%=mmPayData.getPayType()%></td>
<td><%=mmPayData.getPayAmount()%></td>
<td><a href="#" onclick="editAct('<%=mmPayData.getId()%>')"><img src="../_js/ico/btn_edit.gif" 
border="0" alt="修改"/></a></td>
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
    window.location="mmPayDataEdit.jsp";
}
function editAct(id){
	window.location="mmPayDataEdit.jsp?pageno=<%=pageno%>&id="+id;
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
	document.mainForm.action="mmPayDataList.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
