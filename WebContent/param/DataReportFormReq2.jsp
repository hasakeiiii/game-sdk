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
	ArrayList<ReportFormData2> list = new ArrayList<ReportFormData2>();
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
		list = ReportFormData2.getDataList(fromDate, toDate, game_type, gameNo, channelNo);
		
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
			<td>商务</td>
			<td>渠道包号</td>
			<td>渠道名称</td>
			<td>激活数</td>
			<td>注册数</td>
			<td>注册/激活</td>
			<td>次留</td>
			<td>7留</td>
			<td>月留</td>
			<td>第三方支付</td>
			<td>话费支付</td>
			<td>付费人数</td>
			<td>付费率</td>
			<td>ARPPU</td>
			<td>ARPU</td>
			<td>总流水</td>
		</tr>
		<%
			String periodstr = fromDate+"至"+toDate;
				
			
				
				
				
				for(int i=0;i<list.size();i++){
				
				String gamename = "";
				String gameno = "";
				String businessername="";
				String packetid = "";
				String channelname="";
		        int activatenum = 0;
				int registnum = 0; 
				String a_r = "0.0%";                  
				int day1 = 0;
				int day7 = 0;
				int day30 = 0;
				int thirdpaynum = 0;
				int callpaynum = 0;
				int consumernum = 0;
				
				String payratio = "0.0%";
				String arppu = "0.0";
				String arpu = "0.0";
				
				int amountall = 0;
				
					ReportFormData2 data = list.get(i);
					gamename = data.getGameName();
					gameno = data.getGameID();
					businessername = data.getBusinesserName();
					packetid = data.getPacketId();
					channelname = data.getChannelName();
		            activatenum = data.getActivateNum();
					registnum = data.getRegisterNum();
					if(activatenum!=0){
						double a_R = (double)registnum/activatenum*100;
						a_r = String.format("%.2f", a_R)+"%";
					}
					day1 = data.getDay1();
					day7 = data.getDay7();
					day30 = data.getDay30();
					thirdpaynum = data.getThirdPay();
					callpaynum = data.getCallPay();
					consumernum = data.getConsumerNum();
					amountall = thirdpaynum + callpaynum;
					if(consumernum!=0){
						double arpU = (double)amountall/consumernum;
						arpu = String.format("%.2f", arpU);
					}
					if(registnum!=0){
						double payratiO = (double)consumernum/registnum*100;
						payratio = String.format("%.2f", payratiO)+"%";
						double arppU = (double)amountall/registnum;
						arppu = String.format("%.2f", arppU);
					}
					
					
					if(amountall==0&&registnum==0&&activatenum==0)
					continue;
		%>
		<tr>
			<td><%=periodstr%></td>
			<td><%=gamename%></td>
			<td><%=gameno%></td>
			<td><%=businessername%></td>
			<td><%=packetid%></td>
			<td><%=channelname%></td>
			<td><%=activatenum%></td>
			<td><%=registnum%></td>
			<td><%=a_r%></td>
			<td><%=day1%></td>
			<td><%=day7%></td>
			<td><%=day30%></td>
			<td><%=thirdpaynum%></td>
			<td><%=callpaynum%></td>
			<td><%=consumernum%></td>
			<td><%=payratio%></td>
			<td><%=arppu%></td>
			<td><%=arpu%></td>
			
			
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
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function frommonthchg(obj){
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function fromdaychg(obj){
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function toyearchg(obj){
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function tomonthchg(obj){
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function todaychg(obj){
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function gametypechg(obj){
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function gamenochg(obj){
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function channelchg(obj){
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function typechg(obj){
		document.mainForm.action="DataReportFormReq2.jsp?BSS_NO=<%=BSS_NO%>";
		document.mainForm.submit();
	}
	function goSearch(){
		document.mainForm.action="DataReportFormReq2.jsp?op=sch&?BSS_NO=<%=BSS_NO%>";
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