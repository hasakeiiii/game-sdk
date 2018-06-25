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
StringBuffer conditionStrSB=new StringBuffer("select * from "+tableName+" where ok=1");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"cid",cid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"sid",sid,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"msg",msg,"like");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"province",province,"=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",startDate,">=");
azul.JspUtil.appendWhere(paramSB,conditionStrSB,"date_time",endDate,"<=");
String pageSql=conditionStrSB.toString();
String linkParam=paramSB.toString();

//System.out.println("----------------");
//System.out.println(pageSql);
CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
java.util.Map cidMap=cfgCompanyDao.getSelectMap("select cid,concat(name,'(',cid,')') from cfg_company order by name");
int pagesize=30;//每页记录数
int pageno=JspUtil.getInt(request.getParameter("pageno"),1);
int recordcount=0;//得到记录总数
ArrayList list=new ArrayList();
if("true".equals(JspUtil.getStr(request.getParameter("search"),"true"))){
	recordcount=chargeDao.getRecordCount(pageSql);//得到记录总数
	list=chargeDao.getList(pageno,pagesize,pageSql);
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
  &nbsp;&nbsp;合作厂商&nbsp;&nbsp;
  <select name="cid" id="cid">
  <option value="">请选择</option>
  </select>
  地区&nbsp;&nbsp;
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
&nbsp;&nbsp;命令
<input name="msg" type="text" value="<%=msg%>"/>
地址
<input name="spnum" type="text" id="spnum" value="<%=spnum%>"/>
&nbsp; <a href="#" onclick="_jsSearch('billList.jsp')"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/></a>

  <table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>费用ID</td>
<td>LinkId</td>
<td id="js_sort_sid">SID</td>
<td id="js_sort_cid">厂商ID</td>
<td>金额(元)</td>
<td>所属运营商</td>
<td id="js_sort_mobile">手机号码</td>
<td id="js_sort_province">省份</td>
<td>市区</td>
<td id="js_sort_date_time">时间</td>
</tr>
<%
int allMoney=0;
for(int i=0;i<list.size();i++){
    Charge charge=(Charge)list.get(i);
    allMoney+=charge.getFee();
	int operator=charge.getOperator();
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
<td><%=charge.getChargeId()%></td>
<td><%=charge.getLinkid()%></td>
<td><%=charge.getSid()%></td>
<td><%=charge.getCid()%></td>
<td><%=charge.getFee()%></td>
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
<%
out.println(JspUtil.initSelect("sid",sidMap,sid));
out.println(JspUtil.initSelect("cid",cidMap,cid));
%>
initElem("province","<%=province%>");
</script>
</body>
</html>