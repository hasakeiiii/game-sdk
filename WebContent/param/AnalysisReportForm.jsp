<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dao.*,java.math.*,org.jfree.chart.*,org.jfree.chart.plot.PiePlot,org.jfree.chart.title.TextTitle,org.jfree.chart.labels.*,  
org.jfree.data.general.DefaultPieDataset,java.text.NumberFormat"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<%@ page import="viewmodel.*" %>
<jsp:include page="../check.jsp?check_role=admin,sid" flush="true" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setAttribute("basePath", basePath);
	
	DebuUtil.log("AnalysisReportForm");
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
	String business_no = "";

	Map yearMap=SearchCom.getYearMap();
        Map monthMap=SearchCom.getMonthMap();
	Map<String,String> dayMap = SearchCom.getDayMap(fromyear,frommonth);
	Map gameMap,channelMap;
	Map typeMap=new HashMap<String,String>();
	typeMap.put("A", "渠道");
	
	CooperationDao cooperationDao=new CooperationDao();
	Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
	String role = sysUser.getRole();

        if(StringUtil.is_nullString(game_type))//getDayMap
        { 
           game_type = SearchCom.getGameType(role);
        }

        Map<String,String> gameTypeMap = SearchCom.getGameTypeMap(role);

	gameMap=SearchCom.getGameMap(game_type,gameNo, channelNo, business_no);//
	//DebuUtil.log(gameMap.toString());
	channelMap = SearchCom.getChannelMap(game_type,gameNo, channelNo, business_no);
	
	ArrayList<AnalysisData> list = new ArrayList<AnalysisData>();
	int recordcount = 0;
	int pagesize=31;//每页记录数
	int pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
	String fromDate = "";
	String toDate = "";
	String fileName = "";
	if((!StringUtil.is_nullString(op))&&(op.equals("sch"))){
		//当点击确定是进入查询，执行此段逻辑
		fromDate = fromyear+"-"+frommonth+"-"+fromday;
		toDate = toyear+"-"+tomonth+"-"+today;
		
		DebuUtil.log("fromDate"+fromDate+"toDate"+toDate);
		DebuUtil.log("game_type"+game_type+"gameNo"+gameNo+"channelNo"+channelNo+"type"+type);
		list = AnalysisData.getDataList(fromDate, toDate, gameNo, channelNo);
		
		recordcount = list.size();
		
		Integer fromYear = Integer.parseInt(fromyear);
		Integer fromMonth = Integer.parseInt(frommonth);
		Integer fromDay = Integer.parseInt(fromday);
		Integer toYear = Integer.parseInt(toyear);
		Integer toMonth = Integer.parseInt(tomonth);
		Integer toDay = Integer.parseInt(today);
		
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(fromYear, fromMonth - 1, fromDay);
		Date fromDate2 = cal.getTime();
		cal.set(toYear, toMonth - 1, toDay);
		Date toDate2 = cal.getTime();
		
		AnalysisDataDao dao = new AnalysisDataDao();
		
		List<ArrayList> dataList = dao.getData(fromDate2, toDate2, channelNo, gameNo);
		DefaultPieDataset dpd = new DefaultPieDataset();
		
		int total = 0;
		
		
		
		for(ArrayList l : dataList){
			total += ((BigDecimal)l.get(1)).intValue();
		}
		
		int size = dataList.size();
		
		if(size > 4){
			int totaltemp = 0;
			for(int i = 0;i < 4;i++){
				ArrayList l = dataList.get(i);
				String name = (String)l.get(0);
				int mmFee = ((BigDecimal)l.get(1)).intValue();
				dpd.setValue(name, mmFee);
				totaltemp += mmFee;
			}
			
			dpd.setValue("其他", total - totaltemp);
			
		}else{
			for(int i = 0;i < size;i++){
				ArrayList l = dataList.get(i);
				String name = (String)l.get(0);
				int mmFee = ((BigDecimal)l.get(1)).intValue();
				dpd.setValue(name, mmFee);
			}
		}
		
		
		JFreeChart chart = jfreeChartUtil.createPie(dpd, "标题", jfreeChartUtil.standardChartTheme());
		PiePlot pieplot = (PiePlot) chart.getPlot();  
		StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}  :  ({1}   {2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
		pieplot.setLabelGenerator(standarPieIG);  
		//没有数据的时候显示的内容  
		pieplot.setNoDataMessage("无数据显示");  
		pieplot.setLabelGap(0.02D); 
		
		fileName = DateUtil.getDate(fromDate2) + DateUtil.getDate(toDate2) + channelNo + gameNo + ".jpg";
		String filePath = request.getSession().getServletContext().getRealPath("/") 
				+ "jfreeimg\\" + fileName;
		jfreeChartUtil.saveFile(chart, filePath, 1731, 400);
		
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
				<td width="50%">起始时间&nbsp; 
				年 <select name="fromyear"  id="fromyear">
				</select>&nbsp; 
				月 <select name="frommonth"  id="frommonth">
				</select>&nbsp;
				日 <select name="fromday" id="fromday">
				</select>
				</td>
				<td width="50%">结束时间&nbsp; 
				年 <select name="toyear" id="toyear"></select>&nbsp; 
				月 <select name="tomonth"  id="tomonth"></select>&nbsp;
				日 <select name="today"  id="today"></select>
				</td>
				
			</tr>
			<tr>
				<td width="50%">游戏名&nbsp;&nbsp; <select name="game_no"
					onchange=gamenochg(this) id="game_no">
						<option value="">请选择</option>
				</select>&nbsp;</td>

				<td width="50%">渠道&nbsp;&nbsp; <select name="channel_no"
					onchange=channelchg(this) id="channel_no">
						<option value="">请选择</option>
				</select>&nbsp;</td>
				
				
			</tr>
			<tr>
			<td width="50%"></td>
			<td width="50%" >
			<a href="#" onclick="goSearch()">
			<img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"/></a>
			</td>
			<td width="30%"></td>
			<td width="30%"></td>
			</tr>
		</table>
	</form>
	
	<div id = "imgHolder"><img src="<%=request.getAttribute("basePath") + "jfreeimg/" + fileName %>"/></div>	
	
		<table id="TableColor" width="100%" border="0">
			<tr>
				<td>日期</td>
				<td>游戏名</td>
				<td>渠道名称</td>
				<td>渠道包号<br></td>
				<td>今日付费</td>
				<td>付费浮动</td>
				<td>付费用户数</td>
				<td>付费次数</td>
				<td>付费ARPU</td>
				<td>本月付费次数</td>
				<td>截止付费金额</td>
			</tr>
			<%
				String date = "";
				String gameName = "";
				String channelName = "";
				String packetId = "";
				String mmPay = "";
				String paychange = "";
				String pay_times = "";
				String allpay_times = "";
				String allpay = "";
                                int pay_user_num = 0;
				String pay_ARPU = "";

				float lastday = 0;
				String tempId = "";
			        for(int i=0;i<list.size();i++){
				//DebuUtil.log(list.get(i).toString());
                                AnalysisData analysisData=(AnalysisData)list.get(i);
				date = analysisData.getDate();
				gameName = analysisData.getGameName();
				channelName = analysisData.getChannelName();
				packetId = analysisData.getPacketId();
				
				mmPay = analysisData.getMmPay();
				mmPay = AnalysisData.formatPay(mmPay);
				pay_times = analysisData.getPay_times();
				allpay_times = analysisData.getAllpay_times();
				allpay = analysisData.getAllpay();
				allpay = AnalysisData.formatPay(allpay);
				paychange = analysisData.getPaychgratio();
				tempId = packetId;
				lastday = Float.valueOf(mmPay);
                                pay_user_num = analysisData.getPayUserNum();
                                if(pay_user_num > 0)
	                       {	     
		                   pay_ARPU  = String.format("%.2f",Float.valueOf(mmPay)*1.0/pay_user_num);
	                        }	                               
				%>
				<tr>
				<td><%=date %></td>
				<td><%=gameName %></td>
				<td><%=channelName %></td>
				<td><%=packetId %></td>
				<td><%=mmPay %></td>
				<td><%=paychange %></td>
				<td><%=pay_user_num %></td>
				<td><%=pay_times %></td>
				<td><%=pay_ARPU %></td>
				<td><%=allpay_times %></td>
				<td><%=allpay %></td>
				</tr>
			<% 
				}
			%>

		</table>

<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="../_js/jquery.alerts.js"></script>

 <script>
 	var fromYear,fromMonth,fromDay,toYear,toMonth,toDay,gameNo,channelNo;
 	
	function fromyearchg(obj){
		document.mainForm.action="AnalysisReportForm.jsp";
		document.mainForm.submit();
	}
	function frommonthchg(obj){
		document.mainForm.action="AnalysisReportForm.jsp";
		document.mainForm.submit();
	}
	function fromdaychg(obj){
		document.mainForm.action="AnalysisReportForm.jsp";
		document.mainForm.submit();
	}
	function toyearchg(obj){
		document.mainForm.action="AnalysisReportForm.jsp";
		document.mainForm.submit();
	}
	function tomonthchg(obj){
		document.mainForm.action="AnalysisReportForm.jsp";
		document.mainForm.submit();
	}
	function todaychg(obj){
		document.mainForm.action="AnalysisReportForm.jsp";
		document.mainForm.submit();
	}
	
	function gamenochg(obj){
		document.mainForm.action="AnalysisReportForm.jsp";
		document.mainForm.submit();
	}
	function channelchg(obj){
		document.mainForm.action="AnalysisReportForm.jsp";
		document.mainForm.submit();
	}
	
	function goSearch(){
		document.mainForm.action="AnalysisReportForm.jsp?op=sch";
		document.mainForm.submit();
		
	}
</script>

<script type="text/javascript" src="../_js/elemUtil.js"></script>
<script type="text/javascript" src="../_js/TableColor.js"></script>
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
	out.println(azul.JspUtil.initSelectSortByValuePinyin("game_no",gameMap,gameNo));
	out.println(azul.JspUtil.initSelectSortByValuePinyin("channel_no",channelMap,channelNo));
 	%>
</script>