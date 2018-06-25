<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
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
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script type="text/javascript" src="../_js/meizzDate.js"></script>
<script type="text/javascript" src="../_js/jquery.messager.js"></script>
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
String msg=JspUtil.getStr(request.getParameter("msg"),"");
String spnum=JspUtil.getStr(request.getParameter("spnum"),"");
String msg_type=JspUtil.getStr(request.getParameter("msg_type"),"");
String startDate=JspUtil.getStr(request.getParameter("startDate"),"");
String endDate= JspUtil.getStr(request.getParameter("endDate"),"");
String[] arr=azul.JspUtil.getDateDay(startDate,endDate);
startDate=arr[0];
endDate=arr[1];
String tableName=azul.JspUtil.getTableName("charge",startDate);
StringBuffer paramSB=new StringBuffer();
StringBuffer conditionStrSB=new StringBuffer("select DATE(date_time),count(fee),sum(fee) from "+tableName+" where ok=1 and fee_type='A' and cid='"+sessionCid+"'");
if("0".equals(msg_type)){
	azul.JspUtil.appendWhere(paramSB,conditionStrSB,"msg",msg,"="); 
}
else{
	azul.JspUtil.appendWhere(paramSB,conditionStrSB,"msg",msg,"like"); 
}
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"spnum",spnum,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",startDate,">=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",endDate,"<=");
String pageSql=conditionStrSB.toString()+" group by DATE(date_time)";
String linkParam=paramSB.toString();
//System.out.println("--------------------------------");
System.out.println(pageSql);
ChargeDao chargeDao=new ChargeDao();
List list=chargeDao.getObjectList(pageSql);

CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
CfgCompany cfgCompany=(CfgCompany)cfgCompanyDao.loadBySql("select * from cfg_company where cid='"+sessionCid+"'");

java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
%>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td>起始日期&nbsp;&nbsp;
  <input name="startDate" type="text" id="startDate" value="<%=startDate%>" onfocus="calendar(this)" style="width:70px" />
  &nbsp;截止日期&nbsp;
  <input name="endDate" type="text" id="endDate" value="<%=endDate%>"  onfocus="calendar(this)" style="width:70px"/>
  &nbsp;
&nbsp;命令
<input name="msg_type" type="radio" value="0" checked="checked" />
精确
<input type="radio" name="msg_type" value="1" />
模糊
<input name="msg" type="text" value="<%=msg%>" size="10"/>
地址
<input name="spnum" type="text" id="spnum" value="<%=spnum%>" size="10"/>
<a href="#" onClick="_jsSearch('cidChargeListMonth.jsp')"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a>&nbsp;&nbsp;公司名称：<%=cfgCompany.getName()%>&nbsp;&nbsp;用户编号：<%=cfgCompany.getCid()%></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>时间</td>
<td>数据条数(条)</td>
<td>数据金额(元)</td>
<td>总计(条)</td>
<td>总计金额(元)</td>
</tr>
<%
int allCount1=0;
int allSum1=0;
for(int i=0;i<list.size();i++){ 
	List tempList=(List)list.get(i); 
	Date date_timeTemp=(Date)tempList.get(0);
	int count1=Integer.valueOf(tempList.get(1).toString());
	int sum1=Integer.valueOf(tempList.get(2).toString());
	allCount1+=count1;
	allSum1+=sum1;
%>
<tr>
<td><%=i+1%></td>
<td><span class="tdGreen"><%=date_timeTemp%></span></td>
<td><%=count1%></td>
<td><%=sum1%></td>
<td><b><%=count1%></b></td>
<td><b><%=sum1%></b></td>
</tr>
<%
}
%>
<tr>
<td>总计</td>
<td></td>
<td><%=allCount1%></td>
<td><%=allSum1%></td>
<td><b><%=allCount1%></b></td>
<td><b><%=allSum1%></b></td>
</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script>
function goInfo(dateTime){
    location="cidChargeList.jsp?"+linkParam+"&startDate="+dateTime+"&endDate="+dateTime;
}
initElem("msg_type","<%=msg_type%>");
</script>
<%
SysHintDao sysHintDao=new SysHintDao();
out.println(sysHintDao.getHint(request));
%>