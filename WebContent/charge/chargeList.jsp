<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<%
String sid=JspUtil.getStr(request.getParameter("sid"),"");
String cid=JspUtil.getStr(request.getParameter("cid"),"");
String msg=JspUtil.getStr(request.getParameter("msg"),"");
String fee_type=JspUtil.getStr(request.getParameter("fee_type"),"");
String startDate=JspUtil.getStr(request.getParameter("startDate"),"");
String endDate= JspUtil.getStr(request.getParameter("endDate"),"");
String province_code=JspUtil.getStr(request.getParameter("province"),"");
String province=province_code;
if(!"".equals(province)){
	province=azul.JspUtil.undecode(province);
}
String tableName=azul.JspUtil.getTableName("charge",startDate);
StringBuffer paramSB=new StringBuffer();
StringBuffer conditionStrSB=new StringBuffer("select * from "+tableName+" where 1=1");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"sid",sid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"cid",cid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"msg",msg,"like");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"fee_type",fee_type,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",startDate,">=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",endDate,"<=");
String linkParam="cid="+cid+"&sid="+sid+"&msg="+msg+"&fee_type="+fee_type+"&province="+province_code+"&startDate="+startDate+"&endDate="+endDate;

CfgSpDao cfgSpDao=new CfgSpDao();
Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_code,'(',sp_name,')') from cfg_sp");

CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(cid,'(',name,')') from cfg_company");


//System.out.println("============");
//System.out.println(pageSql);
ChargeDao chargeDao=new ChargeDao();
String pageSql=conditionStrSB.toString();
Object obj=chargeDao.getValue(pageSql);
String fee="0";
if(obj!=null){
	fee=obj.toString();
}
int pagesize=30;//每页记录数
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=chargeDao.getRecordCount(pageSql);//得到记录总数
ArrayList list=chargeDao.getList(pageno,pagesize,pageSql);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.net.URLEncoder"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="zh-CN" http-equiv="Content-Language"/>
<title>费用明细</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td>
<b>相关记录总金额<span id="allMoney" style="color:#FF0000"><%=Double.valueOf(fee)%></span>元</b></td>
<td align="right"><a href="#" onclick="javascript:history.back()"><img src="../_js/ico/btn_back.gif" border="0" alt="返回" align="absmiddle"/></a></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>费用ID</td>
<td>LinkId</td>
<td>SID</td>
<td>厂商ID</td>
<td>金额(元)</td>
<td>类型</td>
<td>所属运营商</td>
<td>手机号码</td>
<td>省份</td>
<td>市区</td>
<td>时间</td>
</tr>
<%
int allMoney=0;
for(int i=0;i<list.size();i++){
    Charge charge=(Charge)list.get(i);
    allMoney+=charge.getFee();
	int operator=charge.getOperator();
	String operatorStr="电信";
	if(operator==1){
	     operatorStr="移动";
	}
	else if(operator==2){
		operatorStr="联通";
	}
%>
<tr>
<td title="<%=charge.getChargeId()%>"><%=(pageno-1)*pagesize+i+1%></td>
<td><%=charge.getLinkid()%></td>
<td><%=JspUtil.getLink(charge.getSid(),sidMap)%></td>
<td><%=JspUtil.getLink(charge.getCid(),cidMap)%></td>
<td><%=charge.getFee()%></td>
<td><%=charge.getFeeType()%></td>
<td><%=operatorStr%></td>
<td><%=charge.getMobile()%></td>
<td><%=charge.getProvince()%></td>
<td><%=charge.getCity()%></td>
<td><%=charge.getDateTime()%></td>
</tr>
<%
}
%>
</table>
</form>
<%
out.print(JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<script type="text/javascript" src="../_js/meizzDate.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>