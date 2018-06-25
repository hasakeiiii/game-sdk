<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"></meta>
<title>游戏运营管理系统</title>
<link rel="stylesheet" href="../_css/back.css" type="text/css"></link>
<link href="../_js/jquery.alerts.css" rel="stylesheet" type="text/css"
	media="screen"></link>
</head>
<body>
	<form name="mainForm" method="post" style="margin: 0; padding: 0">
		<table width="100%" class="table_setdata">

			<tr>

				<td width="30%">游戏类型&nbsp;&nbsp;
				<select name="game_type" onchange="chgGameType(this)" id="game_type">
					  	<option value="">请选择</option>
					 	<option value="off_line">单机</option>
					 	<option value="on_line">网游</option>
					 	<option value="mm_on_line">MM网游</option>
					</select>&nbsp;
				</td>
	
				<td width="30%">游戏&nbsp;&nbsp;
					<select name="game_no" onchange="chgGame(this)" id="game_no">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
	
				<td width="30%">所属商务&nbsp;&nbsp;
					<select name="business_no" id="business_no" onchange="chgBusiness()">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
			</tr>
			<tr>
				<td width="30%">渠道&nbsp;&nbsp;
					<select name="channel_no" id="channel_no" onchange = "chgChannel()">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
	
			
	
				<td width="30%">起始时间
					<select name="selYear" id="selYear">
					</select>&nbsp;年
				
					<select name="selMonth" onchange = "chgMonth()" id="selMonth">
					</select>&nbsp;月
				
					<select name="selDay" id="selDay">
					</select>&nbsp;日
				</td>
				
				<td width="30%">结束时间
					<select name="selEndYear" id="selEndYear">
					</select>&nbsp;年
				
					<select name="selEndMonth" onchange = "chgEndMonth()" id="selEndMonth">
					</select>&nbsp;月
				
					<select name="selEndDay" id="selEndDay">
					</select>&nbsp;日
				</td>
			</tr>	
			<tr>
			
				<td width="30%">APK包号&nbsp;&nbsp;
					<select name="apk_no" id="apk_no">
						<option value="">请选择</option>
					</select>&nbsp;
				</td>
				<td width="30%"></td>
				<td width="30%"><a href="#" onclick="goSearch()">
						<img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"></img></a>
				</td>
			</tr>
		</table>

		
		<div id="highchartsContainer"></div>
		
		<table id="TableColor" width="100%" border="0">
			<tr>
				<td>序号</td>
				<td>游戏名称</td>
				<td>计费点名称</td>
				<td>金额</td>
				<td>付费次数</td>
				<td>成功次数</td>
                                <td>付费成功率</td>
			</tr>
		</table>
	</form>


	<script type="text/javascript" src="../_js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="../_js/highcharts.js"></script>
	<script type="text/javascript" src="../_js/jquery.alerts.js"></script>
	<script type="text/javascript" src="../_js/TableColor.js"></script>
	<script type="text/javascript" src="../_js/commonSelectOnload.js"></script>
	<script type="text/javascript" src="../_js/common.js"></script>
	<script type="text/javascript" src="../_js/elemUtil.js"></script>
	<script>
		var year,month,day,channelNo,gameNo,businessNo,apkNo;

		function goSearch() {
			
			if($("#game_no :selected").val() == ""){
				alert("必须选择一款游戏");
			}else{
				
				year = $("#selYear :selected").text();
				month = $("#selMonth :selected").text();
				day = $("#selDay :selected").text();
				
				endYear = $("#selEndYear :selected").text();
				endMonth = $("#selEndMonth :selected").text();
				endDay = $("#selEndDay :selected").text();
				
				
				channelNo = $("#channel_no option:selected").val();
				gameNo = $("#game_no option:selected").val();
				businessNo = $("#business_no :selected").val();
				apkNo = $("#apk_no").val();
				
				$.post(
					"../servlet/FeeDataAction", 
					{
						cmd : "reportForm",
						fromYear : year,
						fromMonth : month,
						fromDay : day,
						endYear : endYear,
						endMonth : endMonth,
						endDay : endDay,
						channelNo : channelNo,
						gameNo : gameNo,
						businessNo : businessNo,
						apkNo : apkNo
					}, function(data) {
					
						$("#TableColor").find("tr").each(function(i) {
							if (i != 0)
								$(this).remove();
						});
		
						$.each(data.list, function(i, fd) {
							var table_tr = "<tr align='center'>";
							table_tr += "<td>" + (i + 1) + "</td>";
							table_tr += "<td>" + $("#game_no :selected").text() + "</td>";
							table_tr += "<td>" + fd.feeName + "</td>";
							table_tr += "<td>" + fd.amount*1/100 + "元</td>";
							table_tr += "<td>" + fd.orderTimers + "</td>";
							table_tr += "<td>" + fd.feeTimers + "</td>";
                                                        table_tr += "<td>" + (fd.feeTimers*100/fd.orderTimers).toFixed(2) + "%</td>";
							table_tr += "</tr>";
							
							$("#TableColor").append(table_tr);
						});
						
					}, "json");
				}
			}
			
		
	</script>
</body>
</html>
