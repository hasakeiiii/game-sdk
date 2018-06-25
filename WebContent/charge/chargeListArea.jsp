<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=admin" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="common.ChartFlash"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/meizzDate.js"></script>
</head>
<body>
<%
String sid=JspUtil.getStr(request.getParameter("sid"),"");
String cid=JspUtil.getStr(request.getParameter("cid"),"");
String fee_type=JspUtil.getStr(request.getParameter("fee_type"),"");
String startDate=JspUtil.getStr(request.getParameter("startDate"),"");
String endDate= JspUtil.getStr(request.getParameter("endDate"),"");
String[] arr=azul.JspUtil.getDateWeek(startDate,endDate);
startDate=arr[0];
endDate=arr[1];
String tableName=azul.JspUtil.getTableName("charge",startDate);
StringBuffer paramSB=new StringBuffer();
StringBuffer conditionStrSB=new StringBuffer("select province,count(*),sum(fee) from "+tableName+" where ok=1");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"cid",cid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"sid",sid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"fee_type",fee_type,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",startDate,">=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",endDate,"<=");
String pageSql=conditionStrSB.toString()+" group by province order by sum(fee) desc";
//System.out.println("--------------------------------");
//System.out.println(pageSql);
ChargeDao chargeDao=new ChargeDao();
List list=chargeDao.getObjectList(pageSql);

CfgSpDao cfgSpDao=new CfgSpDao();
Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp where 1=1");

CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
java.util.Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(name,'(',cid,')') from cfg_company order by name");

String linkParam="sid="+sid+"&cid="+cid+"&fee_type="+fee_type+"&startDate="+startDate+"&endDate="+endDate;
%>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td>
起始日期&nbsp;
  <input name="startDate" type="text" id="startDate" value="<%=startDate%>" onfocus="calendar()" style="width:70px" /><a href="#" onclick="calendar(document.mainForm.startDate)"><img src="../_images/dateButton.gif" width="34" height="21" align="absmiddle"/></a> 
截止日期&nbsp;
<input name="endDate" type="text" id="endDate" value="<%=endDate%>"  onfocus="calendar()" style="width:70px"/>
<a href="#" onclick="calendar(document.mainForm.endDate)"><img src="../_images/dateButton.gif" width="34" height="21" align="absmiddle"/></a>&nbsp;&nbsp;合作SP&nbsp;&nbsp;
<select name="sid" id="sid">
  <option value="">---请选择---</option>
</select>&nbsp;&nbsp;合作厂商&nbsp;&nbsp;
<select name="cid" id="cid">
<option value="">请选择</option>
</select>&nbsp;
<select name="fee_type" id="fee_type">
  <option value="">类型</option>
  <option value="A">A</option>
  <option value="B">B</option>
  <option value="C">C</option>
</select>&nbsp;
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>省份</td>
<td>总计</td>
<td>总计金额</td>
<td>操作</td>
</tr>
<%
int allCount1=0;
int allSum1=0;
String areaStr="";
String numStr="";
for(int i=0;i<list.size();i++){ 
	List tempList=(List)list.get(i); 
	String province="未知";
	String className="tdRed";
	Object obj=tempList.get(0);
	if(obj!=null){
		province=(String)obj;
		className="tdBlue";
	}
	int count1=Integer.valueOf(tempList.get(1).toString());
	int sum1=Integer.valueOf(tempList.get(2).toString());
	allCount1+=count1;
	allSum1+=sum1;
	if(i!=list.size()){
		areaStr+=province+"ZvZ";
		numStr+=count1+"ZvZ";
	}
%>
<tr>
<td><%=i+1%></td>
<td><a href="#" onclick="goInfo('<%=JspUtil.decode(province)%>')"><span class="<%=className%>"><%=province%></span></a></td>
<td><b><%=count1%>条</b></td>
<td><b><%=sum1%>元</b></td>
<td><a href="#" onclick="goInfo('<%=JspUtil.decode(province)%>')">查看明细</a></td>
</tr>
<%
}
//String flashUrl=CacheSystem.WEB_PATH+"_js/open-flash-chart.swf?data="+CacheSystem.WEB_PATH+"_js/open-flash-chart.jsp?flashType=pie%26title="+azul.JspUtil.decode("地区统计图")+"%26info="+azul.JspUtil.decode("元")+"%26colStr="+azul.JspUtil.decode(areaStr)+"%26rowStr="+azul.JspUtil.decode(numStr)+"%26random="+new java.util.Random().nextLong();
String flashUrl=ChartFlash.getUrl("pie","地区统计图","元",areaStr,numStr);
%>
<tr>
<td>总计</td>
<td></td>
<td><b><%=allCount1%>条</b></td>
<td><b><%=allSum1%>元</b></td>
<td></td>
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
</body>
</html>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script>
function goInfo(province){
    location="chargeList.jsp?<%=linkParam%>&province="+province;
}
function goSearch(){
    if(document.mainForm.startDate.value==""){
        error("请选择起始日期！");
        return;
    }
    if(document.mainForm.endDate.value==""){
        error("请选择结束日期！");
        return;
    }
    if(document.mainForm.startDate.value.substring(0,7)!=document.mainForm.endDate.value.substring(0,7)){
        error("暂不支持跨月查询，请选择同月日期！");
        return;
    }
	document.mainForm.action="chargeListArea.jsp";
	document.mainForm.submit();
}
<%
out.println(JspUtil.initSelect("sid",sidMap,sid));
out.println(JspUtil.initSelect("cid",cidMap,cid));
%>
</script>
