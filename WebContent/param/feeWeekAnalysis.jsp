<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DebuUtil.log("cfgGameDataReq");
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

String newDate = "2014-03-01";
String AllAccountPay ="0.0";
String AllpayAccountPay = "0.0";
String AllpayRatio = "0.0";
	
selmonth =SearchCom.getCurMonth(selmonth);
selyear =SearchCom.getCurYear(selyear);

//当以商务角色登陆后台时，需要此逻辑
if(!StringUtil.is_nullString(BSS_NO))
{
	if(BSS_NO.equals("null"))
	{
		BSS_NO = "";
	}
	else
	{
	  business_no = BSS_NO;
	}
}



Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
String role = sysUser.getRole();



CooperationDao cooperationDao=new CooperationDao();
Map<String,String> gameTypeMap =  SearchCom.getGameTypeMap(role);

Map gameMap=SearchCom.getGameMap(game_type,game_no, channel_no, business_no);//
Map businesserMap=SearchCom.getBusinessMap(game_type,game_no, channel_no, business_no);

Map channelMap=SearchCom.getChannelMap(game_type,game_no, channel_no, business_no);
Map packetMap=SearchCom.getPacketMap(game_type,game_no, channel_no, business_no);

Map yearMap=SearchCom.getYearMap();
Map monthMap=SearchCom.getMonthMap();



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
	list = SearchCom.getList(selyear, selmonth, game_no, channel_no, business_no, channel_id, distype, game_type);
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
<select name="game_type" onchange=gametypechg(this) id="game_type">
  <option value="">请选择</option>
</select>&nbsp;
</td>

<td width="10%">游戏&nbsp;&nbsp;
<select name="game_no" onchange=gamechg(this) id="game_no">
  <option value="">请选择</option>
</select>&nbsp;
</td>

<td width="10%">所属商务&nbsp;&nbsp;
<select name="business_no" onchange=businesschg(this) id="business_no">
  <option value="">请选择</option>
</select>&nbsp;
</td>

<td width="10%">渠道&nbsp;&nbsp;
<select name="channel_no" onchange=channelchg(this) id="channel_no">
  <option value="">请选择</option>
</select>&nbsp;
</td>
<td width="10%">APK包号&nbsp;&nbsp;
<select name="channel_id" onchange=packetchg(this) id="channel_id">
  <option value="">请选择</option>
</select>&nbsp;
</td>
<td width="10%">时间&nbsp;年
<select name="selyear" onchange=yearchg(this) id="selyear">
</select>&nbsp;月

<select name="selmonth" onchange=monthchg(this) id="selmonth">
</select>
</td>
<td width="10%">
<a href="#" onclick="goSearch()"><img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"/></a>
</td>
</tr>

</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>注册时间</td>
<td>激活新增</td>
<td>第1天</td>
<td>第2天</td>
<td>第3天</td>
<td>第4天</td>
<td>第5天</td>
<td>第6天</td>
<td>第7天</td>
<td>次留留存</td>
<td>7日留存</td>
</tr>
<%

ChannelDataReqSta channelDataSta = new ChannelDataReqSta(list);
for(int i=0;i<list.size();i++){	
	ChannelDataReq gamedata=(ChannelDataReq)list.get(i);
	
%>
<tr>
<td><%=gamedata.date%></td>
<td><%=gamedata.activityNum%></td>
<td><%=gamedata.day1Pay/100%>元</td>
<td><%=gamedata.day2Pay/100%>元</td>
<td><%=gamedata.day3Pay/100%>元</td>
<td><%=gamedata.day4Pay/100%>元</td>
<td><%=gamedata.day5Pay/100%>元</td>
<td><%=gamedata.day6Pay/100%>元</td>
<td><%=gamedata.day7Pay/100%>元</td>
<td><%=date1R%></td>
<td><%=date7R%></td>
</tr>
<%
}
%>

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
	document.mainForm.action="feeWeekAnalysis.jsp?op=selgame&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function gamechg(obj)
{
	//alert(obj.value);
	document.mainForm.action="feeWeekAnalysis.jsp?op=selgame&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function businesschg(obj)
{
	//alert(obj.value);
	document.mainForm.action="feeWeekAnalysis.jsp?op=selbusiness&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function channelchg(obj)
{
	document.mainForm.action="feeWeekAnalysis.jsp?op=selchannel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function packetchg(obj)
{
	document.mainForm.action="feeWeekAnalysis.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="feeWeekAnalysis.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="feeWeekAnalysis.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="feeWeekAnalysis.jsp?op=sch&BSS_NO=<%=BSS_NO%>";
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
out.println(azul.JspUtil.initSelect("business_no",businesserMap,business_no));
out.println(azul.JspUtil.initSelectSortByValuePinyin("channel_no",channelMap,channel_no));
out.println(azul.JspUtil.initSelect("channel_id",packetMap,channel_id));
out.println(azul.JspUtil.initSelect("selyear",yearMap,selyear));
out.println(azul.JspUtil.initSelect("selmonth",monthMap,selmonth));
%>
</script>
