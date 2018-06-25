<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="dao.*"%>
<%@ page import="model.*"%>
<%@ page import="common.*"%>
<%@ page import="java.util.*"%>
<%@ page import="util.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
%>
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
	
				<td width="15%">时间
					<select name="selYear" id="selYear">
					</select>&nbsp;年
				
					<select name="selMonth" onchange = "chgMonth()" id="selMonth">
					</select>&nbsp;月
				
					<select name="selDay" id="selDay">
					</select>&nbsp;日
				</td>
				<td width="8%">运营商&nbsp;&nbsp;
				<select name="pay_type"  id="pay_type">
				 	<option value="mm">移动</option>
				 	<option value="">请选择</option>
				 	<option value="uni">联通</option>
				 	<option value="tel">电信</option>
				</select>&nbsp;
			</td>
			
			
				<td width="10%"><a href="#" onclick="goSearch()">
						<img src="../_js/ico/btn_ok.gif" border="0" alt="确定" align="absmiddle"></img></a>
				</td>
			</tr>
		</table>

		
		<div id="highchartsContainer"></div>
		
		<table id="TableColor" width="100%" border="0">
			<tr>
				<td>序号</td>
				<td>省份名称</td>
				<td>新增用户</td>
                <td>新增付费</td>
                <td>新增ARPU</td>
				<td>付费金额</td>
				<td>付费次数</td>
				<td>付费金额占比</td>
				<td>省份新增占比</td>
				<td>浮动幅度</td>
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
		var year,month,day,channelNo,gameNo,businessNo,gameType;
		
		function goSearch() {
			year = $("#selYear :selected").text();
			month = $("#selMonth :selected").text();
			day = $("#selDay :selected").text();
			
			
			channelNo = $("#channel_no option:selected").val();
			gameNo = $("#game_no option:selected").val();
			businessNo = $("#business_no :selected").val();
			apkNo = $("#apk_no :selected").val();
			payType =  $("#pay_type option:selected").val();

			$.post("../servlet/provinceDataAction", {
				year : year,
				month : month,
				day : day,
				channelNo : channelNo,
				gameNo : gameNo,
				businessNo : businessNo,
				apkNo : apkNo,
				payType : payType
			}, function(data) {
				
				var info = data.info;
				
				if(info == 'noData'){
					alert("暂无数据");
				}else{
					fillHighcharts(data);
					$("#TableColor").find("tr").each(function(i) {
						if (i != 0)
							$(this).remove();
					});
	
					$.each(data.list, function(i, pd) {
						var table_tr = "<tr align='center'>";
						table_tr += "<td>" + (i + 1) + "</td>";
						table_tr += "<td>" + pd.province + "</td>";
						table_tr += "<td>" + pd.newAcount + "</td>";
           			    table_tr += "<td>" + pd.newPay* 1 / 100 + "</td>";
                        table_tr += "<td>" + pd.arpu + "</td>";
						table_tr += "<td>" + pd.pay * 1 / 100 + "</td>";
						table_tr += "<td>" + pd.feeNum + "</td>";
						table_tr += "<td>" + pd.payProportion + "%</td>";
						table_tr += "<td>" + pd.newAcountProportion + "%</td>";
						if(pd.floatProportion != "divideByZero"){
							var temp = pd.floatProportion * 1;
							
							if (temp > 0) {
								table_tr += "<td><font color='red'>涨" + temp
										+ "%</font></td>";
							} else {
								table_tr += "<td><font color='green'>降" + (temp * -1)
										+ "%</font></td>";
							}
						}else{
							table_tr += "<td>上一日无数据</td>";
						}
						table_tr += "<tr/>";
						$("#TableColor").append(table_tr);
					});
					
				}
				
			}, "json");
		}
		
		function fillHighcharts(jsonData){
			
			$('#highchartsContainer').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: '单机省份报表'
		        },
		        subtitle: {
		            text: year + '年' + month + '月' + day + '日'
		        },
		        xAxis: {
		            categories: jsonData.vo.categories
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: '百分比'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:15px">{point.key}</span>',
		            pointFormat: '' +
		                '',
		            footerFormat: '&nbsp;&nbsp;<b>{point.y:.2f} %',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: [
		                 	{
		                 		name : jsonData.vo.series[0].name,
		                 		data : jsonData.vo.series[0].data
		                	 }
		                 ]
		    });	
		}
	</script>
</body>
</html>
