<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*" %>
<%@ page import="model.*" %>
<%@ page import="common.*" %>
<%@ page import="java.util.*" %>
<%@ page import="util.*" %>
<%@ page import="viewmodel.*" %>
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

selmonth =SearchCom.getCurMonth(selmonth);
selyear =SearchCom.getCurYear(selyear);

//selmonth="04";
DebuUtil.log("selmonth="+selmonth);

CooperationDao cooperationDao=new CooperationDao();
java.util.Map gameMap=cooperationDao.getSelectMap("select no,name from app");

BusinesserDao businesserDao=new BusinesserDao();
java.util.Map businesserMap=businesserDao.getSelectMap("select no,name from businesser");

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
ArrayList<UserLevelList> list = new ArrayList<UserLevelList>();
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
		
		list = UserLevelList.get_UserLevelList(null,null,null,cooperation.packet_id,cooperation.settle_type,distype,beginDate,endDate);
		//DebuUtil.log("app_no="+cooperation.app_no+"packet_id="+cooperation.packet_id);
		//DebuUtil.log("size="+list.size());
	}
	else
	{
		//DebuUtil.log("game_no="+game_no);
		//DebuUtil.log("channel_no="+channel_no);//
		
		if((!StringUtil.is_nullString(game_no)) || (!StringUtil.is_nullString(business_no))|| (!StringUtil.is_nullString(channel_no)))
		list = UserLevelList.get_UserLevelList(game_no,business_no,channel_no,null,null,distype,beginDate,endDate);
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
<td width="15%">时间&nbsp;年
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
<td>注册帐号</td>
<td>3-10级</td>
<td>11-20级</td>
<td>21-30级</td>
<td>31-40级</td>
<td>40以上级</td>
</tr>
<%



int regcount = 0;
int level1 = 0;
int level2 = 0;
int level3 = 0;
int level4 = 0;
int level5 = 0;

for(int i=0;i<list.size();i++){
	String curdate = DateUtil.getDate();
	long datedif = 0;
	UserLevelList gamedata=(UserLevelList)list.get(i);
	float value;
	
	//算总数
	regcount += gamedata.registerNum;
	level1 += gamedata.levle1Num;
	level2 += gamedata.levle2Num;
	level3 += gamedata.levle3Num;
	level4 += gamedata.levle4Num;
	level5 += gamedata.levle5Num;	
	
	datedif = DateUtil.getDayDiff(gamedata.date, curdate);
	
	String datestr = gamedata.date;
	

	
	String strlevel1= "";
	value = 0;
	if(gamedata.registerNum > 0)
	{
		value = gamedata.registerNum;
		value = gamedata.levle1Num/value;
	}
	strlevel1= String.format("%.2f%s(%d)",value*100,"%",gamedata.levle1Num);
	
	String strlevel2= "";
	value = 0;
	if(gamedata.registerNum > 0)
	{
		value = gamedata.registerNum;
		value = gamedata.levle2Num/value;
	}
	strlevel2= String.format("%.2f%s(%d)",value*100,"%",gamedata.levle2Num);
	
	
	String strlevel3= "";
	value = 0;
	if(gamedata.registerNum > 0)
	{
		value = gamedata.registerNum;
		value = gamedata.levle3Num/value;
	}
	strlevel3= String.format("%.2f%s(%d)",value*100,"%",gamedata.levle3Num);
	
	String strlevel4= "";
	value = 0;
	if(gamedata.registerNum > 0)
	{
		value = gamedata.registerNum;
		value = gamedata.levle4Num/value;
	}
	strlevel4= String.format("%.2f%s(%d)",value*100,"%",gamedata.levle4Num);
	
	String strlevel5= "";
	value = 0;
	if(gamedata.registerNum > 0)
	{
		value = gamedata.registerNum;
		value = gamedata.levle5Num/value;
	}
	strlevel5= String.format("%.2f%s(%d)",value*100,"%",gamedata.levle5Num);	
	
%>
<tr>
<td><%=datestr%></td>
<td><%=gamedata.registerNum%></td>
<td><%=strlevel1%></td>
<td><%=strlevel2%></td>
<td><%=strlevel3%></td>
<td><%=strlevel4%></td>
<td><%=strlevel5%></td>
</tr>
<%
}
%>

<%

String allstrlevel1= "";
float value2 = 0;
if(regcount > 0)
{
	value2 = regcount;
	value2 = level1/value2;
}
allstrlevel1= String.format("%.2f%s(%d)",value2*100,"%",level1);

String allstrlevel2= "";
value2 = 0;
if(regcount > 0)
{
	value2 = regcount;
	value2 = level2/value2;
}
allstrlevel2= String.format("%.2f%s(%d)",value2*100,"%",level2);

String allstrlevel3= "";
value2 = 0;
if(regcount > 0)
{
	value2 = regcount;
	value2 = level3/value2;
}
allstrlevel3= String.format("%.2f%s(%d)",value2*100,"%",level3);

String allstrlevel4= "";
value2 = 0;
if(regcount > 0)
{
	value2 = regcount;
	value2 = level4/value2;
}
allstrlevel4= String.format("%.2f%s(%d)",value2*100,"%",level4);

String allstrlevel5= "";
value2 = 0;
if(regcount > 0)
{
	value2 = regcount;
	value2 = level5/value2;
}
allstrlevel5= String.format("%.2f%s(%d)",value2*100,"%",level5);

%>
<tr>
<td>总计</td>
<td><%=regcount%></td>
<td><%=allstrlevel1%></td>
<td><%=allstrlevel2%></td>
<td><%=allstrlevel3%></td>
<td><%=allstrlevel4%></td>
<td><%=allstrlevel5%></td>
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
	document.mainForm.action="UserLevelList.jsp?op=selgame";
	document.mainForm.submit();
}
function businesschg(obj)
{
	//alert(obj.value);
	document.mainForm.action="UserLevelList.jsp?op=selbusiness";
	document.mainForm.submit();
}
function channelchg(obj)
{
	document.mainForm.action="UserLevelList.jsp?op=selchannel";
	document.mainForm.submit();
}

function packetchg(obj)
{
	document.mainForm.action="UserLevelList.jsp?op=sel";
	document.mainForm.submit();
}

function yearchg(obj)
{
	document.mainForm.action="UserLevelList.jsp?op=sel";
	document.mainForm.submit();
}

function monthchg(obj)
{
	document.mainForm.action="UserLevelList.jsp?op=sel";
	document.mainForm.submit();
}

function editAct(cfg_sp_id){
   
}
function infoAct(cfg_sp_id){
    //window.location="cfgSpInfo.jsp?cfg_sp_id="+cfg_sp_id;
}
function goSearch(){
    
	document.mainForm.action="UserLevelList.jsp?op=sch";
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
out.println(azul.JspUtil.initSelect("selyear",yearMap,selyear));
out.println(azul.JspUtil.initSelect("selmonth",monthMap,selmonth));
%>
</script>
