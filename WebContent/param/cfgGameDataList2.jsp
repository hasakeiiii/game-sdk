<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DebuUtil.log("gamelist2=");
int channel_id=azul.JspUtil.getInt(request.getParameter("channel_id"),-1);
String game_no = request.getParameter("game_no");
String business_no = request.getParameter("business_no");
String channel_no = request.getParameter("channel_no");
String op =  request.getParameter("op");
String distype =  request.getParameter("distype");
String selyear =  request.getParameter("selyear");
String selmonth =  request.getParameter("selmonth");

if(StringUtil.is_nullString(selmonth))
{
	Date curdate = new Date();
	selmonth =String.format("%02d",curdate.getMonth()+1);
}
//selmonth="04";
DebuUtil.log("selmonth="+selmonth);

CooperationDao cooperationDao=new CooperationDao();
java.util.Map gameMap=cooperationDao.getSelectMap("select no,name from app");

BusinesserDao businesserDao=new BusinesserDao();
java.util.Map businesserMap=businesserDao.getSelectMap("select no,name from businesser");

java.util.Map channelMap=new HashMap<String,String>();
java.util.Map packetMap=new HashMap<String,String>();

java.util.Map yearMap=new HashMap<String,String>();
yearMap.put("2014", "2014");
java.util.Map monthMap=new HashMap<String,String>();
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
if((!StringUtil.is_nullString(game_no)) && (!StringUtil.is_nullString(business_no)))
{
	String sql = String.format("select channel_no,channel.name from cooperation,channel where app_no='%s' and cooperation.business_no='%s' and channel.no=cooperation.channel_no GROUP BY channel_no",game_no,business_no);
	DebuUtil.log("sql="+sql);
	channelMap = cooperationDao.getSelectMap(sql);
	
    /*Map.Entry[] entries=util.MapUtil.sortByKey(packetMap);
    for (int i=0;i<entries.length;i++){
       Object keyId=entries[i].getKey();
       Object value=packetMap.get(keyId);
       //String 
    }*/
	//channelMap.values();
}
String keyWord="";//azul.JspUtil.getStr(request.getParameter("keyWord"),"");
String pageSql="select * from cfg_sp where 1=1";
if(!keyWord.equals("")){
	   pageSql+=" and sp_name like '%"+keyWord+"%'";
}

