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
String[] arr=azul.JspUtil.getDateDay(startDate,endDate);
startDate=arr[0];
endDate=arr[1];
ChargeDao chargeDao=new ChargeDao();
StringBuffer conditionStrSB=new StringBuffer();
conditionStrSB.append(" and date_time>='");
conditionStrSB.append(startDate);
conditionStrSB.append("' and date_time<='");
conditionStrSB.append(endDate);
conditionStrSB.append(" 23:59:59'");
String tableName=azul.JspUtil.getTableName("charge",startDate);
String pageSql="select province,count(*),sum(fee) from "+tableName+" where fee_type='A' and ok=1 and cid='"+sessionCid+"'";
pageSql=pageSql+conditionStrSB.toString()+" group by province order by sum(fee) desc";
//System.out.println("--------------------------------");
//System.out.println(pageSql);
ArrayList list=chargeDao.getObjectList(pageSql);
%>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td>
起始日期&nbsp;
  <input name="startDate" type="text" id="startDate" value="<%=startDate%>" onclick="calendar(this)" style="width:70px" /> 
截止日期&nbsp;
<input name="endDate" type="text" id="endDate" value="<%=endDate%>"  onclick="calendar(this)" style="width:70px"/>
&nbsp;&nbsp;&nbsp;
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>省份</td>
<td>总计</td>
<td>总计金额</td>
</tr>
<%
int allCount1=0;
double allSum1=0;
for(int i=0;i<list.size();i++){ 
	ArrayList tempList=(ArrayList)list.get(i); 
	String province="未知";
	Object obj=tempList.get(0);
	if(obj!=null){
		province=(String)obj;
	}
	int count1=Integer.valueOf(tempList.get(1).toString());
	double sum1=Double.valueOf(tempList.get(2).toString());
	allCount1+=count1;
	allSum1+=sum1;
%>
<tr>
<td><%=i+1%></td>
<td><span class="tdGreen"><%=province%></span></td>
<td><b><%=count1%>条</b></td>
<td><b><%=sum1%>元</b></td>
</tr>
<%
}
%>
<tr>
<td>总计</td>
<td></td>
<td><b><%=allCount1%>条</b></td>
<td><b><%=allSum1%>元</b></td>
</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript" src="../_js/meizzDate.js"></script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script>
function goInfo(province){
    location="cidChargeListArea.jsp?startDate=<%=startDate%>&endDate=<%=endDate%>&province="+province;
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
	document.mainForm.action="cidChargeListArea.jsp";
	document.mainForm.submit();
}
</script>
