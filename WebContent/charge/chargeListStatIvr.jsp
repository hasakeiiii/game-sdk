<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
</head>
<%
String sid=JspUtil.getStr(request.getParameter("sid"),"");
String cid=JspUtil.getStr(request.getParameter("cid"),"");
String fee_type=JspUtil.getStr(request.getParameter("fee_type"),"");
String mobile=JspUtil.getStr(request.getParameter("mobile"),"");
String startDate=JspUtil.getStr(request.getParameter("startDate"),"");
String endDate= JspUtil.getStr(request.getParameter("endDate"),"");
String[] arr=azul.JspUtil.getDateWeek(startDate,endDate);
startDate=arr[0];
endDate=arr[1];
if(!startDate.substring(0,7).equals(endDate.substring(0,7))){
	%>
	<script>alert("请选择同年同月数据");</script>
	<%
	return;
}
ChargeIvrDao chargeIvrDao=new ChargeIvrDao();
String tableName="charge_ivr";
Calendar begCal=Calendar.getInstance();
Calendar endCal=Calendar.getInstance();
begCal.setTime(util.DateUtil.strToDate(startDate)); 
if(begCal.get(Calendar.YEAR)*100+begCal.get(Calendar.MONTH)<endCal.get(Calendar.YEAR)*100+endCal.get(Calendar.MONTH)){
	tableName="charge_ivr_"+startDate.substring(0,7).replace("-","_");
}
String area=JspUtil.getStr(request.getParameter("area"),"");
CfgSpDao cfgSpDao=new CfgSpDao();
Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp");

String pageSql="select * from "+tableName+" where 1=1";
StringBuffer conditionStrSB=new StringBuffer();
if (!"".equals(sid)){
	conditionStrSB.append(" and sid='");
	conditionStrSB.append(sid);
	conditionStrSB.append("'");
}
if (!"".equals(cid)){
	conditionStrSB.append(" and cid='");
	conditionStrSB.append(cid);
	conditionStrSB.append("'");
}
if(!"".equals(mobile)){
	conditionStrSB.append(" and mobile='");
	conditionStrSB.append(mobile);
    conditionStrSB.append("'");
}
if(!"".equals(area)){
	conditionStrSB.append(" and (province like '%");
	conditionStrSB.append(area);
	conditionStrSB.append("%' or city like '%");
	conditionStrSB.append(area);
	conditionStrSB.append("%')");
}
if (!"".equals(fee_type)) {
	conditionStrSB.append(" and fee_type='");
	conditionStrSB.append(fee_type);
	conditionStrSB.append("'");
}
if (!"".equals(startDate)) {
	conditionStrSB.append(" and date_time>='");
	conditionStrSB.append(startDate);
	conditionStrSB.append("'");
}
if (!"".equals(endDate)) {
	conditionStrSB.append(" and date_time<='");
	conditionStrSB.append(endDate);
	conditionStrSB.append(" 23:59:59'");
}
String sort_by= JspUtil.getStr(request.getParameter("sort_by"),"");
String sort_order= JspUtil.getStr(request.getParameter("sort_order"),"");
if (!"".equals(sort_by)) {
	conditionStrSB.append(" order by ");
	conditionStrSB.append(sort_by);
	conditionStrSB.append(" ");
	conditionStrSB.append(sort_order);
}
else{
	conditionStrSB.append(" order by date_time desc");
}
conditionStrSB.append(" limit 100");
pageSql=pageSql+conditionStrSB.toString();
//System.out.println("----------------");
//System.out.println(pageSql);
CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
java.util.Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(name,'(',cid,')') from cfg_company order by name");

List list=chargeIvrDao.getList(pageSql);
%>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
  <span class="tdRed">默认查询最近100条数据，选择时间段较长时查询速度比较慢，请尽量不要跨越太长时间段  </span>
  <table width="100%" class="table_noborder">
<tr>
<td>起始日期&nbsp;&nbsp;
  <input name="startDate" type="text" id="startDate" value="<%=startDate%>" onfocus="calendar()" style="width:70px" />
  <a href="#" onclick="calendar(document.mainForm.startDate)"><img src="../_images/dateButton.gif" width="34" height="21" align="absmiddle"/></a></td>
<td>截止日期&nbsp;
  <input name="endDate" type="text" id="endDate" value="<%=endDate%>"  onfocus="calendar()" style="width:70px"/>
  <a href="#" onclick="calendar(document.mainForm.endDate)"><img src="../_images/dateButton.gif" width="34" height="21" align="absmiddle"/></a></td>
<td>&nbsp;</td>
<td>手机号码&nbsp;
  <input name="mobile" type="text" id="mobile" value="<%=mobile%>" style="width:100px"/></td>
<td>&nbsp;  <a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td>
</tr>
<tr>
  <td>SP合作商
    <select name="sid" id="sid" style="width:125px">
      <option value="">请选择</option>
    </select></td>
  <td>合作厂商&nbsp;
    <select name="cid" id="cid">
	<option value="">请选择</option>
    </select></td>
  <td>地区信息
    <input name="area" type="text" id="area" value="<%=area%>" style="width:100px"/>
&nbsp;</td>
  <td>费用类型&nbsp;&nbsp;
    <select name="fee_type" id="fee_type">
    <option value="">类型</option>
    <option value="A">A</option>
    <option value="B">B</option>
    <option value="C">C</option>
  </select></td>
  <td>&nbsp;</td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td id="js_sort_sid">SID</td>
<td id="js_sort_cid">厂商ID</td>
<td>金额(元)</td>
<td>费用类型</td>
<td>所属运营商</td>
<td id="js_sort_mobile">手机号码</td>
<td id="js_sort_province">省份</td>
<td>市区</td>
<td id="js_sort_date_time">时间</td>
</tr>
<%
int allMoney=0;
for(int i=0;i<list.size();i++){
    ChargeIvr chargeIvr=(ChargeIvr)list.get(i);
    allMoney+=chargeIvr.getFee();
	int operator=chargeIvr.getOperator();
	String operatorStr="未知";
	if(operator==1){
	     operatorStr="移动";
	}
	else if(operator==2){
		operatorStr="联通";
	}
%>
<tr>
<td><%=i+1%></td>
<td><%=chargeIvr.getSid()%></td>
<td><%=chargeIvr.getCid()%></td>
<td><%=chargeIvr.getFee()%></td>
<td><%=chargeIvr.getFeeType()%></td>
<td><%=operatorStr%></td>
<td><%=chargeIvr.getMobile()%></td>
<td><%=chargeIvr.getProvince()%></td>
<td><%=chargeIvr.getCity()%></td>
<td><%=chargeIvr.getDateTime()%></td>
</tr>
<%
}
%>
</table>
</form>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/meizzDate.js"></script>
<script type="text/javascript" src="../_js/TableSort.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script>
function saveAsExcel(excelTable,fileName){
	var divText=document.getElementById(excelTable).outerHTML;
	var oWin=window.open("","","top=1000,left=2000");
	with(oWin) {
		document.write(divText);
		document.execCommand('Saveas',true,fileName);
	}
} 
function goSearch(){
    document.mainForm.action="chargeListStatIvr.jsp";
    document.mainForm.submit();
} 
<%
out.println(JspUtil.initSelect("sid",sidMap,sid));
out.println(JspUtil.initSelect("cid",cidMap,cid));
%>
TableSort.mySort("<%=sort_by%>","<%=sort_order%>","");
</script>
</body>
</html>