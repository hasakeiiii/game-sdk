<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
	DebuUtil.log("payDataReq");
	String originDateTime = DateUtil.getDate();
	
	String fromyear1 = DateUtil.getStrFromDate("year", originDateTime);
	String frommonth1 = DateUtil.getStrFromDate("month", originDateTime);
	String fromday1 = DateUtil.getStrFromDate("day", originDateTime);
	String toyear1 = DateUtil.getStrFromDate("year", originDateTime);
	String tomonth1 = DateUtil.getStrFromDate("month", originDateTime);
	String today1 = DateUtil.getStrFromDate("day", originDateTime);
	
	String op =  request.getParameter("op");
	String game_type = azul.JspUtil.getStr(request.getParameter("game_type"), "");
	
	String fromyear = azul.JspUtil.getStr(request.getParameter("fromyear"), "");
	String frommonth = azul.JspUtil.getStr(request.getParameter("frommonth"), "");
	String fromday = azul.JspUtil.getStr(request.getParameter("fromday"), "");
	String toyear = azul.JspUtil.getStr(request.getParameter("toyear"), "");
	String tomonth = azul.JspUtil.getStr(request.getParameter("tomonth"), "");
	String today = azul.JspUtil.getStr(request.getParameter("today"), "");
	
	fromyear = fromyear.equals("")?fromyear1:fromyear;
	frommonth = frommonth.equals("")?frommonth1:frommonth;
	fromday = fromday.equals("")?fromday1:fromday;
	toyear = toyear.equals("")?toyear1:toyear;
	tomonth = tomonth.equals("")?tomonth1:tomonth;
	today = today.equals("")?today1:today;
	
	String gameNo = request.getParameter("game_no");
	String channelNo = request.getParameter("channel_no");
	String type = request.getParameter("type");
	String BSS_NO = request.getParameter("BSS_NO");
	String pay_id = request.getParameter("pay_no");
	PayDao dao = new PayDao();
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
        Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
	String role = sysUser.getRole();
	Map<String,String> gameTypeMap = new HashMap<String,String>();
        if(role.equals(Userinfo.adminOnline) || role.equals(Userinfo.operationOnline))
       {
	   gameTypeMap.put(App.ON_LINE, "网游");
           if(StringUtil.is_nullString(game_type))
           {
		game_type = App.ON_LINE;
	   }
           
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
           gameTypeMap.put(App.MM_ON_LINE, "MM网游");
           gameTypeMap.put(App.ON_LINE, "网游");
        }
	Map gameMap,channelMap;
/* 	Map typeMap=new HashMap<String,String>();
	typeMap.put("A", "渠道"); */
	
	CooperationDao cooperationDao=new CooperationDao();
	

	String appsql = "select no,concat(name,'(',no,')') from app_pay where 1=1 ";
	String channelsql = String.format("select channel_no,concat(channel.name,'(',channel_no,')') from cooperation,channel where channel.no=cooperation.channel_no ");
	if(role.equals("SA")){
		game_type = App.OFF_LINE;
	}
        

	if(StringUtil.is_nullString(gameNo)&&StringUtil.is_nullString(channelNo)){
		
	
	}else{
		if(!StringUtil.is_nullString(gameNo)&&StringUtil.is_nullString(channelNo)){
			//当选择了
			channelsql = "select channel_no,concat(channel.name,'(',channel_no,')') from cooperation,channel where channel.no=cooperation.channel_no and app_no='"+gameNo+"'";
		}
		if(!StringUtil.is_nullString(channelNo)&&StringUtil.is_nullString(gameNo)){
			//当渠道选择也不为空时，需要将游戏限制为只是渠道的
			appsql = "select no,concat(name,'(',no,')') from cooperation,app WHERE cooperation.app_no=app.no and channel_no='"+channelNo+"'";
		}
	}
	
	if(!StringUtil.is_nullString(game_type)){
	//	appsql+="and game_type='"+game_type+"' ";
	}
        appsql+=" order by id desc";
	channelsql += " GROUP BY channel_no";
	
	DebuUtil.log(gameNo);
	DebuUtil.log(channelNo);
	gameMap=cooperationDao.getSelectMap(appsql);
	MmPayDataDao mmDao = new MmPayDataDao();
	String paysql = "select pay_id,pay_name from mm_pay_data where 1=1 ";
	if(!StringUtil.is_nullString(gameNo)){
		paysql +=" and app_pay_id in (select pay_id from mm_pay_data where app_pay_id = '" + gameNo + "') ";
	}
	Map payMap = mmDao.getSelectMap1(paysql);
	//DebuUtil.log(gameMap.toString());
	channelMap = cooperationDao.getSelectMap(channelsql);
	List<ArrayList> list = new ArrayList<ArrayList>();
	
	int recordcount = 0;
	int pagesize=31;//每页记录数
	int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
	String fromDate = "";
	String toDate = "";
	if((!StringUtil.is_nullString(op))&&(op.equals("sch"))){
		//当点击确定是进入查询，执行此段逻辑
		fromDate = fromyear+"-"+frommonth+"-"+fromday;
		toDate = toyear+"-"+tomonth+"-"+today;
		if(fromDate.equals("--")){
			fromDate = "2014-01-01";
		}
		if(toDate.equals("2014-01-01")){
			toDate = DateUtil.getDate();
		}
		DebuUtil.log("fromDate"+fromDate+"toDate"+toDate);
		DebuUtil.log("game_type"+game_type+"gameNo"+gameNo+"channelNo"+channelNo+"type"+type);
	    list = dao.getAppPayList(fromDate, toDate, BSS_NO, gameNo, channelNo, pay_id);
		
	recordcount = list.size();
	}
	
	
	
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css" />

