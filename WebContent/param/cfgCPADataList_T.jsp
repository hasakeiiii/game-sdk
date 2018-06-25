<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<%@ page import="viewmodel.*" %>
<%@ page import="service.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DataReqService dataReqService = new DataReqService(request,session);
dataReqService.Handle();
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
<td width="10%">游戏&nbsp;&nbsp;
<select name="game_no" onchange=gamechg(this) id="game_no">
  <option value="">请选择</option>
</select>&nbsp;&nbsp;
</td>
<td width="10%">APK包号&nbsp;&nbsp;
<select name="packet_id" onchange=packetchg(this) id="packet_id">
  <option value="">请选择</option>
</select>&nbsp;&nbsp;
</td>
<td width="30%">时间&nbsp;年
<select name="selyear" onchange=yearchg(this) id="selyear">
</select>&nbsp;月
<select name="selmonth" onchange=monthchg(this) id="selmonth">
</select>
</td>
<td width="10%">
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"/></a>
</td>
</tr>
</tr>
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>日期</td>
<td>激活用户</td>
</tr>
<%
ChannelDataReqSta channelDataSta = new ChannelDataReqSta(dataReqService.list);
cfgGameDataReqTableColorStaStr gameDataReqTableColorStaStr= new cfgGameDataReqTableColorStaStr(channelDataSta);
for(int i=0;i<dataReqService.list.size();i++){
	ChannelDataReq gamedata=(ChannelDataReq)dataReqService.list.get(i);
	cfgGameDataReqTableColorStr channelDataStr = new cfgGameDataReqTableColorStr(gamedata,dataReqService.game_no);


%>
<tr>
<td><%=channelDataStr.datestr%></td>
<td><%=channelDataStr.actStr%></td>
</tr>
<%
}
%>
<tr>
<td>总计</td>
<td><%=channelDataSta.actcount%></td>
</tr>
</table>
</form>
<%
String linkParam="";
out.print(azul.JspUtil.getPageCode(dataReqService.pageno,dataReqService.pagesize,dataReqService.recordcount,request,linkParam));
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
  
}

function gamechg(obj)
{
	//alert(obj.value);
	document.mainForm.action="cfgCPADataList_T.jsp?op=selgame";
	document.mainForm.submit();
}


function packetchg(obj)
{
	document.mainForm.action="cfgCPADataList_T.jsp?op=sel";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="cfgCPADataList_T.jsp?op=sel";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="cfgCPADataList_T.jsp?op=sel";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="cfgCPADataList_T.jsp?op=sch";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript">
<%
out.println(azul.JspUtil.initSelect("game_no",dataReqService.gameMap,dataReqService.game_no));
out.println(azul.JspUtil.initSelect("packet_id",dataReqService.packetMap,dataReqService.packet_id));
out.println(azul.JspUtil.initSelect("selyear",dataReqService.yearMap,dataReqService.selyear));
out.println(azul.JspUtil.initSelect("selmonth",dataReqService.monthMap,dataReqService.selmonth));
%>
</script>
