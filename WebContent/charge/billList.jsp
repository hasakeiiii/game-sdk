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
String msg=JspUtil.getStr(request.getParameter("msg"),"");
String spnum=JspUtil.getStr(request.getParameter("spnum"),"");
String province=JspUtil.getStr(request.getParameter("province"),"");
String msg_type=JspUtil.getStr(request.getParameter("msg_type"),"");
String fee_type=JspUtil.getStr(request.getParameter("fee_type"),"");
String sid=JspUtil.getStr(request.getParameter("sid"),"");
String cid=JspUtil.getStr(request.getParameter("cid"),"");
String startDate=JspUtil.getStr(request.getParameter("startDate"),"");
String endDate= JspUtil.getStr(request.getParameter("endDate"),"");
String[] arr=azul.JspUtil.getDateDay(startDate,endDate);
startDate=arr[0];
endDate=arr[1];

ChargeDao chargeDao=new ChargeDao();
String tableName=azul.JspUtil.getTableName("charge",startDate);

CfgSpDao cfgSpDao=new CfgSpDao();
Map sidMap=cfgSpDao.getSelectMap("select sp_code,concat(sp_name,'(',sp_code,')') from cfg_sp");

StringBuffer paramSB=new StringBuffer();
StringBuffer conditionStrSB=new StringBuffer("select count(*) from "+tableName+" where ok=1");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"cid",cid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"sid",sid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"fee_type",fee_type,"=");
if("0".equals(msg_type)){
	azul.JspUtil.appendWhere(paramSB,conditionStrSB,"msg",msg,"="); 
}
else{
	azul.JspUtil.appendWhere(paramSB,conditionStrSB,"msg",msg,"like"); 
}
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"spnum",spnum,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"province",province,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",startDate,">=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",endDate,"<=");
String pageSql=conditionStrSB.toString();
String linkParam=paramSB.toString();

//System.out.println("----------------");
//System.out.println(pageSql);
CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
java.util.Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(name,'(',cid,')') from cfg_company order by name");
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
  &nbsp;&nbsp;&nbsp;合作SP&nbsp;&nbsp;
  <select name="sid" id="sid">
    <option value="">请选择</option>
  </select>
  费用类型
  <select name="fee_type" id="fee_type">
    <option value="">请选择</option>
    <option value="A">A</option>
    <option value="B">B</option>
    <option value="C">C</option>
      </select>
  <br>
  合作厂商&nbsp;&nbsp;
  <select name="cid" id="cid">
  <option value="">请选择</option>
  </select>
  地区&nbsp;&nbsp;
  <select name="province" id="province">
    <option value="">请选择</option>
    <%
	for(int i=0;i<common.Constant.AREA.length;i++){
	%>
    <option value="<%=common.Constant.AREA[i]%>"><%=common.Constant.AREA[i]%></option>
    <%
	}
    %>
  </select>
&nbsp;&nbsp;命令<input name="msg_type" type="radio" value="0" checked="checked" />
精确<input type="radio" name="msg_type" value="1" />模糊
<input name="msg" type="text" value="<%=msg%>"/>
地址
<input name="spnum" type="text" id="spnum" value="<%=spnum%>"/>
&nbsp; <a href="#" onclick="_jsSearch('billList.jsp')"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a>
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
<%
out.println(JspUtil.initSelect("sid",sidMap,sid));
out.println(JspUtil.initSelect("cid",cidMap,cid));
%>
initElem("province","<%=province%>");
initElem("fee_type","<%=fee_type%>");
initElem("msg_type","<%=msg_type%>");
</script>
</body>
</html>