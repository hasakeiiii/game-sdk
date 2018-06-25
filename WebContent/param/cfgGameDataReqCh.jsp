<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<%@ page import="org.jfree.chart.*" %>
<%@ page import="org.jfree.data.*" %>
<%@ page import="org.jfree.data.category.DefaultCategoryDataset" %>
<%@ page import="java.text.DecimalFormat" %>

<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DebuUtil.log("cfgGameDataReq");
int channel_id=azul.JspUtil.getInt(request.getParameter("apk_no"),-1);
String game_no = request.getParameter("game_no");
String business_no = request.getParameter("business_no");
String channel_no = request.getParameter("channel_no");
String op =  request.getParameter("op");
String distype =  request.getParameter("distype");
String selyear =  request.getParameter("selyear");
String selmonth =  request.getParameter("selmonth");
String BSS_NO = request.getParameter("BSS_NO");
String bssSql = "select no,name from businesser  where 1=1 ";

String filename = "";

if(!StringUtil.is_nullString(game_no))
{
  filename += game_no;
}
if(!StringUtil.is_nullString(business_no))
{
  filename += business_no;
}
if(!StringUtil.is_nullString(channel_no))
{
  filename += channel_no;
}
if(!StringUtil.is_nullString(selyear))
{
  filename += selyear;
}
if(!StringUtil.is_nullString(selmonth))
{
  filename += selmonth;
}
//game_no+business_no+channel_no+selyear+selmonth

//filename="company.jpeg";
String imgfilepath = application.getRealPath("/") + "/jfreeimg/"+filename+".jpeg";

String url = request.getContextPath()+"/jfreeimg/"+filename+".jpeg";

java.util.Map reqPercentMap=new HashMap<String,String>();
java.util.Map msmPercentMap=new HashMap<String,String>();
java.util.Map feePercentMap=new HashMap<String,String>();

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

String appsql = "select no,name from app ";
if(role.equals("SA"))
{
	appsql += "where game_type='off_line' ";
}
CooperationDao cooperationDao=new CooperationDao();
java.util.Map gameMap=cooperationDao.getSelectMap(appsql);

BusinesserDao businesserDao=new BusinesserDao();
java.util.Map businesserMap=businesserDao.getSelectMap(bssSql);

java.util.Map channelMap=new HashMap<String,String>();
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
		list = ChannelDataReq.get_GamedataList(game_no,"","",cooperation.packet_id,cooperation.settle_type,distype,beginDate,endDate);
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
		list = ChannelDataReq.get_GamedataList(game_no,business_no,channel_no,"","",distype,beginDate,endDate);
		
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
					<select name="game_type" onchange="chgGameType(this)" id="game_type">
					  	<option value="">请选择</option>
					 	<option value="off_line">单机</option>
					 	<option value="on_line">网游</option>
					 	<option value="mm_on_line">MM网游</option>
					</select>&nbsp;
				</td>
	
				<td width="15%">游戏&nbsp;&nbsp;
					<select name="game_no" onchange="chgGame(this)" id="game_no">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
	
				<td width="10%">所属商务&nbsp;&nbsp;
					<select name="business_no" id="business_no" onchange="chgBusiness()">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
	
				<td width="10%">渠道&nbsp;&nbsp;
					<select name="channel_no" id="channel_no" onchange = "chgChannel()">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
	
				<td width="10%">APK包号&nbsp;&nbsp;
					<select name="apk_no" id="apk_no">
						<option value="">请选择</option>
					</select>&nbsp;
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
<%
      DefaultCategoryDataset dataset = new DefaultCategoryDataset();
      for(int i=0;i<list.size();i++){
	String curdate = DateUtil.getDate();
	long datedif = 0;      
	ChannelDataReq gamedata=(ChannelDataReq)list.get(i);
        String datestr = gamedata.date;

        datedif = DateUtil.getDayDiff(gamedata.date, curdate);
        String reqRatio = "0.0";
        if(gamedata.reqNum > 0)
	{
           reqRatio = String.format("%.2f",gamedata.reqOkNum*100.0/gamedata.reqNum);
	}

        String msmRatio ="0.0";
	
	if(gamedata.msmNum > 0)
	{
           msmRatio = String.format("%.2f",gamedata.feeNum*100.0/gamedata.msmNum);
	}

        String reqmsmRatio ="0.0";
	
	if(gamedata.reqOkNum > 0)
	{
           reqmsmRatio = String.format("%.2f",gamedata.msmNum*100.0/gamedata.reqOkNum);
	}
        //if((datedif >= 0) && (datedif <= 0+5) )
        { 
            //reqPercentMap.put(datestr,reqRatio);
            //msmPercentMap.put(datestr,msmRatio);
            //feePercentMap.put(datestr,reqmsmRatio);
            dataset.setValue(Float.valueOf(reqRatio)/100,"请求成功率",i+1+"");
            dataset.setValue(Float.valueOf(msmRatio)/100,"短信成功率",i+1+"");
            dataset.setValue(Float.valueOf(reqmsmRatio)/100,"请求转短信率",i+1+"");
        }
}
    if((!StringUtil.is_nullString(op)) &&(op.equals("sch")))
    {
    JFreeChart chart =  jfreeChartUtil.createLine(dataset,"", "","",jfreeChartUtil.standardChartTheme());

    jfreeChartUtil.setTick(chart,0.1d);
    jfreeChartUtil.setYFormat(chart,new DecimalFormat("0.00%"));
    //fillDataset(dataset,reqPercentMap,String type);
    jfreeChartUtil.saveFile(chart,imgfilepath,800,300);
     DebuUtil.log("jfreeChartUtil.createLineTest");
    //jfreeChartUtil.createLineByMap(imgfilepath);
    DebuUtil.log(imgfilepath);
    DebuUtil.log(reqPercentMap.toString());
    //jfreeChartUtil.createLineTest();
   //}
	  
%>
<img src="<%= url %>" width="1300" height="300" align="absmiddle" />
<%
}
%>
<table id="TableColor" width="100%" border="0">
<tr>
<td>日期</td>
<td>取指令成功率</td>
<td>指令转短信成功率</td>
<td>短信成功率</td>
<td>话费支付</td>
</tr>
<%
int mmpaycount = 0;
int tcpaycount = 0;
int unipaycount = 0;
int alipaycount = 0;
int yeepaycount = 0;
int tenpaycount = 0;
int unionpaycount = 0;

