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
String bssSql = "select no,name from businesser  where 1=1 ";
String newDate = "2014-03-01";
String AllAccountPay ="0.0";
String AllpayAccountPay = "0.0";
String AllpayRatio = "0.0";
	
if(StringUtil.is_nullString(selmonth))
{
	Date curdate = new Date();
	selmonth =String.format("%02d",curdate.getMonth()+1);
}

if(StringUtil.is_nullString(selyear))
{
   Date curdate = new Date();
   selyear =String.format("%d",1900+curdate.getYear());
}

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

String appsql = "select no,name from app where 1=1 ";
if(role.equals("SA"))
{
	game_type = App.OFF_LINE;
}
if(!StringUtil.is_nullString(game_type)){
	appsql+="and game_type='"+game_type+"' ";
}


CooperationDao cooperationDao=new CooperationDao();
Map<String,String> gameTypeMap = new HashMap<String,String>();
gameTypeMap.put(App.OFF_LINE, "单机");
gameTypeMap.put(App.ON_LINE, "网游");

Map gameMap=cooperationDao.getSelectMap(appsql);

BusinesserDao businesserDao=new BusinesserDao();
Map businesserMap=businesserDao.getSelectMap(bssSql);

Map channelMap=new HashMap<String,String>();
Map packetMap=new HashMap<String,String>();

Map yearMap=new HashMap<String,String>();
yearMap.put("2014", "2014");
yearMap.put("2015", "2015");
Map monthMap=new HashMap<String,String>();
monthMap.put("01", "1");
monthMap.put("02", "2");
monthMap.put("03", "3");
monthMap.put("04", "4");
monthMap.put("05", "5");
monthMap.put("06", "6");
monthMap.put("07", "7");
monthMap.put("08", "8");
monthMap.put("09", "9");
monthMap.put("10", "10");
monthMap.put("11", "11");
monthMap.put("12", "12");

java.util.Map diptypeMap=new HashMap<String,String>();
diptypeMap.put("A", "渠道");
//diptypeMap.put("C", "扣量");

//DebuUtil.log("game_no="+game_no);
/*if((!StringUtil.is_nullString(game_no)) && (!StringUtil.is_nullString(business_no)))
{
	String sql = String.format("select channel_no,channel.name from cooperation,channel where app_no='%s' and cooperation.business_no='%s' and channel.no=cooperation.channel_no GROUP BY channel_no",game_no,business_no);
	DebuUtil.log("sql="+sql);
	channelMap = cooperationDao.getSelectMap(sql);
	
	//channelMap.values();
}*/

////////////////////////////////////////////////////////////
	String channelsql = String.format("select channel_no,channel.name from cooperation,channel where channel.no=cooperation.channel_no ");

if(!StringUtil.is_nullString(game_no))
{
	channelsql += " and app_no='"+game_no+"'";
}
if(!StringUtil.is_nullString(business_no))
{
	channelsql += " and cooperation.business_no='"+business_no+"'";
}
     channelsql += " GROUP BY channel_no";
     channelMap = cooperationDao.getSelectMap(channelsql);
///////////////////////////////////////////////////////////////////////////////////

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

if((!StringUtil.is_nullString(op)) &&(op.equals("selgame")))
{

}
else 
{
	if(!StringUtil.is_nullString(channel_no))
	{
		String sql = String.format("select id,packet_id from Cooperation where channel_no='%s' and app_no='%s' and business_no='%s'"
				                     ,channel_no,game_no,business_no);
	    packetMap=cooperationDao.getSelectMap(sql);//
	    
	}

}

