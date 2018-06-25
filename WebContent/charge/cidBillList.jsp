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
<script type="text/javascript" src="../_js/jquery.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
</head>
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
String province=JspUtil.getStr(request.getParameter("province"),"");
String msg_type=JspUtil.getStr(request.getParameter("msg_type"),"");
String startDate=JspUtil.getStr(request.getParameter("startDate"),"");
String endDate= JspUtil.getStr(request.getParameter("endDate"),"");
String[] arr=azul.JspUtil.getDateDay(startDate,endDate);
startDate=arr[0];
endDate=arr[1];

ChargeDao chargeDao=new ChargeDao();
String tableName=azul.JspUtil.getTableName("charge",startDate);

StringBuffer paramSB=new StringBuffer();
StringBuffer conditionStrSB=new StringBuffer("select count(*) from "+tableName+" where ok=1 and fee_type='A' and cid='"+sessionCid+"'");
if("0".equals(msg_type)){
    azul.JspUtil.appendWhere(paramSB,conditionStrSB,"msg",msg,"="); 
}
else{
	azul.JspUtil.appendWhere(paramSB,conditionStrSB,"msg",msg,"like"); 
}
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"province",province,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",startDate,">=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",endDate,"<=");
String pageSql=conditionStrSB.toString();
String linkParam=paramSB.toString();

//System.out.println("----------------");
//System.out.println(pageSql);
String recordcount="0";//得到记录总数
if("true".equals(JspUtil.getStr(request.getParameter("search"),"true"))){
	recordcount=String.valueOf(chargeDao.getValue(pageSql));//得到记录总数
}
%>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
起始日期&nbsp;&nbsp;
  <input name="startDate" type="text" id="startDate" value="<%=startDate%>" onclick="calendar(this)" style="width:70px" />
  &nbsp;截止日期&nbsp;
  <input name="endDate" type="text" id="endDate" value="<%=endDate%>"  onclick="calendar(this)" style="width:70px"/>
  &nbsp;&nbsp;&nbsp;  地区&nbsp;&nbsp;
  <select name="province" id="province">
    <option value="">请选择</option>
    <%
	for(int i=1;i<common.Constant.AREA.length;i++){
	%>
    <option value="<%=common.Constant.AREA[i]%>"><%=common.Constant.AREA[i]%></option>
    <%
	}
    %>
  </select>
&nbsp;&nbsp;命令<input name="msg_type" type="radio" value="0" checked="checked" />
精确<input type="radio" name="msg_type" value="1" />模糊
<input name="msg" type="text" value="<%=msg%>" size="10"/>
地址
<input name="spnum" type="text" id="spnum" value="<%=spnum%>" size="10"/>
&nbsp; <a href="#" onclick="_jsSearch('cidBillList.jsp')"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a>
</form>
总数据条数为:<%=recordcount%>条
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/meizzDate.js"></script>
<script>
function saveAsExcel(excelTable,fileName){
	var divText=document.getElementById(excelTable).outerHTML;
	var oWin=window.open("","","top=1000,left=2000");
	with(oWin) {
		document.write(divText);
		document.execCommand('Saveas',true,fileName);
	}
} 
initElem("province","<%=province%>");
initElem("msg_type","<%=msg_type%>");
</script>
</body>
</html>