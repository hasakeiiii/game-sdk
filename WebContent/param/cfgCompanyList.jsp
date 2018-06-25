<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin,cid" flush="true" />
<%
CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from cfg_company where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and name like '%"+keyWord+"%' or cid like '%"+keyWord+"%' or cid_user like '%"+keyWord+"%'";
}
pageSql+=" order by cid";
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=cfgCompanyDao.getRecordCount(pageSql);//得到记录总数
List<CfgCompany> list=cfgCompanyDao.getList(pageno,pagesize,pageSql);

CfgSellDao cfgSellDao=new CfgSellDao();
Map map=cfgSellDao.getSelectMap("select cfg_sell_id,name from cfg_sell");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="90%">查询关键字&nbsp;&nbsp;<input name="keyWord" value="<%=keyWord%>" />&nbsp;&nbsp;
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td><td width="10%" align="right"><a href="#" onclick="addAct()"><img src="../_js/ico/btn_add.gif" border="0" alt="增加" align="absmiddle"/></a></td></tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>公司ID</td>
<td>所属业务员</td>
<td>公司名称</td>
<td>公司CID</td>
<td>比率</td>
<td>登录用户名</td>
<td>登录密码</td>
<td>公司地址</td>
<td>联系人</td>
<td>操作</td></tr>
<%
for(int i=0;i<list.size();i++){
    CfgCompany cfgCompany=(CfgCompany)list.get(i);
%>
<tr>
<td><%=(pageno-1)*pagesize+i+1%></td>
<td><%=cfgCompany.getCfgCompanyId()%></td>
<td><%=azul.JspUtil.getLink(cfgCompany.getCfgSellId(),map)%></td>
<td><%=cfgCompany.getName()%></td>
<td><%=cfgCompany.getCid()%></td>
<td><%=cfgCompany.getRate()%></td>
<td><%=cfgCompany.getCidUser()%></td>
<td><%=cfgCompany.getCidPass()%></td>
<td><%=cfgCompany.getAddress()%></td>
<td><%=cfgCompany.getContact()%></td>
<td><a href="#" onclick="infoAct('<%=cfgCompany.getCfgCompanyId()%>')"><img src="../_js/ico/btn_info.gif" border="0" alt="详细"/></a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="editAct('<%=cfgCompany.getCfgCompanyId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="修改"/></a></td>
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
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
    window.location="cfgCompanyEdit.jsp";
}
function editAct(cfg_company_id){
    window.location="cfgCompanyEdit.jsp?pageno=<%=pageno%>&cfg_company_id="+cfg_company_id;
}
function infoAct(cfg_company_id){
    window.location="cfgCompanyInfo.jsp?cfg_company_id="+cfg_company_id;
}
function goSearch(){
	document.mainForm.action="cfgCompanyList.jsp";
	document.mainForm.submit();
}
</script>
</body>
</html>
