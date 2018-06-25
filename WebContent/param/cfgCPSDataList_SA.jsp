<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%

int channel_id=azul.JspUtil.getInt(request.getParameter("channel_id"),-1);
String game_no = request.getParameter("game_no");
String channel_no = request.getParameter("channel_no");
String op =  request.getParameter("op");
String selyear =  request.getParameter("selyear");
String selmonth =  request.getParameter("selmonth");
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

CooperationDao cooperationDao=new CooperationDao();
java.util.Map gameMap=new HashMap<String,String>();//cooperationDao.getSelectMap("select no,name from app");

//java.util.Map channelMap=new HashMap<String,String>();
java.util.Map packetMap=new HashMap<String,String>();

java.util.Map yearMap=new HashMap<String,String>();
yearMap.put("2014", "2014");
yearMap.put("2015", "2015");
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

if(StringUtil.is_nullString(selmonth))
{
	Date curdate = new Date();
	selmonth =String.format("%02d",curdate.getMonth()+1);
}

if(!StringUtil.is_nullString(channel_no))
{
	String sql = String.format("select app_no,app.name from cooperation,app where channel_no='%s' and app_no=app.no GROUP BY app_no",channel_no);
	gameMap = cooperationDao.getSelectMap(sql);
}

if(!StringUtil.is_nullString(game_no))
{
	String sql = String.format("select id,packet_id from Cooperation where channel_no='%s' and app_no='%s'"
			                     ,channel_no,game_no);
    packetMap=cooperationDao.getSelectMap(sql);//
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
ArrayList<ChannelDataReq> list = new ArrayList<ChannelDataReq>();
int recordcount = 0;


if((!StringUtil.is_nullString(op)) &&(op.equals("sch")))
{
    int day = DateUtil.getDaysByMonth(Integer.valueOf(selyear), Integer.valueOf(selmonth));
	
	String beginDate=selyear+"-"+selmonth+"-01";
	String endDate=selyear+"-"+selmonth+"-"+day;
	
	DebuUtil.log("beginDate="+beginDate);
	DebuUtil.log("endDate="+endDate);
	
	if(channel_id != -1)
	{
		Cooperation cooperation = (Cooperation)cooperationDao.getById(channel_id);
		
		list = ChannelDataReq.get_GamedataList("","","",cooperation.packet_id,cooperation.settle_type,"A",beginDate,endDate);
		//DebuUtil.log("app_no="+cooperation.app_no+"packet_id="+cooperation.packet_id);
		//DebuUtil.log("size="+list.size());
	}
	else
	{
		if((game_no != null) || (channel_no != null))
		{
			list = ChannelDataReq.get_GamedataList(game_no,"",channel_no,"","","A",beginDate,endDate);
		}
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
<td width="10%">APK包号&nbsp;&nbsp;
<select name="channel_id" onchange=packetchg(this) id="channel_id">
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
</table>
<table id="TableColor" width="100%" border="0">
<tr>
<td>日期</td>
<td>新增用户</td>
<!-- <td>注册用户</td> -->
<!-- <td>有效用户</td> -->
<td>登录用户</td>
<!-- <td>激活注册率</td> -->
<td>次日留存率</td>
<!-- <td>周留存率</td> -->
<!-- <td>月留存率</td> -->
<!-- <td>话费支付</td> -->
<!-- <td>第三方支付</td> -->
<td>付费用户</td>
<td>付费金额</td>
<td>付费率</td>
<td>ARPPU</td>
<td>ARPU</td>
</tr>
<%
int mmpaycount = 0;
int tcpaycount = 0;
int unipaycount = 0;
int alipaycount = 0;
int yeepaycount = 0;
int tenpaycount = 0;
int unionpaycount = 0;
int mzpaycount = 0;
int openpaycount = 0;
int wopluspaycount = 0;
int webpaycount = 0;
int boxpaycount = 0;

int newmmpaycount = 0;
int newtcpaycount = 0;
int newunipaycount = 0;
int newalipaycount = 0;
int newyeepaycount = 0;
int newtenpaycount = 0;
int newmzpaycount = 0;
int newunionpaycount = 0;
int newopenpaycount = 0;
int newwopluspaycount = 0;

int actcount = 0;
int regcount = 0;
int logincount = 0;
int allactivityRegNum = 0;
int allrealregisterNum = 0;
int monthdayAllPayAcount = 0;
int monthdayAllAcount = 0;

int allday1Num = 0;
int allday7Num = 0;
int allday30Num = 0;



for(int i=0;i<list.size();i++){
	String curdate = DateUtil.getDate();
	long datedif = 0;
	ChannelDataReq gamedata=(ChannelDataReq)list.get(i);
	
	if(game_no.equals("1000"))
	{
		//gamedata.mmPay = gamedata.activityNum*200;	
		if(gamedata.date.equals(curdate))
		{
			gamedata.mmPay = 0;
			gamedata.activityNum = 0;
		}
	}
	else if(game_no.equals("1001"))
	{
		//gamedata.mmPay = gamedata.activityNum*500;	
		if(gamedata.date.equals(curdate))//if(i == (list.size()-1))
		{
			gamedata.mmPay = 0;
			gamedata.activityNum = 0;
			
		}
	}
	//算总数
	mmpaycount += gamedata.mmPay;
	//tcpaycount += gamedata.tcPay;////////////////
	//unipaycount += gamedata.uniPay;//////////////
	alipaycount += gamedata.aliPay;
	yeepaycount += gamedata.yeePay;
	tenpaycount += gamedata.tenPay;
	unionpaycount += gamedata.unionPay;
	mzpaycount += gamedata.mzpay;
        openpaycount += gamedata.openPay;
        wopluspaycount += gamedata.woplusPay;
    webpaycount += gamedata.webPay;
    boxpaycount += gamedata.boxPay;
        
	
	newmmpaycount += gamedata.newmmPay;
	//newtcpaycount += gamedata.newtcPay;/////////////////
	//newunipaycount += gamedata.newuniPay;//////////////////
	newalipaycount += gamedata.newaliPay;
	newyeepaycount += gamedata.newyeePay;
	newtenpaycount += gamedata.newtenPay;
	newunionpaycount += gamedata.newunionPay;
	newmzpaycount += gamedata.newmzpay;
        newopenpaycount += gamedata.newopenPay;
        newwopluspaycount += gamedata.newwoplusPay;
	
	monthdayAllPayAcount += gamedata.dayAllPayAcount;
        monthdayAllAcount += gamedata.dayAllAcount;

	int daypay = gamedata.newmmPay+gamedata.newtcPay+gamedata.newuniPay
				+gamedata.newaliPay+gamedata.newyeePay+gamedata.newtenPay
				+gamedata.newunionPay+gamedata.newopenPay+gamedata.newwoplusPay
				+gamedata.newmzpay;
	
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
	String dayAllStr = String.format("%d",gamedata.dayAllPayAcount);
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
	int activateReg = 0;
	int datevalue = datestr.compareTo(newDate);
	if(datevalue >= 0)
	 {
	       activateReg = gamedata.dayAllAcount;
	 }
	 else
	 {
		   activateReg = gamedata.registerNum;
      }
		
	if(gamedata.activityNum > 0)
	{
	    value = activateReg;
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
		//value = gamedata.registerNum;
		 value = activateReg;
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
		//value = gamedata.registerNum;
		 value = activateReg;
		value = gamedata.day30Num/value;
	}
	String date30R = "-"; 
	if(datedif >= 30)
	{
	   date30R = String.format("%.2f%s(%d)",value*100,"%",gamedata.day30Num);
	}
	
	int telpay = gamedata.mmPay+gamedata.tcPay+gamedata.uniPay+gamedata.openPay+gamedata.woplusPay+gamedata.webPay+gamedata.boxPay;
	int newtelpay = gamedata.newmmPay+gamedata.newtcPay+gamedata.newuniPay+gamedata.newopenPay+gamedata.newwoplusPay;//////////////////
	int thpay = gamedata.yeePay+gamedata.aliPay+gamedata.tenPay+gamedata.unionPay+gamedata.mzpay;
	int newthpay = gamedata.newyeePay+gamedata.newaliPay+gamedata.newtenPay+gamedata.newunionPay+gamedata.newmzpay;
	String telpaystr = String.format("%.2f",telpay/100.0);//
	//String webpaystr = String.format("%.2f",webpay/100.0);
	String thpaystr = String.format("%.2f(%.2f)",thpay/100.0,newthpay/100.0);
	String mmpaystr = String.format("%.2f",gamedata.mmPay/100.0);
	//String telpaystr = String.format("%.2f",gamedata.telpay/100.0);//

	String payRatio = "0.00%";
	
	
	if(gamedata.dayAllAcount > 0)
	{
		payRatio = String.format("%.2f%s",gamedata.dayAllPayAcount*100.0/gamedata.dayAllAcount,"%");
	}	
	
	
	if(game_no.equals("1000") || game_no.equals("1001"))
	{
		 //int v = NumberUtil.getRandom(0, 35);
		 //payRatio = String.format("%d%s", v ,"%");
		 payRatio = String.format("%.2f%s",0.0,"%");
	}
	
	String payAccountPay = "0.0";
	
	
	if(gamedata.dayAllPayAcount > 0)
	{
		//payRatio = String.format("%.2f%s",gamedata.dayAllPayAcount*100.0/gamedata.dayAllAcount,"%");		     
		payAccountPay = String.format("%.2f",daypay*1.0/100/gamedata.dayAllPayAcount);
	}
			
	
	
	if(game_no.equals("1000") )
	{
		payAccountPay = String.format("%.2f",2.0);
	}
	if(game_no.equals("1001"))
	{
		payAccountPay = String.format("%.2f",5.0);
	}
	
	
	String AccountPay ="0.0";
	
	
	if(gamedata.dayAllAcount > 0)
	{
		 //payRatio = String.format("%.2f%s",gamedata.dayAllPayAcount*100.0/gamedata.dayAllAcount,"%");		     
		 AccountPay = String.format("%.2f",telpay*1.0/100/gamedata.activityNum);
	}
	
	
	if(game_no.equals("1000"))
	{
		AccountPay = String.format("%.2f",2.0);
	}
	if(game_no.equals("1001"))
	{
		AccountPay = String.format("%.2f",5.0);
	}
	
	
%>
<tr>
<td><%=datestr%></td>
<td><%=actStr%></td>
<!-- <td><%=registerStr%></td> -->
<!-- <td><%=gamedata.realregisterNum%></td> -->
<td><%=loginStr%></td>
<!-- <td><%=actRegistrStr%></td> -->
<td><%=date1R%></td>
<td><%=dayAllStr%></td>
<!-- <td><%=date7R%></td> -->
<!-- <td><%=date30R%></td> -->
<td><%=telpaystr%></td>
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

if(monthdayAllAcount > 0)
{
	value1 = allday1Num;
	value1 = value1/monthdayAllAcount;
}
String alldate1R = String.format("%.2f%s",value1*100,"%");



if(monthdayAllAcount > 0)
{
	value1 = allday7Num;
	value1 = value1/monthdayAllAcount;
}
String alldate7R = String.format("%.2f%s",value1*100,"%");

if(monthdayAllAcount > 0)
{
	value1 = allday30Num;
	value1 = value1/monthdayAllAcount;
}
String alldate30R = String.format("%.2f%s",value1*100,"%");

int alltelpay = mmpaycount+tcpaycount+unipaycount+openpaycount+wopluspaycount +webpaycount+boxpaycount;
int newalltelpay = newmmpaycount+newtcpaycount+newunipaycount+newopenpaycount+newwopluspaycount;
int allthpay = yeepaycount+alipaycount+tenpaycount+unionpaycount+mzpaycount;
int newallthpay = newyeepaycount+newalipaycount+newtenpaycount+newunionpaycount+newmzpaycount;
/*String mmpaycountstr = String.format("%.2f(%.2f)", mmpaycount/100.0,newmmpaycount/100.0);
String yeepaycountstr = String.format("%.2f(%.2f)", yeepaycount/100.0,newyeepaycount/100.0);
String alipaycountstr = String.format("%.2f(%.2f)", alipaycount/100.0,newalipaycount/100.0);
String tenpaycountstr = String.format("%.2f(%.2f)", tenpaycount/100.0,newtenpaycount/100.0);
String unionpaycountstr = String.format("%.2f(%.2f)", unionpaycount/100.0,newunionpaycount/100.0);*/

String telpaycountstr = String.format("%.2f", alltelpay/100.0);
String thpaycountstr = String.format("%.2f", allthpay/100.0);

int allnewpay = newalltelpay+newallthpay;

if(monthdayAllPayAcount > 0)
{
	AllpayRatio=String.format("%.2f%s",monthdayAllPayAcount*100.0/monthdayAllAcount,"%");
}


if(monthdayAllPayAcount > 0)
{
	AllpayAccountPay = String.format("%.2f",allnewpay*1.0/100/monthdayAllPayAcount);
}


if(monthdayAllAcount > 0)
{
	 AllAccountPay = String.format("%.2f",allnewpay*1.0/100/monthdayAllAcount);
}


%>
<tr>
<td>总计</td>
<td><%=actcount%></td>
<td><%=logincount %></td>
<td><%=alldate1R%></td>
<td><%=monthdayAllPayAcount %></td>
<td><%=telpaycountstr%></td>
<td><%=AllpayRatio%></td>
<td><%=AllpayAccountPay%></td>
<td><%=AllAccountPay%></td>
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
	document.mainForm.action="cfgCPSDataList_SA.jsp?op=selgame&channel_no=<%=channel_no%>";
	document.mainForm.submit();
}


function packetchg(obj)
{
	document.mainForm.action="cfgCPSDataList_SA.jsp?op=sel&channel_no=<%=channel_no%>";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="cfgCPSDataList_SA.jsp?op=sel&channel_no=<%=channel_no%>";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="cfgCPSDataList_SA.jsp?op=sel&channel_no=<%=channel_no%>";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="cfgCPSDataList_SA.jsp?op=sch&channel_no=<%=channel_no%>";
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
out.println(azul.JspUtil.initSelect("channel_id",packetMap,channel_id));
out.println(azul.JspUtil.initSelect("selyear",yearMap,selyear));
out.println(azul.JspUtil.initSelect("selmonth",monthMap,selmonth));
%>
</script>
