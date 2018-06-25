<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
	DebuUtil.log("mmpaydatalist");
AppPayDao appPayDao = new AppPayDao();
String keyWord=azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from app_pay where 1=1 ";
if(!keyWord.equals("")){
	   pageSql+="and name like '%"+keyWord+"%'";
}
pageSql +=" order by id desc ";
int pagesize=30;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=appPayDao.getRecordCount(pageSql);//得到记录总数
DebuUtil.log("recordcount="+recordcount);
List<Object> list=appPayDao.getList(pageno,pagesize,pageSql);
DebuUtil.log("list="+list.size());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付数据管理</title>
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
<td>游戏名称</td>
<td>游戏编号</td>
<td>公司</td>
<td>总限额</td>
<td>单用户限额</td>
<td>金额百分比</td>
<td>优先级</td>
<!-- <td>分省限额</td>
<td>分省</td> -->
<td>推广游戏</td>
<td>备注</td>
<td>屏蔽省份</td>
<td>编辑</td>
</tr>
<%
for(int i=0;i<list.size();i++){
    AppPay appPay=(AppPay)list.get(i);
    String company = appPay.getCompany();
    if(company.equals("mzhy")){
    	company = "拇指互娱";
    }else if(company.equals("mzyw")){
    	company = "拇指游玩";
    }else if(company.equals("zty")){
    	company = "中泰源";
    }else if(company.equals("jy")){
    	company = "竟游";
    }else if(company.equals("cq")){
    	company = "创趣";
    }else if(company.equals("hlx")){
    	company = "宏乐欣";
    }else if(company.equals("gy")){
    	company = "光游";
    }else if(company.equals("qt")){
    	company = "其他";
    }else if(company.equals("qszd")){
    	company = "奇硕智达";
    }else if(company.equals("mykj")){
    	company = "妙悟科技";
    }else if(company.equals("lst")){
    	company = "联思泰";
    }else if(company.equals("ktx")){
    	company = "科特迅";
    }else if(company.equals("yssj")){
    	company = "原宿设计";
    }else if(company.equals("lb")){
    	company = "遛宝";
    }else if(company.equals("wg")){
    	company = "吴贵";
    }else if(company.equals("bxl")){
    	company = "冰雪狼";
    }else if(company.equals("yy")){
    	company = "页游";
    }
%>
<tr>
<td><%=appPay.getId()%></td>
<td><%=appPay.getName()%></td>
<td><%=appPay.getNo()%></td>
<td><%=company%></td>
<td><%=appPay.getLimitMoney()%></td>
<td><%=appPay.getLimitUserMoney()%></td>
<td><%=appPay.getMoneycount()%></td>
<td><%=appPay.getPriority()%></td>
<%-- <td><%=appPay.getProvinceMoney()%></td>
<td><%=appPay.getProvince()%></td> --%>
<td><%=appPay.getAppPayId()%></td>
<td></td>
<td><%=appPay.getShieldRegion()%></td>
<td><a href="#" onclick="editAct('<%=appPay.getId()%>')"><img src="../_js/ico/btn_edit.gif" 
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
	window.open("payListEdit.jsp");
}
function editAct(id){
	window.open("payListEdit.jsp?pageno=<%=pageno%>&id="+id+"");
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
	document.mainForm.action="payListList.jsp";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
