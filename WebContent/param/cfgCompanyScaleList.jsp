<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin,cid" flush="true" />
<%
CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from cfg_company where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and name like '%"+keyWord+"%'";
}
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
<td>查询关键字&nbsp;&nbsp;<input name="keyWord" value="<%=keyWord%>" />&nbsp;&nbsp;<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a><a href="#" onclick="addAct()"></a></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>公司ID</td>
<td>所属业务员</td>
<td>公司名称</td>
<td>公司CID</td>
<td>扣费比例</td>
<td>包月扣费比例</td>
<td>分成比例</td>
<td>Gprs比例</td>
<td>计费开关</td>
<td>操作</td></tr>
<%
String[] arr=new String[]{"关闭","打开"};
for(int i=0;i<list.size();i++){
    CfgCompany cfgCompany=(CfgCompany)list.get(i);
%>
<tr>
<td><%=(pageno-1)*pagesize+i+1%></td>
<td><%=cfgCompany.getCfgCompanyId()%></td>
<td><%=azul.JspUtil.getLink(cfgCompany.getCfgSellId(),map)%></td>
<td><%=cfgCompany.getName()%></td>
<td><%=cfgCompany.getCid()%></td>
<td><%=cfgCompany.getScale()%></td>
<td><%=cfgCompany.getScaleAnchor()%></td>
<td><%=cfgCompany.getRate()%></td>
<td><%=cfgCompany.getGprs()%></td>
<td><%=azul.JspUtil.getTitle(arr,cfgCompany.getOpen())%></td>
<td><a href="#" onclick="editAct('<%=cfgCompany.getCfgCompanyId()%>')"><img src="../_js/ico/btn_edit.gif" border="0" alt="修改"/></a></td>
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
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function editAct(cfg_company_id){
    window.location="cfgCompanyScaleEdit.jsp?pageno=<%=pageno%>&cfg_company_id="+cfg_company_id;
}
function goSearch(){
    if(document.mainForm.keyWord.value==""){
	     error("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}
	document.mainForm.action="cfgCompanyScaleList.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
