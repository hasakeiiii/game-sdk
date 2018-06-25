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

CooperationDao cooperationDao=new CooperationDao();
java.util.Map gameMap=new HashMap<String,String>();//cooperationDao.getSelectMap("select no,name from app");

//java.util.Map channelMap=new HashMap<String,String>();
java.util.Map packetMap=new HashMap<String,String>();

java.util.Map yearMap=new HashMap<String,String>();
yearMap.put("2015", "2015");
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
<td>取指令成功率</td>
<td>指令转短信成功率</td>
<td>短信成功率</td>
<td>MM移动</td>
<td>易宝</td>
<td>支付宝</td>
<td>财付通</td>
<td>银联</td>
<td>付费率</td>
<td>ARPPU</td>
<td>ARPU</td>
</tr>
<%
int mmpaycount = 0;
int alipaycount = 0;
int yeepaycount = 0;
int tenpaycount = 0;
int unionpaycount = 0;

int msm_numcount = 0;
int fee_numcount= 0;
int req_numcount = 0;
int req_ok_numcount = 0;

int newmmpaycount = 0;
int newalipaycount = 0;
int newyeepaycount = 0;
int newtenpaycount = 0;
int newunionpaycount = 0;
int actcount = 0;
int regcount = 0;

for(int i=0;i<list.size();i++){
	String curdate = DateUtil.getDate();
	long datedif = 0;
	ChannelDataReq gamedata=(ChannelDataReq)list.get(i);
	
	//算总数
	mmpaycount += gamedata.mmPay;
	alipaycount += gamedata.aliPay;
	yeepaycount += gamedata.yeePay;
	tenpaycount += gamedata.tenPay;
	unionpaycount += gamedata.unionPay;
	

      msm_numcount  += gamedata.msmNum;
      fee_numcount += gamedata.feeNum;
      req_numcount += gamedata.reqNum;
      req_ok_numcount += gamedata.reqOkNum;

	newmmpaycount += gamedata.newmmPay;
	newalipaycount += gamedata.newaliPay;
	newyeepaycount += gamedata.newyeePay;
	newtenpaycount += gamedata.newtenPay;
	newunionpaycount += gamedata.newunionPay;
	
	actcount += gamedata.activityNum;
	regcount += gamedata.registerNum;
	
	datedif = DateUtil.getDayDiff(gamedata.date, curdate);
	
	String datestr = gamedata.date;
	String actStr = String.format("%d",gamedata.activityNum);
	String registerStr = String.format("%d",gamedata.registerNum);
	String loginStr = String.format("%d",gamedata.loginNum);
	float value = 0;
	
	if(gamedata.activityNum > 0)
	{
		value = gamedata.activityNum;
		value = gamedata.registerNum/value;
	}
	
	String actRegistrStr= String.format("%.2f%s",value*100,"%");
	
	
	value = 0;
	if(gamedata.activityNum > 0)
	{
		value = gamedata.activityNum;
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
		value = gamedata.activityNum;
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
		value = gamedata.activityNum;
		value = gamedata.day30Num/value;
	}
	String date30R = "-"; 
	if(datedif >= 30)
	{
		date30R = String.format("%.2f%s(%d)",value*100,"%",gamedata.day30Num);
	}
	
	String mmpaystr = String.format("%.2f",gamedata.mmPay/100.0);
	String yeepaystr = String.format("%.2f",gamedata.yeePay/100.0);
	String alipaystr = String.format("%.2f",gamedata.aliPay/100.0);
	String tenpaystr = String.format("%.2f",gamedata.tenPay/100.0);
	String unionpaystr = String.format("%.2f",gamedata.unionPay/100.0);
	
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
       String reqRatio ="0.0";
	
	if(gamedata.reqNum > 0)
	{
           reqRatio = String.format("%.2f%s(%d)",gamedata.reqOkNum*100.0/gamedata.reqNum,"%",gamedata.reqOkNum);
	}

        String msmRatio ="0.0";
	
	if(gamedata.msmNum > 0)
	{
           msmRatio = String.format("%.2f%s(%d)",gamedata.feeNum*100.0/gamedata.msmNum,"%",gamedata.feeNum);
	}

       String reqmsmRatio ="0.0";
	
	if(gamedata.reqOkNum > 0)
	{
           reqmsmRatio = String.format("%.2f%s(%d)",gamedata.msmNum*100.0/gamedata.reqOkNum,"%",gamedata.msmNum);
	}
	
%>
<tr>
<td><%=datestr%></td>
<td><%=reqRatio%></td>
<td><%=reqmsmRatio%></td>
<td><%=msmRatio%></td>
<td><%=mmpaystr%></td>
<td><%=yeepaystr%></td>
<td><%=alipaystr%></td>
<td><%=tenpaystr%></td>
<td><%=unionpaystr%></td>
<td><%=payRatio%></td>
<td><%=payAccountPay%></td>
<td><%=AccountPay%></td>
</tr>
<%
}
%>

<%
String mmpaycountstr = String.format("%.2f", mmpaycount/100.0);
String yeepaycountstr = String.format("%.2f", yeepaycount/100.0);
String alipaycountstr = String.format("%.2f", alipaycount/100.0);
String tenpaycountstr = String.format("%.2f", tenpaycount/100.0);
String unionpaycountstr = String.format("%.2f", unionpaycount/100.0);
String allreqRatio ="0.0";
	
	if(req_numcount > 0)
	{
           allreqRatio  = String.format("%.2f%s(%d)",req_ok_numcount*100.0/req_numcount,"%",req_ok_numcount);
	}

        String allmsmRatio ="0.0";
	
	if(msm_numcount > 0)
	{
           allmsmRatio  = String.format("%.2f%s(%d)",fee_numcount*100.0/msm_numcount,"%",fee_numcount);
	}
String allreqmsmRatio ="0.0";
if(req_ok_numcount > 0)
{
   allreqmsmRatio  = String.format("%.2f%s(%d)",msm_numcount*100.0/req_ok_numcount,"%",msm_numcount);
}
%>
<tr>
<td>总计</td>
<td><%=allreqRatio%></td>
<td><%=allreqmsmRatio%></td>
<td><%=allmsmRatio%></td>
<td><%=mmpaycountstr%></td>
<td><%=yeepaycountstr%></td>
<td><%=alipaycountstr%></td>
<td><%=tenpaycountstr%></td>
<td><%=unionpaycountstr%></td>
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
	document.mainForm.action="cfgCPSDataList_T.jsp?op=selgame&channel_no=<%=channel_no%>";
	document.mainForm.submit();
}


function packetchg(obj)
{
	document.mainForm.action="cfgCPSDataList_T.jsp?op=sel&channel_no=<%=channel_no%>";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="cfgCPSDataList_T.jsp?op=sel&channel_no=<%=channel_no%>";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="cfgCPSDataList_T.jsp?op=sel&channel_no=<%=channel_no%>";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="cfgCPSDataList_T.jsp?op=sch&channel_no=<%=channel_no%>";
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
