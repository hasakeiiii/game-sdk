<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
DebuUtil.log("cfgCPGameDataReq");
String game_type = request.getParameter("game_type");//20141203加入游戏类型判断
String game_no = request.getParameter("game_no");
String op =  request.getParameter("op");
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



//selmonth="04";
DebuUtil.log("selmonth="+selmonth);
DebuUtil.log("BSS_NO="+BSS_NO);
DebuUtil.log("bssSql="+bssSql);

Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
String role = sysUser.getRole();
Integer ID = sysUser.getId();

String appsql = "select no,name from app where cp_no in(select cp_no from cp_manage where login_user = "+ID+")";
if(role.equals("SA"))
{
	game_type = App.OFF_LINE;
}



	Map<String,String> gameTypeMap = new HashMap<String,String>();
        if(role.equals(Userinfo.adminOnline) || role.equals(Userinfo.operationOnline))
       {
	   gameTypeMap.put(App.ON_LINE, "网游");
           game_type = App.ON_LINE;
	}
        else if(role.equals(Userinfo.adminOffline) || role.equals(Userinfo.operationOffline))
       {
	   gameTypeMap.put(App.OFF_LINE, "单机");
           gameTypeMap.put(App.MM_ON_LINE, "MM网游");
           if(StringUtil.is_nullString(game_type))
           {  
               game_type = App.OFF_LINE;
           }
	}
        else
       {
           gameTypeMap.put("", "请选择");
           gameTypeMap.put(App.OFF_LINE, "单机");
          // gameTypeMap.put(App.MM_ON_LINE, "MM网游");
           gameTypeMap.put(App.ON_LINE, "网游");
        }

if(!StringUtil.is_nullString(game_type)){
	appsql+="and game_type='"+game_type+"'";
}
appsql+=" order by id desc ";
DebuUtil.log("appsql="+appsql);
CooperationDao cooperationDao=new CooperationDao();
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
	int day = DateUtil.getDaysByMonth(Integer.valueOf(selyear), Integer.valueOf(selmonth));
	
	String beginDate=selyear+"-"+selmonth+"-01";
	String endDate=selyear+"-"+selmonth+"-"+day;
	
	//DebuUtil.log("beginDate="+beginDate);
	//DebuUtil.log("endDate="+endDate);
	

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
<td>游戏类型&nbsp;&nbsp;
<select name="game_type" onchange=gametypechg(this) id="game_type">
</select>&nbsp;
</td>

<td>游戏&nbsp;&nbsp;
<select name="game_no" onchange=gamechg() id="game_no">
  <option value="">请选择</option>
</select>&nbsp;
</td>






<td>时间&nbsp;年
<select name="selyear" onchange=yearchg(this) id="selyear">
</select>&nbsp;月

<select name="selmonth" onchange=monthchg(this) id="selmonth">
</select>
</td>
<td>
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
<td>话费支付</td>
<td>新增用户支付(话费)</td>
<td>第三方支付</td>
<td>新增用户支付(第三方)</td>
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
int openpaycount = 0;
int wopluspaycount = 0;
int mzpaycount = 0;
int olpaycount = 0;