</head>

<body>
	<form name="mainForm" method="post" style="margin:0;padding:0">
		<table width="100%" class="table_setdata">
			<tr>
				<td width="30%">起始时间&nbsp; 
				年 <select name="fromyear"  id="fromyear">
				</select>&nbsp; 
				月 <select name="frommonth" id="frommonth">
				</select>&nbsp;
				日 <select name="fromday" id="fromday">
				</select>
				</td>
				<td width="30%">结束时间&nbsp; 
				年 <select name="toyear"  id="toyear"></select>&nbsp; 
				月 <select name="tomonth"  id="tomonth"></select>&nbsp;
				日 <select name="today" id="today"></select>
				</td>
				
				
				<td width="8%">游戏类型&nbsp;&nbsp;
					<select name="game_type" onchange="chgGameType(this)" id="game_type">
					  	
					</select>&nbsp;
				</td>
			</tr>
			<tr>
				<td width="15%">游戏&nbsp;&nbsp;
					<select name="game_no" onchange="gamenochg(this)" id="game_no">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>

				<td width="10%">渠道&nbsp;&nbsp;
					<select name="channel_no" id="channel_no" onchange = "chgChannel()">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
				
				<td width="30%">计费点&nbsp;&nbsp; 
				<select name="pay_no" id="pay_no">
						<option value="">请选择</option>
				</select>&nbsp;</td>
			</tr>
			<tr>
			<td width="30%">
			<a href="#" onclick="goSearch()">
			<img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"/></a>
			</td>
			<td width="30%"></td>
			<td width="30%"></td>
			</tr>
		</table>
	</form>
		<table id="TableColor" width="100%" border="0">
			<tr>
				<td>日期</td>
				<td>游戏ID</td>
				<td>渠道名称</td>
				<td>计费游戏</td>
				<td>渠道包号<br></td>
				<td>金额</td>
			</tr>
			<%
				
			for(int i=0;i<list.size();i++){
					String gameno = "";
					String channelno="";
					String packetid = "";
					int amount = 0;
					String gameName = "";
					String channelName = "";
					Object[] data = list.get(i).toArray();
		 			gameno = data[1].toString();
					AppDao appDao = new AppDao();
					App app = appDao.getAppRecord(gameno);
					
					if(app != null){
						gameName = app.name;
					}
				
					channelno = data[3].toString();
					ChannelDao channelDao = new ChannelDao();
					Channel channel = channelDao.getRecord(channelno);
					if(channel != null){
						channelName = channel.name;
					}
					packetid = data[2].toString();
					amount = Integer.parseInt(data[0].toString()); 
					
					String appPayNo = data[4].toString();
					AppPayDao appPayDao = new AppPayDao();
					AppPay appPay = appPayDao.getAppPay(appPayNo);
					if(appPay != null){
						appPayNo = appPay.getName();
					}
				%>
				<tr>
				<td><%=fromDate+"至"+toDate %></td>
				<td><%=gameName %></td>
				<td><%=channelName %></td>
				<td><%=appPayNo %></td>
				<td><%=packetid %></td>
				<td><%=amount/100 %>元</td>
				</tr>
			<% 
				}
			%>

		</table>
<%
String linkParam="";
out.print(azul.JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
%>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>

 <script>
	function fromyearchg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function frommonthchg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function fromdaychg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function toyearchg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function tomonthchg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function todaychg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function gametypechg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function gamenochg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function channelchg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function typechg(obj){
		document.mainForm.action="payDataReq.jsp";
		document.mainForm.submit();
	}
	function goSearch(){
		document.mainForm.action="payDataReq.jsp?op=sch";
		document.mainForm.submit();
	}
</script>

<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
<script type="text/javascript" src="../_js/TableBlankRow.js"></script>	
</body>
</html>
<script type="text/javascript">
	<%
	out.println(azul.JspUtil.initSelect("fromyear",yearMap,fromyear));
	out.println(azul.JspUtil.initSelect("frommonth",monthMap,frommonth));
	out.println(azul.JspUtil.initSelect("fromday",dayMap,fromday));
	out.println(azul.JspUtil.initSelect("toyear",yearMap,toyear));
	out.println(azul.JspUtil.initSelect("tomonth",monthMap,tomonth));
	out.println(azul.JspUtil.initSelect("today",dayMap,today));
//	out.println(azul.JspUtil.initSelect("type",typeMap,type));
	out.println(azul.JspUtil.initSelect("game_type",gameTypeMap,game_type));
	out.println(azul.JspUtil.initSelect("pay_no",payMap,pay_id));
	out.println(azul.JspUtil.initSelectSortByValuePinyin("game_no",gameMap,gameNo));
	out.println(azul.JspUtil.initSelectSortByValuePinyin("channel_no",channelMap,channelNo));
 	%>
</script>