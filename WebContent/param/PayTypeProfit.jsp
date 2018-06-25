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
				<td width="8%">游戏类型&nbsp;&nbsp;
					<select name="game_type" onchange="gametypechg(this)" id="game_type">
					  				 	
					</select>&nbsp;
				</td>
	
				<td width="15%">游戏&nbsp;&nbsp;
					<select name="game_no" onchange="gamechg(this)" id="game_no">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
	
				<td width="10%">所属商务&nbsp;&nbsp;
					<select name="business_no" id="business_no" onchange="businesschg()">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
	
				<td width="10%">渠道&nbsp;&nbsp;
					<select name="channel_no" id="channel_no" onchange = "channelchg()">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
	
				<td width="10%">APK包号&nbsp;&nbsp;
					<select name="packet_id" onchange=packetchg(this) id="packet_id">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
<td width="10%">显示类型&nbsp;&nbsp;
<select name="distype"  id="distype">
  <option value="">请选择</option>
</select>&nbsp;
</td>
<td width="10%">时间&nbsp;年
<select name="selyear"  id="selyear">
</select>&nbsp;月

<select name="selmonth" id="selmonth">
</select>
</td>
<td width="10%">
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"/></a>
</td>
</tr>

</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>日期</td>
<td>电信爱游戏</td>
<td>天翼空间</td>
<td>联通沃商店</td>
<td>沃+</td>
<td>移动MM</td>
<td>移动MM结算</td>
<td>PC页游</td>
<td>PC页游结算</td>
<td>盒饭</td>
<td>盒饭结算</td>
<td>乐动</td>
<td>乐动结算</td>
<td>游戏基地</td>
<td>游戏基地结算</td>
<td>十分科技-电信</td>
<td>十分科技-电信结算</td>
<!-- <td>易宝</td>
<td>支付宝</td>
<td>财付通</td>
<td>银联</td>
<td>拇指币</td>
<td>专属币</td> -->
</tr>
<%
PayTypeCountDao payTypeCountDao = new PayTypeCountDao();
int mmSettle = payTypeCountDao.getPaySettleCount("mmpay");
int webSettle = payTypeCountDao.getPaySettleCount("jdpay");
int boxSettle = payTypeCountDao.getPaySettleCount("fanhe");
int openSettle = payTypeCountDao.getPaySettleCount("openpay");
int ldSettle = payTypeCountDao.getPaySettleCount("leDong");
int jdSettle = payTypeCountDao.getPaySettleCount("mmjd");
int ananSettle = payTypeCountDao.getPaySettleCount("ananpay");
ChannelDataReqSta channelDataSta = new ChannelDataReqSta(dataReqService.list);
for(int i=0;i<dataReqService.list.size();i++){
	ChannelDataReq gamedata=(ChannelDataReq)dataReqService.list.get(i);	
%>
<tr>
<td><%=gamedata.date%></td>
<td><%=String.format("%.2f",gamedata.tcPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.openPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.uniPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.woplusPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.mmPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.mmPay/10000.0*mmSettle)%></td>
<td><%=String.format("%.2f",gamedata.webPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.webPay/10000.0*webSettle)%></td>
<td><%=String.format("%.2f",gamedata.boxPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.boxPay/10000.0*boxSettle)%></td>
<td><%=String.format("%.2f",gamedata.ldPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.ldPay/10000.0*ldSettle)%></td>
<td><%=String.format("%.2f",gamedata.jdPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.jdPay/10000.0*jdSettle)%></td>
<td><%=String.format("%.2f",gamedata.openPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.openPay/10000.0*openSettle)%></td>
<%-- <td><%=String.format("%.2f",gamedata.yeePay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.aliPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.tenPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.unionPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.mzpay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.onlypay/100.0)%></td> --%>
</tr>
<%
}
%>

<tr>
<td>总计</td>
<td><%=String.format("%.2f",channelDataSta.tcpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.openpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.unipaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.wopluspaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.mmpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.settlemmpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.webpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.settlewebpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.boxpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.settleboxpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.ldpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.settleldpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.jdpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.settlejdpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.openpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.settleopenpaycount/100.0)%></td>
<%-- <td><%=String.format("%.2f",channelDataSta.yeepaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.alipaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.tenpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.unionpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.mzpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.olpaycount/100.0)%></td> --%>
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

function gametypechg(obj){
	document.mainForm.action="PayTypeProfit.jsp?op=selgame>";
	document.mainForm.submit();
}

function gamechg(obj)
{
	//alert(obj.value);
	document.mainForm.action="PayTypeProfit.jsp?op=selgame";
	document.mainForm.submit();
}
function businesschg(obj)
{
	//alert(obj.value);
	document.mainForm.action="PayTypeProfit.jsp?op=selbusiness";
	document.mainForm.submit();
}
function channelchg(obj)
{
	document.mainForm.action="PayTypeProfit.jsp?op=selchannel";
	document.mainForm.submit();
}

function packetchg(obj)
{
	document.mainForm.action="PayTypeProfit.jsp?op=sel";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="PayTypeProfit.jsp?op=sel";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="PayTypeProfit.jsp?op=sel";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="PayTypeProfit.jsp?op=sch";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<!-- <script type="text/javascript" src="../_js/TableBlankRow.js"></script> -->
</body>
</html>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript">
<%
out.println(azul.JspUtil.initSelect("game_type",dataReqService.gameTypeMap,dataReqService.game_type));
out.println(azul.JspUtil.initSelectSortByValuePinyin("game_no",dataReqService.gameMap,dataReqService.game_no));
out.println(azul.JspUtil.initSelect("business_no",dataReqService.businesserMap,dataReqService.business_no));
out.println(azul.JspUtil.initSelectSortByValuePinyin("channel_no",dataReqService.channelMap,dataReqService.channel_no));
out.println(azul.JspUtil.initSelect("packet_id",dataReqService.packetMap,dataReqService.packet_id));
out.println(azul.JspUtil.initSelect("distype",dataReqService.diptypeMap,dataReqService.distype));
out.println(azul.JspUtil.initSelect("selyear",dataReqService.yearMap,dataReqService.selyear));
out.println(azul.JspUtil.initSelect("selmonth",dataReqService.monthMap,dataReqService.selmonth));
%>
</script>