int newmmpaycount = 0;
int newtcpaycount = 0;
int newunipaycount = 0;
int newalipaycount = 0;
int newyeepaycount = 0;
int newtenpaycount = 0;
int newunionpaycount = 0;
int newopenpaycount = 0;
int newwopluspaycount = 0;
int newmzpaycount = 0;
int newolpaycount = 0;

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
	tcpaycount += gamedata.tcPay;///////////////////////////
	unipaycount += gamedata.uniPay;////////////////////////
	alipaycount += gamedata.aliPay;
	yeepaycount += gamedata.yeePay;
	tenpaycount += gamedata.tenPay;
	unionpaycount += gamedata.unionPay;
        openpaycount += gamedata.openPay;
	wopluspaycount += gamedata.woplusPay;

	mzpaycount += gamedata.mzpay;
        olpaycount += gamedata.onlypay;
	
	newmmpaycount += gamedata.newmmPay;
	newtcpaycount += gamedata.newtcPay;////////
	newunipaycount += gamedata.newuniPay;////////
	newalipaycount += gamedata.newaliPay;
	newyeepaycount += gamedata.newyeePay;
	newtenpaycount += gamedata.newtenPay;
	newunionpaycount += gamedata.newunionPay;
        newopenpaycount += gamedata.newopenPay;
	newwopluspaycount += gamedata.newwoplusPay;

	newolpaycount += gamedata.newonlypay;
        newmzpaycount += gamedata.newmzpay;
	
	monthdayAllPayAcount += gamedata.dayAllPayAcount;
        monthdayAllAcount += gamedata.dayAllAcount;


	int daypay = gamedata.newmmPay+gamedata.newtcPay+gamedata.newuniPay
				+gamedata.newaliPay+gamedata.newyeePay+gamedata.newtenPay
				+gamedata.newunionPay+gamedata.newmzpay+gamedata.newonlypay
				+gamedata.newopenPay+gamedata.newwoplusPay;

	
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
	

	int telpay = gamedata.mmPay+gamedata.tcPay+gamedata.uniPay+gamedata.openPay+gamedata.woplusPay;
	int newtelpay = gamedata.newmmPay+gamedata.newtcPay+gamedata.newuniPay+gamedata.newopenPay+gamedata.newwoplusPay;
	int thpay = gamedata.yeePay+gamedata.aliPay+gamedata.tenPay+gamedata.unionPay+gamedata.mzpay+gamedata.onlypay;
	int newthpay = gamedata.newyeePay+gamedata.newaliPay+gamedata.newtenPay+gamedata.newunionPay+gamedata.newmzpay+gamedata.newonlypay;
	

	String telpaystr = String.format("%.2f", telpay/100.0);
	String newtelpaystr = String.format("%.2f", newtelpay/100.0);
	//String telpaystr = String.format("%.2f(%.2f)",telpay/100.0,newtelpay/100.0);//
	/*String yeepaystr = String.format("%.2f(%.2f)",gamedata.yeePay/100.0,gamedata.newyeePay/100.0);//gamedata.newyeePay
	String alipaystr = String.format("%.2f(%.2f)",gamedata.aliPay/100.0,gamedata.newaliPay/100.0);//gamedata.newaliPay
	String tenpaystr = String.format("%.2f(%.2f)",gamedata.tenPay/100.0,gamedata.newtenPay/100.0);//gamedata.newaliPay
	String unionpaystr = String.format("%.2f(%.2f)",gamedata.unionPay/100.0,gamedata.newunionPay/100.0);//gamedata.newaliPay
	*/
	//String thpaystr = String.format("%.2f(%.2f)",thpay/100.0,newthpay/100.0);
	String thpaystr = String.format("%.2f",thpay/100.0);
	String newthpaystr = String.format("%.2f", newthpay/100.0);
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
		 AccountPay = String.format("%.2f",daypay*1.0/100/gamedata.dayAllAcount);
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
<td><%=registerStr%></td>
<td><%=gamedata.realregisterNum%></td>
<td><%=loginStr%></td>
<td><%=actRegistrStr%></td>
<td><%=date1R%></td>
<td><%=date7R%></td>
<td><%=date30R%></td>
<td><%=telpaystr%></td>
<td><%=newtelpaystr%></td>
<td><%=thpaystr%></td>
<td><%=newthpaystr%></td>
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

int alltelpay = mmpaycount+tcpaycount+unipaycount+openpaycount+wopluspaycount;
int newalltelpay = newmmpaycount+newtcpaycount+newunipaycount+newopenpaycount+newwopluspaycount;
int allthpay = yeepaycount+alipaycount+tenpaycount+unionpaycount+mzpaycount+olpaycount;
int newallthpay = newyeepaycount+newalipaycount+newtenpaycount+newunionpaycount+newmzpaycount+newolpaycount;

/*String mmpaycountstr = String.format("%.2f(%.2f)", mmpaycount/100.0,newmmpaycount/100.0);
String yeepaycountstr = String.format("%.2f(%.2f)", yeepaycount/100.0,newyeepaycount/100.0);
String alipaycountstr = String.format("%.2f(%.2f)", alipaycount/100.0,newalipaycount/100.0);
String tenpaycountstr = String.format("%.2f(%.2f)", tenpaycount/100.0,newtenpaycount/100.0);
String unionpaycountstr = String.format("%.2f(%.2f)", unionpaycount/100.0,newunionpaycount/100.0);*/

String telpaycountstr = String.format("%.2f", alltelpay/100.0);
String newalltelpaystr = String.format("%.2f",newalltelpay/100.0);
String thpaycountstr = String.format("%.2f", allthpay/100.0);
String newallthpaystr = String.format("%.2f",newallthpay/100.0);
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
<td><%=regcount%></td>
<td><%=allrealregisterNum%></td>
<td></td>
<td><%=allactRegistrStr%></td>
<td><%=alldate1R%></td>
<td><%=alldate7R%></td>
<td><%=alldate30R%></td>
<td><%=telpaycountstr%></td>
<td><%=newalltelpaystr%></td>
<td><%=thpaycountstr%></td>
<td><%=newallthpaystr%></td>
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
function gametypechg(obj){
	document.mainForm.action="cfgCPGameDataReq.jsp?op=selgame&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}
function gamechg(obj)
{
	//alert(obj.value);
	document.mainForm.action="cfgCPGameDataReq.jsp?op=selgame&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}




function yearchg(obj)
{
	document.mainForm.action="cfgCPGameDataReq.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="cfgCPGameDataReq.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="cfgCPGameDataReq.jsp?op=sch&BSS_NO=<%=BSS_NO%>";
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