if((!StringUtil.is_nullString(op)) &&(op.equals("sch")))//
{
	int day = DateUtil.getDaysByMonth(Integer.valueOf(selyear), Integer.valueOf(selmonth));
	
	String beginDate=selyear+"-"+selmonth+"-01";
	String endDate=selyear+"-"+selmonth+"-"+day;
	
	//DebuUtil.log("beginDate="+beginDate);
	//DebuUtil.log("endDate="+endDate);
	
	if(channel_id != -1)
	{
		Cooperation cooperation = (Cooperation)cooperationDao.getById(channel_id);
		DebuUtil.log("GamePayData1");
		list = ChannelDataReq.get_GamedataList(game_type,game_no,"","",cooperation.packet_id,cooperation.settle_type,distype,beginDate,endDate);
		//DebuUtil.log("app_no="+cooperation.app_no+"packet_id="+cooperation.packet_id);
		//DebuUtil.log("size="+list.size());
	}
	else
	{
		//DebuUtil.log("game_no="+game_no);
		//DebuUtil.log("channel_no="+channel_no);//
		DebuUtil.log("game_no="+game_no+"business_no="+business_no
		+"channel_no="+channel_no+"distype="+distype+"beginDate="+beginDate+"endDate="+endDate);
		//if((!StringUtil.is_nullString(game_no)) || (!StringUtil.is_nullString(business_no))|| (!StringUtil.is_nullString(channel_no)))
		list = ChannelDataReq.get_GamedataList(game_type,game_no,business_no,channel_no,"","",distype,beginDate,endDate);
		
	}
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
<td width="10%">显示类型&nbsp;&nbsp;
<select name="distype" onchange=yearchg(this) id="distype">
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
<td>日期</td>
<td>新增用户</td>
<td>新增用户付费用户</td>
<td>新增用户付费次数</td>
<td>新增用户付费金额</td>
<td>当日付费金额</td>
<td>新增付费占比</td>
<td>新增ARPU</td>
<td>新增付费率</td>
</tr>
<%

int allActcount = 0;
int allDay1_pay_acount = 0;
int allDay1_pay_times = 0;
int allDay1Pay = 0;
int allNewPay = 0;
int allPay = 0;

for(int i=0;i<list.size();i++){
	String curdate = DateUtil.getDate();
	long datedif = 0;
	ChannelDataReq gamedata=(ChannelDataReq)list.get(i);
	
	int actcount = gamedata.activityNum;
        int day1_pay_acount = gamedata.day1_pay_acount;
        int day1_pay_times = gamedata.day1_pay_times;
        int day1Pay = gamedata.day1Pay;
        int newPay = gamedata.newmmPay+gamedata.newtcPay+gamedata.newuniPay+gamedata.newaliPay+gamedata.newyeePay+gamedata.newtenPay+gamedata.newunionPay+gamedata.newopenPay+gamedata.newwoplusPay;
        int pay = gamedata.mmPay+gamedata.tcPay+gamedata.uniPay+gamedata.aliPay+gamedata.yeePay+gamedata.tenPay+gamedata.unionPay+gamedata.openPay+gamedata.woplusPay;

	allNewPay += newPay ;
	allPay += pay;
	allActcount += actcount ;
	
        allDay1_pay_acount +=day1_pay_acount ;
        allDay1_pay_times += day1_pay_times ;
        allDay1Pay += day1Pay ;
	
	datedif = DateUtil.getDayDiff(gamedata.date, curdate);
	
	String datestr = gamedata.date;
        String actcountStr = String.format("%d",actcount);
        String day1_pay_acountStr = String.format("%d",day1_pay_acount);
        String day1_pay_timesStr = String.format("%d",day1_pay_times);
        String day1PayStr = String.format("%.2f",day1Pay/100.0);
        String payStr = String.format("%.2f",pay/100.0);
        String newPayPercenStr = "0.0%";
        String newPayARPUStr = "0";
        String newUserPayPercenStr = "0.0%";
	
	
	if(pay > 0)
	{
	    newPayPercenStr = String.format("%.2f%s",day1Pay*100.0/pay,"%");
	}
        if(actcount > 0)
	{
	    newPayARPUStr = String.format("%.2f",day1Pay/100.0/actcount);
         }
        if(actcount > 0)
	{
	    newUserPayPercenStr =  String.format("%.2f%s",day1_pay_acount*100.0/actcount,"%");
         }
	
%>
<tr>
<td><%=datestr%></td>
<td><%=actcountStr%></td>
<td><%=day1_pay_acountStr%></td>
<td><%=day1_pay_timesStr%></td>
<td><%=day1PayStr%></td>
<td><%=payStr%></td>
<td><%=newPayPercenStr%></td>
<td><%=newPayARPUStr%></td>
<td><%=newUserPayPercenStr%></td>
</tr>
<%
}
%>

<%
        String allActcountStr = String.format("%d",allActcount);
        String allDay1_pay_acountStr = String.format("%d",allDay1_pay_acount);
        String allDay1_pay_timesStr = String.format("%d",allDay1_pay_times);
        String allDay1PayStr = String.format("%.2f",allDay1Pay/100.0);
        String allNewPayStr = String.format("%.2f",allNewPay/100.0);
        String allNewPayPercenStr = "";
        String allNewPayARPUStr = "";
	String allUserPayPercenStr = "0.0%";
	if(allPay > 0)
	{
	    allNewPayPercenStr = String.format("%.2f%s",allDay1Pay*100.0/allPay,"%");
	}
        if(allActcount > 0)
	{
	    allNewPayARPUStr = String.format("%.2f",allDay1Pay/100.0/allActcount);
            allUserPayPercenStr = String.format("%.2f%s",allDay1_pay_acount*100.0/allActcount,"%");
	}


%>
<tr>
<td>总计</td>
<td><%=allActcountStr%></td>
<td><%=allDay1_pay_acountStr%></td>
<td><%=allDay1_pay_timesStr%></td>
<td><%=allDay1PayStr%></td>
<td><%=allNewPayStr%></td>
<td><%=allNewPayPercenStr%></td>
<td><%=allNewPayARPUStr%></td>
<td><%=allUserPayPercenStr%></td>
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
	document.mainForm.action="newUserPayAnalysis.jsp?op=selgame&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function gamechg(obj)
{
	//alert(obj.value);
	document.mainForm.action="newUserPayAnalysis.jsp?op=selgame&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function businesschg(obj)
{
	//alert(obj.value);
	document.mainForm.action="newUserPayAnalysis.jsp?op=selbusiness&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function channelchg(obj)
{
	document.mainForm.action="newUserPayAnalysis.jsp?op=selchannel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function packetchg(obj)
{
	document.mainForm.action="newUserPayAnalysis.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="newUserPayAnalysis.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="newUserPayAnalysis.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="newUserPayAnalysis.jsp?op=sch&BSS_NO=<%=BSS_NO%>";
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
out.println(azul.JspUtil.initSelect("distype",diptypeMap,distype));
out.println(azul.JspUtil.initSelect("selyear",yearMap,selyear));
out.println(azul.JspUtil.initSelect("selmonth",monthMap,selmonth));
%>
</script>
