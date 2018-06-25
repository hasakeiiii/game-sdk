<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<%@ page import="viewmodel.*"%>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
	DebuUtil.log("DataReportFormReq");
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

	if(!StringUtil.is_nullString(BSS_NO)) {
		if(BSS_NO.equals("null")) {
			BSS_NO = "";
		}
    }
	Map<String,String> yearMap=SearchCom.getYearMap();
	Map<String,String> monthMap=SearchCom.getMonthMap();
	Map<String,String> dayMap = new HashMap<String,String>();
	for(int i=1;i<=31;i++){
		if(i<10)
			dayMap.put("0"+i, i+"");
		else
			dayMap.put(i+"", i+"");
	}
	
    Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
	String role = sysUser.getRole();
	
	
    Map<String,String> gameTypeMap = SearchCom.getGameTypeMap(role);//new HashMap<String,String>();
    if(StringUtil.is_nullString(game_type)){
       game_type = SearchCom.getGameType(role);
    }

	Map typeMap=new HashMap<String,String>();
	typeMap.put("A", "渠道");
	
	CooperationDao cooperationDao=new CooperationDao();
	

	Map<String,String> gameMap=SearchCom.getGameMap(game_type,gameNo, channelNo, BSS_NO);
	Map<String,String> channelMap = SearchCom.getChannelMap(game_type,gameNo, channelNo, BSS_NO);
	DebuUtil.log("channelMap="+channelMap);
	ArrayList<ReportFormData> list = new ArrayList<ReportFormData>();
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
		list = ReportFormData.getDataList(fromDate, toDate, game_type,BSS_NO, gameNo, channelNo, type);
		
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
				<td width="30%">起始时间&nbsp; 年 <select name="fromyear"
					id="fromyear">
				</select>&nbsp; 月 <select name="frommonth" id="frommonth">
				</select>&nbsp; 日 <select name="fromday" id="fromday">
				</select>
				</td>
				<td width="30%">结束时间&nbsp; 年 <select name="toyear" id="toyear"></select>&nbsp;
					月 <select name="tomonth" id="tomonth"></select>&nbsp; 日 <select
					name="today" id="today"></select>
				</td>


				<td width="8%">游戏类型&nbsp;&nbsp; <select name="game_type"
					onchange="gametypechg(this)" id="game_type">

				</select>&nbsp;
				</td>
			</tr>
			<tr>
				<td width="15%">游戏&nbsp;&nbsp; <select name="game_no"
					onchange="gamenochg(this)" id="game_no">
						<option value="">请选择</option>
				</select>&nbsp;
				</td>

				<td width="10%">渠道&nbsp;&nbsp; <select name="channel_no"
					id="channel_no" onchange="channelchg()">
						<option value="">请选择</option>
				</select>&nbsp;
				</td>

				<td width="30%">显示类型&nbsp;&nbsp; <select name="type" id="type">
						<option value="">请选择</option>
				</select>&nbsp;
				</td>
			</tr>
			<tr>
				<td width="30%"><a href="#" onclick="goSearch()"> <img
						src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle" /></a>
				</td>
				<td width="30%"></td>
				<td width="30%"></td>
			</tr>
		</table>
	</form>
	<table id="TableColor" width="100%" border="0">
		<tr>
			<td>时期</td>
			<td>游戏名</td>
			<td>游戏ID</td>
			<td>渠道名称</td>
			<td>商务</td>
			<td>渠道包号<br></td>
			<td>注册数</td>
			<td>激活数</td>
			<td>支付宝</td>
			<td>易宝</td>
			<td>网银</td>
			<td>财付通</td>
			<td>拇指币</td>
			<td>专属币</td>
			<td>移动</td>
			<td>联通</td>
			<td>电信</td>
			<td>微信</td>
			
			<td>总流水</td>
		</tr>
		<%
			String periodstr = fromDate+"至"+toDate;
				String gamename = "";
				String gameno = "";
				String channelname="";
		                                String businessername="";
				String packetid = "";
				int activatenum = 0;
				int registnum = 0;
				int alipaynum = 0;
				int yeepaynum = 0;
				int unionpaynum = 0;
				int tenpaynum = 0;
				int mznum = 0;
				int onlynum = 0;
				int mmpaynum = 0;
				int unipaynum = 0;
				int tcpaynum = 0;
				int wxpaynum = 0;
				int amountall = 0;
				
				
				
				for(int i=0;i<list.size();i++){
					ReportFormData data = list.get(i);
					gamename = data.getGameName();
					gameno = data.getGameNo();
					channelname = data.getChannelName();
		                                        businessername = data.getBusinesserName();
					packetid = data.getPacketId();
					registnum = data.getRegisterNum();
					activatenum = data.getActivateNum();
					alipaynum = data.getAliPay();
					yeepaynum = data.getYeePay();
					unionpaynum = data.getUnionPay();
					tenpaynum = data.getTenPay();
					mmpaynum = data.getMmPay();
					unipaynum = data.getUniPay();
					tcpaynum = data.getTcPay();
					mznum = data.getMzPay();
					onlynum = data.getOnlyPay();
					wxpaynum = data.getWxPay();
					amountall = data.getAmountAll();
					if(amountall==0)
					continue;
		%>
		<tr>
			<td><%=periodstr%></td>
			<td><%=gamename%></td>
			<td><%=gameno%></td>
			<td><%=channelname%></td>
			<td><%=businessername%></td>
			<td><%=packetid%></td>
			<td><%=registnum%></td>
			<td><%=activatenum%></td>
			<td><%=alipaynum%></td>
			<td><%=yeepaynum%></td>
			<td><%=unionpaynum%></td>
			<td><%=tenpaynum%></td>

			<td><%=mznum%></td>
			<td><%=onlynum%></td>

			<td><%=mmpaynum%></td>
			<td><%=unipaynum%></td>
			<td><%=tcpaynum%></td>
			<td><%=wxpaynum%></td>
			<td><%=amountall%></td>
		</tr>
		<%
			}
		%>

	</table>
	<%
		String linkParam="";
	out.print(azul.JspUtil.getPageCode(pageno,pagesize,recordcount,request,linkParam));
	%>
	<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
		media="screen" />
	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../_js/jquery.alerts.js"></script>

	<script>
	function fromyearchg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function frommonthchg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function fromdaychg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function toyearchg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function tomonthchg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function todaychg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function gametypechg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function gamenochg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function channelchg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function typechg(obj){
		document.mainForm.action="DataReportFormReq.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function goSearch(){
		document.mainForm.action="DataReportFormReq.jsp?op=sch&?BSS_NO=<%=BSS_NO%>";
			document.mainForm.submit();
		}
	</script>

	<script type="text/javascript" src="../_js/elemUtil.js"></script>
	<script type="text/javascript" src="../_js/TableColor.js"></script>
	<script type="text/javascript" src="../_js/TableBlankRow.js"></script>
</body>
</html>
<script type="text/javascript">
	
<%out.println(azul.JspUtil.initSelect("fromyear",yearMap,fromyear));
	out.println(azul.JspUtil.initSelect("frommonth",monthMap,frommonth));
	out.println(azul.JspUtil.initSelect("fromday",dayMap,fromday));
	out.println(azul.JspUtil.initSelect("toyear",yearMap,toyear));
	out.println(azul.JspUtil.initSelect("tomonth",monthMap,tomonth));
	out.println(azul.JspUtil.initSelect("today",dayMap,today));
	out.println(azul.JspUtil.initSelect("type",typeMap,type));
	out.println(azul.JspUtil.initSelect("game_type",gameTypeMap,game_type));
	out.println(azul.JspUtil.initSelectSortByValuePinyin("game_no",gameMap,gameNo));
	out.println(azul.JspUtil.initSelectSortByValuePinyin("channel_no",channelMap,channelNo));%>
	
</script>