int msm_numcount = 0;
int fee_numcount= 0;
int req_numcount = 0;
int req_ok_numcount = 0;

int newmmpaycount = 0;
int newtcpaycount = 0;
int newunipaycount = 0;
int newalipaycount = 0;
int newyeepaycount = 0;
int newtenpaycount = 0;
int newunionpaycount = 0;

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
	ChannelDataReq gamedata=(ChannelDataReq)list.get(i);
	String datestr = gamedata.date;
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
	tcpaycount += gamedata.tcPay;
	unipaycount += gamedata.uniPay;
	alipaycount += gamedata.aliPay;
	yeepaycount += gamedata.yeePay;
	tenpaycount += gamedata.tenPay;
	unionpaycount += gamedata.unionPay;
	

       msm_numcount  += gamedata.msmNum;
       fee_numcount += gamedata.feeNum;
       req_numcount += gamedata.reqNum;
       req_ok_numcount += gamedata.reqOkNum;

	newmmpaycount += gamedata.newmmPay;
	newtcpaycount += gamedata.newtcPay;
	newunipaycount += gamedata.newuniPay;
	newalipaycount += gamedata.newaliPay;
	newyeepaycount += gamedata.newyeePay;
	newtenpaycount += gamedata.newtenPay;
	newunionpaycount += gamedata.newunionPay;
	
	actcount += gamedata.activityNum;
	regcount += gamedata.registerNum;
	allrealregisterNum += gamedata.realregisterNum;
	
	logincount += gamedata.loginNum;
	allactivityRegNum += gamedata.activityRegNum;
	allday1Num += gamedata.day1Num;
	allday7Num += gamedata.day7Num;
	allday30Num += gamedata.day30Num;
	
	datedif = DateUtil.getDayDiff(gamedata.date, curdate);
	
	
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
	
	int telpay = gamedata.mmPay+gamedata.tcPay+gamedata.uniPay;
	int newtelpay = gamedata.newmmPay+gamedata.newtcPay+gamedata.newuniPay;
	int thpay = gamedata.yeePay+gamedata.aliPay+gamedata.tenPay+gamedata.unionPay;
	int newthpay = gamedata.newyeePay+gamedata.newaliPay+gamedata.newtenPay+gamedata.newunionPay;
	String telpaystr = String.format("%.2f",telpay/100.0);//
	/*String yeepaystr = String.format("%.2f(%.2f)",gamedata.yeePay/100.0,gamedata.newyeePay/100.0);//gamedata.newyeePay
	String alipaystr = String.format("%.2f(%.2f)",gamedata.aliPay/100.0,gamedata.newaliPay/100.0);//gamedata.newaliPay
	String tenpaystr = String.format("%.2f(%.2f)",gamedata.tenPay/100.0,gamedata.newtenPay/100.0);//gamedata.newaliPay
	String unionpaystr = String.format("%.2f(%.2f)",gamedata.unionPay/100.0,gamedata.newunionPay/100.0);//gamedata.newaliPay
	*/
	String thpaystr = String.format("%.2f(%.2f)",thpay/100.0,newthpay/100.0);
	
	String payRatio = "0.00%";
	
	
	if(gamedata.allRegisterNumBefore > 0)
	{
	     payRatio = String.format("%.2f%s",gamedata.allPayAccoutNumBefore*100.0/gamedata.allRegisterNumBefore,"%");
	}
	if(game_no.equals("1000") || game_no.equals("1001"))
	{
		 //int v = NumberUtil.getRandom(0, 35);
		 //payRatio = String.format("%d%s", v ,"%");
		 payRatio = String.format("%.2f%s",0.0,"%");
	}
	
	String payAccountPay = "0.0";
	
	if(gamedata.allPayAccoutNumBefore > 0)
	{
	    payAccountPay = String.format("%.2f",gamedata.allPayBefore*1.0/100/gamedata.allPayAccoutNumBefore);
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
	
	if(gamedata.allRegisterNumBefore > 0)
	{
		AccountPay = String.format("%.2f",gamedata.allPayBefore*1.0/100/gamedata.allRegisterNumBefore);
	}
	
	if(game_no.equals("1000"))
	{
		AccountPay = String.format("%.2f",2.0);
	}
	if(game_no.equals("1001"))
	{
		AccountPay = String.format("%.2f",5.0);
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
        if((datedif >= 0) && (datedif <= 5) )
        { 
            reqPercentMap.put(datestr,reqRatio);
            msmPercentMap.put(datestr,msmRatio);
            feePercentMap.put(datestr,reqmsmRatio);
        }
	
%>
<tr>
<td><%=datestr%></td>
<td><%=reqRatio%></td>
<td><%=reqmsmRatio%></td>
<td><%=msmRatio%></td>
<td><%=telpaystr%></td>
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

int alltelpay = mmpaycount+tcpaycount+unipaycount;
int newalltelpay = newmmpaycount+newtcpaycount+newunipaycount;
int allthpay = yeepaycount+alipaycount+tenpaycount+unionpaycount;
int newallthpay = newyeepaycount+newalipaycount+newtenpaycount+newunionpaycount;
/*String mmpaycountstr = String.format("%.2f(%.2f)", mmpaycount/100.0,newmmpaycount/100.0);
String yeepaycountstr = String.format("%.2f(%.2f)", yeepaycount/100.0,newyeepaycount/100.0);
String alipaycountstr = String.format("%.2f(%.2f)", alipaycount/100.0,newalipaycount/100.0);
String tenpaycountstr = String.format("%.2f(%.2f)", tenpaycount/100.0,newtenpaycount/100.0);
String unionpaycountstr = String.format("%.2f(%.2f)", unionpaycount/100.0,newunionpaycount/100.0);*/

String telpaycountstr = String.format("%.2f", alltelpay/100.0);
String thpaycountstr = String.format("%.2f(%.2f)", allthpay/100.0,newallthpay/100.0);


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
<td><%=telpaycountstr%></td>
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
	document.mainForm.action="cfgGameDataReqCh.jsp?op=selgame&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function businesschg(obj)
{
	//alert(obj.value);
	document.mainForm.action="cfgGameDataReqCh.jsp?op=selbusiness&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function channelchg(obj)
{
	document.mainForm.action="cfgGameDataReqCh.jsp?op=selchannel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function packetchg(obj)
{
	document.mainForm.action="cfgGameDataReqCh.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="cfgGameDataReqCh.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="cfgGameDataReqCh.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="cfgGameDataReqCh.jsp?op=sch&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
</script>
<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/commonSelectOnload.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
<script type="text/javascript">
<%
out.println(azul.JspUtil.initSelect("distype",diptypeMap,distype));
out.println(azul.JspUtil.initSelect("selyear",yearMap,selyear));
out.println(azul.JspUtil.initSelect("selmonth",monthMap,selmonth));
%>
</script>
