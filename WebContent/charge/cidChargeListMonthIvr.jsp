<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="azul.*" %>
<%@ page import="java.util.*" %>
<jsp:include page="../check.jsp?check_role=cid" flush="true" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />

</head>
<body>
<%
String sessionCid=JspUtil.getStr(session.getAttribute("cid"),"");
if("".equals(sessionCid)){
   %>
   <script>alert("无法得到厂商编号");</script>
   <%
   return;
}
String startDate=JspUtil.getStr(request.getParameter("startDate"),"");
String endDate= JspUtil.getStr(request.getParameter("endDate"),"");
String spnum= JspUtil.getStr(request.getParameter("spnum"),"");
String[] arr=azul.JspUtil.getDateWeek(startDate,endDate);
startDate=arr[0];
endDate=arr[1];

String tableName=azul.JspUtil.getTableName("charge_ivr",startDate);
StringBuffer paramSB=new StringBuffer();
StringBuffer conditionStrSB=new StringBuffer("select DATE(date_time),sum(case when operator=1 then 1 else 0 end),sum(case when operator=1 then fee else 0 end),sum(case when operator=2 then 1 else 0 end),sum(case when operator=2 then fee else 0 end) from "+tableName+" where fee_type='A' and cid='"+sessionCid+"'");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"spnum",spnum,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",startDate,">=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",endDate,"<=");
String pageSql=conditionStrSB.toString()+" group by DATE(date_time)";

//System.out.println("--------------------------------");
//System.out.println(pageSql);
ChargeIvrDao chargeIvrDao=new ChargeIvrDao();
List list=chargeIvrDao.getObjectList(pageSql);

//JspUtil.p(cidRate);
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
%>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td>起始日期&nbsp;&nbsp;
  <input name="startDate" type="text" id="startDate" value="<%=startDate%>" onfocus="calendar(this)" style="width:70px" />
  &nbsp;截止日期&nbsp;
  <input name="endDate" type="text" id="endDate" value="<%=endDate%>"  onfocus="calendar(this)" style="width:70px"/>
 &nbsp;&nbsp;&nbsp;拨打号码
 <input name="spnum" type="text" id="spnum" value="<%=spnum%>"/> 
 &nbsp;
<a href="#" onclick="_jsSearch('cidChargeListMonthIvr.jsp')"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a>
&nbsp;</td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>时间</td>
<td>移动数据(条)</td>
<td>移动数据总计(元)</td>
<td>联通数据(条)</td>
<td>联通数据总计(元)</td>
<td>总计(条)</td>
<td>总计金额(元)</td>
</tr>
<%
double allCount1=0;
double allCount2=0;
double allSum1=0;
double allSum2=0;
for(int i=0;i<list.size();i++){ 
	List tempList=(List)list.get(i); 
	Date date_timeTemp=(Date)tempList.get(0);
	double count1=Double.valueOf(tempList.get(1).toString());
	double sum1=Double.valueOf(tempList.get(2).toString());
	double count2=Double.valueOf(tempList.get(3).toString());
	double sum2=Double.valueOf(tempList.get(4).toString());
	allCount1+=count1;
	allCount2+=count2;
	allSum1+=sum1;
	allSum2+=sum2;
%>
<tr>
<td><%=i+1%></td>
<td><a href="#" onclick="goInfo('<%=date_timeTemp%>')"><span class="tdBlue"><%=date_timeTemp%></span></a></td>
<td><%=count1%></td>
<td><%=sum1%></td>
<td><%=count2%></td>
<td><%=sum2%></td>
<td><b><%=count1+count2%></b></td>
<td><b><%=(sum1+sum2)%></b></td>
</tr>
<%
}
%>
<tr>
<td>总计</td>
<td></td>
<td><%=allCount1%></td>
<td><%=allSum1%></td>
<td><%=allCount2%></td>
<td><%=allSum2%></td>
<td><b><%=allCount1+allCount2%></b></td>
<td><b><%=allSum1+allSum2%></b></td>
</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript" src="../_js/meizzDate.js"></script>
<script type="text/javascript" src="../_js/openWin.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script>
function goInfo(dateTime){
    //location="chargeList.jsp?startDate="+dateTime+"&endDate="+dateTime;
}
function saveAsExcel(){
	/*
	var divText=document.getElementById('TableColor').outerHTML;
	var oWin=window.open("","","top=1000,left=2000");
	with(oWin) {
		document.write(divText);
		document.execCommand('Saveas',true,'stat.xls');
	}
	*/
	openWinBar("../excel.jsp?info=xxx","导出报表","300","300");
} 
</script>