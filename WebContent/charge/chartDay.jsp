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
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/meizzDate.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
</head>
<%
String sid=JspUtil.getStr(request.getParameter("sid"),"");
String cid=JspUtil.getStr(request.getParameter("cid"),"");
String fee_type=JspUtil.getStr(request.getParameter("fee_type"),"");
String mobile=JspUtil.getStr(request.getParameter("mobile"),"");
String area=JspUtil.getStr(request.getParameter("area"),"");
String startDate=JspUtil.getStr(request.getParameter("startDate"),"");
String endDate= JspUtil.getStr(request.getParameter("endDate"),"");
String[] arr=azul.JspUtil.getDateDay(startDate,endDate);
startDate=arr[0];
endDate=arr[1];
String tableName=azul.JspUtil.getTableName("charge",startDate);
StringBuffer paramSB=new StringBuffer();
StringBuffer conditionStrSB=new StringBuffer("");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"cid",cid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"sid",sid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"mobile",mobile,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"fee_type",fee_type,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",startDate,">=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",endDate,"<=");
String pageSql="";
if(startDate.equals(endDate)){
	pageSql="select HOUR(date_time),count(date_time) from "+tableName+" where ok=1";
	pageSql=pageSql+conditionStrSB.toString()+" group by HOUR(date_time)";
}
else{
	pageSql="select DATE(date_time),count(date_time) from "+tableName+" where ok=1";
	pageSql=pageSql+conditionStrSB.toString()+" group by DATE(date_time)";
}
//System.out.println("----------------");
//System.out.println(pageSql);
ChargeDao chargeDao=new ChargeDao();
ArrayList list=chargeDao.getObjectList(pageSql);
String rowStr="";
String colStr="";
int max=0;
for(int i=0;i<list.size();i++){ 
	ArrayList tempList=(ArrayList)list.get(i); 
	String date_timeTemp=tempList.get(0).toString();
	int count1=Integer.valueOf(tempList.get(1).toString());
	if(count1>max){
		max=count1;
	}
	rowStr+=count1+"ZvZ";
	colStr+=date_timeTemp+"ZvZ";
}
CfgSpDao cfgSpDao=new CfgSpDao();
Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_code,'(',sp_name,')') from cfg_sp");

CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(cid,'(',name,')') from cfg_company");
String flashUrl=CacheSystem.WEB_PATH+"_js/open-flash-chart.swf?data="+CacheSystem.WEB_PATH+"_js/open-flash-chart.jsp?flashType=bar%26title="+azul.JspUtil.decode("收入走势图")+"%26info="+azul.JspUtil.decode("条数")+"%26max="+max+"%26rowStr="+azul.JspUtil.decode(rowStr)+"%26colStr="+azul.JspUtil.decode(colStr)+"%26random="+new java.util.Random().nextLong();
%>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
  <table width="100%" class="table_noborder">
<tr>
<td>起始日期&nbsp;&nbsp;
  <input name="startDate" type="text" id="startDate" value="<%=startDate%>" onclick="calendar(this)" style="width:70px" /></td>
<td>截止日期&nbsp;
  <input name="endDate" type="text" id="endDate" value="<%=endDate%>"  onclick="calendar(this)" style="width:70px"/></td>
<td>手机号码&nbsp;
  <input name="mobile" type="text" id="mobile" value="<%=mobile%>" style="width:100px"/></td>
<td>地区信息
  <input name="area" type="text" id="area" value="<%=area%>" style="width:100px"/>
&nbsp;</td>
<td><a href="#" onclick="_jsSearch('chartDay.jsp')"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a> &nbsp;</td>
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
  <td><select name="fee_type" id="fee_type">
    <option value="">类型</option>
    <option value="A">A</option>
    <option value="B">B</option>
    <option value="C">C</option>
  </select></td>
  <td>&nbsp;<span class="STYLE1">总金额<span id="allMoney"></span>元</span></td>
  <td><input name="button" type="button" onclick="saveAsExcel('TableColor','fee.xls')" value="导出Excel" /></td>
  </tr>
</table>
<table width="100%" class="table_noborder">
<tr height="30"><td></td></tr>
<tr><td>
<div align="center">
<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
	codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0"
	width="700" height="350" id="ie_chart"
	align="middle">
	<param name="allowScriptAccess" value="sameDomain" />
	<param name="movie"
		value="<%=flashUrl%>" />
	<param name="width" value="700" />
	<param name="height" value="350" />
	<param name="quality" value="high" />
	<param name="bgcolor" value="#FFFFFF" />
	<embed src="<%=flashUrl%>" quality="high"
		bgcolor="#FFFFFF" width="700" height="350"
		name="chart" align="middle" allowScriptAccess="sameDomain"
		type="application/x-shockwave-flash"
		pluginspage="http://www.macromedia.com/go/getflashplayer"
		id="chart" />
</object>
</div>
</td></tr>
</table>
</form>
<script>
function saveAsExcel(excelTable,fileName){
	var divText=document.getElementById(excelTable).outerHTML;
	var oWin=window.open("","","top=1000,left=2000");
	with(oWin) {
		document.write(divText);
		document.execCommand('Saveas',true,fileName);
	}
}
<%
out.println(JspUtil.initSelect("sid",sidMap,sid));
out.println(JspUtil.initSelect("cid",cidMap,cid));
%>
initElem("fee_type","<%=fee_type%>");
</script>
</body>
</html>