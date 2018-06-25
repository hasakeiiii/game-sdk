<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>

<%
String order_no=azul.JspUtil.getStr(request.getParameter("order_no"),"");
String action=azul.JspUtil.getStr(request.getParameter("op"),"");
MmPayDao mmPayDao = new MmPayDao();
MmPay mmPay = null;
PayDao payDao = new PayDao();
Pay pay = null;
ActivateDao acDao = new ActivateDao();
Activate activate = null;
     if(action.equals("ation"))
     {
    	  mmPay=mmPayDao.getPayRecordByOrderNO(order_no);//getPayRecordByOrderNO
    	  if(mmPay != null){
    	 	 pay = payDao.getPayRecordByCpOrder(mmPay.app_key);
    	 	 
    	  }
    	  if(pay != null){
    		  activate = acDao.getRecord(pay.device_id);
    	  }
     }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"/>
</head>
<body>
<form name="mainForm" method="post" style="margin:0;padding:0">
<table width="100%" class="table_noborder">
<tr>
<td width="10%">订单号&nbsp;&nbsp;<input name="order_no" value="<%=order_no%>" />&nbsp;&nbsp;
</td>

<td width="10%">
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_search.gif" border="0" alt="搜索" align="absmiddle"/>
</a></td>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>序号</td>
<td>packet_id</td>
<td>订单号</td>
<td>trade_no</td>
<td>应用ID</td>
<td>渠道号</td>
<td>金额</td>
<td>透传参数</td>
<td>激活日期</td>
<td>激活时间</td>
<td>在线时长</td>
<%
    if(mmPay != null)
    {
%>
<tr>
<td><%=mmPay.getId()%></td>
<% 
if(pay != null)
{
%>
<td><%=pay.getPacketId()%></td>
<% 

}else{
%>
<td>pay找不到订单</td>
<% 
}
%>
<td><%=mmPay.getOrderNo()%></td>
<td><%=mmPay.getTradeNo()%></td>
<td><%=mmPay.getAppId()%></td>
<td><%=mmPay.getChannel()%></td>
<td><%=mmPay.getAmount()%></td>
<td><%=mmPay.getAppKey()%></td>
<% 
if(activate != null)
{
%>
<td><%=activate.getDate()%></td>
<td><%=activate.getTime()%></td>
<td><%=activate.getOnlinetime()%></td>
<% 

}else{
%>
<td></td>
<td></td>
<td></td>
<% 
}
%>
</tr>
<%
}
%>
</table>
</form>

<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
   
}

function editAct(id){
    //window.location="notisfyCPAction.jsp?id="+id;
	//PayDao  payDao=new PayDao();
}
function infoAct(id){
    //window.location="cfgSpInfo.jsp?id="+id;
}
function goSearch(){
    /*if(document.mainForm.keyWord.value==""){
	     error("请输入查询关键字");
		 document.mainForm.keyWord.focus();
		 return;
	}*/
	document.mainForm.action="MmpaySearch.jsp?op=ation";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
