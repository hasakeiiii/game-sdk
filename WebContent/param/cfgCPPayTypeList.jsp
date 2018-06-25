<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<%@ page import="viewmodel.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DebuUtil.log("cfgCPPayTypeList");
String game_type = request.getParameter("game_type");//20141203加入游戏类型判断
int channel_id=azul.JspUtil.getInt(request.getParameter("channel_id"),-1);
String game_no = request.getParameter("game_no");
String business_no = request.getParameter("business_no");
String channel_no = request.getParameter("channel_no");
String op =  request.getParameter("op");
String distype =  request.getParameter("distype");
String selyear =  request.getParameter("selyear");
String selmonth =  request.getParameter("selmonth");
String BSS_NO = request.getParameter("BSS_NO");
String bssSql = "select no,name from businesser  where 1=1 ";

selmonth =SearchCom.getCurMonth(selmonth);
selyear =SearchCom.getCurYear(selyear);

if(!StringUtil.is_nullString(BSS_NO))
{
	if(BSS_NO.equals("null"))
	{
		BSS_NO = "";
	}
	else
	{
	  business_no = BSS_NO;
	  bssSql +=" and no='"+business_no+"'";
	}
}

//selmonth="04";
DebuUtil.log("selmonth="+selmonth);
DebuUtil.log("business_no="+business_no);
DebuUtil.log("BSS_NO="+BSS_NO);
DebuUtil.log("bssSql="+bssSql);

Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
String role = sysUser.getRole();

Integer ID = sysUser.getId();

String appsql = "select no,name from app where cp_no in(select cp_no from cp_manage where login_user = "+ID+")";



CooperationDao cooperationDao=new CooperationDao();
Map<String,String> gameTypeMap = SearchCom.getGameTypeMap(role);//

if(StringUtil.is_nullString(game_type))
{
   game_type = SearchCom.getGameType(role);
}

Map gameMap=cooperationDao.getSelectMap(appsql);
Map businesserMap=SearchCom.getBusinessMap(game_type,game_no, channel_no, business_no);

Map channelMap=SearchCom.getChannelMap(game_type,game_no, channel_no, business_no);
Map packetMap=SearchCom.getPacketMap(game_type,game_no, channel_no, business_no);

Map yearMap=SearchCom.getYearMap();
Map monthMap=SearchCom.getMonthMap();

java.util.Map diptypeMap=new HashMap<String,String>();
diptypeMap.put("A", "渠道");
//diptypeMap.put("C", "扣量");





String keyWord="";//azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from cfg_sp where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and sp_name like '%"+keyWord+"%'";
}

int pagesize=31;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
//int recordcount=cfgSpDao.getRecordCount(pageSql);//得到记录总数
//List<CfgSp> list=cfgSpDao.getList(pageno,pagesize,pageSql);
ArrayList<ChannelDataReq> list = new ArrayList<ChannelDataReq>();
int recordcount = 0;


if((!StringUtil.is_nullString(op)) &&(op.equals("sch")))//
{
DebuUtil.log("SearchCom.getList");
	list = SearchCom.getList(selyear, selmonth, game_no, "", "", -1, "","");
	recordcount=list.size();
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
				<td width="8%">游戏类型&nbsp;&nbsp;
					<select name="game_type" onchange="gametypechg(this)" id="game_type">
					  				 	
					</select>&nbsp;
				</td>
	
				<td width="15%">游戏&nbsp;&nbsp;
					<select name="game_no" onchange="gamechg(this)" id="game_no">
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
<td>易宝</td>
<td>支付宝</td>
<td>财付通</td>
<td>银联</td>
<td>微信</td>
<td>拇指币</td>
<td>专属币</td>
</tr>
<%
ChannelDataReqSta channelDataSta = new ChannelDataReqSta(list);
for(int i=0;i<list.size();i++){
	String curdate = DateUtil.getDate();
	ChannelDataReq gamedata=(ChannelDataReq)list.get(i);	
%>
<tr>
<td><%=gamedata.date%></td>
<td><%=String.format("%.2f",gamedata.tcPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.openPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.uniPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.woplusPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.mmPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.yeePay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.aliPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.tenPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.unionPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.wxPay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.mzpay/100.0)%></td>
<td><%=String.format("%.2f",gamedata.onlypay/100.0)%></td>
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
<td><%=String.format("%.2f",channelDataSta.yeepaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.alipaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.tenpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.unionpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.wxpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.mzpaycount/100.0)%></td>
<td><%=String.format("%.2f",channelDataSta.olpaycount/100.0)%></td>
</tr>
</table>
</form>
<%
String linkParam="";
out.print(azul.JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
<script>
function addAct(){
  
}

function gametypechg(obj){
	document.mainForm.action="cfgCPPayTypeList.jsp?op=selgame&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function gamechg(obj)
{
	//alert(obj.value);
	document.mainForm.action="cfgCPPayTypeList.jsp?op=selgame&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function businesschg(obj)
{
	//alert(obj.value);
	document.mainForm.action="cfgCPPayTypeList.jsp?op=selbusiness&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function channelchg(obj)
{
	document.mainForm.action="cfgCPPayTypeList.jsp?op=selchannel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function packetchg(obj)
{
	document.mainForm.action="cfgCPPayTypeList.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="cfgCPPayTypeList.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="cfgCPPayTypeList.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="cfgCPPayTypeList.jsp?op=sch&BSS_NO=<%=BSS_NO%>";
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
out.println(azul.JspUtil.initSelect("game_type",gameTypeMap,game_type));
out.println(azul.JspUtil.initSelectSortByValuePinyin("game_no",gameMap,game_no));
out.println(azul.JspUtil.initSelect("selyear",yearMap,selyear));
out.println(azul.JspUtil.initSelect("selmonth",monthMap,selmonth));
%>
</script>
