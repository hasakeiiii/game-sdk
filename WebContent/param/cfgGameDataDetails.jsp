<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
	String game_type = "on_line";//2015-04-07 默认为网游
	String packet_id = azul.JspUtil.getStr(request.getParameter("packet_id"), "");
	String game_no = azul.JspUtil.getStr(request.getParameter("game_no"), "");
	String business_no = azul.JspUtil.getStr(request.getParameter("business_no"), "");
	String channel_no = azul.JspUtil.getStr(request.getParameter("channel_no"), "");
	
	String originDateTime = DateUtil.getDate();

	String fromyear1 = DateUtil.getStrFromDate("year", originDateTime);
	String frommonth1 = DateUtil.getStrFromDate("month", originDateTime);
	String fromday1 = DateUtil.getStrFromDate("day", originDateTime);
	String toyear1 = DateUtil.getStrFromDate("year", originDateTime);
	String tomonth1 = DateUtil.getStrFromDate("month", originDateTime);
	String today1 = DateUtil.getStrFromDate("day", originDateTime);

	String fromyear = azul.JspUtil.getStr(request.getParameter("fromyear"), fromyear1);
	String frommonth = azul.JspUtil.getStr(request.getParameter("frommonth"), frommonth1);
	String fromday = azul.JspUtil.getStr(request.getParameter("fromday"), fromday1);
	String toyear = azul.JspUtil.getStr(request.getParameter("toyear"),toyear1);
	String tomonth = azul.JspUtil.getStr(request.getParameter("tomonth"), tomonth1);
	String today = azul.JspUtil.getStr(request.getParameter("today"),today1);
	String BSS_NO = azul.JspUtil.getStr(request.getParameter("BSS_NO"),"");

	Map<String,String> yearMap=new HashMap<String,String>();
	yearMap.put("2014", "2014");
	yearMap.put("2015", "2015");
	Map<String,String> monthMap=new HashMap<String,String>();
	for(int i=1;i<=12;i++){
		if(i<10)
			monthMap.put("0"+i, i+"");
		else
			monthMap.put(i+"", i+"");
		}
	Map<String,String> dayMap = new HashMap<String,String>();
	for(int i=1;i<=31;i++){
		if(i<10)
			dayMap.put("0"+i, i+"");
		else
			dayMap.put(i+"", i+"");
		}

	String op = request.getParameter("op");
	String appsql = "select no,concat(name,'(',no,')') from app where 1=1 ";//查询游戏
	String bssSql = "select no,name from businesser  where 1=1 ";//查询商务
	String channelsql = String.format("select channel_no,concat(channel.name,'(',channel_no,')') from cooperation,channel where channel.no=cooperation.channel_no ");
	if(!StringUtil.is_nullString(game_no)){
		channelsql += " and app_no='"+game_no+"'";
	}
	if(!StringUtil.is_nullString(business_no)){
		channelsql += " and cooperation.business_no='"+business_no+"'";
	}
    
     
	if(!StringUtil.is_nullString(BSS_NO)){
		if(BSS_NO.equals("null")){
			BSS_NO = "";
		}
		else{
	  	business_no = BSS_NO;
	  	bssSql +=" and no='"+business_no+"'";
		}
	}
	if(StringUtil.is_nullString(game_no)&&StringUtil.is_nullString(channel_no)){
		
	
	}else{
		if(!StringUtil.is_nullString(game_no)&&StringUtil.is_nullString(channel_no)){
			//当选择了游戏的时候，要找到它对应的渠道
			channelsql = "select channel_no,concat(channel.name,'(',channel_no,')') "+
			"from cooperation,channel where channel.no=cooperation.channel_no and app_no='"+game_no+"'";
			
		}
		if(!StringUtil.is_nullString(channel_no)&&StringUtil.is_nullString(game_no)){
			//当渠道选择不为空时，需要将游戏限制为只是渠道的
			appsql = "select no,concat(name,'(',no,')') from cooperation,app WHERE "+
			"cooperation.app_no=app.no and channel_no='"+channel_no+"'";
			
		}
	}
	appsql+="and game_type='"+game_type+"' ";
	channelsql += " GROUP BY channel_no";
	String packetidsql = String.format("select id,packet_id from Cooperation where channel_no='%s' and app_no='%s' and business_no='%s'"
				                     ,channel_no,game_no,business_no);
	
	
	DebuUtil.log(appsql);
	DebuUtil.log(channelsql);
	Map gameMap,channelMap,packetMap,businesserMap;
	BusinesserDao businesserDao=new BusinesserDao();
	CooperationDao cooperationDao=new CooperationDao();
	
	
	businesserMap=businesserDao.getSelectMap(bssSql);
	gameMap=cooperationDao.getSelectMap(appsql);
	channelMap = cooperationDao.getSelectMap(channelsql);
	packetMap=cooperationDao.getSelectMap(packetidsql);
	
	ChannelDataDao channelDataDao = new ChannelDataDao();
	ArrayList<Gamedata1> list = new ArrayList<Gamedata1>();
	int recordcount = 0;
	String bdate = fromyear+"-"+frommonth+"-"+fromday;
	String edate = toyear+"-"+tomonth+"-"+today;
	if((!StringUtil.is_nullString(op)) &&(op.equals("sch"))){
		list = Gamedata1.getDataList(bdate,edate,business_no,game_no,channel_no,packet_id);
	}
	
	

	
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css" />
</head>
<body>
	<form name="mainForm" method="post" style="margin:0;padding:0">
		<table width="100%" class="table_noborder">

				<tr>
				<td width="25%">起始时间&nbsp; 
				年 <select name="fromyear" onchange=fromyearchg(this) id="fromyear">
				</select>&nbsp; 
				月 <select name="frommonth" onchange=frommonthchg(this) id="frommonth">
				</select>&nbsp;
				日 <select name="fromday" onchange=fromdaychg(this) id="fromday">
				</select>
				</td>
				<td width="25%">结束时间&nbsp; 
				年 <select name="toyear" onchange=toyearchg(this) id="toyear"></select>&nbsp; 
				月 <select name="tomonth" onchange=tomonthchg(this) id="tomonth"></select>&nbsp;
				日 <select name="today" onchange=todaychg(this) id="today"></select>
				</td>
				<td width="25%">游戏&nbsp;&nbsp; <select name="game_no"
					onchange=gamechg(this) id="game_no">
						<option value="">请选择</option>
				</select>&nbsp;</td>
				<td width="25%">所属商务&nbsp;&nbsp;
				<select name="business_no" onchange=businesschg(this) id="business_no">
  				<option value="">请选择</option>
				</select>&nbsp;
				</td>
			</tr>
			<tr>
				<td width="25%">渠道&nbsp;&nbsp; <select name="channel_no"
					onchange=channelchg(this) id="channel_no">
						<option value="">请选择</option>
				</select>&nbsp;</td>
				<td width="25%">APK包号&nbsp;&nbsp; <select name="packet_id"
					onchange=packetchg(this) id="packet_id">
						<option value="">请选择</option>
				</select>&nbsp;</td>

				<td width="25%"><a href="#" onclick="goSearch()"><img
						src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle" />
				</a></td>
				<td></td>
			</tr>

		</table>
		<table id="TableColor" width="100%" border="0">
			<tr>
				<td>日期</td>
				<td>游戏名</td>
				<td>渠道名</td>
				<td>激活用户</td>
				<td>注册用户</td>
				<td>激活注册率</td>
				<td>登录用户</td>
				<td>次日留存</td>
				<td>周留存</td>
				<td>月留存</td>
				<td>支付金额</td>
				<td>付费率</td>
				<td>新增用户支付</td>
				<td>新用户付费率</td>
				<td>ARPPU</td>
				<td>ARPU</td>
			</tr>
			<%
				String date = "";
				String gameName = "";
				String channelName = "";
				String activateNum = "";
				String registerNum = "";
				String aRrate = "";
				String loginNum = "";
				String day1 = "";
				String day7 = "";
				String day30 = "";
				String allPay = "";
				String payRate = "";
				String newUserPay = "";
				String newUserPayRate = "";
				String aRPPU = "";
				String aRPU = "";
				if(list.size()>0){
				for(int i=0;i<list.size();i++){
					Gamedata1 data = new Gamedata1();
					data = list.get(i);
					date = data.getDate();
					gameName = data.getGameName();
					channelName = data.getChannelName();
					activateNum = data.getActivateNum();
					registerNum = data.getRegisterNum();
					aRrate = data.getaRrate();
					loginNum = data.getLoginNum();
					day1 = data.getDay1();
					day7 = data.getDay7();
					day30 = data.getDay30();
					allPay = data.getAllPay();
					payRate = data.getPayRate();
					newUserPay = data.getNewUserPay();
					newUserPayRate = data.getNewUserPayRate();
					aRPPU = data.getaRPPU();
					aRPU = data.getaRPU();
			%>
			<tr>
				<td><%=date %></td>
				<td><%=gameName %></td>
				<td><%=channelName %></td>
				<td><%=activateNum %></td>
				<td><%=registerNum %></td>
				<td><%=aRrate %></td>
				<td><%=loginNum %></td>
				<td><%=day1 %></td>
				<td><%=day7 %></td>
				<td><%=day30 %></td>
				<td><%=allPay %></td>
				<td><%=payRate %></td>
				<td><%=newUserPay %></td>
				<td><%=newUserPayRate %></td>
				<td><%=aRPPU %></td>
				<td><%=aRPU %></td>
			</tr>
			<%
				}
				
			}
			%>


		</table>
	</form>
	
	<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
		media="screen" />
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
	<script>
	function gamechg(obj)
	{
		document.mainForm.action="cfgGameDataDetails.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}

	function businesschg(obj)
	{
		document.mainForm.action="cfgGameDataDetails.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function channelchg(obj)
	{
		document.mainForm.action="cfgGameDataDetails.jsp?op=selchannel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}

	function packetchg(obj)
	{
		document.mainForm.action="cfgGameDataDetails.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}

	function fromyearchg(obj){
		document.mainForm.action="cfgGameDataDetails.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function frommonthchg(obj){
		document.mainForm.action="cfgGameDataDetails.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function fromdaychg(obj){
		document.mainForm.action="cfgGameDataDetails.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function toyearchg(obj){
		document.mainForm.action="cfgGameDataDetails.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function tomonthchg(obj){
		document.mainForm.action="cfgGameDataDetails.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function todaychg(obj){
		document.mainForm.action="cfgGameDataDetails.jsp?op=sel&BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	
	function goSearch(){
		document.mainForm.action="cfgGameDataDetails.jsp?op=sch&BSS_NO=<%=BSS_NO%>";
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
	out.println(azul.JspUtil.initSelect("fromyear",yearMap,fromyear));
	out.println(azul.JspUtil.initSelect("frommonth",monthMap,frommonth));
	out.println(azul.JspUtil.initSelect("fromday",dayMap,fromday));
	out.println(azul.JspUtil.initSelect("toyear",yearMap,toyear));
	out.println(azul.JspUtil.initSelect("tomonth",monthMap,tomonth));
	out.println(azul.JspUtil.initSelect("today",dayMap,today));
	out.println(azul.JspUtil.initSelect("business_no",businesserMap,business_no));
	out.println(azul.JspUtil.initSelect("game_no", gameMap, game_no));
	out.println(azul.JspUtil.initSelect("channel_no", channelMap,channel_no));
	out.println(azul.JspUtil.initSelect("packet_id", packetMap,packet_id));
%>
</script>