int pagesize=31;//每页记录数
int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
//int recordcount=cfgSpDao.getRecordCount(pageSql);//得到记录总数
//List<CfgSp> list=cfgSpDao.getList(pageno,pagesize,pageSql);
ArrayList<Gamedata> list = new ArrayList<Gamedata>();
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
		
		list = Gamedata.get_GamedataList("","","",cooperation.packet_id,cooperation.settle_type,distype,beginDate,endDate);
		//DebuUtil.log("app_no="+cooperation.app_no+"packet_id="+cooperation.packet_id);
		//DebuUtil.log("size="+list.size());
	}
	else
	{
		//DebuUtil.log("game_no="+game_no);
		//DebuUtil.log("channel_no="+channel_no);//
		
		if((!StringUtil.is_nullString(game_no)) || (!StringUtil.is_nullString(business_no))|| (!StringUtil.is_nullString(channel_no)))
		list = Gamedata.get_GamedataList(game_no,business_no,channel_no,"","",distype,beginDate,endDate);
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
<td width="10%">游戏&nbsp;&nbsp;
<select name="game_no" onchange=gamechg(this) id="game_no">
  <option value="">请选择</option>
</select>&nbsp;&nbsp;
</td>
<td width="10%">所属商务&nbsp;&nbsp;
<select name="business_no" onchange=businesschg(this) id="business_no">
  <option value="">请选择</option>
</select>&nbsp;&nbsp;
</td>
<td width="10%">渠道&nbsp;&nbsp;
<select name="channel_no" onchange=channelchg(this) id="channel_no">
  <option value="">请选择</option>
</select>&nbsp;&nbsp;
</td>
<td width="10%">APK包号&nbsp;&nbsp;
<select name="channel_id" onchange=packetchg(this) id="channel_id">
  <option value="">请选择</option>
</select>&nbsp;&nbsp;
</td>
<td width="10%">显示类型&nbsp;&nbsp;
<select name="distype" onchange=yearchg(this) id="distype">
  <option value="">请选择</option>
</select>&nbsp;&nbsp;
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
<td>激活用户</td>
<td>注册用户</td>
<td>有效用户</td>
<td>登录用户</td>
<td>激活注册率</td>
<td>次日留存率</td>
<td>周留存率</td>
<td>月留存率</td>
<td>MM移动</td>
<td>易宝</td>
<td>支付宝</td>
<td>付费率</td>
<td>ARPPU</td>
<td>ARPU</td>
</tr>
<%
int mmpaycount = 0;
int alipaycount = 0;
int yeepaycount = 0;
int newmmpaycount = 0;
int newalipaycount = 0;
int newyeepaycount = 0;

int actcount = 0;
int regcount = 0;
int logincount = 0;
int allactivityRegNum = 0;
int allrealregisterNum = 0;

int allday1Num = 0;
int allday7Num = 0;
int allday30Num = 0;

for(int i=0;i<list.size();i++){
	String curdate = DateUtil.getDate();
	long datedif = 0;
	Gamedata gamedata=(Gamedata)list.get(i);
	
	//算总数
	mmpaycount += gamedata.mmPay;
	alipaycount += gamedata.aliPay;
	yeepaycount += gamedata.yeePay;
	newmmpaycount += gamedata.newmmPay;
	newalipaycount += gamedata.newaliPay;
	newyeepaycount += gamedata.newyeePay;
	
	actcount += gamedata.activityNum;
	regcount += gamedata.registerNum;
	allrealregisterNum += gamedata.realregisterNum;
	
	logincount += gamedata.loginNum;
	allactivityRegNum += gamedata.activityRegNum;
	allday1Num += gamedata.day1Num;
	allday7Num += gamedata.day7Num;
	allday30Num += gamedata.day30Num;
	
	datedif = DateUtil.getDayDiff(gamedata.date, curdate);
	
	String datestr = gamedata.date;
	String actStr = String.format("%d",gamedata.activityNum);
	String registerStr = String.format("%d",gamedata.registerNum);
	String loginStr = String.format("%d",gamedata.loginNum);
	float value = 0;
	
	if(gamedata.activityNum > 0)
	{
		value = gamedata.activityRegNum;
		value = value/gamedata.activityNum;
	}
	
	String actRegistrStr= String.format("%.2f%s",value*100,"%");
	
	
	value = 0;
	if(gamedata.activityNum > 0)
	{
		value = gamedata.registerNum;
		value = gamedata.day1Num/value;
	}
	String date1R= "-";
	if(datedif >= 1)
	{
		date1R= String.format("%.2f%s(%d)",value*100,"%",gamedata.day1Num);
	}
	else
	{
		date1R = "-";
	}
	value = 0;
	if(gamedata.activityNum > 0)
	{
		value = gamedata.registerNum;
		value = gamedata.day7Num/value;
	}
	String date7R = "-"; 
	if(datedif >= 7)
	{
	   date7R = String.format("%.2f%s(%d)",value*100,"%",gamedata.day7Num);
	}
	
	value = 0;
	if(gamedata.activityNum > 0)
	{
		value = gamedata.registerNum;
		value = gamedata.day30Num/value;
	}
	String date30R = "-"; 
	if(datedif >= 30)
	{
	   date30R = String.format("%.2f%s(%d)",value*100,"%",gamedata.day30Num);
	}
	
	String mmpaystr = String.format("%.2f(%.2f)",gamedata.mmPay/100.0,gamedata.newmmPay/100.0);
	String yeepaystr = String.format("%.2f(%.2f)",gamedata.yeePay/100.0,gamedata.newyeePay/100.0);
	String alipaystr = String.format("%.2f(%.2f)",gamedata.aliPay/100.0,gamedata.newaliPay/100.0);
	
	
	String payRatio = "0.00%";
	
	if(gamedata.allRegisterNumBefore > 0)
	{
	     payRatio = String.format("%.2f%s",gamedata.allPayAccoutNumBefore*100.0/gamedata.allRegisterNumBefore,"%");
	}
	
	String payAccountPay = "0.0";
	
	if(gamedata.allPayAccoutNumBefore > 0)
	{
		payAccountPay = String.format("%.2f",gamedata.allPayBefore*1.0/100/gamedata.allPayAccoutNumBefore);
	}
	
	String AccountPay ="0.0";
	
	if(gamedata.allRegisterNumBefore > 0)
	{
		AccountPay = String.format("%.2f",gamedata.allPayBefore*1.0/100/gamedata.allRegisterNumBefore);
	}
%>
<tr>
<td><%=datestr%></td>
<td><%=actStr%></td>
<td><%=registerStr%></td>
<td><%=gamedata.realregisterNum%></td>
<td><%=loginStr%></td>
<td><%=actRegistrStr%></td>
<td><%=date1R%></td>
<td><%=date7R%></td>
<td><%=date30R%></td>
<td><%=mmpaystr%></td>
<td><%=yeepaystr%></td>
<td><%=alipaystr%></td>
<td><%=payRatio%></td>
<td><%=payAccountPay%></td>
<td><%=AccountPay%></td>
</tr>
<%
}
%>

<%
float value1=0;
if(allactivityRegNum > 0)
{
	value1 = allactivityRegNum;
	value1 = value1/actcount;
}
String allactRegistrStr= String.format("%.2f%s",value1*100,"%");

if(regcount > 0)
{
	value1 = allday1Num;
	value1 = value1/regcount;
}
String alldate1R = String.format("%.2f%s",value1*100,"%");

if(regcount > 0)
{
	value1 = allday7Num;
	value1 = value1/regcount;
}
String alldate7R = String.format("%.2f%s",value1*100,"%");

if(regcount > 0)
{
	value1 = allday30Num;
	value1 = value1/regcount;
}
String alldate30R = String.format("%.2f%s",value1*100,"%");

String mmpaycountstr = String.format("%.2f(%.2f)", mmpaycount/100.0,newmmpaycount/100.0);
String yeepaycountstr = String.format("%.2f(%.2f)", yeepaycount/100.0,newyeepaycount/100.0);
String alipaycountstr = String.format("%.2f(%.2f)", alipaycount/100.0,newalipaycount/100.0);
%>
<tr>
<td>总计</td>
<td><%=actcount%></td>
<td><%=regcount%></td>
<td><%=allrealregisterNum%></td>
<td></td>
<td><%=allactRegistrStr%></td>
<td><%=alldate1R%></td>
<td><%=alldate7R%></td>
<td><%=alldate30R%></td>
<td><%=mmpaycountstr%></td>
<td><%=yeepaycountstr%></td>
<td><%=alipaycountstr%></td>
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

function gamechg(obj)
{
	//alert(obj.value);
	document.mainForm.action="cfgGameDataList2.jsp?op=selgame";
	document.mainForm.submit();
}
function businesschg(obj)
{
	//alert(obj.value);
	document.mainForm.action="cfgGameDataList2.jsp?op=selbusiness";
	document.mainForm.submit();
}
function channelchg(obj)
{
	document.mainForm.action="cfgGameDataList2.jsp?op=selchannel";
	document.mainForm.submit();
}

function packetchg(obj)
{
	document.mainForm.action="cfgGameDataList2.jsp?op=sel";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="cfgGameDataList2.jsp?op=sel";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="cfgGameDataList2.jsp?op=sel";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="cfgGameDataList2.jsp?op=sch";
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
out.println(azul.JspUtil.initSelect("game_no",gameMap,game_no));
out.println(azul.JspUtil.initSelect("business_no",businesserMap,business_no));
out.println(azul.JspUtil.initSelect("channel_no",channelMap,channel_no));
out.println(azul.JspUtil.initSelect("channel_id",packetMap,channel_id));
out.println(azul.JspUtil.initSelect("distype",diptypeMap,distype));
out.println(azul.JspUtil.initSelect("selyear",yearMap,selyear));
out.println(azul.JspUtil.initSelect("selmonth",monthMap,selmonth));
%>
</script>
